package com.android.saveo.ui.movieslist

import android.gesture.GestureOverlayView.ORIENTATION_HORIZONTAL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.android.saveo.R
import com.android.saveo.databinding.FragmentMoviesBinding
import com.android.saveo.models.MoviesModel
import com.android.saveo.ui.details.DetailsFragmentDirections
import com.android.saveo.utils.CustomAdapter
import com.android.saveo.utils.OnItemClickListener
import com.android.saveo.utils.SpacesItemDecoration
import com.android.saveo.utils.ViewType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber


@AndroidEntryPoint
class MoviesFragment : Fragment(), OnItemClickListener<MoviesModel> {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var customAdapter: CustomAdapter<ViewType<*>>
    private lateinit var customAdapterPager: CustomAdapter<ViewType<*>>
    private var list = mutableListOf<ViewType<*>>()
    private var listPager = mutableListOf<ViewType<*>>()
    private var count = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenResumed {
            viewModel.uiState.collect { uiState ->
               when(uiState){
                   is MoviesStateEvent.ShowProgress ->{
                       Timber.e("Nothing ShowProgress")
                   }
                   is MoviesStateEvent.HideProgress ->{
                       Timber.e("Nothing HideProgress")
                   }
                   is MoviesStateEvent.Error ->{
                       Timber.e("Nothing Error")
                   }
                   is MoviesStateEvent.MoviesSuccess ->{
                       Timber.e(uiState.data.toString())
                       list = uiState.data.map { movies->
                           MoviesItemView(movies)
                       }.toMutableList()
                       customAdapter.setList(list)
                       listPager = uiState.data.take(5).map {pager->
                           MoviesAdapterItemView(pager)
                       }.toMutableList()
                        customAdapterPager.setList(listPager)
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
        if (viewModel.uiState.value == MoviesStateEvent.Empty) {
            viewModel.getMoviesListData(1, 15)
        }
        customAdapter = CustomAdapter(list.toMutableList(),this)
        binding.rcvGrid.layoutManager =
            GridLayoutManager(requireContext(), 3)
        binding.rcvGrid.addItemDecoration(SpacesItemDecoration(20))
        binding.rcvGrid.adapter = customAdapter
        binding.nestScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v!!.getChildAt(0).height - v.height) {
                count++
                viewModel.getMoviesListData(count,15)
            }
        })
        customAdapterPager = CustomAdapter(listPager.toMutableList(),this)
        binding.viewPager2.adapter = customAdapterPager
        with(binding.viewPager2) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }
        binding.viewPager2.clipToPadding = false;
        binding.viewPager2.setPadding(48, 0, 48, 0);
        //binding.viewPager2.ma(24);
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)
        binding.viewPager2.setPageTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }
    }

    override fun onItemClicked(position: Int) {

    }

    override fun onItemClicked(position: Int, item: MoviesModel) {
        val action = DetailsFragmentDirections.goToDetailsFragment(item)
        binding.root.findNavController().navigate(action)
    }

}