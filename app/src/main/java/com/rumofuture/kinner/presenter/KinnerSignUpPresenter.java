package com.rumofuture.kinner.presenter;

import android.os.Handler;
import android.text.TextUtils;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.KinnerSignUpContract;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.model.source.UserDataSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by WangZhenqi on 2017/5/6.
 */

public class KinnerSignUpPresenter implements KinnerSignUpContract.Presenter, UserDataSource.UserSignUpCallback {

    private KinnerSignUpContract.View mView;
    private UserDataSource mUserRepository;

    private Handler mHandler;
    private int requestedTime;

    public KinnerSignUpPresenter(
            KinnerSignUpContract.View view,
            UserDataSource userRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
        start();
    }

    @Override
    public void start() {
        requestedTime = 59;
        mHandler = new Handler();
    }

    @Override
    public void requestSMSCode(String mobilePhoneNumber) {

        if (TextUtils.isEmpty(mobilePhoneNumber)) {
            mView.showMobilePhoneNumberError(R.string.prompt_field_required);
            return;
        } else if (!isMobilePhoneNumberValid(mobilePhoneNumber)) {
            mView.showMobilePhoneNumberError(R.string.prompt_invalid_user_mobile_phone_number);
            return;
        }

        BmobSMS.requestSMSCode(mobilePhoneNumber, "Nemo", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException e) {
                if (e == null) {
                    mHandler.postDelayed(runnable, 1000);
                    if (mView.isActive())
                        mView.showRequestSMSCodeSuccess(smsId);
                } else {
                    if (mView.isActive())
                        mView.showRequestSMSCodeFailed(e);
                }
            }
        });

    }

    @Override
    public void signUp(String name, String mobilePhoneNumber, String password, String smsCode) {

        if (TextUtils.isEmpty(name) && !isNameValid(name)) {
            mView.showNameError(R.string.prompt_invalid_user_name);
            return;
        }

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mView.showPasswordError(R.string.prompt_incorrect_user_password);
            return;
        }

        if (TextUtils.isEmpty(mobilePhoneNumber)) {
            mView.showMobilePhoneNumberError(R.string.prompt_field_required);
            return;
        } else if (!isMobilePhoneNumberValid(mobilePhoneNumber)) {
            mView.showMobilePhoneNumberError(R.string.prompt_invalid_user_mobile_phone_number);
            return;
        }

        mView.showProgressBar(true);
        final User user = new User();
        user.setName(name);
        user.setUsername(mobilePhoneNumber);
        user.setPassword(password);
        user.setMobilePhoneNumber(mobilePhoneNumber);
        user.setAge(0);
        user.setFollowTotal(0);
        user.setFollowerTotal(0);
        user.setFavoriteTotal(0);
        user.setAlbumTotal(0);
        user.setAuthorize(false);

        BmobSMS.verifySmsCode(user.getMobilePhoneNumber(), smsCode, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    user.setMobilePhoneNumberVerified(true);
                    mUserRepository.signUp(user, KinnerSignUpPresenter.this);
                } else {
                    if (mView.isActive()) {
                        mView.showProgressBar(false);
                        mView.showSignUpFailed(e);
                    }
                }
            }
        });
    }

    @Override
    public void onUserSignUpSuccess(User user) {
        if (mView.isActive()) {
            mView.showProgressBar(false);
            mView.showSignUpSuccess(user);
        }
    }

    @Override
    public void onUserSignUpFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showProgressBar(false);
            mView.showSignUpFailed(e);
        }
    }

    private boolean isNameValid(String name) {
        return name.length() >= 2;
    }

    /**
     * 此方法用于验证用户输入的手机号格式是否正确
     *
     * @param mobilePhoneNumber 用户输入的手机号
     * @return 验证结果
     */
    private boolean isMobilePhoneNumberValid(String mobilePhoneNumber) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNumber = pattern.matcher(mobilePhoneNumber);
        return isNumber.matches() && (11 == mobilePhoneNumber.length());
    }

    /**
     * 此方法用于验证用户输入的密码格式是否正确
     *
     * @param password 用户输入的密码
     * @return 验证结果
     */
    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    //新建一个线程，实现计时功能
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (requestedTime > 0) {
                requestedTime--;
                mView.showSMSCodeRequestTime(requestedTime + "s");
                mHandler.postDelayed(this, 1000);
            } else {
                requestedTime = 59;
                mView.showSMSCodeRequestTimeOut("重新获取");
                mHandler.removeCallbacks(runnable);
            }
        }
    };
}
