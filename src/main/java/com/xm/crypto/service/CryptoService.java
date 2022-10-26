package com.xm.crypto.service;

import com.xm.crypto.entity.CryptoRecord;
import com.xm.crypto.model.CryptoPropertiesMasterModel;
import com.xm.crypto.model.CryptoServicesMasterModel;

import java.util.List;
import java.util.Map;

public interface CryptoService {

    void importCsvRecords(String dirPath);

    List<CryptoServicesMasterModel> getAllCryptoRecords(String symbol, String startDate, String endDate);

    Map<String, CryptoPropertiesMasterModel> getCryptoRecordsMaxMin(List<CryptoServicesMasterModel> cryptoServicesMasterModelList, Boolean minFlag, Boolean maxFlag, Boolean newFlag, Boolean oldFlag);

    List<CryptoServicesMasterModel> getNormalizedRangeCrypto(List<CryptoServicesMasterModel> cryptoServicesMasterModelList,Boolean highestFlag);

    String save(CryptoServicesMasterModel cryptoServicesMasterModel);
}
