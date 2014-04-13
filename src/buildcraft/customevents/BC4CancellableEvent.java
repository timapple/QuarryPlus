package buildcraft.customevents;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BC4CancellableEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	public HandlerList getHandlers() {
		return handlers;
	}
    
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	private boolean cancelled;

	public boolean isCancelled() {
		return cancelled;
	}


	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
}
