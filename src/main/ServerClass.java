package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import java.util.logging.Logger;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;



public class ServerClass implements Runnable {

	private Socket srvSocket = null ;
	private InetAddress localAddress = null;
	private int serverPort;
	ServerSocket mySkServer;
	PrintWriter pout;
	Scanner sc; 
	int i =0;
	String interfaceName = "eth1";
	SubClient client_distant;
	List<SubClient> SubClientList = new ArrayList<SubClient>();
	String ipAddress;
	InputStream inputStream;
	ObjectInputStream objectinputstream;
	ObjectOutputStream outputStream;
	InputStreamReader isr;
	String choice;
	Thread t;
	
	Logger server;


	public ServerClass(InetAddress ipServer, int serverPort, Socket socket, ArrayList<SubClient> listClients)
	{
		this.localAddress = ipServer;
		this.serverPort = serverPort;
		this.srvSocket = socket;
		this.SubClientList = listClients;
		this.server = Logger.getLogger("ServerLog");
	}
	
	public void connect()
	{
		SubClient in = null;
		
		try {

			NetworkInterface ni = NetworkInterface.getByName(interfaceName);
			Enumeration<InetAddress> inetAddresses =  ni.getInetAddresses();
			while(inetAddresses.hasMoreElements()) {
				InetAddress ia = inetAddresses.nextElement();

				if(!ia.isLinkLocalAddress()) {
					if(!ia.isLoopbackAddress()) {
						System.out.println(ni.getName() + "->IP: " + ia.getHostAddress());
						localAddress = ia;
					}
				}   
			}

			//Warning : the backlog value (2nd parameter is handled by the implementation
			mySkServer = new ServerSocket(45000,10,localAddress);
			System.out.println("Usedd IpAddress :" + mySkServer.getInetAddress());
			System.out.println("Listening to Port :" + mySkServer.getLocalPort());
			
			
			//wait for client connection			
			ipAddress = srvSocket.getRemoteSocketAddress().toString();
			System.out.println(ipAddress + " is connected "+ i++);
		  
			try {
				getObjectFromClient();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendClientList();
			getClientChoice();
			giveClientIP();
					
			//Then die
			System.out.println("Now dying");
			srvSocket.close();
			mySkServer.close();
			pout.close();


		}catch (SocketException e) {

			System.out.println("Connection Timed out");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getObjectFromClient() throws ClassNotFoundException
	{
		try {
			System.out.println("Receiving client's informations");
			inputStream = srvSocket.getInputStream();
			
			objectinputstream = new ObjectInputStream(inputStream);
			SubClient receivedObject = (SubClient)objectinputstream.readObject();
			
			if(receivedObject != null)
			{
				SubClientList.add(receivedObject);
				displayArray(SubClientList);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void displayArray(List<SubClient> subClientList)
	{
		for(int i = 0;i<subClientList.size(); i++)
		{
			System.out.println("The ip address of the file owner is the following : " + subClientList.get(i).getIP());
		}
	}
	
	public void sendClientList()
	{
		try {
			outputStream = new ObjectOutputStream(srvSocket.getOutputStream());
			List SubList = SubClientList;
			
			outputStream.writeObject(SubList);
			outputStream.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void giveClientIP()
	{
		SubClient clientChoosed = (SubClient) SubClientList.get(1);
		clientChoosed.getIP();
		
		try {
			
			pout = new PrintWriter(srvSocket.getOutputStream());
			pout.println(clientChoosed.getIP());
			System.out.println("The ip send to connect to the client is " + clientChoosed.getIP());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getClientChoice()
	{
		
		try {
			BufferedReader buffin = new BufferedReader (new InputStreamReader (srvSocket.getInputStream()));
			choice = buffin.readLine();
			System.out.println("Your choice is" + choice);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	@Override
	public void run() {
		
		connect();
		
	}

}