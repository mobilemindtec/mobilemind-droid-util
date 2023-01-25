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

import android.content.Context;
import android.util.Log;
import java.util.logging.Level;
import br.com.mobilemind.api.utils.log.MMLogger;
import java.text.MessageFormat;
import br.com.mobilemind.api.droidutil.logs.LogWriter;
/**
 *
 * @author Ricardo Bocchi
 */
public class AppLogger implements br.com.mobilemind.api.utils.log.AppLogger {

    private final static AppLogger appLogger = new AppLogger();
    public static boolean DEBUG_MODE = false;
    private final static LoggerConfigurarionBuilder builder = new LoggerConfigurarionBuilder();

    private AppLogger() {
    }

    public static void init(Context context){
        builder.build(context);
        MMLogger.addLogger(appLogger);
    }

    public static AppLogger getInstance() {
        return appLogger;
    }

    public static void info(Class clazz, String message) {
        appLogger.log(Level.INFO, clazz, message);
    }

    public static void info(Class clazz, String message, Object... args) {
        appLogger.log(Level.INFO, clazz, MessageFormat.format(message, args));
    }

    public static void error(Class clazz, String message) {
        appLogger.log(Level.SEVERE, clazz, message);
    }

    public static void error(Class clazz, Exception ex) {
        appLogger.log(Level.SEVERE, clazz, ex);
    }

    public static void error(Class clazz, Exception ex, String message) {
        appLogger.log(Level.SEVERE, clazz, message, ex);
    }
    

    @Override
    public void log(Level level, Class clazz, String message) {
        if (clazz == null) {
            clazz = this.getClass();
        }
        

        this.logWrite(level, clazz, message, null);
    }

    @Override
    public void log(Level level, Class clazz, String message, Exception e) {
        if (clazz == null) {
            clazz = this.getClass();
        }    

        this.logWrite(level, clazz, message, e);
    }

    @Override
    public void log(Level level, Class clazz, Exception e) {

        if (clazz == null) {
            clazz = this.getClass();
        }    

        this.logWrite(level, clazz, null, e);
    }

    private void logWrite(Level level, Class clazz, String message, Exception e) {

        boolean severe = (level == Level.SEVERE);

        String[] pack = clazz.getName().split(".");
        String tag = "";
        String className = clazz.getSimpleName();

        for(String it : pack){

            if(it.equals(className))
                break;
            tag += it.charAt(0) + ".";
        }

        tag += className;

        if(level== Level.SEVERE) {
            Log.e(tag, message, e);
        } else if(level== Level.INFO) {
            Log.i(tag, message);
        } else {
            Log.d(tag, message);
        }

        if (!severe && !DEBUG_MODE) {
            return;
        }

        try {

            if (e != null && message == null) {
                message = e.getMessage();
            }

            LogWriter.write(message, e);

        } catch (Exception ex) {
        }
    }
}
