package application.talk.usecases.message;

import application.talk.domains.Message;
import application.talk.enums.FinalResult;
import application.talk.usecases.UseCase;
import application.talk.usecases.adapters.DataStorage;

import java.util.List;

public class MessageRetrievalByNumber extends UseCase<MessageRetrievalByNumber.InputValues, MessageRetrievalByNumber.OutputValues> {
    private DataStorage _dataStorage;
    private TopLatestMessageRetrieval _getTopMessages;

    public MessageRetrievalByNumber(DataStorage dataStorage, TopLatestMessageRetrieval getTopMessages) {
        super();
        _dataStorage = dataStorage;
        _getTopMessages = getTopMessages;
    }

    @Override
    public OutputValues execute(InputValues input) {
        List<Message> foundMessage = _getTopMessages.execute(
                new TopLatestMessageRetrieval.InputValues(input._nRetrievedMessages, 0, input._conversationId)
        ).getfoundMessages();

        return new OutputValues(FinalResult.SUCCESSFUL, "", foundMessage);
    }

    public static class InputValues {
        private int _nRetrievedMessages;
        private String _conversationId;

        public InputValues(int nRetrievedMessages, String conversationId) {
            _nRetrievedMessages = nRetrievedMessages;
            _conversationId = conversationId;
        }
    }

    public static class OutputValues {
        private final FinalResult RESULT;
        private final String MESSAGE;
        private List<Message> _foundMessages;

        public OutputValues(FinalResult result, String message, List<Message> messages) {
            MESSAGE = message;
            RESULT = result;
            _foundMessages = messages;
        }

        public FinalResult getResult() {
            return RESULT;
        }

        public List<Message> getfoundMessages() {
            return _foundMessages;
        }

        public String getMessage() {
            return MESSAGE;
        }
    }
}
