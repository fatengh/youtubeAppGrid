package com.example.youtubeapp

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MainActivity : AppCompatActivity() {
    private val videos: Array<Array<String>> = arrayOf(
        arrayOf("Numbers Game", "89hy6ACuwT0"),
        arrayOf("Calculator", "RQjQddpna04"),
        arrayOf("Guess the Phrase", "I3fHWwHb_AU")
    )
    private lateinit var youTubeView: YouTubePlayerView
    private lateinit var play: YouTubePlayer
    private lateinit var recyclerView: RecyclerView
    private var vid = 0
    private var time = 0f

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            youTubeView = findViewById(R.id.ytPlay)
            //recyclerView = findViewById(R.id.rvVid)


            interntconnect()

            youTubeView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    play = youTubePlayer
                    play.loadVideo(videos[vid][1], time)
                    vidrv()
                }
            })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            youTubeView.enterFullScreen()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            youTubeView.exitFullScreen()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("currentVideo", vid)
        outState.putFloat("timeStamp", time)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        vid= savedInstanceState.getInt("currentVideo", 0)
        time = savedInstanceState.getFloat("timeStamp", 0f)
    }



    private fun vidrv(){

        val gridView: GridView = findViewById(R.id.rvVid)
        gridView.adapter = VidGridAdap(videos, play,this@MainActivity)

    }
   /* private fun vidrv(recyclerView: RecyclerView){
        recyclerView.adapter = vidAdap(videos, play)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

    }*/

    private fun interntconnect(){
        if(!connectedOrNot()){
            AlertDialog.Builder(this)
                .setTitle("Internet Not Connect")
                .setPositiveButton("RETRY"){_, _ -> interntconnect()}
                .setNegativeButton("Cancle"){dilog,id -> dilog.cancel()}
                .show()
        }
    }


    private fun connectedOrNot(): Boolean{
        val ii = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = ii.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}