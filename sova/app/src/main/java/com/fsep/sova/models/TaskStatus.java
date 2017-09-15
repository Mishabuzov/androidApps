package com.fsep.sova.models;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fsep.sova.R;

/**
 * Статус выполнения задачи
 */
public enum TaskStatus {

  //Статусы задачи
  HUNTING("hunting", R.string.status_hunting, R.color.status_hunting_color),
  WORKING("working", R.string.status_working, R.color.status_working_color),
  ACCEPTED("accepted", R.string.status_accepted, R.color.status_accepted_color),
  DONE("done", R.string.status_done, R.color.status_done_color),

  //Статусы событий
  SEARCH("search", R.string.status_search, R.color.status_hunting_color),
  GOING("going", R.string.status_going, R.color.status_working_color),
  PASSED("passed", R.string.status_passed, R.color.status_done_color);

  private String mValue;
  private int mHumanReadableValue;
  private int mStatusColor;

  TaskStatus(String value, @StringRes int humanReadableValue, @ColorRes int statusColor) {
    mHumanReadableValue = humanReadableValue;
    mStatusColor = statusColor;
    mValue = value;
  }

  public int getHumanReadableValue(){
    return mHumanReadableValue;
  }

  public int getStatusColor(){
    return mStatusColor;
  }

  public static TaskStatus getEnum(String value) {
    for (TaskStatus v : values()) {
      if (v.toString().equalsIgnoreCase(value)) {
        return v;
      }
    }
    //TODO DEBUG;
    return null;
    //        throw new IllegalArgumentException();
  }

  @Override @JsonValue public String toString() {
    return mValue;
  }

}
