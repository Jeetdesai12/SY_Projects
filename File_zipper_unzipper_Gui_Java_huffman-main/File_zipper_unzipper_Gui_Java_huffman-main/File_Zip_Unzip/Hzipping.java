package File_Zip_Unzip;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.PriorityQueue;

//if the frequency of a byte is more than 2^32 then there will be problem
public class Hzipping {

    static PriorityQueue<Node> pq = new PriorityQueue<Node>();
    static int[] freq = new int[300];   // for keeping frequncies of all the bytes
    static String[] ss = new String[300];
    static int extra_bits;   
    static byte bt;   // to store byte
    static int count; // number of different characters

    //Bianry tree class
    static class Node implements Comparable<Node>{
        Node Lchild;       //nodes used in binary tree and pq
	Node Rchild;
	public String character;  //variable
	public int Bite;
	public int frequency;

	public int compareTo(Node T){
            if (this.frequency < T.frequency)  
                return -1;  //to end
            if (this.frequency > T.frequency)
		return 1;  //to continue
            return 0;
	}
    }
    
    static Node Root;

/************************* calculating frequency of file fname*********************************/
    public static void CalFreq(String fname){
       
	File file = new File(fname); //providing path of desired file
	Byte bt;

	try{
            DataInputStream data_in = new DataInputStream(new FileInputStream(file)); //accessing the data using data_in variable
            while(true){
		try {
                    bt = data_in.readByte();   //reading using byte data type
                    freq[bt.intValue()]++;     //converting in int and inc frequency
		}catch(EOFException eof) {
                    System.out.println("End of File");
                    break;                     //breaking the loop due to end of file exception
		}
            }
            //System.out.println(freq);
            data_in.close();            //closing it after eof
	} catch (IOException e) {
            System.out.println("IO Exception =: " + e);  //printing exception
	}
	file = null;    //making local variable null
    }

/**************************** byte to int conversion*************************************/
    public static int to(Byte b) {
	int ret = b;
	if (ret < 0){
            ret = ~b;
            ret = ret + 1;
            ret = ret ^ 255;
            ret += 1;
	}
	return ret;
    }

/***************************** freeing the memory************************************************/
    public static void initHzipping() {
	count = 0;
	if (Root != null)
            freeDFS(Root); //passing root if not null
        for (int i = 0; i < 300; i++)
            freq[i] = 0;   //initializing all the  the elements in freq array as 0
	for (int i = 0; i < 300; i++)
            ss[i] = "";    //initializing all the  the elements in string 
	pq.clear();        //clearing the pq
    }

/***************************************** DFS to free memory***********************************/
    public static void freeDFS(Node now) {
	if (now.Lchild == null && now.Rchild == null) {
		now = null;  //making root null if its left and right child are null
		return;
	}
	if (now.Lchild != null)
            freeDFS(now.Lchild);  //passing left child to make it null
	if (now.Rchild != null)
            freeDFS(now.Rchild);  ////passing right child to make it null
    }

/********************************* DFS to make the codes***********************************************/
    public static void DFS(Node now, String st) {
	now.character = st;   //storing string which is ""
	if ((now.Lchild == null) && (now.Rchild == null)) {
		ss[now.Bite] = st;   //Assigning ""  to bite ss string
		return;
	}
	if (now.Lchild != null) 
            DFS(now.Lchild, st + "0");
        if (now.Rchild != null)
            DFS(now.Rchild, st + "1");
       // assigning 0 and 1 till L&R child are not null
    }
    
/********************** Making all the nodes in a priority Q making the tree**************************/
    public static void MakeNode() {
	pq.clear();

	for (int i = 0; i < 300; i++) {
            if (freq[i] != 0) 
            {
		Node Temp = new Node();  //creating object of node type
		Temp.Bite = i;           
		Temp.frequency = freq[i]; //assigning values of frequency to frequency part of temp node 
		Temp.Lchild = null;
                Temp.Rchild = null;
		pq.add(Temp);      //adding values in priority queue according to FIFO
		count++;
            }
	}
        
	Node Temp1, Temp2;  //objects of node
        
	if (count == 0){   //return if count is 0
            return;
	}else if (count == 1)   //
        {
            for (int i = 0; i < 256; i++)
		if (freq[i] != 0)        //storing 0 in string array 
                {
                    ss[i] = "0";
                    break;
		}
            return;
	}
        
	while (pq.size() != 1) {
	Node Temp = new Node();
	Temp1 = pq.poll();      //polling value in temp1
	Temp2 = pq.poll();      //polling value in temp2
	Temp.Lchild = Temp1;   //adding temp 1 value in Lchild
	Temp.Rchild = Temp2;   //adding temp 2 value in Rchild
	Temp.frequency = Temp1.frequency + Temp2.frequency;
	pq.add(Temp);
	}
	Root = pq.poll();     //If only single element left in pq it will be assigned as root
    }
    

/********************************************************************************
	fake zip creates a file "fakezip.txt" where puts 
        the final binary codes of the real zipped file
*******************************************************************************/
    public static void fakezip(String fname) 
    {
	
	File fileIn = new File(fname);               //reading the file
	File fileOut = new File("fakezipped.txt");   //creating var to print the file
        
            try {
                DataInputStream data_in = new DataInputStream(new FileInputStream(fileIn));
		PrintStream ps = new PrintStream(fileOut);  //ps to store file before printing 

		while(true){
			try {
                            bt = data_in.readByte();   //it reads data in byte form
                            ps.print(ss[to(bt)]);      //printing
			}catch (EOFException eof) {
                            System.out.println("End of File");  //exception of EOF
                            break;
			}
		}
                data_in.close();
                ps.close();
            }catch (IOException e) {
		System.out.println("IO Exception =: " + e);  
            }
            //making all FILE var as null
	fileIn = null;
	fileOut = null;
    }

/******************************real zip according to codes of fakezip.txt (fname)****************************/
    public static void realzip(String fname, String fname1) {
	
	Byte btt;
	File fileIn = new File(fname);  //File in var
	File fileOut = new File(fname1); //printing var
                
	try {
            DataInputStream data_in = new DataInputStream(new FileInputStream(fileIn)); 
            DataOutputStream data_out = new DataOutputStream(new FileOutputStream(fileOut));

            data_out.writeInt(count); //writing in file
            
            for (int i = 0; i < 300; i++) {
		if(freq[i] != 0){
                    btt = (byte) i;
                    data_out.write(btt);
                    data_out.writeInt(freq[i]);  //writing zipped data file
		}
            }
            
            long textra_bits;
            textra_bits = fileIn.length() % 8;
            textra_bits = (8 - textra_bits) % 8;  //to get its value
            extra_bits = (int) textra_bits;
            data_out.writeInt(extra_bits);
            
            while (true) {
		try {
                    bt = 0;
                    byte ch;
                    for (extra_bits = 0; extra_bits < 8; extra_bits++) {
			ch = data_in.readByte();
			bt *= 2;
			if (ch == '1')
                            bt++;
                    }
                    data_out.write(bt);
		}catch(EOFException eof){
                    if(extra_bits != 0){
			for(int x = extra_bits; x < 8; x++){
                            bt *= 2;
			}
			data_out.write(bt);
                    }

                    extra_bits = (int) textra_bits;
                    System.out.println("extrabits: " + extra_bits);
                    System.out.println("End of File");
                    break;
		}
            }
            data_in.close();
            data_out.close();
            System.out.println("output file's size: " + fileOut.length());
	}catch(IOException e){
            System.out.println("IO exception = " + e);
	}
	fileIn.delete();
	fileIn = null;
	fileOut = null;
    }
/**********************************************************************************/
    public static void beginHzipping(String arg1) {
	initHzipping();
	CalFreq(arg1); // calculate the frequency of each digit
	MakeNode(); // makeing corresponding nodes
	if (count > 1)
            DFS(Root, ""); // dfs to make the codes
	fakezip(arg1); // fake zip file which will have the binary of the input to fakezipped.txt file
	realzip("fakezipped.txt", arg1 + ".zip"); // making the real zip according the fakezip.txt file
	initHzipping();
    }
}