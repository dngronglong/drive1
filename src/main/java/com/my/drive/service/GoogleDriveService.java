package com.my.drive.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface GoogleDriveService {
    List getList(String id,Integer page,Integer limit) throws GeneralSecurityException, IOException;
}
