package model;

import java.io.Serializable;

/**
 *This class represents a user in the chat
 * @author kim
 */
public class ChatRoomEntry implements Serializable
{
    public String username;
    public String ip;
    
    public ChatRoomEntry(String username, String ip)
    {
        this.username = username;
        this.ip = ip;
    }
}
