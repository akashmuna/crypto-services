package com.xm.crypto.exception.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ResponseCode implements Serializable {
    private String Code;
    private String Desc;
    private String Type;
    private String Language;
    private boolean error;

    public ResponseCode(String code) {
        this.Code = code;
    }
}