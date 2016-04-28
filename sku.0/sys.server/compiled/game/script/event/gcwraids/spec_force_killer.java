package script.event.gcwraids;

import script.dictionary;
import script.library.*;
import script.obj_id;
import script.prose_package;
import script.string_id;

public class spec_force_killer extends script.base_script
{
    public spec_force_killer()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int killReb5 = questGetQuestId("quest/event_gcwcheerleader_sfreb5");
        int killReb10 = questGetQuestId("quest/event_gcwcheerleader_sfreb10");
        int killReb20 = questGetQuestId("quest/event_gcwcheerleader_sfreb20");
        int killReb50 = questGetQuestId("quest/event_gcwcheerleader_sfreb50");
        int killImp5 = questGetQuestId("quest/event_gcwcheerleader_sfimp5");
        int killImp10 = questGetQuestId("quest/event_gcwcheerleader_sfimp10");
        int killImp20 = questGetQuestId("quest/event_gcwcheerleader_sfimp20");
        int killImp50 = questGetQuestId("quest/event_gcwcheerleader_sfimp50");
        if (questIsQuestActive(killReb5, self) || questIsQuestActive(killImp5, self))
        {
            setObjVar(self, "event.event_gcwcheerleader.kills_required", 5);
        }
        if (questIsQuestActive(killReb10, self) || questIsQuestActive(killImp10, self))
        {
            setObjVar(self, "event.event_gcwcheerleader.kills_required", 10);
        }
        if (questIsQuestActive(killReb20, self) || questIsQuestActive(killImp20, self))
        {
            setObjVar(self, "event.event_gcwcheerleader.kills_required", 20);
        }
        if (questIsQuestActive(killReb50, self) || questIsQuestActive(killImp50, self))
        {
            setObjVar(self, "event.event_gcwcheerleader.kills_required", 50);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        int killReb5 = questGetQuestId("quest/event_gcwcheerleader_sfreb5");
        int killReb10 = questGetQuestId("quest/event_gcwcheerleader_sfreb10");
        int killReb20 = questGetQuestId("quest/event_gcwcheerleader_sfreb20");
        int killReb50 = questGetQuestId("quest/event_gcwcheerleader_sfreb50");
        int killImp5 = questGetQuestId("quest/event_gcwcheerleader_sfimp5");
        int killImp10 = questGetQuestId("quest/event_gcwcheerleader_sfimp10");
        int killImp20 = questGetQuestId("quest/event_gcwcheerleader_sfimp20");
        int killImp50 = questGetQuestId("quest/event_gcwcheerleader_sfimp50");
        if (!questIsQuestActive(killReb5, self) &&
                !questIsQuestActive(killReb10, self) &&
                !questIsQuestActive(killReb20, self) &&
                !questIsQuestActive(killReb50, self) &&
                !questIsQuestActive(killImp5, self) &&
                !questIsQuestActive(killImp10, self) &&
                !questIsQuestActive(killImp20, self) &&
                !questIsQuestActive(killImp50, self))
        {
            detachScript(self, "event.gcwraids.spec_force_killer");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleKillerDeathBlow(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id victim = params.getObjId("victim");
        if (pvp.hasKilledVictimRecently(self, victim))
        {
            return SCRIPT_CONTINUE;
        }
        String victimFaction = factions.getFaction(victim);
        if (victimFaction == null)
        {
            return SCRIPT_CONTINUE;
        }
        String killerFaction = factions.getFaction(self);
        if (victimFaction.equals(killerFaction))
        {
            return SCRIPT_CONTINUE;
        }
        int killsRequired = getIntObjVar(self, "event.event_gcwcheerleader.kills_required");
        killsRequired--;
        int killReb50 = questGetQuestId("quest/event_gcwcheerleader_sfreb50");
        int killImp50 = questGetQuestId("quest/event_gcwcheerleader_sfimp50");
        if (killsRequired == 0)
        {
            if (questIsQuestActive(killReb50, self))
            {
                badge.grantBadge(self, "bdg_accolade_live_event");
            }
            if (questIsQuestActive(killImp50, self))
            {
                badge.grantBadge(self, "bdg_accolade_live_event");
            }
            removeObjVar(self, "event.event_gcwcheerleader.kills_required");
            groundquests.sendSignal(self, "got_all_kills");
            messageTo(self, "detachSpecForceKillerScript", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "event.event_gcwcheerleader.kills_required", killsRequired);
        if (killsRequired == 1)
        {
            sendSystemMessage(self, new string_id("event/gcw_raids", "sf_kill_one_left"));
            return SCRIPT_CONTINUE;
        }
        string_id sysMesSID = new string_id("event/gcw_raids", "spec_force_kill_msg");
        prose_package pp = prose.getPackage(sysMesSID, "", killsRequired);
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int detachSpecForceKillerScript(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "event.gcwraids.spec_force_killer");
        return SCRIPT_CONTINUE;
    }
}
