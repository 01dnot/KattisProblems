import java.util.ArrayList;

public class ImperfectGPS {

    private static ArrayList<Vector> gpsPointList = new ArrayList<>();
    private static int currentTime = 0;

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        final int POINTS = io.getInt();
        final int GPS_INTERVAL = io.getInt();
        int[] timeAtPoint = new int[POINTS];
        ArrayList<Vector> pointList = new ArrayList<>();
        for (int i = 0; i < POINTS; i++) {
            pointList.add(new Vector(io.getInt(), io.getInt()));
            timeAtPoint[i] = io.getInt();
        }


        int totalTime = timeAtPoint[POINTS - 1];
        gpsPointList.add(pointList.get(0));
        currentTime += timeAtPoint[0];


        while (currentTime + GPS_INTERVAL < totalTime) { // Use points and collect new points when GPS collects data.
            gpsPointList.add(getPosAt(currentTime + GPS_INTERVAL, pointList, timeAtPoint));
            currentTime += GPS_INTERVAL;
        }
        gpsPointList.add(pointList.get(POINTS - 1));

        double pathLength = findLength(pointList);
        double gpsLength = findLength(gpsPointList);
        io.print(100 * (pathLength - gpsLength) / pathLength);
        io.close();
    }

    private static double findLength(ArrayList<Vector> pointList) {
        double length = 0;
        for (int i = 1; i < pointList.size(); i++) {
            Vector e = pointList.get(i).minus(pointList.get(i - 1));
            length += Math.sqrt(e.dot(e));
        }
        return length;
    }

    private static Vector getPosAt(int atTime, ArrayList<Vector> pointList, int[] time) {
        int vector = 0;
        while (time[vector + 1] < atTime) {
            vector++;
        }
        double howMuchToGo = atTime - time[vector];
        double total = time[vector + 1] - time[vector];
        return pointList.get(vector).pointAt(howMuchToGo / total, pointList.get(vector + 1));
    }
}
