package com.test.newshop1.ui.searchActivity;

import android.content.SearchRecentSuggestionsProvider;

public class SuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.test.newshop1.SuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES | DATABASE_MODE_2LINES;

    public SuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
