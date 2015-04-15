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
    public PrintWriter out;
    private BufferedReader in;
    private ObjectInputStream objectIn;
    Chat chat;
    private RSA key;
    public RSAPublicKey myPublicKey;
    public RSAPublicKey friendPublicKey;
    PrivateChatFrame pc;
    private ObjectOutputStream objectOut;
    ArrayList<ChatEntry> chatentrys = new ArrayList();
    
    public PrivateClient(String ip, int port, String username, Chat chat)
    {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.chat = chat;
        this.key = new RSA();
        myPublicKey = key.publicKey;
        try
        {
        socket = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
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
                
                //RSA key = (RSA)objectIn.readObject();
                //pc.key = key;
                //System.out.println(username + "received rsa key");
                //sleep(100);
                out.println(username);
                sleep(50);
                out.println(ip);
                System.out.println(username + "printed IP and username");
        while (true)
        {
            String inputs  = in.readLine();
            if(inputs == null)
            {
                //Connection to Server was lost
                System.out.println("Connection was lost , client here");
                pc.lostConnection();
                kill();
            }
            if(inputs.equals("117 115 101 114 110 097 109 101"))
            {
                System.out.println(username + "received ascii for username");
                Object o = objectIn.readObject();
                ArrayList<ChatRoomEntry> users = (ArrayList<ChatRoomEntry>) o;
               // ArrayList<ChatRoomEntry> users = (ArrayList<ChatRoomEntry>)objectIn.readObject();
                pc.updateUsers(users);
                users = null;
                continue;
            }
             if(inputs.equalsIgnoreCase("112 114 105 118 097 116 101 032 099 104 097 116"))
             {
                 String port = in.readLine();
                 System.out.println("Client Received port: " + port);
             }
            chat.updateChat(inputs, this);
            
        }
            }
        catch(Exception e)
        {
            pc.lostConnection();
            kill();
        }
    }
    public void sendPublicKey()
    {
        try
        {
            System.out.println("time to send key");
            out.println("082 083 065");
            objectOut.flush();
            objectOut.reset();
            objectOut.writeObject(myPublicKey);
            System.out.println("key sent");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void receivePublicKey()
    {
        try
        {
            System.out.println("TIme to receive key");
            RSAPublicKey k = (RSAPublicKey) objectIn.readObject();
            this.friendPublicKey = k;
            System.out.println("Received key");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void close(Socket socket)
    {
        out.close();
        try
        {
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
            out.close();
            in.close();
            socket.close();
            interrupt();
            stop();
        }
        catch(Exception e)
        {
            
        }
    }
    public void requestChat(String username)
    {
        out.println("112 114 105 118 097 116 101 032 099 104 097 116"); //ascii for "private chat"
        out.println(username);
    }
    
}