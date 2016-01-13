package bluecollege;

import java.util.Random;

public class Student extends Thread{

	Random rand = new Random();
	int rand_Num; //Random number variable
	int num_Attended; //Counter for how many classes student attended
	String class_Attended = ""; //The different classes attended by student
	int groupNum;
	
	public void run(){		
		
		msg("Student born."); //Student thread created.
		
		try {
			msg("Sleeping for "+nextRandom(1000)+" ms. Sweet dreams."); //Student thread sleeping for random time.
			sleep(rand_Num);
			msg("Awake! Going to bathroom."); //Student awake and going to bathroom.
			
			if(Main.bathroom.availablePermits()<1){ //If he's not in the bathroom then he's in line.
				msg("Oops. Bathroom occupied. Waiting."); //Wait in line until bathroom is open, then use it for a random time.
			}
			
			Main.bathroom.acquire();
			msg("Using bathroom for "+nextRandom(500)+" ms.");
			sleep(rand_Num);
			msg("Out of the bathroom. Next!"); //Done with the bathroom. Who's next?
			Main.bathroom.release();
		
			while(Main.session<=3){ 
			//Student trying to take 3 classes in the auditorium
				if(!Main.class_in_Session){
					//Auditorium is locked. Teacher is not here yet.
					msg("Classroom locked. Waiting for teacher."); 	//Waiting for teacher to open the door.
					Main.class_Waiting.acquire(); //If the student made it early to class then add them to class_Waiting,
					
					msg("Made it to class "+Main.session+". Phew!");
					class_Attended+=Main.session+" ";
					num_Attended++;
					Main.classRoom.acquire(); //Waiting for class to end.
					msg("Finished class "+Main.session+"!");
					
				} else { //Missed class. Go run errands for random time.
					msg("Missed class "+Main.session+"! Running errands for "
							+nextRandom(((200*Main.num_students)%5000)+500)+" ms.");
					sleep(rand_Num);
				}
			Main.newClass.acquire();
			}
			

			Main.toCafeteria++;
			if(Main.toCafeteria==Main.num_students){
				Main.cafeteria.release(Main.num_students+1);
			}
			Main.cafeteria.acquire();
			
			msg("Heading to cafeteria!");
			
			//grouping up the students
			Main.mutex1.acquire();
				Main.cafeStudents++;
				if(Main.tableCapacity==1){
					groupUp();
					Main.mutex1.release();
				} else if(Main.cafeStudents%Main.tableCapacity==0 
						|| Main.cafeStudents==Main.num_students){
					groupUp();
					Main.mutex1.release();
					Main.groupUp.release(Main.tableCapacity-1);
				} else {
					groupUp();
					Main.mutex1.release();
					Main.groupUp.acquire();
				}
				
			//students wait in line for a table if it's full
			Main.tablesLine.acquire();
			
			//seating the students at a table
			Main.mutex2.acquire();
			msg("Sitting at table with group "+groupNum);
			Main.tableSitCounter++; 
			if(Main.tableSitCounter==Main.tableCapacity*Main.numTables
					|| Main.tableSitCounter==Main.num_students){
				if(!Main.lock){
					Main.lock=true;
					msg("Teacher, help!");
					Main.teacherFinish.release();
				}
				Main.mutex2.release();
				Main.tables.acquire();
			} else if(Main.num_students<Main.tableCapacity){
				Main.tables.release(Main.num_students);
				Main.mutex2.release();
				Main.tables.acquire();
			} else {
				Main.mutex2.release();
				Main.tables.acquire();
			}

			/*
			 * (Main.tableCapacity*Main.numTables==Main.num_students
					&& Main.tableSitCounter==Main.num_students) || 
					
			 * if(Main.tables.getQueueLength()<Main.numTables*Main.tableCapacity 
					&& Main.tableSitCounter<Main.num_students){
				Main.mutex2.release();
				Main.tables.acquire();
			} else {
				msg("Tables full. Teacher, help!");
				Main.teacherFinish.release();
				Main.mutex2.release();
				Main.tables.acquire();
			}
			 */
			
			msg("Left table with group "+groupNum);
			
			Main.teacherGo.release();
			
			//Report card for the day.
			System.out.println("["+getName()+"] * Number of Classes: "
					+num_Attended+" * Classes Attended: "+class_Attended+"*");
			//End of the day. Student goes back to their dorms.
			msg("Went to their dorm.");
			
			//teacher goes home too
			Main.threadCount--;
			if(Main.threadCount<=0){
				msg("Good night teacher!");
				Main.teacherGo.release(Main.num_students);
				Main.teacherFinish.release(2);
			}
			
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public void groupUp(){
		groupNum=Main.groupCounter;
		msg("In group "+groupNum);
		if(Main.cafeStudents%Main.tableCapacity==0){
			Main.groupCounter++;
		}
	}
	
	//Messaging system
	public static long time = System.currentTimeMillis();
	public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
	
	//Random number variable updater
	private int nextRandom(int ms){
		return rand_Num=rand.nextInt(ms)+500;
	}	
}
