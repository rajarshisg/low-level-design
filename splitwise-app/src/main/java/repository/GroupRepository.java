package repository;

import exception.AlreadyExistsException;
import exception.CannotFindException;
import model.IGroup;
import model.PublicGroup;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GroupRepository implements IRepository<PublicGroup> {
    ConcurrentHashMap<String, PublicGroup> groups;

    public GroupRepository() {
        this.groups = new ConcurrentHashMap<>();
    }

    public void insert(PublicGroup group) {
        if (groups.containsKey(group.getId())) {
            throw new AlreadyExistsException("Cannot insert: Group " + group + " as it already exist in repository!");
        }
        groups.put(group.getId(), group);
    }

    public void delete(String id) {
        if (!groups.containsKey(id)) {
            throw new CannotFindException("Cannot delete: Group with ID " + id + " as it do not exist in repository!");
        }

        groups.remove(id);
    }

    public List<PublicGroup> getAll() {
        return groups
                .keySet()
                .stream()
                .map(id -> groups.get(id))
                .toList();
    }

    public PublicGroup getById(String id) {
        if (!groups.containsKey(id)) {
            throw new CannotFindException("Cannot delete: User with ID " + id + " as it do not exist in repository!");
        }

        return groups.get(id);
    }
}