package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import view.ChatFrame;

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
    public PrintWriter out;
    private BufferedReader in;
    private ObjectInputStream objectIn;
    Chat chat;
    ChatFrame cf;
    ArrayList<ChatEntry> chatentrys = new ArrayList();
    
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
                
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                objectIn = new ObjectInputStream(socket.getInputStream());
                RSA key = (RSA)objectIn.readObject();
                cf.key = key;
                System.out.println(username + "received rsa key");
                sleep(100);
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
                cf.lostConnection();
                kill();
            }
            if(inputs.equals("117 115 101 114 110 097 109 101"))
            {
                System.out.println(username + "received ascii for username");
                Object o = objectIn.readObject();
                ArrayList<ChatRoomEntry> users = (ArrayList<ChatRoomEntry>) o;
               // ArrayList<ChatRoomEntry> users = (ArrayList<ChatRoomEntry>)objectIn.readObject();
                cf.updateUsers(users);
                users = null;
                continue;
            }
            chat.updateChat(inputs, this);
            
        }
            }
        catch(Exception e)
        {
            cf.lostConnection();
            kill();
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
    
}
