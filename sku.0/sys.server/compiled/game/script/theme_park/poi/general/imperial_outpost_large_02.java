package script.theme_park.poi.general;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;

public class imperial_outpost_large_02 extends script.theme_park.poi.base
{
    public imperial_outpost_large_02()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("troopers"))
        {
            obj_id tower = poiCreateObject(self, "tower", "object/installation/turret/turret_tower_large.iff", 1, 8);
            obj_id cmdr = poiCreateObject(self, "cmdr", "object/creature/npc/theme_park/prefect_talmont.iff", 0, 0);
            setName(cmdr, "Major Derlin");
            obj_id st1 = poiCreateNpc("stormtrooper", 7, 5);
            obj_id st2 = poiCreateNpc("stormtrooper", 5, 7);
            obj_id st3 = poiCreateNpc("stormtrooper", 5, 5);
            obj_id st4 = poiCreateNpc("stormtrooper", 7, 7);
            obj_id st5 = poiCreateNpc("stormtrooper", -3, 5);
            obj_id st6 = poiCreateNpc("stormtrooper", -5, 7);
            obj_id st7 = poiCreateNpc("stormtrooper", -5, 5);
            obj_id st8 = poiCreateNpc("stormtrooper", -3, 7);
            obj_id st9 = poiCreateNpc("stormtrooper", -2, 12);
            obj_id st10 = poiCreateNpc("stormtrooper", 3, 12);
            obj_id st11 = poiCreateNpc("stormtrooper", -2, 2);
            obj_id st12 = poiCreateNpc("stormtrooper", 3, 2);
            if (poiGetDifficulty() == "veryHard")
            {
                obj_id atst = poiCreateObject("object/creature/npc/theme_park/at_st.iff", 10, 10);
                factions.setFaction(atst, factions.FACTION_IMPERIAL);
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
