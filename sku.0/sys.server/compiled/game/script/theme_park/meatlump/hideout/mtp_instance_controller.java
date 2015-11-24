package script.theme_park.meatlump.hideout;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.instance;
import script.library.groundquests;
import script.library.structure;
import script.library.sui;
import script.library.utils;

public class mtp_instance_controller extends script.base_script
{
    public mtp_instance_controller()
    {
    }
    public int setMtpInstanceCombatLevel(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        int playerCombatLevel = params.getInt("playerCombatLevel");
        if (playerCombatLevel > 65)
        {
            obj_id[] cellList = getContents(self);
            if (cellList != null && cellList.length > 0)
            {
                for (int i = 0; i < cellList.length; i++)
                {
                    obj_id cell = cellList[i];
                    if ((getTemplateName(cell)).equals(structure.TEMPLATE_CELL))
                    {
                        obj_id[] cellContents = getContents(cell);
                        if (cellContents != null && cellContents.length > 0)
                        {
                            for (int j = 0; j < cellContents.length; j++)
                            {
                                obj_id thing = cellContents[j];
                                if (isMob(thing) && !isPlayer(thing) && !isInvulnerable(thing))
                                {
                                    setObjVar(thing, create.INITIALIZE_CREATURE_DO_NOT_SCALE_OBJVAR, true);
                                    String spawnName = getCreatureName(thing);
                                    dictionary creatureDict = utils.dataTableGetRow(create.CREATURE_TABLE, spawnName);
                                    if (creatureDict != null)
                                    {
                                        create.initializeCreature(thing, spawnName, creatureDict, playerCombatLevel);
                                        ai_lib.setDefaultCalmBehavior(thing, ai_lib.BEHAVIOR_LOITER);
                                        messageTo(thing, "mtpInstanceLevelChanged", null, 0.5f, false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
