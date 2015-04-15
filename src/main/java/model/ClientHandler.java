package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import view.WaitFrame;

/**
 *This class represents the server-side of a connection with a user in the chat.
 * @author kim
 */
public class ClientHandler extends Thread{
        Socket socket;    
        private BufferedReader in;
        private PrintWriter out;
        private ObjectOutputStream objectOut;
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
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                objectOut = new ObjectOutputStream(socket.getOutputStream());
                server.users.add(out);
                sleep(250);
                objectOut.writeObject(server.key);
                String user = in.readLine();
                String ip = in.readLine();
                client = new ChatRoomEntry(user,ip);
                System.out.println(client.username + "handler read ip and username");
                server.chatroomEntrys.add(client);
                updateUsers();
                while(true)
                {
                    System.out.println("ClientHandler waiting");
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
                    if(input.equalsIgnoreCase("112 114 105 118 097 116 101 032 099 104 097 116"))
                    {
                        //Start wait frame for setting up private chat
                        String privatech = in.readLine();
                        ServerSocket privateServer = new ServerSocket(0);
                        for(ClientHandler ch : server.getHandlers())
                        {
                            if(ch.client.username.equalsIgnoreCase(privatech))
                            {
                                String port = Integer.toString(privateServer.getLocalPort());
                                ch.printToClient("112 114 105 118 097 116 101 032 099 104 097 116");
                                ch.printToClient(port);
                                sleep(100);
                                printToClient("112 114 105 118 097 116 101 032 099 104 097 116");
                                printToClient(port);
                                sleep(100);
                                ch.printToClient("send");
                                sleep(100);
                                printToClient("receive");
                                PrivateHandler one = new PrivateHandler(privateServer.accept(), server, 1);
                                sleep(100);
                                PrivateHandler two = new PrivateHandler(privateServer.accept(), server, 1);
                                one.otherhandler = two;
                                two.otherhandler = one;
                                privateServer.close();
                                continue;
                            }
                        }
                        continue;
                    }
                    for(ClientHandler ch : server.getHandlers())
                    {
                        ch.printToClient(input);
                        sleep(1000);
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
            for(ClientHandler ch : server.getHandlers())
                    {
                        ch.printToClient("117 115 101 114 110 097 109 101"); //ascii for username
                        System.out.println("size: " + server.chatroomEntrys.size());
                        ch.printToClient(server.chatroomEntrys);
                        sleep(500);
                    }
            }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    
        }
    }