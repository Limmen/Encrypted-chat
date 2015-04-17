/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Kim
 */
public class ImageReader 
{
    public ImageReader()
    {
        
    }
    
    public BufferedImage readImage(String id)
    {
         try
        {
            return ImageIO.read(new File("src/main/resources/images/" + id));
        }
        catch(Exception e)
        {
            System.out.println("Could not read image");
            return null;
        }
    }
    
}
