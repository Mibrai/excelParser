package com.local.excelparser.service.Iservices;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ParserService {
    public File getFile(String filePath) throws IOException;
    public Map<Integer, List<String>> getFileContent(File file);
    public boolean writeIntoFile(File file, String texte, String col, String row);

    public boolean writeMultipleRows(File file, String texte, String[][] coordinates);
}
