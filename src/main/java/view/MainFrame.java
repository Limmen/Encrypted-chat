/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
public class MainFrame extends JFrame
{
    private Server server;
    JPanel container;
    JLabel txt;
    JLabel status;
    JLabel port;
    JButton btn;
    private Font Italic = new Font("Serif", Font.ITALIC, 12);
    private Font Bold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
    View view;
    public MainFrame(View view)
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
        this.setLayout(new MigLayout());
        this.view = view;
        startup();

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.out.println("window close");
                cleanUp();
            }
        });
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);
    }
    public void startup()
    {
        if(view.checkServer())
        {
            
        }
        else
        {
        container = new JPanel(new MigLayout("wrap 2"));
        txt = new JLabel("Local Server");
        txt.setFont(Bold);
        container.add(txt, "span 2, align center");
        txt = new JLabel("Status: ");
        txt.setFont(Bold);
        container.add(txt, "span 1");
        status = new JLabel("Not running");
        status.setFont(Bold);
        container.add(status, "span 1");
        txt = new JLabel("Port:");
        txt.setFont(Bold);
        container.add(txt, "span 1");
        port = new JLabel("----");
        port.setFont(Bold);
        container.add(port, "span 1");
        btn = new JButton("Start Local Server");
        btn.setFont(Bold);
        btn.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                    view.startServer();
                    int p = view.getServerPort();
                    status.setText("Running");
                    port.setText(Integer.toString(p));
                    pack();
	        }
	});
        container.add(btn, "span 1");
        btn = new JButton("Stop Local Server");
        btn.setFont(Bold);
        btn.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                    view.stopServer();
                    status.setText("Not running");
                    port.setText("----");
                    pack();
	        }
	});
        container.add(btn, "span 1");
        btn = new JButton("Connect");
        btn.setFont(Bold);
        btn.addActionListener(new ActionListener() 
        {
	    public void actionPerformed(ActionEvent arg0) 
                {   
                    view.connect();
                    pack();
	        }
	});
        container.add(btn, "span 2, align center");
        add(container);
        
        }
    }
    public void cleanUp()
    {
        view.stopServer();
    }
}
