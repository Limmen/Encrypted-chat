/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
<<<<<<< HEAD
import view.ChatFrame;
=======
>>>>>>> my_old_master

/**
 *
 * @author kim
 */
public class Chat 
{
<<<<<<< HEAD
    String ip;
    int port;
    String username;
    public Client client;
    private ArrayList<ChatEntry> chatentrys = new ArrayList();
    ChatFrame cf;
    public Chat(String ip, int port, String username)
    {
        this.ip = ip;
        this.port = port;
        this.username = username;
        newClient();
    }
    public void newClient()
    {
        client = new Client(ip, port, username, this);
        client.start();
    }
    public void setFrame(ChatFrame cf)
    {
        this.cf = cf;
    }
    public void updateChat(String msg)
    {
        cf.updateChat(msg);
    }
    public void newEntry(String author, String msg)
    {
        chatentrys.add(new ChatEntry(author, msg));
    }
    public ArrayList<ChatEntry> getChat()
    {
        return chatentrys;
    }
    public String getUsername()
    {
        return username;
    }
    public boolean success()
    {
        return client.success();
    }
    public void cleanUp()
    {
        client.kill();
    }
    public void send(String msg)
    {
        System.out.println("send method here..");
        client.out.println(msg);
    }
=======
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
            for(Client c : clients)
            {
                c.kill();
            }
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
        client.start();
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
    public ClientHandler getClientHandler()
    {
        return server.getclientHandler();
    }
    
>>>>>>> my_old_master
}
