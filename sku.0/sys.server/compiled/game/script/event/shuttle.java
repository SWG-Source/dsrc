package script.event;

import script.dictionary;
import script.library.create;
import script.library.sui;
import script.library.utils;
import script.location;
import script.obj_id;

public class shuttle extends script.base_script
{
    public shuttle()
    {
    }
    private static final String[] PROMPT_TEXT =
    {
        "Enter \"setup\" to setup data, \"data\" to view current data, \"quit\" to end. After setup type \"f\" to have the shuttle do an automatic fly-by. Type \"m\" to spawn and manually control the shuttle then use \"land\" to make it land, \"leave\" to make it take off.",
        "Which of the following shuttles will spawn? Type \"lambda\", \"shuttle\", \"transport\" or \"theed transport\".",
        "Travel to the location where you'd like the shuttle to land and type \"here\" in the box to set its spawn point.",
        "Would you like the shuttle to spawn NPCs upon landing? Answer \"yes\" or \"no\".",
        "Enter the spawn name of the NPC (e.g. stormtrooper, nightsister_elder)",
        "Enter the number of NPCs to spawn.",
        "Enter a phrase that you would like to use to recover the UI. When you say this exact phrase, the UI window will re-appear."
    };
    public static final String[] TITLE = 
    {
        "Standby",
        "Shuttle Type",
        "Landing Area",
        "Spawn NPCs on Landing?",
        "NPC Template",
        "Number of NPCs",
        "UI Recovery Phrase"
    };
    private static final String[] SHUTTLE_TEMPLATE =
    {
        "object/creature/npc/theme_park/lambda_shuttle.iff",
        "object/creature/npc/theme_park/player_shuttle.iff",
        "object/creature/npc/theme_park/player_transport.iff",
        "object/creature/npc/theme_park/player_transport_theed_hangar.iff"
    };
    private static final String[] SHUTTLE_NAME =
    {
        "Lambda Shuttle",
        "Shuttle",
        "Transport",
        "Transport"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self))
        {
            detachScript(self, "event.shuttle");
            removeObjVar(self, "event.shuttle");
            sendSystemMessage(self, "You must be in God Mode to use this script.", null);
            return SCRIPT_CONTINUE;
        }
        int shuttleType = 0;
        float heading = 0.0f;
        int numSpawns = 0;
        String magicWord = "hi";
        int setupStep = 0;
        String template = "";
        location spawnPoint = getLocation(self);
        int setupCompleted = 0;
        setObjVar(self, "event.shuttle.shuttleType", shuttleType);
        setObjVar(self, "event.shuttle.heading", heading);
        setObjVar(self, "event.shuttle.numSpawns", numSpawns);
        setObjVar(self, "event.shuttle.magicWord", magicWord);
        setObjVar(self, "event.shuttle.setupStep", setupStep);
        setObjVar(self, "event.shuttle.template", template);
        setObjVar(self, "event.shuttle.spawnPoint", spawnPoint);
        setObjVar(self, "event.shuttle.setupCompleted", setupCompleted);
        showUI(self, self);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        String magicWord = getStringObjVar(self, "event.shuttle.magicWord");
        if (text.equals(magicWord))
        {
            showUI(self, self);
        }
        return SCRIPT_CONTINUE;
    }
    private int showUI(obj_id self, obj_id player) throws InterruptedException
    {
        int current = getIntObjVar(self, "event.shuttle.setupStep");
        return sui.inputbox(self, self, PROMPT_TEXT[current], TITLE[current], "handleUIdata", 255, false, "");
    }
    public int handleUIdata(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            String magicWord = getStringObjVar(self, "event.shuttle.magicWord");
            int current = getIntObjVar(self, "event.shuttle.setupStep");
            if (current == 0)
            {
                sendSystemMessage(self, "Your UI window was closed. To recover the window later for use say \"" + magicWord + "\". Please make note of this word.", null);
                return SCRIPT_CONTINUE;
            }
            if (current > 0)
            {
                sendSystemMessage(self, "***WARNING: Your UI window was closed in the middle of setup. To recover the window and finish setup say \"" + magicWord + "\".", null);
                return SCRIPT_CONTINUE;
            }
        }
        String text = sui.getInputBoxText(params);
        if (text == null || text.equals(""))
        {
            sendSystemMessage(self, "You entered a null or invalid value, please try again.", null);
            messageTo(self, "continueCollectingData", null, 1, false);
        }
        setObjVar(self, "event.shuttle.lastData", text);
        messageTo(self, "storeLastDataObjVar", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int storeLastDataObjVar(obj_id self, dictionary params) throws InterruptedException
    {
        String lastDataStr = getStringObjVar(self, "event.shuttle.lastData");
        if (lastDataStr.equals("setup"))
        {
            setObjVar(self, "event.shuttle.setupStep", 1);
            setObjVar(self, "event.shuttle.setupCompleted", 0);
            messageTo(self, "continueCollectingData", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        int setupCompleted = getIntObjVar(self, "event.shuttle.setupCompleted");
        if (setupCompleted == 1)
        {
            switch (lastDataStr) {
                case "m":
                    messageTo(self, "spawnShuttle", null, 0, false);
                    messageTo(self, "continueCollectingData", null, 1, false);
                    return SCRIPT_CONTINUE;
                case "f":
                    messageTo(self, "spawnShuttle", null, 0, false);
                    messageTo(self, "doFlyBy", null, 5, false);
                    messageTo(self, "continueCollectingData", null, 1, false);
                    return SCRIPT_CONTINUE;
                case "land": {
                    obj_id shuttle = getObjIdObjVar(self, "event.shuttle.shuttle");
                    if (!isIdValid(shuttle)) {
                        sendSystemMessage(self, "WARNING: Did not find a valid shuttle to message so nothing happened.", null);
                        messageTo(self, "continueCollectingData", null, 1, false);
                        return SCRIPT_CONTINUE;
                    }
                    messageTo(shuttle, "landShuttle", null, 1, false);
                    messageTo(self, "continueCollectingData", null, 1, false);
                    return SCRIPT_CONTINUE;
                }
                case "leave": {
                    obj_id shuttle = getObjIdObjVar(self, "event.shuttle.shuttle");
                    if (!isIdValid(shuttle)) {
                        sendSystemMessage(self, "WARNING: Did not find a valid shuttle to message so nothing happened.", null);
                        messageTo(self, "continueCollectingData", null, 1, false);
                        return SCRIPT_CONTINUE;
                    }
                    messageTo(shuttle, "takeOff", null, 1, false);
                    messageTo(self, "continueCollectingData", null, 1, false);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (lastDataStr.equals("data"))
        {
            String template = getStringObjVar(self, "event.shuttle.template");
            int shuttleType = getIntObjVar(self, "event.shuttle.shuttleType");
            int numSpawns = getIntObjVar(self, "event.shuttle.numSpawns");
            sendSystemMessage(self, "Shuttle: " + SHUTTLE_NAME[shuttleType] + ". Number of Spawns: " + numSpawns + " . Spawn Template: " + template + ".", null);
            messageTo(self, "continueCollectingData", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        else if (lastDataStr.equals("quit"))
        {
            removeObjVar(self, "event.shuttle");
            detachScript(self, "event.shuttle");
            sendSystemMessage(self, "Many thank you for your playing.", null);
            return SCRIPT_CONTINUE;
        }
        int setupStep = getIntObjVar(self, "event.shuttle.setupStep");
        if (setupStep == 0)
        {
            sendSystemMessage(self, "Enter \"setup\" to setup data, \"data\" to view current data, \"quit\" to end. After setup type \"f\" to have the shuttle do an automatic fly-by. Type \"m\" to spawn and manually control the shuttle then use \"land\" to make it land, \"leave\" to make it take off.", null);
            messageTo(self, "continueCollectingData", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        else if (setupStep == 1)
        {
            switch (lastDataStr) {
                case "lambda":
                    setObjVar(self, "event.shuttle.shuttleType", 0);
                    setObjVar(self, "event.shuttle.setupStep", 2);
                    sendSystemMessage(self, TITLE[setupStep] + ": " + lastDataStr, null);
                    messageTo(self, "continueCollectingData", null, 1, false);
                    return SCRIPT_CONTINUE;
                case "shuttle":
                    setObjVar(self, "event.shuttle.shuttleType", 1);
                    setObjVar(self, "event.shuttle.setupStep", 2);
                    sendSystemMessage(self, TITLE[setupStep] + ": " + lastDataStr, null);
                    messageTo(self, "continueCollectingData", null, 1, false);
                    return SCRIPT_CONTINUE;
                case "transport":
                    setObjVar(self, "event.shuttle.shuttleType", 2);
                    setObjVar(self, "event.shuttle.setupStep", 2);
                    sendSystemMessage(self, TITLE[setupStep] + ": " + lastDataStr, null);
                    messageTo(self, "continueCollectingData", null, 1, false);
                    return SCRIPT_CONTINUE;
                case "theed transport":
                    setObjVar(self, "event.shuttle.shuttleType", 3);
                    setObjVar(self, "event.shuttle.setupStep", 2);
                    sendSystemMessage(self, TITLE[setupStep] + ": " + lastDataStr, null);
                    messageTo(self, "continueCollectingData", null, 1, false);
                    return SCRIPT_CONTINUE;
            }
            sendSystemMessage(self, "You must type \"lambda\", \"shuttle\", \"transport\" or \"theed transport\". Try again.", null);
            messageTo(self, "continueCollectingData", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        else if (setupStep == 2)
        {
            if (lastDataStr.equals("here"))
            {
                location spawnPoint = getLocation(self);
                float heading = getYaw(self);
                setObjVar(self, "event.shuttle.heading", heading);
                setObjVar(self, "event.shuttle.spawnPoint", spawnPoint);
                setObjVar(self, "event.shuttle.setupStep", 3);
                messageTo(self, "continueCollectingData", null, 1, false);
                sendSystemMessage(self, TITLE[setupStep] + ": " + lastDataStr, null);
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(self, "You must stand where you want the shuttle to land and type \"here\". Try again.", null);
            messageTo(self, "continueCollectingData", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        else if (setupStep == 3)
        {
            if (lastDataStr.equals("yes"))
            {
                setObjVar(self, "event.shuttle.setupStep", 4);
                messageTo(self, "continueCollectingData", null, 1, false);
                sendSystemMessage(self, TITLE[setupStep] + ": " + lastDataStr, null);
                return SCRIPT_CONTINUE;
            }
            if (lastDataStr.equals("no"))
            {
                setObjVar(self, "event.shuttle.numSpawns", 0);
                setObjVar(self, "event.shuttle.setupStep", 6);
                messageTo(self, "continueCollectingData", null, 1, false);
                sendSystemMessage(self, TITLE[setupStep] + ": " + lastDataStr, null);
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(self, "You must answer either \"yes\" or \"no\". Try again.", null);
            messageTo(self, "continueCollectingData", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        else if (setupStep == 4)
        {
            setObjVar(self, "event.shuttle.template", lastDataStr);
            setObjVar(self, "event.shuttle.setupStep", 5);
            sendSystemMessage(self, TITLE[setupStep] + ": " + lastDataStr, null);
            messageTo(self, "continueCollectingData", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        else if (setupStep == 5)
        {
            int lastDataInt = utils.stringToInt(lastDataStr);
            if (lastDataInt >= 0 && lastDataInt <= 10)
            {
                setObjVar(self, "event.shuttle.numSpawns", lastDataInt);
                setObjVar(self, "event.shuttle.setupStep", 6);
                messageTo(self, "continueCollectingData", null, 1, false);
                sendSystemMessage(self, TITLE[setupStep] + ": " + lastDataStr, null);
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(self, "You must specify a number between 0 and 10. Try again.", null);
            messageTo(self, "continueCollectingData", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        else if (setupStep == 6)
        {
            setObjVar(self, "event.shuttle.magicWord", lastDataStr);
            setObjVar(self, "event.shuttle.setupStep", 0);
            sendSystemMessage(self, TITLE[setupStep] + ": " + lastDataStr, null);
            setObjVar(self, "event.shuttle.setupCompleted", 1);
            messageTo(self, "continueCollectingData", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int continueCollectingData(obj_id self, dictionary params) throws InterruptedException
    {
        showUI(self, self);
        return SCRIPT_CONTINUE;
    }
    public int spawnShuttle(obj_id self, dictionary params) throws InterruptedException
    {
        int numSpawns = getIntObjVar(self, "event.shuttle.numSpawns");
        location spawnPoint = getLocationObjVar(self, "event.shuttle.spawnPoint");
        int shuttleType = getIntObjVar(self, "event.shuttle.shuttleType");
        obj_id shuttle = create.object(SHUTTLE_TEMPLATE[shuttleType], spawnPoint);
        if (numSpawns > 0)
        {
            String template = getStringObjVar(self, "event.shuttle.template");
            setObjVar(shuttle, "event.shuttle.template", template);
        }
        setObjVar(self, "event.shuttle.shuttle", shuttle);
        setObjVar(shuttle, "event.shuttle.owner", self);
        setObjVar(shuttle, "event.shuttle.numSpawns", numSpawns);
        setObjVar(shuttle, "event.shuttle.shuttleType", shuttleType);
        float heading = getFloatObjVar(self, "event.shuttle.heading");
        if (shuttleType < 3)
        {
            setYaw(shuttle, heading + 180);
        }
        else 
        {
            setYaw(shuttle, heading);
        }
        setName(shuttle, SHUTTLE_NAME[shuttleType]);
        detachScript(shuttle, "ai.ai");
        detachScript(shuttle, "ai.creature_combat");
        detachScript(shuttle, "skeleton.humanoid");
        detachScript(shuttle, "systems.combat.combat_actions");
        detachScript(shuttle, "systems.combat.credit_for_kills");
        stop(shuttle);
        if (shuttleType > 0)
        {
            setPosture(shuttle, POSTURE_PRONE);
        }
        attachScript(shuttle, "event.shuttle_control");
        sendSystemMessage(self, "Created shuttle with OID " + shuttle, null);
        return SCRIPT_CONTINUE;
    }
    public int doFlyBy(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(getObjIdObjVar(self, "event.shuttle.shuttle"), "performFlyBy", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int getRidOfOwner(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "event.shuttle.shuttle");
        return SCRIPT_CONTINUE;
    }
}
