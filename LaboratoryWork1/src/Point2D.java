import java.util.*;

public class Point2D implements Iterable<Point2D>{


    private final int WIDTH = 100;
    private final int HEIGHT = 100;

    private int x;
    private int y;
    private boolean correct;

    public Point2D(int x, int y) {
        setX(x);
        setY(y);
    }

    private List<Point2D> points;

    public Point2D(int[] Xs, int[] Ys) {
        if (Xs.length != Ys.length){
            System.err.println("Invalid coordinates: Xs.size() not equal Ys.size()");
            return;
        }
        points = new ArrayList<>();
        for (int i = 0; i < Xs.length; i++) {
            Point2D point = new Point2D(Xs[i], Ys[i]);
            if (!point.isCorrect()) {
                System.err.println("Invalid coordinate has number " + (i + 1));
                continue;
            }
            points.add(point);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private boolean isCorrect(){
        return correct;
    }

    private void setX(int x) {
        if (x < 0 || x > WIDTH) {
            System.err.println("Invalid coordinates. X must be in range [0," + WIDTH + "]");
            this.correct = false;
            return;
        }
        this.x = x;
        this.correct = true;
    }

    private void setY(int y) {
        if (y < 0 || y > HEIGHT) {
            System.err.println("Invalid coordinates. Y must be in range [0," + HEIGHT + "]");
            this.correct = false;
            return;
        }
        this.y = y;
        this.correct = true;
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

    public void setPoint(int index, Point2D point) {
        if (points == null || index < 0 || index >= points.size()) {
            System.err.println("Invalid index");
            return;
        }
        if (!point.isCorrect()) {
            System.err.println("Invalid point");
            return;
        }
        points.set(index, point);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        Point2D point2D = (Point2D) o;
        return x == point2D.x && y == point2D.y && correct == point2D.correct;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, correct);
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
                "x=" + this.x +
                ", y=" + this.y +
                '}';
    }
}
