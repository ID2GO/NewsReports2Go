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


/**
 * An {@link Report} object contains information related to a single report.
 */
public class Report {

//    Declaring global variables of type private: magnitude, sectionTitle & dateOfPublication
//    Remember, opposed to public global variables, variables of the data type private can only be
//    accessed by methods within the same class!

    /**
     * FirstName & LastName of the reporter
     */
    private String mFirstName;
    private String mLastName;

    /**
     * Magnitude of the report
     */
    private String mArticleTitle;

    /**
     * ArticleSection of the report
     */
    private String mArticleSection;

    /**
     * Time of the report
     */
    private String mDateOfPublication;

    /**
     * Website URL of the report
     */
    private String mUrl;

    /**
     * In the Constructor we initialise the variables
     * Constructs a new {@link Report} object.
     *
     * @param firstName        is the name of the news reporter
     * @param lastName        is the name of the news reporter
     * @param articleTitle      is the title of the news report
     * @param sectionTitle      is the section where the report is published
     * @param dateOfPublication is the time when the news report was published
     * @param url               is the website URL to the site of publication to find more details about the
     *                          report
     */
    public Report(String firstName, String lastName, String articleTitle, String sectionTitle, String dateOfPublication, String url) {
        mFirstName = firstName;
        mLastName = lastName;
        mArticleTitle = articleTitle;
        mArticleSection = sectionTitle;
        mDateOfPublication = dateOfPublication;
        mUrl = url;
    }

    /**
     * Public getter methods for accessing the private global variables to make info accessible
     * by other Classes Returns the magnitude, sectionTitle & time of the report and the url of
     * the site where the info is recorded.
     */
    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }


    public String getArticleTitle() {
        return mArticleTitle;
    }

    /**
     * Returns the sectionTitle of the report.
     */
    public String getArticleSection() {
        return mArticleSection;
    }

    /**
     * Returns the time of the report in UNIX-time).
     */
    public String getDateOfPublication() {
        return mDateOfPublication;
    }

    /**
     * Returns the website URL to find more information about the report.
     */
    public String getUrl() {
        return mUrl;
    }
}
