package com.antyzero.smoksmog.ui.widget;


import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.antyzero.smoksmog.R;

public class StationWidgetManager {

    private static final String WIDGET_STATION = "WIDGET_STATION";
    private static final String PREFIX = "station_widget_";

    private final SharedPreferences preference;
    private final Context context;

    public StationWidgetManager( Context context ) {
        this.context = context;
        preference = context.getSharedPreferences( WIDGET_STATION, Context.MODE_PRIVATE );
    }

    public void addWidget( int appWidgetId, long stationId ) {
        preference.edit().putLong( PREFIX + appWidgetId, stationId ).apply();
    }

    public void deleteWidget( int appWidgetId ) {
        preference.edit().remove( PREFIX + appWidgetId ).apply();
    }

    public void updateWidget( AppWidgetManager appWidgetManager, int appWidgetId ) {

        CharSequence widgetText = StationWidgetConfigureActivity.loadTitlePref( context, appWidgetId );

        RemoteViews views = new RemoteViews( context.getPackageName(), R.layout.widget_station );
        views.setTextViewText( R.id.appwidget_text, widgetText );

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget( appWidgetId, views );
    }
}
