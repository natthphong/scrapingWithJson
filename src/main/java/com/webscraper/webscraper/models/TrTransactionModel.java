package com.webscraper.webscraper.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
public class TrTransactionModel {


    private String symbol;

    private LocalDate transaction;

    private Double openPrice;

    private Double maxPrice;

    private Double minPrice;

    private Double closePrice;

    private Double changePrice;

    private Double changeRatio;

    private Double noOfStock;

    private String reason;

    private String status;

    private Long batch_id;

    private Double volume;

}
