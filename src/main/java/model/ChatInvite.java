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
