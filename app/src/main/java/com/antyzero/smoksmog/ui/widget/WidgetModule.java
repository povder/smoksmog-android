package com.antyzero.smoksmog.ui.widget;


import android.content.Context;

import com.antyzero.smoksmog.ui.widget.station.StationWidgetManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class WidgetModule {

    @Provides
    @Singleton
    public StationWidgetManager provideStationWidgetManager( Context context ) {
        return new StationWidgetManager( context );
    }
}
