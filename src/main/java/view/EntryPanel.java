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

    private Font Italic = new Font("Serif", Font.ITALIC, 12);
    private Font smallItalic = new Font("Serif", Font.ITALIC, 10);
    private Font Bold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
    ChatFrame cf;
    JLabel txt;
    public EntryPanel(ChatFrame cf, ChatEntry entry)
    {
        this.cf = cf;
        
        setLayout(new MigLayout("wrap 3"));
        JButton decrypt = new JButton("Decrypt");
        decrypt.setFont(smallItalic);
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
        txt.setFont(Bold);
        add(txt, "span 1, gap 20");
        txt = new JLabel(entry.getMsg());
        add(txt,"span 1");
        setBackground(Color.WHITE); 
    }
    public String decrypt()
    {
        String decrypted = cf.decrypt(txt.getText());
        return decrypted;
    }

}
