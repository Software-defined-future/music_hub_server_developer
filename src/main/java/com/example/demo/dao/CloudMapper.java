package com.example.demo.dao;

import com.example.demo.domain.Cloud;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloudMapper {
    int insertCloudSong(Cloud cloud);
    List<Cloud> allSongs(Integer id);
    int updateSong(Cloud cloud);
    Long getSize(Integer id);
}
