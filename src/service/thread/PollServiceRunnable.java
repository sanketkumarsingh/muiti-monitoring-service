package service.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

import service.model.Service;
import service.model.ServiceStatus;

public class PollServiceRunnable implements Callable<ServiceStatus> {

	private Service service;

	public PollServiceRunnable(Service service) {
		this.service = service;
	}

	public Service getService() {
		return service;
	}

	@Override
	public ServiceStatus call() throws Exception {
//		System.out.println("Called call:" + Thread.currentThread().getName());
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
//		System.out.println("start of call.." + Thread.currentThread().getName());
		try {
			socket = new Socket(service.getHost(), service.getPort());
			String input = "Is " + service.getName() + " Up?";
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println(input);
			out.flush();
//			System.out.println("Send the message." + Thread.currentThread().getName());
			Thread.sleep(20);
			String responseY = input + " Ans: Yes";
			String data = null;
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while ((data = in.readLine()) != null) {
				if (data.equals(responseY)) {
					System.out.println(responseY);
					System.out.println("Received status from service:" + Thread.currentThread().getName());
					return ServiceStatus.UP;
				}
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				in.close();
			}
			if (socket != null) {
				socket.close();
			}
			if(out != null){
				out.close();
			}
		}
		return ServiceStatus.DOWN;
	}
}
