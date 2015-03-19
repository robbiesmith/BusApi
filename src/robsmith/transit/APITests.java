package robsmith.transit;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

/**
 * Created by robsmith on 3/15/2015.
 */
public class APITests {

    @Test
    public void routeEqualsTest() {
        Route first = new Route("66");
        Route second = new Route("66");

        Assert.assertTrue(first.equals(second));
    }

    /*
    @Test
    public void findConnectionsTest() {
        Location end = new Location(1.0, 1.0);
        Route route = new Route("1");
        Trip trip = new Trip("1", route);

        trip.findConnectingRoutes(end);
    }
    */

    @Test
    public void stopLocationTest() {
        Stop stop = new Stop("1_75403");
        Location location = stop.getLocation();
        Assert.assertEquals(location.latitude, 47.654366, 0.000001);
    }

    @Test
    public void stopContainsTest() {
        Location location = new Location(47.654366, 0.000001);
        System.out.println(location.trips.size());
        Iterator<Trip> iterator1 = location.trips.iterator();
        while (iterator1.hasNext()) {
            System.out.println("actual " + iterator1.next().id);
        }

        Assert.assertTrue(location.trips.contains(new Trip("1_26423343", null)));
    }
}
