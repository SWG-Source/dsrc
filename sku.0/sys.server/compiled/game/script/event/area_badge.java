package script.event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.badge;
import java.util.StringTokenizer;

public class area_badge extends script.base_script
{
    public area_badge()
    {
    }
    public static final String[] HELP_TEXT = 
    {
        "------------------------------------",
        "USAGE: \"areabadge <RANGE> <BADGENUMBER>\"",
        "<RANGE>: Valid values are between 1 and 256",
        "<BADGENUMBER>: Valid badges are between 1 and 124",
        "------------------------------------"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isPlayer(self))
        {
            detachScript(self, "event.area_badge");
        }
        if (!isGod(self))
        {
            detachScript(self, "event.area_badge");
            sendSystemMessage(self, "You must be in God Mode for this script to take hold.", null);
            return SCRIPT_CONTINUE;
        }
        if (isGod(self))
        {
            int godLevel = getGodLevel(self);
            if (godLevel < 5)
            {
                detachScript(self, "event.area_badge");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessage(self, "USAGE: \"areabadge <RANGE> <BADGENUMBER>\". Say \"Help\" for usage and options.", null);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (!isGod(self))
        {
            detachScript(self, "event.area_badge");
            sendSystemMessage(self, "You must be in God Mode to use this script.", null);
        }
        if (isGod(self))
        {
            int godLevel = getGodLevel(self);
            if (godLevel < 5)
            {
                detachScript(self, "event.area_badge");
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
            if (godLevel < 5)
            {
                detachScript(self, "event.area_badge");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
                return SCRIPT_CONTINUE;
            }
        }
        if (!isGod(self))
        {
            detachScript(self, "event.area_badge");
            sendSystemMessage(self, "You must be in God Mode to use this script.", null);
            return SCRIPT_CONTINUE;
        }
        if ((toLower(strText)).startsWith("areabadge"))
        {
            float badgeRange;
            int badgeNumber;
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                String badgeRangeStr = st.nextToken();
                String badgeNumberStr = st.nextToken();
                int badgeRangeInt = utils.stringToInt(badgeRangeStr);
                badgeNumber = utils.stringToInt(badgeNumberStr);
                badgeRange = (float)badgeRangeInt;
                if (badgeRange > 256 || badgeRange < 1)
                {
                    sendSystemMessage(self, "Error: Specify a range between 1 and 256.", null);
                    return SCRIPT_CONTINUE;
                }
                if (badgeNumber < 1 || badgeNumber > 124)
                {
                    sendSystemMessage(self, "Error: Please specify a valid badge number between 1 and 124.", null);
                    return SCRIPT_CONTINUE;
                }
                String badgeName = getCollectionSlotName(badgeNumber);
                if ((badgeName == null) || (badgeName.length() == 0))
                {
                    sendSystemMessage(self, "Error: badge number " + badgeNumber + " is not a valid badge.", null);
                    return SCRIPT_CONTINUE;
                }
                obj_id[] objPlayers = getPlayerCreaturesInRange(self, badgeRange);
                if (objPlayers != null && objPlayers.length > 0)
                {
                    for (int i = 0; i < objPlayers.length; i++)
                    {
                        if (objPlayers[i] != self)
                        {
                            badge.grantBadge(objPlayers[i], badgeName);
                            String playerName = getName(objPlayers[i]);
                            sendSystemMessage(self, "Granting badge " + badgeNumber + " to player " + playerName, null);
                        }
                    }
                }
            }
        }
        if ((toLower(strText)).equals("detach"))
        {
            detachScript(self, "event.area_badge");
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
