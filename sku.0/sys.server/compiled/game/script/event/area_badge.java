package script.event;

import script.library.badge;
import script.library.utils;
import script.obj_id;

import java.util.StringTokenizer;

public class area_badge extends script.base_script
{
    public area_badge()
    {
    }
    private static final String[] HELP_TEXT =
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
            if (getGodLevel(self) < 5)
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
        else
        {
            if (getGodLevel(self) < 5)
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
            if (getGodLevel(self) < 5)
            {
                detachScript(self, "event.area_badge");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
                return SCRIPT_CONTINUE;
            }
        }
        else
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
                badgeNumber = utils.stringToInt(badgeNumberStr);
                badgeRange = (float) utils.stringToInt(badgeRangeStr);
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
                    for (obj_id objPlayer : objPlayers) {
                        if (objPlayer != self) {
                            badge.grantBadge(objPlayer, badgeName);
                            sendSystemMessage(self, "Granting badge " + badgeNumber + " to player " + getName(objPlayer), null);
                        }
                    }
                }
            }
        }
        if ((toLower(strText)).equals("detach"))
        {
            detachScript(self, "event.area_badge");
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
