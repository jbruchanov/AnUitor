package com.scurab.gwt.anuitor.client.util;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.scurab.gwt.anuitor.client.AnUitor;

public class ConfigHelper {
    
    public static String colorStyleForType(JSONObject config, String type) {
        if (config != null && type != null) {
            JSONValue highV = config.get("TypeHighlights");
            JSONObject colors = highV != null ? highV.isObject() : null;
            if (colors != null) {
                JSONValue jsonColorV = colors.get(type);                
                JSONString jsonColor = jsonColorV != null ? jsonColorV.isString() : null;
                String color = jsonColor != null ? jsonColor.stringValue() : null;
                return color;
            }
        }
        return null;
    }
    
    public static String colorForProperty(JSONObject config, String key) {
        JSONValue highV = config.get("PropertyHighlights");
        JSONObject colors = highV != null ? highV.isObject() : null;
        if (colors != null) {
            if (key.charAt(key.length() - 1) == ':') {// remove our click flag if necessary
                key = key.substring(0, key.length() - 1);
            }
            // key = key.toLowerCase();
            for (String regexp : colors.keySet()) {
                if (key.toLowerCase().matches(regexp)) {
                    JSONValue jsonColorV = colors.get(regexp);
                    JSONString jsonColor = jsonColorV != null ? jsonColorV.isString() : null;
                    String color = jsonColor != null ? jsonColor.stringValue() : null;
                    return color;
                }
            }
        }
        return null;
    }
    
    public static String getDeviceInfo() {
        JSONObject object = AnUitor.getConfig();
        JSONValue deviceV = object != null ? object.get("Device") : null;
        JSONObject device = deviceV != null ? deviceV.isObject() : null;
        if (device != null) {
            return getValue(device, "MANUFACTURER", "") + " " +
                    getValue(device, "MODEL", "") + " " +
                    "API:" + (int)getValue(device, "API", 0);
        }
        return null;
    }
    
    public static String getGridColor() {
        return getValue(AnUitor.getConfig(), "GridStrokeColor", HTMLColors.RED);
    }
    
    public static String getSelectionColor() {
        return getValue(AnUitor.getConfig(), "SelectionColor", null);
    }
    
    private static String getValue(JSONObject o, String key, String defValue) {
        JSONString val = null;
        if (o != null && o.containsKey(key) && (val = o.get(key).isString()) != null) {
            return val.stringValue();
        }
        return defValue;
    }
    
    private static double getValue(JSONObject o, String key, double defValue) {
        JSONNumber val = null;
        if (o != null && o.containsKey(key) && (val = o.get(key).isNumber()) != null) {
            return val.doubleValue();
        }
        return defValue;
    }
}