package org.pokesplash.sts.api.event;

import org.pokesplash.sts.api.event.events.SellEvent;

/**
 * Class that is used to register and trigger all events.
 */
public abstract class StsEvents {
	public static Event<SellEvent> SELL = new Event<>();
}
