package script.space.content_tools;

import script.dictionary;
import script.library.space_quest;
import script.library.utils;
import script.obj_id;
import script.string_id;

import java.util.Vector;

public class spacestation extends script.base_script
{
    public spacestation()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        requestPreloadCompleteTrigger(self);
        setObjVar(self, "intInvincible", 1);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        requestPreloadCompleteTrigger(self);
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        messageTo(self, "registerStation", null, 2, false);
        String strName = getStringObjVar(self, "strName");
        if (strName != null)
        {
            string_id strSpam = new string_id("space/space_mobile_type", strName);
            setName(self, strSpam);
        }
        return SCRIPT_CONTINUE;
    }
    public int registerStation(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("space", "Registering space station");
        obj_id objQuestManager = getNamedObject(space_quest.QUEST_MANAGER);
        if (!isIdValid(objQuestManager))
        {
            LOG("space", "NO QUEST MANAGER OBJECT FOUND!");
            return SCRIPT_CONTINUE;
        }
        registerStationWithManager(objQuestManager, self);
        return SCRIPT_CONTINUE;
    }
    public void registerStationWithManager(obj_id objManager, obj_id objStation) throws InterruptedException
    {
        LOG("space", "Registering with " + objManager);
        Vector objSpaceStations = utils.getResizeableObjIdArrayScriptVar(objManager, "objSpaceStations");
        if ((objSpaceStations == null) || (objSpaceStations.size() == 0))
        {
            objSpaceStations = utils.addElement(objSpaceStations, objStation);
        }
        else 
        {
            int intIndex = utils.getElementPositionInArray(objSpaceStations, objStation);
            if (intIndex < 0)
            {
                objSpaceStations = utils.addElement(objSpaceStations, objStation);
            }
        }
        utils.setScriptVar(objManager, "objSpaceStations", objSpaceStations);
    }
}
