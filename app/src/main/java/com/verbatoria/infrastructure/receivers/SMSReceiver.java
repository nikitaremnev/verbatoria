package com.verbatoria.infrastructure.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.verbatoria.presentation.login.view.recovery.IRecoveryView;
import com.verbatoria.utils.Logger;

/**
 * @author nikitaremnev
 */
public class SMSReceiver extends BroadcastReceiver {

    private static final String TAG = SMSReceiver.class.getSimpleName();

    private static final String PDUS = "pdus";

    public static final String MESSAGE_FROM = "verbatoria";
    public static final String MESSAGE_BODY = "Ваш код для восстановления пароля: ";
    public static final String TELEPHONE_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    private IRecoveryView.SMSCallback mSMSCallback;

    public IRecoveryView.SMSCallback getSMSCallback() {
        return mSMSCallback;
    }

    public SMSReceiver setSMSCallback(IRecoveryView.SMSCallback SMSCallback) {
        mSMSCallback = SMSCallback;
        return this;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(TELEPHONE_ACTION)) {
            Bundle bundle = intent.getExtras();
            SmsMessage[] messages;

            if (bundle != null) {
                try{
                    Object[] pdus = (Object[]) bundle.get(PDUS);
                    messages = new SmsMessage[pdus.length];

                    for (int i = 0; i < messages.length; i ++){
                        messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);

                        String messageFrom = messages[i].getOriginatingAddress();
                        String messageBody = messages[i].getMessageBody();
                        Logger.e(TAG, "messageFrom: " + messageFrom);
                        Logger.e(TAG, "messageBody: " + messageBody);
                        if (getSMSCallback() != null && messageFrom.equals(MESSAGE_FROM)) {
                            getSMSCallback().showConfirmationCode(messageBody.replace(MESSAGE_BODY, ""));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
