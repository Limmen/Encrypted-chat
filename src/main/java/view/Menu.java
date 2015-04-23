package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class Menu 
{
	private JMenuBar menuBar;
	private JMenuItem Help;
	private JMenuItem About;
        private Font Italic = new Font("Serif", Font.ITALIC, 12);
        private Font Plain = new Font("Serif", Font.PLAIN, 12);
        private Font IBold = Italic.deriveFont(Italic.getStyle() | Font.BOLD);
        private Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
	Menu()
	{
		// Creates a menubar for a JFrame
        menuBar = new JMenuBar();
        
      
        // Define and add two drop down menu to the menubar
        JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setFont(PBold);
        menuBar.add(optionsMenu);
        
        // Create and add simple menu item to one of the drop down menu
        Help = new JMenuItem("Help");
        Help.setFont(Plain);
        About = new JMenuItem("About");
        About.setFont(Plain);
     
        optionsMenu.add(Help);
        optionsMenu.add(About);
    }
	
	public JMenuBar getMenu()
	{
		return this.menuBar;
	}
	public JMenuItem getHelp()
	{
		return Help;
	}
	public JMenuItem getAbout()
	{
		return About;
	}
}
