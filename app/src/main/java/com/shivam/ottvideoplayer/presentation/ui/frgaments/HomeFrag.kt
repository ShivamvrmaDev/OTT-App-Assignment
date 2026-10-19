package com.shivam.ottvideoplayer.presentation.ui.frgaments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shivam.ottvideoplayer.R
import com.shivam.ottvideoplayer.databinding.FragmentHomeBinding
import com.shivam.ottvideoplayer.domain.domain_model.DomainItem
import com.shivam.ottvideoplayer.domain.domain_model.DomainOTTDataModel
import com.shivam.ottvideoplayer.presentation.common.UiState
import com.shivam.ottvideoplayer.presentation.ui.utils.DataAdapter
import com.shivam.ottvideoplayer.presentation.ui.utils.MyViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: MyViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var trendingAdapter: DataAdapter
    private lateinit var recommendedAdapter: DataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.pbLoading.visibility = View.VISIBLE
                        binding.tvErrorMessage.visibility = View.GONE
                    }
                    is UiState.Success -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.tvErrorMessage.visibility = View.GONE
                        populateHomeContent(state.data)
                    }
                    is UiState.Error -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.tvErrorMessage.visibility = View.VISIBLE
                        binding.tvErrorMessage.text = state.message
                    }
                    is UiState.Empty -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.tvErrorMessage.visibility = View.VISIBLE
                        binding.tvErrorMessage.text = getString(R.string.no_content_available)
                    }
                }
            }
        }
    }

    private fun populateHomeContent(data: DomainOTTDataModel) {
        setupHeroBanner(data.mainCard)

        data.categoriesData.forEach { category ->
            when (category.name) {
                "Trending Now" -> {
                    binding.trendingCard.listTopTitle.text = category.name
                    trendingAdapter.submitList(category.items)
                }
                "Recommended for You" -> {
                    binding.otherContentCard.listTopTitle.text = category.name
                    recommendedAdapter.submitList(category.items)
                }
                else -> {
                    binding.otherContentCard.listTopTitle.text = category.name
                    recommendedAdapter.submitList(category.items)
                }
            }
        }
    }

    private fun setupHeroBanner(item: DomainItem) {
        Picasso.get()
            .load(item.bannerUrl)
            .placeholder(R.drawable.funny_cartoon)
            .error(R.drawable.funny_cartoon)
            .into(binding.heroCard)

        binding.tvHeroCardTitle.text = item.title
        binding.tvHeroSubtitle.text = item.description

        binding.btnWatchNow.setOnClickListener {
            viewModel.setSelectedItem(item)
            navigateToNextItem()
        }

        binding.heroCard.setOnClickListener {
            viewModel.setSelectedItem(item)
            navigateToNextItem()
        }
    }

    private fun setupAdapters() {
        trendingAdapter = DataAdapter(
            onClick = { item ->
                viewModel.setSelectedItem(item)
                navigateToNextItem()
            },
            context = requireContext()
        )
        binding.trendingCard.itemList.adapter = trendingAdapter

        recommendedAdapter = DataAdapter(
            onClick = { item ->
                viewModel.setSelectedItem(item)
                navigateToNextItem()
            },
            context = requireContext()
        )
        binding.otherContentCard.itemList.adapter = recommendedAdapter
    }

    private fun navigateToNextItem() {
        findNavController().navigate(R.id.action_homeFragment_to_itemDetailsFrag)
    }
}
