package com.my.drive.service.impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.my.drive.service.GoogleDriveService;
import com.my.drive.utils.DriveQuickstart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class GoogleDriveServiceImpl implements GoogleDriveService {

    @Autowired
    private DriveQuickstart driveQuickstart;

    @Override
    public List getList(String id,Integer page,Integer limit) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, driveQuickstart.JSON_FACTORY, driveQuickstart.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(driveQuickstart.APPLICATION_NAME)
                .build();
        String q = "trashed = false and '"+id+"' in parents ";
        FileList result = service.files().list()
                .setQ(q)
                .setFields("*")

//                .setOrderBy("mimeType")

                .setPageSize(limit)
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s %s (%s) (%s)\n", file.getName(), file.getFullFileExtension(),file.getId(),file.getWebViewLink());
            }
            System.out.println(result.getFiles());
        }
        return files;
    }
}
