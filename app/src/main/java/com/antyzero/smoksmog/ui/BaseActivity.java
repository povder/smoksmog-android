package com.antyzero.smoksmog.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.ActivityLifecycleProvider;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class BaseActivity extends AppCompatActivity implements ActivityLifecycleProvider {

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    private MainActivityComponent activityComponent;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        this.lifecycleSubject.onNext( ActivityEvent.CREATE );

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            getWindow().setNavigationBarColor( getResources().getColor( R.color.primary ) );
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.lifecycleSubject.onNext( ActivityEvent.START );
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.lifecycleSubject.onNext( ActivityEvent.RESUME );
    }

    @Override
    protected void onPause() {
        this.lifecycleSubject.onNext( ActivityEvent.PAUSE );
        super.onPause();
    }

    @Override
    protected void onStop() {
        this.lifecycleSubject.onNext( ActivityEvent.STOP );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        this.lifecycleSubject.onNext( ActivityEvent.DESTROY );
        super.onDestroy();
    }

    @Override
    public final Observable<ActivityEvent> lifecycle() {
        return this.lifecycleSubject.asObservable();
    }

    @Override
    public final <T> Observable.Transformer<T, T> bindUntilEvent( ActivityEvent event ) {
        return RxLifecycle.bindUntilActivityEvent( this.lifecycleSubject, event );
    }

    @Override
    public final <T> Observable.Transformer<T, T> bindToLifecycle() {
        return RxLifecycle.bindActivity( this.lifecycleSubject );
    }

    protected MainActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
