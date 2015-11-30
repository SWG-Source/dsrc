package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_version;
import script.library.skill;
import script.library.sui;
import script.library.utils;
import script.library.gm;
import script.library.money;
import script.library.jedi;
import script.library.space_flags;

public class player_jedi_conversion extends script.base_script
{
    public player_jedi_conversion()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        convertOldJedi(self);
        return SCRIPT_CONTINUE;
    }
    public void convertOldJedi(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "jedi.converted"))
        {
            detachScript(self, "player.player_jedi_conversion");
            return;
        }
        setObjVar(self, "combatLevel", 80);
        detachScript(self, "player.player_jedi_conversion");
        return;
    }
    public int forceSensitiveSui(obj_id self, obj_id player, int test) throws InterruptedException
    {
        String datatable = "datatables/jedi/force_sensitive_skills.iff";
        int forceSensitivePoints = getIntObjVar(self, "jedi.forceSensitivePoints");
        if (forceSensitivePoints <= 0)
        {
            forceSensitivePoints = 24;
            setObjVar(self, "jedi.forceSensitivePoints", forceSensitivePoints);
        }
        int pid = createSUIPage(sui.SUI_LISTBOX, self, self, "forceSensitiveSkillSelection");
        String title = "Jedi Conversion";
        String text = "You need to choose " + (forceSensitivePoints / 4) + " more Force Sensitive Skill lines. Information about all skills can be found in the skill browser (default key : CTRL-S).";
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, text);
        sui.listboxButtonSetup(pid, sui.OK_CANCEL_REFRESH);
        setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "TAKE SKILL");
        setSUIProperty(pid, sui.LISTBOX_BTN_OTHER, sui.PROP_TEXT, "UNDO LAST CHOICE");
        setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_VISIBLE, "false");
        if (!utils.hasScriptVar(self, "last_force_skill_picked"))
        {
            setSUIProperty(pid, sui.LISTBOX_BTN_OTHER, "enabled", "false");
        }
        clearSUIDataSource(pid, sui.LISTBOX_DATASOURCE);
        Vector skillList = new Vector();
        skillList.setSize(0);
        String[] nameList = dataTableGetStringColumnNoDefaults(datatable, "force_sensitive_skills");
        utils.setScriptVar(self, "fullNameList", nameList);
        if (nameList != null && nameList.length != 0)
        {
            for (int i = 0; i < nameList.length; i++)
            {
                String skill = dataTableGetString(datatable, i, "force_sensitive_skills");
                string_id skillName = new string_id("skl_n", skill);
                String skillDisplay = getString(skillName);
                String first = dataTableGetString(datatable, i, "skill_one");
                String second = dataTableGetString(datatable, i, "skill_two");
                String third = dataTableGetString(datatable, i, "skill_three");
                String fourth = dataTableGetString(datatable, i, "skill_four");
                if (!hasSkill(player, first) && !hasSkill(player, second) && !hasSkill(player, third) && !hasSkill(player, fourth))
                {
                    addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + i);
                    setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + i, sui.PROP_TEXT, "@skl_n:" + nameList[i]);
                    skillList = utils.addElement(skillList, skill);
                }
                else 
                {
                }
            }
        }
        utils.setScriptVar(self, "skill_list", skillList);
        subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
        sui.showSUIPage(pid);
        setObjVar(self, "jedi.conversionSui", pid);
        return pid;
    }
    public int forceSensitiveSkillSelection(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int test = 2;
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        switch (bp)
        {
            case sui.BP_REVERT:
            revokeForceSkill(player);
            return SCRIPT_CONTINUE;
            case sui.BP_OK:
            addSkillBox(self, player, idx);
            return SCRIPT_CONTINUE;
            case sui.BP_CANCEL:
            forceSensitiveSui(self, self, 1);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void addSkillBox(obj_id self, obj_id player, int idx) throws InterruptedException
    {
        String datatable = "datatables/jedi/force_sensitive_skills.iff";
        if (idx < 0)
        {
            forceSensitiveSui(player, player, 1);
            return;
        }
        else 
        {
            String[] skillNames = utils.getStringArrayScriptVar(self, "skill_list");
            String thisSkill = skillNames[idx];
            int points = getIntObjVar(player, "jedi.currentPoints");
            if (points < 4)
            {
                sendSystemMessage(player, "You can't afford any force skills right now.", null);
                return;
            }
            String novice = dataTableGetString(datatable, thisSkill, "skill_novice");
            String first = dataTableGetString(datatable, thisSkill, "skill_one");
            String second = dataTableGetString(datatable, thisSkill, "skill_two");
            String third = dataTableGetString(datatable, thisSkill, "skill_three");
            String fourth = dataTableGetString(datatable, thisSkill, "skill_four");
            if (hasSkill(player, first) && hasSkill(player, second) && hasSkill(player, third) && hasSkill(player, fourth))
            {
                sendSystemMessage(player, "You already have that skill.", null);
                forceSensitiveSui(player, player, 1);
                return;
            }
            if (!hasSkill(player, novice))
            {
                setObjVar(self, "jedi.grantingSkill", 1);
                grantSkill(player, novice);
                removeObjVar(self, "jedi.grantingSkill");
                CustomerServiceLog("JediConversion", "%TU has selected " + novice + " skill.", player);
            }
            if (!hasSkill(player, first))
            {
                setObjVar(self, "jedi.grantingSkill", 1);
                grantSkill(player, first);
                removeObjVar(self, "jedi.grantingSkill");
                CustomerServiceLog("JediConversion", "%TU has selected " + first + " skill.", player);
            }
            if (!hasSkill(player, second))
            {
                setObjVar(self, "jedi.grantingSkill", 1);
                grantSkill(player, second);
                removeObjVar(self, "jedi.grantingSkill");
                CustomerServiceLog("JediConversion", "%TU has selected " + second + " skill.", player);
            }
            if (!hasSkill(player, third))
            {
                setObjVar(self, "jedi.grantingSkill", 1);
                grantSkill(player, third);
                removeObjVar(self, "jedi.grantingSkill");
                CustomerServiceLog("JediConversion", "%TU has selected " + third + " skill.", player);
            }
            if (!hasSkill(player, fourth))
            {
                setObjVar(self, "jedi.grantingSkill", 1);
                grantSkill(player, fourth);
                removeObjVar(self, "jedi.grantingSkill");
                CustomerServiceLog("JediConversion", "%TU has selected " + fourth + " skill.", player);
                utils.setScriptVar(player, "last_force_skill_picked", thisSkill);
            }
            int needs = getIntObjVar(self, "jedi.forceSensitivePoints");
            if (points != 0)
            {
                points = points - 4;
                setObjVar(player, "jedi.currentPoints", points);
                needs = needs - 4;
                setObjVar(self, "jedi.forceSensitivePoints", needs);
            }
            if (needs != 0)
            {
                forceSensitiveSui(player, player, 1);
            }
            else 
            {
                setJediState(self, JEDI_STATE_JEDI);
                setMaxForcePower(self, 10);
                setForcePowerRegenRate(self, 1);
                setObjVar(self, "jedi.grantingSkill", 1);
                grantSkill(self, "force_title_jedi_rank_01");
                removeObjVar(self, "jedi.grantingSkill");
                setMaxForcePower(self, 10);
                setForcePowerRegenRate(self, 1);
                setObjVar(self, "jedi.grantingSkill", 1);
                grantSkill(self, "force_title_jedi_rank_02");
                removeObjVar(self, "jedi.grantingSkill");
                jediSui(player, player, 1);
            }
        }
        return;
    }
    public void checkForMasterBox(obj_id player) throws InterruptedException
    {
        obj_id self = player;
        if (hasSkill(player, "force_sensitive_combat_prowess_ranged_accuracy_04") && hasSkill(player, "force_sensitive_combat_prowess_ranged_speed_04") && hasSkill(player, "force_sensitive_combat_prowess_melee_accuracy_04") && hasSkill(player, "force_sensitive_combat_prowess_melee_speed_04"))
        {
            setObjVar(self, "jedi.grantingSkill", 1);
            grantSkill(player, "force_sensitive_combat_prowess_master");
            removeObjVar(self, "jedi.grantingSkill");
            CustomerServiceLog("JediConversion", "%TU was granted force_sensitive_combat_prowess_master", self);
        }
        if (hasSkill(player, "force_sensitive_enhanced_reflexes_ranged_defense_04") && hasSkill(player, "force_sensitive_enhanced_reflexes_melee_defense_04") && hasSkill(player, "force_sensitive_enhanced_reflexes_vehicle_control_04") && hasSkill(player, "force_sensitive_enhanced_reflexes_survival_04"))
        {
            setObjVar(self, "jedi.grantingSkill", 1);
            grantSkill(player, "force_sensitive_enhanced_reflexes_master");
            removeObjVar(self, "jedi.grantingSkill");
            CustomerServiceLog("JediConversion", "%TU was granted force_sensitive_enhanced_reflexes_master", self);
        }
        if (hasSkill(player, "force_sensitive_crafting_mastery_experimentation_04") && hasSkill(player, "force_sensitive_crafting_mastery_assembly_04") && hasSkill(player, "force_sensitive_crafting_mastery_repair_04") && hasSkill(player, "force_sensitive_crafting_mastery_technique_04"))
        {
            setObjVar(self, "jedi.grantingSkill", 1);
            grantSkill(player, "force_sensitive_crafting_mastery_master");
            removeObjVar(self, "jedi.grantingSkill");
            CustomerServiceLog("JediConversion", "%TU was granted force_sensitive_crafting_mastery_master", self);
        }
        if (hasSkill(player, "force_sensitive_heightened_senses_healing_04") && hasSkill(player, "force_sensitive_heightened_senses_surveying_04") && hasSkill(player, "force_sensitive_heightened_senses_persuasion_04") && hasSkill(player, "force_sensitive_heightened_senses_luck_04"))
        {
            setObjVar(self, "jedi.grantingSkill", 1);
            grantSkill(player, "force_sensitive_heightened_senses_master");
            removeObjVar(self, "jedi.grantingSkill");
            CustomerServiceLog("JediConversion", "%TU was granted force_sensitive_heightened_senses_master", self);
        }
        return;
    }
    public int jediSui(obj_id self, obj_id player, int test) throws InterruptedException
    {
        String datatable = "datatables/jedi/jedi_conversion.iff";
        int points = getIntObjVar(self, "jedi.currentPoints");
        int pid = createSUIPage(sui.SUI_LISTBOX, self, self, "jediSkillSelection");
        String text = "You are allowed to spend " + points + " more skill points on Jedi Skills.  If you revoke skills after purchase you will not get those skill points back. Information about all skills can be found in the skill browser (default key : CTRL-S).";
        String title = "Jedi Conversion";
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, text);
        sui.listboxButtonSetup(pid, sui.OK_CANCEL_REFRESH);
        setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "TAKE SKILL");
        setSUIProperty(pid, sui.LISTBOX_BTN_OTHER, sui.PROP_TEXT, "UNDO LAST CHOICE");
        setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_VISIBLE, "false");
        if (!utils.hasScriptVar(self, "last_jedi_skill_picked"))
        {
            setSUIProperty(pid, sui.LISTBOX_BTN_OTHER, "enabled", "false");
        }
        clearSUIDataSource(pid, sui.LISTBOX_DATASOURCE);
        Vector skillList = new Vector();
        skillList.setSize(0);
        String[] nameList = dataTableGetStringColumnNoDefaults(datatable, "new_skill_name");
        utils.setScriptVar(self, "fullNameList", nameList);
        if (nameList != null && nameList.length != 0)
        {
            for (int i = 0; i < nameList.length; i++)
            {
                String skill = dataTableGetString(datatable, i, "new_skill_name");
                string_id skillName = new string_id("skl_n", skill);
                String skillDisplay = getString(skillName);
                int cost = 0;
                boolean affordable = true;
                if (cost > points)
                {
                    affordable = false;
                }
                if (!hasSkill(player, skill))
                {
                    String requiredSkills = dataTableGetString("datatables/skill/skills.iff", skill, "SKILLS_REQUIRED");
                    int stringCheck = requiredSkills.indexOf(",");
                    if (stringCheck > -1)
                    {
                        String[] skills = split(requiredSkills, ',');
                        int requiredLength = skills.length;
                        int hadIt = 0;
                        if (requiredLength > 0)
                        {
                            for (int z = 0; z < requiredLength; z++)
                            {
                                String checkSkill = skills[z];
                                if (hasSkill(player, checkSkill))
                                {
                                    hadIt = hadIt + 1;
                                }
                            }
                            if (hadIt == requiredLength)
                            {
                                if (affordable == true)
                                {
                                    addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + i);
                                    setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + i, sui.PROP_TEXT, "@skl_n:" + nameList[i]);
                                    skillList = utils.addElement(skillList, skill);
                                }
                            }
                        }
                    }
                    else if (hasSkill(player, requiredSkills) || requiredSkills.equals("") || requiredSkills.equals("force_title_jedi_rank_02"))
                    {
                        if (affordable == true)
                        {
                            addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + i);
                            setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + i, sui.PROP_TEXT, "@skl_n:" + nameList[i]);
                            skillList = utils.addElement(skillList, skill);
                        }
                    }
                    else 
                    {
                    }
                }
                else 
                {
                }
            }
        }
        utils.setScriptVar(self, "skill_list", skillList);
        subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
        if (skillList.size() == 0)
        {
            int nonJedi = getIntObjVar(player, "jedi.nonJediPoints");
            nonJedi += points;
            setObjVar(player, "jedi.nonJediPoints", nonJedi);
            setObjVar(player, "jedi.currentPoints", 0);
            if (nonJedi > 15)
            {
                regularSkillSui(player, player, 1);
            }
            else 
            {
                completeTraining(player);
            }
            return 0;
        }
        sui.showSUIPage(pid);
        setObjVar(self, "jedi.conversionSui", pid);
        return pid;
    }
    public int jediSkillSelection(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int test = 2;
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        switch (bp)
        {
            case sui.BP_REVERT:
            revokeJediSkill(player);
            return SCRIPT_CONTINUE;
            case sui.BP_OK:
            addJediSkillBox(self, player, idx);
            return SCRIPT_CONTINUE;
            case sui.BP_CANCEL:
            jediSui(self, self, 1);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void addJediSkillBox(obj_id self, obj_id player, int idx) throws InterruptedException
    {
        String datatable = "datatables/jedi/jedi_conversion.iff";
        if (idx < 0)
        {
            jediSui(player, player, 1);
            return;
        }
        else 
        {
            String[] skillNames = utils.getStringArrayScriptVar(self, "skill_list");
            String thisSkill = skillNames[idx];
            int cost = 0;
            int points = getIntObjVar(player, "jedi.currentPoints");
            if (hasSkill(player, thisSkill))
            {
                sendSystemMessage(player, "You already have that skill.", null);
                jediSui(player, player, 1);
                return;
            }
            if (cost > points)
            {
                sendSystemMessage(player, "You can't afford that skill.", null);
                jediSui(player, player, 1);
                return;
            }
            boolean granted = false;
            if (!hasSkill(player, thisSkill))
            {
                if (skill.hasRequiredSkillsForSkillPurchase(player, thisSkill))
                {
                    setObjVar(self, "jedi.grantingSkill", 1);
                    granted = grantSkill(player, thisSkill);
                    removeObjVar(self, "jedi.grantingSkill");
                    utils.setScriptVar(player, "last_jedi_skill_picked", thisSkill);
                    CustomerServiceLog("JediConversion", "%TU selected " + thisSkill + " skill.", self);
                }
                else 
                {
                    sendSystemMessage(player, "You don't have the proper prerequisites for this skill.", null);
                }
            }
            if (points > 0 && granted == true)
            {
                points = points - cost;
                setObjVar(player, "jedi.currentPoints", points);
            }
            if (points > 0)
            {
                jediSui(player, player, 1);
            }
            else 
            {
                int nonJedi = getIntObjVar(player, "jedi.nonJediPoints");
                nonJedi += points;
                setObjVar(player, "jedi.nonJediPoints", nonJedi);
                setObjVar(player, "jedi.currentPoints", 0);
                if (nonJedi > 15)
                {
                    regularSkillSui(player, player, 1);
                }
                else 
                {
                    completeTraining(player);
                }
            }
        }
        return;
    }
    public int regularSkillSui(obj_id self, obj_id player, int test) throws InterruptedException
    {
        if (hasObjVar(self, "cancel"))
        {
            return 0;
        }
        String datatable = "datatables/skill/skills.iff";
        int points = getIntObjVar(self, "jedi.nonJediPoints");
        if (points > 0)
        {
            points = 0;
            setObjVar(player, "jedi.nonJediPoints", points);
        }
        int pid = createSUIPage(sui.SUI_LISTBOX, self, self, "regularSkillSelection");
        String text = "You need to spend all of your remaining skill points.  You currently have " + points + " skill points.  Information about all skills can be found in the skill browser (default key : CTRL-S). **WARNING** Do not revoke skills through the skills window.  Those points will not be added back in to the conversion window.  If you mistakenly choose a skill use the 'Undo Last Choice' button.";
        String title = "Skill Selection";
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, text);
        sui.listboxButtonSetup(pid, sui.OK_CANCEL_REFRESH);
        setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "TAKE SKILL");
        setSUIProperty(pid, sui.LISTBOX_BTN_OTHER, sui.PROP_TEXT, "UNDO LAST CHOICE");
        setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_VISIBLE, "false");
        if (!utils.hasScriptVar(self, "last_regular_skill_picked"))
        {
            setSUIProperty(pid, sui.LISTBOX_BTN_OTHER, "enabled", "false");
        }
        clearSUIDataSource(pid, sui.LISTBOX_DATASOURCE);
        Vector skillList = new Vector();
        skillList.setSize(0);
        String[] nameList = getLearnableSkillList(self);
        utils.setScriptVar(self, "fullNameList", nameList);
        if (nameList != null && nameList.length != 0)
        {
            for (int i = 0; i < nameList.length; i++)
            {
                String skill = nameList[i];
                addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + i);
                setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + i, sui.PROP_TEXT, "@skl_n:" + nameList[i]);
                skillList = utils.addElement(skillList, skill);
            }
        }
        utils.setScriptVar(self, "skill_list", skillList);
        subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
        if (skillList.size() == 0)
        {
            completeTraining(self);
            return 0;
        }
        sui.showSUIPage(pid);
        setObjVar(self, "jedi.conversionSui", pid);
        return pid;
    }
    public String[] getMasterSkillList(obj_id player) throws InterruptedException
    {
        String tblSkillList = "social_entertainer_master,outdoors_scout_master,science_medic_master,crafting_artisan_master,combat_brawler_master,combat_marksman_master,combat_rifleman_master,combat_pistol_master,combat_carbine_master,combat_unarmed_master,combat_1hsword_master,combat_2hsword_master,combat_polearm_master,social_dancer_master,social_musician_master,science_doctor_master,outdoors_ranger_master,outdoors_creaturehandler_master,outdoors_bio_engineer_master,crafting_armorsmith_master,crafting_weaponsmith_master,crafting_chef_master,crafting_tailor_master,crafting_architect_master,crafting_droidengineer_master,crafting_merchant_master,combat_smuggler_master,combat_bountyhunter_master,combat_commando_master,science_combatmedic_master,social_imagedesigner_master,outdoors_squadleader_master,social_politician_master";
        if (tblSkillList == null || tblSkillList.equals(""))
        {
            return null;
        }
        Vector masterSkillList = new Vector();
        masterSkillList.setSize(0);
        java.util.StringTokenizer skills = new java.util.StringTokenizer(toLower(tblSkillList), ",");
        while (skills.hasMoreTokens())
        {
            String skillName = skills.nextToken();
            if (skillName != null && !skillName.equals(""))
            {
                masterSkillList = utils.addElement(masterSkillList, skillName);
            }
        }
        String[] _masterSkillList = new String[0];
        if (masterSkillList != null)
        {
            _masterSkillList = new String[masterSkillList.size()];
            masterSkillList.toArray(_masterSkillList);
        }
        return _masterSkillList;
    }
    public String[] getTotalSkillList(obj_id player) throws InterruptedException
    {
        String[] masterSkillList = getMasterSkillList(player);
        if (masterSkillList == null)
        {
            return null;
        }
        Vector skillList = new Vector();
        skillList.setSize(0);
        for (int i = 0; i < masterSkillList.length; i++)
        {
            skillList = utils.addElement(skillList, masterSkillList[i]);
            String[] tmp = skill.getAllRequiredSkills(masterSkillList[i]);
            if ((tmp == null) || (tmp.length == 0))
            {
            }
            else 
            {
                for (int n = 0; n < tmp.length; n++)
                {
                    int pos = utils.getElementPositionInArray(skillList, tmp[n]);
                    if (pos == -1)
                    {
                        skillList = utils.addElement(skillList, tmp[n]);
                    }
                }
            }
        }
        String[] _skillList = new String[0];
        if (skillList != null)
        {
            _skillList = new String[skillList.size()];
            skillList.toArray(_skillList);
        }
        return _skillList;
    }
    public String[] getLearnableSkillList(obj_id player) throws InterruptedException
    {
        String[] learnableSkillList = getTotalSkillList(player);
        if (learnableSkillList == null)
        {
            return null;
        }
        Vector skillList = new Vector();
        skillList.setSize(0);
        for (int i = 0; i < learnableSkillList.length; i++)
        {
            if (hasSkill(player, learnableSkillList[i]))
            {
                continue;
            }
            if (skill.hasRequiredSkillsForSkillPurchase(player, learnableSkillList[i]))
            {
                int cost = 0;
                int pointsToSpend = getIntObjVar(player, "jedi.nonJediPoints");
                if (cost <= pointsToSpend)
                {
                    skillList = utils.addElement(skillList, learnableSkillList[i]);
                }
            }
        }
        String[] _skillList = new String[0];
        if (skillList != null)
        {
            _skillList = new String[skillList.size()];
            skillList.toArray(_skillList);
        }
        return _skillList;
    }
    public int regularSkillSelection(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int test = 2;
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        switch (bp)
        {
            case sui.BP_REVERT:
            revokeRegularSkill(player);
            return SCRIPT_CONTINUE;
            case sui.BP_OK:
            addRegularSkillBox(self, player, idx);
            return SCRIPT_CONTINUE;
            case sui.BP_CANCEL:
            regularSkillSui(self, self, 1);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void addRegularSkillBox(obj_id self, obj_id player, int idx) throws InterruptedException
    {
        String datatable = "datatables/skill/skills.iff";
        if (idx < 0)
        {
            regularSkillSui(player, player, 1);
            return;
        }
        else 
        {
            String[] skillNames = utils.getStringArrayScriptVar(self, "skill_list");
            String thisSkill = skillNames[idx];
            int costRow = dataTableSearchColumnForString(thisSkill, "NAME", datatable);
            int cost = 1;
            int points = getIntObjVar(player, "jedi.nonJediPoints");
            if (hasSkill(player, thisSkill))
            {
                sendSystemMessage(player, "You already have that skill.", null);
                regularSkillSui(player, player, 1);
                return;
            }
            if (cost > points || cost > 0)
            {
                sendSystemMessage(player, "You can't afford that skill.", null);
                regularSkillSui(player, player, 1);
                return;
            }
            boolean granted = false;
            if (!hasSkill(player, thisSkill))
            {
                if (skill.hasRequiredSkillsForSkillPurchase(player, thisSkill))
                {
                    setObjVar(self, "jedi.grantingSkill", 1);
                    granted = grantSkill(player, thisSkill);
                    removeObjVar(self, "jedi.grantingSkill");
                    utils.setScriptVar(player, "last_regular_skill_picked", thisSkill);
                    CustomerServiceLog("JediConversion", "%TU selected " + thisSkill + " skill.", self);
                }
                else 
                {
                    sendSystemMessage(player, "You don't have the proper prerequisites for this skill.", null);
                }
            }
            if (points != 0 && granted == true)
            {
                points = points - cost;
                setObjVar(player, "jedi.nonJediPoints", points);
            }
            if (points > 0)
            {
                regularSkillSui(player, player, 1);
            }
            else 
            {
                completeTraining(player);
            }
        }
        return;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        setSkillTemplate(self, "a");
        detachScript(self, "player.player_jedi_conversion");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setSkillTemplate(self, "a");
        detachScript(self, "player.player_jedi_conversion");
        return SCRIPT_CONTINUE;
    }
    public void restartConversionSUI() throws InterruptedException
    {
        obj_id self = getSelf();
        setObjVar(self, "jedi.usingSui", 1);
        int pid = getIntObjVar(self, "jedi.conversionSui");
        forceCloseSUIPage(pid);
        int needs = getIntObjVar(self, "jedi.forceSensitivePoints");
        int jedi = getIntObjVar(self, "jedi.currentPoints");
        int non = getIntObjVar(self, "jedi.nonJediPoints");
        if (needs > 0)
        {
            forceSensitiveSui(self, self, 1);
            return;
        }
        else if (jedi > 0)
        {
            jediSui(self, self, 1);
            return;
        }
        else if (non > 0)
        {
            regularSkillSui(self, self, 1);
            return;
        }
        else 
        {
            completeTraining(self);
        }
        return;
    }
    public void revokeSkills(obj_id objPlayer) throws InterruptedException
    {
        String[] skillList = getSkillListingForPlayer(objPlayer);
        int count = 0;
        while (skillList != null && count < 15)
        {
            skillList = player_version.orderSkillListForRevoke(skillList);
            if ((skillList != null) && (skillList.length > 0))
            {
                for (int x = 0; x < skillList.length; x++)
                {
                    revokeSkill(objPlayer, skillList[x]);
                }
            }
            skillList = getSkillListingForPlayer(objPlayer);
            count++;
        }
        if (hasScript(objPlayer, "player.species_innate"))
        {
            detachScript(objPlayer, "player.species_innate");
            attachScript(objPlayer, "player.species_innate");
        }
    }
    public void revokeForceSkill(obj_id player) throws InterruptedException
    {
        obj_id self = player;
        String thisSkill = utils.getStringScriptVar(player, "last_force_skill_picked");
        if (thisSkill == null || thisSkill.equals(""))
        {
            return;
        }
        String datatable = "datatables/jedi/force_sensitive_skills.iff";
        String novice = dataTableGetString(datatable, thisSkill, "skill_novice");
        String first = dataTableGetString(datatable, thisSkill, "skill_one");
        String second = dataTableGetString(datatable, thisSkill, "skill_two");
        String third = dataTableGetString(datatable, thisSkill, "skill_three");
        String fourth = dataTableGetString(datatable, thisSkill, "skill_four");
        if (fourth.equals("force_sensitive_combat_prowess_ranged_accuracy_04") || fourth.equals("force_sensitive_combat_prowess_ranged_speed_04") || fourth.equals("force_sensitive_combat_prowess_melee_accuracy_04") || fourth.equals("force_sensitive_combat_prowess_melee_speed_04"))
        {
            if (hasSkill(player, "force_sensitive_combat_prowess_master"))
            {
                setObjVar(player, "jedi.revokeAllowed", 1);
            }
            revokeSkill(player, "force_sensitive_combat_prowess_master");
            removeObjVar(player, "jedi.revokeAllowed");
            CustomerServiceLog("JediConversion", "%TU revoked force_sensitive_combat_prowess_master.", self);
        }
        else if (fourth.equals("force_sensitive_enhanced_reflexes_ranged_defense_04") || fourth.equals("force_sensitive_enhanced_reflexes_melee_defense_04") || fourth.equals("force_sensitive_enhanced_reflexes_vehicle_control_04") || fourth.equals("force_sensitive_enhanced_reflexes_survival_04"))
        {
            if (hasSkill(player, "force_sensitive_enhanced_reflexes_master"))
            {
                setObjVar(player, "jedi.revokeAllowed", 1);
            }
            revokeSkill(player, "force_sensitive_enhanced_reflexes_master");
            removeObjVar(player, "jedi.revokeAllowed");
            CustomerServiceLog("JediConversion", "%TU revoked force_sensitive_enhanced_reflexes_master.", self);
        }
        else if (fourth.equals("force_sensitive_crafting_mastery_experimentation_04") || fourth.equals("force_sensitive_crafting_mastery_assembly_04") || fourth.equals("force_sensitive_crafting_mastery_repair_04") || fourth.equals("force_sensitive_crafting_mastery_technique_04"))
        {
            if (hasSkill(player, "force_sensitive_crafting_mastery_master"))
            {
                setObjVar(player, "jedi.revokeAllowed", 1);
            }
            revokeSkill(player, "force_sensitive_crafting_mastery_master");
            removeObjVar(player, "jedi.revokeAllowed");
            CustomerServiceLog("JediConversion", "%TU revoked force_sensitive_crafting_mastery_master.", self);
        }
        else if (fourth.equals("force_sensitive_heightened_senses_healing_04") || fourth.equals("force_sensitive_heightened_senses_surveying_04") || fourth.equals("force_sensitive_heightened_senses_persuasion_04") || fourth.equals("force_sensitive_heightened_senses_luck_04"))
        {
            if (hasSkill(player, "force_sensitive_heightened_senses_master"))
            {
                setObjVar(player, "jedi.revokeAllowed", 1);
            }
            revokeSkill(player, "force_sensitive_heightened_senses_master");
            removeObjVar(player, "jedi.revokeAllowed");
            CustomerServiceLog("JediConversion", "%TU revoked force_sensitive_heightened_senses_master.", self);
        }
        if (hasSkill(player, fourth))
        {
            setObjVar(player, "jedi.revokeAllowed", 1);
            revokeSkill(player, fourth);
            removeObjVar(player, "jedi.revokeAllowed");
            CustomerServiceLog("JediConversion", "%TU revoked " + fourth + " skill.", self);
        }
        if (hasSkill(player, third))
        {
            setObjVar(player, "jedi.revokeAllowed", 1);
            revokeSkill(player, third);
            removeObjVar(player, "jedi.revokeAllowed");
            CustomerServiceLog("JediConversion", "%TU revoked " + third + " skill.", self);
        }
        if (hasSkill(player, second))
        {
            setObjVar(player, "jedi.revokeAllowed", 1);
            revokeSkill(player, second);
            removeObjVar(player, "jedi.revokeAllowed");
            CustomerServiceLog("JediConversion", "%TU revoked " + second + " skill.", self);
        }
        if (hasSkill(player, first))
        {
            setObjVar(player, "jedi.revokeAllowed", 1);
            revokeSkill(player, first);
            removeObjVar(player, "jedi.revokeAllowed");
            CustomerServiceLog("JediConversion", "%TU revoked " + first + " skill.", self);
        }
        int points = getIntObjVar(player, "jedi.currentPoints");
        int needs = getIntObjVar(player, "jedi.forceSensitivePoints");
        points = points + 4;
        needs = needs + 4;
        setObjVar(player, "jedi.currentPoints", points);
        if (needs > 24)
        {
            needs = 24;
        }
        setObjVar(player, "jedi.forceSensitivePoints", needs);
        utils.removeScriptVar(player, "last_force_skill_picked");
        forceSensitiveSui(player, player, 1);
        return;
    }
    public void revokeJediSkill(obj_id player) throws InterruptedException
    {
        String thisSkill = utils.getStringScriptVar(player, "last_jedi_skill_picked");
        if (thisSkill == null || thisSkill.equals(""))
        {
            return;
        }
        String datatable = "datatables/jedi/jedi_conversion.iff";
        int cost = 0;
        int points = getIntObjVar(player, "jedi.currentPoints");
        if (hasSkill(player, thisSkill))
        {
            revokeSkill(player, thisSkill);
            CustomerServiceLog("JediConversion", "%TU revoked " + thisSkill + " skill.", player);
        }
        points = points + cost;
        setObjVar(player, "jedi.currentPoints", points);
        utils.removeScriptVar(player, "last_jedi_skill_picked");
        jediSui(player, player, 1);
        return;
    }
    public void revokeRegularSkill(obj_id player) throws InterruptedException
    {
        String thisSkill = utils.getStringScriptVar(player, "last_regular_skill_picked");
        if (thisSkill == null || thisSkill.equals(""))
        {
            return;
        }
        String datatable = "datatables/skill/skills.iff";
        int cost = 0;
        int points = getIntObjVar(player, "jedi.nonJediPoints");
        if (hasSkill(player, thisSkill))
        {
            revokeSkill(player, thisSkill);
            CustomerServiceLog("JediConversion", "%TU revoked " + thisSkill + " skill.", player);
        }
        points = points + cost;
        setObjVar(player, "jedi.nonJediPoints", points);
        utils.removeScriptVar(player, "last_regular_skill_picked");
        regularSkillSui(player, player, 1);
        return;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "jedi.usingSui"))
        {
            removeObjVar(self, "jedi.usingSui");
            utils.removeScriptVar(self, "last_force_skill_picked");
            utils.removeScriptVar(self, "last_jedi_skill_picked");
            utils.removeScriptVar(self, "last_regular_skill_picked");
        }
        if (hasObjVar(self, "jedi.currentPoints"))
        {
            removeObjVar(self, "jedi.currentPoints");
        }
        if (hasObjVar(self, "jedi.forceSensitivePoints"))
        {
            removeObjVar(self, "jedi.forceSensitivePoints");
        }
        if (hasObjVar(self, "jedi.nonJediPoints"))
        {
            removeObjVar(self, "jedi.nonJediPoints");
        }
        if (hasObjVar(self, "jedi.conversionSui"))
        {
            removeObjVar(self, "jedi.conversionSui");
        }
        return SCRIPT_CONTINUE;
    }
    public void completeTraining(obj_id self) throws InterruptedException
    {
        setObjVar(self, "jedi.converted", 1);
        sendSystemMessageTestingOnly(self, "You have completed your training...");
        removeObjVar(self, "jedi.currentPoints");
        removeObjVar(self, "jedi.forceSensitivePoints");
        removeObjVar(self, "jedi.nonJediPoints");
        attachScript(self, "player.jedi_conversion_robe");
        int oldJediXp = getIntObjVar(self, "jedi.currentXp");
        int curJediXp = getExperiencePoints(self, "jedi_general");
        if (curJediXp < oldJediXp)
        {
            grantExperiencePoints(self, "jedi_general", (oldJediXp - curJediXp));
        }
        obj_id inv = utils.getInventoryContainer(self);
        jedi.createColorCrystal(inv, rand(0, 11));
        detachScript(self, "player.player_jedi_conversion");
        CustomerServiceLog("JediConversion", "%TU Completed Jedi Conversion.", self);
        return;
    }
}
