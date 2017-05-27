package com.example.hbl.kotlin.event;

public class DownloadProgressEvent {

    public String repoId;
    public long downloadId;
    public float factor;
    public boolean isUnzip;

    public DownloadProgressEvent(long downloadId, boolean isUnzip) {
        this.downloadId = downloadId;
        this.factor = 1.f;
        this.isUnzip = isUnzip;
    }

    public DownloadProgressEvent(String repoId, long downloadId, float factor, boolean isUnzip) {
        this.repoId = repoId;
        this.downloadId = downloadId;
        this.factor = factor;
        this.isUnzip = isUnzip;
    }
}
