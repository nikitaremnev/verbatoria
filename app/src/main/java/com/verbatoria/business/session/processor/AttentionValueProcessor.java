package com.verbatoria.business.session.processor;

import java.util.ArrayList;

/**
 * Процессор для обработки значений
 *
 * @author nikitaremnev
 */
public class AttentionValueProcessor {

    private static ArrayList<Integer> sAttentionQueue;
    private static int T = 2;
    private static int Fa = 2;

    static {
        sAttentionQueue = new ArrayList<>();
    }

    private static int processValue(int value) {
        if (sAttentionQueue == null) {
            sAttentionQueue = new ArrayList<>();
            sAttentionQueue.add(value);
            return -1;
        }
        if (sAttentionQueue.size() == Fa * T) {
            sAttentionQueue.remove(0);
            sAttentionQueue.add(value);
            return meanValue();
        } else {
            sAttentionQueue.add(value);
            if (sAttentionQueue.size() == Fa * T) {
                return meanValue();
            }
            return -1;
        }
    }

    private static int meanValue() {
        int sum = 0;
        for (int i = 0; i < sAttentionQueue.size(); i ++) {
            sum += sAttentionQueue.get(i);
        }
        return sum / sAttentionQueue.size();
    }
}
