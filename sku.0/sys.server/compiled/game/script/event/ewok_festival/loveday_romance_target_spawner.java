package script.event.ewok_festival;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.holiday;
import script.library.utils;

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
            location here = getLocation(self);
            String planet = here.area;
            String maleDatatable = "datatables/spawning/holiday/love_day_romance_target_male_" + planet + ".iff";
            spawnRomanceTargets(maleDatatable, self, planet, "loveday_romance_target_male");
            String femaleDatatable = "datatables/spawning/holiday/love_day_romance_target_female_" + planet + ".iff";
            spawnRomanceTargets(femaleDatatable, self, planet, "loveday_romance_target_female");
            utils.setScriptVar(self, "spawnedRomanceTargetNpcs", true);
        }
        return SCRIPT_CONTINUE;
    }
    public void areaDebugMessaging(obj_id self, String message) throws InterruptedException
    {
        obj_id[] players = getAllPlayers(getLocation(getTopMostContainer(self)), 35.0f);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                sendSystemMessage(players[i], message, "");
            }
        }
    }
    public void spawnRomanceTargets(String datatable, obj_id self, String planet, String spawnName) throws InterruptedException
    {
        int numSets = holiday.getNumRomanticTraitsSets();
        Vector traitSets = new Vector();
        traitSets.setSize(0);
        for (int j = 0; j < numSets; j++)
        {
            utils.addElement(traitSets, j);
            utils.addElement(traitSets, j);
        }
        int numRows = dataTableGetNumRows(datatable);
        for (int i = 0; i < numRows; i++)
        {
            if (traitSets == null || traitSets.size() < 1)
            {
                break;
            }
            dictionary cupidData = dataTableGetRow(datatable, i);
            float x = cupidData.getFloat("loc_x");
            float y = cupidData.getFloat("loc_y");
            float z = cupidData.getFloat("loc_z");
            float yaw = cupidData.getFloat("yaw");
            location spawnLoc = new location(x, y, z, planet, null);
            obj_id target = create.object(spawnName, spawnLoc);
            if (isIdValid(target))
            {
                ai_lib.setDefaultCalmBehavior(target, ai_lib.BEHAVIOR_SENTINEL);
                setYaw(target, yaw);
                int traitSetIndex = rand(0, traitSets.size() - 1);
                int traitSet = ((Integer)traitSets.get(traitSetIndex)).intValue();
                holiday.setRomanticTraits(target, traitSet);
                utils.removeElementAt(traitSets, traitSetIndex);
                utils.setScriptVar(self, spawnName + "_" + i, target);
            }
        }
        return;
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
