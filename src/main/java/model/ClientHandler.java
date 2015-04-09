package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *This class represents the server-side of a connection with a user in the chat.
 * @author kim
 */
public class ClientHandler extends Thread{
        Socket socket;    
        private BufferedReader in;
        private PrintWriter out;
        private ObjectOutputStream objectOut;
        private Server  server;
        private int nr;
        public ClientHandler(Socket socket, Server  server, int nr)
        {
            this.socket = socket;
            this.server = server;
            this.nr = nr;
            start();
        }
        public void run() {
            try 
            {
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                objectOut = new ObjectOutputStream(socket.getOutputStream());
                server.users.add(out);
                sleep(250);
                objectOut.writeObject(server.key);
                while(true)
                {
                    String input = in.readLine();
                    if(input == null)
                    {
                        //Client left the chat.
                        server.getHandlers().remove(this);
                        return;
                    }
                    for(ClientHandler ch : server.getHandlers())
                    {
                        ch.printToClient(input);
                        sleep(1000);
                    }
                }
            }
                catch(Exception e)
                {
                        System.out.println("There was a error with the socket connection");
                }
        }
        public void printToClient(String msg)
        {
            out.println(msg);
        }
        public void kill()
        {
            try
            {
                interrupt();
            }
            catch(Exception e)
            {
                    
            }
        }
    }