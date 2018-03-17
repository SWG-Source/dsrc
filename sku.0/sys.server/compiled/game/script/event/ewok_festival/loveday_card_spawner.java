package script.event.ewok_festival;

import script.*;
import script.library.groundquests;
import script.library.static_item;
import script.library.utils;

public class loveday_card_spawner extends script.base_script
{
    public loveday_card_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "initializeCardSpawner", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int initializeCardSpawner(obj_id self, dictionary params) throws InterruptedException
    {
        modifyPitch(self, 90);
        location here = getLocation(self);
        here.y = here.y + 0.05f;
        setLocation(self, here);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canManipulate(player, self, false, false, 15, true))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("spam", "loveday_cards_take"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (canManipulate(player, self, false, false, 15, true))
        {
            if (item == menu_info_types.ITEM_USE)
            {
                if (isIncapacitated(player) || isDead(player))
                {
                    sendSystemMessage(player, new string_id("player_structure", "while_dead"));
                    return SCRIPT_CONTINUE;
                }
                String cardType = getStringObjVar(self, "lovedayCardType");
                if (cardType != null && cardType.length() > 0)
                {
                    groundquests.sendPlacedInInventorySystemMessage(player, static_item.createNewItemFunction(cardType, utils.getInventoryContainer(player)));
                    destroyObject(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
