package script.event.ewok_festival;

import script.library.static_item;
import script.library.utils;
import script.*;

import java.util.Vector;

public class loveday_card_bundler extends script.base_script
{
    public loveday_card_bundler()
    {
    }
    public static final String LOVEDAY_CARD_01 = "item_event_loveday_card_01";
    public static final String LOVEDAY_CARD_02 = "item_event_loveday_card_02";
    public static final String LOVEDAY_CARD_03 = "item_event_loveday_card_03";
    public static final String LOVEDAY_CARD_04 = "item_event_loveday_card_04";
    public static final String LOVEDAY_CARD_05 = "item_event_loveday_card_05";
    public static final String LOVEDAY_CARD_06 = "item_event_loveday_card_06";
    public static final String LOVEDAY_CARD_07 = "item_event_loveday_card_07";
    public static final String LOVEDAY_CARDS_STACK = "item_event_loveday_card_stack";
    public static final String[] LOVEDAY_CARDS_COMPLETE_SET = 
    {
        LOVEDAY_CARD_01,
        LOVEDAY_CARD_02,
        LOVEDAY_CARD_03,
        LOVEDAY_CARD_04,
        LOVEDAY_CARD_05,
        LOVEDAY_CARD_06,
        LOVEDAY_CARD_07
    };
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canManipulate(player, self, true, true, 15, true))
        {
            if (utils.isNestedWithinAPlayer(self))
            {
                menu_info_data mid = mi.getMenuItemByType(menu_info_types.SERVER_MENU4);
                if (mid != null)
                {
                    mid.setServerNotify(true);
                }
                else 
                {
                    mi.addRootMenu(menu_info_types.SERVER_MENU4, new string_id("spam", "loveday_cards_bundle"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU4)
        {
            if (isIncapacitated(player) || isDead(player))
            {
                sendSystemMessage(player, new string_id("player_structure", "while_dead"));
                return SCRIPT_CONTINUE;
            }
            Vector cardsOwned = new Vector();
            cardsOwned.setSize(0);
            obj_id loveDayCard;
            for (String cardInSet : LOVEDAY_CARDS_COMPLETE_SET) {
                loveDayCard = utils.getStaticItemInInventory(player, cardInSet);
                if (isIdValid(loveDayCard)) {
                    utils.addElement(cardsOwned, loveDayCard);
                }
            }
            if (cardsOwned.size() == LOVEDAY_CARDS_COMPLETE_SET.length)
            {
                for (Object aCardsOwned : cardsOwned) {
                    decrementCount(((obj_id) aCardsOwned));
                }
                static_item.createNewItemFunction(LOVEDAY_CARDS_STACK, utils.getInventoryContainer(player));
                sendSystemMessage(player, new string_id("spam", "loveday_cards_set_bundled"));
            }
            else 
            {
                sendSystemMessage(player, new string_id("spam", "loveday_cards_set_incomplete"));
            }
        }
        return SCRIPT_CONTINUE;
    }
}
