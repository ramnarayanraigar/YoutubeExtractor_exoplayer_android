package in.alphonic.youtubeextractor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    TextView textView;
    private String s = "";
    private EditText editUrl;

    private String defVideo, defAudio;
    private boolean isVideoContainsAudio;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        editUrl = findViewById(R.id.edit_enter_url);
        Button btnExtract = findViewById(R.id.extract);

        btnExtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extract(editUrl.getText().toString());

              //  extract("https://www.youtube.com/watch?v=cQZnUeoaEEE");
            }
        });

        Button btnNext = findViewById(R.id.next);
        Button exo = findViewById(R.id.btn_exo);

        exo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExoPLayerActivity.class);
                startActivity(intent);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, YouTubePlayerActivity.class);
                intent.putExtra("youtube_url", editUrl.getText().toString());
                startActivity(intent);
            }
        });

        textView = findViewById(R.id.desc);

        String youtubeLink = "https://www.youtube.com/watch?v=2jQJTppSCck"; // 2jQJTppSCck XQwe30cZffg


/*
        YouTubeUriExtractor ytEx = new YouTubeUriExtractor(this) {
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                if (ytFiles != null) {
                    if (ytFiles != null) {
                        Log.v(TAG, ytFiles.size() + "sizesddfg");
                        for (int i = 0; i < ytFiles.size(); i++) {
                            YtFile ytFile = ytFiles.valueAt(i);

                            if (ytFile != null) {
                                s = s + "\n\n Url   " + ytFile.getUrl() + "\n\n" + Uri.decode(ytFile.getUrl()) + " \n\n " + ytFile.getFormat();
                                Log.v(TAG, "Urls: " + i + " " + ytFile.getUrl());
                                Log.v(TAG, "Formats: " + i + " " + ytFile.getFormat());
                            }

                        }

                        textView.setText(s);
                    }
                }
            }
        };

        ytEx.execute(youtubeLink);*/
    }

    private void extract(String youtubeLink) {
        Log.v(TAG, "Enter url " + youtubeLink);
        new YouTubeExtractor(this) {
            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {

                if (ytFiles != null) {
                    Log.v(TAG, ytFiles.size() + "sizesddfg");
                    for (int i = 0; i < ytFiles.size(); i++) {
                        YtFile ytFile = ytFiles.valueAt(i);

                        if (ytFile != null) {
                            s = s + "\n\n Url   " + ytFile.getUrl() +  " \n\n " + ytFile.getFormat();
                            Log.v(TAG, "Urls: " + i + " " + ytFile.getUrl() + Uri.decode(ytFile.getUrl()));
                            Log.v(TAG, "Formats: " + i + " " + ytFile.getFormat());

                            if (defVideo == null || isVideoContainsAudio == false) {
                                if (ytFile.getFormat().getAudioBitrate() != 1 && ytFile.getFormat().getHeight() != -1) {
                                    defVideo = ytFile.getUrl();
                                    isVideoContainsAudio = true;
                                } else if (ytFile.getFormat().getHeight() != -1) {
                                    if (ytFile.getFormat().getAudioBitrate() == -1) {
                                        defVideo = ytFile.getUrl();
                                        isVideoContainsAudio = false;
                                    }
                                }
                            }

                            if (defAudio == null) {
                                if (ytFile.getFormat().getAudioBitrate() != -1 && ytFile.getFormat().getHeight() == -1) {
                                    defAudio = ytFile.getUrl();
                                }
                            }
                        }

                    }

                    //  textView.setText(s);
                }

                loadAdapter(ytFiles, vMeta, defVideo, defAudio, isVideoContainsAudio);
            }
        }.extract(youtubeLink, true, true);

    }

    private void loadAdapter(SparseArray<YtFile> ytFiles, VideoMeta vMeta, String defVideo
            , String defAudio, boolean isVideoContainsAudio) {

        LinearLayoutManager manager = new LinearLayoutManager(mContext);

        RecyclerView recyclerView = findViewById(R.id.recycler);

        recyclerView.setLayoutManager(manager);
        YouTubeExtractorAdapter adapter = new YouTubeExtractorAdapter(ytFiles, vMeta, defVideo, defAudio, isVideoContainsAudio);

        recyclerView.setAdapter(adapter);
    }
}
