package com.example.bankapp.ui.theme.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bankapp.R
import com.example.bankapp.navigation.Destination
import com.example.bankapp.ui.theme.BankAppTheme

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!viewModel.showErrorDialog.value) {
            Body(modifier = Modifier, viewModel, navController)
        } else {
            AlertErrorDialog(viewModel)
        }
    }
}

@Composable
fun Body(
    modifier: Modifier,
    viewModel: LoginViewModel,
    navController: NavHostController
) {
    val email: String by viewModel.email.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    val isLoginEnabled: Boolean by viewModel.isEnabled.observeAsState(false)

    Column(
        modifier = modifier.widthIn(max = 380.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Spacer(modifier = modifier.size(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Email(email = email, modifier = modifier) {
                viewModel.onLoginChanged(
                    email = it,
                    password = password
                )
            }

            Password(password = password, modifier = modifier) {
                viewModel.onLoginChanged(
                    email = email,
                    password = it
                )
            }
        }

        LoginButton(
            isLoginEnabled,
            onClick = {
                viewModel.onLoginClicked(email, password) {
                    navController.navigate(Destination.Home.route) {
                        popUpTo(Destination.Login.route) {
                            inclusive = true
                        }
                    }
                }
            }
        )

        RegisterButton(onClick = { navController.navigate(Destination.Register.route) })

        Spacer(modifier = modifier.size(16.dp))
    }
}

@Composable
fun Email(email: String, modifier: Modifier, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = modifier.widthIn(max = 280.dp),
        value = email,
        onValueChange = { onTextChanged(it) },
        label = { Text(text = stringResource(id = R.string.email)) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black
        )
    )
}

@Composable
fun Password(password: String, modifier: Modifier, onTextChanged: (String) -> Unit) {
    var passWordVisibility by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier.widthIn(max = 280.dp),
        value = password,
        onValueChange = { onTextChanged(it) },
        label = { Text(text = stringResource(id = R.string.password)) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black
        ),
        trailingIcon = {
            val image = if (passWordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passWordVisibility = !passWordVisibility }) {
                Icon(
                    imageVector = image,
                    contentDescription = ""
                )
            }
        },
        visualTransformation = if (passWordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}

@Composable
fun LoginButton(
    isLoginEnabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = isLoginEnabled,
        modifier = Modifier.width(340.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black
        )
    ) {
        Text(text = stringResource(id = R.string.login_button))
    }
}

@Composable
fun RegisterButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.width(340.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black
        )
    ) {
        Text(text = stringResource(id = R.string.register_button))
    }
}

@Composable
fun AlertErrorDialog(
    viewModel: LoginViewModel
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.error_dialog_title))
        },
        text = {
            Text(text = stringResource(id = R.string.error_dialog_description))
        },
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.showErrorDialog.value = false
                }
            ) {
                Text(stringResource(id = R.string.error_dialog_button))
            }
        })
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    BankAppTheme {
        LoginScreen(navController = rememberNavController())
    }
}