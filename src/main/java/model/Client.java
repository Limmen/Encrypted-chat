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
    public PrintWriter out;
    BufferedReader in;
    private boolean success;
    Chat chat;
    public Client(String ip, int port, String username, Chat chat)
    {
        this.ip = ip;
        this.port = port;
        this.username = username;
        success = true;
        this.chat = chat;
    }
    @Override
    public void run()
    {
            try
            {
                socket = new Socket(ip, port);
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
    public String getUsername()
    {
        return username;
    }
    public boolean success()
    {
        return success;
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
