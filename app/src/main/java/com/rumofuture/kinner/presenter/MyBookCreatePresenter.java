package com.rumofuture.kinner.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.MyBookCreateContract;
import com.rumofuture.kinner.app.manager.ImageChooseManager;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.model.schema.UserSchema;
import com.rumofuture.kinner.model.source.AlbumDataSource;
import com.rumofuture.kinner.model.source.UserDataSource;
import com.rumofuture.kinner.view.fragment.MyBookCreateFragment;
import com.smile.filechoose.api.ChosenImage;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/13.
 */

public class MyBookCreatePresenter implements MyBookCreateContract.Presenter, UserDataSource.UserInfoUpdateCallback, AlbumDataSource.BookSaveCallback {

    private MyBookCreateContract.View mView;
    private UserDataSource mUserRepository;
    private AlbumDataSource mBookRepository;
    private ImageChooseManager mImageChooseManager;
    private ChosenImage mCoverImage;

    public MyBookCreatePresenter(
            @NonNull MyBookCreateContract.View view,
            @NonNull UserDataSource userRepository,
            @NonNull AlbumDataSource bookRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
        mBookRepository = bookRepository;
        start();
    }

    @Override
    public void start() {
        mImageChooseManager = new ImageChooseManager((MyBookCreateFragment) mView, this);  // 构造成功默认创建一个图片选择器
    }

    /**
     * 图片选择方法，view发出图片选择请求时将调用此方法
     */
    @Override
    public void chooseImage() {
        start();
        // 获取选择到的图片
        mImageChooseManager.chooseImage();
    }

    @Override
    public void submitChoice(int requestCode, Intent data) {
        // 图片选择的中间操作
        mImageChooseManager.submitChoice(requestCode, data);
    }

    @Override
    public void createBook(final Album album) {

        // 如果未选择封面，则取消创建并提示错误
        if (mCoverImage == null) {
            if (mView.isActive())
                mView.showBookInfoError(R.string.prompt_book_cover_required);
            return;
        }

        // 如果未填写漫画册名称，则取消创建并提示错误
        if (TextUtils.isEmpty(album.getName())) {
            if (mView.isActive()) {
                mView.showBookInfoError(R.string.prompt_book_name_required);
            }
            return;
        }

        // 如果未填写漫画册风格，则取消创建并提示错误
        if (TextUtils.isEmpty(album.getStyle())) {
            if (mView.isActive())
                mView.showBookInfoError(R.string.prompt_book_style_required);
            return;
        }

        // 如果未填写漫画册简介，则取消创建并提示错误
        if (TextUtils.isEmpty(album.getIntroduction())) {
            if (mView.isActive())
                mView.showBookInfoError(R.string.prompt_book_introduction_required);
            return;
        }

        mView.showProgressBar(true);
        // 显示进度条
        album.setCover(new BmobFile(new File(mCoverImage.getFilePathOriginal())));
        album.setAuthor(BmobUser.getCurrentUser(User.class));
        album.setMusicTotal(0);
        album.setFavorTotal(0);
        album.setApprove(false);
        mBookRepository.saveBook(album, this);
    }

    @Override
    public void onBookSaveSuccess(Album album) {
        if (mView.isActive()) {
            mView.showBookCreateSuccess(album);
            mView.showProgressBar(false);
        }

        User currentUser = BmobUser.getCurrentUser(User.class);
        currentUser.increment(UserSchema.Table.Cols.ALBUM_TOTAL);
        mUserRepository.updateUserInfo(currentUser, this);
    }

    @Override
    public void onBookSaveFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showBookCreateFailed(e);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void setChosenImage(ChosenImage chosenImage) {
        mCoverImage = chosenImage;
        ((MyBookCreateFragment) mView).getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mView.isActive())
                    mView.showBookCoverHasChosen(mCoverImage.getFilePathOriginal());
            }
        });
    }

    @Override
    public void releaseImageChooseManager() {

    }

    @Override
    public void onUserInfoUpdateSuccess() {

    }

    @Override
    public void onUserInfoUpdateFailed(BmobException e) {

    }
}
