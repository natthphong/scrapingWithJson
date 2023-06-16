package com.webscraper.webscraper.service;


import com.webscraper.webscraper.models.ResponseModel;
import com.webscraper.webscraper.repository.Impl.TrTransactionNativeRepositoryImpl;
import com.webscraper.webscraper.repository.Impl.UrlNativeRepositoryImpl;

import com.webscraper.webscraper.untils.AsyncClass;
import com.webscraper.webscraper.untils.PoolList;
import lombok.extern.slf4j.Slf4j;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class WebScraperService {


    private final UrlNativeRepositoryImpl urlNativeRepository;
    private final AsyncClass asyncClass;
    private final TrTransactionNativeRepositoryImpl trTransactionNativeRepository;


    public WebScraperService(UrlNativeRepositoryImpl urlNativeRepository, AsyncClass asyncClass, TrTransactionNativeRepositoryImpl trTransactionNativeRepository) {
        this.urlNativeRepository = urlNativeRepository;
        this.asyncClass = asyncClass;
        this.trTransactionNativeRepository = trTransactionNativeRepository;
    }

    public ResponseModel<Void> getAllData() {
        ResponseModel<Void> response = new ResponseModel<>();
        response.setStatus(200);
        response.setTimeStamp(LocalDate.now());
        response.setMessage("ok");
        try {

            List<String> symbols = urlNativeRepository.getAllUrl();
            PoolList poolList = new PoolList(trTransactionNativeRepository, symbols.size());
            for (String symbol : symbols) {
                String urlApi = "https://www.set.or.th/api/set/stock/" + symbol + "/historical-trading?lang=en";
                String url = "https://www.set.or.th/en/market/product/stock/quote/" + symbol + "/historical-trading";
                asyncClass.getJson(url, urlApi, poolList);
            }

//            for (int i = 0; i < symbols.size(); i += 100) {
//                int end = i + 99;
//                if (end >= symbols.size()) {
//                    end = symbols.size();
//                }
//
//                divine(i, end, poolList, symbols);
//            }

        } catch (Exception ex) {
            response.setStatus(500);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    @Async("SymbolTask")
    public void divine(int start, int end, PoolList poolList, List<String> symbols) throws InterruptedException {
        for (; start < end; start++) {
            String symbol = symbols.get(start);
            String urlApi = "https://www.set.or.th/api/set/stock/" + symbol + "/historical-trading?lang=en";
            String url = "https://www.set.or.th/en/market/product/stock/quote/" + symbol + "/historical-trading";
            asyncClass.getJson(url, urlApi, poolList);
        }
    }


}
