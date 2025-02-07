package Entity.Specification;

public class Specification {
    private final int id;
    private final String color;
    private final int size;

    public Specification(int id, String color, int size) {
        this.id = id;
        this.color = color;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Specification{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", size=" + size +
                '}';
    }
}
