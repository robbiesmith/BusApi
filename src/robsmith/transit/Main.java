package robsmith.transit;

import java.util.HashSet;
import java.util.Set;

public class Main {

    protected static String apiKey = "TEST";

    public static void main(String[] args) {
	// write your code here

        // http://api.pugetsound.onebusaway.org/api/where/trips-for-location.json?key=TEST&lat=47.653&lon=-122.307&latSpan=0.008&lonSpan=0.008
        // to get trips for start

        // http://api.pugetsound.onebusaway.org/api/where/stops-for-location.json?key=TEST&lat=47.653435&lon=-122.305641
        // to get stops and routes for end

        // http://api.pugetsound.onebusaway.org/api/where/trip-details/1_21812112.json?key=TEST
        // to find items on this route that reach the destination
        // then cross reference to find the transfer point
        Location start = new Location(47.656,-122.314271);
        start.setAsStart();
        Location end = new Location(47.671285,-122.292608);
        end.setAsEnd();

        findComboTrip(start, end);
    }

    private static void findComboTrip(Location start, Location end) {
        if (start.trips == null) {
            return;
        }

        Set<ConnectionPoint> points = new HashSet<ConnectionPoint>();
        for (Trip trip:start.trips) {
            points.addAll( trip.findConnectingRoutes(end));
        }

        for (ConnectionPoint point: points) {
            System.out.println(point.trip.id);
        }
    }
}
