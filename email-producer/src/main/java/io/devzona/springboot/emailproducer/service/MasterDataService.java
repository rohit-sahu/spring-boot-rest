package io.devzona.springboot.emailproducer.service;

import io.devzona.springboot.emailproducer.model.CommonData;
import io.devzona.springboot.emailproducer.model.Lmt;

import java.util.Map;

public interface MasterDataService {

    String getCndCndKey(String key);

    String descByCndRfnum(String key);

    String getcndCodeByCndrfnum(String cndrfnum);

    Map<Long, String> getCndGroupByGroupName(String string);

    String cndRfnumBydescAndGroupName(String desc, String groupName);

    Map<Long, String> getGroupDataByGroupNameAndParent(String string, String stateId);

    Map<Long, String> getGroupDataByGroupNameAndGroupType(String groupname, Long grouptype);

    CommonData findByTpcndDtorfnum(Long cndrfnum);

    Lmt getTplmtByLmtnameAndCity(String lmtname, Long city);

    Lmt getTplmtByLmtrfnum(String lmtrfnum);

    String getPpptValueByName(String pptName);

    String getPptValueByName(String... pptNameWithComma);

    Map<Long, String> getCountryIsdMap();

    String keyByCodeValue(String code, String groupName);

    String keyByRfNumValue(String code, String groupName);

    Map<Long, String> findByExfield1AndGroup(Long exfield1, String groupName);

    Map<String, Long> getCountryCndRFNumByCndDesc(String country);
}