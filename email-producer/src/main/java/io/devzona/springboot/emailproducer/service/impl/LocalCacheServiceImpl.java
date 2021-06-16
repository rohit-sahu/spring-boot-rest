package io.devzona.springboot.emailproducer.service.impl;

import io.devzona.springboot.emailproducer.model.Lmt;
import io.devzona.springboot.emailproducer.service.LocalCacheService;
import io.devzona.springboot.emailproducer.service.MasterDataService;
import io.devzona.springboot.emailproducer.utils.CommonUtils;
import io.devzona.springboot.emailproducer.utils.LocalCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class LocalCacheServiceImpl implements LocalCacheService {

    private static final Map<String, String> groupDescriptionKeyMap = new HashMap<>();
    private static final Map<String, String> localityDescKeyMap = new HashMap<>();
    private static final Map<String, String> cndDescriptionKeyMap = new HashMap<>();
    private static final Map<String, Lmt> localityMap = new HashMap<>();
    private static final Map<String, Map<Long, String>> cndGroupMap = new HashMap<>();
    private static final Map<String, String> cndCndKeyMap = new HashMap<>();
    private static final LocalCache<String, String> cndCodeKeyMap = new LocalCache<>(86400000);
    private static final LocalCache<String, String> pptKeyValueMap = new LocalCache<>(86400000);
    private static final Map<String, String> psmNameByKeyMap = new HashMap<>();
    private static final Map<String, String> seoPriceTitle = new HashMap<>();
    private static final Map<String, String> landmarkNameKeyMap = new HashMap<>();
    private static final Map<String, Map> loadMasterDataMap = new HashMap<>();
    private static final Map<String, Map<Long, String>> authorityDescMap = new HashMap<>();
    final String subUrbSeperator = "-";

    @Autowired
    MasterDataService masterDataService;

    public Map<Long, String> getChildGroupMap(String parentId, String group) {
        String key = group;
        if (parentId != null) key += "_" + parentId;
        if (cndGroupMap.containsKey(key)) {
            return cndGroupMap.get(key);
        } else {
            Map<Long, String> resdata = null;
            if (parentId != null) {
                resdata = masterDataService.getGroupDataByGroupNameAndParent(group, parentId);
            } else {
                resdata = masterDataService.getCndGroupByGroupName(group);
            }
            if (resdata != null && !resdata.isEmpty())
                cndGroupMap.put(key, resdata);
            return resdata;
        }
    }

    public Map<Long, String> getGroupMapByGroupType(Long grouptype, String group) {
        String key = group;
        if (grouptype != null) key += "_" + grouptype;
        if (cndGroupMap.containsKey(key)) {
            return cndGroupMap.get(key);
        } else {
            Map<Long, String> resdata = masterDataService.getGroupDataByGroupNameAndGroupType(group, grouptype);
            if (resdata != null && !resdata.isEmpty())
                cndGroupMap.put(key, resdata);
            return resdata;
        }
    }

    public String getDescription(String code) {
        if (code != null && code.indexOf(subUrbSeperator) > 0) {
            StringBuilder cityValBuffer = new StringBuilder();
            cityValBuffer.append(getCndDesc(code.substring(0, code.indexOf(subUrbSeperator))));
            cityValBuffer.append(subUrbSeperator);
            cityValBuffer.append(getCndDesc(code.substring(code.indexOf(subUrbSeperator) + 1)));
            return cityValBuffer.toString();
        }
        return getCndDesc(code);
    }

    private String getCndDesc(String key) {
        if (cndDescriptionKeyMap.containsKey(key)) {
            return cndDescriptionKeyMap.get(key);
        } else {
            String desc = masterDataService.descByCndRfnum(key);
            if (desc != null)
                cndDescriptionKeyMap.put(key, desc);
            return desc;
        }
    }

    @Override
    public String getCndCndFromCnd(String key) {
        if (cndCndKeyMap.containsKey(key)) {
            return cndCndKeyMap.get(key);
        } else {
            if (key != null && CommonUtils.isNumeric(key)) {
                String desc = masterDataService.getCndCndKey(key);
                if (StringUtils.isNotEmpty(desc)) {
                    cndCndKeyMap.put(key, desc);
                    return desc;
                }
            }
        }
        return null;
    }

    @Override
    public void clearCache() {

    }

    public String getKeyBydescAndGroupName(String desc, String group) {
        if (groupDescriptionKeyMap.containsKey(group + "_" + desc.replaceAll(" ", "-"))) {
            return groupDescriptionKeyMap.get(group + "_" + desc.replaceAll(" ", "-"));
        } else {
            String key = masterDataService.cndRfnumBydescAndGroupName(desc, group);
            groupDescriptionKeyMap.put(group + "_" + desc.replaceAll(" ", "-"), key);
            return key;
        }
    }

    @Override
    public String getcndCodeByCndrfnum(String key) {
        if (cndCodeKeyMap.containsKey(key)) {
            return cndCodeKeyMap.get(key);
        } else {
            if (key != null && CommonUtils.isNumeric(key)) {
                String desc = masterDataService.getcndCodeByCndrfnum(key);
                if (StringUtils.isNotEmpty(desc)) {
                    cndCodeKeyMap.put(key, desc);
                    return desc;
                }
            }
        }
        return null;
    }
}