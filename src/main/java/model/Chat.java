/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import view.ChatFrame;

/**
 *
 * @author kim
 */
public class Chat 
{
    String ip;
    int port;
    private ArrayList<Client> clients = new ArrayList();
    public RSA key;
    public Chat(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
        this.key = new RSA();
    }
    public Client newClient(String username)
    {
        Client client = new Client(ip, port, username, this);
        client.start();
        clients.add(client);
        return client;
    }
    public void setFrame(ChatFrame frame, Client client)
    {
        client.cf = frame;
    }
    public void updateChat(String msg, Client client)
    {
        client.cf.updateChat(msg);
    }
    public void newEntry(String author, String msg, Client client)
    {
        client.chatentrys.add(new ChatEntry(author, msg));
    }
    public ArrayList<ChatEntry> getChat(Client client)
    {
        return client.chatentrys;
    }
    
    public boolean success(Client client)
    {
        return client.success();
    }
    public void cleanUp(Client client)
    {
        client.kill();
        clients.remove(client);
    }
    public void send(String msg, Client client)
    {
        System.out.println("send method here..");
        client.out.println(msg);
    }
}
