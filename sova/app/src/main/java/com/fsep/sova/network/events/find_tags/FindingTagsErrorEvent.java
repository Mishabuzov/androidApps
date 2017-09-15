package com.fsep.sova.network.events.find_tags;

import com.fsep.sova.network.events.BaseErrorEvent;

public class FindingTagsErrorEvent extends BaseErrorEvent {
    public FindingTagsErrorEvent(int responseCode) {
        super(responseCode);
    }

    public FindingTagsErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
