package com.xm.crypto.util;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.xm.crypto.entity.CryptoRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSVHelper {

    private static Logger LOGGER = LoggerFactory.getLogger(CSVHelper.class);

    /**
     * The Method csvToCryptoRecord is conversion Method which iterates the CSV file
     * @param filePath CsvFilePath Location
     * @return List of CryptoRecord
     */
    public static List<CryptoRecord> csvToCryptoRecord(String filePath) {

        LOGGER.info("START csvToTutorials()....");

        List<CryptoRecord> csvRecordList = new ArrayList<>();
        LOGGER.info("CSV Path...."+ filePath);
        try ( Reader reader = Files.newBufferedReader(Paths.get(filePath));
              CSVParser csvParser = new CSVParser(reader,CSVFormat.DEFAULT
                      .withFirstRecordAsHeader()
                      .withHeader(CryptoConstants.HEADERS)
                      .withIgnoreHeaderCase()
                      .withTrim());
             ) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                CryptoRecord cryptoRecord = new CryptoRecord();
                cryptoRecord.setId(EntityIdGenerator.getInstance().nextId());
                Optional<String> optionalTimeStamp = Optional.ofNullable(csvRecord.get(CryptoConstants.HEADERS[0]));
                if(optionalTimeStamp.isPresent())
                    cryptoRecord.setUpdatedDate(new Date(Long.valueOf(optionalTimeStamp.get())));

                Optional<String> optionalSymbol = Optional.ofNullable(csvRecord.get(CryptoConstants.HEADERS[1]));
                if(optionalSymbol.isPresent())
                    cryptoRecord.setSymbol(optionalSymbol.get());

                Optional<String> optionalPrice = Optional.ofNullable(csvRecord.get(CryptoConstants.HEADERS[2]));
                if(optionalSymbol.isPresent())
                    cryptoRecord.setPrice(new BigDecimal(optionalPrice.get()));

                csvRecordList.add(cryptoRecord);
            }

        } catch (IOException e) {
            LOGGER.error("Exception while parsing  the CSV with an Error Message : ",e.getMessage());
        }
        LOGGER.info("END csvToTutorials().....");
        return csvRecordList;
    }

    /**
     * Recursive Method to List All the CSV Files
     * @param dirPath
     * @return
     */
    public static List<String> getAllCsvFiles(String dirPath){
        LOGGER.info("START getAllCsvFiles().....");

        List<String> fileList = new ArrayList<>();
        File[] filesInDirectory = new File(dirPath).listFiles();
        for(File f : filesInDirectory){
            if(f.isDirectory()){
                getAllCsvFiles(f.getAbsolutePath());
            }
            String filePath = f.getAbsolutePath();
            String fileExtenstion = filePath.substring(filePath.lastIndexOf(".") + 1,filePath.length());
            if("csv".equals(fileExtenstion)){
                LOGGER.debug("CSV file found -> " + filePath);
                fileList.add(filePath);
            }
        }

        LOGGER.info("END getAllCsvFiles().....");
        return fileList;
    }

}
