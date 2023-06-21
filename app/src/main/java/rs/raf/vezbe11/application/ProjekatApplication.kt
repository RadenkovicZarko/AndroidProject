package rs.raf.vezbe11.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import timber.log.Timber
import org.koin.core.logger.Level
import rs.raf.vezbe11.modules.coreModule
import rs.raf.vezbe11.modules.mealModule

class ProjekatApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
//        TODO
        val modules = listOf(
            coreModule,
            mealModule
        )
        startKoin {
            androidLogger(Level.ERROR)
            // Use application context
            androidContext(this@ProjekatApplication)
            // Use properties from assets/koin.properties
            androidFileProperties()
            // Use koin fragment factory for fragment instantiation
            fragmentFactory()
            // modules
            modules(modules)
        }
    }
}