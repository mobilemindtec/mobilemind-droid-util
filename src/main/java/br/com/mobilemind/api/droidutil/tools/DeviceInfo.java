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
import android.content.res.Configuration;

/**
 *
 * @author Ricardo Bocchi
 */
public class DeviceInfo {

    public static String getDeviceInfo(Context context) {
        String s = "Debug-infos:";
        s += "<br/> OS Version: " + System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")";
        s += "<br/> OS API Level: " + android.os.Build.VERSION.SDK;
        s += "<br/> Device: " + android.os.Build.DEVICE;
        s += "<br/> Model (and Product): " + android.os.Build.MODEL + " (" + android.os.Build.PRODUCT + ")";
        s += "<br/> Screen Height: " + Display.getHeight(context);
        s += "<br/> Screen Width: " + Display.getWidth(context);
        s += "<br/> Is Tablet: " + isTablet(context);


        return s;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
