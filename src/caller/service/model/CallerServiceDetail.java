package caller.service.model;

import service.model.Service;

public class CallerServiceDetail {

	private Service service;

	private long pollingFreq;

	private String outageStartDate;

	private String outageEndData;

	public CallerServiceDetail(Service service, long pollingFreq, String outageStartDate,
			String outageEndData) {
		super();
		this.service = service;
		this.pollingFreq = pollingFreq;
		this.outageStartDate = outageStartDate;
		this.outageEndData = outageEndData;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public long getPollingFreq() {
		return pollingFreq;
	}

	public void setPollingFreq(long pollingFreq) {
		this.pollingFreq = pollingFreq;
	}

	public String getOutageStartDate() {
		return outageStartDate;
	}

	public void setOutageStartDate(String outageStartDate) {
		this.outageStartDate = outageStartDate;
	}

	public String getOutageEndData() {
		return outageEndData;
	}

	public void setOutageEndData(String outageEndData) {
		this.outageEndData = outageEndData;
	}

}
