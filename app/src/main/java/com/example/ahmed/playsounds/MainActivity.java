package com.example.ahmed.playsounds;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Song> songList;
    private ListView songView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songView = (ListView) findViewById(R.id.song_list);
        songList = new ArrayList<Song>();

        getPermission();
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            } else {
                getSongList();
            }
        } else {
            getSongList();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch ((requestCode)) {
            case 10:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getSongList();
                } else {
                    Toast.makeText(this, "You have not permission to get songs", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void getSongList() {
        songList.clear();
        ContentResolver resolver = getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = resolver.query(musicUri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            int title = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int path = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String thisTitle = cursor.getString(title);
                String thisPath = cursor.getString(path);
                songList.add(new Song(thisTitle, thisPath));
            }
            while (cursor.moveToNext());

            Adapter adapter = new Adapter(songList, this);
            songView.setAdapter(adapter);
        }
    }
}