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
import model.Chat;
import model.ChatEntry;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class ChatFrame extends JFrame
{
    private View view;
    private Chat chat;
    JPanel container;
    JLabel txt;
    JButton btn;
    JTextArea entry;
    JList chatlist;
    JScrollPane scroll;
    DefaultListModel <ChatEntry> model;
    JPanel chatPanel;
    ChatRenderer rend = new ChatRenderer();
    Thread thread;
    private Font Italic = new Font("Serif", Font.ITALIC, 12);
    private Font Bold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
    public ChatFrame(View view, Chat chat)
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
        this.chat = chat;
        chat.setFrame(this);
        this.setLayout(new MigLayout());
        startup();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) 
            {
                cleanUp();
            }
        });
        pack();
        //setLocationRelativeTo(null);    // centers on screen
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
        chatlist.setModel(listModel(chat.getChat()));
        scroll = new JScrollPane(chatlist);
        scroll.setPreferredSize(new Dimension(300, 200));
        chatPanel.add(scroll, "span");
        container.add(chatPanel, "span 2, align center");
        txt = new JLabel(chat.getUsername()+":");
        txt.setFont(Bold);
        container.add(txt, "span 1");
        entry = new JTextArea(5,20);
        entry.setFont(Bold);
        container.add(entry, "span 1");
        btn = new JButton("Send");
        btn.setFont(Bold);
        btn.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                    
                    chat.client.out.println(chat.getUsername() + " " + entry.getText());
                    entry.setText("");
                    pack();
	        }
	});
        container.add(btn, "span 1, align center");
        btn = new JButton("Encrypt");
        btn.setFont(Bold);
        btn.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                    //entry.setText(view.encrypt(entry.getText()));
                    pack();
	        }
	});
        container.add(btn, "span 1, align center");
        add(container);
        thread = new Thread(new Runnable() {
            @Override public void run() {
                while(true)
                {
                updateChat(chat.getChat());
                try
            {
            thread.sleep(10000);
            }
            catch(Exception e)
            {
                
            }
                }
            }
        });
        thread.start(); 
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
        pack();
    }
    public void updateChat(String msg)
    {
        String[] someentry = msg.split(" ", 2);
        String user = someentry[0];
        String text = someentry[1];
        chat.newEntry(user, text);
        updateChat(chat.getChat());
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
    public void location(JFrame f)
    {
        setLocation(f.getX() + f.getWidth() + f.getWidth()/14, f.getY());
        pack();
    }
    public void cleanUp()
    {
        chat.cleanUp();
    }
}