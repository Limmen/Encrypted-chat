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
import javax.swing.Timer;
import javax.swing.UIManager;
import model.Chat;
import model.ChatEntry;
import model.ChatRoomEntry;
import model.PrivateClient;
import model.RSA;
import model.RSAPublicKey;
import net.miginfocom.swing.MigLayout;

/**
 *This clas represents a chatframe for a existing connection of a client and a server.
 * @author kim
 */
public class PrivateChatFrame extends JFrame
{
    private Chat chat;
    private PrivateClient client;
    JPanel container;
    JLabel txt;
    JLabel title;
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
    boolean encrypted = false;
    String clearText;
    public ChatFrame cf;
    public PrivateChatFrame(Chat chat, PrivateClient client)
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
        this.chat = chat;
        this.client = client;
        //chat.setFrame(this, client);
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
        JPanel panel = new JPanel(new MigLayout("wrap 2"));
        title = new JLabel("<html><font color=red>Private chat with: </font></html>");
        title.setFont(TBold);
        panel.add(title, "span 1");
        txt = new JLabel("   ("+ client.ip + "  " + client.port +  ")");
        txt.setFont(Plain);
        panel.add(txt, "span 1");
        container.add(panel, "span 2, align center");
        panel = new JPanel(new MigLayout("wrap 2"));
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
        usersScroll.setPreferredSize(new Dimension(250,300));
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
                    try
                    {
                       if(encrypted)
                        {
                            updateChat(new ChatEntry(client.getUsername(), clearText, false));
                            client.objectOut.flush();
                            ChatEntry ce = new ChatEntry(client.getUsername(), entry.getText());
                            client.objectOut.writeObject(ce);
                            client.objectOut.reset();
                        }
                        else
                        {
                            updateChat(new ChatEntry(client.getUsername(), entry.getText(), false));
                            client.objectOut.flush();
                            ChatEntry ce = new ChatEntry(client.getUsername(), entry.getText(), false);
                            client.objectOut.writeObject(ce);
                            client.objectOut.reset();
                        }
                        entry.setText("");
                        encrypted = false;
                        pack();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
	        }
	});
        panel.add(btn, "span 1");
        btn = new JButton("Encrypt");
        btn.setFont(PBold);
        btn.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                    clearText = entry.getText();
                    BigInteger crypto = key.encrypt(key.createmessage(entry.getText()));
                    entry.setText(crypto.toString());
                    encrypted = true;
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
    public void updateChat(ChatEntry ce)
    {
        chat.newEntry(ce, client);
        updateChat(chat.getChat(client));
        pack();
    }
     public void updateUsers(ArrayList<ChatRoomEntry> users)
    {
        this.users = users;
        usersPanel.remove(usersScroll);
        extraPanel = genUsers(new JPanel(new MigLayout("wrap 1")));
        usersScroll = new JScrollPane(extraPanel);
        usersScroll.setPreferredSize(new Dimension(250,300));
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
        cf.setState(cf.MAXIMIZED_BOTH);
        cf.pack();
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
        BigInteger decrypted = key.decrypt(crypt, client.friendPublicKey);
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
            if(e == null)
            {
                System.out.println("ChatRoomEntry is null");
                continue;
            }
            if(e.username != client.username)
                setUser(e.username);
            JPanel user = new UserPanel(this, e.username, e.ip);
            panel.add(user, "span 1");
        }
        return panel;
    }
    public void lostConnection()
    {
        //new ErrorFrame(name);
        dispose();
    }
    public void delay()
    {
        int delay = 2000;
        Timer timer = new Timer( delay, new ActionListener(){
            @Override
            public void actionPerformed( ActionEvent e ){
                System.out.println("timer went off");
                cleanUp();
            }
        });
        timer.setRepeats( false );
        timer.start();
    }
    public void setUser(String user)
    {
        title.setText("<html><font color=red>Private chat with: </font><font color=black>" + user + "</font></html>");
        pack();
    }
}
