package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Note implements Parcelable {

    /**
     * Тип записи
     */
    protected NoteType noteType;

    /**
     * Идентификатор задачи
     */
    private long id;

    /**
     * Дата/время публикации в формате UNIX TIME
     */
    private long publishedTime;

    /**
     * Опубликована, или нет
     */
    private boolean published; //*

    /**
     * Данные автора задачи
     */
    private UserInfo author;

    /**
     * Обложка задачи
     */
    private Photo cover;

    /**
     * Заголовок задачи
     */
    private String title;

    /**
     * Текст задачи
     */
    private String text;

    /**
     * Тип задачи - public/private
     */
    private TaskType type;

    /**
     * Список тегов
     */
    private List<String> tags; //*

    /**
     * Место локации задачи
     */
    private Place place;

    /**
     * Количество лайков
     */
    private int likesCount;

    /**
     * Количество комментариев
     */
    private int commentsCount;

    /*
     * Имеет значение true, если пользователь лайкнул задачу. false - в противном случае
     */
    private boolean isLiked;

    /*
     * Имеет значение true, если пользователь добавил задачу в избранное. false - в противном случае
     */
    private boolean favorited;

    /**
     * Прикрепленные файлы
     */
    private Media media;

    /**
     * Количество пользователей, откликнувшихся на задачу
     */
    private int respondersCount;

    /*
     * Имеет значение true, если пользователь откликнулся на задачу, false - в противном случае
     */
    private boolean responsed;

    /**
     * Статус выполнения задачи, события
     */
    private TaskStatus status;

    /**
     * Мой отклик. Поле заданно, только если пользователь, запрашивающий задачу, сделал на нее отклик.
     */
    private ResponseOnTaskInTask response;

    /**
     * Дедлайн задачи - в формате UNIX TIME
     */
    private long deadline;

    /**
     * Исполнитель
     */
    private UserInfo performer;

    /**
     * Количество участников события
     */
    private int participantsCount;

    /**
     * Дедлайн события - в формате UNIX TIME
     */
    private long endingTime;


    /**
     * принимает ли юзер участие в событии или нет
     */
    private boolean participates;


    public Note() {
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    public int getRespondersCount() {
        return respondersCount;
    }

    public void setRespondersCount(int respondersCount) {
        this.respondersCount = respondersCount;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public boolean isResponsed() {
        return responsed;
    }

    public void setResponsed(boolean responsed) {
        this.responsed = responsed;
    }

    public ResponseOnTaskInTask getResponse() {
        return response;
    }

    public void setResponse(ResponseOnTaskInTask response) {
        this.response = response;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public UserInfo getPerformer() {
        return performer;
    }

    public void setPerformer(UserInfo performer) {
        this.performer = performer;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }

    public long getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(long endingTime) {
        this.endingTime = endingTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(long publishedTime) {
        this.publishedTime = publishedTime;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public UserInfo getAuthor() {
        return author;
    }

    public void setAuthor(UserInfo author) {
        this.author = author;
    }

    public Photo getCover() {
        return cover;
    }

    public void setCover(Photo cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public boolean isParticipates() {
        return participates;
    }

    public void setParticipates(boolean participates) {
        this.participates = participates;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.noteType == null ? -1 : this.noteType.ordinal());
        dest.writeLong(this.id);
        dest.writeLong(this.publishedTime);
        dest.writeByte(this.published ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.author, flags);
        dest.writeParcelable(this.cover, flags);
        dest.writeString(this.title);
        dest.writeString(this.text);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeStringList(this.tags);
        dest.writeParcelable(this.place, flags);
        dest.writeInt(this.likesCount);
        dest.writeInt(this.commentsCount);
        dest.writeByte(this.isLiked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.favorited ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.media, flags);
        dest.writeInt(this.respondersCount);
        dest.writeByte(this.responsed ? (byte) 1 : (byte) 0);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
        dest.writeParcelable(this.response, flags);
        dest.writeLong(this.deadline);
        dest.writeParcelable(this.performer, flags);
        dest.writeInt(this.participantsCount);
        dest.writeLong(this.endingTime);
        dest.writeByte(this.participates ? (byte) 1 : (byte) 0);
    }

    protected Note(Parcel in) {
        int tmpNoteType = in.readInt();
        this.noteType = tmpNoteType == -1 ? null : NoteType.values()[tmpNoteType];
        this.id = in.readLong();
        this.publishedTime = in.readLong();
        this.published = in.readByte() != 0;
        this.author = in.readParcelable(UserInfo.class.getClassLoader());
        this.cover = in.readParcelable(Photo.class.getClassLoader());
        this.title = in.readString();
        this.text = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : TaskType.values()[tmpType];
        this.tags = in.createStringArrayList();
        this.place = in.readParcelable(Place.class.getClassLoader());
        this.likesCount = in.readInt();
        this.commentsCount = in.readInt();
        this.isLiked = in.readByte() != 0;
        this.favorited = in.readByte() != 0;
        this.media = in.readParcelable(Media.class.getClassLoader());
        this.respondersCount = in.readInt();
        this.responsed = in.readByte() != 0;
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : TaskStatus.values()[tmpStatus];
        this.response = in.readParcelable(ResponseOnTaskInTask.class.getClassLoader());
        this.deadline = in.readLong();
        this.performer = in.readParcelable(UserInfo.class.getClassLoader());
        this.participantsCount = in.readInt();
        this.endingTime = in.readLong();
        this.participates = in.readByte() != 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
