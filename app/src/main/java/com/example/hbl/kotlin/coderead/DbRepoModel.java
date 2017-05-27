package com.example.hbl.kotlin.coderead;


import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.sqldelight.RowMapper;

public interface DbRepoModel {
    String TABLE_NAME = "db_repo";

    String _ID = "_id";

    String NAME = "name";

    String LAST_MODIFY = "last_modify";

    String ABSOLUTE_PATH = "absolute_path";

    String NET_URL = "net_url";

    String IS_FOLDER = "is_folder";

    String DOWNLOAD_ID = "download_id";

    String FACTOR = "factor";

    String IS_UNZIP = "is_unzip";

    String CREATE_TABLE = ""
            + "CREATE TABLE db_repo (\n"
            + "   _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
            + "  name TEXT,\n"
            + "  last_modify INTEGER,\n"
            + "  absolute_path TEXT,\n"
            + "  net_url TEXT,\n"
            + "  is_folder INTEGER DEFAULT 0,\n"
            + "  download_id INTEGER DEFAULT 0,\n"
            + "  factor REAL,\n"
            + "  is_unzip INTEGER DEFAULT 0\n"
            + ")";

    String SELECT_ALL = ""
            + "SELECT *\n"
            + "FROM db_repo\n"
            + "ORDER BY last_modify DESC";

    String CLEAN_ALL = ""
            + "DELETE\n"
            + "FROM db_repo";

    String DELETE_REPO = ""
            + "DELETE FROM db_repo\n"
            + "WHERE _id = ?";

    String UPDATE_LAST_MODIFY = ""
            + "UPDATE db_repo\n"
            + "SET last_modify = ?\n"
            + "WHERE _id = ?";

    String CHECK_SAME_REPO = ""
            + "SELECT *\n"
            + "FROM db_repo\n"
            + "WHERE name = ?\n"
            + "AND absolute_path = ?";

    String UPDATE_DOWNLOAD_ID = ""
            + "UPDATE db_repo\n"
            + "SET download_id = ?\n"
            + "WHERE _id = ?";

    String RESET_DOWNLOAD_ID = ""
            + "UPDATE db_repo\n"
            + "SET download_id = 0\n"
            + "WHERE download_id = ?";

    String UPDATE_DOWNLOAD_PROGRESS = ""
            + "UPDATE db_repo\n"
            + "SET factor = ?\n"
            + "WHERE download_id = ?";

    String UPDATE_UNZIP_PROGRESS = ""
            + "UPDATE db_repo\n"
            + "SET factor = ?, is_unzip = ?\n"
            + "WHERE download_id = ?";

    long _id();

    @Nullable
    String name();

    @Nullable
    Long last_modify();

    @Nullable
    String absolute_path();

    @Nullable
    String net_url();

    @Nullable
    Boolean is_folder();

    @Nullable
    Long download_id();

    @Nullable
    Float factor();

    @Nullable
    Boolean is_unzip();

    interface Creator<T extends DbRepoModel> {
        T create(long _id, @Nullable String name, @Nullable Long last_modify, @Nullable String absolute_path, @Nullable String net_url, @Nullable Boolean is_folder, @Nullable Long download_id, @Nullable Float factor, @Nullable Boolean is_unzip);
    }

    final class Mapper<T extends DbRepoModel> implements RowMapper<T> {
        private final Factory<T> dbRepoModelFactory;

        public Mapper(Factory<T> dbRepoModelFactory) {
            this.dbRepoModelFactory = dbRepoModelFactory;
        }

        @Override
        public T map(@NonNull Cursor cursor) {
            return dbRepoModelFactory.creator.create(
                    cursor.getLong(0),
                    cursor.isNull(1) ? null : cursor.getString(1),
                    cursor.isNull(2) ? null : cursor.getLong(2),
                    cursor.isNull(3) ? null : cursor.getString(3),
                    cursor.isNull(4) ? null : cursor.getString(4),
                    cursor.isNull(5) ? null : cursor.getInt(5) == 1,
                    cursor.isNull(6) ? null : cursor.getLong(6),
                    cursor.isNull(7) ? null : cursor.getFloat(7),
                    cursor.isNull(8) ? null : cursor.getInt(8) == 1
            );
        }
    }

    final class Marshal {
        protected final ContentValues contentValues = new ContentValues();

        Marshal(@Nullable DbRepoModel copy) {
            if (copy != null) {
                this._id(copy._id());
                this.name(copy.name());
                this.last_modify(copy.last_modify());
                this.absolute_path(copy.absolute_path());
                this.net_url(copy.net_url());
                this.is_folder(copy.is_folder());
                this.download_id(copy.download_id());
                this.factor(copy.factor());
                this.is_unzip(copy.is_unzip());
            }
        }

        public ContentValues asContentValues() {
            return contentValues;
        }

        public Marshal _id(long _id) {
            contentValues.put(_ID, _id);
            return this;
        }

        public Marshal name(String name) {
            contentValues.put(NAME, name);
            return this;
        }

        public Marshal last_modify(Long last_modify) {
            contentValues.put(LAST_MODIFY, last_modify);
            return this;
        }

        public Marshal absolute_path(String absolute_path) {
            contentValues.put(ABSOLUTE_PATH, absolute_path);
            return this;
        }

        public Marshal net_url(String net_url) {
            contentValues.put(NET_URL, net_url);
            return this;
        }

        public Marshal is_folder(Boolean is_folder) {
            if (is_folder == null) {
                contentValues.putNull(IS_FOLDER);
                return this;
            }
            contentValues.put(IS_FOLDER, is_folder ? 1 : 0);
            return this;
        }

        public Marshal download_id(Long download_id) {
            contentValues.put(DOWNLOAD_ID, download_id);
            return this;
        }

        public Marshal factor(Float factor) {
            contentValues.put(FACTOR, factor);
            return this;
        }

        public Marshal is_unzip(Boolean is_unzip) {
            if (is_unzip == null) {
                contentValues.putNull(IS_UNZIP);
                return this;
            }
            contentValues.put(IS_UNZIP, is_unzip ? 1 : 0);
            return this;
        }
    }

    final class Factory<T extends DbRepoModel> {
        public final Creator<T> creator;

        public Factory(Creator<T> creator) {
            this.creator = creator;
        }

        public Marshal marshal() {
            return new Marshal(null);
        }

        public Marshal marshal(DbRepoModel copy) {
            return new Marshal(copy);
        }

        public Mapper<T> select_allMapper() {
            return new Mapper<T>(this);
        }

        public Mapper<T> check_same_repoMapper() {
            return new Mapper<T>(this);
        }
    }
}
