package com.example.bankapp.ui.theme.register

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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bankapp.R
import com.example.bankapp.navigation.Destination

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!viewModel.showErrorDialog.value) {
            Body(
                modifier = Modifier,
                viewModel = viewModel,
                navController = navController
            )
        } else {
            AlertDialogCustom(
                viewModel = viewModel,
                isErrorDialog = true,
                navController = navController,
                title = stringResource(id = R.string.error_dialog_title),
                description = stringResource(id = R.string.error_dialog_register_description)
            )
        }
    }
}

@Composable
fun Body(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: RegisterViewModel
) {
    val email: String by viewModel.email.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    val name: String by viewModel.name.observeAsState("")
    val lastName: String by viewModel.lastName.observeAsState("")
    val isCreateAccountEnabled: Boolean by viewModel.isEnabled.observeAsState(false)

    Column(
        modifier = modifier.widthIn(max = 380.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        if (viewModel.showConfirmDialog.value) {
            AlertDialogCustom(
                viewModel = viewModel,
                navController = navController,
                title = stringResource(id = R.string.success_dialog_register_title),
                description = stringResource(id = R.string.success_dialog_register_description),
                isErrorDialog = false
            )
        }

        Spacer(modifier = modifier.size(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Name(name = name, modifier = modifier) {
                viewModel.onCreateAccountChanged(
                    name = it,
                    email = email,
                    lastName = lastName,
                    password = password
                )
            }
            LastName(lastName = lastName, modifier = modifier) {
                viewModel.onCreateAccountChanged(
                    name = name,
                    email = email,
                    lastName = it,
                    password = password
                )
            }

            Email(email = email, modifier = modifier) {
                viewModel.onCreateAccountChanged(
                    email = it,
                    name = name,
                    lastName = lastName,
                    password = password
                )
            }

            Password(password = password, modifier = modifier) {
                viewModel.onCreateAccountChanged(
                    name = name,
                    email = email,
                    lastName = lastName,
                    password = it
                )
            }
        }

        Button(
            onClick = { navController.navigate(Destination.Camera.route) },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = stringResource(id = R.string.take_picture_button))
        }

        RegisterButton(
            isCreateAccountEnabled,
            onClick = { viewModel.onCreateAccount(email, password, name, lastName) }
        )

        Spacer(modifier = modifier.size(16.dp))
    }
}

@Composable
fun Name(name: String, modifier: Modifier, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = modifier.widthIn(max = 280.dp),
        value = name,
        onValueChange = { onTextChanged(it) },
        label = { Text(text = stringResource(id = R.string.personal_data_name)) },
        maxLines = 1,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black
        )
    )
}

@Composable
fun LastName(lastName: String, modifier: Modifier, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = modifier.widthIn(max = 280.dp),
        value = lastName,
        onValueChange = { onTextChanged(it) },
        label = { Text(text = stringResource(id = R.string.register_form_last_name)) },
        maxLines = 1,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black
        )
    )
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
fun RegisterButton(
    isCreateAccountEnabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = isCreateAccountEnabled,
        modifier = Modifier.width(340.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black
        )
    ) {
        Text(text = stringResource(id = R.string.register_button))
    }
}

@Composable
fun AlertDialogCustom(
    viewModel: RegisterViewModel,
    navController: NavHostController,
    title: String,
    description: String,
    isErrorDialog: Boolean
) {
    AlertDialog(
        title = {
            Text(text = title)
        },
        text = {
            Text(text = description)
        },
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(
                onClick = {
                    if (isErrorDialog) {
                        viewModel.showErrorDialog.value = false
                    } else {
                        navController.navigate(Destination.Login.route) {
                            popUpTo(Destination.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            ) {
                Text(stringResource(id = R.string.error_dialog_button))
            }
        }
    )
}