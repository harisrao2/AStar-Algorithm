import java.util.*;

public class AStar {
    private static int  cost = 1;

  
    private Block[][] searchArea;
    private PriorityQueue<Block> openList;
    private Set<Block> closedSet;
    private Block startNode;
    private Block endNode;

    public AStar(int rows, int cols, Block startNode, Block endNode, int cost) {
  
        setstartNode(startNode);
        setendNode(endNode);
        this.searchArea = new Block[rows][cols];
        this.openList = new PriorityQueue<Block>(new Comparator<Block>() {
            @Override
            public int compare(Block first, Block second) {
                return Integer.compare(first.getF(), second.getF());
            }
        });
        setNodes();
        this.closedSet = new HashSet<>();
    }

    public AStar(int rows, int cols, Block startNode, Block endNode) {
        this(rows, cols, startNode, endNode, cost);
    }

    private void setNodes() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                Block node = new Block(i, j);
                node.manhattanDistance(getendNode());
                this.searchArea[i][j] = node;
            }
        }
    }

    public List<Block> findPath() {
        openList.add(startNode);
        while (!isEmpty(openList)) {
            Block currentNode = openList.poll();
            closedSet.add(currentNode);
            if (isendNode(currentNode)) {
                return getPath(currentNode);
            } else {
                addToOpenList(currentNode);
            }
        }
        return new ArrayList<Block>();
    }

    private List<Block> getPath(Block currentNode) {
        List<Block> path = new ArrayList<Block>();
        path.add(currentNode);
        Block parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private void addToOpenList(Block currentNode) {
    
    	 int row = currentNode.getRow();
         int col = currentNode.getCol();
         int TopRow = row - 1;
         if (TopRow >= 0) {
        
             checkNode(currentNode, col, TopRow );
         } 
         row = currentNode.getRow();
         col = currentNode.getCol();
         int bottomRow = row + 1;
         if (bottomRow < getSearchArea().length) {
            
             checkNode(currentNode, col, bottomRow);
         }
         row = currentNode.getRow();
        col = currentNode.getCol();
         int middleRow = row;
         if (col - 1 >= 0) {
             checkNode(currentNode, col - 1, middleRow );
         }
         if (col + 1 < getSearchArea()[0].length) {
             checkNode(currentNode, col + 1, middleRow );
         }
    	
    }

 

    private void checkNode(Block currentNode, int col, int row) {
        Block adjacentNode = getSearchArea()[row][col];
        if (!adjacentNode.isBlock() && !getClosedSet().contains(adjacentNode)) {
            if (!getOpenList().contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode, cost);
                getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                if (changed) {
      
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }
    }

    private boolean isendNode(Block currentNode) {
        return currentNode.equals(endNode);
    }

    private boolean isEmpty(PriorityQueue<Block> openList) {
        return openList.size() == 0;
    }

    public void setBlock(int row, int col) {
        this.searchArea[row][col].setBlock(true);
    }

    public Block getstartNode() {
        return startNode;
    }

    public void setstartNode(Block startNode) {
        this.startNode = startNode;
    }

    public Block getendNode() {
        return endNode;
    }

    public void setendNode(Block endNode) {
        this.endNode = endNode;
    }

    public Block[][] getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(Block[][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Block> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<Block> openList) {
        this.openList = openList;
    }

    public Set<Block> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<Block> closedSet) {
        this.closedSet = closedSet;
    }

  


  
}
