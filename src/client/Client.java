package client;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import util.Input;

public class Client extends Applet implements Runnable, KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	static Socket socket;
	static DataOutputStream out;
	static DataInputStream in;

	int playerId;

	int[] x = new int[10];
	int[] y = new int[10];
	int[] numberOfClicks = new int[10];

	boolean left, right, up, down, click;

	int playerX;
	int playerY;
	int playerNumberOfClicks;

	public void init() {
		setSize(300, 300);
		addKeyListener(this);
		addMouseListener(this);
		try {
			System.out.println("Connecting..");
			socket = new Socket("localhost", 7777);
			System.out.println("Connection successful");
			in = new DataInputStream(socket.getInputStream());
			playerId = in.readInt(); 
			out = new DataOutputStream(socket.getOutputStream());

			Input input = new Input(in, this);
			Thread thread = new Thread(input);
			thread.start();
			Thread thread2 = new Thread(this);
			thread2.start();
		} catch (Exception ex) {
			System.out.println("Unable to start client");
		}
	}

	public void updateCoordinates(int playerId, int xIn, int yIn) {
		this.x[playerId] = xIn;
		this.y[playerId] = yIn;
	}
	
	public void updateClickCount(int playerId, int count) {
		this.numberOfClicks[playerId] = count;
	}

	public Color getColor(int i) {
		switch (i) {
		case 0:
			return Color.BLUE;
		case 1:
			return Color.RED;
		case 2:
			return Color.GREEN;
		case 3:
			return Color.YELLOW;
		case 4:
			return Color.PINK;
		default:
			return Color.BLACK;
		}
	}

	public void paint(Graphics g) { 
		for (int i = 0; i < 10; i++) {
			g.setColor(getColor(i));
			g.drawOval(x[i], y[i], 5, 5);
			g.drawString(String.valueOf(this.numberOfClicks[i]), x[i], y[i]);
		}
	}

	@Override
	public void run() {
		while (true) {
			if (right) {
				playerX += 10;
			}
			if (left) {
				playerX -= 10;
			}
			if (up) {
				playerY -= 10;
			}
			if (down) {
				playerY += 10;
			}

			if (right || left || up || down || click) {
				try {				
					System.out.println("PLAYER ID " + playerId);
					System.out.println("X " + playerX);
					System.out.println("Y " + playerY);
					System.out.println("CLICKS " + playerNumberOfClicks);

					out.writeInt(playerId);
					out.writeInt(playerX);
					out.writeInt(playerY);
					out.writeInt(numberOfClicks[playerId]);

				} catch (IOException e) {
					System.out.println("Error sending coordinates");
				}
				repaint();
				
				if(click) click = !click;
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.numberOfClicks[playerId]++;
		click = true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
