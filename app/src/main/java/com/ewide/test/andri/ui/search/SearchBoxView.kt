package com.ewide.test.andri.ui.search

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView.OnEditorActionListener
import com.ewide.test.andri.databinding.SearchViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchBoxView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private val binding = SearchViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var listener: Listener? = null

    init {

        binding.searchBox.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.searchBox.text?.run {
                    listener?.onSearch(toString())
                }
                return@OnEditorActionListener true
            }
            false
        })

        binding.searchBox.apply {
            setOnClickListener { binding.searchBox.text?.clear() }
            addTextChangedListener(Watcher { listener?.onSearch(it) })
        }
    }

    fun onSearch(listener: Listener) {
        this.listener = listener
    }

    inner class Watcher(val input: (String) -> Unit) : TextWatcher {
        private var lastInput = ""
        private var debounceJob: Job? = null
        private val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (s.toString().trim { it <= ' ' }.isEmpty()) {
                binding.clearButton.visibility = GONE
            } else {
                binding.clearButton.visibility = VISIBLE
            }
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // No-op
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                val newtInput = s.toString()
                debounceJob?.cancel()
                if (lastInput != newtInput) {
                    lastInput = newtInput
                    debounceJob = uiScope.launch {
                        delay(500)
                        if (lastInput == newtInput && newtInput.isNotBlank() && newtInput.isNotBlank()) {
                            input(newtInput)
                        }
                    }
                }
            }
        }
    }

    interface Listener {
        fun onSearch(keyword: String)
    }
}