package com.laobai.dynamicrouter.router;


import android.app.Activity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by kris on 16/12/1.
 */
public class KeyGetter {


    public static String getKey(Class<? extends Activity> page, Field field){
        Annotation [] annotations = field.getDeclaredAnnotations();
        String fieldKey = "";
        String clzKey = "";
        for(Annotation annotation: annotations){
            if(annotation instanceof RouterKey){
                //只有第一个有效
                fieldKey = ((RouterKey) annotation).value()[0];
            }
        }

        Annotation [] annotationsOfPage = page.getDeclaredAnnotations();
        for(Annotation annotation : annotations){
            if(annotation instanceof RouterKey){
                clzKey = ((RouterKey) annotation).value()[0];
            }
        }


        return clzKey + ":" + fieldKey;
    }
}
