package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.sui;
import script.library.groundquests;
import script.library.create;

public class comm_array extends script.base_script
{
    public comm_array()
    {
    }
    public static final string_id SID_MNU_HACK = new string_id("restuss_event/object", "hack_comm");
    public static final string_id SID_NO_HACK = new string_id("restuss_event/object", "cant_hack");
    public static final string_id SID_BEING_HACKED = new string_id("restuss_event/object", "being_hacked");
    public static final string_id SID_HACK_PROGRESS = new string_id("restuss_event/object", "hack_in_progress");
    public static final string_id SID_HACK_INTERRUPTED = new string_id("restuss_event/object", "hack_interrupted");
    public static final string_id SID_OWN_ALREADY = new string_id("restuss_event/object", "own_already");
    public static final String VOLUME_NAME = "hacker_area";
    public static final String OBJ_FACTION = "faction";
    public static final String FAC_REBEL = "Rebel";
    public static final String FAC_IMPERIAL = "Imperial";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        utils.removeObjVar(self, "spawned0");
        utils.removeObjVar(self, "spawned1");
        utils.removeObjVar(self, "spawned2");
        utils.removeObjVar(self, "spawned3");
        utils.removeObjVar(self, "spawned4");
        utils.removeObjVar(self, "spawned5");
        utils.removeObjVar(self, "spawned6");
        utils.removeObjVar(self, "spawned7");
        utils.removeObjVar(self, "spawned8");
        utils.removeObjVar(self, "spawned9");
        utils.removeObjVar(self, "spawned10");
        createTriggerVolume(VOLUME_NAME, 5.0f, true);
        messageTo(self, "checkFactionInitial", null, 10, false);
        if (!utils.hasObjVar(self, OBJ_FACTION))
        {
            utils.setObjVar(self, OBJ_FACTION, FAC_IMPERIAL);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        utils.removeObjVar(self, "spawned0");
        utils.removeObjVar(self, "spawned1");
        utils.removeObjVar(self, "spawned2");
        utils.removeObjVar(self, "spawned3");
        utils.removeObjVar(self, "spawned4");
        utils.removeObjVar(self, "spawned5");
        utils.removeObjVar(self, "spawned6");
        utils.removeObjVar(self, "spawned7");
        utils.removeObjVar(self, "spawned8");
        utils.removeObjVar(self, "spawned9");
        utils.removeObjVar(self, "spawned10");
        messageTo(self, "checkFactionInitial", null, 15, false);
        if (!hasTriggerVolume(self, VOLUME_NAME))
        {
            createTriggerVolume(VOLUME_NAME, 5.0f, true);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "cantHack"))
        {
            sendSystemMessage(player, SID_NO_HACK);
            return SCRIPT_OVERRIDE;
        }
        if (utils.hasScriptVar(self, "beingHacked"))
        {
            sendSystemMessage(player, SID_BEING_HACKED);
            return SCRIPT_OVERRIDE;
        }
        else if (!isDead(player) && !isIncapacitated(player))
        {
            if ((getStringObjVar(self, OBJ_FACTION)).equals(FAC_REBEL))
            {
                if (groundquests.isTaskActive(player, "restuss_imperial_st3_comm_array", "hackCommImperial1"))
                {
                    mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_HACK);
                    return SCRIPT_CONTINUE;
                }
                else if (groundquests.isTaskActive(player, "restuss_imperial_st3_comm_array_spy", "hackCommImperialSpy1"))
                {
                    mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_HACK);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    sendSystemMessage(player, SID_OWN_ALREADY);
                    return SCRIPT_CONTINUE;
                }
            }
            else if ((getStringObjVar(self, OBJ_FACTION)).equals(FAC_IMPERIAL))
            {
                if (groundquests.isTaskActive(player, "restuss_rebel_st3_comm_array", "hackCommRebel1"))
                {
                    mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_HACK);
                    return SCRIPT_CONTINUE;
                }
                else if (groundquests.isTaskActive(player, "restuss_rebel_st3_comm_array_spy", "hackCommRebelSpy1"))
                {
                    mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_HACK);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    sendSystemMessage(player, SID_OWN_ALREADY);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!isDead(player) && !isIncapacitated(player))
            {
                if ((getStringObjVar(self, OBJ_FACTION)).equals(FAC_REBEL))
                {
                    if (groundquests.isTaskActive(player, "restuss_imperial_st3_comm_array", "hackCommImperial1"))
                    {
                        utils.setScriptVar(self, "beingHacked", player);
                        sendSystemMessage(player, SID_HACK_PROGRESS);
                        messageTo(self, "checkForIncap", null, 2.0f, false);
                        messageTo(self, "hackComplete", null, 40.0f, false);
                        return SCRIPT_CONTINUE;
                    }
                    else if (groundquests.isTaskActive(player, "restuss_imperial_st3_comm_array_spy", "hackCommImperialSpy1"))
                    {
                        utils.setScriptVar(self, "beingHacked", player);
                        sendSystemMessage(player, SID_HACK_PROGRESS);
                        messageTo(self, "checkForIncap", null, 2.0f, false);
                        messageTo(self, "hackComplete", null, 20.0f, false);
                        return SCRIPT_CONTINUE;
                    }
                }
                else if ((getStringObjVar(self, OBJ_FACTION)).equals(FAC_IMPERIAL))
                {
                    if (groundquests.isTaskActive(player, "restuss_rebel_st3_comm_array", "hackCommRebel1"))
                    {
                        utils.setScriptVar(self, "beingHacked", player);
                        sendSystemMessage(player, SID_HACK_PROGRESS);
                        messageTo(self, "checkForIncap", null, 2.0f, false);
                        messageTo(self, "hackComplete", null, 40.0f, false);
                        return SCRIPT_CONTINUE;
                    }
                    else if (groundquests.isTaskActive(player, "restuss_rebel_st3_comm_array_spy", "hackCommRebelSpy1"))
                    {
                        utils.setScriptVar(self, "beingHacked", player);
                        sendSystemMessage(player, SID_HACK_PROGRESS);
                        messageTo(self, "checkForIncap", null, 2.0f, false);
                        messageTo(self, "hackComplete", null, 20.0f, false);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (breacher == utils.getObjIdScriptVar(self, "beingHacked"))
        {
            sendSystemMessage(breacher, SID_HACK_INTERRUPTED);
            utils.removeScriptVar(self, "beingHacked");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnCreatures(int x, String datatable, obj_id self) throws InterruptedException
    {
        String spawn = dataTableGetString(datatable, x, "spawns");
        float xCoord = dataTableGetFloat(datatable, x, "loc_x");
        float yCoord = dataTableGetFloat(datatable, x, "loc_y");
        float zCoord = dataTableGetFloat(datatable, x, "loc_z");
        location myself = getLocation(self);
        String planet = myself.area;
        location spawnPoint = new location(xCoord, yCoord, zCoord, planet);
        obj_id spawnedCreature = create.object(spawn, spawnPoint);
        String creatureName = dataTableGetString(datatable, x, "name");
        if (creatureName != null && !creatureName.equals(""))
        {
            setName(spawnedCreature, creatureName);
        }
        setObjVar(spawnedCreature, "spawn_number", x);
        setObjVar(spawnedCreature, "mom", self);
        setObjVar(self, "spawned" + x, spawnedCreature);
        attachScript(spawnedCreature, "theme_park.restuss_event.comm_array_spawn_died");
        if (ai_lib.isNpc(spawnedCreature))
        {
            ai_lib.setDefaultCalmBehavior(spawnedCreature, ai_lib.BEHAVIOR_SENTINEL);
            aiEquipPrimaryWeapon(spawnedCreature);
        }
        String scriptList = dataTableGetString(datatable, x, "script");
        if (scriptList != null && !scriptList.equals(""))
        {
            String[] scriptArray = split(scriptList, ',');
            for (int i = 0; i < scriptArray.length; i++)
            {
                attachScript(spawnedCreature, scriptArray[i]);
            }
        }
        if (dataTableHasColumn(datatable, "yaw"))
        {
            float spawnYaw = dataTableGetFloat(datatable, x, "yaw");
            if (spawnYaw != 0)
            {
                setYaw(spawnedCreature, spawnYaw);
            }
        }
        if (dataTableHasColumn(datatable, "mood"))
        {
            String creatureMood = dataTableGetString(datatable, x, "mood");
            if (creatureMood != null && !creatureMood.equals(""))
            {
                ai_lib.setDefaultCalmMood(spawnedCreature, creatureMood);
            }
        }
        if (dataTableHasColumn(datatable, "spawn_objvar2"))
        {
            String spawnObjVar = dataTableGetString(datatable, x, "spawn_objvar");
            String objvarValue1 = dataTableGetString(datatable, x, "spawn_objvar_value");
            String spawnObjVar2 = dataTableGetString(datatable, x, "spawn_objvar2");
            String spawnValue2 = dataTableGetString(datatable, x, "spawn_objvar_value2");
            if (spawnObjVar != null && !spawnObjVar.equals(""))
            {
                if (objvarValue1 != null && !objvarValue1.equals(""))
                {
                    setObjVar(spawnedCreature, spawnObjVar, objvarValue1);
                }
            }
            if (spawnObjVar2 != null && !spawnObjVar2.equals(""))
            {
                if (spawnValue2 != null && !spawnValue2.equals(""))
                {
                    setObjVar(spawnedCreature, spawnObjVar2, spawnValue2);
                }
            }
        }
        else 
        {
            String objVarList = dataTableGetString(datatable, x, "spawn_objvar");
            setCreatureObjVars(spawnedCreature, objVarList);
        }
        return;
    }
    public void setCreatureObjVars(obj_id creature, String objVarList) throws InterruptedException
    {
        if (objVarList == null || objVarList.equals(""))
        {
            return;
        }
        String[] pairs = split(objVarList, ',');
        for (int i = 0; i < pairs.length; i++)
        {
            String[] objVarToSet = split(pairs[i], '=');
            String objVarValue = objVarToSet[1];
            String[] objVarNameAndType = split(objVarToSet[0], ':');
            String objVarType = objVarNameAndType[0];
            String objVarName = objVarNameAndType[1];
            if (objVarType.equals("string"))
            {
                setObjVar(creature, objVarName, objVarValue);
            }
            else if (objVarType.equals("int"))
            {
                setObjVar(creature, objVarName, utils.stringToInt(objVarValue));
            }
            else if (objVarType.equals("float"))
            {
                setObjVar(creature, objVarName, utils.stringToFloat(objVarValue));
            }
            else if (objVarType.equals("boolean") || objVarType.equals("bool"))
            {
                setObjVar(creature, objVarName, utils.stringToInt(objVarValue));
            }
            else 
            {
                setObjVar(creature, objVarName, objVarValue);
            }
        }
    }
    public int checkForIncap(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] hackerInRange = getPlayerCreaturesInRange(self, 5.0f);
        if (hackerInRange == null || hackerInRange.length == 0)
        {
            utils.removeScriptVar(self, "beingHacked");
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < hackerInRange.length; i++)
        {
            if (hackerInRange[i] == utils.getObjIdScriptVar(self, "beingHacked"))
            {
                if (isIncapacitated(hackerInRange[i]))
                {
                    sendSystemMessage(hackerInRange[i], SID_HACK_INTERRUPTED);
                    utils.removeScriptVar(self, "beingHacked");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    messageTo(self, "checkForIncap", null, 2.0f, false);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        utils.removeScriptVar(self, "beingHacked");
        return SCRIPT_CONTINUE;
    }
    public int hackComplete(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] playersInRange = getPlayerCreaturesInRange(self, 64.0f);
        if (utils.hasScriptVar(self, "beingHacked"))
        {
            if ((getStringObjVar(self, OBJ_FACTION)).equals(FAC_REBEL))
            {
                groundquests.sendSignal(playersInRange, "hackedCommImperial1");
                utils.setScriptVar(self, "cantHack", 1);
                utils.removeScriptVar(self, "beingHacked");
                messageTo(self, "changeFactionImperial", null, 5.0f, false);
                messageTo(self, "hackAvailable", null, 120.0f, false);
            }
            else if ((getStringObjVar(self, OBJ_FACTION)).equals(FAC_IMPERIAL))
            {
                groundquests.sendSignal(playersInRange, "hackedCommRebel1");
                utils.setScriptVar(self, "cantHack", 1);
                utils.removeScriptVar(self, "beingHacked");
                messageTo(self, "changeFactionRebel", null, 5.0f, false);
                messageTo(self, "hackAvailable", null, 120.0f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int hackAvailable(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "cantHack");
        return SCRIPT_CONTINUE;
    }
    public int changeFactionImperial(obj_id self, dictionary params) throws InterruptedException
    {
        location loc = getLocation(self);
        obj_id flagInRange = getFirstObjectWithTemplate(loc, 10.0f, "object/tangible/gcw/flip_banner_onpole_rebel.iff");
        if (!isIdNull(flagInRange))
        {
            destroyObject(flagInRange);
        }
        utils.removeObjVar(self, "spawned0");
        utils.removeObjVar(self, "spawned1");
        utils.removeObjVar(self, "spawned2");
        utils.removeObjVar(self, "spawned3");
        utils.removeObjVar(self, "spawned4");
        utils.removeObjVar(self, "spawned5");
        utils.removeObjVar(self, "spawned6");
        utils.removeObjVar(self, "spawned7");
        utils.removeObjVar(self, "spawned8");
        utils.removeObjVar(self, "spawned9");
        utils.removeObjVar(self, "spawned10");
        utils.setObjVar(self, OBJ_FACTION, FAC_IMPERIAL);
        messageTo(self, "beginSpawnImperial", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int changeFactionRebel(obj_id self, dictionary params) throws InterruptedException
    {
        location loc = getLocation(self);
        obj_id flagInRange = getFirstObjectWithTemplate(loc, 10.0f, "object/tangible/gcw/flip_banner_onpole_imperial.iff");
        if (!isIdNull(flagInRange))
        {
            destroyObject(flagInRange);
        }
        utils.removeObjVar(self, "spawned0");
        utils.removeObjVar(self, "spawned1");
        utils.removeObjVar(self, "spawned2");
        utils.removeObjVar(self, "spawned3");
        utils.removeObjVar(self, "spawned4");
        utils.removeObjVar(self, "spawned5");
        utils.removeObjVar(self, "spawned6");
        utils.removeObjVar(self, "spawned7");
        utils.removeObjVar(self, "spawned8");
        utils.removeObjVar(self, "spawned9");
        utils.removeObjVar(self, "spawned10");
        utils.setObjVar(self, OBJ_FACTION, FAC_REBEL);
        messageTo(self, "beginSpawnRebel", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int beginSpawnImperial(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "spawn_table_imperial");
        if (datatable == null || datatable.equals(""))
        {
            return SCRIPT_OVERRIDE;
        }
        int numberOfCreaturesToSpawn = dataTableGetNumRows(datatable);
        int x = utils.getIntScriptVar(self, "spawnCounter");
        while (x < numberOfCreaturesToSpawn)
        {
            if (hasObjVar(self, "spawned" + x))
            {
                obj_id check = getObjIdObjVar(self, "spawned" + x);
                if (!check.isLoaded())
                {
                    spawnCreatures(x, datatable, self);
                }
            }
            else 
            {
                spawnCreatures(x, datatable, self);
            }
            x = x + 1;
        }
        return SCRIPT_CONTINUE;
    }
    public int beginSpawnRebel(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "spawn_table_rebel");
        if (datatable == null || datatable.equals(""))
        {
            return SCRIPT_OVERRIDE;
        }
        int numberOfCreaturesToSpawn = dataTableGetNumRows(datatable);
        int x = utils.getIntScriptVar(self, "spawnCounter");
        while (x < numberOfCreaturesToSpawn)
        {
            if (hasObjVar(self, "spawned" + x))
            {
                obj_id check = getObjIdObjVar(self, "spawned" + x);
                if (!check.isLoaded())
                {
                    spawnCreatures(x, datatable, self);
                }
            }
            else 
            {
                spawnCreatures(x, datatable, self);
            }
            x = x + 1;
        }
        return SCRIPT_CONTINUE;
    }
    public int tellingMomIDied(obj_id self, dictionary params) throws InterruptedException
    {
        int spawn_num = params.getInt("spawnNumber");
        obj_id spawn_mob = params.getObjId("spawnMob");
        if (hasObjVar(self, "spawned" + spawn_num) && (spawn_mob == getObjIdObjVar(self, "spawned" + spawn_num)))
        {
            if ((getStringObjVar(self, OBJ_FACTION)).equals(FAC_REBEL))
            {
                String datatable = getStringObjVar(self, "spawn_table_rebel");
                spawnCreatures(spawn_num, datatable, self);
            }
            else 
            {
                String datatable = getStringObjVar(self, "spawn_table_imperial");
                spawnCreatures(spawn_num, datatable, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int checkFactionInitial(obj_id self, dictionary params) throws InterruptedException
    {
        if ((getStringObjVar(self, OBJ_FACTION)).equals(FAC_IMPERIAL))
        {
            messageTo(self, "beginSpawnImperial", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        if ((getStringObjVar(self, OBJ_FACTION)).equals(FAC_REBEL))
        {
            messageTo(self, "beginSpawnRebel", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
