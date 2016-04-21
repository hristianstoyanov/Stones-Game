
public class InvalidNumberException extends Exception {
	@Override
	public String getMessage() {
		return "Enter number between 3 and 10";
	}
}
