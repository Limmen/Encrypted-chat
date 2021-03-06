package model;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import view.ErrorFrame;

/**
 *This class represents the server running on localhost.
 * For every new user connecting a clienthandler thread is created.
 * @author kim
 */
public class Server extends Thread
{
    ServerSocket server;
    Socket socket = new Socket();
    ChatManager cm;
    ArrayList<PrintWriter> users = new ArrayList();
    ArrayList<ClientHandler> handlers = new ArrayList();
    ArrayList <ChatRoomEntry> chatroomEntrys = new ArrayList();
    ArrayList <ChatCertificate> certificates = new ArrayList();
    int count;
    public RSA key;
    public Server(ChatManager cm)
    {
        this.cm = cm;
        this.key = new RSA();
        count = 0;
    }
    @Override
    public void run()
    {
      try {
         server = new ServerSocket(0); //server socket listening on port xxxx
         while(!isInterrupted())
         {
             try
             {
                 listen();
             }
             catch(Exception e)
             {
                 
             }
         }
      }
      catch(Exception e) 
      {
         new ErrorFrame("Error with local chatserver");
      }
    
    }
    public void listen()
    {
        try
        {
            handlers.add(new ClientHandler(server.accept(), this, count));
            count++;
            
        }
        catch(Exception e)
        {
            
        }
    }
    public ArrayList<PrintWriter> getUsers()
    {
        return users;
    }
    public ArrayList<ClientHandler> getHandlers()
    {
        return handlers;
    }
    public int getPort()
    {
        return server.getLocalPort();
    }
    public void kill()
    {
        try
        {
            socket.close();
            server.close();
            for(ClientHandler h : handlers)
            {
                h.kill();
            }
            interrupt();
        }
        catch(Exception e)
        {
                    
        }
    }
}
