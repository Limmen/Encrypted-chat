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
public class Client extends Thread 
{
    private String ip;
    private int port;
    private String username;
    private Socket socket;
    PrintWriter out;
    BufferedReader in;
    public Client(String ip, int port, String username)
    {
        this.ip = ip;
        this.port = port;
        this.username = username;
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
            System.out.println("server says:" + in.readLine());
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
    public void close(Socket socket)
    {
        System.out.println("Shutting down socket");
        out.close();
        try
        {
            socket.close();
            socket.close();
        }
        catch(Exception e)
        {
            
        }
    }
    public void write(String msg)
    {
        out.println(msg);
    }
    public String getUsername()
    {
        return username;
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
