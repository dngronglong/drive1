package com.my.drive.service;

import com.my.drive.utils.R;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface GoogleDriveService {
    R getList(String id, Integer page, Integer limit, String pageToken) throws GeneralSecurityException, IOException;
    List getTeamDriveList() throws GeneralSecurityException, IOException;
    List getTeamList(String id,Integer page,Integer limit) throws GeneralSecurityException, IOException;
}
