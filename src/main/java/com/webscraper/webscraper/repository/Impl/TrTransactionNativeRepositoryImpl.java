package com.webscraper.webscraper.repository.Impl;

import com.webscraper.webscraper.models.TrTransactionModel;
import com.webscraper.webscraper.repository.TrTransactionNativeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


@Repository
@Slf4j
public class TrTransactionNativeRepositoryImpl implements TrTransactionNativeRepository {
    private final JdbcTemplate jdbcTemplate;
    public TrTransactionNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertTransaction(List<TrTransactionModel> trTransactionModels) {
        //log.info("inside insert transaction  {}" ,trTransactionEntity);
        List<Object> parameter = new ArrayList<>();
        String sql = " insert into TR_TRANSACTION(SYMBOL,TRANSACTION_DATE,OPEN_PRICE,MAX_PRICE,MIN_PRICE,CLOSE_PRICE,CHANGE_PRICE,CHANGE_RATIO,NO_OF_STOCK,VOLUME,REASON,STATUS,BATCH_ID )  ";
        StringJoiner stringJoiner = new StringJoiner(",");
        sql += " values ";

        for (TrTransactionModel trTransactionModel : trTransactionModels) {
            String value = " ( ? ,? ,? ,? ,? ,? ,? , ?, ? , ? , ? , ? , ? ) ";
            parameter.add(trTransactionModel.getSymbol());
            parameter.add(trTransactionModel.getTransaction());
            parameter.add(trTransactionModel.getOpenPrice());
            parameter.add(trTransactionModel.getMaxPrice());
            parameter.add(trTransactionModel.getMinPrice());
            parameter.add(trTransactionModel.getClosePrice());
            parameter.add(trTransactionModel.getChangePrice());
            parameter.add(trTransactionModel.getChangeRatio());
            parameter.add(trTransactionModel.getNoOfStock());
            parameter.add(trTransactionModel.getVolume());
            parameter.add(trTransactionModel.getReason());
            parameter.add(trTransactionModel.getStatus());
            parameter.add(trTransactionModel.getBatch_id());
            stringJoiner.add(value);
        }

        sql+=stringJoiner.toString();
        //log.info("sql {}" ,sql);
       try{
           this.jdbcTemplate.update(sql, parameter.toArray());
        }catch (Exception ex){
            log.info("message {}"  ,ex.getMessage());
        }

    }
}
