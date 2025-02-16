import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Point2D implements Iterable<Point2D>{


    private final int WIDTH = 100;
    private final int HEIGHT = 100;

    private int x;
    private int y;
    private boolean correct;

    public Point2D(int x, int y) {
        if (0 <= x && x <= WIDTH && 0 <= y && y <= HEIGHT) {
            this.x = x;
            this.y = y;
            this.correct = true;
       }else {
            System.out.println("Invalid coordinates. Coordinates must be in range [0,100]");
            this.correct = false;
        }
    }

    private List<Point2D> points;

    public Point2D(int[] Xs, int[] Ys) {
        points = new ArrayList<>();
        if (Xs.length == Ys.length) {
            for (int i = 0; i < Xs.length; i++) {
                points.add(new Point2D(Xs[i], Ys[i]));
                if (!points.get(i).isCorrect()){
                    System.out.println("Invalid coordinate has number " + (i + 1));
                }
            }
        }else {
            System.out.println("Invalid coordinates: Xs.size() not equal Ys.size()");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isCorrect() {
        return correct;
    }

    private void setX(int x) {
        this.x = x;
    }

    private void setY(int y) {
        this.y = y;
    }

    public int size() {
        if (points != null) {return points.size();}
        return 0;
    }

    public Point2D getPoint(int index) {
        if (points != null && index >= 0 && index < points.size()) {
            return points.get(index);
        }else {
            return null;
        }
    }

    public void setPoint(int index, int x, int y) {
        if (points != null && index >= 0 && index < points.size() && 0 <= x && x <= WIDTH && 0 <= y && y <= HEIGHT) {
            points.get(index).setX(x);
            points.get(index).setY(y);
        }
    }

    @Override
    public Iterator<Point2D> iterator() {
        if (points != null) {
            return points.iterator();
        }else {return Collections.emptyIterator();}
    }

    @Override
    public String toString() {
        if (points != null) {
            return points.toString();
        }
        return "Point2D{" +
                "x=" + x +
                ", y=" + y +
                ", correct=" + correct +
                '}';
    }
}
