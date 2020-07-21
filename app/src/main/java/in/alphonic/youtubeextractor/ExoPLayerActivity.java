package in.alphonic.youtubeextractor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

public class ExoPLayerActivity extends AppCompatActivity {

    private Context context;
    private PlayerView playerView;
    private SimpleExoPlayer player;

    private String urlAudio = "https://r2---sn-gwpa-w5pe.googlevideo.com/videoplayback?expire=1595344200&ei=6LAWX_ujJK6CssUPn_23uA0&ip=2409%3A4052%3A909%3A553c%3Ae8f6%3Ab542%3Aa183%3Ae696&id=o-AJ1KEgFeCcNQuMDbydoDijNq0wywdM_ow29U7Lrsz_gS&itag=251&source=youtube&requiressl=yes&mh=Vn&mm=31%2C29&mn=sn-gwpa-w5pe%2Csn-gwpa-qxa6&ms=au%2Crdu&mv=m&mvi=2&pcm2cms=yes&pl=36&initcwndbps=142500&vprv=1&mime=audio%2Fwebm&gir=yes&clen=181965212&otfp=1&dur=14001.141&lmt=1594312667853155&mt=1595322463&fvip=2&keepalive=yes&fexp=23883097&c=WEB&txp=6211222&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cgir%2Cclen%2Cotfp%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAM-5mAib-baIHgUU6unRZ-NQTb5X_M2QCbLGDpxgyXhnAiBBd4i8tmbwzuSfvWSfkEVAMv14PSFi0q_JfFA1jk61Dg%3D%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpcm2cms%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIgQUTXfMvplyhvH3n-tqc3VMr5xj3dHpOynqvnvgmQE_MCIQC3Eaiu_ejjuunzceKNqGBvvDTBni6GtViaRvPUgAHEfg%3D%3D";
  //  private String urlVideo = "https://r2---sn-gwpa-w5pe.googlevideo.com/videoplayback?expire=1595344200&ei=6LAWX_ujJK6CssUPn_23uA0&ip=2409%3A4052%3A909%3A553c%3Ae8f6%3Ab542%3Aa183%3Ae696&id=o-AJ1KEgFeCcNQuMDbydoDijNq0wywdM_ow29U7Lrsz_gS&itag=134&aitags=133%2C134%2C135%2C136%2C137%2C160%2C242%2C243%2C244%2C247%2C248%2C278&source=youtube&requiressl=yes&mh=Vn&mm=31%2C29&mn=sn-gwpa-w5pe%2Csn-gwpa-qxa6&ms=au%2Crdu&mv=m&mvi=2&pcm2cms=yes&pl=36&initcwndbps=142500&vprv=1&mime=video%2Fmp4&gir=yes&clen=509858251&otfp=1&dur=14001.120&lmt=1594347602058720&mt=1595322463&fvip=2&keepalive=yes&fexp=23883097&c=WEB&sparams=expire%2Cei%2Cip%2Cid%2Caitags%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cgir%2Cclen%2Cotfp%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAI_JepVoVa6v7hRhWhcLuW_NbbzJ-Rfgi8ZKGKRzY8ojAiAEfGZbMpUqZ9gzdWllEBn3KtCnN396_0va8bW4NIg7Iw%3D%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpcm2cms%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIgQUTXfMvplyhvH3n-tqc3VMr5xj3dHpOynqvnvgmQE_MCIQC3Eaiu_ejjuunzceKNqGBvvDTBni6GtViaRvPUgAHEfg%3D%3D";

    private String urlVideo = "https://r2---sn-gwpa-w5pe.googlevideo.com/videoplayback?expire=1595345557&ei=NbYWX7nlIOrj4-EPwvebsAk&ip=2409%3A4052%3A909%3A553c%3Ae8f6%3Ab542%3Aa183%3Ae696&id=o-ANFXQ4_SfnG6CHp1c_B6JZbHJFrtNKSNWg8lxMZv2EXu&itag=18&source=youtube&requiressl=yes&mh=Vn&mm=31%2C29&mn=sn-gwpa-w5pe%2Csn-gwpa-qxa6&ms=au%2Crdu&mv=m&mvi=2&pl=36&initcwndbps=143750&vprv=1&mime=video%2Fmp4&gir=yes&clen=798125279&ratebypass=yes&dur=14001.168&lmt=1594310485874660&mt=1595323804&fvip=2&fexp=23883097&c=WEB&txp=6216222&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRAIge5K-Q8InabCw8lAvHje9hqOl2FgRNeXyXbV-JiZ-0bkCIAUVFTO4zQj6rM1Ee-fdvypRJm3zFXsnH1ICfojBP1WT&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRgIhANe7nAbPmaUIrbij-sAq276Jq_6k0P068POS7uPxuzYYAiEA1pW5Ouk_X6FvBtmwwKNTRGJzELiBggYPAVn1Jo8m5Sg%3D";

   private String defVideo, defAudio;
   private boolean isVideoContainsAudio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_p_layer);

        getIntent(getIntent());

        playerView = findViewById(R.id.video_view);

        context = this;

         player = new SimpleExoPlayer.Builder(context).build();

        playerView.setPlayer(player);

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "yourApplicationName"));
// This is the MediaSource representing the media to be played.
        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(urlAudio));
// Prepare the player with the source.
//        player.prepare(videoSource);

     //   player.prepare(mergerMediaSource(urlVideo, urlAudio));

        mergerMediaSource(urlVideo, urlAudio);
    }

    private void mergerMediaSource(String url1, String url2) {
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "yourApplicationName"));


        if (defVideo == null) { // // play audio only
            MediaSource audiosource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(defAudio));
       player.prepare(audiosource);
        } else if (isVideoContainsAudio) { // play only video with single url
            MediaSource audiosource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(defVideo));
            player.prepare(audiosource);
        } else { // play video with two url audio and video

            MediaSource audiosource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(defVideo));
            MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(defAudio));
            player.prepare(new MergingMediaSource(videoSource, audiosource), true, false);
        }
        /*
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "yourApplicationName"));

        MediaSource firstSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url1));
        MediaSource secondSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url2));
// Plays the first video, then the second video.
        ConcatenatingMediaSource concatenatedSource =
                new ConcatenatingMediaSource(firstSource, secondSource);

        return concatenatedSource;*/
    }

    private void getIntent(Intent intent) {
        if (intent != null) {
            defVideo = intent.getStringExtra("defVideo");
            defAudio = intent.getStringExtra("defAudio");
            isVideoContainsAudio = intent.getBooleanExtra("isVideoContainsAudio", false);


            System.out.println("DefVideo " + defVideo);
            System.out.println("defAudio " + defAudio);
            System.out.println("isVideoContainsAudio " + isVideoContainsAudio);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        player.stop();
        player.release();
    }
}
