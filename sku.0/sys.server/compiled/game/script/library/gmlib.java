package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.gm;
import script.library.static_item;

public class gmlib extends script.base_script
{
    public gmlib()
    {
    }
    public static String freezePlayer(long playerId) throws InterruptedException
    {
        debugServerConsoleMsg(null, "In freeze");
        obj_id target = obj_id.getObjId(playerId);
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(null, "NOT VALID TARGET");
            return "bad id";
        }
        setState(target, STATE_FROZEN, true);
        return "frozen?";
    }
    public static void unFreezePlayer(long playerId) throws InterruptedException
    {
        obj_id target = obj_id.getObjId(playerId);
        if (!isIdValid(target))
        {
            LOG("gmlib", "NOT VALID TARGET");
            return;
        }
        setState(target, STATE_FROZEN, false);
    }
    public static void generateStaticItem(obj_id targetid, String item_name) throws InterruptedException
    {
        if (isIdValid(targetid))
        {
            static_item.createNewItemFunction(item_name, targetid);
        }
    }
}
