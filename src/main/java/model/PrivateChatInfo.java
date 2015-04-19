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
public class PrivateChatInfo implements Serializable
{
    
    public String port;
    
    public PrivateChatInfo(String port)
    {
        this.port = port;
    }
}
