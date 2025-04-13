import java.util.*;

public class Vector2D{
    private int x;
    private int y;
    private int z;

    public Vector2D(int x, int y, Integer... z) {
        this.x = x;
        this.y = y;
        this.z = z.length > 0 ? z[0] : 0;
    }
    
    public Vector2D(Point2D point1, Point2D point2) {
        this.x = point2.getX() - point1.getX();
        this.y = point2.getY() - point1.getY();
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

    public int get(int index) {
        if (index == 0) return x;
        else if (index == 1) return y;
        throw new IndexOutOfBoundsException("index out of bounds");
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
    public String toString() {
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

    public static double abs(Vector2D vector){
        double abs = Math.sqrt((double) vector.getX() * vector.getX()
                        + (double) vector.getY() * vector.getY()
                        + (double) vector.getZ() * vector.getZ());
        double scale = Math.pow(10, 3);
        return Math.floor(abs * scale) / scale;
    }

    public static int dotProduct(Vector2D vector1, Vector2D vector2){
        return vector1.getX() * vector2.getX() + vector1.getY() * vector2.getY() + vector1.getZ() * vector2.getZ();
    }

    public static Vector2D crossProduct(Vector2D vector1, Vector2D vector2){
        return new Vector2D(vector1.getY() * vector2.getZ() - vector1.getZ() * vector2.getY(),
                vector1.getZ() * vector2.getX() - vector1.getX() * vector2.getZ(),
                vector1.getX() * vector2.getY() - vector1.getY() * vector2.getX());
    }

    public static double crossProductModule(Vector2D vector1, Vector2D vector2){
        return abs((crossProduct(vector1, vector2)));
    }

    public Vector2D add(Vector2D vector) {
        return new Vector2D(x + vector.getX(), y + vector.getY(), z + vector.getZ());
    }

    public Vector2D subtract(Vector2D vector) {
        return new Vector2D(x - vector.getX(), y - vector.getY(), z - vector.getZ());
    }

    public int dotProduct(Vector2D vector) {
        return dotProduct(this, vector);
    }

    public Vector2D vectorNumberMultiplication(int number) {
        return new Vector2D(x * number, y * number, z * number);
    }

    public Vector2D crossProduct(Vector2D vector) {
        return crossProduct(this, vector);
    }

    public double crossProductModule(Vector2D vector) {return crossProductModule(this, vector);}

    public int mixedProduct(Vector2D vector1, Vector2D vector2) {
        return this.x * vector1.getY() * vector2.getZ()
                + this.y * vector1.getZ() * vector2.getX()
                + this.z * vector1.getX() * vector2.getY()
                - this.z * vector1.getY() * vector2.getX()
                - this.y * vector1.getX() * vector2.getZ()
                - this.x * vector1.getZ() * vector2.getY();
    }
}
