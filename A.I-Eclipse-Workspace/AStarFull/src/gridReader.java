import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class gridReader extends JPanel {
	private static  Color[][] terrainGrid = new Color [101][101];
	//static  AStarTest aStarT =new  AStarTest(grid,101,101);
		
	 @Override
	 public void paintComponent(Graphics g) {
	     // Important to call super class method
	     super.paintComponent(g);
	     // Clear the board
	     g.clearRect(0, 0, getWidth(), getHeight());
	     // Draw the grid
	     int rectWidth = getWidth() / 101;
	     int rectHeight = getHeight() / 101;
	   //  g.rotate(-Math.PI / 2, getWidth()/2, getHeight()/2);
	     for (int i = 0; i < 101; i++) {
	         for (int j = 0; j < 101; j++) {
	             // Upper left corner of this terrain rect
	             int x = i * rectWidth;
	             int y = j * rectHeight;
	             Color terrainColor = terrainGrid[i][j];
	             g.setColor(terrainColor);
	             g.fillRect(x, y, rectWidth, rectHeight);
	             
	         }
	     }
	 }
	 public static void main(String [] args) throws IOException {
		 Block[][] grid = new Block[101][101];
			 Block start;			 
			Block end;
			 Color blocked = new Color(255,0,0);
			 Color unblocked = new Color(0,0,0);
			 Color playerColor = new Color (0,255,0);
			 Color targetColor = new Color (0,0,255);
			  Color pathColor = new Color (255,255,255);
			  char [][] chargrid = new char [101][101];
			String ptr = null;
		/*	
			JTextArea textArea = new JTextArea("Enter Grid Name :");
			textArea.setEditable(false);
			
			JTextArea textField = new JTextArea("ex. grid1, grid2 ....");
			textArea.setEditable(true);
			
			JButton gridB = new JButton("Generate Grid");
			
			*/

			
			
			Scanner scan = new Scanner(System.in);
			
			
			for(int i = 0;i<101;i++){
	            for (int j=0;j<101;j++){
	            	grid[i][j] = new Block(i,j);
	            }
			}
	            
			try {
				FileReader read = new FileReader("grid32.txt");
				BufferedReader buff = new BufferedReader(read);
				
				for(int i = 0;i<101;i++) {
					ptr = buff.readLine();
					for(int j = 0;j<101;j++) {
						chargrid[i][j] = ptr.charAt(j);
						if(chargrid[i][j]=='A') {
							start = new Block(i,j);
							
							start.setRow(i);
							start.setCol(j);
							
						}
						if(chargrid[i][j]=='T') {
							end = new Block(i,j);
							System.out.println("End : "+end);
							end.setRow(i);
							end.setCol(j);
						}
					}
				}
				buff.close(); 		
						
			}catch(FileNotFoundException e){
				System.out.println("File Not Found");
			}
		int starti=0;
		int startj=0;
		int endi = 0;
		int endj=0;
			for(int i = 0;i<101;i++) {	
				for(int j = 0 ;j<101;j++) {
					if(chargrid[i][j] == 'A') {
						starti= i;
						startj=j;
					}
					if(chargrid[i][j] == 'T') {
						endi = i;
						endj = j;
					}
					
				}
			}
			
			
			start = new Block(starti,startj);
			end = new Block(endi,endj);
			System.out.println("start" + start);
			System.out.println("end" + end);
			AStar aStar =new AStar(101, 101, start, end);
		for(int i = 0;i<101;i++) {	
			for(int j = 0 ;j<101;j++) {
				
			
				if(chargrid[i][j] =='0') {    	
					grid[i][j].setBlock(true);
					aStar.setBlock(i,j);     	
				} 
				else if(chargrid[i][j]=='_') {
					grid[i][j].setBlock(false);
            
				}
            
				else if(chargrid[i][j]=='T') {
					//	end = new Node(i,j);
					grid[i][j]=end;
					aStar.setendNode(end);
					end.setRow(i);
					end.setCol(j);
      	
            }else if(chargrid[i][j]=='A'){
            		//start = new Node(i,j);
            		
            	grid[i][j] = start;
            	aStar.setstartNode(start);
            	start.setRow(i);
            		start.setCol(j);
            		
            	 }
			   }	     		
            }
			
			 
			 List<Block> path = aStar.findPath();
		        for (Block node : path) {
		           System.out.println(node);
		        	grid[node.getRow()][node.getCol()].setPath(true);
		        	
		        }
		        
		        System.out.println("START" + start);
		        terrainGrid[starti][startj] = playerColor;
		        for (int i=0;i<101;i++){
		            for (int j=0;j<101;j++){
		                if(grid[i][j].isBlock()==true){
		               
		                	Color c = blocked;
		             	   terrainGrid[i][j] = c;
		     
		                }else{
		                	if(grid[i][j]==start) {
		               
		                	//	Color c = playerColor;
		                  	//   terrainGrid[i][j] = c;
		              
		                	}else if(grid[i][j] == end){

		                		Color c = targetColor;
		                  	   terrainGrid[i][j] = c;
		                	
		                		
		                	}else if(grid[i][j].isPath()==true){
		                		if(grid[i][j]==end) {
		                   			Color c = targetColor;
		                      	  terrainGrid[i][j] = c;
		                   		}else {
		                		Color c = pathColor;
		                  	   terrainGrid[i][j] = c;
		                   		}
		                	}
		                	else if(grid[i][j].isBlock()==false) {

		                		Color c = unblocked;
		                		terrainGrid[i][j] = c;
		                  
		                	}
		                }
		            }
		            System.out.println();
		         
			    	}
		        
		     //   terrainGrid[end.getRow()][end.getCol()] = targetColor;
		 	   
		     	int preferredWidth = 101*8;
		         int preferredHeight = 101*8;
		        //etPreferredSize(new Dimension(preferredWidth, preferredHeight));

		         
		         
		         
		         
		         AStarTest map = new AStarTest(grid,101,101, start, end,true); // Forward A Star
		      //AStarTest map = new AStarTest(grid,101,101, end, start,true);  // Backwards A Star.
		       
		         
		         AStarTest initialmap = new AStarTest(grid,101,101, start, end, false);
		         
		         
		         
		         SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		            
		                JFrame frame = new JFrame("AStargridReader");
		                JPanel pane = new JPanel();
		              frame.setSize(new Dimension(preferredWidth,preferredHeight));
		                
		             frame.add(pane);
		             pane.add(initialmap);
		                pane.add(map);
		                
		             //   pane.add(textArea);
		               // pane.add(textField);
		             //   pane.add(gridB);
		                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		                frame.pack();
		                frame.setVisible(true);
		                
		            }
		        });  
		            
	 }
		
}
