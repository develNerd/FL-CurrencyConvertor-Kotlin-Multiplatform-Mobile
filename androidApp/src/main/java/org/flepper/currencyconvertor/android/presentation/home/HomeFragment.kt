package org.flepper.currencyconvertor.android.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import org.flepper.currencyconvertor.android.MainActivityViewModel
import org.flepper.currencyconvertor.android.databinding.FragmentHomeBinding
import org.flepper.currencyconvertor.android.presentation.core.BaseFragment
import org.flepper.currencyconvertor.android.theme.PayPayAppTheme


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    val mainActivityViewModel:MainActivityViewModel by activityViewModels()

    override fun initUI() {
        // Implement UI Here
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                PayPayAppTheme() {
                    MainHomeScreen(mainActivityViewModel = mainActivityViewModel)
                }
            }
        }
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
       return FragmentHomeBinding.inflate(inflater,container,false)
    }


}