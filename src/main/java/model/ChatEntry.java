package model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *This class represents a entry in the chat.
 * @author kim
 */
public class ChatEntry implements Serializable
{
    private String author;
    public String msg;
    public boolean encrypted = true;
    BigInteger sign;
    public boolean verified;
    public ChatEntry(String author, String msg)
    {
        this.author = author + ":";
        this.msg = msg;
        this.verified = false;
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
    public void sign(RSA key)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(msg.getBytes());
            byte byteData[] = md.digest();
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) 
            {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            sign = key.sign(key.createmessage(sb.toString()));
        }
        catch(Exception e)
        {
                    
        }
        
       
    }
}
