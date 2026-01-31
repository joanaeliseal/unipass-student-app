package br.edu.ifpb.unipass.di

import br.edu.ifpb.unipass.data.repository.TripRepository
import br.edu.ifpb.unipass.ui.screens.home.HomeViewModel
import br.edu.ifpb.unipass.ui.screens.trips.TripsViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FirebaseFirestore.getInstance() }
    single { TripRepository(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { TripsViewModel(get()) }
}
