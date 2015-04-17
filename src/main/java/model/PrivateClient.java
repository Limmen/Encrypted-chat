package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.ArrayList;
import view.PrivateChatFrame;

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
    //public PrintWriter out;
    //public BufferedReader in;
    public ObjectInputStream objectIn;
    Chat chat;
    private RSA key;
    public RSAPublicKey myPublicKey;
    public RSAPublicKey friendPublicKey;
    PrivateChatFrame pc;
    public ObjectOutputStream objectOut;
    ArrayList<ChatEntry> chatentrys = new ArrayList();
    
    public PrivateClient(String ip, int port, String username, Chat chat)
    {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.chat = chat;
        this.key = new RSA();
        this.myPublicKey = key.publicKey;
        try
        {
        socket = new Socket(ip, port);
        //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //out = new PrintWriter(socket.getOutputStream(), true);
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        objectIn = new ObjectInputStream(socket.getInputStream());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void setFrame()
    {
        chat.setFrame(this);
        pc.key = this.key;
    }
    @Override
    public void run()
    {
            try
            {
                /*
                out.println("082 083 065");
                sleep(500);
                objectOut.flush();
                objectOut.reset();
                objectOut.writeObject(myPublicKey);
                sleep(1000);
                        */
                //out.println("117 115 101 114 110 097 109 101");
                objectOut.flush();
                objectOut.writeObject(username);
                objectOut.reset();
                objectOut.flush();
                objectOut.writeObject(ip);
                objectOut.reset();
                
        while (true)
        {
            String inputs  = (String) objectIn.readObject();
            if(inputs == null)
            {
                //Connection to Server was lost
                pc.lostConnection();
                kill();
            }
            if(inputs.equals("105 110 099 111 109 109 105 110 103 032 107 101 121"))
            {
                RSAPublicKey k = (RSAPublicKey) objectIn.readObject();
                friendPublicKey = k;
                continue;
            }
            if(inputs.equals("082 083 065"))
            {
                objectOut.writeObject("105 110 099 111 109 109 105 110 103 032 107 101 121");
                sleep(1000);
                objectOut.flush();
                objectOut.writeObject(myPublicKey);
                objectOut.reset();
                continue;
            }
            if(inputs.equals("117 115 101 114 110 097 109 101"))
            {
                sleep(500);
                Object o = objectIn.readObject();
                ArrayList<ChatRoomEntry> users = (ArrayList<ChatRoomEntry>) o;
                pc.updateUsers(users);
                users = null;
                continue;
            }
             if(inputs.equalsIgnoreCase("112 114 105 118 097 116 101 032 099 104 097 116"))
             {
                 String port = (String) objectIn.readObject();
             }
            chat.updateChat(inputs, this);
            
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
    
    public void requestKey()
    {
        try
        {
            objectOut.flush();
            objectOut.writeObject("082 083 065");
            objectOut.reset();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}