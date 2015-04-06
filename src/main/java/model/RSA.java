/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigInteger;
import java.security.SecureRandom;
    

public class RSA 
{
   /*BigInteger class provides methods for modular arithmetic,
    prime number generation etc.
    SecureRandom class provides a cryptographically strong random number generator*/
   private final static BigInteger one      = new BigInteger("1");
   private final static SecureRandom random = new SecureRandom();

   private BigInteger d;
   private BigInteger e;
   private BigInteger n;
   private int N;

   // generate an N-bit (roughly) public and private key
   public RSA() {
       this.N = 2048;
      /*returns a positive BigInteger that is probably prime, 
       with the specified bitLength. (bitLength = N/2 */
      BigInteger p = BigInteger.probablePrime(N/2, random);
      BigInteger q = BigInteger.probablePrime(N/2, random);
      //Calculate phi. (1 < e < phi, e and phi needs to be relative prime)
      BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

      n    = p.multiply(q);                                  
      e  = new BigInteger("65537");     // common value in practice = 2^16 + 1
      d = e.modInverse(phi);
   }

   
   public BigInteger encrypt(BigInteger message) 
   {
      return message.modPow(e, n);
   }

   public BigInteger decrypt(BigInteger encrypted) {
      return encrypted.modPow(d, n);
   }

   public String toString() 
   {
      String s = "";
      s += "public  = " + e  + "\n";
      s += "private = " + d + "\n";
      s += "n = " + n;
      return s;
   }
   public BigInteger createmessage(String input)
   {
       BigInteger message = new BigInteger(input.getBytes());
       System.out.println("message to encrypt: " + message.toString());
       return message;
   }
   
}
