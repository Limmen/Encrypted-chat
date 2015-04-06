/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import model.Chat;
import model.Client;

/**
 *
 * @author kim
 */
public class View 
{
    private Controller contr;
    private ConnectFrame connect;
    public View(Controller contr)
    {
        this.contr = contr;
        contr.setView(this);
        connect = new ConnectFrame(this);
    }
    public int getServerPort()
    {
        return contr.getServerPort();
    }
    public Client newChat(String ip, int port, String username)
    {
        Chat chat = contr.newChat(ip, port);
        Client client = chat.newClient(username);
        if(client.success())
        {
            new ChatFrame(this, chat, client);
        }
        return client;
        
    }
    /*
    public void newChat(Chat chat)
    {
        if(chat.success())
        {
            System.out.println("chat success");
            new ChatFrame(this, chat);
        }
        
    } */
}
