package org.jaggy.gold;

import com.vexsoftware.votifier.VoteHandler;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VoteEvent implements Listener {
    private Main plugin;
    
    public VoteEvent(Main main) {
        plugin = main;
    }

    @EventHandler
    public void OnVote(VotifierEvent event) {
        Vote vote = event.getVote();
    }
}
