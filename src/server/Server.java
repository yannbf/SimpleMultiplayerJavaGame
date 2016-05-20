package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.User;

public class Server {

	static ServerSocket serverSocket;
	static User[] users = new User[10];
	
	public static void main(String[] args) throws IOException {
		System.out.println("Connecting..");
		serverSocket = new ServerSocket(7777);
		System.out.println("Server started..");
		while(true){
			Socket socket = serverSocket.accept();
			for(int i = 0 ; i < 10 ; i++){
				if(users[i] == null){
					System.out.println("Connection from: " + socket.getInetAddress());
					DataOutputStream out = new DataOutputStream(socket.getOutputStream());
					DataInputStream in = new DataInputStream(socket.getInputStream());
					users[i] = new User(out,in,users,i);
					Thread thread = new Thread(users[i]);
					thread.start();
					break;
				}
			}
		}
	}
}
