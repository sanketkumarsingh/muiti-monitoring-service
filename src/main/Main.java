package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.Scanner;

import caller.service.model.CallerServiceDetail;
import service.model.Service;
import service.model.ServiceStatus;
import service.poll.InitiatePoll;
import service.poll.PollService;
import service.util.ServiceGenerator;
import service.util.ServiceHelper;

public class Main {
	
	private static final String CHOICE_Y = "Y";

	public static void main(String[] args) {

		// assuming, all services are started separately.

		// register caller for service.
		// Enter caller detail
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter caller id");
		String callerId = sc.next();

		// Enter services for which notification is required.
		System.out.println("Enter service details for which notification is required.");
		System.out.println("Do you want to enter a service detail: Y/N");
		String callerChoice = sc.next();
		List<CallerServiceDetail> serviceCallerInput = new ArrayList<>();
		while (CHOICE_Y.equals(callerChoice)) {
			System.out.println("Enter service id. Supported ids: 1,2,3 or 4.(required):");
			String serviceId = sc.next();
			Service service = ServiceHelper.getServiceById(serviceId);
			if(service == null){
				System.out.println("Service not available. Value can be only 1,2,3 or 4");
				System.out.println("Do you want to enter again service detail: Y/N");
				callerChoice = sc.next();
				continue;
			}
			System.out.println("Enter polling freq. in seconds for the service. enter 0 if to be "
					+ "polled continously. (required).");
			long pollFreq = sc.nextLong() * 1000;
			System.out.println("Enter outage start date in format: (optional)");
			String startDate = sc.next();
			System.out.println("Enter outage end date in format: (optional)");
			String endDate = sc.next();
			CallerServiceDetail callerServiceDetail = new CallerServiceDetail(service, pollFreq,startDate, endDate);
			serviceCallerInput.add(callerServiceDetail);
			System.out.println("Do you want to enter next service detail: Y/N");
			callerChoice = sc.next();
		}
		
		if (serviceCallerInput.size() == 0){
			System.out.println("No valid service registered for notification. exiting");
			return;
		}
		
//		System.out.println("Enter comma separated service ids for which notification is required, for ex: 1,2,3"
//				+ " Currently there are four services supported with ids 1,2,3 and 4.");
//		String serviceIds = sc.next();
//		
//		System.out.println("");
//
//		System.out.println("Enter the polling frequency in seconds (same polling freq. will be applied to each"
//				+ " of the provided services.");
//		long pollingFreqSec = sc.nextLong();
//		long pollFreqMs = pollingFreqSec * 1000;
		
		
		
		// Poll only services as entered by caller.
//		List<Service> services = ServiceHelper.getFixedServices();
//		List<Service> servicesToMonitor = ServiceHelper.getFilterdServices(services, serviceCallerInput);

		System.out.println("Starting to poll.");
		int pollingCount = 0;
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(serviceCallerInput.size());
		for (CallerServiceDetail serviceCaller: serviceCallerInput){
			
			executor.submit(new InitiatePoll(serviceCaller));
			
		}
		executor.shutdown();
		while (true) {
			
			
			
			
		}
	}

}
