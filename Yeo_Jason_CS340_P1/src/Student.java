import java.util.Random;


public class Student extends Thread{

	Random rand = new Random();
	int rand_Num; //Random number variable
	int num_Attended; //Counter for how many classes student attended
	String class_Attended = ""; //The different classes attended by student
	
	public void run(){		
		
		msg("Student born."); //Student thread created.
		
		//Student thread sleeping for random time.
		try {
			msg("Sleeping for "+nextRandom(1000)+" ms. Sweet dreams.");
			sleep(rand_Num);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		
		//Student awake and going to bathroom.
		msg("Awake! Going to bathroom.");
		Main.bRoom_Line.add(this);
		
		//If he's not in the bathroom then he's in line.
		if(Main.bRoom_Line.indexOf(this)!=0){
			msg("Oops. Bathroom occupied. Waiting.");
			yield();
		}
		
		//Wait in line until bathroom is open, then use it for a random time.
		while(true){
			if(Main.bRoom_Line.indexOf(this)==0){
				try {
					msg("Using bathroom for "+nextRandom(500)+" ms.");
					sleep(rand_Num);
					break;
				} catch (InterruptedException e){
					e.printStackTrace();
				}
			}
		}
		
		//Done with the bathroom. Who's next?
		msg("Out of the bathroom. Next!");
		Main.bRoom_Line.remove(this);
		
		for(int i=1; i<=3; i++){
			/*
			 * Student trying to take 3 classes in the auditorium
			 */
			
			//Auditorium is locked. Teacher is not here yet.
			if(Main.class_in_Session==false && Main.auditorium_Lock==true){
				msg("Classroom locked. Waiting for teacher.");
			}
			
			//Waiting for teacher to open the door.
			while(true){
				if(!(Main.class_in_Session==false && Main.auditorium_Lock==true)){
					if((Main.class_in_Session==true) && (Main.auditorium_Lock==false)){ 
						/*
						 * If the student made it to class then add them to the auditorium LinkedList,
						 * add 1 to the num_Attended counter, and add class session to the class_Attended string. 
						 */
						msg("Made it to class "+Main.session+". Phew!");
						Main.auditorium.add(this);
						class_Attended+=Main.session+" ";
						num_Attended++;
						
						//In class, wait for teacher to interrupt().
						try {
							sleep(10000);
						} catch (InterruptedException e) {}
						
						//Class is over. Increase priority and go have fun for a random time,
						//then set priority back to default.
						setPriority(4);
						try {
							msg("Having fun for "
									+ nextRandom(((2000 * Main.num_students)%7000)+1000)
									+ " ms @ Priority " + getPriority());
							sleep(rand_Num);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						setPriority(5);
						break;
					} else { //Missed class. Go run errands for random time.
						msg("Missed class "+i+"! Running errands for "
								+nextRandom(((2000*Main.num_students)%7000)+1000)+" ms.");
						try { 
							sleep(rand_Num); 
						} catch (InterruptedException e){ 
							e.printStackTrace(); 
						}
						break;
					}
				}
			}
			
			
			
			//Wait for next session to start by teacher before moving on to next class.
			while(true){
				if(i<Main.session || i==3){
					break;
				}
			}
		}
		
		//Report card for the day.
		System.out.println("["+getName()+"]  ** Number of Classes: "+num_Attended+"   Classes Attended: "+class_Attended+"**");
		
		//End of the day. Student goes back to their dorm with N-1 student.
		for(int i=Main.student.length-1; i>0; i--){
			if(this==Main.student[i]){
				if (Main.student[i - 1].isAlive()) {
					try {
						while(Main.bRoom_Line.contains(Main.student[i-1])){
							//setPriority(MIN_PRIORITY);
							msg("***********sleeping waiting for student["+Main.student[i-1]);
							sleep(nextRandom(1000));
						}
						msg("joining " + Main.student[i - 1].getName());
						Main.student[i - 1].join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else { break; }
			}
		}
		msg("Went to their dorm.");
	}
	
	/*
	 *			//End of the day. Student goes back to their dorm with N-1 student.
			for(int i=Main.student.length-1; i>0; i--){
				if(this==Main.student[i]){
					for(int j=1; j<=i; j++){
						if (Main.student[i-j].isAlive()) {
							msg("joining " + Main.student[i - j].getName());
							Main.student[i-j].join();
							break;
						}
					}
				}
				if(this==Main.student[0]){Main.teacherRelease.release(2);}
			}
			msg("Went to their dorm."); 
	 */
	
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
