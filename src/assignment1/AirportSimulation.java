package assignment1;

import java.util.LinkedList;
import java.util.UUID;

public class AirportSimulation{

	private final int numOfFirstClassServiceStations = 2;
	private final int numOfCoachServiceStations = 3;
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

	
	//statistics variables
	private static int maxCoachLength=0;
	private static int maxFirstClassLength=0;


	//input variables
	private final int simulationTime = 100;
	private final static int avgArrivalFirstClass = 5;
	private final static int avgArrivalCoach = 3;
	private final static int avgServiceFirstClass = 18;
	private final static int avgServiceCoach = 14;

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

		int counter = 1;
		while(onGoingSimulation(counter)){
			if(newPassenger(counter)){
				coachPassenger(randomEvent(avgArrivalCoach), counter);
				firstClassPassenger(randomEvent(avgArrivalFirstClass), counter);
			}
			coachServiceStation1();
			coachServiceStation2();
			coachServiceStation3();
			firstClassServiceStation1();
			firstClassServiceStation2();

			counter++;
			updateStatistics();
		}	
		printStatistics(counter);
	}	

	private void updateStatistics() {		
		if(maxCoachLength < coachQ.size()){
			maxCoachLength = coachQ.size();
		}
		if(maxFirstClassLength < firstClassQ.size()){
			maxFirstClassLength = firstClassQ.size();
		}	
	}

	private void printStatistics(int counter) {
		System.out.println("*************************************");
		System.out.println("Size of coach queue: " +coachQ.size());
		System.out.println("Size of firstClass queue: " +firstClassQ.size());
		System.out.println("Actual time of simulation: " +counter);
		System.out.println("Max Size of coach queue: " +maxCoachLength);
		System.out.println("Max Size of firstClass queue: " +maxFirstClassLength);		
		System.out.println("*************************************");
	}

	private boolean newPassenger(int time) {
		return time <= simulationTime;
	}

	private void firstClassServiceStation1() {
		Passenger passenger = null;
		if(!firstClassQ.isEmpty()){
			if(randomEvent(avgServiceFirstClass)){
				passenger = new Passenger();
				passenger = firstClassQ.dequeue();
				System.out.println(passenger+ " removed from the FirstClass queue");
				firstClass1.setPassengerID(passenger.getId());
				firstClass1free = false;
				//firstClass1.setTimeRemaining(timeRemaining);
			}	
			firstClass1free = false;
		}
		else if(firstClassQ.isEmpty()){
			firstClass1free = true;
		}
	}
	
	private void firstClassServiceStation2() {
		Passenger passenger = null;
		if(!firstClassQ.isEmpty()){
			if(randomEvent(avgServiceFirstClass)){
				passenger = new Passenger();
				passenger = firstClassQ.dequeue();
				System.out.println(passenger+ " removed from the FirstClass queue");
				firstClass2.setPassengerID(passenger.getId());
				firstClass2free = false;
				//firstClass1.setTimeRemaining(timeRemaining);
			}	
			firstClass2free = false;
		}
		else if(firstClassQ.isEmpty()){
			firstClass2free = true;
		}
	}

	private void coachServiceStation1() {
		Passenger passenger = null;
		if(!coachQ.isEmpty()){
			if(randomEvent(avgServiceFirstClass)){
			passenger = new Passenger();
			passenger = coachQ.dequeue();
			System.out.println(passenger+ " removed from the Coach queue");
			coach1.setPassengerID(passenger.getId());
			coach1free = false;
			//coach1.setTimeRemaining(timeRemaining);
			}
			coach1free = false;			
		}
		else if(coachQ.isEmpty()){
			coach1free = true;
		}
	}
	
	private void coachServiceStation2() {
		Passenger passenger = null;
		if(!coachQ.isEmpty()){
			if(randomEvent(avgServiceFirstClass)){
			passenger = new Passenger();
			passenger = coachQ.dequeue();
			System.out.println(passenger+ " removed from the Coach queue");
			coach2.setPassengerID(passenger.getId());
			coach2free = false;
			//coach1.setTimeRemaining(timeRemaining);
			}
			coach2free = false;			
		}
		else if(coachQ.isEmpty()){
			coach2free = true;
		}
	}
	
	private void coachServiceStation3() {
		Passenger passenger = null;
		if(!coachQ.isEmpty()){
			if(randomEvent(avgServiceFirstClass)){
			passenger = new Passenger();
			passenger = coachQ.dequeue();
			System.out.println(passenger+ " removed from the Coach queue");
			coach3.setPassengerID(passenger.getId());
			coach3free = false;
			//coach1.setTimeRemaining(timeRemaining);
			}
			coach3free = false;			
		}
		else if(coachQ.isEmpty()){
			coach3free = true;
		}
	}

	private boolean onGoingSimulation(int time) {
		return (time <= simulationTime || !(firstClassQ.isEmpty() && coachQ.isEmpty()) || !(firstClass1free && coach1free && firstClass2free && coach2free && coach3free));
	}

	public static void main(String[] args) {
		AirportSimulation simulation = new AirportSimulation();
		simulation.start();
	}

	private static void firstClassPassenger(Boolean createPassenger, int arrivalTime) {
		if(createPassenger){
			Passenger passenger = new Passenger();
			passenger.setId(UUID.randomUUID().toString());
			passenger.setArrivalTime(arrivalTime);
			firstClassQ.enqueue(passenger);
			System.out.println(passenger+ " added to the FirstClass queue");
		}		
	}

	private static void coachPassenger(Boolean createPassenger, int arrivalTime) {
		if(createPassenger){
			Passenger passenger = new Passenger();
			passenger.setId(UUID.randomUUID().toString());
			passenger.setArrivalTime(arrivalTime);
			coachQ.enqueue(passenger);
			System.out.println(passenger+ " added to the Coach queue");
		}		
	}

	private static boolean randomEvent(int n){		
		return Math.random() <= (1.0/n);		
	}
}
