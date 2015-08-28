package com.psl.plugin.ui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class LoginView {

	
	JFrame frame = null;
	
	JLabel serverLabel = null;
	JTextField serverText = null;
	
	JLabel userLabel = null;
	JTextField userText = null;
	
	JLabel passwordLabel = null;
	JPasswordField passwordText = null;
	
	JButton loginButton = null;
	JButton cancelButton = null;
	
	boolean isAuthenticate = false;
	
	String username = ""; 
	String passwordStr = null;
	
	String url = "";
	
	
	public LoginView() {
		JFrame frame = new JFrame("Connect GitHub Repository");
		frame.setSize(500, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		placeComponents(panel);
		frame.setLocationRelativeTo(null);// show frame at center of the screen
		frame.setVisible(true);
		loginAction();
		
	}

	private  void placeComponents(JPanel panel) {


		panel.setLayout(null);
		
		serverLabel = new JLabel("Server:*");
		serverLabel.setBounds(10, 10, 80, 25);
		panel.add(serverLabel);
		
		serverText = new JTextField(20);
		serverText.setBounds(100, 10, 350, 20);
		panel.add(serverText);

		userLabel = new JLabel("Use ID:*");
		userLabel.setBounds(10, 40, 80, 25);
		panel.add(userLabel);

		userText = new JTextField(20);
		userText.setBounds(100, 40, 250, 20);
		panel.add(userText);

		 passwordLabel = new JLabel("Password:*");
		passwordLabel.setBounds(10, 70, 80, 25);
		panel.add(passwordLabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 70, 250, 20);
		panel.add(passwordText);

		loginButton = new JButton("Connect");
		loginButton.setBounds(170, 120, 90, 20);
		panel.add(loginButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(270, 120, 90, 20);
		panel.add(cancelButton);
		
		
	}

	public void loginAction(){
		  
		loginButton.addActionListener(new ActionListener(){
		
	      public void actionPerformed(ActionEvent e){	
	    	  if(isAllFieldsEntered()==false){
	    		  JOptionPane.showMessageDialog(frame, "Please enter required details", "Error",
	    			        JOptionPane.ERROR_MESSAGE);
	    	  }else{
	    		  sendUserAuthenticateDetails();
	    	  }
	      }
	  
	    });
	}
	
	private boolean isAllFieldsEntered(){
		
		username = userText.getText().trim(); 
		passwordStr = new String(passwordText.getPassword()).trim();
		url = serverText.getText().trim();
		
		
		if (url.length() == 0 || username.length() == 0
				|| passwordStr.length() == 0) {
			isAuthenticate = false;
		} else {
			isAuthenticate = true;
		}
		return isAuthenticate;
	}
	
	private void sendUserAuthenticateDetails() {

		try {

			String input = "{\"username\":\"Metallica\",\"password\":\"FadeToBlack\",\"url\":\"defaultUrl\"}";
			System.out.println(input);
			
			Client client = Client.create();
			Form form = new Form();
			form.add("username", username);
			form.add("password", passwordStr);
			form.add("url", url);
			WebResource webResource2 = client.resource("http://localhost:8080/EclipsePluggin/validate");
			ClientResponse response2 = webResource2.accept("application/json")
					.post(ClientResponse.class,form);

			if (response2.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response2.getStatus());
			}

			String output2 = response2.getEntity(String.class);
			System.out.println("Git Response :"+output2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
