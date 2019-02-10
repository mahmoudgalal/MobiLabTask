/*
 * Copyright (c) 2019 Mahmoud Galal.
 * Mahmoudgalal57@yahoo.com
 */

package com.mahmoud_galal.mobilabtask.Utils;

import android.app.AlertDialog;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mahmoud_galal.mobilabtask.R;

import static com.mahmoud_galal.mobilabtask.Utils.Constants.*;

/**
 * Handling filtering and filter UI
 */
public class FilterDialogManager {

    private AlertDialog dialog;
    private RadioGroup windowGroup,sectionGroup,sortOptionsGroup ;
    private RadioButton risingBtn ;
    private CheckBox viralCheckBox ;
    private OnFilterConfirmed onFilterConfirmedListener;

    public FilterObject getCurrentFilterObject() {
        return currentFilterObject;
    }

    private FilterObject currentFilterObject = new FilterObject();

    /**
     * Filter Parameters
     */
    public static class FilterObject{

        public String selectedSection = Sections.SECTION_HOT;
        public String selectedSortOption = SortOption.SORT_VIRAL;
        public String selectedWindowOption = WindowOption.WINDOW_DAY;
        public boolean showViral = true;

    }
    public void setOnFilterConfirmedListener(OnFilterConfirmed onFilterConfirmedListener) {
        this.onFilterConfirmedListener = onFilterConfirmedListener;
    }
    public interface OnFilterConfirmed{
        void onFilterConfirmed(FilterObject filterObject);
    }

    public FilterDialogManager(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.filter_dialog,null);
        viralCheckBox = view.findViewById(R.id.show_viral_btn);
        windowGroup = view.findViewById(R.id.window_group);
        sectionGroup = view.findViewById(R.id.section_group);
        sortOptionsGroup = view.findViewById(R.id.sort_group);

        risingBtn = view.findViewById(R.id.rising_btn);

        RadioGroup.OnCheckedChangeListener selectionListener = (radioGroup, i) -> {
            String ret = ((RadioButton) radioGroup.findViewById(i)).getText().toString();
            if (radioGroup == sectionGroup) {
                handleSectionSelection(ret);
                currentFilterObject.selectedSection = ret;
            } else if (radioGroup == windowGroup) {
                currentFilterObject.selectedWindowOption = ret;
            } else {
                currentFilterObject.selectedSortOption = ret;
            }
        };
        sectionGroup.setOnCheckedChangeListener(selectionListener);
        windowGroup.setOnCheckedChangeListener(selectionListener);
        sortOptionsGroup.setOnCheckedChangeListener(selectionListener);


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.setTitle("Filter").setView(view).setPositiveButton("Ok",
               (dialogInterface, i) ->{
                saveFilterState();
                if(onFilterConfirmedListener != null)
                    onFilterConfirmedListener.onFilterConfirmed(currentFilterObject);
            }).create() ;
    }

    private void saveFilterState(){
        currentFilterObject.showViral = viralCheckBox.isChecked();
        //persist filter object here;

    }

    private void handleSectionSelection(String section){

        switch (section.toLowerCase()){
            case Sections.SECTION_HOT:
                viralCheckBox.setEnabled(false);
                risingBtn.setEnabled(false);
                setChildrenEnabled(windowGroup,false);

                break;
            case Sections.SECTION_TOP:
                risingBtn.setEnabled(false);
                viralCheckBox.setEnabled(false);
                setChildrenEnabled(windowGroup,true);
                break;
            case Sections.SECTION_USER:
                risingBtn.setEnabled(true);
                viralCheckBox.setEnabled(true);
                setChildrenEnabled(windowGroup,false);
                break;
        }
    }

    private void setChildrenEnabled(ViewGroup view , boolean enabled){
        int count = view.getChildCount();
        for (int i = 0; i <count ; i++) {
            view.getChildAt(i).setEnabled(enabled);
        }
    }

    public void showDialog(){
        dialog.show();
    }
}
