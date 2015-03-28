/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author kim
 */
public class Server extends Thread
{
    ServerSocket server;
    private int port;
    Socket socket = new Socket();
    public Server()
    {
        port = 1237;
    }
    @Override
    public void run()
    {
    while(!isInterrupted())
    {
      try {
         server = new ServerSocket(port); //server socket listening on port xxxx
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
         
         
      }
      catch(Exception e) 
      {
         System.out.print("Whoops! It didn't work!\n");
      }
    }
    }
    public void listen()
    {
        try
        {
            socket = server.accept(); //wait for client to connect.
            new ClientHandler(socket);
        }
        catch(Exception e)
        {
            
        }
    }
    public int getPort()
    {
        return port;
    }
    public void kill()
    {
        try
        {
            interrupt();
            socket.close();
            server.close();
        }
        catch(Exception e)
        {
                    
        }
    }

}
