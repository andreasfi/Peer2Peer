/**
 * Andreas Fischer
 * Peer2Peer
 * 26.04.2016
 * Client.java
 */
package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * @author Andreas
 *
 */
public class Client {
	
	String myName = "Andy Client";
	String myIP = "192.168.1.101";
	String myPath ="";
	List<SubClient> subClientList;
	
	ObjectOutputStream oos;
	
	Socket clientSocket;
	InetAddress serverAddress;
	String serverName;
	PrintWriter pout;
	Socket mySocket;
	BufferedReader buffin;
	SubClient me;
	
	public Client() {
        serverName = "192.168.108.10";
        
        me = new SubClient(myIP, myName, getMyFiles());
        
        subClientList = new ArrayList<SubClient>();
	}
	
	public void connectToServer(){
		try {
			serverAddress = InetAddress.getByName(serverName);
			System.out.println("Get the address of the server : "+ serverAddress);
			//get a connection to the server
			mySocket = new Socket(serverAddress,45000);
			System.out.println("We got the connexion to  "+ serverAddress);
			// LOG ->
			System.out.println("Will read data given by server:\n");	
		} catch (ConnectException e) {
			// TODO: handle exception
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getClientIP(){
		
	}
	public void openStream(){
		try {
			pout = new PrintWriter(mySocket.getOutputStream());
			
			//create an input stream to read data from the server
			buffin = new BufferedReader (new InputStreamReader (mySocket.getInputStream()));			
			//Read a line from the input buffer coming from the server	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
	}
	public List<String> getMyFiles(){
		List<String> fileList = new ArrayList<String>();
		
		return fileList;
	}
	public void sendSubClient(){		
		
		 try {
			oos = new ObjectOutputStream(mySocket.getOutputStream());			
			oos.writeObject(me);
	        oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@SuppressWarnings("unchecked")
	public void getClientFileList(){
		try {
			
			ObjectInputStream inputStream = new ObjectInputStream(mySocket.getInputStream());
			Object object = inputStream.readObject();
			subClientList = (ArrayList<SubClient>) object;
			subClientList.get(1).getIP();
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close(){
		try {
			System.out.println("\nMessage read. Now dying...");
			mySocket.close();
			pout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Client client = new Client();
		
		client.connectToServer();
		
		client.openStream();
		
		client.sendSubClient();
		
		client.getClientFileList();
		
		
		/*
		String message_distant="";
		while(!message_distant.equals("quit")){
			try {
				message_distant = client.buffin.readLine().trim();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println(message_distant);
	        
	        switch (message_distant) {
			case "needIP":
				client.pout.println(message_distant);
				client.pout.flush();
				break;

			default:
				break;
			}
	        
	        client.pout.println(message_distant);
	        client.pout.flush();	
		}
		
		
		client.close();
		*/
		
		String message ="";
		Scanner sc = new Scanner(System.in);			
		message = sc.nextLine();
		
		switch (message) {
		case "(1)Get File List":
			
			break;

		default:
			break;
		}
		
		
	}
}
