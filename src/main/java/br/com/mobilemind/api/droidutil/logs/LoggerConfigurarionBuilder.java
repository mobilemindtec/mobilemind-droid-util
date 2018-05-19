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
import com.google.code.microlog4android.config.PropertyConfigurator;
import java.io.File;

/**
 *
 * @author Ricardo Bocchi
 */
public class LoggerConfigurarionBuilder {

    public static String FILE_LOG = "";

    public void build() {


        String fileName = LogResourceUtil.getString("log.file.name", "file.log");
        String pathName = LogResourceUtil.getString("log.file.path.name", "mobilemind");
        AppLogger.DEBUG_MODE = Boolean.valueOf(LogResourceUtil.getString("log.debug.mode", "FALSE"));
        int maxFileSize = Integer.parseInt(LogResourceUtil.getString("log.max.size.file", LogWriter.maxSize + ""));
        int maxFiles = Integer.parseInt(LogResourceUtil.getString("log.max.files", "10"));

        LogWriter.fileName = fileName;
        LogWriter.filePath = pathName;
        LogWriter.maxSize = maxFileSize;
        LogWriter.maxFiles = maxFiles;

        FILE_LOG = LogWriter.logFile.getAbsolutePath();

    }


}
