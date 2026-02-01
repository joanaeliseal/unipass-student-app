package br.edu.ifpb.unipass.di

import br.edu.ifpb.unipass.data.local.AppDatabase
import br.edu.ifpb.unipass.data.local.UserSessionManager
import br.edu.ifpb.unipass.data.repository.TripRepository
import br.edu.ifpb.unipass.ui.viewmodel.HomeViewModel
import br.edu.ifpb.unipass.ui.viewmodel.LoginViewModel
import br.edu.ifpb.unipass.ui.screens.trips.TripsViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Firebase
    single { FirebaseFirestore.getInstance() }

    // Database
    single { AppDatabase.getDatabase(androidContext()) }

    // Session Manager
    single { UserSessionManager(androidContext()) }

    // DAOs
    single { get<AppDatabase>().tripDao() }
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().studentCardDao() }

    // Repositories
    single { TripRepository(get()) }

    // ViewModels
    viewModel {
        LoginViewModel(
            database = get(),
            sessionManager = get()
        )
    }

    viewModel {
        val sessionManager: UserSessionManager = get()
        val userId = sessionManager.getUserId() ?: "guest"

        HomeViewModel(
            database = get(),
            tripRepository = get(),
            userId = userId
        )
    }

    viewModel {
        TripsViewModel(
            tripRepository = get(),
            database = get()
        )
    }
}
