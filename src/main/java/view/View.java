/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.util.ArrayList;
import model.ChatEntry;
import model.ClientHandler;

/**
 *
 * @author kim
 */
public class View 
{
    private Controller contr;
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
    }
    public int getServerPort()
    {
        return contr.getServerPort();
    }
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
    
}
