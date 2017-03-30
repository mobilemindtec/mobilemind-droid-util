package br.com.mobilemind.api.droidutil.ioc;

/*
 * #%L
 * Mobile Mind - Droid Util
 * %%
 * Copyright (C) 2012 - 2016 Mobile Mind Empresa de Tecnologia
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

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import br.com.mobilemind.api.utils.ClassUtil;

/**
 * Created by ricardo on 12/7/16.
 */

public class IocBuilder {

    private Activity activity;
    private boolean debug;

    public IocBuilder onDebug(){
        this.debug = true;
        return this;
    }    

    public void Build(Object instance) {

        try {
            List<Field> injectList = ClassUtil.getAnnotatedsFields(instance.getClass(), Inject.class);
            List<Field> injectViewList = ClassUtil.getAnnotatedsFields(instance.getClass(), InjectView.class);

            if (instance instanceof Activity) {
                this.activity = (Activity) instance;                    
                
                ContentView contentView = instance.getClass().getAnnotation(ContentView.class);
                if(contentView != null){
                    this.activity.setContentView(contentView.value());

                    if(this.debug)
                        Log.i("IOC", "## set activity content view");
                }
            } 

            if(this.activity == null) {
                throw new RuntimeException("Activity can't be null");
            }

            for (Field f : injectViewList) {
                InjectView injectView = f.getAnnotation(InjectView.class);
                f.setAccessible(true);
                View view = this.activity.findViewById(injectView.value());
                f.set(instance, view);
                if(this.debug)
                    Log.i("IOC", "## set view to field " + f.getName() + " view " + view);
            }            


            for (Field f : injectList) {
                f.setAccessible(true);
                if (f.get(instance) == null) {
                    Class type = f.getType();
                    Object newObject = null;
                    boolean resolve = true;

                    if (type.equals(Activity.class)) {
                        newObject = activity;
                        resolve = false;
                    } else if (type.equals(Context.class)) {
                        newObject = activity;
                        resolve = false;
                    } else if (type.equals(Resources.class)) {
                        newObject = activity.getResources();
                        resolve = false;
                    } else {
                        newObject = type.newInstance();
                    }

                    f.set(instance, newObject);
                    if(this.debug)
                        Log.i("IOC", "## set value to field " + f.getName() + " of type " + type.getName());

                    if (resolve)
                        this.Build(newObject);
                }
            }

            List<Method> methods = ClassUtil.getAnnotatedMethods(instance.getClass(), Inject.class);

            for(Method m : methods){
                m.setAccessible(true);
                m.invoke(instance);
            }            
        } catch (Exception e) {
            Log.e("IOC", e.getMessage(), e);
            br.com.mobilemind.api.droidutil.logs.AppLogger.error(getClass(), e);
            throw new RuntimeException(e);
        }

    }
}
