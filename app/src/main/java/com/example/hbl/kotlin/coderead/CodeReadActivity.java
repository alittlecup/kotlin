package com.example.hbl.kotlin.coderead;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.hbl.kotlin.R;
import com.example.hbl.kotlin.utils.DeviceUtils;

import butterknife.BindView;

public class CodeReadActivity extends BaseActivity implements DirectoryNavDelegate.FileClickListener, DirectoryNavDelegate.LoadFileCallback {

    @BindView(R.id.directory_view)
    RecyclerView mDirectoryRecyclerView;
    @BindView(R.id.left_sheet)
    View mLeftSheet;
    @BindView(R.id.container_code_read)
    FrameLayout mContainer;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private CodeReadFragment mFragment;
    private DirectoryNode mDirectoryNode;
    private DirectoryNode mSelectedNode;

    private DirectoryNavDelegate mDirectoryNavDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_read);
        setupStatusBar();

        mDirectoryNavDelegate = new DirectoryNavDelegate(mDirectoryRecyclerView, this);
        mDirectoryNavDelegate.setLoadFileCallback(this);
        createFragment(null);
        parseIntent(savedInstanceState);
    }

    private void setupStatusBar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mDirectoryRecyclerView.setPadding(0, DeviceUtils.getStatusBarHeight(), 0, 0);
        mDirectoryRecyclerView.setClipToPadding(true);
    }

    private void parseIntent(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mDirectoryNode = (DirectoryNode) savedInstanceState.getSerializable(Navigator.EXTRA_DIRETORY_ROOT);
            mSelectedNode = (DirectoryNode) savedInstanceState.getSerializable(Navigator.EXTRA_DIRETORY_SELECTING);
            DirectoryNode rootNodeInstance = (DirectoryNode) savedInstanceState.getSerializable(Navigator.EXTRA_DIRETORY_ROOT_NODE_INSTANCE);
            mFragment.updateRootNode(mDirectoryNode);
            mDirectoryNavDelegate.resumeDirectoryState(rootNodeInstance);
            doOpenFile(mSelectedNode);
            return;
        }
        Intent intent = getIntent();
        Repo repo = (Repo) intent.getSerializableExtra(Navigator.EXTRA_REPO);
        if (repo == null) {
            String openFilePath = intent.getData().getPath();
            String[] mids = openFilePath.split("/");
            String name = mids[mids.length - 1];
            repo = new Repo(name, openFilePath, false);
            repo.id = String.valueOf(CoReaderDbHelper.getInstance(this).insertRepo(repo));
        }
        CoReaderDbHelper.getInstance(this).updateRepoLastModify(Long.valueOf(repo.id)
                , System.currentTimeMillis());
        mDirectoryNode = repo.toDirectoryNode();
        mFragment.updateRootNode(mDirectoryNode);
        mDirectoryNavDelegate.updateData(mDirectoryNode);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Navigator.EXTRA_DIRETORY_ROOT, mDirectoryNode);
        outState.putSerializable(Navigator.EXTRA_DIRETORY_SELECTING, mSelectedNode);
        outState.putSerializable(Navigator.EXTRA_DIRETORY_ROOT_NODE_INSTANCE, mDirectoryNavDelegate.getDirectoryNodeInstance());
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_code_read_go_out, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_go_out) {
            finish();
            return true;
        }
        if (id == android.R.id.home) {
            if (!mDrawerLayout.isDrawerOpen(GravityCompat.START))
                mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDirectoryNavDelegate.clearSubscription();
        mDirectoryNavDelegate = null;
    }

    @Override
    public void doOpenFile(DirectoryNode node) {
        setTitle(node == null ? mDirectoryNode.name : node.name);
        mSelectedNode = node;
        loadCodeData(node);
    }

    private void loadCodeData(DirectoryNode node) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mFragment.openFile(node);
    }

    private void createFragment(DirectoryNode node) {
        mFragment = CodeReadFragment.newInstance(node, mDirectoryNode);
        mFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_code_read, mFragment).commit();
    }

    @Override
    public void onFileOpenStart() {
        if (mFragment != null && mFragment.isVisible())
            mFragment.getCodeContentLoader().showProgress();
    }

    @Override
    public void onFileOpenEnd() {

    }

}
