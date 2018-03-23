package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Arrays;
import script.library.utils;
import script.library.qa;
import script.library.sui;
import script.library.skill;
import script.library.respec;
import script.library.skill_template;
import script.library.gm;
import script.library.buff;
import script.library.performance;

public class qa_character extends script.base_script
{
    public qa_character()
    {
    }
    public static final String TEMPLATE_TABLE = "datatables/test/qa_setup_expertise.iff";
    public static final String EXOTIC_MOD_STRINGS = "datatables/crafting/reverse_engineering_mods.iff";
    public static final String[] QASETUP_MAIN_MENU = 
    {
        "Quick Buff",
        "Set Template",
        "Generate Equipment",
        "Write Template to Disk"
    };
    public static final String QASETUP_TITLE = "QA Setup";
    public static final String QASETUP_PROMPT = "Choose the tool you want to use";
    public static final String[] TOOL_OPTIONS = 
    {
        "Quick Setup",
        "Quick Buff",
        "Set Class and Template",
        "Generate Equipment"
    };
    public static final String[] MOD_TYPES = 
    {
        "basic1",
        "basic2",
        "basic3",
        "exotic1",
        "exotic2",
        "exotic3"
    };
    public static final String[] EQUIPMENT_OPTIONS = 
    {
        "Prefered Mods",
        "All",
        "Weapon",
        "Armor",
        "Powerups",
        "Consumables"
    };
    public static final String[] CLASS_LIST = 
    {
        "bounty_hunter_1a",
        "commando_1a",
        "officer_1a",
        "force_sensitive_1a",
        "medic_1a",
        "spy_1a",
        "smuggler_1a",
        "trader_1a",
        "trader_1d",
        "trader_1b",
        "trader_1c"
    };
    public static final String[] BASIC_MOD_STRINGS = 
    {
        "precision_modified",
        "strength_modified",
        "agility_modified",
        "stamina_modified",
        "constitution_modified",
        "luck_modified",
        "camouflage",
        "combat_block_value"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 10) {
            detachScript(self, "test.qa_character");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        debugConsoleMsg(self, text);
        java.util.StringTokenizer st = new java.util.StringTokenizer(text);
        obj_id pInv = utils.getInventoryContainer(self);
        if (text.equals("qaCharacter"))
        {
            sendSystemMessageTestingOnly(self, "start menu wooo hoo.");
            qaToolMainMenu(self);
        }
        if (text.equals("writeTemp"))
        {
            writeTemplateFile();
        }
        return SCRIPT_CONTINUE;
    }
    public void qaToolMainMenu(obj_id self) throws InterruptedException
    {
        qa.refreshMenu(self, QASETUP_PROMPT, QASETUP_TITLE, QASETUP_MAIN_MENU, "handleMainMenu", true, "qasetup.pid", "qasetup.qasetup");
    }
    public int handleMainMenu(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "qasetup.pid"))
        {
            qa.checkParams(params, "qasetup");
            int idx = sui.getListboxSelectedRow(params);
            int btn = sui.getIntButtonPressed(params);
            String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "qasetup.qasetup");
            if (btn == sui.BP_CANCEL)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                String previousSelection = previousMainMenuArray[idx];
                if (previousSelection.equals("Quick Buff"))
                {
                    quickBuff(self);
                }
                if (previousSelection.equals("Set Template"))
                {
                    String[] templateList = dataTableGetColumnNames(TEMPLATE_TABLE);
                    qa.refreshMenu(self, "Select the profession you wish to use.", "Set Profession", templateList, "handleSetProfession", true, "profession.pid", "qasetup.qasetup");
                }
                if (previousSelection.equals("Generate Equipment"))
                {
                    qa.refreshMenu(self, "Select the equipment type you want to generate.", "Generate Equipment", EQUIPMENT_OPTIONS, "handleEquipmentMenu", true, "equipment.pid", "equipment.qasetup");
                    return SCRIPT_CONTINUE;
                }
                if (previousSelection.equals("Write Template to Disk"))
                {
                    writeTemplateFile();
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void quickBuff(obj_id self) throws InterruptedException
    {
        String prof = getSkillTemplate(self);
        obj_id recipientId = self;
        obj_id bufferId = recipientId;
        buff.applyBuff(recipientId, "buildabuff_inspiration", 3600);
        addSkillModModifier(self, "buildabuff_expertise_action_all", "expertise_action_all", 9, 3600, false, true);
        addSkillModModifier(self, "buildabuff_expertise_glancing_blow_all", "expertise_glancing_blow_all", 7, 3600, false, true);
        addSkillModModifier(self, "buildabuff_expertise_innate_protection_energy", "expertise_innate_protection_energy", 3750, 3600, false, true);
        addSkillModModifier(self, "buildabuff_expertise_innate_protection_kinetic", "expertise_innate_protection_kinetic", 3750, 3600, false, true);
        messageTo(self, "recalcPools", null, .25f, false);
        messageTo(self, "recalcArmor", null, .25f, false);
        buff.applyBuff((recipientId), "me_buff_health_2", 3600);
        buff.applyBuff((recipientId), "me_buff_action_3", 3600);
        buff.applyBuff((recipientId), "me_buff_strength_3", 3600);
        buff.applyBuff((recipientId), "me_buff_agility_3", 3600);
        buff.applyBuff((recipientId), "me_buff_precision_3", 3600);
        buff.applyBuff((recipientId), "drink_flameout", 3600);
        qaToolMainMenu(self);
    }
    public int handleSetProfession(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "profession.pid"))
        {
            qa.checkParams(params, "profession");
            int idx = sui.getListboxSelectedRow(params);
            int btn = sui.getIntButtonPressed(params);
            String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "qasetup.qasetup");
            if (btn == sui.BP_CANCEL)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                String previousSelection = previousMainMenuArray[idx];
                String[] selectedTemplate = dataTableGetStringColumn(TEMPLATE_TABLE, previousSelection);
                String selectedProf = selectedTemplate[0];
                for (int i = 1; i < selectedTemplate.length; i++)
                {
                    int n = 0;
                    if (!selectedTemplate[i].equals("null"))
                    {
                        sendSystemMessageTestingOnly(self, "selectedTemp: " + selectedTemplate[i]);
                    }
                }
                sendSystemMessageTestingOnly(self, "Class template: " + selectedProf);
                if (!previousSelection.equals("null"))
                {
                    setTemplate(self, selectedTemplate, selectedProf);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void handleProfessionLevelToNinety(obj_id player, String roadmap) throws InterruptedException
    {
        revokeAllSkills(player);
        int currentCombatXp = getExperiencePoints(player, "combat_general");
        grantExperiencePoints(player, "combat_general", -currentCombatXp);
        skill.recalcPlayerPools(player, true);
        setSkillTemplate(player, roadmap);
        respec.autoLevelPlayer(player, 90, false);
        utils.fullExpertiseReset(player, false);
        skill.setPlayerStatsForLevel(player, 90);
    }
    public void revokeAllSkills(obj_id player) throws InterruptedException
    {
        String[] skillList = getSkillListingForPlayer(player);
        int attempts = skillList.length;
        if ((skillList != null) && (skillList.length != 0))
        {
            while (skillList.length > 0 && attempts > 0)
            {
                for (int i = 0; i < skillList.length; i++)
                {
                    String skillName = skillList[i];
                    if (!skillName.startsWith("species_") && !skillName.startsWith("social_language_") && !skillName.startsWith("utility_") && !skillName.startsWith("common_") && !skillName.startsWith("demo_") && !skillName.startsWith("force_title_") && !skillName.startsWith("force_sensitive_") && !skillName.startsWith("combat_melee_basic") && !skillName.startsWith("pilot_") && !skillName.startsWith("internal_expertise_") && !skillName.startsWith("combat_ranged_weapon_basic"))
                    {
                        skill.revokeSkillSilent(player, skillName);
                    }
                }
                skillList = getSkillListingForPlayer(player);
                --attempts;
            }
        }
        skill.recalcPlayerPools(player, true);
    }
    public void setTemplate(obj_id self, String[] skillList, String baseClass) throws InterruptedException
    {
        handleProfessionLevelToNinety(self, baseClass);
        utils.fullExpertiseReset(self, false);
        for (int j = 1; j < skillList.length; j++)
        {
            int exotic = 1;
            int basic = 1;
            String expertiseSkill = skillList[j];
            if (expertiseSkill.startsWith("expertise_"))
            {
                skill.grantSkillToPlayer(self, expertiseSkill);
            }
            if (expertiseSkill.startsWith("mod_exotic") && exotic <= 3)
            {
                String[] modString = split(expertiseSkill, ':');
                String scriptVarName = "qacharacter.exotic" + exotic;
                utils.setScriptVar(self, scriptVarName, modString[1]);
                exotic++;
            }
            if (expertiseSkill.startsWith("mod_basic") && basic <= 3)
            {
                String[] modString = split(expertiseSkill, ':');
                String scriptVarName = "qacharacter.basic" + basic;
                utils.setScriptVar(self, scriptVarName, modString[1]);
                basic++;
            }
        }
        qaToolMainMenu(self);
    }
    public void writeTemplateFile() throws InterruptedException
    {
        obj_id self = getSelf();
        sendSystemMessageTestingOnly(self, "In write file method");
        String[] pSkill = getSkillListingForPlayer(self);
        String prof = getSkillTemplate(self);
        String template = "Template Name\n" + "s\n" + prof;
        String temp;
        int attempts = pSkill.length;
        if ((pSkill != null) && (pSkill.length != 0))
        {
            while (pSkill.length > 0 && attempts > 0)
            {
                for (int i = 0; i < pSkill.length; i++)
                {
                    String skillName = pSkill[i];
                    if (skillName.startsWith("expertise_"))
                    {
                        temp = pSkill[i];
                        sendSystemMessageTestingOnly(self, "Adding line: " + pSkill[i]);
                        template += "\n" + temp;
                    }
                    --attempts;
                }
            }
        }
        saveTextOnClient(self, "qaSetup_" + getServerFrame() + ".tab", template);
        qaToolMainMenu(self);
    }
    public void startModSelection() throws InterruptedException
    {
        obj_id self = getSelf();
        if (!utils.hasScriptVar(self, "modSelectionInProgress"))
        {
            boolean rm = removeMods();
            utils.setScriptVar(self, "modSelectionInProgress", 1);
            utils.setScriptVar(self, "modBasic", 1);
            utils.setScriptVar(self, "modExotic", 1);
        }
        String[] exoticModList = getExoticMods();
        int basicCounter = utils.getIntScriptVar(self, "modBasic");
        int exoticCounter = utils.getIntScriptVar(self, "modExotic");
        if (basicCounter <= 3)
        {
            qa.refreshMenu(self, "Select basic mod number " + basicCounter + " of 3", "Select Mods", BASIC_MOD_STRINGS, "handleMod", true, "profession.pid", "qasetup.qasetup");
        }
        if (basicCounter > 3 && exoticCounter <= 3)
        {
            qa.refreshMenu(self, "Select exotic mod number " + exoticCounter + " of 3", "Select Mods", exoticModList, "handleMod", true, "profession.pid", "qasetup.qasetup");
        }
        utils.removeScriptVar(self, "modSelectionInProgress");
    }
    public boolean removeMods() throws InterruptedException
    {
        obj_id self = getSelf();
        for (int j = 1; j < MOD_TYPES.length; j++)
        {
            if (utils.hasScriptVar(self, MOD_TYPES[j]))
            {
                utils.removeScriptVar(self, MOD_TYPES[j]);
            }
        }
        if (utils.hasScriptVar(self, "modBasic"))
        {
            utils.removeScriptVar(self, "modBasic");
        }
        if (utils.hasScriptVar(self, "modExotic"))
        {
            utils.removeScriptVar(self, "modExotic");
        }
        return true;
    }
    public int handleMod(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "selectMod.pid"))
        {
            qa.checkParams(params, "selectMod");
            int idx = sui.getListboxSelectedRow(params);
            int btn = sui.getIntButtonPressed(params);
            String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "selectMod.qasetup");
            String modChoice = previousMainMenuArray[idx];
            int basicCounter = utils.getIntScriptVar(self, "modBasic");
            int exoticCounter = utils.getIntScriptVar(self, "modExotic");
            String type = "basic";
            String modType = "modBasic";
            if (basicCounter > 3)
            {
                type = "exotic";
                modType = "modExotic";
            }
            int modNumber = utils.getIntScriptVar(self, modType);
            utils.setScriptVar(self, type + modNumber, 1);
            modNumber++;
            utils.setScriptVar(self, modType, modNumber);
            startModSelection();
        }
        return SCRIPT_CONTINUE;
    }
    public String[] getExoticMods() throws InterruptedException
    {
        String[] skillMods = dataTableGetStringColumn(EXOTIC_MOD_STRINGS, "name");
        for (int i = 0; i < skillMods.length; i++)
        {
            skillMods[i] = utils.packStringId(new string_id("stat_n", skillMods[i]));
        }
        return skillMods;
    }
}
