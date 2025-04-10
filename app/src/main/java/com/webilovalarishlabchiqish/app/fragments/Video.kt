package com.webilovalarishlabchiqish.app.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webilovalarishlabchiqish.app.VideoData
import com.webilovalarishlabchiqish.app.adapters.VideoAdapter
import com.webilovalarishlabchiqish.application.R
import com.webilovalarishlabchiqish.application.databinding.FragmentVideoBinding

class Video : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VideoAdapter
    private lateinit var binding: FragmentVideoBinding
    private lateinit var fragmentContext:Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_video, container, false)
        binding = FragmentVideoBinding.bind(view)

        val videoList = listOf(
            VideoData(getString(R.string.html_asoslari), "3JluqTojuME"),
            VideoData(getString(R.string.css_asoslari), "UB1O30fR-EE"),
            VideoData(getString(R.string.javascript_boshlang_ich), "1Rs2ND1ryYc"),
            VideoData(getString(R.string.responsive_dizayn_nima), "srvUrASNj0s"),
            VideoData(getString(R.string.flexbox_to_liq), "JJSoEo8JSnc"),
            VideoData(getString(R.string.grid_layout_asoslari), "rg7Fvvl3taU"),
            VideoData(getString(R.string.javascript_dom), "0ik6X4DJKCc"),
            VideoData(getString(R.string.bootstrap_5), "4sosXZsdy-s"),
            VideoData(getString(R.string.javascript_bilan_mini_project_calculator), "j59qQ7YWLxw"),
            VideoData(getString(R.string.git_github_asoslari), "RGOj5yH7evk"),
            VideoData(getString(R.string.web_hosting_va_server), "0h2b4ftbZcU"),
            VideoData(getString(R.string.react_js_asoslari), "DLX62G4lc44"),
            VideoData(getString(R.string.vue_js_asoslari), "Wy9q22isx3U"),
            VideoData(getString(R.string.angular_asoslari), "k5E2AVpwsko")
        )


        recyclerView = binding.videoRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(fragmentContext)
        adapter = VideoAdapter(videoList)
        recyclerView.adapter = adapter

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

}