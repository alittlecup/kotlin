package com.example.hbl.kotlin.coderead;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.hbl.kotlin.utils.FileCache;
import com.example.hbl.kotlin.utils.FileTypeUtils;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class DirectoryNavDelegate {
    private static final String TAG = "DirectoryNavDelegate";

    public interface FileClickListener {
        void doOpenFile(DirectoryNode node);
    }

    public interface LoadFileCallback{
        void onFileOpenStart();
        void onFileOpenEnd();
    }

    private RecyclerView mRecyclerView;
    private DirectoryAdapter mDirectoryAdapter;
    private Context mContext;
    private FileClickListener mFileClickListener;
    private LoadFileCallback mLoadFileCallback;
    private final ArrayList<Disposable> disposables=new ArrayList<>();
    public DirectoryNavDelegate(RecyclerView recyclerView, FileClickListener listener) {
        mRecyclerView = recyclerView;
        mContext = recyclerView.getContext();
        mFileClickListener = listener;
        mDirectoryAdapter = new DirectoryAdapter(recyclerView.getContext(), listener);
        setUpRecyclerView();
    }

    public void setLoadFileCallback(LoadFileCallback loadFileCallback) {
        mLoadFileCallback = loadFileCallback;
    }

    public void clearSubscription() {
       for(Disposable d:disposables){
           d.dispose();
       }
        disposables.clear();
    }

    public void resumeDirectoryState(DirectoryNode node) {
        mDirectoryAdapter.setNodeRoot(node);
    }

    public DirectoryNode getDirectoryNodeInstance() {
        return mDirectoryAdapter.getNodeRoot();
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mDirectoryAdapter);
    }

    public void updateData(DirectoryNode directoryNode) {
        mLoadFileCallback.onFileOpenStart();
        disposables.add(
                Observable.fromCallable(() -> {
                    DirectoryNode node;
                    if (directoryNode.isDirectory) {
                        node = FileCache.getFileDirectory(new File(directoryNode.absolutePath));
                    } else {
                        node = directoryNode;
                    }
                    return node;
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(mDirectoryAdapter::setNodeRoot)
                        .doOnNext(this::checkOpenFirstFile)
                        .doOnError(e -> Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show())
                        .onErrorResumeNext(Observable.empty())
                        .doOnComplete(() -> mLoadFileCallback.onFileOpenEnd())
                        .subscribe());
    }

    private void checkOpenFirstFile(DirectoryNode node) {
        if (node.isDirectory && node.pathNodes != null) {
            boolean haveOpen = false;
            for (DirectoryNode n : node.pathNodes) {
                if (FileTypeUtils.isMdFileType(n.name) && n.name.equalsIgnoreCase("readme.md")) {
                    mFileClickListener.doOpenFile(n);
                    haveOpen = true;
                }
            }
            if (!haveOpen) {
                mFileClickListener.doOpenFile(null);
            }
        } else if (!node.isDirectory) {
            mFileClickListener.doOpenFile(node);
        } else {
            mFileClickListener.doOpenFile(null);
        }
    }

}
