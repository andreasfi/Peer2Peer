package GUI;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ClientAccueilGUI extends JFrame {
	
	Client client = new Client();
	JPanel frame1 = new JPanel();
	JPanel frame2 = new JPanel();
	JTextField tfIP = new JTextField(client.serverName);
	JLabel labelMyIP = new JLabel("My address is " + client.myIP + " and my name is " + client.myName);
	JLabel labelIP = new JLabel("Adresse IP du serveur : ");
	JButton connect = new JButton("Connect");
	ConnectButton connectbutton = new ConnectButton();
	
	public ClientAccueilGUI() 
	{
		setSize(500,300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		add(frame1);
		
		tfIP.setBounds(5,5,200,50);
		tfIP.setPreferredSize(new Dimension(200, 24));
		labelIP.setPreferredSize(new Dimension(200, 24));
		frame1.add(labelMyIP);
		frame1.add(labelIP);
		frame1.add(tfIP);
		connect.addActionListener(new ConnectButton());
		frame1.add(connect);
		

	}

}


class ConnectButton implements ActionListener
{
	boolean connection = false;

	public void actionPerformed(ActionEvent e) {
		Client client = new Client();
		client.connectToServer();

	}
}



