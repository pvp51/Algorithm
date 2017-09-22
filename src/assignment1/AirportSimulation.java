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
	}

	private void start() {

		int counter = 1;
		while(onGoingSimulation(counter)){
			if(newPassenger(counter)){
				coachPassenger(randomEvent(avgArrivalCoach), counter);
				firstClassPassenger(randomEvent(avgArrivalFirstClass), counter);
			}
			coachServiceStation();
			firstClassServiceStation();	

			counter++;			
		}	
		printStatistics(counter);
	}	

	private void printStatistics(int counter) {
		System.out.println("*************************************");
		System.out.println("Size of coach queue: " +coachQ.size());
		System.out.println("Size of firstClass queue: " +firstClassQ.size());
		System.out.println("Actual time of simulation: " +counter);
		System.out.println("*************************************");
	}

	private boolean newPassenger(int time) {
		return time <= simulationTime;
	}

	private void firstClassServiceStation() {
		Passenger passenger = null;
		if(randomEvent(avgServiceFirstClass) && !firstClassQ.isEmpty()){
			passenger = new Passenger();
			passenger = firstClassQ.dequeue();
			System.out.println(passenger+ " removed from the FirstClass queue");
			firstClass1.setPassengerID(passenger.getId());
			firstClass1free = false;
			//firstClass1.setTimeRemaining(timeRemaining);
		}
		if(passenger == null){
			firstClass1free = true;
		}
	}

	private void coachServiceStation() {
		Passenger passenger = null;
		if(randomEvent(avgServiceCoach) && !coachQ.isEmpty()){
			passenger = new Passenger();
			passenger = coachQ.dequeue();
			System.out.println(passenger+ " removed from the Coach queue");
			coach1.setPassengerID(passenger.getId());
			coach1free = false;
			//coach1.setTimeRemaining(timeRemaining);
		}
		if(passenger == null){
			coach1free = true;
		}
	}

	private boolean onGoingSimulation(int time) {
		return (time <= simulationTime || !(firstClassQ.isEmpty() && coachQ.isEmpty()) || !(firstClass1free && coach1free));
	}

	public static void main(String[] args) {
		AirportSimulation simulation = new AirportSimulation();
		//simulation.start();
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
