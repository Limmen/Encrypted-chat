package model;

import controller.Controller;
import java.util.ArrayList;

/**
 *This class is a manager that handles all of the ongoing chats for the localhost.
 * @author kim
 */
public class ChatManager 
{
    private Controller contr;
    private Server server;
    private ArrayList<Chat> serverChats = new ArrayList();
    
    public ChatManager(Controller contr)
    {
        this.contr = contr;
        startServer();
    }
    public void startServer()
    {
        server = new Server(this);
        server.start();
    }
    public int getServerPort()
    {   
        return server.getPort();
    }
    public void stopServer()
    {
            server.kill();
    }
    public Chat newChat(String ip, int port, String username)
    {
        for(Chat c : serverChats)
        {
            if(ip.equals(c.ip) && port == c.port)
            {
                if(c.checkUsername(username))
                {
                    return c;
                }
                else
                {
                    return null;
                }
            }
        }
        Chat newchat = new Chat(ip, port);
        serverChats.add(newchat);
        return newchat;
    }
    public void cleanUp()
    {
        stopServer();
    }
}
