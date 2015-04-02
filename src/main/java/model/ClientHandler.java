/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author kim
 */
public class ClientHandler extends Thread
{
    PrintWriter out;
    BufferedReader in;
    Socket socket;
    String ip;
    int port;
    public ClientHandler(Socket socket)
    {
        this.socket = socket;
        ip = socket.getRemoteSocketAddress().toString();
        port = socket.getLocalPort();
        System.out.println("client has connected to IP: " + ip + "port: " + port);
    }
    @Override
    public void run()
    {
        try
            {
                socket = new Socket(ip, port);
                bindStreams(socket);
            }
            catch(Exception e)
            {
                
            }
        while (true)
        {
            try
            {
            System.out.println("Client says:" + in.readLine());
            }
        catch(Exception e)
        {
            
        }
        }
        
    }
    
    public void bindStreams(Socket socket)
    {
        //Bind standard streams to writer and reader.
        try
        {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(Exception e)
        {
            
        }
    }
    public void kill()
    {
        System.out.println("Shutting down socket");
        try
        {
            out.close();
            in.close();
            socket.close();
        }
        catch(Exception e)
        {
            
        }
    }
   
}
