package com.xm.crypto.exception.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto implements Serializable {
    private static final long serialVersionUID = -8555557115768857726L;
    private String keyId;
    private String status;
    private List<ResponseCode> codes = new ArrayList();
    private HttpStatus httpStatusCode;

}
