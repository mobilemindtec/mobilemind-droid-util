package br.com.mobilemind.api.droidutil.filter;

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

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;


/**
 *
 * @author Ricardo Bocchi
 */
public class MoneyValueFilter extends DigitsKeyListener {
    public MoneyValueFilter() {
        super(false, true);
    }

    private int digits = 2;

    public void setDigits(int d) {
        digits = d;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
            Spanned dest, int dstart, int dend) {
        CharSequence out = super.filter(source, start, end, dest, dstart, dend);

        // if changed, replace the source
        if (out != null) {
            source = out;
            start = 0;
            end = out.length();
        }

        int len = end - start;

        // if deleting, source is empty
        // and deleting can't break anything
        if (len == 0) {
            return source;
        }

        int dlen = dest.length();

        // Find the position of the decimal .
        for (int i = 0; i < dstart; i++) {
            if (dest.charAt(i) == '.') {
                // being here means, that a number has
                // been inserted after the dot
                // check if the amount of digits is right
                return (dlen-(i+1) + len > digits) ? 
                    "" :
                    new SpannableStringBuilder(source, start, end);
            }
        }

        for (int i = start; i < end; ++i) {
            if (source.charAt(i) == '.') {
                // being here means, dot has been inserted
                // check if the amount of digits is right
                if ((dlen-dend) + (end-(i + 1)) > digits)
                    return "";
                else
                    break;  // return new SpannableStringBuilder(source, start, end);
            }
        }

        // if the dot is after the inserted part,
        // nothing can break
        return new SpannableStringBuilder(source, start, end);
    }
}

