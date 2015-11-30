package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.gcw;
import script.library.trial;
import script.library.factions;

public class pvp_region_bonus_controller extends script.base_script
{
    public pvp_region_bonus_controller()
    {
    }
    public static final float CYCLE_HEARTBEAT = 30.0f;
    public static final float CYCLE_MAX_RUN = 600.0f;
    public static final String PVP_AREA_RECORD = "gcw_pvp_region.activity_list";
    public static final String CYCLE_STATUS = "gcw_pvp_region.isActive";
    public static final String CYCLE_ITTERATION = "gcw_pvp_region.cycle_itteration";
    public static final String GCW_REGION_DATA = "gcw_pvp_region";
    public static final String LAST_CYCLE = "gcw_pvp_region.lastCycle";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        gcw.getRegionToRegister(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        gcw.getRegionToRegister(self);
        return SCRIPT_CONTINUE;
    }
    public boolean isCycleActive(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, CYCLE_STATUS))
        {
            if (getGameTime() - utils.getIntScriptVar(self, LAST_CYCLE) > CYCLE_MAX_RUN)
            {
                return false;
            }
            else 
            {
                return utils.getBooleanScriptVar(self, CYCLE_STATUS);
            }
        }
        utils.setScriptVar(self, CYCLE_STATUS, false);
        return false;
    }
    public void setCycleActiveState(obj_id self, boolean state) throws InterruptedException
    {
        if (state)
        {
            utils.setScriptVar(self, LAST_CYCLE, getGameTime());
        }
        utils.setScriptVar(self, CYCLE_STATUS, state);
    }
    public void clearCycleData(obj_id self) throws InterruptedException
    {
        utils.removeScriptVarTree(self, GCW_REGION_DATA);
        setCycleActiveState(self, false);
        trial.bumpSession(self);
    }
    public int getCycleItteration(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, CYCLE_ITTERATION))
        {
            return utils.getIntScriptVar(self, CYCLE_ITTERATION);
        }
        utils.setScriptVar(self, CYCLE_ITTERATION, 0);
        return 0;
    }
    public int itterateCycle(obj_id self) throws InterruptedException
    {
        int current = getCycleItteration(self);
        int newValue = current + 1;
        utils.setScriptVar(self, CYCLE_ITTERATION, newValue);
        return newValue;
    }
    public int cycleUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        float cycleItteration = (float)getCycleItteration(self);
        float maxItteration = CYCLE_MAX_RUN / CYCLE_HEARTBEAT;
        doLogging("cycleUpdate", "We are on " + cycleItteration + " of " + maxItteration);
        if (cycleItteration >= maxItteration)
        {
            clearCycleData(self);
            return SCRIPT_CONTINUE;
        }
        itterateCycle(self);
        messageTo(self, "cycleUpdate", trial.getSessionDict(self), CYCLE_HEARTBEAT, false);
        return SCRIPT_CONTINUE;
    }
    public int diedInPvpRegion(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        Vector recordList = new Vector();
        recordList.setSize(0);
        if (utils.hasResizeableStringBatchScriptVar(self, PVP_AREA_RECORD))
        {
            recordList = utils.getResizeableStringBatchScriptVar(self, PVP_AREA_RECORD);
        }
        utils.removeBatchScriptVar(self, PVP_AREA_RECORD);
        if (getPlayerDataFromList(player, recordList) == null)
        {
            recordList = addPlayerToList(player, recordList, 0, 1);
        }
        else 
        {
            recordList = incrementDeathsByPlayer(player, recordList);
        }
        utils.setBatchScriptVar(self, PVP_AREA_RECORD, recordList);
        return SCRIPT_CONTINUE;
    }
    public int getFactionCount(Vector recordList, String passedFaction) throws InterruptedException
    {
        if (recordList == null || recordList.size() == 0)
        {
            return 0;
        }
        int factionCount = 0;
        for (int i = 0; i < recordList.size(); i++)
        {
            String[] parse = split(((String)recordList.get(i)), '-');
            String faction = parse[1];
            if (faction.equals(passedFaction))
            {
                factionCount++;
            }
        }
        return factionCount;
    }
    public int getTotalDeathCount(Vector recordList) throws InterruptedException
    {
        if (recordList == null || recordList.size() == 0)
        {
            return 0;
        }
        int deathCount = 0;
        for (int i = 0; i < recordList.size(); i++)
        {
            String[] parse = split(((String)recordList.get(i)), '-');
            int playerDeath = utils.stringToInt(parse[3]);
            deathCount += playerDeath;
        }
        return deathCount;
    }
    public Vector addPlayerToList(obj_id player, Vector recordList, int uniqueHits, int deaths) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player) || !isPlayer(player) || recordList == null)
        {
            doLogging("addPlayerToList", "Attempting to add player to list but he is invalid");
            return recordList;
        }
        String faction = factions.getFaction(player);
        if (faction == null || (!faction.equals("Rebel") && !faction.equals("Imperial")))
        {
            doLogging("addPlayerToList", "Player was null faction or was neither rebel/imperial");
            return recordList;
        }
        doLogging("xx", "Player is " + faction);
        String newEntry = "" + player + "-" + faction + "-" + uniqueHits + "-" + deaths;
        utils.addElement(recordList, newEntry);
        doLogging("xx", "Adding element to array: " + newEntry);
        return recordList;
    }
    public String getPlayerDataFromList(obj_id player, Vector recordList) throws InterruptedException
    {
        String listEntry = null;
        if (recordList.size() == 0)
        {
            return null;
        }
        for (int i = 0; i < recordList.size(); i++)
        {
            String[] parse = split(((String)recordList.get(i)), '-');
            obj_id listId = utils.stringToObjId(parse[0]);
            if (listId == player)
            {
                listEntry = ((String)recordList.get(i));
            }
        }
        return listEntry;
    }
    public int getPositionInArrayByPlayer(obj_id player, Vector recordList) throws InterruptedException
    {
        if (recordList.size() == 0)
        {
            return -1;
        }
        int position = -1;
        for (int i = 0; i < recordList.size(); i++)
        {
            String[] parse = split(((String)recordList.get(i)), '-');
            obj_id listId = utils.stringToObjId(parse[0]);
            if (listId == player)
            {
                position = i;
            }
        }
        return position;
    }
    public Vector incrementUpdateHitsByPlayer(obj_id player, Vector recordList) throws InterruptedException
    {
        int playerData = getPositionInArrayByPlayer(player, recordList);
        String[] parse = split(((String)recordList.get(playerData)), '-');
        int uniqueHits = utils.stringToInt(parse[2]);
        uniqueHits += 1;
        String updatedData = "" + parse[0] + "-" + parse[1] + "-" + uniqueHits + "-" + parse[3];
        doLogging("incrementUpdateHitsByPlayer", "Updating Data: " + ((String)recordList.get(playerData)) + " to " + updatedData);
        recordList.set(playerData, updatedData);
        return recordList;
    }
    public Vector incrementDeathsByPlayer(obj_id player, Vector recordList) throws InterruptedException
    {
        int playerData = getPositionInArrayByPlayer(player, recordList);
        String[] parse = split(((String)recordList.get(playerData)), '-');
        int deaths = utils.stringToInt(parse[3]);
        deaths += 1;
        if (deaths > gcw.MAX_DEATH_BY_PLAYER)
        {
            deaths = gcw.MAX_DEATH_BY_PLAYER;
        }
        String updatedData = "" + parse[0] + "-" + parse[1] + "-" + parse[2] + "-" + deaths;
        doLogging("incrementDeathsByPlayer", "updating Data: " + ((String)recordList.get(playerData)) + " to " + updatedData);
        recordList.set(playerData, updatedData);
        return recordList;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
    }
}
