package org.flepper.currencyconvertor.android.presentation.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.flepper.currencyconvertor.android.MainActivityViewModel
import org.flepper.currencyconvertor.android.R
import org.flepper.currencyconvertor.android.presentation.preview_providers.BaseUnitForPreview
import org.flepper.currencyconvertor.android.presentation.preview_providers.CurrencyPreviewProvider
import org.flepper.currencyconvertor.android.presentation.preview_providers.BaseUnitPreviewProvider
import org.flepper.currencyconvertor.android.presentation.widgets.*
import org.flepper.currencyconvertor.android.theme.*
import org.flepper.currencyconvertor.android.utils.localeToEmoji
import org.flepper.currencyconvertor.android.utils.parseAmount
import org.flepper.currencyconvertor.android.utils.to2Dp
import org.flepper.currencyconvertor.android.utils.toLocalCurrency
import org.flepper.currencyconvertor.data.Currency
import org.flepper.currencyconvertor.data.GRID_COLUMN_COUNT


enum class BottomSheetScreen {
    CHOOSE_BASE,
    VIEW_SELECTED
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MainHomeScreen(mainActivityViewModel: MainActivityViewModel) {

    val currencyRatesResult = mainActivityViewModel.currencyRates.collectAsState()
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)


    var currencyText by remember {
        mutableStateOf(
            TextFieldValue(
                text = ""
            )
        )
    }


            var changeCurrentCurrency by remember {
        mutableStateOf(false)
    }

    var countrySearch by remember {
        mutableStateOf("")
    }

    val coroutinesScope = rememberCoroutineScope()

    fun hideBottomSheet() {
        coroutinesScope.launch {
            modalBottomSheetState.hide()
        }
    }

    fun showBottomSheet() {
        coroutinesScope.launch {
            modalBottomSheetState.show()
        }
    }





    val keyboardController = LocalSoftwareKeyboardController.current
    var bottomSheetView by remember {
        mutableStateOf(BottomSheetScreen.VIEW_SELECTED)
    }
    var currentCurrency by remember {
        mutableStateOf(currencyRatesResult.value.result?.rates?.first())
    }


    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = size10dp, topEnd = size10dp),
        sheetContent = {
            if (bottomSheetView == BottomSheetScreen.CHOOSE_BASE) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(smallPadding)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(smallPadding))
                    {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            MediumTextBold(
                                text = stringResource(id = R.string.select_currency),
                                modifier = Modifier.align(
                                    Alignment.CenterHorizontally
                                )
                            )
                            OutLineEdittextText(
                                modifier = Modifier.fillMaxWidth(),
                                text = countrySearch,
                                onTextChanged = { srch ->
                                    countrySearch = srch
                                    mainActivityViewModel.searchFilter(srch)
                                },
                                hint = stringResource(id = R.string.search_here)
                            )
                        }
                        val filteredItems =
                            mainActivityViewModel.bottomSheetCurrencyFilter.collectAsState().value
                        GenericListViewRenderer(
                            filteredItems,
                            loadComplete = currencyRatesResult.value.isLoaded,
                            emptyStateValue = stringResource(
                                id = R.string.no_items
                            )
                        ) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(spacing6dp)
                            ) {
                                items(filteredItems) { cur ->
                                    CurrencyNameViewItem(currency = cur) { baseSelectedCurrency ->
                                        mainActivityViewModel.setBaseCurrency(baseSelectedCurrency)
                                        mainActivityViewModel.calculateConversion(if (currencyText.text.isNotEmpty()) parseAmount(currencyText.text).toDouble() else 0.0)
                                        hideBottomSheet()
                                        keyboardController?.hide()
                                    }
                                }
                            }
                        }

                    }
                }
            } else {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(smallPadding), contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            spacing12dp
                        )
                    ) {
                        val flag = currentCurrency?.code?.dropLast(1)?.let { localeToEmoji(it) }
                        MediumTextBold(text = "( $flag )  ${currentCurrency?.name}")
                        TextButton(onClick = {
                            mainActivityViewModel.setBaseCurrency(currentCurrency!!)
                            mainActivityViewModel.calculateConversion(if (currencyText.text.isNotEmpty()) currencyText.text.toDouble() else 0.0)
                            hideBottomSheet()
                        }) {
                            MediumTextBold(text = stringResource(id = R.string.set_as_base), color = MaterialTheme.colors.onSurface)
                        }
                    }
                }
            }

        },
        sheetState = modalBottomSheetState
    ) {
        val context = LocalContext.current
        Scaffold(
            topBar = {
                LargeBoldText(
                    text = stringResource(id = R.string.app_name), modifier = Modifier.padding(
                        largePadding
                    ), fontStyle = FontStyle.Italic
                )
            },
            modifier = Modifier.fillMaxSize()
        ) { pd ->

            pd
            /** @MainContent */

            val base by mainActivityViewModel.baseCurrency.collectAsState()

            Column(modifier = Modifier.fillMaxSize()) {

                Column(
                    Modifier.padding(smallPadding), verticalArrangement = Arrangement.spacedBy(
                        mediumPadding
                    )
                ) {

                    /** @TextFiled_For_Currency_Amount */

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = currencyText,
                            onValueChange = {
                                            newTxt ->
                                val value = if (newTxt.text.isNotEmpty()) newTxt.copy(newTxt.text.toLocalCurrency(),
                                    TextRange(newTxt.text.length + 1)
                                ) else newTxt.copy("")
                                currencyText = value
                                mainActivityViewModel.calculateConversion(
                                    if (currencyText.text.trim().isNotEmpty()) parseAmount(newTxt.text) else 0.0
                                )

                            },
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.End, fontWeight = FontWeight.Medium, fontSize = 20.sp
                            ),
                            label = {
                                Text(
                                    "", color = MaterialTheme.colors.onSurface, modifier = Modifier.align(
                                        Alignment.CenterEnd
                                    )
                                )
                            },
                            placeholder = {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        stringResource(
                                            id = R.string.enter_amount,
                                            base.code ?: ""
                                        ), color = MaterialTheme.colors.onSurface, modifier = Modifier.align(
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


                 /*   OutLineEdittextNumber(
                        hint = stringResource(
                            id = R.string.enter_amount,
                            base.code ?: ""
                        ), text = currencyText
                    ) { newTxt ->

                    }*/

                    val baseUnitForPreview = BaseUnitForPreview(
                        base.code,
                        currencyRatesResult.value.result?.rates?.firstOrNull { it.code == base.code }?.name
                            ?: stringResource(id = R.string.default_base)
                    ) {
                        // OnClick
                        bottomSheetView = BottomSheetScreen.CHOOSE_BASE
                        showBottomSheet()

                    }
                    CurrencyButtonView(baseUnitForPreview)

                    GenericListViewRenderer(
                        currencyRatesResult.value.result?.rates,
                        loadComplete = currencyRatesResult.value.isLoaded
                    ) {
                        // Conversion goes here
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(
                                mediumPadding
                            ),
                            columns = GridCells.Fixed(GRID_COLUMN_COUNT)
                        ) {
                            items(currencyRatesResult.value.result!!.rates) { currency ->
                                CurrencyItem(currency = currency){cur ->
                                    currentCurrency = cur
                                    bottomSheetView = BottomSheetScreen.VIEW_SELECTED
                                    showBottomSheet()

                                }
                            }
                        }
                    }


                }
            }


        }

    }


}


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun MainHomeScreenPreview() {


    BottomSheetScaffold(topBar = {
        MediumTextBold(
            text = stringResource(id = R.string.app_name), modifier = Modifier.padding(
                largePadding
            ), fontStyle = FontStyle.Italic
        )
    }, sheetContent = {
        /** @SheetContent */
        Box(Modifier.fillMaxWidth()) {

        }

    }) {
        /** @MainContent */
        Column(modifier = Modifier.fillMaxSize()) {
            /** @TextView_For_Currency */
            Column(
                Modifier.padding(smallPadding), verticalArrangement = Arrangement.spacedBy(
                    mediumPadding
                )
            ) {
                OutLineEdittextNumberPreview()
                val baseUnitForPreview = BaseUnitForPreview("US", "United States Dollar") {
                    // OnClick
                }
                CurrencyButtonView(baseUnitForPreview)
                // Conversion go here
                LazyColumn() {

                }


            }


        }
    }
}

