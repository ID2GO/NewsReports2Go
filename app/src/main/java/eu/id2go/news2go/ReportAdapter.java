/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.id2go.news2go;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * An {@link ReportAdapter} knows how to create a list item layout for each report
 * in the data source (a list of {@link Report} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class ReportAdapter extends ArrayAdapter<Report> {

    /**
     * Constructs a new {@link ReportAdapter}.
     *
     * @param context of the app
     * @param reports is the list of reports, which is the data source of the adapter
     */
    public ReportAdapter(Context context, List<Report> reports) {
        super(context, 0, reports);
    }

    /**
     * Returns a list item view that displays information about the report at the given position
     * in the list of reports.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that can be reused,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.report_list_item, parent, false);
        }

        // Find the report at the given position in the list of reports
        Report currentReport = getItem(position);


        // Bind the data of the report object to the views in the list_item layout based
        // on the view_id's

        // Find the TextView with view ID article
        TextView articleView = listItemView.findViewById(R.id.article);
        // Format the articleTitle to show 1 decimal place
        articleView.setText(currentReport.getArticleTitle());

        // Find the TextView with view ID section
        TextView sectionView = listItemView.findViewById(R.id.section);
        // Format the articleTitle to show 1 decimal place
        sectionView.setText(currentReport.getArticleSection());

        // Create a new dateTime object from the time in regular time & date format in the
        // JSONreport

        String dateTime = currentReport.getDateOfPublication();
        String[] dateAndTime = formatDateAndTime(dateTime);

        // Find the TextView with view ID date
        TextView dateView = listItemView.findViewById(R.id.date);
        // Set Array entry to position 0
        dateView.setText(dateAndTime[0]);

        // Find the TextView with view ID time
        TextView timeView = listItemView.findViewById(R.id.time);
        // Set Array entry to position 1
        timeView.setText(dateAndTime[1]);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }


    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String[] formatDateAndTime(String dateTime) {
        String[] dateAndTimeSplit = dateTime.replace("T", " ").replace("Z", " ").trim().split
                (" ");

        return dateAndTimeSplit;
    }

}
