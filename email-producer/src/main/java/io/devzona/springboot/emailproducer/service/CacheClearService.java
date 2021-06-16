package io.devzona.springboot.emailproducer.service;

public interface CacheClearService {

    void validateToken(String token) throws Exception;

    void resetAllCache(String[] caches);
}
