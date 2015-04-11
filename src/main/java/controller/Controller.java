package controller;

import java.util.ArrayList;
import model.Chat;
import model.ChatManager;
import view.View;

/**
 *This is the controller class which works as a traditional controller from
 * the MVC design pattern.
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
    public int getServerPort()
    {
        return cm.getServerPort();
    }
    public Chat newChat(String ip, int port, String username)
    {
        return cm.newChat(ip, port, username);
    }
    public void cleanUp()
    {
        cm.cleanUp();
    }
}
