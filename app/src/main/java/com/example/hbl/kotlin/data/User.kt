package com.example.hbl.kotlin.data

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by mingjun on 16/7/27.
 */
class User : Parcelable {

    /**
     * login : mingjunli
     * id : 6701864
     * avatar_url : https://avatars.githubusercontent.com/u/6701864?v=3
     * gravatar_id :
     * url : https://api.github.com/users/mingjunli
     * html_url : https://github.com/mingjunli
     * followers_url : https://api.github.com/users/mingjunli/followers
     * following_url : https://api.github.com/users/mingjunli/following{/other_user}
     * gists_url : https://api.github.com/users/mingjunli/gists{/gist_id}
     * starred_url : https://api.github.com/users/mingjunli/starred{/owner}{/repo}
     * subscriptions_url : https://api.github.com/users/mingjunli/subscriptions
     * organizations_url : https://api.github.com/users/mingjunli/orgs
     * repos_url : https://api.github.com/users/mingjunli/repos
     * events_url : https://api.github.com/users/mingjunli/events{/privacy}
     * received_events_url : https://api.github.com/users/mingjunli/received_events
     * type : User
     * site_admin : false
     * name : mingjun
     * company : null
     * blog : blog.lmj.wiki
     * location : Beijing China
     * email : xx
     * hireable : null
     * bio : A simple Android developer
     * public_repos : 17
     * public_gists : 0
     * followers : 2
     * following : 1
     * created_at : 2014-02-17T06:24:25Z
     * updated_at : 2016-07-19T10:19:19Z
     */

    var login=""
    var id: Int = 0
    var avatar_url=""
    var gravatar_id=""
    var url=""
    var html_url=""
    var followers_url=""
    var following_url=""
    var gists_url=""
    var starred_url=""
    var subscriptions_url=""
    var organizations_url=""
    var repos_url=""
    var events_url=""
    var received_events_url=""
    var type=""
    var isSite_admin: Boolean = false
    var name=""
    var company=""
    var blog=""
    var location=""
    var email=""
    var hireable=""
    var bio=""
    var public_repos: Int = 0
    var public_gists: Int = 0
    var followers: Int = 0
    var following: Int = 0
    var created_at=""
    var updated_at=""

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.login)
        dest.writeInt(this.id)
        dest.writeString(this.avatar_url)
        dest.writeString(this.gravatar_id)
        dest.writeString(this.url)
        dest.writeString(this.html_url)
        dest.writeString(this.followers_url)
        dest.writeString(this.following_url)
        dest.writeString(this.gists_url)
        dest.writeString(this.starred_url)
        dest.writeString(this.subscriptions_url)
        dest.writeString(this.organizations_url)
        dest.writeString(this.repos_url)
        dest.writeString(this.events_url)
        dest.writeString(this.received_events_url)
        dest.writeString(this.type)
        dest.writeByte(if (this.isSite_admin) 1.toByte() else 0.toByte())
        dest.writeString(this.name)
        dest.writeString(this.company)
        dest.writeString(this.blog)
        dest.writeString(this.location)
        dest.writeString(this.email)
        dest.writeString(this.hireable)
        dest.writeString(this.bio)
        dest.writeInt(this.public_repos)
        dest.writeInt(this.public_gists)
        dest.writeInt(this.followers)
        dest.writeInt(this.following)
        dest.writeString(this.created_at)
        dest.writeString(this.updated_at)
    }


    protected constructor(parcel: Parcel) {
        this.login = parcel.readString()
        this.id = parcel.readInt()
        this.avatar_url = parcel.readString()
        this.gravatar_id = parcel.readString()
        this.url = parcel.readString()
        this.html_url = parcel.readString()
        this.followers_url = parcel.readString()
        this.following_url = parcel.readString()
        this.gists_url = parcel.readString()
        this.starred_url = parcel.readString()
        this.subscriptions_url = parcel.readString()
        this.organizations_url = parcel.readString()
        this.repos_url = parcel.readString()
        this.events_url = parcel.readString()
        this.received_events_url = parcel.readString()
        this.type = parcel.readString()
        this.isSite_admin = parcel.readByte().toInt() != 0
        this.name = parcel.readString()
        this.company = parcel.readString()
        this.blog = parcel.readString()
        this.location = parcel.readString()
        this.email = parcel.readString()
        this.hireable = parcel.readString()
        this.bio = parcel.readString()
        this.public_repos = parcel.readInt()
        this.public_gists = parcel.readInt()
        this.followers = parcel.readInt()
        this.following = parcel.readInt()
        this.created_at = parcel.readString()
        this.updated_at = parcel.readString()
    }

    companion object {

        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User {
                return User(source)
            }

            override fun newArray(size: Int): Array<User> {
                return newArray(size)
            }
        }
    }
}
