package model;

import java.io.Serializable;

/**
 *This is a object that a user sends to invite a other user to a private chat.
 * @author kim
 */
public class ChatInvite implements Serializable
{
    public String to;
    public String from;
    
    public ChatInvite(String to, String from)
    {
        this.to = to;
        this.from = from;
    }
}
