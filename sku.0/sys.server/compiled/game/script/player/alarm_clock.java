package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.prose;
import script.prose_package;

public class alarm_clock extends script.base_script
{
    public alarm_clock()
    {
    }
    public static final string_id SID_ALARM_CLOCK_FORMAT = new string_id("player/player_utility.stf", "alarm_clock_format");
    public static final string_id SID_ALARM_CLOCK_1_1000 = new string_id("player/player_utility.stf", "alarm_clock_1_1000");
    public static final string_id SID_ALARM_CLOCK_SET = new string_id("player/player_utility.stf", "alarm_clock_set");
    public int msgAlarmClock(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
        }
        String sentAlarmMsg = params.getString("message");
        sui.msgbox(self, self, sentAlarmMsg, 0, "Alarm Clock", 0, "noHandler");
        LOG("LOG_CHANNEL", "Received Alarm Clock message: " + sentAlarmMsg);
        return SCRIPT_OVERRIDE;
    }
    public int alarmClock(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        int totalWords = st.countTokens();
        String[] parsedText;
        if (totalWords > 0)
        {
            parsedText = new String[totalWords];
        }
        else 
        {
            return SCRIPT_OVERRIDE;
        }
        for (int i = 0; st.hasMoreTokens(); i++)
        {
            parsedText[i] = st.nextToken();
            LOG("LOG_CHANNEL", "parsedText[" + i + "] " + parsedText[i]);
        }
        long alarmDelay = 0;
        if (totalWords < 2)
        {
            sendSystemMessage(self, SID_ALARM_CLOCK_FORMAT);
            LOG("LOG_CHANNEL", "Format: /alarmclock <time> <message>");
            return SCRIPT_OVERRIDE;
        }
        else 
        {
            Long time;
            try
            {
                time = new Long(parsedText[0]);
                alarmDelay = time.longValue();
            }
            catch(NumberFormatException err)
            {
                sendSystemMessage(self, SID_ALARM_CLOCK_FORMAT);
                LOG("LOG_CHANNEL", "Format: /alarmclock <time> <message>");
                return SCRIPT_OVERRIDE;
            }
            if (alarmDelay < 1 || alarmDelay > 1000)
            {
                sendSystemMessage(self, SID_ALARM_CLOCK_1_1000);
                LOG("LOG_CHANNEL", "Alarm time must be from 1 to 1000 minutes");
                return SCRIPT_OVERRIDE;
            }
            else 
            {
                StringBuffer sb = new StringBuffer();
                for (int i = 1; i < parsedText.length; i++)
                {
                    if (parsedText.length - i == 1)
                    {
                        sb.append(parsedText[i]);
                    }
                    else 
                    {
                        sb.append(parsedText[i] + " ");
                    }
                }
                String alarmMessage = sb.toString();
                LOG("LOG_CHANNEL", "Sending Alarm Clock Message (in " + alarmDelay + " minutes): " + alarmMessage);
                prose_package ppAlarm = prose.getPackage(SID_ALARM_CLOCK_SET);
                prose.setDI(ppAlarm, (int)alarmDelay);
                sendSystemMessageProse(self, ppAlarm);
                dictionary dctMsg = new dictionary();
                dctMsg.put("message", alarmMessage);
                messageTo(self, "msgAlarmClock", dctMsg, alarmDelay * 60, false);
            }
            return SCRIPT_OVERRIDE;
        }
    }
}
