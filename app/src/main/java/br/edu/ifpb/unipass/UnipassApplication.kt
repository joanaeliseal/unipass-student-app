package br.edu.ifpb.unipass

import android.app.Application
import br.edu.ifpb.unipass.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UnipassApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@UnipassApplication)
            modules(appModule)
        }
    }
}
