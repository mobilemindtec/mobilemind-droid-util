package br.com.mobilemind.api.droidutil.preferences;

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
import android.preference.EditTextPreference;
import android.preference.Preference;

/**
 *
 * @author Ricardo Bocchi
 */
public class PreferencesFactory {

    public static EditTextPreference buildEditTextPreference(Context context, String key, String title,
            String summary, boolean enabled,
            Preference.OnPreferenceChangeListener changeListener) {

        final EditTextPreference editTextPref = new EditTextPreference(context);
        editTextPref.setKey(key);
        editTextPref.setDialogTitle(title);
        editTextPref.setTitle(title);
        editTextPref.setSummary(summary);
        editTextPref.setOnPreferenceChangeListener(changeListener);
        editTextPref.setEnabled(enabled);
        return editTextPref;
    }

    public static EditTextPreference buildEditTextPreference(Context context, String key, String defaltValue, String title,
            String summary, boolean enabled,
            Preference.OnPreferenceChangeListener changeListener) {

        final EditTextPreference editTextPref = new EditTextPreference(context);
        editTextPref.setKey(key);
        editTextPref.setDialogTitle(title);
        editTextPref.setTitle(title);
        editTextPref.setSummary(summary);
        editTextPref.setText(defaltValue);
        editTextPref.setDefaultValue(defaltValue);        
        editTextPref.setOnPreferenceChangeListener(changeListener);
        editTextPref.setEnabled(enabled);
        return editTextPref;
    }

}
