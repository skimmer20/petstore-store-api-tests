package com.dto.response;

import lombok.Data;

@Data
public class ErrorOrderResponse {

    private Integer code;
    private String type;
    private String message;
}
