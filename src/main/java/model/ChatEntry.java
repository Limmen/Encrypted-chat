package model;
/**
 *This class represents a entry in the chat.
 * @author kim
 */
public class ChatEntry 
{
    private String author;
    private String msg;;
    
    public ChatEntry(String author, String msg)
    {
        this.author = author + ":";
        this.msg = msg;
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
