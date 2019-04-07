package service.model;

public class Service {

	private String id;
	
	private String name;
	
	private String host;
	
	private int port;

	public Service() {
		super();
	}
	
	public Service(String id, String name, String host, int port) {
		super();
		this.id = id;
		this.name = name;
		this.host = host;
		this.port = port;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
