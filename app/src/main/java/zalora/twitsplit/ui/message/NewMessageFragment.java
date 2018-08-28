package zalora.twitsplit.ui.message;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import zalora.twitsplit.R;
import zalora.twitsplit.base.BaseFragment;
import zalora.twitsplit.utils.CommonUtils;

public class NewMessageFragment extends BaseFragment implements NewMessageView, TextWatcher, View.OnClickListener{
    private NewMessagePresenter presenter;
    private EditText edtInput;
    private TextInputLayout grpInput;
    private ProgressDialog progressBar;
    private Button btnSend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_message, container, false);
        edtInput = view.findViewById(R.id.edt_input);
        grpInput = view.findViewById(R.id.grp_input);
        btnSend = view.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);

        presenter = new NewMessagePresenter(this);

        edtInput.addTextChangedListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar = CommonUtils.showLoadingDialog(getContext());
    }

    @Override
    public void hideProgress() {
        if (progressBar != null) {
            progressBar.hide();
            progressBar = null;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = ((MessageActivity)getActivity()).setActionBarTitle("New Message");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s)) {
            btnSend.setEnabled(false);
        } else {
            btnSend.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId) {
            case R.id.btn_send:
                presenter.sendMessage(edtInput.getText().toString().trim());
                break;
        }
    }

    @Override
    public void setItems(String[] items) {
        presenter.saveMessageToDb(getContext(), items);
        Toast.makeText(getContext(), "The message has sent", Toast.LENGTH_LONG).show();
        CommonUtils.hideSoftKeyboard(getActivity());
        getActivity().onBackPressed();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        edtInput.setError(message);
    }
}
