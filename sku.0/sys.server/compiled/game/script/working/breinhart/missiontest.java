package script.working.breinhart;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_quest;

public class missiontest extends script.base_script
{
    public missiontest()
    {
    }
    public static final String mission_type = "patrol";
    public static final String mission_name = "npe_easy_main_1";
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        location playerLocation = getLocation(self);
        if (text.equals("assignquest"))
        {
            boolean grant = space_quest.grantQuest(self, mission_type, mission_name);
        }
        else if (text.equals("winquest"))
        {
            obj_id mobj = space_quest._getQuest(self, mission_type, mission_name);
            space_quest.setQuestWon(self, mobj);
        }
        else if (text.equals("failquest"))
        {
            obj_id mobj = space_quest._getQuest(self, mission_type, mission_name);
            space_quest.setQuestFailed(self, mobj);
        }
        else if (text.equals("abortquest"))
        {
            obj_id mobj = space_quest._getQuest(self, mission_type, mission_name);
            space_quest.setQuestAborted(self, mobj);
        }
        return SCRIPT_CONTINUE;
    }
}
