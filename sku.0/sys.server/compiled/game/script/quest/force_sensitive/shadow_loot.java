package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.ai_lib;
import script.library.xp;
import script.library.group;
import script.library.fs_dyn_village;

public class shadow_loot extends script.base_script
{
    public shadow_loot()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        fs_dyn_village.getRegisteredIntegerFromClusterWideData(fs_dyn_village.CLUSTER_INT_KEY_PHASE_UID, "findCurrentPhase", self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        fs_dyn_village.getRegisteredIntegerFromClusterWideData(fs_dyn_village.CLUSTER_INT_KEY_PHASE_UID, "findCurrentPhase", self);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int findCurrentPhase(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey(fs_dyn_village.CLUSTER_INT_KEY_PHASE_UID))
        {
            LOG(fs_dyn_village.LOG_CHAN, "fs_quest_player::msgValidateFSQuestPhase: -> Error getting current phase from cluster data.  Potentially on wrong quest phase.");
            return SCRIPT_CONTINUE;
        }
        int curUid = params.getInt(fs_dyn_village.CLUSTER_INT_KEY_PHASE_UID);
        int curPhase = fs_dyn_village.getPhaseFromVersionUid(curUid);
        setObjVar(self, "current_phase", curPhase);
        return SCRIPT_CONTINUE;
    }
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id corpseInventory = utils.getInventoryContainer(self);
        if (corpseInventory == null)
        {
            return SCRIPT_CONTINUE;
        }
        int phase = getIntObjVar(self, "current_phase");
        if (phase == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int success = 75;
        if (phase == 4)
        {
            success = 10;
        }
        int chance = rand(1, 100);
        if (chance <= success)
        {
            String treasure = pickLootItem();
            obj_id item = createObject(treasure, corpseInventory, "");
            if (isIdValid(item))
            {
                setSpecialResourceObjVars(treasure, item);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public String pickLootItem() throws InterruptedException
    {
        String treasure = "object/tangible/loot/quest/endrine.iff";
        int which = rand(1, 5);
        switch (which)
        {
            case 1:
            treasure = "object/tangible/loot/quest/ardanium_ii.iff";
            break;
            case 2:
            treasure = "object/tangible/loot/quest/endrine.iff";
            break;
            case 3:
            treasure = "object/tangible/loot/quest/ostrine.iff";
            break;
            case 4:
            treasure = "object/tangible/loot/quest/wind_crystal.iff";
            break;
            case 5:
            treasure = "object/tangible/loot/quest/rudic.iff";
            break;
        }
        return treasure;
    }
    public void setSpecialResourceObjVars(String special, obj_id resource) throws InterruptedException
    {
        if (!isIdValid(resource) || special == null || special.equals(""))
        {
            return;
        }
        final int average = 500;
        if (special.equals("object/tangible/loot/quest/ardanium_ii.iff"))
        {
            setObjVar(resource, "crafting_components.res_potential_energy", getResourceValue(average));
            setObjVar(resource, "crafting_components.res_quality", getResourceValue(average));
        }
        else if (special.equals("object/tangible/loot/quest/wind_crystal.iff"))
        {
            setObjVar(resource, "crafting_components.res_potential_energy", getResourceValue(average));
        }
        else if (special.equals("object/tangible/loot/quest/ostrine.iff"))
        {
            setObjVar(resource, "crafting_components.res_malleability", getResourceValue(average));
            setObjVar(resource, "crafting_components.res_quality", getResourceValue(average));
        }
        else if (special.equals("object/tangible/loot/quest/endrine.iff"))
        {
            setObjVar(resource, "crafting_components.res_malleability", getResourceValue(average));
            setObjVar(resource, "crafting_components.res_quality", getResourceValue(average));
            setObjVar(resource, "crafting_components.res_toughness", getResourceValue(average));
        }
        else if (special.equals("object/tangible/loot/quest/rudic.iff"))
        {
            setObjVar(resource, "crafting_components.res_conductivity", getResourceValue(average));
            setObjVar(resource, "crafting_components.res_decay_resist", getResourceValue(average));
            setObjVar(resource, "crafting_components.res_quality", getResourceValue(average));
            setObjVar(resource, "crafting_components.res_shock_resistance", getResourceValue(average));
        }
    }
    public int getResourceValue(int average) throws InterruptedException
    {
        float value = gaussRand(average, 50.0f);
        if (value > 1000.0f)
        {
            value = 1000.0f;
        }
        else if (value < 150.0f)
        {
            value = 150.0f;
        }
        return (int)(value);
    }
}
