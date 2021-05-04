package com.example.sqlitebiteinkotlin

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqlitebiteinkotlin.databinding.ActivityInsertBinding.bind
import com.example.sqlitebiteinkotlin.databinding.ActivityInsertBinding.inflate
import com.example.sqlitebiteinkotlin.databinding.ActivityYoutubeBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class YoutubeActivity : YouTubeBaseActivity() {
    lateinit var binding: ActivityYoutubeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val youTube= binding.youTubePlayerView
        youTube.initialize("AIzaSyBpCt4YmFGNNECF4D1TZWbrMyNr0ldNvhk",object: YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                if(p1==null){
                    return
                }
                if(p2){
                    p1.play()
                }
                else{
                    p1.cueVideo("_vQooqEQJWg")
                    p1.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                }
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {

            }

        })

    }
}