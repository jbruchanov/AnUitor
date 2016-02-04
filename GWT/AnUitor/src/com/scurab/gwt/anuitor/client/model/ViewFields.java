package com.scurab.gwt.anuitor.client.model;

public final class ViewFields {

    public static final String LOCATION_SCREEN_X = "LocationScreenX";
    public static final String LOCATION_SCREEN_Y = "LocationScreenY";
    public static final String HEIGHT = "Height";
    public static final String WIDTH = "Width";
    public static final String TYPE = "Type";
    public static final String VISIBILITY = "Visibility";

    /**
     * Fields which are used for internal purposes only and have meaning only
     * related with context of GWT AnUitor...
     * 
     * @author jbruchanov
     * 
     */
    public static final class Internal {
        public static String SCALE_X = "_ScaleX";
        public static String SCALE_Y = "_ScaleX";
        public static String VISIBILITY = "_Visibility";        
        public static String RENDERVIEWCONTENT = "_RenderViewContent";
        public static String RENDER_AREA_RELATIVE = "_RenderAreaRelative";
    }
}
