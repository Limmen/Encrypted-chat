package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
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
        private boolean sent;
        private RSAPublicKey clientKey;
        private RSAPublicKey friendKey;
        public PrivateHandler(Socket socket, Server  server, int nr)
        {
            this.socket = socket;
            this.server = server;
            this.sent = false;
            this.nr = nr;
            start();
        }
        public void run() {
            try 
            {
                objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectIn = new ObjectInputStream(socket.getInputStream());
                String user = (String) objectIn.readObject();
                String ip = (String) objectIn.readObject();
                client = new ChatRoomEntry(user,ip);
                System.out.println(client.username + "handler read ip and username");
                chatRoomEntrys.add(client);
                updateUsers();
                while(true)
                {
                    String input = (String)objectIn.readObject();
                    if(input == null)
                    {
                        //Client left the chat.
                        server.getHandlers().remove(this);
                        server.chatroomEntrys.remove(client);
                        updateUsers();
                        socket.close();
                        return;
                    }
                    if(input.equalsIgnoreCase("105 110 099 111 109 109 105 110 103 032 107 101 121"))
                    {
                        RSAPublicKey k = (RSAPublicKey) objectIn.readObject();
                        otherhandler.printToClient("105 110 099 111 109 109 105 110 103 032 107 101 121");
                        sleep(100);
                        otherhandler.printToClient(k);
                        continue;
                    }
                    if(input.equalsIgnoreCase("082 083 065")) //"RSA"
                    {
                        otherhandler.printToClient("082 083 065");
                        continue;
                    }
                        printToClient(input);
                        sleep(100);
                        otherhandler.printToClient(input);
                }
            }
                catch(Exception e)
                {
                        e.printStackTrace();
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
                    chatRoomEntrys.add(otherhandler.client);
                    if(otherhandler.chatRoomEntrys.size() < 2)
                    {
                        otherhandler.chatRoomEntrys.add(client);
                    }
                    otherhandler.printToClient("117 115 101 114 110 097 109 101");
                    otherhandler.printToClient(otherhandler.chatRoomEntrys);
                    otherhandler.sleep(100);
                    sleep(100);
                }
                printToClient("117 115 101 114 110 097 109 101"); //ascii for username
                printToClient(chatRoomEntrys);
                otherhandler.sleep(100);
                sleep(100);
                
            }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    
        }
    }