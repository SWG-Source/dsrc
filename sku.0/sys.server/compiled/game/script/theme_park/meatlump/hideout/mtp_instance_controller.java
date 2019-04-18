package script.theme_park.meatlump.hideout;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.library.structure;
import script.library.utils;
import script.obj_id;

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
                for (obj_id cell : cellList) {
                    if ((getTemplateName(cell)).equals(structure.TEMPLATE_CELL)) {
                        obj_id[] cellContents = getContents(cell);
                        if (cellContents != null && cellContents.length > 0) {
                            for (obj_id thing : cellContents) {
                                if (isMob(thing) && !isPlayer(thing) && !isInvulnerable(thing)) {
                                    setObjVar(thing, create.INITIALIZE_CREATURE_DO_NOT_SCALE_OBJVAR, true);
                                    String spawnName = getCreatureName(thing);
                                    dictionary creatureDict = utils.dataTableGetRow(create.CREATURE_TABLE, spawnName);
                                    if (creatureDict != null) {
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
