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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolderGeneric implements ViewHolder {

    public ImageView imageView;
    public ImageView imageViewTwo;
    public ImageView imageViewTree;
    public ImageView imageViewFour;
    public ImageView imageViewFive;
    public TextView textViewOne;
    public TextView textViewTwo;
    public TextView textViewTree;
    public TextView textViewFour;
    public TextView textViewFive;
    public TextView textViewSix;
    public TextView textViewSeven;
    public TextView textViewEight;
    public TextView textViewNine;
    public TextView textViewTen;
    public TextView textViewEleven;
    public TextView textViewTwelve;
    public ImageButton imageButtonOne;
    public ImageButton imageButtonTwo;
    public ImageButton imageButtonTree;
    public CheckBox checkBox;
    public Object tag;
    public int index;
    public Button btnOne;
    public Button btnTwo;
    public Button btnThree;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
