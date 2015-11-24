package script.theme_park.new_player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.chat;
import script.library.create;
import script.library.factions;
import script.library.money;
import script.library.performance;
import script.library.pet_lib;
import script.library.prose;
import script.library.sui;
import script.library.utils;
import script.library.veteran_deprecated;
import script.library.xp;
import script.library.weapons;

public class new_player extends script.base_script
{
    public new_player()
    {
    }
    public static final int PERIODIC_CHECK_DELAY = 30 * 60;
    public static final int PERIODIC_CHECK_CAP = 3 * 60 * 60;
    public static final int ACCOUNT_AGE_CAP = 30;
    public static final String NEW_PLAYER_SCRIPT = "theme_park.new_player.new_player";
    public static final String HELPER_DROID_PCD_SCRIPT = "theme_park.new_player.helper_droid_pcd";
    public static final String DROID_VOCALIZE_01 = "sound/dro_astromech_converse.snd";
    public static final String DROID_VOCALIZE_02 = "sound/dro_astromech_whistle.snd";
    public static final String DROID_VOCALIZE_03 = "sound/dro_astromech_ok.snd";
    public static final String DATAPAD_HELPER_DROID_TEMPLATE = "object/intangible/pet/nhelper_droid.iff";
    public static final String HELPER_DROID_CREATURE_NAME = "nhelper_droid";
    public static final String SUI_STARTING_SIZE = "500,200";
    public static final string_id SID_BRAWLER = new string_id("new_player", "submenu_brawler");
    public static final string_id SID_MARKSMAN = new string_id("new_player", "submenu_marksman");
    public static final string_id SID_SCOUT = new string_id("new_player", "submenu_scout");
    public static final string_id SID_ARTISAN = new string_id("new_player", "submenu_artisan");
    public static final string_id SID_ENTERTAINER = new string_id("new_player", "submenu_entertainer");
    public static final string_id SID_MEDIC = new string_id("new_player", "submenu_medic");
    public static final string_id SID_CREDITS_REWARD = new string_id("new_player", "credits_reward");
    public static final String NOVICE_MARKSMAN = "combat_marksman_novice";
    public static final String NOVICE_BRAWLER = "combat_brawler_novice";
    public static final String NOVICE_MEDIC = "science_medic_novice";
    public static final String NOVICE_ARTISAN = "crafting_artisan_novice";
    public static final String NOVICE_ENTERTAINER = "social_entertainer_novice";
    public static final String NOVICE_SCOUT = "outdoors_scout_novice";
    public static final String BRAWLER = "brawler";
    public static final String MARKSMAN = "marksman";
    public static final String MEDIC = "medic";
    public static final String ARTISAN = "artisan";
    public static final String ENTERTAINER = "entertainer";
    public static final String SCOUT = "scout";
    public static final String NEW_PLAYER_OBJVAR_BASE = "new_player.";
    public static final String QUEST_OBJVAR_BASE = NEW_PLAYER_OBJVAR_BASE + "quest.";
    public static final String BRAWLER_QUEST_OBJVAR = QUEST_OBJVAR_BASE + BRAWLER;
    public static final String MARKSMAN_QUEST_OBJVAR = QUEST_OBJVAR_BASE + MARKSMAN;
    public static final String MEDIC_QUEST_OBJVAR = QUEST_OBJVAR_BASE + MEDIC;
    public static final String ARTISAN_QUEST_OBJVAR = QUEST_OBJVAR_BASE + ARTISAN;
    public static final String ENTERTAINER_QUEST_OBJVAR = QUEST_OBJVAR_BASE + ENTERTAINER;
    public static final String SCOUT_QUEST_OBJVAR = QUEST_OBJVAR_BASE + SCOUT;
    public static final String REWARD_OBJVAR_BASE = NEW_PLAYER_OBJVAR_BASE + "reward.";
    public static final String BRAWLER_REWARD_OBJVAR = REWARD_OBJVAR_BASE + BRAWLER;
    public static final String MARKSMAN_REWARD_OBJVAR = REWARD_OBJVAR_BASE + MARKSMAN;
    public static final String MEDIC_REWARD_OBJVAR = REWARD_OBJVAR_BASE + MEDIC;
    public static final String ARTISAN_REWARD_OBJVAR = REWARD_OBJVAR_BASE + ARTISAN;
    public static final String ENTERTAINER_REWARD_OBJVAR = REWARD_OBJVAR_BASE + ENTERTAINER;
    public static final String SCOUT_REWARD_OBJVAR = REWARD_OBJVAR_BASE + SCOUT;
    public static final String QUEST_COMPLETE_OBJVAR = NEW_PLAYER_OBJVAR_BASE + "questComplete";
    public static final String SPECIAL_REWARD_OBJVAR = NEW_PLAYER_OBJVAR_BASE + "specialReward";
    public static final String CURRENT_PROFESSION_OBJVAR = NEW_PLAYER_OBJVAR_BASE + "currentProfession";
    public static final String PROFESSION_ADDED_OBJVAR = NEW_PLAYER_OBJVAR_BASE + "newProfessionAdded";
    public static final String SCRVAR_PROGRESS_BRAWLER = NEW_PLAYER_OBJVAR_BASE + "brawlerProgess";
    public static final String SCRVAR_PROGRESS_MARKSMAN = NEW_PLAYER_OBJVAR_BASE + "marksmanProgess";
    public static final String SCRVAR_PROGRESS_SCOUT = NEW_PLAYER_OBJVAR_BASE + "scoutProgess";
    public static final String SCRVAR_PROGRESS_ENTERTAINER = NEW_PLAYER_OBJVAR_BASE + "entertainerProgess";
    public static final String SCRVAR_PROGRESS_MEDIC = NEW_PLAYER_OBJVAR_BASE + "medicProgess";
    public static final String SCRVAR_PROGRESS_ARTISAN = NEW_PLAYER_OBJVAR_BASE + "artisanProgess";
    public static final String LECEPANINE_DART = "object/tangible/scout/trap/trap_drowsy_dart.iff";
    public static final String CHEMICAL_SURVEY_DEVICE = "object/tangible/survey_tool/survey_tool_liquid.iff";
    public static final String CDEF_RIFLE = "object/weapon/ranged/rifle/rifle_cdef.iff";
    public static final String STIMPACK = "object/tangible/medicine/crafted/crafted_stimpack_sm_s1_a.iff";
    public static final String MINERAL_RESOURCE_CONTAINER = "object/resource_container/inorganic_minerals.iff";
    public static final String SURVIVAL_KNIFE_TEMPLATE = "object/weapon/melee/knife/knife_survival.iff";
    public static final String HEAVY_AXE_TEMPLATE = "object/weapon/melee/axe/axe_heavy_duty.iff";
    public static final String WOODEN_STAFF_TEMPLATE = "object/weapon/melee/polearm/lance_staff_wood_s1.iff";
    public static final String CDEF_PISTOL_TEMPLATE = "object/weapon/ranged/pistol/pistol_cdef.iff";
    public static final String CDEF_CARBINE_TEMPLATE = "object/weapon/ranged/carbine/carbine_cdef.iff";
    public static final String CDEF_RIFLE_TEMPLATE = "object/weapon/ranged/rifle/rifle_cdef.iff";
    public static final String FIREWORKS_TYPE_10 = "object/tangible/firework/firework_s10.iff";
    public static final String FIREWORKS_TYPE_18 = "object/tangible/firework/firework_s18.iff";
    public static final String CLONING_COUPON = "object/tangible/item/new_player/new_player_cloning_coupon.iff";
    public static final String TRAVEL_COUPON = "object/tangible/item/new_player/new_player_travel_coupon.iff";
    public static final String VEHICLE_COUPON = "object/tangible/item/new_player/new_player_vehicle_coupon.iff";
    public static final String GENERIC_CRAFTING_TOOL = "object/tangible/crafting/station/generic_tool.iff";
    public static final String MINERAL_SURVEY_DEVICE = "object/tangible/survey_tool/survey_tool_mineral.iff";
    public static final String SLITHERHORN = "object/tangible/instrument/slitherhorn.iff";
    public static final String NEW_PLAYER_LOG = "NEW_PLAYER_QUESTS";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (newPlayerQuestsEnabled() && !isJedi(self))
        {
            String custLogMsg = "New Player Quests: %TU : " + NEW_PLAYER_SCRIPT + " was attached.";
            CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
            utils.setScriptVar(self, "new_player.firstEverDroid", true);
            messageTo(self, "handleHelperDroidCheck", null, 1, false);
        }
        else 
        {
            detachScript(self, NEW_PLAYER_SCRIPT);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (newPlayerQuestsEnabled() && !isJedi(self))
        {
            if (!isSpaceScene())
            {
                utils.setScriptVar(self, "new_player.giveDroidGreeting", true);
                messageTo(self, "handleHelperDroidCheck", null, 9, false);
            }
        }
        else 
        {
            detachScript(self, NEW_PLAYER_SCRIPT);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        String custLogMsg = "New Player Script Detached: %TU has had the " + NEW_PLAYER_SCRIPT + "script detached.";
        CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
        if (hasObjVar(self, "new_player.temp"))
        {
            removeObjVar(self, "new_player.temp");
        }
        if (hasObjVar(self, QUEST_OBJVAR_BASE))
        {
            removeObjVar(self, QUEST_OBJVAR_BASE);
        }
        if (hasObjVar(self, "new_player.advice"))
        {
            removeObjVar(self, "new_player.advice");
        }
        if (hasObjVar(self, CURRENT_PROFESSION_OBJVAR))
        {
            removeObjVar(self, CURRENT_PROFESSION_OBJVAR);
        }
        if (!newPlayerQuestsEnabled())
        {
            obj_id helperDroidPcd = getHelperDroidPcd(self);
            destroyObject(helperDroidPcd);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSkillRevoked(obj_id self, String skillName) throws InterruptedException
    {
        if (skillName.endsWith("_novice"))
        {
            if (skillName.equals(NOVICE_MARKSMAN))
            {
                if (hasObjVar(self, MARKSMAN_QUEST_OBJVAR))
                {
                    removeObjVar(self, MARKSMAN_QUEST_OBJVAR);
                }
                if (hasObjVar(self, "new_player.temp.marksman"))
                {
                    removeObjVar(self, "new_player.temp.marksman");
                }
            }
            if (skillName.equals(NOVICE_BRAWLER))
            {
                if (hasObjVar(self, BRAWLER_QUEST_OBJVAR))
                {
                    removeObjVar(self, BRAWLER_QUEST_OBJVAR);
                }
                if (hasObjVar(self, "new_player.temp.brawler"))
                {
                    removeObjVar(self, "new_player.temp.brawler");
                }
            }
            if (skillName.equals(NOVICE_MEDIC))
            {
                if (hasObjVar(self, MEDIC_QUEST_OBJVAR))
                {
                    removeObjVar(self, MEDIC_QUEST_OBJVAR);
                }
                if (hasObjVar(self, "new_player.temp.medic"))
                {
                    removeObjVar(self, "new_player.temp.medic");
                }
            }
            if (skillName.equals(NOVICE_ARTISAN))
            {
                if (hasObjVar(self, ARTISAN_QUEST_OBJVAR))
                {
                    removeObjVar(self, ARTISAN_QUEST_OBJVAR);
                }
                if (hasObjVar(self, "new_player.temp.artisan"))
                {
                    removeObjVar(self, "new_player.temp.artisan");
                }
                if (hasObjVar(self, "new_player.temp.artisan_resource"))
                {
                    removeObjVar(self, "new_player.temp.artisan_resource");
                }
            }
            if (skillName.equals(NOVICE_ENTERTAINER))
            {
                if (hasObjVar(self, ENTERTAINER_QUEST_OBJVAR))
                {
                    removeObjVar(self, ENTERTAINER_QUEST_OBJVAR);
                }
                if (hasObjVar(self, "new_player.temp.entertainer_listeners"))
                {
                    removeObjVar(self, "new_player.temp.entertainer_listeners");
                }
                if (hasObjVar(self, "new_player.temp.entertainer_watchers"))
                {
                    removeObjVar(self, "new_player.temp.entertainer_watchers");
                }
            }
            if (skillName.equals(NOVICE_SCOUT))
            {
                if (hasObjVar(self, SCOUT_QUEST_OBJVAR))
                {
                    removeObjVar(self, SCOUT_QUEST_OBJVAR);
                }
                if (hasObjVar(self, "new_player.temp.scout"))
                {
                    removeObjVar(self, "new_player.temp.scout");
                }
                if (hasObjVar(self, "new_player.temp.scout_hide"))
                {
                    removeObjVar(self, "new_player.temp.scout_hide");
                }
                if (hasObjVar(self, "new_player.temp.scout_bone"))
                {
                    removeObjVar(self, "new_player.temp.scout_bone");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "new_player.berserkUsed"))
        {
            int berserkCheck = getState(self, STATE_BERSERK);
            if (berserkCheck <= 0)
            {
                utils.removeScriptVar(self, "new_player.berserkUsed");
            }
        }
        if (utils.hasScriptVar(self, "new_player.lungeUsed"))
        {
            utils.removeScriptVar(self, "new_player.lungeUsed");
        }
        if (utils.hasScriptVar(self, "new_player.centerOfBeingActivated"))
        {
            if (!utils.hasScriptVar(self, "COB_unarmed") && !utils.hasScriptVar(self, "COB_onehandmelee") && !utils.hasScriptVar(self, "COB_polearm") && !utils.hasScriptVar(self, "COB_twohandmelee"))
            {
                utils.removeScriptVar(self, "new_player.centerOfBeingUsed");
            }
        }
        if (utils.hasScriptVar(self, "new_player.overChargeShotUsed"))
        {
            utils.removeScriptVar(self, "new_player.overChargeShotUsed");
        }
        if (utils.hasScriptVar(self, "new_player.pointBlankSingleUsed"))
        {
            utils.removeScriptVar(self, "new_player.pointBlankSingleUsed");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        dictionary webster = new dictionary();
        if (hasObjVar(self, ENTERTAINER_QUEST_OBJVAR) && getIntObjVar(self, ENTERTAINER_QUEST_OBJVAR) == 5)
        {
            if (!isIdValid(sourceContainer) && isIdValid(destContainer))
            {
                obj_id building = getTopMostContainer(self);
                if (isIdValid(building))
                {
                    if (hasObjVar(building, "healing.canhealshock"))
                    {
                        String cantinaTemplate = getTemplateName(building);
                        if (!hasObjVar(building, "player_structure"))
                        {
                            int hotelCheck = cantinaTemplate.indexOf("hotel");
                            if (hotelCheck < 0)
                            {
                                webster.put("enteredCantina", 1);
                                messageTo(self, "handleNewPlayerEntertainerAction", webster, 1, false);
                            }
                        }
                        else 
                        {
                            int cantinaCheck = cantinaTemplate.indexOf("cantina");
                            if (cantinaCheck > -1)
                            {
                                webster.put("enteredCantina", 1);
                                messageTo(self, "handleNewPlayerEntertainerAction", webster, 1, false);
                            }
                            else 
                            {
                                int theaterCheck = cantinaTemplate.indexOf("theater");
                                if (theaterCheck > -1)
                                {
                                    webster.put("enteredCantina", 1);
                                    messageTo(self, "handleNewPlayerEntertainerAction", webster, 1, false);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (hasObjVar(self, MEDIC_QUEST_OBJVAR) && getIntObjVar(self, MEDIC_QUEST_OBJVAR) == 2)
        {
            if (!isIdValid(sourceContainer) && isIdValid(destContainer))
            {
                obj_id building = getTopMostContainer(self);
                if (isIdValid(building))
                {
                    if (hasObjVar(building, "healing.canhealwound"))
                    {
                        if (!hasObjVar(building, "player_structure"))
                        {
                            webster.put("enteredMedCenter", 1);
                            messageTo(self, "handleNewPlayerMedicAction", webster, 1, false);
                        }
                        else 
                        {
                            String hospitalTemplate = getTemplateName(building);
                            int hospitalCheck = hospitalTemplate.indexOf("hospital");
                            if (hospitalCheck > -1)
                            {
                                webster.put("enteredMedCenter", 1);
                                messageTo(self, "handleNewPlayerMedicAction", webster, 1, false);
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleHelperDroidCheck(obj_id self, dictionary params) throws InterruptedException
    {
        if (!newPlayerQuestsEnabled() || isJedi(self))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleSetupPeriodicProgressCheck", null, 2, false);
        if (hasObjVar(self, PROFESSION_ADDED_OBJVAR))
        {
            String noviceSkill = getStringObjVar(self, PROFESSION_ADDED_OBJVAR);
            if (noviceSkill != null && !noviceSkill.equals(""))
            {
                String profession = getQuestProfession(self, noviceSkill);
                setObjVar(self, CURRENT_PROFESSION_OBJVAR, profession);
                String questObjVar = QUEST_OBJVAR_BASE + profession;
                if (!hasObjVar(self, questObjVar))
                {
                    setObjVar(self, questObjVar, 0);
                }
                checkForProfessionEquipmentNeeded(self);
            }
        }
        obj_id helperDroidPcd = getHelperDroidPcd(self);
        if (!isIdValid(helperDroidPcd))
        {
            if (isOnTutorial(self))
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (hasObjVar(self, veteran_deprecated.OBJVAR_TIME_ACTIVE))
                {
                    int accountTime = getIntObjVar(self, veteran_deprecated.OBJVAR_TIME_ACTIVE);
                    if (accountTime < ACCOUNT_AGE_CAP)
                    {
                        if (!hasObjVar(self, "new_player.advice.showRefusedHelp"))
                        {
                            setObjVar(self, "new_player.advice.showRefusedHelp", 3);
                        }
                        if (!hasObjVar(self, "new_player.advice.callingStoredHelperDroid"))
                        {
                            setObjVar(self, "new_player.advice.callingStoredHelperDroid", 2);
                        }
                    }
                }
                messageTo(self, "setupHelperDroid", null, 10, false);
            }
        }
        else 
        {
            if (hasObjVar(self, veteran_deprecated.OBJVAR_TIME_ACTIVE))
            {
                int accountTime = getIntObjVar(self, veteran_deprecated.OBJVAR_TIME_ACTIVE);
                if (accountTime < ACCOUNT_AGE_CAP)
                {
                    if (callable.hasCDCallable(helperDroidPcd))
                    {
                        obj_id helperDroid = callable.getCDCallable(helperDroidPcd);
                        if (isIdValid(helperDroid))
                        {
                            messageTo(helperDroid, "handleDroidInitiateGreeting", null, 1, false);
                        }
                    }
                    else 
                    {
                        unpackHelperDroid(self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int setupHelperDroid(obj_id self, dictionary params) throws InterruptedException
    {
        if (!newPlayerQuestsEnabled() || isJedi(self))
        {
            return SCRIPT_CONTINUE;
        }
        String creatureName = HELPER_DROID_CREATURE_NAME;
        int petType = pet_lib.PET_TYPE_DROID;
        obj_id datapad = utils.getPlayerDatapad(self);
        if (!isIdValid(datapad))
        {
            if (isGod(self))
            {
                sendSystemMessageTestingOnly(self, "GODMODE MSG: theme_park.new_player.new_player : Player datapad is invalid.");
            }
            return SCRIPT_CONTINUE;
        }
        String controlTemplate = DATAPAD_HELPER_DROID_TEMPLATE;
        obj_id petControlDevice = createObject(controlTemplate, datapad, "");
        if (!isIdValid(petControlDevice))
        {
            sendSystemMessage(self, pet_lib.SID_SYS_TOO_MANY_STORED_PETS);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            String custLogMsg = "New Player Quests: %TU has been given a helper droid.";
            CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
            string_id nameId = new string_id(create.CREATURE_NAME_FILE, creatureName);
            String typeName = getString(nameId);
            String droidName = getDroidName();
            String fullName = droidName + " (" + typeName + ")";
            setName(petControlDevice, fullName);
            setObjVar(petControlDevice, "pet.creatureName", creatureName);
            setObjVar(petControlDevice, "pet.crafted", true);
            setObjVar(petControlDevice, "ai.pet.type", petType);
            setObjVar(petControlDevice, "pet.nonCombatDroid", 1);
            attachScript(petControlDevice, "ai.pet_control_device");
            attachScript(petControlDevice, HELPER_DROID_PCD_SCRIPT);
            for (int attribNum = 0; attribNum < NUM_ATTRIBUTES; attribNum++)
            {
                setObjVar(petControlDevice, "creature_attribs." + create.MAXATTRIBNAMES[attribNum], 500);
            }
        }
        if (!callable.hasCallable(self, callable.CALLABLE_TYPE_COMBAT_PET))
        {
            if (isSpaceScene())
            {
                return SCRIPT_CONTINUE;
            }
            if (!utils.hasScriptVar(self, "new_player.questMessage"))
            {
                if (hasObjVar(self, veteran_deprecated.OBJVAR_TIME_ACTIVE))
                {
                    int accountTime = getIntObjVar(self, veteran_deprecated.OBJVAR_TIME_ACTIVE);
                    if (accountTime >= ACCOUNT_AGE_CAP)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            else 
            {
                utils.removeScriptVar(self, "new_player.questMessage");
            }
            location where = getLocation(self);
            where.x = where.x + 2;
            where.z = where.z - 2;
            obj_id pet = create.object(creatureName, where);
            if (!isIdValid(pet))
            {
                if (isGod(self))
                {
                    sendSystemMessageTestingOnly(self, "GODMODE MSG: theme_park.new_player.new_player : Tried to create droid but failed.");
                }
                return SCRIPT_CONTINUE;
            }
            pet_lib.setCraftedPetStatsByGrowth(petControlDevice, pet, 10);
            callable.setCallableLinks(self, petControlDevice, pet);
            pet_lib.makePet(pet, self);
            dictionary droidInfo = new dictionary();
            droidInfo.put("pet", pet);
            droidInfo.put("master", self);
            messageTo(pet, "handleAddMaster", droidInfo, 1, false);
            String name = getAssignedName(petControlDevice);
            setName(pet, name);
            location destLoc = getLocation(self);
            destLoc.z = destLoc.z + 2;
            pathTo(pet, destLoc);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleNewProfessionGreetingResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, CURRENT_PROFESSION_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, PROFESSION_ADDED_OBJVAR))
        {
            removeObjVar(self, PROFESSION_ADDED_OBJVAR);
        }
        int bp = sui.getIntButtonPressed(params);
        String droidName = getDroidName(self);
        String profession = getStringObjVar(self, CURRENT_PROFESSION_OBJVAR);
        String custLogMsg = "New Player Quests: %TU has added a new profession and its new player quests: " + profession;
        CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
        switch (bp)
        {
            case sui.BP_OK:
            setObjVar(self, QUEST_OBJVAR_BASE + profession, 1);
            if (profession.equals(BRAWLER))
            {
                if (!hasObjVar(self, BRAWLER_REWARD_OBJVAR) || getIntObjVar(self, BRAWLER_REWARD_OBJVAR) < 1)
                {
                    giveBrawlerWeaponChoice(self);
                }
                else 
                {
                    setObjVar(self, BRAWLER_QUEST_OBJVAR, 2);
                    giveQuestMessage(self);
                }
                return SCRIPT_CONTINUE;
            }
            else if (profession.equals(MARKSMAN))
            {
                if (!hasObjVar(self, MARKSMAN_REWARD_OBJVAR) || getIntObjVar(self, MARKSMAN_REWARD_OBJVAR) < 1)
                {
                    giveMarksmanWeaponChoice(self);
                }
                else 
                {
                    setObjVar(self, MARKSMAN_QUEST_OBJVAR, 2);
                    giveQuestMessage(self);
                }
                return SCRIPT_CONTINUE;
            }
            else if (profession.equals(SCOUT) || profession.equals(MEDIC))
            {
                giveStartingRecommendation(self);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                giveQuestMessage(self);
            }
            break;
            case sui.BP_CANCEL:
            showDelayedAcceptingQuestMessage(self);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleGiveQuestHelp(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            getProfessionQuestHelp(self);
            break;
            case sui.BP_CANCEL:
            if (hasObjVar(self, "new_player.advice.showRefusedHelp"))
            {
                int numShowHelp = getIntObjVar(self, "new_player.advice.showRefusedHelp") - 1;
                if (numShowHelp <= 0)
                {
                    removeObjVar(self, "new_player.advice.showRefusedHelp");
                }
                else 
                {
                    setObjVar(self, "new_player.advice.showRefusedHelp", numShowHelp);
                }
                String droidName = getDroidName(self);
                string_id textMsg = new string_id("new_player", "refused_quest_help");
                string_id okButton = new string_id("new_player", "help_now_button");
                string_id cancelButton = new string_id("new_player", "thank_you_button");
                twoButtonSui(self, self, "handleRefusedQuestHelp", droidName, textMsg, okButton, cancelButton);
            }
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleShowHolocronGeneralInfo(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        String profession = getStringObjVar(self, CURRENT_PROFESSION_OBJVAR);
        switch (bp)
        {
            case sui.BP_OK:
            if (profession.equals("brawler"))
            {
                openHolocronToPage(self, "Combat.Targeting.Consider");
                openHolocronToPage(self, "Combat.Targeting");
            }
            if (profession.equals("marksman"))
            {
                openHolocronToPage(self, "Combat.Targeting.Consider");
                openHolocronToPage(self, "Combat.Targeting");
            }
            if (profession.equals("medic"))
            {
                openHolocronToPage(self, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingMedic.Damage");
                openHolocronToPage(self, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingMedic");
            }
            if (profession.equals("entertainer"))
            {
                openHolocronToPage(self, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingEntertainer.Instruments");
                openHolocronToPage(self, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingEntertainer");
            }
            if (profession.equals("artisan"))
            {
                openHolocronToPage(self, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingArtisan.Resources");
                openHolocronToPage(self, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingArtisan");
            }
            if (profession.equals("scout"))
            {
                openHolocronToPage(self, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingScout.Camps");
                openHolocronToPage(self, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingScout");
            }
            break;
            case sui.BP_CANCEL:
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDelayedAcceptingQuest(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        String profession = getStringObjVar(self, CURRENT_PROFESSION_OBJVAR);
        String droidName = getDroidName(self);
        switch (bp)
        {
            case sui.BP_OK:
            setObjVar(self, QUEST_OBJVAR_BASE + profession, 1);
            if (profession.equals(BRAWLER))
            {
                if (!hasObjVar(self, BRAWLER_REWARD_OBJVAR) || getIntObjVar(self, BRAWLER_REWARD_OBJVAR) < 1)
                {
                    giveBrawlerWeaponChoice(self);
                }
                else 
                {
                    setObjVar(self, BRAWLER_QUEST_OBJVAR, 2);
                    giveQuestMessage(self);
                }
                return SCRIPT_CONTINUE;
            }
            else if (profession.equals(MARKSMAN))
            {
                if (!hasObjVar(self, MARKSMAN_REWARD_OBJVAR) || getIntObjVar(self, MARKSMAN_REWARD_OBJVAR) < 1)
                {
                    giveMarksmanWeaponChoice(self);
                }
                else 
                {
                    setObjVar(self, MARKSMAN_QUEST_OBJVAR, 2);
                    giveQuestMessage(self);
                }
                return SCRIPT_CONTINUE;
            }
            else if (profession.equals(SCOUT) || profession.equals(MEDIC))
            {
                giveStartingRecommendation(self);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                giveQuestMessage(self);
            }
            break;
            case sui.BP_CANCEL:
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRefusedQuestHelp(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            getProfessionQuestHelp(self);
            break;
            case sui.BP_CANCEL:
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBrawlerWeaponChoice(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String revert = params.getString(sui.MSGBOX_BTN_REVERT + ".RevertWasPressed");
        int bp = sui.getIntButtonPressed(params);
        obj_id playerInv = getObjectInSlot(self, "inventory");
        if (!isIdValid(playerInv))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] survivalKnives = utils.getAllItemsPlayerHasByTemplateInBankAndInventory(self, SURVIVAL_KNIFE_TEMPLATE);
        if (survivalKnives != null && survivalKnives.length > 0)
        {
            for (int i = 0; i < survivalKnives.length; i++)
            {
                obj_id knife = survivalKnives[i];
                if (isIdValid(knife))
                {
                    if (!isCrafted(knife))
                    {
                        destroyObject(knife);
                        break;
                    }
                }
            }
        }
        else 
        {
            obj_id rightHandItem = getObjectInSlot(self, "hold_r");
            if (isIdValid(rightHandItem))
            {
                String equippedTemplate = getTemplateName(rightHandItem);
                if (equippedTemplate.equals(SURVIVAL_KNIFE_TEMPLATE))
                {
                    if (!isCrafted(rightHandItem))
                    {
                        destroyObject(rightHandItem);
                    }
                }
            }
        }
        int free_space = getVolumeFree(playerInv);
        if (free_space > 0)
        {
            switch (bp)
            {
                case sui.BP_OK:
                if (revert != null && !revert.equals(""))
                {
                    obj_id item = weapons.createWeapon(WOODEN_STAFF_TEMPLATE, playerInv, 1.0f);
                }
                else 
                {
                    obj_id item = weapons.createWeapon(HEAVY_AXE_TEMPLATE, playerInv, 1.0f);
                }
                break;
                case sui.BP_CANCEL:
                obj_id item = weapons.createWeapon(SURVIVAL_KNIFE_TEMPLATE, playerInv, 1.0f);
                break;
            }
            String custLogMsg = "New Player Quests: %TU finished brawler task 1... which is simply to choose a weapon style.";
            CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
            setObjVar(self, CURRENT_PROFESSION_OBJVAR, BRAWLER);
            setObjVar(self, BRAWLER_QUEST_OBJVAR, 2);
            setObjVar(self, BRAWLER_REWARD_OBJVAR, 1);
            giveQuestMessage(self);
        }
        else 
        {
            giveInventoryFullMessage(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMarksmanWeaponChoice(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String revert = params.getString(sui.MSGBOX_BTN_REVERT + ".RevertWasPressed");
        int bp = sui.getIntButtonPressed(params);
        obj_id playerInv = getObjectInSlot(self, "inventory");
        if (!isIdValid(playerInv))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] cdefPistols = utils.getAllItemsPlayerHasByTemplateInBankAndInventory(self, CDEF_PISTOL_TEMPLATE);
        if (cdefPistols != null && cdefPistols.length > 0)
        {
            for (int i = 0; i < cdefPistols.length; i++)
            {
                obj_id pistol = cdefPistols[i];
                if (isIdValid(pistol))
                {
                    if (!isCrafted(pistol))
                    {
                        destroyObject(pistol);
                        break;
                    }
                }
            }
        }
        else 
        {
            obj_id rightHandItem = getObjectInSlot(self, "hold_r");
            if (isIdValid(rightHandItem))
            {
                String equippedTemplate = getTemplateName(rightHandItem);
                if (equippedTemplate.equals(CDEF_PISTOL_TEMPLATE))
                {
                    if (!isCrafted(rightHandItem))
                    {
                        destroyObject(rightHandItem);
                    }
                }
            }
        }
        int free_space = getVolumeFree(playerInv);
        if (free_space > 0)
        {
            switch (bp)
            {
                case sui.BP_OK:
                if (revert != null && !revert.equals(""))
                {
                    obj_id item = weapons.createWeapon(CDEF_RIFLE_TEMPLATE, playerInv, 1.0f);
                }
                else 
                {
                    obj_id item = weapons.createWeapon(CDEF_CARBINE_TEMPLATE, playerInv, 1.0f);
                }
                break;
                case sui.BP_CANCEL:
                obj_id item = weapons.createWeapon(CDEF_PISTOL_TEMPLATE, playerInv, 1.0f);
                break;
            }
            String custLogMsg = "New Player Quests: %TU finished marksman task 1... which is simply to choose a weapon style.";
            CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
            setObjVar(self, CURRENT_PROFESSION_OBJVAR, MARKSMAN);
            setObjVar(self, MARKSMAN_QUEST_OBJVAR, 2);
            setObjVar(self, MARKSMAN_REWARD_OBJVAR, 1);
            giveQuestMessage(self);
        }
        else 
        {
            giveInventoryFullMessage(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleStartingRecommendation(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String profession = getStringObjVar(self, CURRENT_PROFESSION_OBJVAR);
        String questObjVar = QUEST_OBJVAR_BASE + profession;
        setObjVar(self, questObjVar, 2);
        String custLogMsg = "New Player Quests: %TU finished " + profession + " task 1... which is just a brief bit of advice.";
        CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
        giveQuestMessage(self);
        return SCRIPT_CONTINUE;
    }
    public int handleHelperDroidDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (isSpaceScene())
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "new_player.advice.callingStoredHelperDroid"))
        {
            int numTimes = getIntObjVar(self, "new_player.advice.callingStoredHelperDroid") - 1;
            if (numTimes <= 0)
            {
                removeObjVar(self, "new_player.advice.callingStoredHelperDroid");
            }
            else 
            {
                setObjVar(self, "new_player.advice.callingStoredHelperDroid", numTimes);
            }
            String droidName = getDroidName(self);
            string_id textMsg = new string_id("new_player", "calling_stored_helper_droid");
            string_id okButton = new string_id("new_player", "okay_button");
            oneButtonSui(self, self, "noHandler", droidName, textMsg, okButton);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleGiveNewProfessionQuestPrompt(obj_id self, dictionary params) throws InterruptedException
    {
        String droidName = getDroidName(self);
        String profession = getStringObjVar(self, CURRENT_PROFESSION_OBJVAR);
        String questText = profession + "_quest_00";
        string_id textMsg = new string_id("new_player", questText);
        string_id okButton = new string_id("new_player", "lets_start_button");
        string_id cancelButton = new string_id("new_player", "not_now_button");
        twoButtonSui(self, self, "handleNewProfessionGreetingResponse", droidName, textMsg, okButton, cancelButton);
        return SCRIPT_CONTINUE;
    }
    public int handleGiveProfessionAddedMessage(obj_id self, dictionary params) throws InterruptedException
    {
        giveProfessionAddedMessage(self);
        return SCRIPT_CONTINUE;
    }
    public int handleGiveQuestMessage(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id helperDroidPcd = getHelperDroidPcd(self);
        if (!isIdValid(helperDroidPcd))
        {
            utils.setScriptVar(self, "new_player.questMessage", true);
            messageTo(self, "handleHelperDroidCheck", null, 1, false);
        }
        else 
        {
            if (callable.hasCDCallable(helperDroidPcd))
            {
                obj_id helperDroid = callable.getCDCallable(helperDroidPcd);
                if (isIdValid(helperDroid))
                {
                    giveQuestMessage(self);
                    return SCRIPT_CONTINUE;
                }
            }
            giveQuestMessage(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDoQuestComplete(obj_id self, dictionary params) throws InterruptedException
    {
        String profession = params.getString("profession");
        setObjVar(self, QUEST_COMPLETE_OBJVAR, profession);
        obj_id helperDroidPcd = getHelperDroidPcd(self);
        if (!isIdValid(helperDroidPcd))
        {
            utils.setScriptVar(self, "new_player.questMessage", true);
            messageTo(self, "handleHelperDroidCheck", null, 1, false);
        }
        else 
        {
            if (callable.hasCDCallable(helperDroidPcd))
            {
                obj_id helperDroid = callable.getCDCallable(helperDroidPcd);
                if (isIdValid(helperDroid))
                {
                    giveQuestCompleteSui(self);
                    return SCRIPT_CONTINUE;
                }
            }
            unpackHelperDroid(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerCombatKill(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        if (isIdValid(target))
        {
            obj_id primaryKiller = getObjIdObjVar(target, xp.VAR_TOP_GROUP);
            obj_id landedDeathBlow = getObjIdObjVar(target, xp.VAR_LANDED_DEATHBLOW);
            obj_id[] topDamagers = getObjIdArrayObjVar(target, xp.VAR_TOP_DAMAGERS);
            if (isIdValid(primaryKiller) && isIdValid(landedDeathBlow))
            {
                if (primaryKiller == self || landedDeathBlow == self || utils.isObjIdInArray(topDamagers, self))
                {
                    String[] xpTypes = params.getStringArray("xpTypes");
                    if (xpTypes == null)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    int numXpTypes = xpTypes.length;
                    int meleeCount = 0;
                    int rangedCount = 0;
                    for (int i = 0; i < xpTypes.length; i++)
                    {
                        String xpType = xpTypes[i];
                        int stringCheck = xpType.indexOf("melee");
                        if (stringCheck > -1)
                        {
                            meleeCount++;
                        }
                        stringCheck = xpType.indexOf("ranged");
                        if (stringCheck > -1)
                        {
                            rangedCount++;
                        }
                    }
                    String xpReward = "";
                    int xpAmount = 0;
                    int creditsReward = 0;
                    String custLogMsg = "";
                    prose_package msg = new prose_package();
                    dictionary webster = new dictionary();
                    if (meleeCount == numXpTypes && hasObjVar(self, BRAWLER_QUEST_OBJVAR))
                    {
                        int questNum = getIntObjVar(self, BRAWLER_QUEST_OBJVAR);
                        switch (questNum)
                        {
                            case 0:
                            break;
                            case 1:
                            break;
                            case 2:
                            custLogMsg = "New Player Quests: %TU finished brawler task 2 - kill a creature.";
                            CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                            if (getIntObjVar(self, BRAWLER_REWARD_OBJVAR) < 2)
                            {
                                xpReward = xpTypes[rand(0, xpTypes.length - 1)];
                                xpAmount = 50;
                                creditsReward = 100;
                                xp.grantCombatStyleXp(self, xpReward, xpAmount);
                                money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                                giveTaskCompleteRewardMessage(self, SID_BRAWLER, creditsReward);
                                checkForSpecialReward(self, false);
                                setObjVar(self, BRAWLER_REWARD_OBJVAR, 2);
                            }
                            else 
                            {
                                giveTaskCompleteNoRewardMessage(self, SID_BRAWLER);
                            }
                            setObjVar(self, BRAWLER_QUEST_OBJVAR, 3);
                            setObjVar(self, CURRENT_PROFESSION_OBJVAR, BRAWLER);
                            messageTo(self, "handleGiveQuestMessage", null, 9, false);
                            return SCRIPT_CONTINUE;
                            case 3:
                            if (utils.hasScriptVar(self, "new_player.berserkUsed"))
                            {
                                utils.removeScriptVar(self, "new_player.berserkUsed");
                                custLogMsg = "New Player Quests: %TU finished brawler task 3 - use berserk.";
                                CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                                if (getIntObjVar(self, BRAWLER_REWARD_OBJVAR) < 3)
                                {
                                    xpReward = xpTypes[rand(0, xpTypes.length - 1)];
                                    xpAmount = 50;
                                    creditsReward = 100;
                                    xp.grantCombatStyleXp(self, xpReward, xpAmount);
                                    money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                                    giveTaskCompleteRewardMessage(self, SID_BRAWLER, creditsReward);
                                    checkForSpecialReward(self, false);
                                    setObjVar(self, BRAWLER_REWARD_OBJVAR, 3);
                                }
                                else 
                                {
                                    giveTaskCompleteNoRewardMessage(self, SID_BRAWLER);
                                }
                                setObjVar(self, BRAWLER_QUEST_OBJVAR, 4);
                                setObjVar(self, CURRENT_PROFESSION_OBJVAR, BRAWLER);
                                messageTo(self, "handleGiveQuestMessage", null, 9, false);
                                return SCRIPT_CONTINUE;
                            }
                            break;
                            case 4:
                            if (utils.hasScriptVar(self, "new_player.lungeUsed"))
                            {
                                obj_id lungeTarget = utils.getObjIdScriptVar(self, "new_player.lungeUsed");
                                if (lungeTarget == target)
                                {
                                    utils.removeScriptVar(self, "new_player.lungeUsed");
                                    custLogMsg = "New Player Quests: %TU finished brawler task 4 - use lunge.";
                                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                                    if (getIntObjVar(self, BRAWLER_REWARD_OBJVAR) < 4)
                                    {
                                        xpReward = xpTypes[rand(0, xpTypes.length - 1)];
                                        xpAmount = 50;
                                        creditsReward = 100;
                                        xp.grantCombatStyleXp(self, xpReward, xpAmount);
                                        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                                        giveTaskCompleteRewardMessage(self, SID_BRAWLER, creditsReward);
                                        checkForSpecialReward(self, false);
                                        setObjVar(self, BRAWLER_REWARD_OBJVAR, 4);
                                    }
                                    else 
                                    {
                                        giveTaskCompleteNoRewardMessage(self, SID_BRAWLER);
                                    }
                                    setObjVar(self, BRAWLER_QUEST_OBJVAR, 5);
                                    setObjVar(self, CURRENT_PROFESSION_OBJVAR, BRAWLER);
                                    messageTo(self, "handleGiveQuestMessage", null, 9, false);
                                    return SCRIPT_CONTINUE;
                                }
                            }
                            break;
                            case 5:
                            if (utils.hasScriptVar(self, "new_player.centerOfBeingUsed"))
                            {
                                utils.removeScriptVar(self, "new_player.centerOfBeingUsed");
                                custLogMsg = "New Player Quests: %TU finished brawler task 5 - use center of being.";
                                CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                                if (getIntObjVar(self, BRAWLER_REWARD_OBJVAR) < 5)
                                {
                                    xpReward = xpTypes[rand(0, xpTypes.length - 1)];
                                    xpAmount = 50;
                                    creditsReward = 100;
                                    xp.grantCombatStyleXp(self, xpReward, xpAmount);
                                    money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                                    giveTaskCompleteRewardMessage(self, SID_BRAWLER, creditsReward);
                                    checkForSpecialReward(self, false);
                                    setObjVar(self, BRAWLER_REWARD_OBJVAR, 5);
                                }
                                else 
                                {
                                    giveTaskCompleteNoRewardMessage(self, SID_BRAWLER);
                                }
                                setObjVar(self, BRAWLER_QUEST_OBJVAR, 6);
                                setObjVar(self, CURRENT_PROFESSION_OBJVAR, BRAWLER);
                                messageTo(self, "handleGiveQuestMessage", null, 9, false);
                                return SCRIPT_CONTINUE;
                            }
                            break;
                            case 6:
                            int numKilled = 1;
                            if (hasObjVar(self, "new_player.temp.brawler"))
                            {
                                numKilled = getIntObjVar(self, "new_player.temp.brawler") + 1;
                            }
                            setObjVar(self, "new_player.temp.brawler", numKilled);
                            if (numKilled >= 10)
                            {
                                custLogMsg = "New Player Quests: %TU finished the final brawler task - kill 10 creatures.";
                                CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                                if (getIntObjVar(self, BRAWLER_REWARD_OBJVAR) < 6)
                                {
                                    xpReward = xpTypes[rand(0, xpTypes.length - 1)];
                                    xpAmount = 200;
                                    creditsReward = 2000;
                                    String questCompleteRewardSetting = getConfigSetting("New_Player", "QuestCompleteRewardAmount");
                                    int questCompleteReward = utils.stringToInt(questCompleteRewardSetting);
                                    if (questCompleteReward > 0)
                                    {
                                        creditsReward = questCompleteReward;
                                    }
                                    xp.grantCombatStyleXp(self, xpReward, xpAmount);
                                    money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                                    giveTaskCompleteRewardMessage(self, SID_BRAWLER, creditsReward);
                                    checkForSpecialReward(self, true);
                                    setObjVar(self, BRAWLER_REWARD_OBJVAR, 6);
                                }
                                else 
                                {
                                    giveTaskCompleteNoRewardMessage(self, SID_BRAWLER);
                                }
                                removeObjVar(self, BRAWLER_QUEST_OBJVAR);
                                removeObjVar(self, "new_player.temp.brawler");
                                removeCurrentProfessionObjVar(self, BRAWLER);
                                webster = new dictionary();
                                webster.put("profession", BRAWLER);
                                messageTo(self, "handleDoQuestComplete", webster, 3, false);
                                return SCRIPT_CONTINUE;
                            }
                            break;
                            default:
                            break;
                        }
                        return SCRIPT_CONTINUE;
                    }
                    if (rangedCount == numXpTypes && hasObjVar(self, MARKSMAN_QUEST_OBJVAR))
                    {
                        int questNum = getIntObjVar(self, MARKSMAN_QUEST_OBJVAR);
                        switch (questNum)
                        {
                            case 0:
                            break;
                            case 1:
                            break;
                            case 2:
                            custLogMsg = "New Player Quests: %TU finished marksman task 2 - kill a creature.";
                            CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                            if (getIntObjVar(self, MARKSMAN_REWARD_OBJVAR) < 2)
                            {
                                xpReward = xpTypes[rand(0, xpTypes.length - 1)];
                                xpAmount = 60;
                                creditsReward = 130;
                                xp.grantCombatStyleXp(self, xpReward, xpAmount);
                                money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                                giveTaskCompleteRewardMessage(self, SID_MARKSMAN, creditsReward);
                                checkForSpecialReward(self, false);
                                setObjVar(self, MARKSMAN_REWARD_OBJVAR, 2);
                            }
                            else 
                            {
                                giveTaskCompleteNoRewardMessage(self, SID_MARKSMAN);
                            }
                            setObjVar(self, MARKSMAN_QUEST_OBJVAR, 3);
                            setObjVar(self, CURRENT_PROFESSION_OBJVAR, MARKSMAN);
                            messageTo(self, "handleGiveQuestMessage", null, 9, false);
                            return SCRIPT_CONTINUE;
                            case 3:
                            if (utils.hasScriptVar(self, "new_player.pointBlankSingleUsed"))
                            {
                                obj_id pointBlankTarget = utils.getObjIdScriptVar(self, "new_player.pointBlankSingleUsed");
                                if (pointBlankTarget == target)
                                {
                                    utils.removeScriptVar(self, "new_player.pointBlankSingleUsed");
                                    custLogMsg = "New Player Quests: %TU finished marksman task 3 - use point blank single.";
                                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                                    if (getIntObjVar(self, MARKSMAN_REWARD_OBJVAR) < 3)
                                    {
                                        xpReward = xpTypes[rand(0, xpTypes.length - 1)];
                                        xpAmount = 70;
                                        creditsReward = 130;
                                        xp.grantCombatStyleXp(self, xpReward, xpAmount);
                                        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                                        giveTaskCompleteRewardMessage(self, SID_MARKSMAN, creditsReward);
                                        checkForSpecialReward(self, false);
                                        setObjVar(self, MARKSMAN_REWARD_OBJVAR, 3);
                                    }
                                    else 
                                    {
                                        giveTaskCompleteNoRewardMessage(self, SID_MARKSMAN);
                                    }
                                    setObjVar(self, MARKSMAN_QUEST_OBJVAR, 4);
                                    setObjVar(self, CURRENT_PROFESSION_OBJVAR, MARKSMAN);
                                    messageTo(self, "handleGiveQuestMessage", null, 9, false);
                                    return SCRIPT_CONTINUE;
                                }
                            }
                            break;
                            case 4:
                            if (utils.hasScriptVar(self, "new_player.overChargeShotUsed"))
                            {
                                obj_id overchargeTarget = utils.getObjIdScriptVar(self, "new_player.overChargeShotUsed");
                                if (overchargeTarget == target)
                                {
                                    utils.removeScriptVar(self, "new_player.overChargeShotUsed");
                                    custLogMsg = "New Player Quests: %TU finished marksman task 4 - use overcharge shot.";
                                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                                    if (getIntObjVar(self, MARKSMAN_REWARD_OBJVAR) < 4)
                                    {
                                        xpReward = xpTypes[rand(0, xpTypes.length - 1)];
                                        xpAmount = 70;
                                        creditsReward = 140;
                                        xp.grantCombatStyleXp(self, xpReward, xpAmount);
                                        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                                        giveTaskCompleteRewardMessage(self, SID_MARKSMAN, creditsReward);
                                        checkForSpecialReward(self, false);
                                        setObjVar(self, MARKSMAN_REWARD_OBJVAR, 4);
                                    }
                                    else 
                                    {
                                        giveTaskCompleteNoRewardMessage(self, SID_MARKSMAN);
                                    }
                                    setObjVar(self, MARKSMAN_QUEST_OBJVAR, 5);
                                    setObjVar(self, CURRENT_PROFESSION_OBJVAR, MARKSMAN);
                                    messageTo(self, "handleGiveQuestMessage", null, 9, false);
                                    return SCRIPT_CONTINUE;
                                }
                            }
                            break;
                            case 5:
                            int numKilled = 1;
                            if (hasObjVar(self, "new_player.temp.marksman"))
                            {
                                numKilled = getIntObjVar(self, "new_player.temp.marksman") + 1;
                            }
                            setObjVar(self, "new_player.temp.marksman", numKilled);
                            if (numKilled >= 10)
                            {
                                custLogMsg = "New Player Quests: %TU finished the final marksman task - kill 10 creatures.";
                                CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                                if (getIntObjVar(self, MARKSMAN_REWARD_OBJVAR) < 5)
                                {
                                    xpReward = xpTypes[rand(0, xpTypes.length - 1)];
                                    xpAmount = 200;
                                    creditsReward = 2000;
                                    String questCompleteRewardSetting = getConfigSetting("New_Player", "QuestCompleteRewardAmount");
                                    int questCompleteReward = utils.stringToInt(questCompleteRewardSetting);
                                    if (questCompleteReward > 0)
                                    {
                                        creditsReward = questCompleteReward;
                                    }
                                    xp.grantCombatStyleXp(self, xpReward, xpAmount);
                                    money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                                    giveTaskCompleteRewardMessage(self, SID_MARKSMAN, creditsReward);
                                    checkForSpecialReward(self, true);
                                    setObjVar(self, MARKSMAN_REWARD_OBJVAR, 5);
                                }
                                else 
                                {
                                    giveTaskCompleteNoRewardMessage(self, SID_MARKSMAN);
                                }
                                removeObjVar(self, MARKSMAN_QUEST_OBJVAR);
                                removeObjVar(self, "new_player.temp.marksman");
                                removeCurrentProfessionObjVar(self, MARKSMAN);
                                webster = new dictionary();
                                webster.put("profession", MARKSMAN);
                                messageTo(self, "handleDoQuestComplete", webster, 3, false);
                                return SCRIPT_CONTINUE;
                            }
                            break;
                            default:
                            break;
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleNewPlayerScoutAction(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, SCOUT_QUEST_OBJVAR))
        {
            int questNum = getIntObjVar(self, SCOUT_QUEST_OBJVAR);
            String xpReward = "";
            int xpAmount = 0;
            int creditsReward = 0;
            prose_package msg = new prose_package();
            switch (questNum)
            {
                case 0:
                break;
                case 1:
                break;
                case 2:
                if (params.containsKey("resourceType"))
                {
                    String resType = params.getString("resourceType");
                    int amount = params.getInt("amount");
                    if (resType.startsWith("hide"))
                    {
                        setObjVar(self, "new_player.temp.scout_hide", amount);
                    }
                    else if (resType.startsWith("bone"))
                    {
                        setObjVar(self, "new_player.temp.scout_bone", amount);
                    }
                    String custLogMsg = "New Player Quests: %TU finished scout task 2 - harvest from a creature.";
                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                    if (getIntObjVar(self, SCOUT_REWARD_OBJVAR) < 2)
                    {
                        xpReward = "scout";
                        xpAmount = 60;
                        creditsReward = 100;
                        xp.grantCombatStyleXp(self, xpReward, xpAmount);
                        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                        giveTaskCompleteRewardMessage(self, SID_SCOUT, creditsReward);
                        checkForSpecialReward(self, false);
                        setObjVar(self, SCOUT_REWARD_OBJVAR, 2);
                    }
                    else 
                    {
                        giveTaskCompleteNoRewardMessage(self, SID_SCOUT);
                    }
                    setObjVar(self, SCOUT_QUEST_OBJVAR, 3);
                    setObjVar(self, CURRENT_PROFESSION_OBJVAR, SCOUT);
                    messageTo(self, "handleGiveQuestMessage", null, 9, false);
                    return SCRIPT_CONTINUE;
                }
                break;
                case 3:
                if (params.containsKey("resourceType"))
                {
                    String resType = params.getString("resourceType");
                    int amount = params.getInt("amount");
                    int hides = 0;
                    int bones = 0;
                    if (hasObjVar(self, "new_player.temp.scout_hide"))
                    {
                        hides = getIntObjVar(self, "new_player.temp.scout_hide");
                    }
                    if (hasObjVar(self, "new_player.temp.scout_bone"))
                    {
                        bones = getIntObjVar(self, "new_player.temp.scout_bone");
                    }
                    if (resType.startsWith("hide"))
                    {
                        hides = hides + amount;
                        setObjVar(self, "new_player.temp.scout_hide", hides);
                    }
                    else if (resType.startsWith("bone"))
                    {
                        bones = bones + amount;
                        setObjVar(self, "new_player.temp.scout_bone", bones);
                    }
                    if (hides >= 3 && bones >= 3)
                    {
                        String custLogMsg = "New Player Quests: %TU finished scout task 3 - harvest hides and bones.";
                        CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                        if (getIntObjVar(self, SCOUT_REWARD_OBJVAR) < 3)
                        {
                            xpReward = "scout";
                            xpAmount = 70;
                            creditsReward = 100;
                            xp.grantCombatStyleXp(self, xpReward, xpAmount);
                            money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                            giveTaskCompleteRewardMessage(self, SID_SCOUT, creditsReward);
                            checkForSpecialReward(self, false);
                            setObjVar(self, SCOUT_REWARD_OBJVAR, 3);
                        }
                        else 
                        {
                            giveTaskCompleteNoRewardMessage(self, SID_SCOUT);
                        }
                        setObjVar(self, SCOUT_QUEST_OBJVAR, 4);
                        setObjVar(self, CURRENT_PROFESSION_OBJVAR, SCOUT);
                        removeObjVar(self, "new_player.temp.scout_hide");
                        removeObjVar(self, "new_player.temp.scout_bone");
                        messageTo(self, "handleGiveQuestMessage", null, 9, false);
                        return SCRIPT_CONTINUE;
                    }
                }
                break;
                case 4:
                break;
                case 5:
                if (params.containsKey("deployedCamp"))
                {
                    String custLogMsg = "New Player Quests: %TU finished scout task 5 - deploy a camp.";
                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                    if (getIntObjVar(self, SCOUT_REWARD_OBJVAR) < 5)
                    {
                        xpReward = "scout";
                        xpAmount = 50;
                        creditsReward = 100;
                        xp.grantCombatStyleXp(self, xpReward, xpAmount);
                        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                        giveTaskCompleteRewardMessage(self, SID_SCOUT, creditsReward);
                        checkForSpecialReward(self, false);
                        setObjVar(self, SCOUT_REWARD_OBJVAR, 5);
                    }
                    else 
                    {
                        giveTaskCompleteNoRewardMessage(self, SID_SCOUT);
                    }
                    setObjVar(self, SCOUT_QUEST_OBJVAR, 6);
                    setObjVar(self, CURRENT_PROFESSION_OBJVAR, SCOUT);
                    messageTo(self, "handleGiveQuestMessage", null, 9, false);
                    return SCRIPT_CONTINUE;
                }
                break;
                case 6:
                if (params.containsKey("resourceType"))
                {
                    String resType = params.getString("resourceType");
                    int amount = params.getInt("amount");
                    if (!resType.startsWith("hide"))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    int numHides = 0;
                    if (hasObjVar(self, "new_player.temp.scout"))
                    {
                        numHides = getIntObjVar(self, "new_player.temp.scout");
                    }
                    numHides = numHides + amount;
                    if (numHides < 90)
                    {
                        setObjVar(self, "new_player.temp.scout", numHides);
                    }
                    else 
                    {
                        String custLogMsg = "New Player Quests: %TU finished the final scout task - harvest 90 hides.";
                        CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                        if (getIntObjVar(self, SCOUT_REWARD_OBJVAR) < 6)
                        {
                            xpReward = "scout";
                            xpAmount = 200;
                            creditsReward = 2000;
                            String questCompleteRewardSetting = getConfigSetting("New_Player", "QuestCompleteRewardAmount");
                            int questCompleteReward = utils.stringToInt(questCompleteRewardSetting);
                            if (questCompleteReward > 0)
                            {
                                creditsReward = questCompleteReward;
                            }
                            xp.grantCombatStyleXp(self, xpReward, xpAmount);
                            money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                            giveTaskCompleteRewardMessage(self, SID_SCOUT, creditsReward);
                            checkForSpecialReward(self, true);
                            setObjVar(self, SCOUT_REWARD_OBJVAR, 6);
                        }
                        else 
                        {
                            giveTaskCompleteNoRewardMessage(self, SID_SCOUT);
                        }
                        removeObjVar(self, SCOUT_QUEST_OBJVAR);
                        removeObjVar(self, "new_player.temp.scout");
                        removeCurrentProfessionObjVar(self, SCOUT);
                        dictionary webster = new dictionary();
                        webster.put("profession", SCOUT);
                        messageTo(self, "handleDoQuestComplete", webster, 3, false);
                        return SCRIPT_CONTINUE;
                    }
                }
                break;
                default:
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQuestCraftingAction(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id prototype = params.getObjId("prototype");
        String protoTemplate = params.getString("prototypeTemplate");
        String xpReward = "";
        int xpAmount = 0;
        int creditsReward = 0;
        prose_package msg = new prose_package();
        dictionary webster = new dictionary();
        if (hasObjVar(self, SCOUT_QUEST_OBJVAR) && getIntObjVar(self, SCOUT_QUEST_OBJVAR) == 4)
        {
            if (protoTemplate.equals(LECEPANINE_DART))
            {
                String custLogMsg = "New Player Quests: %TU finished scout task 4 - craft lecepanine darts.";
                CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                if (getIntObjVar(self, SCOUT_REWARD_OBJVAR) < 4)
                {
                    xpReward = "scout";
                    xpAmount = 70;
                    creditsReward = 100;
                    xp.grantCombatStyleXp(self, xpReward, xpAmount);
                    money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                    giveTaskCompleteRewardMessage(self, SID_SCOUT, creditsReward);
                    checkForSpecialReward(self, false);
                    setObjVar(self, SCOUT_REWARD_OBJVAR, 4);
                }
                else 
                {
                    giveTaskCompleteNoRewardMessage(self, SID_SCOUT);
                }
                setObjVar(self, SCOUT_QUEST_OBJVAR, 5);
                setObjVar(self, CURRENT_PROFESSION_OBJVAR, SCOUT);
                messageTo(self, "handleGiveQuestMessage", null, 9, false);
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, ARTISAN_QUEST_OBJVAR))
        {
            int questNum = getIntObjVar(self, ARTISAN_QUEST_OBJVAR);
            if (questNum == 4)
            {
                if (protoTemplate.equals(CHEMICAL_SURVEY_DEVICE))
                {
                    String custLogMsg = "New Player Quests: %TU finished artisan task 4 - craft a chemical survey device.";
                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                    if (getIntObjVar(self, ARTISAN_REWARD_OBJVAR) < 4)
                    {
                        xpReward = "crafting_general";
                        xpAmount = 25;
                        creditsReward = 100;
                        xp.grantCraftingStyleXp(self, xpReward, xpAmount);
                        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                        giveTaskCompleteRewardMessage(self, SID_ARTISAN, creditsReward);
                        checkForSpecialReward(self, false);
                        setObjVar(self, ARTISAN_REWARD_OBJVAR, 4);
                    }
                    else 
                    {
                        giveTaskCompleteNoRewardMessage(self, SID_ARTISAN);
                    }
                    setObjVar(self, ARTISAN_QUEST_OBJVAR, 5);
                    setObjVar(self, CURRENT_PROFESSION_OBJVAR, ARTISAN);
                    messageTo(self, "handleGiveQuestMessage", null, 9, false);
                    return SCRIPT_CONTINUE;
                }
            }
            if (questNum == 5)
            {
                if (protoTemplate.equals(CDEF_RIFLE))
                {
                    int rifles = 1;
                    if (hasObjVar(self, "new_player.temp.artisan"))
                    {
                        rifles = getIntObjVar(self, "new_player.temp.artisan") + 1;
                    }
                    setObjVar(self, "new_player.temp.artisan", rifles);
                    if (rifles >= 5)
                    {
                        String custLogMsg = "New Player Quests: %TU finished the final artisan task - craft 5 cdef rifles.";
                        CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                        if (getIntObjVar(self, ARTISAN_REWARD_OBJVAR) < 5)
                        {
                            xpReward = "crafting_general";
                            xpAmount = 100;
                            creditsReward = 2000;
                            String questCompleteRewardSetting = getConfigSetting("New_Player", "QuestCompleteRewardAmount");
                            int questCompleteReward = utils.stringToInt(questCompleteRewardSetting);
                            if (questCompleteReward > 0)
                            {
                                creditsReward = questCompleteReward;
                            }
                            xp.grantCraftingStyleXp(self, xpReward, xpAmount);
                            money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                            giveTaskCompleteRewardMessage(self, SID_ARTISAN, creditsReward);
                            checkForSpecialReward(self, true);
                            setObjVar(self, ARTISAN_REWARD_OBJVAR, 5);
                        }
                        else 
                        {
                            giveTaskCompleteNoRewardMessage(self, SID_ARTISAN);
                        }
                        removeObjVar(self, ARTISAN_QUEST_OBJVAR);
                        removeObjVar(self, "new_player.temp.artisan");
                        removeCurrentProfessionObjVar(self, ARTISAN);
                        webster = new dictionary();
                        webster.put("profession", ARTISAN);
                        messageTo(self, "handleDoQuestComplete", webster, 3, false);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        if (hasObjVar(self, MEDIC_QUEST_OBJVAR) && getIntObjVar(self, MEDIC_QUEST_OBJVAR) == 5)
        {
            if (protoTemplate.equals(STIMPACK))
            {
                String custLogMsg = "New Player Quests: %TU finished medic task 5 - craft a stimpack.";
                CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                if (getIntObjVar(self, MEDIC_REWARD_OBJVAR) < 5)
                {
                    xpReward = "crafting_medicine_general";
                    xpAmount = 50;
                    creditsReward = 100;
                    xp.grantCraftingStyleXp(self, xpReward, xpAmount);
                    money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                    giveTaskCompleteRewardMessage(self, SID_MEDIC, creditsReward);
                    checkForSpecialReward(self, false);
                    setObjVar(self, MEDIC_REWARD_OBJVAR, 5);
                }
                else 
                {
                    giveTaskCompleteNoRewardMessage(self, SID_MEDIC);
                }
                setObjVar(self, MEDIC_QUEST_OBJVAR, 6);
                setObjVar(self, CURRENT_PROFESSION_OBJVAR, MEDIC);
                messageTo(self, "handleGiveQuestMessage", null, 9, false);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleNewPlayerArtisanAction(obj_id self, dictionary params) throws InterruptedException
    {
        String xpReward = "";
        int xpAmount = 0;
        int creditsReward = 0;
        prose_package msg = new prose_package();
        if (hasObjVar(self, ARTISAN_QUEST_OBJVAR))
        {
            int questNum = getIntObjVar(self, ARTISAN_QUEST_OBJVAR);
            if (questNum == 1)
            {
                if (params.containsKey("resource_class"))
                {
                    String resource_class = params.getString("resource_class");
                    if (resource_class.equals("mineral"))
                    {
                        String custLogMsg = "New Player Quests: %TU finished artisan task 1 - survey for a mineral.";
                        CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                        if (getIntObjVar(self, ARTISAN_REWARD_OBJVAR) < 1)
                        {
                            xpReward = "crafting_general";
                            xpAmount = 25;
                            creditsReward = 100;
                            xp.grantCraftingStyleXp(self, xpReward, xpAmount);
                            money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                            giveTaskCompleteRewardMessage(self, SID_ARTISAN, creditsReward);
                            checkForSpecialReward(self, false);
                            setObjVar(self, ARTISAN_REWARD_OBJVAR, 1);
                        }
                        else 
                        {
                            giveTaskCompleteNoRewardMessage(self, SID_ARTISAN);
                        }
                        setObjVar(self, ARTISAN_QUEST_OBJVAR, 2);
                        setObjVar(self, CURRENT_PROFESSION_OBJVAR, ARTISAN);
                        messageTo(self, "handleGiveQuestMessage", null, 9, false);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            if (questNum == 2)
            {
                if (params.containsKey("sampling"))
                {
                    obj_id[] minerals = utils.getAllItemsPlayerHasByTemplate(self, MINERAL_RESOURCE_CONTAINER);
                    if (minerals != null && minerals.length > 0)
                    {
                        for (int i = 0; i < minerals.length; i++)
                        {
                            obj_id containerId = minerals[i];
                            obj_id resTypeId = getResourceContainerResourceType(containerId);
                            boolean isMineral = isResourceDerivedFrom(resTypeId, "mineral");
                            if (isMineral)
                            {
                                int amt = getResourceContainerQuantity(minerals[i]);
                                if (amt >= 8)
                                {
                                    String resName = getResourceName(resTypeId);
                                    setObjVar(self, "new_player.temp.artisan_resource", resName);
                                    String custLogMsg = "New Player Quests: %TU finished artisan task 2 - sample some minerals.";
                                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                                    if (getIntObjVar(self, ARTISAN_REWARD_OBJVAR) < 2)
                                    {
                                        xpReward = "crafting_general";
                                        xpAmount = 25;
                                        creditsReward = 100;
                                        xp.grantCraftingStyleXp(self, xpReward, xpAmount);
                                        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                                        giveTaskCompleteRewardMessage(self, SID_ARTISAN, creditsReward);
                                        checkForSpecialReward(self, false);
                                        setObjVar(self, ARTISAN_REWARD_OBJVAR, 2);
                                    }
                                    else 
                                    {
                                        giveTaskCompleteNoRewardMessage(self, SID_ARTISAN);
                                    }
                                    setObjVar(self, ARTISAN_QUEST_OBJVAR, 3);
                                    setObjVar(self, CURRENT_PROFESSION_OBJVAR, ARTISAN);
                                    messageTo(self, "handleGiveQuestMessage", null, 9, false);
                                    return SCRIPT_CONTINUE;
                                }
                            }
                        }
                    }
                }
            }
            if (questNum == 3)
            {
                if (params.containsKey("sampling"))
                {
                    obj_id[] minerals = utils.getAllItemsPlayerHasByTemplate(self, MINERAL_RESOURCE_CONTAINER);
                    if (minerals != null && minerals.length > 0)
                    {
                        for (int i = 0; i < minerals.length; i++)
                        {
                            obj_id containerId = minerals[i];
                            obj_id resTypeId = getResourceContainerResourceType(containerId);
                            boolean isMetal = isResourceDerivedFrom(resTypeId, "metal");
                            if (isMetal)
                            {
                                int amt = getResourceContainerQuantity(minerals[i]);
                                int requiredResource = 19;
                                String resName = getResourceName(resTypeId);
                                String mineralName = getStringObjVar(self, "new_player.temp.artisan_resource");
                                if (resName.equals(mineralName))
                                {
                                    requiredResource = 27;
                                }
                                if (amt >= requiredResource)
                                {
                                    String custLogMsg = "New Player Quests: %TU finished artisan task 3 - sample 19 units of metal.";
                                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                                    if (getIntObjVar(self, ARTISAN_REWARD_OBJVAR) < 3)
                                    {
                                        xpReward = "crafting_general";
                                        xpAmount = 25;
                                        creditsReward = 100;
                                        xp.grantCraftingStyleXp(self, xpReward, xpAmount);
                                        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                                        giveTaskCompleteRewardMessage(self, SID_ARTISAN, creditsReward);
                                        checkForSpecialReward(self, false);
                                        setObjVar(self, ARTISAN_REWARD_OBJVAR, 3);
                                    }
                                    else 
                                    {
                                        giveTaskCompleteNoRewardMessage(self, SID_ARTISAN);
                                    }
                                    setObjVar(self, ARTISAN_QUEST_OBJVAR, 4);
                                    setObjVar(self, CURRENT_PROFESSION_OBJVAR, ARTISAN);
                                    removeObjVar(self, "new_player.temp.artisan_resource");
                                    messageTo(self, "handleGiveQuestMessage", null, 9, false);
                                    return SCRIPT_CONTINUE;
                                }
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleImageDesignerSkipped(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, CURRENT_PROFESSION_OBJVAR, ENTERTAINER);
        setObjVar(self, ENTERTAINER_QUEST_OBJVAR, 4);
        giveQuestMessage(self);
        return SCRIPT_CONTINUE;
    }
    public int handleNewPlayerEntertainerAction(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, ENTERTAINER_QUEST_OBJVAR))
        {
            int questNum = getIntObjVar(self, ENTERTAINER_QUEST_OBJVAR);
            String xpReward = "";
            int xpAmount = 0;
            int creditsReward = 0;
            prose_package msg = new prose_package();
            switch (questNum)
            {
                case 0:
                break;
                case 1:
                if (params.containsKey("startedDancing"))
                {
                    String custLogMsg = "New Player Quests: %TU finished entertainer task 1 - dance.";
                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                    if (getIntObjVar(self, ENTERTAINER_REWARD_OBJVAR) < 1)
                    {
                        xpReward = "dance";
                        xpAmount = 50;
                        creditsReward = 80;
                        xp.grantSocialStyleXp(self, xpReward, xpAmount);
                        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                        giveTaskCompleteRewardMessage(self, SID_ENTERTAINER, creditsReward);
                        checkForSpecialReward(self, false);
                        setObjVar(self, ENTERTAINER_REWARD_OBJVAR, 1);
                    }
                    else 
                    {
                        giveTaskCompleteNoRewardMessage(self, SID_ENTERTAINER);
                    }
                    setObjVar(self, ENTERTAINER_QUEST_OBJVAR, 2);
                    setObjVar(self, CURRENT_PROFESSION_OBJVAR, ENTERTAINER);
                    messageTo(self, "handleGiveQuestMessage", null, 9, false);
                    return SCRIPT_CONTINUE;
                }
                break;
                case 2:
                if (params.containsKey("playingMusic"))
                {
                    String custLogMsg = "New Player Quests: %TU finished entertainer task 2 - play music.";
                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                    if (getIntObjVar(self, ENTERTAINER_REWARD_OBJVAR) < 2)
                    {
                        xpReward = "music";
                        xpAmount = 50;
                        creditsReward = 80;
                        xp.grantSocialStyleXp(self, xpReward, xpAmount);
                        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                        giveTaskCompleteRewardMessage(self, SID_ENTERTAINER, creditsReward);
                        checkForSpecialReward(self, false);
                        setObjVar(self, ENTERTAINER_REWARD_OBJVAR, 2);
                    }
                    else 
                    {
                        giveTaskCompleteNoRewardMessage(self, SID_ENTERTAINER);
                    }
                    setObjVar(self, ENTERTAINER_QUEST_OBJVAR, 3);
                    setObjVar(self, CURRENT_PROFESSION_OBJVAR, ENTERTAINER);
                    messageTo(self, "handleGiveQuestMessage", null, 9, false);
                    return SCRIPT_CONTINUE;
                }
                break;
                case 3:
                if (params.containsKey("hairStyled"))
                {
                    boolean hairStyled = params.getBoolean("hairStyled");
                    obj_id target = params.getObjId("target");
                    if (hairStyled)
                    {
                        String custLogMsg = "New Player Quests: %TU finished entertainer task 3 - style your hair.";
                        CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                        if (getIntObjVar(self, ENTERTAINER_REWARD_OBJVAR) < 3)
                        {
                            xpReward = "imagedesigner";
                            xpAmount = 50;
                            creditsReward = 80;
                            xp.grantSocialStyleXp(self, xpReward, xpAmount);
                            money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                            giveTaskCompleteRewardMessage(self, SID_ENTERTAINER, creditsReward);
                            checkForSpecialReward(self, false);
                            setObjVar(self, ENTERTAINER_REWARD_OBJVAR, 3);
                        }
                        else 
                        {
                            giveTaskCompleteNoRewardMessage(self, SID_ENTERTAINER);
                        }
                        setObjVar(self, ENTERTAINER_QUEST_OBJVAR, 4);
                        setObjVar(self, CURRENT_PROFESSION_OBJVAR, ENTERTAINER);
                        messageTo(self, "handleGiveQuestMessage", null, 9, false);
                        return SCRIPT_CONTINUE;
                    }
                }
                break;
                case 4:
                if (params.containsKey("addingFlourish"))
                {
                    String entertainmentType = params.getString("entertainmentType");
                    if (entertainmentType.equals("music") || entertainmentType.equals("dance"))
                    {
                        String custLogMsg = "New Player Quests: %TU finished entertainer task 4 - add a flourish to your performance.";
                        CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                        if (getIntObjVar(self, ENTERTAINER_REWARD_OBJVAR) < 4)
                        {
                            xpReward = entertainmentType;
                            xpAmount = 50;
                            creditsReward = 80;
                            xp.grantSocialStyleXp(self, xpReward, xpAmount);
                            money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                            giveTaskCompleteRewardMessage(self, SID_ENTERTAINER, creditsReward);
                            checkForSpecialReward(self, false);
                            setObjVar(self, ENTERTAINER_REWARD_OBJVAR, 4);
                        }
                        else 
                        {
                            giveTaskCompleteNoRewardMessage(self, SID_ENTERTAINER);
                        }
                        setObjVar(self, ENTERTAINER_QUEST_OBJVAR, 5);
                        setObjVar(self, CURRENT_PROFESSION_OBJVAR, ENTERTAINER);
                        messageTo(self, "handleGiveQuestMessage", null, 9, false);
                        return SCRIPT_CONTINUE;
                    }
                }
                break;
                case 5:
                if (params.containsKey("enteredCantina"))
                {
                    String custLogMsg = "New Player Quests: %TU finished entertainer task 5 - find a cantina or theater.";
                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                    if (getIntObjVar(self, ENTERTAINER_REWARD_OBJVAR) < 5)
                    {
                        xpReward = "entertainer";
                        xpAmount = 50;
                        creditsReward = 80;
                        xp.grantSocialStyleXp(self, xpReward, xpAmount);
                        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                        giveTaskCompleteRewardMessage(self, SID_ENTERTAINER, creditsReward);
                        checkForSpecialReward(self, false);
                        setObjVar(self, ENTERTAINER_REWARD_OBJVAR, 5);
                    }
                    else 
                    {
                        giveTaskCompleteNoRewardMessage(self, SID_ENTERTAINER);
                    }
                    setObjVar(self, ENTERTAINER_QUEST_OBJVAR, 6);
                    setObjVar(self, CURRENT_PROFESSION_OBJVAR, ENTERTAINER);
                    messageTo(self, "handleGiveQuestMessage", null, 9, false);
                    return SCRIPT_CONTINUE;
                }
                break;
                case 6:
                String questCompleteXp = "none";
                if (params.containsKey("listener"))
                {
                    obj_id listener = params.getObjId("listener");
                    if (isIdValid(listener))
                    {
                        if (listener != self)
                        {
                            Vector listenerList = new Vector();
                            listenerList.setSize(0);
                            if (hasObjVar(self, "new_player.temp.entertainer_listeners"))
                            {
                                listenerList = getResizeableObjIdArrayObjVar(self, "new_player.temp.entertainer_listeners");
                            }
                            if (!utils.isElementInArray(listenerList, listener))
                            {
                                listenerList = utils.addElement(listenerList, listener);
                            }
                            int numListeners = listenerList.size();
                            if (numListeners > 0)
                            {
                                setObjVar(self, "new_player.temp.entertainer_listeners", listenerList);
                            }
                            if (numListeners >= 7)
                            {
                                questCompleteXp = "music";
                            }
                        }
                    }
                }
                else if (params.containsKey("watcher"))
                {
                    obj_id watcher = params.getObjId("watcher");
                    if (isIdValid(watcher))
                    {
                        if (watcher != self)
                        {
                            Vector watcherList = new Vector();
                            watcherList.setSize(0);
                            if (hasObjVar(self, "new_player.temp.entertainer_watchers"))
                            {
                                watcherList = getResizeableObjIdArrayObjVar(self, "new_player.temp.entertainer_watchers");
                            }
                            if (!utils.isElementInArray(watcherList, watcher))
                            {
                                watcherList = utils.addElement(watcherList, watcher);
                            }
                            int numWatchers = watcherList.size();
                            if (numWatchers > 0)
                            {
                                setObjVar(self, "new_player.temp.entertainer_watchers", watcherList);
                            }
                            if (numWatchers >= 7)
                            {
                                questCompleteXp = "dance";
                            }
                        }
                    }
                }
                if (!questCompleteXp.equals("none"))
                {
                    String custLogMsg = "New Player Quests: %TU finished the final entertainer task - perform for 7 players.";
                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                    if (getIntObjVar(self, ENTERTAINER_REWARD_OBJVAR) < 6)
                    {
                        xpReward = questCompleteXp;
                        xpAmount = 200;
                        creditsReward = 2000;
                        String questCompleteRewardSetting = getConfigSetting("New_Player", "QuestCompleteRewardAmount");
                        int questCompleteReward = utils.stringToInt(questCompleteRewardSetting);
                        if (questCompleteReward > 0)
                        {
                            creditsReward = questCompleteReward;
                        }
                        xp.grantSocialStyleXp(self, xpReward, xpAmount);
                        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                        giveTaskCompleteRewardMessage(self, SID_ENTERTAINER, creditsReward);
                        checkForSpecialReward(self, true);
                        setObjVar(self, ENTERTAINER_REWARD_OBJVAR, 6);
                    }
                    else 
                    {
                        giveTaskCompleteNoRewardMessage(self, SID_ENTERTAINER);
                    }
                    removeObjVar(self, ENTERTAINER_QUEST_OBJVAR);
                    removeObjVar(self, "new_player.temp.entertainer_watchers");
                    removeObjVar(self, "new_player.temp.entertainer_listeners");
                    removeCurrentProfessionObjVar(self, ENTERTAINER);
                    dictionary webster = new dictionary();
                    webster.put("profession", ENTERTAINER);
                    messageTo(self, "handleDoQuestComplete", webster, 3, false);
                    return SCRIPT_CONTINUE;
                }
                break;
                default:
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleNewPlayerMedicAction(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, MEDIC_QUEST_OBJVAR))
        {
            int questNum = getIntObjVar(self, MEDIC_QUEST_OBJVAR);
            String xpReward = "";
            int xpAmount = 0;
            int creditsReward = 0;
            prose_package msg = new prose_package();
            switch (questNum)
            {
                case 0:
                break;
                case 1:
                break;
                case 2:
                if (params.containsKey("enteredMedCenter"))
                {
                    String custLogMsg = "New Player Quests: %TU finished medic task 2 - find a medical center.";
                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                    if (getIntObjVar(self, MEDIC_REWARD_OBJVAR) < 2)
                    {
                        xpReward = "medical";
                        xpAmount = 60;
                        creditsReward = 100;
                        xp.grantCombatStyleXp(self, xpReward, xpAmount);
                        money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                        giveTaskCompleteRewardMessage(self, SID_MEDIC, creditsReward);
                        checkForSpecialReward(self, false);
                        setObjVar(self, MEDIC_REWARD_OBJVAR, 2);
                    }
                    else 
                    {
                        giveTaskCompleteNoRewardMessage(self, SID_MEDIC);
                    }
                    setObjVar(self, MEDIC_QUEST_OBJVAR, 3);
                    setObjVar(self, CURRENT_PROFESSION_OBJVAR, MEDIC);
                    messageTo(self, "handleGiveQuestMessage", null, 9, false);
                    return SCRIPT_CONTINUE;
                }
                break;
                case 3:
                if (params.containsKey("diagnosing"))
                {
                    obj_id target = params.getObjId("target");
                    if (isIdValid(target))
                    {
                        if (target != self && isPlayer(target))
                        {
                            String custLogMsg = "New Player Quests: %TU finished medic task 3 - diagnose a player's wounds.";
                            CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                            if (getIntObjVar(self, MEDIC_REWARD_OBJVAR) < 3)
                            {
                                xpReward = "medical";
                                xpAmount = 70;
                                creditsReward = 100;
                                xp.grantCombatStyleXp(self, xpReward, xpAmount);
                                money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                                giveTaskCompleteRewardMessage(self, SID_MEDIC, creditsReward);
                                checkForSpecialReward(self, false);
                                setObjVar(self, MEDIC_REWARD_OBJVAR, 3);
                            }
                            else 
                            {
                                giveTaskCompleteNoRewardMessage(self, SID_MEDIC);
                            }
                            setObjVar(self, MEDIC_QUEST_OBJVAR, 4);
                            setObjVar(self, CURRENT_PROFESSION_OBJVAR, MEDIC);
                            messageTo(self, "handleGiveQuestMessage", null, 9, false);
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
                break;
                case 4:
                if (params.containsKey("tendingWounds"))
                {
                    obj_id target = params.getObjId("target");
                    if (isIdValid(target))
                    {
                        if (target != self)
                        {
                            String custLogMsg = "New Player Quests: %TU finished medic task 4 - tend a player's wounds.";
                            CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                            if (getIntObjVar(self, MEDIC_REWARD_OBJVAR) < 4)
                            {
                                xpReward = "medical";
                                xpAmount = 70;
                                creditsReward = 100;
                                xp.grantCombatStyleXp(self, xpReward, xpAmount);
                                money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                                giveTaskCompleteRewardMessage(self, SID_MEDIC, creditsReward);
                                checkForSpecialReward(self, false);
                                setObjVar(self, MEDIC_REWARD_OBJVAR, 4);
                            }
                            else 
                            {
                                giveTaskCompleteNoRewardMessage(self, SID_MEDIC);
                            }
                            giveStimpackResources(self);
                            setObjVar(self, MEDIC_QUEST_OBJVAR, 5);
                            setObjVar(self, CURRENT_PROFESSION_OBJVAR, MEDIC);
                            messageTo(self, "handleGiveQuestMessage", null, 9, false);
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
                break;
                case 5:
                break;
                case 6:
                if (params.containsKey("tendingWounds"))
                {
                    obj_id target = params.getObjId("target");
                    if (isIdValid(target))
                    {
                        if (target != self)
                        {
                            Vector patientList = new Vector();
                            patientList.setSize(0);
                            if (hasObjVar(self, "new_player.temp.medic"))
                            {
                                patientList = getResizeableObjIdArrayObjVar(self, "new_player.temp.medic");
                            }
                            if (!utils.isElementInArray(patientList, target))
                            {
                                patientList = utils.addElement(patientList, target);
                            }
                            int numPatients = patientList.size();
                            if (numPatients > 0)
                            {
                                setObjVar(self, "new_player.temp.medic", patientList);
                            }
                            if (numPatients >= 5)
                            {
                                String custLogMsg = "New Player Quests: %TU finished the final medic task - tend wounds on 5 players.";
                                CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
                                if (getIntObjVar(self, MEDIC_REWARD_OBJVAR) < 6)
                                {
                                    xpReward = "medical";
                                    xpAmount = 200;
                                    creditsReward = 2000;
                                    String questCompleteRewardSetting = getConfigSetting("New_Player", "QuestCompleteRewardAmount");
                                    int questCompleteReward = utils.stringToInt(questCompleteRewardSetting);
                                    if (questCompleteReward > 0)
                                    {
                                        creditsReward = questCompleteReward;
                                    }
                                    xp.grantCombatStyleXp(self, xpReward, xpAmount);
                                    money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, creditsReward);
                                    giveTaskCompleteRewardMessage(self, SID_MEDIC, creditsReward);
                                    checkForSpecialReward(self, true);
                                    setObjVar(self, MEDIC_REWARD_OBJVAR, 6);
                                }
                                else 
                                {
                                    giveTaskCompleteNoRewardMessage(self, SID_MEDIC);
                                }
                                removeObjVar(self, MEDIC_QUEST_OBJVAR);
                                removeObjVar(self, "new_player.temp.medic");
                                removeCurrentProfessionObjVar(self, MEDIC);
                                dictionary webster = new dictionary();
                                webster.put("profession", MEDIC);
                                messageTo(self, "handleDoQuestComplete", webster, 3, false);
                                return SCRIPT_CONTINUE;
                            }
                        }
                    }
                }
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetupPeriodicProgressCheck(obj_id self, dictionary params) throws InterruptedException
    {
        if (isSpaceScene())
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, veteran_deprecated.OBJVAR_TIME_ACTIVE))
        {
            int accountTime = getIntObjVar(self, veteran_deprecated.OBJVAR_TIME_ACTIVE);
            if (accountTime >= ACCOUNT_AGE_CAP)
            {
                return SCRIPT_CONTINUE;
            }
        }
        int playTime = getPlayerPlayedTime(self);
        if (playTime > PERIODIC_CHECK_CAP)
        {
            return SCRIPT_CONTINUE;
        }
        setInitialProfessionProgress(self, BRAWLER);
        setInitialProfessionProgress(self, MARKSMAN);
        setInitialProfessionProgress(self, SCOUT);
        setInitialProfessionProgress(self, ARTISAN);
        setInitialProfessionProgress(self, ENTERTAINER);
        setInitialProfessionProgress(self, MEDIC);
        int numQuests = getNumProfessionQuests(self);
        if (numQuests > 0)
        {
            utils.setScriptVar(self, "new_player.periodicCheckTime", getGameTime() + PERIODIC_CHECK_DELAY);
            messageTo(self, "handlePeriodicProgressCheck", null, PERIODIC_CHECK_DELAY, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePeriodicProgressCheck(obj_id self, dictionary params) throws InterruptedException
    {
        if (isSpaceScene())
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, veteran_deprecated.OBJVAR_TIME_ACTIVE))
        {
            int accountTime = getIntObjVar(self, veteran_deprecated.OBJVAR_TIME_ACTIVE);
            if (accountTime > ACCOUNT_AGE_CAP)
            {
                return SCRIPT_CONTINUE;
            }
        }
        int playTime = getPlayerPlayedTime(self);
        if (playTime > PERIODIC_CHECK_CAP)
        {
            return SCRIPT_CONTINUE;
        }
        int checkTime = utils.getIntScriptVar(self, "new_player.periodicCheckTime");
        if (checkTime > getGameTime())
        {
            return SCRIPT_CONTINUE;
        }
        boolean progressMade = false;
        int previousQuest = 0;
        int currentQuest = 0;
        boolean brawlerProgess = getProfessionProgress(self, BRAWLER);
        if (brawlerProgess)
        {
            progressMade = true;
        }
        boolean marksmanProgess = getProfessionProgress(self, MARKSMAN);
        if (marksmanProgess)
        {
            progressMade = true;
        }
        boolean scoutProgess = getProfessionProgress(self, SCOUT);
        if (scoutProgess)
        {
            progressMade = true;
        }
        boolean artisanProgess = getProfessionProgress(self, ARTISAN);
        if (artisanProgess)
        {
            progressMade = true;
        }
        boolean entertainerProgess = getProfessionProgress(self, ENTERTAINER);
        if (entertainerProgess)
        {
            progressMade = true;
        }
        boolean medicProgess = getProfessionProgress(self, MEDIC);
        if (medicProgess)
        {
            progressMade = true;
        }
        int numQuests = getNumProfessionQuests(self);
        if (!progressMade)
        {
            String custLogMsg = "New Player Quests: %TU is being prompt for assistance after a periodic progess check.";
            CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, self);
            obj_id helperDroidPcd = getHelperDroidPcd(self);
            if (!isIdValid(helperDroidPcd))
            {
                utils.setScriptVar(self, "new_player.questMessage", true);
                messageTo(self, "handleHelperDroidCheck", null, 1, false);
            }
            else 
            {
                if (callable.hasCDCallable(helperDroidPcd))
                {
                    obj_id helperDroid = callable.getCDCallable(helperDroidPcd);
                    if (isIdValid(helperDroid))
                    {
                        if (numQuests == 1)
                        {
                            giveQuestMessage(self);
                        }
                        else if (numQuests > 1)
                        {
                            string_id greetingEnd = new string_id("new_player", "droid_periodic_check_end");
                            showProfessionsListbox(self, greetingEnd);
                        }
                    }
                }
                else 
                {
                    utils.setScriptVar(self, "new_player.showPeriodicPrompt", true);
                    unpackHelperDroid(self);
                }
            }
        }
        if (numQuests > 0)
        {
            utils.setScriptVar(self, "new_player.periodicCheckTime", getGameTime() + PERIODIC_CHECK_DELAY);
            messageTo(self, "handlePeriodicProgressCheck", null, PERIODIC_CHECK_DELAY, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDroidGreeting(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx < 0)
        {
            if (!utils.hasScriptVar(self, "new_player.listboxNoSelection"))
            {
                utils.setScriptVar(self, "new_player.listboxNoSelection", true);
                string_id noSelection = new string_id("new_player", "listbox_no_selection");
                sendSystemMessage(self, noSelection);
                string_id greetingEnd = new string_id("new_player", "droid_periodic_check_end");
                showProfessionsListbox(self, greetingEnd);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                utils.removeScriptVar(player, "new_player.professionsList");
                utils.removeScriptVar(self, "new_player.listboxNoSelection");
                return SCRIPT_CONTINUE;
            }
        }
        String[] professionsList = utils.getStringArrayScriptVar(self, "new_player.professionsList");
        utils.removeScriptVar(player, "new_player.professionsList");
        if (utils.hasScriptVar(self, "new_player.listboxNoSelection"))
        {
            utils.removeScriptVar(self, "new_player.listboxNoSelection");
        }
        if (professionsList == null || professionsList.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        String capProfession = professionsList[idx];
        String profession = capProfession.toLowerCase();
        if (profession == null || profession.length() == 0)
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(player, CURRENT_PROFESSION_OBJVAR, profession);
        String questObjVar = QUEST_OBJVAR_BASE + profession;
        int questNum = getIntObjVar(self, questObjVar);
        if (questNum > 0)
        {
            giveQuestMessage(self);
        }
        else 
        {
            messageTo(self, "handleGiveNewProfessionQuestPrompt", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void giveQuestMessage(obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, CURRENT_PROFESSION_OBJVAR))
        {
            return;
        }
        String droidName = getDroidName(player);
        String profession = getStringObjVar(player, CURRENT_PROFESSION_OBJVAR);
        String questObjVar = QUEST_OBJVAR_BASE + profession;
        int questNum = getIntObjVar(player, questObjVar);
        String questText = profession + "_quest_0" + questNum;
        if (questNum == 1 && profession.equals("brawler"))
        {
            giveBrawlerWeaponChoice(player);
            return;
        }
        if (questNum == 1 && profession.equals("marksman"))
        {
            giveMarksmanWeaponChoice(player);
            return;
        }
        if (questNum == 1 && (profession.equals("scout") || profession.equals("medic")))
        {
            giveStartingRecommendation(player);
            return;
        }
        if (profession.equals("entertainer") && questNum == 3)
        {
            int species = getSpecies(player);
            String speciesName = utils.getPlayerSpeciesName(species);
            questText = questText + "_" + speciesName;
            String[] speciesThatSkipImageDesignTask = 
            {
                "moncalamari",
                "wookiee",
                "sullustan",
                "ithorian"
            };
            for (int q = 0; q < speciesThatSkipImageDesignTask.length; q++)
            {
                String speciesToCheckFor = speciesThatSkipImageDesignTask[q];
                if (speciesName.equals(speciesToCheckFor))
                {
                    skipImageDesign(player);
                    return;
                }
            }
        }
        if (profession.equals("brawler") && questNum == 6)
        {
            giveFinalQuestMessage(player, questNum);
            return;
        }
        if (profession.equals("marksman") && questNum == 5)
        {
            giveFinalQuestMessage(player, questNum);
            return;
        }
        if (profession.equals("scout") && questNum == 6)
        {
            giveFinalQuestMessage(player, questNum);
            return;
        }
        if (profession.equals("artisan") && questNum == 5)
        {
            giveFinalQuestMessage(player, questNum);
            return;
        }
        if (profession.equals("entertainer") && questNum == 6)
        {
            giveEntertainerFinal(player);
            return;
        }
        if (profession.equals("medic") && questNum == 6)
        {
            giveMedicFinal(player);
            return;
        }
        string_id textMsg = new string_id("new_player", questText);
        string_id okButton = new string_id("new_player", "help_please_button");
        string_id cancelButton = new string_id("new_player", "no_help_needed_button");
        String handler = "handleGiveQuestHelp";
        if (questNum == 0)
        {
            okButton = new string_id("new_player", "lets_start_button");
            cancelButton = new string_id("new_player", "not_now_button");
            handler = "handleNewProfessionGreetingResponse";
        }
        twoButtonSui(player, player, handler, droidName, textMsg, okButton, cancelButton);
        return;
    }
    public void skipImageDesign(obj_id player) throws InterruptedException
    {
        int species = getSpecies(player);
        String speciesName = utils.getPlayerSpeciesName(species);
        String speciesMsg = "entertainer_quest_03_" + speciesName;
        String droidName = getDroidName(player);
        string_id textMsg = new string_id("new_player", speciesMsg);
        string_id okButton = new string_id("new_player", "continue_button");
        String custLogMsg = "New Player Quests: %TU is skipping entertainer task 3 because they are a " + speciesName + ".";
        CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, player);
        oneButtonSui(player, player, "handleImageDesignerSkipped", droidName, textMsg, okButton);
        return;
    }
    public void showDelayedAcceptingQuestMessage(obj_id player) throws InterruptedException
    {
        String droidName = getDroidName(player);
        string_id textMsg = new string_id("new_player", "delayed_accepting_quest");
        string_id okButton = new string_id("new_player", "give_quest_button");
        string_id cancelButton = new string_id("new_player", "thank_you_button");
        twoButtonSui(player, player, "handleDelayedAcceptingQuest", droidName, textMsg, okButton, cancelButton);
        return;
    }
    public void giveBrawlerWeaponChoice(obj_id player) throws InterruptedException
    {
        if (getIntObjVar(player, BRAWLER_QUEST_OBJVAR) != 1)
        {
            giveQuestMessage(player);
            return;
        }
        if (getIntObjVar(player, BRAWLER_REWARD_OBJVAR) >= 1)
        {
            setObjVar(player, BRAWLER_QUEST_OBJVAR, 2);
            giveQuestMessage(player);
            return;
        }
        String droidName = getDroidName(player);
        string_id textMsg = new string_id("new_player", "brawler_quest_01");
        string_id okButton = new string_id("new_player", "heavy_axe_button");
        string_id cancelButton = new string_id("new_player", "survival_knife_button");
        string_id revertButton = new string_id("new_player", "wooden_staff_button");
        threeButtonSui(player, player, "handleBrawlerWeaponChoice", droidName, textMsg, okButton, cancelButton, revertButton);
        return;
    }
    public void giveMarksmanWeaponChoice(obj_id player) throws InterruptedException
    {
        if (getIntObjVar(player, MARKSMAN_QUEST_OBJVAR) != 1)
        {
            giveQuestMessage(player);
            return;
        }
        if (getIntObjVar(player, MARKSMAN_REWARD_OBJVAR) >= 1)
        {
            setObjVar(player, MARKSMAN_QUEST_OBJVAR, 2);
            giveQuestMessage(player);
            return;
        }
        String droidName = getDroidName(player);
        string_id textMsg = new string_id("new_player", "marksman_quest_01");
        string_id okButton = new string_id("new_player", "cdef_carbine_button");
        string_id cancelButton = new string_id("new_player", "cdef_pistol_button");
        string_id revertButton = new string_id("new_player", "cdef_rifle_button");
        threeButtonSui(player, player, "handleMarksmanWeaponChoice", droidName, textMsg, okButton, cancelButton, revertButton);
        return;
    }
    public void giveStartingRecommendation(obj_id player) throws InterruptedException
    {
        String profession = getStringObjVar(player, CURRENT_PROFESSION_OBJVAR);
        if (!profession.equals("scout") && !profession.equals("medic"))
        {
            return;
        }
        String droidName = getDroidName(player);
        String questText = profession + "_quest_01";
        string_id textMsg = new string_id("new_player", questText);
        string_id okButton = new string_id("new_player", "continue_button");
        oneButtonSui(player, player, "handleStartingRecommendation", droidName, textMsg, okButton);
        return;
    }
    public void giveProfessionAddedMessage(obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, PROFESSION_ADDED_OBJVAR))
        {
            return;
        }
        removeObjVar(player, PROFESSION_ADDED_OBJVAR);
        String droidName = getDroidName(player);
        String profession = getStringObjVar(player, CURRENT_PROFESSION_OBJVAR);
        String addedProfessionText = profession + "_added";
        string_id textMsg = new string_id("new_player", addedProfessionText);
        string_id okButton = new string_id("new_player", "lets_start_button");
        string_id cancelButton = new string_id("new_player", "not_now_button");
        twoButtonSui(player, player, "handleNewProfessionGreetingResponse", droidName, textMsg, okButton, cancelButton);
        return;
    }
    public void giveInventoryFullMessage(obj_id player) throws InterruptedException
    {
        String droidName = getDroidName(player);
        string_id textMsg = new string_id("new_player", "inventory_full");
        string_id okButton = new string_id("new_player", "continue_button");
        oneButtonSui(player, player, "noHandler", droidName, textMsg, okButton);
        return;
    }
    public void giveQuestCompleteSui(obj_id player) throws InterruptedException
    {
        String droidName = getDroidName(player);
        String profession = getStringObjVar(player, QUEST_COMPLETE_OBJVAR);
        String completeMsg = profession + "_quest_complete";
        string_id textMsg = new string_id("new_player", completeMsg);
        string_id okButton = new string_id("new_player", "okay_button");
        removeObjVar(player, QUEST_COMPLETE_OBJVAR);
        oneButtonSui(player, player, "noHandler", droidName, textMsg, okButton);
        return;
    }
    public void giveFinalQuestMessage(obj_id player, int questNum) throws InterruptedException
    {
        String profession = getStringObjVar(player, CURRENT_PROFESSION_OBJVAR);
        String droidName = getDroidName(player);
        int numAchieved = 0;
        String finalObjVar = "new_player.temp." + profession;
        if (hasObjVar(player, finalObjVar))
        {
            numAchieved = getIntObjVar(player, finalObjVar);
        }
        String textBodyStr = profession + "_quest_0" + questNum;
        String questCountStr = textBodyStr + "_b";
        string_id textBody = new string_id("new_player", textBodyStr);
        string_id questCount = new string_id("new_player", questCountStr);
        string_id notUpdate = new string_id("new_player", "counter_not_update");
        String textMsgStr = utils.packStringId(textBody) + "         \\#pcontrast1 " + utils.packStringId(questCount) + " " + numAchieved + " " + utils.packStringId(notUpdate) + " \\#. ";
        string_id okButton = new string_id("new_player", "help_please_button");
        string_id cancelButton = new string_id("new_player", "no_help_needed_button");
        twoButtonSui(player, player, "handleGiveQuestHelp", droidName, textMsgStr, okButton, cancelButton);
        return;
    }
    public void giveEntertainerFinal(obj_id player) throws InterruptedException
    {
        String droidName = getDroidName(player);
        int numEntertained = 0;
        int numListeners = 0;
        int numWatchers = 0;
        if (hasObjVar(player, "new_player.temp.entertainer_listeners"))
        {
            obj_id[] listeners = getObjIdArrayObjVar(player, "new_player.temp.entertainer_listeners");
            if (listeners != null)
            {
                numListeners = listeners.length;
            }
        }
        if (hasObjVar(player, "new_player.temp.entertainer_watchers"))
        {
            obj_id[] watchers = getObjIdArrayObjVar(player, "new_player.temp.entertainer_watchers");
            if (watchers != null)
            {
                numWatchers = watchers.length;
            }
        }
        if (numWatchers > numListeners)
        {
            numEntertained = numWatchers;
        }
        else if (numWatchers < numListeners)
        {
            numEntertained = numListeners;
        }
        else if (numWatchers == numListeners)
        {
            numEntertained = numWatchers;
        }
        string_id textBody = new string_id("new_player", "entertainer_quest_06");
        string_id questCount = new string_id("new_player", "entertainer_quest_06_b");
        string_id notUpdate = new string_id("new_player", "counter_not_update");
        String textMsgStr = utils.packStringId(textBody) + "         \\#pcontrast1 " + utils.packStringId(questCount) + " " + numEntertained + " " + utils.packStringId(notUpdate) + " \\#. ";
        string_id okButton = new string_id("new_player", "help_please_button");
        string_id cancelButton = new string_id("new_player", "no_help_needed_button");
        twoButtonSui(player, player, "handleGiveQuestHelp", droidName, textMsgStr, okButton, cancelButton);
        return;
    }
    public void giveMedicFinal(obj_id player) throws InterruptedException
    {
        String droidName = getDroidName(player);
        int numHealed = 0;
        if (hasObjVar(player, "new_player.temp.medic"))
        {
            obj_id[] patients = getObjIdArrayObjVar(player, "new_player.temp.medic");
            if (patients != null)
            {
                numHealed = patients.length;
            }
        }
        string_id textBody = new string_id("new_player", "medic_quest_06");
        string_id questCount = new string_id("new_player", "medic_quest_06_b");
        string_id notUpdate = new string_id("new_player", "counter_not_update");
        String textMsgStr = utils.packStringId(textBody) + "         \\#pcontrast1 " + utils.packStringId(questCount) + " " + numHealed + " " + utils.packStringId(notUpdate) + " \\#. ";
        string_id okButton = new string_id("new_player", "help_please_button");
        string_id cancelButton = new string_id("new_player", "no_help_needed_button");
        twoButtonSui(player, player, "handleGiveQuestHelp", droidName, textMsgStr, okButton, cancelButton);
        return;
    }
    public boolean newPlayerQuestsEnabled() throws InterruptedException
    {
        return false;
    }
    public boolean isOnTutorial(obj_id player) throws InterruptedException
    {
        if (hasScript(player, "theme_park.newbie_tutorial.newbie"))
        {
            return true;
        }
        if (hasScript(player, "theme_park.newbie_tutorial.newbie_skipped"))
        {
            return true;
        }
        return false;
    }
    public void unpackHelperDroid(obj_id player) throws InterruptedException
    {
        if (isSpaceScene())
        {
            return;
        }
        obj_id pcd = getHelperDroidPcd(player);
        int petType = pet_lib.PET_TYPE_DROID;
        if (!pet_lib.hasMaxPets(player, petType))
        {
            pet_lib.createPetFromData(pcd);
        }
        else 
        {
            if (hasObjVar(player, QUEST_COMPLETE_OBJVAR))
            {
                String profession = getStringObjVar(player, QUEST_COMPLETE_OBJVAR);
                dictionary webster = new dictionary();
                webster.put("profession", profession);
                removeObjVar(player, QUEST_COMPLETE_OBJVAR);
                messageTo(player, "handleDoQuestComplete", webster, 1, false);
                return;
            }
            if (hasObjVar(player, PROFESSION_ADDED_OBJVAR))
            {
                giveProfessionAddedMessage(player);
            }
            else 
            {
                giveQuestMessage(player);
            }
        }
        return;
    }
    public obj_id getHelperDroidPcd(obj_id player) throws InterruptedException
    {
        obj_id datapad = utils.getPlayerDatapad(player);
        if (!isIdValid(datapad))
        {
            return null;
        }
        obj_id[] dataContents = getContents(datapad);
        if (dataContents != null && dataContents.length > 0)
        {
            for (int i = 0; i < dataContents.length; i++)
            {
                obj_id data = dataContents[i];
                if (hasObjVar(data, "pet.creatureName"))
                {
                    if (isIdValid(data))
                    {
                        String creatureName = getStringObjVar(data, "pet.creatureName");
                        if (creatureName != null && creatureName.equals("nhelper_droid"))
                        {
                            return data;
                        }
                    }
                }
            }
        }
        return null;
    }
    public void removeCurrentProfessionObjVar(obj_id player, String profession) throws InterruptedException
    {
        if (hasObjVar(player, CURRENT_PROFESSION_OBJVAR))
        {
            String curProfession = getStringObjVar(player, CURRENT_PROFESSION_OBJVAR);
            if (curProfession.equals(profession))
            {
                removeObjVar(player, CURRENT_PROFESSION_OBJVAR);
            }
        }
        return;
    }
    public String getDroidName(obj_id player) throws InterruptedException
    {
        obj_id pcd = getHelperDroidPcd(player);
        String droidName = "a helper droid";
        if (isIdValid(pcd))
        {
            droidName = getAssignedName(pcd);
        }
        return droidName;
    }
    public String getQuestProfession(obj_id self, String noviceSkill) throws InterruptedException
    {
        if (noviceSkill.equals(NOVICE_MARKSMAN))
        {
            return MARKSMAN;
        }
        if (noviceSkill.equals(NOVICE_BRAWLER))
        {
            return BRAWLER;
        }
        if (noviceSkill.equals(NOVICE_MEDIC))
        {
            return MEDIC;
        }
        if (noviceSkill.equals(NOVICE_ARTISAN))
        {
            return ARTISAN;
        }
        if (noviceSkill.equals(NOVICE_ENTERTAINER))
        {
            return ENTERTAINER;
        }
        if (noviceSkill.equals(NOVICE_SCOUT))
        {
            return SCOUT;
        }
        return null;
    }
    public void checkForSpecialReward(obj_id player, boolean finalCompleted) throws InterruptedException
    {
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (!isIdValid(playerInv))
        {
            return;
        }
        obj_id item = null;
        if (!hasObjVar(player, SPECIAL_REWARD_OBJVAR))
        {
            int chance = rand(1, 2);
            if (chance == 1)
            {
                item = createObject(FIREWORKS_TYPE_10, playerInv, "");
            }
            else 
            {
                item = createObject(FIREWORKS_TYPE_18, playerInv, "");
            }
            if (isIdValid(item))
            {
                String custLogMsg = "New Player Quests: %TU was given a special reward: fireworks.";
                CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, player);
                sendSystemMessage(player, new string_id("new_player", "completed_first_task"));
                sendSystemMessage(player, new string_id("new_player", "given_fireworks"));
                setObjVar(player, SPECIAL_REWARD_OBJVAR, 1);
                setObjVar(item, "noTrade", true);
                if (!hasScript(item, "item.special.nomove"))
                {
                    attachScript(item, "item.special.nomove");
                }
            }
        }
        else 
        {
            int rewardNum = getIntObjVar(player, SPECIAL_REWARD_OBJVAR);
            switch (rewardNum)
            {
                case 1:
                item = createObject(CLONING_COUPON, playerInv, "");
                if (isIdValid(item))
                {
                    String custLogMsg = "New Player Quests: %TU was given a special reward: a cloning coupon.";
                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, player);
                    sendSystemMessage(player, new string_id("new_player", "completed_second_task"));
                    sendSystemMessage(player, new string_id("new_player", "given_cloning_coupon"));
                    setObjVar(item, "owner", player);
                    setObjVar(player, SPECIAL_REWARD_OBJVAR, 2);
                    return;
                }
                break;
                case 2:
                item = createObject(TRAVEL_COUPON, playerInv, "");
                if (isIdValid(item))
                {
                    String custLogMsg = "New Player Quests: %TU was given a special reward: a travel voucher.";
                    CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, player);
                    sendSystemMessage(player, new string_id("new_player", "completed_third_task"));
                    sendSystemMessage(player, new string_id("new_player", "given_travel_coupon"));
                    setObjVar(item, "owner", player);
                    setObjVar(player, SPECIAL_REWARD_OBJVAR, 3);
                    return;
                }
                break;
                case 3:
                if (finalCompleted)
                {
                    item = createObject(VEHICLE_COUPON, playerInv, "");
                    if (isIdValid(item))
                    {
                        String custLogMsg = "New Player Quests: %TU was given a special reward: a vehicle rental device.";
                        CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, player);
                        sendSystemMessage(player, new string_id("new_player", "completed_first_profession"));
                        sendSystemMessage(player, new string_id("new_player", "given_vehicle_coupon"));
                        setObjVar(item, "owner", player);
                        setObjVar(player, SPECIAL_REWARD_OBJVAR, 4);
                        return;
                    }
                }
                break;
                case 4:
                break;
            }
        }
        return;
    }
    public void giveStimpackResources(obj_id player) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        if (isIdValid(playerInv))
        {
            obj_id resourceId = pickRandomNonDepeletedResource("organic");
            if (!isIdValid(resourceId))
            {
                resourceId = getResourceTypeByName("organic");
            }
            String crateTemplate = getResourceContainerForType(resourceId);
            obj_id crate = createObject(crateTemplate, playerInv, "");
            addResourceToContainer(crate, resourceId, 8, player);
            resourceId = pickRandomNonDepeletedResource("inorganic");
            if (!isIdValid(resourceId))
            {
                resourceId = getResourceTypeByName("inorganic");
            }
            crateTemplate = getResourceContainerForType(resourceId);
            crate = createObject(crateTemplate, playerInv, "");
            addResourceToContainer(crate, resourceId, 8, player);
        }
        return;
    }
    public void checkForProfessionEquipmentNeeded(obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, CURRENT_PROFESSION_OBJVAR))
        {
            return;
        }
        String profession = getStringObjVar(player, CURRENT_PROFESSION_OBJVAR);
        if (profession.equals(ARTISAN) && !hasObjVar(player, ARTISAN_REWARD_OBJVAR))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                if (!utils.playerHasItemByTemplateInBankOrInventory(player, MINERAL_SURVEY_DEVICE))
                {
                    obj_id surveyDevice = createObject(MINERAL_SURVEY_DEVICE, playerInv, "");
                    if (isIdValid(surveyDevice))
                    {
                        sendSystemMessage(player, new string_id("new_player", "given_survey_device"));
                        setObjVar(player, ARTISAN_REWARD_OBJVAR, 0);
                    }
                }
                if (!utils.playerHasItemByTemplateInBankOrInventory(player, GENERIC_CRAFTING_TOOL))
                {
                    obj_id craftingTool = createObject(GENERIC_CRAFTING_TOOL, playerInv, "");
                    if (isIdValid(craftingTool))
                    {
                        sendSystemMessage(player, new string_id("new_player", "given_crafting_tool"));
                        setObjVar(player, ARTISAN_REWARD_OBJVAR, 0);
                    }
                }
                return;
            }
        }
        if (profession.equals(MEDIC) && !hasObjVar(player, MEDIC_REWARD_OBJVAR))
        {
            if (!utils.playerHasItemByTemplateInBankOrInventory(player, GENERIC_CRAFTING_TOOL))
            {
                obj_id playerInv = getObjectInSlot(player, "inventory");
                if (isIdValid(playerInv))
                {
                    obj_id item = createObject(GENERIC_CRAFTING_TOOL, playerInv, "");
                    if (isIdValid(item))
                    {
                        sendSystemMessage(player, new string_id("new_player", "given_crafting_tool"));
                        setObjVar(player, MEDIC_REWARD_OBJVAR, 0);
                        return;
                    }
                }
            }
        }
        if (profession.equals(SCOUT) && !hasObjVar(player, SCOUT_REWARD_OBJVAR))
        {
            if (!utils.playerHasItemByTemplateInBankOrInventory(player, GENERIC_CRAFTING_TOOL))
            {
                obj_id playerInv = getObjectInSlot(player, "inventory");
                if (isIdValid(playerInv))
                {
                    obj_id item = createObject(GENERIC_CRAFTING_TOOL, playerInv, "");
                    if (isIdValid(item))
                    {
                        sendSystemMessage(player, new string_id("new_player", "given_crafting_tool"));
                        setObjVar(player, SCOUT_REWARD_OBJVAR, 0);
                        return;
                    }
                }
            }
        }
        if (profession.equals(ENTERTAINER) && !hasObjVar(player, ENTERTAINER_REWARD_OBJVAR))
        {
            if (utils.playerHasItemByTemplateInBankOrInventory(player, SLITHERHORN))
            {
                return;
            }
            obj_id rightHandItem = getObjectInSlot(player, "hold_r");
            if (isIdValid(rightHandItem))
            {
                String equippedTemplate = getTemplateName(rightHandItem);
                if (equippedTemplate.equals(SLITHERHORN))
                {
                    return;
                }
            }
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id slitherhorn = createObject(SLITHERHORN, playerInv, "");
                if (isIdValid(slitherhorn))
                {
                    sendSystemMessage(player, new string_id("new_player", "given_slitherhorn"));
                    setObjVar(player, ENTERTAINER_REWARD_OBJVAR, 0);
                    return;
                }
            }
        }
        return;
    }
    public void giveTaskCompleteRewardMessage(obj_id player, string_id profession, int credits) throws InterruptedException
    {
        String droidName = getDroidName(player);
        string_id proseSid = new string_id("new_player", "task_complete_reward_0" + rand(1, 5));
        prose_package msg = prose.getPackage(proseSid, null, droidName, null, null, null, profession, null, null, null, credits, 0.0f);
        sendSystemMessageProse(player, msg);
    }
    public void giveTaskCompleteNoRewardMessage(obj_id player, string_id profession) throws InterruptedException
    {
        String droidName = getDroidName(player);
        string_id proseSid = new string_id("new_player", "task_complete_no_reward_0" + rand(1, 5));
        prose_package msg = prose.getPackage(proseSid, null, droidName, null, null, null, profession, null, null, null, 0, 0.0f);
        sendSystemMessageProse(player, msg);
    }
    public int oneButtonSui(obj_id controller, obj_id player, String handler, string_id title, string_id textMsg, string_id okButton) throws InterruptedException
    {
        String newTitle = utils.packStringId(title);
        return oneButtonSui(controller, player, handler, newTitle, textMsg, okButton);
    }
    public int oneButtonSui(obj_id controller, obj_id player, String handler, String title, string_id textMsg, string_id okButton) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "new_player.openSui"))
        {
            int oldSui = utils.getIntScriptVar(player, "new_player.openSui");
            utils.removeScriptVar(player, "new_player.openSui");
            if (oldSui > -1)
            {
                forceCloseSUIPage(oldSui);
            }
        }
        String TEXT_MSG = utils.packStringId(textMsg);
        String OK_BUTTON = utils.packStringId(okButton);
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, controller, player, handler);
        setSUIProperty(pid, "", "Location", "500,75");
        setSUIProperty(pid, "", "Size", SUI_STARTING_SIZE);
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, TEXT_MSG);
        sui.msgboxButtonSetup(pid, sui.OK_ONLY);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, OK_BUTTON);
        utils.setScriptVar(player, "new_player.openSui", pid);
        sui.showSUIPage(pid);
        playMusic(player, chooseDroidVocals());
        return pid;
    }
    public int twoButtonSui(obj_id controller, obj_id player, String handler, String title, string_id textMsg, string_id okButton, string_id cancelButton) throws InterruptedException
    {
        String TEXT_MSG = utils.packStringId(textMsg);
        return twoButtonSui(controller, player, handler, title, TEXT_MSG, okButton, cancelButton);
    }
    public int twoButtonSui(obj_id controller, obj_id player, String handler, String title, String textMsg, string_id okButton, string_id cancelButton) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "new_player.openSui"))
        {
            int oldSui = utils.getIntScriptVar(player, "new_player.openSui");
            utils.removeScriptVar(player, "new_player.openSui");
            if (oldSui > -1)
            {
                forceCloseSUIPage(oldSui);
            }
        }
        String OK_BUTTON = utils.packStringId(okButton);
        String CANCEL_BUTTON = utils.packStringId(cancelButton);
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, controller, player, handler);
        setSUIProperty(pid, "", "Location", "500,75");
        setSUIProperty(pid, "", "Size", SUI_STARTING_SIZE);
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, textMsg);
        sui.msgboxButtonSetup(pid, sui.YES_NO);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, OK_BUTTON);
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, CANCEL_BUTTON);
        utils.setScriptVar(player, "new_player.openSui", pid);
        sui.showSUIPage(pid);
        playMusic(player, chooseDroidVocals());
        return pid;
    }
    public int threeButtonSui(obj_id controller, obj_id player, String handler, string_id title, string_id textMsg, string_id okButton, string_id cancelButton, string_id revertButton) throws InterruptedException
    {
        String newTitle = utils.packStringId(title);
        return threeButtonSui(controller, player, handler, newTitle, textMsg, okButton, cancelButton, revertButton);
    }
    public int threeButtonSui(obj_id controller, obj_id player, String handler, String title, string_id textMsg, string_id okButton, string_id cancelButton, string_id revertButton) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "new_player.openSui"))
        {
            int oldSui = utils.getIntScriptVar(player, "new_player.openSui");
            utils.removeScriptVar(player, "new_player.openSui");
            if (oldSui > -1)
            {
                forceCloseSUIPage(oldSui);
            }
        }
        String TEXT_MSG = utils.packStringId(textMsg);
        String OK_BUTTON = utils.packStringId(okButton);
        String CANCEL_BUTTON = utils.packStringId(cancelButton);
        String REVERT_BUTTON = utils.packStringId(revertButton);
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, controller, player, handler);
        setSUIProperty(pid, "", "Location", "500,75");
        setSUIProperty(pid, "", "Size", SUI_STARTING_SIZE);
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, TEXT_MSG);
        sui.msgboxButtonSetup(pid, sui.YES_NO_CANCEL);
        setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, sui.PROP_TEXT, REVERT_BUTTON);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, OK_BUTTON);
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, CANCEL_BUTTON);
        setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "OnPress", "RevertWasPressed=1\r\nparent.btnOk.press=t");
        subscribeToSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "RevertWasPressed");
        utils.setScriptVar(player, "new_player.openSui", pid);
        sui.showSUIPage(pid);
        playMusic(player, chooseDroidVocals());
        return pid;
    }
    public void showProfessionsListbox(obj_id player, string_id greetingEnd) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "new_player.openSui"))
        {
            int oldSui = utils.getIntScriptVar(player, "new_player.openSui");
            utils.removeScriptVar(player, "new_player.openSui");
            if (oldSui > -1)
            {
                forceCloseSUIPage(oldSui);
            }
        }
        utils.removeScriptVar(player, "new_player.professionsList");
        String droidName = getDroidName(player);
        String masterName = getName(player);
        int msgNum = rand(1, 3);
        string_id greetingBegin = new string_id("new_player", "droid_greeting_begin_0" + msgNum);
        String TEXT_MSG = utils.packStringId(greetingBegin) + " \\#pcontrast1 " + masterName + " \\#., " + utils.packStringId(greetingEnd);
        int numQuests = getNumProfessionQuests(player);
        Vector tempList = new Vector();
        tempList.setSize(numQuests);
        if (hasObjVar(player, BRAWLER_QUEST_OBJVAR))
        {
            tempList = utils.addElement(tempList, "Brawler");
        }
        if (hasObjVar(player, MARKSMAN_QUEST_OBJVAR))
        {
            tempList = utils.addElement(tempList, "Marksman");
        }
        if (hasObjVar(player, SCOUT_QUEST_OBJVAR))
        {
            tempList = utils.addElement(tempList, "Scout");
        }
        if (hasObjVar(player, ARTISAN_QUEST_OBJVAR))
        {
            tempList = utils.addElement(tempList, "Artisan");
        }
        if (hasObjVar(player, MEDIC_QUEST_OBJVAR))
        {
            tempList = utils.addElement(tempList, "Medic");
        }
        if (hasObjVar(player, ENTERTAINER_QUEST_OBJVAR))
        {
            tempList = utils.addElement(tempList, "Entertainer");
        }
        if (tempList != null || tempList.size() > 0)
        {
            String[] professionsList = utils.toStaticStringArray(tempList);
            int pid = sui.listbox(player, player, TEXT_MSG, sui.OK_CANCEL, droidName, professionsList, "handleDroidGreeting", false, false);
            if (pid > -1)
            {
                utils.setScriptVar(player, "new_player.professionsList", professionsList);
                utils.setScriptVar(player, "new_player.openSui", pid);
                string_id okButton = new string_id("new_player", "default_okay_button");
                String OK_BUTTON = utils.packStringId(okButton);
                setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, OK_BUTTON);
                setSUIProperty(pid, "", "Location", "500,75");
                setSUIProperty(pid, "", "Size", "500,250");
                showSUIPage(pid);
            }
            playMusic(player, chooseDroidVocals());
        }
        return;
    }
    public int getNumProfessionQuests(obj_id player) throws InterruptedException
    {
        int num = 0;
        if (hasObjVar(player, BRAWLER_QUEST_OBJVAR))
        {
            num++;
        }
        if (hasObjVar(player, MARKSMAN_QUEST_OBJVAR))
        {
            num++;
        }
        if (hasObjVar(player, SCOUT_QUEST_OBJVAR))
        {
            num++;
        }
        if (hasObjVar(player, ARTISAN_QUEST_OBJVAR))
        {
            num++;
        }
        if (hasObjVar(player, MEDIC_QUEST_OBJVAR))
        {
            num++;
        }
        if (hasObjVar(player, ENTERTAINER_QUEST_OBJVAR))
        {
            num++;
        }
        return num;
    }
    public void setInitialProfessionProgress(obj_id player, String profession) throws InterruptedException
    {
        String objvarName = QUEST_OBJVAR_BASE + profession;
        String scriptvarName = NEW_PLAYER_OBJVAR_BASE + profession + "Progress";
        if (hasObjVar(player, objvarName))
        {
            int currentQuestNum = getIntObjVar(player, objvarName);
            utils.setScriptVar(player, scriptvarName, currentQuestNum);
        }
        return;
    }
    public boolean getProfessionProgress(obj_id player, String profession) throws InterruptedException
    {
        String objvarName = QUEST_OBJVAR_BASE + profession;
        String scriptvarName = NEW_PLAYER_OBJVAR_BASE + profession + "Progress";
        if (hasObjVar(player, objvarName) || utils.hasScriptVar(player, scriptvarName))
        {
            int previousQuest = 0;
            int currentQuest = 9999;
            if (utils.hasScriptVar(player, scriptvarName))
            {
                previousQuest = utils.getIntScriptVar(player, scriptvarName);
            }
            if (hasObjVar(player, objvarName))
            {
                currentQuest = getIntObjVar(player, objvarName);
                utils.setScriptVar(player, scriptvarName, currentQuest);
            }
            else 
            {
                if (utils.hasScriptVar(player, scriptvarName))
                {
                    utils.removeScriptVar(player, scriptvarName);
                }
            }
            if (currentQuest > previousQuest)
            {
                return true;
            }
        }
        return false;
    }
    public void getProfessionQuestHelp(obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, CURRENT_PROFESSION_OBJVAR))
        {
            return;
        }
        String profession = getStringObjVar(player, CURRENT_PROFESSION_OBJVAR);
        String custLogMsg = "New Player Quests: %TU is asking the helper droid for help with " + profession;
        CustomerServiceLog(NEW_PLAYER_LOG, custLogMsg, player);
        String droidName = getDroidName(player);
        string_id textMsg = new string_id("new_player", "default_text");
        string_id okButton = new string_id("new_player", "okay_button");
        string_id cancelButton = new string_id("new_player", "thank_you_button");
        if (profession.equals(BRAWLER))
        {
            int questNum = getIntObjVar(player, BRAWLER_QUEST_OBJVAR);
            switch (questNum)
            {
                case 0:
                break;
                case 1:
                break;
                case 2:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingBrawler.MeleeTactics");
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingBrawler");
                break;
                case 3:
                textMsg = new string_id("new_player", "brawler_berserk");
                okButton = new string_id("new_player", "general_combat_button");
                twoButtonSui(player, player, "handleShowHolocronGeneralInfo", droidName, textMsg, okButton, cancelButton);
                break;
                case 4:
                textMsg = new string_id("new_player", "brawler_lunge");
                okButton = new string_id("new_player", "general_combat_button");
                twoButtonSui(player, player, "handleShowHolocronGeneralInfo", droidName, textMsg, okButton, cancelButton);
                break;
                case 5:
                textMsg = new string_id("new_player", "brawler_center_of_being");
                okButton = new string_id("new_player", "general_combat_button");
                twoButtonSui(player, player, "handleShowHolocronGeneralInfo", droidName, textMsg, okButton, cancelButton);
                break;
                case 6:
                openHolocronToPage(player, "Combat");
                break;
            }
        }
        else if (profession.equals(MARKSMAN))
        {
            int questNum = getIntObjVar(player, MARKSMAN_QUEST_OBJVAR);
            switch (questNum)
            {
                case 0:
                break;
                case 1:
                break;
                case 2:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingMarksman.RangedTactics");
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingMarksman");
                break;
                case 3:
                textMsg = new string_id("new_player", "marksman_point_blank_single");
                okButton = new string_id("new_player", "general_combat_button");
                twoButtonSui(player, player, "handleShowHolocronGeneralInfo", droidName, textMsg, okButton, cancelButton);
                break;
                case 4:
                textMsg = new string_id("new_player", "marksman_overcharge_shot");
                okButton = new string_id("new_player", "general_combat_button");
                twoButtonSui(player, player, "handleShowHolocronGeneralInfo", droidName, textMsg, okButton, cancelButton);
                break;
                case 5:
                openHolocronToPage(player, "Combat");
                break;
            }
        }
        else if (profession.equals(SCOUT))
        {
            int questNum = getIntObjVar(player, SCOUT_QUEST_OBJVAR);
            switch (questNum)
            {
                case 0:
                break;
                case 1:
                break;
                case 2:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingScout.Harvesting");
                break;
                case 3:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingScout.Harvesting");
                break;
                case 4:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingScout.Crafting");
                break;
                case 5:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingScout.Camps");
                break;
                case 6:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingScout.Camps");
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingScout");
                break;
            }
        }
        else if (profession.equals(ENTERTAINER))
        {
            int questNum = getIntObjVar(player, ENTERTAINER_QUEST_OBJVAR);
            switch (questNum)
            {
                case 0:
                break;
                case 1:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingEntertainer.Instruments");
                break;
                case 2:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingEntertainer.Instruments");
                break;
                case 3:
                textMsg = new string_id("new_player", "entertainer_image_design");
                okButton = new string_id("new_player", "general_entertainer_button");
                twoButtonSui(player, player, "handleShowHolocronGeneralInfo", droidName, textMsg, okButton, cancelButton);
                break;
                case 4:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingEntertainer.Instruments");
                break;
                case 5:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Exploring.FindCantina");
                break;
                case 6:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingEntertainer");
                break;
            }
        }
        else if (profession.equals(ARTISAN))
        {
            int questNum = getIntObjVar(player, ARTISAN_QUEST_OBJVAR);
            switch (questNum)
            {
                case 0:
                break;
                case 1:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingArtisan.Resources");
                break;
                case 2:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingArtisan.Resources");
                break;
                case 3:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingArtisan.Resources");
                break;
                case 4:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingArtisan.Crafting");
                break;
                case 5:
                openHolocronToPage(player, "Crafting.FindingResources");
                openHolocronToPage(player, "Crafting");
                break;
            }
        }
        else if (profession.equals(MEDIC))
        {
            int questNum = getIntObjVar(player, MEDIC_QUEST_OBJVAR);
            switch (questNum)
            {
                case 0:
                break;
                case 1:
                break;
                case 2:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Exploring.FindHospital");
                break;
                case 3:
                textMsg = new string_id("new_player", "medic_diagnose");
                okButton = new string_id("new_player", "general_medic_button");
                twoButtonSui(player, player, "handleShowHolocronGeneralInfo", droidName, textMsg, okButton, cancelButton);
                break;
                case 4:
                textMsg = new string_id("new_player", "medic_tend_wounds");
                okButton = new string_id("new_player", "general_medic_button");
                twoButtonSui(player, player, "handleShowHolocronGeneralInfo", droidName, textMsg, okButton, cancelButton);
                break;
                case 5:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingMedic.DraftSchematics");
                break;
                case 6:
                openHolocronToPage(player, "WelcomeToSWG.WhereDoIBegin.Professions.PlayingMedic.Damage");
                break;
            }
        }
        return;
    }
    public String getDroidName() throws InterruptedException
    {
        String secondLetter = getLetter();
        int secondNum = rand(0, 9);
        if (secondLetter.equals("D"))
        {
            int foo = rand(1, 9);
            if (foo <= 2)
            {
                secondNum = rand(0, 1);
            }
            else 
            {
                secondNum = rand(3, 9);
            }
        }
        return "R2-" + secondLetter + secondNum;
    }
    public String getLetter() throws InterruptedException
    {
        int which = rand(1, 26);
        String letter = "A";
        switch (which)
        {
            case 1:
            letter = "A";
            break;
            case 2:
            letter = "B";
            break;
            case 3:
            letter = "C";
            break;
            case 4:
            letter = "D";
            break;
            case 5:
            letter = "E";
            break;
            case 6:
            letter = "F";
            break;
            case 7:
            letter = "G";
            break;
            case 8:
            letter = "H";
            break;
            case 9:
            letter = "I";
            break;
            case 10:
            letter = "J";
            break;
            case 11:
            letter = "K";
            break;
            case 12:
            letter = "L";
            break;
            case 13:
            letter = "M";
            break;
            case 14:
            letter = "N";
            break;
            case 15:
            letter = "O";
            break;
            case 16:
            letter = "P";
            break;
            case 17:
            letter = "Q";
            break;
            case 18:
            letter = "R";
            break;
            case 19:
            letter = "S";
            break;
            case 20:
            letter = "T";
            break;
            case 21:
            letter = "U";
            break;
            case 22:
            letter = "V";
            break;
            case 23:
            letter = "W";
            break;
            case 24:
            letter = "X";
            break;
            case 25:
            letter = "Y";
            break;
            case 26:
            letter = "Z";
            break;
        }
        return letter;
    }
    public void showSpaceListbox(obj_id player, string_id sid_msg, boolean useGreeting) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "new_player.openSui"))
        {
            int oldSui = utils.getIntScriptVar(player, "new_player.openSui");
            utils.removeScriptVar(player, "new_player.openSui");
            if (oldSui > -1)
            {
                forceCloseSUIPage(oldSui);
            }
        }
        String str_msg = utils.packStringId(sid_msg);
        if (useGreeting)
        {
            String masterName = getName(player);
            int msgNum = rand(1, 3);
            string_id greetingBegin = new string_id("new_player", "droid_greeting_begin_0" + msgNum);
            str_msg = utils.packStringId(greetingBegin) + " \\#pcontrast1 " + masterName + " \\#., " + utils.packStringId(sid_msg);
        }
        string_id sid_ship_option = new string_id("new_player", "space_option_how_to_find_ship");
        string_id sid_email_option = new string_id("new_player", "space_option_how_to_check_email");
        string_id sid_travel_option = new string_id("new_player", "space_option_how_to_travel");
        string_id sid_money_option = new string_id("new_player", "space_option_how_to_make_money");
        String str_droid_name = getDroidName(player);
        String str_ship_option = utils.packStringId(sid_ship_option);
        String str_email_option = utils.packStringId(sid_email_option);
        String str_travel_option = utils.packStringId(sid_travel_option);
        String str_money_option = utils.packStringId(sid_money_option);
        String[] connectionList = 
        {
            str_ship_option,
            str_email_option,
            str_travel_option,
            str_money_option
        };
        int pid = sui.listbox(player, player, str_msg, sui.OK_CANCEL, str_droid_name, connectionList, "handleSpaceIntro", false, false);
        if (pid > -1)
        {
            utils.setScriptVar(player, "new_player.openSui", pid);
            string_id okButton = new string_id("new_player", "default_okay_button");
            String OK_BUTTON = utils.packStringId(okButton);
            setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, OK_BUTTON);
            setSUIProperty(pid, "", "Size", "500,250");
            showSUIPage(pid);
        }
        playMusic(player, chooseDroidVocals());
        return;
    }
    public String chooseDroidVocals() throws InterruptedException
    {
        int choice = rand(1, 3);
        String sound = DROID_VOCALIZE_02;
        switch (choice)
        {
            case 1:
            sound = DROID_VOCALIZE_01;
            break;
            case 2:
            sound = DROID_VOCALIZE_02;
            break;
            case 3:
            sound = DROID_VOCALIZE_03;
            break;
        }
        return sound;
    }
    public int handleSpaceIntro(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        string_id sid_msg = new string_id("new_player", "space_greeting");
        if (idx > -1)
        {
            switch (idx)
            {
                case 0:
                sid_msg = new string_id("new_player", "space_info_how_to_find_ship");
                break;
                case 1:
                sid_msg = new string_id("new_player", "space_info_how_to_check_email");
                break;
                case 2:
                sid_msg = new string_id("new_player", "space_info_how_to_travel");
                break;
                case 3:
                sid_msg = new string_id("new_player", "space_info_how_to_make_money");
                break;
            }
        }
        showSpaceListbox(player, sid_msg, false);
        return SCRIPT_CONTINUE;
    }
    public int handleSpaceIntroBegin(obj_id self, dictionary params) throws InterruptedException
    {
        string_id sid_msg = new string_id("new_player", "space_greeting");
        showSpaceListbox(self, sid_msg, true);
        return SCRIPT_CONTINUE;
    }
}
