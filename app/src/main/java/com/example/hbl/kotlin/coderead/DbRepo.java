package com.example.hbl.kotlin.coderead;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;

@AutoValue
public abstract class DbRepo implements DbRepoModel {

    public static final Factory<DbRepo> FACTORY =
            new Factory<>(new Creator<DbRepo>() {
                @Override
                public DbRepo create(long _id, @Nullable String name, @Nullable Long last_modify, @Nullable String absolute_path, @Nullable String net_url, @Nullable Boolean is_folder, @Nullable Long download_id, @Nullable Float factor, @Nullable Boolean is_unzip) {
                    return new DbRepo() {
                        @Override
                        public long _id() {
                            return _id;
                        }

                        @Nullable
                        @Override
                        public String name() {
                            return name;
                        }

                        @Nullable
                        @Override
                        public Long last_modify() {
                            return last_modify;
                        }

                        @Nullable
                        @Override
                        public String absolute_path() {
                            return absolute_path;
                        }

                        @Nullable
                        @Override
                        public String net_url() {
                            return net_url;
                        }

                        @Nullable
                        @Override
                        public Boolean is_folder() {
                            return is_folder;
                        }

                        @Nullable
                        @Override
                        public Long download_id() {
                            return download_id;
                        }

                        @Nullable
                        @Override
                        public Float factor() {
                            return factor;
                        }

                        @Nullable
                        @Override
                        public Boolean is_unzip() {
                            return is_unzip;
                        }
                    };
                }
            });
//            new Factory<>((Creator<DbRepo>) (_id, name, last_modify, absolute_path, net_url, is_folder, download_id, factor, is_unzip)
//                    -> new AutoValue_DbRepo(_id, name, last_modify, absolute_path, net_url, is_folder, download_id, factor, is_unzip));

    public static final RowMapper<DbRepo> FOR_TEAM_MAPPER = FACTORY.select_allMapper();
}
