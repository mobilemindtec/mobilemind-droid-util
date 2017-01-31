package br.com.mobilemind.api.droidutil.tools;

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
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import br.com.mobilemind.api.utils.MobileMindUtil;

public class ProgressBarManager {

    private ProgressDialog progressDialog;
    private final Object progressSyncronize = new Object();
    private View mainView;
    private Context context;
    private Activity activity;
    private int resMessage;
    private String message;
    private String title;

    public ProgressBarManager(Context context, View mainView, int resMessage) {
        super();
        this.mainView = mainView;
        this.context = context;
        this.resMessage = resMessage;
    }

    public ProgressBarManager(Activity activity, String message) {
        super();
        this.context = activity;
        this.activity = activity;
        this.message = message;
    }

    public ProgressBarManager(Activity activity, int resMessage) {
        super();
        this.context = activity;
        this.activity = activity;
        this.message = message;
    }

    public ProgressBarManager setMessage(String message) {
        this.message = message;
        return this;
    }

    public ProgressBarManager setTitle(String title) {
        this.title = title;
        return this;
    }

    public void openProgressDialog() {
        synchronized (this.progressSyncronize) {
            if (this.progressDialog == null) {
                this.progressDialog = this.createProgressDialog();
            }
        }

    }

    public boolean isOpeneed() {
        synchronized (this.progressSyncronize) {
            if (this.progressDialog == null) {
                return this.progressDialog.isShowing();
            }
        }
        return false;
    }

    public void closeProgressDialog() {
        this.post(new Runnable() {
            @Override
            public void run() {
                synchronized (progressSyncronize) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }
            }
        });
    }

    public ProgressDialog createProgressDialog() {

        if (MobileMindUtil.isNullOrEmpty(title)) {
            title = "Alert";
        }

        if (MobileMindUtil.isNullOrEmpty(message)) {
            message = context.getString(this.resMessage);
        }
        return ProgressDialog.show(context, title, message, true, false);
    }

    public void post(Runnable run) {
        if (activity != null) {
            activity.runOnUiThread(run);
        } else {
            this.mainView.post(run);
        }
    }

    public void addMessage(final String message) {
        this.post(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) {
                    progressDialog.setMessage(message);
                }
            }
        });
    }
}
