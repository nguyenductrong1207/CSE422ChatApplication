package application.talk.usecases.message;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import application.talk.enums.FinalResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.talk.domains.ChatEntity;
import application.talk.domains.Conversation;
import application.talk.domains.Message;
import application.talk.domains.PrivateGroup;
import application.talk.domains.User;
import application.talk.infrastructure.data.InMemoryDataStorage;
import application.talk.usecases.adapters.DataStorage;

public class AdminDeleteMessageTest {
	private DataStorage _storage;
	private AdminDeleteMessage _useCase;
	private User _sender;
	private Conversation _conversation;

	@Before
	public void setUp() throws Exception {
		_storage = InMemoryDataStorage.getInstance();
		DeleteMessage deleteMessage = new DeleteMessage(_storage);
		_useCase = new AdminDeleteMessage(_storage, deleteMessage);

		_sender = new User("kiet", "0710");
		PrivateGroup privateGroup = new PrivateGroup("group1", _sender);
		_conversation = new Conversation(_sender, privateGroup);

		for (int i = 0; i < 3; i++) {
			_conversation.addMessage(new Message(_sender, LocalDateTime.now(), privateGroup, "hello!"));
		}

		_storage.getConversations().add(_conversation);
		_storage.getGroups().add(privateGroup);
	}

	@After
	public void tearDown() throws Exception {
		DataStorage storage = InMemoryDataStorage.getInstance();
		storage.cleanAll();
	}

	@Test
	public void testRemovingMessgage() {
		Message removingMessage = _conversation.getMessages().get(0);
        
        AdminDeleteMessage.InputValues input = new AdminDeleteMessage.InputValues(_sender.getId(),
        		_conversation.getId(), removingMessage.getId());
        AdminDeleteMessage.OutputValues output = _useCase.execute(input);
        
        assertEquals(FinalResult.SUCCESSFUL, output.getResult());
	}
}
