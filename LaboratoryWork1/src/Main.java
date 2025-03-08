//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Point2D point1 = new Point2D(5, 7);
        System.out.println(point1);
        Point2D points1 = new Point2D(new int[]{1, 2, 3, 4, 5}, new int[]{6, 7, 8, 9, 11});
        System.out.println(points1);
        Point2D points2 = new Point2D(new int[]{15, 8, 1, 0, 2}, new int[]{53, 77, 12, 33, 15});
        System.out.println();

        Vector2D vector1 = new Vector2D(5, 7);
        System.out.println(vector1);
        Vector2D vector2 = new Vector2D(9, 77);
        Vector2D vectors = new Vector2D(points1, points2);
        System.out.println(vectors);
        System.out.println();

        for (Vector2D vector : vectors){
            System.out.println("Module of " + vector + " = " + Vector2D.abs(vector));
            System.out.println(vector + " + " + vector1 + " = " + vector.add(vector1));
            System.out.println(vector + " - " + vector1 + " = " + vector.subtract(vector1));
            System.out.println(vector + " * " + vector1 + " = " + vector.dotProduct(vector1));
            System.out.println(vector + " * " + 3 + " = " + vector.vectorNumberMultiplication(3));
            System.out.println(vector + " x " + vector1 + " = " + vector.crossProduct(vector1));
            System.out.println("Module of " + vector + " x " + vector1 + " = " + vector.crossProductModule(vector1));
            System.out.println("<" + vector + ", " + vector1 + ", " + vector2 + "> = " + vector.mixedProduct(vector1, vector2));


            System.out.println();
        }

        System.out.println(vectors.getVector(4));
        Vector2D vector = new Vector2D(4, 9);
        vectors.setVector(2, vector);
        System.out.println(vectors);

        Point2D point = new Point2D(11, 11);
        points1.setPoint(3, point);
        System.out.println(points1);
    }
}