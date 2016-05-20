package model;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class User implements Runnable{

	DataOutputStream out;
	DataInputStream in;
	User[] user = new User[10];
	int playerId;
	int playerIdInput;
	int xInput;
	int yInput;
	
	public User(DataOutputStream out, DataInputStream in, User[] user, int pid) {
		super();
		this.out = out;
		this.in = in;
		this.user = user;
		this.playerId = pid;
	}

	@Override
	public void run() {
		
		try {
			out.writeInt(playerId); 
		} catch (IOException e) {
			System.out.println("Failed to send PlayerID");
		}
		
		while(true){
			try{	
				playerIdInput = in.readInt();
				xInput = in.readInt();
				yInput = in.readInt();
				for(int i = 0 ; i < 10; i++){
					if(user[i] != null){
						user[i].out.writeInt(playerIdInput);
						user[i].out.writeInt(xInput);
						user[i].out.writeInt(yInput);
					}
				}
			} catch(IOException ex){
				user[playerId] = null;
			}
		}
	
	}
	
}
