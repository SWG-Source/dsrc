package script.working.wwallace;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class dungeon_teleport extends script.base_script
{
    public dungeon_teleport()
    {
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equals("teleport"))
        {
            obj_id top = getTopMostContainer(self);
            String whereTo = strCommands[1];
            location randomLoc = getGoodLocation(top, whereTo);
            obj_id cell = getCellId(top, whereTo);
            utils.warpPlayer(self, randomLoc);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
