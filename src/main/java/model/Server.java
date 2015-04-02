/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
=======
import java.net.ServerSocket;
import java.net.Socket;
>>>>>>> my_old_master

/**
 *
 * @author kim
 */
public class Server extends Thread
{
    ServerSocket server;
<<<<<<< HEAD
    Socket socket = new Socket();
    ChatManager cm;
    ArrayList<PrintWriter> users = new ArrayList();
    ArrayList<ClientHandler> handlers = new ArrayList();
    public Server(ChatManager cm)
    {
        this.cm = cm;
=======
    private int port;
    Socket socket = new Socket();
    ClientHandler clienthandler;
    public Server()
    {
        port = 1237;
>>>>>>> my_old_master
    }
    @Override
    public void run()
    {
<<<<<<< HEAD
      try {
         server = new ServerSocket(0); //server socket listening on port xxxx
=======
    while(!isInterrupted())
    {
      try {
         server = new ServerSocket(port); //server socket listening on port xxxx
>>>>>>> my_old_master
         System.out.println("Server up and listening");
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
<<<<<<< HEAD
=======
         
         
>>>>>>> my_old_master
      }
      catch(Exception e) 
      {
         System.out.print("Whoops! It didn't work!\n");
      }
<<<<<<< HEAD
    
=======
    }
>>>>>>> my_old_master
    }
    public void listen()
    {
        try
        {
<<<<<<< HEAD
            handlers.add(new ClientHandler(server.accept(), this));
            
=======
            socket = server.accept(); //wait for client to connect.
            clienthandler = new ClientHandler(socket);
>>>>>>> my_old_master
        }
        catch(Exception e)
        {
            
        }
    }
<<<<<<< HEAD
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
=======
    public int getPort()
    {
        return port;
>>>>>>> my_old_master
    }
    public void kill()
    {
        try
        {
            interrupt();
            socket.close();
            server.close();
<<<<<<< HEAD
            for(ClientHandler h : handlers)
            {
                h.kill();
            }
=======
            clienthandler.kill();
>>>>>>> my_old_master
        }
        catch(Exception e)
        {
                    
        }
    }
<<<<<<< HEAD
    private static class ClientHandler extends Thread{
        Socket socket;    
        private BufferedReader in;
        private PrintWriter out;
        private Server  server;
        public ClientHandler(Socket socket, Server  server)
        {
            this.socket = socket;
            this.server = server;
            start();
        }
        public void run() {
            try 
            {
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                server.users.add(out);
                while(true)
                {
                    String input = in.readLine();
                    if(input == null)
                    {
                        System.out.println("Input is null");
                        server.getHandlers().remove(this);
                        return;
                    }
                    System.out.println("Number of handlers/users in the chat: " + server.getHandlers().size());
                    for(ClientHandler ch : server.getHandlers())
                    {
                        System.out.println("round");
                        ch.printToClient(input);
                    }
                }
            }
                catch(Exception e)
                {
                        System.out.println("Exception!");
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
    
=======
    public ClientHandler getclientHandler()
    {
        return clienthandler;
    }
>>>>>>> my_old_master

}
