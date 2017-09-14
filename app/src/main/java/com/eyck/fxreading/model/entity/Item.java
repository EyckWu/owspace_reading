package com.eyck.fxreading.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eyck on 2017/9/6.
 */

public class Item implements Parcelable {

    private String id;
    private String uid;
    private String name;
    private String title;
    private String excerpt;
    private String lead;
    private String model;
    private String position;
    private String thumbnail;
    private String create_time;
    private String update_time;
    private String status;
    private String video;
    private String fm;
    private String link_url;
    private String publish_time;
    private String view;
    private String share;
    private String comment;
    private String good;
    private String bookmark;
    private String show_uid;
    private String fm_play;
    private String html5;
    private String author;
    private int tpl;
    private String avatar;
    private String category;
    private int parseXML;
    /**
     * name : 美国精神
     */

    private List<TagsBean> tags;
    /**
     * id : 39457
     * pid : 0
     * uid : 218409
     * content : 我们不要陷入自我的陷阱。唯有务实才是真正的办法
     * post_id : 292322
     * update_time : 2 天前
     * good : 6
     * model : 3
     * to_author_name : Sying
     * under_id : 0
     * nickname : Dolan Yves
     * avatar : http://img.owspace.com//@/avatar/2016-04-19/46efbcfbfd6809f2eb2b34ab01ee874a45
     */

    private List<HotCommentsBean> hot_comments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getFm() {
        return fm;
    }

    public void setFm(String fm) {
        this.fm = fm;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    public String getShow_uid() {
        return show_uid;
    }

    public void setShow_uid(String show_uid) {
        this.show_uid = show_uid;
    }

    public String getFm_play() {
        return fm_play;
    }

    public void setFm_play(String fm_play) {
        this.fm_play = fm_play;
    }

    public String getHtml5() {
        return html5;
    }

    public void setHtml5(String html5) {
        this.html5 = html5;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTpl() {
        return tpl;
    }

    public void setTpl(int tpl) {
        this.tpl = tpl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getParseXML() {
        return parseXML;
    }

    public void setParseXML(int parseXML) {
        this.parseXML = parseXML;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<HotCommentsBean> getHot_comments() {
        return hot_comments;
    }

    public void setHot_comments(List<HotCommentsBean> hot_comments) {
        this.hot_comments = hot_comments;
    }

    public static class TagsBean implements Parcelable {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
        }

        public TagsBean() {
        }

        protected TagsBean(Parcel in) {
            this.name = in.readString();
        }

        public static final Creator<TagsBean> CREATOR = new Creator<TagsBean>() {
            @Override
            public TagsBean createFromParcel(Parcel source) {
                return new TagsBean(source);
            }

            @Override
            public TagsBean[] newArray(int size) {
                return new TagsBean[size];
            }
        };
    }

    public static class HotCommentsBean implements Parcelable {
        private String id;
        private String pid;
        private String uid;
        private String content;
        private String post_id;
        private String update_time;
        private String good;
        private String model;
        private String to_author_name;
        private String under_id;
        private String nickname;
        private String avatar;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPost_id() {
            return post_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getGood() {
            return good;
        }

        public void setGood(String good) {
            this.good = good;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getTo_author_name() {
            return to_author_name;
        }

        public void setTo_author_name(String to_author_name) {
            this.to_author_name = to_author_name;
        }

        public String getUnder_id() {
            return under_id;
        }

        public void setUnder_id(String under_id) {
            this.under_id = under_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.pid);
            dest.writeString(this.uid);
            dest.writeString(this.content);
            dest.writeString(this.post_id);
            dest.writeString(this.update_time);
            dest.writeString(this.good);
            dest.writeString(this.model);
            dest.writeString(this.to_author_name);
            dest.writeString(this.under_id);
            dest.writeString(this.nickname);
            dest.writeString(this.avatar);
        }

        public HotCommentsBean() {
        }

        protected HotCommentsBean(Parcel in) {
            this.id = in.readString();
            this.pid = in.readString();
            this.uid = in.readString();
            this.content = in.readString();
            this.post_id = in.readString();
            this.update_time = in.readString();
            this.good = in.readString();
            this.model = in.readString();
            this.to_author_name = in.readString();
            this.under_id = in.readString();
            this.nickname = in.readString();
            this.avatar = in.readString();
        }

        public static final Creator<HotCommentsBean> CREATOR = new Creator<HotCommentsBean>() {
            @Override
            public HotCommentsBean createFromParcel(Parcel source) {
                return new HotCommentsBean(source);
            }

            @Override
            public HotCommentsBean[] newArray(int size) {
                return new HotCommentsBean[size];
            }
        };
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.uid);
        dest.writeString(this.name);
        dest.writeString(this.title);
        dest.writeString(this.excerpt);
        dest.writeString(this.lead);
        dest.writeString(this.model);
        dest.writeString(this.position);
        dest.writeString(this.thumbnail);
        dest.writeString(this.create_time);
        dest.writeString(this.update_time);
        dest.writeString(this.status);
        dest.writeString(this.video);
        dest.writeString(this.fm);
        dest.writeString(this.link_url);
        dest.writeString(this.publish_time);
        dest.writeString(this.view);
        dest.writeString(this.share);
        dest.writeString(this.comment);
        dest.writeString(this.good);
        dest.writeString(this.bookmark);
        dest.writeString(this.show_uid);
        dest.writeString(this.fm_play);
        dest.writeString(this.html5);
        dest.writeString(this.author);
        dest.writeInt(this.tpl);
        dest.writeString(this.avatar);
        dest.writeString(this.category);
        dest.writeInt(this.parseXML);
        dest.writeList(this.tags);
        dest.writeList(this.hot_comments);
    }

    public Item() {
    }

    protected Item(Parcel in) {
        this.id = in.readString();
        this.uid = in.readString();
        this.name = in.readString();
        this.title = in.readString();
        this.excerpt = in.readString();
        this.lead = in.readString();
        this.model = in.readString();
        this.position = in.readString();
        this.thumbnail = in.readString();
        this.create_time = in.readString();
        this.update_time = in.readString();
        this.status = in.readString();
        this.video = in.readString();
        this.fm = in.readString();
        this.link_url = in.readString();
        this.publish_time = in.readString();
        this.view = in.readString();
        this.share = in.readString();
        this.comment = in.readString();
        this.good = in.readString();
        this.bookmark = in.readString();
        this.show_uid = in.readString();
        this.fm_play = in.readString();
        this.html5 = in.readString();
        this.author = in.readString();
        this.tpl = in.readInt();
        this.avatar = in.readString();
        this.category = in.readString();
        this.parseXML = in.readInt();
        this.tags = new ArrayList<TagsBean>();
        in.readList(this.tags, TagsBean.class.getClassLoader());
        this.hot_comments = new ArrayList<HotCommentsBean>();
        in.readList(this.hot_comments, HotCommentsBean.class.getClassLoader());
    }
}
