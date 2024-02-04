import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server{
	int count = 1, port;	
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback;
	
	
	Server(int port, Consumer<Serializable> call){
		this.port = port;
		callback = call;
		server = new TheServer();
		server.start();
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(port);){
		    System.out.println("Server is waiting for a client!");
		  
			
		    while(true) {
		
				ClientThread c = new ClientThread(mysocket.accept(), count);
				callback.accept("client has connected to server: " + "client #" + count);
				clients.add(c);
				c.start();
				
				count++;
				
			    }
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}
	

		class ClientThread extends Thread{
		
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			private HangmanGame hangmanGame;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;
				try {
					this.hangmanGame = new HangmanGame();
				} catch (Exception e) {
					e.printStackTrace(); // or log the error
				}
			}
			
			// sends message to all clients
			public void updateClients(String message) {
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					try {
					 t.out.writeObject(message);
					}
					catch(Exception e) {}
				}
			}

			public void updateClient(ClientThread targetClient, Packet data) {
				try {
					targetClient.out.writeObject(data);
				} catch (Exception e) {
					e.printStackTrace(); // Handle the exception appropriately
				}
			}
			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				
				updateClients("new client on server: client #"+count);
					
				 while(true) {
					    try {
					    	String data = in.readObject().toString();
					    	callback.accept("client: " + count + " sent: " + data);
					    	updateClients("client #"+count+" said: "+data);

							callMethod(data);
					    }
					    catch(Exception e) {
					    	callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
					    	updateClients("Client #"+count+" has left the server!");
					    	clients.remove(this);
					    	break;
					    }
					}
				}//end of run
			

			// calls method from client data
			void callMethod(String data) {
				// get method and command
				int index = data.indexOf(" ");
				String method = data;
				String extra = "";
				if (index != -1) {
					method = data.substring(0, index);
					extra = data.substring(index + 1);
				}

				// process
				if (method.equals("startGame")) {
					hangmanGame.initialize();
				}
				// receive data
				else if (method.equals("choicesPage")) {
					updateClient(this, hangmanGame.getCategoryData());
					callback.accept("client: " + count + " received: category data - names, attempts, hasWon");
				}
				else if (method.equals("roundData")) {
					updateClient(this, hangmanGame.getRoundData()); // package
					callback.accept("client: " + count + " received: hiddenWord, prevGuesses, remainingGuesses");
				}

				// function calls
				else if (!extra.equals("")) {

					if (method.equals("pickCategory")) {
						hangmanGame.pickCategory(extra); // run method
						hangmanGame.startRound();
					}
					else if (method.equals("makeGuess")) {
						hangmanGame.makeGuess(extra.charAt(0)); // run method
					}
				}
			}
			
		}//end of client thread
}