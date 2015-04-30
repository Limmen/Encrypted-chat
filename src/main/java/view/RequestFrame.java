package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import static java.lang.Thread.sleep;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import model.AcceptedInvite;
import model.ChatInvite;
import model.Client;
import net.miginfocom.swing.MigLayout;
import util.ImageReader;

/**
 *This class is a frame for connecting to a running server and start a chat.
 * @author kim
 */
public class RequestFrame extends JFrame
{
    JPanel container;
    JLabel txt;
    JButton yes;
    JButton no;
    private Font Italic = new Font("Serif", Font.ITALIC, 12);
    private Font Plain = new Font("Serif", Font.PLAIN, 12);
    private Font IBold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
    private Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    BufferedImage image;
    ImageReader reader;
    private String requestFrom;
    private Client client;
    ChatInvite invite;
    public RequestFrame(ChatInvite invite, Client client)
    {
        super("Private chat request from " + invite.from);
        this.requestFrom = invite.from;
        this.client = client;
        this.invite = invite;
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
        reader = new ImageReader();
        startup();
        
        pack();
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);
    }
    public void startup()
    {
        container = new JPanel(new MigLayout("wrap 1"));
        JPanel panel = new JPanel(new MigLayout("wrap 2"));
        txt = new JLabel("<html> <body> <b>" + requestFrom + "</b> have invited you to a private and secure chat. <br>"
                + " By accepting this invite you agree to send your public key to <b> " + requestFrom + "</b> for encryption </body> </html>");
        txt.setFont(Plain);
        panel.add(txt,"span 2, align center");
        container.add(panel, "span, align center");
        panel = new JPanel(new MigLayout("wrap 2"));
        yes = new JButton("Accept");
        yes.setFont(PBold);
        yes.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                   privateChat();
                   //dispose();
	        }
	});
        panel.add(yes, "span 1, align center");
        no = new JButton("Decline");
        no.setFont(PBold);
        no.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                   dispose();
	        }
	});
        panel.add(no, "span 1, align center");
        container.add(panel, "span, align center");
        image = reader.readImage("encrypt2.png");
        JLabel wait = new JLabel(new ImageIcon(image));
        container.add(wait, "span, align center");
        add(container, BorderLayout.CENTER);
    }
    public void location(JFrame f)
    {
        setLocation(f.getX() - (getWidth() - f.getWidth())/2, f.getY() + f.getHeight() + f.getHeight()/6);
        pack();
    }
    public void privateChat()
    {
        try
        {
        dispose();
        WaitFrame wf = new WaitFrame("Setting up private chat");
        wf.setText("Please wait while we set up the private chat. Exchanging public RSA keys..");
        client.wf = wf;
        AcceptedInvite acc = new AcceptedInvite(this.invite);
        client.objectOut.writeObject(acc);
        client.objectOut.reset();
        sleep(500);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}