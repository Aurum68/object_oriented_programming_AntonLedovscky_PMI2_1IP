//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Point2D point1 = new Point2D(5, 7);
        System.out.println(point1);
        Point2D point2 = new Point2D(new int[]{1, 2, 3, 4, 5}, new int[]{6, 7, 8, 9, 11});
        System.out.println(point2);
        Point2D point3 = new Point2D(new int[]{15, 8, 1, 0, 2}, new int[]{53, 77, 12, 33, 15});
        System.out.println();

        Vector2D vector1 = new Vector2D(5, 7);
        System.out.println(vector1);
        Vector2D vector2 = new Vector2D(point2, point3);
        System.out.println(vector2);
        System.out.println();

        for (Vector2D vector : vector2){
            System.out.println(vector + " + " + vector1 + " = " + vector.add(vector1));
            System.out.println(vector + " - " + vector1 + " = " + vector.subtract(vector1));
            System.out.println(vector + " * " + vector1 + " = " + vector.scalarMultiply(vector1));
            System.out.println(vector + " * " + 3 + " = " + vector.multiplyNumber(3));
            System.out.println("Module of " + vector + " = " + Vector2D.abs(vector));
            System.out.println();
        }
    }
}