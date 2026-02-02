package br.edu.ifpb.unipass.ui.screens.trips

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.NavController
import br.edu.ifpb.unipass.models.Trip
import br.edu.ifpb.unipass.ui.components.AppTopBar
import br.edu.ifpb.unipass.ui.components.EmptyState
import br.edu.ifpb.unipass.ui.components.FilterChip
import br.edu.ifpb.unipass.ui.components.TripCard

@Composable
fun TripsScreen(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel: TripsViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        TripsContent(
            modifier = modifier,
            trips = uiState.filteredTrips,
            selectedFilter = uiState.selectedFilter,
            onFilterChange = viewModel::onFilterSelected
        )
    }
}

@Composable
private fun TripsContent(
    trips: List<Trip>,
    selectedFilter: TripFilter,
    onFilterChange: (TripFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = "Histórico de Viagens",
                showNavigationIcon = false,
                showMenuIcon = true,
                onMenuClick = { /* TODO: Implementar menu */ }
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                FilterSection(
                    selectedFilter = selectedFilter,
                    onFilterChange = onFilterChange
                )
            }

            if (trips.isEmpty()) {
                EmptyState(
                    icon = Icons.Outlined.DirectionsBus,
                    title = "Nenhuma viagem encontrada",
                    description = "Você ainda não possui viagens ${getFilterText(selectedFilter)}.",
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(trips) { trip ->
                        TripCard(trip = trip)
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterSection(
    selectedFilter: TripFilter,
    onFilterChange: (TripFilter) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                label = "Todas",
                isSelected = selectedFilter == TripFilter.ALL,
                onClick = { onFilterChange(TripFilter.ALL) }
            )
        }
        item {
            FilterChip(
                label = "Concluídas",
                isSelected = selectedFilter == TripFilter.COMPLETED,
                onClick = { onFilterChange(TripFilter.COMPLETED) }
            )
        }
        item {
            FilterChip(
                label = "Canceladas",
                isSelected = selectedFilter == TripFilter.CANCELLED,
                onClick = { onFilterChange(TripFilter.CANCELLED) }
            )
        }
    }
}

private fun getFilterText(filter: TripFilter): String {
    return when (filter) {
        TripFilter.ALL -> "registradas"
        TripFilter.COMPLETED -> "concluídas"
        TripFilter.CANCELLED -> "canceladas"
    }
}

@Composable
fun ViagensScreen(navController: NavController, modifier: Modifier) {
    TripsScreen(navController, modifier)
}
