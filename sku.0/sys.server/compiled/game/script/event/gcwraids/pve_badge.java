package script.event.gcwraids;

import script.dictionary;
import script.library.badge;
import script.obj_id;

public class pve_badge extends script.base_script
{
    public pve_badge()
    {
    }
    public int receiveCreditForKill(obj_id self, dictionary params) throws InterruptedException
    {
        int cmReb4 = questGetQuestId("quest/event_gcwcheerleader_cmreb4");
        int cmImp4 = questGetQuestId("quest/event_gcwcheerleader_cmimp4");
        if (questIsQuestActive(cmReb4, self) || questIsQuestComplete(cmReb4, self))
        {
            badge.grantBadge(self, "bdg_accolade_live_event");
            detachScript(self, "event.gcwraids.pve_badge");
            return SCRIPT_CONTINUE;
        }
        if (questIsQuestActive(cmImp4, self) || questIsQuestComplete(cmImp4, self))
        {
            badge.grantBadge(self, "bdg_accolade_live_event");
            detachScript(self, "event.gcwraids.pve_badge");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        int cmReb4 = questGetQuestId("quest/event_gcwcheerleader_cmreb4");
        int cmImp4 = questGetQuestId("quest/event_gcwcheerleader_cmimp4");
        if (!questIsQuestActive(cmReb4, self) && !questIsQuestActive(cmImp4, self))
        {
            detachScript(self, "event.gcwraids.pve_badge");
        }
        return SCRIPT_CONTINUE;
    }
}
