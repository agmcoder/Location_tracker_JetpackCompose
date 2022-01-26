package com.devcode.powerlock.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.devcode.powerlock.R
import com.devcode.powerlock.theme.primaryColor

@Composable
fun OutLinedTextFieldCustom() {

}

@Composable
fun OutLinedTextFieldCustomPassword(
    passwordValue : MutableState<String>,
    text : String,
    placeholderText : String,
    passwordVisibilityIn : MutableState<Boolean>
) {
    val focusRequester = remember { FocusRequester() }
    val passwordVisibility=passwordVisibilityIn
    OutlinedTextField(
        value = passwordValue.value,
        onValueChange = { passwordValue.value = it },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        label = { Text(text = text, color = Color.Black) },
        placeholder = { Text(text = placeholderText, color = Color.Black) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .focusRequester(focusRequester = focusRequester),
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility.value = !passwordVisibility.value
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.password_eye),
                    contentDescription = "",
                    tint = if (passwordVisibility.value) primaryColor else Color.Black
                )

            }
        },
        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
        else PasswordVisualTransformation()
    )

}