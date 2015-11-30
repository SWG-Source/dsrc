package script.space.quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.space_flags;

public class corellia_station_mission_mail extends script.base_script
{
    public corellia_station_mission_mail()
    {
    }
    public static final string_id SUBJECT_ONE = new string_id("space/quest_mail", "corellia_station_mission_subject_one");
    public static final string_id SUBJECT_TWO = new string_id("space/quest_mail", "corellia_station_mission_subject_two");
    public static final string_id BODY_ONE = new string_id("space/quest_mail", "corellia_station_mission_body_one");
    public static final string_id BODY_TWO = new string_id("space/quest_mail", "corellia_station_mission_body_two");
    public int mailMessageOne(obj_id self, dictionary params) throws InterruptedException
    {
        if (space_flags.isSpaceTrack(self, space_flags.PRIVATEER_CORELLIA))
        {
            messageTo(self, "sendMessageOne", null, 90.0f, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int mailMessageTwo(obj_id self, dictionary params) throws InterruptedException
    {
        if (space_flags.isSpaceTrack(self, space_flags.PRIVATEER_CORELLIA))
        {
            messageTo(self, "sendMessageTwo", null, 90.0f, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int sendMessageOne(obj_id self, dictionary params) throws InterruptedException
    {
        utils.sendMail(SUBJECT_ONE, BODY_ONE, self, "Sender blocked: Data not available");
        space_flags.setSpaceFlag(self, "readyForCorelliaStationMission", 4);
        detachScript(self, "space.quest.corellia_station_mission_mail");
        return SCRIPT_CONTINUE;
    }
    public int sendMessageTwo(obj_id self, dictionary params) throws InterruptedException
    {
        utils.sendMail(SUBJECT_TWO, BODY_TWO, self, "Sender blocked: Data not available");
        space_flags.setSpaceFlag(self, "readyForCorelliaStationMission", 6);
        detachScript(self, "space.quest.corellia_station_mission_mail");
        return SCRIPT_CONTINUE;
    }
}
