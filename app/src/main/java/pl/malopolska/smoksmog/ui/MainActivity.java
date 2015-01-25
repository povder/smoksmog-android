package pl.malopolska.smoksmog.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import pl.malopolska.smoksmog.R;
import pl.malopolska.smoksmog.data.Dagger_DataManager;
import pl.malopolska.smoksmog.data.NetworkModule;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        setSupportActionBar( (Toolbar) findViewById( R.id.toolbar ) );

    }
}
