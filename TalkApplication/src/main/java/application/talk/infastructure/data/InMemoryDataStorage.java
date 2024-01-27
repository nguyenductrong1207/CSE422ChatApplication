package application.talk.infastructure.data;

import application.talk.domains.Group;
import application.talk.domains.User;
import application.talk.infrastructure.repositories.InMemoryRepository;
import application.talk.usecases.adapters.DataStorage;
import application.talk.usecases.adapters.Repository;

public class InMemoryDataStorage implements DataStorage {
	private Repository<User> _users;
	private Repository<Group> _groups;
    private static InMemoryDataStorage storage;
	
	private InMemoryDataStorage() {
		_users =  new InMemoryRepository<User>();
	}
	
	public static synchronized InMemoryDataStorage getInstance() {
        if (storage == null) {
            storage = new InMemoryDataStorage();
        }
        
        return storage;
    }

	@Override
	public Repository<User> getUsers() {
		return _users;
	}

	@Override
	public Repository<Group> getGroups() {
		return _groups;
	}
	
	@Override
	public void cleanAll() {
		_users.deleteAll();
		_groups.deleteAll();
	}


}
