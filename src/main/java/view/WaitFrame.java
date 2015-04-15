package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

/**
 *This class is a frame for connecting to a running server and start a chat.
 * @author kim
 */
public class WaitFrame extends JFrame
{
    JPanel container;
    JLabel txt;
    private Font Italic = new Font("Serif", Font.ITALIC, 12);
    private Font Plain = new Font("Serif", Font.PLAIN, 18);
    private Font IBold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
    private Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    public WaitFrame(String name)
    {
        super(name);
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
        startup();
        
        pack();
        //setSize(150,300);
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);
    }
    public void startup()
    {
        container = new JPanel(new MigLayout("wrap 2"));
        txt = new JLabel("Wait while we set up the private chat.. exchanging public keys");
        txt.setFont(PBold);
        container.add(txt, "span 2, align center");
       // container.setBackground(Color.yellow);
        add(container, BorderLayout.CENTER);
    }
    public void location(JFrame f)
    {
        setLocation(f.getX() - (getWidth() - f.getWidth())/2, f.getY() + f.getHeight() + f.getHeight()/6);
        pack();
    }
}