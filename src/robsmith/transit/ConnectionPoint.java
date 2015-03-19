package robsmith.transit;

/**
 * Created by robsmith on 3/15/2015.
 */
public class ConnectionPoint {
    protected final Route route;
    protected final Trip trip;
    protected final Stop stop;

    ConnectionPoint(Route route, Trip trip, Stop stop) {
        this.route = route;
        this.trip = trip;
        this.stop = stop;
    }

}
