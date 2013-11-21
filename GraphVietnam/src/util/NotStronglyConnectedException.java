package util;

@SuppressWarnings("serial")
public class NotStronglyConnectedException extends Exception {
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Graph is not strongly connected";
	}
}
