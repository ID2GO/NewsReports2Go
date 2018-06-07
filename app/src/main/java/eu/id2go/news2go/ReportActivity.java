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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity implements
        LoaderCallbacks<java.util.List<Report>> {


    // When we get to the onPostExecute() method, we need to update the ListView. The only way
    // to update the contents of the list is to update the data set within the ReportAdapter.
    // To access and modify the instance of the ReportAdapter, we need to make it a global
    // variable in the ReportActivity.
    /**
     * Adapter for the list of reports
     */
    private ReportAdapter mAdapter;
    /**
     * Tag for test log messages
     */
    public static final String LOG_TAG = ReportActivity.class.getName();

    /**
     * URL for report data from the GUARDIAN dataset
     */
    private static final String GUARDIAN_REQUEST_URL = "http://content.guardianapis.com/search?" +
     "q=politics&api-key=test";

    /**
     * Constant value for the report loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int REPORT_LOADER_ID = 1;

    /**
     * TextView that is displayed when the list is empty
     */
    private android.widget.TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//      Log for testing purposes
//      android.util.Log.i(LOG_TAG, "Test: Report Activity onCreate() called.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView reportListView = findViewById(R.id.list);

        // If there is no report data to be displayed, mEmptyStateTextView is called to action
        mEmptyStateTextView = findViewById(R.id.empty_view);
        reportListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of reports as input
        mAdapter = new ReportAdapter(this, new ArrayList<Report>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        reportListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected report.
        reportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current report that was clicked on
                Report currentReport = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri reportUri = Uri.parse(currentReport.getUrl());

                // Create a new intent to view the report URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, reportUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        android.net.ConnectivityManager connMgr = (android.net.ConnectivityManager)
                getSystemService(android.content.Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        android.net.NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(REPORT_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }

    // The onCreateLoader() is needed for when the LoaderManager has determined that the loader
    // with our specified ID isn't running, so we should create a new one.
    @Override
    public Loader<java.util.List<Report>> onCreateLoader(int i, Bundle bundle) {
//      Log for testing purposes
//      android.util.Log.i(LOG_TAG, "Test: Report Activity onCreateLoader() called.");
    // Create a new loader for the given URL
        return new ReportLoader(this, GUARDIAN_REQUEST_URL);
    }

    // The onLoadFinished() is needed for updating the dataset in the adapter
    @Override
    public void onLoadFinished(Loader<java.util.List<Report>> loader, java.util.List<Report>
            reports) {
//      Log for testing purposes
//      android.util.Log.i(LOG_TAG, "Test: Report Activity onLoadFinished() called.");
       // Clear the adapter of previous report data
        mAdapter.clear();

        // If there is a valid list of {@link Report}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (reports != null && !reports.isEmpty()) {
//        android.util.Log function for testing purposes
//        android.util.Log.i(LOG_TAG, "Test: Report Activity onLoadFinished() if empty called.");

            // Hide loading indicator because the data has been loaded
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Set empty state text to display "No reports found."
            mEmptyStateTextView.setText(R.string.no_reports);

            // Clear the adapter of previous report data
            mAdapter.clear();

            // Fill the adapter with new report data
            mAdapter.addAll(reports);
        }
    }

    // The onLoaderReset() is needed for when the data from our loader is no langer valid and
    // the adapters data set needs to be reset
    @Override
    public void onLoaderReset(Loader<java.util.List<Report>> loader) {
//        android.util.Log function for testing purposes
//        android.util.Log.i(LOG_TAG, "Test: Report Activity onLoaderReset() called.");

        // Loader reset, so we can clear out existing data.
        mAdapter.clear();
    }

}
