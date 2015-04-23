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
    private String to;
    private ChatRoomEntry me;
    
    public Client(String ip, int port, String username, Chat chat) throws Exception
    {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.chat = chat;
        this.me = new ChatRoomEntry(username, ip);
        socket = new Socket(ip, port);
    }
    @Override
    public void run()
    {
            try
            {
                objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectIn = new ObjectInputStream(socket.getInputStream());
                
                objectOut.writeObject(me);
                objectOut.reset();
        while (true)
        {
            Object o  =objectIn.readObject();
            if(o == null)
            {
                //Connection to Server was lost
                cf.lostConnection();
                kill();
            }
            if(o instanceof RSA)
            {
                cf.key = (RSA) o;
                continue;
            }
            if(o instanceof ArrayList)
            {
                ArrayList<ChatRoomEntry> users = (ArrayList<ChatRoomEntry>) o;
                cf.updateUsers(users);
                users = null;
                continue; 
            }
            if(o instanceof ChatInvite)
            {
                ChatInvite invite = (ChatInvite) o;
                new RequestFrame(invite, this);
                continue;
            }
             if(o instanceof PrivateChatInfo)
             {
                 System.out.println("Starting new privateClient: " + username);
                 PrivateChatInfo info = (PrivateChatInfo) o;
                 PrivateClient pc = new PrivateClient(ip, Integer.parseInt(info.port), username, new Chat(ip, Integer.parseInt(info.port)));
                 cf.setState(cf.ICONIFIED);
                 pc.setFrame(cf);
                 wf.setText("Please wait while we set up the private chat. Exchanging public RSA keys..");
                 pc.setWaitFrame(wf);
                 pc.start();
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
        this.to = to;
        this.wf = new WaitFrame("Waiting for " +  to + " to accept private-chat invite");
        wf.setText("<html> <body> Waiting for <b> " +  to + "</b> to accept private-chat invite </body> </html>");
        ChatInvite invite = new ChatInvite(to, from);
        objectOut.writeObject(invite); 
        objectOut.reset();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
