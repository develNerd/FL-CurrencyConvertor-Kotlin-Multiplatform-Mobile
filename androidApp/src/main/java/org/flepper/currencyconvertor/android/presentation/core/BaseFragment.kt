package org.flepper.currencyconvertor.android.presentation.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

/**
 * Abstract BaseFragment for other fragments to extend.
 * */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    val binding: VB
        get() = _binding!!
    lateinit var supportFragmentManager: FragmentManager

    private var _binding: VB? = null

    // will be called in onViewCreated in child class i.e fragment
    abstract fun initUI()

    /**
     * Get view binding for current fragment and use on onCreateView Method
     * override this method in all fragments to return the current view binding
     * */
    abstract fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = initViewBinding(inflater, container)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportFragmentManager =  requireActivity().supportFragmentManager
        initUI()
    }

    override fun onDestroy() {
        //set binding to null on fragment destroy
        _binding = null
        super.onDestroy()
    }

    /**
     * @param actionId represents navGraph id or destination
     * @param args for passing data between fragments with navGraph
     * */
    protected open fun navigate(@IdRes actionId: Int, args: Bundle? = null) {
        findNavController().navigate(actionId, args)
    }



}