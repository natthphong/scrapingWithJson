package com.webscraper.webscraper.controller.rest;


import com.webscraper.webscraper.models.ResponseModel;
import com.webscraper.webscraper.service.WebScraperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/webscraper")
public class WebScraperController {
        private  final WebScraperService webScraperService;

    public WebScraperController(WebScraperService webScraperService) {
        this.webScraperService = webScraperService;
    }

    @GetMapping(path = "/get/data")
    public ResponseModel<Void> getAllData(){
        return webScraperService.getAllData();
    }
}
