package com.bridgelabz.fundoonote.module;

import lombok.Data;

@Data
public class Email {

    private String emailId;
    private String subject;
    private String message;

    public Email(String emailId, String subject, String message) {
        this.emailId = emailId;
        this.subject = subject;
        this.message = message;
    }

    public Email() {
    }

    public String getEmailId() {
        return emailId;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
