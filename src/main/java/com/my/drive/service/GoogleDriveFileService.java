package com.my.drive.service;

import com.my.drive.utils.R;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleDriveFileService {

    R downloadFile(String file,String fileType) throws GeneralSecurityException, IOException;
}
