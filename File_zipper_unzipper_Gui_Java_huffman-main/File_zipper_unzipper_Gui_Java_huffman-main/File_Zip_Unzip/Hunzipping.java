package File_Zip_Unzip;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.PriorityQueue;

public class Hunzipping {
    static PriorityQueue<Node> pq1 = new PriorityQueue<Node>();
    static int[] freq1 = new int[300];
    static String[] ss1 = new String[300]; // INT TO CODE
    static String[] btost = new String[300]; // INT TO BIN
    static String big_string; // THE BIG STRING
    static String temp; // TEMPORARY STRING
    static int extra_bits1; // EXTRA BITS ADDED AT THE LAST TO MAKE THE FINAL ZIP CODE MULTIPLE OF 8
    static int putit; //
    static int count1; // NUMBER OF freqs available

    static class Node implements Comparable<Node> {
	Node Lchild;
	Node Rchild;
	public String character;
	public int Bite;
	public int frequency1;

	public int compareTo(Node T) {
            if (this.frequency1 < T.frequency1)
                return -1;
            if (this.frequency1 > T.frequency1)
		return 1;
            return 0;
	}
    }
    static Node Root;

/********************* freeing the memory *******************************************/
    public static void initHunzipping() {
	int i;
	if (Root != null)
            freeDFS1(Root);
        for (i = 0; i < 300; i++)
            freq1[i] = 0;
        for (i = 0; i < 300; i++)
            ss1[i] = "";
        pq1.clear();
        big_string = ""; 
        temp = ""; 
        extra_bits1 = 0; 
        putit = 0; 
        count1 = 0;
    }
    
/***********************************DFS1 to free memory*********************************************/
    public static void freeDFS1(Node now) {
	if(now.Lchild == null && now.Rchild == null){
            now = null;
            return;
	}
	if (now.Lchild != null)
            freeDFS1(now.Lchild);
	if (now.Rchild != null)
            freeDFS1(now.Rchild);
    }
    
/**********************************DFS to make the codes *****************************************/
    public static void DFS1(Node now, String st) {
	now.character = st;
	if ((now.Lchild == null) && (now.Rchild == null)) {
            ss1[now.Bite] = st;
            return;
	}
	if (now.Lchild != null)
            DFS1(now.Lchild, st + "0");
	if (now.Rchild != null)
            DFS1(now.Rchild, st + "1");
    }
    
/**************** Making all the nodes in a priority Q making the tree**********************************/
    public static void MakeNode1() {
	count1 = 0;
	for (int i = 0; i < 300; i++) {
		if (freq1[i] != 0) {
                    Node Temp = new Node();
                    Temp.Bite = i;
                    Temp.frequency1 = freq1[i];
                    Temp.Lchild = null;
                    Temp.Rchild = null;
                    pq1.add(Temp);
                    count1++;
		}
	}
	Node Temp1, Temp2;
        
	if(count1 == 0){
            return;
	}else if(count1 == 1) {
            for(int i = 0; i < 300; i++){
		if (freq1[i] != 0) {
                    ss1[i] = "0";
                    break;
		}
            }
            return;
	}

	while (pq1.size() != 1) {
            Node Temp = new Node();
            Temp1 = pq1.poll();
            Temp2 = pq1.poll();
            Temp.Lchild = Temp1;
            Temp.Rchild = Temp2;
            Temp.frequency1 = Temp1.frequency1 + Temp2.frequency1;
            pq1.add(Temp);
	}
        Root = pq1.poll();
    }
/******************** reading the freq1 from "codes.txt"//updating ss********************/
    public static void readFreq1(String cc) {
        File fileIn = new File(cc);
	int f;
	Byte baital;
	try {
            DataInputStream data_in = new DataInputStream(new FileInputStream(fileIn));
            count1 = data_in.readInt();

            for (int i = 0; i < count1; i++) {
		baital = data_in.readByte();
		f = data_in.readInt();
		freq1[to(baital)] = f;
            }
            data_in.close();
	} catch (IOException e) {
            System.out.println("IO exception = " + e);
	}

	MakeNode1(); // making corresponding nodes
	if (count1 > 1)
            DFS1(Root, ""); // dfs1 to make the codes

	for(int i = 0; i < 256; i++){
            if (ss1[i] == null)
		ss1[i] = "";
	}
	fileIn = null;
    }

/******************************int to bin string conversion code creating*************************/
    public static void createBin() {
	int i, j;
	String t;
	for (i = 0; i < 256; i++) {
            btost[i] = "";
            j = i;
            while(j != 0){
		if (j % 2 == 1)
                    btost[i] += "1";
                else
                    btost[i] += "0";
		j /= 2;
            }
            t="";
            for (j = btost[i].length() - 1; j >= 0; j--) {
		t += btost[i].charAt(j);
            }
            btost[i] = t;
	}
	btost[0] = "0";
    }

/********** got yes means temp is a valid code and
 *           putit is the code's corresponding value***************************************/
    public static int got() {
	for (int i = 0; i < 256; i++) {
            if (ss1[i].compareTo(temp) == 0) {
		putit = i;
		return 1;
            }
	}
	return 0;
}

/***************** byte to int conversion*****************************/
    public static int to(Byte b) {
	int ret = b;
	if (ret < 0) {
		ret = b& 0xFF;
	}
	return ret;
    }

/************* convert any string into eight digit string************************/
    public static String makeEight(String b) {
	String ret = "";
	int len = b.length();
	for (int i = 0; i < (8 - len); i++)
            ret += "0";
        ret += b;
	return ret;
    }

/***************** unzipping function**************************/
    public static void readBin(String zip, String unzip) {
	int ok, bt;
	Byte b;
	int j;
	big_string = "";
	File f1 = new File(zip);
	File f2 = new File(unzip);
	try {
            DataOutputStream data_out = new DataOutputStream(new FileOutputStream(f2));
            DataInputStream data_in = new DataInputStream( new FileInputStream(f1));
            try {
		count1 = data_in.readInt();
		System.out.println(count1);
		for (int i= 0; i < count1; i++) {
                    b = data_in.readByte();
                    j = data_in.readInt();
		}
		extra_bits1 = data_in.readInt();
		System.out.println(extra_bits1);
            } catch (EOFException eof) {
		System.out.println("End of File");
            }
            
            while (true) {
                try {
                    b = data_in.readByte();
                    bt = to(b);
                    big_string += makeEight(btost[bt]);
                    while (true) {
                        ok = 1;
                        temp = "";
                        for (int i = 0; i < big_string.length() - extra_bits1; i++) {
                            temp += big_string.charAt(i);
                            if (got() == 1) {
                                data_out.write(putit);
                                ok = 0;
                                String s = "";
                                for (j = temp.length(); j < big_string.length(); j++) {
                                    s += big_string.charAt(j);
                                }
                                big_string = s;
                                break;
                            }
                        }
                        if (ok == 1)
                            break;
                    }   
                } catch (EOFException eof) {
                    System.out.println("End of File");
                    break;
                }
            }
            data_out.close();
            data_in.close();
	}catch (IOException e) {
            System.out.println("IO Exception =: " + e);
	}
	f1 = null;
	f2 = null;
    }
    
/************************************************************************************/

    public static void beginHunzipping(String arg1) {
	initHunzipping();
        readFreq1(arg1);
	createBin();
	int n = arg1.length();
	String arg2 = arg1.substring(0, n - 6);
	readBin(arg1, arg2);
	initHunzipping();
	}
}