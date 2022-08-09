import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        Person engineer = new Person(10, 10, "engineer", 1);
        Person scientist = new Person(10, 10, "scientist", 2);
        Person supervisor = new Person(10, 10, "supervisor", 3);
        Person student = new Person(10, 10, "student", 4);
        Person cleaner = new Person(10, 10, "cleaner", 5);

        // Setting up myBuilding and people.
        Building myBuilding = new Building();
        myBuilding.addRoom(new Room(100, "room1"));
        myBuilding.addRoom(new Room(100, "room2"));
        myBuilding.addRoom(new Room(100, "room3"));
        myBuilding.addRoom(new Room(100, "room4"));

        LinkedList<Person> people = new LinkedList<>();
        people.add(new Person(engineer));
        people.add(new Person(engineer));
        people.add(new Person(engineer));
        people.add(new Person(engineer));
        people.add(new Person(engineer));
        people.add(new Person(engineer));
        people.add(new Person(engineer));
        people.add(new Person(engineer));

        people.add(new Person(scientist));
        people.add(new Person(scientist));
        people.add(new Person(scientist));
        people.add(new Person(scientist));
        people.add(new Person(scientist));
        people.add(new Person(scientist));
        people.add(new Person(scientist));
        people.add(new Person(scientist));

        people.add(new Person(supervisor));
        people.add(new Person(supervisor));
        people.add(new Person(supervisor));
        people.add(new Person(supervisor));
        people.add(new Person(supervisor));
        people.add(new Person(supervisor));
        people.add(new Person(supervisor));
        people.add(new Person(supervisor));

        people.add(new Person(student));
        people.add(new Person(student));
        people.add(new Person(student));
        people.add(new Person(student));
        people.add(new Person(student));
        people.add(new Person(student));
        people.add(new Person(student));
        people.add(new Person(student));

        people.add(new Person(cleaner));
        people.add(new Person(cleaner));
        people.add(new Person(cleaner));
        people.add(new Person(cleaner));
        people.add(new Person(cleaner));
        people.add(new Person(cleaner));
        people.add(new Person(cleaner));
        people.add(new Person(cleaner));

        final long startTime = System.currentTimeMillis();
        myBuilding.greedyQMKP(people);
        myBuilding.calculateValue(people.size());
        // myBuilding.shufflePeopleInRooms();
        // myBuilding.greedyQMKP(people);
        // myBuilding.calculateValue(people.size());
        final long endTime1 = System.currentTimeMillis();
        long timeTaken1 = (endTime1 - startTime);
        myBuilding.printResult(timeTaken1);
        Building result = myBuilding.neighborSearch(myBuilding);
        final long endTime2 = System.currentTimeMillis();
        long timeTaken2 = (endTime2 - startTime);
        result.printResult(timeTaken2);

    }

}
