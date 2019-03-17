package com.desiatko.countries.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.desiatko.countries.presentation.R
import com.desiatko.countries.presentation.util.id
import com.desiatko.countries.presentation.view.LoadingContentView

abstract class LoadingContentFragment : Fragment(), LoadingContentView {

    @get:LayoutRes
    abstract val contentLayoutResource: Int

    private lateinit var viewHolder: ViewHolder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(activity).inflate(R.layout.layout_loading_view, container, false)
        val stub by view.id<ViewStub>(R.id.content_stub)
        stub.layoutResource = contentLayoutResource
        stub.inflate()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewHolder = ViewHolder(
            content = view.findViewById(R.id.content),
            loading = view.findViewById(R.id.loading),
            error = view.findViewById(R.id.error)
        )
        onContentCreated(viewHolder.content)
    }

    protected open fun onContentCreated(content: View) {}

    private fun showView(@IdRes id: Int) {
        viewHolder.all.forEach { it.isVisible = it.id == id }
    }

    override fun showLoading() {
        showView(R.id.loading)
    }

    override fun showContent() {
        showView(R.id.content)
    }

    override fun showError(message: CharSequence) {
        showView(R.id.error)
        viewHolder.error.text = message
    }

    class ViewHolder(
        val content: View,
        val loading: View,
        val error: TextView
    ) {
        val all = arrayOf(content, loading, error)
    }
}