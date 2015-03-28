/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import model.Server;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class ServerFrame extends JFrame
{
    private Server server;
    JPanel container;
    JLabel txt;
    JButton btn;
    private Font Italic = new Font("Serif", Font.ITALIC, 12);
    private Font Bold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
    public ServerFrame()
    {
        super("Server");
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
        this.setLayout(new MigLayout());
        startup();
        
        pack();
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);
    }
    public void startup()
    {
        container = new JPanel(new MigLayout("wrap 2"));
        txt = new JLabel("Adress: ");
        txt.setFont(Bold);
        container.add(txt, "span 1");
        txt = new JLabel("Not connected");
        txt.setFont(Bold);
        container.add(txt, "span 1");
        txt = new JLabel("Port:");
        txt.setFont(Bold);
        container.add(txt, "span 1");
        txt = new JLabel("Not connected");
        txt.setFont(Bold);
        container.add(txt, "span 1");
        btn = new JButton("Connect");
        btn.setFont(Bold);
        container.add(btn, "span 2");
        add(container);
    }
}
