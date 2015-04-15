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
        private BufferedReader in;
        private PrintWriter out;
        private ObjectOutputStream objectOut;
        private Server  server;
        public PrivateHandler otherhandler;
        private int nr;
        private ChatRoomEntry client;
        private ArrayList<ChatRoomEntry> chatRoomEntrys = new ArrayList();
        private ObjectInputStream objectIn;
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
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectIn = new ObjectInputStream(socket.getInputStream());
                //sleep(250);
                //objectOut.writeObject(server.key);
                exchangeKeys();
                
                String user = in.readLine();
                String ip = in.readLine();
                client = new ChatRoomEntry(user,ip);
                System.out.println(client.username + "handler read ip and username");
                chatRoomEntrys.add(client);
                if(otherhandler.client != null)
                chatRoomEntrys.add(otherhandler.client);
                sleep(500);
                updateUsers();
                while(true)
                {
                    if(chatRoomEntrys.size()<2 && otherhandler.client != null)
                    {
                        chatRoomEntrys.add(otherhandler.client);
                    }
                    String input = in.readLine();
                    if(input == null)
                    {
                        //Client left the chat.
                        server.getHandlers().remove(this);
                        server.chatroomEntrys.remove(client);
                        updateUsers();
                        socket.close();
                        return;
                    }
                        printToClient(input);
                        sleep(1000);
                        otherhandler.printToClient(input);
                        sleep(500);
                }
            }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
        public void exchangeKeys()
        {
            try
            {
            String input = in.readLine();
            if(input.equalsIgnoreCase("082 083 065")) //"RSA"
                    {
                        System.out.println("Received ascii for RSA");
                        RSAPublicKey k = (RSAPublicKey) objectIn.readObject();
                        otherhandler.printToClient(k);
                    }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
        public void printToClient(String msg)
        {
            out.println(msg);
        }
        public void printToClient(Object o)
        {
            System.out.println("Object to print is: "  + o);
            try
            {
                objectOut.flush();
                objectOut.reset();
                objectOut.writeObject(o);
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
            System.out.println(client.username + "is updating the users");
            try
            {
                printToClient("117 115 101 114 110 097 109 101"); //ascii for username
                System.out.println("size: " + chatRoomEntrys.size());
                printToClient(chatRoomEntrys);
            }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    
        }
    }