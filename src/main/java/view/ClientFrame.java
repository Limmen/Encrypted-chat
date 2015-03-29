/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import model.ChatEntry;
import model.Client;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class ClientFrame extends JFrame
{
    private View view;
    private Client client;
    JPanel container;
    JLabel txt;
    JButton btn;
    JTextArea entry;
    JList chatlist;
    JScrollPane scroll;
    DefaultListModel <ChatEntry> model;
    JPanel chatPanel;
    ChatRenderer rend = new ChatRenderer();
    private Font Italic = new Font("Serif", Font.ITALIC, 12);
    private Font Bold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
    public ClientFrame(View view, Client client)
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
        this.client = client;
        this.setLayout(new MigLayout());
        startup();
        
        pack();
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);
    }
    public void startup()
    {
        container = new JPanel(new MigLayout("wrap 2"));
        chatPanel = new JPanel(new MigLayout());
        txt = new JLabel("Chat");
        txt.setFont(Bold);
        container.add(txt, "span 2, align center");
        chatlist = new JList();
        chatlist.setCellRenderer(rend);
        chatlist.setModel(listModel(view.getChat()));
        scroll = new JScrollPane(chatlist);
        scroll.setPreferredSize(new Dimension(300, 200));
        chatPanel.add(scroll, "span");
        container.add(chatPanel, "span 2, align center");
        txt = new JLabel(client.getUsername()+":");
        txt.setFont(Bold);
        container.add(txt, "span 1");
        entry = new JTextArea(10,20);
        entry.setFont(Bold);
        container.add(entry, "span 1");
        btn = new JButton("Send");
        btn.setFont(Bold);
        btn.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                    view.newEntry(client.getUsername(), entry.getText());
                    updateChat(view.getChat());
                    pack();
	        }
	});
        container.add(btn, "span 2, align center");
        add(container);
    }
    public void updateChat(ArrayList<ChatEntry> entrys)
    {
        chatlist = new JList();
        chatlist.setCellRenderer(rend);
        chatlist.setModel(listModel(entrys));
        scroll = new JScrollPane(chatlist);
        scroll.setPreferredSize(new Dimension(300, 200));
        chatPanel.removeAll();
        chatPanel.add(scroll, "span");
        entry.setText("");
        pack();
    }
    public DefaultListModel listModel(ArrayList<ChatEntry> entries)
    {
        model = new DefaultListModel();
        for (ChatEntry c : entries)
        {
            model.addElement(c);
        }
        return model;
    }
}
