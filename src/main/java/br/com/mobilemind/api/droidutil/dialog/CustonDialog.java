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
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ricardo Bocchi
 */
public class CustonDialog {

    private LayoutInflater li;
    private View view;
    private AlertDialog.Builder builder;
    private TableLayout table;
    private TableRow row;
    private LinearLayout layout;
    private Context context;
    private Map<Integer, View> views;

    public CustonDialog(Context context, int title, int viewLayout, int layout) {
        this.context = context;
        this.li = LayoutInflater.from(context);
        this.view = li.inflate(viewLayout, null);
        this.builder = new AlertDialog.Builder(context);
        this.layout = (LinearLayout) view.findViewById(layout);
        this.views = new HashMap<Integer, View>();
    }

    public View findViewById(Integer id) {
        if (this.views.containsKey(id)) {
            return this.views.get(id);
        }
        return null;
    }

    public CustonDialog addDatePicker(int resource) {
        DatePicker datePicker = new DatePicker(this.context);
        this.layout.addView(datePicker);
        this.views.put(resource, datePicker);
        return this;
    }

    public CustonDialog addSpinner(List items, int resource) {
        Spinner spinner = new Spinner(context);
        ArrayAdapter array = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item);
        for (Object p : items) {
            array.add(p);
        }
        spinner.setAdapter(array);

        this.layout.addView(spinner);
        this.views.put(resource, spinner);

        return this;
    }

    public CustonDialog addSpinner(Object[] items, int resource) {
        Spinner spinner = new Spinner(context);
        ArrayAdapter array = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item);
        for (Object p : items) {
            array.add(p);
        }
        spinner.setAdapter(array);

        this.layout.addView(spinner);
        this.views.put(resource, spinner);

        return this;
    }

    public CustonDialog addTextView(int resource) {
        TextView[] v = createTextView(this.context, resource);
        this.layout.addView(v[0]);
        this.views.put(resource, v[0]);
        return this;
    }

    public CustonDialog addEditText(int resource, InputFilter... filters) {
        EditText[] v = createEditText(this.context, 1, filters);
        this.layout.addView(v[0]);
        this.views.put(resource, v[0]);
        return this;
    }

    public CustonDialog addButton(int resource, int width, int top, int left, int rigth,
            OnClickListener listener) {
        Button[] v = createButtons(this.context, width, resource);

        if (table == null) {
            this.table = new TableLayout(context);
            this.row = new TableRow(context);
            this.row.setGravity(Gravity.CENTER);
        }

        if (listener != null) {
            v[0].setOnClickListener(listener);
            v[0].setPadding(left, top, rigth, 0);
        }
        this.row.addView(v[0]);
        this.views.put(resource, v[0]);
        return this;
    }

    public CustonDialog setTitle(int resource) {
        builder.setTitle(resource);
        return this;
    }

    public AlertDialog builder() {

        if (table != null) {
            table.addView(row);
            layout.addView(table);
        }

        builder.setView(view);
        return builder.create();
    }

    public static Button[] createButtons(Context context, int width, int... labels) {
        Button[] btns = new Button[labels.length];
        int j = 0;
        for (int i : labels) {
            Button btn = new Button(context);
            btn.setText(i);
            if (width > 0) {
                btn.setWidth(width);
            }
            btns[j++] = btn;
        }
        return btns;
    }

    public static TextView[] createTextView(Context context, int... labels) {
        TextView[] text = new TextView[labels.length];
        int j = 0;
        for (int i : labels) {
            TextView btn = new TextView(context);
            btn.setText(i);
            text[j++] = btn;
        }
        return text;
    }

    public static EditText[] createEditText(Context context, int count, InputFilter... filters) {
        EditText[] text = new EditText[count];
        int j = 0;
        for (int i = 0; i < count; i++) {
            EditText btn = new EditText(context);
            btn.setFilters(filters);
            text[j++] = btn;
        }
        return text;
    }
}
