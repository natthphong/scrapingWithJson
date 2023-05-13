package com.webscraper.webscraper.repository;


import com.webscraper.webscraper.models.TrTransactionModel;

import java.util.List;


public interface TrTransactionNativeRepository  {

    void   insertTransaction(List<TrTransactionModel> trTransactionModels);
}
