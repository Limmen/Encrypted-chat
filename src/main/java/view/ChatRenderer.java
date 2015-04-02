/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import model.ChatEntry;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */

public class ChatRenderer extends JPanel implements ListCellRenderer<ChatEntry>
{

    private Font Italic = new Font("Serif", Font.ITALIC, 12);
    private Font Bold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
    @Override
    public Component getListCellRendererComponent(JList<? extends ChatEntry> list, 
            ChatEntry value, int index, boolean isSelected, boolean cellHasFocus) 
    {
        removeAll();
        setLayout(new MigLayout("wrap 2"));
        JLabel txt = new JLabel(value.getAuthor());
        txt.setFont(Bold);
        add(txt, "span 1");
        txt = new JLabel(value.getMsg());
        add(txt,"span 1");
        return this;
    }
    
}
