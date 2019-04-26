package com.my.drive.service.impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.my.drive.service.GoogleDriveFileService;
import com.my.drive.utils.DriveQuickstart;
import com.my.drive.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

@Service
public class GoogleDriveFileServiceImpl implements GoogleDriveFileService {

    @Autowired
    private DriveQuickstart driveQuickstart;

    @Override
    public R downloadFile(String file, String fileType) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, driveQuickstart.JSON_FACTORY, driveQuickstart.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(driveQuickstart.APPLICATION_NAME)
                .build();
        OutputStream outputStream = new ByteArrayOutputStream();
        service.files().get(file).executeMediaAndDownloadTo(outputStream);
        return R.ok();
    }
}
