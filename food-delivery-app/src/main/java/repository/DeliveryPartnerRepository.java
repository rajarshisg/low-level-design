package repository;


import model.DeliveryPartner;
import model.Location;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DeliveryPartnerRepository implements Repository<DeliveryPartner> {
    ConcurrentHashMap<String, DeliveryPartner> deliveryPartners;

    public DeliveryPartnerRepository() {
        this.deliveryPartners = new ConcurrentHashMap<>();
    }

    public void insert(DeliveryPartner deliveryPartner) {
        if (deliveryPartners.containsKey(deliveryPartner.getId())) {
            System.out.println("[ERROR] Cannot insert: DeliveryPartner with ID " + deliveryPartner.getId() + " already exists!");
            return;
        }
        deliveryPartners.put(deliveryPartner.getId(), deliveryPartner);
    }

    public void delete(String id) {
        if (!deliveryPartners.containsKey(id)) {
            System.out.println("[ERROR] Cannot delete: DeliveryPartner with ID " + id + " does not exist!");
            return;
        }

        deliveryPartners.remove(id);
    }

    public List<DeliveryPartner> getAll() {
        return deliveryPartners
                .keySet()
                .stream()
                .map(id -> deliveryPartners.get(id))
                .toList();
    }

    public DeliveryPartner getById(String id) {
        if (!deliveryPartners.containsKey(id)) {
            System.out.println("[ERROR] Cannot find: DeliveryPartner with ID " + id + " does not exist!");
            return null;
        }

        return deliveryPartners.get(id);
    }

    public void updateLocation(String id, long latitude, long longitude) {
        if (!deliveryPartners.containsKey(id)) {
            System.out.println("[ERROR] Cannot find: DeliveryPartner with ID " + id + " does not exist!");
            return;
        }

        deliveryPartners.get(id).getLocation().setLatitude(latitude);
        deliveryPartners.get(id).getLocation().setLongitude(longitude);
    }
}