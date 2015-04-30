package model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
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
    ChatFrame cf;
    boolean sent = false;
    
    public PrivateClient(String ip, int port, String username, Chat chat, RSA key, ChatCertificate certificate)
    {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.chat = chat;
        this.key = key;
        this.myPublicKey = key.publicKey;
        this.friendPublicKey = certificate.publicKey;
        this.me = new ChatRoomEntry(username, ip);
        pc = new PrivateChatFrame(chat, this);
        pc.key = this.key;
        pc.setVisible(true);
        pc.cf = cf;
        try
        {
            socket = new Socket(ip, port);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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
            if(o instanceof ArrayList)
            {
                ArrayList<ChatRoomEntry> users = (ArrayList<ChatRoomEntry>) o;
                pc.updateUsers(users);
                continue;
            }
            if(o instanceof ChatEntry)
            {
                ChatEntry ce = (ChatEntry) o;
                if(verify(ce))
                {
                    ce.verified = true;
                }
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
            objectOut.writeObject(me);
            objectOut.reset();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public boolean verify(ChatEntry ce)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(ce.msg.getBytes());
            byte byteData[] = md.digest();
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) 
            {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            BigInteger hash = new BigInteger(sb.toString().getBytes());
            if(friendPublicKey.verify(ce.sign).equals(hash))
            {
                return true;
            }
            else
                return false;
                 
        }
        catch(Exception e)
        {
            
        }
        return false;
    }
    
    
}