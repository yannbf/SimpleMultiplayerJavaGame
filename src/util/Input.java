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
				int xInput = in.readInt();
				int yInput = in.readInt();
				client.updateCoordinates(playerId, xInput, yInput);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
