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
import model.Chat;
import model.Client;
import model.PrivateClient;
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
    public RequestFrame(String name, Client client)
    {
        super("Private chat request from " + name);
        this.requestFrom = name;
        this.client = client;
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
        txt = new JLabel(requestFrom);
        txt.setFont(PBold);
        panel.add(txt, "span 1, align center");
        txt = new JLabel(" have invited you to a private and secure chat");
        txt.setFont(Plain);
        panel.add(txt,"span 1, align center");
        container.add(panel, "span, align center");
        panel = new JPanel(new MigLayout("wrap 2"));
        yes = new JButton("Accept");
        yes.setFont(PBold);
        yes.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                   privateChat(requestFrom);
                   dispose();
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
    public void privateChat(String from)
    {
        try
        {
        WaitFrame wf = new WaitFrame("Setting up private chat");
        client.objectOut.writeObject("097 099 099 101 112 116 101 100");
        client.objectOut.reset();
        sleep(100);
        client.wf = wf;
        client.objectOut.writeObject(from);
        client.objectOut.reset();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}