package com.vodafone.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorDetails {
    private String code;
    private String message;
    private String url;
}
