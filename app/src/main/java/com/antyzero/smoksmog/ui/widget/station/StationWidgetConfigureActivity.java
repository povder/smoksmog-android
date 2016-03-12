package com.antyzero.smoksmog.ui.widget.station;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.logger.Logger;
import com.antyzero.smoksmog.ui.BaseDragonActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import pl.malopolska.smoksmog.SmokSmog;
import pl.malopolska.smoksmog.model.Station;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * The configuration screen for the {@link StationWidget StationWidget} AppWidget.
 */
public class StationWidgetConfigureActivity extends BaseDragonActivity {

    private static final String TAG = StationWidgetConfigureActivity.class.getSimpleName();

    @Inject
    StationWidgetManager stationWidgetManager;
    @Inject
    SmokSmog smokSmog;
    @Inject
    Logger logger;

    @Bind( R.id.recyclerView )
    RecyclerView recyclerView;

    private final List<Station> stationList = new ArrayList<>();
    private final StationAdapter adapter = new StationAdapter( stationList );
    ;

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    public void onCreate( Bundle icicle ) {
        super.onCreate( icicle );
        setContentView( R.layout.activity_configure_widget_station );
        SmokSmogApplication.get( this ).getAppComponent().inject( this );

        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.setAdapter( adapter );

        smokSmog.getApi().stations()
                .subscribeOn( Schedulers.newThread() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe( stations -> {
                            stationList.clear();
                            stationList.addAll( stations );
                        },
                        throwable -> {
                            logger.w( TAG, "Problem with station loading", throwable );
                        },
                        adapter::notifyDataSetChanged );

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult( RESULT_CANCELED );

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

/*
        mAppWidgetText.setText( String.valueOf(
                stationWidgetManager.getWidgetStationId( mAppWidgetId ) ) );*/
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

