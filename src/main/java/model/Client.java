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
    
    public Client(String ip, int port, String username, Chat chat)
    {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.chat = chat;
    }
    @Override
    public void run()
    {
            try
            {
                socket = new Socket(ip, port);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                objectIn = new ObjectInputStream(socket.getInputStream());
                RSA key = (RSA)objectIn.readObject();
                cf.key = key;
                sleep(100);
                out.println(username);
                sleep(50);
                out.println(ip);
        while (true)
        {
            String inputs  = in.readLine();
            if(inputs == null)
            {
                //Connection to Server was lost
                cf.lostConnection();
                kill();
            }
            if(inputs.equals("117 115 101 114 110 097 109 101"))
            {
                ArrayList<ChatRoomEntry> users = (ArrayList<ChatRoomEntry>)objectIn.readObject();
                cf.updateUsers(users);
                continue;
            }
            chat.updateChat(inputs, this);
            
        }
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
        }
        catch(Exception e)
        {
            
        }
    }
    
}
