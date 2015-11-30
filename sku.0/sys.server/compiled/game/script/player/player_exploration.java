package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.badge;
import script.library.utils;

public class player_exploration extends script.base_script
{
    public player_exploration()
    {
    }
    public int explorerBadge(obj_id self, dictionary params) throws InterruptedException
    {
        String badgeName = "";
        if (params.containsKey("badgeNumber"))
        {
            int badgeNum = params.getInt("badgeNumber");
            badgeName = getCollectionSlotName(badgeNum);
        }
        else if (params.containsKey("badgeName"))
        {
            badgeName = params.getString("badgeName");
        }
        if ((badgeName != null) && (badgeName.length() > 0))
        {
            badge.grantBadge(self, badgeName);
        }
        int[] intExplorerBadges = dataTableGetIntColumn("datatables/badge/exploration_badges.iff", "intIndex");
        int intExplBadgeCount = 0;
        for (int intI = 0; intI < intExplorerBadges.length; intI++)
        {
            badgeName = getCollectionSlotName(intExplorerBadges[intI]);
            if ((badgeName != null) && (badgeName.length() > 0) && badge.hasBadge(self, badgeName))
            {
                intExplBadgeCount = intExplBadgeCount + 1;
            }
        }
        if (intExplBadgeCount >= 10)
        {
            if (!badge.hasBadge(self, "bdg_exp_10_badges"))
            {
                badge.grantBadge(self, "bdg_exp_10_badges");
                return SCRIPT_CONTINUE;
            }
        }
        if (intExplBadgeCount >= 20)
        {
            if (!badge.hasBadge(self, "bdg_exp_20_badges"))
            {
                badge.grantBadge(self, "bdg_exp_20_badges");
                return SCRIPT_CONTINUE;
            }
        }
        if (intExplBadgeCount >= 30)
        {
            if (!badge.hasBadge(self, "bdg_exp_30_badges"))
            {
                badge.grantBadge(self, "bdg_exp_30_badges");
                return SCRIPT_CONTINUE;
            }
        }
        if (intExplBadgeCount >= 40)
        {
            if (!badge.hasBadge(self, "bdg_exp_40_badges"))
            {
                badge.grantBadge(self, "bdg_exp_40_badges");
                return SCRIPT_CONTINUE;
            }
        }
        if (intExplBadgeCount >= 45)
        {
            if (!badge.hasBadge(self, "bdg_exp_45_badges"))
            {
                badge.grantBadge(self, "bdg_exp_45_badges");
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCleanupHarassment(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "being_scanned"))
        {
            utils.removeScriptVar(self, "being_scanned");
        }
        return SCRIPT_CONTINUE;
    }
}
