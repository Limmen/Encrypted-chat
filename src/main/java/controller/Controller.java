/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.Chat;
<<<<<<< HEAD
import model.ChatManager;
import view.View;
=======
import model.ChatEntry;
import model.Client;
import model.ClientHandler;
>>>>>>> my_old_master

/**
 *
 * @author kim
 */
public class Controller 
{
<<<<<<< HEAD
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
    public void incommingChat(Chat chat)
    {
        view.newChat(chat);
    }
   /* 
=======
    private Chat chat;
    public Controller()
    {
        chat = new Chat();
    }
>>>>>>> my_old_master
    public boolean checkServer()
    {
        return chat.checkServer();
    }
    public void startServer()
    {
        chat.startServer();
<<<<<<< HEAD
    } */
    public int getServerPort()
    {
        return cm.getServerPort();
    }
    /*
=======
    }
    public int getServerPort()
    {
        return chat.getServerPort();
    }
>>>>>>> my_old_master
    public void stopServer()
    {
        chat.stopServer();
    }
<<<<<<< HEAD
    */
    public Chat newChat(String ip, int port,  String username)
    {
        return cm.newChat(ip, port, username);
=======
    public Client newConnection(String ip, int port,  String username)
    {
        return chat.newClient(ip, port, username);
    }
    public void newEntry(String author, String msg)
    {
        chat.newEntry(author, msg);
    }
    public ArrayList<ChatEntry> getChat()
    {
        return chat.getChat();
    }
    public ClientHandler getClientHandler()
    {
        return chat.getClientHandler();
>>>>>>> my_old_master
    }
    public void encrypt()
    {
        
    }
}
