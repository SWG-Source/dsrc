package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.HashSet;
import script.library.ai_lib;
import script.library.anims;
import script.library.buff;
import script.library.performance;
import script.library.qa;
import script.library.sui;
import script.library.utils;

public class qa_ai_helper_attach extends script.base_script
{
    public qa_ai_helper_attach()
    {
    }
    public static final int ATTACK_OPTION = 0;
    public static final int PROGRAM_OPTION = 1;
    public static final int DIEINPLACE = 0;
    public static final int HEAL_ALL = 1;
    public static final int FREEZEMOB = 2;
    public static final int FOLLOWTESTER = 3;
    public static final int DANCE_POSTURE = 4;
    public static final int NODEATH = 5;
    public static final int REMOVENODEATH = 6;
    public static final int STARTDATACOLLECTION = 7;
    public static final int STOPSTOREDATA = 8;
    public static final int MOVETOWAYPOINT = 9;
    public static final int ACCESS_ATTACK_MENU = 10;
    public static final int DANCE = 0;
    public static final int STOP_DANCE = 1;
    public static final int FLOURISHES = 2;
    public static final int EXPRESS = 3;
    public static final int POSTURE = 4;
    public static final int PROGRAM_MENU = 5;
    public static final int ATTACK_MENU = 6;
    public static final int FLO_ONE = 0;
    public static final int FLO_TWO = 1;
    public static final int FLO_THREE = 2;
    public static final int FLO_FOUR = 3;
    public static final int FLO_FIVE = 4;
    public static final int FLO_SIX = 5;
    public static final int FLO_SEVEN = 6;
    public static final int FLO_EIGHT = 7;
    public static final int PREVIOUS_MENU = 8;
    public static final int MEDITATING = 0;
    public static final int NPC_DEAD_03 = 1;
    public static final int ENTERTAINED = 2;
    public static final int NPC_SITTING_CHAIR = 3;
    public static final int CROUNCHING = 4;
    public static final int PRONE = 5;
    public static final int SNEAKING = 6;
    public static final int SITTING = 7;
    public static final int PREVIOUS_ANIMATION_MENU = 8;
    public static final int ATTACKME = 0;
    public static final int STOPATTACKING = 1;
    public static final int ACCESS_PROGRAM_MENU = 2;
    public static final String QA_REGEN_OBJVAR = "QARegen";
    public static final float REGEN_RATE = 0;
    public static final String PET_MENU_STRFILE = "pet/pet_menu";
    public static final String ITEM_NAME = "item_n";
    public static final String SCRIPTVAR = "qahelper_attached";
    public static final String SCRIPTVAR_MOB = "qahelper_record";
    public static final int CELEBRATE = 0;
    public static final int CELEBRATE1 = 1;
    public static final int CHECK_WRIST_DEVICE = 2;
    public static final int CLAP_ROUSING = 3;
    public static final int COUGH_POLITE = 4;
    public static final int COVER_EARS_MOCKING = 5;
    public static final int COVER_MOUTH = 6;
    public static final int CURTSEY = 7;
    public static final int CURTSEY1 = 8;
    public static final int DOOR_KNOCK = 9;
    public static final int DOOR_POUND = 10;
    public static final int EMBARRASSED = 11;
    public static final int FLEX_BICEPS = 12;
    public static final int FORCE_ABSORB = 13;
    public static final int FORCE_BLAST = 14;
    public static final int FORCE_CHANNEL = 15;
    public static final int FORCE_CHOKE = 16;
    public static final int FORCE_DAMPEN = 17;
    public static final int FORCE_DESTRUCTION = 18;
    public static final int FORCE_ILLUSION = 19;
    public static final int FORCE_LIGHTNING = 20;
    public static final int FORCE_PERSUASION = 21;
    public static final int FORCE_PROTECTION = 22;
    public static final int FORCE_PULL = 23;
    public static final int FORCE_PUSH = 24;
    public static final int FORCE_STRENGTH = 25;
    public static final int FORCE_THROW = 26;
    public static final int GESTICULATE_WILDLY = 27;
    public static final int HAIR_FLIP = 28;
    public static final int HANDS_ABOVE_HEAD = 29;
    public static final int HANDS_BEHIND_HEAD = 30;
    public static final int HEAVY_COUGH_VOMIT = 31;
    public static final int HOLD_NOSE = 32;
    public static final int HUG_SELF = 33;
    public static final int IMPLORE = 34;
    public static final int KISS_BLOW_KISS = 35;
    public static final int LAUGH_CACKLE = 36;
    public static final int LAUGH_POINTING = 37;
    public static final int LAUGH_TITTER = 38;
    public static final int LISTEN = 39;
    public static final int NOD_HEAD_MULTIPLE = 40;
    public static final int NOD_HEAD_ONCE = 41;
    public static final int OFFER_AFFECTION = 42;
    public static final int PAT_ABDOMEN = 43;
    public static final int POINT_ACCUSINGLY = 44;
    public static final int POINT_AWAY = 45;
    public static final int POINT_DOWN = 46;
    public static final int POINT_FORWARD = 47;
    public static final int POINT_LEFT = 48;
    public static final int POINT_RIGHT = 49;
    public static final int POINT_TO_SELF = 50;
    public static final int POINT_UP = 51;
    public static final int POSE_PROUDLY = 52;
    public static final int POUND_FIST_CHEST = 53;
    public static final int POUND_FIST_PALM = 54;
    public static final int REFUSE_OFFER_AFFECTION = 55;
    public static final int REFUSE_OFFER_FORMAL = 56;
    public static final int RUB_BELLY = 57;
    public static final int RUB_CHIN_THOUGHTFUL = 58;
    public static final int SALUTE1 = 59;
    public static final int SALUTE2 = 60;
    public static final int SCRATCH_HEAD = 61;
    public static final int SEARCH = 62;
    public static final int SHAKE_HEAD_DISGUST = 63;
    public static final int SHAKE_HEAD_NO = 64;
    public static final int SHIVER = 65;
    public static final int SHRUG_HANDS = 66;
    public static final int SHRUG_SHOULDERS = 67;
    public static final int SHUSH = 68;
    public static final int SIGH_DEEPLY = 69;
    public static final int SLIT_THROAT = 70;
    public static final int SLOW_DOWN = 71;
    public static final int SLUMP_HEAD = 72;
    public static final int SMACK_SELF = 73;
    public static final int SMELL_AIR = 74;
    public static final int SMELL_ARMPIT = 75;
    public static final int SNAP_FINGER1 = 76;
    public static final int SNAP_FINGER2 = 77;
    public static final int SNEEZE = 78;
    public static final int STAMP_FEET = 79;
    public static final int STOP = 80;
    public static final int TAP_HEAD = 81;
    public static final int TAUNT1 = 82;
    public static final int TAUNT2 = 83;
    public static final int TAUNT3 = 84;
    public static final int WAVE_FINGER_WARNING = 85;
    public static final int WAVE_HAIL = 86;
    public static final int WAVE_ON_DIRECTING = 87;
    public static final int WAVE_ON_DISMISSING = 88;
    public static final int WAVE1 = 89;
    public static final int WAVE2 = 90;
    public static final int WEEPING = 91;
    public static final int WHISPER = 92;
    public static final int YAWN = 93;
    public static final int BACK = 94;
    public static final int SCRIPTVAR_LENGTH_CAP = 3000;
    public static final String[] AI_MAIN_MENU = 
    {
        "Attack Menu",
        "Program Menu"
    };
    public static final String[] AI_ATTACK_MENU = 
    {
        "Attack me",
        "Stop Combat & Remove Aggro",
        "Go to Program Menu"
    };
    public static final String[] AI_PROGRAM_MENU = 
    {
        "Die in place",
        "Heal All",
        "Stay/Freeze",
        "Follow",
        "NPC Animations",
        "Apply No Death script",
        "Remove No Death script",
        "Start Recording Damage",
        "Export Damage Report",
        "Move to Coordinates",
        "Go to Attack Menu"
    };
    public static final String[] NPC_ANIMATION_MENU = 
    {
        "Dancing",
        "Stop Dancing",
        "Flourish Menu",
        "Expressions",
        "Postures",
        "Go to Program Menu",
        "Go to Attack Menu"
    };
    public static final String[] NPC_POSTURE_MENU = 
    {
        "Meditating",
        "Play Dead",
        "Entertained",
        "Sitting in chair",
        "Crouching",
        "Prone",
        "Sneaking",
        "Sitting",
        "Go to Previous Menu"
    };
    public static final String[] AI_ANIMATIONS = 
    {
        "basic dance",
        "Heal All",
        "Stay/Freeze",
        "Follow",
        "Dance and Postures",
        "Apply No Death script",
        "Remove No Death script",
        "Start Recording Damage",
        "Export Damage Report",
        "Move to Coordinates",
        "Go to Attack Menu"
    };
    public static final String[] AI_FLOURISH_MENU = 
    {
        "Flourish 1",
        "Flourish 2",
        "Flourish 3",
        "Flourish 4",
        "Flourish 5",
        "Flourish 6",
        "Flourish 7",
        "Flourish 8",
        "Go to Previous Menu"
    };
    public static final String[] ENTERTAINER_SKILLS = 
    {
        "social_entertainer_novice",
        "social_entertainer_dance_01",
        "social_entertainer_music_01",
        "social_entertainer_healing_01",
        "social_entertainer_hairstyle_01",
        "social_entertainer_dance_02",
        "social_entertainer_music_02",
        "social_entertainer_healing_02",
        "social_entertainer_hairstyle_02",
        "social_entertainer_dance_03",
        "social_entertainer_music_03",
        "social_entertainer_healing_03",
        "social_entertainer_hairstyle_03",
        "social_entertainer_dance_04",
        "social_entertainer_music_04",
        "social_entertainer_healing_04",
        "social_entertainer_hairstyle_04",
        "social_entertainer_master",
        "social_dancer_novice",
        "social_musician_novice",
        "social_imagedesigner_novice",
        "social_dancer_ability_01",
        "social_dancer_wound_01",
        "social_dancer_knowledge_01",
        "social_dancer_shock_01",
        "social_musician_ability_01",
        "social_musician_wound_01",
        "social_musician_knowledge_01",
        "social_musician_shock_01",
        "social_imagedesigner_hairstyle_01",
        "social_imagedesigner_exotic_01",
        "social_imagedesigner_bodyform_01",
        "social_imagedesigner_markings_01",
        "social_dancer_ability_02",
        "social_dancer_wound_02",
        "social_dancer_knowledge_02",
        "social_dancer_shock_02",
        "social_musician_ability_02",
        "social_musician_wound_02",
        "social_musician_knowledge_02",
        "social_musician_shock_02",
        "social_imagedesigner_hairstyle_02",
        "social_imagedesigner_exotic_02",
        "social_imagedesigner_bodyform_02",
        "social_imagedesigner_markings_02",
        "social_dancer_ability_03",
        "social_dancer_wound_03",
        "social_dancer_knowledge_03",
        "social_dancer_shock_03",
        "social_musician_ability_03",
        "social_musician_wound_03",
        "social_musician_knowledge_03",
        "social_musician_shock_03",
        "social_imagedesigner_hairstyle_03",
        "social_imagedesigner_exotic_03",
        "social_imagedesigner_bodyform_03",
        "social_imagedesigner_markings_03",
        "social_dancer_ability_04",
        "social_dancer_wound_04",
        "social_dancer_knowledge_04",
        "social_dancer_shock_04",
        "social_musician_ability_04",
        "social_musician_wound_04",
        "social_musician_knowledge_04",
        "social_musician_shock_04",
        "social_imagedesigner_hairstyle_04",
        "social_imagedesigner_exotic_04",
        "social_imagedesigner_bodyform_04",
        "social_imagedesigner_markings_04",
        "social_imagedesigner_master",
        "social_musician_master",
        "social_dancer_master"
    };
    public static final String[] EXPRESSION_MENU = 
    {
        "celebrate",
        "celebrate1",
        "check wrist device",
        "clap rousing",
        "cough polite",
        "cover ears mocking",
        "cover mouth",
        "curtsey",
        "curtsey1",
        "door knock",
        "door pound",
        "embarrassed",
        "flex biceps",
        "force absorb",
        "force blast",
        "force channel",
        "force choke",
        "force dampen",
        "force destruction",
        "force illusion",
        "force lightning",
        "force persuasion",
        "force protection",
        "force pull",
        "force push",
        "force strength",
        "force throw",
        "gesticulate wildly",
        "hair flip",
        "hands above head",
        "hands behind head",
        "heavy cough vomit",
        "hold nose",
        "hug self",
        "implore",
        "kiss blow kiss",
        "laugh cackle",
        "laugh pointing",
        "laugh titter",
        "listen",
        "nod head multiple",
        "nod head once",
        "offer affection",
        "pat abdomen",
        "point accusingly",
        "point away",
        "point down",
        "point forward",
        "point left",
        "point right",
        "point to self",
        "point up",
        "pose proudly",
        "pound fist chest",
        "pound fist palm",
        "refuse offer affection",
        "refuse offer formal",
        "rub belly",
        "rub chin thoughtful",
        "salute1",
        "salute2",
        "scratch head",
        "search",
        "shake head disgust",
        "shake head no",
        "shiver",
        "shrug hands",
        "shrug shoulders",
        "shush",
        "sigh deeply",
        "slit throat",
        "slow down",
        "slump head",
        "smack self",
        "smell air",
        "smell armpit",
        "snap finger1",
        "snap finger2",
        "sneeze",
        "stamp feet",
        "stop",
        "tap head",
        "taunt1",
        "taunt2",
        "taunt3",
        "wave finger warning",
        "wave hail",
        "wave on directing",
        "wave on dismissing",
        "wave1",
        "wave2",
        "weeping",
        "whisper",
        "yawn",
        "Previous Menu"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id tester = utils.getObjIdScriptVar(self, "spawnedBy");
        if (isGod(tester))
        {
            if (isIdValid(tester) && exists(tester) && tester.isLoaded() && tester != self)
            {
                float mobSpeed = getRunSpeed(self);
                utils.setScriptVar(self, SCRIPTVAR + "oldRunSpeed", mobSpeed);
                qa.followTester(self, tester);
                doNotAggroTester(self, tester);
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "Tester must be in God Mode to use QA Helper.");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id tester, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("", ""));
        mi.addSubMenu(menu, menu_info_types.SERVER_MENU1, new string_id(PET_MENU_STRFILE, "menu_attack"));
        mi.addSubMenu(menu, menu_info_types.SERVER_MENU2, new string_id(PET_MENU_STRFILE, "menu_command_droid"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id tester, int menu) throws InterruptedException
    {
        if (isGod(tester))
        {
            String creaturePrompt = getCreaturePrompt(self, tester);
            if (menu == menu_info_types.ITEM_USE)
            {
                startLazyMenuFunction(tester, self);
            }
            if (menu == menu_info_types.SERVER_MENU1)
            {
                int pid = sui.listbox(self, tester, creaturePrompt + "\n\rSelect an Attack Option", sui.OK_CANCEL, "Attack Menu", AI_ATTACK_MENU, "attackMenuOptions", false, false);
                utils.setScriptVar(tester, "qahelper_attached.pid", pid);
                sui.showSUIPage(pid);
            }
            if (menu == menu_info_types.SERVER_MENU2)
            {
                int pid = sui.listbox(self, tester, creaturePrompt + "\n\rChoose a Program option", sui.OK_CANCEL, "Program Menu", AI_PROGRAM_MENU, "programMenuOptions", false, false);
                utils.setScriptVar(tester, "qahelper_attached.pid", pid);
                sui.showSUIPage(pid);
            }
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "Tester must be in God Mode to use QA Helper.");
            return SCRIPT_CONTINUE;
        }
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        obj_id tester = utils.getObjIdScriptVar(self, "spawnedBy");
        if (killer == tester)
        {
            removeScriptVarData(self, tester, "Helper Destroyed.");
        }
        exportDamageData(self, tester);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (hasScript(self, "player.yavin_e3") && utils.getBooleanScriptVar(self, SCRIPTVAR_MOB + ".recordDamage") && utils.getObjIdScriptVar(attacker, "spawnedBy") == self)
        {
            sendSystemMessageTestingOnly(self, "Damage numbers will not be accurate due to player.yavin_e3 script attached to your character");
        }
        else 
        {
            if (utils.getObjIdScriptVar(self, "spawnedBy") == attacker)
            {
                if (utils.getBooleanScriptVar(self, SCRIPTVAR_MOB + ".recordDamage"))
                {
                    String testerDamageDone = utils.getStringScriptVar(attacker, SCRIPTVAR_MOB + ".damageDone");
                    String mobDamageDone = utils.getStringScriptVar(self, SCRIPTVAR_MOB + ".damageDone");
                    int mobDamageScriptVarLength = mobDamageDone.length();
                    int testerDamageScriptVarLength = testerDamageDone.length();
                    int mobHealth = utils.getIntScriptVar(self, SCRIPTVAR_MOB + ".healthVar");
                    if (mobDamageScriptVarLength < SCRIPTVAR_LENGTH_CAP && testerDamageScriptVarLength < SCRIPTVAR_LENGTH_CAP)
                    {
                        String weaponString = "";
                        String unlocalizedWeaponName = "";
                        int currentTime = getGameTime();
                        int currentHealth = getAttrib(self, HEALTH);
                        int damageAmount = damage[0];
                        int testerMaxAction = getMaxAction(attacker);
                        int testerUsedAction = calculateActionCost(attacker, testerMaxAction);
                        utils.setScriptVar(self, SCRIPTVAR_MOB + ".healthVar", currentHealth);
                        obj_id objWeapon = getCurrentWeapon(attacker);
                        String objectWeaponName = getName(objWeapon);
                        int staticItem = objectWeaponName.indexOf("static_item_n:");
                        int nonStaticItem = objectWeaponName.indexOf("weapon_name:");
                        if (nonStaticItem > -1)
                        {
                            unlocalizedWeaponName = objectWeaponName.substring(12);
                            weaponString = localize(new string_id("weapon_name", unlocalizedWeaponName));
                        }
                        else if (staticItem > -1)
                        {
                            unlocalizedWeaponName = objectWeaponName.substring(14);
                            weaponString = localize(new string_id("static_item_n", unlocalizedWeaponName));
                        }
                        else if (!unlocalizedWeaponName.equals(""))
                        {
                            weaponString = unlocalizedWeaponName;
                        }
                        else 
                        {
                            weaponString = "Error Retrieving Weapon Data";
                        }
                        String damageData = attacker + "\t" + currentTime + "\t " + damageAmount + "\t" + testerUsedAction + "\t" + weaponString + "\r\n";
                        String appendDamage = "";
                        if (!mobDamageDone.equals("No Data"))
                        {
                            appendDamage = mobDamageDone + damageData;
                        }
                        else 
                        {
                            appendDamage = damageData;
                        }
                        utils.setScriptVar(self, SCRIPTVAR_MOB + ".damageDone", appendDamage);
                        currentHealth = 0;
                        damageAmount = 0;
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        dataStorageCapacityReached(self, attacker);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAiCombatFrame(obj_id self) throws InterruptedException
    {
        obj_id tester = utils.getObjIdScriptVar(self, "spawnedBy");
        if (utils.hasScriptVar(self, SCRIPTVAR_MOB + ".damageDone") && utils.hasScriptVar(tester, SCRIPTVAR_MOB + ".damageDone"))
        {
            String testerDamageDone = utils.getStringScriptVar(tester, SCRIPTVAR_MOB + ".damageDone");
            String mobDamageDone = utils.getStringScriptVar(self, SCRIPTVAR_MOB + ".damageDone");
            int mobDamageScriptVarLength = mobDamageDone.length();
            int testerDamageScriptVarLength = testerDamageDone.length();
            if (mobDamageScriptVarLength >= SCRIPTVAR_LENGTH_CAP || testerDamageScriptVarLength >= SCRIPTVAR_LENGTH_CAP)
            {
                dataStorageCapacityReached(self, tester);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMainMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id tester = utils.getObjIdScriptVar(self, "spawnedBy");
        if (isGod(tester))
        {
            if (utils.hasScriptVar(tester, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    removeScriptVarData(self, tester, "");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String creaturePrompt = getCreaturePrompt(self, tester);
                    switch (idx)
                    {
                        case ATTACK_OPTION:
                        callAttackSUI(self, tester);
                        break;
                        case PROGRAM_OPTION:
                        callProgramOptions(self, tester);
                        break;
                        default:
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "Tester must be in God Mode to use QA Helper.");
        }
        return SCRIPT_CONTINUE;
    }
    public int attackMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id tester = utils.getObjIdScriptVar(self, "spawnedBy");
        if (isGod(tester))
        {
            if (utils.hasScriptVar(tester, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    removeScriptVarData(self, tester, "");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    switch (idx)
                    {
                        case ATTACKME:
                        revertRunSpeed(self);
                        attackTester(self, tester);
                        callAttackSUI(self, tester);
                        break;
                        case STOPATTACKING:
                        qa.stopCreatureCombat(self, tester);
                        callAttackSUI(self, tester);
                        break;
                        case ACCESS_PROGRAM_MENU:
                        callProgramOptions(self, tester);
                        break;
                        default:
                        removeScriptVarData(self, tester, "Default Option on Switch");
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "Tester must be in God Mode to use QA Helper.");
        }
        return SCRIPT_CONTINUE;
    }
    public int programMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id tester = utils.getObjIdScriptVar(self, "spawnedBy");
        if (isGod(tester))
        {
            if (utils.hasScriptVar(tester, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    switch (idx)
                    {
                        case DIEINPLACE:
                        killHelper(self, tester);
                        break;
                        case HEAL_ALL:
                        storeCurrentHealth(self, tester);
                        callProgramOptions(self, tester);
                        break;
                        case FREEZEMOB:
                        freezeMob(self, tester);
                        callProgramOptions(self, tester);
                        break;
                        case FOLLOWTESTER:
                        qa.followTester(self, tester);
                        callProgramOptions(self, tester);
                        break;
                        case DANCE_POSTURE:
                        createAnimationSUI(self, tester);
                        break;
                        case NODEATH:
                        attachNoDeathScript(self, tester);
                        callProgramOptions(self, tester);
                        break;
                        case REMOVENODEATH:
                        removeNoDeathScript(self, tester);
                        callProgramOptions(self, tester);
                        break;
                        case STARTDATACOLLECTION:
                        buff.removeAllBuffs(tester);
                        sendSystemMessageTestingOnly(tester, "All buffs have been removed, please re-apply if needed.");
                        storeCurrentHealth(self, tester);
                        removeActionRegen(tester);
                        recordDamage(self, tester);
                        callProgramOptions(self, tester);
                        break;
                        case STOPSTOREDATA:
                        exportDamageData(self, tester);
                        break;
                        case MOVETOWAYPOINT:
                        toolMovementMenu(self, tester);
                        break;
                        case ACCESS_ATTACK_MENU:
                        callAttackSUI(self, tester);
                        break;
                        default:
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "Tester must be in God Mode to use QA Helper.");
        }
        return SCRIPT_CONTINUE;
    }
    public int animationMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id tester = utils.getObjIdScriptVar(self, "spawnedBy");
        if (isGod(tester))
        {
            if (utils.hasScriptVar(tester, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    switch (idx)
                    {
                        case DANCE:
                        createDanceSUI(self, tester);
                        break;
                        case STOP_DANCE:
                        performance.stopDance(self);
                        createAnimationSUI(self, tester);
                        break;
                        case FLOURISHES:
                        createFlourishSUI(self, tester);
                        break;
                        case EXPRESS:
                        createExpressionSUI(self, tester);
                        break;
                        case POSTURE:
                        createPostureSUI(self, tester);
                        break;
                        case PROGRAM_MENU:
                        callProgramOptions(self, tester);
                        break;
                        case ATTACK_MENU:
                        callAttackSUI(self, tester);
                        break;
                        default:
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "Tester must be in God Mode to use QA Helper.");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMoveToOptions(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id tester = utils.getObjIdScriptVar(self, "spawnedBy");
        if (isGod(tester))
        {
            if (utils.hasScriptVar(tester, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String prevMenuArray[] = utils.getStringArrayScriptVar(tester, SCRIPTVAR + ".moveToMenu");
                if (btn == sui.BP_CANCEL)
                {
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    location waypointWarpLocationArray[] = utils.getLocationArrayScriptVar(tester, SCRIPTVAR + ".warpPoints");
                    location warpSelection = waypointWarpLocationArray[idx];
                    pathTo(self, warpSelection);
                }
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "Tester must be in God Mode to use QA Helper.");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgFlourishSelected(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id tester = utils.getObjIdScriptVar(self, "spawnedBy");
        if (isGod(tester))
        {
            if (utils.hasScriptVar(tester, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    createAnimationSUI(self, tester);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    switch (idx)
                    {
                        case FLO_ONE:
                        performance.flourish(self, 1);
                        createFlourishSUI(self, tester);
                        break;
                        case FLO_TWO:
                        performance.flourish(self, 2);
                        createFlourishSUI(self, tester);
                        break;
                        case FLO_THREE:
                        performance.flourish(self, 3);
                        createFlourishSUI(self, tester);
                        break;
                        case FLO_FOUR:
                        performance.flourish(self, 4);
                        createFlourishSUI(self, tester);
                        break;
                        case FLO_FIVE:
                        performance.flourish(self, 5);
                        createFlourishSUI(self, tester);
                        break;
                        case FLO_SIX:
                        performance.flourish(self, 6);
                        createFlourishSUI(self, tester);
                        break;
                        case FLO_SEVEN:
                        performance.flourish(self, 7);
                        createFlourishSUI(self, tester);
                        break;
                        case FLO_EIGHT:
                        performance.flourish(self, 8);
                        createFlourishSUI(self, tester);
                        break;
                        case PREVIOUS_MENU:
                        createAnimationSUI(self, tester);
                        break;
                        default:
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "Tester must be in God Mode to use QA Helper.");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgPostureSelected(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id tester = utils.getObjIdScriptVar(self, "spawnedBy");
        if (isGod(tester))
        {
            if (utils.hasScriptVar(tester, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    createAnimationSUI(self, tester);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    switch (idx)
                    {
                        case MEDITATING:
                        setPosture(self, POSTURE_SITTING);
                        setAnimationMood(self, "meditating");
                        createPostureSUI(self, tester);
                        break;
                        case NPC_DEAD_03:
                        setPosture(self, POSTURE_UPRIGHT);
                        setAnimationMood(self, "npc_dead_03");
                        createPostureSUI(self, tester);
                        break;
                        case ENTERTAINED:
                        setPosture(self, POSTURE_UPRIGHT);
                        setAnimationMood(self, "entertained");
                        createPostureSUI(self, tester);
                        break;
                        case NPC_SITTING_CHAIR:
                        setPosture(self, POSTURE_UPRIGHT);
                        setAnimationMood(self, "npc_sitting_chair");
                        createPostureSUI(self, tester);
                        break;
                        case CROUNCHING:
                        setPosture(self, POSTURE_CROUCHED);
                        createPostureSUI(self, tester);
                        break;
                        case PRONE:
                        setPosture(self, POSTURE_PRONE);
                        createPostureSUI(self, tester);
                        break;
                        case SNEAKING:
                        setPosture(self, POSTURE_SNEAKING);
                        createPostureSUI(self, tester);
                        break;
                        case SITTING:
                        setPosture(self, POSTURE_SITTING);
                        createPostureSUI(self, tester);
                        break;
                        case PREVIOUS_ANIMATION_MENU:
                        setPosture(self, POSTURE_UPRIGHT);
                        createAnimationSUI(self, tester);
                        break;
                        default:
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "Tester must be in God Mode to use QA Helper.");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgExpressionSelected(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id tester = utils.getObjIdScriptVar(self, "spawnedBy");
        if (isGod(tester))
        {
            if (utils.hasScriptVar(tester, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    createAnimationSUI(self, tester);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    switch (idx)
                    {
                        case CELEBRATE:
                        doAnimationAction(self, anims.PLAYER_CELEBRATE);
                        createExpressionSUI(self, tester);
                        break;
                        case CELEBRATE1:
                        doAnimationAction(self, anims.PLAYER_CELEBRATE1);
                        createExpressionSUI(self, tester);
                        break;
                        case CHECK_WRIST_DEVICE:
                        doAnimationAction(self, anims.PLAYER_CHECK_WRIST_DEVICE);
                        createExpressionSUI(self, tester);
                        break;
                        case CLAP_ROUSING:
                        doAnimationAction(self, anims.PLAYER_CLAP_ROUSING);
                        createExpressionSUI(self, tester);
                        break;
                        case COUGH_POLITE:
                        doAnimationAction(self, anims.PLAYER_COUGH_POLITE);
                        createExpressionSUI(self, tester);
                        break;
                        case COVER_EARS_MOCKING:
                        doAnimationAction(self, anims.PLAYER_COVER_EARS_MOCKING);
                        createExpressionSUI(self, tester);
                        break;
                        case COVER_MOUTH:
                        doAnimationAction(self, anims.PLAYER_COVER_MOUTH);
                        createExpressionSUI(self, tester);
                        break;
                        case CURTSEY:
                        doAnimationAction(self, anims.PLAYER_CURTSEY);
                        createExpressionSUI(self, tester);
                        break;
                        case CURTSEY1:
                        doAnimationAction(self, anims.PLAYER_CURTSEY1);
                        createExpressionSUI(self, tester);
                        break;
                        case DOOR_KNOCK:
                        doAnimationAction(self, anims.PLAYER_DOOR_KNOCK);
                        createExpressionSUI(self, tester);
                        break;
                        case DOOR_POUND:
                        doAnimationAction(self, anims.PLAYER_DOOR_POUND);
                        createExpressionSUI(self, tester);
                        break;
                        case EMBARRASSED:
                        doAnimationAction(self, anims.PLAYER_EMBARRASSED);
                        createExpressionSUI(self, tester);
                        break;
                        case FLEX_BICEPS:
                        doAnimationAction(self, anims.PLAYER_FLEX_BICEPS);
                        createExpressionSUI(self, tester);
                        break;
                        case FORCE_ABSORB:
                        doAnimationAction(self, anims.PLAYER_FORCE_ABSORB);
                        createExpressionSUI(self, tester);
                        break;
                        case FORCE_BLAST:
                        doAnimationAction(self, anims.PLAYER_FORCE_BLAST);
                        createExpressionSUI(self, tester);
                        break;
                        case FORCE_CHANNEL:
                        doAnimationAction(self, anims.PLAYER_FORCE_CHANNEL);
                        createExpressionSUI(self, tester);
                        break;
                        case FORCE_CHOKE:
                        doAnimationAction(self, anims.PLAYER_FORCE_CHOKE);
                        createExpressionSUI(self, tester);
                        break;
                        case FORCE_DAMPEN:
                        doAnimationAction(self, anims.PLAYER_FORCE_DAMPEN);
                        createExpressionSUI(self, tester);
                        break;
                        case FORCE_DESTRUCTION:
                        doAnimationAction(self, anims.PLAYER_FORCE_DESTRUCTION);
                        createExpressionSUI(self, tester);
                        break;
                        case FORCE_ILLUSION:
                        doAnimationAction(self, anims.PLAYER_FORCE_ILLUSION);
                        createExpressionSUI(self, tester);
                        break;
                        case FORCE_LIGHTNING:
                        doAnimationAction(self, anims.PLAYER_FORCE_LIGHTNING);
                        createExpressionSUI(self, tester);
                        break;
                        case FORCE_PERSUASION:
                        doAnimationAction(self, anims.PLAYER_FORCE_PERSUASION);
                        createExpressionSUI(self, tester);
                        break;
                        case FORCE_PROTECTION:
                        doAnimationAction(self, anims.PLAYER_FORCE_PROTECTION);
                        createExpressionSUI(self, tester);
                        break;
                        case FORCE_PULL:
                        doAnimationAction(self, anims.PLAYER_FORCE_PULL);
                        createExpressionSUI(self, tester);
                        break;
                        case FORCE_PUSH:
                        doAnimationAction(self, anims.PLAYER_FORCE_PUSH);
                        createExpressionSUI(self, tester);
                        break;
                        case FORCE_STRENGTH:
                        doAnimationAction(self, anims.PLAYER_FORCE_STRENGTH);
                        createExpressionSUI(self, tester);
                        break;
                        case FORCE_THROW:
                        doAnimationAction(self, anims.PLAYER_FORCE_THROW);
                        createExpressionSUI(self, tester);
                        break;
                        case GESTICULATE_WILDLY:
                        doAnimationAction(self, anims.PLAYER_GESTICULATE_WILDLY);
                        createExpressionSUI(self, tester);
                        break;
                        case HAIR_FLIP:
                        doAnimationAction(self, anims.PLAYER_HAIR_FLIP);
                        createExpressionSUI(self, tester);
                        break;
                        case HANDS_ABOVE_HEAD:
                        doAnimationAction(self, anims.PLAYER_HANDS_ABOVE_HEAD);
                        createExpressionSUI(self, tester);
                        break;
                        case HANDS_BEHIND_HEAD:
                        doAnimationAction(self, anims.PLAYER_HANDS_BEHIND_HEAD);
                        createExpressionSUI(self, tester);
                        break;
                        case HEAVY_COUGH_VOMIT:
                        doAnimationAction(self, anims.PLAYER_HEAVY_COUGH_VOMIT);
                        createExpressionSUI(self, tester);
                        break;
                        case HOLD_NOSE:
                        doAnimationAction(self, anims.PLAYER_HOLD_NOSE);
                        createExpressionSUI(self, tester);
                        break;
                        case HUG_SELF:
                        doAnimationAction(self, anims.PLAYER_HUG_SELF);
                        createExpressionSUI(self, tester);
                        break;
                        case IMPLORE:
                        doAnimationAction(self, anims.PLAYER_IMPLORE);
                        createExpressionSUI(self, tester);
                        break;
                        case KISS_BLOW_KISS:
                        doAnimationAction(self, anims.PLAYER_KISS_BLOW_KISS);
                        createExpressionSUI(self, tester);
                        break;
                        case LAUGH_CACKLE:
                        doAnimationAction(self, anims.PLAYER_LAUGH_CACKLE);
                        createExpressionSUI(self, tester);
                        break;
                        case LAUGH_POINTING:
                        doAnimationAction(self, anims.PLAYER_LAUGH_POINTING);
                        createExpressionSUI(self, tester);
                        break;
                        case LAUGH_TITTER:
                        doAnimationAction(self, anims.PLAYER_LAUGH_TITTER);
                        createExpressionSUI(self, tester);
                        break;
                        case LISTEN:
                        doAnimationAction(self, anims.PLAYER_LISTEN);
                        createExpressionSUI(self, tester);
                        break;
                        case NOD_HEAD_MULTIPLE:
                        doAnimationAction(self, anims.PLAYER_NOD_HEAD_MULTIPLE);
                        createExpressionSUI(self, tester);
                        break;
                        case NOD_HEAD_ONCE:
                        doAnimationAction(self, anims.PLAYER_NOD_HEAD_ONCE);
                        createExpressionSUI(self, tester);
                        break;
                        case OFFER_AFFECTION:
                        doAnimationAction(self, anims.PLAYER_OFFER_AFFECTION);
                        createExpressionSUI(self, tester);
                        break;
                        case PAT_ABDOMEN:
                        doAnimationAction(self, anims.PLAYER_PAT_ABDOMEN);
                        createExpressionSUI(self, tester);
                        break;
                        case POINT_ACCUSINGLY:
                        doAnimationAction(self, anims.PLAYER_POINT_ACCUSINGLY);
                        createExpressionSUI(self, tester);
                        break;
                        case POINT_AWAY:
                        doAnimationAction(self, anims.PLAYER_POINT_AWAY);
                        createExpressionSUI(self, tester);
                        break;
                        case POINT_DOWN:
                        doAnimationAction(self, anims.PLAYER_POINT_DOWN);
                        createExpressionSUI(self, tester);
                        break;
                        case POINT_FORWARD:
                        doAnimationAction(self, anims.PLAYER_POINT_FORWARD);
                        createExpressionSUI(self, tester);
                        break;
                        case POINT_LEFT:
                        doAnimationAction(self, anims.PLAYER_POINT_LEFT);
                        createExpressionSUI(self, tester);
                        break;
                        case POINT_RIGHT:
                        doAnimationAction(self, anims.PLAYER_POINT_RIGHT);
                        createExpressionSUI(self, tester);
                        break;
                        case POINT_TO_SELF:
                        doAnimationAction(self, anims.PLAYER_POINT_TO_SELF);
                        createExpressionSUI(self, tester);
                        break;
                        case POINT_UP:
                        doAnimationAction(self, anims.PLAYER_POINT_UP);
                        createExpressionSUI(self, tester);
                        break;
                        case POSE_PROUDLY:
                        doAnimationAction(self, anims.PLAYER_POSE_PROUDLY);
                        createExpressionSUI(self, tester);
                        break;
                        case POUND_FIST_CHEST:
                        doAnimationAction(self, anims.PLAYER_POUND_FIST_CHEST);
                        createExpressionSUI(self, tester);
                        break;
                        case POUND_FIST_PALM:
                        doAnimationAction(self, anims.PLAYER_POUND_FIST_PALM);
                        createExpressionSUI(self, tester);
                        break;
                        case REFUSE_OFFER_AFFECTION:
                        doAnimationAction(self, anims.PLAYER_REFUSE_OFFER_AFFECTION);
                        createExpressionSUI(self, tester);
                        break;
                        case REFUSE_OFFER_FORMAL:
                        doAnimationAction(self, anims.PLAYER_REFUSE_OFFER_FORMAL);
                        createExpressionSUI(self, tester);
                        break;
                        case RUB_BELLY:
                        doAnimationAction(self, anims.PLAYER_RUB_BELLY);
                        createExpressionSUI(self, tester);
                        break;
                        case RUB_CHIN_THOUGHTFUL:
                        doAnimationAction(self, anims.PLAYER_RUB_CHIN_THOUGHTFUL);
                        createExpressionSUI(self, tester);
                        break;
                        case SALUTE1:
                        doAnimationAction(self, anims.PLAYER_SALUTE1);
                        createExpressionSUI(self, tester);
                        break;
                        case SALUTE2:
                        doAnimationAction(self, anims.PLAYER_SALUTE2);
                        createExpressionSUI(self, tester);
                        break;
                        case SCRATCH_HEAD:
                        doAnimationAction(self, anims.PLAYER_SCRATCH_HEAD);
                        createExpressionSUI(self, tester);
                        break;
                        case SEARCH:
                        doAnimationAction(self, anims.PLAYER_SEARCH);
                        createExpressionSUI(self, tester);
                        break;
                        case SHAKE_HEAD_DISGUST:
                        doAnimationAction(self, anims.PLAYER_SHAKE_HEAD_DISGUST);
                        createExpressionSUI(self, tester);
                        break;
                        case SHAKE_HEAD_NO:
                        doAnimationAction(self, anims.PLAYER_SHAKE_HEAD_NO);
                        createExpressionSUI(self, tester);
                        break;
                        case SHIVER:
                        doAnimationAction(self, anims.PLAYER_SHIVER);
                        createExpressionSUI(self, tester);
                        break;
                        case SHRUG_HANDS:
                        doAnimationAction(self, anims.PLAYER_SHRUG_HANDS);
                        createExpressionSUI(self, tester);
                        break;
                        case SHRUG_SHOULDERS:
                        doAnimationAction(self, anims.PLAYER_SHRUG_SHOULDERS);
                        createExpressionSUI(self, tester);
                        break;
                        case SHUSH:
                        doAnimationAction(self, anims.PLAYER_SHUSH);
                        createExpressionSUI(self, tester);
                        break;
                        case SIGH_DEEPLY:
                        doAnimationAction(self, anims.PLAYER_SIGH_DEEPLY);
                        createExpressionSUI(self, tester);
                        break;
                        case SLIT_THROAT:
                        doAnimationAction(self, anims.PLAYER_SLIT_THROAT);
                        createExpressionSUI(self, tester);
                        break;
                        case SLOW_DOWN:
                        doAnimationAction(self, anims.PLAYER_SLOW_DOWN);
                        createExpressionSUI(self, tester);
                        break;
                        case SLUMP_HEAD:
                        doAnimationAction(self, anims.PLAYER_SLUMP_HEAD);
                        createExpressionSUI(self, tester);
                        break;
                        case SMACK_SELF:
                        doAnimationAction(self, anims.PLAYER_SMACK_SELF);
                        createExpressionSUI(self, tester);
                        break;
                        case SMELL_AIR:
                        doAnimationAction(self, anims.PLAYER_SMELL_AIR);
                        createExpressionSUI(self, tester);
                        break;
                        case SMELL_ARMPIT:
                        doAnimationAction(self, anims.PLAYER_SMELL_ARMPIT);
                        createExpressionSUI(self, tester);
                        break;
                        case SNAP_FINGER1:
                        doAnimationAction(self, anims.PLAYER_SNAP_FINGER1);
                        createExpressionSUI(self, tester);
                        break;
                        case SNAP_FINGER2:
                        doAnimationAction(self, anims.PLAYER_SNAP_FINGER2);
                        createExpressionSUI(self, tester);
                        break;
                        case SNEEZE:
                        doAnimationAction(self, anims.PLAYER_SNEEZE);
                        createExpressionSUI(self, tester);
                        break;
                        case STAMP_FEET:
                        doAnimationAction(self, anims.PLAYER_STAMP_FEET);
                        createExpressionSUI(self, tester);
                        break;
                        case STOP:
                        doAnimationAction(self, anims.PLAYER_STOP);
                        createExpressionSUI(self, tester);
                        break;
                        case TAP_HEAD:
                        doAnimationAction(self, anims.PLAYER_TAP_HEAD);
                        createExpressionSUI(self, tester);
                        break;
                        case TAUNT1:
                        doAnimationAction(self, anims.PLAYER_TAUNT1);
                        createExpressionSUI(self, tester);
                        break;
                        case TAUNT2:
                        doAnimationAction(self, anims.PLAYER_TAUNT2);
                        createExpressionSUI(self, tester);
                        break;
                        case TAUNT3:
                        doAnimationAction(self, anims.PLAYER_TAUNT3);
                        createExpressionSUI(self, tester);
                        break;
                        case WAVE_FINGER_WARNING:
                        doAnimationAction(self, anims.PLAYER_WAVE_FINGER_WARNING);
                        createExpressionSUI(self, tester);
                        break;
                        case WAVE_HAIL:
                        doAnimationAction(self, anims.PLAYER_WAVE_HAIL);
                        createExpressionSUI(self, tester);
                        break;
                        case WAVE_ON_DIRECTING:
                        doAnimationAction(self, anims.PLAYER_WAVE_ON_DIRECTING);
                        createExpressionSUI(self, tester);
                        break;
                        case WAVE_ON_DISMISSING:
                        doAnimationAction(self, anims.PLAYER_WAVE_ON_DISMISSING);
                        createExpressionSUI(self, tester);
                        break;
                        case WAVE1:
                        doAnimationAction(self, anims.PLAYER_WAVE1);
                        createExpressionSUI(self, tester);
                        break;
                        case WAVE2:
                        doAnimationAction(self, anims.PLAYER_WAVE2);
                        createExpressionSUI(self, tester);
                        break;
                        case WEEPING:
                        doAnimationAction(self, anims.PLAYER_WEEPING);
                        createExpressionSUI(self, tester);
                        break;
                        case WHISPER:
                        doAnimationAction(self, anims.PLAYER_WHISPER);
                        createExpressionSUI(self, tester);
                        break;
                        case YAWN:
                        doAnimationAction(self, anims.PLAYER_YAWN);
                        createExpressionSUI(self, tester);
                        break;
                        case BACK:
                        createAnimationSUI(self, tester);
                        break;
                        default:
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "Tester must be in God Mode to use QA Helper.");
        }
        return SCRIPT_CONTINUE;
    }
    public void removeScriptVarData(obj_id self, obj_id tester, String err) throws InterruptedException
    {
        qa.removeScriptVars(tester, SCRIPTVAR);
        utils.removeScriptVarTree(tester, SCRIPTVAR);
        qa.removeScriptVars(self, SCRIPTVAR);
        utils.removeScriptVarTree(self, SCRIPTVAR);
        sendSystemMessageTestingOnly(tester, err);
    }
    public void startLazyMenuFunction(obj_id tester, obj_id self) throws InterruptedException
    {
        if (isGod(tester))
        {
            String creaturePrompt = getCreaturePrompt(self, tester);
            int pid = sui.listbox(self, tester, creaturePrompt + "\n\rSelect a menu option", sui.OK_CANCEL, "Main Menu", AI_MAIN_MENU, "handleMainMenuOptions", false, false);
            utils.setScriptVar(tester, "qahelper_attached.pid", pid);
            sui.showSUIPage(pid);
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "Tester must be in God Mode to use QA Helper.");
        }
    }
    public void stopFollowingTester(obj_id self, obj_id tester) throws InterruptedException
    {
        revertRunSpeed(self);
        ai_lib.aiStopFollowing(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_LOITER);
        sendSystemMessageTestingOnly(tester, "Follow command revoked.  Mobile will now wander.");
    }
    public void revertRunSpeed(obj_id self) throws InterruptedException
    {
        float normalMobRunSpeed = utils.getFloatScriptVar(self, SCRIPTVAR + "oldRunSpeed");
        setBaseRunSpeed(self, normalMobRunSpeed);
    }
    public void doNotAggroTester(obj_id self, obj_id tester) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        removeTriggerVolume(ai_lib.ALERT_VOLUME_NAME);
        removeTriggerVolume(ai_lib.AGGRO_VOLUME_NAME);
        detachScript(self, "ai.creature_combat");
        sendSystemMessageTestingOnly(tester, "Aggressive behaviour has been disabled.");
    }
    public void killHelper(obj_id self, obj_id tester) throws InterruptedException
    {
        if (!isIncapacitated(self))
        {
            if (hasScript(self, "player.yavin_e3"))
            {
                detachScript(self, "player.yavin_e3");
            }
            setPosture(self, POSTURE_INCAPACITATED);
        }
    }
    public void attachNoDeathScript(obj_id self, obj_id tester) throws InterruptedException
    {
        if (hasScript(self, "player.yavin_e3"))
        {
            sendSystemMessageTestingOnly(tester, "No death script already attached.");
        }
        else 
        {
            attachScript(self, "player.yavin_e3");
            sendSystemMessageTestingOnly(tester, "No death script attached.");
        }
    }
    public void removeNoDeathScript(obj_id self, obj_id tester) throws InterruptedException
    {
        if (hasScript(self, "player.yavin_e3"))
        {
            detachScript(self, "player.yavin_e3");
            sendSystemMessageTestingOnly(tester, "No Death Script Removed.");
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "Mobile doesn't have the No Death script.");
        }
    }
    public void freezeMob(obj_id self, obj_id tester) throws InterruptedException
    {
        ai_lib.aiStopFollowing(self);
        setState(self, STATE_FROZEN, true);
        sendSystemMessageTestingOnly(tester, "Helper set to FROZEN. Helper will not move from current location.");
    }
    public String getCreaturePrompt(obj_id creature, obj_id tester) throws InterruptedException
    {
        dictionary creatureDictionary = utils.getDictionaryScriptVar(tester, "qahelper.creatureDictionary");
        if (creatureDictionary.getString("creatureName") != "")
        {
            String combinedData = "";
            String creatureInvul = "";
            String creatureRoot = "";
            String creatureSnare = "";
            String creatureSlow = "";
            String creatureMez = "";
            String creatureArmBreak = "";
            String creatureName = creatureDictionary.getString("creatureName");
            int creatureLevel = creatureDictionary.getInt("BaseLevel");
            int creatureArmorKinetic = creatureDictionary.getInt("armorKinetic");
            int creatureArmorEnergy = creatureDictionary.getInt("armorEnergy");
            int creatureArmorBlast = creatureDictionary.getInt("armorBlast");
            int creatureArmorHeat = creatureDictionary.getInt("armorHeat");
            int creatureArmorCold = creatureDictionary.getInt("armorCold");
            int creatureArmorElectric = creatureDictionary.getInt("armorElectric");
            int creatureArmorAcid = creatureDictionary.getInt("armorAcid");
            int creatureArmorStun = creatureDictionary.getInt("armorStun");
            int creatureInvulnerable = creatureDictionary.getInt("invulnerable");
            if (creatureInvulnerable > 0)
            {
                creatureInvul = "YES";
            }
            else 
            {
                creatureInvul = "NO";
            }
            int creatureRootImmune = creatureDictionary.getInt("rootImmune");
            if (creatureRootImmune > 0)
            {
                creatureRoot = "YES";
            }
            else 
            {
                creatureRoot = "NO";
            }
            int creatureSnareImmune = creatureDictionary.getInt("snareImmune");
            if (creatureSnareImmune > 0)
            {
                creatureSnare = "YES";
            }
            else 
            {
                creatureSnare = "NO";
            }
            int creatureSlowImmune = creatureDictionary.getInt("slowImmune");
            if (creatureSlowImmune > 0)
            {
                creatureSlow = "YES";
            }
            else 
            {
                creatureSlow = "NO";
            }
            int creatureMezImmune = creatureDictionary.getInt("mezImmune");
            if (creatureMezImmune > 0)
            {
                creatureMez = "YES";
            }
            else 
            {
                creatureMez = "NO";
            }
            int creatureArmorBreakImmune = creatureDictionary.getInt("armorBreakImmune");
            if (creatureArmorBreakImmune > 0)
            {
                creatureArmBreak = "YES";
            }
            else 
            {
                creatureArmBreak = "NO";
            }
            String creatureSkillmods = creatureDictionary.getString("skillmods");
            String creatureObjvars = creatureDictionary.getString("objvars");
            String creatureScripts = creatureDictionary.getString("scripts");
            String creaturePrimaryWeapon = creatureDictionary.getString("primary_weapon");
            String creatureSecondaryWeapon = creatureDictionary.getString("secondary_weapon");
            float creatureAggressive = creatureDictionary.getFloat("aggressive");
            float creatureAssist = creatureDictionary.getFloat("assist");
            String creatureDeathBlow = creatureDictionary.getString("death_blow");
            combinedData += "Creature Name: ";
            combinedData += creatureName + "\t\t";
            combinedData += "Creature Level: ";
            combinedData += "" + creatureLevel + "\r\n";
            combinedData += "Creature Invulnerable: ";
            combinedData += creatureInvul + "\r\n";
            combinedData += "Creature Aggressive Value: ";
            combinedData += "" + creatureAggressive + "\r\n";
            combinedData += "Primary Weapon: ";
            combinedData += creaturePrimaryWeapon + "\r\n";
            combinedData += "Secondary Weapon: ";
            combinedData += creatureSecondaryWeapon + "\r\n";
            combinedData += "Kinetic Resist: ";
            combinedData += "" + creatureArmorKinetic + "\r\n";
            combinedData += "Energy Resist: ";
            combinedData += "" + creatureArmorEnergy + "\r\n";
            combinedData += "Blast Resist: ";
            combinedData += "" + creatureArmorBlast + "\r\n";
            combinedData += "Heat Resist: ";
            combinedData += "" + creatureArmorHeat + "\r\n";
            combinedData += "Cold Resist: ";
            combinedData += "" + creatureArmorCold + "\r\n";
            combinedData += "Electric Resist: ";
            combinedData += "" + creatureArmorElectric + "\r\n";
            combinedData += "Acid Resist: ";
            combinedData += "" + creatureArmorAcid + "\r\n";
            combinedData += "Stun Resist: ";
            combinedData += "" + creatureArmorStun + "\r\n";
            combinedData += "Resist Root: ";
            combinedData += creatureRoot + "\r\n";
            combinedData += "Resist Snare: ";
            combinedData += creatureSnare + "\r\n";
            combinedData += "Resist Slow: ";
            combinedData += creatureSlow + "\r\n";
            combinedData += "Resist Mez: ";
            combinedData += creatureMez + "\r\n";
            combinedData += "Resist Armor Break: ";
            combinedData += creatureArmBreak + "\r\n";
            combinedData += "Creature Assist Value: ";
            combinedData += "" + creatureAssist + "\r\n";
            combinedData += "Creature Skill Mods: ";
            combinedData += creatureSkillmods + "\r\n";
            combinedData += "Creature ObjVars: ";
            combinedData += creatureObjvars + "\r\n";
            combinedData += "Creature Special Scripts Attached: ";
            combinedData += creatureScripts + "\r\n";
            if (!combinedData.equals(""))
            {
                return combinedData;
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "The tool failed to retrieve the creature data needed.");
        }
        String errorStr = "There was an error retriving Creature Data";
        return errorStr;
    }
    public boolean checkFaction(obj_id self, obj_id tester) throws InterruptedException
    {
        int testerFaction = pvpGetAlignedFaction(tester);
        int creatureFaction = pvpGetAlignedFaction(self);
        if (testerFaction != 0 && creatureFaction != 0 && testerFaction == creatureFaction)
        {
            return false;
        }
        else if (testerFaction == 0 && creatureFaction != 0)
        {
            return false;
        }
        return true;
    }
    public void attackTester(obj_id self, obj_id tester) throws InterruptedException
    {
        if (checkFaction(self, tester) && !hasObjVar(tester, "gm"))
        {
            dictionary creatureDictionary = utils.getDictionaryScriptVar(tester, "qahelper.creatureDictionary");
            if (creatureDictionary.getString("primary_weapon") != "")
            {
                String creaturePrimaryWeapon = creatureDictionary.getString("primary_weapon");
                if (!creaturePrimaryWeapon.equals("none"))
                {
                    if (!hasScript(self, "ai.creature_combat"))
                    {
                        attachScript(self, "ai.creature_combat");
                    }
                    aiEquipPrimaryWeapon(self);
                    setInvulnerable(self, false);
                    startCombat(self, tester);
                }
                else 
                {
                    sendSystemMessageTestingOnly(tester, "This creature has no primary weapon.");
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(tester, "An error was encountered and no creature data was found.");
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "You must remove AI Ignore or be of the opposing faction before using this function.");
        }
    }
    public void toolMovementMenu(obj_id self, obj_id tester) throws InterruptedException
    {
        obj_id[] waypointArray = qa.getAllValidWaypoints(tester);
        if (waypointArray.length > 0)
        {
            String[] waypointMenu = qa.getMenuList(tester, waypointArray, "waypoint menu");
            location[] waypointWarpLocations = qa.getLocationList(tester, waypointArray);
            utils.setScriptVar(tester, SCRIPTVAR + ".warpPoints", waypointWarpLocations);
            utils.setScriptVar(tester, SCRIPTVAR + ".moveToMenu", waypointMenu);
            int pid = sui.listbox(self, tester, "Select a location you want the Mobile to travel to", sui.OK_CANCEL, "Move To", waypointMenu, "handleMoveToOptions", false, false);
            utils.setScriptVar(tester, "qahelper_attached.pid", pid);
            sui.showSUIPage(pid);
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "You must have at least one waypoint to use this function.");
        }
    }
    public void recordDamage(obj_id self, obj_id tester) throws InterruptedException
    {
        utils.setScriptVar(tester, SCRIPTVAR_MOB + ".recordDamage", true);
        utils.setScriptVar(self, SCRIPTVAR_MOB + ".recordDamage", true);
        utils.setScriptVar(tester, SCRIPTVAR_MOB + ".damageDone", "No Data");
        utils.setScriptVar(self, SCRIPTVAR_MOB + ".damageDone", "No Data");
        sendSystemMessageTestingOnly(tester, "Damage Recording On.");
    }
    public void exportDamageData(obj_id self, obj_id tester) throws InterruptedException
    {
        if (utils.hasScriptVar(self, SCRIPTVAR_MOB + ".recordDamage") && utils.hasScriptVar(tester, SCRIPTVAR_MOB + ".recordDamage"))
        {
            restoreActionRegenRate(tester);
            int currentTime = getGameTime();
            String fileName = getCreatureName(self);
            String testerDamage = utils.getStringScriptVar(tester, SCRIPTVAR_MOB + ".damageDone");
            String mobDamage = utils.getStringScriptVar(self, SCRIPTVAR_MOB + ".damageDone");
            String colNames = "Attacker\tTime\tDamage\tAction\tWeapon Name\n\r";
            String combinedData = "Tester Damage Received \n\r" + colNames + testerDamage + "\n\r Mob Damage Received \n\r" + colNames + mobDamage;
            saveTextOnClient(tester, fileName + "Combat" + currentTime + ".tab", combinedData);
            utils.removeScriptVar(tester, SCRIPTVAR_MOB + ".damageDone");
            utils.removeScriptVar(self, SCRIPTVAR_MOB + ".damageDone");
            utils.removeScriptVar(tester, SCRIPTVAR_MOB + ".recordDamage");
            utils.removeScriptVar(self, SCRIPTVAR_MOB + ".recordDamage");
            utils.removeScriptVar(tester, SCRIPTVAR_MOB + ".healthVar");
            utils.removeScriptVar(self, SCRIPTVAR_MOB + ".healthVar");
            sendSystemMessageTestingOnly(tester, "Damage Recording Off.");
        }
    }
    public void callAttackSUI(obj_id self, obj_id tester) throws InterruptedException
    {
        String creaturePrompt = getCreaturePrompt(self, tester);
        int pidCase1 = sui.listbox(self, tester, creaturePrompt + "\n\rSelect an Attack Option", sui.OK_CANCEL, "Attack Menu", AI_ATTACK_MENU, "attackMenuOptions", false, false);
        utils.setScriptVar(tester, "qahelper_attached.pid", pidCase1);
        sui.showSUIPage(pidCase1);
    }
    public void callProgramOptions(obj_id self, obj_id tester) throws InterruptedException
    {
        String creaturePrompt = getCreaturePrompt(self, tester);
        int pidCase2 = sui.listbox(self, tester, creaturePrompt + "\n\rChoose a Program option", sui.OK_CANCEL, "Program Menu", AI_PROGRAM_MENU, "programMenuOptions", false, false);
        utils.setScriptVar(tester, "qahelper_attached.pid", pidCase2);
        sui.showSUIPage(pidCase2);
    }
    public void dataStorageCapacityReached(obj_id self, obj_id tester) throws InterruptedException
    {
        sendSystemMessageTestingOnly(tester, "Damage data storage capacity reached.  Combat stopped and data stored.");
        exportDamageData(self, tester);
        qa.stopCreatureCombat(self, tester);
        qa.followTester(self, tester);
    }
    public void storeCurrentHealth(obj_id self, obj_id tester) throws InterruptedException
    {
        giveFullHealth(self);
        giveFullHealth(tester);
        int mobHealthMax = getAttrib(self, HEALTH);
        int testerHealthMax = getAttrib(tester, HEALTH);
        utils.setScriptVar(self, SCRIPTVAR_MOB + ".healthVar", mobHealthMax);
        utils.setScriptVar(tester, SCRIPTVAR_MOB + ".healthVar", testerHealthMax);
        sendSystemMessageTestingOnly(tester, "Health set to maximum.");
    }
    public void giveFullHealth(obj_id recipient) throws InterruptedException
    {
        healShockWound(recipient, getShockWound(recipient));
        setAttrib(recipient, HEALTH, getMaxAttrib(recipient, HEALTH));
    }
    public int calculateActionCost(obj_id tester, int testerMaxAction) throws InterruptedException
    {
        int currentAction = getAction(tester);
        int testerUsedAction = testerMaxAction - currentAction;
        setAction(tester, testerMaxAction);
        return testerUsedAction;
    }
    public void setRegenRate(obj_id tester, float regenRate) throws InterruptedException
    {
        getActionRegenRate(tester, regenRate);
    }
    public void removeActionRegen(obj_id tester) throws InterruptedException
    {
        float QARegenRate = getActionRegenRate(tester);
        if (QARegenRate != 0)
        {
            setObjVar(tester, QA_REGEN_OBJVAR, QARegenRate);
        }
        else 
        {
            sendSystemMessageTestingOnly(tester, "Action Regen Rate has already been set to Zero.");
        }
        setRegenRate(tester, REGEN_RATE);
    }
    public void restoreActionRegenRate(obj_id tester) throws InterruptedException
    {
        float oldRegen = getFloatObjVar(tester, "QARegen");
        setRegenRate(tester, oldRegen);
        removeObjVar(tester, "QARegen");
    }
    public boolean getEntertainerSkills(obj_id self, obj_id tester) throws InterruptedException
    {
        for (int i = 0; i < ENTERTAINER_SKILLS.length; i++)
        {
            grantSkill(self, ENTERTAINER_SKILLS[i]);
        }
        return true;
    }
    public void startTheDancing(obj_id self, obj_id tester, String danceName) throws InterruptedException
    {
        int performanceIndex = performance.lookupPerformanceIndex((-1788534963), danceName, 0);
        sendSystemMessageTestingOnly(tester, "idx: " + performanceIndex);
        if (!performance.canPerformDance(self, performanceIndex))
        {
            sendSystemMessageTestingOnly(tester, "can't do it");
        }
        setClientUsesAnimationLocomotion(self, true);
        dictionary params;
        int startTime = getGameTime();
        setPerformanceType(self, performanceIndex);
        setPerformanceStartTime(self, startTime);
        attachScript(self, performance.DANCE_HEARTBEAT_SCRIPT);
        params = new dictionary();
        params.put("performer", self);
        broadcastMessage("handlePerformerStartPerforming", params);
        params = new dictionary();
        params.put("performanceIndex", performanceIndex);
        params.put("sequence", 0);
        messageTo(self, "OnPulse", params, performance.PERFORMANCE_HEARTBEAT_TIME, false);
    }
    public boolean displayAvailableDanceMenu(obj_id self, obj_id tester, boolean changeDance) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return false;
        }
        if (hasObjVar(self, performance.VAR_AVAILABLE_PERFORMANCES))
        {
            return false;
        }
        Vector available_dances = new Vector();
        available_dances.setSize(0);
        int num_items = dataTableGetNumRows(performance.DATATABLE_PERFORMANCE);
        for (int i = 0; i < num_items; i++)
        {
            dictionary row = dataTableGetRow(performance.DATATABLE_PERFORMANCE, i);
            int type = row.getInt("type");
            if (type == (-1788534963))
            {
                String name = row.getString("performanceName");
                if (performance.canPerformDance(self, i + 1))
                {
                    name = (name.substring(0, 1)).toUpperCase() + (name.substring(1)).toLowerCase();
                    available_dances = utils.addElement(available_dances, name);
                }
            }
        }
        if (available_dances.size() > 0)
        {
            sui.listbox(self, tester, "@performance:select_dance", sui.OK_CANCEL, "@performance:available_dances", available_dances, "msgDanceSelected");
            if (!hasScript(self, performance.PERFORMANCE_SELECT))
            {
                attachScript(self, performance.PERFORMANCE_SELECT);
            }
            setObjVar(self, performance.VAR_AVAILABLE_PERFORMANCES, available_dances);
            if (changeDance)
            {
                setObjVar(self, performance.VAR_SELECT_DANCE_CHANGE, 1);
            }
            return true;
        }
        return false;
    }
    public void createFlourishSUI(obj_id self, obj_id tester) throws InterruptedException
    {
        int pid = sui.listbox(self, tester, "Select a Flourish.  This menu will only work for a Helper when they are dancing. \nYou must wait for one flourish to stop before starting a new one.", sui.OK_CANCEL, "Flourish Menu", AI_FLOURISH_MENU, "msgFlourishSelected");
        utils.setScriptVar(tester, "qahelper_attached.pid", pid);
    }
    public void createDanceSUI(obj_id self, obj_id tester) throws InterruptedException
    {
        boolean skillsReceived = getEntertainerSkills(self, tester);
        if (skillsReceived)
        {
            performance.stopDance(self);
            boolean danceUICompleted = displayAvailableDanceMenu(self, tester, false);
            if (danceUICompleted)
            {
                createAnimationSUI(self, tester);
            }
        }
    }
    public void createAnimationSUI(obj_id self, obj_id tester) throws InterruptedException
    {
        int pid = sui.listbox(self, tester, "Select an animation option.\n\nYou will need to select a dance animation before using the flourish menu.", sui.OK_CANCEL, "NPC Animation Menu", NPC_ANIMATION_MENU, "animationMenuOptions", false, false);
        sui.showSUIPage(pid);
        utils.setScriptVar(tester, "qahelper_attached.pid", pid);
    }
    public void createPostureSUI(obj_id self, obj_id tester) throws InterruptedException
    {
        int pid = sui.listbox(self, tester, "Select a posture for the NPC. Some postures will take a few seconds before being performed.", sui.OK_CANCEL, "NPC Posture Menu", NPC_POSTURE_MENU, "msgPostureSelected", false, false);
        sui.showSUIPage(pid);
        utils.setScriptVar(tester, "qahelper_attached.pid", pid);
    }
    public void createExpressionSUI(obj_id self, obj_id tester) throws InterruptedException
    {
        int pid = sui.listbox(self, tester, "Select an expression for the NPC.", sui.OK_CANCEL, "NPC Expression Menu", EXPRESSION_MENU, "msgExpressionSelected", false, false);
        sui.showSUIPage(pid);
        utils.setScriptVar(tester, "qahelper_attached.pid", pid);
    }
}
