package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import model.Client;
import net.miginfocom.swing.MigLayout;

/**
 *This class is a frame for connecting to a running server and start a chat.
 * @author kim
 */
public class ConnectFrame extends JFrame
{
    private View view;
    JPanel container;
    JLabel txt;
    JButton btn;
    JTextField ip;
    JTextField port;
    JTextField username;
    private Font Italic = new Font("Serif", Font.ITALIC, 12);
    private Font Plain = new Font("Serif", Font.PLAIN, 12);
    private Font IBold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
    private Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    public ConnectFrame(View view)
    {
        super("Connect");
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
        this.view = view;
        this.setLayout(new MigLayout());
        startup();
        
        pack();
        setLocationRelativeTo(null);    // centers on screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void startup()
    {
        container = new JPanel(new MigLayout("wrap 2"));
        txt = new JLabel("Create a connection to a running server");
        txt.setFont(Plain);
        container.add(txt, "span 2, align center");
        txt = new JLabel("Localhost running on port: " + view.getServerPort());
        txt.setFont(Plain);
        container.add(txt, "span 2, align center");
        txt = new JLabel("IP adress ");
        txt.setFont(Plain);
        container.add(txt, "span 1");
        ip = new JTextField(30);
        ip.setFont(Plain);
        container.add(ip, "span 1");
        txt = new JLabel("Port");
        txt.setFont(Plain);
        container.add(txt, "span 1");
        port = new JTextField(30);
        port.setFont(Plain);
        container.add(port, "span 1");
        txt = new JLabel("Chat username");
        txt.setFont(Plain);
        container.add(txt, "span 1");
        username = new JTextField(30);
        username.setFont(Plain);
        container.add(username, "span 1");
        btn = new JButton("Connect");
        btn.setFont(PBold);
        btn.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                    String adress = ip.getText();
                    int portnr = Integer.parseInt(port.getText());
                    String user = username.getText();
                    Pattern pattern = Pattern.compile("\\s");
                    Matcher matcher = pattern.matcher(user);
                    boolean found = matcher.find();
                    if(found)
                    {
                        username.setText("Username can not contain whitespaces");
                        return;
                    }
                    Client client = view.newChat(adress, portnr, user);
                    if(client == null)
                    {
                        username.setText("Username already taken in that chatroom.");
                        return;
                    }
                    ip.setText("");
                    port.setText("");
                    username.setText("");
                    pack();
	        }
	});
        container.add(btn, "span 2");
        txt = new JLabel("Copyright \u00a9, Kim Hammar all rights reserved");
        txt.setFont(Plain);
        container.add(txt, "span 2, gaptop 20");
        add(container);
    }
    public void location(JFrame f)
    {
        setLocation(f.getX() - (getWidth() - f.getWidth())/2, f.getY() + f.getHeight() + f.getHeight()/6);
        pack();
    }
}

