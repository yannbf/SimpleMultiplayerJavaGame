package util;
import java.io.DataInputStream;
import java.io.IOException;

import client.Client;

public class Input implements Runnable {
	DataInputStream in;
	Client client;

	public Input(DataInputStream in, Client client) {
		this.in = in;
		this.client = client;
	}

	@Override
	public void run() {
		try {
			while (true) {
				int playerId = in.readInt();
				System.out.println("PLAYER ID " + playerId);
				int xInput = in.readInt();
				System.out.println("X " + xInput);
				int yInput = in.readInt();
				System.out.println("Y " + yInput);
				client.updateCoordinates(playerId, xInput, yInput);
				//int numberOfClicks = in.readInt();
				//System.out.println("CLICKS " + numberOfClicks);
				client.updateClickCount(playerId, xInput);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
