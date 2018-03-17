package script.theme_park.dungeon.diant_zuy;

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

public class explosion_master extends script.base_script
{
    public explosion_master()
    {
    }
    public static final String SCRIPT_EXPLOSION = "theme_park.dungeon.diant_zuy.explosion_player";
    public static final string_id EXPLOSION_WARNING = new string_id("dungeon/diant_bunker", "explosion_warning");
    public int OnAboutToLoseItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id entryb = getCellId(self, "entryb");
        obj_id guardroom = getCellId(self, "guardroom");
        obj_id mess = getCellId(self, "mess");
        obj_id armory = getCellId(self, "armory");
        obj_id computer = getCellId(self, "computer");
        obj_id lair = getCellId(self, "lair");
        obj_id generator = getCellId(self, "generator");
        obj_id planning = getCellId(self, "planning");
        obj_id barracks = getCellId(self, "barracks");
        obj_id pantry = getCellId(self, "pantry");
        obj_id entry = getCellId(self, "entry");
        obj_id bldg = getTopMostContainer(self);
        int exploding = getIntObjVar(bldg, "diant.isExploding");
        if (exploding == 1)
        {
            if (destinationCell == entryb || destinationCell == guardroom || destinationCell == mess || destinationCell == armory || destinationCell == computer || destinationCell == lair || destinationCell == generator || destinationCell == planning || destinationCell == barracks || destinationCell == pantry)
            {
                if (!hasScript(item, SCRIPT_EXPLOSION))
                {
                    sendSystemMessage(item, EXPLOSION_WARNING);
                    attachScript(item, SCRIPT_EXPLOSION);
                    return SCRIPT_CONTINUE;
                }
            }
            if (destinationCell == entry)
            {
                detachScript(item, SCRIPT_EXPLOSION);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
