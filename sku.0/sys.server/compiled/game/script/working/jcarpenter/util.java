package script.working.jcarpenter;

import script.attribute;
import script.library.create;
import script.library.healing;
import script.library.utils;
import script.location;
import script.obj_id;

import java.util.StringTokenizer;

public class util extends script.base_script
{
    public util()
    {
    }
    public static final String[] HELP_TEXT = 
    {
        "Utility commands:",
        "heal",
        "autoheal",
        "stopautoheal",
        "up",
        "down",
        "eject",
        "warp <speed>",
        "create <resource class>",
        "create <resource name>",
        "create <creature>",
        "create <object template>",
        "destroyObject (target)",
        "grant <profession>",
        "grant help",
        "revoke <profession>",
        "revoke help",
        "wound <attribute> <amount> (target)",
        "wound all <amount> (target)",
        "damage <attribute> <amount> (target)",
        "damage all <amount> (target)",
        "============================"
    };
    public static final String[] PROFESSIONS = 
    {
        "entertainer",
        "scout",
        "medic",
        "artisan",
        "brawler",
        "marksman",
        "rifleman",
        "pistoleer",
        "carbineer",
        "teraskasi",
        "fencer",
        "swordsman",
        "pikeman",
        "dancer",
        "musician",
        "doctor",
        "ranger",
        "creaturehandler",
        "bioengineer",
        "armorsmith",
        "weaponsmith",
        "chef",
        "tailor",
        "architect",
        "droidengineer",
        "bountyhunter",
        "commando",
        "combatmedic",
        "imagedesigner",
        "squadleader",
        "merchant",
        "politician"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        boolean remove = true;
        if (isPlayer(self))
        {
            if (isGod(self))
            {
                int godLevel = getGodLevel(self);
                if (godLevel >= 50)
                {
                    remove = false;
                }
            }
        }
        if (remove)
        {
            sendSystemMessageTestingOnly(self, "...you do not have a sufficient access level to use this script...");
            detachScript(self, "jcarpenter.util");
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "...utility script attached...");
            sendSystemMessageTestingOnly(self, "...say 'help' to see a list of utility commands...");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if ((toLower(text)).equals("heal"))
        {
            healing.fullHeal(self);
            sendSystemMessageTestingOnly(self, "...you have been fully healed...");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(text)).equals("autoheal"))
        {
            utils.setScriptVar(self, "autoheal", 1);
            sendSystemMessageTestingOnly(self, "...you will now be auto-healed...");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(text)).equals("stopautoheal"))
        {
            if (utils.hasScriptVar(self, "autoheal"))
            {
                
            }
            utils.removeScriptVar(self, "autoheal");
            sendSystemMessageTestingOnly(self, "...you will no longer be auto-healed...");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(text)).equals("help"))
        {
            for (String s : HELP_TEXT) {
                sendSystemMessageTestingOnly(self, s);
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(text)).equals("up"))
        {
            if (elevatorMove(self, 1) != 0)
            {
                sendSystemMessageTestingOnly(self, "...you press the up button on a magic elevator...");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(text)).equals("down"))
        {
            if (elevatorMove(self, -1) != 0)
            {
                sendSystemMessageTestingOnly(self, "...you press the down button on a magic elevator...");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(text)).equals("eject"))
        {
            obj_id building = getTopMostContainer(self);
            if (building == null || !isIdValid(building) || building == self)
            {
                return SCRIPT_CONTINUE;
            }
            location ejectLoc = getBuildingEjectLocation(building);
            sendSystemMessageTestingOnly(self, "...you are being ejected from the building...");
            utils.warpPlayer(self, ejectLoc);
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(text)).startsWith("warp"))
        {
            StringTokenizer st = new StringTokenizer(text);
            if (st.countTokens() != 2)
            {
                return SCRIPT_CONTINUE;
            }
            String command = st.nextToken();
            String warpStr = st.nextToken();
            float warp = utils.stringToFloat(warpStr);
            if (warp == Float.NEGATIVE_INFINITY)
            {
                return SCRIPT_CONTINUE;
            }
            warp += 0.05f;
            warp *= 10.0f;
            int chop = (int)warp;
            warp = chop / 10.0f;
            if (warp <= 0.0f)
            {
                return SCRIPT_CONTINUE;
            }
            if (warp > 10.0f)
            {
                warp = 10.0f;
            }
            setMovementPercent(self, warp);
            sendSystemMessageTestingOnly(self, "...you are now running at warp " + warp + "...");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(text)).startsWith("create"))
        {
            StringTokenizer st = new StringTokenizer(text);
            if (st.countTokens() != 2)
            {
                return SCRIPT_CONTINUE;
            }
            String command = st.nextToken();
            String createStr = st.nextToken();
            if (createResource(self, createStr))
            {
                return SCRIPT_CONTINUE;
            }
            else if (createCreature(self, createStr))
            {
                return SCRIPT_CONTINUE;
            }
            else if (createItem(self, createStr))
            {
                return SCRIPT_CONTINUE;
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(text)).equals("destroyobject"))
        {
            if (!isGod(self))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id target = getLookAtTarget(self);
            if (isPlayer(target))
            {
                return SCRIPT_CONTINUE;
            }
            if (destroyObject(target))
            {
                sendSystemMessageTestingOnly(self, "...target destroyed...");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(text)).startsWith("grant"))
        {
            StringTokenizer st = new StringTokenizer(text);
            if (st.countTokens() != 2)
            {
                return SCRIPT_CONTINUE;
            }
            String command = st.nextToken();
            String profession = st.nextToken();
            if (profession.equals("help"))
            {
                sendSystemMessageTestingOnly(self, "These are the professions that can be used with the grant utility:");
                for (String profession1 : PROFESSIONS) {
                    sendSystemMessageTestingOnly(self, profession1);
                }
                return SCRIPT_CONTINUE;
            }
            String[] skills = getSkills(profession);
            if (skills == null || skills.length == 0)
            {
                return SCRIPT_CONTINUE;
            }
            boolean result = true;
            for (String skill : skills) {
                if (!hasSkill(self, skill)) {
                    result &= grantSkill(self, skill);
                }
            }
            if (result)
            {
                sendSystemMessageTestingOnly(self, "...profession successfully granted...");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(text)).startsWith("revoke"))
        {
            StringTokenizer st = new StringTokenizer(text);
            if (st.countTokens() != 2)
            {
                return SCRIPT_CONTINUE;
            }
            String command = st.nextToken();
            String profession = st.nextToken();
            if (profession.equals("help"))
            {
                sendSystemMessageTestingOnly(self, "These are the professions that can be used with the revoke utility:");
                for (String profession1 : PROFESSIONS) {
                    sendSystemMessageTestingOnly(self, profession1);
                }
                return SCRIPT_CONTINUE;
            }
            String[] skills = getSkills(profession);
            if (skills == null || skills.length == 0)
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = (skills.length - 1); i >= 0; i--)
            {
                if (hasSkill(self, skills[i]))
                {
                    revokeSkill(self, skills[i]);
                }
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(text)).startsWith("wound"))
        {
            StringTokenizer st = new StringTokenizer(text);
            if (st.countTokens() != 3)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                return SCRIPT_CONTINUE;
            }
            String command = st.nextToken();
            String attribStr = st.nextToken();
            String amountStr = st.nextToken();
            int[] attrib = 
            {
                0,
                0,
                0,
                0,
                0,
                0
            };
            int amount = utils.stringToInt(amountStr);
            switch (attribStr) {
                case "health":
                    attrib[HEALTH] = amount;
                    break;
                case "constitution":
                    attrib[CONSTITUTION] = amount;
                    break;
                case "action":
                    attrib[ACTION] = amount;
                    break;
                case "stamina":
                    attrib[STAMINA] = amount;
                    break;
                case "mind":
                    attrib[MIND] = amount;
                    break;
                case "willpower":
                    attrib[WILLPOWER] = amount;
                    break;
                case "all":
                    for (int i = 0; i < attrib.length; i++) {
                        attrib[i] = amount;
                    }
                    break;
                default:
                    return SCRIPT_CONTINUE;
            }
            int inc = 1;
            if (!isPlayer(target))
            {
                inc = NUM_ATTRIBUTES_PER_GROUP;
            }
            for (int j = 0; j < attrib.length; j += inc)
            {
                if (attrib[j] > 0)
                {
                    int shock = getShockWound(target);
                    setShockWound(target, shock);
                }
            }
            sendSystemMessageTestingOnly(self, "...target successully wounded...");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(text)).startsWith("damage"))
        {
            StringTokenizer st = new StringTokenizer(text);
            if (st.countTokens() != 3)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                return SCRIPT_CONTINUE;
            }
            String command = st.nextToken();
            String attribStr = st.nextToken();
            String amountStr = st.nextToken();
            attribute[] attrib = getAttribs(target);
            int amount = utils.stringToInt(amountStr);
            switch (attribStr) {
                case "health":
                    attrib[HEALTH] = new attribute(attrib[HEALTH].getType(), (attrib[HEALTH].getValue() - amount));
                    break;
                case "action":
                    attrib[ACTION] = new attribute(attrib[ACTION].getType(), (attrib[ACTION].getValue() - amount));
                    break;
                case "mind":
                    attrib[MIND] = new attribute(attrib[MIND].getType(), (attrib[MIND].getValue() - amount));
                    break;
                case "all":
                    for (int i = 0; i < attrib.length; i += NUM_ATTRIBUTES_PER_GROUP) {
                        attrib[i] = new attribute(attrib[i].getType(), (attrib[i].getValue() - amount));
                    }
                    break;
                default:
                    return SCRIPT_CONTINUE;
            }
            for (int j = 0; j < attrib.length; j += NUM_ATTRIBUTES_PER_GROUP)
            {
                if (attrib[j].getValue() < 1)
                {
                    attrib[j] = new attribute(attrib[j].getType(), 1);
                }
            }
            if (setAttribs(target, attrib))
            {
                sendSystemMessageTestingOnly(self, "...target successully damaged...");
            }
            return SCRIPT_CONTINUE;
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "autoheal"))
        {
            attribute[] maxAttribs = getUnmodifiedMaxAttribs(self);
            attribute[] currentAttribs = getAttribs(self);
            for (int i = 0; i < maxAttribs.length; i += 2)
            {
                if (((float)currentAttribs[i].getValue() / maxAttribs[i].getValue()) < 0.25f)
                {
                    healing.fullHeal(self);
                    sendSystemMessageTestingOnly(self, "...you have been auto-healed...");
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean createResource(obj_id self, String createStr) throws InterruptedException
    {
        if (self == null || !isIdValid(self))
        {
            return false;
        }
        if (createStr == null || createStr.equals(""))
        {
            return false;
        }
        obj_id resourceId = pickRandomNonDepeletedResource(createStr);
        if (resourceId == null || !isIdValid(resourceId))
        {
            resourceId = getResourceTypeByName(createStr);
            if (resourceId == null || !isIdValid(resourceId))
            {
                return false;
            }
        }
        String crateTemplate = getResourceContainerForType(resourceId);
        if (crateTemplate != null && !crateTemplate.equals(""))
        {
            obj_id pInv = utils.getInventoryContainer(self);
            if (pInv != null)
            {
                obj_id crate = createObject(crateTemplate, pInv, "");
                if (addResourceToContainer(crate, resourceId, 100000, self))
                {
                    sendSystemMessageTestingOnly(self, "...resource " + createStr + " created...");
                    return true;
                }
            }
        }
        return false;
    }
    public boolean createCreature(obj_id self, String createStr) throws InterruptedException
    {
        if (self == null || !isIdValid(self))
        {
            return false;
        }
        if (createStr == null || createStr.equals(""))
        {
            return false;
        }
        location here = getLocation(self);
        obj_id creature = create.object(createStr, here);
        if (creature == null || !isIdValid(creature))
        {
            return false;
        }
        sendSystemMessageTestingOnly(self, "..." + createStr + " created...");
        return true;
    }
    public boolean createItem(obj_id self, String createStr) throws InterruptedException
    {
        if (self == null || !isIdValid(self))
        {
            return false;
        }
        if (createStr == null || createStr.equals(""))
        {
            return false;
        }
        location here = getLocation(self);
        obj_id item = createObject(createStr, here);
        if (item == null || !isIdValid(item))
        {
            return false;
        }
        sendSystemMessageTestingOnly(self, "..." + createStr + " created...");
        return true;
    }
    public String[] getSkills(String profession) throws InterruptedException
    {
        final String[][] SKILLS = 
        {
            
            {
                "social_entertainer_novice",
                "social_entertainer_hairstyle_01",
                "social_entertainer_hairstyle_02",
                "social_entertainer_hairstyle_03",
                "social_entertainer_hairstyle_04",
                "social_entertainer_music_01",
                "social_entertainer_music_02",
                "social_entertainer_music_03",
                "social_entertainer_music_04",
                "social_entertainer_dance_01",
                "social_entertainer_dance_02",
                "social_entertainer_dance_03",
                "social_entertainer_dance_04",
                "social_entertainer_healing_01",
                "social_entertainer_healing_02",
                "social_entertainer_healing_03",
                "social_entertainer_healing_04",
                "social_entertainer_master"
            },
            
            {
                "outdoors_scout_novice",
                "outdoors_scout_movement_01",
                "outdoors_scout_movement_02",
                "outdoors_scout_movement_03",
                "outdoors_scout_movement_04",
                "outdoors_scout_tools_01",
                "outdoors_scout_tools_02",
                "outdoors_scout_tools_03",
                "outdoors_scout_tools_04",
                "outdoors_scout_harvest_01",
                "outdoors_scout_harvest_02",
                "outdoors_scout_harvest_03",
                "outdoors_scout_harvest_04",
                "outdoors_scout_camp_01",
                "outdoors_scout_camp_02",
                "outdoors_scout_camp_03",
                "outdoors_scout_camp_04",
                "outdoors_scout_master"
            },
            
            {
                "science_medic_novice",
                "science_medic_injury_01",
                "science_medic_injury_02",
                "science_medic_injury_03",
                "science_medic_injury_04",
                "science_medic_injury_speed_01",
                "science_medic_injury_speed_02",
                "science_medic_injury_speed_03",
                "science_medic_injury_speed_04",
                "science_medic_ability_01",
                "science_medic_ability_02",
                "science_medic_ability_03",
                "science_medic_ability_04",
                "science_medic_crafting_01",
                "science_medic_crafting_02",
                "science_medic_crafting_03",
                "science_medic_crafting_04",
                "science_medic_master"
            },
            
            {
                "crafting_artisan_novice",
                "crafting_artisan_engineering_01",
                "crafting_artisan_engineering_02",
                "crafting_artisan_engineering_03",
                "crafting_artisan_engineering_04",
                "crafting_artisan_domestic_01",
                "crafting_artisan_domestic_02",
                "crafting_artisan_domestic_03",
                "crafting_artisan_domestic_04",
                "crafting_artisan_business_01",
                "crafting_artisan_business_02",
                "crafting_artisan_business_03",
                "crafting_artisan_business_04",
                "crafting_artisan_survey_01",
                "crafting_artisan_survey_02",
                "crafting_artisan_survey_03",
                "crafting_artisan_survey_04",
                "crafting_artisan_master"
            },
            
            {
                "combat_brawler_novice",
                "combat_brawler_unarmed_01",
                "combat_brawler_unarmed_02",
                "combat_brawler_unarmed_03",
                "combat_brawler_unarmed_04",
                "combat_brawler_1handmelee_01",
                "combat_brawler_1handmelee_02",
                "combat_brawler_1handmelee_03",
                "combat_brawler_1handmelee_04",
                "combat_brawler_2handmelee_01",
                "combat_brawler_2handmelee_02",
                "combat_brawler_2handmelee_03",
                "combat_brawler_2handmelee_04",
                "combat_brawler_polearm_01",
                "combat_brawler_polearm_02",
                "combat_brawler_polearm_03",
                "combat_brawler_polearm_04",
                "combat_brawler_master"
            },
            
            {
                "combat_marksman_novice",
                "combat_marksman_rifle_01",
                "combat_marksman_rifle_02",
                "combat_marksman_rifle_03",
                "combat_marksman_rifle_04",
                "combat_marksman_pistol_01",
                "combat_marksman_pistol_02",
                "combat_marksman_pistol_03",
                "combat_marksman_pistol_04",
                "combat_marksman_carbine_01",
                "combat_marksman_carbine_02",
                "combat_marksman_carbine_03",
                "combat_marksman_carbine_04",
                "combat_marksman_support_01",
                "combat_marksman_support_02",
                "combat_marksman_support_03",
                "combat_marksman_support_04",
                "combat_marksman_master"
            },
            
            {
                "combat_rifleman_novice",
                "combat_rifleman_accuracy_01",
                "combat_rifleman_accuracy_02",
                "combat_rifleman_accuracy_03",
                "combat_rifleman_accuracy_04",
                "combat_rifleman_speed_01",
                "combat_rifleman_speed_02",
                "combat_rifleman_speed_03",
                "combat_rifleman_speed_04",
                "combat_rifleman_ability_01",
                "combat_rifleman_ability_02",
                "combat_rifleman_ability_03",
                "combat_rifleman_ability_04",
                "combat_rifleman_support_01",
                "combat_rifleman_support_02",
                "combat_rifleman_support_03",
                "combat_rifleman_support_04",
                "combat_rifleman_master"
            },
            
            {
                "combat_pistol_novice",
                "combat_pistol_accuracy_01",
                "combat_pistol_accuracy_02",
                "combat_pistol_accuracy_03",
                "combat_pistol_accuracy_04",
                "combat_pistol_speed_01",
                "combat_pistol_speed_02",
                "combat_pistol_speed_03",
                "combat_pistol_speed_04",
                "combat_pistol_ability_01",
                "combat_pistol_ability_02",
                "combat_pistol_ability_03",
                "combat_pistol_ability_04",
                "combat_pistol_support_01",
                "combat_pistol_support_02",
                "combat_pistol_support_03",
                "combat_pistol_support_04",
                "combat_pistol_master"
            },
            
            {
                "combat_carbine_novice",
                "combat_carbine_accuracy_01",
                "combat_carbine_accuracy_02",
                "combat_carbine_accuracy_03",
                "combat_carbine_accuracy_04",
                "combat_carbine_speed_01",
                "combat_carbine_speed_02",
                "combat_carbine_speed_03",
                "combat_carbine_speed_04",
                "combat_carbine_ability_01",
                "combat_carbine_ability_02",
                "combat_carbine_ability_03",
                "combat_carbine_ability_04",
                "combat_carbine_support_01",
                "combat_carbine_support_02",
                "combat_carbine_support_03",
                "combat_carbine_support_04",
                "combat_carbine_master"
            },
            
            {
                "combat_unarmed_novice",
                "combat_unarmed_accuracy_01",
                "combat_unarmed_accuracy_02",
                "combat_unarmed_accuracy_03",
                "combat_unarmed_accuracy_04",
                "combat_unarmed_speed_01",
                "combat_unarmed_speed_02",
                "combat_unarmed_speed_03",
                "combat_unarmed_speed_04",
                "combat_unarmed_ability_01",
                "combat_unarmed_ability_02",
                "combat_unarmed_ability_03",
                "combat_unarmed_ability_04",
                "combat_unarmed_support_01",
                "combat_unarmed_support_02",
                "combat_unarmed_support_03",
                "combat_unarmed_support_04",
                "combat_unarmed_master"
            },
            
            {
                "combat_1hsword_novice",
                "combat_1hsword_accuracy_01",
                "combat_1hsword_accuracy_02",
                "combat_1hsword_accuracy_03",
                "combat_1hsword_accuracy_04",
                "combat_1hsword_speed_01",
                "combat_1hsword_speed_02",
                "combat_1hsword_speed_03",
                "combat_1hsword_speed_04",
                "combat_1hsword_ability_01",
                "combat_1hsword_ability_02",
                "combat_1hsword_ability_03",
                "combat_1hsword_ability_04",
                "combat_1hsword_support_01",
                "combat_1hsword_support_02",
                "combat_1hsword_support_03",
                "combat_1hsword_support_04",
                "combat_1hsword_master"
            },
            
            {
                "combat_2hsword_novice",
                "combat_2hsword_accuracy_01",
                "combat_2hsword_accuracy_02",
                "combat_2hsword_accuracy_03",
                "combat_2hsword_accuracy_04",
                "combat_2hsword_speed_01",
                "combat_2hsword_speed_02",
                "combat_2hsword_speed_03",
                "combat_2hsword_speed_04",
                "combat_2hsword_ability_01",
                "combat_2hsword_ability_02",
                "combat_2hsword_ability_03",
                "combat_2hsword_ability_04",
                "combat_2hsword_support_01",
                "combat_2hsword_support_02",
                "combat_2hsword_support_03",
                "combat_2hsword_support_04",
                "combat_2hsword_master"
            },
            
            {
                "combat_polearm_novice",
                "combat_polearm_accuracy_01",
                "combat_polearm_accuracy_02",
                "combat_polearm_accuracy_03",
                "combat_polearm_accuracy_04",
                "combat_polearm_speed_01",
                "combat_polearm_speed_02",
                "combat_polearm_speed_03",
                "combat_polearm_speed_04",
                "combat_polearm_ability_01",
                "combat_polearm_ability_02",
                "combat_polearm_ability_03",
                "combat_polearm_ability_04",
                "combat_polearm_support_01",
                "combat_polearm_support_02",
                "combat_polearm_support_03",
                "combat_polearm_support_04",
                "combat_polearm_master"
            },
            
            {
                "social_dancer_novice",
                "social_dancer_ability_01",
                "social_dancer_ability_02",
                "social_dancer_ability_03",
                "social_dancer_ability_04",
                "social_dancer_wound_01",
                "social_dancer_wound_02",
                "social_dancer_wound_03",
                "social_dancer_wound_04",
                "social_dancer_knowledge_01",
                "social_dancer_knowledge_02",
                "social_dancer_knowledge_03",
                "social_dancer_knowledge_04",
                "social_dancer_shock_01",
                "social_dancer_shock_02",
                "social_dancer_shock_03",
                "social_dancer_shock_04",
                "social_dancer_master"
            },
            
            {
                "social_musician_novice",
                "social_musician_ability_01",
                "social_musician_ability_02",
                "social_musician_ability_03",
                "social_musician_ability_04",
                "social_musician_wound_01",
                "social_musician_wound_02",
                "social_musician_wound_03",
                "social_musician_wound_04",
                "social_musician_knowledge_01",
                "social_musician_knowledge_02",
                "social_musician_knowledge_03",
                "social_musician_knowledge_04",
                "social_musician_shock_01",
                "social_musician_shock_02",
                "social_musician_shock_03",
                "social_musician_shock_04",
                "social_musician_master"
            },
            
            {
                "science_doctor_novice",
                "science_doctor_wound_speed_01",
                "science_doctor_wound_speed_02",
                "science_doctor_wound_speed_03",
                "science_doctor_wound_speed_04",
                "science_doctor_wound_01",
                "science_doctor_wound_02",
                "science_doctor_wound_03",
                "science_doctor_wound_04",
                "science_doctor_ability_01",
                "science_doctor_ability_02",
                "science_doctor_ability_03",
                "science_doctor_ability_04",
                "science_doctor_support_01",
                "science_doctor_support_02",
                "science_doctor_support_03",
                "science_doctor_support_04",
                "science_doctor_master"
            },
            
            {
                "outdoors_ranger_novice",
                "outdoors_ranger_movement_01",
                "outdoors_ranger_movement_02",
                "outdoors_ranger_movement_03",
                "outdoors_ranger_movement_04",
                "outdoors_ranger_tracking_01",
                "outdoors_ranger_tracking_02",
                "outdoors_ranger_tracking_03",
                "outdoors_ranger_tracking_04",
                "outdoors_ranger_harvest_01",
                "outdoors_ranger_harvest_02",
                "outdoors_ranger_harvest_03",
                "outdoors_ranger_harvest_04",
                "outdoors_ranger_support_01",
                "outdoors_ranger_support_02",
                "outdoors_ranger_support_03",
                "outdoors_ranger_support_04",
                "outdoors_ranger_master"
            },
            
            {
                "outdoors_creaturehandler_novice",
                "outdoors_creaturehandler_taming_01",
                "outdoors_creaturehandler_taming_02",
                "outdoors_creaturehandler_taming_03",
                "outdoors_creaturehandler_taming_04",
                "outdoors_creaturehandler_training_01",
                "outdoors_creaturehandler_training_02",
                "outdoors_creaturehandler_training_03",
                "outdoors_creaturehandler_training_04",
                "outdoors_creaturehandler_healing_01",
                "outdoors_creaturehandler_healing_02",
                "outdoors_creaturehandler_healing_03",
                "outdoors_creaturehandler_healing_04",
                "outdoors_creaturehandler_support_01",
                "outdoors_creaturehandler_support_02",
                "outdoors_creaturehandler_support_03",
                "outdoors_creaturehandler_support_04",
                "outdoors_creaturehandler_master"
            },
            
            {
                "outdoors_bio_engineer_novice",
                "outdoors_bio_engineer_creature_01",
                "outdoors_bio_engineer_creature_02",
                "outdoors_bio_engineer_creature_03",
                "outdoors_bio_engineer_creature_04",
                "outdoors_bio_engineer_tissue_01",
                "outdoors_bio_engineer_tissue_02",
                "outdoors_bio_engineer_tissue_03",
                "outdoors_bio_engineer_tissue_04",
                "outdoors_bio_engineer_dna_harvesting_01",
                "outdoors_bio_engineer_dna_harvesting_02",
                "outdoors_bio_engineer_dna_harvesting_03",
                "outdoors_bio_engineer_dna_harvesting_04",
                "outdoors_bio_engineer_production_01",
                "outdoors_bio_engineer_production_02",
                "outdoors_bio_engineer_production_03",
                "outdoors_bio_engineer_production_04",
                "outdoors_bio_engineer_master"
            },
            
            {
                "crafting_armorsmith_novice",
                "crafting_armorsmith_personal_01",
                "crafting_armorsmith_personal_02",
                "crafting_armorsmith_personal_03",
                "crafting_armorsmith_personal_04",
                "crafting_armorsmith_heavy_01",
                "crafting_armorsmith_heavy_02",
                "crafting_armorsmith_heavy_03",
                "crafting_armorsmith_heavy_04",
                "crafting_armorsmith_deflectors_01",
                "crafting_armorsmith_deflectors_02",
                "crafting_armorsmith_deflectors_03",
                "crafting_armorsmith_deflectors_04",
                "crafting_armorsmith_complexity_01",
                "crafting_armorsmith_complexity_02",
                "crafting_armorsmith_complexity_03",
                "crafting_armorsmith_complexity_04",
                "crafting_armorsmith_master"
            },
            
            {
                "crafting_weaponsmith_novice",
                "crafting_weaponsmith_melee_01",
                "crafting_weaponsmith_melee_02",
                "crafting_weaponsmith_melee_03",
                "crafting_weaponsmith_melee_04",
                "crafting_weaponsmith_firearms_01",
                "crafting_weaponsmith_firearms_02",
                "crafting_weaponsmith_firearms_03",
                "crafting_weaponsmith_firearms_04",
                "crafting_weaponsmith_munitions_01",
                "crafting_weaponsmith_munitions_02",
                "crafting_weaponsmith_munitions_03",
                "crafting_weaponsmith_munitions_04",
                "crafting_weaponsmith_techniques_01",
                "crafting_weaponsmith_techniques_02",
                "crafting_weaponsmith_techniques_03",
                "crafting_weaponsmith_techniques_04",
                "crafting_weaponsmith_master"
            },
            
            {
                "crafting_chef_novice",
                "crafting_chef_dish_01",
                "crafting_chef_dish_02",
                "crafting_chef_dish_03",
                "crafting_chef_dish_04",
                "crafting_chef_dessert_01",
                "crafting_chef_dessert_02",
                "crafting_chef_dessert_03",
                "crafting_chef_dessert_04",
                "crafting_chef_drink_01",
                "crafting_chef_drink_02",
                "crafting_chef_drink_03",
                "crafting_chef_drink_04",
                "crafting_chef_techniques_01",
                "crafting_chef_techniques_02",
                "crafting_chef_techniques_03",
                "crafting_chef_techniques_04",
                "crafting_chef_master"
            },
            
            {
                "crafting_tailor_novice",
                "crafting_tailor_casual_01",
                "crafting_tailor_casual_02",
                "crafting_tailor_casual_03",
                "crafting_tailor_casual_04",
                "crafting_tailor_field_01",
                "crafting_tailor_field_02",
                "crafting_tailor_field_03",
                "crafting_tailor_field_04",
                "crafting_tailor_formal_01",
                "crafting_tailor_formal_02",
                "crafting_tailor_formal_03",
                "crafting_tailor_formal_04",
                "crafting_tailor_production_01",
                "crafting_tailor_production_02",
                "crafting_tailor_production_03",
                "crafting_tailor_production_04",
                "crafting_tailor_master"
            },
            
            {
                "crafting_architect_novice",
                "crafting_architect_production_01",
                "crafting_architect_production_02",
                "crafting_architect_production_03",
                "crafting_architect_production_04",
                "crafting_architect_techniques_01",
                "crafting_architect_techniques_02",
                "crafting_architect_techniques_03",
                "crafting_architect_techniques_04",
                "crafting_architect_harvesting_01",
                "crafting_architect_harvesting_02",
                "crafting_architect_harvesting_03",
                "crafting_architect_harvesting_04",
                "crafting_architect_blueprints_01",
                "crafting_architect_blueprints_02",
                "crafting_architect_blueprints_03",
                "crafting_architect_blueprints_04",
                "crafting_architect_master"
            },
            
            {
                "crafting_droidengineer_novice",
                "crafting_droidengineer_production_01",
                "crafting_droidengineer_production_02",
                "crafting_droidengineer_production_03",
                "crafting_droidengineer_production_04",
                "crafting_droidengineer_techniques_01",
                "crafting_droidengineer_techniques_02",
                "crafting_droidengineer_techniques_03",
                "crafting_droidengineer_techniques_04",
                "crafting_droidengineer_refinement_01",
                "crafting_droidengineer_refinement_02",
                "crafting_droidengineer_refinement_03",
                "crafting_droidengineer_refinement_04",
                "crafting_droidengineer_blueprints_01",
                "crafting_droidengineer_blueprints_02",
                "crafting_droidengineer_blueprints_03",
                "crafting_droidengineer_blueprints_04",
                "crafting_droidengineer_master"
            },
            
            {
                "combat_bountyhunter_novice",
                "combat_bountyhunter_investigation_01",
                "combat_bountyhunter_investigation_02",
                "combat_bountyhunter_investigation_03",
                "combat_bountyhunter_investigation_04",
                "combat_bountyhunter_droidcontrol_01",
                "combat_bountyhunter_droidcontrol_02",
                "combat_bountyhunter_droidcontrol_03",
                "combat_bountyhunter_droidcontrol_04",
                "combat_bountyhunter_droidresponse_01",
                "combat_bountyhunter_droidresponse_02",
                "combat_bountyhunter_droidresponse_03",
                "combat_bountyhunter_droidresponse_04",
                "combat_bountyhunter_support_01",
                "combat_bountyhunter_support_02",
                "combat_bountyhunter_support_03",
                "combat_bountyhunter_support_04",
                "combat_bountyhunter_master"
            },
            
            {
                "combat_commando_novice",
                "combat_commando_heavyweapon_accuracy_01",
                "combat_commando_heavyweapon_accuracy_02",
                "combat_commando_heavyweapon_accuracy_03",
                "combat_commando_heavyweapon_accuracy_04",
                "combat_commando_heavyweapon_speed_01",
                "combat_commando_heavyweapon_speed_02",
                "combat_commando_heavyweapon_speed_03",
                "combat_commando_heavyweapon_speed_04",
                "combat_commando_thrownweapon_01",
                "combat_commando_thrownweapon_02",
                "combat_commando_thrownweapon_03",
                "combat_commando_thrownweapon_04",
                "combat_commando_support_01",
                "combat_commando_support_02",
                "combat_commando_support_03",
                "combat_commando_support_04",
                "combat_commando_master"
            },
            
            {
                "science_combatmedic_novice",
                "science_combatmedic_healing_range_01",
                "science_combatmedic_healing_range_02",
                "science_combatmedic_healing_range_03",
                "science_combatmedic_healing_range_04",
                "science_combatmedic_healing_range_speed_01",
                "science_combatmedic_healing_range_speed_02",
                "science_combatmedic_healing_range_speed_03",
                "science_combatmedic_healing_range_speed_04",
                "science_combatmedic_medicine_01",
                "science_combatmedic_medicine_02",
                "science_combatmedic_medicine_03",
                "science_combatmedic_medicine_04",
                "science_combatmedic_support_01",
                "science_combatmedic_support_02",
                "science_combatmedic_support_03",
                "science_combatmedic_support_04",
                "science_combatmedic_master"
            },
            
            {
                "social_imagedesigner_novice",
                "social_imagedesigner_hairstyle_01",
                "social_imagedesigner_hairstyle_02",
                "social_imagedesigner_hairstyle_03",
                "social_imagedesigner_hairstyle_04",
                "social_imagedesigner_exotic_01",
                "social_imagedesigner_exotic_02",
                "social_imagedesigner_exotic_03",
                "social_imagedesigner_exotic_04",
                "social_imagedesigner_bodyform_01",
                "social_imagedesigner_bodyform_02",
                "social_imagedesigner_bodyform_03",
                "social_imagedesigner_bodyform_04",
                "social_imagedesigner_markings_01",
                "social_imagedesigner_markings_02",
                "social_imagedesigner_markings_03",
                "social_imagedesigner_markings_04",
                "social_imagedesigner_master"
            },
            
            {
                "outdoors_squadleader_novice",
                "outdoors_squadleader_movement_01",
                "outdoors_squadleader_movement_02",
                "outdoors_squadleader_movement_03",
                "outdoors_squadleader_movement_04",
                "outdoors_squadleader_offense_01",
                "outdoors_squadleader_offense_02",
                "outdoors_squadleader_offense_03",
                "outdoors_squadleader_offense_04",
                "outdoors_squadleader_defense_01",
                "outdoors_squadleader_defense_02",
                "outdoors_squadleader_defense_03",
                "outdoors_squadleader_defense_04",
                "outdoors_squadleader_support_01",
                "outdoors_squadleader_support_02",
                "outdoors_squadleader_support_03",
                "outdoors_squadleader_support_04",
                "outdoors_squadleader_master"
            },
            
            {
                "crafting_merchant_novice",
                "crafting_merchant_advertising_01",
                "crafting_merchant_advertising_02",
                "crafting_merchant_advertising_03",
                "crafting_merchant_advertising_04",
                "crafting_merchant_sales_01",
                "crafting_merchant_sales_02",
                "crafting_merchant_sales_03",
                "crafting_merchant_sales_04",
                "crafting_merchant_hiring_01",
                "crafting_merchant_hiring_02",
                "crafting_merchant_hiring_03",
                "crafting_merchant_hiring_04",
                "crafting_merchant_management_01",
                "crafting_merchant_management_02",
                "crafting_merchant_management_03",
                "crafting_merchant_management_04",
                "crafting_merchant_master"
            },
            
            {
                "social_politician_novice",
                "social_politician_fiscal_01",
                "social_politician_fiscal_02",
                "social_politician_fiscal_03",
                "social_politician_fiscal_04",
                "social_politician_martial_01",
                "social_politician_martial_02",
                "social_politician_martial_03",
                "social_politician_martial_04",
                "social_politician_civic_01",
                "social_politician_civic_02",
                "social_politician_civic_03",
                "social_politician_civic_04",
                "social_politician_urban_01",
                "social_politician_urban_02",
                "social_politician_urban_03",
                "social_politician_urban_04",
                "social_politician_master"
            }
        };
        if (profession == null || profession.equals(""))
        {
            return null;
        }
        for (int i = 0; i < PROFESSIONS.length; i++)
        {
            if (profession.equals(PROFESSIONS[i]))
            {
                return SKILLS[i];
            }
        }
        return null;
    }
}
