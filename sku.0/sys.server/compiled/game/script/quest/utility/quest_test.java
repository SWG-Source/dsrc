package script.quest.utility;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;

public class quest_test extends script.base_script
{
    public quest_test()
    {
    }
    public static String[] OPTIONS = 
    {
        "=========================",
        "HELP",
        "ACTIVATEQUEST QUESTNAME",
        "COMPLETEQUEST QUESTNAME",
        "CLEARQUESTFLAG QUESTNAME",
        "========================="
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "Ground quest test script attached");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        String[] parse = split(text, ' ');
        if (parse[0].equalsIgnoreCase("help"))
        {
            showHelp();
        }
        else if (parse[0].equalsIgnoreCase("showBuildoutArea"))
        {
            location here = getLocation(self);
            obj_id containingBuilding = getTopMostContainer(self);
            if (isIdValid(containingBuilding))
            {
                here = getLocation(containingBuilding);
            }
            String buildoutAreaName = getBuildoutAreaName(here.x, here.z);
            sendSystemMessageTestingOnly(self, "You are in buildout area: " + buildoutAreaName);
        }
        else if (parse[0].equalsIgnoreCase("activatequest"))
        {
            if (parse.length < 2)
            {
                sendSystemMessageTestingOnly(self, "SYNTAX ACTIVATEQUEST QUESTNAME " + text);
                return SCRIPT_CONTINUE;
            }
            boolean result = groundquests.isValidQuestName(parse[1]);
            LOG("debug_test", "boolean result returned " + result);
            if (result == false)
            {
                sendSystemMessageTestingOnly(self, "FAILED TO ACTIVATE QUEST ");
            }
            else 
            {
                groundquests.clearQuest(self, parse[1]);
                groundquests.requestGrantQuest(self, parse[1]);
                sendSystemMessageTestingOnly(self, "Granted Quest: " + parse[1]);
            }
        }
        else if (parse[0].equalsIgnoreCase("completequest"))
        {
            if (parse.length < 2)
            {
                sendSystemMessageTestingOnly(self, "SYNTAX COMPLETEQUEST QUESTNAME: " + text);
                return SCRIPT_CONTINUE;
            }
            groundquests.completeQuest(self, parse[1]);
            return SCRIPT_CONTINUE;
        }
        else if (parse[0].equalsIgnoreCase("clearquestflag"))
        {
            if (parse.length < 2)
            {
                sendSystemMessageTestingOnly(self, "SYNTAX CLEARQUESTFLAG QUESTNAME " + text);
                return SCRIPT_CONTINUE;
            }
            if (!groundquests.isQuestActive(self, parse[1]) && !groundquests.hasCompletedQuest(self, parse[1]))
            {
                sendSystemMessageTestingOnly(self, "Unable to verify quest" + text);
            }
            else 
            {
                groundquests.clearQuest(self, parse[1]);
                sendSystemMessageTestingOnly(self, "Quest data for " + parse[1] + " cleared");
            }
            return SCRIPT_CONTINUE;
        }
        else if (parse[0].equalsIgnoreCase("activatetaskname"))
        {
            if (parse.length < 3)
            {
                sendSystemMessageTestingOnly(self, "SYNTAX ACTIVATETASKNAME QUESTNAME TASKNAME " + text);
                return SCRIPT_CONTINUE;
            }
            String questName = parse[1];
            int questCrc = questGetQuestId(questName);
            if (questCrc == 0)
            {
                sendSystemMessageTestingOnly(self, "BAD QUEST NAME");
            }
            else 
            {
                String taskName = parse[2];
                int taskId = groundquests.getTaskId(questCrc, taskName);
                if (taskId == -1)
                {
                    sendSystemMessageTestingOnly(self, "BAD TASK NAME");
                }
                else 
                {
                    questActivateTask(questCrc, taskId, self);
                }
            }
        }
        else if (parse[0].equalsIgnoreCase("completetaskname"))
        {
            if (parse.length < 3)
            {
                sendSystemMessageTestingOnly(self, "SYNTAX COMPLETETASKNAME QUESTNAME TASKNAME " + text);
                return SCRIPT_CONTINUE;
            }
            String questName = parse[1];
            int questCrc = questGetQuestId(questName);
            if (questCrc == 0)
            {
                sendSystemMessageTestingOnly(self, "BAD QUEST NAME");
            }
            else 
            {
                String taskName = parse[2];
                int taskId = groundquests.getTaskId(questCrc, taskName);
                if (taskId == -1)
                {
                    sendSystemMessageTestingOnly(self, "BAD TASK NAME");
                }
                else 
                {
                    questCompleteTask(questCrc, taskId, self);
                }
            }
        }
        else if (parse[0].equalsIgnoreCase("failtaskname"))
        {
            if (parse.length < 3)
            {
                sendSystemMessageTestingOnly(self, "SYNTAX FAILTASKNAME QUESTNAME TASKNAME " + text);
                return SCRIPT_CONTINUE;
            }
            String questName = parse[1];
            int questCrc = questGetQuestId(questName);
            if (questCrc == 0)
            {
                sendSystemMessageTestingOnly(self, "BAD QUEST NAME");
            }
            else 
            {
                String taskName = parse[2];
                int taskId = groundquests.getTaskId(questCrc, taskName);
                if (taskId == -1)
                {
                    sendSystemMessageTestingOnly(self, "BAD TASK NAME");
                }
                else 
                {
                    questFailTask(questCrc, taskId, self);
                }
            }
        }
        else if (parse[0].equalsIgnoreCase("activatetaskid"))
        {
            if (parse.length < 3)
            {
                sendSystemMessageTestingOnly(self, "SYNTAX ACTIVATETASKID QUESTNAME TASKID " + text);
                return SCRIPT_CONTINUE;
            }
            String questName = parse[1];
            int questCrc = questGetQuestId(questName);
            if (questCrc == 0)
            {
                sendSystemMessageTestingOnly(self, "BAD QUEST NAME");
            }
            else 
            {
                int taskId = utils.stringToInt(parse[2]);
                questActivateTask(questCrc, taskId, self);
            }
        }
        else if (parse[0].equalsIgnoreCase("completetaskid"))
        {
            if (parse.length < 3)
            {
                sendSystemMessageTestingOnly(self, "SYNTAX COMPLETETASKID QUESTNAME TASKID " + text);
                return SCRIPT_CONTINUE;
            }
            String questName = parse[1];
            int questCrc = questGetQuestId(questName);
            if (questCrc == 0)
            {
                sendSystemMessageTestingOnly(self, "BAD QUEST NAME");
            }
            else 
            {
                int taskId = utils.stringToInt(parse[2]);
                questCompleteTask(questCrc, taskId, self);
            }
        }
        else if (parse[0].equalsIgnoreCase("failtaskid"))
        {
            if (parse.length < 3)
            {
                sendSystemMessageTestingOnly(self, "SYNTAX FAILTASKID QUESTNAME TASKID " + text);
                return SCRIPT_CONTINUE;
            }
            String questName = parse[1];
            int questCrc = questGetQuestId(questName);
            if (questCrc == 0)
            {
                sendSystemMessageTestingOnly(self, "BAD QUEST NAME");
            }
            else 
            {
                int taskId = utils.stringToInt(parse[2]);
                questFailTask(questCrc, taskId, self);
            }
        }
        else if (parse[0].equalsIgnoreCase("sendsignal"))
        {
            if (parse.length < 2)
            {
                return SCRIPT_CONTINUE;
            }
            groundquests.sendSignal(self, parse[1]);
        }
        return SCRIPT_CONTINUE;
    }
    public void showHelp() throws InterruptedException
    {
        for (int i = 0; i < OPTIONS.length; i++)
        {
            sendSystemMessageTestingOnly(getSelf(), OPTIONS[i]);
        }
        return;
    }
}
