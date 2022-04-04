package exceptions;

public class NotEnoughResourcesException extends GameActionException {
	NotEnoughResourcesException() {
		super();
	}
	NotEnoughResourcesException(String s) {
		super(s);
	}
}
