public class Person {

    private int size;
    private int value;
    private double valueBySize;
    private String name;
    private int type;

    // constructor that instantiates size, value and name for an person.
    public Person(int size, int value, String name, int type) {
        this.size = size;
        this.value = value;
        this.valueBySize = (double) value / (double) size;
        this.name = name;
        this.type = type;
    }

    // constructor which copies a person object and creates a new identical one
    public Person(Person person) {
        this.size = person.size;
        this.value = person.value;
        this.valueBySize = (double) person.value / (double) person.size;
        this.name = person.name;
        this.type = person.type;
    }

    // returns the value / size value from an person.
    public double getValueBySize() {
        return this.valueBySize;
    }

    // returns the size a person is.
    public int getSize() {
        return this.size;
    }

    // returns the value a person has.
    public int getValue() {
        return this.value;
    }

    // returns the name of a person.
    public String getName() {
        return this.name;
    }

    // returns the type of a person.
    public int getType() {
        return this.type;
    }
}
