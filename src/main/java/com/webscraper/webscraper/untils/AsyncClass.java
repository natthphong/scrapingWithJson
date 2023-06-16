package com.webscraper.webscraper.untils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webscraper.webscraper.models.JsonDataModel;
import com.webscraper.webscraper.models.TrTransactionModel;
import com.webscraper.webscraper.repository.Impl.TrTransactionNativeRepositoryImpl;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AsyncClass {


    @Value("${cookie}")
    private String cookie;

    @Value("${host}")
    private String host;


    @Async("workExecutor")
    public void getJson(String url, String apiUrl, PoolList poolList) throws InterruptedException {
        List<TrTransactionModel> trTransactionModels = new ArrayList<>();
        try {
            URL urlTest = new URL(apiUrl);
            HttpURLConnection http = (HttpURLConnection) urlTest.openConnection();
            http.setRequestProperty("cookie", cookie);
            http.setRequestProperty("Host", host);
            http.setRequestProperty("referer", url);
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            //JSONException = not found data
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JsonDataModel jsonStr = new ObjectMapper().readValue(jsonArray.get(i).toString(), JsonDataModel.class);
                TrTransactionModel trTransactionModel = new TrTransactionModel();
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(jsonStr.getDate());
                LocalDate localDate = offsetDateTime.toLocalDate();
                trTransactionModel.setSymbol(String.valueOf(jsonStr.getSymbol()));
                trTransactionModel.setTransaction(localDate);
                trTransactionModel.setOpenPrice(jsonStr.getOpen());
                trTransactionModel.setMaxPrice(jsonStr.getHigh());
                trTransactionModel.setMinPrice(jsonStr.getLow());
                trTransactionModel.setClosePrice(jsonStr.getClose());
                trTransactionModel.setChangePrice(jsonStr.getChange());
                trTransactionModel.setChangeRatio(jsonStr.getPercentChange());
                trTransactionModel.setNoOfStock(jsonStr.getMarketIndex());
                trTransactionModel.setVolume(jsonStr.getTotalVolume());
                trTransactionModels.add(trTransactionModel);
            }

            poolList.addListTrTransactionModel(trTransactionModels);
            http.disconnect();
        } catch (Exception ex) {
            poolList.addStartNum();
            //not found
            log.info("not found this link {}", url);
        }

    }


    @Async("workExecutor2")
    public void getJsonWithTwo(String url, String apiUrl, PoolList poolList) throws InterruptedException {
        List<TrTransactionModel> trTransactionModels = new ArrayList<>();
        try {
            URL urlTest = new URL(apiUrl);
            HttpURLConnection http = (HttpURLConnection) urlTest.openConnection();
            http.setRequestProperty("cookie", cookie);
            http.setRequestProperty("Host", host);
            http.setRequestProperty("referer", url);
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            //JSONException = not found data
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JsonDataModel jsonStr = new ObjectMapper().readValue(jsonArray.get(i).toString(), JsonDataModel.class);
                TrTransactionModel trTransactionModel = new TrTransactionModel();
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(jsonStr.getDate());
                LocalDate localDate = offsetDateTime.toLocalDate();
                trTransactionModel.setSymbol(String.valueOf(jsonStr.getSymbol()));
                trTransactionModel.setTransaction(localDate);
                trTransactionModel.setOpenPrice(jsonStr.getOpen());
                trTransactionModel.setMaxPrice(jsonStr.getHigh());
                trTransactionModel.setMinPrice(jsonStr.getLow());
                trTransactionModel.setClosePrice(jsonStr.getClose());
                trTransactionModel.setChangePrice(jsonStr.getChange());
                trTransactionModel.setChangeRatio(jsonStr.getPercentChange());
                trTransactionModel.setNoOfStock(jsonStr.getMarketIndex());
                trTransactionModel.setVolume(jsonStr.getTotalVolume());
                trTransactionModels.add(trTransactionModel);
            }

            poolList.addListTrTransactionModel(trTransactionModels);
            http.disconnect();
        } catch (Exception ex) {
            poolList.addStartNum();
            //not found
            log.info("not found this link {}", url);
        }

    }



}
