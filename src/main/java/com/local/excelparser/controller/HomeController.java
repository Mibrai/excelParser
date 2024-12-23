package com.local.excelparser.controller;

import com.local.excelparser.service.Iservices.ParserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class HomeController {

    private final ParserService parserService;

    private static final String FILE_PATH = "/static/inputData/input.xlsx";

    public HomeController(final ParserService parserService) {
        this.parserService = parserService;
    }

    @RequestMapping(value = "excelParser", method = RequestMethod.GET)
    public String loadHome() throws IOException {

        System.out.println(parserService.getFileContent(parserService.getFile(FILE_PATH)));
        return "Home";
    }
}
