package com.xm.crypto.controller;

import com.xm.crypto.exception.model.ResponseDtoWrapper;
import com.xm.crypto.model.CryptoServicesMasterCollection;
import com.xm.crypto.model.CryptoServicesMasterModel;
import com.xm.crypto.service.CryptoService;
import com.xm.crypto.util.CryptoConstants;
import com.xm.crypto.util.CryptoServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class CryptoAggregateController implements CryptoServicesAggregateApi{

    private static final Logger LOGGER  = LoggerFactory.getLogger(CryptoAggregateController.class);

    private CryptoService cryptoService;

    private final String baseUri = "/web/v1/cryptoservices";

    @Autowired
    public CryptoAggregateController(CryptoService cryptoService){
        this.cryptoService = cryptoService;
    }

    @Override
    public ResponseEntity<CryptoServicesMasterCollection> getAllCryptoRecords(String symbol, String startDate, String endDate, Boolean maxFlag, Boolean minFlag, Boolean oldFlag, Boolean newFlag) {
        LOGGER.info("Request received to get the getAllCryptoRecords.... ");
        ResponseEntity<CryptoServicesMasterCollection> responseDetail = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(symbol!=null || (startDate!=null && endDate!=null)) {
             List<CryptoServicesMasterModel> cryptoServicesMasterModelList = cryptoService.getAllCryptoRecords(symbol, startDate, endDate);
             if(cryptoServicesMasterModelList!=null && !cryptoServicesMasterModelList.isEmpty()) {

                 if(minFlag!=null || maxFlag!=null || newFlag!=null || oldFlag!=null)
                 cryptoServicesMasterModelList = CryptoServiceUtils.mapVOToList(cryptoService.getCryptoRecordsMaxMin(cryptoServicesMasterModelList,minFlag,maxFlag,newFlag,oldFlag),true);

                 CryptoServicesMasterCollection cryptoServicesMasterCollection = new CryptoServicesMasterCollection();
                 cryptoServicesMasterCollection.setCryptoServicesMasterModelList(cryptoServicesMasterModelList);

                 responseDetail = ok(cryptoServicesMasterCollection);
             }else{
                 responseDetail = new ResponseEntity<>(HttpStatus.NO_CONTENT);
             }
        }

        return responseDetail;
    }

    @Override
    public ResponseEntity<CryptoServicesMasterCollection> getNormalizedCrypto(String startDate, String endDate,Boolean highestFlag) {
        LOGGER.info("Request received to get the getAllCryptoRecords.... ");
        ResponseEntity<CryptoServicesMasterCollection> responseDetail = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<CryptoServicesMasterModel> cryptoServicesMasterModelList = cryptoService.getAllCryptoRecords(null, startDate, endDate);

        if(cryptoServicesMasterModelList!=null && !cryptoServicesMasterModelList.isEmpty()) {
            cryptoServicesMasterModelList = cryptoService.getNormalizedRangeCrypto(cryptoServicesMasterModelList,highestFlag);

            CryptoServicesMasterCollection cryptoServicesMasterCollection = new CryptoServicesMasterCollection();
            cryptoServicesMasterCollection.setCryptoServicesMasterModelList(cryptoServicesMasterModelList);

            responseDetail = ok(cryptoServicesMasterCollection);

        }else{
            responseDetail = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return responseDetail;
    }

    @Override
    public ResponseEntity<CryptoServicesMasterModel> saveCryptoServices(CryptoServicesMasterModel cryptoServicesMasterModel) {
        ResponseEntity<CryptoServicesMasterModel> responseDetail = null;
        String id = null;
        id = cryptoService.save(cryptoServicesMasterModel);
        if (id != null) {
            // CryptoServicesMasterModel saved successfully
            // Build the success response
            cryptoServicesMasterModel.setId(id);
            responseDetail = new ResponseEntity<>(HttpStatus.CREATED);
            responseDetail = ok(cryptoServicesMasterModel);
        }else{
            cryptoServicesMasterModel.setErrorStatus(CryptoConstants.CPS_ERR_10);
            responseDetail = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDetail = ok(cryptoServicesMasterModel);
        }
        // Return the response
        return responseDetail;
    }
}
