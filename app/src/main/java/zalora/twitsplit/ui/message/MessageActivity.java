package zalora.twitsplit.ui.message;

import android.support.v4.app.FragmentTransaction;

import zalora.twitsplit.R;
import zalora.twitsplit.base.BaseActivity;
import zalora.twitsplit.base.BaseFragment;

public class MessageActivity extends BaseActivity {
    @Override
    public void initFragment() {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        HistoryMessageFragment fragment = new HistoryMessageFragment();
        tx.replace(R.id.activity_template_frame, fragment);
        tx.commit();
    }

    public void changeToNewMessageView() {
        BaseFragment fragment = new NewMessageFragment();
        super.displaySelectedScreen(fragment, 1);
    }
}
