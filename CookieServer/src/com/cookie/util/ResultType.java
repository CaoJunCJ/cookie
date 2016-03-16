package com.cookie.util;

public enum ResultType {
	TIMEOUT(-1), SUCCESS(0), ARGUMENT_INVALID_OR_MISS(1), INTERNAL_ERROR(99);

	public int errorCode;

	private ResultType(int code) {
		this.errorCode = code;
	}

	public String toString() {
		String message = "Unknow Error.";
		
		switch (this) {
		case TIMEOUT:
			message = "Time out.";
			break;
			
		case SUCCESS:
			message = "Success.";
			break;
			
		case INTERNAL_ERROR:
			message = "Internal error.";
			break;
			
		default:
			break;
		}

		return message;
	}
}
