package main;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;


public class MainServer {

	public static void main(String[] args) {

		InetAddress ipServer;

		ArrayList<SubClient> listClients = new ArrayList<SubClient>();

		Logger server;

		try {


			ipServer = InetAddress.getByName("192.168.108.10");
			ServerSocket socketServer = new ServerSocket(45000, 10, ipServer);
			server = new LogClass().getLog();
			server.info("new Connection");

			while(true){
				Socket socket = socketServer.accept();
				System.out.println("connection request received");
				Thread t = new Thread(new ServerClass(ipServer, 45000, socket, listClients));
				t.start();
			}

		} catch (Exception e) {
			e.printStackTrace();


		}

	}
}
