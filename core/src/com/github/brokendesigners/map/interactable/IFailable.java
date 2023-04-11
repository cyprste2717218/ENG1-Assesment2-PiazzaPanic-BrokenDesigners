package com.github.brokendesigners.map.interactable;

import com.github.brokendesigners.Player;

public interface IFailable {

    public void handleStationInteraction();
    public boolean finishSuccessfulOperation(final Player player, float endingStationTime);
    public boolean finishFailedOperation(final Player player, float endingStationTime);
    public void generalFinish(Player player);


}
