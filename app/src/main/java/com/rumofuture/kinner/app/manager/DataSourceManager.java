package com.rumofuture.kinner.app.manager;

import android.content.Context;

import com.rumofuture.kinner.model.source.StyleRepository;
import com.rumofuture.kinner.model.source.AlbumRepository;
import com.rumofuture.kinner.model.source.FavoriteRepository;
import com.rumofuture.kinner.model.source.FollowRepository;
import com.rumofuture.kinner.model.source.MusicRepository;
import com.rumofuture.kinner.model.source.ReviewRepository;
import com.rumofuture.kinner.model.source.UserRepository;
import com.rumofuture.kinner.model.source.local.StyleLocalDataSource;
import com.rumofuture.kinner.model.source.local.AlbumLocalDataSource;
import com.rumofuture.kinner.model.source.local.FavoriteLocalDataSource;
import com.rumofuture.kinner.model.source.local.FollowLocalDataSource;
import com.rumofuture.kinner.model.source.local.MusicLocalDataSource;
import com.rumofuture.kinner.model.source.local.ReviewLocalDataSource;
import com.rumofuture.kinner.model.source.local.UserLocalDataSource;
import com.rumofuture.kinner.model.source.remote.StyleRemoteDataSource;
import com.rumofuture.kinner.model.source.remote.AlbumRemoteDataSource;
import com.rumofuture.kinner.model.source.remote.FavoriteRemoteDataSource;
import com.rumofuture.kinner.model.source.remote.FollowRemoteDataSource;
import com.rumofuture.kinner.model.source.remote.MusicRemoteDataSource;
import com.rumofuture.kinner.model.source.remote.ReviewRemoteDataSource;
import com.rumofuture.kinner.model.source.remote.UserRemoteDataSource;

/**
 * Created by WangZhenqi on 2017/1/20.
 */

public class DataSourceManager {

    public static UserRepository provideUserRepository(Context context) {
        return UserRepository.getInstance(
                UserLocalDataSource.getInstance(context),
                UserRemoteDataSource.getInstance()
        );
    }

    public static StyleRepository provideAlbumRepository(Context context) {
        return StyleRepository.getInstance(
                StyleLocalDataSource.getInstance(context),
                StyleRemoteDataSource.getInstance()
        );
    }

    public static AlbumRepository provideBookRepository(Context context) {
        return AlbumRepository.getInstance(
                AlbumLocalDataSource.getInstance(context),
                AlbumRemoteDataSource.getInstance()
        );
    }

    public static FavoriteRepository provideFavoriteRepository(Context context) {
        return FavoriteRepository.getInstance(
                FavoriteLocalDataSource.getInstance(context),
                FavoriteRemoteDataSource.getInstance()
        );
    }

    public static FollowRepository provideFollowRepository(Context context) {
        return FollowRepository.getInstance(
                FollowLocalDataSource.getInstance(context),
                FollowRemoteDataSource.getInstance()
        );
    }

    public static MusicRepository providePageRepository(Context context) {
        return MusicRepository.getInstance(
                MusicLocalDataSource.getInstance(context),
                MusicRemoteDataSource.getInstance()
        );
    }

    public static ReviewRepository provideReviewRepository(Context context) {
        return ReviewRepository.getInstance(
                ReviewLocalDataSource.getInstance(context),
                ReviewRemoteDataSource.getInstance()
        );
    }
}
