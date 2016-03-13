package com.antyzero.smoksmog.ui.widget.station;


import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.air.AirQualityIndex;
import com.antyzero.smoksmog.logger.Logger;

import java.util.Locale;

import javax.inject.Inject;

import pl.malopolska.smoksmog.SmokSmog;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class StationWidgetManager {

    private static final String TAG = StationWidgetManager.class.getSimpleName();
    private static final String WIDGET_STATION = "WIDGET_STATION";
    private static final String PREFIX = "station_widget_";

    private final SharedPreferences preference;
    private final Context context;
    private final AppWidgetManager appWidgetManager;

    @Inject
    SmokSmog smokSmog;
    @Inject
    Logger logger;


    public StationWidgetManager( Context context ) {
        SmokSmogApplication.get( context ).getAppComponent().inject( this );
        this.context = context;
        preference = context.getSharedPreferences( WIDGET_STATION, Context.MODE_PRIVATE );
        appWidgetManager = AppWidgetManager.getInstance( context );
    }

    public void addWidget( int appWidgetId, long stationId ) {
        preference.edit().putLong( PREFIX + appWidgetId, stationId ).apply();
    }

    public long getWidgetStationId( int appWidgetId ) {
        return preference.getLong( PREFIX + appWidgetId, AppWidgetManager.INVALID_APPWIDGET_ID );
    }

    public void deleteWidget( int appWidgetId ) {
        preference.edit().remove( PREFIX + appWidgetId ).apply();
    }

    public void updateWidget( int appWidgetId ) {

        smokSmog.getApi().station( getWidgetStationId( appWidgetId ) )
                .subscribeOn( Schedulers.newThread() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        station -> {

                            double indexValue = AirQualityIndex.calculate( station );
                            String airQualityIndex = String.format( Locale.getDefault(), "%.1f", indexValue );

                            RemoteViews views = new RemoteViews( context.getPackageName(), R.layout.widget_station );

                            views.setTextViewText( R.id.textViewStationName, "" + station.getName() );
                            views.setTextViewText( R.id.textViewAirQualityIndex, airQualityIndex );

                            appWidgetManager.updateAppWidget( appWidgetId, views );
                        },
                        throwable -> {
                            logger.w( TAG, "Unable to update widget " + appWidgetId, throwable );
                        } );

    }
}
