package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.structure;
import script.library.player_structure;

public class poison_master extends script.base_script
{
    public poison_master()
    {
    }
    public static final String SCRIPT_POISON = "theme_park.dungeon.death_watch_bunker.death_watch_poison_player";
    public static final string_id ALLUM_WARNING = new string_id("dungeon/death_watch", "allum_warning");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "death_watch.air_vent_on"))
        {
            removeObjVar(self, "death_watch.air_vent_on");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id poscell = getCellId(self, "medroom38");
        if (destinationCell == poscell)
        {
            if (!hasScript(item, SCRIPT_POISON))
            {
                sendSystemMessage(item, ALLUM_WARNING);
                attachScript(item, SCRIPT_POISON);
                return SCRIPT_CONTINUE;
            }
        }
        obj_id clncell = getCellId(self, "hall32");
        if (destinationCell == clncell)
        {
            if (hasScript(item, SCRIPT_POISON))
            {
                detachScript(item, SCRIPT_POISON);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAirVentOff(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "death_watch.air_vent_on");
        return SCRIPT_CONTINUE;
    }
}
