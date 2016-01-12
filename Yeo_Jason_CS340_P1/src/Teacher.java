
public class Teacher extends Thread{
	
	public void run(){
		
		setName("Teacher"); //Setting name of thread to Teacher.
		try { sleep(((1000*Main.num_students)%3000)+1000); } //Sleep for a bit until going to auditorium
		catch (InterruptedException e){ e.printStackTrace(); }
		
		for(int i=1; i<=3; i++){
			Main.session=i; //Setting the class session number

			//Teacher going to auditorium to open the door for waiting students
			msg("On my way to class "+i+"!");
			try {
				sleep(((1000*Main.num_students)%4000)+1000);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			
			//Doors open. Letting students in to class.
			msg("The auditorium is open!");
			Main.class_in_Session=true;
			Main.auditorium_Lock=false;
			try{
				sleep(500);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
						
			//Locking doors because class in session.
			msg("Class "+i+" in session!");
			Main.auditorium_Lock=true;
			try {
				sleep(2000); //Class in session.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Doors are open and class is over. Wake up students! Bye!
			Main.class_in_Session=false;
			Main.auditorium_Lock=false;
			for(int j=Main.auditorium.size()-1; j>=0; j--){
				msg("Class "+i+" is over "+Main.auditorium.get(j).getName()+". See you later!");
				Main.auditorium.remove(j).interrupt();
			}
			Main.class_in_Session=false;
			
			//There are no more classes today. Anymore students left have missed classes.
			if(Main.session==3){
				Main.auditorium_Lock=false;
			} else {
				Main.auditorium_Lock=true;
			}
		}
		
	}

	//Messaging system
	public static long time = System.currentTimeMillis();
	public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}

	
}
