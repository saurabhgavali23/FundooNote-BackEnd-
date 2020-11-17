package com.bridgelabz.fundoonote.exception;

public class FundooUserException extends RuntimeException{

    public int httpStatus;

    public FundooUserException(String s, int httpStatus) {
        super(s);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

}
