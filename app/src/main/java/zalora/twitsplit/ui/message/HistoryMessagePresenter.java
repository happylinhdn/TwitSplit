package zalora.twitsplit.ui.message;

import android.content.Context;
import android.os.Handler;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zalora.twitsplit.data.room.entity.MessageModel;
import zalora.twitsplit.data.room.repository.MessageRepository;

public class HistoryMessagePresenter {
    private HistoryMessageView historyMessageView;

    public HistoryMessagePresenter(HistoryMessageView historyMessageView) {
        this.historyMessageView = historyMessageView;
    }

    public void loadData(Context context) {
        if (historyMessageView != null) {
            historyMessageView.showProgress();
        }

        MessageRepository messageRepository = MessageRepository.getInstance(context.getApplicationContext());
        messageRepository.getAllMessage()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<MessageModel>>() {
                    @Override
                    public void accept(List<MessageModel> messageModels) {
                        if (historyMessageView != null) {
                            historyMessageView.onLoadedData(messageModels);
                            historyMessageView.hideProgress();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        if (historyMessageView != null) {
                            historyMessageView.onError(throwable.getMessage());
                            historyMessageView.hideProgress();
                        }
                    }
                });
    }

    public void onDestroy() {
        historyMessageView = null;
    }
}
