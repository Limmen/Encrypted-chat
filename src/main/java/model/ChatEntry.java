/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
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
