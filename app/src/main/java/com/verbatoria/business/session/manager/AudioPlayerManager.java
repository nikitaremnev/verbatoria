package com.verbatoria.business.session.manager;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.session.ISessionInteractor;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Реализация плеера для записи
 *
 * @author nikitaremnev
 */
public class AudioPlayerManager implements IAudioPlayerManager {

    @Inject
    public Context mContext;

    private int mCurrentMusicIndex = 1;

    private MediaPlayer mMediaPlayer;

    private ISessionInteractor.IPlayerCallback mPlayerCallback;

    private int[] sMusicRaw = new int[]{
            R.raw.zvuk1,
            R.raw.zvuk2,
            R.raw.zvuk3,
            R.raw.zvuk4,
            R.raw.zvuk5,
            R.raw.zvuk6,
            R.raw.zvuk7,
            R.raw.zvuk8,
            R.raw.zvuk9,
            R.raw.zvuk10,
            R.raw.zvuk11,
            R.raw.zvuk12
    };

    public AudioPlayerManager(ISessionInteractor.IPlayerCallback playerCallback) {
        mPlayerCallback = playerCallback;
        VerbatoriaApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void playClick() {
        setPlayingFileName();
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            mPlayerCallback.setUpPauseMode();
        } else if (sMusicRaw.length >= mCurrentMusicIndex) {
            setUpMediaPlayer();
            AssetFileDescriptor afd = getAssetFileDescriptor(mCurrentMusicIndex);
            if (afd == null) {
                showErrorSnackbar();
            } else {
                preparePlayer(afd);
            }
        } else {
            showErrorSnackbar();
        }
    }

    @Override
    public void pauseClick() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    @Override
    public void nextClick() {
        moveToNextPlayingFile();
        pausePlayer();
        setPlayingFileName();
    }

    @Override
    public void backClick() {
        moveToPreviousPlayingFile();
        pausePlayer();
        setPlayingFileName();
    }

    @Override
    public void showPlayer() {
        mCurrentMusicIndex = 1;
    }

    @Override
    public void hidePlayer() {
        pausePlayer();
    }

    @Override
    public void dropPlayer() {
        Log.e("test", "audioPlayer: dropPlayer()");
        try {
            pausePlayer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        sMusicRaw = null;
    }

    /*
        Инициализация плеера
     */
    private void setUpMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.start();
            mPlayerCallback.setUpPauseMode();
        });
        mMediaPlayer.setOnCompletionListener(mediaPlayer -> {
            mPlayerCallback.setUpPlayMode();
            mMediaPlayer = null;
        });
    }

    /*
        Подготовка плеера к воспроизведению
     */
    private void preparePlayer(AssetFileDescriptor afd) {
        try {
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorSnackbar();
        }
    }

    /*
        Получение музыкального файла для воспроизведения
     */
    private AssetFileDescriptor getAssetFileDescriptor(int index) {
        return mContext.getResources().openRawResourceFd(sMusicRaw[index - 1]);
    }

    /*
        Пауза плеера
     */
    private void pausePlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /*
        Вперед к следующему файлу
     */
    private void moveToNextPlayingFile() {
        mCurrentMusicIndex++;
        if (sMusicRaw.length < mCurrentMusicIndex) {
            mCurrentMusicIndex = 1;
        }
    }

    /*
        Назад к предыдущему файлу
     */
    private void moveToPreviousPlayingFile() {
        mCurrentMusicIndex--;
        if (mCurrentMusicIndex < 1) {
            mCurrentMusicIndex = 1;
        }
    }

    /*
        Отображение ошибки
     */
    private void showErrorSnackbar() {
        mPlayerCallback.showPlayerError(mContext.getString(R.string.session_player_error));
    }

    /*
        Отображение названия файла
     */
    private void setPlayingFileName() {
        mPlayerCallback.showPlayingFileName(Integer.toString(mCurrentMusicIndex));
    }

}
