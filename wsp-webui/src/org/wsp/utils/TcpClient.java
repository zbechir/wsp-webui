package org.wsp.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.wsp.managedBeans.LoginManagedBeans;

public class TcpClient {

	private static String ServerAddr = "localhost";
	private static int port = 2503;
	private static final Logger log = Logger.getLogger(TcpClient.class);

	public static Object request(String query) {
		Object Res ="";
		try {
			BufferedReader inFromUser = new BufferedReader(
					new InputStreamReader(System.in));
			Socket clientSocket = new Socket(ServerAddr, port);
			DataOutputStream outToServer = new DataOutputStream(
					clientSocket.getOutputStream());
			ObjectInputStream inFromServer =
	                   new ObjectInputStream(clientSocket.getInputStream());
			log.info("send query to the server "+query);
			outToServer.writeBytes(query + '\n');
			Res= inFromServer.readObject();
			log.info("FROM SERVER: " + Res);
			clientSocket.close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		}
		return Res;
	}

}
