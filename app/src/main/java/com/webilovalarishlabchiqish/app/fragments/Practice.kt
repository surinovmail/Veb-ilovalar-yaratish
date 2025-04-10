package com.webilovalarishlabchiqish.app.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.webilovalarishlabchiqish.application.databinding.FragmentPracticeBinding

class PracticeFragment : Fragment() {

    private var _binding: FragmentPracticeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPracticeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.webViewClient = WebViewClient()


        binding.htmlEditor.setText("<html><body><h1>Hello World</h1></body></html>")


        binding.btnRun.setOnClickListener {
            val htmlCode = binding.htmlEditor.text.toString()
            val formattedHtml = "<html><body>$htmlCode</body></html>"
            binding.webView.loadData(formattedHtml, "text/html", "UTF-8")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
