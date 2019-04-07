package caller.service.model;

import java.util.List;

import caller.model.Caller;

public class RegisterCaller {

	Caller caller;
	
	List<CallerServiceDetail> callerServiceDetails;

	public Caller getCaller() {
		return caller;
	}

	public void setCaller(Caller caller) {
		this.caller = caller;
	}

	public List<CallerServiceDetail> getCallerServiceDetails() {
		return callerServiceDetails;
	}

	public void setCallerServiceDetails(List<CallerServiceDetail> callerServiceDetails) {
		this.callerServiceDetails = callerServiceDetails;
	}

}
