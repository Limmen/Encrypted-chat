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
}
