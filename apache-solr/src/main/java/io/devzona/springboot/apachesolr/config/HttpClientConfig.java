package io.devzona.springboot.apachesolr.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * Supports both HTTP and HTTPS - Uses a connection pool to re-use connections
 * and save overhead of creating connections. - Has a custom connection
 * keep-alive strategy (to apply a default keep-alive if one isn't specified) -
 * Starts an idle connection monitor to continuously clean up stale connections.
 *
 * @author rohit-sahu
 * @version 1.0
 * @since 19-Sep-2020
 */
@Slf4j
@Configuration
@EnableScheduling
public class HttpClientConfig {

    // Determines the timeout in milliseconds until a connection is established.
    private static final int CONNECT_TIMEOUT = 3000;

    // The timeout when requesting a connection from the connection manager.
    private static final int REQUEST_TIMEOUT = 3000;

    // The timeout for waiting for data
    private static final int SOCKET_TIMEOUT = 60000;

    private static final int MAX_TOTAL_CONNECTIONS = 50;
    private static final int MAX_ROUTE_CONNECTIONS = 50;
    private static final int MAX_LOCALHOST_CONNECTIONS = 100;
    private static final int DEFAULT_KEEP_ALIVE_TIME_MILLIS = 20 * 1000;
    private static final int CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS = 30;

    @Bean
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
        log.info("PoolingHttpClientConnectionManager is configuring...");
        final SSLContextBuilder builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            log.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
        }

        SSLConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(builder.build());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            log.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
        }

        final Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();

        final PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // set total amount of connections across all HTTP routes
        poolingConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        // set maximum amount of connections for each http route in pool
        poolingConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
        // increase the amounts of connections if host is localhost
        HttpHost localhost = new HttpHost("http://localhost");
        poolingConnectionManager.setMaxPerRoute(new HttpRoute(localhost), MAX_LOCALHOST_CONNECTIONS);
        return poolingConnectionManager;
    }

    @Bean
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return (HttpResponse response, HttpContext context) ->
        {
            final HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    return Long.parseLong(value) * 1000;
                }
            }
            return DEFAULT_KEEP_ALIVE_TIME_MILLIS;
        };
    }

    @Bean
    public CloseableHttpClient httpClient() {
        log.info("CloseableHttpClient is configuring...");
        final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).setAuthenticationEnabled(true)
                .setContentCompressionEnabled(true).setStaleConnectionCheckEnabled(true).build();

        return HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(poolingConnectionManager())
                .setKeepAliveStrategy(connectionKeepAliveStrategy()).build();
    }

    @Bean
    public Runnable idleConnectionMonitor(final PoolingHttpClientConnectionManager connectionManager) {
        return new Runnable() {
            @Override
            @Scheduled(fixedDelay = 20000)
            public void run() {
                Thread.currentThread().setName("Connection-Monitor");
                try {
                    if (connectionManager != null) {
                        log.trace("run IdleConnectionMonitor - Closing expired and idle connections...");
                        // Close expired connections
                        connectionManager.closeExpiredConnections();
                        // Optionally, close connections that have been idle longer than 30 sec
                        connectionManager.closeIdleConnections(CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS, TimeUnit.SECONDS);
                    } else {
                        log.trace("run IdleConnectionMonitor - Http Client Connection manager is not initialised");
                    }
                } catch (Exception e) {
                    log.error("run IdleConnectionMonitor - Exception occurred. msg={}, e={}", e.getMessage(), e);
                }
            }
        };
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("IdleConnectionMonitor-PoolScheduler");
        scheduler.setPoolSize(50);
        return scheduler;
    }
}