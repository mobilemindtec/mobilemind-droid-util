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

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.mobilemind.api.droidutil.filter.MoneyValueFilter;
import br.com.mobilemind.api.utils.MobileMindUtil;
import java.util.List;

/**
 *
 * @author Ricardo Bocchi
 */
public class ViewUtil {

    public static <E> ArrayAdapter<E> getAdapeterForSpinner(Context context, List<E> values) {
        ArrayAdapter<E> array = new ArrayAdapter<E>(context,
                android.R.layout.simple_spinner_dropdown_item);
        for (E e : values) {
            array.add(e);
        }
        return array;
    }

    public static <E> ArrayAdapter<E> getAdapterForSpinner(Context context, E[] values) {
        ArrayAdapter<E> array = new ArrayAdapter<E>(context,
                android.R.layout.simple_spinner_dropdown_item);
        for (E e : values) {
            array.add(e);
        }
        return array;
    }

    public static String getText(EditText text) {
        if (text != null && text.getText() != null) {
            return text.getText().toString().trim();
        }
        return "";
    }

    public static <E> void setSpinnerSelectedItem(E e, Spinner spinner) {
        int idx = ((ArrayAdapter<E>) spinner.getAdapter()).getPosition(e);
        if (idx > -1) {
            spinner.setSelection(idx);
        }
    }

    public static CheckBox createCheckBox(Context context, int resource) {
        CheckBox view = new CheckBox(context);
        view.setText(resource);
        return view;
    }

    public static TextView createTextView(Context context, int resource) {
        TextView view = new TextView(context);
        view.setText(resource);
        return view;
    }

    public static EditText createEditText(Context context, InputFilter... filters) {
        return createEditText(context, EditTextType.CHARACTER, filters);
    }

    public static EditText createEditText(Context context, EditTextType type, InputFilter... filters) {
        EditText text = new EditText(context);
        text.setFilters(filters);
        if (EditTextType.DECIMAL.equals(type)) {
            setDecimalFocusListener(text);
            text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            if (!EditTextType.CHARACTER.equals(type)) {
                text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
            }
            setSelectAllListener(text);
        }
        return text;
    }

    public static Spinner createSpinner(Context context, ArrayAdapter<?> adapter) {
        Spinner spinner = new Spinner(context);
        //spinner.setPromptId();
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        return spinner;
    }

    public static double getDouble(EditText txt) {
        return MobileMindUtil.parseMoney(getText(txt));
    }

    public static int getInt(EditText txt) {
        try {
            return Integer.parseInt(getText(txt));
        } catch (Exception e) {
        }
        return 0;
    }

    public static InputFilter[] getFilterLength(int len) {
        return new InputFilter[]{new InputFilter.LengthFilter(len)};
    }

    public static InputFilter[] getFilterLength() {
        return getFilterLength(50);
    }

    public static InputFilter[] getFilterDecimal(int len) {
        InputFilter filters[] = new InputFilter[2];
        filters[0] = new InputFilter.LengthFilter(len);
        filters[1] = new DigitsKeyListener(false, true);
        return filters;
    }

    public static InputFilter[] getFilterNumeric(int len) {
        InputFilter filters[] = new InputFilter[2];
        filters[0] = new InputFilter.LengthFilter(len);
        filters[1] = new DigitsKeyListener(false, false);
        return filters;
    }

    public static InputFilter[] getMoneyFilter(int len) {
        InputFilter filters[] = new InputFilter[2];
        filters[0] = new InputFilter.LengthFilter(len);
        filters[1] = new MoneyValueFilter();
        return filters;
    }

    public static void setDecimalFocusListener(final EditText txt) {
        txt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean focus) {
                if (!focus) {
                    Double val = getDouble(txt);
                    txt.setText(formatMoney(val));
                    txt.selectAll();
                }
            }
        });
    }

    public static void setSelectAllListener(final EditText txt) {
        txt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean focus) {
                if (focus) {
                    txt.selectAll();
                }
            }
        });
    }

    public static String formatMoney(double d) {
        return MobileMindUtil.formatMoneyStr(d);
    }
}
