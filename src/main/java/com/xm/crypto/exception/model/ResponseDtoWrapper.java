package com.xm.crypto.exception.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class ResponseDtoWrapper {
    private ResponseDto messages;
    private ResponseResourceSupport data;
}
