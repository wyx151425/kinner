package com.rumofuture.kinner.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rumofuture.kinner.R;

public class KinnerProfessionPickerFragment extends DialogFragment {

    public static final String EXTRA_PROFESSION = "com.rumofuture.nemo.view.fragment.KinnerProfessionPickerFragment.profession";
    private static final String ARG_PROFESSION = "com.rumofuture.nemo.view.fragment.KinnerProfessionPickerFragment.profession";

    // 初始化职业名称数组
    private String[] mProfessionArray = {
            "计算机/互联网/通信",
            "生产/工艺/制造",
            "医疗/护理/制药",
            "金融/银行/投资/保险",
            "商业/服务业/个体经营",
            "文化/广告/传媒",
            "娱乐/艺术/表演",
            "律师/法务",
            "教育/培训",
            "公务员/行政/事业单位",
            "学生",
            "其他职业"
    };

    private View mFocusedViewPreviously;
    private ListView mListView;

    private int mProfessionIndex;
    private String mProfession;

    public KinnerProfessionPickerFragment() {

    }

    public static KinnerProfessionPickerFragment newInstance(@NonNull String profession) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROFESSION, profession);
        KinnerProfessionPickerFragment fragment = new KinnerProfessionPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取个人信息Fragment传来的职业名称
        mProfession = (String) getArguments().getSerializable(ARG_PROFESSION);
        // 获取用户当前职业对应的职业名称数组中的下标
        if (null != mProfession) {
            // 用户职业状态为未选择时
            if (mProfession.equals(getString(R.string.prompt_unselected))) {
                mProfessionIndex = mProfessionArray.length;
            } else {
                // 用户职业已选择过
                for (int index = 0; index < mProfessionArray.length; index++) {
                    if (mProfession.equals(mProfessionArray[index])) {
                        mProfessionIndex = index;
                    }
                }
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_nemo_list_view, null);

        mListView = view.findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), R.layout.item_nemo_profession_list, mProfessionArray);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*
                 * mFocusedViewPreviously为在此试图被点击时，前一个被点击的视图，逻辑如下：
                 * 1. 判断先前是否有试图被点击过，如果有，则将之前被点击的试图释放；
                 * 2. 将当前被点击的试图设置背景颜色，并获取其承载的职业名称；
                 * 3. 将先前被点击过的视图的引用设为此视图。
                 */
                if (mFocusedViewPreviously != null) {
                    mFocusedViewPreviously.setBackgroundColor(0);
                }
                view.setBackgroundColor(getResources().getColor(R.color.colorGainsboro));
                mProfession = mProfessionArray[i];
                mFocusedViewPreviously = view;
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.prompt_user_profession_pick)
                .setPositiveButton(R.string.prompt_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(Activity.RESULT_OK, mProfession);
                    }
                })
                .setNegativeButton(R.string.prompt_cancel, null)
                .create();
    }

    private void sendResult(int resultCode, String profession) {
        if (null == getTargetFragment())
            return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PROFESSION, profession);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
