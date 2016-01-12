package bluecollege;

public class Teacher extends Thread{
	
	public void run(){
		
		try { 
			Main.classRoom.drainPermits();
			Main.class_Waiting.drainPermits();
			sleep(((1000*Main.num_students)%3000)+1000); //Sleep for a bit until going to auditorium
			
			while(Main.session<=3){
				
				//Teacher going to auditorium to open the door for waiting students
				msg("Yoohoo! On my way to class "+Main.session+"!");
				sleep(800*Main.session);
				
				//Doors open. Letting students in to class.
				msg("The auditorium is open!");
				Main.class_Waiting.release(Main.num_students);
				sleep(700);
				
				//Locking doors because class in session.
				msg("Class "+Main.session+" in session!");
				Main.newClass.drainPermits();
				Main.class_in_Session=true;
				sleep(2000); //Class in session.
				

				msg("Class "+Main.session+" is over!");
				Main.classRoom.release(Main.num_students);
				Main.class_in_Session=false;
				Main.class_Waiting.drainPermits();
				sleep(1000);
				
				Main.newClass.release(Main.num_students);
				Main.session++;
				Main.classRoom.drainPermits();
			}
			
			Main.cafeteria.acquire();
			msg("Heading to cafeteria!");
			
			//tables full
			Main.teacherFinish.acquire();
			if(!Main.tablesLine.tryAcquire()){
				msg("I am here to save the day!");		
			}
			
			//assign tables
			while(Main.threadCount>0){
				if(Main.tableSitCounter==Main.num_students 
						&& Main.tables.getQueueLength()==Main.num_students%Main.tableCapacity){
					bigRelease(Main.num_students%Main.tableCapacity);
				} else {
					bigRelease(Main.tableCapacity);
				}
			} 
			
			Main.teacherFinish.acquire();
			msg("Good night students!");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void bigRelease(int x) throws InterruptedException{
		Main.tables.release(x);
		Main.teacherGo.acquire(x);
		if(Main.tablesLine.hasQueuedThreads()){
			msg("A table now available.");
		}
		Main.tablesLine.release(x);
	}
	
	//Messaging system
	public static long time = System.currentTimeMillis();
	public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}

	
}
