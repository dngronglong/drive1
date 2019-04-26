package com.my.drive.controller;

import com.my.drive.service.GoogleDriveFileService;
import com.my.drive.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RequestMapping("/file")
@RestController
public class DownFileController {

    @Autowired
    private GoogleDriveFileService googleDriveFileService;

    @GetMapping("/download")
    public R downFile(String fileId,String fileType) throws GeneralSecurityException, IOException {
        return googleDriveFileService.downloadFile(fileId,fileType);
    }
}
