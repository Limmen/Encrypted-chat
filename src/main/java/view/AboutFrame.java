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
public class AboutFrame extends JFrame
{
    JPanel container;
    JLabel txt;
    private Font Italic = new Font("Serif", Font.ITALIC, 12);
    private Font Plain = new Font("Serif", Font.PLAIN, 12);
    private Font IBold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
    private Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    public AboutFrame()
    {
        super("About");
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
        txt = new JLabel("About");
        txt.setFont(PBold);
        container.add(txt, "span 2, align center");
        txt = new JLabel("<html> <body> This is a encrypted chat application that is developed by Kim Hammar. <br> "
                + "The project is part of a assignment in a course on"
                + " Computer Security. <br> "
                + "Once you start the application, a local chatserver will start listening"
                + " on a free port on your network. <br> "
                + "The connect-frame that "
                + "shows up when u start the application is used to join a chatroom "
                + "that's up and running, <br> you can either join a local chatroom or <br>"
                + "a extern one, as long as you know the IP-adress and the port number. <br> <br>"
                + "The chatrooms have RSA encryption enabled, <br>"
                + " however what you as a user should know is that in the public chatrooms you are not using <br> "
                + "your personal RSA-keys for decryption/encryption instead you are using the keys of that particular chatrooms. <br>"
                + "If you want to achieve better security and use your own private key to encrypt you can press <br>"
                + " the button <b> invite-to-private-chat </b> "
                + "that is next to every user in the userlist that u can find on the right in the chatroom. <br>"
                + " Inviting a person to a private chat means that the invited person <br>"
                + "will get a request from you where he can accept or decline the invite. <br>"
                + "If he accepts the invite it means that a exchange between your public <br>"
                + "keys will take place. <br> Once that exchange is done "
                + "you can securely communicate with RSA encryption.</body> </html>");
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