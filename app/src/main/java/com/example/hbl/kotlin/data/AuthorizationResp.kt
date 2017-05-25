package com.example.hbl.kotlin.data

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by mingjun on 16/7/27.
 */
class AuthorizationResp : Parcelable {

    /**
     * id : 42390878
     * url : https://api.github.com/authorizations/42390878
     * app : {"name":"GithubApp","url":"https://github.com/mingjunli/GithubApp","client_id":"5b074b14a3c166278774"}
     * token : 70f28edae0a1f38a82c74548e46b70e1af1a653f
     * hashed_token : eecf10631f409697fd7d8844f18f36195d087048e9f6194e285a1c228af9c056
     * token_last_eight : af1a653f
     * note : test
     * note_url : null
     * created_at : 2016-07-27T08:19:02Z
     * updated_at : 2016-07-27T08:19:02Z
     * scopes : []
     * fingerprint : null
     */

    var id: Int = 0
    var url: String = ""
    /**
     * name : GithubApp
     * url : https://github.com/mingjunli/GithubApp
     * client_id : 5b074b14a3c166278774
     */
    var app: AppBean? = null
    var token = ""
    var hashed_token  = ""
    var token_last_eight  = ""
    var note = ""
    var note_url  = ""
    var created_at = ""
    var updated_at  = ""
    var fingerprint  = ""
    var scopes: Array<String> = arrayOf("")

    class AppBean : Parcelable {
        var name: String? = ""
        var url: String? = ""
        var client_id: String? = ""

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(this.name)
            dest.writeString(this.url)
            dest.writeString(this.client_id)
        }


        protected constructor(parcel: Parcel) {
            this.name = parcel.readString()
            this.url = parcel.readString()
            this.client_id = parcel.readString()
        }

        companion object {

            val CREATOR: Parcelable.Creator<AppBean> = object : Parcelable.Creator<AppBean> {
                override fun createFromParcel(source: Parcel): AppBean {
                    return AppBean(source)
                }

                override fun newArray(size: Int): Array<AppBean> {
                    return newArray(size)
                }
            }
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.id)
        dest.writeString(this.url)
        dest.writeParcelable(this.app, flags)
        dest.writeString(this.token)
        dest.writeString(this.hashed_token)
        dest.writeString(this.token_last_eight)
        dest.writeString(this.note)
        dest.writeString(this.note_url)
        dest.writeString(this.created_at)
        dest.writeString(this.updated_at)
        dest.writeString(this.fingerprint)
        dest.writeStringArray(this.scopes)
    }


    protected constructor(parcel: Parcel) {
        this.id = parcel.readInt()
        this.url = parcel.readString()
        this.app = parcel.readParcelable<AppBean>(AppBean::class.java.classLoader)
        this.token = parcel.readString()
        this.hashed_token = parcel.readString()
        this.token_last_eight = parcel.readString()
        this.note = parcel.readString()
        this.note_url = parcel.readString()
        this.created_at = parcel.readString()
        this.updated_at = parcel.readString()
        this.fingerprint = parcel.readString()
        this.scopes = parcel.createStringArray()
    }

    companion object {

        val CREATOR: Parcelable.Creator<AuthorizationResp> = object : Parcelable.Creator<AuthorizationResp> {
            override fun createFromParcel(source: Parcel): AuthorizationResp {
                return AuthorizationResp(source)
            }

            override fun newArray(size: Int): Array<AuthorizationResp> {
                return newArray(size)
            }
        }
    }
}
