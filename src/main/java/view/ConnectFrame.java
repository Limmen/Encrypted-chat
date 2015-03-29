/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import model.Server;
import net.miginfocom.swing.MigLayout;

/**
 *
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
    private Font Bold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
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
        setVisible(true);
    }
    public void startup()
    {
        container = new JPanel(new MigLayout("wrap 2"));
        txt = new JLabel("Create a connection to a running server");
        txt.setFont(Bold);
        container.add(txt, "span 2, align center");
        txt = new JLabel("IP adress ");
        txt.setFont(Bold);
        container.add(txt, "span 1");
        ip = new JTextField(30);
        ip.setFont(Bold);
        container.add(ip, "span 1");
        txt = new JLabel("Port");
        txt.setFont(Bold);
        container.add(txt, "span 1");
        port = new JTextField(30);
        port.setFont(Bold);
        container.add(port, "span 1");
        txt = new JLabel("Chat username");
        txt.setFont(Bold);
        container.add(txt, "span 1");
        username = new JTextField(30);
        username.setFont(Bold);
        container.add(username, "span 1");
        btn = new JButton("Connect");
        btn.setFont(Bold);
        btn.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                    String adress = ip.getText();
                    int portnr = Integer.parseInt(port.getText());
                    String user = username.getText();
                    view.newConnection(adress, portnr, user);
                    pack();
	        }
	});
        container.add(btn, "span 2");
        add(container);
    }
}

