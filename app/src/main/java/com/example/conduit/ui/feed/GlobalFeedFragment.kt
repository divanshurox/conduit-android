package com.example.conduit.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.conduit.R
import com.example.conduit.databinding.FragmentFeedBinding

class GlobalFeedFragment: Fragment() {
    private val feedViewModel: FeedViewModel by viewModels()
    private lateinit var feedAdapter: FeedAdapter

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        feedAdapter = FeedAdapter(object: FeedAdapter.OnArticleClickedListener{
            override fun onArticleClicked() = openArticle()

        })
        _binding?.apply {
            viewModel = feedViewModel
            feedList.layoutManager = LinearLayoutManager(context)
            feedList.adapter = feedAdapter
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedViewModel.fetchGlobalFeed()
    }

    fun openArticle(){
        findNavController().navigate(R.id.action_nav_global_feed_to_nav_article)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}