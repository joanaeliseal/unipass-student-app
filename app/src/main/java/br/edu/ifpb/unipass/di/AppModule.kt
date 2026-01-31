package br.edu.ifpb.unipass.di

import br.edu.ifpb.unipass.data.repository.TripRepository
import br.edu.ifpb.unipass.ui.screens.home.HomeViewModel
import br.edu.ifpb.unipass.ui.screens.trips.TripsViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
// Database
    single { AppDatabase.getDatabase(androidContext()) }

    // DAOs
    single { get<AppDatabase>().tripDao() }
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().studentCardDao() }

    // Repositories
    single { TripRepository() }

    // ViewModels
    viewModel {
        HomeViewModel(
            tripRepository = get(),
            database = get()
        )
    }

    viewModel {
        TripsViewModel(
            tripRepository = get(),
            database = get()
        )
    }
}
