package com.twino.bank.entity.dto.response;

import com.twino.bank.entity.Loan;

public class ResponseMessage {
    private String message;
    private Object value;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public ResponseMessage(String message, Object value){
        this(message);
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
