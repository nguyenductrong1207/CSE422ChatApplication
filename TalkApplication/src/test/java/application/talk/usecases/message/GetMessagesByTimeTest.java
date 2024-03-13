package application.talk.usecases.message;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import application.talk.domains.ChatEntity;
import application.talk.domains.Conversation;
import application.talk.domains.Message;
import application.talk.domains.User;
import application.talk.enums.FinalResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.talk.infrastructure.data.InMemoryDataStorage;
import application.talk.usecases.adapters.DataStorage;

public class GetMessagesByTimeTest {
    private DataStorage _storage;
    private GetMessagesByTime _useCase;
    private Conversation _newConversation;

    @Before
    public void setUp() throws Exception {
        _storage = InMemoryDataStorage.getInstance();
        _useCase = new GetMessagesByTime(_storage);

        User sender = new User("kiet", "123");
        User receiver = new User("duyen", "haha");

        _newConversation = new Conversation(sender, receiver);

        for (int i = 0; i < 5; i++) {
            _newConversation.addMessage(new Message(sender, LocalDateTime.now(), receiver, "hello"));
        }

        _storage.getConversations().add(_newConversation);
    }

    @After
    public void tearDown() throws Exception {
        _storage.cleanAll();
    }

    @Test
    public void testGetMessagesEmptyResult() {
        LocalDateTime currentTime = LocalDateTime.now();

        GetMessagesByTime.InputValues input = new GetMessagesByTime.InputValues(currentTime, "daucoiddau");
        GetMessagesByTime.OutputValues output = _useCase.execute(input);

        assertEquals(FinalResult.FAILED, output.getResult());
    }

    @Test
    public void testGetMessageValid() {
        LocalDateTime currentTime = LocalDateTime.now();

        GetMessagesByTime.InputValues input = new GetMessagesByTime.InputValues(currentTime, _newConversation.getId());
        GetMessagesByTime.OutputValues output = _useCase.execute(input);

        assertEquals("hello", output.getFoundMessages().get(0).getContent());
    }
}
