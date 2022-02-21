package com.android.saveo.ui.movieslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.saveo.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber


@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
               when(uiState){
                   is MoviesStateEvent.MoviesSuccess ->{
                       Timber.e(uiState.data.toString())
                   }
                   else -> {
                       Timber.e("Nothing Called")
                   }
               }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMoviesListData(0,10)
    }

}