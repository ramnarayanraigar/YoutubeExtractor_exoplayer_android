package in.alphonic.youtubeextractor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.commit451.youtubeextractor.YouTubeExtractionResult;
import com.commit451.youtubeextractor.YouTubeExtractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YoutubeCommitExtractorActivity extends AppCompatActivity {

    private static final String YOUTUBE_ID = "st1XVfkDWqk";

    private final YouTubeExtractor mExtractor = YouTubeExtractor.create();


    private Callback<YouTubeExtractionResult> mExtractionCallback = new Callback<YouTubeExtractionResult>() {
        @Override
        public void onResponse(Call<YouTubeExtractionResult> call, Response<YouTubeExtractionResult> response) {
            bindVideoResult(response.body());
        }

        @Override
        public void onFailure(Call<YouTubeExtractionResult> call, Throwable t) {
            onError(t);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_commit_extractor);


//        For android youtube extractor library  com.github.Commit451.YouTubeExtractor:youtubeextractor:2.1.0'
        mExtractor.extract(YOUTUBE_ID).enqueue(mExtractionCallback);

    }


    private void onError(Throwable t) {
        t.printStackTrace();
        Toast.makeText(YoutubeCommitExtractorActivity.this, "It failed to extract. So sad", Toast.LENGTH_SHORT).show();
    }


    private void bindVideoResult(YouTubeExtractionResult result) {

//        Here you can get download url link
        Log.d("OnSuccess", "Got a result with the best url: " + result.getBestAvailableQualityVideoUri());

        Toast.makeText(this, "result : " + result.getSd360VideoUri(), Toast.LENGTH_SHORT).show();
    }
}
