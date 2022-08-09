import java.util.LinkedList;

// class that represents a Room.
public class Room {

    private int remainingArea;
    private int startArea;
    private String name;
    private LinkedList<Person> people;

    // Constructor that cerates a new room with given properties.
    public Room(int remainingArea, String name) {
        this.remainingArea = remainingArea;
        this.name = name;
        this.startArea = remainingArea;
        this.people = new LinkedList<>();
    }

    // constructor which copies a room object and creates a new identical one
    public Room(Room room) {
        this.remainingArea = room.getRemainingArea();
        this.startArea = room.getStartArea();
        this.name = room.getName();
        this.people = new LinkedList<>(room.getPeople());
    }

    // adds an person into the person-list and updates the remainingArea
    public void addPerson(Person person) {
        if (person != null) {
            this.people.add(person);
            this.remainingArea = this.remainingArea - person.getSize();
        }
    }

    // sets the remainingArea to the initial value of the room.
    public void resetArea() {
        this.remainingArea = startArea;
    }

    // sets the remainingArea to the value provided to the method.
    public void setArea(int remainingArea) {
        this.remainingArea = remainingArea;
    }

    // returns the room's startArea
    public int getStartArea() {
        return this.startArea;
    }

    // returns the room's remainingArea.
    public int getRemainingArea() {
        return this.remainingArea;
    }

    // returns the room's name.
    public String getName() {
        return this.name;
    }

    // returns the people the room is currently holding.
    public LinkedList<Person> getPeople() {
        return this.people;
    }
}
