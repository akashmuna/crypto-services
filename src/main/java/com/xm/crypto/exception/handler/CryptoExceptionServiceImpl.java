package com.xm.crypto.exception.handler;

import com.xm.crypto.exception.model.ResponseCode;
import com.xm.crypto.exception.model.ResponseDto;
import com.xm.crypto.exception.model.ResponseDtoWrapper;
import com.xm.crypto.exception.model.ResponseResourceSupport;
import com.xm.crypto.util.CryptoConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CryptoExceptionServiceImpl implements CryptoExceptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoExceptionServiceImpl.class);

    @Override
    public ResponseDtoWrapper build(List<ResponseCode> responseCodeList) {
        LOGGER.trace("Entered build");
        String prevStatus = "SUCCESS";
        List<ResponseCode> responseList = new ArrayList();
        ResponseDtoWrapper responseDtoWrapper = new ResponseDtoWrapper();
        ResponseDto responseDto = new ResponseDto();
        Iterator var7 = responseCodeList.iterator();

        while(var7.hasNext()) {
            ResponseCode responseCode = (ResponseCode)var7.next();

            if (responseCode.getType().equalsIgnoreCase("E")) {
                if (prevStatus != "FAILURE") {
                    responseList.clear();
                }

                responseList.add(responseCode);
                prevStatus = "FAILURE";
            }

            if (responseCode.getType().equalsIgnoreCase("O") && prevStatus != "FAILURE") {
                if (prevStatus != "WARNING") {
                    responseList.clear();
                }

                responseList.add(responseCode);
                prevStatus = "WARNING";
            }

            if (responseCode.getType().equalsIgnoreCase("I") && prevStatus != "FAILURE" && prevStatus != "WARNING") {
                responseList.add(responseCode);
                prevStatus = "SUCCESS";
            }
        }

        responseDto.setCodes(responseList);
        responseDto.setStatus(prevStatus);
        if (prevStatus != null && prevStatus.equalsIgnoreCase("SUCCESS")) {
                responseDto.setHttpStatusCode(HttpStatus.OK);
        } else if (prevStatus != null && prevStatus.equalsIgnoreCase("FAILURE")) {
                responseDto.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (responseList == null || responseList.size() == 0) {
            responseDto.setStatus("SUCCESS");
            responseDto.setHttpStatusCode(HttpStatus.OK);
        }

        responseDtoWrapper.setMessages(responseDto);
        responseDtoWrapper.setData(new ResponseResourceSupport());
        LOGGER.trace("Exiting build");
        return responseDtoWrapper;
    }

    @Override
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {
        List<ResponseCode> responseCodes = new ArrayList();
        new ResponseCode();
        new ResponseDtoWrapper();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Iterator var6 = fieldErrors.iterator();

        while(var6.hasNext()) {
            FieldError fieldError = (FieldError)var6.next();
            String code = null;
            List<Object> errParam = new ArrayList();
            StringBuilder messageBuilder = new StringBuilder();
            String field = fieldError.getField().replaceAll("\\[.[0-9]*\\]", "");

                if (fieldError.getDefaultMessage().startsWith("size must be")) {
                    messageBuilder.append(field).append(" ").append(fieldError.getDefaultMessage());
                    code = CryptoConstants.CPS_ERR_06;
                    errParam.add(field);
                    errParam.add(fieldError.getDefaultMessage().replaceAll("size must be between ", "").replaceAll(" and ", "-"));

                } else if (!fieldError.getDefaultMessage().startsWith("may not be null") && !fieldError.getDefaultMessage().startsWith("must not be null")) {
                    if (fieldError.getDefaultMessage().startsWith("must be greater than")) {
                        messageBuilder.append(field).append(" ").append(fieldError.getDefaultMessage());
                        code = CryptoConstants.CPS_ERR_07;
                        errParam.add(field);
                        errParam.add(fieldError.getDefaultMessage().replaceAll("must be greater than or equal to ", ""));

                    } else if (fieldError.getDefaultMessage().startsWith("must be less than")) {
                        messageBuilder.append(field).append(" ").append(fieldError.getDefaultMessage());
                        code = CryptoConstants.CPS_ERR_08;
                        errParam.add(field);
                        errParam.add(fieldError.getDefaultMessage().replaceAll("must be less than or equal to ", ""));

                    } else {
                        messageBuilder.append(field).append(" ").append(fieldError.getDefaultMessage());
                    }
                } else {
                    messageBuilder.append("Please provide a valid value for ").append(field);
                    code = CryptoConstants.CPS_ERR_09;
                    errParam.add(field);
                }

            ResponseCode responseCode = new ResponseCode();
            responseCode.setType("E");
            responseCode.setCode(code);
            responseCode.setDesc(messageBuilder.toString());
            responseCode.setLanguage(responseCode.getLanguage());
            responseCodes.add(responseCode);
        }

        ResponseDtoWrapper responseDto = this.build(responseCodes);
        return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
    }

    
}
