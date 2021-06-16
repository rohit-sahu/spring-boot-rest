package io.devzona.springboot.apachesolr.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
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
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RestTemplateFactory {
    private static final int DEFAULT_KEEP_ALIVE_TIME_MILLIS = 20 * 1000;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(50);
    private static final RestTemplateFactory instance = new RestTemplateFactory();
    private static final int timeout = 5 * 1000;
    private static CloseableHttpClient client = null;
    private static RestTemplate restTemplate = null;

    static {
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

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connectionManager.setMaxTotal(400);
        connectionManager.setDefaultMaxPerRoute(200);
        connectionManager.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(timeout).build());
        HttpHost localhost = new HttpHost("http://localhost");
        connectionManager.setMaxPerRoute(new HttpRoute(localhost), 400);
        client = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout)
                        .setSocketTimeout(timeout).setAuthenticationEnabled(true).setContentCompressionEnabled(true)
                        .setStaleConnectionCheckEnabled(true).build())
                .setConnectionManager(connectionManager).setKeepAliveStrategy((response, context) -> {
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
                }).build();
        scheduler.scheduleAtFixedRate(() -> {
            // Close expired connections
            connectionManager.closeExpiredConnections();
            // Optionally, close connections that have been idle longer than 10 sec
            connectionManager.closeIdleConnections(10, TimeUnit.SECONDS);
        }, 10, 10, TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(scheduler::shutdown));
        restTemplate = new RestTemplateBuilder().requestFactory(RestTemplateFactory::clientHttpRequestFactory)
                .errorHandler(new CustomClientErrorHandler()).interceptors(new CustomClientHttpRequestInterceptor()).build();
    }

    private RestTemplateFactory() {
        // Restrict the object creation.
    }

    public static HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(client);
        return clientHttpRequestFactory;
    }

    public static RestTemplateFactory build() {
        return instance;
    }

    public RestTemplate getRestTemplate() {
        if (restTemplate == null)
            return new RestTemplateBuilder().requestFactory(RestTemplateFactory::clientHttpRequestFactory)
                    .errorHandler(new CustomClientErrorHandler()).interceptors(new CustomClientHttpRequestInterceptor()).build();
        else
            return restTemplate;
    }
}
