package model;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *This class represents a RSA public key.
 * @author kim
 */
public class RSAPublicKey implements Serializable
{
    private static final long serialVersionUID = 2L;
    public BigInteger e;
    public BigInteger n;
    
    public RSAPublicKey(BigInteger e, BigInteger n)
    {
        this.e = e;
        this.n = n;
    }
    public BigInteger encrypt(BigInteger message) 
   {
      return message.modPow(e, n);
   }
   public BigInteger createmessage(String input)
   {
       BigInteger message = new BigInteger(input.getBytes());
       return message;
   }
   public BigInteger verify(BigInteger sign)
   {
       return sign.modPow(e, n);
   }
}
