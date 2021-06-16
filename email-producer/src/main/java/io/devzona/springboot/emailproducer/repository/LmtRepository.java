package io.devzona.springboot.emailproducer.repository;

import io.devzona.springboot.emailproducer.model.Lmt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LmtRepository extends CrudRepository<Lmt, Long> {

    @Query("from Lmt where REPLACE(lmtname, '-', ' ')=:lmtname and lmtcndcity=:cndcity and isActive=true and isDeleted=false")
    List<Lmt> getTplmtByLmtnameAndCity(@Param("lmtname") String lmtname, @Param("cndcity") Long cndcity);

    @Query("from Lmt where REPLACE(lmtalias, '-', ' ')=:lmtname and lmtcndcity=:cndcity and isActive=true and isDeleted=false")
    List<Lmt> getTplmtByLmtAliasAndCity(@Param("lmtname") String lmtname, @Param("cndcity") Long cndcity);

    @Query("from Lmt where lmtrfnum=:lmtrfnum and isActive=true and isDeleted=false")
    Lmt findTplmtByLmtrfnum(@Param("lmtrfnum") Long lmtrfnum);
}
