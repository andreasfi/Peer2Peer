import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Test {

	public static void main(String[] args) {
		
		Socket clientSocket;
		InetAddress serverAddress;
        String serverName = "192.168.108.10";

		PrintWriter pout;
	        
			try {
				serverAddress = InetAddress.getByName(serverName);
				System.out.println("Get the address of the server : "+ serverAddress);
			    
				//get a connection to the server
				Socket mySocket = new Socket(serverAddress,45000);
				System.out.println("We got the connexion to  "+ serverAddress);
				System.out.println("Will read data given by server:\n");
				

				pout = new PrintWriter(mySocket.getOutputStream());

				//create an input stream to read data from the server
				BufferedReader buffin = new BufferedReader (new InputStreamReader (mySocket.getInputStream()));
				
				//Read a line from the input buffer coming from the server
				String message_distant="";
				while(!message_distant.equals("quit")){
					message_distant = buffin.readLine().trim();
			        
			        System.out.println(message_distant);
			        
			        pout.println(message_distant);
					pout.flush();	
				}
		        
				
				//close the connection
		        System.out.println("\nMessage read. Now dying...");
		        mySocket.close();
				pout.close();

			}catch (UnknownHostException e) {
				
				e.printStackTrace();
			}catch (ConnectException e) {
				
				 System.out.println("\n cannot connect to server");;
			}catch (IOException e) {
				
				e.printStackTrace();
			}

	}

}
