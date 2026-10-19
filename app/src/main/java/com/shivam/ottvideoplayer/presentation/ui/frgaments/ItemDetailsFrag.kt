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
import com.shivam.ottvideoplayer.databinding.FragmentItemDetailsBinding
import com.shivam.ottvideoplayer.domain.domain_model.DomainItem
import com.shivam.ottvideoplayer.presentation.ui.utils.MyViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ItemDetailsFrag : Fragment() {

    private val viewModel: MyViewModel by activityViewModels()
    private lateinit var binding: FragmentItemDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSelectedItem()
    }

    private fun observeSelectedItem() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedItem.collect { item ->
                if (item != null) {
                    setupCard(item)
                }
            }
        }
    }

    private fun setupCard(item: DomainItem) {
        Picasso.get()
            .load(item.thumbnailUrl)
            .placeholder(R.drawable.funny_cartoon)
            .error(R.drawable.funny_cartoon)
            .into(binding.ivSelectedItem)

        binding.tvShowTitle.text = item.title
        binding.tvShowDescrip.text = item.description

        binding.watchBtn.setOnClickListener {
            findNavController().navigate(R.id.action_itemDetailsFrag_to_videoPlayerFrag)
        }
    }
}
