//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Point2D point1 = new Point2D(5, 7);
        System.out.println(point1);
        Point2D point2 = new Point2D(8, 8);
        System.out.println(point2);

        Vector2D vector1 = new Vector2D(5, 7);
        System.out.println(vector1);
        Vector2D vector2 = new Vector2D(point1, point2);
        System.out.println(vector2);
        Vector2D vector3 = new Vector2D(6, 9);
        System.out.println(vector3);

        System.out.println("Module of " + vector1 + ": " + Vector2D.abs(vector1));
        System.out.println("Module of " + vector2 + ": " + Vector2D.abs(vector2));
        System.out.println(vector1 + " + " + vector2 + " = " + vector1.add(vector2));
        System.out.println(vector1 + " - " + vector2 + " = " + vector1.subtract(vector2));
        System.out.println(vector1 + " * " + vector2 + " = " + vector1.dotProduct(vector2));
        System.out.println(vector1 + " x " + vector2 + " = " + vector1.crossProduct(vector2));
        System.out.println(vector1 + " * 3 = " + vector1.vectorNumberMultiplication(3));
        System.out.println("Module of " + vector1 + " x " + vector2 + " = " + vector1.crossProductModule(vector2));
        System.out.println(vector1 + " x " + vector2 + " x " + vector3 + " = " + vector1.mixedProduct(vector2, vector3));
        System.out.println(vector1 + ".x = " + vector1.get(0));
    }
}