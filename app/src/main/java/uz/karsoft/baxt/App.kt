package uz.karsoft.baxt

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uz.karsoft.baxt.di.helperModule
import uz.karsoft.baxt.di.networkModule
import uz.karsoft.baxt.di.repositoryModule
import uz.karsoft.baxt.di.viewModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@App)
            modules(networkModule, repositoryModule, viewModule, helperModule)
        }


    }
}