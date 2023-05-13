package com.webscraper.webscraper.service;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.webscraper.webscraper.models.ResponseModel;
import com.webscraper.webscraper.models.TrTransactionModel;
import com.webscraper.webscraper.models.JsonDataModel;
import com.webscraper.webscraper.repository.Impl.TrTransactionNativeRepositoryImpl;
import com.webscraper.webscraper.repository.Impl.UrlNativeRepositoryImpl;

import lombok.extern.slf4j.Slf4j;


import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WebScraperService {


    @Value ("${cookie}")
    private  String cookie;

    @Value("${host}")
    private String host;

    private final UrlNativeRepositoryImpl urlNativeRepository;
    private final TrTransactionNativeRepositoryImpl trTransactionRepository;

    public WebScraperService(UrlNativeRepositoryImpl urlNativeRepository, TrTransactionNativeRepositoryImpl trTransactionRepository) {
        this.urlNativeRepository = urlNativeRepository;

        this.trTransactionRepository = trTransactionRepository;
    }

    public ResponseModel<Void> getAllData() {
        ResponseModel<Void> response = new ResponseModel<>();
        response.setStatus(200);
        response.setTimeStamp(LocalDate.now());
        response.setMessage("ok");
        try {
            List<String> symbols = urlNativeRepository.getAllUrl();
            //log.info("symbols {}" , symbols);
            for (String symbol : symbols){
                String urlApi =  "https://www.set.or.th/api/set/stock/"+symbol+"/historical-trading?lang=en";
                String url = "https://www.set.or.th/en/market/product/stock/quote/"+symbol+"/historical-trading";
                List<TrTransactionModel>  trTransactionModels = getJson(url,urlApi);
                 if (trTransactionModels != null && !trTransactionModels.isEmpty()){
                      trTransactionRepository.insertTransaction(trTransactionModels);
                 }
            }

        } catch (Exception ex) {
            response.setStatus(500);
            response.setMessage(ex.getMessage());
        }
        return response;
    }


    public List<TrTransactionModel> getJson(String url , String apiUrl){
        try {
            URL urlTest = new URL(apiUrl);
            HttpURLConnection http = (HttpURLConnection)urlTest.openConnection();
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
            JSONArray jsonArray = getJsonArrayFromString(response.toString());

            List<TrTransactionModel> trTransactionModels = new ArrayList<>();
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
            http.disconnect();
            return trTransactionModels;
        }catch (Exception ex){
            //not found
                log.info("not found this link {}" , url);
        }
        return null;

    }


    public  JSONArray getJsonArrayFromString(String data) throws JSONException {
        JSONArray jsonArray = new JSONArray(data);
        return jsonArray;
    }




}
