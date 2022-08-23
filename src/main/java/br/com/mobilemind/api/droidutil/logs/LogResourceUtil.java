package br.com.mobilemind.api.droidutil.logs;

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

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import br.com.mobilemind.api.droidutil.logs.LogWriter;

/**
 *
 * @author Ricardo Bocchi
 */
public class LogResourceUtil {

    private static Properties props = new Properties();

    static {
        load();
    }

    private static void load() {
        InputStream in = null;
        try {
            in = LogResourceUtil.class.getClassLoader().getResourceAsStream("log4j.properties");
            if (in != null) {
                props.load(in);
                Log.d(LogResourceUtil.class.getSimpleName(), "log4j.properties loaded...");

            } else {
                Log.d(LogResourceUtil.class.getSimpleName(),
                        "log4j.properties not fount... loading log4j_default.properties.");
            }
        } catch (IOException ex) {
            Log.d(LogResourceUtil.class.getSimpleName(), ex.getMessage(), ex);
        }

        if (in == null) {
            try {
                in = LogResourceUtil.class.getClassLoader().getResourceAsStream("log4j_default.properties");
                if (in != null) {
                    props.load(in);
                    Log.d(LogResourceUtil.class.getSimpleName(), "log4j_default.properties loaded...");
                } else {
                    Log.d(LogResourceUtil.class.getSimpleName(), "log4j_default.properties not found");
                }
            } catch (IOException ex) {
                Log.d(LogResourceUtil.class.getSimpleName(), ex.getMessage(), ex);
            }
        }
    }

    public static String getString(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
