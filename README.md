# CS 251
This is a repo for CS 25100 at Purdue.

Throughout the course we had five projects.

## Project 1: Percolation (Java)
Percolation is a scientific model that is used to analyze the connectivity of systems. For example, it can be used to analyze if a porous landscape with water on the surface will eventually allow the water to drain through to the bottom. It can be used to analyze if oil would be able to reach the surface in a similar manner. The idea of the model is to analyze what conditions are necessary for the system to percolate (i.e. let the water or oil through).
  
The current assignment will allow students to apply the union find data structure to solve this problem. The system will be represented as a N by N grid where each cell can be in one of 3 states: blocked (black), open (white) or full (blue). A grid where percolation has been achieved will have a path of full cells from the surface to the bottom.
  
The flow of the material goes from top to bottom and each cell routes that flow through the top, left, right, and bottom neighbors (4 neighbors). A system where at least one bottom row cell is full (thanks to the flow route) is said to percolate.

## Project 2: Pattern Recognition (Java)
Write a program to recognize line patterns in a given set of points.

Computer vision involves analyzing patterns in visual images and reconstructing the real-world objects that produced them. The process is often broken up into two phases: feature detection and pattern recognition. Feature detection involves selecting important features of the image; pattern recognition involves discovering patterns in the features. We will investigate a particularly clean pattern recognition problem involving points and line segments. This kind of pattern recognition arises in many other applications, for example statistical data analysis.

## Project 3: Password Cracking (C++)
When a system's login manager is presented with a password, it needs to check whether that password corresponds to the user's name in its internal tables. The naive method would be to store passwords in a symbol table with the users' names as keys, but that method is vulnerable to someone getting unauthorized access to the system's table which would expose all of the users' passwords. Instead, most systems use a more secure method where the system keeps a symbol table that stores an encrypted password for each user. When a user types a password, that password is encrypted and checked against the stored value. If it matches, the user is allowed into the system. 

For it to be effective, this scheme requires an encryption method with two properties: encrypting a password should be easy (since it has to be done each time a user logs in), and recovering the original password from the encrypted version should be hard.

## Project 4: WordNet (Java)
The project will consist of two main tasks revolving around the use of directed graphs: finding shortest ancestral paths and implementing a WordNet application.

## Project 5: Data Compression (Java)
The Burrows-Wheeler data compression algorithm is a revolutionary algorithm that outcompresses gzip and PKZIP, is relatively easy to implement, and is not protected by any patents. It forms the basis of the Unix compression utility bzip2.

It consists of three different algorithmic components, which are applied in succession:
1. Burrows-Wheeler transform. Given a typical English text file, transform it into a text file in which sequences of the same character occur near each other many times.

2. Move-to-front encoding. Given a text file in which sequences of the same character occur near each other many times, convert it into a text file in which certain characters appear more frequently than others.

3. Huffman compression. Given a text file in which certain characters appear more frequently than others, compress it by encoding frequently occuring characters with short codewords and rare ones with long codewords.

### Comments
More extensive project descriptions can be found in the corresponding pdfs.
