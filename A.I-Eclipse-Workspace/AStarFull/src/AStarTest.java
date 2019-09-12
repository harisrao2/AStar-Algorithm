import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Dimension;




import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class AStarTest extends JPanel {
	public int gridPixelSize = 8;
	static Block initialNode = new Block(0,0);
    static Block finalNode = new Block(0,0);
   static  int[][] blocksArray;
 static   Block[][] grid = new Block[101][101];
	static int rows = 101;
   static int cols = 101;
   private final Color[][] terrainGrid = new Color [101][101];
 static  AStar aStar =new AStar(rows, cols, initialNode, finalNode);
 public Color blocked = new Color(255,0,0);
 public Color unblocked = new Color(0,0,0);
 public Color playerColor = new Color (0,255,0);
 public Color targetColor = new Color (0,0,255);
 public Color pathColor = new Color (255,255,255);
   
 @Override
 public void paintComponent(Graphics g) {
     // Important to call super class method
     super.paintComponent(g);
     // Clear the board
     g.clearRect(0, 0, getWidth(), getHeight());
     // Draw the grid
     int rectWidth = getWidth() / cols;
     int rectHeight = getHeight() / rows;
     
     for (int i = 0; i < rows; i++) {
         for (int j = 0; j < cols; j++) {
             // Upper left corner of this terrain rect
             int x = i * rectWidth;
             int y = j * rectHeight;
             Color terrainColor = terrainGrid[i][j];
             g.setColor(terrainColor);
             g.fillRect(x, y, rectWidth, rectHeight);
         }
     }
 }
 
 public AStarTest(Block [][]grid,int rows, int cols, Block s, Block e, boolean printpath) {
 
//PrintWriter writer = null;
//try {
//	PrintWriter writer = new PrintWriter("grid50.txt");
	 boolean pp = printpath;
	 
	 for (int i=0;i<rows;i++){
           for (int j=0;j<cols;j++){
               if(grid[i][j].isBlock()==true){
            	   Color c = blocked;
            	   this.terrainGrid[i][j] = c;
                  // System.out.print("0");
            	//   writer.print("0");
    
               }else{
               	if(grid[i][j]==s) {
               		Color c = playerColor;
             	   this.terrainGrid[i][j] = c;
               		//System.out.print("A");
             	//  writer.print("A");
            
               	}else if(grid[i][j] == e){
               		Color c = targetColor;
             	   this.terrainGrid[i][j] = c;
               		//System.out.print("T");
             	//  writer.print("T");
               	
               		
               	}else if(pp==true) {
               	
               		if(grid[i][j].isPath()==true ){
               			if(grid[i][j]==e) {
               				Color c = targetColor;
               				this.terrainGrid[i][j] = c;
               			}else {
               				Color c = pathColor;
               				this.terrainGrid[i][j] = c;
               				//System.out.print("2");
               				//writer.print("_");
               			}
               		}else {
               			Color c = unblocked;
           				this.terrainGrid[i][j] = c;
               		}
               	}
               	else if(grid[i][j].isBlock()==false){
               			Color c = unblocked;
               			this.terrainGrid[i][j] = c;
               			//System.out.print("_");\
               		//	writer.print("_");
               	}
               }
           }
          // System.out.println();
         //  writer.println();
        
	    	}
	   			//	this.terrainGrid[finalNode.getRow()][finalNode.getCol()] = targetColor;
	   
	   				int preferredWidth = cols * gridPixelSize;
	   				int preferredHeight = rows * gridPixelSize;
	   				setPreferredSize(new Dimension(preferredWidth, preferredHeight));
     
	   			//	writer.close();
	   
		//	} catch (FileNotFoundException e) {
				
		//		e.printStackTrace();
		//	}
			
 		}

   
	 public static void generateGrid(Block [][] grid, int rows, int cols){
	        int ratio = (rows*cols)/5;
	        //System.out.println(ratio);
	        for(int i = 0;i<rows;i++){
	            for (int j=0;j<cols;j++){
	                
	                    grid[i][j] = new Block(i,j);
	            }
	        }
	        for(int i = 0;i<rows;i++){
	            for (int j=0;j<cols;j++){
	                if(i==0 || j ==0 || i== rows-1 || j==cols-1){
	                    grid[i][j].setBlock(true);
	                }
	                while(ratio>0){
	                    int max = rows-2;
	                    int max2 = cols-2;//Carfull with rows ONLY !
	                    int min = 1;
	                    int range = max - min +1;
	                    int range2 = max2-min +1;
	                    int rand1 = (int) (Math.random()*range)+min;
	                    int rand2 = (int) (Math.random()*range2)+min;
	                    //System.out.println(rand1);
	                    //System.out.println(rand2);
	                    if(grid[rand1][rand2].isBlock()==false){
	                        int check = checkCells(grid,rand1,rand2);
	                        if(check>=3){
	                            grid[rand1][rand2].setBlock(true);
	                            aStar.setBlock(rand1,rand2);
	                            
	                        }     
	                    }
	                    ratio--;
	                }    
	            }
	        }

	       for(int i = 1;i<rows-1;i++){     //Cleans up the map for more solid blocks
	            for(int j=1;j<cols-1;j++){
	                int check = checkCells(grid,i,j);
	                if(check<=1){
	                    grid[i][j].setBlock(true);
	                    aStar.setBlock(i,j);
	                }
	            }
	        }
	        for(int i = 1;i<rows-1;i++){   //Cleans up a map from a block that has all 4 directions blocked
	            for(int j=1;j<cols-1;j++){
	                int check = checkCells(grid,i,j);
	                if(check==0){
	                    grid[i][j].setBlock(true);
	                    aStar.setBlock(i,j);
	                }
	            }
	        }
	        int AgentRow;
	        int AgentCol;
	        int DestRow;
	    	int DestCol;
	        do {
	        	AgentRow =(int) (Math.random()*93)+3;
	        	AgentCol = (int) (Math.random()*93)+3;
	        	DestRow =(int) (Math.random()*93)+3;
	        	DestCol = (int) (Math.random()*93)+3;
	        	System.out.println("DestRow :"+DestRow+ "DestCol :"+DestCol);
	        	System.out.println("AgentRow :"+AgentRow+ "AgentCol :"+AgentCol);
	        }
	        while((grid[AgentRow][AgentCol].isBlock() == true) || (grid[DestRow][DestCol].isBlock()==true));

	        	
	        	grid[AgentRow][AgentCol] =initialNode;
	        	aStar.setstartNode(initialNode);
	        	initialNode.setRow(AgentRow);
	        	initialNode.setCol(AgentCol);
	        	grid[DestRow][DestCol] = finalNode;
	        	aStar.setendNode(finalNode);
	        	finalNode.setRow(DestRow);
	        	finalNode.setCol(DestCol);
	        	
	        	

	    }
	 
	 public static int checkCells(Block [][] grid, int i, int j){
	       int count = 0;
	       if(grid[i+1][j].isBlock()==false){
	           count++;
	       }
	       if(grid[i-1][j].isBlock()==false){
	           count++;
	       }
	       if(grid[i][j+1].isBlock()==false){
	           count++;
	       }
	       if(grid[i][j-1].isBlock()==false){
	           count++;
	       }
	    
	       return count;
	    }

    public static void main(String[] args) throws IOException  {
     
    	for(int i = 0;i<101;i++){
            for (int j=0;j<101;j++){
            	grid[i][j] = new Block(i,j);
            }
		}
    	
    	List<Block> path;
    	do {
    	generateGrid(grid,rows,cols);
    	//aStar.setFinalNode(finalNode);
        path = aStar.findPath();
    	}
        while(path.isEmpty()); 
        	
        for (Block node : path) {
        	
           System.out.println(node);
        	grid[node.getRow()][node.getCol()].setPath(true);
        	
        }
        
        
        for (int i=0;i<rows;i++){
            for (int j=0;j<cols;j++){
                if(grid[i][j].isBlock()==true){
               
                    System.out.print("0");
     
                }else{
                	if(grid[i][j]==initialNode) {
               
                		System.out.print("A");
              
                	}else if(grid[i][j] == finalNode){

                		System.out.print("T");
                	
                		
                	}else if(grid[i][j].isPath()==true){
                		System.out.print("2");
                	}
                	else if(grid[i][j].isBlock()==false) {

                    	System.out.print("_");
                  
                	}
                }
            }
            System.out.println();
         
	    	}

     
      SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("AStarRandomTest");
                JPanel pane = new JPanel();
                //frame.setSize(new Dimension(400, 400));
                AStarTest map = new AStarTest(grid,rows,cols, initialNode, finalNode,true);
                AStarTest initialmap = new AStarTest(grid,rows,cols, initialNode, finalNode,false);
                frame.add(pane);
	             pane.add(initialmap);
	                pane.add(map);
               
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                
            }
        }); 
    
    }
}