package io.devzona.springboot.emailproducer.service.impl;

import io.devzona.springboot.emailproducer.model.CommonData;
import io.devzona.springboot.emailproducer.model.Lmt;
import io.devzona.springboot.emailproducer.repository.CommonDataRepository;
import io.devzona.springboot.emailproducer.repository.LmtRepository;
import io.devzona.springboot.emailproducer.repository.PropertyRepository;
import io.devzona.springboot.emailproducer.service.MasterDataService;
import io.devzona.springboot.emailproducer.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@EnableCaching
public class MasterDataServiceImpl implements MasterDataService {

    public static final String COUNTRY_GROUP_NAME = "Country";
    @Autowired
    LmtRepository lmtRepository;
    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    private CommonDataRepository commonDataRepository;

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#p0.toString().concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public String getCndCndKey(String key) {
        if (key != null && CommonUtils.isNumeric(key)) {
            try {
                String desc = commonDataRepository.getCndCndKey(Long.valueOf(key));
                if (StringUtils.isNotEmpty(desc)) {
                    return desc;
                }
            } catch (Exception e) {
                log.error("Exception in Master Service" + e);
            }
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#p0.toString().concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public String descByCndRfnum(String key) {
        if (!StringUtils.isEmpty(key) && CommonUtils.isNumeric(key)) {
            return commonDataRepository.findDescByCndRfnum(Long.valueOf(key));
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#p0.toString().concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public String getcndCodeByCndrfnum(String cndrfnum) {
        if (!StringUtils.isEmpty(cndrfnum) && CommonUtils.isNumeric(cndrfnum.trim())) {
            CommonData cnd = findByTpcndDtorfnum(Long.valueOf(cndrfnum.trim()));
            if (cnd != null) {
                return cnd.getCndcode();
            }
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#p0.toString().concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public Map<Long, String> getCndGroupByGroupName(String groupName) {
        List<Object[]> cndList = commonDataRepository.getCndGroupByGroupName(groupName.split(","));
        return convertKeyDescMapOrdered(cndList);
    }

    private Map<Long, String> convertKeyDescMapOrdered(List<Object[]> cndList) {
        Map<Long, String> dataMap = new LinkedHashMap<>();
        for (Object[] result : cndList) {
            dataMap.put(Long.valueOf(result[0].toString()), (String) result[1]);
        }
        return dataMap;
    }

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#p0.toString().replaceAll(' ','-').concat('-').concat(#p1.toString()).concat('-').concat(#root.method.name)")
    public String cndRfnumBydescAndGroupName(String desc, String groupName) {
        String[] group = groupName.split(",");
        if (ObjectUtils.isNotEmpty(group)) {
            try {
                List<Long> cnd = commonDataRepository.findCndRfnumBydescAndGroupName(desc, group);
                return cnd.get(0).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#p0.toString().concat('-').concat(#p1.toString()).concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public Map<Long, String> getGroupDataByGroupNameAndParent(String groupname, String parentcndrfnum) {
        if (StringUtils.isEmpty(parentcndrfnum)) {
            return getCndGroupByGroupName(groupname);
        } else {
            List<Object[]> cndList = commonDataRepository.getGroupDataByGroupNameAndParent(groupname.split(","), Long.parseLong(parentcndrfnum));
            return convertKeyDescMapOrdered(cndList);
        }
    }

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#p0.toString().concat('-').concat(#p1.toString()).concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public Map<Long, String> getGroupDataByGroupNameAndGroupType(String groupname, Long grouptype) {
        List<Object[]> cndList = commonDataRepository.getGroupDataByGroupNameAndGroupType(groupname.split(","), grouptype);
        return convertKeyDescMap(cndList);
    }

    private Map<Long, String> convertKeyDescMap(List<Object[]> cndList) {
        return cndList.stream().collect(Collectors.toMap(a -> (Long) a[0], a -> (String) a[1]));
    }

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#p0.toString().concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public CommonData findByTpcndDtorfnum(Long cndrfnum) {
        return commonDataRepository.findByTpcndDtorfnum(cndrfnum);
    }

    @Override
    @Cacheable(cacheNames = "masterLocCache", key = "#p0.toString().replaceAll(' ','-').concat('-').concat(#p1.toString()).concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public Lmt getTplmtByLmtnameAndCity(String lmtname, Long cndCity) {
        if (!StringUtils.isEmpty(lmtname)) {
            lmtname = lmtname.replaceAll("-", " ").trim();
            List<Lmt> lmt = lmtRepository.getTplmtByLmtnameAndCity(lmtname, cndCity);
            if (lmt == null || lmt.isEmpty()) {
                lmt = lmtRepository.getTplmtByLmtAliasAndCity(lmtname, cndCity);
            }
            if (lmt != null && !lmt.isEmpty()) {
                return lmt.get(0);
            }
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "masterLocCache", key = "#p0.toString().concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public Lmt getTplmtByLmtrfnum(String lmtrfnum) {
        if (!StringUtils.isEmpty(lmtrfnum) && StringUtils.isNumeric(lmtrfnum)) {
            return lmtRepository.findTplmtByLmtrfnum(Long.valueOf(lmtrfnum));
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#p0.toString().concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public String getPpptValueByName(String pptName) {
        List<String> pptValue = propertyRepository.findppptValueByNameRfnum(pptName);
        return pptValue.stream().map(e -> e).collect(Collectors.joining(","));
    }

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#p0.toString().concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public String getPptValueByName(String... pptNameWithComma) {
        List<String> pptValue = propertyRepository.findByPptnameIn(Arrays.asList(pptNameWithComma));
        return pptValue.stream().map(e -> e).collect(Collectors.joining(","));
    }

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#root.method.name")
    public Map<Long, String> getCountryIsdMap() {
        Map<Long, String> countriesCdValMap = new LinkedHashMap<>();
        List<Object[]> result = commonDataRepository.getCountryCodeGroupByGroupName(new String[]{COUNTRY_GROUP_NAME});
        countriesCdValMap = result.stream().collect(Collectors.toMap(a -> (Long) a[0], (a -> a[1] + " +" + a[2])));
        return countriesCdValMap;
    }

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#p0.toString().replaceAll(' ','-').concat('-').concat(#p1.toString()).concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public String keyByCodeValue(String code, String groupName) {
        try {
            return commonDataRepository.findByCodeAndGroup(groupName, code);
        } catch (Exception e) {
            log.error("Exception in Master Service{}", e);
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#p0.toString().replaceAll(' ','-').concat('-').concat(#p1.toString()).concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public String keyByRfNumValue(String code, String groupName) {
        try {
            String response = commonDataRepository.findByRfNumAndGroup(groupName, Long.valueOf(code));
            return response;
        } catch (Exception e) {
            log.error("Exception in Master Service{}", e);
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#p0.toString().replaceAll(' ','-').concat('-').concat(#p1.toString()).concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public Map<Long, String> findByExfield1AndGroup(Long exfield1, String groupName) {
        List<Object[]> cndList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(exfield1) && !StringUtils.isEmpty(groupName)) {
            log.info("findByCndCodeAndGroup controller called for exfield1:" + exfield1 + " and  groupName :" + groupName);
            cndList = commonDataRepository.findByExfield1AndGroup(exfield1, groupName);
        }
        return cndList.stream().collect(Collectors.toMap(a -> (Long) a[0], a -> (String) a[1]));
    }

    @Override
    @Cacheable(cacheNames = "masterDataCache", key = "#p0.toString().concat('-').concat(#root.method.name)", condition = "#p0!=null")
    public Map<String, Long> getCountryCndRFNumByCndDesc(String country) {
        String[] group = {"Country"};
        Map<String, Long> cndRfNumAndRfDesc = new HashMap<>();
        country = country.replaceAll("_", " ");
        log.info("getCountryCndRFNumByCndDesc :" + country + " and group :" + group);
        List<Long> cnd = commonDataRepository.findCndRfnumBydescAndGroupName(country, group);
        if (cnd != null) {
            for (Long long1 : cnd) {
                cndRfNumAndRfDesc.put(country.replaceAll("_", " "), long1);
            }
        } else {
            cndRfNumAndRfDesc.put("India", 50L); //Default India
        }
        return cndRfNumAndRfDesc;
    }
}
