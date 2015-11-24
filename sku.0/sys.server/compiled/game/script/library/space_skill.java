package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.xp;

public class space_skill extends script.base_script
{
    public space_skill()
    {
    }
    public static final String WAYPOINT_BTN = "@space/space_interaction:retire_waypoint_btn";
    public static final String REBEL_WARNING = "@space/space_interaction:retire_rebel_warning";
    public static final String IMPERIAL_WARNING = "@space/space_interaction:retire_imperial_warning";
    public static final String NEUTRAL_WARNING = "@space/space_interaction:retire_neutral_warning";
    public static final String WARNING_TITLE = "@space/space_interaction:retire_warning_title";
    public static final String REBEL_WP_NAME = "@npc_spawner_n:j_pai_brek";
    public static final String IMPERIAL_WP_NAME = "@npc_spawner_n:landau";
    public static final String NEUTRAL_WP_NAME = "@npc_spawner_n:gil_burtin";
    public static final String REBEL = "pilot_rebel_navy";
    public static final String IMPERIAL = "pilot_imperial_navy";
    public static final String NEUTRAL = "pilot_neutral";
    public static final String[] SKILL_NAMES = 
    {
        "_master",
        "_starships_04",
        "_weapons_04",
        "_procedures_04",
        "_droid_04",
        "_starships_03",
        "_weapons_03",
        "_procedures_03",
        "_droid_03",
        "_starships_02",
        "_weapons_02",
        "_procedures_02",
        "_droid_02",
        "_starships_01",
        "_weapons_01",
        "_procedures_01",
        "_droid_01",
        "_novice"
    };
    public static boolean hasRebelSkill(obj_id player) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice"));
    }
    public static boolean hasImperialSkill(obj_id player) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_novice"));
    }
    public static boolean hasPilotSkill(obj_id player) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_novice"));
    }
    public static boolean hasSpaceSkills(obj_id player) throws InterruptedException
    {
        if (hasRebelSkill(player))
        {
            return true;
        }
        else if (hasImperialSkill(player))
        {
            return true;
        }
        else if (hasPilotSkill(player))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static void retire(obj_id player, String profession) throws InterruptedException
    {
        utils.setScriptVar(player, "revokePilotSkill", 1);
        for (int i = 0; i < SKILL_NAMES.length; i++)
        {
            String skill = profession + SKILL_NAMES[i];
            if (hasSkill(player, skill))
            {
                revokeSkill(player, skill);
            }
        }
        utils.removeScriptVar(player, "revokePilotSkill");
    }
    public static void retireWarning(obj_id player, String skill) throws InterruptedException
    {
        String prompt = "";
        String wp_name = "";
        location loc = new location();
        if (skill.indexOf("rebel") >= 0)
        {
            prompt = REBEL_WARNING;
            wp_name = REBEL_WP_NAME;
            loc = new location(-5072.0f, 0.0f, -2343.0f, "corellia");
        }
        else if (skill.indexOf("imperial") >= 0)
        {
            prompt = IMPERIAL_WARNING;
            wp_name = IMPERIAL_WP_NAME;
            loc = new location(-5516.0f, 0.0f, 4403.0f, "naboo");
        }
        else if (skill.indexOf("neutral") >= 0)
        {
            prompt = NEUTRAL_WARNING;
            wp_name = NEUTRAL_WP_NAME;
            loc = new location(-1174.0f, 0.0f, -3647.0f, "tatooine");
        }
        else 
        {
            return;
        }
        int pid = createSUIPage(sui.SUI_MSGBOX, player, player, "handleRetireWarning");
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, prompt);
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, WARNING_TITLE);
        sui.msgboxHideBtnLeft(pid);
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, WAYPOINT_BTN);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "@ok");
        showSUIPage(pid);
        if (pid > 0)
        {
            utils.setScriptVar(player, "revokePilotSkillWarningLoc", loc);
            utils.setScriptVar(player, "revokePilotSkillWarningWpName", wp_name);
        }
    }
    public static void revokeExperienceForRetire(obj_id player, String skill) throws InterruptedException
    {
        String prestigeXpName = "";
        if (skill.indexOf("rebel") >= 0)
        {
            prestigeXpName = xp.SPACE_PRESTIGE_REBEL;
        }
        else if (skill.indexOf("imperial") >= 0)
        {
            prestigeXpName = xp.SPACE_PRESTIGE_IMPERIAL;
        }
        else if (skill.indexOf("neutral") >= 0)
        {
            prestigeXpName = xp.SPACE_PRESTIGE_PILOT;
        }
        else 
        {
            return;
        }
        int prestigeXp = getExperiencePoints(player, prestigeXpName);
        if (prestigeXp > 0)
        {
            prestigeXp *= -1;
            xp.grantUnmodifiedExperience(player, prestigeXpName, prestigeXp);
        }
        int combatXp = getExperiencePoints(player, xp.SPACE_COMBAT_GENERAL);
        if (combatXp > 0)
        {
            combatXp *= -1;
            xp.grantUnmodifiedExperience(player, xp.SPACE_COMBAT_GENERAL, combatXp);
        }
    }
    public static boolean isMasterPilot(obj_id player) throws InterruptedException
    {
        if (hasRebelSkill(player))
        {
            return (hasSkill(player, "pilot_rebel_navy_master"));
        }
        if (hasImperialSkill(player))
        {
            return (hasSkill(player, "pilot_imperial_navy_master"));
        }
        if (hasPilotSkill(player))
        {
            return (hasSkill(player, "pilot_neutral_master"));
        }
        return false;
    }
}
