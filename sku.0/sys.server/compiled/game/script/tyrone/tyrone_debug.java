package script.tyrone; //oops

import script.*;
import java.util.Arrays;

import java.util.StringTokenizer;
import script.library.utils;
import script.library.bio_engineer;
import script.library.craftinglib;
import script.library.create;
import script.library.chat;
import script.library.loot;
import script.library.pet_lib;
import script.library.static_item;
import script.library.trial;

public class tyrone_debug extends script.base_script
{
    public tyrone_debug()
    {
    }
    public static final String[] TYRONECOMMANDTITLE = 
    {
        "Tyrone's Debug Script: Commands"
    };
    public static final String[] TYRONECOMMANDPROMPT = 
    {
        " grantAllSkillsCombat -- Grants all professions that are combat related. (excludes entertainer).",
        " grantAllSkillsEntertainer -- Grants Entertainer.",
        " grantAllSkillsCrafting -- Grants All four crafting professions.",
        " setInteresting -- Sets Look-at target to have Interesting Disk above object/creature.",
        " clearInteresting -- Clears Look-at target's Interesting Disk above object/creature.",
        " setSpaceInteresting -- Sets Look-at target to have Space Interesting Disk above object/creature.",
        " clearSpaceInteresting -- Clears Look-at target's Space Interesting Disk above object/creature.",
        " makeDroid-T -- Makes a crafting droid with datapad and inventory storage.",
        " tyronesTestQuests -- Grant Tyrone's testing quests (requires client patch).",
        " persist -- persist your look-at target",
        " persistArea -- WARNING: do not use unless you are on character_farm or godclient_test",
        " chatTest -- Make someone say whatever you input",
        " playCEF -- plays a client effect. EX: jawa_love. (Note: do not include clienteffect/ or .cef)",
        " playSound -- plays a snd file. EX: jawa_surprise. (Note: Use full sound/path.snd path)",
        " attachScript -- attachs a script without having to input objvars (self only) EX: tyrone.tyrone_debug",
        " detachScript -- detachs a script without having to input objvars (self only) EX: tyrone.tyrone_debug",
        " generateLoot -- spawns loot into your inventory from loot tables. (EX: rls/rare_loot)",
        " makeTangibleItem -- spawns a -> tangible <- item into your inventory. (Note: do not include object/tangible or .iff",
        " editLootNumber -- edits how many items your target creature has when looting (EX editLootNumber 10) to give it 10 items.",
        " editLootTable -- edits the loot table for your creature target. (EX editLootTable rls/rare_loot) ",
        " makeBestResources -- makes server best resources",
        " makeDNA -- Target a creature and type makeDNA to get dna from that creature",
        " createLootChestHere -- spawns a crate of 10 loot items for anyone to grab. You can use any loot table.(EX: createLootChestHere rls/rare_loot)",
        " Note: there may be some missing, please check script file to be 100% sure.",
    };
    public static final String[] JEDI = 
    {
        "class_forcesensitive_phase1",
        "class_forcesensitive_phase1_novice",
        "class_forcesensitive_phase1_02",
        "class_forcesensitive_phase1_03",
        "class_forcesensitive_phase1_04",
        "class_forcesensitive_phase1_05",
        "class_forcesensitive_phase1_master",
        "class_forcesensitive_phase2",
        "class_forcesensitive_phase2_novice",
        "class_forcesensitive_phase2_02",
        "class_forcesensitive_phase2_03",
        "class_forcesensitive_phase2_04",
        "class_forcesensitive_phase2_05",
        "class_forcesensitive_phase2_master",
        "class_forcesensitive_phase3",
        "class_forcesensitive_phase3_novice",
        "class_forcesensitive_phase3_02",
        "class_forcesensitive_phase3_03",
        "class_forcesensitive_phase3_04",
        "class_forcesensitive_phase3_05",
        "class_forcesensitive_phase3_master",
        "class_forcesensitive_phase4",
        "class_forcesensitive_phase4_novice",
        "class_forcesensitive_phase4_02",
        "class_forcesensitive_phase4_03",
        "class_forcesensitive_phase4_04",
        "class_forcesensitive_phase4_05",
        "class_forcesensitive_phase4_master"
    };
    public static final String[] SMUGGLER = 
    {
        "class_smuggler_phase1",
        "class_smuggler_phase1_novice",
        "class_smuggler_phase1_02",
        "class_smuggler_phase1_03",
        "class_smuggler_phase1_04",
        "class_smuggler_phase1_05",
        "class_smuggler_phase1_master",
        "class_smuggler_phase2",
        "class_smuggler_phase2_novice",
        "class_smuggler_phase2_02",
        "class_smuggler_phase2_03",
        "class_smuggler_phase2_04",
        "class_smuggler_phase2_05",
        "class_smuggler_phase2_master",
        "class_smuggler_phase3",
        "class_smuggler_phase3_novice",
        "class_smuggler_phase3_02",
        "class_smuggler_phase3_03",
        "class_smuggler_phase3_04",
        "class_smuggler_phase3_05",
        "class_smuggler_phase3_master",
        "class_smuggler_phase4",
        "class_smuggler_phase4_novice",
        "class_smuggler_phase4_02",
        "class_smuggler_phase4_03",
        "class_smuggler_phase4_04",
        "class_smuggler_phase4_05",
        "class_smuggler_phase4_master"
    };
    public static final String[] SPY = 
    {
        "class_spy_phase1",
        "class_spy_phase1_novice",
        "class_spy_phase1_02",
        "class_spy_phase1_03",
        "class_spy_phase1_04",
        "class_spy_phase1_05",
        "class_spy_phase1_master",
        "class_spy_phase2",
        "class_spy_phase2_novice",
        "class_spy_phase2_02",
        "class_spy_phase2_03",
        "class_spy_phase2_04",
        "class_spy_phase2_05",
        "class_spy_phase2_master",
        "class_spy_phase3",
        "class_spy_phase3_novice",
        "class_spy_phase3_02",
        "class_spy_phase3_03",
        "class_spy_phase3_04",
        "class_spy_phase3_05",
        "class_spy_phase3_master",
        "class_spy_phase4",
        "class_spy_phase4_novice",
        "class_spy_phase4_02",
        "class_spy_phase4_03",
        "class_spy_phase4_04",
        "class_spy_phase4_05",
        "class_spy_phase4_master"
    };
    public static final String[] BH = 
    {
        "class_bountyhunter_phase1",
        "class_bountyhunter_phase1_novice",
        "class_bountyhunter_phase1_02",
        "class_bountyhunter_phase1_03",
        "class_bountyhunter_phase1_04",
        "class_bountyhunter_phase1_05",
        "class_bountyhunter_phase1_master",
        "class_bountyhunter_phase2",
        "class_bountyhunter_phase2_novice",
        "class_bountyhunter_phase2_02",
        "class_bountyhunter_phase2_03",
        "class_bountyhunter_phase2_04",
        "class_bountyhunter_phase2_05",
        "class_bountyhunter_phase2_master",
        "class_bountyhunter_phase3",
        "class_bountyhunter_phase3_novice",
        "class_bountyhunter_phase3_02",
        "class_bountyhunter_phase3_03",
        "class_bountyhunter_phase3_04",
        "class_bountyhunter_phase3_05",
        "class_bountyhunter_phase3_master",
        "class_bountyhunter_phase4",
        "class_bountyhunter_phase4_novice",
        "class_bountyhunter_phase4_02",
        "class_bountyhunter_phase4_03",
        "class_bountyhunter_phase4_04",
        "class_bountyhunter_phase4_05",
        "class_bountyhunter_phase4_master"
    };
    public static final String[] OFFICER = 
    {
        "class_officer_phase1",
        "class_officer_phase1_novice",
        "class_officer_phase1_02",
        "class_officer_phase1_03",
        "class_officer_phase1_04",
        "class_officer_phase1_05",
        "class_officer_phase1_master",
        "class_officer_phase2",
        "class_officer_phase2_novice",
        "class_officer_phase2_02",
        "class_officer_phase2_03",
        "class_officer_phase2_04",
        "class_officer_phase2_05",
        "class_officer_phase2_master",
        "class_officer_phase3",
        "class_officer_phase3_novice",
        "class_officer_phase3_02",
        "class_officer_phase3_03",
        "class_officer_phase3_04",
        "class_officer_phase3_05",
        "class_officer_phase3_master",
        "class_officer_phase4",
        "class_officer_phase4_novice",
        "class_officer_phase4_02",
        "class_officer_phase4_03",
        "class_officer_phase4_04",
        "class_officer_phase4_05",
        "class_officer_phase4_master"
    };
    public static final String[] MEDIC = 
    {
        "class_medic_phase1",
        "class_medic_phase1_novice",
        "class_medic_phase1_02",
        "class_medic_phase1_03",
        "class_medic_phase1_04",
        "class_medic_phase1_05",
        "class_medic_phase1_master",
        "class_medic_phase2",
        "class_medic_phase2_novice",
        "class_medic_phase2_02",
        "class_medic_phase2_03",
        "class_medic_phase2_04",
        "class_medic_phase2_05",
        "class_medic_phase2_master",
        "class_medic_phase3",
        "class_medic_phase3_novice",
        "class_medic_phase3_02",
        "class_medic_phase3_03",
        "class_medic_phase3_04",
        "class_medic_phase3_05",
        "class_medic_phase3_master",
        "class_medic_phase4",
        "class_medic_phase4_novice",
        "class_medic_phase4_02",
        "class_medic_phase4_03",
        "class_medic_phase4_04",
        "class_medic_phase4_05",
        "class_medic_phase4_master"
    };
    public static final String[] COMMANDO = 
    {
        "class_commando_phase1",
        "class_commando_phase1_novice",
        "class_commando_phase1_02",
        "class_commando_phase1_03",
        "class_commando_phase1_04",
        "class_commando_phase1_05",
        "class_commando_phase1_master",
        "class_commando_phase2",
        "class_commando_phase2_novice",
        "class_commando_phase2_02",
        "class_commando_phase2_03",
        "class_commando_phase2_04",
        "class_commando_phase2_05",
        "class_commando_phase2_master",
        "class_commando_phase3",
        "class_commando_phase3_novice",
        "class_commando_phase3_02",
        "class_commando_phase3_03",
        "class_commando_phase3_04",
        "class_commando_phase3_05",
        "class_commando_phase3_master",
        "class_commando_phase4",
        "class_commando_phase4_novice",
        "class_commando_phase4_02",
        "class_commando_phase4_03",
        "class_commando_phase4_04",
        "class_commando_phase4_05",
        "class_commando_phase4_master"
    };
    public static final String[] ENTERTAINER = 
    {
        "class_entertainer_phase1",
        "class_entertainer_phase1_novice",
        "class_entertainer_phase1_02",
        "class_entertainer_phase1_03",
        "class_entertainer_phase1_04",
        "class_entertainer_phase1_05",
        "class_entertainer_phase1_master",
        "class_entertainer_phase2",
        "class_entertainer_phase2_novice",
        "class_entertainer_phase2_02",
        "class_entertainer_phase2_03",
        "class_entertainer_phase2_04",
        "class_entertainer_phase2_05",
        "class_entertainer_phase2_master",
        "class_entertainer_phase3",
        "class_entertainer_phase3_novice",
        "class_entertainer_phase3_02",
        "class_entertainer_phase3_03",
        "class_entertainer_phase3_04",
        "class_entertainer_phase3_05",
        "class_entertainer_phase3_master",
        "class_entertainer_phase4",
        "class_entertainer_phase4_novice",
        "class_entertainer_phase4_02",
        "class_entertainer_phase4_03",
        "class_entertainer_phase4_04",
        "class_entertainer_phase4_05",
        "class_entertainer_phase4_master"
    };
    public static final String[] DOMESTICS = 
    {
        "class_domestics_phase1",
        "class_domestics_phase1_novice",
        "class_domestics_phase1_02",
        "class_domestics_phase1_03",
        "class_domestics_phase1_04",
        "class_domestics_phase1_05",
        "class_domestics_phase1_master",
        "class_domestics_phase2",
        "class_domestics_phase2_novice",
        "class_domestics_phase2_02",
        "class_domestics_phase2_03",
        "class_domestics_phase2_04",
        "class_domestics_phase2_05",
        "class_domestics_phase2_master",
        "class_domestics_phase3",
        "class_domestics_phase3_novice",
        "class_domestics_phase3_02",
        "class_domestics_phase3_03",
        "class_domestics_phase3_04",
        "class_domestics_phase3_05",
        "class_domestics_phase3_master",
        "class_domestics_phase4",
        "class_domestics_phase4_novice",
        "class_domestics_phase4_02",
        "class_domestics_phase4_03",
        "class_domestics_phase4_04",
        "class_domestics_phase4_05",
        "class_domestics_phase4_master"
    };
    public static final String[] MUNITIONS = 
    {
        "class_munitions_phase1",
        "class_munitions_phase1_novice",
        "class_munitions_phase1_02",
        "class_munitions_phase1_03",
        "class_munitions_phase1_04",
        "class_munitions_phase1_05",
        "class_munitions_phase1_master",
        "class_munitions_phase2",
        "class_munitions_phase2_novice",
        "class_munitions_phase2_02",
        "class_munitions_phase2_03",
        "class_munitions_phase2_04",
        "class_munitions_phase2_05",
        "class_munitions_phase2_master",
        "class_munitions_phase3",
        "class_munitions_phase3_novice",
        "class_munitions_phase3_02",
        "class_munitions_phase3_03",
        "class_munitions_phase3_04",
        "class_munitions_phase3_05",
        "class_munitions_phase3_master",
        "class_munitions_phase4",
        "class_munitions_phase4_novice",
        "class_munitions_phase4_02",
        "class_munitions_phase4_03",
        "class_munitions_phase4_04",
        "class_munitions_phase4_05",
        "class_munitions_phase4_master"
    };
    public static final String[] ENGINEER = 
    {
        "class_engineering_phase1",
        "class_engineering_phase1_novice",
        "class_engineering_phase1_02",
        "class_engineering_phase1_03",
        "class_engineering_phase1_04",
        "class_engineering_phase1_05",
        "class_engineering_phase1_master",
        "class_engineering_phase2",
        "class_engineering_phase2_novice",
        "class_engineering_phase2_02",
        "class_engineering_phase2_03",
        "class_engineering_phase2_04",
        "class_engineering_phase2_05",
        "class_engineering_phase2_master",
        "class_engineering_phase3",
        "class_engineering_phase3_novice",
        "class_engineering_phase3_02",
        "class_engineering_phase3_03",
        "class_engineering_phase3_04",
        "class_engineering_phase3_05",
        "class_engineering_phase3_master",
        "class_engineering_phase4",
        "class_engineering_phase4_novice",
        "class_engineering_phase4_02",
        "class_engineering_phase4_03",
        "class_engineering_phase4_04",
        "class_engineering_phase4_05",
        "class_engineering_phase4_master"
    };
    public static final String[] STRUCTURE = 
    {
        "class_structures_phase1",
        "class_structures_phase1_novice",
        "class_structures_phase1_02",
        "class_structures_phase1_03",
        "class_structures_phase1_04",
        "class_structures_phase1_05",
        "class_structures_phase1_master",
        "class_structures_phase2",
        "class_structures_phase2_novice",
        "class_structures_phase2_02",
        "class_structures_phase2_03",
        "class_structures_phase2_04",
        "class_structures_phase2_05",
        "class_structures_phase2_master",
        "class_structures_phase3",
        "class_structures_phase3_novice",
        "class_structures_phase3_02",
        "class_structures_phase3_03",
        "class_structures_phase3_04",
        "class_structures_phase3_05",
        "class_structures_phase3_master",
        "class_structures_phase4",
        "class_structures_phase4_novice",
        "class_structures_phase4_02",
        "class_structures_phase4_03",
        "class_structures_phase4_04",
        "class_structures_phase4_05",
        "class_structures_phase4_master"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "tyrone's script attached: tyrone.tyrone_debug");
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self) throws InterruptedException {
        
        if(isDead(self) && (isGod(self))) {
        healShockWound(self, getShockWound(self));
        setAttrib(self, HEALTH, getMaxAttrib(self, HEALTH));
        sendSystemMessageTestingOnly(self, "[Anti Kill Script] avoided death");
        }
        
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "GOD MODE player logged out with script attached.");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
            //obj_id player = self;
            //obj_id pInv = utils.getInventoryContainer(player);
            //obj_id target = getIntendedTarget(self);
            //obj_id tInv = utils.getInventoryContainer(target);
            StringTokenizer st = new java.util.StringTokenizer(text);
            int tokens = st.countTokens();
            String command = null;
            if (st.hasMoreTokens())
            {
                command = st.nextToken();
            }
        if (command.equals("tyroneCommands"))
        {
                String allHelpData = "";
                Arrays.sort(TYRONECOMMANDPROMPT);
                for (int i = 0; i < TYRONECOMMANDPROMPT.length; i++)
                {
                    allHelpData = allHelpData + TYRONECOMMANDPROMPT[i] + "\r\n\t";
                }
                //savecommandOnClient(self, "debugTyroneCommandUsage.txt", allHelpData);
                playMusic(self, "sound/sys_comm_rebel.snd");
                //sui.msgbox(self, self, allHelpData, sui.OK_ONLY);//, TYRONECOMMANDTITLE, "noHandler");
        }
        if (command.equals("tyronesTestQuests"))
        {
            //groundquests.clearQuest(self, "tyrone_clothes");
            //groundquests.clearQuest(self, "tyrone_reward");
            //groundquests.grantQuest(self, "tyrones_clothes");
            //groundquests.grantQuest(self, "tyrone_reward");
            sendSystemMessageTestingOnly(self, "No Quests at this time.");
        }
        if (command.equals("chatTest"))
        {
            if (st.hasMoreTokens())
            {    
                obj_id objTarget1 = getLookAtTarget(self);
                String chatter = st.nextToken();
                chat.chat(objTarget1, chatter);
            }
        }
        if (command.equals("grantAllSkillsCombat"))
        {
            grantAllSkills(self, SPY);
            grantAllSkills(self, OFFICER);
            grantAllSkills(self, COMMANDO);
            grantAllSkills(self, BH);
            grantAllSkills(self, SMUGGLER);
            grantAllSkills(self, JEDI);
            grantAllSkills(self, MEDIC);
        }
        if (command.equals("grantAllSkillsEntertainer"))
        {
            grantAllSkills(self, ENTERTAINER);
        }
        if (command.equals("grantAllSkillsCrafting"))
        {
            grantAllSkills(self, DOMESTICS);
            grantAllSkills(self, MUNITIONS);
            grantAllSkills(self, ENGINEER);
            grantAllSkills(self, STRUCTURE);
        }
        if (command.equals("grantAllSkills"))
        {
            grantAllSkills(self, SPY);
            grantAllSkills(self, OFFICER);
            grantAllSkills(self, COMMANDO);
            grantAllSkills(self, BH);
            grantAllSkills(self, SMUGGLER);
            grantAllSkills(self, JEDI);
            grantAllSkills(self, MEDIC);
            grantAllSkills(self, ENTERTAINER);
            grantAllSkills(self, DOMESTICS);
            grantAllSkills(self, MUNITIONS);
            grantAllSkills(self, ENGINEER);
            grantAllSkills(self, STRUCTURE);
        }
        if (command.equals("setInteresting"))
        {
            setCondition(getLookAtTarget(self), CONDITION_INTERESTING);
            sendSystemMessageTestingOnly(self, "Set object as interesting.");
        }
        if (command.equals("jail"))
        {
            createTriggerVolume("trigger_jail", 10.0f, false);
            String wallTemplate = "object/tangible/furniture/decorative/wod_ns_wall.iff";
            location loc = getLocation(self);
            
            location firstWall = new location(loc.x + 5, loc.y, loc.z, loc.area, loc.cell);
            location secondWall = new location(loc.x - 5, loc.y, loc.z, loc.area, loc.cell);
            location thirdWall = new location(loc.x, loc.y, loc.z + 5, loc.area, loc.cell);
            location fourthWall = new location(loc.x, loc.y, loc.z - 5, loc.area, loc.cell);
            
            
            obj_id THREEWall = create.object(wallTemplate, thirdWall);
            obj_id FOURWall = create.object(wallTemplate, fourthWall);
            obj_id ONEWall = create.object(wallTemplate, firstWall);
            obj_id TWOWall = create.object(wallTemplate, secondWall);
            modifyYaw(THREEWall, 90);
            modifyYaw(FOURWall, 90);
            setName(THREEWall, "Jail Wall");
            setName(FOURWall, "Jail Wall");
            setName(ONEWall, "Jail Wall");
            setName(TWOWall, "Jail Wall");
            confineToTriggerVolume(self, "trigger_jail", self);
            
           
        }
        if (command.equals("deletejail"))
        {
            removeTriggerVolume("trigger_jail");
            obj_id[] objObjects = getAllObjectsWithTemplate(getLocation(self), 11.0f, "object/tangible/furniture/decorative/wod_ns_wall.iff");
            for (int intI = 0; intI < objObjects.length; intI++)
            {
                if (isIdValid(objObjects[intI]) && !isPlayer(objObjects[intI]))
                {
                    setObjVar(objObjects[intI], "intCleaningUp", 1);
                    trial.cleanupObject(objObjects[intI]);
                }
            }
        }
        if (command.equals("createSchematic"))
        {
            if (st.hasMoreTokens())
            {
                String template = st.nextToken();
                obj_id pInv = utils.getInventoryContainer(self);
                createSchematic(template, pInv);
                sendSystemMessageTestingOnly(self, "Schematic'd: " + template + "!");
            }
        }
        if (command.equals("travelPoint"))
        {
            if (st.hasMoreTokens())
            {
                String descriptor = st.nextToken();// Alpha, Beta, or Charlie
                String basename = "Event Transit Point: ";
                String planetName = getCurrentSceneName();
                location location = getLocation(self);
                int cost = 10;
                boolean interplanetary = true;
                int type = TPT_NPC_Starport;
                addPlanetTravelPoint(planetName, basename + descriptor, location, cost, interplanetary, type);
                sendSystemMessageTestingOnly(self, "Made new travel point with Data: Planet: " + planetName + " | Location: " + location + " | Cost: " + cost + " .");
            }
        }
        if (command.equals("rmtravelPoint"))
        {
            if (st.hasMoreTokens())
            {
                String descriptor = st.nextToken();
                if (descriptor.equals("Alpha") || (descriptor.equals("Beta") || (descriptor.equals("Gamma"))))
                {
                    String planetName = getCurrentSceneName();
                    String basename = "Event Transit Point: ";
                    removePlanetTravelPoint(planetName, basename + descriptor);
                    sendSystemMessageTestingOnly(self, "Removed travel point..");
                    return SCRIPT_CONTINUE;
                }  
            }
        }
        if (command.equals("clearInteresting"))
        {
            clearCondition(getLookAtTarget(self), CONDITION_INTERESTING);
            sendSystemMessageTestingOnly(self, "Cleared object of interesting tag.");
        }
        if (command.equals("flattenterrain"))
        {
            String poiLarge = "object/building/poi/generic_flatten_large.iff";
            create.object(poiLarge, getLocation(self));
            if (st.hasMoreTokens())
            {
                obj_id[] objObjects = getAllObjectsWithTemplate(getLocation(self), 100.0f, "object/building/poi/generic_flatten_large.iff");
                for (int intI = 0; intI < objObjects.length; intI++)
                {
                    if (isIdValid(objObjects[intI]) && !isPlayer(objObjects[intI]))
                    {
                        setObjVar(objObjects[intI], "intCleaningUp", 1);
                        trial.cleanupObject(objObjects[intI]);
                    }
                }
            }
        }
        if (command.equals("addeffect"))
        {
            if (st.hasMoreTokens())
            {
                obj_id obj = getIntendedTarget(self);
                String effectFile = st.nextToken();
                String hardpoint = st.nextToken();
                vector test = new vector();
                String label = st.nextToken();
                addObjectEffect(obj, effectFile, hardpoint, test, 1, label);
                sendSystemMessageTestingOnly(self, "Added effect");
            }
        }
         if (command.equals("makePet"))
        {
            obj_id target = getIntendedTarget(self);
            pet_lib.makePet(self, target);
        }
        if (command.equals("scriptvars"))
        {
            obj_id target = getIntendedTarget(self);
            deltadictionary dctScriptVars = target.getScriptVars();
            sendSystemMessageTestingOnly(self, "Scriptvars are " + dctScriptVars.toString());
        }
        if (command.equals("setSpaceInteresting"))
        {
            setCondition(getLookAtTarget(self), CONDITION_SPACE_INTERESTING);
            sendSystemMessageTestingOnly(self, "Set object as space interesting.");
        }
        if (command.equals("playSound"))
        {
            if (st.hasMoreTokens())
            {
                String sound = st.nextToken();
                playMusic(self, sound);
                sendSystemMessageTestingOnly(self, "Sound: " + sound + " is now playing.");
            }
        }
        if (command.equals("makeBestResources"))
        {
            craftinglib.makeBestResource(self, "steel", 1000000);
            craftinglib.makeBestResource(self, "iron", 1000000);
            craftinglib.makeBestResource(self, "copper", 1000000);
            craftinglib.makeBestResource(self, "fuel_petrochem_solid", 1000000);
            craftinglib.makeBestResource(self, "radioactive", 1000000);
            craftinglib.makeBestResource(self, "aluminum", 1000000);
            craftinglib.makeBestResource(self, "ore_extrusive", 1000000);
            craftinglib.makeBestResource(self, "petrochem_inert", 1000000);
            craftinglib.makeBestResource(self, "fiberplast", 1000000);
            craftinglib.makeBestResource(self, "gas_inert", 1000000);
            craftinglib.makeBestResource(self, "gas_reactive", 1000000);
            craftinglib.makeBestResource(self, "gemstone", 100000);
            craftinglib.makeBestResource(self, "water", 100000);
            craftinglib.makeBestResource(self, "hide", 100000);
            craftinglib.makeBestResource(self, "bone", 100000);
            craftinglib.makeBestResource(self, "meat", 100000);
            sendSystemMessageTestingOnly(self, "Completed.");
        }
        if (command.equals("makeTangibleItem"))
        {
            if (st.hasMoreTokens())
            {
                String tangibleobject = st.nextToken();
                obj_id inventoryContainer = getObjectInSlot(self, "inventory");
                createObject("object/tangible/" + tangibleobject + ".iff", inventoryContainer, "SLOT_INVENTORY");
                sendSystemMessageTestingOnly(self, "The item: " + tangibleobject + " was made!");
            }
                
        }
        if (command.equals("makeDNA"))
        {
            if (st.hasMoreTokens())
                {
                    String creature = st.nextToken();
                    bio_engineer.quickHarvest(self, creature);
                    sendSystemMessageTestingOnly(self, "DNA of " + creature + " successfully made!");
                }
        }
        if (command.equals("attachScript"))
        {
            if (st.hasMoreTokens())
                {
                    String script = st.nextToken();
                    attachScript(self, script);
                    sendSystemMessageTestingOnly(self, "Script: " + script + " successfully successfully attached!!");
                }
        }
        if (command.equals("detachScript"))
        {
            if (st.hasMoreTokens())
                {
                    String script = st.nextToken();
                    detachScript(self, script);
                    sendSystemMessageTestingOnly(self, "Script: " + script + " successfully successfully attached!!");
                }
        }
        if (command.equals("generateLoot"))
        {
                if (st.hasMoreTokens())
                {
                    String lootTable = st.nextToken();
                    obj_id inventoryContainer = getObjectInSlot(self, "inventory");
                    loot.makeLootInContainer(inventoryContainer, lootTable, 10, 0);
                    sendSystemMessageTestingOnly(self, "Loot Generated from the table: " + lootTable);
                }    
        }
        if (command.equals("editLootNumber"))
        {
                if (st.hasMoreTokens())
                {
                    obj_id target = getIntendedTarget(self);
                    String number = st.nextToken();
                    setObjVar(target, "loot.numItems", number);
                    sendSystemMessageTestingOnly(self, "Number of loot items on creature object set to: " + number);
                }    
        }
        //
        if (command.equals("itemtable")) {
            
            obj_id pInv = utils.getInventoryContainer(self);
            String sTable = "datatables/item/master_item/master_item.iff";
            int num_items = dataTableGetNumRows(sTable);
            String[] inventory = new String[num_items];
                for (int i = 0 + 1; i < num_items; i++)
                {
                    inventory[i] = dataTableGetString(sTable, i, "name");
                    static_item.createNewItemFunction(inventory[i], pInv);
                    sendSystemMessageTestingOnly(self, "All Static Items Given.");
                    return SCRIPT_CONTINUE;
                }
        }
        
        
        
        
        
        //
        if (command.equals("editLootTable"))
        {
                if (st.hasMoreTokens())
                {
                    obj_id target = getIntendedTarget(self);
                    String lootTable = st.nextToken();
                    setObjVar(target, "loot.lootTable", lootTable);
                    sendSystemMessageTestingOnly(self, "The loot table on the creature was set to: " + lootTable);
                }    
        }
        if (command.equals("playCEF"))
        {
                if (st.hasMoreTokens())
                {
                    String cef = st.nextToken();
                    playClientEffectLoc(self, "clienteffect/" + cef + ".cef", getLocation(self), 0);
                }    sendSystemMessageTestingOnly(self, "Client Effect Played.");
        }
        if (command.equals("createLootChestHere"))
        {
                if (st.hasMoreTokens())
                {
                    String lootTable = st.nextToken();
                    location treasureLoc = getLocation(self);
                    obj_id treasureChest = createObject("object/tangible/container/drum/treasure_drum.iff", treasureLoc);
                    loot.makeLootInContainer(treasureChest, lootTable, 10, 0);
                    sendSystemMessageTestingOnly(self, "A loot chest was made with 10 items from the loot table: " + lootTable);
                }
        }
        if (command.equals("clearSpaceInteresting"))
        {
            clearCondition(getLookAtTarget(self), CONDITION_SPACE_INTERESTING);
            sendSystemMessageTestingOnly(self, "Cleared object as space interesting.");
        }
        if (command.equals("droidme"))
        {
            obj_id inventoryContainer = getObjectInSlot(self, "inventory");
            if (!isIdValid(inventoryContainer))
            {
                sendSystemMessageTestingOnly(self, "looks like the objid of the player inventory is invalid");
            }
            obj_id deed = createObject("object/tangible/deed/pet_deed/deed_pit_droid.iff", inventoryContainer, "SLOT_INVENTORY");
            if (!isIdValid(deed))
            {
                sendSystemMessageTestingOnly(self, "failed to create the deed object");
            }
            final String CREATURE_NAME = "pit_droid_crafted";
            dictionary creatureDict = dataTableGetRow(create.CREATURE_TABLE, "pit_droid_crafted");
            setObjVar(deed, "creature_attribs.type", "pit_droid_crafted");
            setObjVar(deed, "creature_attribs.level", 1);
            setObjVar(deed, "creature_attribs.maxHealth", 100);
            setObjVar(deed, "creature_attribs.maxConstitution", 100);
            setObjVar(deed, "creature_attribs.general_protection", 100);
            setObjVar(deed, "creature_attribs.toHitChance", 90);
            setObjVar(deed, "creature_attribs.defenseValue", 90);
            setObjVar(deed, "creature_attribs.minDamage", 1010);
            setObjVar(deed, "creature_attribs.maxDamage", 2284);
            setObjVar(deed, "creature_attribs.aggroBonus", 1.0f);
            setObjVar(deed, "creature_attribs.critChance", 1.0f);
            setObjVar(deed, "creature_attribs.critSave", 1.0f);
            setObjVar(deed, "creature_attribs.scale", 10.1f);
            setObjVar(deed, "creature_attribs.stateResist", 1.0f);
            setObjVar(deed, "crafting_components.cmbt_module", 600.0f);
            setObjVar(deed, "dataModuleRating", 12);
            setObjVar(deed, "storageModuleRating", 12);
            setObjVar(deed, "ai.pet.hasContainer", 12);
            setObjVar(deed, "ai.pet.isRepairDroid", true);
            setObjVar(deed, "craftingStationSpace", true);
            setObjVar(deed, "craftingStationWeapon", true);
            setObjVar(deed, "craftingStationFood", true);
            setObjVar(deed, "craftingStationClothing", true);
            setObjVar(deed, "module_data.quickset_metal", true);
            setName(deed, "Deed for: Development Pit Droid");
            setObjVar(deed, "noTrade", 1);
            setObjVar(deed, "gm", 1);
            sendSystemMessageTestingOnly(self, "This is a one time use droid. you wont be able to call it again. aka stats are too op for the cucks");
        }
        return SCRIPT_CONTINUE;
    }
    public void grantAllSkills(obj_id objPlayer, String[] strSkillList) throws InterruptedException
    {
        for (int intI = 0; intI < strSkillList.length; intI++)
        {
            grantSkill(objPlayer, strSkillList[intI]);
        }
    }
}