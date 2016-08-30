package eu.erbs.kik.utils;

public class KikException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public KikException(Exception e) {
		super(e);
	}

	public KikException(String text) {
		super(text);
	}

}