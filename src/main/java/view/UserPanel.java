package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.ChatEntry;
import net.miginfocom.swing.MigLayout;

/**
 *This class represents the panel which holds the connected users.
 * @author kim
 */

public class UserPanel extends JPanel
{

    private Font smallItalic = new Font("Serif", Font.ITALIC, 10);
    private Font smallPlain = new Font("Serif", Font.PLAIN, 10);
    private Font Italic = new Font("Serif", Font.ITALIC, 12);
    private Font Plain = new Font("Serif", Font.PLAIN, 12);
    private Font IBold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
    private Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private Font smallBold = smallPlain.deriveFont(smallPlain.getStyle() | Font.BOLD);
    ChatFrame cf;
    PrivateChatFrame pc;
    JLabel txt;
    String username;
    String ip;
    JButton btn;
    public UserPanel(ChatFrame cf, String username, String ip)
    {
        this.cf = cf;
        this.username = username;
        
        setLayout(new MigLayout("wrap 3"));
        txt = new JLabel(username);
        txt.setFont(Plain);
        add(txt, "span 1");
        txt = new JLabel("(" + ip + ")");
        txt.setFont(Plain);
        add(txt, "span 1");
        if(! username.equals(cf.client.username))
        {
            btn = new JButton("Invite to Private Chat");
            btn.setFont(smallBold);
            btn.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent arg0) 
                {   
                    requestChat();
	        }
            });
            add(btn, "span 1");
        }
        setBackground(Color.WHITE); 
    }
    public UserPanel(PrivateChatFrame pc, String username, String ip)
    {
        this.pc = pc;
        this.username = username;
        
        setLayout(new MigLayout("wrap 2"));
        txt = new JLabel(username);
        txt.setFont(Plain);
        add(txt, "span 1");
        txt = new JLabel("(" + ip + ")");
        txt.setFont(Plain);
        add(txt, "span 1");
        setBackground(Color.WHITE); 
    }
    public void requestChat()
    {
        cf.requestChat(username);
    }

}
