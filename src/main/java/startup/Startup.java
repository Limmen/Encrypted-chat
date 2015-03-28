/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startup;

import controller.Controller;
import view.View;

/**
 *
 * @author kim
 */
public class Startup 
{
    public static void main(String[] args)
    {
        Controller contr = new Controller();
        View view = new View(contr);
    }
}
