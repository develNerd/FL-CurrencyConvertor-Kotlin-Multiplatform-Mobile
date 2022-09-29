package org.flepper.currencyconvertor.android.presentation.preview_providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.flepper.currencyconvertor.data.Currency

class BaseUnitPreviewProvider: PreviewParameterProvider<BaseUnitForPreview> {
    override val values= sequenceOf(BaseUnitForPreview(base = "USD","United States Dollar") {})
}

data class BaseUnitForPreview(val base:String,val name:String, val onClick: () -> Unit)

class CurrencyPreviewProvider:PreviewParameterProvider<Currency>{
    override val values: Sequence<Currency>
        get() = sequenceOf(Currency("AED",3.672988,"United Arab Emirates Dirham"))
}
