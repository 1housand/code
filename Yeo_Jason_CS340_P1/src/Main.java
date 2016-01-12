import java.util.LinkedList;
import java.util.Scanner;
/*
 * CS340
 * Project 1
 * Jason Yeo
 */
public class Main {

	static Student[] student; //Pointers to Student threads
	static int num_students; //Counter for number of total Students
	static volatile LinkedList<Student> bRoom_Line = new LinkedList<Student>(); //Queue for bathroom
	static LinkedList<Student> auditorium = new LinkedList<Student>(); //LinkedList for auditorium
	static volatile boolean class_in_Session = false; //Flag to indicate if class is in session 
	static volatile boolean auditorium_Lock = true; //Lock for the auditorium door
	static volatile int session; //The current class session being held.
	
	public static void main(String args[]){
		
		//Receiving number of students from user. Default value is 10.		
		Scanner sc = new Scanner(System.in);
		System.out.println("How many students do you want to run? (0 will use a default value of 10)");
		num_students=sc.nextInt();
		sc.close();	

		//Creating student pointers in array. 
		if(num_students==0){ num_students=10; }
		student = new Student[num_students];
		
		//Setting names and starting student threads.
		for(int i=0; i<num_students; i++){
			student[i] = new Student();
			student[i].setName("Student"+i);
			student[i].start();
		}
		
		//Creating and starting teacher thread.
		Teacher teacher = new Teacher();
		teacher.start();
		
	}
	
}
