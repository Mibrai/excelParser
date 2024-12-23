package com.local.excelparser.service.IMServices;

import com.local.excelparser.service.Iservices.ParserService;

import java.io.File;

public class ParserServiceImpl implements ParserService {
    @Override
    public String getFileContent(File file) {
        return null;
    }

    @Override
    public boolean writeIntoFile(File file, String texte, String col, String row) {
        return false;
    }

    @Override
    public boolean writeMultipleRows(File file, String texte, String[][] coordinates) {
        return false;
    }
}
