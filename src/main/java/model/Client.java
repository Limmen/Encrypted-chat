package model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import view.ChatFrame;
import view.RequestFrame;
import view.WaitFrame;

/**
 *This class represents a chat user (client) and the corresponding socket connection
 * with a server.
 * @author kim
 */
public class Client extends Thread 
{
    public String ip;
    public int port;
    public String username;
    private Socket socket;
    private ObjectInputStream objectIn;
    public ObjectOutputStream objectOut;
    Chat chat;
    ChatFrame cf;
    ArrayList<ChatEntry> chatentrys = new ArrayList();
    public WaitFrame wf;
    
    public Client(String ip, int port, String username, Chat chat) throws Exception
    {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.chat = chat;
        socket = new Socket(ip, port);
    }
    @Override
    public void run()
    {
            try
            {
                objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectIn = new ObjectInputStream(socket.getInputStream());
                RSA key = (RSA)objectIn.readObject();
                cf.key = key;
                sleep(100);
                objectOut.writeObject(username);
                objectOut.reset();
                sleep(50);
                objectOut.writeObject(ip);
        while (true)
        {
            String inputs =(String)objectIn.readObject();
            if(inputs == null)
            {
                //Connection to Server was lost
                cf.lostConnection();
                kill();
            }
            if(inputs.equals("117 115 101 114 110 097 109 101"))
            {
                Object o = objectIn.readObject();
                ArrayList<ChatRoomEntry> users = (ArrayList<ChatRoomEntry>) o;
                cf.updateUsers(users);
                users = null;
                continue;
            }
             if(inputs.equalsIgnoreCase("112 114 105 118 097 116 101 032 099 104 097 116"))
             {
                 //String port = (String) objectIn.readObject();
                 String from = (String) objectIn.readObject();
                 new RequestFrame(from, this);
                 continue;
             }
             if(inputs.equalsIgnoreCase("115 101 116 116 105 110 103 032 117 112 032 099 104 097 116"))
             {
                 String port = (String) objectIn.readObject();
                 //sleep(1000);
                 PrivateClient pc = new PrivateClient(ip, Integer.parseInt(port), username, new Chat(ip, Integer.parseInt(port)));
                 cf.setState(cf.ICONIFIED);
                 sleep(100);
                 wf.dispose();
                 pc.setFrame();
                 pc.start();
             }
             if(inputs.equalsIgnoreCase("097 099 099 101 112 116 101 100"))
             {
                 String port = (String) objectIn.readObject();
                 //sleep(500);
                 PrivateClient pc = new PrivateClient(ip, Integer.parseInt(port), username, new Chat(ip, Integer.parseInt(port)));
                 pc.setFrame();
                 pc.start();
                 continue;
             }
            chat.updateChat(inputs, this);
            
        }
            }
        catch(Exception e)
        {
            e.printStackTrace();
            cf.lostConnection();
            kill();
        }
    }
    public void close(Socket socket)
    {
        try
        {
            objectOut.close();
            socket.close();
        }
        catch(Exception e)
        {
            
        }
    }
    public String getUsername()
    {
        return username;
    }
    public void kill()
    {
        try
        {
            objectIn.close();
            objectOut.close();
            socket.close();
            interrupt();
            stop();
        }
        catch(Exception e)
        {
            
        }
    }
    public void requestChat(String from, String to)
    {
        try
        {
        objectOut.writeObject("112 114 105 118 097 116 101 032 099 104 097 116"); //ascii for "private chat"
        objectOut.reset();
        objectOut.writeObject(from + " " + to);
        objectOut.reset();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
