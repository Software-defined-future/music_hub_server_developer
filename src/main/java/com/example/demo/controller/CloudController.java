package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;


import com.example.demo.domain.Cloud;
import com.example.demo.service.impl.CloudServiceImpl;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.mp3.MP3FileReader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

import java.util.Date;

@RestController
@CrossOrigin
public class CloudController {

    @Autowired
    private CloudServiceImpl cloudService;

    private final Long total = 515396075520l;

    @PostMapping(value = "/cloud/upload")
    public Object cloudUpload(@RequestParam("music") MultipartFile music,@RequestParam("id")Integer id){


        JSONObject jsonObject = new JSONObject();
        if (music.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        Long size=music.getSize();
//        String len = "";
//        if(size<1024){
//            len = String.format("%.1f",size*1.0)+"B";
//
//        }else if(size<1048576){
//            len = String.format("%.1f",size*1.0/1024)+"KB";
//        }else if(size<1073741824){
//            len = String.format("%.1f",size*1.0/1048576)+"MB";
//        }else {
//            len = String.format("%.1f",size*1.0/1048576)+"GB";
//        }
        String originalName = music.getOriginalFilename();
        String suffixName = originalName.substring(originalName.lastIndexOf(".")+1).toUpperCase();
        String fileName = System.currentTimeMillis()+originalName;
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "cloudMusic" ;
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeAvatorPath = "/cloudMusic/"+fileName;
        try {
            music.transferTo(dest);
            File mp3 = new File(filePath + System.getProperty("file.separator") + fileName);
            MP3FileReader reader = new MP3FileReader();
            AudioFile audioFile = reader.read(mp3);
            Tag tag = audioFile.getTag();
//            System.out.println("artist:"+tag.getFirst(FieldKey.ARTIST));
//            System.out.println("title:"+tag.getFirst(FieldKey.TITLE));
//            System.out.println("album:"+tag.getFirst(FieldKey.ALBUM));
            String artist = tag.getFirst(FieldKey.ARTIST);
            String title = tag.getFirst(FieldKey.TITLE);
            String album = tag.getFirst(FieldKey.ALBUM);
            artist = artist.equals("") ? "暂无" : artist;
            title = title.equals("") ? originalName.substring(0,originalName.lastIndexOf(".")) : title;
            album = album.equals("") ? "暂无" : album;
            Cloud cloud =new Cloud();
            cloud.setName(artist);
            cloud.setTitle(title);
            cloud.setIntro(album);
            cloud.setFormat(suffixName);
            cloud.setUpload_time(new Date());
            cloud.setSize(size);
            cloud.setLyric("");
            cloud.setUrl(storeAvatorPath);
            cloud.setUserId(id);
            cloudService.addCloudSong(cloud);
            jsonObject.put("code", 1);
//            jsonObject.put("size",size);
            jsonObject.put("msg", "上传成功");
        }catch (IOException e){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败"+e.getMessage());
            return jsonObject;
        }finally {
            return jsonObject;
        }
    }

    @GetMapping(value = "/cloud/allSongs")
    public Object cloudAll(@RequestParam("id")Integer id){
        return cloudService.allSongs(id);
    }
    @PostMapping(value = "/cloud/updateInfo")
    public Object cloudUpdateInfo(HttpServletRequest req,MultipartFile img)  {
//System.out.println("test");
        JSONObject jsonObject = new JSONObject();
        String userId = req.getParameter("userId").trim();
        String id = req.getParameter("id").trim();
        String name  = req.getParameter("name").trim();
        String title  = req.getParameter("title").trim();
        String intro  = req.getParameter("intro").trim();
//        MultipartFile img  = req.getParameter("img");
Cloud cloud = new Cloud();
cloud.setTitle(title);
cloud.setIntro(intro);
cloud.setName(name);
cloud.setId(Integer.parseInt(id));
if(img!=null){
    String originalName = img.getOriginalFilename();

    String fileName = System.currentTimeMillis()+originalName;
    String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "cloudPic" ;
    File file1 = new File(filePath);
    if (!file1.exists()){
        file1.mkdir();
    }
    File dest = new File(filePath + System.getProperty("file.separator") + fileName);
    String storeAvatorPath = "/cloudPic/"+fileName;
    try {
        img.transferTo(dest);
        cloud.setPic(storeAvatorPath);
        boolean res = cloudService.updateSong(cloud);
        jsonObject.put("code", 1);
        jsonObject.put("msg", "修改成功");
    }catch (IOException e){

        jsonObject.put("code", 0);
        jsonObject.put("msg", "修改失败");
    } finally {
        return jsonObject;
    }

}else {
    boolean res = cloudService.updateSong(cloud);
    jsonObject.put("code", 1);
    jsonObject.put("msg", "修改成功");
    return jsonObject;
}


    }

    //获取用户所用的内存
    @GetMapping(value = "/cloud/size")
    public Object getSize(@RequestParam("id")Integer id){
        JSONObject jsonObject = new JSONObject();
      Long size =  cloudService.getSize(id);
      jsonObject.put("size",size);
        return jsonObject;
    }
}
