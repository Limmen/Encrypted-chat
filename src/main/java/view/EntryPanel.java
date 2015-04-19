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
 *This class represents the panel which holds the chatentrys.
 * @author kim
 */

public class EntryPanel extends JPanel
{

    private Font smallItalic = new Font("Serif", Font.ITALIC, 10);
    private Font smallPlain = new Font("Serif", Font.PLAIN, 10);
    private Font Italic = new Font("Serif", Font.ITALIC, 12);
    private Font Plain = new Font("Serif", Font.PLAIN, 12);
    private Font IBold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
    private Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    ChatFrame cf;
    PrivateChatFrame pc;
    JLabel txt;
    ChatEntry entry;
    public EntryPanel(ChatFrame cf, ChatEntry entry)
    {
        this.cf = cf;
        this.entry = entry;
        if(entry.encrypted)
        {
            setLayout(new MigLayout("wrap 3"));
            
            JButton decrypt = new JButton("Decrypt");
            decrypt.setFont(smallPlain);
            decrypt.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent arg0) 
                {   
                    String decrypted = decrypt();
                    if(decrypted != null)
                    {
                        txt.setText(decrypted);
                        clearPanel(decrypted);
                    }
	        }
            });
            add(decrypt, "span 1");
            txt = new JLabel(entry.getAuthor());
            txt.setFont(PBold);
            add(txt, "span 1, gap 20");
            txt = new JLabel(entry.getMsg());
            txt.setFont(Plain);
            add(txt,"span 1");
            setBackground(Color.WHITE);
        }
        else
        {
            setLayout(new MigLayout("wrap 2"));
            txt = new JLabel(entry.getAuthor());
            txt.setFont(PBold);
            add(txt, "span 1");
            txt = new JLabel(entry.getMsg());
            txt.setFont(Plain);
            add(txt,"span 1");
            setBackground(Color.WHITE);
        }
    }
    public EntryPanel(PrivateChatFrame pc, ChatEntry entry)
    {
        this.pc = pc;
        this.entry = entry;
        if(entry.encrypted)
        {
            setLayout(new MigLayout("wrap 3"));
            JButton decrypt = new JButton("Decrypt");
            decrypt.setFont(smallPlain);
            decrypt.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent arg0) 
                {   
                    String decrypted = privateDecrypt();
                    if(decrypted != null)
                    {
                        txt.setText(decrypted);
                        clearPanel(decrypted);
                    }
	        }
            });
            add(decrypt, "span 1");
            txt = new JLabel(entry.getAuthor());
            txt.setFont(PBold);
            add(txt, "span 1, gap 20");
            txt = new JLabel(entry.getMsg());
            txt.setFont(Plain);
            add(txt,"span 1");
            setBackground(Color.WHITE); 
        }
        else
        {
            setLayout(new MigLayout("wrap 2"));
            txt = new JLabel(entry.getAuthor());
            txt.setFont(PBold);
            add(txt, "span 1");
            txt = new JLabel(entry.getMsg());
            txt.setFont(Plain);
            add(txt,"span 1");
            setBackground(Color.WHITE); 
        }
    }
    public void clearPanel(String text)
    {
        entry.encrypted = false;
        entry.msg = text;
        removeAll();
        txt = new JLabel(entry.getAuthor());
        txt.setFont(PBold);
        add(txt, "span 1");
        txt = new JLabel(text);
        txt.setFont(Plain);
        add(txt,"span 1");
        setBackground(Color.WHITE); 
    }
    public String decrypt()
    {
        String decrypted = cf.decrypt(txt.getText());
        return decrypted;
    }
    public String privateDecrypt()
    {
        String decrypted = pc.decrypt(txt.getText());
        return decrypted;
    }

}
