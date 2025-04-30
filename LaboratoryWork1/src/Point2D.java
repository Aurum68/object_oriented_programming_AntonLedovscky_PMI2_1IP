import java.util.*;

public class Point2D{


    private final int WIDTH = 100;
    private final int HEIGHT = 100;

    private int x;
    private int y;

    public Point2D(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void setX(int x) {
        if (x < 0 || x > WIDTH) {
            throw new IllegalArgumentException("x must be between 0 and WIDTH");
        }
        this.x = x;
    }

    public int get(int index){
        if (index == 0) return x;
        if (index == 1) return y;
        throw new IndexOutOfBoundsException("index must be 0 or 1");
    }

    private void setY(int y) {
        if (y < 0 || y > HEIGHT) {
            throw new IllegalArgumentException("y must be between 0 and HEIGHT");
        }
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point2D point2D)) throw new ClassCastException();
        return x == point2D.x && y == point2D.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }



    @Override
    public String toString() {
        return "Point2D{" +
                "x=" + this.x +
                ", y=" + this.y +
                '}';
    }
}
