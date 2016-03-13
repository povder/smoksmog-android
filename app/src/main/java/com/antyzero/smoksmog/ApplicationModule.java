package com.antyzero.smoksmog;

import android.app.Application;
import android.content.Context;

import com.antyzero.smoksmog.permission.PermissionHelper;
import com.antyzero.smoksmog.ui.typeface.TypefaceProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

@Singleton
@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule( Application application ) {
        this.application = application;

        CalligraphyConfig.initDefault( new CalligraphyConfig.Builder()
                .setDefaultFontPath( "fonts/Lato-Light.ttf" )
                .build() );
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public TypefaceProvider provideTypefaceProvider( Context context ) {
        return new TypefaceProvider( context );
    }

    @Provides
    @Singleton
    public PermissionHelper providePermissionHelper( Context context ){
        return new PermissionHelper( context );
    }
}
