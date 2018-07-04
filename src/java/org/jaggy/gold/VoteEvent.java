package org.jaggy.gold;

import com.vexsoftware.votifier.VoteHandler;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Listens and handles what to do votes when we get one
 * @author Quirkylee
 */
public class VoteEvent implements Listener {
    /**
     * Reference pointer to the Main class
     */
    private Main plugin;

    /**
     * Class constructor
     * @param main passes pointer from the parent class
     */
    public VoteEvent(Main main) {
        plugin = main;
    }

    /**
     * Handles what to do when we get a vote
     * @param event
     */
    @EventHandler
    public void OnVote(VotifierEvent event) {
        Vote vote = event.getVote();
    }
}