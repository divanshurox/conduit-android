package com.example.conduit.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.conduit.R
import com.example.conduit.databinding.FragmentArticleBinding
import com.example.conduit.extensions.showFormattedDate

class ArticleFragment: Fragment() {
    private val articleViewModel: ArticleViewModel by viewModels()
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private var slug: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater,container,false)
        arguments?.let{
            slug = it.getString(R.string.slug.toString()).toString()
        }
        slug?.let{
            articleViewModel.getArticle(it)
        }
        _binding?.apply {
            viewModel = articleViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        articleViewModel.article.observe({lifecycle}) {
            (activity as AppCompatActivity).supportActionBar?.title = it?.title.toString()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleViewModel.article.observe({lifecycle}) {
            _binding?.apply{
                createdAtTextView.showFormattedDate(it.createdAt)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

