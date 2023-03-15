package de.tomgrill.gdxtesting.unit_tests.map.interactable;

import com.badlogic.gdx.graphics.Texture;
import com.github.brokendesigners.Hand;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.*;
import com.github.brokendesigners.map.interactable.CuttingStation;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class StationTests {
    @Test
    public void correctChoppedItemRegisterSwap()    {
        Texture fakeTexture = Mockito.mock(Texture.class);
        Item Onion = new Onion(fakeTexture);
        Item Cut_Onion = new Onion(fakeTexture);
        ItemRegister.addItem("Cut_Onion", Cut_Onion);

        assertEquals(Onion.name, ItemRegister.itemRegister.get("Cut_" + Onion.getName()).name);
    }
    @Test
    public void correctCookedItemRegisterSwap() {
        Texture fakeTexture = Mockito.mock(Texture.class);
        Item Patty = new Patty(fakeTexture);
        Item Cooked_Patty = new Cooked_Patty(fakeTexture);
        ItemRegister.addItem("Cooked_Patty", Cooked_Patty);

        assertEquals(Cooked_Patty.name,ItemRegister.itemRegister.get("Cooked_" + Patty.getName()).name);
    }
    @Test
    public void correctFormedItemRegisterSwap() {
        Texture fakeTexture = Mockito.mock(Texture.class);
        Item Meat = new Meat(fakeTexture);
        Item Patty = new Cooked_Patty(fakeTexture);
        ItemRegister.addItem("Patty", Patty);
        ItemRegister.addItem("Meat", Meat);

        Player fakePlayer = Mockito.mock(Player.class);
        fakePlayer.hand = new Hand();
        fakePlayer.hand.give(Meat);
        System.out.println("Hand: "+ fakePlayer.hand.heldItems.get(0).name);
        Item topOfHand = fakePlayer.hand.heldItems.get(0);
        // Testing the conditions made in CounterStation to check the item held is meat
        assertTrue(topOfHand.equals(ItemRegister.itemRegister.get("Meat")));

    }
    @Test
    public void testApplicable() {
        Texture fakeTexture = Mockito.mock(Texture.class);
        Item Onion = new Onion(fakeTexture);

        CuttingStation fakeCuttingStation = new CuttingStation();
        fakeCuttingStation.inuse = false;
        fakeCuttingStation.setName("Cutting_Station");
        assertTrue(fakeCuttingStation.Applicable(fakeCuttingStation.getCuttables(), "Cutting_Station",Onion.name));

    }
}
