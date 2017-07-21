package com.verbatoria.business.session.manager;

/**
 * Интерфейс плеера для записи
 *
 * @author nikitaremnev
 */
public interface IAudioPlayerManager {

    void playClick();
    void pauseClick();
    void nextClick();
    void backClick();

    void showPlayer();
    void hidePlayer();

    void dropPlayer();

}
