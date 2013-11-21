package util;

@SuppressWarnings("serial")
public class NotConnectedException extends Exception {
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Graph is not connected";
	}

}
