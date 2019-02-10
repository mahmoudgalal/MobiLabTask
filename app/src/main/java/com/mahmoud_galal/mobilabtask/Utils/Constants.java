/*
 * Copyright (c) 2019 Mahmoud Galal.
 * Mahmoudgalal57@yahoo.com
 */

package com.mahmoud_galal.mobilabtask.Utils;

public class Constants {
    //IMGUR API keys
    public static final String CLIENT_ID = "10fce7bf0bb122e";
    public static final String CLIENT_SECRET = "3a7d00d2efb33eea94484d9a3b4c1429627cf67f";

    public static final String GALLERY_ITEM_KEY = "com.mahmoud_galal.mobilabtask_GALLERY_ITEM_KEY";
    public static final String IMAGE_ITEM_KEY = "com.mahmoud_galal.mobilabtask_IMAGE_ITEM_KEY";

    public static final String BASE_URL = "https://api.imgur.com/3/";

    public enum  Sections{
        SECTIONS_HOT,
        SECTIONS_TOP,
        SECTIONS_USER;
        public static final String SECTION_HOT = "hot";
        public static final String SECTION_TOP = "top";
        public static final String SECTION_USER = "user";
        public String getName(){
            switch (this){
                case SECTIONS_HOT:
                    return SECTION_HOT;
                case SECTIONS_TOP:
                    return SECTION_TOP;
                case SECTIONS_USER:
                    return SECTION_USER;
                    default:
                        return SECTION_HOT;
            }
        }
    }

    public enum  SortOption{
        SORT_OPTION_VIRAL,
        SORT_OPTION_TOP,
        SORT_OPTION_TIME,
        SORT_OPTION_RISING;

        public static final String SORT_VIRAL = "viral";
        public static final String SORT_TOP = "top";
        public static final String SORT_TIME = "time";
        public static final String SORT_RISING = "rising";

        public final String getName(){
            switch (this){
                case SORT_OPTION_VIRAL:
                    return SORT_VIRAL;
                case SORT_OPTION_TOP:
                    return SORT_TOP;
                case SORT_OPTION_TIME:
                    return SORT_TIME;
                case SORT_OPTION_RISING:
                    return SORT_RISING;
                    default:
                        return SORT_VIRAL;
            }
        }
    }

    public enum  WindowOption{
        WINDOW_OPTION_DAY,
        WINDOW_OPTION_WEEK,
        WINDOW_OPTION_MONTH,
        WINDOW_OPTION_YEAR,
        WINDOW_OPTION_ALL;

        public static final String WINDOW_DAY = "day";
        public static final String WINDOW_WEEK = "week";
        public static final String WINDOW_MONTH = "month";
        public static final String WINDOW_YEAR = "year";
        public static final String WINDOW_ALL = "all";

        public String getName(){
            switch (this){
                case WINDOW_OPTION_DAY:
                    return WINDOW_DAY;
                case WINDOW_OPTION_WEEK:
                    return WINDOW_WEEK;
                case WINDOW_OPTION_MONTH:
                    return WINDOW_MONTH;
                case WINDOW_OPTION_YEAR:
                    return WINDOW_YEAR;
                case WINDOW_OPTION_ALL:
                    return WINDOW_ALL;
                    default:
                        return WINDOW_DAY;
            }
        }
    }
}
