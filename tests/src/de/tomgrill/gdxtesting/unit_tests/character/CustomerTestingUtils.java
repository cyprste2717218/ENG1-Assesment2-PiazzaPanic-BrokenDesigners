package de.tomgrill.gdxtesting.unit_tests.character;

import com.badlogic.gdx.math.Vector2;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.character.Customer;
import com.github.brokendesigners.enums.DifficultyLevel;
import com.github.brokendesigners.enums.GameMode;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.map.interactable.CustomerStation;
import org.mockito.Mockito;

public class CustomerTestingUtils {


    public static Customer createTestCustomer(Item item){
        CustomerStation customerStation = Mockito.mock(CustomerStation.class);
        Mockito.when(customerStation.getCustomerPosition()).thenReturn(new Vector2(10,5));
        Mockito.when(customerStation.getItemInHand()).thenReturn(item);
        Mockito.when(customerStation.hasEmptyHand()).thenReturn(item == null);
        return new Customer(customerStation, item, new Vector2(2,1), new Match(GameMode.SCENARIO, DifficultyLevel.MEDIUM, 5));
    }

    public static Customer createTestCustomer(){
        return createTestCustomer(ItemRegister.itemRegister.get("Burger"));
    }

}
