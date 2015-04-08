/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
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
    JLabel txt;
    public EntryPanel(ChatFrame cf, ChatEntry entry)
    {
        this.cf = cf;
        
        setLayout(new MigLayout("wrap 3"));
        JButton decrypt = new JButton("Decrypt");
        decrypt.setFont(smallPlain);
        decrypt.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                    String decrypted = decrypt();
                    if(decrypted != null)
                    txt.setText(decrypted);
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
    public String decrypt()
    {
        String decrypted = cf.decrypt(txt.getText());
        return decrypted;
    }

}
