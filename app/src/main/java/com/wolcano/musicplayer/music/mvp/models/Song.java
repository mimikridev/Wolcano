package com.wolcano.musicplayer.music.mvp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
/**
 *  Do not change Property and Entity values its auto generated by GreenDao!
 */
import java.io.Serializable;


@Entity(nameInDb = "AppWolcanoTable")
public class Song implements Serializable,Parcelable {

    private static final long serialVersionUID = 1L;
    @Property(nameInDb = "song_id")
    private long songId;
    @Property(nameInDb = "title")
    private String title;
    @Property(nameInDb = "artist")
    private String artist;
    @Property(nameInDb = "cover_path")
    private String covPath;
    @NotNull
    @Property(nameInDb = "type")
    private int tip;
    @NotNull
    @Property(nameInDb = "duration")
    private long duration;
    @NotNull
    @Property(nameInDb = "path")
    private String path;
    @Property(nameInDb = "fileName")
    private String dosName;
    @Id(autoincrement = true)
    @Property(nameInDb = "id")
    private Long id;
    @Property(nameInDb = "fileSize")
    private long dosSize;
    @Property(nameInDb = "album")
    private String album;
    @Property(nameInDb = "album_id")
    private long albumId;

    public Song() {
    }
    protected Song(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        tip = in.readInt();
        songId = in.readLong();
        title = in.readString();
        artist = in.readString();
        album = in.readString();
        albumId = in.readLong();
        covPath = in.readString();
        duration = in.readLong();
        path = in.readString();
        dosName = in.readString();
        dosSize = in.readLong();
    }
    public Song(Long id, int tip, long songId, String title, String artist,
                String album, long albumId, String covPath, long duration,
                @NotNull String path, String dosName, long dosSize) {
        this.id = id;
        this.tip = tip;
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.albumId = albumId;
        this.covPath = covPath;
        this.duration = duration;
        this.path = path;
        this.dosName = dosName;
        this.dosSize = dosSize;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public String getDosName() {
        return dosName;
    }

    public String getCovPath() {
        return covPath;
    }

    public void setCovPath(String covPath) {
        this.covPath = covPath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }
    public int getType() {
        return tip;
    }

    public void setType(int tip) {
        this.tip = tip;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }
    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public void setDosName(String dosName) {
        this.dosName = dosName;
    }
    public String getTitle() {
        return title;
    }
    public long getDosSize() {
        return dosSize;
    }

    public void setDosSize(long dosSize) {
        this.dosSize = dosSize;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.tip);
        dest.writeLong(this.songId);
        dest.writeString(this.title);
        dest.writeString(this.artist);
        dest.writeString(this.album);
        dest.writeLong(this.albumId);
        dest.writeString(this.covPath);
        dest.writeLong(this.duration);
        dest.writeString(this.path);
        dest.writeString(this.dosName);
        dest.writeLong(this.dosSize);

    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Song)) {
            return false;
        }
        Song song = (Song) o;
        if (song.songId > 0 && song.songId == this.songId) {
            return true;
        }
        if (TextUtils.equals(song.title, this.title)
                && TextUtils.equals(song.artist, this.artist)
                && TextUtils.equals(song.album, this.album)
                && song.duration == this.duration) {
            return true;
        }
        return false;
    }
    public interface Tip {
        int MODEL0 = 0;
        int MODEL1 = 1;
    }
    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }



}
