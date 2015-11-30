package script.city.imperial_crackdown;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;

public class door_check extends script.base_script
{
    public door_check()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (isIdValid(srcContainer))
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                obj_id top = getTopMostContainer(self);
                if (hasObjVar(top, "checkingForTrouble"))
                {
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    setObjVar(top, "checkingForTrouble", 1);
                    dictionary websters = new dictionary();
                    websters.put("foyer", self);
                    websters.put("player", item);
                    messageTo(top, "doTroubleCheck", websters, 1, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
