package model;

import java.io.Serializable;

/**
 *This class represents a certificate in this chatpplication.
 * The certificate binds a public key to a username and the 
 * Certificate Authority (CA) is the local chatserver.
 * @author kim
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
