package com.df4j.boot.datasource;

import com.df4j.base.exception.DfException;
import com.df4j.base.jdbc.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OracleSequenceIdGenerator implements IdGenerator<Long> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long next(String key) {
        try {
            String sql = "select " + key + ".nextval from dual";
            return jdbcTemplate.queryForObject(sql, Long.class);
        }catch (Exception e){
            throw new DfException("生成序列异常:" + key, e);
        }
    }
}
