package zalora.twitsplit.ui.message;


import java.util.List;

import zalora.twitsplit.data.room.entity.MessageModel;

public interface HistoryMessageView {

    void showProgress();

    void hideProgress();

    void callAddNewMessage();

    void onLoadedData(List<MessageModel> datas);

    void onError(String error);
}