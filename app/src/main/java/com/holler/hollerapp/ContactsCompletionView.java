package com.holler.hollerapp;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokenautocomplete.TokenCompleteTextView;

import Models.TagItem;

public class ContactsCompletionView extends TokenCompleteTextView<TagItem> {
    public ContactsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getViewForObject(TagItem tag) {

        LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout)l.inflate(R.layout.token_layout, (ViewGroup)ContactsCompletionView.this.getParent(), false);
        ((TextView)view.findViewById(R.id.name)).setText(tag.getTagName());

        return view;
    }

    @Override
    protected TagItem defaultObject(String completionText) {
        //Stupid simple example of guessing if we have an email or not

            return new TagItem(completionText);

    }
}