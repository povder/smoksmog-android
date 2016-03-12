package com.antyzero.smoksmog.ui.widget.station;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.ui.BaseDragonActivity;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * The configuration screen for the {@link StationWidget StationWidget} AppWidget.
 */
public class StationWidgetConfigureActivity extends BaseDragonActivity {

    private static final String PREFS_NAME = "com.antyzero.smoksmog.ui.widget.station.StationWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";

    @Inject
    StationWidgetManager stationWidgetManager;

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    EditText mAppWidgetText;

    @Override
    public void onCreate( Bundle icicle ) {
        super.onCreate( icicle );
        setContentView( R.layout.activity_configure_widget_station );
        SmokSmogApplication.get( this ).getAppComponent().inject( this );

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult( RESULT_CANCELED );

        mAppWidgetText = ( EditText ) findViewById( R.id.appwidget_text );

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if ( extras != null ) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID );
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if ( mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID ) {
            finish();
            return;
        }

        mAppWidgetText.setText( String.valueOf(
                stationWidgetManager.getWidgetStationId( mAppWidgetId ) ) );
    }

    @OnClick( R.id.add_button )
    void onAddStationClick() {

        // When the button is clicked, store the string locally
        stationWidgetManager.addWidget( mAppWidgetId, 0L ); // TODO change station

        stationWidgetManager.updateWidget( mAppWidgetId );

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra( AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId );
        setResult( RESULT_OK, resultValue );
        finish();
    }
}

