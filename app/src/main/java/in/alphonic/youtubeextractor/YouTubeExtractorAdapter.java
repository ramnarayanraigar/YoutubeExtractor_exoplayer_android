package in.alphonic.youtubeextractor;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YtFile;

public class YouTubeExtractorAdapter extends RecyclerView.Adapter<YouTubeExtractorAdapter.YouTubeExtractor>{

    private SparseArray<YtFile> ytFiles;
    private VideoMeta vMeta;

    private String defVideo,defAudio;
    private  boolean isVideoContainsAudio;

    private String finalPlayVideoUrl, finalPlayAudioUrl;
    private boolean finalIsVideoContainsAudio = true;

    private Context mContext;

    public YouTubeExtractorAdapter(SparseArray<YtFile> ytFiles, VideoMeta vMeta, String defVideo
    , String defAudio, boolean isVideoContainsAudio) {
        this.ytFiles = ytFiles;
        this.vMeta = vMeta;

        this.defAudio = defAudio;
        this.defVideo = defVideo;
        this.isVideoContainsAudio = isVideoContainsAudio;
    }

    @NonNull
    @Override
    public YouTubeExtractor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);

       mContext = parent.getContext();
        return new YouTubeExtractor(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YouTubeExtractor holder, final int position) {
        holder.textUrl.setText(ytFiles.valueAt(position).getUrl());
        holder.textFormat.setText(String.valueOf(ytFiles.valueAt(position).getFormat()));

        if (defVideo == null || !isVideoContainsAudio) {
            holder.playVideo.setVisibility(View.GONE);
        }

        if (defAudio == null) {
            holder.playAudio.setVisibility(View.GONE);
        }

        holder.playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ytFiles.valueAt(position).getFormat().getHeight() != -1) {
                    finalPlayVideoUrl = defVideo;
                }

                if (ytFiles.valueAt(position).getFormat().getAudioBitrate() == -1) {
                    finalIsVideoContainsAudio = false;
                }

                callExoPlayer(finalPlayVideoUrl == null ? defVideo : finalPlayVideoUrl, finalPlayAudioUrl ==
                        null ? defAudio : finalPlayAudioUrl, isVideoContainsAudio);

            }
        });

        holder.playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ytFiles.valueAt(position).getFormat().getAudioBitrate() == -1) {
                    finalPlayAudioUrl = defAudio;
                }

                callExoPlayer(null, finalPlayAudioUrl == null ? defAudio : finalPlayAudioUrl, isVideoContainsAudio);
            }
        });
    }

    private void callExoPlayer(String defVideo, String defAudio, boolean isVideoContainsAudio) {
        Intent intent = new Intent(mContext, ExoPLayerActivity.class);

        intent.putExtra("defVideo", defVideo);
        intent.putExtra("defAudio", defAudio);
        intent.putExtra("isVideoContainsAudio", isVideoContainsAudio);

        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return ytFiles != null ? ytFiles.size() : 0;
    }

    class YouTubeExtractor extends RecyclerView.ViewHolder {
        private TextView textUrl, textFormat;
        private Button playVideo, playAudio;

        public YouTubeExtractor(@NonNull View itemView) {
            super(itemView);

            textUrl = itemView.findViewById(R.id.text_url);
            textFormat = itemView.findViewById(R.id.text_format);
            playAudio = itemView.findViewById(R.id.btn_play_audio);
            playVideo = itemView.findViewById(R.id.btn_play_video);
        }
    }
}
