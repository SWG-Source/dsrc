package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.proc;
import script.library.sui;
import script.library.skill;

public class expertise extends script.base_script
{
    public expertise()
    {
    }
    public static final String JEDI_AUTO_ALLOCATION_TABLE = "datatables/expertise/autoallocation_jedi.iff";
    public static final string_id SID_SUI_EXPERTISE_INTRODUCTION_TITLE = new string_id("expertise_d", "sui_expertise_introduction_title");
    public static final string_id SID_SUI_EXPERTISE_INTRODUCTION_BODY = new string_id("expertise_d", "sui_expertise_introduction_body");
    public static void cacheExpertiseProcReacList(obj_id player) throws InterruptedException
    {
        String[] skillModList = getSkillStatModListingForPlayer(player);
        Vector expertiseProcReacList = new Vector();
        expertiseProcReacList.setSize(0);
        for (int i = 0; i < skillModList.length; ++i)
        {
            if ((skillModList[i].startsWith("expertise_") || skillModList[i].startsWith("kill_meter_")) && (skillModList[i].endsWith("_proc") || skillModList[i].endsWith("_reac")))
            {
                expertiseProcReacList.addElement(skillModList[i]);
            }
        }
        if (expertiseProcReacList.size() > 0)
        {
            utils.setScriptVar(player, "expertiseProcReacList", expertiseProcReacList);
        }
        else 
        {
            if (utils.hasScriptVar(player, "expertiseProcReacList"))
            {
                utils.removeScriptVar(player, "expertiseProcReacList");
            }
        }
        proc.buildCurrentProcList(player);
        proc.buildCurrentReacList(player);
    }
    public static void autoAllocateExpertiseByLevel(obj_id player, boolean onLevel) throws InterruptedException
    {
        int playerLevel = getLevel(player);
        if (playerLevel > 9)
        {
            String playerClass = getSkillTemplate(player);
            grantSkill(player, "expertise");
            displayIntroductionToExpertise(player);
        }
        return;
    }
    public static void displayIntroductionToExpertise(obj_id player) throws InterruptedException
    {
        String title = utils.packStringId(SID_SUI_EXPERTISE_INTRODUCTION_TITLE);
        String prompt = utils.packStringId(SID_SUI_EXPERTISE_INTRODUCTION_BODY);
        int pid = sui.msgbox(player, player, prompt, sui.OK_ONLY, title, "introToExpertise");
        if (pid > -1)
        {
            sui.setAutosaveProperty(pid, false);
            sui.setSizeProperty(pid, 400, 225);
            sui.setLocationProperty(pid, 200, 200);
            flushSUIPage(pid);
        }
    }
    public static boolean hasExpertiseAllocated(obj_id player) throws InterruptedException
    {
        String[] skillList = getSkillListingForPlayer(player);
        if (skillList != null)
        {
            for (int i = 0; i < skillList.length; ++i)
            {
                if (skillList[i].startsWith("expertise_"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static String[] getExpertiseAllocation(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return null;
        }
        if (!hasExpertiseAllocated(player))
        {
            return null;
        }
        String[] skillList = getSkillListingForPlayer(player);
        Vector resizeExpertiseAllocated = new Vector();
        resizeExpertiseAllocated.setSize(0);
        if (skillList != null)
        {
            for (int i = 0; i < skillList.length; ++i)
            {
                if (skillList[i].startsWith("expertise_"))
                {
                    resizeExpertiseAllocated = utils.addElement(resizeExpertiseAllocated, skillList[i]);
                }
            }
        }
        String[] finalExpertiseAllocated = new String[resizeExpertiseAllocated.size()];
        resizeExpertiseAllocated.toArray(finalExpertiseAllocated);
        return finalExpertiseAllocated;
    }
    public static boolean isProfAllowedSkill(obj_id player, String skillName) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (skillName == null || skillName.equals(""))
        {
            return false;
        }
        String skillTemplate = getSkillTemplate(player);
        String profession = skill.getProfessionName(skillTemplate);
        boolean isTrader = false;
        if (profession.equals("trader"))
        {
            isTrader = true;
            if (skillTemplate.equals("trader_0a"))
            {
                profession = "trader_dom";
            }
            if (skillTemplate.equals("trader_0b"))
            {
                profession = "trader_struct";
            }
            if (skillTemplate.equals("trader_0c"))
            {
                profession = "trader_mun";
            }
            if (skillTemplate.equals("trader_0d"))
            {
                profession = "trader_eng";
            }
        }
        int row = dataTableSearchColumnForString(skillName, "NAME", skill.DATATABLE_EXPERTISE);
        if (row < 0)
        {
            return false;
        }
        String reqProf = dataTableGetString(skill.DATATABLE_EXPERTISE, row, "REQ_PROF");
        if (!reqProf.equals(profession))
        {
            if (reqProf.equals("trader") && isTrader)
            {
                return true;
            }
            else if (reqProf.equals("all"))
            {
                return true;
            }
            else 
            {
                CustomerServiceLog("SuspectedCheaterChannel: ", "DualProfessionCheat: Player " + getFirstName(player) + "(" + player + ") attempted to give themselves an expertise they cannot have.");
                CustomerServiceLog("SuspectedCheaterChannel: ", "DualProfessionCheat: Player " + getFirstName(player) + "(" + player + ") Their profession is " + profession + " and the skill was " + skillName + ".");
                return false;
            }
        }
        else 
        {
            return true;
        }
    }
}
