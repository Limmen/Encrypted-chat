/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import model.Server;

/**
 *
 * @author kim
 */
public class View 
{
    private Controller contr;
    private ServerFrame server;
    private MainFrame main;
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
    
}
