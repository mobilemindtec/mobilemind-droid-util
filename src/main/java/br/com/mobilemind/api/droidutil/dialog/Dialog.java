package br.com.mobilemind.api.droidutil.dialog;

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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;
import br.com.mobilemind.api.droidutil.tools.ViewUtil;

/**
 *
 * @author Ricardo Bocchi
 */
public class Dialog {

    private static DialogResult result;
    public static int ERROR_ICON_RESOURCE = -1;
    public static int OK_ICON_ICON_RESOURCE = -1;
    public static int WARNING_ICON_ICON_RESOURCE = -1;
    public static int INFORMATION_ICON_ICON_RESOURCE = -1;
    public static int QUESTION_ICON_ICON_RESOURCE = -1;
    public static int YES_DIALOG_RESOURCE = -1;
    public static int NO_DIALOG_RESOURCE = -1;
    public static int OK_DIALOG_RESOURCE = -1;
    public static int CANCEL_DIALOG_RESOURCE = -1;
    public static int TITLE_DIALOG_RESOURCE = -1;

    public static void showInfo(Context context, int resource) {
        createAlert(context, context.getString(resource), INFORMATION_ICON_ICON_RESOURCE);
    }

    public static void showInfo(Context context, int resource, final RespostaListener respostaListener) {
        showInfo(context, context.getString(resource), respostaListener);
    }

    public static void showInfo(Context context, String message) {
        createAlert(context, message, INFORMATION_ICON_ICON_RESOURCE);
    }

