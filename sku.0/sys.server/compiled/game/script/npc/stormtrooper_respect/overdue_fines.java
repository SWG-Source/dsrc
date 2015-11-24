package script.npc.stormtrooper_respect;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.factions;
import script.library.prose;

public class overdue_fines extends script.base_script
{
    public overdue_fines()
    {
    }
    public static final String WARNING_SYS_MSG = "datatables/npc/stormtrooper_attitude/responsetext.iff";
    public static final String PP_FILE_LOC = "stormtrooper_attitude/st_response";
    public static final int ROW_FOR_SYS_WARNING = 41;
    public static final int ROW_FOR_SYS_WARNING_PASTDUE = 42;
    public static final int SUBJECT_WARNING_MSG = 42;
    public static int WEEK_IN_SECONDS = 60 * 60 * 24 * 7;
    public static int DAY_IN_SECONDS = 60 * 60 * 24;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            String delayWeekStr = getConfigSetting("GameServer", "STRespectWeekDelay");
            String delayDayStr = getConfigSetting("GameServer", "STRespectDayDelay");
            if (delayWeekStr != null && !delayWeekStr.equals(""))
            {
                WEEK_IN_SECONDS = Integer.parseInt(delayWeekStr);
            }
            if (delayDayStr != null && !delayDayStr.equals(""))
            {
                DAY_IN_SECONDS = Integer.parseInt(delayDayStr);
            }
        }
        if (hasObjVar(self, "overdue_fine"))
        {
            if ((getGameTime() - getIntObjVar(self, "overdue_fine") > WEEK_IN_SECONDS))
            {
                string_id body = new string_id(PP_FILE_LOC, utils.dataTableGetString(WARNING_SYS_MSG, ROW_FOR_SYS_WARNING, 1));
                string_id subject = new string_id(PP_FILE_LOC, utils.dataTableGetString(WARNING_SYS_MSG, SUBJECT_WARNING_MSG, 1));
                utils.sendMail(subject, body, self, "@stormtrooper_attitude/st_response:imperial_collection_email_t");
                utils.removeObjVar(self, "overdue_fine");
                utils.setObjVar(self, "week_old_fine", getGameTime());
            }
        }
        else if (hasObjVar(self, "week_old_fine") && (getGameTime() - getIntObjVar(self, "week_old_fine") > DAY_IN_SECONDS))
        {
            string_id body = new string_id(PP_FILE_LOC, utils.dataTableGetString(WARNING_SYS_MSG, ROW_FOR_SYS_WARNING_PASTDUE, 1));
            string_id subject = new string_id(PP_FILE_LOC, utils.dataTableGetString(WARNING_SYS_MSG, SUBJECT_WARNING_MSG, 1));
            utils.sendMail(subject, body, self, "@stormtrooper_attitude/st_response:imperial_collection_email_t");
            int fineAmt = utils.getIntObjVar(self, "trooper_fine");
            int factionLoss = (0 - (fineAmt / 1000));
            if (factionLoss > -1)
            {
                factionLoss = -1;
            }
            factions.addFactionStanding(self, factions.FACTION_IMPERIAL, factionLoss);
            utils.removeObjVar(self, "week_old_fine");
            utils.setObjVar(self, "week_old_fine", getGameTime());
        }
        return SCRIPT_CONTINUE;
    }
}
