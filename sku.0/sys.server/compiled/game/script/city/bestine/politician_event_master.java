package script.city.bestine;

import script.dictionary;
import script.library.utils;
import script.obj_id;

public class politician_event_master extends script.base_script
{
    public politician_event_master()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "beginBestineElection", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int getElectionDuration() throws InterruptedException
    {
        String durationConfigSetting = getConfigSetting("BestineEvents", "PoliticianEventDuration");
        int electionDuration = utils.stringToInt(durationConfigSetting);
        if (electionDuration == 0 || electionDuration == -1)
        {
            electionDuration = 2592000;
        }
        return electionDuration;
    }
    public int beginBestineElection(obj_id self, dictionary params) throws InterruptedException
    {
        int electionNum = 1;
        if (hasObjVar(self, "bestine.electionEnded"))
        {
            electionNum = getIntObjVar(self, "bestine.electionEnded") + 1;
            removeObjVar(self, "bestine.electionEnded");
        }
        if (!hasObjVar(self, "bestine.electionWinner"))
        {
            setObjVar(self, "bestine.electionWinner", "none");
        }
        if (hasObjVar(self, "bestine.timeNextElectionStarts"))
        {
            removeObjVar(self, "bestine.timeNextElectionStarts");
        }
        setObjVar(self, "bestine.electionStarted", electionNum);
        setObjVar(self, "bestine.votesForSean", 0);
        setObjVar(self, "bestine.votesForVictor", 0);
        obj_id tatooineCapitolObjId = getObjIdObjVar(self, "bestine.tatooineCapitolObjId");
        messageTo(tatooineCapitolObjId, "handlePoliticianEventStateChange", null, 1, false);
        int electionDuration = getElectionDuration();
        messageTo(self, "endBestineElection", null, electionDuration, true);
        return SCRIPT_CONTINUE;
    }
    public int endBestineElection(obj_id self, dictionary params) throws InterruptedException
    {
        int electionNum = 1;
        if (hasObjVar(self, "bestine.electionStarted"))
        {
            electionNum = getIntObjVar(self, "bestine.electionStarted");
            removeObjVar(self, "bestine.electionStarted");
        }
        if (!hasObjVar(self, "bestine.electionWinner"))
        {
            setObjVar(self, "bestine.electionWinner", "none");
        }
        setObjVar(self, "bestine.electionEnded", electionNum);
        removeObjVar(self, "bestine.votesForSean");
        removeObjVar(self, "bestine.votesForVictor");
        obj_id tatooineCapitolObjId = getObjIdObjVar(self, "bestine.tatooineCapitolObjId");
        messageTo(tatooineCapitolObjId, "handlePoliticianEventStateChange", null, 1, false);
        int electionDuration = getElectionDuration();
        int timeNextElectionStarts = getGameTime() + electionDuration;
        setObjVar(self, "bestine.timeNextElectionStarts", timeNextElectionStarts);
        messageTo(self, "beginBestineElection", null, electionDuration, true);
        return SCRIPT_CONTINUE;
    }
}
