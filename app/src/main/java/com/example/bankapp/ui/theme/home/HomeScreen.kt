package com.example.bankapp.ui.theme.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.CallReceived
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bankapp.R
import com.example.bankapp.data.model.UserMovement
import com.example.bankapp.navigation.Destination
import com.example.bankapp.ui.theme.components.MovementItemCard

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel()
) {
    val movements by viewModel.allMovements.observeAsState(initial = UserMovement("", emptyList()))

    Column {
        Balance(movements)

        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            text = stringResource(id = R.string.home_title_movements)
        )

        if (movements.movements.isNotEmpty()) {
            ListItems(
                navController = navController,
                movements = movements
            )
        } else {
            UserWithoutMovements()
        }
    }
}

@Composable
fun Balance(
    movements: UserMovement
) {
    Column {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.balance),
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    fontSize = 24.sp,
                    text = if (movements.balance.isNotEmpty()) {
                        "$ ${movements.balance}"
                    } else {
                        "$ 0.0 "
                    }
                )

                Spacer(modifier = Modifier.size(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Button(onClick = { /*TODO*/ }) {

                        Icon(imageVector = Icons.Filled.CallReceived, contentDescription = "")
                        Text(text = stringResource(id = R.string.deposit))
                    }

                    OutlinedButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.ArrowOutward, contentDescription = "")
                        Text(text = stringResource(id = R.string.transfer))
                    }
                }
            }
        }
    }
}

@Composable
fun ListItems(
    navController: NavHostController,
    movements: UserMovement
) {
    LazyColumn {
        items(movements.movements) {
            MovementItemCard(
                item = it,
                onClick = {
                    navController.navigate(
                        Destination.MovementDetail.createRoute(
                            amount = it.amount,
                            concept = it.concept,
                            date = it.date.toString()
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun UserWithoutMovements() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(86.dp),
            imageVector = Icons.Rounded.AccountBalanceWallet,
            tint = MaterialTheme.colorScheme.surfaceTint,
            contentDescription = stringResource(id = R.string.without_movements)
        )

        Text(text = stringResource(id = R.string.without_movements_description))
    }
}