package model;

import java.util.ArrayList;
import view.ChatFrame;
import view.PrivateChatFrame;

/**
 *This class represents a chat consisting of clients and a server.
 * This class works as a interface between GUI and the socket.
 * @author kim
 */
public class Chat 
{
    String ip;
    int port;
    private ArrayList<Client> clients = new ArrayList();
    public Chat(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }
    public Client newClient(String username) throws Exception
    {
        Client client = new Client(ip, port, username, this);
        client.start();
        clients.add(client);
        return client;
    }
    public void setFrame(ChatFrame frame, Client client)
    {
        client.cf = frame;
    }
    public void setFrame(PrivateClient client)
    {
        if(client == null)
            System.out.println("client == null..");
        client.pc = new PrivateChatFrame(this, client);
    }
    public void updateChat(ChatEntry ce, Client client)
    {
        client.cf.updateChat(ce);
    }
    public void updateChat(ChatEntry ce, PrivateClient client)
    {
        client.pc.updateChat(ce);
    }
    public void newEntry(ChatEntry ce, Client client)
    {
        client.chatentrys.add(ce);
    }
    public void newEntry(ChatEntry ce, PrivateClient client)
    {
        client.chatentrys.add(ce);
    }
    public ArrayList<ChatEntry> getChat(Client client)
    {
        return client.chatentrys;
    }
    public ArrayList<ChatEntry> getChat(PrivateClient client)
    {
        return client.chatentrys;
    }
    public void cleanUp(Client client)
    {
        client.kill();
        clients.remove(client);
    }
    public void cleanUp(PrivateClient client)
    {
        client.kill();
    }
    public boolean checkUsername(String username)
    {
        for (Client client : clients)
        {
            if(client.username.equalsIgnoreCase(username))
            {
                return false;
            }
        }
        return true;
    }
}
