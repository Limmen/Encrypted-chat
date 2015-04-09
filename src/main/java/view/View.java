package view;

import controller.Controller;
import model.Chat;
import model.Client;

/**
 *This class is a interface for the different frames to the Controller and
 * the model.
 * @author kim
 */
public class View 
{
    private Controller contr;
    private ConnectFrame connect;
    public View(Controller contr)
    {
        this.contr = contr;
        connect = new ConnectFrame(this);
    }
    public int getServerPort()
    {
        return contr.getServerPort();
    }
    public Client newChat(String ip, int port, String username)
    {
        Chat chat = contr.newChat(ip, port, username);
        if(chat == null)
            return null;
        Client client = chat.newClient(username);
        new ChatFrame(this, chat, client);
        return client;
        
    }
}
