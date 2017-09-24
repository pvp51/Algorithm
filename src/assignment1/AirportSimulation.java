package assignment1;

import java.util.Scanner;

public class AirportSimulation{

	private static int counter = 1;	//current time

	private static QueueADT<Passenger> firstClassQ;
	private static QueueADT<Passenger> coachQ;
	private ServiceStation firstClass1;
	private ServiceStation firstClass2;
	private ServiceStation coach1;
	private ServiceStation coach2;
	private ServiceStation coach3;

	private static boolean firstClass1free = true;
	private static boolean coach1free = true;
	private static boolean coach2free = true;
	private static boolean firstClass2free = true;
	private static boolean coach3free = true;

	private static int firstClassPassenger = 0;
	private static int coachPassenger = 0;

	//statistics variables
	private static int maxCoachLength = 0;
	private static int maxFirstClassLength = 0;
	private static int serviceTimefirstClass1 = 0;
	private static int totalServiceTimefirstClass1 = 0;
	private static int serviceTimefirstClass2 = 0;
	private static int totalServiceTimefirstClass2 = 0;
	private static int serviceTimeCoach1 = 0;
	private static int totalServiceTimeCoach1 = 0;
	private static int serviceTimeCoach2 = 0;
	private static int totalServiceTimeCoach2 = 0;
	private static int serviceTimeCoach3 = 0;
	private static int totalServiceTimeCoach3 = 0;
	private static int waitingTimeFirstClass1 = 0;
	private static int waitingTimeFirstClass2 = 0;
	private static int waitingTimeCoach1 = 0;
	private static int waitingTimeCoach2 = 0;
	private static int waitingTimeCoach3 = 0;
	private static int waitingTimeCoach4=0;	//when firstclass1 service station is empty and coach queue is not
	private static int waitingTimeCoach5=0;	//when firstclass2 service station is empty and coach queue is not
	private static int maxWaitimgTimeFirstClassQ = 0;
	private static int maxWaitimgTimeCoachQ = 0;
	private static int totalWaitingTimeFirstClass = 0;
	private static int totalWaitingTimeCoach = 0;

	//input variables
	private int checkInTime;
	private int avgArrivalFirstClass;
	private int avgArrivalCoach;
	private int avgServiceFirstClass;
	private int avgServiceCoach;

	public AirportSimulation(){		
		firstClassQ = new QueueADT<Passenger>();
		coachQ = new QueueADT<Passenger>();	
		firstClass1 = new ServiceStation();
		coach1 = new ServiceStation();
		firstClass2 = new ServiceStation();
		coach2 = new ServiceStation();
		coach3 = new ServiceStation();
	}

	private void start() {
		inputParameters();
		while(onGoingSimulation()){
			if(newPassenger()){
				coachPassenger(randomEvent(avgArrivalCoach));
				firstClassPassenger(randomEvent(avgArrivalFirstClass));
			}
			coachServiceStation1();
			coachServiceStation2();
			coachServiceStation3();
			firstClassServiceStation1();
			firstClassServiceStation2();

			counter++;
			updateStatistics();
		}	
		printStatistics();
	}	

	private void inputParameters() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Please enter the check-in Time(min):");
		checkInTime=scan.nextInt();
		System.out.println("Please enter the average Arrival Time for First Class passenger(min):");
		avgArrivalFirstClass=scan.nextInt();
		System.out.println("Please enter the average Arrival Time for Coach passenger(min):");
		avgArrivalCoach=scan.nextInt();
		System.out.println("Please enter the average Service Time for First Class passenger(min):");
		avgServiceFirstClass=scan.nextInt();
		System.out.println("Please enter the average Service Time for Coach passenger(min):");
		avgServiceCoach=scan.nextInt();
		
		scan.close();
		
