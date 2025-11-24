import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {

    public static final int NAME_LEN = 35;
    public static final int DESC_LEN = 75;
    public static final int ID_LEN   = 6;

    private String name;
    private String description;
    private String ID;
    private double cost;

    public Product(String name, String description, String ID, double cost) {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.cost = cost;
    }


    private String pad(String s, int length) {
        if (s.length() > length)
            return s.substring(0, length);

        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < length)
            sb.append(" ");
        return sb.toString();
    }


    public String getRAFName()        { return pad(name, NAME_LEN); }
    public String getRAFDescription() { return pad(description, DESC_LEN); }
    public String getRAFID()          { return pad(ID, ID_LEN); }


    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getID() { return ID; }
    public double getCost() { return cost; }

    @Override
    public String toString() {
        return "Product{ID=" + ID +
                ", name=" + name +
                ", description=" + description +
                ", cost=" + cost +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product p = (Product) o;
        return Double.compare(p.cost, cost) == 0 &&
                Objects.equals(name, p.name) &&
                Objects.equals(description, p.description) &&
                Objects.equals(ID, p.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, ID, cost);
    }
}