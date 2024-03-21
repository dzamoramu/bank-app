package com.example.bankapp.ui.theme.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bankapp.R
import com.example.bankapp.data.model.ProfileInformation
import com.example.bankapp.navigation.Destination
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController
) {

    val userInformation by viewModel.userInformation.observeAsState(
        initial = ProfileInformation(
            "",
            "",
            ""
        )
    )
    val userEmail = FirebaseAuth.getInstance().currentUser?.email

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Spacer(modifier = modifier.size(48.dp))

            Text(
                text = stringResource(id = R.string.profile_title),
                style = MaterialTheme.typography.titleMedium
            )

            Icon(
                modifier = modifier.size(86.dp),
                imageVector = Icons.Filled.AccountCircle,
                tint = MaterialTheme.colorScheme.surfaceTint,
                contentDescription = stringResource(id = R.string.profile_content_description)
            )
        }

        Column(
            modifier = modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                modifier = modifier.padding(bottom = 8.dp),
                text = stringResource(id = R.string.personal_data_title),
                fontSize = 18.sp,
                style = MaterialTheme.typography.headlineSmall
            )

            val fullName = userInformation.name + " " + userInformation.lastName
            with(userInformation) {
                Text(text = "${stringResource(id = R.string.personal_data_name)}: $fullName")
                Text(text = "${stringResource(id = R.string.email)}: $userEmail")
                Text(text = "${stringResource(id = R.string.personal_data_user_id)}: ${this.userId}")
            }
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                modifier = modifier.clickable {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Destination.Login.route) {
                        popUpTo(Destination.Profile.route) {
                            inclusive = true
                        }
                    }
                },
                text = stringResource(id = R.string.logout)
            )
        }
    }
}
