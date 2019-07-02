package com.my.drive.controller;

import com.my.drive.service.GoogleDriveService;
import com.my.drive.utils.DriveQuickstart;
import com.my.drive.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/drive")
public class GoogleDriveController {
    @Autowired
    private GoogleDriveService googleDriveService;
    @Autowired
    private DriveQuickstart driveQuickstart;

    @GetMapping("/list")
    public R getList(String id,Integer page,Integer limit,String pageToken) throws GeneralSecurityException, IOException {
        return googleDriveService.getList(id,page,limit,pageToken);
    }
    @GetMapping("/teamList")
    public R teamList() throws GeneralSecurityException, IOException {
        return R.ok().put("data",googleDriveService.getTeamDriveList()).put("token",driveQuickstart.token);
    }
}
