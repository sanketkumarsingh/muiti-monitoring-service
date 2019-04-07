package service.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import caller.service.model.CallerServiceDetail;
import service.model.Service;

public class ServiceHelper {

	private static List<Service> services;

	static {
		services = new ArrayList<>();
		services.add(new Service("1", "serviceA", "127.0.0.1", 8001));
		services.add(new Service("2", "serviceB", "127.0.0.1", 8002));
		services.add(new Service("3", "serviceC", "127.0.0.1", 8003));
		services.add(new Service("4", "serviceD", "127.0.0.1", 8004));
	}

	public static List<Service> getFixedServices() {
		return services;
	}

	public static Service getServiceById(String serviceId) {
		Optional<Service> serviceOpt = services.stream().filter(s -> s.getId().equals(serviceId)).findFirst();
		if (serviceOpt.isPresent()){
			return serviceOpt.get();
		}
		return null;
	}
}
