/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.util.ArrayList;
<<<<<<< HEAD
import model.Chat;
import model.ChatEntry;
=======
import model.ChatEntry;
import model.ClientHandler;
>>>>>>> my_old_master

/**
 *
 * @author kim
 */
public class View 
{
    private Controller contr;
<<<<<<< HEAD
    private ConnectFrame connect;
    public View(Controller contr)
    {
        this.contr = contr;
        contr.setView(this);
        connect = new ConnectFrame(this);
=======
    private ServerFrame server;
    private MainFrame main;
    private ConnectFrame connect;
    private ArrayList<ClientFrame> clients = new ArrayList();
    private ClientHandler clienthandler;
    public View(Controller contr)
    {
        this.contr = contr;
        //server = new ServerFrame();
        main = new MainFrame(this);
    }
    public boolean checkServer()
    {
        return contr.checkServer();
    }
    public void startServer()
    {
        contr.startServer();
>>>>>>> my_old_master
    }
    public int getServerPort()
    {
        return contr.getServerPort();
    }
<<<<<<< HEAD
    public Chat newChat(String ip, int port, String username)
    {
        Chat chat = contr.newChat(ip, port, username);
        if(chat.success())
        {
            new ChatFrame(this, chat);
        }
        return chat;
        
    }
    public void newChat(Chat chat)
    {
        if(chat.success())
        {
            System.out.println("chat success");
            new ChatFrame(this, chat);
        }
        
    }
=======
    public void stopServer()
    {
        contr.stopServer();
        if(server != null)
        server.dispose();
        if(connect != null)
        {
            connect.dispose();
        }
        for(ClientFrame c : clients)
        {
            c.dispose();
        }
    }
    public void connect()
    {
        connect = new ConnectFrame(this);
        connect.location(main);
        
    }
    public void newConnection(String ip, int port, String username)
    {
        ClientFrame client = new ClientFrame(this, contr.newConnection(ip, port, username));
        client.location(main);
        clients.add(client);
        clienthandler = contr.getClientHandler();
        server = new ServerFrame(this,clienthandler);
        server.location(main);
    }
    public void newEntry(String author, String msg)
    {
        contr.newEntry(author, msg);
    }
    public ArrayList<ChatEntry> getChat()
    {
        return contr.getChat();
    }
    public  void encrypt()
    {
        contr.encrypt();
    }
    
>>>>>>> my_old_master
}
