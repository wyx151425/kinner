package com.rumofuture.kinner.presenter;

import android.content.Intent;

import com.rumofuture.kinner.app.KinnerPresenter;
import com.smile.filechoose.api.ChosenImage;

/**
 * Created by WangZhenqi on 2017/4/14.
 */

public interface KinnerImageUploadPresenter extends KinnerPresenter {
    void chooseImage();
    void submitChoice(int requestCode, Intent data);
    void setChosenImage(ChosenImage chosenImage);
    void releaseImageChooseManager();
}
