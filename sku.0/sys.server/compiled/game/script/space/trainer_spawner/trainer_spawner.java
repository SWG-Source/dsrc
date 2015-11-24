package script.space.trainer_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.factions;

public class trainer_spawner extends script.base_script
{
    public trainer_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "msgTimeStamp"))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleStartSpawning", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int handleStartSpawning(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "tableName");
        if (datatable == null || datatable.equals(""))
        {
            debugServerConsoleMsg(self, "tableName is missing from " + getName(self) + " so it doesn't know what to spawn.");
            return SCRIPT_CONTINUE;
        }
        int timeStamp = getGameTime();
        utils.setScriptVar(self, "msgTimeStamp", timeStamp);
        utils.setScriptVar(self, "indexNum", 0);
        dictionary parms = new dictionary();
        parms.put("msgTimeStamp", timeStamp);
        messageTo(self, "handleSpawnNext", parms, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnNext(obj_id self, dictionary params) throws InterruptedException
    {
        int timeStamp = utils.getIntScriptVar(self, "msgTimeStamp");
        int msgTimeStamp = params.getInt("msgTimeStamp");
        if (msgTimeStamp != timeStamp)
        {
            return SCRIPT_CONTINUE;
        }
        int indexNum = utils.getIntScriptVar(self, "indexNum");
        String tableName = "datatables/space_trainer_spawner/" + getStringObjVar(self, "tableName") + ".iff";
        int maxIndex = dataTableGetNumRows(tableName);
        dictionary npcInfo = dataTableGetRow(tableName, indexNum);
        location spawnLocation = getLocation(self);
        spawnLocation.cell = getCellId(self, npcInfo.getString("cellName"));
        spawnLocation.x = npcInfo.getFloat("x");
        spawnLocation.y = npcInfo.getFloat("y");
        spawnLocation.z = npcInfo.getFloat("z");
        obj_id newNpc = create.staticObject("object/mobile/" + npcInfo.getString("templateName"), spawnLocation);
        setYaw(newNpc, npcInfo.getFloat("yaw"));
        String factionName = npcInfo.getString("faction");
        factions.setFaction(newNpc, factionName);
        String scriptName = npcInfo.getString("convoScript");
        if (scriptName != null && !scriptName.equals(""))
        {
            attachScript(newNpc, "conversation." + scriptName);
        }
        String indexName = npcInfo.getString("indexName");
        if (indexName != null && !indexName.equals(""))
        {
            utils.setScriptVar(self, "space_spawn." + indexName, newNpc);
        }
        String[] npcNameFile = split(npcInfo.getString("npcName"), ':');
        if (npcNameFile != null && npcNameFile.length == 2)
        {
            string_id nameId = new string_id(npcNameFile[0], npcNameFile[1]);
            setName(newNpc, "");
            setName(newNpc, nameId);
        }
        String animMood = npcInfo.getString("animMood");
        if (animMood != null && !animMood.equals(""))
        {
            setAnimationMood(newNpc, animMood);
        }
        indexNum++;
        if (indexNum < maxIndex)
        {
            utils.setScriptVar(self, "indexNum", indexNum);
            messageTo(self, "handleSpawnNext", params, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
}
