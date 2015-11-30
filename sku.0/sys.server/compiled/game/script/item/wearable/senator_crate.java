package script.item.wearable;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.HashSet;
import script.library.static_item;
import script.library.utils;

public class senator_crate extends script.base_script
{
    public senator_crate()
    {
    }
    public static final String STF_FILE = "npe";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu2 = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id(STF_FILE, "crate_use"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            sendSystemMessage(player, new string_id(STF_FILE, "opened_crate"));
            grantSenatorClothes(player);
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void grantSenatorClothes(obj_id player) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        int pSpecies = getSpecies(player);
        String playerTemplate = getSkillTemplate(player);
        int pGender = getGender(player);
        HashSet theSet = new HashSet();
        if (pSpecies == SPECIES_WOOKIEE)
        {
            theSet.add(static_item.createNewItemFunction("item_senator_wookiee_robe", pInv));
        }
        else if (pSpecies == SPECIES_ITHORIAN)
        {
            theSet.add(static_item.createNewItemFunction("item_senator_ithorian_robe", pInv));
            theSet.add(static_item.createNewItemFunction("item_senator_ithorian_gloves", pInv));
        }
        else 
        {
            theSet.add(static_item.createNewItemFunction("item_senator_robe", pInv));
            theSet.add(static_item.createNewItemFunction("item_senator_gloves", pInv));
        }
        obj_id[] items = new obj_id[theSet.size()];
        theSet.toArray(items);
        showLootBox(player, items);
    }
}
