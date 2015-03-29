/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.util.ArrayList;
import model.ChatEntry;

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
    }
    public void connect()
    {
        this.connect = new ConnectFrame(this);
    }
    public void newConnection(String ip, int port, String username)
    {
        clients.add(new ClientFrame(this, contr.newConnection(ip, port, username)));
    }
    public void newEntry(String author, String msg)
    {
        contr.newEntry(author, msg);
    }
    public ArrayList<ChatEntry> getChat()
    {
        return contr.getChat();
    }
    
}
