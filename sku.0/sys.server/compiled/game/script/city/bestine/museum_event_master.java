package script.city.bestine;

import script.dictionary;
import script.library.utils;
import script.obj_id;

import java.util.Vector;

public class museum_event_master extends script.base_script
{
    public museum_event_master()
    {
    }
    public static final String DATATABLE_NAME = "datatables/city/bestine/bestine_museum_event.iff";

    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "beginBestineMuseumEvent", null, 1, false);
        return SCRIPT_CONTINUE;
    }

    public int getMuseumEventDuration() throws InterruptedException
    {
        String durationConfigSetting = getConfigSetting("BestineEvents", "MuseumEventDuration");
        int museumEventDuration = utils.stringToInt(durationConfigSetting);
        if (museumEventDuration == 0 || museumEventDuration == -1)
        {
            museumEventDuration = 1209600;
        }
        return museumEventDuration;
    }
    public int beginBestineMuseumEvent(obj_id self, dictionary params) throws InterruptedException
    {
        int museumEventNum = 1;
        if (hasObjVar(self, "bestine.museumEventEnded"))
        {
            museumEventNum = getIntObjVar(self, "bestine.museumEventEnded") + 1;
            removeObjVar(self, "bestine.museumEventEnded");
        }
        if (!hasObjVar(self, "bestine.museumEventWinner"))
        {
            setObjVar(self, "bestine.museumEventWinner", "none");
        }
        if (hasObjVar(self, "bestine.timeNextEventStarts"))
        {
            removeObjVar(self, "bestine.timeNextEventStarts");
        }
        setObjVar(self, "bestine.museumEventStarted", museumEventNum);
        setObjVar(self, "bestine.votesForEntry01", 0);
        setObjVar(self, "bestine.votesForEntry02", 0);
        setObjVar(self, "bestine.votesForEntry03", 0);

        pickArtistsForEvent(self);

        if (!hasObjVar(self, "bestine.museumEventEntry01") || !hasObjVar(self, "bestine.museumEventEntry02") || !hasObjVar(self, "bestine.museumEventEntry03"))
        {
            removeObjVar(self, "bestine.previousArtworkIndexes");
            pickArtistsForEvent(self);
        }

        obj_id museumBuildingObjId = getObjIdObjVar(self, "bestine.objMuseumBuilding");
        messageTo(museumBuildingObjId, "handleMuseumEventStateChange", null, 1, false);

        int museumEventDuration = getMuseumEventDuration();
        messageTo(self, "endBestineMuseumEvent", null, museumEventDuration, true);

        return SCRIPT_CONTINUE;
    }
    public int endBestineMuseumEvent(obj_id self, dictionary params) throws InterruptedException
    {
        int museumEventNum = 1;
        if (hasObjVar(self, "bestine.museumEventStarted"))
        {
            museumEventNum = getIntObjVar(self, "bestine.museumEventStarted");
            removeObjVar(self, "bestine.museumEventStarted");
        }
        setObjVar(self, "bestine.museumEventEnded", museumEventNum);
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
        obj_id museumBuildingObjId = getObjIdObjVar(self, "bestine.objMuseumBuilding");
        messageTo(museumBuildingObjId, "handleMuseumEventStateChange", null, 1, false);

        int museumEventDuration = getMuseumEventDuration();
        int timeNextEventStarts = getGameTime() + museumEventDuration;
        setObjVar(self, "bestine.timeNextEventStarts", timeNextEventStarts);
        messageTo(self, "beginBestineMuseumEvent", null, museumEventDuration, true);

        return SCRIPT_CONTINUE;
    }
    public void pickArtistsForEvent(obj_id self) throws InterruptedException
    {
        String[] strArtists = dataTableGetStringColumnNoDefaults(DATATABLE_NAME, "artistNpc");
        Vector vectArtists = new Vector();
        for (String temp : strArtists) {
            vectArtists = utils.addElement(vectArtists, temp);
        }

        Vector previousArtworkIndexes = new Vector();

        if (hasObjVar(self, "bestine.previousArtworkIndexes"))
        {
            previousArtworkIndexes = utils.getResizeableIntBatchObjVar(self, "bestine.previousArtworkIndexes");
        }

        int entryChosen = 0;
        String artistName;

        while ((entryChosen < 3) && (vectArtists.size() > 0))
        {
            int vectorSize = vectArtists.size();
            int vectRow = rand(0, (vectorSize - 1));
            artistName = ((String) vectArtists.get(vectRow));
            int artistRow = utils.getElementPositionInArray(strArtists, artistName);
            int numArtworksByArtist = dataTableGetInt(DATATABLE_NAME, artistRow, "numWorksOfArt");
            int artworkNum = 1;
            boolean foundArtworkEntry = false;
            while ((artworkNum <= numArtworksByArtist) && (!foundArtworkEntry))
            {
                int artistIndex = (artistRow * 100) + artworkNum;
                if (previousArtworkIndexes.size() < 1)
                {
                    foundArtworkEntry = true;
                }
                else if ((utils.getElementPositionInArray(previousArtworkIndexes, artistIndex)) == -1)
                {
                    foundArtworkEntry = true;
                }
                else 
                {
                    artworkNum = artworkNum + 1;
                }
            }
            if (foundArtworkEntry)
            {
                entryChosen = entryChosen + 1;
                String entryObjVar = "bestine.museumEventEntry0" + entryChosen;
                String indexObjVar = "bestine.museumEventIndex0" + entryChosen;
                setObjVar(self, entryObjVar, artistName);
                setObjVar(self, indexObjVar, artworkNum);
            }
            vectArtists = utils.removeElementAt(vectArtists, vectRow);
        }
    }
}
