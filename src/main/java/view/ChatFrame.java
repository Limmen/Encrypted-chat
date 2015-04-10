package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import model.Chat;
import model.ChatEntry;
import model.ChatRoomEntry;
import model.Client;
import model.RSA;
import net.miginfocom.swing.MigLayout;

/**
 *This clas represents a chatframe for a existing connection of a client and a server.
 * @author kim
 */
public class ChatFrame extends JFrame
{
    private View view;
    private Chat chat;
    private Client client;
    JPanel container;
    JLabel txt;
    JButton btn;
    JTextArea entry;
    JScrollPane scroll;
    JScrollPane scroll2;
    JScrollPane usersScroll;
    DefaultListModel <ChatEntry> model;
    JPanel chatPanel;
    JPanel entrypanels;
    JPanel extraPanel;
    JPanel usersPanel;
    Thread thread;
    private String name;
    public RSA key;
    private Font Title = new Font("Serif", Font.PLAIN, 20);
    private Font Italic = new Font("Serif", Font.ITALIC, 12);
    private Font Plain = new Font("Serif", Font.PLAIN, 12);
    private Font IBold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
    private Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private Font TBold = Title.deriveFont(Title.getStyle() | Font.BOLD);
    private ArrayList<ChatRoomEntry> users = new ArrayList();
    public ChatFrame(View view, Chat chat, Client client)
    {
        super(client.username + " " + client.ip + " " + client.port);
        name = client.username + " " + client.ip + " " + client.port; 
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
        this.client = client;
        chat.setFrame(this, client);
        this.setLayout(new MigLayout());
        startup();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) 
            {
                cleanUp();
            }
        });
        Random random = new Random();
        this.setLocation(random.nextInt(200), random.nextInt(200));
        pack();
        setVisible(true);
    }
    public void startup()
    {
        container = new JPanel(new MigLayout("wrap 2"));
        chatPanel = new JPanel(new MigLayout());
        txt = new JLabel("Chat (" + client.ip + client.port +  ")");
        txt.setFont(TBold);
        container.add(txt, "span 2, align center");
        JPanel panel = new JPanel(new MigLayout("wrap 2"));
        entrypanels = genChat(new JPanel(new MigLayout("wrap 1")));
        scroll = new JScrollPane(entrypanels);
        scroll.setPreferredSize(new Dimension(500, 300));
        chatPanel.add(scroll, "span");
        
        usersPanel = new JPanel(new MigLayout("wrap 1"));
        txt = new JLabel("Users in the chatroom");
        txt.setFont(PBold);
        usersPanel.add(txt,"span");
        extraPanel = genUsers(new JPanel(new MigLayout("wrap 1")));
        usersScroll = new JScrollPane(extraPanel);
        usersScroll.setPreferredSize(new Dimension(150,300));
        usersPanel.add(usersScroll, "span");
        
        panel.add(chatPanel, "span 1");
        panel.add(usersPanel, "span 1");
        container.add(panel, "span");
        panel = new JPanel(new MigLayout("wrap 2"));
        txt = new JLabel(client.getUsername()+":");
        txt.setFont(PBold);
        panel.add(txt, "span 1");
        entry = new JTextArea(5,50);
        entry.setWrapStyleWord(true);
        entry.setLineWrap(true);
        entry.setFont(Plain);
        scroll2 = new JScrollPane(entry);
        panel.add(scroll2, "span 1");
        container.add(panel, "span");
        panel = new JPanel(new MigLayout("wrap 2"));
        btn = new JButton("Send");
        btn.setFont(PBold);
        btn.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                    
                    client.out.println(client.getUsername() + " " + entry.getText());
                    entry.setText("");
                    pack();
	        }
	});
        panel.add(btn, "span 1");
        btn = new JButton("Encrypt");
        btn.setFont(PBold);
        btn.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                    BigInteger crypto = key.encrypt(key.createmessage(entry.getText()));
                    entry.setText(crypto.toString());
                    pack();
	        }
	});
        panel.add(btn, "span 1");
        container.add(panel, "span 2");
        txt = new JLabel("Copyright \u00a9 Kim Hammar all rights reserved");
        txt.setFont(Plain);
        container.add(txt, "span 1, gaptop 20");
        add(container, BorderLayout.CENTER);
    }
    public void updateChat(ArrayList<ChatEntry> entrys)
    {
        entrypanels = genChat(new JPanel(new MigLayout("wrap 1")));
        scroll = new JScrollPane(entrypanels);
        scroll.setPreferredSize(new Dimension(500, 300));
        chatPanel.removeAll();
        chatPanel.add(scroll, "span");
        pack();
    }
    public void updateChat(String msg)
    {
        String[] someentry = msg.split(" ", 2);
        String user = someentry[0];
        String text = someentry[1];
        chat.newEntry(user, text, client);
        updateChat(chat.getChat(client));
        pack();
    }
     public void updateUsers(ArrayList<ChatRoomEntry> users)
    {
        this.users = users;
        usersPanel.remove(usersScroll);
        extraPanel = genUsers(new JPanel(new MigLayout("wrap 1")));
        usersScroll = new JScrollPane(extraPanel);
        usersScroll.setPreferredSize(new Dimension(150,300));
        usersPanel.add(usersScroll, "span");
        pack();
    }
    public void location(JFrame f)
    {
        setLocation(f.getX() + f.getWidth() + f.getWidth()/14, f.getY());
        pack();
    }
    public void cleanUp()
    {
        chat.cleanUp(client);
    }
    public String decrypt(String crypto)
    {
        BigInteger crypt;
        try
        {
            crypt = new BigInteger(crypto);
        }
        catch(Exception e)
        {
            return null;
        }
        BigInteger decrypted = key.decrypt(crypt);
        String decrypt = new String(decrypted.toByteArray());
        return decrypt;
    }
    public JPanel genChat(JPanel panel)
    {
        ArrayList<ChatEntry> entrys = chat.getChat(client);
        for(ChatEntry e : entrys)
        {
            JPanel entry = new EntryPanel(this, e);
            panel.add(entry, "span 1");
        }
        return panel;
    }
    public JPanel genUsers(JPanel panel)
    {
        for(ChatRoomEntry e : users)
        {
            JPanel user = new UserPanel(this, e.username, e.ip);
            panel.add(user, "span 1");
        }
        return panel;
    }
    public void lostConnection()
    {
        new ErrorFrame(name);
        dispose();
    }
}