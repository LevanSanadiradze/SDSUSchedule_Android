package com.example.root.sdsu_gmap.font;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FontsOverride {
    private static final String TAG = FontsOverride.class.getSimpleName();

    private static final int BOLD = 1;
    private static final int BOLD_ITALIC = 2;
    private static final int ITALIC = 3;
    private static final int LIGHT = 4;
    private static final int CONDENSED = 5;
    private static final int THIN = 6;
    private static final int MEDIUM = 7;
    private static final int REGULAR = 8;

    private Map<String, Typeface> fontsMap;

    private Context context;

    public FontsOverride(Context context) {
        this.context = context;
    }

    public void loadFonts() {
        fontsMap = new HashMap<>();

        fontsMap.put("myriadbold", getTypeface("myriadprobold.otf", 0));
        fontsMap.put("myriaditalic", getTypeface("myriadproitalic.ttf", 0));

        overrideFonts(fontsMap);
    }

    private void overrideFonts(Map<String, Typeface> typefaces) {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                final Field field = Typeface.class.getDeclaredField("sSystemFontMap");
                field.setAccessible(true);
                Map<String, Typeface> oldFonts = (Map<String, Typeface>) field.get(null);
                if (oldFonts != null) {
                    oldFonts.putAll(typefaces);
                } else {
                    oldFonts = typefaces;
                }
                field.set(null, oldFonts);
                field.setAccessible(false);
            } catch (Exception e) {
                Log.e(TAG, "Cannot set custom fonts", e);
            }
        } else {
            try {
                for (Map.Entry<String, Typeface> entry : typefaces.entrySet()) {
                    final Field staticField = Typeface.class.getDeclaredField(entry.getKey());
                    staticField.setAccessible(true);
                    staticField.set(null, entry.getValue());
                }
            } catch (Exception e) {
                Log.e(TAG, "Cannot set custom fonts", e);
            }
        }
    }

    private Typeface getTypeface(String fontFileName, int fontType) {
        final Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontFileName);
        return Typeface.create(tf, fontType);
    }

    public Typeface getFont(String name) {
        return fontsMap.get(name);
    }
}