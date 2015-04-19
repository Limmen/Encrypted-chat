package model;

import java.io.Serializable;

/**
 *This class represents a entry in the chat.
 * @author kim
 */
public class ChatEntry implements Serializable
{
    private String author;
    public String msg;
    public boolean encrypted = true;
    
    public ChatEntry(String author, String msg)
    {
        this.author = author + ":";
        this.msg = msg;
    }
    public ChatEntry(String author, String msg, boolean encrypted)
    {
        this.author = author + ":";
        this.msg = msg;
        this.encrypted = encrypted;
    }
    public String getAuthor()
    {
        return author;
    }
    public String getMsg()
    {
        return msg;
    }
    public String toString()
    {
        return msg;
    }
}
