/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author kim
 */
public class Chat 
{
    private boolean serverRunning;
    private Server server;
    private ArrayList<Client> clients = new ArrayList();
    private ArrayList<ChatEntry> chatentrys = new ArrayList();
    
    public Chat()
    {
        serverRunning = false;
    }
    public void startServer()
    {
        if(serverRunning)
        {
            System.out.println("Server is already running on: ");
            return;
        }
        else
        {
            server = new Server();
            server.start();
            serverRunning = true;
        }
    }
    public boolean checkServer()
    {
        return serverRunning;
    }
    public int getServerPort()
    {
        if(serverRunning)
        {
            return server.getPort();
        }
        else
        {
            System.out.println("Server not running");
            return -1;
        }
        
    }
    public void stopServer()
    {
        if(serverRunning)
        {
            server.kill();
            serverRunning = false;
        }
        else
        {
            System.out.println("Server not running");
        }
    }
    public Client newClient(String ip, int port, String username)
    {
        Client client = new Client(ip, port, username);
        clients.add(client);
        return client;
    }
    public void newEntry(String author, String msg)
    {
        chatentrys.add(new ChatEntry(author, msg));
    }
    public ArrayList<ChatEntry> getChat()
    {
        return chatentrys;
    }
    
}
