package service.poll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import caller.service.model.CallerServiceDetail;
import service.model.ServiceStatus;
import service.thread.PollServiceRunnable;

public class PollService {

	public static Map<String, ServiceStatus> pollServices(List<CallerServiceDetail> callerServiceInputs) {
		List<PollServiceRunnable> serviceThreads = new ArrayList<>();
		
		for (CallerServiceDetail callerService : callerServiceInputs) {
			if(isServiceInOutage(callerService.getOutageStartDate(), callerService.getOutageEndData())) {
				continue;
			}
			serviceThreads.add(new PollServiceRunnable(callerService.getService()));
		}
		return monitorServices(serviceThreads);
	}

	public static Map<String, ServiceStatus> monitorServices(List<PollServiceRunnable> services) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(services.size());
		Map<String, ServiceStatus> serviceStatus = new HashMap<>();
		for (PollServiceRunnable serviceRunnable : services) {
			ServiceStatus status;
			try {
				status = executor.submit(serviceRunnable).get();
				serviceStatus.put(serviceRunnable.getService().getId(), status);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		executor.shutdown();
		return serviceStatus;
	}
	
	private static boolean isServiceInOutage(String startDate, String endDate) {
		
		return false;
	}

}
