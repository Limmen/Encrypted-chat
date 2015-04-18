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
                String user = (String) objectIn.readObject();
                String ip = (String) objectIn.readObject();
                client = new ChatRoomEntry(user,ip);
                server.chatroomEntrys.add(client);
                updateUsers();
                while(true)
                {
                    String input = (String) objectIn.readObject();
                    if(input == null)
                    {
                        //Client left the chat.
                        server.getHandlers().remove(this);
                        server.chatroomEntrys.remove(client);
                        updateUsers();
                        socket.close();
                        return;
                    }
                    if(input.equalsIgnoreCase("112 114 105 118 097 116 101 032 099 104 097 116"))
                    {
                        //Start wait frame for setting up private chat
                        String received = (String) objectIn.readObject();
                        String[] someentry = received.split(" ", 2);
                        String from = someentry[0];
                        String to = someentry[1];
                        for(ClientHandler ch : server.getHandlers())
                        {
                            if(ch.client.username.equalsIgnoreCase(to))
                            {
                                ch.printToClient("112 114 105 118 097 116 101 032 099 104 097 116");
                                sleep(100);
                                ch.printToClient(from);
                                continue;
                            }
                        }
                        continue;
                    }
                    if(input.equalsIgnoreCase("101 110 099 114 121 112 116 101 100"))
                    {
                        String crypto = (String) objectIn.readObject();
                        for(ClientHandler ch : server.getHandlers())
                        {
                            if(ch != this)
                            {
                                ch.printToClient(crypto);
                                sleep(100);
                            }
                        }
                        continue;
                    }
                    if(input.equalsIgnoreCase("097 099 099 101 112 116 101 100"))
                    {
                        ServerSocket privateServer = new ServerSocket(0);
                        String port = Integer.toString(privateServer.getLocalPort());
                        String from = (String) objectIn.readObject();
                        printToClient("115 101 116 116 105 110 103 032 117 112 032 099 104 097 116");
                        printToClient(port);
                        for(ClientHandler ch : server.getHandlers())
                        {
                            if(ch.client.username.equalsIgnoreCase(from))
                            {
                                ch.printToClient("097 099 099 101 112 116 101 100");
                                ch.printToClient(port);
                                
                            }
                        }
                        PrivateHandler one = new PrivateHandler(privateServer.accept(), server, 1);
                        PrivateHandler two = new PrivateHandler(privateServer.accept(), server, 1);
                        one.otherhandler = two;
                        two.otherhandler = one;
                        sleep(2000);
                        one.requestKey();
                        privateServer.close();
                        continue;
                        
                    }
                    for(ClientHandler ch : server.getHandlers())
                    {
                        ch.printToClient(input);
                        sleep(100);
                    }
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
                        ch.printToClient("117 115 101 114 110 097 109 101"); //ascii for username
                        ch.printToClient(server.chatroomEntrys);
                        sleep(100);
                    }
            }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    
        }
    }