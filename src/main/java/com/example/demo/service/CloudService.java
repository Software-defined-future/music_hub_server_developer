package com.example.demo.service;

import com.example.demo.domain.Cloud;

import java.util.List;

public interface CloudService {

    boolean addCloudSong(Cloud cloud);

    List<Cloud> allSongs(Integer id);
    boolean updateSong(Cloud cloud);
    Long getSize(Integer  id);
}
