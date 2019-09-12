# AStar-Algorithm
- This A Star algorithm that I built  makes a random grid that is 100x100 large, it picks a random start and end spot and then finds the shortest path between those 2 points using the A Star Algorithm.
- To run the code, run the "AStarTest.java". This will make a random grid everytime you run it. The grid making algorithm that I designed always makes a grid such if you pick a 2 random open points in the grid, there will always be a path between them. The density (ratio) of the blocked cells vs unblocked cells can be changed from line 134 of "AStarTest". Dividing the ratio by a larger number will reduce the density and vice versa.
- The class "AStar.java" holds most of the algorithm related calculations
- There is also a "gridReader.java" class which reads a given grid along with a start and end point to find out the shortest path between the 2.
