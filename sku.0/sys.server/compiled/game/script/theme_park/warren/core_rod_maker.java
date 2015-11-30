package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class core_rod_maker extends script.base_script
{
    public core_rod_maker()
    {
    }
    public static final String COREROD_TEMPLATE = "object/tangible/mission/quest_item/warren_core_control_rod_s01.iff";
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        utils.setScriptVar(self, "messageSent", true);
        messageTo(self, "respawncoreRod", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public void spawncoreRod(obj_id container) throws InterruptedException
    {
        obj_id[] contents = getContents(container);
        if (contents != null)
        {
            for (int i = 0; i < contents.length; i++)
            {
                if (hasObjVar(contents[i], "warren.reactorControlRod"))
                {
                    for (int j = i + 1; j < contents.length; ++j)
                    {
                        destroyObject(contents[j]);
                    }
                }
                return;
            }
        }
        obj_id coreRod = createObject(COREROD_TEMPLATE, container, "");
        setObjVar(coreRod, "warren.reactorControlRod", true);
        setName(coreRod, "");
        setName(coreRod, new string_id(SYSTEM_MESSAGES, "core_rod_name"));
    }
    public int respawncoreRod(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "messageSent");
        spawncoreRod(self);
        return SCRIPT_CONTINUE;
    }
    public int OnClosedContainer(obj_id self, obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "messageSent"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] contents = getContents(self);
        if (contents != null)
        {
            for (int i = 0; i < contents.length; i++)
            {
                if (hasObjVar(contents[i], "warren.reactorControlRod"))
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        messageTo(self, "respawncoreRod", null, 1800, false);
        utils.setScriptVar(self, "messageSent", true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        location here = getLocation(player);
        location term = getLocation(self);
        float range = getDistance(here, term);
        if (range > 5.0)
        {
            sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "elev_range"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnOpenedContainer(obj_id self, obj_id player) throws InterruptedException
    {
        location here = getLocation(player);
        location term = getLocation(self);
        float range = getDistance(here, term);
        if (range > 5.0)
        {
            sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "elev_range"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
