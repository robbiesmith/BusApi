package robsmith.transit;

import org.json.simple.JSONObject;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by robsmith on 3/14/2015.
 */
public class Stop {
    private Location location = null;
    protected final String id;
    protected Set<Trip> trips = null;
    protected Set<Route> routes = null;

    Stop(String id) {
        this.id = id;
    }

    public void addRoute(Route route) {
        if (routes == null) {
            routes = new HashSet<Route>();
        }
        routes.add(route);
    }

    public void addTrip(Trip trip) {
        if (trips == null) {
            trips = new HashSet<Trip>();
        }
        trips.add(trip);
    }

    public Location getLocation() {
        if (location == null) {
            try {
                URL url = new URL(String.format("http://api.pugetsound.onebusaway.org/api/where/stop/%s.json?key=%s", id, Main.apiKey));
                JSONObject jsonObject = NetworkHelper.getContents(url);

                JSONObject data = (JSONObject) jsonObject.get("data");
                JSONObject entry = (JSONObject) data.get("entry");
                Double latitude = (Double) entry.get("lat");
                Double longitude = (Double) entry.get("lon");

                location = new Location(latitude, longitude);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return location;
    }

    @Override
    public boolean equals(Object s) {
        if (s instanceof Stop) {
            return id.equals(((Stop) s).id);
        }
        return false;
    }
}
