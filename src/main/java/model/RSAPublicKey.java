/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author kim
 */
public class RSAPublicKey implements Serializable
{
    private static final long serialVersionUID = 2L;
    public BigInteger d;
    public BigInteger n;
    
    public RSAPublicKey(BigInteger d, BigInteger n)
    {
        this.d = d;
        this.n = n;
    }
}
