package script.event.ewok_festival;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.library.holiday;
import script.library.utils;
import script.location;
import script.obj_id;

import java.util.Vector;

public class loveday_romance_target_spawner extends script.base_script
{
    public loveday_romance_target_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnRomanceTargetNpcs", null, 120, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnRomanceTargetNpcs", null, 180, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnRomanceTargetNpcs(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "spawnedRomanceTargetNpcs"))
        {
            String planet = getLocation(self).area;
            spawnRomanceTargets("datatables/spawning/holiday/love_day_romance_target_male_" + planet + ".iff", self, planet, "loveday_romance_target_male");
            spawnRomanceTargets("datatables/spawning/holiday/love_day_romance_target_female_" + planet + ".iff", self, planet, "loveday_romance_target_female");
            utils.setScriptVar(self, "spawnedRomanceTargetNpcs", true);
        }
        return SCRIPT_CONTINUE;
    }
    public void areaDebugMessaging(obj_id self, String message) throws InterruptedException
    {
        obj_id[] players = getAllPlayers(getLocation(getTopMostContainer(self)), 35.0f);
        if (players != null && players.length > 0)
        {
            for (obj_id player : players) {
                sendSystemMessage(player, message, "");
            }
        }
    }
    public void spawnRomanceTargets(String datatable, obj_id self, String planet, String spawnName) throws InterruptedException
    {
        Vector traitSets = new Vector();
        traitSets.setSize(0);
        for (int j = 0; j < holiday.getNumRomanticTraitsSets(); j++)
        {
            utils.addElement(traitSets, j);
            utils.addElement(traitSets, j);
        }
        int numRows = dataTableGetNumRows(datatable);
        dictionary cupidData;
        obj_id target;
        for (int i = 0; i < numRows; i++)
        {
            if (traitSets.size() < 1)
            {
                break;
            }
            cupidData = dataTableGetRow(datatable, i);
            float x = cupidData.getFloat("loc_x");
            float y = cupidData.getFloat("loc_y");
            float z = cupidData.getFloat("loc_z");
            float yaw = cupidData.getFloat("yaw");
            target = create.object(spawnName, new location(x, y, z, planet, null));
            if (isIdValid(target))
            {
                ai_lib.setDefaultCalmBehavior(target, ai_lib.BEHAVIOR_SENTINEL);
                setYaw(target, yaw);
                int traitSetIndex = rand(0, traitSets.size() - 1);
                int traitSet = (Integer) traitSets.get(traitSetIndex);
                holiday.setRomanticTraits(target, traitSet);
                utils.removeElementAt(traitSets, traitSetIndex);
                utils.setScriptVar(self, spawnName + "_" + i, target);
            }
        }
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!isGod(speaker) || !hasObjVar(speaker, "cupidTestingAuthorized"))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("cupid_testing_spawner"))
        {
            utils.removeScriptVar(self, "spawnedRomanceTargetNpcs");
            messageTo(self, "spawnRomanceTargetNpcs", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
}
