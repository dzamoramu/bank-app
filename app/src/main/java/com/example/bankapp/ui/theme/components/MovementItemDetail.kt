package com.example.bankapp.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bankapp.R

@Composable
fun MovementItemDetail(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    amount: String,
    concept: String,
    date: String
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {

        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.on_back)
            )
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TitleOperationText(amount, concept)
        }

        Column(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {


            OperationText(detail = "${stringResource(id = R.string.operation)} $concept")

            OperationText(detail = stringResource(id = R.string.success_movement_status))

            OperationText(detail = "${stringResource(id = R.string.customer_data)} 1254958693")

            OperationText(detail = "${stringResource(id = R.string.date)} $date")

        }
    }
}

@Composable
fun TitleOperationText(amount: String, concept: String) {
    Text(
        text = stringResource(id = R.string.detail),
        fontSize = 18.sp,
        style = MaterialTheme.typography.headlineSmall
    )

    Text(
        text = "$ $amount",
        fontSize = 34.sp,
        color = if (concept != "Transferencia") {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.error
        },
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun OperationText(detail: String) {
    Text(
        text = detail,
        style = MaterialTheme.typography.titleLarge
    )
    Divider(
        modifier = Modifier.fillMaxWidth()
    )
}