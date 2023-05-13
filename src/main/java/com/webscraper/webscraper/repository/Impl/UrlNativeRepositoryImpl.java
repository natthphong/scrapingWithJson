package com.webscraper.webscraper.repository.Impl;

import com.webscraper.webscraper.repository.UrlNativeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
@Slf4j
public class UrlNativeRepositoryImpl implements UrlNativeRepository {

    private  final JdbcTemplate jdbcTemplate;

    public UrlNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> getAllUrl() {
        String sql = " select symbol from MS_SET ";
        List<String> result = new ArrayList<>();
        jdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                result.add(rs.getString(1).replaceAll("\\s+",""));
            }
        });

        return result;
    }
}
