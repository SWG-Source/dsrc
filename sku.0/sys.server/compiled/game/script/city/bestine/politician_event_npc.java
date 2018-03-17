package script.city.bestine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.planetary_map;

public class politician_event_npc extends script.base_script
{
    public politician_event_npc()
    {
    }
    public static final String FACE_TO_VOLUME_NAME = "faceToTriggerVolume";
    public static final String EVIDENCE_ITEM_SEAN_1 = "object/tangible/loot/quest/sean_questp_ctestimony.iff";
    public static final String EVIDENCE_ITEM_SEAN_2 = "object/tangible/loot/quest/sean_questp_mdisk.iff";
    public static final String EVIDENCE_ITEM_SEAN_3 = "object/tangible/loot/quest/sean_questp_htestimony.iff";
    public static final String EVIDENCE_ITEM_VICTOR_1 = "object/tangible/loot/quest/victor_questp_testimony.iff";
    public static final String EVIDENCE_ITEM_VICTOR_2 = "object/tangible/loot/quest/victor_questp_jregistry.iff";
    public static final String EVIDENCE_ITEM_VICTOR_3 = "object/tangible/loot/quest/victor_questp_receipt.iff";
    public static final String VARNAME_ELECTION_STATUS = "strElectionStatus";
    public static final String VARNAME_ELECTION_WINNER = "strElectionWinner";
    public static final String VARNAME_ELECTION_NUM = "intElectionNum";
    public static final String OBJVAR_TATOOINE_CAPITOL = "bestine.tatooineCapitolObjId";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume(FACE_TO_VOLUME_NAME, 8.0f, true);
        if (isMob(self))
        {
            String creatureName = ai_lib.getCreatureName(self);
            if (creatureName.equals("tour_aryon"))
            {
                messageTo(self, "handleGovernorSetup", null, 9, false);
                return SCRIPT_CONTINUE;
            }
        }
        messageTo(self, "handlePoliticianEventNpcSetup", null, 18, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        createTriggerVolume(FACE_TO_VOLUME_NAME, 8.0f, true);
        if (isMob(self))
        {
            String creatureName = ai_lib.getCreatureName(self);
            if (creatureName.equals("tour_aryon"))
            {
                messageTo(self, "handleGovernorSetup", null, 9, false);
                return SCRIPT_CONTINUE;
            }
        }
        messageTo(self, "handlePoliticianEventNpcSetup", null, 150, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (isMob(self))
        {
            if (isPlayer(breacher))
            {
                if (volumeName.equals(FACE_TO_VOLUME_NAME))
                {
                    if (!isInNpcConversation(self))
                    {
                        if (canSee(self, breacher))
                        {
                            faceTo(self, breacher);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        if (isMob(self))
        {
            String creatureName = ai_lib.getCreatureName(self);
            if (creatureName.equals("tour_aryon"))
            {
                String itemTemplate = getTemplateName(item);
                if ((itemTemplate.equals(EVIDENCE_ITEM_SEAN_1)) || (itemTemplate.equals(EVIDENCE_ITEM_SEAN_2)) || (itemTemplate.equals(EVIDENCE_ITEM_SEAN_3)) || (itemTemplate.equals(EVIDENCE_ITEM_VICTOR_1)) || (itemTemplate.equals(EVIDENCE_ITEM_VICTOR_2)) || (itemTemplate.equals(EVIDENCE_ITEM_VICTOR_3)))
                {
                    string_id spokenMsg = new string_id("bestine", "give_governor_item");
                    chat.chat(self, spokenMsg);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleGovernorSetup(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!ai_lib.getCreatureName(self).equals("tour_aryon"))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary msgData = new dictionary();
        msgData.put("objRequester", self);
        obj_id tatooineCapitol = getObjIdObjVar(self, OBJVAR_TATOOINE_CAPITOL);
        messageTo(tatooineCapitol, "handleGovernorSetupRequest", msgData, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handleGovernorSetupResponse(obj_id self, dictionary params) throws InterruptedException
    {
        clearElectionObjvars(self);
        String electionStatus = params.getString(VARNAME_ELECTION_STATUS);
        String electionStatusObjvar = "bestine." + electionStatus;
        setObjVar(self, electionStatusObjvar, params.getInt(VARNAME_ELECTION_NUM));
        setObjVar(self, "bestine.electionWinner", params.getString(VARNAME_ELECTION_WINNER));
        setObjVar(self, "bestine.votesForSean", params.getInt("intVotesForSean"));
        setObjVar(self, "bestine.votesForVictor", params.getInt("intVotesForVictor"));
        int timeNextElectionStarts = params.getInt("timeNextElectionStarts");
        if (timeNextElectionStarts > 0)
        {
            setObjVar(self, "bestine.timeNextElectionStarts", timeNextElectionStarts);
        }
        messageTo(self, "handleGovernorDataStorage", null, 600, false);
        return SCRIPT_CONTINUE;
    }
    public int handleGovernorDataStorage(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "bestine.electionStarted"))
        {
            int votesForSean = 0;
            int votesForVictor = 0;
            if (hasObjVar(self, "bestine.votesForSean"))
            {
                votesForSean = getIntObjVar(self, "bestine.votesForSean");
            }
            if (hasObjVar(self, "bestine.votesForVictor"))
            {
                votesForVictor = getIntObjVar(self, "bestine.votesForVictor");
            }
            dictionary msgData = new dictionary();
            msgData.put("objRequester", self);
            msgData.put("intVotesForSean", votesForSean);
            msgData.put("intVotesForVictor", votesForVictor);
            obj_id tatooineCapitol = getObjIdObjVar(self, OBJVAR_TATOOINE_CAPITOL);
            messageTo(tatooineCapitol, "processGovernorDataStorageRequest", msgData, 1, false);
            messageTo(self, "handleGovernorDataStorage", null, 600, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleElectionStateChangeGovernorAlert(obj_id self, dictionary params) throws InterruptedException
    {
        clearElectionObjvars(self);
        String electionStatus = params.getString(VARNAME_ELECTION_STATUS);
        String electionWinner = params.getString(VARNAME_ELECTION_WINNER);
        int electionNum = params.getInt(VARNAME_ELECTION_NUM);
        int timeNextElectionStarts = params.getInt("timeNextElectionStarts");
        if (timeNextElectionStarts > 0)
        {
            setObjVar(self, "bestine.timeNextElectionStarts", timeNextElectionStarts);
        }
        else 
        {
            if (hasObjVar(self, "bestine.timeNextElectionStarts"))
            {
                removeObjVar(self, "bestine.timeNextElectionStarts");
            }
        }
        dictionary msgData = new dictionary();
        if (electionStatus.equals("electionEnded"))
        {
            int votesForSean = getIntObjVar(self, "bestine.votesForSean");
            int votesForVictor = getIntObjVar(self, "bestine.votesForVictor");
            if (votesForSean > votesForVictor)
            {
                electionWinner = "Sean";
            }
            if (votesForVictor > votesForSean)
            {
                electionWinner = "Victor";
            }
            if (votesForSean == votesForVictor)
            {
                int chance = rand(1, 2);
                if (chance == 1)
                {
                    electionWinner = "Sean";
                }
                else 
                {
                    electionWinner = "Victor";
                }
            }
            removeObjVar(self, "bestine.votesForSean");
            removeObjVar(self, "bestine.votesForVictor");
        }
        if (electionStatus.equals("electionStarted"))
        {
            messageTo(self, "handleGovernorDataStorage", null, 600, false);
        }
        String electionStatusObjvar = "bestine." + electionStatus;
        setObjVar(self, electionStatusObjvar, electionNum);
        setObjVar(self, "bestine.electionWinner", electionWinner);
        msgData.put(VARNAME_ELECTION_WINNER, electionWinner);
        obj_id tatooineCapitol = getObjIdObjVar(self, OBJVAR_TATOOINE_CAPITOL);
        messageTo(tatooineCapitol, "handleSetElectionWinner", msgData, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handlePoliticianEventNpcSetup(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, OBJVAR_TATOOINE_CAPITOL))
        {
            dictionary msgData = new dictionary();
            msgData.put("objRequester", self);
            obj_id tatooineCapitol = getObjIdObjVar(self, OBJVAR_TATOOINE_CAPITOL);
            listenToMessage(tatooineCapitol, "handlePoliticianEventStatusResponse");
            messageTo(tatooineCapitol, "handlePoliticianEventStatusRequest", msgData, 2, false);
        }
        else 
        {
            messageTo(self, "checkForCapitolBuilding", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int checkForCapitolBuilding(obj_id self, dictionary params) throws InterruptedException
    {
        map_location tatooineCapitol = planetary_map.findClosestLocation(self, "capitol", "");
        obj_id tatooineCapitolObjId = tatooineCapitol.getLocationId();
        deltadictionary dctScriptVars = self.getScriptVars();
        int numCapitolChecks = dctScriptVars.getInt("numCapitolChecks");
        if (!isIdValid(tatooineCapitolObjId))
        {
            if (numCapitolChecks < 4)
            {
                numCapitolChecks = numCapitolChecks + 1;
                dctScriptVars.put("numCapitolChecks", numCapitolChecks);
                messageTo(self, "checkForCapitolBuilding", null, 60, false);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                LOG("DESIGNER_FATAL", "BESTINE POLITICIAN EVENT: Could not find registered capitol building on Tatooine.");
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            setObjVar(self, OBJVAR_TATOOINE_CAPITOL, tatooineCapitolObjId);
            messageTo(self, "handlePoliticianEventNpcSetup", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePoliticianEventStatusResponse(obj_id self, dictionary params) throws InterruptedException
    {
        clearElectionObjvars(self);
        String electionStatus = params.getString(VARNAME_ELECTION_STATUS);
        String electionStatusObjvar = "bestine." + electionStatus;
        setObjVar(self, electionStatusObjvar, params.getInt(VARNAME_ELECTION_NUM));
        setObjVar(self, "bestine.electionWinner", params.getString(VARNAME_ELECTION_WINNER));
        return SCRIPT_CONTINUE;
    }
    public void clearElectionObjvars(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "bestine.electionStarted"))
        {
            removeObjVar(self, "bestine.electionStarted");
        }
        if (hasObjVar(self, "bestine.electionEnded"))
        {
            removeObjVar(self, "bestine.electionEnded");
        }
        if (hasObjVar(self, "bestine.electionWinner"))
        {
            removeObjVar(self, "bestine.electionWinner");
        }
    }
}
