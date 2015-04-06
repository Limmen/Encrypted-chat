/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.Chat;
import model.ChatManager;
import view.View;

/**
 *
 * @author kim
 */
public class Controller 
{
    private ChatManager cm;
    private View view;
    public Controller()
    {
        cm = new ChatManager(this);
    }
    public void setView(View view)
    {
        this.view = view;
    }
    /*
    public void incommingChat(Chat chat)
    {
        view.newChat(chat);
    } */
   /* 
    public boolean checkServer()
    {
        return chat.checkServer();
    }
    public void startServer()
    {
        chat.startServer();
    } */
    public int getServerPort()
    {
        return cm.getServerPort();
    }
    /*
    public void stopServer()
    {
        chat.stopServer();
    }
    */
    public Chat newChat(String ip, int port)
    {
        return cm.newChat(ip, port);
    }
    public void encrypt()
    {
        
    }
}
