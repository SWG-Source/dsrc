package script.working.breinhart;

import script.library.space_quest;
import script.location;
import script.obj_id;

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
        switch (text) {
            case "assignquest":
                boolean grant = space_quest.grantQuest(self, mission_type, mission_name);
                break;
            case "winquest": {
                obj_id mobj = space_quest._getQuest(self, mission_type, mission_name);
                space_quest.setQuestWon(self, mobj);
                break;
            }
            case "failquest": {
                obj_id mobj = space_quest._getQuest(self, mission_type, mission_name);
                space_quest.setQuestFailed(self, mobj);
                break;
            }
            case "abortquest": {
                obj_id mobj = space_quest._getQuest(self, mission_type, mission_name);
                space_quest.setQuestAborted(self, mobj);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
