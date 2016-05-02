package script.event;

import script.library.utils;
import script.obj_id;

public class area_tefme extends script.base_script
{
    public area_tefme()
    {
    }
    private static final String[] HELP_TEXT =
    {
        "=========================================",
        "KEY WORDS (These are case sensitive): setStartPhrase, setEndPhrase, setAreaRange, setSingleMode, setAreaMode, showStats, detach.",
        "setStartPhrase: Set the phrase that you will speak to cause yourself to become TEF'd. Default is \"Come get me\"",
        "setEndPhrase: Set the phrase that you will speak to attempt to end your TEFs. Default is \"Not in the face!\"",
        "setAreaRange: Set the range for area wide TEF mode.",
        "setSingleMode: Puts you in single target mode, meaning only your look-at target will get a TEF against you.",
        "setAreaMode: Puts you in area wide mode.",
        "showStats: Check what mode you are in and current variables.",
        "detach: Detaches this script and clears objvars.",
        "========================================="
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isPlayer(self))
        {
            detachScript(self, "event.area_tefme");
        }
        removeObjVar(self, "event.tefme");
        if (!isGod(self))
        {
            detachScript(self, "event.area_tefme");
            sendSystemMessage(self, "You must be in God Mode for this script to take hold.", null);
            removeObjVar(self, "event.tefme");
            return SCRIPT_CONTINUE;
        }
        else
        {
            if (getGodLevel(self) < 15)
            {
                detachScript(self, "event.area_tefme");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
                removeObjVar(self, "event.tefme");
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessage(self, "Say \"Help\" for usage and options. Remember to refresh God Mode after teleporting or warping.", null);
        String startPhrase = "Come get me";
        String endPhrase = "Not in the face!";
        setObjVar(self, "event.tefme.currentState", 0);
        setObjVar(self, "event.tefme.startPhrase", startPhrase);
        setObjVar(self, "event.tefme.endPhrase", endPhrase);
        setObjVar(self, "event.tefme.range", 100);
        setObjVar(self, "event.tefme.mode", 0);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (objSpeaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (isGod(self))
        {
            if (getGodLevel(self) < 15)
            {
                detachScript(self, "event.area_tefme");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
                removeObjVar(self, "event.tefme");
                return SCRIPT_CONTINUE;
            }
        }
        else
        {
            detachScript(self, "event.area_tefme");
            sendSystemMessage(self, "You must be in God Mode to use this script.", null);
            removeObjVar(self, "event.tefme");
            return SCRIPT_CONTINUE;
        }
        int currentState = getIntObjVar(self, "event.tefme.currentState");
        String startPhrase = getStringObjVar(self, "event.tefme.startPhrase");
        String endPhrase = getStringObjVar(self, "event.tefme.endPhrase");
        int range = getIntObjVar(self, "event.tefme.range");
        int mode = getIntObjVar(self, "event.tefme.mode");
        if (mode == 0 && currentState == 0)
        {
            if (strText.equalsIgnoreCase(startPhrase))
            {
                obj_id[] objPlayers = getPlayerCreaturesInRange(self, range);
                if (objPlayers != null && objPlayers.length > 0)
                {
                    for (obj_id objPlayer : objPlayers) {
                        if (objPlayer != self) {
                            pvpSetPersonalEnemyFlag(self, objPlayer);
                            sendSystemMessage(self, getName(objPlayer) + " can now attack you!", null);
                        }
                    }
                }
            }
            if (strText.equalsIgnoreCase(endPhrase))
            {
                pvpRemoveAllTempEnemyFlags(self);
                sendSystemMessage(self, "Clearing your TEFs. Note that you may still be able to attack some players for a period of time.", null);
            }
        }
        else if (mode == 1 && currentState == 0)
        {
            if (strText.equalsIgnoreCase(startPhrase))
            {
                obj_id target = getLookAtTarget(self);
                if (isIdValid(target) && target != self)
                {
                    pvpSetPersonalEnemyFlag(self, target);
                    sendSystemMessage(self, getName(target) + " can now attack you!", null);
                }
            }
            if (strText.equalsIgnoreCase(endPhrase))
            {
                pvpRemoveAllTempEnemyFlags(self);
                sendSystemMessage(self, "Clearing your TEFs. Note that you may still be able to attack some players for a period of time.", null);
            }
        }
        else if (currentState == 1)
        {
            if (strText.equalsIgnoreCase(endPhrase))
            {
                sendSystemMessage(self, "Your start phrase must be different from your end phrase dummy.", null);
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, "event.tefme.startPhrase", strText);
            setObjVar(self, "event.tefme.currentState", 0);
            sendSystemMessage(self, "You will TEF yourself when you say the following phrase: " + strText, null);
            return SCRIPT_CONTINUE;
        }
        else if (currentState == 2)
        {
            if (strText.equalsIgnoreCase(startPhrase))
            {
                sendSystemMessage(self, "Your end phrase must be different from your start phrase dummy.", null);
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, "event.tefme.endPhrase", strText);
            setObjVar(self, "event.tefme.currentState", 0);
            sendSystemMessage(self, "You will attempt to lose your TEFs when you say the following phrase: " + strText, null);
            return SCRIPT_CONTINUE;
        }
        else if (currentState == 3)
        {
            range = utils.stringToInt(strText);
            if (range < 1 || range > 256)
            {
                sendSystemMessage(self, "Range must be between 1 and 255. Please note that these are numbers. Try again.", null);
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, "event.tefme.range", range);
            setObjVar(self, "event.tefme.currentState", 0);
            sendSystemMessage(self, "Your TEF range is set to " + strText + "m.", null);
            return SCRIPT_CONTINUE;
        }
        else if (currentState > 3)
        {
            setObjVar(self, "event.tefme.currentState", 0);
            sendSystemMessage(self, "Somehow currentState was above 3. Resetting it to 0.", null);
            return SCRIPT_CONTINUE;
        }
        if (mode > 1)
        {
            setObjVar(self, "event.tefme.mode", 0);
            sendSystemMessage(self, "Somehow mode was above 1. Resetting it to 0.", null);
            return SCRIPT_CONTINUE;
        }
        if (strText.equals("setStartPhrase"))
        {
            setObjVar(self, "event.tefme.currentState", 1);
            sendSystemMessage(self, "The next phrase you say will be set as the trigger to TEF yourself.", null);
        }
        else if (strText.equals("setEndPhrase"))
        {
            setObjVar(self, "event.tefme.currentState", 2);
            sendSystemMessage(self, "The next phrase you say will be set as the trigger to attempt to remove your TEFs.", null);
        }
        else if (strText.equals("setAreaRange"))
        {
            setObjVar(self, "event.tefme.currentState", 3);
            sendSystemMessage(self, "Speak the range you wish to use for the AOE TEF.", null);
        }
        else if (strText.equals("setSingleMode"))
        {
            setObjVar(self, "event.tefme.mode", 1);
            sendSystemMessage(self, "Single TEF Mode active. Your look-at target will recieve a TEF for you when you speak the startPhrase.", null);
        }
        else if (strText.equals("setAreaMode"))
        {
            setObjVar(self, "event.tefme.mode", 0);
            sendSystemMessage(self, "Area Mode active. Everyone within " + range + "m will get a TEF against you when you speak the startPhrase.", null);
        }
        else if (strText.equals("showStats"))
        {
            String currentModeStr = "Area Mode";
            if (mode == 0)
            {
                currentModeStr = "Area Mode";
            }
            if (mode == 1)
            {
                currentModeStr = "Single Target Mode";
            }
            sendSystemMessage(self, "You are currently in " + currentModeStr, null);
            sendSystemMessage(self, "Your start phrase is: " + startPhrase, null);
            sendSystemMessage(self, "Your end phrase is: " + endPhrase, null);
            sendSystemMessage(self, "Your current AOE range is set to " + range + "m", null);
        }
        else if ((toLower(strText)).equals("detach"))
        {
            detachScript(self, "event.area_tefme");
            removeObjVar(self, "event.tefme");
        }
        else if ((toLower(strText)).equals("help"))
        {
            for (String helpText : HELP_TEXT) {
                sendSystemMessage(self, helpText, null);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
