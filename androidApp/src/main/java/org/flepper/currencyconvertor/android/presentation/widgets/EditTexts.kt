package org.flepper.currencyconvertor.android.presentation.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import org.flepper.currencyconvertor.android.theme.gray


@Composable
fun OutLineEdittextText(
    modifier: Modifier = Modifier,
    hint: String,
    text: String,
    onTextChanged: (String) -> Unit
) {

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChanged,
            placeholder = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        hint, color = MaterialTheme.colors.onSurface, modifier = Modifier.align(
                            Alignment.CenterStart
                        )
                    )
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(size2dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                unfocusedIndicatorColor = gray,
                textColor = MaterialTheme.colors.onSurface
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), singleLine = true, keyboardActions = KeyboardActions()
        )
    }

}

/** @EditText_Number */

@Composable
fun OutLineEdittextNumber(
    modifier: Modifier = Modifier,
    hint: String,
    text: String,
    onTextChanged: (String) -> Unit
) {

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChanged,
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.End, fontWeight = FontWeight.Medium, fontSize = 20.sp
            ),
            label = {
                Text(
                    hint, color = MaterialTheme.colors.onSurface, modifier = Modifier.align(
                        Alignment.CenterEnd
                    )
                )
            },
            placeholder = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        hint, color = MaterialTheme.colors.onSurface, modifier = Modifier.align(
                            Alignment.CenterEnd
                        )
                    )
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(size2dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                unfocusedIndicatorColor = gray,
                textColor = MaterialTheme.colors.onSurface
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun OutLineEdittextNumberPreview() {

    var text by remember {
        mutableStateOf("")
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = text,
            onValueChange = { txt ->
                text = txt
            },
            textStyle = TextStyle(textAlign = TextAlign.End),
            placeholder = { Text("sample hint", color = MaterialTheme.colors.onSurface) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(size2dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                unfocusedIndicatorColor = gray,
                textColor = MaterialTheme.colors.onSurface
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}


