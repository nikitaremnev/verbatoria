package com.remnev.verbatoriamini.model;

/**
 * Created by nikitaremnev on 27.03.16.
 */
public class ActionID {

    public static final int RECORD_START_ID = 1;
    public static final int RECORD_END_ID = 2;
    public static final int WORD_START_ID = 3;
    public static final int WORD_END_ID = 4;
    public static final int WORD_FAIL_ID = 5;
    public static final int WORD_SUCCESS_ID = 6;
    public static final int WORD_SKIP_ID = 7;
    public static final int CONNECT_ID = 8;
    public static final int DISCONNECT_ID = 9;
    public static final int WORD_BACK_ID = 10;

    public static final String RECORD_START_STRING = "Запись";
    public static final String RECORD_END_STRING = "Конец";
    public static final String WORD_START_STRING = "Старт";
    public static final String WORD_END_STRING = "Время вышло";
    public static final String WORD_FAIL_STRING = "Ошибка";
    public static final String WORD_SUCCESS_STRING = "Успех";
    public static final String WORD_SKIP_STRING = "Пропуск";
    public static final String CONNECT_STRING = "Коннект";
    public static final String DISCONNECT_STRING = "Дисконнект";
    public static final String BACK_STRING = "Кнопка назад";

}
