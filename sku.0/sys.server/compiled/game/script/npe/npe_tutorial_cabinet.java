package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.npe;
import script.library.groundquests;
import script.library.sequencer;
import script.library.static_item;
import script.library.xp;

public class npe_tutorial_cabinet extends script.base_script
{
    public npe_tutorial_cabinet()
    {
    }
    public static final string_id OPEN_CABINET = new string_id("npe_hangar_1", "open_cabinet");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self, "cabinet");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "gotPistol"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "cabinetReady"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, OPEN_CABINET);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!utils.hasScriptVar(self, "gotPistol") && utils.hasScriptVar(self, "cabinetReady"))
            {
                destroyClientPath(player);
                obj_id playerInventory = utils.getInventoryContainer(player);
                obj_id pistol = static_item.createNewItemFunction("weapon_pistol_02_02", playerInventory);
                obj_id[] lootBoxItems = new obj_id[1];
                lootBoxItems[0] = pistol;
                showLootBox(player, lootBoxItems);
                attachScript(pistol, "npe.npe_tutorial_pistol");
                utils.setScriptVar(self, "gotPistol", true);
                obj_id building = getTopMostContainer(self);
                xp.grantXpByTemplate(player, 10);
                messageTo(building, "continueMainTable", null, 0, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
