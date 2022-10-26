package com.xm.crypto.exception.handler;

import com.xm.crypto.exception.model.ResponseCode;
import com.xm.crypto.exception.model.ResponseDtoWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public interface CryptoExceptionService {

    ResponseDtoWrapper build(List<ResponseCode> responseCodeList);

    ResponseEntity<Object> handleException(MethodArgumentNotValidException ex);
}
