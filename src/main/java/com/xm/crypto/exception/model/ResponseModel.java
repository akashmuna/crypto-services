package com.xm.crypto.exception.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponseModel<T> {
    private T data = null;
}
