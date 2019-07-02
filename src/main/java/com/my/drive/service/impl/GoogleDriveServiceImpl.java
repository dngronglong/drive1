package com.my.drive.service.impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.TeamDrive;
import com.google.api.services.drive.model.TeamDriveList;
import com.my.drive.service.GoogleDriveService;
import com.my.drive.utils.DriveQuickstart;
import com.my.drive.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoogleDriveServiceImpl implements GoogleDriveService {

    @Autowired
    private DriveQuickstart driveQuickstart;

    @Override
    public R getList(String id, Integer page, Integer limit, String pageToken) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, driveQuickstart.JSON_FACTORY, driveQuickstart.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(driveQuickstart.APPLICATION_NAME)
                .build();
        String q = "trashed = false and '"+id+"' in parents ";

        FileList result = service.files().list()
                .setQ(q)
                .setFields("*")
                .setOrderBy("folder")
                .setPageToken(pageToken)
                .setPageSize(limit)
                .setIncludeTeamDriveItems(true)
                .setSupportsAllDrives(true)
//                .setOrderBy("mimeType")

//                .setPageSize(limit)
                .execute();
        List<File> files = result.getFiles();
        System.out.println(pageToken);
        System.out.println(result.getNextPageToken());
        List<Map> list=new ArrayList<>();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
//            System.out.println("Files:");
            for (File file : files) {
                Map map=new HashMap();
                map.put("iconLink",file.getIconLink());
                map.put("fileType",file.getMimeType());
                map.put("name",file.getName());
                map.put("size",file.getSize());
                map.put("id",file.getId());
                map.put("thumbnailLink",file.getThumbnailLink());
                map.put("createdDateTime",file.getCreatedTime().getValue());
                list.add(map);
//                System.out.printf("%s %s (%s) (%s)\n", file.getName(), file.getFullFileExtension(),file.getId(),file.getWebViewLink());
            }
//            System.out.println(list);
        }
        return R.ok().put("data",list).put("nextPageToken",result.getNextPageToken()).put("token",driveQuickstart.token);
    }

    @Override
    public List getTeamDriveList() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, driveQuickstart.JSON_FACTORY, driveQuickstart.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(driveQuickstart.APPLICATION_NAME)
                .build();
        Drive.Teamdrives.List result=service.teamdrives().list();
        List<TeamDrive> list=result.execute().getTeamDrives();
        return list;
    }

    @Override
    public List getTeamList(String id, Integer page, Integer limit) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, driveQuickstart.JSON_FACTORY, driveQuickstart.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(driveQuickstart.APPLICATION_NAME)
                .build();
        String q = "trashed = false and 'root' in parents ";
        FileList result = service.files().list()
                .setQ(q)
                .setDriveId(id)
                .setFields("*")
                .setIncludeTeamDriveItems(true)
                .execute();
        List<File> files = result.getFiles();
        return null;
    }

//    public static void main(String[] args) throws GeneralSecurityException, IOException {
//        GoogleDriveServiceImpl googleDriveService=new GoogleDriveServiceImpl();
//        googleDriveService.getTeamDriveList();
//        googleDriveService.getList("1LQ7o5ZZQjr3TQGz2q37CwElN6o6Cic27",1,20);
//        googleDriveService.getTeamList("0AAhXqA1_HL-pUk9PVA",1,20);
//    }
}
