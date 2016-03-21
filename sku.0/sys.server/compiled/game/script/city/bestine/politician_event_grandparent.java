package script.city.bestine;

import script.*;
import script.library.utils;

public class politician_event_grandparent extends script.base_script
{
    public politician_event_grandparent()
    {
    }
    public static final String MASTER_OBJECT_TEMPLATE = "object/tangible/poi/tatooine/bestine/bestine_politician_event_master_object.iff";
    public static final String VARNAME_ELECTION_STATUS = "strElectionStatus";
    public static final String VARNAME_ELECTION_WINNER = "strElectionWinner";
    public static final String VARNAME_ELECTION_NUM = "intElectionNum";
    public static final String MISSING_NPC_TEMPLATE = "object/tangible/spawning/static_npc/victor_questn_capitol.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "bootStrap", null, 2, false);
        messageTo(self, "spawnMissingNpc", null, 120, false);
        return SCRIPT_CONTINUE;
    }
    public int bootStrap(obj_id self, dictionary params) throws InterruptedException
    {
        deltadictionary dctScriptVars = self.getScriptVars();
        obj_id[] objMasterObjectIds = getAllObjectsWithTemplate(getLocation(self), 10, MASTER_OBJECT_TEMPLATE);
        if ((objMasterObjectIds != null) && (objMasterObjectIds.length != 0))
        {
            dctScriptVars.put("objMasterObjectId", objMasterObjectIds[0]);
            updatePoliticianEventStatus(self);
        }
        else 
        {
            location locTest = getLocation(self);
            locTest.x = locTest.x + 1;
            obj_id objPoliticianEventMaster = createObject(MASTER_OBJECT_TEMPLATE, locTest);
            persistObject(objPoliticianEventMaster);

            dctScriptVars.put("objMasterObjectId", objPoliticianEventMaster);
            dctScriptVars.put(VARNAME_ELECTION_STATUS, "electionStarted");
            dctScriptVars.put(VARNAME_ELECTION_WINNER, "none");
            dctScriptVars.put(VARNAME_ELECTION_NUM, 1);
            setObjVar(objPoliticianEventMaster, "bestine.tatooineCapitolObjId", self);
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnMissingNpc(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objCapitolLobby = getCellId(self, "lobby");
        location locMissingNpcSpawnerLocation = new location(6.10f, 0.30f, -0.49f, "tatooine", objCapitolLobby);
        obj_id[] objMissingNpcSpawnerIds = getAllObjectsWithTemplate(locMissingNpcSpawnerLocation, 10, MISSING_NPC_TEMPLATE);

        if ( objMissingNpcSpawnerIds == null || objMissingNpcSpawnerIds.length < 1 ) {
            createObject(MISSING_NPC_TEMPLATE, locMissingNpcSpawnerLocation);
        }

        return SCRIPT_CONTINUE;
    }
    public void updatePoliticianEventStatus(obj_id self) throws InterruptedException
    {
        deltadictionary dctScriptVars = self.getScriptVars();
        obj_id masterObjectId = dctScriptVars.getObjId("objMasterObjectId");

        int intElectionNum = 1;
        obj_var_list varList = getObjVarList(masterObjectId, "bestine");
        if (varList != null)
        {
            int numItem = varList.getNumItems();
            for (int i = 0; i < numItem; i++)
            {
                obj_var var = varList.getObjVar(i);
                String varName = var.getName();
                switch (varName) {
                    case "electionStarted":
                        intElectionNum = var.getIntData();
                        dctScriptVars.put(VARNAME_ELECTION_STATUS, "electionStarted");
                        dctScriptVars.put(VARNAME_ELECTION_NUM, intElectionNum);
                        break;
                    case "electionEnded":
                        intElectionNum = var.getIntData();
                        dctScriptVars.put(VARNAME_ELECTION_STATUS, "electionEnded");
                        dctScriptVars.put(VARNAME_ELECTION_NUM, intElectionNum);
                        break;
                    case "electionWinner":
                        String strVarValue = var.getStringData();
                        dctScriptVars.put(VARNAME_ELECTION_WINNER, strVarValue);
                        break;
                    case "votesForSean":
                        int intVotesForSean = var.getIntData();
                        dctScriptVars.put("intVotesForSean", intVotesForSean);
                        break;
                    case "votesForVictor":
                        int intVotesForVictor = var.getIntData();
                        dctScriptVars.put("intVotesForVictor", intVotesForVictor);
                        break;
                    case "timeNextElectionStarts":
                        int timeNextElectionStarts = var.getIntData();
                        dctScriptVars.put("timeNextElectionStarts", timeNextElectionStarts);
                        break;
                }
            }
        }
    }
    public int handlePoliticianEventStateChange(obj_id self, dictionary params) throws InterruptedException
    {
        updatePoliticianEventStatus(self);

        deltadictionary dctScriptVars = self.getScriptVars();
        obj_id objBestineGovernor = dctScriptVars.getObjId("objBestineGovernor");

        dictionary dctParams = new dictionary();
        dctParams.put(VARNAME_ELECTION_STATUS, dctScriptVars.getString(VARNAME_ELECTION_STATUS));
        dctParams.put(VARNAME_ELECTION_WINNER, dctScriptVars.getString(VARNAME_ELECTION_WINNER));
        dctParams.put(VARNAME_ELECTION_NUM, dctScriptVars.getInt(VARNAME_ELECTION_NUM));

        if (utils.hasScriptVar(self, "timeNextElectionStarts"))
        {
            dctParams.put("timeNextElectionStarts", dctScriptVars.getInt("timeNextElectionStarts"));
        }

        messageTo(objBestineGovernor, "handleElectionStateChangeGovernorAlert", dctParams, 1, false);

        return SCRIPT_CONTINUE;
    }
    public int handleSetElectionWinner(obj_id self, dictionary params) throws InterruptedException
    {
        String strElectionWinner = params.getString(VARNAME_ELECTION_WINNER);
        deltadictionary dctScriptVars = self.getScriptVars();
        obj_id masterObjectId = dctScriptVars.getObjId("objMasterObjectId");
        setObjVar(masterObjectId, "bestine.electionWinner", strElectionWinner);
        dctScriptVars.put(VARNAME_ELECTION_WINNER, strElectionWinner);
        dictionary dctParams = new dictionary();
        dctParams.put(VARNAME_ELECTION_STATUS, dctScriptVars.getString(VARNAME_ELECTION_STATUS));
        dctParams.put(VARNAME_ELECTION_WINNER, strElectionWinner);
        dctParams.put(VARNAME_ELECTION_NUM, dctScriptVars.getInt(VARNAME_ELECTION_NUM));
        broadcastMessage("handlePoliticianEventStatusResponse", dctParams);
        return SCRIPT_CONTINUE;
    }
    public int handleGovernorSetupRequest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objRequester = params.getObjId("objRequester");
        deltadictionary dctScriptVars = self.getScriptVars();
        dctScriptVars.put("objBestineGovernor", objRequester);
        dictionary dctParams = new dictionary();
        dctParams.put(VARNAME_ELECTION_STATUS, dctScriptVars.getString(VARNAME_ELECTION_STATUS));
        dctParams.put(VARNAME_ELECTION_WINNER, dctScriptVars.getString(VARNAME_ELECTION_WINNER));
        dctParams.put(VARNAME_ELECTION_NUM, dctScriptVars.getInt(VARNAME_ELECTION_NUM));
        dctParams.put("intVotesForSean", dctScriptVars.getInt("intVotesForSean"));
        dctParams.put("intVotesForVictor", dctScriptVars.getInt("intVotesForVictor"));
        if (utils.hasScriptVar(self, "timeNextElectionStarts"))
        {
            dctParams.put("timeNextElectionStarts", dctScriptVars.getInt("timeNextElectionStarts"));
        }
        messageTo(objRequester, "handleGovernorSetupResponse", dctParams, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int processGovernorDataStorageRequest(obj_id self, dictionary params) throws InterruptedException
    {
        int intVotesForSean = params.getInt("intVotesForSean");
        int intVotesForVictor = params.getInt("intVotesForVictor");
        deltadictionary dctScriptVars = self.getScriptVars();
        obj_id masterObjectId = dctScriptVars.getObjId("objMasterObjectId");
        setObjVar(masterObjectId, "bestine.votesForSean", intVotesForSean);
        setObjVar(masterObjectId, "bestine.votesForVictor", intVotesForVictor);
        return SCRIPT_CONTINUE;
    }
    public int handlePoliticianEventStatusRequest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objRequester = params.getObjId("objRequester");
        deltadictionary dctScriptVars = self.getScriptVars();
        dictionary dctParams = new dictionary();
        dctParams.put(VARNAME_ELECTION_STATUS, dctScriptVars.getString(VARNAME_ELECTION_STATUS));
        dctParams.put(VARNAME_ELECTION_WINNER, dctScriptVars.getString(VARNAME_ELECTION_WINNER));
        dctParams.put(VARNAME_ELECTION_NUM, dctScriptVars.getInt(VARNAME_ELECTION_NUM));
        messageTo(objRequester, "handlePoliticianEventStatusResponse", dctParams, 1, false);
        return SCRIPT_CONTINUE;
    }
}
