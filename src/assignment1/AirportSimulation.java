package assignment1;

public class AirportSimulation{

	private static int counter = 1;

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
	private static int maxWaitimgTimeFirstClassQ = 0;
	private static int maxWaitimgTimeCoachQ = 0;

	private static int totalWaitingTimeFirstClass = 0;
	private static int totalWaitingTimeCoach = 0;



	//input variables
	private final int checkInTime = 100;
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

	private void updateStatistics() {	
		int max = 0;
		int max1 = 0;
		if(maxCoachLength < coachQ.size()){
			maxCoachLength = coachQ.size();
		}
		if(maxFirstClassLength < firstClassQ.size()){
			maxFirstClassLength = firstClassQ.size();
		}
		max = Math.max(waitingTimeFirstClass1, waitingTimeFirstClass2);
		if( maxWaitimgTimeFirstClassQ < max){
			maxWaitimgTimeFirstClassQ = max;
		}
		max = Math.max(waitingTimeCoach1, waitingTimeCoach2);
		max1 = Math.max(max, waitingTimeCoach3);
		if( maxWaitimgTimeCoachQ < max1){
			maxWaitimgTimeCoachQ = max1;
		}
	}

	private void printStatistics() {
		System.out.println("*************************************");
		//System.out.println("Size of coach queue: " +coachQ.size());
		//System.out.println("Size of firstClass queue: " +firstClassQ.size());

		System.out.println("Actual time of simulation: " +counter);
		System.out.println("Max Size of coach queue: " +maxCoachLength);
		System.out.println("Max Size of firstClass queue: " +maxFirstClassLength);	

		System.out.println("Average Waiting Time of firstClass queue: " +totalWaitingTimeFirstClass/checkInTime);
		System.out.println("Average Waiting Time of Coach queue: " +totalWaitingTimeCoach/checkInTime);
		System.out.println("Max Waiting Time of firstClass queue: " +maxWaitimgTimeFirstClassQ);
		System.out.println("Max Waiting Time of Coach queue: " +maxWaitimgTimeCoachQ);

		System.out.println("Percentage Service of firstClass1 service station: " +(totalServiceTimefirstClass1*100/counter));
		System.out.println("Percentage Service of firstClass2 service station: " +(totalServiceTimefirstClass2*100/counter));
		System.out.println("Percentage Service of coach1 service station: " +(totalServiceTimeCoach1*100/counter));
		System.out.println("Percentage Service of coach2 service station: " +(totalServiceTimeCoach2*100/counter));
		System.out.println("Percentage Service of coach3 service station: " +(totalServiceTimeCoach3*100/counter));
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
				passenger = new Passenger();
				//total waiting time for firstClass queue
				waitingTimeFirstClass1 = counter - passenger.getArrivalTime();
				System.out.println(waitingTimeFirstClass1);
				totalWaitingTimeFirstClass += waitingTimeFirstClass1;

				passenger = firstClassQ.dequeue();
				System.out.println(passenger.getId()+ " removed from the FirstClass queue");
				firstClass1.setPassengerID(passenger.getId());
				firstClass1free = false;
				//firstClass1.setTimeRemaining(timeRemaining);
				serviceTimefirstClass1=0;
			}	
			firstClass1free = false;
			serviceTimefirstClass1++;
		}
		else if(firstClassQ.isEmpty()){
			firstClass1free = true;
		}
	}

	private void firstClassServiceStation2() {
		Passenger passenger = null;
		waitingTimeFirstClass2=0;
		if(!firstClassQ.isEmpty()){
			if(randomEvent(avgServiceFirstClass)){
				totalServiceTimefirstClass2 += serviceTimefirstClass2;
				passenger = new Passenger();
				//total waiting time for firstClass queue
				waitingTimeFirstClass2 = counter - passenger.getArrivalTime();
				System.out.println(waitingTimeFirstClass2);
				totalWaitingTimeFirstClass += waitingTimeFirstClass2;

				passenger = firstClassQ.dequeue();
				System.out.println(passenger.getId()+ " removed from the FirstClass queue");
				firstClass2.setPassengerID(passenger.getId());
				firstClass2free = false;
				//firstClass1.setTimeRemaining(timeRemaining);
				serviceTimefirstClass2=0;
			}	
			firstClass2free = false;
			serviceTimefirstClass2++;
		}
		else if(firstClassQ.isEmpty()){
			firstClass2free = true;
		}
	}

	private void coachServiceStation1() {
		Passenger passenger = null;
		waitingTimeCoach1=0;
		if(!coachQ.isEmpty()){
			if(randomEvent(avgServiceCoach)){
				totalServiceTimeCoach1 += serviceTimeCoach1;
				passenger = new Passenger();
				passenger = coachQ.dequeue();
				//total waiting time for coach queue
				waitingTimeCoach1 = counter - passenger.getArrivalTime();
				System.out.println(waitingTimeCoach1);
				totalWaitingTimeCoach += waitingTimeCoach1;

				System.out.println(passenger.getId()+ " removed from the Coach queue");
				coach1.setPassengerID(passenger.getId());
				coach1free = false;
				//coach1.setTimeRemaining(timeRemaining);
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
				passenger = new Passenger();
				//total waiting time for coach queue
				waitingTimeCoach2 = counter - passenger.getArrivalTime();
				System.out.println(waitingTimeCoach2);
				totalWaitingTimeCoach += waitingTimeCoach2;

				passenger = coachQ.dequeue();
				System.out.println(passenger.getId()+ " removed from the Coach queue");
				coach2.setPassengerID(passenger.getId());
				coach2free = false;
				//coach1.setTimeRemaining(timeRemaining);
				serviceTimeCoach2=0;
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
				passenger = new Passenger();
				//total waiting time for coach queue
				waitingTimeCoach3 = counter - passenger.getArrivalTime();
				System.out.println(waitingTimeCoach3);
				totalWaitingTimeCoach += waitingTimeCoach3;

				passenger = coachQ.dequeue();
				System.out.println(passenger.getId()+ " removed from the Coach queue");
				coach3.setPassengerID(passenger.getId());
				coach3free = false;
				//coach1.setTimeRemaining(timeRemaining);
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
			System.out.println(passenger.getId()+ " added to the FirstClass queue");
		}		
	}

	private static void coachPassenger(Boolean createPassenger) {
		if(createPassenger){
			Passenger passenger = new Passenger();
			coachPassenger++;
			passenger.setId("Coach_"+coachPassenger);
			passenger.setArrivalTime(counter);
			coachQ.enqueue(passenger);
			System.out.println(passenger.getId()+ " added to the Coach queue");
		}		
	}

	private static boolean randomEvent(int n){		
		return Math.random() <= (1.0/n);		
	}
}
