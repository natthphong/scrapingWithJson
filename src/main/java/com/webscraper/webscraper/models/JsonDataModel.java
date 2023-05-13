package com.webscraper.webscraper.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JsonDataModel {
    private String date;
    private String symbol;
    private Double prior;
    private  Double open;
    private Double high;
    private  Double low;
    private Double average;
    private  Double close;
    private Double change;
    private  Double percentChange;
    private Double totalVolume;
    private  Double totalValue;
    private Double pe;
    private  Double pbv;
    private Double bookValuePerShare;
    private  Double dividendYield;
    private Long marketCap;
    private  Long listedShare;
    private Double par;
    private  String financialDate;
    private Object nav;
    private  Double marketIndex;
    private  Double marketPercentChange;

}
