package com.bridgelabz.fundoonote.exception;

public class FundooNoteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -196446193046294980L;
	public ExceptionType type;

	public enum ExceptionType {
		INVALID_DATA, USER_ALREADY_REGISTERED, LINK_IS_INVALID, INVALID_USER, 
		ACCOUNT_NOT_VALID, INVALID_EMAIL, INTERNAL_SERVER_ERROR, INVALID_PASSWORD,
        Users_Not_Found, LINK_IS_EXPIRED
	}

	public FundooNoteException(ExceptionType type, String message) {
		super(message);
		this.type = type;
	}
}
