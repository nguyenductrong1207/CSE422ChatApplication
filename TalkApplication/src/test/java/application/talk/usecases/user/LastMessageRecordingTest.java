package application.talk.usecases.user;

import application.talk.domains.ChatEntity;
import application.talk.domains.Conversation;
import application.talk.domains.Message;
import application.talk.domains.User;
import application.talk.enums.FinalResult;
import application.talk.infrastructure.data.InMemoryDataStorage;
import application.talk.usecases.adapters.DataStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

public class LastMessageRecordingTest {
    private DataStorage _storage;
    private User _sender;
    private ChatEntity _receiver;
    private Message _message;

    @Before
    public void setUp() throws Exception {
        _storage = InMemoryDataStorage.getInstance();
        _sender = new User("trong", "1207");
        _receiver = new User("kiet", "071002");
        _message = new Message(_sender, LocalDateTime.now(), _receiver, "helo");

        Conversation newConversation = new Conversation(_sender, _receiver);
        newConversation.addMessage(_message);

        _storage.getConversations().add(newConversation);
    }

    @After
    public void tearDown() throws Exception {
        DataStorage storage = InMemoryDataStorage.getInstance();
        storage.cleanAll();
    }

    @Test
    public void testRecordingMessage() {
        String userID = _sender.getId();
        String messageID = _message.getId();
        LastMessageRecording.InputValues input = new LastMessageRecording.InputValues(userID, messageID);

        LastMessageRecording recordTest = new LastMessageRecording(_storage);
        LastMessageRecording.OutputValues outputValues = recordTest.execute(input);

        Assertions.assertEquals(FinalResult.SUCCESSFUL, outputValues.getResult());
        Assertions.assertNotNull(outputValues.getFoundMessage());
    }
}