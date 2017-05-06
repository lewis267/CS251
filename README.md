# CS 251
This is a repo for CS 25100 at Purdue

Throughout the course we had five projects:

## Project 1: Percolation

      Percolation is a scientific model that is used to analyze the connectivity of systems. For example, it can be used to analyze if a porous landscape with water on the surface will eventually allow the water to drain through to the bottom. It can be used to analyze if oil would be able to reach the surface in a similar manner. The idea of the model is to analyze what conditions are necessary for the system to percolate (i.e. let the water or oil through).
  
      The current assignment will allow students to apply the union find data structure to solve this problem.
  The system will be represented as a N by N grid where each cell can be in one of 3 states: blocked
  (black), open (white) or full (blue). A grid where percolation has been achieved will have a path of full
  cells from the surface to the bottom.
  
      The flow of the material goes from top to bottom and each cell routes that flow through the
  top,left,right and bottom neighbors (4 neighbors). A system where at least one bottom row cell is
  full (thanks to the flow route) is said to percolate