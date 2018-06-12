package com.yk.adverte.view;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Silence on 2018/6/12.
 * 监听短信数据库，自动获取短信验证码
 */

public class PhoneCode extends ContentObserver {
    Cursor mCursor;
    Activity mActivity;
    EditText mEdtContent;
    String smsContent;


    public PhoneCode(Activity activity, Handler handler, EditText editText) {
        super(handler);
        this.mActivity = activity;
        this.mEdtContent = editText;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        mCursor = mActivity.managedQuery(Uri.parse("content://sms/inbox"),
                new String[]{"_id", "address", "read", "body"}, "address=? and read=?",
                new String[]{mEdtContent.getText().toString(), "0"}, "_id desc");
        if (mCursor != null && mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            if (mCursor.moveToFirst()) {
                String smsbody = mCursor
                        .getString(mCursor.getColumnIndex("body"));
                String regEx = "(?<![0-9])([0-9]{" + 6 + "})(?![0-9])";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(smsbody.toString());
                while (m.find()) {
                    smsContent = m.group();
                    mEdtContent.setText(smsContent);
                }
            }
        }
    }

}
