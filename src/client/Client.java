/**
 * Andreas Fischer
 * Peer2Peer
 * 26.04.2016
 * Client.java
 */
package client;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Andreas
 *
 */
public class Client {
	Socket clientSocket;
	InetAddress serverAddress;
	String serverName;
	PrintWriter pout;
	
	public Client() {

        serverName = "192.168.108.10";
	}
}
