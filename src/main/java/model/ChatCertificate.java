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
public class ChatCertificate implements Serializable
{
    RSAPublicKey publicKey;
    String username;

    public ChatCertificate(RSAPublicKey publicKey, String username)
    {
        this.publicKey = publicKey;
        this.username = username;
    }
    
}
