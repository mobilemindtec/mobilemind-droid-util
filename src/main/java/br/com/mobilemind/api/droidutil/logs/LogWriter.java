/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mobilemind.api.droidutil.logs;

/*
 * #%L
 * Mobile Mind - Droid Util
 * %%
 * Copyright (C) 2012 - 2017 Mobile Mind Empresa de Tecnologia
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
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author ricardo
 */
public class LogWriter {

    static final String TAG = "DROIDUTIL_LOG";

    static String fileName = "file.log";
    static String filePath = "mobilemind";

    static File basePath;
    static int maxSize = 5*1024*1024; // 5MB
    static int maxFiles = 10;
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static DateFormat dateFileFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static PrintWriter writer;
    private static boolean initialized;
    static File logFile;

    public static void init(Context context){

        basePath = new File(context.getFilesDir(), filePath);

        if(logFile == null){
            logFile = new File(basePath, fileName);

            if (!basePath.exists()) {
                if(!basePath.mkdirs()){
                    Log.d(TAG, "can't create log path: " + basePath.getAbsolutePath());
                }
            }
        }

        initialized = true;
    }

    public static void write(String message, Throwable exception) {

        if(!initialized){
            Log.d(TAG, "LogWrite not initialized");
            return;
        }


        try {

            if(logFile.exists() && logFile.length() > maxSize){
                logRotate(basePath, fileName);
                writer = null;
            }

            if (writer == null) {
                writer = new PrintWriter(new FileWriter(logFile, logFile.exists()));
            }

            writer.write(dateFormat.format(new Date()));
            writer.write(": " + message + "\n");
            if (exception != null) {
                exception.printStackTrace(writer);
                writer.write("\n");
            }


            writer.flush();

        } catch (Exception e) {
            writer = null;
            Log.e(TAG, "error on write log", e);
            Log.e(TAG, "original log: " + message, exception);

        }

    }

    static void logRotate(File parent, String fileName) {
        File file = new File(parent, fileName);
        Map<Date, File> filesByDate = new TreeMap<Date, File>();

        if (file.exists()) {
            if (file.length() > maxSize) {
                try {
                    if (writer != null) {
                        writer.close();
                    }

                    File[] files = parent.listFiles();

                    if (files != null) {
                        System.out.println("parent len=" + files.length);
                        for (File f : files) {
                            String[] splited = f.getName().split(".bkp.");
                            if(splited.length > 1){
                                String date = splited[1];
                                filesByDate.put(dateFileFormat.parse(date), f);
                            }
                        }
                    }

                    //System.out.println("log files count=" + filesByDate.size());

                    if(filesByDate.size()+1 > maxFiles){
                        int toRemove = filesByDate.size()+1 - maxFiles;
                        if(toRemove > 0){
                            List<Date> keys = new ArrayList<Date>(filesByDate.keySet());
                            //Collections.reverse(keys);
                            for(int i = 0; i < toRemove; i++){
                                if(i >= keys.size())
                                    break;
                                File f = filesByDate.get(keys.get(i));
                                if(!f.delete()){
                                    Log.e(TAG, "error on log rotate");
                                }
                            }
                        }
                    }

                    System.out.println("rename file");
                    file.renameTo(new File(file.getAbsolutePath() + ".bkp." + dateFileFormat.format(new Date())));

                } catch (Exception e) {
                    Log.e(TAG, "error on close log file", e);
                }
            }
        }
    }
}
