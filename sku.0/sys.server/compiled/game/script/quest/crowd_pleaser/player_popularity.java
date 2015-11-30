package script.quest.crowd_pleaser;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.prose;

public class player_popularity extends script.base_script
{
    public player_popularity()
    {
    }
    public static final String SCRIPT_NAME = "quest.crowd_pleaser.player_popularity";
    public static final String QUEST_COMPLETE_OBJVAR = "quest.crowd_pleaser.complete";
    public static final String STATUS_OBJVAR = "quest.crowd_pleaser.status";
    public static final String POPULARITY_OBJVAR = "quest.crowd_pleaser.popularity";
    public static final String ENTERTAINED_OBJVAR = POPULARITY_OBJVAR + ".entertained";
    public static final String NUMBER_OBJVAR = POPULARITY_OBJVAR + ".number";
    public static final String LEVEL_OBJVAR = POPULARITY_OBJVAR + ".level";
    public static final String COMPLETE_OBJVAR = POPULARITY_OBJVAR + ".complete";
    public static final string_id STARTING_MESSAGE = new string_id("quest/crowd_pleaser/system_messages", "popularity_starting_message");
    public static final string_id POPULARITY_1_COMPLETE = new string_id("quest/crowd_pleaser/system_messages", "popularity_1_complete");
    public static final string_id POPULARITY_2_COMPLETE = new string_id("quest/crowd_pleaser/system_messages", "popularity_2_complete");
    public static final string_id POPULARITY_3_COMPLETE = new string_id("quest/crowd_pleaser/system_messages", "popularity_3_complete");
    public static final string_id POPULARITY_1_REMAINING = new string_id("quest/crowd_pleaser/system_messages", "popularity_1_remaining");
    public static final string_id POPULARITY_2_REMAINING = new string_id("quest/crowd_pleaser/system_messages", "popularity_2_remaining");
    public static final string_id POPULARITY_3_REMAINING = new string_id("quest/crowd_pleaser/system_messages", "popularity_3_remaining");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int number = getIntObjVar(self, NUMBER_OBJVAR);
        prose_package pp = prose.getPackage(STARTING_MESSAGE, number);
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int handleEntertainedPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, POPULARITY_OBJVAR))
        {
            detachScript(self, SCRIPT_NAME);
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isPlayer(player))
        {
            return SCRIPT_CONTINUE;
        }
        Vector entertained = new Vector();
        entertained.setSize(0);
        if (hasObjVar(self, ENTERTAINED_OBJVAR))
        {
            entertained = utils.getResizeableObjIdBatchObjVar(self, ENTERTAINED_OBJVAR);
        }
        int idx = entertained.indexOf(player);
        if (idx >= 0)
        {
            return SCRIPT_CONTINUE;
        }
        entertained = utils.addElement(entertained, player);
        int number = getIntObjVar(self, NUMBER_OBJVAR);
        int level = getIntObjVar(self, LEVEL_OBJVAR);
        if (entertained.size() >= number)
        {
            removeObjVar(self, POPULARITY_OBJVAR);
            int status = 0;
            switch (level)
            {
                case 1:
                sendSystemMessage(self, POPULARITY_1_COMPLETE);
                status = 6;
                break;
                case 2:
                sendSystemMessage(self, POPULARITY_2_COMPLETE);
                status = 12;
                break;
                case 3:
                sendSystemMessage(self, POPULARITY_3_COMPLETE);
                status = 18;
                break;
            }
            setObjVar(self, STATUS_OBJVAR, status);
            detachScript(self, SCRIPT_NAME);
        }
        else 
        {
            if (entertained == null || entertained.size() == 0)
            {
                return SCRIPT_CONTINUE;
            }
            utils.setResizeableBatchObjVar(self, ENTERTAINED_OBJVAR, entertained);
            int remaining = (number - entertained.size());
            prose_package pp = new prose_package();
            switch (level)
            {
                case 1:
                pp = prose.getPackage(POPULARITY_1_REMAINING, getFirstName(player), remaining);
                break;
                case 2:
                pp = prose.getPackage(POPULARITY_2_REMAINING, getFirstName(player), remaining);
                break;
                case 3:
                pp = prose.getPackage(POPULARITY_3_REMAINING, getFirstName(player), remaining);
                break;
            }
            sendSystemMessageProse(self, pp);
        }
        return SCRIPT_CONTINUE;
    }
}
