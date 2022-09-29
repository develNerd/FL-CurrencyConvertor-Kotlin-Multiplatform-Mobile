package org.flepper.currencyconvertor.android.presentation.widgets

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.flepper.therapeutic.android.presentation.widgets.GenericCircleProgressIndicator
import org.flepper.currencyconvertor.android.R

@Composable
fun <T> GenericListViewRenderer(list:List<T>? = null, loadComplete:Boolean, isError:Boolean = false, emptyStateValue:String = stringResource(id =  R.string.something_went_wrong), content: @Composable () -> Unit){
    Log.e("loadComplete", loadComplete.toString())
    Crossfade(targetState = list){ retrievedList ->
        when{
            isError -> {

            }
            retrievedList == null  -> {
                GenericCircleProgressIndicator()
            }
            loadComplete && retrievedList!!.isEmpty()  -> {
                EmptyStateText(text = emptyStateValue)
            }
            loadComplete -> {
                content()
            }

            //loadComplete and List is not Empty

        }
    }

}

@Composable
fun EmptyStateText(text:String){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(verticalArrangement = Arrangement.spacedBy(mediumPadding)) {

            RegularText(
                text = text,
                color = MaterialTheme.colors.onBackground,
                size = large_bold_text_size
            )
        }

    }
}