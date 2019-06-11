import java.util.ArrayList;

public class GlyphRecognition {

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        final int POINTS = io.getInt();
        ArrayList<Vector> vectorList = new ArrayList<>(POINTS);
        double[] distToOrigo = new double[POINTS];
        for (int i = 0; i < POINTS; i++) {
            Vector vector = new Vector(io.getInt(), io.getInt());
            vectorList.add(vector);
            distToOrigo[i] = vector.distToOrigo();
        }

        int bestShape = -1;
        double bestRatio = 0;
        for (int shape = 3; shape < 9; shape++) {
            double smallestLengthAB = Double.MAX_VALUE;
            double biggestLengthAB = 0;

            double angleABC = ((Math.PI * (shape - 2)) / shape) / 2; // HALF OF ONE OF THE INNER ANGLES

            for (int point = 0; point < POINTS; point++) {
                Vector currentPoint = vectorList.get(point);
                double lengthAC = distToOrigo[point];

                // FIND ANGLE FROM X-AXIS TO POINT, THEN MOVE POINT TO FIRST TRIANGLE
                double angleBAC = Math.atan2(currentPoint.getY(), currentPoint.getX());
                while (angleBAC > 2 * Math.PI / shape) {
                    angleBAC -= 2 * Math.PI / shape;
                }
                while (angleBAC <= 0) {
                    angleBAC += 2 * Math.PI / shape;
                }

                double angleACB = Math.PI - angleBAC - angleABC; // FIND THE SECRET ANGLE (SUM 180)
                double lengthAB = (lengthAC * Math.sin(angleACB)) / Math.sin(angleABC); // FIND LENGTH OF RADIUS, BY
                // SOLVING AAS TRIANGLE
                if (lengthAB < smallestLengthAB)
                    smallestLengthAB = lengthAB;

                if (lengthAB > biggestLengthAB)
                    biggestLengthAB = lengthAB;

            }
            double smallestShapeAreal = 0.5 * smallestLengthAB * smallestLengthAB * Math.sin(2 * Math.PI / shape); // CALCULATE AREA
            double biggestShapeAreal = 0.5 * biggestLengthAB * biggestLengthAB * Math.sin(2 * Math.PI / shape);
            double shapeRatio = smallestShapeAreal / biggestShapeAreal;

            if (bestRatio < shapeRatio) {
                bestRatio = shapeRatio;
                bestShape = shape;
            }
        }
        io.print(bestShape + " " + bestRatio);
        io.close();
    }
}
