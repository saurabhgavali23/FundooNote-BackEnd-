package com.bridgelabz.fundoonote.exception;

public class FundooNoteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -196446193046294980L;

	public int httpStatus;

	public FundooNoteException(String s, int httpStatus) {
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
