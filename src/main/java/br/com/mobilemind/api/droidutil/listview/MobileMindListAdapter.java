package br.com.mobilemind.api.droidutil.listview;

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
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 *
 * @author Ricardo Bocchi
 */
public class MobileMindListAdapter<T> extends ArrayAdapter<T> {

    private final Activity context;
    private final List<T> items;
    private final int resourceId;
    private ViewHolderControl<T> viewHolderControl;
    private View selectdView;
    private Class<? extends ViewHolder> holderClass;

    public MobileMindListAdapter(Activity context, List<T> items,
            int resourceId, Class<? extends ViewHolder> holderClass) {
        super(context, resourceId, items);
        this.context = context;
        this.items = items;
        this.resourceId = resourceId;
        this.holderClass = holderClass;
    }

    public List<T> getItems() {
        return items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder = null;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(resourceId, null, true);
            try {
                holder = (ViewHolder) holderClass.newInstance();
            } catch (Exception e) {
            }
            this.viewHolderControl.load(rowView, holder);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.setIndex(position);
        this.viewHolderControl.populate(this.items.get(position), holder);

        return rowView;
    }

    public void setViewHolderControl(ViewHolderControl<T> viewHolderControl) {
        this.viewHolderControl = viewHolderControl;
    }

    public T getSelectedItem() {

        int idx = -1;

        if (selectdView != null) {
            idx = ((ViewHolder) selectdView.getTag()).getIndex();
        }

        if (idx > -1) {
            return this.items.get(idx);
        }

        return null;
    }

    public View getSelectdView() {
        return selectdView;
    }

    public void setSelectdView(View selectdView) {
        this.selectdView = selectdView;
    }

    @Override
    public int getPosition(T item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(item)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void remove(T object) {

        T toRemove = null;

        if (items != null) {
            for (T t : items) {
                if (t.equals(object)) {
                    toRemove = t;
                }
            }
        }

        if (toRemove != null) {
            this.items.remove(toRemove);
        }
    }
}
