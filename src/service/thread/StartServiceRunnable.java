package service.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import service.model.Service;

public class StartServiceRunnable implements Runnable {

	private Service service;

	public StartServiceRunnable(Service service) {
		this.service = service;
	}

	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(service.getPort(), 1, InetAddress.getByName(service.getHost()));
			System.out.println("service: " + service.getName() + " is Up and listening..");
			while (true) {
				listen(server);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void listen(ServerSocket server) throws IOException {
		Socket client = server.accept();
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String data = null;
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		data = in.readLine();
		if (data != null) {
			System.out.println("Recieving message from " + client.getInetAddress().getHostAddress() + ": " + data);
			out.println(data + " Ans: Yes");
			out.flush();
		}
	}

}
