package com.adarsh.dynamicui.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.adarsh.dynamicui.R
import com.adarsh.dynamicui.databinding.FragmentMainBinding
import com.adarsh.dynamicui.model.Card
import com.adarsh.dynamicui.model.CardGroup
import com.adarsh.dynamicui.model.UiResponse
import com.adarsh.dynamicui.util.DataState
import com.adarsh.dynamicui.util.PrefsHelper
import com.adarsh.dynamicui.view.adapter.CardsGroupAdapter
import com.adarsh.dynamicui.viewmodel.MainViewModel
import java.io.BufferedReader
import java.io.InputStreamReader

class MainFragment : Fragment(R.layout.fragment_main), CardsGroupAdapter.InteractionListener,
    SwipeRefreshLayout.OnRefreshListener {
    companion object {
        private const val TAG = "MainFragment"
    }

    private lateinit var uiResponse: UiResponse

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var binding: FragmentMainBinding
    private val adapter by lazy {
        CardsGroupAdapter().apply {
            interactionListener = this@MainFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)
        binding.root.setOnRefreshListener(this)

        subscribeToViewModel()
        viewModel.setStateEvent(MainViewModel.UiStateEvent.FetchLocal)

        binding.mainRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = this@MainFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun subscribeToViewModel() {
        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    hideLoading()
                    hideError()
                    uiResponse = it.data
                    adapter.submitList(uiResponse.cardGroups)
                }
                is DataState.Failure -> {
                    hideLoading()
                    showError(it.exception)
                }
                DataState.Loading -> {
                    showLoading()
                    hideError()
                }
            }
        }
    }

    private fun hideLoading() {
        binding.root.isRefreshing = false
    }

    private fun showLoading() {
        binding.root.isRefreshing = true
    }

    private fun showError(e: Exception) {
        binding.mainRecyclerView.visibility = View.GONE
        binding.tvError.visibility = View.VISIBLE
    }
    private fun hideError() {
        binding.mainRecyclerView.visibility = View.VISIBLE
        binding.tvError.visibility = View.GONE
    }

    override fun onRemindLater(position: Int, card: Card) {
        val group = with(uiResponse.cardGroups[position]) {
            CardGroup(
                name,
                cards.toMutableList(), // To avoid using old list of cards
                designType,
                height,
                isScrollable
            )
        }
        group.cards.remove(card)
        uiResponse.cardGroups.removeAt(position)
        uiResponse.cardGroups.add(position, group)
        adapter.submitList(uiResponse.cardGroups)
    }

    override fun onDismissCard(position: Int, card: Card) {
        onRemindLater(position, card)
        card.name?.let { PrefsHelper(requireContext()).putDismissedCard(it) }
    }

    override fun onRefresh() {
        viewModel.setStateEvent(MainViewModel.UiStateEvent.FetchLocal)
    }
}