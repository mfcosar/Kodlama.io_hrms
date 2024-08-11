package kodlamaio.hrms.core.utilities.results;

public class Result { //super type
	
	private boolean success;
	private String message;
	
	public Result(boolean success) {
		this.success = success;
	}

	public Result(boolean success, String message) {
		this(success); // do not repeat yourself
		this.message = message;
	}
	
	public boolean isSuccess() {
		return this.success;
	}
	
	public String getMessage() {
		return this.message;
	}
}
