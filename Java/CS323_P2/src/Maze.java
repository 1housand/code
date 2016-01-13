/*
 * CS323 Project 2
 * Jason Yeo
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
import java.util.Random;

public class Maze {

	static MazeBlock[][] maze; //The array board for the maze
	static int sizeR, sizeC; //Amount of rows and columns
	static Stack<MazeBlock> backTrack = new Stack<MazeBlock> (); //Backtracking stack
	static Random rand = new Random(); //Random number
	static int rand_Num;
	static MazeBlock marker; //current place of the maze runner
	static String outputFile;
	static Stack<MazeBlock>	path = new Stack<MazeBlock> ();
	
	//If no dimensions are taken in then uses default settings
	public static void main(String[] args){
		if (args.length==0) {System.out.println("A 20x20 random maze will be printed to output.txt");
			sizeR=20;
			sizeC=20;
			maze=new MazeBlock[sizeR][sizeC];
			outputFile="output.txt";
			
			mazeRunner();
			mazePrinter();
		
		} else { //Using user's settings
			Scanner sc;
			try {
				sc = new Scanner(new FileReader(args[0]));
				sizeR = sc.nextInt(); //add user's dimensions
				sizeC = sc.nextInt();
				sc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			maze=new MazeBlock[sizeR][sizeC]; //create the maze board with the user's dimensions
			outputFile=args[1]; //name of output file desired by user
			System.out.println("A "+sizeR+"x"+sizeC+" random maze will be printed to "+outputFile);
			
			mazeRunner();
			mazePrinter();	
		}
	}
	
	//Keeps running through the array looking for a path
	private static void mazeRunner(){
		
		for(int i=0; i<sizeR; i++){ //Initialize the maze array
			for(int j=0; j<sizeC; j++){
				maze[i][j]=new MazeBlock(); //create MazeBlock object in each array index
				maze[i][j].r=i;
				maze[i][j].c=j;
				Arrays.fill(maze[i][j].walls, Boolean.TRUE); //make walls for all 4 sides
			}
		}
		
		maze[0][0].walls[0]=false; //make maze entrance
		maze[sizeR-1][sizeC-1].walls[2]=false; //make maze exit
		maze[0][0].visited=true; //start from [0,0]
		backTrack.push(maze[0][0]); //put [0,0] starting point on the stack
		boolean[] crossRoad = new boolean[4]; //array that keeps track if all directions were checked
		
		while(true){ //keeps looping until every path has been made
			marker=nextStep(backTrack.peek()); //marker is starting point. look for next random step
			
			while(backTrack.peek()==marker){ //loops until it finds an open path
				crossRoad[rand_Num]=true; //first blocked path
				int deadEnd = 0; //keeps track of how many directions are blocked
				for(int i=0; i<crossRoad.length; i++){
					if(crossRoad[i]==true){deadEnd++;} //if a direction is blocked then increment dead end counter
				}
				if(deadEnd==4){ //this is a dead end
					backTrack.pop(); //backtrack to previous step
					Arrays.fill(crossRoad, Boolean.FALSE); //reset crossRoad array because new starting point
					if(backTrack.isEmpty()){ break;} //stack is empty, we are done
				}
				marker=nextStep(backTrack.peek()); //new starting point. look for next random step
			} 
			
			if(backTrack.isEmpty()){ break;} //stack is empty, we are done
			else { //this path is good
				marker.visited=true; //mark as visited so we don't visit again
				walls(); //set up proper wall configuration for the path
				backTrack.push(marker); //put the step on the stack
				if(marker.r==sizeR-1 && marker.c==sizeC-1){path();}
			}				
		}
	}
	
	static void path(){
		Stack<MazeBlock> backTrack2 = new Stack<MazeBlock> ();
		while(!backTrack.empty()){
			backTrack2.push(backTrack.peek());
			path.push(backTrack.pop());
		}
		while(!backTrack2.empty()){
			backTrack.push(backTrack2.pop());
		}
	}
	
	//finds the next step by random number
	static MazeBlock nextStep(MazeBlock x){ 
		rand_Num = rand.nextInt(4); //randomly generate next step
		if(rand_Num==0){ //go West
			if(x.c-1<0){ return x; }
			else if(maze[x.r][x.c-1].visited==true){
				return x;
			} else { return maze[x.r][x.c-1]; }
		} else if (rand_Num==1){ //go South
			if(x.r+1>=sizeR){ return x; }
			else if(maze[x.r+1][x.c].visited==true){
				return x;
			} else {return maze[x.r+1][x.c];}
		} else if (rand_Num==2){ //go East
			if(x.c+1>=sizeC){ return x; }
			else if(maze[x.r][x.c+1].visited==true){
				return x;
			} else {return maze[x.r][x.c+1];}
		} else { //go North
			if(x.r-1<0){ return x; }
			else if(maze[x.r-1][x.c].visited==true){
				return x;
			} else {return maze[x.r-1][x.c];}
		}
	}
	
	//configure walls for the path that was just found
	static void walls(){
		if(rand_Num==0){ //West wall
			backTrack.peek().walls[0]=false;
			marker.walls[2]=false;
		}
		else if (rand_Num==1){ //South wall
			backTrack.peek().walls[1]=false;
		}
		else if (rand_Num==2){ //East wall
			backTrack.peek().walls[2]=false;
			marker.walls[0]=false;
		} else {marker.walls[1]=false;} //North wall
	}
	
	//Print the maze according to variables 
	private static void mazePrinter(){ 
		for(int i=-1; i<sizeR; i++){
			for(int j=0; j<sizeC; j++){
				if(i==-1){ System.out.print("__");} //print top border
				else{
					if(maze[i][j].walls[0]==true){
						System.out.print("|");
					} else {
						System.out.print("_");
					}	
					if(maze[i][j].walls[1]==true){
						System.out.print("_");
					} else {
						System.out.print(" ");
					}
					if(j==sizeC-1 && i!=sizeR-1){
						System.out.print("|");
					}
				}
			}
			System.out.println();
		}
		System.out.println();
		
		//print solution path
		char[][] solution = new char[sizeR][sizeC];
		while(!path.empty()){
			solution[path.peek().r][path.peek().c]='x';
			path.pop();
		}
		//print solution to screen
		for(int i=-1; i<sizeR; i++){
			for(int j=0; j<sizeC; j++){
				if(i==-1){ System.out.print("__");} //print top border
				else{
					if(maze[i][j].walls[0]==true){
						System.out.print("|");
					} else {
						System.out.print("_");
					}	
					if(solution[i][j]=='x'){
						System.out.print("x");
					} else if(maze[i][j].walls[1]==true) {
						System.out.print("_");
					} else {System.out.print(" ");}
					if(j==sizeC-1 && i!=sizeR-1){
						System.out.print("|");
					}
				}
			}
			System.out.println();
		}	
		
		
		//Print the maze to the output file specified
		File file = new File(outputFile); //create file for output
		if(!file.exists()){
			try {
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				
				for(int i=-1; i<sizeR; i++){
					for(int j=0; j<sizeC; j++){
						if(i==-1){ bw.write("__");} //print top border
						else{
							if(maze[i][j].walls[0]==true){
								bw.write("|");
							} else {
								bw.write("_");
							}	
							if(maze[i][j].walls[1]==true){
								bw.write("_");
							} else {
								bw.write(" ");
							}
							if(j==sizeC-1 && i!=sizeR-1){
								bw.write("|");
							}
						}
					}
					bw.newLine();
				}
				
				for(int i=-1; i<sizeR; i++){
					for(int j=0; j<sizeC; j++){
						if(i==-1){ bw.write("__");} //print top border
						else{
							if(maze[i][j].walls[0]==true){
								bw.write("|");
							} else {
								bw.write("_");
							}	
							if(solution[i][j]=='x'){
								bw.write("x");
							} else if(maze[i][j].walls[1]==true) {
								bw.write("_");
							} else {bw.write(" ");}
							if(j==sizeC-1 && i!=sizeR-1){
								bw.write("|");
							}
						}
					}
					bw.newLine();
				}
				
				for(int i=-1; i<sizeR; i++){
					for(int j=0; j<sizeC; j++){
						if(i==-1){ System.out.print("__");} //print top border
						else{
							if(maze[i][j].walls[0]==true){
								System.out.print("|");
							} else {
								System.out.print("_");
							}	
							if(solution[i][j]=='x'){
								System.out.print("x");
							} else if(maze[i][j].walls[1]==true) {
								System.out.print("_");
							} else {System.out.print(" ");}
							if(j==sizeC-1 && i!=sizeR-1){
								System.out.print("|");
							}
						}
					}
					System.out.println();
				}
				
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
