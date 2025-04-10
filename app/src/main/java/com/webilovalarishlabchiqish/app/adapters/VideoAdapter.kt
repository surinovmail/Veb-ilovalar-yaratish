package com.webilovalarishlabchiqish.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.webilovalarishlabchiqish.app.VideoData
import com.webilovalarishlabchiqish.application.R

class VideoAdapter(private val videos: List<VideoData>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val webView: WebView = itemView.findViewById(R.id.webView)
        val title: TextView = itemView.findViewById(R.id.videoTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false)
        return VideoViewHolder(view)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        holder.title.text = video.title

        holder.webView.settings.javaScriptEnabled = true
        holder.webView.webChromeClient = WebChromeClient()

        val html = """
            <iframe width="100%" height="100%" 
                src="https://www.youtube.com/embed/${video.videoId}" 
                frameborder="0" allowfullscreen></iframe>
        """.trimIndent()

        holder.webView.loadData(html, "text/html", "utf-8")
    }

    override fun getItemCount(): Int = videos.size
}

