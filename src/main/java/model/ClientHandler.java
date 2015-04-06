/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
        Socket socket;    
        private BufferedReader in;
        private PrintWriter out;
        private Server  server;
        private int nr;
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
                server.users.add(out);
                while(true)
                {
                    String input = in.readLine();
                    if(input == null)
                    {
                        System.out.println("Clienthandler Input is null");
                        server.getHandlers().remove(this);
                        return;
                    }
                    System.out.println("Number of handlers/users in the chat: " + server.getHandlers().size());
                    for(ClientHandler ch : server.getHandlers())
                    {
                        System.out.println("sleep");
                        ch.printToClient(input);
                        sleep(1000);
                        System.out.println("wakeup");
                    }
                }
            }
                catch(Exception e)
                {
                        System.out.println("Exception!");
                }
        }
        public void printToClient(String msg)
        {
            System.out.println("Printing to client, clienthandler nr is: " + nr);
            out.println(msg);
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
    }