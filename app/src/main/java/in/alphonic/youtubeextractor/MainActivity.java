package in.alphonic.youtubeextractor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    TextView textView;
    private String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                startActivity(intent);
            }
        });

        textView = findViewById(R.id.desc);

        String youtubeLink = "https://www.youtube.com/watch?v=2jQJTppSCck"; // 2jQJTppSCck XQwe30cZffg

        new YouTubeExtractor(this) {
            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {

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
        }.extract(youtubeLink, true, true);

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
}
