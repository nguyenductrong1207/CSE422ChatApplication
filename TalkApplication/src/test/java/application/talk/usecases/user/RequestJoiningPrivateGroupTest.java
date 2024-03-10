package application.talk.usecases.user;

import application.talk.domains.PrivateGroup;
import application.talk.domains.User;
import application.talk.infrastructure.data.InMemoryDataStorage;
import application.talk.usecases.adapters.DataStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestJoiningPrivateGroupTest {

    DataStorage _storage;
    RequestJoiningPrivateGroup _useCase;

    @Before
    public void setUp() throws Exception {
        _storage = InMemoryDataStorage.getInstance();
        _useCase = new RequestJoiningPrivateGroup(_storage);
    }

    @After
    public void tearDown() throws Exception {
        _storage.cleanAll();
    }

    @Test
    public void testRequestJoiningPrivateGroup() {
        User requester = new User("Trong1", "123456");
        PrivateGroup privateGroup = new PrivateGroup("Nhuc Dau", new User("Trong", "123456"));

        RequestJoiningPrivateGroup.InputValues input = new RequestJoiningPrivateGroup.InputValues(requester, privateGroup);
        RequestJoiningPrivateGroup.OutputValues output = _useCase.execute(input);

        assertEquals(RequestJoiningPrivateGroup.RequestJoiningPrivateGroupResult.SUCCESSFUL, output.getResult());
    }


}
