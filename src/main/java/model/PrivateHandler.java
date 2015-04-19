package model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.ArrayList;

/**
 *This class represents the server-side of a connection with a user in the chat.
 * @author kim
 */
public class PrivateHandler extends Thread{
        Socket socket;    
        private ObjectOutputStream objectOut;
        private Server  server;
        public PrivateHandler otherhandler;
        private int nr;
        private ChatRoomEntry client;
        private ArrayList<ChatRoomEntry> chatRoomEntrys = new ArrayList();
        private ObjectInputStream objectIn;
        private RSAPublicKey clientKey;
        public PrivateHandler(Socket socket, Server  server, int nr)
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
                while(true)
                {
                    Object o = objectIn.readObject();
                    if(o == null)
                    {
                        //Client left the chat.
                        socket.close();
                        otherhandler.userLeft(client);
                        return;
                    }
                    if(o instanceof RSAPublicKey)
                    {
                        if(client != null)
                            System.out.println(client.username);
                        this.clientKey = (RSAPublicKey) o;
                        if(otherhandler != null)
                        {
                            otherhandler.printToClient(o);
                            otherhandler.setHandler(this);
                        }
                        continue;
                    }
                    if(o instanceof ChatRoomEntry)
                    {
                        this.client = (ChatRoomEntry) o;
                        if(chatRoomEntrys.size() < 2)
                        {
                            chatRoomEntrys.add(client);
                            updateUsers();
                        }
                        continue;
                    }
                    if(o instanceof ChatEntry)
                    {
                        otherhandler.printToClient(o);
                    }
                    
                        
                }
            }
                catch(Exception e)
                {
                        //e.printStackTrace();
                    try
                    {
                        socket.close();
                        otherhandler.userLeft(client);
                        return;
                    }
                    catch(Exception g)
                    {
                        
                    }
                }
        }
        public void printToClient(String msg)
        {
            try
            {
                objectOut.flush();
                objectOut.writeObject(msg);
                objectOut.reset();
            }
            catch(Exception e)
            {
                
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
                if(otherhandler != null)
                {
                    if(otherhandler.client != null && chatRoomEntrys.size() < 2)
                    chatRoomEntrys.add(otherhandler.client);
                    if(otherhandler.chatRoomEntrys.size() < 2)
                    {
                        otherhandler.chatRoomEntrys.add(client);
                    }
                    otherhandler.printToClient(otherhandler.chatRoomEntrys);
                    otherhandler.sleep(100);
                    sleep(100);
                }
                printToClient(chatRoomEntrys);
                otherhandler.sleep(100);
                sleep(100);
                
            }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    
        }
        public void setHandler(PrivateHandler p)
        {
            this.otherhandler = p;
            if(clientKey != null)
            {
                otherhandler.printToClient(clientKey);
            }
        }
        public void userLeft(ChatRoomEntry entry)
        {
            chatRoomEntrys.remove(entry);
            printToClient(chatRoomEntrys);
            ChatEntry ce = new ChatEntry("Server", "<html> <font color=blue> " + entry.username + "</font>" + " left the chat </html>", false);
            printToClient(ce);
        }
    }