import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class DrawingInterface extends JComponent{
	
	// Most recent x and y coordinates
	int recentx = -1;
	int recenty = -1;
	
	Color defaultColor = Color.orange;
	Color defaultLineColor = Color.black;
	int currentThickness = 1;
	
	private static class Line{
	    final int x1; 
	    final int y1;
	    final int x2;
	    final int y2;   
	    final Color color;
	    final int thickness;

	    public Line(int x1, int y1, int x2, int y2, Color color, int thickness) {
	        this.x1 = x1;
	        this.y1 = y1;
	        this.x2 = x2;
	        this.y2 = y2;
	        this.color = color;
	        this.thickness = thickness;
	    }               
	}
	
	private LinkedList<Line> lines = new LinkedList<Line>();								// LinkedList of coordinates for current polygon
	private ArrayList<Polygon> polygonArray = new ArrayList<Polygon>();						// ArrayList of LinkedLists containing finished polygons
	private ArrayList<Color> colorArray = new ArrayList<Color>();
	private ArrayList<LinkedList<Line>> lineArray = new ArrayList<LinkedList<Line>>();

	public void addLine(int x1, int y1, int x2, int y2) {
	    addLine(x1, y1, x2, y2, defaultLineColor);
	}

	public void addLine(int x1, int y1, int x2, int y2, Color color) {
	    lines.add(new Line(x1, y1, x2, y2, color, currentThickness));        
	    repaint();
	}

	public void clearLines() {
	    lines.clear();
	    repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    Graphics2D g2D = (Graphics2D) g;
	    
	    for(int i = 0; i < polygonArray.size(); i++) {					// First for loop gets the completed polygons
    		g2D.setColor(colorArray.get(i));
        	g2D.fillPolygon(polygonArray.get(i));
        	g2D.drawPolygon(polygonArray.get(i));
	    }
	    
	    for (Line line : lines) {									// Third for loop creates in-progress polygon
	    	g2D.setStroke(new BasicStroke(line.thickness));
    		g2D.setColor(line.color);
        	g2D.drawLine(line.x1, line.y1, line.x2, line.y2);
    	}
	    
	    for(LinkedList<Line> lines : lineArray) {
	    	for (Line line : lines) {
		    	g2D.setStroke(new BasicStroke(line.thickness));
	    		g2D.setColor(line.color);
	        	g2D.drawLine(line.x1, line.y1, line.x2, line.y2);
	    	}
	    }
	}	
	
	public static void main(String[] args) {
		
		// Initialize frame, panel, comp, and buttons
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		DrawingInterface comp = new DrawingInterface();
		JButton doneButton = new JButton("Done");
		JButton redButton = new JButton("Red");
		JButton blueButton = new JButton("Blue");
		JButton greenButton = new JButton("Green");
		JButton thinButton = new JButton("Thin");
		JButton normalButton = new JButton("Normal");
		JButton thickButton = new JButton("Thick");
		JButton lineRedButton = new JButton("Red");
		JButton lineBlueButton = new JButton("Blue");
		JButton lineGreenButton = new JButton("Green");
		
		panel.add(doneButton);
		doneButton.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {	        	
	            comp.addLine(comp.recentx, comp.recenty, comp.lines.getFirst().x1, comp.lines.getFirst().y1);	// Adds the final line between start and end coordinates	          
	            
	            comp.colorArray.add(comp.defaultColor);
	            
	            int[] xCoordinates = new int[comp.lines.size()];
	            int[] yCoordinates = new int[comp.lines.size()];
	            
	            for(int i = 0; i < comp.lines.size(); i++) {
	            	xCoordinates[i] = comp.lines.get(i).x1;
	            	yCoordinates[i] = comp.lines.get(i).y1;
	            }
	            
	            comp.polygonArray.add(new Polygon(xCoordinates, yCoordinates, comp.lines.size()));			// Create a polygon with coordinates
	            comp.lineArray.add(comp.lines);
	            comp.lines = new LinkedList<Line>();	// Create a new list for the next polygon
	            
	            comp.recentx = -1;	// Reset values
	            comp.recenty = -1;	// Reset values
	        }
	    });	
		
		panel.add(new JLabel("|"));		// Spacer
		
		panel.add(redButton);
		redButton.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {	        	
	        	comp.defaultColor = Color.red;
	        }
	    });	
		
		panel.add(blueButton);
		blueButton.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {	        	
	        	comp.defaultColor = Color.blue;
	        }
	    });	
		
		panel.add(greenButton);
		greenButton.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {	
	        	comp.defaultColor = Color.green;
	        }
	    });	
		
		panel.add(new JLabel("|"));		// Spacer
		
		panel.add(thinButton);
		thinButton.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {	
	        	comp.currentThickness = 1;
	        }
	    });	
		
		panel.add(normalButton);
		normalButton.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {	
	        	comp.currentThickness = 2;
	        }
	    });	
		
		panel.add(thickButton);
		thickButton.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {	
	        	comp.currentThickness = 3;
	        }
	    });	
		
		panel.add(lineRedButton);
		lineRedButton.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {	        	
	        	comp.defaultLineColor = Color.red;
	        }
	    });	
		
		panel.add(lineBlueButton);
		lineBlueButton.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {	        	
	        	comp.defaultLineColor = Color.blue;
	        }
	    });	
		
		panel.add(lineGreenButton);
		lineGreenButton.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {	        	
	        	comp.defaultLineColor = Color.green;
	        }
	    });	
		
		// Gets the mouse position when the mouse is clicked and records the coordinates in an array which is then stored in a list
		comp.addMouseListener(new MouseAdapter() {
			@Override //I override only one method for presentation
		    public void mousePressed(MouseEvent e) {				
				if(comp.recentx != -1 && comp.recenty != -1) {
					comp.addLine(comp.recentx, comp.recenty, e.getX(), e.getY());										
				}	
					comp.recentx = e.getX();
					comp.recenty = e.getY();
							
				System.out.println(e.getX() + "," + e.getY());						
		    }
		});
		
		comp.setPreferredSize(new Dimension(320, 200));
		
		frame.getContentPane().add(comp, BorderLayout.CENTER);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);				// What happens when they close the frame
		frame.setTitle("Drawing Window");										// Adds the title of the window
		frame.pack();															// Set window to certain size
		frame.setVisible(true);													// Set window to be visible and in focus
	}
}
