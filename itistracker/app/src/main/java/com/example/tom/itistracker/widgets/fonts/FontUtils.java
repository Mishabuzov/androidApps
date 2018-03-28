package com.example.tom.itistracker.widgets.fonts;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public class FontUtils {

    public enum TypefaceType {
        ROBOTO_LIGHT,
        ROBOTO_REGULAR,
        ROBOTO_MEDIUM,
        ROBOTO_BOLD,
        ROBOTO_ITALIC
    }

    // todo check whether a problem with performance while loading a typeface
    // todo in the multiple rows (listview, recycler view) exist or not
    // todo because a cache map below is intended to get rid of this issue

    // this map is used to cache typefonts (lazy loading way)
    private static final Map<TypefaceType, Typeface> cache = new HashMap<>();
    private static final Map<TypefaceType, String> pathCache = new HashMap<>();

    static {
        pathCache.put(TypefaceType.ROBOTO_LIGHT, "fonts/Roboto-Light.ttf");
        pathCache.put(TypefaceType.ROBOTO_REGULAR, "fonts/Roboto-Regular.ttf");
        pathCache.put(TypefaceType.ROBOTO_MEDIUM, "fonts/Roboto-Medium.ttf");
        pathCache.put(TypefaceType.ROBOTO_BOLD, "fonts/Roboto-Bold.ttf");
        pathCache.put(TypefaceType.ROBOTO_ITALIC, "fonts/Roboto-Italic.ttf");
    }

    // factory method
    public static Typeface getTypeface(Context context, TypefaceType typefaceType) {
        Typeface typeface = cache.get(typefaceType);
        if (null == typeface) {
            // do not check for null when needed path is not exist
            typeface = Typeface.createFromAsset(context.getAssets(), pathCache.get(typefaceType));
            cache.put(typefaceType, typeface);
        }
        return typeface;
    }
}
