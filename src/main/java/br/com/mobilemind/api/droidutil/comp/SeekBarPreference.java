package br.com.mobilemind.api.droidutil.comp;

/*
 * #%L
 * Mobile Mind - Droid Util
 * %%
 * Copyright (C) 2012 Mobile Mind Empresa de Tecnologia
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 *
 * @author Ricardo Bocchi
 */
public class SeekBarPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener {

    private static final String androidns = "http://schemas.android.com/apk/res/android";
    private SeekBar mSeekBar;
    private TextView mSplashText, mValueText;
    private Context mContext;
    private String mDialogMessage, mSuffix;
    private int mMax, mDefault, newValue;

    public SeekBarPreference(Context context, String title, String message, int value, int max) {
        super(context, null);
        mContext = context;

        mDialogMessage = title;
        mSuffix = message;
        mDefault = value;
        mMax = max;

    }

    @Override
    protected View onCreateDialogView() {
        LinearLayout.LayoutParams params;
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(6, 6, 6, 6);

        mSplashText = new TextView(mContext);
        if (mDialogMessage != null) {
            mSplashText.setText(mDialogMessage);
        }
        layout.addView(mSplashText);

        mValueText = new TextView(mContext);
        mValueText.setGravity(Gravity.CENTER_HORIZONTAL);
        mValueText.setTextSize(32);
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.addView(mValueText, params);

        mSeekBar = new SeekBar(mContext);
        mSeekBar.setOnSeekBarChangeListener(this);
        layout.addView(mSeekBar, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

//        if (shouldPersist()) {
//            mValue = getPersistedInt(mDefault);
//        }

        mSeekBar.setMax(mMax);
        mSeekBar.setProgress(mDefault);
        return layout;
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        mSeekBar.setMax(mMax);
        mSeekBar.setProgress(mDefault);
    }

    @Override
    protected void onSetInitialValue(boolean restore, Object defaultValue) {
        super.onSetInitialValue(restore, defaultValue);
        if (restore) {
            newValue = shouldPersist() ? getPersistedInt(mDefault) : 0;
        } else {
            newValue = (Integer) defaultValue;
        }
    }

    public void onProgressChanged(SeekBar seek, int value, boolean fromTouch) {
        String t = String.valueOf(value);
        mValueText.setText(mSuffix == null ? t : t.concat(mSuffix));
        if (shouldPersist()) {
            persistInt(value);
        }
        this.newValue = value;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        super.onClick(dialog, which);
        
        switch(which){
            case DialogInterface.BUTTON1:
                callChangeListener(new Integer(newValue));
                break;
        }
    }

    public void onStartTrackingTouch(SeekBar seek) {
    }

    public void onStopTrackingTouch(SeekBar seek) {
    }

    public void setMax(int max) {
        mMax = max;
    }

    public int getMax() {
        return mMax;
    }

    public void setProgress(int progress) {
        mDefault = progress;
        if (mSeekBar != null) {
            mSeekBar.setProgress(progress);
        }
    }

    public int getProgress() {
        return mDefault;
    }

    @Override
    public void setDefaultValue(Object defaultValue) {
        this.mDefault = (Integer) defaultValue;
        super.setDefaultValue(defaultValue);
    }
}