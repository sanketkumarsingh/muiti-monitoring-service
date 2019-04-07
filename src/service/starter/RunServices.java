package service.starter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import service.model.Service;
import service.thread.StartServiceRunnable;


public class RunServices {

	public static void startServices(List<Service> services) {
		List<StartServiceRunnable> serviceThreads = new ArrayList<>();
		for(Service service: services) {
			serviceThreads.add(new StartServiceRunnable(service));
		}
		runServices(serviceThreads);
	}
	
	public static void runServices(List<StartServiceRunnable> services) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		for(StartServiceRunnable service: services) {
			executor.submit(service);
		}
		
		try {
			executor.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
