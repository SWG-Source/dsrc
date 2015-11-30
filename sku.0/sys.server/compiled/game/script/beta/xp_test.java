package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class xp_test extends script.base_script
{
    public xp_test()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if ((text.startsWith("grantXP")) || (text.startsWith("revokeXP")))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            if (st.countTokens() != 3)
            {
                debugSpeakMsg(self, "PARAM ERROR - syntax: <grantXP/revokeXP> <xpType> <amt>");
                return SCRIPT_OVERRIDE;
            }
            String cmd = st.nextToken();
            String xpType = st.nextToken();
            int amt = utils.stringToInt(st.nextToken());
            if (amt == -1)
            {
                debugSpeakMsg(self, "PARSE ERROR - syntax: <grantXP/revokeXP> <xpType> <amt>");
            }
            obj_id target = getLookAtTarget(self);
            if ((target == null) || (target == obj_id.NULL_ID))
            {
                target = self;
            }
            if (!isPlayer(target))
            {
                debugSpeakMsg(self, "test_xp: invalid target for action!");
                return SCRIPT_OVERRIDE;
            }
            if (cmd.equals("grantXP"))
            {
                if (grantExperiencePoints(target, xpType, amt) != XP_ERROR)
                {
                    debugSpeakMsg(target, "test_xp(grantXP): " + xpType + " + " + amt + " = " + getExperiencePoints(target, xpType));
                }
            }
            else if (cmd.equals("revokeXP"))
            {
                if (grantExperiencePoints(target, xpType, -amt) != XP_ERROR)
                {
                    debugSpeakMsg(target, "test_xp(revokeXP): " + xpType + " - " + amt + " = " + getExperiencePoints(target, xpType));
                }
            }
            return SCRIPT_OVERRIDE;
        }
        else if (text.startsWith("getXP"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            if (st.countTokens() != 2)
            {
                debugSpeakMsg(self, "PARAM ERROR - syntax: getXP <xpType>");
                return SCRIPT_OVERRIDE;
            }
            String cmd = st.nextToken();
            String xpType = st.nextToken();
            obj_id target = getLookAtTarget(self);
            if ((target == null) || (target == obj_id.NULL_ID))
            {
                target = self;
            }
            if (!isPlayer(target))
            {
                debugSpeakMsg(self, "test_xp: invalid target for action!");
                return SCRIPT_OVERRIDE;
            }
            int xp = getExperiencePoints(target, xpType);
            debugSpeakMsg(target, "XP: " + xpType + " = " + xp);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
