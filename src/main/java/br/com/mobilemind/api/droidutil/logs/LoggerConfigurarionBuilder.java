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

import android.os.Environment;
import android.util.Log;
import de.mindpipe.android.logging.log4j.LogConfigurator;
import java.io.File;

/**
 *
 * @author Ricardo Bocchi
 */
public class LoggerConfigurarionBuilder {

    public static String FILE_LOG = "";
    private boolean fail;

    public boolean isFail() {
        return fail;
    }

    public void build() {


        boolean useExternalStorage = Boolean.valueOf(LogResourceUtil.getString("log.use.external.storage"));
        String fileName = LogResourceUtil.getString("log.file.name");
        String pathName = LogResourceUtil.getString("log.file.path.name");
        AppLogger.DEBUG_MODE = Boolean.valueOf(LogResourceUtil.getString("log.debug.mode"));
        long maxFileSize = Long.parseLong(LogResourceUtil.getString("log.max.size.file"));


        final LogConfigurator logConfigurator = new LogConfigurator();

        if (useExternalStorage) {
            Log.d("LOG", "use external storage " + Environment.getExternalStorageDirectory());
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + pathName);
            if (!file.exists()) {
                try {
                    file.mkdir();
                } catch (Exception e) {
                    Log.e(this.getClass().getName(), "error create log directory", e);
                    this.fail = true;
                    return;
                }
            }

            FILE_LOG = file.getAbsolutePath() + File.separator + fileName;
            logConfigurator.setFileName(FILE_LOG);
        } else {
            Log.d("LOG", "not use external storage ");
            FILE_LOG = pathName + File.separator + fileName;
            logConfigurator.setFileName(FILE_LOG);
        }

        logConfigurator.setRootLevel(org.apache.log4j.Level.DEBUG);
        logConfigurator.setMaxFileSize(maxFileSize);
        logConfigurator.setUseFileAppender(true);
        logConfigurator.setLevel("org.apache", org.apache.log4j.Level.INFO);

        try {
            logConfigurator.configure();
        } catch (Exception e) {
            Log.e(this.getClass().getName(), "error create log directory", e);
            this.fail = true;
            return;
        }
    }
}
