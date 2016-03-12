package com.antyzero.smoksmog.ui.widget.station;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import com.antyzero.smoksmog.SmokSmogApplication;

import javax.inject.Inject;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link StationWidgetConfigureActivity StationWidgetConfigureActivity}
 */
public class StationWidget extends AppWidgetProvider {

    @Inject
    StationWidgetManager stationWidgetManager;

    @Override
    public void onReceive( Context context, Intent intent ) {
        SmokSmogApplication.get( context ).getAppComponent().inject( this );
        super.onReceive( context, intent );
    }

    @Override
    public void onUpdate( Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds ) {
        for ( int appWidgetId : appWidgetIds ) {
            stationWidgetManager.updateWidget( appWidgetManager, appWidgetId );
        }
    }

    @Override
    public void onDeleted( Context context, int[] appWidgetIds ) {
        for ( int appWidgetId : appWidgetIds ) {
            stationWidgetManager.deleteWidget( appWidgetId );
        }
    }

    @Override
    public void onEnabled( Context context ) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled( Context context ) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

