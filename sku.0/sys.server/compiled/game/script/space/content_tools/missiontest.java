package script.space.content_tools;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_quest;
import script.library.utils;

public class missiontest extends script.base_script
{
    public missiontest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "MISSION TEST SCRIPT ATTACHED");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        String[] commands = split(text, ' ');
        if (commands[0].equalsIgnoreCase("namequest"))
        {
            obj_id datapad = utils.getPlayerDatapad(self);
            if (isIdValid(datapad))
            {
                obj_id[] dpobjs = getContents(datapad);
                for (int i = 0; i < dpobjs.length; i++)
                {
                    if (hasObjVar(dpobjs[i], space_quest.QUEST_TYPE))
                    {
                        String tname = getStringObjVar(dpobjs[i], space_quest.QUEST_TYPE);
                        String qname = getStringObjVar(dpobjs[i], space_quest.QUEST_NAME);
                        sendSystemMessageTestingOnly(self, "Found: " + tname + ":" + qname);
                    }
                }
            }
        }
        else if (commands[0].equalsIgnoreCase("grantquest"))
        {
            if (commands.length < 3)
            {
                sendSystemMessageTestingOnly(self, "unable to parse: " + text);
                sendSystemMessageTestingOnly(self, "say GRANTQUEST QUESTTYPE QUESTNAME" + text);
                return SCRIPT_CONTINUE;
            }
            if (!space_quest.grantQuest(self, commands[1], commands[2]))
            {
                sendSystemMessageTestingOnly(self, "FAILED: " + text);
                return SCRIPT_CONTINUE;
            }
        }
        else if (commands[0].equalsIgnoreCase("checkquest"))
        {
            if (commands.length < 3)
            {
                sendSystemMessageTestingOnly(self, "unable to parse: " + text);
                sendSystemMessageTestingOnly(self, "say CHECKQUEST QUESTTYPE QUESTNAME" + text);
                return SCRIPT_CONTINUE;
            }
            if (space_quest.hasQuest(self, commands[1], commands[2]))
            {
                sendSystemMessageTestingOnly(self, "hasQuest:          TRUE");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "hasQuest:          FALSE");
            }
            if (space_quest.hasCompletedQuest(self, commands[1], commands[2]))
            {
                sendSystemMessageTestingOnly(self, "hasCompletedQuest: TRUE");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "hasCompletedQuest: FALSE");
            }
            if (space_quest.hasFailedQuest(self, commands[1], commands[2]))
            {
                sendSystemMessageTestingOnly(self, "hasFailedQuest:    TRUE");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "hasFailedQuest:    FALSE");
            }
            if (space_quest.hasWonQuest(self, commands[1], commands[2]))
            {
                sendSystemMessageTestingOnly(self, "hasWonQuest:       TRUE");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "hasWonQuest:       FALSE");
            }
            return SCRIPT_CONTINUE;
        }
        else if (commands[0].equalsIgnoreCase("clearquest"))
        {
            if (commands.length < 3)
            {
                sendSystemMessageTestingOnly(self, "unable to parse: " + text);
                sendSystemMessageTestingOnly(self, "say CLEARQUEST QUESTTYPE QUESTNAME" + text);
                return SCRIPT_CONTINUE;
            }
            space_quest.clearQuestFlags(self, commands[1], commands[2]);
            sendSystemMessageTestingOnly(self, commands[1] + ":" + commands[2] + " quest flags cleared");
        }
        else if (commands[0].equalsIgnoreCase("winquest"))
        {
            if (commands.length < 3)
            {
                sendSystemMessageTestingOnly(self, "unable to parse: " + text);
                sendSystemMessageTestingOnly(self, "say WINQUEST QUESTTYPE QUESTNAME");
                return SCRIPT_CONTINUE;
            }
            String questType = commands[1];
            String questName = commands[2];
            String qTable = "datatables/spacequest/" + questType + "/" + questName + ".iff";
            dictionary questInfo = dataTableGetRow(qTable, 0);
            if (questInfo == null)
            {
                sendSystemMessageTestingOnly(self, "Unable to find quest data for " + questType + ":" + questName + "!");
                return SCRIPT_CONTINUE;
            }
            obj_id missionObj = space_quest._getQuest(self, questType, questName);
            if (missionObj == null)
            {
                sendSystemMessageTestingOnly(self, "You don't have that quest.");
                return SCRIPT_CONTINUE;
            }
            int splittype = questInfo.getInt("triggerSplitCondition");
            String splitmission = questInfo.getString("triggerArg");
            if ((splitmission == null) || splitmission.equals("") || (splittype == 2))
            {
                space_quest.setQuestWon(self, missionObj, false);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                space_quest.setQuestWon(self, missionObj, false);
                sendSystemMessageTestingOnly(self, "--- Forced Victory on Split Mission, Assigning Split ---");
                java.util.StringTokenizer st = new java.util.StringTokenizer(splitmission, ":");
                String rtype = st.nextToken();
                String rname = st.nextToken();
                space_quest.grantQuest(self, rtype, rname);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
