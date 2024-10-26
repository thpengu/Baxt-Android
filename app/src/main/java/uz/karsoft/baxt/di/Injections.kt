package uz.karsoft.baxt.di

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.karsoft.baxt.data.remote.ApiInterface
import uz.karsoft.baxt.repo.AuthRepository
import uz.karsoft.baxt.repo.MainRepository
import uz.karsoft.baxt.settings.Settings
import uz.karsoft.baxt.ui.auth.AuthVM
import uz.karsoft.baxt.ui.main.home.HomeVM
import uz.karsoft.baxt.ui.main.home.category.CategoryVM
import uz.karsoft.baxt.ui.main.home.category.markets.MarketByIdVM
import uz.karsoft.baxt.ui.main.home.category.products.ProductsVM
import uz.karsoft.baxt.ui.main.home.foods.AllFoodsVM
import uz.karsoft.baxt.ui.main.search.SearchVM

val networkModule = module {
    val baseUrl = "https://www.api.baxt.iztileuoff.uz"

    single {
        GsonBuilder().create()
    }

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true)
            .build()
    }
    single {
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }
    single {
        get<Retrofit>().create(ApiInterface::class.java)
    }
}
val helperModule = module {
    single { Settings(androidApplication().applicationContext) }
}

val repositoryModule = module {
    single {AuthRepository(get(), get())}
    single { MainRepository(get(), get()) }
}
val viewModule = module {
    viewModel { AuthVM(get()) }
    viewModel { HomeVM(get()) }
    viewModel { SearchVM(get()) }
    viewModel { CategoryVM(get())}
    viewModel { ProductsVM(get())}
    viewModel { AllFoodsVM(get())}
    viewModel { MarketByIdVM(get())}
}
