package com.fsep.sova.network.events.get_ticket;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingTicketErrorEvent extends BaseErrorEvent {
  public GettingTicketErrorEvent(int responseCode) {
    super(responseCode);
  }
}
