package zalora.twitsplit.ui.message;


public interface NewMessageView {

    void showProgress();

    void hideProgress();

    void setItems(String[] items);

    void showError(String message);
}