import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.lang.Math;

public class Building {

    private LinkedList<Room> myBuilding;
    private LinkedList<Person> people;
    private int value;
    int count = 0;
    int numTypes = 5;

    // solves the QMKP Problem by a greedy approach.
    public void greedyQMKP(LinkedList<Person> people) {
        // sorts people by valueBySize
        Collections.sort(people, new Comparator<Person>() {
            @Override
            public int compare(Person i1, Person i2) {
                if (i1.getValueBySize() > i2.getValueBySize()) {
                    return -1;
                } else if (i1.getValueBySize() < i2.getValueBySize()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        Room bestRoom;
        double bestAreaDifference;
        double currentAreaDifference;

        // iterate through people, ordered by valueBySize, assigning to room of
        // 'best fit'
        for (int i = 0; i < people.size(); i++) {
            if (!this.people.contains(people.get(i))) {
                this.people.add(people.get(i));
            }
            // bestAreaDifference = 0;
            bestAreaDifference = Integer.MAX_VALUE;
            bestRoom = null;
            // finds room which fits person with best(least) size difference(spare)
            for (int j = 0; j < myBuilding.size(); j++) {
                if (myBuilding.get(j).getRemainingArea() >= people.get(i).getSize()) {
                    currentAreaDifference = myBuilding.get(j).getRemainingArea() - people.get(i).getSize();
                    if (currentAreaDifference < bestAreaDifference && currentAreaDifference >= 0) {
                        bestAreaDifference = currentAreaDifference;
                        bestRoom = myBuilding.get(j);
                    }
                    // if (currentAreaDifference < bestAreaDifference && currentAreaDifference >= 0)
                    // {
                    // bestAreaDifference = currentAreaDifference;
                    // bestRoom = myBuilding.get(j);
                    // }
                }
            }
            // add person to 'best' room
            if (bestRoom != null) {
                bestRoom.addPerson(people.get(i));
                this.people.remove(people.get(i));
            }
        }
    }

    // gets all neighbors a current solution has.
    public LinkedList<Building> getNeighbors(LinkedList<Room> myBuilding, LinkedList<Person> people) {
        LinkedList<Building> roomNeighbours = new LinkedList<>();

        for (int gRoom = 0; gRoom < myBuilding.size(); gRoom++) {
            for (int gPerson = 0; gPerson < myBuilding.get(gRoom).getPeople().size(); gPerson++) {
                for (int lRoom = 0; lRoom < myBuilding.size(); lRoom++) {
                    for (int lPerson = 0; lPerson < myBuilding.get(lRoom).getPeople().size(); lPerson++) {
                        Room globalRoom = myBuilding.get(gRoom);
                        Room localRoom = myBuilding.get(lRoom);
                        // count += 1;
                        if (!globalRoom.equals(localRoom)) {
                            LinkedList<Person> golbalRoomPeople = globalRoom.getPeople();
                            LinkedList<Person> localRoomPeople = localRoom.getPeople();
                            if ((golbalRoomPeople.get(gPerson).getSize() <= (localRoomPeople.get(lPerson).getSize()
                                    + localRoom.getRemainingArea()))
                                    && (localRoomPeople.get(lPerson)
                                            .getSize() <= (golbalRoomPeople.get(gPerson).getSize()
                                                    + globalRoom.getRemainingArea()))) {

                                Building neighbor = new Building();
                                Person removed;
                                LinkedList<Room> currentRooms = new LinkedList<>();
                                LinkedList<Person> currentPeople = new LinkedList<>(people);
                                for (Room room : myBuilding) {
                                    if (room.equals(localRoom)) {
                                        Room local = new Room(room);
                                        local.setArea(
                                                localRoom.getRemainingArea() + localRoomPeople.get(lPerson).getSize()
                                                        - golbalRoomPeople.get(gPerson).getSize());
                                        removed = localRoomPeople.get(lPerson);
                                        local.getPeople().set(lPerson, golbalRoomPeople.get(gPerson));
                                        currentRooms.add(local);
                                        Room global = new Room(globalRoom);
                                        global.setArea(
                                                global.getRemainingArea() + global.getPeople().get(gPerson).getSize()
                                                        - localRoomPeople.get(lPerson).getSize());
                                        global.getPeople().set(gPerson, removed);
                                        currentRooms.add(global);
                                    } else if (room.equals(globalRoom)) {
                                        // do nothing
                                    } else {
                                        currentRooms.add(new Room(room));
                                    }
                                }

                                neighbor.setRooms(currentRooms);
                                neighbor.setPeople(currentPeople);
                                // neighbor.shufflePeopleInRooms(); // use in initial solution?
                                // neighbor.greedyQMKP(currentPeople);
                                neighbor.calculateValue(currentPeople.size());
                                roomNeighbours.add(neighbor);
                            }
                        }
                    }
                }
            }
        }

        return roomNeighbours;
    }

    // recursive function whcih tries to find neighbors for a solution that have a
    // better outcome than the solution itself.
    public Building neighborSearch(Building myBuilding) {
        LinkedList<Building> neighbors = getNeighbors(myBuilding.getRooms(), myBuilding.getPeople());
        for (Building neighbor : neighbors) {
            if (neighbor.getValue() > myBuilding.getValue()) {
                myBuilding = neighborSearch(neighbor);
            }
        }

        return myBuilding;
    }

    // shuffles or rearranges the people in the solution, helps to avoid local
    // maxima and speed up model
    public void shufflePeopleInRooms() {
        LinkedList<Person> peopleInRooms = new LinkedList<>();
        for (Room room : myBuilding) {
            peopleInRooms.addAll(room.getPeople());
        }

        Collections.sort(peopleInRooms, new Comparator<Person>() {
            @Override
            public int compare(Person i1, Person i2) {
                if (Math.random() < .33) {
                    return -1;
                } else if (Math.random() > .33) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        for (Room room : myBuilding) {
            room.getPeople().clear();
            room.resetArea();
            for (Iterator<Person> it = peopleInRooms.iterator(); it.hasNext();) {
                Person person = it.next();
                if (person.getSize() <= room.getRemainingArea()) {
                    room.addPerson(person);
                    it.remove();
                }
            }
        }
    }

    // calculates a Building objects total value.
    public void calculateValue(int peopleLength) {
        for (Room room : myBuilding) {
            int roomValue = 0;
            int duplicateTypeCount = 1;
            duplicateTypeCount += countDuplicateTypes(room);
            for (Person person : room.getPeople()) {
                roomValue += person.getValue();
            }
            roomValue = Math.toIntExact((long) (roomValue * (10000 /
                    duplicateTypeCount))); // punish same type
            // roomValue = Math.toIntExact((long) (roomValue * duplicateTypeCount)); //
            // reward same type
            this.value += roomValue;
        }

        if (containsAllPeople(peopleLength)) {
            this.value = Math.toIntExact((long) (this.value * 1.1));
        }
    }

    // returns whether a Building object contains all people
    public boolean containsAllPeople(int peopleLength) {
        int totalRoomContent = 0;
        for (Room room : myBuilding) {
            totalRoomContent += room.getPeople().size();
        }
        if (totalRoomContent != peopleLength)
            return false;
        else
            return true;
    }

    // returns number of people of same type in a Building object
    public int countDuplicateTypes(Room room) {
        LinkedList<Person> currentPeopleToCheck = room.getPeople();
        if (currentPeopleToCheck.size() == 0) {
            return 0;
        }
        // Create a hash table
        HashSet<Integer> s = new HashSet<>();
        // Traverse through room people, incrementing count every time a duplicate
        // person type is found
        int[] count = new int[numTypes];
        for (Person person : room.getPeople()) {
            if (s.contains(person.getType())) {
                count[person.getType() - 1] += 1;
            }
            s.add(person.getType());
        }

        // find the max duplicate of any given type
        // int maxDuplicate = 0;
        int duplicateTypesCoefficient = 0;
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0)
                duplicateTypesCoefficient += count[i] * count[i];
            // if (count[i] > duplicateTypesCoefficient) {
            // duplicateTypesCoefficient = count[i]; //reward same type
            // }
        }

        // Return the count of room people of same type
        return duplicateTypesCoefficient;
    }

    // prints out the result of a Building object
    public void printResult(long timeTaken) {
        for (Room room : myBuilding) {
            System.out.println("------" + room.getName() + "------\n"
                    + "\nRoom size: " + room.getStartArea() + "\nRemaining Space: " + room.getRemainingArea() + "\n");
            for (Person person : room.getPeople()) {
                System.out.println("Person\n" + "Name: " + person.getName()
                        + "\nValue: " + person.getValue() + "\nSize: " + person.getSize());
            }
            System.out.println("---------------------------");
        }

        System.out.println("\n---------------------------");
        System.out.println("Total value: " + value);
        System.out.println("Time Taken: " + timeTaken + "ms");
        System.out.println("---------------------------\n");
    }

    // sets the people that are not in a room already.
    public void setPeople(LinkedList<Person> people) {
        this.people = people;
    }

    // sets rooms of myBuilding.
    public void setRooms(LinkedList<Room> myBuilding) {
        this.myBuilding = myBuilding;
    }

    // returns the total value of a Building object.
    public int getValue() {
        return value;
    }

    // instantiates necessary objects.
    public Building() {
        myBuilding = new LinkedList<>();
        people = new LinkedList<>();
    }

    // returns all of the rooms in the Building.
    public LinkedList<Room> getRooms() {
        return myBuilding;
    }

    // returns all of the people that are not in a room already.
    public LinkedList<Person> getPeople() {
        return people;
    }

    // adds a room into the Building object
    public void addRoom(Room room) {
        myBuilding.add(room);
    }

}
