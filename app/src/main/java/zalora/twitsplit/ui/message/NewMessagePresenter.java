package zalora.twitsplit.ui.message;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import zalora.twitsplit.data.room.entity.MessageModel;
import zalora.twitsplit.data.room.repository.MessageRepository;
import zalora.twitsplit.utils.AppConstants;
import zalora.twitsplit.utils.CommonUtils;

public class NewMessagePresenter {
    private NewMessageView newMessageView;

    NewMessagePresenter(NewMessageView view) {
        this.newMessageView = view;
    }

    void onResume() {

    }

    void sendMessage(final String message) {
        if (newMessageView != null) {
            newMessageView.showProgress();
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                MessageInteractor.splitMessage(message, new MessageInteractor.OnFinishedListener() {
                    @Override
                    public void onFinished(String[] items) {
                        if (newMessageView != null) {
                            newMessageView.setItems(items);
                            newMessageView.hideProgress();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        if (newMessageView != null) {
                            newMessageView.showError(error);
                            newMessageView.hideProgress();
                        }
                    }
                });
            }
        });


    }

    void onDestroy() {
        newMessageView = null;
    }

    public void saveMessageToDb(Context context, String[] messages) {
        if (messages == null || messages.length == 0) return;

        MessageModel[] messageModels = new MessageModel[messages.length];
        String hashGroupKey = AppConstants.PREF_NAME + "_" + CommonUtils.getTimeStamp();
        for (int i = 0; i < messages.length; i++) {
            messageModels[i] = new MessageModel(messages[i], CommonUtils.getCurrent().getTime(), hashGroupKey);
        }
        MessageRepository messageRepository = MessageRepository.getInstance(context.getApplicationContext());
        messageRepository.insertNewMessages(messageModels);
    }
}
