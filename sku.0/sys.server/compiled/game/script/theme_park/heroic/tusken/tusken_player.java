package script.theme_park.heroic.tusken;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.instance;
import script.library.locations;

public class tusken_player extends script.base_script
{
    public tusken_player()
    {
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        String area = locations.getBuildoutAreaName(self);
        if (area == null || area.equals(""))
        {
            detachScript(self, "theme_park.heroic.tusken.tusken_player");
            return SCRIPT_CONTINUE;
        }
        if (!area.equals("heroic_tusken_army"))
        {
            detachScript(self, "theme_park.heroic.tusken.tusken_player");
            return SCRIPT_CONTINUE;
        }
        clearCurrentTuskenQuest(self);
        messageTo(self, "update_quest", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        clearCurrentTuskenQuest(self);
        return SCRIPT_CONTINUE;
    }
    public int update_quest(obj_id self, dictionary params) throws InterruptedException
    {
        dictionary dict = new dictionary();
        dict.put("player", self);
        obj_id quest_manager = getFirstObjectWithScript(getLocation(self), 1000.0f, "theme_park.heroic.tusken.tusken_quest_tracker");
        if (!isIdValid(quest_manager))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(quest_manager, "requestUpdatePlayer", dict, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void clearCurrentTuskenQuest(obj_id self) throws InterruptedException
    {
        String[] questToClear = 
        {
            "heroic_tusken_tracking_01",
            "heroic_tusken_tracking_02",
            "heroic_tusken_tracking_02a",
            "heroic_tusken_tracking_03"
        };
        for (int i = 0; i < questToClear.length; i++)
        {
            groundquests.clearQuest(self, questToClear[i]);
        }
    }
}
