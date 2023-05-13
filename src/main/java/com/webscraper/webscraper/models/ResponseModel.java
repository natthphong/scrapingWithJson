package com.webscraper.webscraper.models;


import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseModel <T>{
    private Integer status;
    private String message;
    private LocalDate timeStamp;
    private T data;
}
