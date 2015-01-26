package pl.malopolska.smoksmog.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.malopolska.smoksmog.R;
import pl.malopolska.smoksmog.base.BaseActivity;
import pl.malopolska.smoksmog.network.Station;
import pl.malopolska.smoksmog.toolbar.ToolbarController;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String EXTRA_STATION_ID = "EXTRA_STATION_ID";

    private ToolbarController toolbarController;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        toolbarController = new ToolbarController(this, toolbar);

        GoogleApiClient googleApiClient = getGoogleApiClient();

        googleApiClient.registerConnectionCallbacks(this);
        googleApiClient.registerConnectionFailedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getGoogleApiClient().connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getGoogleApiClient().disconnect();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        long stationId = intent.getLongExtra(EXTRA_STATION_ID, -1);

        Toast.makeText(this, ">> " + stationId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(getGoogleApiClient());

        // TODO get nearest station for that location
    }

    @Override
    public void onConnectionSuspended(int i) {
        // do nothing
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // TODO unable to get location service
    }

    /**
     * Starts activity with default sation selected
     *
     * @param context for starting Activity
     * @param station provides access to ID
     */
    public static void start(Context context, Station station) {

        Intent intent = new Intent(context, MainActivity.class);

        intent.putExtra(EXTRA_STATION_ID, station.getId());

        context.startActivity(intent);
    }
}
