package org.pukho;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Pukho on 05.03.2017.
 */
@Repository
public class Dao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public <T> void save(String sql, T entity) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(entity);
        jdbcTemplate.update(sql, namedParameters);
    }

    public <T> List<T> getAll(String sql, RowMapper<T> rowMapper) {
        return jdbcTemplate.query(sql, rowMapper);
    }

    public <T> T get(String sql,  Map<String, Object> params, RowMapper<T> rowMapper) {

        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(params),
                rowMapper);
    }

    public void remove(String sql,  Map<String, Object> params) {

        jdbcTemplate.update(sql, new MapSqlParameterSource(params));
    }

    public <T> void update(String sql, T entity) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(entity);
        jdbcTemplate.update(sql, namedParameters);
    }
}
