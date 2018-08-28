package zalora.twitsplit.ui.message;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import zalora.twitsplit.R;
import zalora.twitsplit.base.BaseActivity;
import zalora.twitsplit.base.BaseFragment;
import zalora.twitsplit.data.room.entity.MessageModel;
import zalora.twitsplit.utils.CommonUtils;

import static android.widget.LinearLayout.VERTICAL;

public class HistoryMessageFragment extends BaseFragment implements BaseActivity.FragmentBackListener, HistoryMessageView{
    private View llEmpty;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private HistoryMessageAdapter historyAdapter;
    private ProgressDialog progressBar;
    private HistoryMessagePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_message, container, false);
        ((BaseActivity) getActivity()).fragmentBackListener = this;

        mRecyclerView = view.findViewById(R.id.recycler_view);
        llEmpty = view.findViewById(R.id.ll_empty);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        historyAdapter = new HistoryMessageAdapter(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(historyAdapter);
        mRecyclerView.setRecyclerListener(mRecycleListener);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getActivity(), VERTICAL);
        mRecyclerView.addItemDecoration(itemDecor);

        View btnAddRecord = view.findViewById(R.id.btn_new_record);
        btnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAddNewMessage();
            }
        });
        showEmptyView();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new HistoryMessagePresenter(this);
        title = ((MessageActivity)getActivity()).setActionBarTitle(R.string.title_history);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadData(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void showEmptyView() {
        if (historyAdapter != null && historyAdapter.getItemCount() > 0) {
            llEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            llEmpty.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    private RecyclerView.RecyclerListener mRecycleListener = new RecyclerView.RecyclerListener() {
        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            //Todo : recycled data
        }
    };

    @Override
    public boolean onFragmentBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage("Are you want to exit?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing
                    }
                });
        builder.create().show();
        return false;
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
    public void callAddNewMessage() {
        ((MessageActivity)getActivity()).changeToNewMessageView();
    }

    @Override
    public void onLoadedData(List<MessageModel> datas) {
        historyAdapter.initData(datas);
        if (datas.size() != 0) {
            mRecyclerView.scrollToPosition(datas.size() - 1);
        }

        showEmptyView();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }
}
