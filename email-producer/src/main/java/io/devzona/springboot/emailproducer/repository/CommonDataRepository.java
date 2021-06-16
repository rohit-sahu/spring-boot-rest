package io.devzona.springboot.emailproducer.repository;

import io.devzona.springboot.emailproducer.model.CommonData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonDataRepository extends JpaRepository<CommonData, Long> {

    @Query("SELECT cndcndrfnum FROM CommonData WHERE id = :cndrfnum")
    String getCndCndKey(@Param("cndrfnum") Long cndrfnum);

    @Query("SELECT cnddesc FROM CommonData WHERE id = :cndrfnum")
    String findDescByCndRfnum(@Param("cndrfnum") Long cndrfnum);

    @Query("SELECT id FROM CommonData WHERE cnddesc = :cnddesc and cndgroup in :cndgroup and isActive=true and isDeleted=false")
    List<Long> findCndRfnumBydescAndGroupName(@Param("cnddesc") String cnddesc, @Param("cndgroup") String[] cndgroup);

    @Query("SELECT id FROM CommonData WHERE exfield2 like %:cnddesc% and cndgroup in :cndgroup and isActive=true and isDeleted=false")
    List<Long> findCndRfnumByaliasAndGroupName(@Param("cnddesc") String cnddesc, @Param("cndgroup") String[] cndgroup);

    @Query("from CommonData WHERE id = :cndrfnum")
    CommonData findByTpcndDtorfnum(@Param("cndrfnum") Long cndrfnum);

    @Query("SELECT id,cnddesc FROM CommonData WHERE cndgroup in :cndgroup and isActive=true and isDeleted=false order by cndcndrfnum asc , cndseqnum asc")
    List<Object[]> getCndGroupByGroupName(@Param("cndgroup") String[] cndgroup);

    @Query("SELECT id,cndcode,exfield1 FROM CommonData WHERE cndgroup in :cndgroup and isActive=true and isDeleted=false order by cndcndrfnum asc , cndseqnum asc")
    List<Object[]> getCountryCodeGroupByGroupName(@Param("cndgroup") String[] cndgroup);

    @Query("SELECT id FROM CommonData WHERE cndgroup ='Suburbs' and cnddesc = :desc and cndcndrfnum =:cityId and isActive=true and isDeleted=false")
    String getSubUrbRefnumByCity(@Param("desc") String desc, @Param("cityId") Long cityId);

    @Query("SELECT id,cnddesc FROM CommonData WHERE cndgroup in :cndgroup and cndcndrfnum=:parentcndrfnum and isActive=true and isDeleted=false order by cndcndrfnum asc , cndseqnum asc")
    List<Object[]> getGroupDataByGroupNameAndParent(@Param("cndgroup") String[] cndgroup, @Param("parentcndrfnum") Long parentcndrfnum);

    @Query("SELECT id FROM CommonData WHERE cndgroup =:groupName and cndcode=:code and isActive=true and isDeleted=false")
    String findByCodeAndGroup(@Param("groupName") String groupName, @Param("code") String code);

    @Query("SELECT id,cnddesc FROM CommonData WHERE cndgroup in :cndgroup and exfield1=:grouptype and isActive=true and isDeleted=false order by cndcndrfnum asc,cndseqnum asc")
    List<Object[]> getGroupDataByGroupNameAndGroupType(@Param("cndgroup") String[] cndgroup, @Param("grouptype") Long grouptype);

    @Query("SELECT id FROM CommonData WHERE cndgroup =:groupName and id=:code and isActive=true and isDeleted=false")
    String findByRfNumAndGroup(@Param("groupName") String groupName, @Param("code") Long code);

    @Query(value = "select cnd from CommonData cnd where cndgroup =:groupName and isActive=true and isDeleted=false order by cndseqnum desc")
    List<CommonData> findByCndGroup(@Param("groupName") String groupName);

    @Query(value = "select cnd from CommonData cnd where cndgroup =:groupName and isActive=true and isDeleted=false order by exfield1 desc")
    List<CommonData> findByCndGroupOrderByExfield1(@Param("groupName") String groupName);

    @Query("SELECT id,cndcode FROM CommonData WHERE cndgroup =:groupName and exfield1=:code and isActive=true and isDeleted=false")
    List<Object[]> findByExfield1AndGroup(@Param("code") Long code, @Param("groupName") String groupName);
}
