package com.webscraper.webscraper.untils;

import com.webscraper.webscraper.models.TrTransactionModel;
import com.webscraper.webscraper.repository.Impl.TrTransactionNativeRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;



@Slf4j
public class PoolList {

    private final TrTransactionNativeRepositoryImpl trTransactionRepository;
    public List<TrTransactionModel> trTransactionModels;
    public Long startTime;
    public int numOfEnd;
    public int startNum;


    public  PoolList(TrTransactionNativeRepositoryImpl trTransactionRepository, int end) {
        this.trTransactionRepository = trTransactionRepository;
        this.trTransactionModels = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
        this.numOfEnd = end;
        this.startNum = 0;
    }
    public void  addListTrTransactionModel(List<TrTransactionModel> tr){
        this.trTransactionModels.addAll(tr);
        addStartNum();
        if (startNum==numOfEnd){
            trTransactionRepository.insertTransaction(this.trTransactionModels);
            long endTime = System.currentTimeMillis();
            long executionTimeMillis = endTime - startTime;
            double executionTimeSeconds = executionTimeMillis / 1000.0;
            log.info("executionTimeSeconds {}" , executionTimeSeconds);
        }
    }

    public  void  addStartNum(){
        startNum++;
    }


}
