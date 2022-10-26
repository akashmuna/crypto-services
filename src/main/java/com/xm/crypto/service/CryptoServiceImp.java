package com.xm.crypto.service;

import com.xm.crypto.entity.CryptoRecord;
import com.xm.crypto.exception.model.CryptoException;
import com.xm.crypto.model.CryptoPropertiesMasterModel;
import com.xm.crypto.model.CryptoServicesMasterModel;
import com.xm.crypto.persistence.CryptoRepository;
import com.xm.crypto.util.CSVHelper;
import com.xm.crypto.util.CryptoConstants;
import com.xm.crypto.util.CryptoServiceUtils;
import lombok.AllArgsConstructor;
import one.util.streamex.StreamEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CryptoServiceImp implements CryptoService {

    private static Logger LOGGER = LoggerFactory.getLogger(CryptoServiceImp.class);

    private CryptoRepository cryptoRepository;

    @Override
    public void importCsvRecords(String dirPath) {

        LOGGER.info("START importCsvRecords()...");
        if(dirPath!=null){

           List<String> filePathList = CSVHelper.getAllCsvFiles(dirPath);
           LOGGER.info("Directory Path: "+dirPath);

           filePathList.stream().forEach(filePath -> {
               if(filePath!=null) {
                   List<CryptoRecord> cryptoRecordList = CSVHelper.csvToCryptoRecord(filePath);
                   if (cryptoRecordList != null && !cryptoRecordList.isEmpty()) {
                       LOGGER.info("Saving Records to DB.....");
                       cryptoRecordList = filterRecordsNotInDB(cryptoRecordList);
                       cryptoRepository.saveAll(cryptoRecordList);
                   }
               }
           });
        }
        LOGGER.info("END importCsvRecords().....");
    }

    private List<CryptoRecord> filterRecordsNotInDB(List<CryptoRecord> cryptoRecordList) {
        LOGGER.info("START filterRecordsNotInDB().....");

        cryptoRecordList.removeIf(cryptoRecord ->
                (!cryptoRepository.findBytimeStamp(cryptoRecord.getUpdatedDate()).isEmpty())
        );
        LOGGER.info("END filterRecordsNotInDB().....");
        return cryptoRecordList;
    }

    @Override
    public List<CryptoServicesMasterModel> getAllCryptoRecords(String symbol, String startDate, String endDate) {

        List<CryptoRecord> entityList = new ArrayList<>();
        try {
            if(startDate!=null && endDate!=null){
                Date stDate = new SimpleDateFormat(CryptoConstants.DATE_FOMRAT).parse(startDate + CryptoConstants.START_TIME);
                Date enDate = new SimpleDateFormat(CryptoConstants.DATE_FOMRAT).parse(endDate + CryptoConstants.END_TIME);
                if(symbol!=null){
                    entityList = cryptoRepository.findByUpdatedAndSymbol(stDate,enDate,symbol, Sort.by(Sort.Direction.DESC, CryptoConstants.SORTING_COL));
                }else{
                    entityList = cryptoRepository.findByUpdatedDate(stDate,enDate,Sort.by(Sort.Direction.DESC, CryptoConstants.SORTING_COL));
                }
            }else if(symbol!=null){
                entityList =  cryptoRepository.findBySymbol(symbol,Sort.by(Sort.Direction.DESC, CryptoConstants.SORTING_COL));
            }else{
                entityList = (List<CryptoRecord>) cryptoRepository.findAll(Sort.by(Sort.Direction.DESC, CryptoConstants.SORTING_COL));
            }

        }catch(ParseException e){
            LOGGER.error("Date Parse Exception Valid Format is ",CryptoConstants.DATE_FOMRAT);
            throw new CryptoException(CryptoConstants.CPS_ERR_01, "Date Format is not Correct as (yyyy-MM-ddHH:mm:ss)", CryptoConstants.ERROR, HttpStatus.BAD_REQUEST);
        }

        return CryptoServiceUtils.convertEntityToVOList(entityList);
    }

    @Override
    public Map<String, CryptoPropertiesMasterModel> getCryptoRecordsMaxMin(List<CryptoServicesMasterModel> cryptoServicesMasterModelList, Boolean minFlag, Boolean maxFlag, Boolean newFlag, Boolean oldFlag) {

        Map<String, CryptoPropertiesMasterModel> cryptoPropMap = new HashMap<>();

        if(cryptoServicesMasterModelList!=null && !cryptoServicesMasterModelList.isEmpty()) {

            StreamEx.of(cryptoServicesMasterModelList)
                            .distinct(CryptoServicesMasterModel::getSymbol)
                            .forEach(record-> {
                                cryptoPropMap.put(record.getSymbol(),new CryptoPropertiesMasterModel());
                            });
        }
        if(minFlag!=null && minFlag && !cryptoPropMap.isEmpty()) {

            cryptoPropMap.forEach((key,value) ->{
                value.setMin(CryptoServiceUtils.getMinimumValue(key,cryptoServicesMasterModelList));
            });
        }

        if(maxFlag!=null && maxFlag && !cryptoPropMap.isEmpty()) {

            cryptoPropMap.forEach((key,value) ->{
                value.setMax(CryptoServiceUtils.getMaximumValue(key,cryptoServicesMasterModelList));
            });
        }

        if(oldFlag!=null && oldFlag && !cryptoPropMap.isEmpty()){

            cryptoPropMap.forEach((key,value) ->{
                value.setOldest(CryptoServiceUtils.getOldestValue(key,cryptoServicesMasterModelList));
            });
        }

        if(newFlag!=null && newFlag && !cryptoPropMap.isEmpty()){

            cryptoPropMap.forEach((key,value) ->{
                value.setNewest(CryptoServiceUtils.getNewestValue(key,cryptoServicesMasterModelList));
            });
        }

        return cryptoPropMap;
    }

    @Override
    public List<CryptoServicesMasterModel> getNormalizedRangeCrypto(List<CryptoServicesMasterModel> cryptoServicesMasterModelList,Boolean highestFlag) {
        LOGGER.info("inside getNormalizedRangeCrypto ..");
        Map<String, CryptoPropertiesMasterModel> cryptoPropMap  = getCryptoRecordsMaxMin(cryptoServicesMasterModelList,true,true,false,false);

        if(cryptoPropMap!=null && !cryptoPropMap.isEmpty()){
            cryptoPropMap = CryptoServiceUtils.getNormalizedRange(cryptoPropMap);
            cryptoServicesMasterModelList = CryptoServiceUtils.compareNormalizedRange(cryptoServicesMasterModelList,cryptoPropMap);

            if(highestFlag!=null && highestFlag){
                cryptoServicesMasterModelList.subList(1,cryptoServicesMasterModelList.size()).clear();
            }
        }

        return cryptoServicesMasterModelList;
    }

    @Override
    @Transactional()
    public String save(CryptoServicesMasterModel cryptoServicesMasterModel) {
        LOGGER.info("inside save() ..");
        CryptoRecord cryptoRecord = CryptoServiceUtils.convertVOToEntity(cryptoServicesMasterModel);

        if(cryptoRecord!=null){

           List<CryptoRecord> cryptoRecordList =
                   cryptoRepository.findBytimeStampAndDate(cryptoRecord.getUpdatedDate(),cryptoRecord.getSymbol());

           // In Case of Updating the Price of the Crypto at the Given Time
           if(cryptoRecordList!=null && !cryptoRecordList.isEmpty()){
               cryptoRecordList.get(0).setPrice(cryptoRecord.getPrice());
               cryptoRecord.setId(cryptoRecordList.get(0).getId());
               cryptoRepository.save(cryptoRecordList.get(0));
           }else{
               cryptoRepository.save(cryptoRecord);
           }
        }
        return cryptoRecord.getId();
    }
}
