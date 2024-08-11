package kodlamaio.hrms.core.utilities.results;

public class ErrorResult extends Result{

	public ErrorResult() {
		super(false);
	}

	public ErrorResult(boolean success, String message) {
		super(false, message);
	}
	
	public ErrorResult(String message) {
		super(false, message);
	}
}
