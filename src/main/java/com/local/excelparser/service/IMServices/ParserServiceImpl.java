package com.local.excelparser.service.IMServices;

import com.local.excelparser.service.Iservices.ParserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParserServiceImpl implements ParserService {
    @Override
    public File getFile(String filePath) throws IOException {

        Resource resource = new ClassPathResource(filePath);
        if(Files.exists(Path.of(resource.getFile().toURI())))
            return resource.getFile();
        return null;
    }

    @Override
    public Map<Integer, List<String>> getFileContent(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            //read file
            Sheet sheet = workbook.getSheetAt(0);
            Map<Integer, List<String>> data = new HashMap<>();
            int i = 0;

            for(Row row : sheet){
                data.put(i,new ArrayList<>());
                for (Cell cell : row){
                    switch (cell.getCellType()){
                        case STRING:
                            data.get(i).add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            data.get(i).add(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case BOOLEAN:
                            data.get(i).add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        case FORMULA:
                            data.get(i).add(cell.getCellFormula());
                            break;
                        default: data.get(i).add(" ");
                    }
                }
                i++;
            }

            return data;
        }catch(FileNotFoundException fnf){
            //
            System.out.println("File not found at : "+file.getName());
        } catch (IOException e) {
            //
            System.out.println("can't open file "+file.getAbsolutePath());
            throw new RuntimeException(e);
        }
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
