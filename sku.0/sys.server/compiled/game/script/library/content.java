package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.badge;

public class content extends script.base_script
{
    public content()
    {
    }
    public static final String SECURITY_CLEARANCE_ASTRO = "bdg_content_rsf_clearance_1";
    public static final String SECURITY_CLEARANCE_BINARY = "bdg_content_rsf_clearance_2";
    public static final String SECURITY_CLEARANCE_CHUBA = "bdg_content_rsf_clearance_3";
    public static final String SECURITY_CLEARANCE_DUSK = "bdg_content_rsf_clearance_4";
    public static final String SECURITY_CLEARANCE_ECHO = "bdg_content_rsf_clearance_5";
    public static final String SECURITY_CLEARANCE_FALCON = "bdg_content_rsf_clearance_6";
    public static final String SECURITY_CLEARANCE_GORAX = "bdg_content_rsf_clearance_7";
    public static final String REBEL_PATH_OBJVAR_NAME = "legacy.faction.rebelPath";
    public static final String IMPERIAL_PATH_OBJVAR_NAME = "legacy.faction.imperialPath";
    public static boolean isCrafter(obj_id player) throws InterruptedException
    {
        String profession = getSkillTemplate(player);
        int crafting = profession.indexOf("trader");
        if (crafting > -1)
        {
            return true;
        }
        return false;
    }
    public static boolean isEntertainer(obj_id player) throws InterruptedException
    {
        String profession = getSkillTemplate(player);
        int entertaining = profession.indexOf("entertainer");
        if (entertaining > -1)
        {
            return true;
        }
        return false;
    }
    public static void grantRsfSecurityClearance(obj_id player) throws InterruptedException
    {
        if (badge.hasBadge(player, SECURITY_CLEARANCE_GORAX))
        {
            return;
        }
        if (badge.hasBadge(player, SECURITY_CLEARANCE_FALCON))
        {
            badge.grantBadge(player, SECURITY_CLEARANCE_GORAX);
            return;
        }
        if (badge.hasBadge(player, SECURITY_CLEARANCE_ECHO))
        {
            badge.grantBadge(player, SECURITY_CLEARANCE_FALCON);
            return;
        }
        if (badge.hasBadge(player, SECURITY_CLEARANCE_DUSK))
        {
            badge.grantBadge(player, SECURITY_CLEARANCE_ECHO);
            return;
        }
        if (badge.hasBadge(player, SECURITY_CLEARANCE_CHUBA))
        {
            badge.grantBadge(player, SECURITY_CLEARANCE_DUSK);
            return;
        }
        if (badge.hasBadge(player, SECURITY_CLEARANCE_BINARY))
        {
            badge.grantBadge(player, SECURITY_CLEARANCE_CHUBA);
            return;
        }
        if (badge.hasBadge(player, SECURITY_CLEARANCE_ASTRO))
        {
            badge.grantBadge(player, SECURITY_CLEARANCE_BINARY);
            return;
        }
        badge.grantBadge(player, SECURITY_CLEARANCE_ASTRO);
        return;
    }
    public static void grantAllRsfSecurityClearance(obj_id player) throws InterruptedException
    {
        if (!badge.hasBadge(player, "bdg_content_rsf_clearance_1"))
        {
            badge.grantBadge(player, "bdg_content_rsf_clearance_1");
        }
        if (!badge.hasBadge(player, "bdg_content_rsf_clearance_2"))
        {
            badge.grantBadge(player, "bdg_content_rsf_clearance_2");
        }
        if (!badge.hasBadge(player, "bdg_content_rsf_clearance_3"))
        {
            badge.grantBadge(player, "bdg_content_rsf_clearance_3");
        }
        if (!badge.hasBadge(player, "bdg_content_rsf_clearance_4"))
        {
            badge.grantBadge(player, "bdg_content_rsf_clearance_4");
        }
        if (!badge.hasBadge(player, "bdg_content_rsf_clearance_5"))
        {
            badge.grantBadge(player, "bdg_content_rsf_clearance_5");
        }
        if (!badge.hasBadge(player, "bdg_content_rsf_clearance_6"))
        {
            badge.grantBadge(player, "bdg_content_rsf_clearance_6");
        }
        if (!badge.hasBadge(player, "bdg_content_rsf_clearance_7"))
        {
            badge.grantBadge(player, "bdg_content_rsf_clearance_7");
        }
        return;
    }
}
