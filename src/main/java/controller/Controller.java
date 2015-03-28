/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Chat;
import model.Server;

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
    
}
