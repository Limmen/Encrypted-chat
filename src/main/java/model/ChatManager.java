/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Controller;
import java.util.ArrayList;

/**
 *
 * @author kim
 */
public class ChatManager 
{
    private Controller contr;
    private Server server;
    private ArrayList<Chat> serverChats = new ArrayList();
    
    public ChatManager(Controller contr)
    {
        this.contr = contr;
        startServer();
    }
    public void startServer()
    {
        server = new Server(this);
        server.start();
    }
    public int getServerPort()
    {   
        return server.getPort();
    }
    public void stopServer()
    {
            server.kill();
    }
    public Chat newChat(String ip, int port)
    {
        for(Chat c : serverChats)
        {
            if(ip.equals(c.ip) && port == c.port)
            {
                return c;
            }
        }
        Chat newchat = new Chat(ip, port);
        serverChats.add(newchat);
        return newchat;
    }
}
