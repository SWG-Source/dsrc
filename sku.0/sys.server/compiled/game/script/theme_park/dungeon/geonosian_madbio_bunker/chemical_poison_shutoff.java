package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class chemical_poison_shutoff extends script.base_script
{
    public chemical_poison_shutoff()
    {
    }
    public static final String MSGS = "dungeon/geonosian_madbio";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        setOwner(self, player);
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid == null)
        {
            return SCRIPT_CONTINUE;
        }
        mid.setServerNotify(true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id trap = getObjIdObjVar(self, "trap");
            string_id liquid = new string_id(MSGS, "wrong_chem");
            sendSystemMessage(player, liquid);
        }
        return SCRIPT_CONTINUE;
    }
    public void shutoffTrap(obj_id trap, obj_id player) throws InterruptedException
    {
        if (!hasObjVar(trap, "trap_off"))
        {
            dictionary test = new dictionary();
            test.put("player", player);
            messageTo(trap, "trapShutOff", test, 1, true);
            string_id stabilizer = new string_id(MSGS, "stabilizer");
            sendSystemMessage(player, stabilizer);
        }
        else 
        {
            string_id clean = new string_id(MSGS, "clean");
            sendSystemMessage(player, clean);
        }
        return;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id giver, obj_id item) throws InterruptedException
    {
        obj_id trap = getObjIdObjVar(self, "trap");
        String templateName = getTemplateName(item);
        if (!templateName.equals("object/tangible/dungeon/poison_stabilizer.iff"))
        {
            string_id tooBig = new string_id(MSGS, "too_big");
            sendSystemMessage(giver, tooBig);
            return SCRIPT_OVERRIDE;
        }
        else 
        {
            shutoffTrap(trap, giver);
            dictionary webster = new dictionary();
            webster.put("chemical", item);
            messageTo(self, "destroyPoisonStabilizer", webster, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int destroyPoisonStabilizer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id chemical = params.getObjId("chemical");
        destroyObject(chemical);
        return SCRIPT_CONTINUE;
    }
}
