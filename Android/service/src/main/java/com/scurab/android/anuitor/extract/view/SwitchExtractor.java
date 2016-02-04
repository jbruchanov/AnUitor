package com.scurab.android.anuitor.extract.view;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.widget.Switch;

import com.scurab.android.anuitor.extract.Translator;

import java.util.HashMap;

/**
 * Created by jbruchanov on 06/06/2014.
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class SwitchExtractor extends CompoundButtonExtractor {

    public SwitchExtractor(Translator translator) {
        super(translator);
    }

    @Override
    public HashMap<String, Object> fillValues(View v, HashMap<String, Object> data,
                                              HashMap<String, Object> parentData) {
        super.fillValues(v, data, parentData);

        Switch s = (Switch) v;

        data.put("TextOn", s.getTextOn());
        data.put("TextOff", s.getTextOff());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            data.put("SwitchMinWidth", s.getSwitchMinWidth());
            data.put("SwitchPadding", s.getSwitchPadding());
            data.put("ThumbTextPadding", s.getThumbTextPadding());
        }

        if (Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            data.put("ShowText", s.getShowText());
            data.put("SplitTrack", s.getSplitTrack());
        }

        return data;
    }
}