@Composable
@Preview
fun CurrencyButtonView(@PreviewParameter(BaseUnitPreviewProvider::class) baseUnitForPreview: BaseUnitForPreview) {
    Card(
        Modifier
            .fillMaxWidth()
            .clickable { baseUnitForPreview.onClick() },
        backgroundColor = colorPrimaryVariant
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(largePadding),
            horizontalArrangement = Arrangement.spacedBy(spacing6dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "", tint = textDark)
            MediumTextBold(
                text = stringResource(
                    id = R.string.current_currency,
                    localeToEmoji(baseUnitForPreview.base.dropLast(1)),
                    baseUnitForPreview.name
                ), color = textDark, textAlign = TextAlign.Center, modifier = Modifier.weight(1f)
            )
        }
    }

}

@Composable
fun CurrencyItem(currency: Currency,onClick: (Currency) -> Unit) {

    Box(
        Modifier
            .padding(smallPadding)
            .clickable {
                onClick(currency)
            }
            .border(
                size1dot5dp,
                gray,
                RoundedCornerShape(size6dp)
            )
    ) {
        Column(
            Modifier.padding(horizontal = largePadding, vertical = smallPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                spacing6dp
            )
        ) {
            MediumTextBold(text = currency.conversion.to2Dp(), fontWeight = FontWeight.SemiBold)
            MediumTextBold(
                text = stringResource(
                    id = R.string.currency_with_logo,
                    currency.code,
                    localeToEmoji(currency.code.dropLast(1))
                ), maxLines = 1
            )
        }
    }

}

@Composable
fun CurrencyNameViewItem(currency: Currency, onClick: (Currency) -> Unit) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing6dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = spacing4dp, horizontal = mediumPadding)
            .clickable { onClick(currency) }
    ) {
        MediumTextBold(
            text = stringResource(
                id = R.string.currency_with_logo,
                currency.code,
                localeToEmoji(currency.code.dropLast(1))
            ), maxLines = 1
        )
        MediumTextBold(text = currency.name)
    }


}




