package script.theme_park.dungeon.diant_zuy;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.locations;

public class generator extends script.base_script
{
    public generator()
    {
    }
    public static final String DIANT_BUNKER = "dungeon/diant_bunker";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        string_id generatorUse = new string_id(DIANT_BUNKER, "generator_use");
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, generatorUse);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        int genState = getIntObjVar(top, "diant.generator");
        if (item == menu_info_types.ITEM_USE)
        {
            if (genState == 1)
            {
                setObjVar(top, "diant.generator", 0);
                string_id generatorDeactivate = new string_id(DIANT_BUNKER, "generator_deactivate");
                sendSystemMessage(player, generatorDeactivate);
                location loc = getLocation(self);
                if (loc != null)
                {
                    playClientEffectLoc(player, "clienteffect/droid_effect_fog_machine.cef", loc, 0.f);
                }
                playClientEffectLoc(player, "clienteffect/door_open_hydraulic_style_01.cef", loc, 0.f);
            }
            if (genState == 0)
            {
                setObjVar(top, "diant.generator", 1);
                string_id generatorActivate = new string_id(DIANT_BUNKER, "generator_activate");
                sendSystemMessage(player, generatorActivate);
                location loc = getLocation(self);
                if (loc != null)
                {
                    playClientEffectLoc(player, "clienteffect/door_open_hydraulic_style_01.cef", loc, 0.f);
                }
                playMusic(player, "sound/amb_biogenics_reactor_bootup.snd");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
