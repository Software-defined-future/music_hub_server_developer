package com.example.demo.service;

import com.example.demo.domain.SongList;

import java.util.List;

public interface SongListService {

    boolean addSongList (SongList songList);

    boolean updateSongListMsg(SongList songList);

    boolean updateSongListImg(SongList songList);

    boolean deleteSongList(Integer id);

    List<SongList> allSongList();

    List<SongList> likeTitle(String title);

    List<SongList> likeStyle(String style);

    List<SongList> songListOfTitle(String title);

    List<SongList> containerSongList(Integer song);
    List<Long> getUpdateId();
    boolean updateSongListState(Integer id);
    boolean updateSongListScore(Long id,Integer score);

}
