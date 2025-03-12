import java.util.*;

public class Vector2D implements Iterable<Vector2D> {
    private int x;
    private int y;
    private int z;

    public Vector2D(int x, int y, Integer... z) {
        this.x = x;
        this.y = y;
        this.z = z.length > 0 ? z[0] : 0;
    }

    private List<Vector2D> vectors;
    
    public Vector2D(Point2D point1, Point2D point2) {
        if (point1.iterator().hasNext() && point2.iterator().hasNext()) {
            if (point1.size() != point2.size()) {
                System.err.println("Error: point1 and point2 have different sizes");
                return;
            }

            vectors = new ArrayList<>();
            for (int i = 0; i < point1.size(); i++) {
                vectors.add(new Vector2D(point1.getPoint(i), point2.getPoint(i)));
            }

        }else if (!point1.iterator().hasNext() && !point2.iterator().hasNext()) {
            this.x = point2.getX() - point1.getX();
            this.y = point2.getY() - point1.getY();

        }else {
            System.err.println("Error: point1 and point2 must be both Arrays or both Points");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int getZ() {
        return z;
    }

    public int size() {
        if (vectors != null) {return vectors.size();}
        return 0;
    }

    public Vector2D getVector(int index) {
        if (vectors == null || index < 0 || index > vectors.size()) {
            System.err.println("Error: index out of bounds");
            return null;
        }

        return vectors.get(index);
    }

    public void setVector(int index, Vector2D vector) {
        if (vectors == null || index < 0 || index > vectors.size()) {
            System.err.println("Error: index out of bounds");
            return;
        }
        if (vector == null) {
            System.err.println("Error: vector is null");
            return;
        }

        vectors.set(index, vector);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return x == vector2D.x && y == vector2D.y && z == vector2D.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public Iterator<Vector2D> iterator() {
        if (vectors != null) {return vectors.iterator();}
        return Collections.emptyIterator();
    }

    @Override
    public String toString() {
        if (vectors != null) {return vectors.toString();}
        return z == 0 ? "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}' :
                "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public static String abs(Vector2D vector){
        double abs = Math.sqrt((double) vector.getX() * vector.getX()
                        + (double) vector.getY() * vector.getY()
                        + (double) vector.getZ() * vector.getZ());
        return String.format("%.2f", abs);
    }

    public static int dotProduct(Vector2D vector1, Vector2D vector2){
        return vector1.getX() * vector2.getX() + vector1.getY() * vector2.getY() + vector1.getZ() * vector2.getZ();
    }

    public static Vector2D crossProduct(Vector2D vector1, Vector2D vector2){
        return new Vector2D(vector1.getY() * vector2.getZ() - vector1.getZ() * vector2.getY(),
                vector1.getZ() * vector2.getX() - vector1.getX() * vector2.getZ(),
                vector1.getX() * vector2.getY() - vector1.getY() * vector2.getX());
    }

    public static String crossProductModule(Vector2D vector1, Vector2D vector2){
        return abs((crossProduct(vector1, vector2)));
    }

    public Vector2D add(Vector2D vector) {
        return new Vector2D(x + vector.getX(), y + vector.getY(), z + vector.getZ());
    }

    public Vector2D subtract(Vector2D vector) {
        return new Vector2D(x - vector.getX(), y - vector.getY(), z - vector.getZ());
    }

    public int dotProduct(Vector2D vector) {
        return this.x * vector.getX() + this.y * vector.getY() + this.z * vector.getZ();
    }

    public Vector2D vectorNumberMultiplication(int number) {
        return new Vector2D(x * number, y * number, z * number);
    }

    public Vector2D crossProduct(Vector2D vector) {
        return new Vector2D(this.y * vector.getZ() - this.z * vector.getY(),
                this.z * vector.getX() - this.x * vector.getZ(),
                this.x * vector.getY() - this.y * vector.getX());
    }

    public String crossProductModule(Vector2D vector) {
        return abs(crossProduct(vector));
    }

    public int mixedProduct(Vector2D vector1, Vector2D vector2) {
        return this.x * vector1.getY() * vector2.getZ()
                + this.y * vector1.getZ() * vector2.getX()
                + this.z * vector1.getX() * vector2.getY()
                - this.z * vector1.getY() * vector2.getX()
                - this.y * vector1.getX() * vector2.getZ()
                - this.x * vector1.getZ() * vector2.getY();
    }
}
