import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Vector2D implements Iterable<Vector2D> {
    private int x;
    private int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private List<Vector2D> vectors;
    
    public Vector2D(Point2D point1, Point2D point2) {
        if (point1.iterator().hasNext() && point2.iterator().hasNext()) {
            if (point1.size() == point2.size()) {
                vectors = new ArrayList<>();
                for (int i = 0; i < point1.size(); i++) {
                    vectors.add(new Vector2D(point1.getPoint(i), point2.getPoint(i)));
                }
            }else {
                System.out.println("Error: point1 and point2 have different sizes");
            }
        }else {
            this.x = point2.getX() - point1.getX();
            this.y = point2.getY() - point1.getY();
        }
    }

    public int getX() {
        return x;
    }

    private void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    private void setY(int y) {
        this.y = y;
    }

    public int size() {
        if (vectors != null) {return vectors.size();}
        return 0;
    }

    public Vector2D getVector(int index) {
        if (vectors != null && index >= 0 && index < vectors.size()) {
            return vectors.get(index);
        }else {
            return null;
        }
    }

    public void setVector(int index, int x, int y) {
        if (vectors != null && index >= 0 && index < vectors.size() && 0 <= x) {
            vectors.get(index).setX(x);
            vectors.get(index).setY(y);
        }
    }

    @Override
    public Iterator<Vector2D> iterator() {
        if (vectors != null) {return vectors.iterator();}
        return Collections.emptyIterator();
    }

    @Override
    public String toString() {
        if (vectors != null) {return vectors.toString();}
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Vector2D add(Vector2D vector) {
        return new Vector2D(x + vector.getX(), y + vector.getY());
    }

    public Vector2D subtract(Vector2D vector) {
        return new Vector2D(x - vector.getX(), y - vector.getY());
    }

    public Vector2D scalarMultiply(Vector2D vector) {
        return new Vector2D(x * vector.getX(), y * vector.getY());
    }

    public Vector2D multiplyNumber(int number) {
        return new Vector2D(x * number, y * number);
    }

    public static String abs(Vector2D vector){
        double abs = Math.sqrt((double) vector.getX()* vector.getX() + (double) vector.getY()* vector.getY());
        return String.format("%.2f", abs);
    }

    // Векторное и смешанное произведения векторов невозможно вычислить на плоскости, так как эти операции определены только в пространстве!
}
