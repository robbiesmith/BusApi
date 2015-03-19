package robsmith.transit;

import java.util.List;

/**
 * Created by robsmith on 3/14/2015.
 */
public class Route {
    protected final String id;
    protected String name;
    private List<Stop> stops;

    Route(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object r) {
        if (r instanceof Route) {
            return id.equals(((Route) r).id);
        }
        return false;
    }
}
