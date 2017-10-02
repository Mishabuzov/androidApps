package com.kpfu.mikhail.vk.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.content.Group;
import com.kpfu.mikhail.vk.content.News;
import com.kpfu.mikhail.vk.content.NewsItem;
import com.kpfu.mikhail.vk.content.NewsLocal;
import com.kpfu.mikhail.vk.content.Profile;
import com.kpfu.mikhail.vk.content.attachments.Attachment;
import com.kpfu.mikhail.vk.exceptions.IncorrectParsingDataException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.kpfu.mikhail.vk.content.attachments.AttachmentType.PHOTO;
import static com.kpfu.mikhail.vk.content.attachments.AttachmentType.VIDEO;

public class ConvertUtils {

    public static List<NewsLocal> convertResponseIntoAdapterModel(@NonNull News newsResponse,
                                                                  @NonNull Context context)
            throws IncorrectParsingDataException {
        List<NewsLocal> news = new ArrayList<>();
        List<Attachment> badAttachments = new ArrayList<>();
        for (NewsItem newsItem : newsResponse.getItems()) {
            filterAttachments(newsItem.getAttachments(), badAttachments);
            if (!newsItem.getText().isEmpty() || newsItem.getAttachments() != null
                    && !newsItem.getAttachments().isEmpty()) {
                NewsLocal newsLocal = new NewsLocal();
                setAuthorInfoById(newsItem.getSourceId(), newsResponse, newsLocal, context);
                newsLocal.setAttachments(newsItem.getAttachments());
                newsLocal.setDate(convertMillisToHumanReadableFormat(newsItem.getDate(), context));
                newsLocal.setLiked(newsItem.getLikes().getLikeStatus().isLiked());
                newsLocal.setLikesCount(newsItem.getLikes().getCount());
//            newsLocal.setViewsCount(newsItem.getViews().getCount());
                newsLocal.setText(newsItem.getText());
                news.add(newsLocal);
            }
        }
        return news;
    }

    private static void filterAttachments(List<Attachment> attachments,
                                          List<Attachment> badAttachments) {
        if (attachments != null) {
            for (Attachment attachment : attachments) {
                if (attachment.getType() != PHOTO && attachment.getType() != VIDEO) {
                    badAttachments.add(attachment);
                }
            }
            attachments.removeAll(badAttachments);
            badAttachments.clear();
        }
    }

    private static void setAuthorInfoById(long authorId,
                                          @NonNull News news,
                                          @NonNull NewsLocal newsLocal,
                                          @NonNull Context context) throws IncorrectParsingDataException {
        if (authorId > 0) {
            Profile profile = findUserById(authorId, news.getProfiles(), context);
            newsLocal.setAuthorName(
                    String.format(context.getString(R.string.name_format),
                            profile.getFirstName(),
                            profile.getLastName()));
            newsLocal.setAvatar(profile.getAvatar());
        } else {
            Group group = findGroupById(authorId * (-1), news.getGroups(), context);
            newsLocal.setAuthorName(group.getName());
            newsLocal.setAvatar(group.getAvatar());
        }
    }

    private static Profile findUserById(long currentId,
                                        @NonNull List<Profile> profiles,
                                        @NonNull Context context) throws IncorrectParsingDataException {
        for (Profile profile : profiles) {
            if (currentId == profile.getId()) {
                return profile;
            }
        }
        throw new IncorrectParsingDataException(context
                .getString(R.string.incorrect_profile_exception));
    }

    private static Group findGroupById(long currentId,
                                       @NonNull List<Group> groups,
                                       @NonNull Context context) throws IncorrectParsingDataException {
        for (Group group : groups) {
            if (currentId == group.getId()) {
                return group;
            }
        }
        throw new IncorrectParsingDataException(context
                .getString(R.string.incorrect_group_exception));
    }

    private static String convertMillisToHumanReadableFormat(long sec,
                                                             @NonNull Context context) {
        Date date = new Date(sec*1000);
        SimpleDateFormat dateCalendar = new SimpleDateFormat(
                context.getString(R.string.date_format), Locale.ENGLISH);
        SimpleDateFormat dateTime = new SimpleDateFormat(
                context.getString(R.string.time_format), Locale.ENGLISH);
        String dateText = dateCalendar.format(date);
        String timeText = dateTime.format(date);
        return String.format(context.getString(R.string.feed_full_date_format), dateText, timeText);
    }

  /*  public static String convertMillisToHourReadableFormat(long millis) {
        long hoursLong = TimeUnit.MILLISECONDS.toHours(millis);
        long minutesLong = TimeUnit.MILLISECONDS.toMinutes(millis)
                - TimeUnit.HOURS.toMinutes(hoursLong);
        long secondsLong = TimeUnit.MILLISECONDS.toSeconds(millis)
                - TimeUnit.HOURS.toSeconds(hoursLong) - TimeUnit.MINUTES.toSeconds(minutesLong);
        String hours = String.valueOf(hoursLong);
        String minutes = String.valueOf(minutesLong);
        String seconds = String.valueOf(secondsLong);
        if (hoursLong < 10) {
            hours = "0" + hours;
        }
        if (minutesLong < 10) {
            minutes = "0" + minutes;
        }
        if (secondsLong < 10) {
            seconds = "0" + seconds;
        }
        return String.format("%s:%s:%s",
                hours, minutes, seconds);
    }*/

}
