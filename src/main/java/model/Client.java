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
<<<<<<< HEAD
    public PrintWriter out;
    BufferedReader in;
    private boolean success;
    Chat chat;
    public Client(String ip, int port, String username, Chat chat)
=======
    PrintWriter out;
    BufferedReader in;
    public Client(String ip, int port, String username)
>>>>>>> my_old_master
    {
        this.ip = ip;
        this.port = port;
        this.username = username;
<<<<<<< HEAD
        success = true;
        this.chat = chat;
=======
>>>>>>> my_old_master
    }
    @Override
    public void run()
    {
            try
            {
                socket = new Socket(ip, port);
<<<<<<< HEAD
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
        while (true)
        {
            chat.updateChat(in.readLine());
            System.out.println(username + " received a message!");
        }
            }
        catch(Exception e)
        {
           success = false;
=======
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
            
>>>>>>> my_old_master
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
<<<<<<< HEAD
    public String getUsername()
    {
        return username;
    }
    public boolean success()
    {
        return success;
=======
    public void write(String msg)
    {
        out.println(msg);
    }
    public String getUsername()
    {
        return username;
>>>>>>> my_old_master
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
