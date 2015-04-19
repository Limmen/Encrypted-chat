package model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import view.ChatFrame;
import view.PrivateChatFrame;
import view.WaitFrame;

/**
 *This class represents a chat user (client) and the corresponding socket connection
 * with a server.
 * @author kim
 */
public class PrivateClient extends Thread 
{
    public String ip;
    public int port;
    public String username;
    private Socket socket;
    public ObjectInputStream objectIn;
    Chat chat;
    private RSA key;
    public RSAPublicKey myPublicKey;
    public RSAPublicKey friendPublicKey;
    PrivateChatFrame pc;
    public ObjectOutputStream objectOut;
    ArrayList<ChatEntry> chatentrys = new ArrayList();
    WaitFrame wf;
    ChatRoomEntry me;
    boolean sent = false;
    
    public PrivateClient(String ip, int port, String username, Chat chat)
    {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.chat = chat;
        this.key = new RSA();
        this.myPublicKey = key.publicKey;
        this.me = new ChatRoomEntry(username, ip);
        try
        {
        socket = new Socket(ip, port);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void setFrame(ChatFrame cf)
    {
        chat.setFrame(this);
        pc.key = this.key;
        pc.setVisible(false);
        pc.cf = cf;
    }
    public void setWaitFrame(WaitFrame wf)
    {
        this.wf = wf;
    }
    @Override
    public void run()
    {
            try
            {
                init();
        while (true)
        {
            Object o  = objectIn.readObject();
            if(o == null)
            {
                //Connection to Server was lost
                pc.lostConnection();
                kill();
            }
            if(o instanceof RSAPublicKey)
            {
                this.friendPublicKey = (RSAPublicKey) o;
                wf.dispose();
                pc.setVisible(true);
            }
            
            if(o instanceof ArrayList)
            {
                ArrayList<ChatRoomEntry> users = (ArrayList<ChatRoomEntry>) o;
                pc.updateUsers(users);
                continue;
            }
            if(o instanceof ChatEntry)
            {
                ChatEntry ce = (ChatEntry) o;
                chat.updateChat(ce, this);
            }
            
        }
            }
        catch(Exception e)
        {
            e.printStackTrace();
            //pc.lostConnection();
            //kill();
        }
    }
    public void close(Socket socket)
    {
        
        try
        {
            objectIn.close();
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
    public void init()
    {
        try
        {
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        objectIn = new ObjectInputStream(socket.getInputStream());
        objectOut.writeObject(myPublicKey);
        objectOut.reset();
        objectOut.flush();
        objectOut.writeObject(me);
        objectOut.reset();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
}