    public static void showInfo(Context context, String message, final RespostaListener respostaListener) {
        createAlert(context, message, INFORMATION_ICON_ICON_RESOURCE, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                respostaListener.onOk();
            }
        }, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                respostaListener.onCancel();
            }
        });
    }

    public static void showError(Context context, int resource) {
        createAlert(context, context.getString(resource), ERROR_ICON_RESOURCE);
    }

    public static void showError(Context context, String message) {
        createAlert(context, message, ERROR_ICON_RESOURCE);
    }

    public static void showError(Context context, String message, final RespostaListener respostaListener) {
        createAlert(context, message, ERROR_ICON_RESOURCE, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                respostaListener.onOk();
            }
        }, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                respostaListener.onCancel();
            }
        });
    }

    public static void showSuccess(Context context, int resource) {
        createAlert(context, context.getString(resource), OK_ICON_ICON_RESOURCE);
    }

    public static void showSuccess(Context context, String message) {
        createAlert(context, message, OK_ICON_ICON_RESOURCE);
    }

    public static void showSuccess(Context context, String message, final RespostaListener respostaListener) {
        createAlert(context, message, OK_ICON_ICON_RESOURCE, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                respostaListener.onOk();
            }
        }, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                respostaListener.onCancel();
            }
        });
    }

    public static void showSuccess(Context context, int resource, OnClickListener event) {
        createAlert(context, context.getString(resource), OK_ICON_ICON_RESOURCE, event);
    }

    public static void showSuccess(Context context, int resource, OnClickListener event, DialogInterface.OnCancelListener cancel) {
        createAlert(context, context.getString(resource), OK_ICON_ICON_RESOURCE, event, cancel);
    }
    
        public static void showSuccess(Context context, String message, OnClickListener event, DialogInterface.OnCancelListener cancel) {
        createAlert(context, message, OK_ICON_ICON_RESOURCE, event, cancel);
    }

    public static void showWarning(Context context, int resource) {
        createAlert(context, context.getString(resource), WARNING_ICON_ICON_RESOURCE);
    }

    public static void showWarning(Context context, String message) {
        createAlert(context, message, WARNING_ICON_ICON_RESOURCE);
    }

    public static void showWarning(Context context, String message, final RespostaListener respostaListener) {
        createAlert(context, message, WARNING_ICON_ICON_RESOURCE, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                respostaListener.onOk();
            }
        }, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                respostaListener.onCancel();
            }
        });
    }

    public static void showQuestion(Context context, int resource, final OnRespostEvent event) {
        showQuestion(context, context.getString(resource), event);
    }

    public static void showQuestion(Context context, String message, final OnRespostEvent event) {
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
        alertbox.setMessage(message);
        alertbox.setCancelable(false);
        alertbox.setPositiveButton(YES_DIALOG_RESOURCE, new OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                event.responded(DialogResult.YES);
            }
        });
        alertbox.setNegativeButton(NO_DIALOG_RESOURCE, new OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                event.responded(DialogResult.NO);
            }
        });
        alertbox.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                event.responded(DialogResult.NO);
            }
        });
        AlertDialog alert = alertbox.create();
        alert.setIcon(QUESTION_ICON_ICON_RESOURCE);
        alert.setTitle(TITLE_DIALOG_RESOURCE);
        alert.show();
    }

    public static void showReportQuestion(Context context, String message, final OnRespostEvent event) {
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
        alertbox.setMessage(message);
        alertbox.setCancelable(false);
        alertbox.setPositiveButton(YES_DIALOG_RESOURCE, new OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                event.responded(DialogResult.YES);
            }
        });
        alertbox.setNegativeButton(NO_DIALOG_RESOURCE, new OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                event.responded(DialogResult.NO);
            }
        });
        alertbox.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                event.responded(DialogResult.NO);
            }
        });
        AlertDialog alert = alertbox.create();
        alert.setIcon(QUESTION_ICON_ICON_RESOURCE);
        alert.setTitle(TITLE_DIALOG_RESOURCE);
        alert.show();
    }

    private static void createAlert(Context context, String message, int resource) {
        createAlert(context, message, resource, null);
    }

    private static void createAlert(Context context, String message, int resource,
            final OnClickListener event) {
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
        alertbox.setMessage(message);
        alertbox.setCancelable(false);
        alertbox.setPositiveButton(OK_DIALOG_RESOURCE, event);
        if (event != null) {
            alertbox.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    event.onClick(dialog, NO_DIALOG_RESOURCE);
                }
            });
        }
        AlertDialog alert = alertbox.create();
        alert.setTitle(TITLE_DIALOG_RESOURCE);
        alert.setIcon(resource);
        alert.show();
    }

    private static void createAlert(Context context, String message, int resource,
            final OnClickListener event, final DialogInterface.OnCancelListener cancelListener) {
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
        alertbox.setMessage(message);
        alertbox.setPositiveButton(OK_DIALOG_RESOURCE, event);
        alertbox.setOnCancelListener(cancelListener);
        AlertDialog alert = alertbox.create();
        alert.setTitle(TITLE_DIALOG_RESOURCE);
        alert.setIcon(resource);
        alert.setCancelable(false);
        alert.show();
    }

    public static void showOptions(Context context, DialogInterface.OnClickListener event, int... resId) {
        createOptions(context, event, resId).show();
    }

    public static AlertDialog showOptions(Context context, String[] options, DialogInterface.OnClickListener event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(context.getString(TITLE_DIALOG_RESOURCE));
        builder.setItems(options, event);
        return builder.create();
    }

    public static AlertDialog createOptions(Context context, DialogInterface.OnClickListener event,
            int... resId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String options[] = new String[resId.length];
        builder.setTitle(context.getString(TITLE_DIALOG_RESOURCE));
        for (int i = 0; i < resId.length; i++) {
            options[i] = context.getString(resId[i]);
        }
        builder.setItems(options, event);
        return builder.create();
    }

    public static AlertDialog createErrorDialog(Context context, String message, OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(TITLE_DIALOG_RESOURCE));
        builder.setMessage(message);
        builder.setIcon(ERROR_ICON_RESOURCE);
        builder.setPositiveButton(OK_DIALOG_RESOURCE, listener);
        return builder.create();
    }

    public static AlertDialog createSuccess(Context context, String message, OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(TITLE_DIALOG_RESOURCE));
        builder.setMessage(message);
        builder.setIcon(OK_ICON_ICON_RESOURCE);
        builder.setPositiveButton(OK_DIALOG_RESOURCE, listener);
        return builder.create();
    }

    public static void showShortToast(Context context, Object message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }

    public static void showShortToast(Context context, int res) {
        showShortToast(context, context.getString(res));
    }

    public static void showLongToast(Context context, Object message) {
        Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(Context context, int res) {
        showLongToast(context, context.getString(res));
    }

    public static void showInput(Context context, String message, final InputRespotaListener respotaListener) {
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
        showInput0(alertbox, context, message, -1, true, respotaListener);
    }

    public static void showInput(Context context, String message, int inputType, final InputRespotaListener respotaListener) {
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
        showInput0(alertbox, context, message, inputType, true, respotaListener);
    }

    public static AlertDialog.Builder showInput0(AlertDialog.Builder alertbox, Context context, String message, int inputType, boolean show, final InputRespotaListener respotaListener) {
        alertbox.setTitle(context.getString(TITLE_DIALOG_RESOURCE));
        alertbox.setMessage(message);
        alertbox.setCancelable(false);
        final EditText textEdit = new EditText(context);

        if (inputType != -1) {
            textEdit.setInputType(inputType);
        }
        alertbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                respotaListener.onOkClicked(ViewUtil.getText(textEdit));
            }
        });
        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                respotaListener.onCancel();
            }
        });
        alertbox.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                respotaListener.onCancel();
            }
        });

        alertbox.setView(textEdit);
        if (show) {
            alertbox.create().show();
        }

        return alertbox;
    }
}
