package model;

import java.io.Serializable;

/**
 *This is a object that a user replys with if he/she accepts the chat invite.
 * @author kim
 */
public class AcceptedInvite implements Serializable
{
    public ChatInvite invite;
    
    public AcceptedInvite(ChatInvite invite)
    {
        this.invite = invite;
    }
}
