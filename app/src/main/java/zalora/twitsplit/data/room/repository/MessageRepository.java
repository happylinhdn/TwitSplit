package zalora.twitsplit.data.room.repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import zalora.twitsplit.data.room.MyDatabase;
import zalora.twitsplit.data.room.dao.MessageDao;
import zalora.twitsplit.data.room.entity.MessageModel;

public class MessageRepository {
    private static MessageRepository INSTANCE;
    public static MessageRepository getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (MessageRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MessageRepository(context);

                }
            }
        }
        return INSTANCE;
    }

    private MessageDao messageDao;

    private MessageRepository(Context context) {
        MyDatabase db = MyDatabase.getDatabase(context);
        messageDao = db.messageDao();
    }

    public void insertNewMessages(MessageModel... messageModels) {
        AgentAsyncTask task = new AgentAsyncTask();
        task.execute(messageModels);
    }

    public void updateOrCreateMessage(MessageModel messageModel) {
        AgentAsyncTask task = new AgentAsyncTask();
        task.execute(messageModel);
    }

    public Single<MessageModel> getLastMessage() {
        Single<MessageModel> results = messageDao.getLastMessage();
        return results;
    }

    public Flowable<List<MessageModel>> getAllMessage() {
        Flowable<List<MessageModel>> results = messageDao.getAllMessage();
        return results;
    }

    class AgentAsyncTask extends AsyncTask<MessageModel, Void, Void> {

        @Override
        protected Void doInBackground(MessageModel... data) {
            messageDao.insertMessage(data);
            return null;
        }
    }

    public interface LoadCallback {
        public void onLoaded();
    }
}
