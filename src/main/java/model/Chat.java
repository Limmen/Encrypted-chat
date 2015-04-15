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
        client.pc = new PrivateChatFrame(this, client);
    }
    public void updateChat(String msg, Client client)
    {
        client.cf.updateChat(msg);
    }
    public void updateChat(String msg, PrivateClient client)
    {
        client.pc.updateChat(msg);
    }
    public void newEntry(String author, String msg, Client client)
    {
        client.chatentrys.add(new ChatEntry(author, msg));
    }
    public void newEntry(String author, String msg, PrivateClient client)
    {
        client.chatentrys.add(new ChatEntry(author, msg));
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
        clients.remove(client);
    }
    public void send(String msg, Client client)
    {
        client.out.println(msg);
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
