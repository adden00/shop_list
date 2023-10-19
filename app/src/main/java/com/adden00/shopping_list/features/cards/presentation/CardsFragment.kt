package com.adden00.shopping_list.features.cards.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.adden00.shopping_list.app.di.ui.DaggerCardsComponent
import com.adden00.shopping_list.app.getAppComponent
import com.adden00.shopping_list.core.ViewModelFactory
import com.adden00.shopping_list.core.utills.OnClickListener
import com.adden00.shopping_list.databinding.FragmentCardsBinding
import com.adden00.shopping_list.features.cards.presentation.models.CardModelPres
import com.adden00.shopping_list.features.cards.presentation.mvi.CardsEffect
import com.adden00.shopping_list.features.cards.presentation.mvi.CardsEvent
import com.adden00.shopping_list.features.cards.presentation.mvi.CardsState
import com.adden00.shopping_list.features.cards.presentation.utills.addEmptyCard
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

import javax.inject.Inject

class CardsFragment : Fragment() {
    private var _binding: FragmentCardsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: CardsViewModel by viewModels { viewModelFactory }
    private val adapter by lazy {
        CardsAdapter(object : OnClickListener<CardModelPres> {
            override fun onClick(item: CardModelPres) {
                // TODO открываем реальный баркод или qr
            }

            override fun onLongClick(item: CardModelPres) = Unit
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerCardsComponent.factory().create(requireContext().getAppComponent()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUi()
        subscribeOnState()
    }

    private fun subscribeOnState() {
        viewModel.cardsState
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)

        viewModel.cardsEffect
            .flowWithLifecycle(lifecycle)
            .onEach(::handleEffect)
            .launchIn(lifecycleScope)
    }

    private fun render(state: CardsState) {
        binding.pbarCardsLoading.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.tvEmptyList.visibility = if (state.cardsList.isEmpty()) View.VISIBLE else View.GONE
        binding.swipeRefresh.isRefreshing = false
        val addedList = state.cardsList.addEmptyCard()
        Log.d("CardsFragment", addedList.toString())
        adapter.submitList(state.cardsList.addEmptyCard())
    }

    private fun handleEffect(effect: CardsEffect) {
        when (effect) {
            is CardsEffect.InternetError -> Unit
            is CardsEffect.LogOut -> Unit
            is CardsEffect.ShowMessage -> Unit
            is CardsEffect.Waiting -> Unit
        }
    }

    private fun setUi() {
        binding.rcCards.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcCards.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.newEvent(CardsEvent.GetCards)
        }
    }
}
