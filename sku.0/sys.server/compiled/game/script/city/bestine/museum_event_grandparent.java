package script.city.bestine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;

public class museum_event_grandparent extends script.base_script
{
    public museum_event_grandparent()
    {
    }
    public static final String MASTER_OBJECT_TEMPLATE = "object/tangible/poi/tatooine/bestine/bestine_museum_event_master_object.iff";
    public static final String VARNAME_MUSEUM_STATUS = "strMuseumEventStatus";
    public static final String VARNAME_MUSEUM_WINNER = "strMuseumEventWinner";
    public static final String VARNAME_MUSEUM_NUM = "intMuseumEventNum";
    public static final String VARNAME_MUSEUM_WINNER_ARTWORK_INDEX = "intMuseumWinnerArtworkIndex";
    public static final String OBJVAR_MUSEUM_BUILDING = "bestine.objMuseumBuilding";
    public static final String DATATABLE_NAME = "datatables/city/bestine/bestine_museum_event.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "bootStrap", null, 2, false);
        messageTo(self, "spawnCurator", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int bootStrap(obj_id self, dictionary params) throws InterruptedException
    {
        deltadictionary dctScriptVars = self.getScriptVars();
        obj_id[] objMasterObjectIds = getAllObjectsWithTemplate(getLocation(self), 10, MASTER_OBJECT_TEMPLATE);
        if ((objMasterObjectIds != null) && (objMasterObjectIds.length != 0))
        {
            dctScriptVars.put("objMasterObjectId", objMasterObjectIds[0]);
            updateMuseumEventStatus(self);
            String strMuseumEventWinner = dctScriptVars.getString(VARNAME_MUSEUM_WINNER);
            int intMuseumEventWinnerArtworkIndex = dctScriptVars.getInt(VARNAME_MUSEUM_WINNER_ARTWORK_INDEX);
            if (strMuseumEventWinner != null && !strMuseumEventWinner.equals("none"))
            {
                dictionary artistData = dataTableGetRow(DATATABLE_NAME, strMuseumEventWinner);
                String strArtworkColumn = "artwork_" + intMuseumEventWinnerArtworkIndex;
                String strArtworkTemplate = artistData.getString(strArtworkColumn);
                dctScriptVars.put("strFeaturedArtworkTemplate", strArtworkTemplate);
                String locXColumn = "locX_" + intMuseumEventWinnerArtworkIndex;
                String locYColumn = "locY_" + intMuseumEventWinnerArtworkIndex;
                String locZColumn = "locZ_" + intMuseumEventWinnerArtworkIndex;
                float locX = artistData.getFloat(locXColumn);
                float locY = artistData.getFloat(locYColumn);
                float locZ = artistData.getFloat(locZColumn);
                obj_id objMuseumLobby = getCellId(self, "lobby");
                location locArtworkLocation = new location(locX, locY, locZ, "tatooine", objMuseumLobby);
                obj_id objNewArtwork = createObject(strArtworkTemplate, locArtworkLocation);
                setYaw(objNewArtwork, -91);
                dctScriptVars.put("objFeaturedArtwork", objNewArtwork);
            }
        }
        else 
        {
            location locTest = getLocation(self);
            locTest.x = locTest.x + 1;
            obj_id objMuseumEventMaster = createObject(MASTER_OBJECT_TEMPLATE, locTest);
            dctScriptVars.put("objMasterObjectId", objMuseumEventMaster);
            dctScriptVars.put(VARNAME_MUSEUM_STATUS, "museumEventStarted");
            dctScriptVars.put(VARNAME_MUSEUM_WINNER, "none");
            dctScriptVars.put(VARNAME_MUSEUM_NUM, 1);
            dctScriptVars.put(VARNAME_MUSEUM_WINNER_ARTWORK_INDEX, 0);
            setObjVar(objMuseumEventMaster, OBJVAR_MUSEUM_BUILDING, self);
        }
        return SCRIPT_CONTINUE;
    }
    public void updateMuseumEventStatus(obj_id self) throws InterruptedException
    {
        deltadictionary dctScriptVars = self.getScriptVars();
        obj_id masterObjectId = dctScriptVars.getObjId("objMasterObjectId");
        if (utils.hasScriptVar(self, "strMuseumEventEntry01"))
        {
            utils.removeScriptVar(self, "strMuseumEventEntry01");
        }
        if (utils.hasScriptVar(self, "strMuseumEventEntry02"))
        {
            utils.removeScriptVar(self, "strMuseumEventEntry02");
        }
        if (utils.hasScriptVar(self, "strMuseumEventEntry03"))
        {
            utils.removeScriptVar(self, "strMmuseumEventEntry03");
        }
        if (utils.hasScriptVar(self, "intMuseumEventIndex01"))
        {
            utils.removeScriptVar(self, "intMuseumEventEntry01");
        }
        if (utils.hasScriptVar(self, "intMuseumEventIndex02"))
        {
            utils.removeScriptVar(self, "intMuseumEventEntry02");
        }
        if (utils.hasScriptVar(self, "intMuseumEventIndex03"))
        {
            utils.removeScriptVar(self, "intMuseumEventIndex03");
        }
        if (utils.hasScriptVar(self, "intVotesForEntry01"))
        {
            utils.removeScriptVar(self, "intVotesForEntry01");
        }
        if (utils.hasScriptVar(self, "intVotesForEntry02"))
        {
            utils.removeScriptVar(self, "intVotesForEntry02");
        }
        if (utils.hasScriptVar(self, "intVotesForEntry03"))
        {
            utils.removeScriptVar(self, "intVotesForEntry03");
        }
        int intMuseumEventNum = 1;
        obj_var_list varList = getObjVarList(masterObjectId, "bestine");
        if (varList != null)
        {
            int numItem = varList.getNumItems();
            for (int i = 0; i < numItem; i++)
            {
                obj_var var = varList.getObjVar(i);
                String varName = var.getName();
                if (varName.equals("museumEventStarted"))
                {
                    intMuseumEventNum = var.getIntData();
                    dctScriptVars.put(VARNAME_MUSEUM_STATUS, "museumEventStarted");
                    dctScriptVars.put(VARNAME_MUSEUM_NUM, intMuseumEventNum);
                }
                else if (varName.equals("museumEventEnded"))
                {
                    intMuseumEventNum = var.getIntData();
                    dctScriptVars.put(VARNAME_MUSEUM_STATUS, "museumEventEnded");
                    dctScriptVars.put(VARNAME_MUSEUM_NUM, intMuseumEventNum);
                }
                else if (varName.equals("museumEventWinner"))
                {
                    String strVarValue = var.getStringData();
                    if (strVarValue == null || strVarValue.equals(""))
                    {
                        strVarValue = "none";
                    }
                    dctScriptVars.put(VARNAME_MUSEUM_WINNER, strVarValue);
                }
                else if (varName.equals("museumEventWinnerArtworkIndex"))
                {
                    int intWinnerArtworkIndexValue = var.getIntData();
                    dctScriptVars.put(VARNAME_MUSEUM_WINNER_ARTWORK_INDEX, intWinnerArtworkIndexValue);
                }
                else if (varName.equals("museumEventEntry01"))
                {
                    String strEntry01Value = var.getStringData();
                    dctScriptVars.put("strMuseumEventEntry01", strEntry01Value);
                }
                else if (varName.equals("museumEventEntry02"))
                {
                    String strEntry02Value = var.getStringData();
                    dctScriptVars.put("strMuseumEventEntry02", strEntry02Value);
                }
                else if (varName.equals("museumEventEntry03"))
                {
                    String strEntry03Value = var.getStringData();
                    dctScriptVars.put("strMuseumEventEntry03", strEntry03Value);
                }
                else if (varName.equals("museumEventIndex01"))
                {
                    int intIndex01Value = var.getIntData();
                    dctScriptVars.put("intMuseumEventIndex01", intIndex01Value);
                }
                else if (varName.equals("museumEventIndex02"))
                {
                    int intIndex02Value = var.getIntData();
                    dctScriptVars.put("intMuseumEventIndex02", intIndex02Value);
                }
                else if (varName.equals("museumEventIndex03"))
                {
                    int intIndex03Value = var.getIntData();
                    dctScriptVars.put("intMuseumEventIndex03", intIndex03Value);
                }
                else if (varName.equals("votesForEntry01"))
                {
                    int intVotesForEntry01 = var.getIntData();
                    dctScriptVars.put("intVotesForEntry01", intVotesForEntry01);
                }
                else if (varName.equals("votesForEntry02"))
                {
                    int intVotesForEntry02 = var.getIntData();
                    dctScriptVars.put("intVotesForEntry02", intVotesForEntry02);
                }
                else if (varName.equals("votesForEntry03"))
                {
                    int intVotesForEntry03 = var.getIntData();
                    dctScriptVars.put("intVotesForEntry03", intVotesForEntry03);
                }
                else if (varName.equals("timeNextEventStarts"))
                {
                    int timeNextEventStarts = var.getIntData();
                    dctScriptVars.put("timeNextEventStarts", timeNextEventStarts);
                }
            }
        }
        return;
    }
    public int spawnCurator(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objMuseumRoom = getCellId(self, "livingroom2");
        location locCuratorLocation = new location(22.6f, 0.19f, -0.15f, "tatooine", objMuseumRoom);
        obj_id template = create.object("object/tangible/ground_spawning/area_spawner.iff", locCuratorLocation);
        setObjVar(template, "spawns", "lilas_dinhint");
        setObjVar(template, "npc_name", "Lilas Dinhint");
        setObjVar(template, "quest_script", "conversation.lilas_dinhint");
        String spawnerObjName = "Lilas Dinhint Spawner";
        setName(template, spawnerObjName);
        attachScript(template, "city.bestine.museum_event_spawner");
        return SCRIPT_CONTINUE;
    }
    public int handleMuseumEventStateChange(obj_id self, dictionary params) throws InterruptedException
    {
        updateMuseumEventStatus(self);
        deltadictionary dctScriptVars = self.getScriptVars();
        obj_id objBestineCurator = dctScriptVars.getObjId("objBestineCurator");
        dictionary dctParams = new dictionary();
        dctParams.put(VARNAME_MUSEUM_STATUS, dctScriptVars.getString(VARNAME_MUSEUM_STATUS));
        dctParams.put(VARNAME_MUSEUM_WINNER, dctScriptVars.getString(VARNAME_MUSEUM_WINNER));
        dctParams.put(VARNAME_MUSEUM_NUM, dctScriptVars.getInt(VARNAME_MUSEUM_NUM));
        dctParams.put(VARNAME_MUSEUM_WINNER_ARTWORK_INDEX, dctScriptVars.getInt(VARNAME_MUSEUM_WINNER_ARTWORK_INDEX));
        if (utils.hasScriptVar(self, "timeNextEventStarts"))
        {
            dctParams.put("timeNextEventStarts", dctScriptVars.getInt("timeNextEventStarts"));
        }
        messageTo(objBestineCurator, "handleMuseumEventStateChangeCuratorAlert", dctParams, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int handleSetMuseumEventWinner(obj_id self, dictionary params) throws InterruptedException
    {
        String strMuseumEventWinner = params.getString(VARNAME_MUSEUM_WINNER);
        if (strMuseumEventWinner == null || strMuseumEventWinner.equals(""))
        {
            strMuseumEventWinner = "none";
        }
        int intMuseumEventWinnerArtworkIndex = params.getInt(VARNAME_MUSEUM_WINNER_ARTWORK_INDEX);
        deltadictionary dctScriptVars = self.getScriptVars();
        dctScriptVars.put(VARNAME_MUSEUM_WINNER, strMuseumEventWinner);
        dctScriptVars.put(VARNAME_MUSEUM_WINNER_ARTWORK_INDEX, intMuseumEventWinnerArtworkIndex);
        String eventStatus = dctScriptVars.getString(VARNAME_MUSEUM_STATUS);
        obj_id masterObjectId = dctScriptVars.getObjId("objMasterObjectId");
        setObjVar(masterObjectId, "bestine.museumEventWinner", strMuseumEventWinner);
        setObjVar(masterObjectId, "bestine.museumEventWinnerArtworkIndex", intMuseumEventWinnerArtworkIndex);
        if (eventStatus.equals("museumEventEnded"))
        {
            if (strMuseumEventWinner != null && !strMuseumEventWinner.equals("none"))
            {
                Vector previousArtworkIndexes = new Vector();
                if (hasObjVar(masterObjectId, "bestine.previousArtworkIndexes"))
                {
                    previousArtworkIndexes = utils.getResizeableIntBatchObjVar(masterObjectId, "bestine.previousArtworkIndexes");
                }
                String[] strArtists = dataTableGetStringColumnNoDefaults(DATATABLE_NAME, "artistNpc");
                int artistRow = utils.getElementPositionInArray(strArtists, strMuseumEventWinner);
                int artistIndex = (artistRow * 100) + intMuseumEventWinnerArtworkIndex;
                previousArtworkIndexes = utils.addElement(previousArtworkIndexes, artistIndex);
                utils.setResizeableBatchObjVar(masterObjectId, "bestine.previousArtworkIndexes", previousArtworkIndexes);
            }
        }
        if (utils.hasScriptVar(self, "objFeaturedArtwork"))
        {
            obj_id objOldArtwork = dctScriptVars.getObjId("objFeaturedArtwork");
            if (isIdValid(objOldArtwork))
            {
                destroyObject(objOldArtwork);
            }
        }
        if (strMuseumEventWinner != null && !strMuseumEventWinner.equals("none"))
        {
            dictionary artistData = dataTableGetRow(DATATABLE_NAME, strMuseumEventWinner);
            String strArtworkColumn = "artwork_" + intMuseumEventWinnerArtworkIndex;
            String strArtworkTemplate = artistData.getString(strArtworkColumn);
            dctScriptVars.put("strFeaturedArtworkTemplate", strArtworkTemplate);
            String locXColumn = "locX_" + intMuseumEventWinnerArtworkIndex;
            String locYColumn = "locY_" + intMuseumEventWinnerArtworkIndex;
            String locZColumn = "locZ_" + intMuseumEventWinnerArtworkIndex;
            float locX = artistData.getFloat(locXColumn);
            float locY = artistData.getFloat(locYColumn);
            float locZ = artistData.getFloat(locZColumn);
            obj_id objMuseumLobby = getCellId(self, "lobby");
            location locArtworkLocation = new location(locX, locY, locZ, "tatooine", objMuseumLobby);
            obj_id objNewArtwork = createObject(strArtworkTemplate, locArtworkLocation);
            setYaw(objNewArtwork, -91);
            dctScriptVars.put("objFeaturedArtwork", objNewArtwork);
        }
        updateMuseumEventStatus(self);
        dictionary dctParams = new dictionary();
        dctParams.put(VARNAME_MUSEUM_STATUS, eventStatus);
        dctParams.put(VARNAME_MUSEUM_WINNER, strMuseumEventWinner);
        dctParams.put(VARNAME_MUSEUM_NUM, dctScriptVars.getInt(VARNAME_MUSEUM_NUM));
        if (eventStatus.equals("museumEventStarted"))
        {
            dctParams.put("strMuseumEventEntry01", dctScriptVars.getString("strMuseumEventEntry01"));
            dctParams.put("strMuseumEventEntry02", dctScriptVars.getString("strMuseumEventEntry02"));
            dctParams.put("strMuseumEventEntry03", dctScriptVars.getString("strMuseumEventEntry03"));
        }
        broadcastMessage("handleMuseumEventStatusResponse", dctParams);
        return SCRIPT_CONTINUE;
    }
    public int handleCuratorSetupRequest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objRequester = params.getObjId("objRequester");
        deltadictionary dctScriptVars = self.getScriptVars();
        dctScriptVars.put("objBestineCurator", objRequester);
        dictionary dctParams = new dictionary();
        String eventStatus = dctScriptVars.getString(VARNAME_MUSEUM_STATUS);
        dctParams.put(VARNAME_MUSEUM_STATUS, eventStatus);
        dctParams.put(VARNAME_MUSEUM_WINNER, dctScriptVars.getString(VARNAME_MUSEUM_WINNER));
        dctParams.put(VARNAME_MUSEUM_NUM, dctScriptVars.getInt(VARNAME_MUSEUM_NUM));
        dctParams.put(VARNAME_MUSEUM_WINNER_ARTWORK_INDEX, dctScriptVars.getInt(VARNAME_MUSEUM_WINNER_ARTWORK_INDEX));
        if (eventStatus.equals("museumEventStarted"))
        {
            dctParams.put("intVotesForEntry01", dctScriptVars.getInt("intVotesForEntry01"));
            dctParams.put("intVotesForEntry02", dctScriptVars.getInt("intVotesForEntry02"));
            dctParams.put("intVotesForEntry03", dctScriptVars.getInt("intVotesForEntry03"));
            dctParams.put("strMuseumEventEntry01", dctScriptVars.getString("strMuseumEventEntry01"));
            dctParams.put("strMuseumEventEntry02", dctScriptVars.getString("strMuseumEventEntry02"));
            dctParams.put("strMuseumEventEntry03", dctScriptVars.getString("strMuseumEventEntry03"));
            dctParams.put("intMuseumEventIndex01", dctScriptVars.getInt("intMuseumEventIndex01"));
            dctParams.put("intMuseumEventIndex02", dctScriptVars.getInt("intMuseumEventIndex02"));
            dctParams.put("intMuseumEventIndex03", dctScriptVars.getInt("intMuseumEventIndex03"));
        }
        if (utils.hasScriptVar(self, "timeNextEventStarts"))
        {
            dctParams.put("timeNextEventStarts", dctScriptVars.getInt("timeNextEventStarts"));
        }
        messageTo(objRequester, "handleCuratorSetupResponse", dctParams, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int processCuratorDataStorageRequest(obj_id self, dictionary params) throws InterruptedException
    {
        int intVotesForEntry01 = params.getInt("intVotesForEntry01");
        int intVotesForEntry02 = params.getInt("intVotesForEntry02");
        int intVotesForEntry03 = params.getInt("intVotesForEntry03");
        deltadictionary dctScriptVars = self.getScriptVars();
        obj_id masterObjectId = dctScriptVars.getObjId("objMasterObjectId");
        setObjVar(masterObjectId, "bestine.votesForEntry01", intVotesForEntry01);
        setObjVar(masterObjectId, "bestine.votesForEntry02", intVotesForEntry02);
        setObjVar(masterObjectId, "bestine.votesForEntry03", intVotesForEntry03);
        return SCRIPT_CONTINUE;
    }
    public int handleMuseumEventStatusRequest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objRequester = params.getObjId("objRequester");
        updateMuseumEventStatus(self);
        deltadictionary dctScriptVars = self.getScriptVars();
        dictionary dctParams = new dictionary();
        String eventStatus = dctScriptVars.getString(VARNAME_MUSEUM_STATUS);
        dctParams.put(VARNAME_MUSEUM_STATUS, eventStatus);
        dctParams.put(VARNAME_MUSEUM_WINNER, dctScriptVars.getString(VARNAME_MUSEUM_WINNER));
        dctParams.put(VARNAME_MUSEUM_NUM, dctScriptVars.getInt(VARNAME_MUSEUM_NUM));
        if (eventStatus.equals("museumEventStarted"))
        {
            dctParams.put("strMuseumEventEntry01", dctScriptVars.getString("strMuseumEventEntry01"));
            dctParams.put("strMuseumEventEntry02", dctScriptVars.getString("strMuseumEventEntry02"));
            dctParams.put("strMuseumEventEntry03", dctScriptVars.getString("strMuseumEventEntry03"));
        }
        messageTo(objRequester, "handleMuseumEventStatusResponse", dctParams, 1, false);
        return SCRIPT_CONTINUE;
    }
}
