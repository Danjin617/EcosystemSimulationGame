package game;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.event.*;  // Needed for ActionListener

//import com.hsl.common.SystemLogger;
//import com.hsl.util.FileUtil;

class MainGameGUI extends JFrame implements ActionListener, ChangeListener, MouseMotionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Ecosystem eco = new Ecosystem ();
    static JSlider speedSldr = new JSlider ();
    //final JComboBox fileComboBox;
    JLabel scoreLbl;
    JButton simulateBtn;
    static Timer t;
	int maxX = 2000;
	int maxY = 1200;
	int screenX = 900;
	int screenY = 600;
	static final int SIMULATION_STATUS_NOT_STARTED = 0;
	static final int SIMULATION_STATUS_STARTED = 1;
	static final int SIMULATION_STATUS_PAUSED = 2;	
	int simulationStatus = SIMULATION_STATUS_NOT_STARTED;
	
	BufferedImage imagePrey;
	BufferedImage imagePredator;
	BufferedImage imagePlayer;
	BufferedImage imageTree;
	BufferedImage imageRiver;
	BufferedImage bg;
	BufferedImage disaster;
	
	 DrawArea board;
	
	private void loadImages() {
        try {
           	//load images
        	imagePrey = ImageIO.read(new File("src/homework/images/prey.jpg"));
        	imagePredator = ImageIO.read(new File("src/homework/images/predator.jpg"));
        	imagePlayer = ImageIO.read(new File("src/homework/images/player.jpg"));
        	imageTree = ImageIO.read(new File("src/homework/images/tree.jpg"));
        	imageRiver = ImageIO.read(new File("src/homework/images/river.jpg"));
        	bg = ImageIO.read(new File("src/homework/images/bg.jpg"));
        	disaster = ImageIO.read(new File("src/homework/images/disaster.png"));
        }catch (IOException e) {
        	e.printStackTrace();
        	System.exit(-1);
        }
 		
	}
	
	
    //======================================================== constructor
    public MainGameGUI ()
    {
    	loadImages();
        
       	//simulation or pause button
        simulateBtn = new JButton ("Simulate");
        simulateBtn.addActionListener (this);
        
        //display score
        scoreLbl = new JLabel("0 Points");
        
        addMouseMotionListener(this);
        
        //String [] choices = {"eradicate", "repopulate"};
        speedSldr.addChangeListener (this);        

        // 2... Create content pane, set layout
        JPanel content = new JPanel ();        // Create a content pane
        content.setLayout (new BorderLayout ()); // Use BorderLayout for panel
        JPanel north = new JPanel ();
        north.setLayout (new FlowLayout ()); // Use FlowLayout for input area

        board = new DrawArea ();
        
        // 3... Add the components to the input area.

        north.add (simulateBtn);
        north.add (speedSldr);
        north.add(scoreLbl);
       //north.add(comboBox);
        //north.add (sizeBox);
       // north.add (fileComboBox);
        
        JButton disasterButton = new JButton("Disaster");
        //JButton saveButton = new JButton("Save");
        north.add (disasterButton);
        //north.add (saveButton);
        BtnListener btnListener = new BtnListener(); 
        disasterButton.addActionListener(btnListener);
        //saveButton.addActionListener(btnListener);
        
        content.add (north, "North"); // Input area
        content.add (board, BorderLayout.CENTER); // Output area

        // 4... Set this window's attributes.
        setContentPane (content);
        pack ();
        setTitle ("Life Simulation Demo");
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize (screenX, screenY);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo (null);           // Center window.

        eco.init(maxX, maxY,screenX, screenY,
    			imagePrey,
    			imagePredator,
    			imagePlayer,
    			imageTree,
    			imageRiver,
    			bg,disaster, board
        		);
        //this.maxX = board.getWidth();
        //this.maxY = board.getHeight();
        
        //mouse move action 
        addMouseListener(new MouseAdapter(){
            
            public void mousePressed (MouseEvent e){
                int x = e.getX();
                int y = e.getY();
                //int z = sizeBox.getSelectedIndex();
                if (t!=null) {
                    //eco.setPlayerLocation(x, y);
                    repaint();
                }
            }
            
        });
          
    }
    public void mouseMoved(MouseEvent e){
    	eco.moveRelativePositions(e.getX(), e.getY());
      }
      public void mouseDragged(MouseEvent e){}
      
     //change the speed
    public void stateChanged (ChangeEvent e)
    {
        if (t != null)
            t.setDelay (400 - 4 * speedSldr.getValue ()); // 0 to 400 ms
    }

    public void actionPerformed (ActionEvent e)
    {
    	String command = e.getActionCommand() ;
        if (command.equals ("Simulate") ||command.equals ("Resume") || command.equals ("Pause") )
        {
        	System.out.println("simulationStatus="+simulationStatus);
        	if (simulationStatus==SIMULATION_STATUS_NOT_STARTED) {
                if (t==null) {
                    Movement moveColony = new Movement (maxX, maxY); // ActionListener
                 	t = new Timer (200, moveColony); // set up timer
                  	
                }
                t.start (); // start simulation
                simulationStatus = SIMULATION_STATUS_STARTED;
                simulateBtn.setText("Pause");
        	}
        	else if (simulationStatus==SIMULATION_STATUS_STARTED) {
        		t.stop();
        		simulationStatus = SIMULATION_STATUS_PAUSED;
        		simulateBtn.setText("Resume");
         	}
        	else if (simulationStatus==SIMULATION_STATUS_PAUSED) {
        		t.start();
        		simulationStatus = SIMULATION_STATUS_STARTED;
        		simulateBtn.setText("Pause");
         	}
         }
        repaint ();            // refresh display of deck
        
       
    }
    
    class BtnListener implements ActionListener // Button menu

	{

		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("Disaster")) {
				eco.disaster();
			}
		}

	}

    class DrawArea extends JPanel
    {
        public DrawArea ()
        {
            //this.setPreferredSize (new Dimension (width, height)); // size
        }

        public void paintComponent (Graphics g)
        {
            eco.show (g);
        }
    }

    class Movement implements ActionListener
    {
        int maxX,maxY;
        JFrame parent;

        public Movement (int maxX, int maxY)
        {
           this.maxX = maxX;
           this.maxY = maxY;
        }

        public void actionPerformed (ActionEvent event)
        {
            boolean gameOver = eco.advance (-1);
            int score = eco.getScore();
            scoreLbl.setText(score+" Points");
            if (t!=null && gameOver) {
            	t.stop();
            	//t = null;
            	eco = new Ecosystem();
                eco.init(maxX, maxY,screenX, screenY,
            			imagePrey,
            			imagePredator,
            			imagePlayer,
            			imageTree,
            			imageRiver,
            			bg,disaster, board
                		);
            	simulateBtn.setText("Simulate");
            	simulationStatus=SIMULATION_STATUS_NOT_STARTED;
            }
            repaint ();
        }
    }

    //======================================================== method main
    public static void main (String[] args)
    {

        MainGameGUI window = new MainGameGUI ();
        window.setVisible (true);
    }
   
    
}



