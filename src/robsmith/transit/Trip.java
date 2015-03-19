package robsmith.transit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by robsmith on 3/14/2015.
 */
public class Trip {
    protected final String id;
    protected final Route route;
    protected String headsign;
    protected long timestamp;

    Trip(String id, Route route) {
        this.id = id;
        this.route = route;
    }


    protected Set<ConnectionPoint> findConnectingRoutes(Location end) {
        Set<ConnectionPoint> points = new HashSet<ConnectionPoint>();

        try {

            // http://api.pugetsound.onebusaway.org/api/where/stops-for-route/1_100254.json?key=TEST&version=2
            URL url = new URL(String.format("http://api.pugetsound.onebusaway.org/api/where/trip-details/%s.json?key=%s", id, Main.apiKey));
            JSONObject jsonObject = NetworkHelper.getContents(url);

            JSONObject data = (JSONObject) jsonObject.get("data");
            JSONObject references = (JSONObject) data.get("references");
            JSONArray stops = (JSONArray) references.get("stops");

            Iterator<JSONObject> iterator = stops.iterator();
            while (iterator.hasNext()) {
                JSONObject stopJson = iterator.next();
                String stopId = (String) stopJson.get("id");
                Stop stop = new Stop(stopId);
                JSONArray routesForStop = (JSONArray) stopJson.get("routeIds");
                Iterator<String> routeIterator = routesForStop.iterator();
                while (routeIterator.hasNext()) {
                    String routeId = routeIterator.next();
                    Route route = new Route(routeId);
                    if (end.routes.contains(route)) {
                        points.add(new ConnectionPoint(route, this, stop));
                        break;
                    } else {
                        System.out.println("no match route " + routeId);
                    }
                }
            }
            Iterator<Route> iterator1 = end.routes.iterator();
            while (iterator1.hasNext()) {
                System.out.println("actual " + iterator1.next().id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return points;
    }

    @Override
    public boolean equals(Object t) {
        if (t instanceof Trip) {
            return id.equals(((Trip) t).id);
        }
        return false;
    }
}
