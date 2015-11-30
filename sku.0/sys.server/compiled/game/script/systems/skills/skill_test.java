package script.systems.skills;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.datatable;
import java.util.*;

public class skill_test extends script.base_script
{
    public skill_test()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "Skill Test Script Attached");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("reportskills"))
        {
            String[] skillnames = getAllSkills(self);
            reportSkillNames(self, skillnames);
        }
        if (text.equals("testall"))
        {
            debugServerConsoleMsg(self, "testing getExperiencePoints");
            int intXpOfType = getExperiencePoints(self, "rifle");
            debugServerConsoleMsg(self, "getSkillCommandsProvided");
            String[] stringCommandsProvided = getSkillCommandsProvided("basic_ranged_weapons");
            debugServerConsoleMsg(self, "getSkillListingForPlayer");
            String[] stringSkillListing = getSkillListingForPlayer(self);
            debugServerConsoleMsg(self, "getSkillPrerequisiteExperience");
            dictionary dictSkillPrerequisiteXp = getSkillPrerequisiteExperience("basic_ranged_weapons");
            debugServerConsoleMsg(self, "getSkillPrerequisiteSkills");
            String[] stringSkillPrerequisiteSkills = getSkillPrerequisiteSkills("basic_ranged_weapons");
            debugServerConsoleMsg(self, "etSkillPrerequisiteSpecies");
            dictionary dictSkillPrerequisiteSpecies = getSkillPrerequisiteSpecies("basic_ranged_weapons");
            debugServerConsoleMsg(self, "getSkillProfession");
            String stringProfession = getSkillProfession("basic_ranged_weapons");
            debugServerConsoleMsg(self, "getSkillStatisticModifiers");
            dictionary dictSkillStatMod = getSkillStatisticModifiers("basic_ranged_weapons");
            debugServerConsoleMsg(self, "getSkillTitleGranted");
            String stringSkillTitle = getSkillTitleGranted("basic_ranged_weapons");
            debugServerConsoleMsg(self, "grantCommand");
            boolean boolCommandGranted = grantCommand(self, "diveshot");
            debugServerConsoleMsg(self, "grantExperiencePoints");
            int intXpGranted = grantExperiencePoints(self, "rifle", 10);
            debugServerConsoleMsg(self, "grantSkill");
            boolean boolSkillGranted = grantSkill(self, "basic_ranged_weapons");
            debugServerConsoleMsg(self, "hasCommand");
            boolean boolHasCommand = hasCommand(self, "diveshot");
            debugServerConsoleMsg(self, "hasSkill");
            boolean boolHasSkill = hasSkill(self, "basic_ranged_weapons");
            debugServerConsoleMsg(self, "revokeCommand");
            revokeCommand(self, "diveshot");
            debugServerConsoleMsg(self, "revokeSkill");
            revokeSkill(self, "basic_ranged_weapons");
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public String[] getAllSkills(obj_id self) throws InterruptedException
    {
        String[] skillnames = getSkillListingForPlayer(self);
        int counter = skillnames.length;
        if (counter == 0)
        {
            LOG("skills", "skills.script: getSkillListingForPlayer returned an empty array.");
        }
        return skillnames;
    }
    public void reportSkillNames(obj_id self, String[] skillnames) throws InterruptedException
    {
        String skillname;
        for (int i = 0; i <= skillnames.length; i++)
        {
            skillname = skillnames[i];
            LOG("skills", "skill.skills: " + skillname);
        }
        return;
    }
}
