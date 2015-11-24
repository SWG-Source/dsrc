package script.working.cmayer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class bottom extends script.base_script
{
    public bottom()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("test"))
        {
            obj_id building = getTopMostContainer(self);
            moveHouseItemToPlayer(building, self, 0);
        }
        else if (text.equals("test2"))
        {
            obj_id building = getTopMostContainer(self);
            deleteAllHouseItems(building, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            debugServerConsoleMsg(self, "Got player");
            sendSystemMessageTestingOnly(item, "Building got player");
        }
        return SCRIPT_CONTINUE;
    }
}
