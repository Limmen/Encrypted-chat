/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;

/**
 *
 * @author Kim
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
