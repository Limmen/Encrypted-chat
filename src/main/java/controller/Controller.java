/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.Chat;
import model.ChatEntry;
import model.Client;

/**
 *
 * @author kim
 */
public class Controller 
{
    private Chat chat;
    public Controller()
    {
        chat = new Chat();
    }
    public boolean checkServer()
    {
        return chat.checkServer();
    }
    public void startServer()
    {
        chat.startServer();
    }
    public int getServerPort()
    {
        return chat.getServerPort();
    }
    public void stopServer()
    {
        chat.stopServer();
    }
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
}
