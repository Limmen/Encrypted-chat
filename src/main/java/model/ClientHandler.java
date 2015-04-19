package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *This class represents the server-side of a connection with a user in the chat.
 * @author kim
 */
public class ClientHandler extends Thread{
        Socket socket;    
        private ObjectOutputStream objectOut;
        private ObjectInputStream objectIn;
        private Server  server;
        private int nr;
        private ChatRoomEntry client;
        public ClientHandler(Socket socket, Server  server, int nr)
        {
            this.socket = socket;
            this.server = server;
            this.nr = nr;
            start();
        }
        public void run() {
            try 
            {
                objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectIn = new ObjectInputStream(socket.getInputStream());
                //server.users.add(out);
                sleep(100);
                objectOut.writeObject(server.key);
                objectOut.reset();
                while(true)
                {
                    Object o =  objectIn.readObject();
                    if(o == null)
                    {
                        //Client left the chat.
                        userLeft();
                        socket.close();
                        return;
                    }
                    if(o instanceof ChatRoomEntry)
                    {
                        this.client = (ChatRoomEntry) o;
                        server.chatroomEntrys.add(client);
                        updateUsers();
                    }
                    if(o instanceof ChatInvite)
                    {
                        ChatInvite invite = (ChatInvite) o;
                        for(ClientHandler ch : server.getHandlers())
                        {
                            if(ch.client.username.equalsIgnoreCase(invite.to))
                            {
                                ch.printToClient(invite);
                                continue;
                            }
                        }
                    }
                    if(o instanceof AcceptedInvite)
                    {
                        AcceptedInvite acc = (AcceptedInvite) o;
                        ServerSocket privateServer = new ServerSocket(0);
                        String port = Integer.toString(privateServer.getLocalPort());
                        PrivateChatInfo info = new PrivateChatInfo(port);
                        
                        printToClient(info);
                        for(ClientHandler ch : server.getHandlers())
                        {
                            if(ch.client.username.equalsIgnoreCase(acc.invite.from))
                            {
                                ch.printToClient(info);  
                            }
                        }
                        PrivateHandler one = new PrivateHandler(privateServer.accept(), server, 1);
                        PrivateHandler two = new PrivateHandler(privateServer.accept(), server, 1);
                        one.setHandler(two);
                        two.setHandler(one);
                        privateServer.close();
                        continue;
                    }
                    if(o instanceof ChatEntry)
                    {
                        for(ClientHandler ch : server.getHandlers())
                        {
                            if(ch != this)
                            ch.printToClient(o);
                            sleep(100);
                        }
                    }
                }
            }
                catch(Exception e)
                {
                        userLeft();
                        try
                        {
                            socket.close();
                        }
                        catch(Exception g)
                        {
                            
                        }
                        return;
                }
        }
        public void printToClient(String msg)
        {
            try
            {
                objectOut.writeObject(msg);
                objectOut.reset();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
        public void printToClient(Object o)
        {
            try
            {
                objectOut.flush();
                objectOut.writeObject(o);
                objectOut.reset();
            }
            catch(Exception e)
            {
                
            }
        }
        public void kill()
        {
            try
            {
                interrupt();
            }
            catch(Exception e)
            {
                    
            }
        }
        public void updateUsers()
        {
            try
            {
            for(ClientHandler ch : server.getHandlers())
                    {
                        ch.printToClient(server.chatroomEntrys);
                        sleep(100);
                    }
            }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    
        }
        public void userLeft()
        {
            server.getHandlers().remove(this);
            server.chatroomEntrys.remove(client);
            updateUsers();
            ChatEntry ce = new ChatEntry("Server", "<html> <font color=blue> " + client.username + "</font>" + " left the chat </html>", false);
            for(ClientHandler ch : server.getHandlers())
            {
                if(ch != this)
                    ch.printToClient(ce);
            }
            
        }
    }