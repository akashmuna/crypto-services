package com.xm.crypto.exception;

import com.xm.crypto.exception.handler.CryptoExceptionService;
import com.xm.crypto.exception.model.CryptoException;
import com.xm.crypto.exception.model.ResponseCode;
import com.xm.crypto.exception.model.ResponseDtoWrapper;
import com.xm.crypto.exception.model.ResponseResourceSupport;
import com.xm.crypto.util.CryptoConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
@RestController
@Scope("prototype")
public class CryptoExceptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoExceptionController.class);

    Locale currentLocale = LocaleContextHolder.getLocale();

    @Autowired
    private CryptoExceptionService cryptoExceptionService;

    @ExceptionHandler({PersistenceException.class, SQLException.class})
    public ResponseEntity<ResponseDtoWrapper> persistenceException(Exception ex) {
        LOGGER.error("Exception", ex);
        List<ResponseCode> responseCodes = new ArrayList();
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(CryptoConstants.CPS_ERR_02);
        responseCode.setType(CryptoConstants.ERROR);
        responseCode.setLanguage(this.currentLocale.getISO3Language().toUpperCase());
        responseCode.setDesc("Exception Occurred while Executing Query");
        responseCodes.add(responseCode);

        ResponseDtoWrapper responseDtoWrapper = cryptoExceptionService.build(responseCodes);
        if (responseDtoWrapper == null) {
            responseDtoWrapper = new ResponseDtoWrapper();
        }
        responseDtoWrapper.setData(new ResponseResourceSupport());

        return new ResponseEntity(responseDtoWrapper, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<ResponseDtoWrapper> numberFormatException(Exception ex) {
        LOGGER.error("Exception", ex);
        List<ResponseCode> responseCodes = new ArrayList();
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(CryptoConstants.CPS_ERR_03);
        responseCode.setType(CryptoConstants.ERROR);
        responseCode.setLanguage(this.currentLocale.getISO3Language().toUpperCase());
        responseCodes.add(responseCode);
        ResponseDtoWrapper responseDtoWrapper = cryptoExceptionService.build(responseCodes);
        if (responseDtoWrapper == null) {
            responseDtoWrapper = new ResponseDtoWrapper();
        }

        return new ResponseEntity(responseDtoWrapper, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpServerErrorException.class})
    public ResponseEntity<ResponseDtoWrapper> httpServerErrorException(Exception ex) {
        LOGGER.error("Exception", ex);
        List<ResponseCode> responseCodes = new ArrayList();
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(CryptoConstants.CPS_ERR_04);
        responseCode.setType(CryptoConstants.ERROR);
        responseCode.setDesc("Server Error Occurred during API call");
        responseCode.setLanguage(this.currentLocale.getISO3Language().toUpperCase());
        responseCodes.add(responseCode);
        ResponseDtoWrapper responseDtoWrapper = cryptoExceptionService.build(responseCodes);
        if (responseDtoWrapper == null) {
            responseDtoWrapper = new ResponseDtoWrapper();
        }

        return new ResponseEntity(responseDtoWrapper, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CryptoException.class})
    public ResponseEntity<ResponseDtoWrapper> cryptoException(CryptoException ex) {
        LOGGER.error("Exception", ex);
        List<ResponseCode> responseCodes = new ArrayList();
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(CryptoConstants.CPS_ERR_01);
        responseCode.setType(CryptoConstants.ERROR);
        responseCode.setDesc(ex.getErrorMessage());
        responseCode.setLanguage(this.currentLocale.getISO3Language().toUpperCase());
        responseCodes.add(responseCode);
        ResponseDtoWrapper responseDtoWrapper = this.cryptoExceptionService.build(responseCodes);
        if (responseDtoWrapper == null) {
            responseDtoWrapper = new ResponseDtoWrapper();
        }

        return new ResponseEntity(responseDtoWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        LOGGER.error("MethodArgumentNotValidException", ex);
        return this.cryptoExceptionService.handleException(ex);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseDtoWrapper> exception(Exception ex) {
        LOGGER.error("Exception", ex);
        List<ResponseCode> responseCodes = new ArrayList();
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(CryptoConstants.CPS_ERR_05);
        responseCode.setType(CryptoConstants.ERROR);
        responseCode.setLanguage(this.currentLocale.getISO3Language().toUpperCase());
        responseCodes.add(responseCode);
        ResponseDtoWrapper responseDtoWrapper = this.cryptoExceptionService.build(responseCodes);
        if (responseDtoWrapper == null) {
            responseDtoWrapper = new ResponseDtoWrapper();
        }

        return new ResponseEntity(responseDtoWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
