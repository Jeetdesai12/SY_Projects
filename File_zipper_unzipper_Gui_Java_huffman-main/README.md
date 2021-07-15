# File_zipper_unzipper_Gui_Java_huffman
File zipper and unzipper application is made using Huffman encode and decode algorithm along with using priority queue and binary tree data structure. This is GUI based application
There are total 3 classes in this code.

Main class-
This class calls other two classes.
This class works with GUI part. It creates diffrent J frames which are used in GUI.

Hzipping class-
This class has code to convert a text file into a zipped file.

Hunzipping class-
This class has code to convert zipped file into the text file back again.


Steps to build Huffman Tree-
Input is an array of unique characters along with their frequency of occurrences and output is Huffman Tree. 

Create a leaf node for each unique character and build a min heap of all leaf nodes (Min Heap is used as a priority queue. The value of frequency field is used to compare two nodes in min heap. Initially, the least frequent character is at root)
Extract two nodes with the minimum frequency from the min heap.
 
Create a new internal node with a frequency equal to the sum of the two nodes frequencies. Make the first extracted node as its left child and the other extracted node as its right child. Add this node to the min heap.
Repeat steps#2 and #3 until the heap contains only one node. The remaining node is the root node and the tree is complete.
