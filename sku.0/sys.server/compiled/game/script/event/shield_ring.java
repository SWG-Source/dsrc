package script.event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import java.util.StringTokenizer;

public class shield_ring extends script.base_script
{
    public shield_ring()
    {
    }
    public static final String[] HELP_TEXT = 
    {
        "=========================================",
        "USAGE: ShieldRing [Armor Effectiveness 1-100] (default effectiveness is 20)",
        "ShieldRing [Armor Effectiveness 1-100] [Armor Rating 0-3] (default armor rating is 1)",
        "ShieldRing [Armor Effectiveness 1-100] [Armor Rating 0-3] [Armor Vulnerability 0-511] (default vulnerability is 0, none)",
        "Note: Armor Vulnerabilities use 9 bits. If you don't know what this means and need to be vulnerable to something see Mark.",
        "Saying \"detach\" will automatically detach this script.",
        "========================================="
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isPlayer(self))
        {
            detachScript(self, "event.shield_ring");
        }
        if (!isGod(self))
        {
            detachScript(self, "event.shield_ring");
            sendSystemMessage(self, "You must be in God Mode for this script to take hold.", null);
            return SCRIPT_CONTINUE;
        }
        if (isGod(self))
        {
            int godLevel = getGodLevel(self);
            if (godLevel < 15)
            {
                detachScript(self, "event.shield_ring");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessage(self, "Say \"Help\" for usage and options.", null);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (!isGod(self))
        {
            detachScript(self, "event.shield_ring");
            sendSystemMessage(self, "You must be in God Mode to use this script.", null);
        }
        if (isGod(self))
        {
            int godLevel = getGodLevel(self);
            if (godLevel < 15)
            {
                detachScript(self, "event.shield_ring");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
                return SCRIPT_CONTINUE;
            }
        }
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
            int godLevel = getGodLevel(self);
            if (godLevel < 15)
            {
                detachScript(self, "event.shield_ring");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
                return SCRIPT_CONTINUE;
            }
        }
        if (!isGod(self))
        {
            detachScript(self, "event.shield_ring");
            sendSystemMessage(self, "You must be in God Mode to use this script.", null);
            return SCRIPT_CONTINUE;
        }
        if (strText.startsWith("ShieldRing"))
        {
            sendSystemMessage(self, "This command has been temporarily disabled. See S. Jakab or T. Blair", null);
        }
        if ((toLower(strText)).equals("detach"))
        {
            detachScript(self, "event.shield_ring");
            return SCRIPT_CONTINUE;
        }
        if ((toLower(strText)).equals("help"))
        {
            for (int i = 0; i < HELP_TEXT.length; i++)
            {
                sendSystemMessage(self, HELP_TEXT[i], null);
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
