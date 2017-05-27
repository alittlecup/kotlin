package com.example.hbl.kotlin.event;


import com.example.hbl.kotlin.coderead.Repo;

public class DownloadFailDeleteEvent {

    public Repo deleteRepo;

    public DownloadFailDeleteEvent(Repo deleteRepo) {
        this.deleteRepo = deleteRepo;
    }
}

