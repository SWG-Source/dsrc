package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.storyteller;
import script.library.utils;

public class blueprint_split_controller extends script.base_script
{
    public blueprint_split_controller()
    {
    }
    public int handleSplitBlueprintItemCreation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        location targetLoc = params.getLocation("targetLoc");
        float targetYaw = params.getFloat("targetYaw");
        if (!isIdValid(player) || targetLoc == null)
        {
            return SCRIPT_CONTINUE;
        }
        String[] blueprintObjects = utils.getStringBatchObjVar(self, storyteller.BLUEPRINT_OBJECTS_OBJVAR);
        if (blueprintObjects == null || blueprintObjects.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (exists(player) && isGod(player))
        {
            utils.setScriptVar(player, "storyteller.godModeStopOverrideMessages", true);
        }
        boolean splitItemCreation = false;
        int numObjectsToCreate = blueprintObjects.length;
        if (numObjectsToCreate > storyteller.BLUEPRINT_CREATE_CYCLE_MAX)
        {
            numObjectsToCreate = storyteller.BLUEPRINT_CREATE_CYCLE_LOOP;
            splitItemCreation = true;
        }
        for (int i = 0; i < numObjectsToCreate; i++)
        {
            String objectData = blueprintObjects[i];
            obj_id blueprintObject = storyteller.createBlueprintObject(objectData, self, player, targetLoc, targetYaw);
        }
        if (splitItemCreation)
        {
            String[] splitBlueprintObjects = new String[blueprintObjects.length - storyteller.BLUEPRINT_CREATE_CYCLE_LOOP];
            splitBlueprintObjects = utils.copyArrayOfRange(blueprintObjects, splitBlueprintObjects, storyteller.BLUEPRINT_CREATE_CYCLE_LOOP, blueprintObjects.length - 1);
            utils.setBatchObjVar(self, storyteller.BLUEPRINT_OBJECTS_OBJVAR, splitBlueprintObjects);
            dictionary webster = new dictionary();
            webster.put("player", player);
            webster.put("targetLoc", targetLoc);
            webster.put("targetYaw", targetYaw);
            messageTo(self, "handleSplitBlueprintItemCreation", webster, 1, false);
        }
        else 
        {
            if (exists(player) && utils.hasScriptVar(player, "storyteller.godModeStopOverrideMessages"))
            {
                utils.removeScriptVar(player, "storyteller.godModeStopOverrideMessages");
            }
            removeObjVar(self, storyteller.BLUEPRINT_OBJECTS_OBJVAR);
            detachScript(self, "systems.storyteller.blueprint_split_controller");
        }
        return SCRIPT_CONTINUE;
    }
}
