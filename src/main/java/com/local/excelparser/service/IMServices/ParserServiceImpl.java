package com.local.excelparser.service.IMServices;

import com.local.excelparser.service.Iservices.ParserService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

        String[] extArray = file.getName().split("\\.");

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook;
            if("xls".equalsIgnoreCase(extArray[extArray.length -1 ]))
                workbook = new HSSFWorkbook(fileInputStream);
            else
                workbook = new XSSFWorkbook(fileInputStream);

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
    public Map<String, Map<String, String>> getFormatedData(Map<Integer, List<String>> data) {
        Map<String, Map<String,String>> formatedData = new HashMap<>();
        List<String> listLand = new ArrayList<>();
        for(Integer key : data.keySet()){
            //key  = 0 is row 0 for =>> Key,EN,DE,FR
            if(key == 0){
                listLand = data.get(key);
                break;
            }
        }

        //find Key ,Value in data and prepare FormatedData
        int index = 0;
        while ( index < listLand.size()){

            if(index != 0){
                //Put Land as Key in Map
                formatedData.put(listLand.get(index),new HashMap<>());
                //iterate Map data to find key and value for given Land
                Map<String,String> listLabels = new HashMap<>();
                for(Integer key : data.keySet()){

                    if(key != 0){
                        List<String> currentListLabels = data.get(key);
                        //fill the map with key, value
                        if(currentListLabels.size() > index)
                            listLabels.put(currentListLabels.get(0),currentListLabels.get(index));
                        else
                            listLabels.put(currentListLabels.get(0),"");
                    }
                }
                formatedData.put(listLand.get(index),listLabels);
            }

            index++;
        }
        return formatedData;
    }

    @Override
    public boolean writeIntoFile(File file, String texte, String col, String row) {
        return false;
    }

    @Override
    public boolean writeMultipleRows(File file, String texte, String[][] coordinates) {
        return false;
    }

    public static final void printMapFormatedData(Map<String,Map<String,String>> formatedData){
        for (String key : formatedData.keySet()){
            System.out.println(key );
            for (String labelKey : formatedData.get(key).keySet()){
                System.out.println(labelKey + " = "+formatedData.get(key).get(labelKey));
            }
        }
    }
}
