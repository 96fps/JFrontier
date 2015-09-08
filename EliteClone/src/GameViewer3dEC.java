import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameViewer3dEC extends JFrame{

	/**
	 * @param args
	 */
	

		
	public static void main(String[] args) 
	{	
		GameViewer3dEC window = new GameViewer3dEC();
		JPanel p = new JPanel();
        p.add(new GamePanelEC());  //  add a class that extends JPanel
        window.setTitle("EliteClone!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        window.setContentPane(p);
        
       
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
	}

}