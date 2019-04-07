package service.poll;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import caller.service.model.CallerServiceDetail;
import service.model.ServiceStatus;

public class InitiatePoll implements Runnable{

	private CallerServiceDetail callerService;
	
	public InitiatePoll(CallerServiceDetail callerService) {
		super();
		this.callerService = callerService;
	}

	@Override
	public void run() {
		int pollingCount = 0;
		
		System.out.println("Polling:" + (++pollingCount) +". Service:" + callerService.getService().getName()+ 
				" Current Timestamp:" + );
		Map<String, ServiceStatus> serviceStatusMap = PollService.pollServices(callerService);
		System.out.println("For caller:" + callerId + " ,following are the services and their status:");
		Iterator<Entry<String, ServiceStatus>> iterator = serviceStatusMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, ServiceStatus> entry = iterator.next();
			System.out.println(entry.getKey() + " " + entry.getValue().toString());
		}
		try {
			Thread.sleep(pollFreqMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("===========================================");
	}

}
