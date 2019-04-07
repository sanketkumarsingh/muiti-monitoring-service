package action;
import java.util.List;

import service.model.Service;
import service.poll.PollService;

public class MonitorServices {

	public void pollServices(List<Service> services) {
		PollService.pollServices(services);
	}
}
