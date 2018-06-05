package eu.id2go.news2go;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

// Define the ReportLoader class, extend AsyncTaskLoader and specify List as the generic
// parameter. This explains what type of data is expected to be loaded. In this case, the loader is
// loading a list of Report objects. Then take a String URL in the constructor, and in
// loadInBackground(), do the exact same operations as in doInBackground back in
// ReportAsyncTask. Important: Override the onStartLoading() method to call forceLoad() which is
// a required step to actually trigger the loadInBackground() method to execute.
/**
 * Loads a list of reports by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class ReportLoader extends AsyncTaskLoader<List<Report>> {

    /** Tag for log messages */
    private static final String LOG_TAG = ReportLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link ReportLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public ReportLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
//        android.util.Log.i(LOG_TAG, "Test: Report Loader onStartLoading() called.");

        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Report> loadInBackground() {
//        android.util.Log.i(LOG_TAG, "Test: Report Loader loadInBackground() called.");

        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of reports.
        List<Report> reports = QueryUtils.fetchReportData(mUrl);
        return reports;
    }
}