package io.devzona.springboot.emailproducer.service.impl;

import io.devzona.springboot.emailproducer.exception.UnauthorizedAccessException;
import io.devzona.springboot.emailproducer.service.CacheClearService;
import io.devzona.springboot.emailproducer.service.LocalCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;

@Slf4j
public class CacheClearServiceImpl implements CacheClearService {

    private final Object CLEAR_CACHE_TOKEN = "as$%^-yusd0-@%hsd-ASD67-098lA";
    @Autowired
    LocalCacheService cacheService;
    @Autowired
    private CacheManager cacheManager;

    @Override
    public void validateToken(String token) throws UnauthorizedAccessException {
        if (token == null) {
            throw new UnauthorizedAccessException(HttpStatus.UNAUTHORIZED.toString());
        } else if (token.equals(this.CLEAR_CACHE_TOKEN) == false) {
            throw new UnauthorizedAccessException("invalid token");
        }
    }

    @Override
    public void resetAllCache(String[] caches) {
        if (caches != null) {
            for (String name : caches) {
                log.error(" cache.....{}", name);
                this.cacheManager.getCache(name).clear();
            }
        }
        //this.cacheService.localityClearCache(); // CLeare Locality Cache From Local Server
        //this.cacheService.psmClearCache(); // CLeare PSM Cache From Local Server
    }
}
