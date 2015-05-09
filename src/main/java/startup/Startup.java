package startup;

import controller.Controller;
import view.View;

/**
 *This is the startup class where execution starts.
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
