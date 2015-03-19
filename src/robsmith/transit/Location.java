package robsmith.transit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by robsmith on 3/14/2015.
 */
public class Location {
    protected final double latitude;
    protected final double longitude;

    protected Set<Trip> trips = new HashSet<Trip>();
    protected Set<Stop> stops = new HashSet<Stop>();
    protected Set<Route> routes = new HashSet<Route>();

    Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected void setAsStart() {
        try {
            URL url = new URL(String.format("http://api.pugetsound.onebusaway.org/api/where/trips-for-location.json?key=%s&lat=%s&lon=%s&latSpan=0.008&lonSpan=0.008", Main.apiKey, latitude, longitude));
            JSONObject jsonObject = NetworkHelper.getContents(url);

            JSONObject data = (JSONObject) jsonObject.get("data");
            JSONObject references = (JSONObject) data.get("references");
            JSONArray tripsArray = (JSONArray) references.get("trips");

            Iterator<JSONObject> iterator = tripsArray.iterator();
            while (iterator.hasNext()) {
                JSONObject tripJson = iterator.next();
                String tripId = (String) tripJson.get("id");
                String routeId =(String) tripJson.get("routeId");
                Route route = new Route(routeId);
                Trip trip = new Trip(tripId, route);
                routes.add(route);
                trips.add(trip);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Date getTimeForRoute(Route route) {
        if (!routes.contains(route)) {
            return null;
        }
        for (Trip trip : trips) {
            if (route.equals(trip.route)) {
                Date date = new Date(trip.timestamp);
            }
        }
        return null;
    }

    protected void setAsEnd() {
        try {
            URL url = new URL(String.format("http://api.pugetsound.onebusaway.org/api/where/stops-for-location.json?key=%s&lat=%s&lon=%s&latSpan=0.008&lonSpan=0.008", Main.apiKey, latitude, longitude));
            JSONObject jsonObject = NetworkHelper.getContents(url);

            JSONObject data = (JSONObject) jsonObject.get("data");
            JSONArray list = (JSONArray) data.get("list");

            Iterator<JSONObject> iterator = list.iterator();
            while (iterator.hasNext()) {
                JSONObject stopJson = iterator.next();
                String stopId = (String) stopJson.get("id");
                Stop stop = new Stop(stopId);
                JSONArray routesForStop = (JSONArray) stopJson.get("routeIds");
                Iterator<String> routeIterator = routesForStop.iterator();
                while (routeIterator.hasNext()) {
                    String routeId = routeIterator.next();
                    Route route = new Route(routeId);
                    stop.addRoute(route);
                    routes.add(route);
                    System.out.println("reaches end route " + routeId);
                }
                stops.add(stop);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
