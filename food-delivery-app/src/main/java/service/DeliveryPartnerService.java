package service;

import model.DeliveryPartner;
import model.Location;
import repository.DeliveryPartnerRepository;
import strategy.NearestDeliveryPartnerAssignmentStrategy;

import java.util.List;

public class DeliveryPartnerService {
    private final DeliveryPartnerRepository repository;
    private final NearestDeliveryPartnerAssignmentStrategy strategy;

    public DeliveryPartnerService(DeliveryPartnerRepository repository) {
        this.repository = repository;
        this.strategy = new NearestDeliveryPartnerAssignmentStrategy();
    }

    public void registerPartner(String id, String name) {
        DeliveryPartner partner = new DeliveryPartner(id, name);
        partner.getIsAvailable().set(true);
        repository.insert(partner);
    }

    public void updateLocation(String partnerId, long longitude, long latitude) {
        DeliveryPartner partner = repository.getById(partnerId);
        partner.getLocation().setLatitude(latitude);
        partner.getLocation().setLongitude(longitude);
    }

    public DeliveryPartner assignPartner(Location restaurantLocation) {
        List<DeliveryPartner> partners = repository.getAll();
        return strategy.getRider(restaurantLocation, partners);
    }

    public boolean markUnavailable(String partnerId) {
        DeliveryPartner partner = repository.getById(partnerId);
        return partner.getIsAvailable().compareAndSet(true, false);
    }

    public void markAvailable(String partnerId) {
        DeliveryPartner partner = repository.getById(partnerId);
        partner.getIsAvailable().set(true);
    }
}