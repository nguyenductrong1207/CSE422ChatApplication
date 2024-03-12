package application.talk.usecases.message;

import application.talk.domains.Message;
import application.talk.usecases.UseCase;
import application.talk.usecases.adapters.DataStorage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GetMessagesByTime extends UseCase<GetMessagesByTime.InputValues, GetMessagesByTime.OutputValues> {
	private DataStorage _dataStorage;

	public GetMessagesByTime(DataStorage dataStorage) {
		super();
		_dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) {
		List<Message> messages =  _dataStorage.getMessages().getAll();
		List<Message> foundMessages = new ArrayList<>();

		for(Message message :messages){
			if(input._time.isAfter(message.getTimestamp())){
				break;
			}

			foundMessages.add(message);
		}

		if(foundMessages.isEmpty()){
			return new OutputValues(GetLastestMessagesResult.FAILED, "");
		}

		OutputValues output =  new OutputValues(GetLastestMessagesResult.SUCCESSFUL, "");
		output.setFoundMessages(foundMessages);

		return output;
	}

	public static class InputValues {
		private LocalDateTime _time;

		public InputValues(LocalDateTime time) {
			_time = time;
		}
	}

	public static class OutputValues {
		private final GetLastestMessagesResult RESULT;
		private final String MESSAGE;
		private List<Message> _foundMessages;

		public OutputValues(GetLastestMessagesResult result, String message) {
			MESSAGE = message;
			RESULT = result;
		}

		public GetLastestMessagesResult getResult() {
			return RESULT;
		}

		public List<Message> getFoundMessages() {
			return _foundMessages;
		}

		public void setFoundMessages(List<Message> foundMessages) {
			_foundMessages = foundMessages;
		}

		public String getMessage() {
			return MESSAGE;
		}
	}

	public enum GetLastestMessagesResult {
		SUCCESSFUL, FAILED
	}
}