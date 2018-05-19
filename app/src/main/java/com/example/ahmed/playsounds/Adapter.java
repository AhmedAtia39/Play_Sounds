package com.example.ahmed.playsounds;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by AHMED on 19/05/2018.
 */

public class Adapter extends BaseAdapter {
    ArrayList<Song> songList = new ArrayList<>();
    Context context;
    MediaPlayer mediaPlayer;

    public Adapter(ArrayList<Song> songList, Context context) {
        this.songList = songList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int i) {
        return songList.get(i).title;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final int position = i;
        View v = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);

        TextView song_name = v.findViewById(R.id.txt_songName);
        song_name.setText(songList.get(i).title);

        mediaPlayer = new MediaPlayer();

        song_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.stop();
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(songList.get(position).path);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }
}
