package com.xm.crypto.util;

import com.xm.crypto.entity.CryptoRecord;
import com.xm.crypto.exception.model.CryptoException;
import com.xm.crypto.model.CryptoPropertiesMasterModel;
import com.xm.crypto.model.CryptoServicesMasterModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CryptoServiceUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(CryptoServiceUtils.class);

    public static List<CryptoServicesMasterModel> convertEntityToVOList(List<CryptoRecord> entityList){
        LOGGER.debug("Inside convertEntityToVOList()....");
        List<CryptoServicesMasterModel> voList = new ArrayList<>();
        if(entityList!=null && !entityList.isEmpty()){
            entityList.forEach(entity -> voList.add(convertEntityToVO(entity)));
        }
        return voList;
    }

    public static CryptoServicesMasterModel convertEntityToVO(CryptoRecord entity) {
        LOGGER.debug("Inside convertEntityToVO()....");
        CryptoServicesMasterModel vo = null;

        if(entity!=null){
            vo = new CryptoServicesMasterModel();
            BeanUtils.copyProperties(entity,vo);
            Optional<Date> updatedDate = Optional.ofNullable(entity.getUpdatedDate());
            if(updatedDate.isPresent())
                vo.setUpdatedDate(updatedDate.get().toString());
            vo.setCryptoProp(new CryptoPropertiesMasterModel());
        }
        return vo;
    }

    public static BigDecimal getMinimumValue(String symbol,List<CryptoServicesMasterModel> cryptoServicesMasterModelList){
        if(symbol!=null){
          return cryptoServicesMasterModelList.stream()
                    .filter(record -> record.getSymbol().equalsIgnoreCase(symbol))
                    .map(CryptoServicesMasterModel::getPrice)
                    .min(Comparator.naturalOrder())
                    .orElse(BigDecimal.ZERO);
        }else{
            return cryptoServicesMasterModelList.stream()
                    .map(CryptoServicesMasterModel::getPrice)
                    .min(Comparator.naturalOrder())
                    .orElse(BigDecimal.ZERO);
        }
    }

    public static BigDecimal getMaximumValue(String symbol,List<CryptoServicesMasterModel> cryptoServicesMasterModelList){
        if(symbol!=null){
            return cryptoServicesMasterModelList.stream()
                    .filter(record -> record.getSymbol().equalsIgnoreCase(symbol))
                    .map(CryptoServicesMasterModel::getPrice)
                    .max(Comparator.naturalOrder())
                    .orElse(BigDecimal.ZERO);
        }else{
            return cryptoServicesMasterModelList.stream()
                    .map(CryptoServicesMasterModel::getPrice)
                    .max(Comparator.naturalOrder())
                    .orElse(BigDecimal.ZERO);
        }
    }

    public static BigDecimal getOldestValue(String symbol, List<CryptoServicesMasterModel> cryptoServicesMasterModelList) {
        if(symbol!=null){
            return cryptoServicesMasterModelList.stream()
                    .filter(record -> record.getSymbol().equalsIgnoreCase(symbol))
                    .map(CryptoServicesMasterModel::getPrice)
                    .findFirst()
                    .orElse(BigDecimal.ZERO);
        }else{
            return cryptoServicesMasterModelList.stream()
                    .map(CryptoServicesMasterModel::getPrice)
                    .findFirst()
                    .orElse(BigDecimal.ZERO);
        }
    }

    public static BigDecimal getNewestValue(String symbol, List<CryptoServicesMasterModel> cryptoServicesMasterModelList) {
        if(symbol!=null){
            Collections.reverse(cryptoServicesMasterModelList);
            return cryptoServicesMasterModelList.stream()
                    .filter(record -> record.getSymbol().equalsIgnoreCase(symbol))
                    .map(CryptoServicesMasterModel::getPrice)
                    .findFirst()
                    .orElse(BigDecimal.ZERO);
        }else{
            Collections.reverse(cryptoServicesMasterModelList);
            return cryptoServicesMasterModelList.stream()
                    .sorted(Comparator.comparing(CryptoServicesMasterModel::getUpdatedDate).reversed())
                    .map(CryptoServicesMasterModel::getPrice)
                    .findFirst()
                    .orElse(BigDecimal.ZERO);
        }
    }

    public static List<CryptoServicesMasterModel> compareNormalizedRange(List<CryptoServicesMasterModel> cryptoServicesMasterModelList, Map<String, CryptoPropertiesMasterModel> cryptoMap) {

        if(cryptoServicesMasterModelList!=null && !cryptoServicesMasterModelList.isEmpty()){
            cryptoServicesMasterModelList.parallelStream().forEach(crypto ->{
                crypto.getCryptoProp().setMin(cryptoMap.get(crypto.getSymbol()).getMin());
                crypto.getCryptoProp().setMax(cryptoMap.get(crypto.getSymbol()).getMax());
            });

            cryptoServicesMasterModelList = getNormalizedRange(cryptoServicesMasterModelList);
            cryptoServicesMasterModelList = sortVOBasedOnCompNormPrice(cryptoServicesMasterModelList);
        }
        return cryptoServicesMasterModelList;
    }

    public static List<CryptoServicesMasterModel> sortVOBasedOnCompNormPrice(List<CryptoServicesMasterModel> cryptoServicesMasterModelList) {
        List<CryptoServicesMasterModel> sortedList = null;
        if(cryptoServicesMasterModelList!=null && !cryptoServicesMasterModelList.isEmpty()) {
            sortedList = cryptoServicesMasterModelList.stream()
                    .sorted(Comparator.comparing(CryptoServicesMasterModel::getCompNormalizedPrice).reversed())
                    .collect(Collectors.toList());
        }
        return sortedList;
    }

    public static List<CryptoServicesMasterModel> getNormalizedRange(List<CryptoServicesMasterModel> distinctRecordList) {

        distinctRecordList.stream().forEach(record -> {
            record.getCryptoProp().setNormRange(record.getCryptoProp().getMax().subtract(record.getCryptoProp().getMin())
                    .divide(record.getCryptoProp().getMin(),2, RoundingMode.HALF_UP));
            record.setCompNormalizedPrice(record.getCryptoProp().getNormRange().multiply(record.getPrice()));
        });

        return distinctRecordList;
    }

    public static Map<String, CryptoPropertiesMasterModel>getNormalizedRange(Map<String,CryptoPropertiesMasterModel> cryptoMap) {

        cryptoMap.forEach((key,value) -> {
            value.setNormRange(value.getMax().subtract(value.getMin())
                    .divide(value.getMin(),2, RoundingMode.HALF_UP));
        });

        return cryptoMap;
    }

    public static List<CryptoServicesMasterModel> mapVOToList(Map<String, CryptoPropertiesMasterModel> cryptoMap,boolean flag) {
        List<CryptoServicesMasterModel> finalList = new ArrayList<>();
        if(cryptoMap!=null && !cryptoMap.isEmpty()){
            cryptoMap.forEach((key,value) ->{
                CryptoServicesMasterModel vo = new CryptoServicesMasterModel();
                vo.setSymbol(key);
                vo.setCryptoProp(value);
                finalList.add(vo);
            });
        }
        return finalList;
    }

    public static CryptoRecord convertVOToEntity(CryptoServicesMasterModel vo) {
        LOGGER.info("Inside convertVOToEntity....");
        CryptoRecord entity = null;
        try {
            if (vo != null) {
                entity = new CryptoRecord();
                BeanUtils.copyProperties(vo, entity);
                if(entity.getId()==null){
                    entity.setId(EntityIdGenerator.getInstance().nextId());
                }
                Optional<String> updatedDate = Optional.ofNullable(vo.getUpdatedDate());
                if (updatedDate.isPresent())
                    entity.setUpdatedDate(new SimpleDateFormat(CryptoConstants.DATE_FOMRAT).parse(updatedDate.get()));
            }
        }catch(ParseException e){
            LOGGER.error("Date Parse Exception Valid Format is ",CryptoConstants.DATE_FOMRAT);
            throw new CryptoException(CryptoConstants.CPS_ERR_01, "Date Format is not Correct as (yyyy-MM-ddHH:mm:ss)", CryptoConstants.ERROR, HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
