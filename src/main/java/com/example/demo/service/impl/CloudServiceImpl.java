package com.example.demo.service.impl;

import com.example.demo.dao.CloudMapper;
import com.example.demo.domain.Cloud;
import com.example.demo.service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudServiceImpl implements CloudService {
    @Autowired
    private CloudMapper cloudMapper;
    @Override
    @Async
    public boolean addCloudSong(Cloud cloud) {
        return cloudMapper.insertCloudSong(cloud)>0;
    }

    @Override
    public List<Cloud> allSongs(Integer id) {
        return cloudMapper.allSongs(id);
    }

    @Override
    public boolean updateSong(Cloud cloud) {
        return cloudMapper.updateSong(cloud)>0;
    }

    @Override
    public Long getSize(Integer id) {
        return cloudMapper.getSize(id);
    }
}
