package script.event;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.library.sui;
import script.library.utils;
import script.location;
import script.obj_id;

public class honor_guard extends script.base_script
{
    public honor_guard()
    {
    }
    public static final String[] PROMPT_TEXT = 
    {
        "Enter \"setup\" to setup data, \"singleSetup\" to setup single spawn data, \"spawn\" to spawn NPCs using current data, \"data\" to view current data or \"quit\" end.",
        "Enter the template name for the NPC to spawn (e.g. stormtrooper).",
        "How many NPCs per line (shoulder to shoulder)?",
        "How many rows of NPCs?",
        "How far apart are the NPCs going to be spaced in meters?",
        "Enter an expiration time in seconds.",
        "Current data shows that more then 50 NPCs are set to be spawned. If this is intended type \"ok\" to ignore this warning and continue. Otherwise type \"setup\" to make changes."
    };
    public static final String[] TITLE = 
    {
        "Standby",
        "Template Name",
        "Number per Line",
        "Number per Row",
        "Spacing",
        "Expiration Time",
        "WARNING"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self))
        {
            detachScript(self, "event.honor_guard");
            removeObjVar(self, "event.honor_guard");
            sendSystemMessage(self, "You must be in God Mode to use this script.", null);
            return SCRIPT_CONTINUE;
        }
        String template = "stormtrooper";
        int numPerLine = 5;
        int numRows = 2;
        float offset = 1.0f;
        int setupStep = 0;
        int overRide = 0;
        setObjVar(self, "event.honor_guard.template", template);
        setObjVar(self, "event.honor_guard.numPerLine", numPerLine);
        setObjVar(self, "event.honor_guard.numRows", numRows);
        setObjVar(self, "event.honor_guard.offset", offset);
        setObjVar(self, "event.honor_guard.setupStep", setupStep);
        setObjVar(self, "event.honor_guard.overRide", overRide);
        setObjVar(self, "event.honor_guard.single", 1);
        showUI(self, self);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (objSpeaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (strText.equals("help"))
        {
            sendSystemMessage(self, "If you lost the UI window, say \"showUI\" to get it back.", null);
        }
        if (strText.equals("showUI"))
        {
            messageTo(self, "continueCollectingData", null, 2, false);
        }
        return SCRIPT_CONTINUE;
    }
    private int showUI(obj_id self, obj_id player) throws InterruptedException
    {
        int current = getIntObjVar(self, "event.honor_guard.setupStep");
        return sui.inputbox(self, self, PROMPT_TEXT[current], TITLE[current], "handleUIdata", 255, false, "");
    }
    public int handleUIdata(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        String text = sui.getInputBoxText(params);
        if (bp == sui.BP_CANCEL || text == null || text.equals(""))
        {
            sendSystemMessage(self, "Enter \"setup\" to setup data, \"spawn\" to spawn NPCs using current data, \"data\" to view current data or \"quit\" end.", null);
            messageTo(self, "continueCollectingData", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "event.honor_guard.lastData", text);
        messageTo(self, "storeLastDataObjVar", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int storeLastDataObjVar(obj_id self, dictionary params) throws InterruptedException
    {
        String lastDataStr = getStringObjVar(self, "event.honor_guard.lastData");
        switch (lastDataStr) {
            case "setup":
                setObjVar(self, "event.honor_guard.setupStep", 1);
                setObjVar(self, "event.honor_guard.single", 0);
                messageTo(self, "continueCollectingData", null, 1, false);
                return SCRIPT_CONTINUE;
            case "spawn":
                messageTo(self, "spawnHonorGuard", null, 0, false);
                messageTo(self, "continueCollectingData", null, 1, false);
                return SCRIPT_CONTINUE;
            case "data":
                String template = getStringObjVar(self, "event.honor_guard.template");
                int numPerLine = getIntObjVar(self, "event.honor_guard.numPerLine");
                int numRows = getIntObjVar(self, "event.honor_guard.numRows");
                float offset = getFloatObjVar(self, "event.honor_guard.offset");
                sendSystemMessage(self, "Current Template: " + template + ". Number per line: " + numPerLine + " . Number of Rows: " + numRows + " . Spacing: " + offset, null);
                messageTo(self, "continueCollectingData", null, 1, false);
                return SCRIPT_CONTINUE;
            case "quit":
                removeObjVar(self, "event.honor_guard");
                detachScript(self, "event.honor_guard");
                sendSystemMessage(self, "Many thank you for your playing.", null);
                return SCRIPT_CONTINUE;
            case "singleSetup":
                setObjVar(self, "event.honor_guard.single", 1);
                setObjVar(self, "event.honor_guard.setupStep", 1);
                setObjVar(self, "event.honor_guard.numPerLine", 1);
                setObjVar(self, "event.honor_guard.numRows", 1);
                setObjVar(self, "event.honor_guard.offset", 1.0f);
                messageTo(self, "continueCollectingData", null, 1, false);
                return SCRIPT_CONTINUE;
        }
        int setupStep = getIntObjVar(self, "event.honor_guard.setupStep");
        switch (setupStep) {
            case 0:
                sendSystemMessage(self, "Type \"quit\" to terminate the script, \"setup\" to start over, \"data\" to see current data or \"spawn\" to spawn the NPCs.", null);
                messageTo(self, "continueCollectingData", null, 1, false);
                return SCRIPT_CONTINUE;
            case 1:
                setObjVar(self, "event.honor_guard.template", lastDataStr);
                if (getIntObjVar(self, "event.honor_guard.single") == 0) {
                    setObjVar(self, "event.honor_guard.setupStep", 2);
                } else {
                    setObjVar(self, "event.honor_guard.setupStep", 5);
                }
                sendSystemMessage(self, "Template set as: " + lastDataStr, null);
                break;
            case 2: {
                int lastDataInt = utils.stringToInt(lastDataStr);
                if (lastDataInt <= 0) {
                    sendSystemMessage(self, "You must enter a whole number greater then 0.", null);
                    messageTo(self, "continueCollectingData", null, 1, false);
                    return SCRIPT_CONTINUE;
                }
                setObjVar(self, "event.honor_guard.setupStep", 3);
                setObjVar(self, "event.honor_guard.numPerLine", lastDataInt);
                sendSystemMessage(self, "Number of spawns per line set to: " + lastDataInt, null);
                break;
            }
            case 3: {
                int lastDataInt = utils.stringToInt(lastDataStr);
                if (lastDataInt <= 0) {
                    sendSystemMessage(self, "You must enter a whole number greater then 0.", null);
                    messageTo(self, "continueCollectingData", null, 1, false);
                    return SCRIPT_CONTINUE;
                }
                setObjVar(self, "event.honor_guard.setupStep", 4);
                setObjVar(self, "event.honor_guard.numRows", lastDataInt);
                sendSystemMessage(self, "Number of rows set to: " + lastDataInt, null);
                break;
            }
            case 4: {
                float lastDataFloat = utils.stringToFloat(lastDataStr);
                if (lastDataFloat <= 0) {
                    sendSystemMessage(self, "You must enter a value greater then 0.", null);
                    messageTo(self, "continueCollectingData", null, 1, false);
                    return SCRIPT_CONTINUE;
                }
                setObjVar(self, "event.honor_guard.setupStep", 5);
                setObjVar(self, "event.honor_guard.offset", lastDataFloat);
                sendSystemMessage(self, "Spacing between NPCs set to: " + lastDataFloat, null);
                break;
            }
            case 5: {
                float lastDataFloat = utils.stringToFloat(lastDataStr);
                if (lastDataFloat <= 0 || lastDataFloat > 864001) {
                    sendSystemMessage(self, "You must enter a value more then 0 and at most 864000 (10 days).", null);
                    messageTo(self, "continueCollectingData", null, 1, false);
                    return SCRIPT_CONTINUE;
                }
                setObjVar(self, "event.honor_guard.setupStep", 0);
                setObjVar(self, "event.honor_guard.expirationTime", lastDataFloat);
                sendSystemMessage(self, "Expiration time set to: " + lastDataFloat + " seconds.", null);
                break;
            }
            case 6:
                if (lastDataStr.equals("ok")) {
                    setObjVar(self, "event.honor_guard.overRide", 1);
                    setObjVar(self, "event.honor_guard.setupStep", 4);
                }
                break;
        }
        int numPerLine = getIntObjVar(self, "event.honor_guard.numPerLine");
        int numRows = getIntObjVar(self, "event.honor_guard.numRows");
        int overRide = getIntObjVar(self, "event.honor_guard.overRide");
        if (overRide == 0)
        {
            int totalSpawns = numPerLine * numRows;
            if (totalSpawns > 50)
            {
                setObjVar(self, "event.honor_guard.setupStep", 6);
            }
        }
        removeObjVar(self, "event.honor_guard.lastData");
        messageTo(self, "continueCollectingData", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int continueCollectingData(obj_id self, dictionary params) throws InterruptedException
    {
        showUI(self, self);
        return SCRIPT_CONTINUE;
    }
    public int spawnHonorGuard(obj_id self, dictionary params) throws InterruptedException
    {
        String template = getStringObjVar(self, "event.honor_guard.template");
        int numPerLine = getIntObjVar(self, "event.honor_guard.numPerLine");
        int numRows = getIntObjVar(self, "event.honor_guard.numRows");
        float offset = getFloatObjVar(self, "event.honor_guard.offset");
        float expirationTime = getFloatObjVar(self, "event.honor_guard.expirationTime");
        float timeStamp = getGameTime();
        location here = getLocation(self);
        float heading = getYaw(self);
        float xLoc = here.x;
        float yLoc = here.y;
        float zLoc = here.z;
        obj_id room = here.cell;
        obj_id honorGuard;
        for (int i = 0; i < numPerLine; i++)
        {
            double xRowOffsetDbl = Math.sin(Math.toRadians(heading + 90)) * (i * offset);
            float xRowOffset = (float)xRowOffsetDbl;
            double zRowOffsetDbl = Math.cos(Math.toRadians(heading + 90)) * (i * offset);
            float zRowOffset = (float)zRowOffsetDbl;
            for (int j = 0; j < numRows; j++)
            {
                double xSpawnDbl = Math.sin(Math.toRadians(heading)) * (j * offset) + xLoc + xRowOffset;
                double zSpawnDbl = Math.cos(Math.toRadians(heading)) * (j * offset) + zLoc + zRowOffset;
                float xSpawn = (float)xSpawnDbl;
                float zSpawn = (float)zSpawnDbl;
                location spawnPoint = new location();
                spawnPoint.x = xSpawn;
                spawnPoint.y = yLoc;
                spawnPoint.z = zSpawn;
                spawnPoint.cell = room;
                honorGuard = create.object(template, spawnPoint);
                persistObject(honorGuard);
                attachScript(honorGuard, "event.invasion.key_object");
                setObjVar(honorGuard, "event.invasion.keyObject.expirationTime", expirationTime);
                setObjVar(honorGuard, "event.invasion.keyObject.timeStamp", timeStamp);
                setYaw(honorGuard, heading);
                ai_lib.setDefaultCalmBehavior(honorGuard, ai_lib.BEHAVIOR_SENTINEL);
                ai_lib.stop(honorGuard);
                setInvulnerable(honorGuard, true);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
