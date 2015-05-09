package model;

import java.io.Serializable;

/**
 *This class is used when setting up a private chat. The class contains 
 * information that's needed to setup a chat: port and certificate of the
 * other user.
 * @author kim
 */
public class PrivateChatInfo implements Serializable
{
    
    public String port;
    public ChatCertificate certificate;
    
    public PrivateChatInfo(String port, ChatCertificate certificate)
    {
        this.port = port;
        this.certificate = certificate;
    }
}
