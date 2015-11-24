package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.utils;
import script.library.ai_lib;
import script.library.utils;
import script.library.anims;
import script.library.create;
import script.ai.ai_combat;
import script.library.group;

public class repair_droid extends script.base_script
{
    public repair_droid()
    {
    }
    public static final String TBL_ATTACK_WAVE1 = "datatables/dungeon/death_watch/attack_wave_01.iff";
    public static final String TBL_ATTACK_WAVE2 = "datatables/dungeon/death_watch/attack_wave_02.iff";
    public static final string_id VENTILATION_REPAIR = new string_id("dungeon/death_watch", "ventilation_repair");
    public static final string_id REPAIR_FAILED = new string_id("dungeon/death_watch", "repair_failed");
    public static final string_id PROTECT_TOOLS = new string_id("dungeon/death_watch", "protect_tools");
    public static final string_id PROTECT_FIX = new string_id("dungeon/death_watch", "protect_fix");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.ai_combat");
        setName(self, "R2-F4 (a repair droid)");
        messageTo(self, "handleGetTools", null, 10f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("tools") && !hasObjVar(self, "toolsReached"))
        {
            spawnAttackWaveOne(self);
            setObjVar(self, "toolsReached", 1);
            messageTo(self, "handleGoVentilation", null, 60f, false);
        }
        if (name.equals("fix") && !hasObjVar(self, "fixing"))
        {
            spawnAttackWaveTwo(self);
            setObjVar(self, "fixing", 1);
            messageTo(self, "handleFix", null, 120f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "repair_completed"))
        {
            obj_id player = getObjIdObjVar(structure, "death_watch.missionTaker");
            if (!isIdValid(player))
            {
                return SCRIPT_CONTINUE;
            }
            int curTime = getGameTime();
            removeObjVar(player, "repair_droid");
            setObjVar(player, "death_watch.air_vent_fail", curTime);
            removeObjVar(structure, "death_watch.missionTaker");
            if (!group.isGrouped(player))
            {
                sendSystemMessage(player, REPAIR_FAILED);
                return SCRIPT_CONTINUE;
            }
            if (group.isGrouped(player))
            {
                Vector members = group.getPCMembersInRange(player, 100f);
                if (members != null && members.size() > 0)
                {
                    int numInGroup = members.size();
                    if (numInGroup < 1)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    for (int i = 0; i < numInGroup; i++)
                    {
                        obj_id thisMember = ((obj_id)members.get(i));
                        sendSystemMessage(thisMember, REPAIR_FAILED);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnAttackWaveOne(obj_id droid) throws InterruptedException
    {
        int defenderCreatures = dataTableGetNumRows(TBL_ATTACK_WAVE1);
        int x = 0;
        while (x < defenderCreatures)
        {
            String spawn = dataTableGetString(TBL_ATTACK_WAVE1, x, "spawns");
            float xCoord = dataTableGetFloat(TBL_ATTACK_WAVE1, x, "loc_x");
            float yCoord = dataTableGetFloat(TBL_ATTACK_WAVE1, x, "loc_y");
            float zCoord = dataTableGetFloat(TBL_ATTACK_WAVE1, x, "loc_z");
            location myself = getLocation(droid);
            String planet = myself.area;
            obj_id top = getTopMostContainer(droid);
            obj_id room = getCellId(top, "filtrationroom65");
            location spawnPoint = new location(xCoord, yCoord, zCoord, planet, room);
            obj_id spawnedCreature = create.object(spawn, spawnPoint);
            attachScript(spawnedCreature, "theme_park.dungeon.death_watch_bunker.attacker_wave");
            dictionary params = new dictionary();
            params.put("droid", droid);
            params.put("spawn", spawnedCreature);
            messageTo(droid, "handleAttackDroid", params, 5, false);
            x = x + 1;
        }
        return;
    }
    public void spawnAttackWaveTwo(obj_id droid) throws InterruptedException
    {
        int defenderCreatures = dataTableGetNumRows(TBL_ATTACK_WAVE2);
        int x = 0;
        while (x < defenderCreatures)
        {
            String spawn = dataTableGetString(TBL_ATTACK_WAVE2, x, "spawns");
            float xCoord = dataTableGetFloat(TBL_ATTACK_WAVE2, x, "loc_x");
            float yCoord = dataTableGetFloat(TBL_ATTACK_WAVE2, x, "loc_y");
            float zCoord = dataTableGetFloat(TBL_ATTACK_WAVE2, x, "loc_z");
            location myself = getLocation(droid);
            String planet = myself.area;
            obj_id top = getTopMostContainer(droid);
            obj_id room = getCellId(top, "filtrationroom65");
            location spawnPoint = new location(xCoord, yCoord, zCoord, planet, room);
            obj_id spawnedCreature = create.object(spawn, spawnPoint);
            attachScript(spawnedCreature, "theme_park.dungeon.death_watch_bunker.attacker_wave");
            dictionary params = new dictionary();
            params.put("droid", droid);
            params.put("spawn", spawnedCreature);
            messageTo(droid, "handleAttackDroid", params, 5, false);
            x = x + 1;
        }
        return;
    }
    public int handleGetTools(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(structure, "death_watch.missionTaker");
        obj_id top = getTopMostContainer(self);
        obj_id toolChest = getCellId(top, "filtrationroom65");
        location here = getLocation(self);
        String planet = here.area;
        location attack1 = new location(-4.5f, 0, -120.6f, planet, toolChest);
        ai_lib.aiPathTo(self, attack1);
        addLocationTarget("tools", attack1, 1);
        if (!group.isGrouped(player))
        {
            sendSystemMessage(player, PROTECT_TOOLS);
            return SCRIPT_CONTINUE;
        }
        if (group.isGrouped(player))
        {
            Vector members = group.getPCMembersInRange(player, 100f);
            if (members != null && members.size() > 0)
            {
                int numInGroup = members.size();
                if (numInGroup < 1)
                {
                    return SCRIPT_CONTINUE;
                }
                for (int i = 0; i < numInGroup; i++)
                {
                    obj_id thisMember = ((obj_id)members.get(i));
                    sendSystemMessage(thisMember, PROTECT_TOOLS);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleGoVentilation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(structure, "death_watch.missionTaker");
        obj_id top = getTopMostContainer(self);
        obj_id ventilation = getCellId(top, "filtrationroom65");
        location here = getLocation(self);
        String planet = here.area;
        location attack2 = new location(-17f, 0, -61.5f, planet, ventilation);
        ai_lib.aiPathTo(self, attack2);
        addLocationTarget("fix", attack2, 1);
        if (!isIncapacitated(self))
        {
            if (!group.isGrouped(player))
            {
                sendSystemMessage(player, PROTECT_FIX);
                CustomerServiceLog("DUNGEON_DeathWatchBunker", "*Death Watch Air Vent: %TU has turned the air vent back on.", player);
                return SCRIPT_CONTINUE;
            }
            if (group.isGrouped(player))
            {
                Vector members = group.getPCMembersInRange(player, 100f);
                if (members != null && members.size() > 0)
                {
                    int numInGroup = members.size();
                    if (numInGroup < 1)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    for (int i = 0; i < numInGroup; i++)
                    {
                        obj_id thisMember = ((obj_id)members.get(i));
                        sendSystemMessage(thisMember, PROTECT_FIX);
                        CustomerServiceLog("DUNGEON_DeathWatchBunker", "*Death Watch Air Vent: %TU has turned the air vent back on.", thisMember);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFix(obj_id self, dictionary params) throws InterruptedException
    {
        if (isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(structure, "death_watch.missionTaker");
        setObjVar(player, "death_watch.air_vent_success", 1);
        setObjVar(self, "repair_completed", 1);
        setObjVar(structure, "death_watch.air_vent_on", 1);
        messageTo(self, "handleCleanUp", null, 10f, false);
        messageTo(structure, "handleAirVentOff", null, 1800f, false);
        removeObjVar(structure, "death_watch.missionTaker");
        if (!group.isGrouped(player))
        {
            sendSystemMessage(player, VENTILATION_REPAIR);
            return SCRIPT_CONTINUE;
        }
        if (group.isGrouped(player))
        {
            Vector members = group.getPCMembersInRange(player, 100f);
            if (members != null && members.size() > 0)
            {
                int numInGroup = members.size();
                if (numInGroup < 1)
                {
                    return SCRIPT_CONTINUE;
                }
                for (int i = 0; i < numInGroup; i++)
                {
                    obj_id thisMember = ((obj_id)members.get(i));
                    sendSystemMessage(thisMember, VENTILATION_REPAIR);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleAttackDroid(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id droid = params.getObjId("droid");
        obj_id spawn = params.getObjId("spawn");
        startCombat(spawn, droid);
        return SCRIPT_CONTINUE;
    }
}
