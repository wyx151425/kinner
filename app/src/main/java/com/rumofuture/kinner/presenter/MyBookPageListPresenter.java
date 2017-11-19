package com.rumofuture.kinner.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.MyBookPageListContract;
import com.rumofuture.kinner.app.manager.ImageChooseManager;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Music;
import com.rumofuture.kinner.model.schema.AlbumSchema;
import com.rumofuture.kinner.model.source.AlbumDataSource;
import com.rumofuture.kinner.model.source.MusicDataSource;
import com.smile.filechoose.api.ChosenImage;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/15.
 */

public class MyBookPageListPresenter implements MyBookPageListContract.Presenter, MusicDataSource.PageListGetCallback, MusicDataSource.TotalGetCallback,
        MusicDataSource.PageSaveCallback, MusicDataSource.PageDeleteCallback, MusicDataSource.PageUpdateCallback, AlbumDataSource.BookUpdateCallback {

    private static final int NO_REQUEST_CODE = 0;
    private static final int UPLOAD_PAGE_REQUEST_CODE = 1;
    private static final int UPDATE_PAGE_REQUEST_CODE = 2;

    private int requestCode = NO_REQUEST_CODE;

    private MyBookPageListContract.View mView;
    private AlbumDataSource mBookRepository;
    private MusicDataSource mPageRepository;
    private ImageChooseManager mImageChooseManager;
    private Album mAlbum;
    private Music mMusic;

    private ChosenImage mPageImage;

    public MyBookPageListPresenter(
            @NonNull MyBookPageListContract.View view,
            @NonNull AlbumDataSource bookRepository,
            @NonNull MusicDataSource pageRepository
    ) {
        mView = view;
        mBookRepository = bookRepository;
        mPageRepository = pageRepository;
    }

    @Override
    public void start() {
        requestCode = NO_REQUEST_CODE;
        mMusic = null;
        mPageImage = null;
        mImageChooseManager = new ImageChooseManager((Fragment) mView, this);  // 构造成功默认创建一个图片选择器
    }

    /**
     * 图片选择成功后 最后一个处理方法
     * @param chosenImage 被图片选择器封装的被选择的图片
     */
    @Override
    public void setChosenImage(ChosenImage chosenImage) {
        mPageImage = chosenImage;
        // 由于处理过程在异步线程中进行，所以需要调用主线程进行后续操作
        ((Fragment) mView).getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 图片选择成功后，根据请求码进行判断，如果请求码为1，则将进行的是新漫画分页保存
                // 如果请求码为2，则将进行的是漫画分页更新操作
                if (mView.isActive()) {
                    final BmobFile newImage = new BmobFile(new File(mPageImage.getFilePathOriginal()));
                    if (UPLOAD_PAGE_REQUEST_CODE == requestCode) {
                        mView.showProgressBar(true, R.string.prompt_uploading);
                        mMusic.setMusic(newImage);
                        mPageRepository.savePage(mMusic, MyBookPageListPresenter.this);
                    } else if (UPDATE_PAGE_REQUEST_CODE == requestCode) {
                        mView.showProgressBar(true, R.string.prompt_updating);
                        mPageRepository.updatePage(mMusic, newImage, MyBookPageListPresenter.this);
                    }
                }
            }
        });
    }

    @Override
    public void releaseImageChooseManager() {

    }

    /**
     * 图片选择方法，view发出图片选择请求时将调用此方法
     */
    @Override
    public void chooseImage() {
        // 获取选择到的图片
        mImageChooseManager.chooseImage();
    }

    @Override
    public void submitChoice(int requestCode, Intent data) {
        // 图片选择的中间操作
        mImageChooseManager.submitChoice(requestCode, data);
    }

    @Override
    public void uploadPage(Music music) {
        start();
        requestCode = UPLOAD_PAGE_REQUEST_CODE;
        mMusic = music;
        chooseImage();
    }

    @Override
    public void deletePage(Music music) {
        mView.showProgressBar(true, R.string.prompt_deleting);
        mPageRepository.deletePage(music, this);
    }

    @Override
    public void updatePage(Music music) {
        start();
        requestCode = UPDATE_PAGE_REQUEST_CODE;
        mMusic = music;
        chooseImage();
    }

    @Override
    public void getBookPageList(Album album, int pageCode) {
        mAlbum = album;
        mPageRepository.getPageListByBook(mAlbum, pageCode, this);
        mPageRepository.getPageTotal(mAlbum, this);
    }

    @Override
    public void onPageSaveSuccess(Music music) {
        if (mView.isActive()) {
            mView.showProgressBar(false, 0);
            mView.showPageSaveSuccess(music);
        }

        mAlbum.increment(AlbumSchema.Table.Cols.MUSIC_TOTAL);
        mBookRepository.updateBook(mAlbum, null, this);
    }

    @Override
    public void onPageSaveFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showProgressBar(false, 0);
            mView.showPageSaveFailed(e);
        }
    }

    @Override
    public void onPageDeleteSuccess(Music music) {
        if (mView.isActive()) {
            mView.showProgressBar(false, 0);
            mView.showPageDeleteSuccess(music);
        }

        mAlbum.increment(AlbumSchema.Table.Cols.MUSIC_TOTAL, -1);
        mBookRepository.updateBook(mAlbum, null, this);
    }

    @Override
    public void onPageDeleteFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showProgressBar(false, 0);
            mView.showPageDeleteFailed(e);
        }
    }

    @Override
    public void onPageUpdateSuccess(Music music) {
        if (mView.isActive()) {
            mView.showProgressBar(false, 0);
            mView.showPageUpdateSuccess(music);
        }
    }

    @Override
    public void onPageUpdateFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showProgressBar(false, 0);
            mView.showPageUpdateFailed(e);
        }
    }

    @Override
    public void onPageListGetSuccess(List<Music> musicList) {
        if (mView.isActive()) {
            mView.showPageListGetSuccess(musicList);
        }

        mAlbum.setMusicTotal(musicList.size());
        mBookRepository.updateBook(mAlbum, null, this);
    }

    @Override
    public void onPageListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showPageListGetFailed(e);
        }
    }

    @Override
    public void onBookUpdateSuccess(Album album) {

    }

    @Override
    public void onBookUpdateFailed(BmobException e) {

    }

    @Override
    public void onTotalGetSuccess(Integer total) {
        mAlbum.setMusicTotal(total);
        mBookRepository.updateBook(mAlbum, null, this);
    }

    @Override
    public void onTotalGetFailed(BmobException e) {

    }
}
