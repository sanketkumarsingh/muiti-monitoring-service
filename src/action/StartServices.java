package action;

import java.util.List;

import service.model.Service;
import service.starter.RunServices;
import service.util.ServiceGenerator;

public class StartServices {

	public void startServices(List<Service> services) {
		RunServices.startServices(services);
	}
	
	public static void main(String[] args) {
		StartServices test = new StartServices();
		List<Service> services = ServiceGenerator.getFixedServices();
		test.startServices(services);
	}
}
