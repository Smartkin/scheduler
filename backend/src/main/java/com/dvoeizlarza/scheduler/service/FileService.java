package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.entity.File;
import com.dvoeizlarza.scheduler.entity.Homework;
import com.dvoeizlarza.scheduler.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Objects;

@Service
public class FileService {
    private FileRepository fileRepository;
    final String path = "files/";

    public Boolean create(Homework homework, MultipartFile file){
        if(!file.isEmpty()){
            try {
                byte[] bytes = file.getBytes();
                File f = new File();
                f.setHomework(homework);
                String extension;
                try {
                    extension = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
                }catch (NullPointerException e){
                    extension = "";
                }
                f.setExtension(extension);
                fileRepository.save(f);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path+f.getId()+"."+f.getExtension()));
                stream.write(bytes);
                stream.close();
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }


    public byte[] read(Long id){
        File file = fileRepository.findById(id).orElse(null);
        if(file==null){
            return null;
        }
        String name = file.getId()+"."+file.getExtension();
        return read(name);
    }

    public byte[] read(String name){
        try {
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path + name));
            return stream.readAllBytes();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
}
