package com.local.excelparser.service.Iservices;

import java.io.File;

public interface ParserService {
    public String getFileContent(File file);
    public boolean writeIntoFile(File file, String texte, String col, String row);

    public boolean writeMultipleRows(File file, String texte, String[][] coordinates);
}
