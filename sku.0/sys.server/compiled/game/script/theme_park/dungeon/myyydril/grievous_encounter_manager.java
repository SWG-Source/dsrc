package script.theme_park.dungeon.myyydril;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.badge;

public class grievous_encounter_manager extends script.base_script
{
    public grievous_encounter_manager()
    {
    }
    public static final string_id START = new string_id("dungeon/myyydril", "start");
    public static final string_id STOP = new string_id("dungeon/myyydril", "stop");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "grievous_encounter.active", false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "grievous_encounter.active", false);
        setObjVar(self, "grievous_encounter.numPlayers", 0);
        setObjVar(self, "grievous_encounter.powerCellsActive", 0);
        setObjVar(self, "grievous_encounter.powerSpawn", 20.0f);
        setObjVar(self, "grievous_encounter.numMobs", 3);
        messageTo(self, "handleBeginEncounter", null, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (text.equals("startEncounter"))
        {
            dictionary params = new dictionary();
            params.put("player", speaker);
            messageTo(self, "handleBeginEncounter", params, 5.0f, false);
        }
        if (text.equals("stopEncounter"))
        {
            dictionary params = new dictionary();
            params.put("player", speaker);
            messageTo(self, "handleEndEncounter", params, 5.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBeginEncounter(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "grievous_encounter.active", true);
        obj_id top = getTopMostContainer(self);
        obj_id cell = getCellId(top, "hall57");
        float mx = -227.2f;
        float my = -92.6f;
        float mz = 156.2f;
        location machineLoc = new location(mx, my, mz, getCurrentSceneName(), cell);
        obj_id machine = createObject("object/tangible/theme_park/myyydril/myyydril_machine.iff", machineLoc);
        if (isIdValid(machine))
        {
            setObjVar(machine, "grievous_encounter.contentManager", self);
            setObjVar(self, "grievous_encounter.machine", machine);
            attachScript(machine, "theme_park.dungeon.myyydril.machine");
        }
        dictionary d = new dictionary();
        String powerTable = "datatables/dungeon/myyydril/power_cell_locations.iff";
        location[] locs = new location[2];
        for (int i = 0; i < 2; i++)
        {
            int rand = rand(0, 3);
            float x = dataTableGetFloat(powerTable, rand, 1);
            float y = dataTableGetFloat(powerTable, rand, 2);
            float z = dataTableGetFloat(powerTable, rand, 3);
            locs[i] = new location(x, y, z, getCurrentSceneName(), cell);
            obj_id powerCell = createObject("object/tangible/theme_park/myyydril/myyydril_power_cell.iff", locs[i]);
            playClientEffectObj(powerCell, "appearance/pt_grievious_cellspawn.prt", powerCell, "");
            if (isIdValid(powerCell))
            {
                int active = getIntObjVar(self, "grievous_encounter.powerCellsActive");
                setObjVar(self, "grievous_encounter.powerCellsActive", active + 1);
                setObjVar(powerCell, "grievous_encounter.contentManager", self);
                setObjVar(powerCell, "grievous_encounter.isPowerCell", true);
                attachScript(powerCell, "theme_park.dungeon.myyydril.power_cell");
            }
        }
        location grievousLoc = getLocation(self);
        grievousLoc.x = -216.9f;
        grievousLoc.y = -93.8f;
        grievousLoc.z = 137.3f;
        obj_id grievous = create.object("ep3_general_grievous", grievousLoc);
        attachScript(grievous, "theme_park.dungeon.myyydril.grievous_ai");
        attachScript(grievous, "theme_park.dungeon.myyydril.grievous_death");
        setObjVar(self, "grievous_encounter.grievousId", grievous);
        setObjVar(grievous, "grievous_encounter.contentManager", self);
        location guard1Loc = new location(-215.3f, -94f, 138.4f, grievousLoc.area, grievousLoc.cell);
        location guard2Loc = new location(-217.2f, -93.7f, 134.9f, grievousLoc.area, grievousLoc.cell);
        obj_id grievousGuard1 = create.object("ep3_myyydril_nk3", guard1Loc);
        setObjVar(self, "grievous_encounter.grievousGuardIdOne", grievousGuard1);
        setObjVar(grievousGuard1, "grievous_encounter.contentManager", self);
        attachScript(grievousGuard1, "theme_park.dungeon.myyydril.grievous_guard");
        obj_id grievousGuard2 = create.object("ep3_myyydril_nk3", guard2Loc);
        setObjVar(self, "grievous_encounter.grievousGuardIdTwo", grievousGuard2);
        setObjVar(grievousGuard2, "grievous_encounter.contentManager", self);
        attachScript(grievousGuard2, "theme_park.dungeon.myyydril.grievous_guard");
        return SCRIPT_CONTINUE;
    }
    public int handleEndEncounter(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        obj_id cell = getCellId(top, "hall57");
        obj_id player = params.getObjId("player");
        setObjVar(self, "grievous_encounter.active", false);
        setObjVar(self, "grievous_encounter.powerCellsActive", 0);
        obj_id grievous = getObjIdObjVar(self, "grievous_encounter.grievousId");
        obj_id guard1 = getObjIdObjVar(self, "grievous_encounter.grievousGuardIdOne");
        obj_id guard2 = getObjIdObjVar(self, "grievous_encounter.grievousGuardIdTwo");
        if (isIdValid(grievous))
        {
            destroyObject(grievous);
        }
        if (isIdValid(guard1))
        {
            destroyObject(guard1);
        }
        if (isIdValid(guard2))
        {
            destroyObject(guard2);
        }
        obj_id machine = getObjIdObjVar(self, "grievous_encounter.machine");
        if (isIdValid(machine))
        {
            destroyObject(machine);
        }
        location selfLocation = getLocation(self);
        obj_id[] objectsInRange = getObjectsInRange(selfLocation, 150.0f);
        for (int i = 0; i < objectsInRange.length; i++)
        {
            if (isIdValid(objectsInRange[i]) && hasObjVar(objectsInRange[i], "grievous_encounter.isPowerCell"))
            {
                obj_id powerCell = objectsInRange[i];
                destroyObject(powerCell);
            }
        }
        messageTo(top, "resetEncounterLocks", null, 0.0f, false);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handlePowerCellDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        obj_id cell = getCellId(top, "hall57");
        String powerTable = "datatables/dungeon/myyydril/power_cell_locations.iff";
        int active = getIntObjVar(self, "grievous_encounter.powerCellsActive");
        setObjVar(self, "grievous_encounter.powerCellsActive", active - 1);
        location spawnLoc = new location(0, 0, 0, getCurrentSceneName(), cell);
        int rand = rand(0, 5);
        float x = dataTableGetFloat(powerTable, rand, 1);
        float y = dataTableGetFloat(powerTable, rand, 2);
        float z = dataTableGetFloat(powerTable, rand, 3);
        spawnLoc.x = x;
        spawnLoc.y = y;
        spawnLoc.z = z;
        obj_id powerCell = createObject("object/tangible/theme_park/myyydril/myyydril_power_cell.iff", spawnLoc);
        playClientEffectObj(powerCell, "appearance/pt_grievious_cellspawn.prt", powerCell, "");
        if (isIdValid(powerCell))
        {
            setObjVar(self, "grievous_encounter.powerCellsActive", active + 1);
            setObjVar(powerCell, "grievous_encounter.contentManager", self);
            setObjVar(powerCell, "grievous_encounter.isPowerCell", true);
            attachScript(powerCell, "theme_park.dungeon.myyydril.power_cell");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnSpeedFast(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "grievous_encounter.powerSpawn", 20.0f);
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnSpeedMed(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "grievous_encounter.powerSpawn", 40.0f);
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnSpeedSlow(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "grievous_encounter.powerSpawn", 60.0f);
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnSpeedReallySlow(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "grievous_encounter.powerSpawn", 80.0f);
        return SCRIPT_CONTINUE;
    }
    public int handleNpcDeath(obj_id self, dictionary params) throws InterruptedException
    {
        int num = getIntObjVar(self, "grievous_encounter.numMobs");
        num = num - 1;
        setObjVar(self, "grievous_encounter.numMobs", num);
        if (num <= 0)
        {
            obj_id[] players = getEventPlayersInDungeon(getTopMostContainer(self));
            if (players != null && players.length > 0)
            {
                for (int i = 0; i < players.length; i++)
                {
                    string_id msg = new string_id("dungeon/myyydril", "encounter_ending");
                    if (!badge.hasBadge(players[i], "bdg_kash_grievous"))
                    {
                        badge.grantBadge(players[i], "bdg_kash_grievous");
                    }
                    sendSystemMessage(players[i], msg);
                }
            }
            messageTo(self, "handleEndEncounter", params, 60.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id[] getEventPlayersInDungeon(obj_id dungeon) throws InterruptedException
    {
        obj_id[] cellIds = getCellIds(dungeon);
        Vector eventPlayers = new Vector();
        eventPlayers.setSize(0);
        for (int i = 0; i < cellIds.length; i++)
        {
            obj_id[] contents = getContents(cellIds[i]);
            if (contents != null && contents.length > 0)
            {
                for (int k = 0; k < contents.length; k++)
                {
                    if (isPlayer(contents[k]) && hasScript(contents[k], "theme_park.dungeon.myyydril.grievous_player"))
                    {
                        if (validatePlayerSessionId(contents[k], dungeon))
                        {
                            eventPlayers = utils.addElement(eventPlayers, contents[k]);
                        }
                    }
                }
            }
        }
        if (eventPlayers == null)
        {
            return null;
        }
        obj_id[] convertedArray = new obj_id[0];
        if (eventPlayers != null)
        {
            convertedArray = new obj_id[eventPlayers.size()];
            eventPlayers.toArray(convertedArray);
        }
        if (convertedArray.length > 0)
        {
            return convertedArray;
        }
        else 
        {
            return null;
        }
    }
    public boolean validatePlayerSessionId(obj_id player, obj_id dungeon) throws InterruptedException
    {
        if (hasObjVar(dungeon, "grievous_lock.isActive"))
        {
            if (getIntObjVar(dungeon, "grievous_lock.isActive") == 1)
            {
                return (getSessionId(player) == getSessionId(dungeon));
            }
            else 
            {
                return false;
            }
        }
        return false;
    }
    public int getSessionId(obj_id target) throws InterruptedException
    {
        if (utils.hasScriptVar(target, "grievous_lock.encounter_session_current"))
        {
            return utils.getIntScriptVar(target, "grievous_lock.encounter_session_current");
        }
        else 
        {
            return -1;
        }
    }
}
