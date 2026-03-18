package strategies;

import models.Location;
import models.Rider;
import models.Trip;

import java.util.List;

public interface RiderMatchingStrategy {
    public Rider getRider(Location userLocation, List<Rider> riders);
}