		System.out.println("*************************************");
		System.out.println("Input Parameters");
		System.out.println("The check-in Time(min): "+checkInTime);
		System.out.println("The average Arrival Time for First Class passenger(min): "+avgArrivalFirstClass);
		System.out.println("The average Arrival Time for Coach passenger(min): "+avgArrivalCoach);
		System.out.println("The average Service Time for First Class Station(min): "+avgServiceFirstClass);
		System.out.println("The average Service Time for Coach Station(min): "+avgServiceCoach);
		System.out.println("*************************************");
		
		
		
		
	}

	private void updateStatistics() {	
		int max = 0;
		int max1 = 0;
		int max2 = 0;
		int max3 = 0;
		if(maxCoachLength < coachQ.size()){
			maxCoachLength = coachQ.size();
		}
		if(maxFirstClassLength < firstClassQ.size()){
			maxFirstClassLength = firstClassQ.size();
		}
		max = Math.max(waitingTimeFirstClass1, waitingTimeFirstClass2);
		if(maxWaitimgTimeFirstClassQ < max){
			maxWaitimgTimeFirstClassQ = max;
		}
		max = Math.max(waitingTimeCoach1, waitingTimeCoach2);
		max1 = Math.max(max, waitingTimeCoach3);
		max2 = Math.max(max1, waitingTimeCoach4);	//when firstClass1 takes coach passengers
		max3 = Math.max(max2, waitingTimeCoach5);	//when firstClass2 takes coach passengers
		if(maxWaitimgTimeCoachQ < max3){
			maxWaitimgTimeCoachQ = max3;
		}
	}

	private void printStatistics() {
		System.out.println("*************************************");
		//System.out.println("Size of coach queue: " +coachQ.size());
		//System.out.println("Size of firstClass queue: " +firstClassQ.size());

		System.out.println("Actual time of simulation: " +counter);
		System.out.println("Max Size of firstClass queue: " +maxFirstClassLength);
		System.out.println("Max Size of coach queue: " +maxCoachLength);			

		System.out.println("Average Waiting Time of firstClass queue: " +totalWaitingTimeFirstClass/maxFirstClassLength);
		System.out.println("Average Waiting Time of Coach queue: " +totalWaitingTimeCoach/maxCoachLength);
		System.out.println("Max Waiting Time of firstClass queue: " +maxWaitimgTimeFirstClassQ);
		System.out.println("Max Waiting Time of Coach queue: " +maxWaitimgTimeCoachQ);

		System.out.println("Rate of occupancy of firstClass1 service station: " +(totalServiceTimefirstClass1*100/counter));
		System.out.println("Rate of occupancy of firstClass2 service station: " +(totalServiceTimefirstClass2*100/counter));
		System.out.println("Rate of occupancy of coach1 service station: " +(totalServiceTimeCoach1*100/counter));
		System.out.println("Rate of occupancy of coach2 service station: " +(totalServiceTimeCoach2*100/counter));
		System.out.println("Rate of occupancy of coach3 service station: " +(totalServiceTimeCoach3*100/counter));
		System.out.println("*************************************");
	}

	private boolean newPassenger() {
		return counter <= checkInTime;
	}

	private void firstClassServiceStation1() {		
		Passenger passenger = null;
		waitingTimeFirstClass1 = 0;
		if(!firstClassQ.isEmpty()){
			if(randomEvent(avgServiceFirstClass)){
				totalServiceTimefirstClass1 += serviceTimefirstClass1;
				passenger = firstClassQ.dequeue();
				//total waiting time for firstClass queue
				waitingTimeFirstClass1 = counter - passenger.getArrivalTime();
				System.out.println("[First Class Service Station 1] "+passenger.getId()+ " is served for "+serviceTimefirstClass1+ " min.");
				totalWaitingTimeFirstClass += waitingTimeFirstClass1;				

				firstClass1.setPassengerID(passenger.getId());
				firstClass1free = false;
				serviceTimefirstClass1=0;
			}	
			firstClass1free = false;
			serviceTimefirstClass1++;
		}
		else if(firstClassQ.isEmpty()){
			firstClass1free = true;
		}
		else if(firstClass1free && !coachQ.isEmpty()){	//when firstClass1 is free but coach queue is not empty
			waitingTimeCoach4=0;
			//even if the passenger is from coach, the service time of first class applies
			if(randomEvent(avgServiceFirstClass)){	
				totalServiceTimefirstClass1 += serviceTimefirstClass1;		
				passenger = coachQ.dequeue();
				firstClass1.setPassengerID(passenger.getId());
				//total waiting time for coach queue as passenger is taken from coach queue
				waitingTimeCoach4 = counter - passenger.getArrivalTime();
				System.out.println("[First Class Service Station 1] "+passenger.getId()+ " is served for "+serviceTimefirstClass1+ " min.");
				totalWaitingTimeCoach += waitingTimeCoach4;
				
				firstClass1free = false;
				serviceTimefirstClass1=0;
			}
			firstClass1free = false;
			serviceTimefirstClass1++;
		}
		else{
			firstClass1free = true;
		}
	}

	private void firstClassServiceStation2() {
		Passenger passenger = null;
		waitingTimeFirstClass2 = 0;
		if(!firstClassQ.isEmpty()){
			if(randomEvent(avgServiceFirstClass)){
				totalServiceTimefirstClass2 += serviceTimefirstClass2;
				passenger = firstClassQ.dequeue();
				//total waiting time for firstClass queue
				waitingTimeFirstClass2 = counter - passenger.getArrivalTime();
				System.out.println("[First Class Service Station 2] "+passenger.getId()+ " is served for "+serviceTimefirstClass2+ " min.");
				totalWaitingTimeFirstClass += waitingTimeFirstClass2;
				
				firstClass2.setPassengerID(passenger.getId());
				firstClass2free = false;
				
				serviceTimefirstClass2=0;
			}	
			firstClass2free = false;
			serviceTimefirstClass2++;
		}
		else if(firstClassQ.isEmpty()){
			firstClass2free = true;
		}
		else if(firstClass2free && !coachQ.isEmpty()){	//when firstClass2 is free but coach queue is not empty
			waitingTimeCoach5=0;
			//even if the passenger is from coach, the service time of first class applies
			if(randomEvent(avgServiceFirstClass)){	
				totalServiceTimefirstClass2 += serviceTimefirstClass2;		
				passenger = coachQ.dequeue();
				firstClass2.setPassengerID(passenger.getId());
				//total waiting time for coach queue as passenger is taken from coach queue
				waitingTimeCoach5 = counter - passenger.getArrivalTime();
				System.out.println("[First Class Service Station 2] "+passenger.getId()+ " is served for "+serviceTimefirstClass2+ " min.");
				totalWaitingTimeCoach += waitingTimeCoach5;
				
				firstClass2free = false;
				serviceTimefirstClass2=0;
			}
			firstClass2free = false;
			serviceTimefirstClass2++;
		}
		else{
			firstClass2free = true;
		}
	}

	private void coachServiceStation1() {
		Passenger passenger = null;
		waitingTimeCoach1=0;
		if(!coachQ.isEmpty()){
			if(randomEvent(avgServiceCoach)){
				totalServiceTimeCoach1 += serviceTimeCoach1;
				passenger = coachQ.dequeue();				
				//total waiting time for coach queue
				waitingTimeCoach1 = counter - passenger.getArrivalTime();				
				System.out.println("[Coach Service Station 1] "+passenger.getId()+ " is served for "+serviceTimeCoach1+ " min.");
				totalWaitingTimeCoach += waitingTimeCoach1;
				
				coach1.setPassengerID(passenger.getId());
				coach1free = false;
				
				serviceTimeCoach1=0;
			}
			coach1free = false;	
			serviceTimeCoach1++;
		}
		else if(coachQ.isEmpty()){
			coach1free = true;
		}
	}

	private void coachServiceStation2() {
		Passenger passenger = null;
		waitingTimeCoach2=0;
		if(!coachQ.isEmpty()){
			if(randomEvent(avgServiceCoach)){
				totalServiceTimeCoach2 += serviceTimeCoach2;
				passenger = coachQ.dequeue();
				//total waiting time for coach queue
				waitingTimeCoach2 = counter - passenger.getArrivalTime();
				System.out.println("[Coach Service Station 2] "+passenger.getId()+ " is served for "+serviceTimeCoach2+ " min.");
				totalWaitingTimeCoach += waitingTimeCoach2;
				
				coach2.setPassengerID(passenger.getId());
				coach2free = false;
				
				serviceTimeCoach2 = 0;
			}
			coach2free = false;
			serviceTimeCoach2++;
		}
		else if(coachQ.isEmpty()){
			coach2free = true;
		}
	}

	private void coachServiceStation3() {
		Passenger passenger = null;
		waitingTimeCoach3=0;
		if(!coachQ.isEmpty()){
			if(randomEvent(avgServiceCoach)){
				totalServiceTimeCoach3 += serviceTimeCoach3;
				passenger = coachQ.dequeue();
				//total waiting time for coach queue
				waitingTimeCoach3 = counter - passenger.getArrivalTime();
				System.out.println("[Coach Service Station 3] "+passenger.getId()+ " is served for "+serviceTimeCoach3+ " min.");
				totalWaitingTimeCoach += waitingTimeCoach3;
				
				coach3.setPassengerID(passenger.getId());				
				coach3free = false;
				
				serviceTimeCoach3=0;
			}
			coach3free = false;		
			serviceTimeCoach3++;
		}
		else if(coachQ.isEmpty()){
			coach3free = true;
		}
	}

	private boolean onGoingSimulation() {
		return (counter <= checkInTime || !(firstClassQ.isEmpty() && coachQ.isEmpty()) || !(firstClass1free && coach1free && firstClass2free && coach2free && coach3free));
	}

	public static void main(String[] args) {
		AirportSimulation simulation = new AirportSimulation();
		simulation.start();
	}

	private static void firstClassPassenger(Boolean createPassenger) {
		if(createPassenger){
			Passenger passenger = new Passenger();
			firstClassPassenger++;
			passenger.setId("FirstClass_"+firstClassPassenger);			
			passenger.setArrivalTime(counter);
			firstClassQ.enqueue(passenger);
			System.out.println("[New Passenger] "+passenger.getId()+ " added to the FirstClass queue");
		}		
	}

	private static void coachPassenger(Boolean createPassenger) {
		if(createPassenger){
			Passenger passenger = new Passenger();
			coachPassenger++;
			passenger.setId("Coach_"+coachPassenger);
			passenger.setArrivalTime(counter);
			coachQ.enqueue(passenger);
			System.out.println("[New Passenger] "+passenger.getId()+ " added to the Coach queue");
		}		
	}

	private static boolean randomEvent(int n){		
		return Math.random() <= (1.0/n);		
	}
}
