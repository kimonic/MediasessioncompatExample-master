/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.cui.mediaplayer;

import android.app.Service;
import android.content.Intent;
import com.tencent.qqmusic.innovation.network.b.c;
import com.tencent.qqmusictv.app.activity.MainActivity;
import com.tencent.qqmusic.innovation.common.a.b;
import android.text.TextUtils;
import com.tencent.qqmusictv.network.request.RequestFactory;
import com.tencent.qqmusictv.network.request.SearchRequestNew;
import com.tencent.qqmusic.innovation.network.Network;
import com.tencent.qqmusic.innovation.network.request.CommonRequest;
import com.tencent.qqmusiccommon.util.music.MusicPlayList;
import com.tencent.qqmusictv.app.activity.PlayerActivity;
import android.os.Bundle;
import android.os.IBinder;

public class SearchService extends Service {
    public static final String COMMING_DATA = "commingData";
    public static final String SEARCH_RESULT = "search_result";
    private static final String TAG = "SearchService";
    private Intent mIntent;
    private boolean mMb;
    public static final int sAlbum = 0x2;
    public static final int sRadio = 0x3;
    public static final int sSinger = 0x1;
    private c.a searchListener;
    
    public SearchService() {
        searchListener = new SearchService.1(this);
    }
    
    public int onStartCommand(Intent p1, int p2, int p3) {
        mIntent = p1;
        sendBeginBroadcast();
        handleSearch(p1);
        return 0x3;
    }
    
    public IBinder onBind(Intent p1) {
        return null;
    }
    
    private void handleSearch(Intent p1) {
        b.b("SearchService", "intent : " + p1);
        if(p1 != null) {
            localString1 = p1.getStringExtra("commingData");
            mMb = p1.getBooleanExtra("mb", false);
            b.b("SearchService", " data : " + "commingData");
            if(!TextUtils.isEmpty("commingData")) {
                sendSearch("commingData");
            }
            return;
        }
        handleError();
    }
    
    private void sendSearch(String p1) {
        localconst/41 = RequestFactory.createSearchRequsetNew(p1, 0x0, "txt.android.song", 0x1);
        Network.a().a(0x0, searchListener);
    }
    
    private void handleError() {
        Intent localIntent1 = new Intent(this, MainActivity.class);
        localIntent1.setFlags(0x10000000);
        startActivity(localIntent1);
        stopSelf();
    }
    
    private void transferPlaylist(MusicPlayList p1) {
        Intent localIntent1 = new Intent(this, PlayerActivity.class);
        Bundle localint2 = new Bundle();
        localint2.putInt(PlayerActivity.PLAYER_TYPE, PlayerActivity.SEARCH_PLAYER);
        localint2.putParcelable("search_result", p1);
        localint2.putBoolean("mb", mMb);
        localIntent1.putExtras(localint2);
        localIntent1.setFlags(0x10000000);
        startActivity(localIntent1);
        stopSelf();
    }
    
    private void sendBeginBroadcast() {
        Intent localIntent1 = new Intent();
        localIntent1.setAction("com.tencent.qqmusictv.ACTION_SEARCH_BEGIN.QQMusicTV");
        sendBroadcast(localIntent1);
    }
    
    private void sendFinsihBroadcast() {
        b.b("SearchService", "sendFinsihBroadcast");
        Intent localIntent1 = new Intent();
        localIntent1.setAction("com.tencent.qqmusictv.ACTION_SEARCH_FINISH.QQMusicTV");
        sendBroadcast(localIntent1);
    }
}
