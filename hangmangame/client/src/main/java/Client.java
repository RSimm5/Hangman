import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.LogManager;




public class Client extends Thread{

	int port;
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	private Consumer<Serializable> callback;

	Packet updateCategory;
	Packet updateRound;
	
	Client(int port, Consumer<Serializable> call){
		this.port = port;
		callback = call;
	}
	
	public void run() {
		
		try {
		socketClient= new Socket("127.0.0.1", port);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}
		
		while(true) {
			 
			try {
				Object message = in.readObject();

				if (message instanceof Packet) {
					updateCategory = (Packet) message;
					callback.accept(updateCategory);
				}
				else if (message instanceof String) {
					String strMessage = (String) message;
                	callback.accept(strMessage);
				}
			}
			catch(Exception e) {}
		}
	
    } // end run
	
	public void send(String data) {
		
		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startGame() {
		send("startGame");
		send("choicesPage");
	}

	public void getCatData() {
		send("choicesPage");
	}

	public void pickCategory(String category) {
		send("pickCategory " + category);
		send("roundData");
	}

	public void makeGuess(char guess) {
		send("makeGuess " + guess);
		send("roundData");
	}

}
