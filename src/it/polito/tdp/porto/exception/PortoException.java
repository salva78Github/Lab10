package it.polito.tdp.porto.exception;

public class PortoException extends Exception{
	private static final long serialVersionUID = -2452837270560092285L;

	public PortoException(String message, Exception e){
		super(message, e);
	}
	
	public PortoException(String message){
		super(message);
	}
}
