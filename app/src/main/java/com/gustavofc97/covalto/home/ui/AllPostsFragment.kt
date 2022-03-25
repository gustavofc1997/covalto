package com.gustavofc97.covalto.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gustavofc97.covalto.databinding.FragmentPostListBinding
import com.gustavofc97.covalto.home.ui.adapters.PostItemsAdapter
import com.gustavofc97.covalto.home.viewmodels.PostListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllPostsFragment : Fragment() {

    private val viewModel: PostListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DataBindingUtil.bind<FragmentPostListBinding>(view)?.let {
            setUpObservables(it)
            setUpRecyclerView(it)
        }
    }

    private fun setUpRecyclerView(binding: FragmentPostListBinding){
        binding.rvPostList.adapter = PostItemsAdapter().apply {
            setOnPostClicked { itemSelected ->
                findNavController().navigate(
                    AllPostsFragmentDirections.actionFirstFragmentToSecondFragment(
                        itemSelected
                    )
                )
            }
        }
        binding.rvPostList.layoutManager = LinearLayoutManager(context)
        binding.rvPostList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setUpObservables(binding: FragmentPostListBinding) {
        with(viewModel) {
            loadingEvent.observe(viewLifecycleOwner) {
                binding.pbItemsLoading.isVisible = it
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllPosts()
    }
}