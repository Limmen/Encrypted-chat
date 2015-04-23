package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

/**
 *This class is a frame for connecting to a running server and start a chat.
 * @author kim
 */
public class HelpFrame extends JFrame
{
    JPanel container;
    JLabel txt;
    private Font Italic = new Font("Serif", Font.ITALIC, 12);
    private Font Plain = new Font("Serif", Font.PLAIN, 12);
    private Font IBold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
    private Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    public HelpFrame()
    {
        super("Help");
        try 
        {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
        {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        } 
        catch (Exception e) 
        {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        startup();
        
        pack();
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);
    }
    public void startup()
    {
        container = new JPanel(new MigLayout("wrap 2"));
        txt = new JLabel("Help");
        txt.setFont(PBold);
        container.add(txt, "span 2, align center");
        txt = new JLabel("<html> <body> If you have problems with the usage of the application please check the following: <br> <br>"
                + "- make sure you have a stable internet connection <br>"
                + "- make sure you have free ports avaiable <br>"
                + "- make sure you are entering the correct IP-adress and port number of the chatroom your joining. <br> <br>"
                + "Any feedback or questions is welcomed warmly! please contact me on: kimham@kth.se </body> </html>");
        txt.setFont(Plain);
        container.add(txt, "span 2, align center");
        txt = new JLabel("Copyright \u00a9 Kim Hammar all rights reserved");
        txt.setFont(Plain);
        container.add(txt, "span 2, gaptop 20");
        add(container, BorderLayout.CENTER);
    }
    public void location(JFrame f)
    {
        setLocation(f.getX() - (getWidth() - f.getWidth())/2, f.getY() + f.getHeight() + f.getHeight()/6);
        pack();
    }
}