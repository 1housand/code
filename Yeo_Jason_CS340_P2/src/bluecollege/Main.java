package bluecollege;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
/*
 * CS340
 * Project 1
 * Jason Yeo
 */
public class Main {

	static Student[] students; //Pointers to Student threads
	static int num_students; //Counter for number of total Students
	static boolean class_in_Session = false; //Flag to indicate if class is in session 
	static volatile int session = 1; //The current class session being held.
	static volatile int tableCounter=1;
	static volatile int tableCapacity;
	static volatile int numTables;
	static volatile int tableNumber;
	static volatile int tableSitCounter=0;
	static volatile int groupCounter=1;
	static volatile int threadCount;
	static volatile int cafeStudents; //still need volatile?
	static volatile int toCafeteria;
	static boolean lock=false;
	static Semaphore bathroom = new Semaphore(1, true);
	static Semaphore class_Waiting = new Semaphore(0);
	static Semaphore classRoom = new Semaphore(0);
	static Semaphore newClass = new Semaphore(0);
	static Semaphore tables = new Semaphore(0, true);
	static Semaphore tablesLine;
	static Semaphore mutex1 = new Semaphore(1, true);
	static Semaphore mutex2 = new Semaphore(1, true);
	static Semaphore groupUp = new Semaphore(0, true);
	static Semaphore cafeteria = new Semaphore(0, true);
	static Semaphore teacherGo = new Semaphore(0);
	static Semaphore teacherFinish = new Semaphore(0);

	public static void main(String args[]){
		
		//Receiving number of students from user. Default value is 10.		
		Scanner sc = new Scanner(System.in);
		System.out.println("How many students do you want? (0 will use default value of 15)");
		num_students=sc.nextInt();
		System.out.println("How much capacity per table do you want? (0 will use default value of 4)");
		tableCapacity=sc.nextInt();
		System.out.println("How many tables do you want? (0 will use default value of 3)");
		numTables=sc.nextInt();
		sc.close();	

		//Creating student pointers in array. 
		if(num_students==0){ num_students=15; }
		if(tableCapacity==0){tableCapacity=4;}
		if(numTables==0){numTables=3;}
		
		students = new Student[num_students];
		threadCount = num_students;
		tablesLine = new Semaphore(tableCapacity*numTables, true);
		
		//Setting names and starting student threads.
		for(int i=0; i<num_students; i++){
			students[i] = new Student();
			students[i].setName("Student"+i);
			students[i].start();
		}
		
		//Creating and starting teacher thread.
		Teacher teacher = new Teacher();
		teacher.setName("Teacher"); //Setting name of thread to Teacher.
		teacher.start();
		
	}
	
	
}