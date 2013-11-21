package util;

@SuppressWarnings("serial")
public class NotLegalFlowException extends Exception {
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Network is not a legal flow";
	}
	
	

}
