package jchord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.Scanner;


public class Main1 {
    
        //declare required strings
	public static final String HASH_FUNCTION = "SHA-1";

	public static final int KEY_LENGTH = 160;

	public static int NUM_OF_NODES=10;
        
        //used for menu loop
        public static int i=-1;
        
        public static Chord chord = new Chord();
        
        
          
            //initiate stream to write on file
		public static PrintStream out = System.out;
                
            //start timer
		public static long start = System.currentTimeMillis();    
                          
       //main function
        public static void main(String[] args) throws Exception {
            menu();
            
            //initiate stream to write on file
		PrintStream out = System.out;
            //declare output file
		out = new PrintStream("result.log");
            
            
                //specify hash function
		Hash.setFunction(HASH_FUNCTION);
                
                //specify hash key length
		Hash.setKeyLength(KEY_LENGTH);

                
                
                
                
		
		}
        public static void menu() throws IOException{
            
            //declare output file
		out = new PrintStream("result.log");
                System.out.println("Please enter number from 0-6");
                System.out.println("\n\t1.InitChord()");
                System.out.println("\n\t2.AddPeer()");
                System.out.println("\n\t3.Findkey()");
                System.out.println("\n\t4.Insert_data()");
                System.out.println("\n\t5.Delete()");
                System.out.println("\n\t6.Print()");
                System.out.println("\n\t0.Stop()");
                Scanner scanner = new Scanner(System.in);
                i = scanner.nextInt();
            switch(i)
            {
        case 1:
            InitChord();
            break;
        case 2:
            Addpeer();
            break;
        case 3:
            FindKey();
            break;
        case 4:
            Insert_data();
            break;
        case 5:
            DeleteIt();
            break;
        case 6:
            PrintIt();
            break;
        case 0:
            break;
            
        default:
            menu();
                
            }
          
        }
        
        public static void PrintIt(){
            

        }
        public static void DeleteIt() throws IOException{
        System.out.println("Enter node ID");
                InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String nodeID = reader.readLine();
                int nodeId=Integer.parseInt(nodeID);
                ChordNode.settonull(nodeId);
                System.out.println("Node at ID:"+nodeID+" is set to null");
                menu();
        
        }
        public static void Insert_data() throws IOException{
                System.out.println("Enter node ID");
                InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String nodeID = reader.readLine();
                
                System.out.println("\nEnter data:");
                String data = reader.readLine();
                        
                System.out.println("Data:"+data+" added at "+nodeID);    
                
                menu();
        
        
        }
        public static void FindKey() throws IOException{
                System.out.println("Enter node ID");
                InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String nodeID = reader.readLine();
                ChordKey key= new ChordKey(nodeID);
               byte[] gKey= key.getKey();
              
               
               StringBuilder sb = new StringBuilder();
		sb.append("ChordNode[");
		sb.append("ID=" + nodeID);
		sb.append(",KEY=" + gKey);
		sb.append("]");
		sb.toString();
               
               System.out.println(sb);
               menu();
                
        }
        public static void Addpeer() throws IOException{
                System.out.println("Enter node ID");
                InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String name = reader.readLine();
                int nodeID=Integer.parseInt(name);
                
                //get ip address of host
                String host = InetAddress.getLocalHost().getHostAddress();
                //set port number
		int port = 9000;
                if(nodeID!=NUM_OF_NODES){
                Chord chord = new Chord();
                URL url = new URL("http", host, port + NUM_OF_NODES, "");
			try {
				chord.createNode(url.toString());
                                System.out.println("Node inserted at "+nodeID+" successfully with finger table!");
                                menu();
			} catch (ChordException e) {
				System.exit(0);
			}
                }
                else{
                System.out.println("Error! try again!");
                menu();
                }
            
        }
        public static void InitChord() throws IOException{
        
        //prompt user to enter number of nodes between 2 to 2^31
                System.out.println("Enter number of nodes(2-2^31):");
                InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String name = reader.readLine();
                NUM_OF_NODES=Integer.parseInt(name);
                
                
                System.out.println("Student name + roll no");
                
                //get ip address of host
                String host = InetAddress.getLocalHost().getHostAddress();
                //set port number
		int port = 9000;
                
                
                URL url = new URL("http", host, port + NUM_OF_NODES, "");
			try {
				chord.createNode(url.toString());
                                System.out.println("Created index node successfully with finger table!");
                                menu();
			} catch (ChordException e) {
				System.exit(0);
			}
                        
                for (int i = 0; i < NUM_OF_NODES; i++) {
			ChordNode node = chord.getSortedNode(i);
			System.out.println(node);
		}
                for (int i = 1; i < NUM_OF_NODES; i++) {
			ChordNode node = chord.getNode(i);
			node.join(chord.getNode(0));
			ChordNode preceding = node.getSuccessor().getPredecessor();
			node.stabilize();
			if (preceding == null) {
				node.getSuccessor().stabilize();
			} else {
				preceding.stabilize();
			}
		}
		System.out.println("Chord ring is established.");

		for (int i = 0; i < NUM_OF_NODES; i++) {
			ChordNode node = chord.getNode(i);
			node.fixFingers();
		}
		System.out.println("Finger Tables are fixed.");

		for (int i = 0; i < NUM_OF_NODES; i++) {
			ChordNode node = chord.getSortedNode(i);
			node.printFingerTable();
		}

		long end = System.currentTimeMillis();

		int interval = (int) (end - start);
		System.out.printf("Elapsed Time : %d.%d", interval / 1000,
				interval % 1000);
	

        
        }
}
