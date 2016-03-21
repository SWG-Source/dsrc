package script.city.bestine;

import script.*;
import script.library.ai_lib;
import script.library.planetary_map;

public class museum_event_npc extends script.base_script
{
    public museum_event_npc()
    {
    }
    public static final String FACE_TO_VOLUME_NAME = "faceToTriggerVolume";

    public static final String VARNAME_MUSEUM_STATUS = "strMuseumEventStatus";
    public static final String VARNAME_MUSEUM_WINNER = "strMuseumEventWinner";
    public static final String VARNAME_MUSEUM_NUM = "intMuseumEventNum";
    public static final String VARNAME_MUSEUM_WINNER_ARTWORK_INDEX = "intMuseumWinnerArtworkIndex";
    public static final String OBJVAR_MUSEUM_BUILDING = "bestine.objMuseumBuilding";
    public static final String DATATABLE_NAME = "datatables/city/bestine/bestine_museum_event.iff";

    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume(FACE_TO_VOLUME_NAME, 8.0f, true);

        String creatureName = ai_lib.getCreatureName(self);
        if (creatureName.equals("lilas_dinhint"))
        {
            messageTo(self, "handleCuratorSetup", null, 9, false);
            return SCRIPT_CONTINUE;
        }

        messageTo(self, "handleMuseumEventNpcSetup", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
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
        return SCRIPT_CONTINUE;
    }
    public int handleCuratorSetup(obj_id self, dictionary params) throws InterruptedException
    {
        if (!ai_lib.getCreatureName(self).equals("lilas_dinhint"))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary msgData = new dictionary();
        msgData.put("objRequester", self);
        obj_id museumBuilding = getObjIdObjVar(self, OBJVAR_MUSEUM_BUILDING);
        messageTo(museumBuilding, "handleCuratorSetupRequest", msgData, 9, false);
        return SCRIPT_CONTINUE;
    }
    public int handleCuratorSetupResponse(obj_id self, dictionary params) throws InterruptedException
    {
        clearMuseumEventObjvars(self);
        String museumEventStatus = params.getString(VARNAME_MUSEUM_STATUS);
        String museumEventStatusObjvar = "bestine." + museumEventStatus;
        String museumEventWinner = params.getString(VARNAME_MUSEUM_WINNER);
        int museumEventWinnerArtworkIndex = params.getInt(VARNAME_MUSEUM_WINNER_ARTWORK_INDEX);
        setObjVar(self, museumEventStatusObjvar, params.getInt(VARNAME_MUSEUM_NUM));
        setObjVar(self, "bestine.museumEventWinner", museumEventWinner);
        setObjVar(self, "bestine.museumEventWinnerArtworkIndex", museumEventWinnerArtworkIndex);
        if (museumEventStatus.equals("museumEventStarted"))
        {
            setObjVar(self, "bestine.votesForEntry01", params.getInt("intVotesForEntry01"));
            setObjVar(self, "bestine.votesForEntry02", params.getInt("intVotesForEntry02"));
            setObjVar(self, "bestine.votesForEntry03", params.getInt("intVotesForEntry03"));
            setObjVar(self, "bestine.museumEventEntry01", params.getString("strMuseumEventEntry01"));
            setObjVar(self, "bestine.museumEventEntry02", params.getString("strMuseumEventEntry02"));
            setObjVar(self, "bestine.museumEventEntry03", params.getString("strMuseumEventEntry03"));
            setObjVar(self, "bestine.museumEventIndex01", params.getInt("intMuseumEventIndex01"));
            setObjVar(self, "bestine.museumEventIndex02", params.getInt("intMuseumEventIndex02"));
            setObjVar(self, "bestine.museumEventIndex03", params.getInt("intMuseumEventIndex03"));
        }
        if (museumEventWinner != null && !museumEventWinner.equals("none"))
        {
            dictionary artistData = dataTableGetRow(DATATABLE_NAME, museumEventWinner);
            String artworkColumn = "artwork_" + museumEventWinnerArtworkIndex;
            String schematicColumn = "schematic_" + museumEventWinnerArtworkIndex;
            String artworkTemplate = artistData.getString(artworkColumn);
            String schematicTemplate = artistData.getString(schematicColumn);
            setObjVar(self, "bestine.museumEventFeaturedArtwork", artworkTemplate);
            setObjVar(self, "bestine.museumEventFeaturedSchematic", schematicTemplate);
        }
        int timeNextEventStarts = params.getInt("timeNextEventStarts");
        if (timeNextEventStarts > 0)
        {
            setObjVar(self, "bestine.timeNextEventStarts", timeNextEventStarts);
        }
        messageTo(self, "handleCuratorDataStorage", null, 600, false);
        return SCRIPT_CONTINUE;
    }
    public int handleCuratorDataStorage(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "bestine.museumEventStarted"))
        {
            int votesForEntry01 = 0;
            int votesForEntry02 = 0;
            int votesForEntry03 = 0;
            if (hasObjVar(self, "bestine.votesForEntry01"))
            {
                votesForEntry01 = getIntObjVar(self, "bestine.votesForEntry01");
            }
            if (hasObjVar(self, "bestine.votesForEntry02"))
            {
                votesForEntry01 = getIntObjVar(self, "bestine.votesForEntry02");
            }
            if (hasObjVar(self, "bestine.votesForEntry03"))
            {
                votesForEntry03 = getIntObjVar(self, "bestine.votesForEntry03");
            }
            dictionary msgData = new dictionary();
            msgData.put("objRequester", self);
            msgData.put("intVotesForEntry01", votesForEntry01);
            msgData.put("intVotesForEntry02", votesForEntry02);
            msgData.put("intVotesForEntry03", votesForEntry03);
            obj_id museumBuilding = getObjIdObjVar(self, OBJVAR_MUSEUM_BUILDING);
            messageTo(museumBuilding, "processCuratorDataStorageRequest", msgData, 1, false);
            messageTo(self, "handleCuratorDataStorage", null, 600, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMuseumEventStateChangeCuratorAlert(obj_id self, dictionary params) throws InterruptedException
    {
        clearMuseumEventObjvars(self);
        String museumEventStatus = params.getString(VARNAME_MUSEUM_STATUS);
        String museumEventWinner = params.getString(VARNAME_MUSEUM_WINNER);
        int museumEventNum = params.getInt(VARNAME_MUSEUM_NUM);
        int museumEventWinnerArtworkIndex = params.getInt(VARNAME_MUSEUM_WINNER_ARTWORK_INDEX);
        int timeNextEventStarts = params.getInt("timeNextEventStarts");
        if (timeNextEventStarts > 0)
        {
            setObjVar(self, "bestine.timeNextEventStarts", timeNextEventStarts);
        }
        else 
        {
            if (hasObjVar(self, "bestine.timeNextEventStarts"))
            {
                removeObjVar(self, "bestine.timeNextEventStarts");
            }
        }
        dictionary msgData = new dictionary();
        if (museumEventStatus.equals("museumEventEnded"))
        {
            int votesForEntry01 = getIntObjVar(self, "bestine.votesForEntry01");
            int votesForEntry02 = getIntObjVar(self, "bestine.votesForEntry02");
            int votesForEntry03 = getIntObjVar(self, "bestine.votesForEntry03");
            String entry01 = getStringObjVar(self, "bestine.museumEventEntry01");
            String entry02 = getStringObjVar(self, "bestine.museumEventEntry02");
            String entry03 = getStringObjVar(self, "bestine.museumEventEntry03");
            int index01 = getIntObjVar(self, "bestine.museumEventIndex01");
            int index02 = getIntObjVar(self, "bestine.museumEventIndex02");
            int index03 = getIntObjVar(self, "bestine.museumEventIndex03");
            if (votesForEntry01 > votesForEntry02 && votesForEntry01 > votesForEntry03)
            {
                museumEventWinner = entry01;
                museumEventWinnerArtworkIndex = index01;
            }
            else if (votesForEntry02 > votesForEntry01 && votesForEntry02 > votesForEntry03)
            {
                museumEventWinner = entry02;
                museumEventWinnerArtworkIndex = index02;
            }
            else if (votesForEntry03 > votesForEntry01 && votesForEntry03 > votesForEntry02)
            {
                museumEventWinner = entry03;
                museumEventWinnerArtworkIndex = index03;
            }
            else if (votesForEntry01 == 0 && votesForEntry02 == 0 && votesForEntry03 == 0)
            {
                museumEventWinner = "none";
                museumEventWinnerArtworkIndex = 0;
            }
            else if (votesForEntry01 == votesForEntry02 && votesForEntry02 == votesForEntry03)
            {
                switch (rand(1, 3))
                {
                    case 1:
                        museumEventWinner = entry01;
                        museumEventWinnerArtworkIndex = index01;
                        break;
                    case 2:
                        museumEventWinner = entry02;
                        museumEventWinnerArtworkIndex = index02;
                        break;
                    case 3:
                        museumEventWinner = entry03;
                        museumEventWinnerArtworkIndex = index03;
                        break;
                }
            }
            else if (votesForEntry01 == votesForEntry02)
            {
                switch (rand(1, 2))
                {
                    case 1:
                        museumEventWinner = entry01;
                        museumEventWinnerArtworkIndex = index01;
                        break;
                    case 2:
                        museumEventWinner = entry02;
                        museumEventWinnerArtworkIndex = index02;
                        break;
                }
            }
            else if (votesForEntry01 == votesForEntry03)
            {
                switch (rand(1, 2))
                {
                    case 1:
                        museumEventWinner = entry01;
                        museumEventWinnerArtworkIndex = index01;
                        break;
                    case 2:
                        museumEventWinner = entry03;
                        museumEventWinnerArtworkIndex = index03;
                        break;
                }
            }
            else if (votesForEntry02 == votesForEntry03)
            {
                switch (rand(1, 2))
                {
                    case 1:
                        museumEventWinner = entry02;
                        museumEventWinnerArtworkIndex = index02;
                        break;
                    case 2:
                        museumEventWinner = entry03;
                        museumEventWinnerArtworkIndex = index03;
                        break;
                }
            }
            else 
            {
                museumEventWinner = "none";
                museumEventWinnerArtworkIndex = 0;
            }
            if (museumEventWinner == null)
            {
                museumEventWinner = "none";
            }
            if (!museumEventWinner.equals("none"))
            {
                dictionary artistData = dataTableGetRow(DATATABLE_NAME, museumEventWinner);
                String artworkColumn = "artwork_" + museumEventWinnerArtworkIndex;
                String schematicColumn = "schematic_" + museumEventWinnerArtworkIndex;
                String artworkTemplate = artistData.getString(artworkColumn);
                String schematicTemplate = artistData.getString(schematicColumn);
                setObjVar(self, "bestine.museumEventFeaturedArtwork", artworkTemplate);
                setObjVar(self, "bestine.museumEventFeaturedSchematic", schematicTemplate);
            }
            else 
            {
                removeObjVar(self, "bestine.museumEventFeaturedArtwork");
                removeObjVar(self, "bestine.museumEventFeaturedSchematic");
            }
            for (int i = 1; i <= 3; i++)
            {
                String votesObjVar = "bestine.votesForEntry0" + i;
                String entryObjVar = "bestine.museumEventEntry0" + i;
                String indexObjVar = "bestine.museumEventIndex0" + i;
                if (hasObjVar(self, votesObjVar))
                {
                    removeObjVar(self, votesObjVar);
                }
                if (hasObjVar(self, entryObjVar))
                {
                    removeObjVar(self, entryObjVar);
                }
                if (hasObjVar(self, indexObjVar))
                {
                    removeObjVar(self, indexObjVar);
                }
            }
        }
        if (museumEventStatus.equals("museumEventStarted"))
        {
            messageTo(self, "handleCuratorSetup", null, 10, false);
        }
        setObjVar(self, "bestine." + museumEventStatus, museumEventNum);
        setObjVar(self, "bestine.museumEventWinner", museumEventWinner);
        setObjVar(self, "bestine.museumEventWinnerArtworkIndex", museumEventWinnerArtworkIndex);

        msgData.put(VARNAME_MUSEUM_WINNER, museumEventWinner);
        msgData.put(VARNAME_MUSEUM_WINNER_ARTWORK_INDEX, museumEventWinnerArtworkIndex);

        messageTo(getObjIdObjVar(self, OBJVAR_MUSEUM_BUILDING), "handleSetMuseumEventWinner", msgData, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int handleMuseumEventNpcSetup(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, OBJVAR_MUSEUM_BUILDING))
        {
            dictionary msgData = new dictionary();
            msgData.put("objRequester", self);
            obj_id museumBuilding = getObjIdObjVar(self, OBJVAR_MUSEUM_BUILDING);

            listenToMessage(museumBuilding, "handleMuseumEventStatusResponse");
            messageTo(museumBuilding, "handleMuseumEventStatusRequest", msgData, 10, false);
        }
        else 
        {
            messageTo(self, "checkForMuseumBuilding", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int checkForMuseumBuilding(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        map_location registeredMuseum = planetary_map.findClosestLocation(self, "museum", "");
        if (registeredMuseum != null)
        {
            obj_id objMuseumBuilding = registeredMuseum.getLocationId();
            deltadictionary dctScriptVars = self.getScriptVars();
            int numMuseumChecks = dctScriptVars.getInt("numMuseumChecks");
            if (!isIdValid(objMuseumBuilding))
            {
                if (numMuseumChecks < 10)
                {
                    numMuseumChecks = numMuseumChecks + 1;
                    dctScriptVars.put("numMuseumChecks", numMuseumChecks);
                    messageTo(self, "checkForMuseumBuilding", null, 60, false);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    LOG("DESIGNER_FATAL", "city.bestine.museum_event_spawner: Could not find obj_id for a registered museum from here:" + here);
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                setObjVar(self, OBJVAR_MUSEUM_BUILDING, objMuseumBuilding);
                dctScriptVars.put("objMuseumBuilding", objMuseumBuilding);
            }
        }
        else 
        {
            LOG("DESIGNER_FATAL", "city.bestine.museum_event_spawner: Could not find a map_location for a registered museum from here: " + here);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMuseumEventStatusResponse(obj_id self, dictionary params) throws InterruptedException
    {
        clearMuseumEventObjvars(self);
        String museumEventStatus = params.getString(VARNAME_MUSEUM_STATUS);
        setObjVar(self, "bestine." + museumEventStatus, params.getInt(VARNAME_MUSEUM_NUM));
        setObjVar(self, "bestine.museumEventWinner", params.getString(VARNAME_MUSEUM_WINNER));
        if (museumEventStatus.equals("museumEventStarted"))
        {
            setObjVar(self, "bestine.museumEventEntry01", params.getString("strMuseumEventEntry01"));
            setObjVar(self, "bestine.museumEventEntry02", params.getString("strMuseumEventEntry02"));
            setObjVar(self, "bestine.museumEventEntry03", params.getString("strMuseumEventEntry03"));
        }
        else 
        {
            if (hasObjVar(self, "bestine.museumEventEntry01"))
            {
                removeObjVar(self, "bestine.museumEventEntry01");
            }
            if (hasObjVar(self, "bestine.museumEventEntry02"))
            {
                removeObjVar(self, "bestine.museumEventEntry02");
            }
            if (hasObjVar(self, "bestine.museumEventEntry03"))
            {
                removeObjVar(self, "bestine.museumEventEntry03");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void clearMuseumEventObjvars(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "bestine.museumEventStarted"))
        {
            removeObjVar(self, "bestine.museumEventStarted");
        }
        if (hasObjVar(self, "bestine.museumEventEnded"))
        {
            removeObjVar(self, "bestine.museumEventEnded");
        }
        if (hasObjVar(self, "bestine.museumEventWinner"))
        {
            removeObjVar(self, "bestine.museumEventWinner");
        }
        if (hasObjVar(self, "bestine.museumEventWinnerArtworkIndex"))
        {
            removeObjVar(self, "bestine.museumEventWinnerArtworkIndex");
        }
    }
}
