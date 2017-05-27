package com.example.hbl.kotlin.coderead;

import java.util.List;

public class DirectoryNode extends BaseModel implements Comparable {

    public String name;
    public List<DirectoryNode> pathNodes;
    public boolean isDirectory;
    public boolean openChild;
    public int depth;
    public String displayName;
    public String absolutePath;

    public DirectoryNode() {
    }

    public DirectoryNode(String name) {
        this.name = name;
    }

    public DirectoryNode(List<DirectoryNode> pathNodes, String name) {
        this.pathNodes = pathNodes;
        this.name = name;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof DirectoryNode)) {
            return 1;
        }
        if (((DirectoryNode) o).isDirectory && !isDirectory) {
            return 1;
        }
        if (!((DirectoryNode) o).isDirectory && isDirectory) {
            return -1;
        }
        return name.compareTo(((DirectoryNode) o).name);
    }
}
