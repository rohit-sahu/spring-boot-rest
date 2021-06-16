package io.devzona.springboot.emailproducer.service;

import java.util.Map;

public interface LocalCacheService {

    void clearCache();

    String getKeyBydescAndGroupName(String desc, String group);

    String getDescription(String key);

    Map<Long, String> getChildGroupMap(String parentId, String group);

    String getCndCndFromCnd(String string);

    String getcndCodeByCndrfnum(String cndrfnum);

    Map<Long, String> getGroupMapByGroupType(Long grouptype, String group);
}