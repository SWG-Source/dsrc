package script.simulator;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.create;
import script.library.utils;
import script.library.weapons;
import script.library.armor;
import java.util.Enumeration;

public class combat_simulator_user extends script.base_script
{
    public combat_simulator_user()
    {
    }
    public static final String COMBAT_MASTER_SCRIPT = "simulator.combat_simulator_master";
    public static final String[] COMBAT_SIMULATOR_MAIN_OPTIONS = 
    {
        "Set label",
        "Set number of rounds",
        "Set combat range",
        "Customize actor A",
        "Customize actor B",
        "Start simulation",
        "Destroy running simulation",
        "Reset all settings"
    };
    public static final String[] COMBAT_SIMULATOR_ACTOR_OPTIONS = 
    {
        "Race",
        "Gender",
        "Starting Profession",
        "Professions",
        "Weapon",
        "Armor",
        "Buffs",
        "Wound Levels",
        "Command Queue",
        "Copy From Other Actor",
        "Make Actor A Creature",
        "Reset"
    };
    public static final String[] COMBAT_SIMULATOR_ACTOR_RACE_OPTIONS = 
    {
        "bothan",
        "human",
        "ithorian",
        "moncal",
        "rodian",
        "sullustan",
        "trandoshan",
        "twilek",
        "wookiee",
        "zobrak"
    };
    public static final String[] COMBAT_SIMULATOR_ACTOR_GENDER_OPTIONS = 
    {
        "female",
        "male"
    };
    public static final String[] COMBAT_SIMULATOR_ACTOR_STARTING_PROFESSION_OPTIONS = 
    {
        "artisan",
        "brawler",
        "entertainer",
        "marksman",
        "medic",
        "scout",
        "jedi"
    };
    public static final String[] COMBAT_SIMULATOR_ACTOR_PROFESSION_OPTIONS = 
    {
        "architect",
        "armorsmith",
        "artisan",
        "bioengineer",
        "bountyhunter",
        "brawler",
        "carbineer",
        "chef",
        "combatmedic",
        "commando",
        "creaturehandler",
        "dancer",
        "doctor",
        "droidengineer",
        "entertainer",
        "fencer",
        "imagedesigner",
        "marksman",
        "medic",
        "merchant",
        "musician",
        "pikeman",
        "pistoleer",
        "politician",
        "ranger",
        "rifleman",
        "scout",
        "smuggler",
        "squadleader",
        "swordsman",
        "tailor",
        "teraskasi",
        "weaponsmith"
    };
    public static final String[] COMBAT_SIMULATOR_ACTOR_PROFESSION_LEVEL_OPTIONS = 
    {
        "novice",
        "1",
        "2",
        "3",
        "4",
        "master",
        "none"
    };
    public static final String[] COMBAT_SIMULATOR_ACTOR_ATTRIBUTES = 
    {
        "health",
        "constitution",
        "action",
        "stamina",
        "mind",
        "willpower",
        "reset"
    };
    public void combatSimulatorMain() throws InterruptedException
    {
        obj_id self = getSelf();
        String[] allWeapons = dataTableGetStringColumnNoDefaults(weapons.WEAPON_DATA_TABLE, weapons.COL_TEMPLATE);
        utils.setScriptVar(self, "combat_simulator.weapons", allWeapons);
        String[] allArmorInitial = dataTableGetStringColumnNoDefaults(armor.DATATABLE_MASTER_ARMOR, "crafted_object_template");
        Vector allArmor = new Vector();
        for (int i = 0; i < allArmorInitial.length; ++i)
        {
            if (allArmorInitial[i].startsWith("object/tangible/wearables/armor/"))
            {
                allArmor.add(allArmorInitial[i]);
            }
        }
        utils.setScriptVar(self, "combat_simulator.armor", allArmor);
        String[] creatureList = dataTableGetStringColumnNoDefaults(create.CREATURE_TABLE, "creatureName");
        Arrays.sort(creatureList);
        utils.setScriptVar(self, "combat_simulator.creature_list", creatureList);
        String label = "Main options:";
        if (utils.hasScriptVar(self, "combat_simulator.label"))
        {
            label += "\nCurrent label is: ";
            label += utils.getStringScriptVar(self, "combat_simulator.label");
        }
        if (utils.hasScriptVar(self, "combat_simulator.rounds"))
        {
            label += "\nCurrent number of rounds is: ";
            label += utils.getIntScriptVar(self, "combat_simulator.rounds");
        }
        if (utils.hasScriptVar(self, "combat_simulator.range"))
        {
            label += "\nCurrent range is: ";
            label += utils.getIntScriptVar(self, "combat_simulator.range");
        }
        utils.setScriptVar(self, "combat_simulator.current_actor", "A");
        label += "\n\nActor A: ";
        label += getActorInfo();
        utils.setScriptVar(self, "combat_simulator.current_actor", "B");
        label += "\n\nActor B: ";
        label += getActorInfo();
        utils.removeScriptVar(self, "combat_simulator.current_actor");
        closeOldWindowPid();
        int pid = sui.listbox(self, self, label, sui.OK_CANCEL, "Combat simulator", COMBAT_SIMULATOR_MAIN_OPTIONS, "mainOptionsOk", true, false);
        setWindowPid(pid);
    }
    public int mainOptionsOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL || idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        switch (idx)
        {
            case 0:
            setLabel();
            break;
            case 1:
            setNumberOfRounds();
            break;
            case 2:
            setRange();
            break;
            case 3:
            utils.setScriptVar(self, "combat_simulator.current_actor", "A");
            customizeActor();
            break;
            case 4:
            utils.setScriptVar(self, "combat_simulator.current_actor", "B");
            customizeActor();
            break;
            case 5:
            startSimulation();
            break;
            case 6:
            destroySimulation();
            break;
            case 7:
            reset("combat_simulator");
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public void setLabel() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String label = "Input a label for this simulation:";
        label += "\n(will also be data output filename)";
        dictionary params = new dictionary();
        if (utils.hasScriptVar(self, "combat_simulator.label"))
        {
            label += "\nCurrent label is: ";
            String pastLabel = utils.getStringScriptVar(self, "combat_simulator.label");
            label += pastLabel;
            params.put(sui.INPUTBOX_INPUT + "-" + sui.PROP_TEXT, pastLabel);
        }
        int pid = sui.inputbox(self, self, label, sui.OK_CANCEL, "Set Label", sui.INPUT_NORMAL, null, "setLabelOk", params);
        setWindowPid(pid);
    }
    public int setLabelOk(obj_id self, dictionary params) throws InterruptedException
    {
        String label = sui.getInputBoxText(params);
        int button = sui.getIntButtonPressed(params);
        if (button == sui.BP_OK)
        {
            if (utils.hasScriptVar(self, "combat_simulator.label") && label.equals(""))
            {
                utils.removeScriptVar(self, "combat_simulator.label");
            }
            else 
            {
                utils.setScriptVar(self, "combat_simulator.label", label);
            }
        }
        combatSimulatorMain();
        return SCRIPT_CONTINUE;
    }
    public void setNumberOfRounds() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String label = "Set the number of rounds you want to run in this simulation:";
        if (utils.hasScriptVar(self, "combat_simulator.rounds"))
        {
            label += "\nCurrent number of rounds is: ";
            label += utils.getIntScriptVar(self, "combat_simulator.rounds");
        }
        label += "\n(an even number is recommended to even out first-shot advantage)";
        int pid = sui.inputbox(self, self, label, sui.OK_CANCEL, "Set Runs", sui.INPUT_NORMAL, null, "setNumberOfRoundsOk", null);
        setWindowPid(pid);
    }
    public int setNumberOfRoundsOk(obj_id self, dictionary params) throws InterruptedException
    {
        String label = sui.getInputBoxText(params);
        int button = sui.getIntButtonPressed(params);
        if (button == sui.BP_OK)
        {
            if (utils.hasScriptVar(self, "combat_simulator.rounds") && label.equals(""))
            {
                utils.removeScriptVar(self, "combat_simulator.rounds");
            }
            else if (!label.equals(""))
            {
                utils.setScriptVar(self, "combat_simulator.rounds", utils.stringToInt(label));
            }
        }
        combatSimulatorMain();
        return SCRIPT_CONTINUE;
    }
    public void setRange() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String label = "Input a range for simulation:";
        if (utils.hasScriptVar(self, "combat_simulator.range"))
        {
            label += "\nCurrent range is: ";
            label += utils.getIntScriptVar(self, "combat_simulator.range");
        }
        int pid = sui.inputbox(self, self, label, sui.OK_CANCEL, "Set Range", sui.INPUT_NORMAL, null, "setRangeOk", null);
        setWindowPid(pid);
    }
    public int setRangeOk(obj_id self, dictionary params) throws InterruptedException
    {
        String range = sui.getInputBoxText(params);
        int button = sui.getIntButtonPressed(params);
        if (button == sui.BP_OK)
        {
            if (utils.hasScriptVar(self, "combat_simulator.range") && range.equals(""))
            {
                utils.removeScriptVar(self, "combat_simulator.range");
            }
            else if (!range.equals(""))
            {
                utils.setScriptVar(self, "combat_simulator.range", utils.stringToInt(range));
            }
        }
        combatSimulatorMain();
        return SCRIPT_CONTINUE;
    }
    public void customizeActor() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String player = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String textbox = "Options for actor " + player + ":\n";
        textbox += "Current actor info: ";
        textbox += getActorInfo();
        int pid = sui.listbox(self, self, textbox, sui.OK_CANCEL, "Actor customization", COMBAT_SIMULATOR_ACTOR_OPTIONS, "customizeActorOk", true, false);
        setWindowPid(pid);
    }
    public int customizeActorOk(obj_id self, dictionary params) throws InterruptedException
    {
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL || idx == -1)
        {
            utils.removeScriptVar(self, "combat_simulator.current_actor");
            combatSimulatorMain();
            return SCRIPT_CONTINUE;
        }
        switch (idx)
        {
            case 0:
            setRace();
            break;
            case 1:
            setGender();
            break;
            case 2:
            setStartingProfession();
            break;
            case 3:
            setProfessions();
            break;
            case 4:
            setWeapon();
            break;
            case 5:
            setArmor();
            break;
            case 6:
            setBuffs();
            break;
            case 7:
            break;
            case 8:
            setCommandQueue();
            break;
            case 9:
            copyOtherActor();
            break;
            case 10:
            setCreatureLetter();
            break;
            case 11:
            reset("combat_simulator." + utils.getStringScriptVar(self, "combat_simulator.current_actor"));
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public void setRace() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String label = "Select a race:";
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".race"))
        {
            label += "\nCurrent race is: ";
            label += utils.getStringScriptVar(self, "combat_simulator." + actor + ".race");
        }
        int pid = sui.listbox(self, self, label, sui.OK_CANCEL, "Race selection", COMBAT_SIMULATOR_ACTOR_RACE_OPTIONS, "setRaceOk", true, false);
        setWindowPid(pid);
    }
    public int setRaceOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        if (btn == sui.BP_OK && idx != -1)
        {
            utils.setScriptVar(self, "combat_simulator." + actor + ".race", COMBAT_SIMULATOR_ACTOR_RACE_OPTIONS[idx]);
        }
        customizeActor();
        return SCRIPT_CONTINUE;
    }
    public void setGender() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String label = "Select a gender:";
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".gender"))
        {
            label += "\nCurrent gender is: ";
            label += utils.getStringScriptVar(self, "combat_simulator." + actor + ".gender");
        }
        int pid = sui.listbox(self, self, label, sui.OK_CANCEL, "Gender selection", COMBAT_SIMULATOR_ACTOR_GENDER_OPTIONS, "setGenderOk", true, false);
        setWindowPid(pid);
    }
    public int setGenderOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        if (btn == sui.BP_OK && idx != -1)
        {
            utils.setScriptVar(self, "combat_simulator." + actor + ".gender", COMBAT_SIMULATOR_ACTOR_GENDER_OPTIONS[idx]);
        }
        customizeActor();
        return SCRIPT_CONTINUE;
    }
    public void setStartingProfession() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String label = "Select a starting profession:";
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".starting_profession"))
        {
            label += "\nCurrent starting profession is: ";
            label += utils.getStringScriptVar(self, "combat_simulator." + actor + ".starting_profession");
        }
        int pid = sui.listbox(self, self, label, sui.OK_CANCEL, "Starting profession selection", COMBAT_SIMULATOR_ACTOR_STARTING_PROFESSION_OPTIONS, "setStartingProfessionOk", true, false);
        setWindowPid(pid);
    }
    public int setStartingProfessionOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        if (btn == sui.BP_OK && idx != -1)
        {
            utils.setScriptVar(self, "combat_simulator." + actor + ".starting_profession", COMBAT_SIMULATOR_ACTOR_STARTING_PROFESSION_OPTIONS[idx]);
        }
        customizeActor();
        return SCRIPT_CONTINUE;
    }
    public void setProfessions() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String label = "Select a profession to grant:";
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".professions"))
        {
            label += "\nCurrent professions are: ";
            String[] professions = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".professions");
            for (int i = 0; i < professions.length; ++i)
            {
                java.util.StringTokenizer st = new java.util.StringTokenizer(professions[i]);
                int professionIndex = utils.stringToInt(st.nextToken());
                String professionLevel = st.nextToken();
                if (professionLevel.equals("0"))
                {
                    professionLevel = "novice";
                }
                if (professionLevel.equals("5"))
                {
                    professionLevel = "master";
                }
                label += "\n";
                label += (COMBAT_SIMULATOR_ACTOR_PROFESSION_OPTIONS[professionIndex] + " " + professionLevel);
            }
        }
        int pid = sui.listbox(self, self, label, sui.OK_CANCEL, "Profession selection", COMBAT_SIMULATOR_ACTOR_PROFESSION_OPTIONS, "setProfessionsOk", true, false);
        setWindowPid(pid);
    }
    public int setProfessionsOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        int btn = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL || idx == -1)
        {
            customizeActor();
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "combat_simulator.current_profession", idx);
        setProfessionLevel();
        return SCRIPT_CONTINUE;
    }
    public void setProfessionLevel() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        int currentProfessionIndex = utils.getIntScriptVar(self, "combat_simulator.current_profession");
        String label = "Select a level to grant for " + COMBAT_SIMULATOR_ACTOR_PROFESSION_OPTIONS[currentProfessionIndex] + ":";
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".professions"))
        {
            label += "\nCurrent profession level is: ";
            String[] professions = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".professions");
            for (int i = 0; i < professions.length; ++i)
            {
                java.util.StringTokenizer st = new java.util.StringTokenizer(professions[i]);
                int professionIndex = utils.stringToInt(st.nextToken());
                String professionLevel = st.nextToken();
                if (currentProfessionIndex == professionIndex)
                {
                    if (professionLevel.equals("0"))
                    {
                        professionLevel = "novice";
                    }
                    if (professionLevel.equals("5"))
                    {
                        professionLevel = "master";
                    }
                    label += "\n";
                    label += (COMBAT_SIMULATOR_ACTOR_PROFESSION_OPTIONS[professionIndex] + " " + professionLevel);
                }
            }
        }
        int pid = sui.listbox(self, self, label, sui.OK_CANCEL, "Profession level selection", COMBAT_SIMULATOR_ACTOR_PROFESSION_LEVEL_OPTIONS, "setProfessionLevelOk", true, false);
        setWindowPid(pid);
    }
    public int setProfessionLevelOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        int currentProfessionIndex = utils.getIntScriptVar(self, "combat_simulator.current_profession");
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        utils.removeScriptVar(self, "combat_simulator.current_profession");
        if (btn == sui.BP_CANCEL || idx == -1)
        {
            setProfessions();
            return SCRIPT_CONTINUE;
        }
        String professionToAdd = (currentProfessionIndex + " " + idx);
        Vector professions = new Vector();
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".professions"))
        {
            professions = utils.getResizeableStringArrayScriptVar(self, "combat_simulator." + actor + ".professions");
        }
        boolean foundProfession = false;
        for (int i = 0; i < professions.size(); ++i)
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(((String)professions.get(i)));
            int professionIndex = utils.stringToInt(st.nextToken());
            String professionLevel = st.nextToken();
            if (currentProfessionIndex == professionIndex)
            {
                if (idx == (COMBAT_SIMULATOR_ACTOR_PROFESSION_LEVEL_OPTIONS.length - 1))
                {
                    professions.remove(i);
                }
                else 
                {
                    professions.set(i, professionToAdd);
                    foundProfession = true;
                }
            }
        }
        if (!foundProfession && idx != 6)
        {
            professions.add(professionToAdd);
        }
        java.util.Collections.sort(professions);
        if (professions.size() > 0)
        {
            utils.setScriptVar(self, "combat_simulator." + actor + ".professions", professions);
        }
        else 
        {
            utils.removeScriptVar(self, "combat_simulator." + actor + ".professions");
        }
        setProfessions();
        return SCRIPT_CONTINUE;
    }
    public void setWeapon() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String label = "Select a Weapon:";
        String depth = "";
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".weapon"))
        {
            label += "\nCurrent weapon is: \n";
            label += utils.getStringScriptVar(self, "combat_simulator." + actor + ".weapon");
        }
        if (utils.hasScriptVar(self, "combat_simulator.weapon_depth"))
        {
            label += "\nCurrent depth is: \n";
            depth = utils.getStringScriptVar(self, "combat_simulator.weapon_depth");
            label += depth;
        }
        String[] weaponChoices = getLayerOfArray("object/weapon/" + depth, "combat_simulator.weapons");
        utils.setScriptVar(self, "combat_simulator.weapon_choices", weaponChoices);
        int pid = sui.listbox(self, self, label, sui.OK_CANCEL, "Weapon selection", weaponChoices, "setWeaponOk", true, false);
        setWindowPid(pid);
    }
    public int setWeaponOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        obj_id player = sui.getPlayerId(params);
        String[] weaponChoices = utils.getStringArrayScriptVar(self, "combat_simulator.weapon_choices");
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        String depth = "";
        if (utils.hasScriptVar(self, "combat_simulator.weapon_depth"))
        {
            depth = utils.getStringScriptVar(self, "combat_simulator.weapon_depth");
        }
        if (btn == sui.BP_CANCEL || idx == -1)
        {
            if (depth.equals(""))
            {
                utils.removeScriptVar(self, "combat_simulator.weapon_choices");
                customizeActor();
                return SCRIPT_CONTINUE;
            }
            else 
            {
                depth = depth.substring(0, depth.length() - 1);
                int lastSlash = depth.lastIndexOf('/');
                if (lastSlash == -1)
                {
                    utils.removeScriptVar(self, "combat_simulator.weapon_depth");
                }
                else 
                {
                    depth = depth.substring(0, lastSlash + 1);
                    utils.setScriptVar(self, "combat_simulator.weapon_depth", depth);
                }
                setWeapon();
                return SCRIPT_CONTINUE;
            }
        }
        String selectedWeapon = weaponChoices[idx];
        if (selectedWeapon.equals("reset"))
        {
            utils.removeScriptVar(self, "combat_simulator.weapon_choices");
            utils.removeScriptVar(self, "combat_simulator.weapon_depth");
            utils.removeScriptVar(self, "combat_simulator." + actor + ".weapon");
            setWeapon();
            return SCRIPT_CONTINUE;
        }
        if (selectedWeapon.endsWith(".iff"))
        {
            utils.removeScriptVar(self, "combat_simulator.weapon_depth");
            utils.setScriptVar(self, "combat_simulator." + actor + ".weapon", depth + selectedWeapon);
            customizeActor();
        }
        else 
        {
            utils.setScriptVar(self, "combat_simulator.weapon_depth", depth + selectedWeapon);
            setWeapon();
        }
        return SCRIPT_CONTINUE;
    }
    public void setArmor() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String label = "Select a piece of armor:";
        String depth = "";
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".armor"))
        {
            label += "\nCurrent armor is: ";
            String[] currentArmor = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".armor");
            for (int i = 0; i < currentArmor.length; ++i)
            {
                label += ("\n" + currentArmor[i]);
            }
        }
        if (utils.hasScriptVar(self, "combat_simulator.armor_depth"))
        {
            label += "\nCurrent depth is: \n";
            depth = utils.getStringScriptVar(self, "combat_simulator.armor_depth");
            label += depth;
        }
        String[] armorChoices = getLayerOfArray("object/tangible/wearables/armor/" + depth, "combat_simulator.armor");
        utils.setScriptVar(self, "combat_simulator.armor_choices", armorChoices);
        int pid = sui.listbox(self, self, label, sui.OK_CANCEL, "Armor selection", armorChoices, "setArmorOk", true, false);
        setWindowPid(pid);
    }
    public int setArmorOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        obj_id player = sui.getPlayerId(params);
        String[] armorChoices = utils.getStringArrayScriptVar(self, "combat_simulator.armor_choices");
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        String depth = "";
        if (utils.hasScriptVar(self, "combat_simulator.armor_depth"))
        {
            depth = utils.getStringScriptVar(self, "combat_simulator.armor_depth");
        }
        if (btn == sui.BP_CANCEL || idx == -1)
        {
            if (depth.equals(""))
            {
                utils.removeScriptVar(self, "combat_simulator.armor_choices");
                customizeActor();
                return SCRIPT_CONTINUE;
            }
            else 
            {
                depth = depth.substring(0, depth.length() - 1);
                int lastSlash = depth.lastIndexOf('/');
                if (lastSlash == -1)
                {
                    utils.removeScriptVar(self, "combat_simulator.armor_depth");
                }
                else 
                {
                    depth = depth.substring(0, lastSlash + 1);
                    utils.setScriptVar(self, "combat_simulator.armor_depth", depth);
                }
                setArmor();
                return SCRIPT_CONTINUE;
            }
        }
        String selectedArmor = armorChoices[idx];
        if (selectedArmor.equals("reset"))
        {
            utils.removeScriptVar(self, "combat_simulator.armor_choices");
            utils.removeScriptVar(self, "combat_simulator.armor_depth");
            utils.removeScriptVar(self, "combat_simulator." + actor + ".armor");
            setArmor();
            return SCRIPT_CONTINUE;
        }
        if (selectedArmor.endsWith(".iff"))
        {
            Vector armorPieces = new Vector();
            if (utils.hasScriptVar(self, "combat_simulator." + actor + ".armor"))
            {
                armorPieces = utils.getResizeableStringArrayScriptVar(self, "combat_simulator." + actor + ".armor");
            }
            boolean foundArmor = false;
            int selectedArmorCategory = getArmorType(depth + selectedArmor);
            for (int i = 0; i < armorPieces.size(); ++i)
            {
                if (selectedArmorCategory == getArmorType(((String)armorPieces.get(i))))
                {
                    armorPieces.set(i, depth + selectedArmor);
                    foundArmor = true;
                }
            }
            if (!foundArmor)
            {
                armorPieces.add(depth + selectedArmor);
            }
            java.util.Collections.sort(armorPieces);
            utils.setScriptVar(self, "combat_simulator." + actor + ".armor", armorPieces);
            setArmor();
        }
        else 
        {
            utils.setScriptVar(self, "combat_simulator.armor_depth", depth + selectedArmor);
            setArmor();
        }
        return SCRIPT_CONTINUE;
    }
    public void setBuffs() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String label = "Select a buff to grant:";
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".buffs"))
        {
            label += "\nCurrent buffs are: ";
            String[] buffs = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".buffs");
            for (int i = 0; i < buffs.length; ++i)
            {
                label += "\n";
                label += buffs[i];
            }
        }
        int pid = sui.listbox(self, self, label, sui.OK_CANCEL, "Buffs selection", COMBAT_SIMULATOR_ACTOR_ATTRIBUTES, "setBuffsOk", true, false);
        setWindowPid(pid);
    }
    public int setBuffsOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL || idx == -1)
        {
            customizeActor();
            return SCRIPT_CONTINUE;
        }
        if (idx == (COMBAT_SIMULATOR_ACTOR_ATTRIBUTES.length - 1))
        {
            utils.removeScriptVar(self, "combat_simulator." + actor + ".buffs");
            setBuffs();
            return SCRIPT_CONTINUE;
        }
        String buffToAdd = COMBAT_SIMULATOR_ACTOR_ATTRIBUTES[idx];
        Vector buffs = new Vector();
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".buffs"))
        {
            buffs = utils.getResizeableStringArrayScriptVar(self, "combat_simulator." + actor + ".buffs");
        }
        boolean foundBuff = false;
        for (int i = 0; i < buffs.size(); ++i)
        {
            if ((((String)buffs.get(i))).indexOf(buffToAdd) == 0)
            {
                foundBuff = true;
            }
        }
        if (!foundBuff)
        {
            buffs.add(buffToAdd);
        }
        java.util.Collections.sort(buffs);
        utils.setScriptVar(self, "combat_simulator." + actor + ".buffs", buffs);
        setBuffs();
        return SCRIPT_CONTINUE;
    }
    public void setConsumables() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        int pid = sui.msgbox(self, self, "This feature has not yet been implemented", sui.OK_ONLY, "Not yet implemented", sui.MSG_EXCLAMATION, "setConsumablesOk");
        setWindowPid(pid);
    }
    public int setConsumablesOk(obj_id self, dictionary params) throws InterruptedException
    {
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        customizeActor();
        return SCRIPT_CONTINUE;
    }
    public void setWounds() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String label = "Select a wound level to set:";
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".wounds"))
        {
            label += "\nCurrent wound levels are: ";
            String[] wounds = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".wounds");
            for (int i = 0; i < wounds.length; ++i)
            {
                label += "\n";
                label += wounds[i];
            }
        }
        int pid = sui.listbox(self, self, label, sui.OK_CANCEL, "Wounds levels", COMBAT_SIMULATOR_ACTOR_ATTRIBUTES, "setWoundsOk", true, false);
        setWindowPid(pid);
    }
    public int setWoundsOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        int btn = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL || idx == -1)
        {
            customizeActor();
            return SCRIPT_CONTINUE;
        }
        if (idx == (COMBAT_SIMULATOR_ACTOR_ATTRIBUTES.length - 1))
        {
            utils.removeScriptVar(self, "combat_simulator." + actor + ".wounds");
            setWounds();
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "combat_simulator.current_wound", COMBAT_SIMULATOR_ACTOR_ATTRIBUTES[idx]);
        setWoundLevel();
        return SCRIPT_CONTINUE;
    }
    public void setWoundLevel() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String wound = utils.getStringScriptVar(self, "combat_simulator.current_wound");
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String label = "Specify a wound amount for " + wound + ":";
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".wounds"))
        {
            String[] wounds = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".wounds");
            for (int i = 0; i < wounds.length; ++i)
            {
                if ((((String)wounds[i])).indexOf(wound) == 0)
                {
                    label += "\nCurrent wound level is: \n";
                    label += wounds[i];
                }
            }
        }
        int pid = sui.inputbox(self, self, label, sui.OK_CANCEL, "Set wound level", sui.INPUT_NORMAL, null, "setWoundLevelOk", null);
        setWindowPid(pid);
    }
    public int setWoundLevelOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String label = sui.getInputBoxText(params);
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String wound = utils.getStringScriptVar(self, "combat_simulator.current_wound");
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        utils.removeScriptVar(self, "combat_simulator.current_wound");
        if (btn == sui.BP_CANCEL || label.equals(""))
        {
            setWounds();
            return SCRIPT_CONTINUE;
        }
        String woundToAdd = wound + " " + label;
        Vector wounds = new Vector();
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".wounds"))
        {
            wounds = utils.getResizeableStringArrayScriptVar(self, "combat_simulator." + actor + ".wounds");
        }
        boolean foundWound = false;
        for (int i = 0; i < wounds.size(); ++i)
        {
            String currentWound = toString(wounds.get(i));
            if (currentWound.startsWith(wound))
            {
                foundWound = true;
                wounds.set(i, woundToAdd);
            }
        }
        if (!foundWound)
        {
            wounds.add(woundToAdd);
        }
        java.util.Collections.sort(wounds);
        utils.setScriptVar(self, "combat_simulator." + actor + ".wounds", wounds);
        setWounds();
        return SCRIPT_CONTINUE;
    }
    public void setCommandQueue() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String label = "Select a command to add to the queue:";
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".commands"))
        {
            label += "\nCurrent commands are: ";
            String[] commands = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".commands");
            for (int i = 0; i < commands.length; ++i)
            {
                label += "\n";
                label += commands[i];
            }
        }
        String[] validCommands;
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".valid_commands"))
        {
            validCommands = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".valid_commands");
        }
        else 
        {
            validCommands = buildCommandListForActor();
            utils.setScriptVar(self, "combat_simulator." + actor + ".valid_commands", validCommands);
        }
        int pid;
        if (validCommands.length == 0)
        {
            pid = sui.msgbox(self, self, "No valid commands found - does the actor have any professions?", sui.OK_ONLY, "No commands", sui.MSG_NORMAL, "setCommandQueueMessageOk");
        }
        else 
        {
            pid = sui.listbox(self, self, label, sui.OK_CANCEL, "Command selection", validCommands, "setCommandQueueOk", true, false);
        }
        setWindowPid(pid);
    }
    public int setCommandQueueMessageOk(obj_id self, dictionary params) throws InterruptedException
    {
        customizeActor();
        return SCRIPT_CONTINUE;
    }
    public int setCommandQueueOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String[] validCommands = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".valid_commands");
        int btn = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL || idx == -1)
        {
            utils.removeScriptVar(self, "combat_simulator." + actor + ".valid_commands");
            customizeActor();
            return SCRIPT_CONTINUE;
        }
        if (validCommands[idx].equals("reset"))
        {
            utils.removeScriptVar(self, "combat_simulator." + actor + ".commands");
            setCommandQueue();
            return SCRIPT_CONTINUE;
        }
        String commandToAdd = validCommands[idx];
        Vector commands = new Vector();
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".commands"))
        {
            commands = utils.getResizeableStringArrayScriptVar(self, "combat_simulator." + actor + ".commands");
        }
        boolean foundCommand = false;
        for (int i = 0; i < commands.size(); ++i)
        {
            if (((String)commands.get(i)) == commandToAdd)
            {
                foundCommand = true;
            }
        }
        if (!foundCommand)
        {
            commands.add(commandToAdd);
        }
        utils.setScriptVar(self, "combat_simulator." + actor + ".commands", commands);
        setCommandQueue();
        return SCRIPT_CONTINUE;
    }
    public void copyOtherActor() throws InterruptedException
    {
        obj_id self = getSelf();
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        closeOldWindowPid();
        String prompt = ("Are you sure you want to remove all settings from actor " + actor + " and replace them the other actor's settings?");
        int pid = sui.msgbox(self, self, prompt, sui.OK_CANCEL, "Copy actor?", sui.MSG_QUESTION, "copyOtherActorOk");
        setWindowPid(pid);
    }
    public int copyOtherActorOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        if (btn == sui.BP_CANCEL)
        {
            customizeActor();
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVarTree(self, "combat_simulator." + actor);
        String otherActor;
        if (actor.equals("A"))
        {
            otherActor = "B";
        }
        else 
        {
            otherActor = "A";
        }
        deltadictionary dd = self.getScriptVars();
        Enumeration keys = dd.keys();
        while (keys.hasMoreElements())
        {
            String key = (String)(keys.nextElement());
            if (key.equals("combat_simulator." + otherActor + ".race") || key.equals("combat_simulator." + otherActor + ".gender") || key.equals("combat_simulator." + otherActor + ".starting_profession") || key.equals("combat_simulator." + otherActor + ".weapon") || key.equals("combat_simulator." + otherActor + ".creature"))
            {
                String newKey = ("combat_simulator." + actor + key.substring(18));
                dd.put(newKey, dd.getString(key));
            }
            else if (key.equals("combat_simulator." + otherActor + ".professions") || key.equals("combat_simulator." + otherActor + ".armor") || key.equals("combat_simulator." + otherActor + ".buffs") || key.equals("combat_simulator." + otherActor + ".wounds") || key.equals("combat_simulator." + otherActor + ".commands"))
            {
                String newKey = ("combat_simulator." + actor + key.substring(18));
                dd.put(newKey, dd.getStringArray(key));
            }
        }
        customizeActor();
        return SCRIPT_CONTINUE;
    }
    public void setCreatureLetter() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String label = "Select a creature starting letter:";
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".creature"))
        {
            label += "\nCurrent creature selection is: ";
            label += utils.getStringScriptVar(self, "combat_simulator." + actor + ".creature");
        }
        label += "\n(Only buffs and wound level selections are taken into account when you make a creature actor)";
        Vector alphabet = new Vector();
        for (int i = (int)'a'; i <= (int)'z'; ++i)
        {
            alphabet.add("" + (char)i);
        }
        alphabet.add("reset");
        int pid = sui.listbox(self, self, label, sui.OK_CANCEL, "Creature selection", alphabet, "setCreatureLetterOk", true, false);
        setWindowPid(pid);
    }
    public int setCreatureLetterOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        if (btn == sui.BP_OK && idx != -1)
        {
            if (idx == 26)
            {
                utils.removeScriptVar(self, "combat_simulator." + actor + ".creature");
                customizeActor();
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(self, "combat_simulator." + actor + ".creature_letter", ("" + (char)((int)'a' + idx)));
            setCreature();
            return SCRIPT_CONTINUE;
        }
        customizeActor();
        return SCRIPT_CONTINUE;
    }
    public void setCreature() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String creatureLetter = utils.getStringScriptVar(self, "combat_simulator." + actor + ".creature_letter");
        String label = "Select a creature:";
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".creature"))
        {
            label += "\nCurrent creature selection is: ";
            label += utils.getStringScriptVar(self, "combat_simulator." + actor + ".creature");
        }
        label += "\n(Only buffs and wound level selections are taken into account when you make a creature actor)";
        String[] allCreatures = utils.getStringArrayScriptVar(self, "combat_simulator.creature_list");
        Vector availableCreatures = new Vector();
        for (int i = 0; i < allCreatures.length; ++i)
        {
            if (allCreatures[i].startsWith(creatureLetter))
            {
                availableCreatures.add(allCreatures[i]);
            }
        }
        availableCreatures.add("reset");
        utils.setScriptVar(self, "combat_simulator." + actor + ".creature_letter_list", availableCreatures);
        int pid = sui.listbox(self, self, label, sui.OK_CANCEL, "Creature selection", availableCreatures, "setCreatureOk", true, false);
        setWindowPid(pid);
    }
    public int setCreatureOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String[] availableCreatures = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".creature_letter_list");
        utils.removeScriptVar(self, "combat_simulator.creature_letter_list");
        utils.removeScriptVar(self, "combat_simulator." + actor + ".creature_letter");
        if (btn == sui.BP_OK && idx != -1)
        {
            if (idx == (availableCreatures.length - 1))
            {
                utils.removeScriptVar(self, "combat_simulator." + actor + ".creature");
                customizeActor();
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(self, "combat_simulator." + actor + ".creature", availableCreatures[idx]);
            customizeActor();
            return SCRIPT_CONTINUE;
        }
        setCreatureLetter();
        return SCRIPT_CONTINUE;
    }
    public void startSimulation() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        if (!(utils.hasScriptVar(self, "combat_simulator.label") && utils.hasScriptVar(self, "combat_simulator.rounds") && ((utils.hasScriptVar(self, "combat_simulator.A.race") && utils.hasScriptVar(self, "combat_simulator.A.gender") && utils.hasScriptVar(self, "combat_simulator.A.starting_profession")) || utils.hasScriptVar(self, "combat_simulator.A.creature")) && ((utils.hasScriptVar(self, "combat_simulator.B.race") && utils.hasScriptVar(self, "combat_simulator.B.gender") && utils.hasScriptVar(self, "combat_simulator.B.starting_profession")) || utils.hasScriptVar(self, "combat_simulator.B.creature"))))
        {
            String message = "You need to have at least a label and number of rounds as well as a race, gender, and starting profession for each customized actor or a creature selection for each creature actor to begin a simulation";
            int pid = sui.msgbox(self, self, message, sui.OK_ONLY, "Cannot run", sui.MSG_EXCLAMATION, "startSimulationMessageOk");
            setWindowPid(pid);
            return;
        }
        String message = "Start simulation with these settings?";
        message += ("\nLabel: " + utils.getStringScriptVar(self, "combat_simulator.label"));
        message += ("\nRounds: " + utils.getIntScriptVar(self, "combat_simulator.rounds"));
        if (utils.hasScriptVar(self, "combat_simulator.range"))
        {
            message += ("\nRange: " + utils.getIntScriptVar(self, "combat_simulator.range"));
        }
        utils.setScriptVar(self, "combat_simulator.current_actor", "A");
        message += "\n\nActor A: ";
        message += getActorInfo();
        utils.setScriptVar(self, "combat_simulator.current_actor", "B");
        message += "\n\nActor B: ";
        message += getActorInfo();
        utils.removeScriptVar(self, "combat_simulator.current_actor");
        int pid = sui.msgbox(self, self, message, sui.OK_CANCEL, "Start simulation?", sui.MSG_QUESTION, "startSimulationOk");
    }
    public int startSimulationMessageOk(obj_id self, dictionary params) throws InterruptedException
    {
        combatSimulatorMain();
        return SCRIPT_CONTINUE;
    }
    public int startSimulationOk(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            combatSimulatorMain();
            return SCRIPT_CONTINUE;
        }
        Vector commands = new Vector();
        location combatLocation = getLocation(self);
        combatLocation.z += 3;
        obj_id combatMaster = createObjectSimulator("object/creature/player/human_male.iff", combatLocation);
        attachScript(combatMaster, COMBAT_MASTER_SCRIPT);
        setInvulnerable(combatMaster, true);
        commands.add("setLabel " + utils.getStringScriptVar(self, "combat_simulator.label"));
        commands.add("setNumRounds " + utils.getIntScriptVar(self, "combat_simulator.rounds"));
        if (utils.hasScriptVar(self, "combat_simulator.range"))
        {
            commands.add("setRange " + utils.getIntScriptVar(self, "combat_simulator.range"));
        }
        commands.add(getMakePlayerCommand("A"));
        commands.add(getMakePlayerCommand("B"));
        commands.addAll(getProfessionCommands("A"));
        commands.addAll(getProfessionCommands("B"));
        commands.addAll(getWoundCommands("A"));
        commands.addAll(getWoundCommands("B"));
        commands.addAll(getBuffCommands("A"));
        commands.addAll(getBuffCommands("B"));
        if (utils.hasScriptVar(self, "combat_simulator.A.weapon"))
        {
            commands.add(getWeaponCommand("A"));
        }
        if (utils.hasScriptVar(self, "combat_simulator.B.weapon"))
        {
            commands.add(getWeaponCommand("B"));
        }
        commands.addAll(getArmorCommands("A"));
        commands.addAll(getArmorCommands("B"));
        commands.addAll(getActionCommands("A"));
        commands.addAll(getActionCommands("B"));
        putCommandList(combatMaster, (String[])commands.toArray(new String[0]));
        setObjVar(combatMaster, "combat_simulator.stop_accumulating_commands", 1);
        setObjVar(combatMaster, "combat_simulator.created_from_combat_simulator_user", 1);
        setObjVar(combatMaster, "combat_simulator.current_round", 1);
        messageTo(combatMaster, "runSimulation", null, 0, false);
        String label = utils.getStringScriptVar(self, "combat_simulator.label");
        if (label.indexOf(".") != -1)
        {
            int splitIndex = -1;
            if (utils.stringToInt(label.substring(label.length() - 4)) != -1)
            {
                splitIndex = (label.length() - 4);
            }
            else if (utils.stringToInt(label.substring(label.length() - 3)) != -1)
            {
                splitIndex = (label.length() - 3);
            }
            else if (utils.stringToInt(label.substring(label.length() - 2)) != -1)
            {
                splitIndex = (label.length() - 2);
            }
            else if (utils.stringToInt(label.substring(label.length() - 1)) != -1)
            {
                splitIndex = (label.length() - 1);
            }
            if (splitIndex != -1)
            {
                int currentNumber = utils.stringToInt(label.substring(splitIndex));
                label = label.substring(0, splitIndex);
                ++currentNumber;
                label += currentNumber;
                utils.setScriptVar(self, "combat_simulator.label", label);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void destroySimulation() throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindowPid();
        dictionary params = new dictionary();
        obj_id currentSelectedObject = getLookAtTarget(self);
        if (currentSelectedObject != null)
        {
            params.put(sui.INPUTBOX_INPUT + "-" + sui.PROP_TEXT, "" + currentSelectedObject);
        }
        String label = "Input the object id of the combat master of the simulation you wish to destroy:\n(the main person that spawns the two combatants)";
        int pid = sui.inputbox(self, self, label, sui.OK_CANCEL, "Enter combat master", sui.INPUT_NORMAL, null, "destroySimulationOk", params);
        setWindowPid(pid);
    }
    public int destroySimulationOk(obj_id self, dictionary params) throws InterruptedException
    {
        String label = sui.getInputBoxText(params);
        int button = sui.getIntButtonPressed(params);
        if (button == sui.BP_OK && !label.equals(""))
        {
            obj_id target = utils.stringToObjId(label);
            debugSpeakMsg(target, "reset");
            removeAllObjVars(target);
            destroyObjectSimulator(target);
        }
        combatSimulatorMain();
        return SCRIPT_CONTINUE;
    }
    public void reset(String tag) throws InterruptedException
    {
        obj_id self = getSelf();
        utils.setScriptVar(self, "combat_simulator.reset_tag", tag);
        closeOldWindowPid();
        String prompt = "Are you sure you want to reset ";
        if (tag.equals("combat_simulator"))
        {
            prompt += "all combat simulator settings?";
        }
        else if (tag.equals("combat_simulator.A"))
        {
            prompt += "all actor A customizations?";
        }
        else if (tag.equals("combat_simulator.B"))
        {
            prompt += "all actor B customizations?";
        }
        int pid = sui.msgbox(self, self, prompt, sui.OK_CANCEL, "Reset alert!", sui.MSG_EXCLAMATION, "resetOk");
        setWindowPid(pid);
    }
    public int resetOk(obj_id self, dictionary params) throws InterruptedException
    {
        String resetTag = utils.getStringScriptVar(self, "combat_simulator.reset_tag");
        utils.removeScriptVar(self, "combat_simulator.reset_tag");
        int button = sui.getIntButtonPressed(params);
        if (button == sui.BP_OK)
        {
            utils.removeScriptVarTree(self, resetTag);
        }
        if (resetTag.equals("combat_simulator"))
        {
            combatSimulatorMain();
        }
        else if (resetTag.equals("combat_simulator.A"))
        {
            utils.setScriptVar(self, "combat_simulator.current_actor", "A");
            customizeActor();
        }
        else if (resetTag.equals("combat_simulator.B"))
        {
            utils.setScriptVar(self, "combat_simulator.current_actor", "B");
            customizeActor();
        }
        return SCRIPT_CONTINUE;
    }
    public void closeOldWindowPid() throws InterruptedException
    {
        obj_id self = getSelf();
        if (utils.hasScriptVar(self, "combat_simulator.pid"))
        {
            int oldpid = utils.getIntScriptVar(self, "combat_simulator.pid");
            forceCloseSUIPage(oldpid);
            utils.removeScriptVar(self, "combat_simulator.pid");
        }
    }
    public void setWindowPid(int pid) throws InterruptedException
    {
        obj_id self = getSelf();
        if (pid > -1)
        {
            utils.setScriptVar(self, "combat_simulator.pid", pid);
        }
    }
    public void cleanScriptVars() throws InterruptedException
    {
        obj_id self = getSelf();
        utils.removeScriptVarTree(self, "combat_simulator");
    }
    public String[] buildCommandListForActor() throws InterruptedException
    {
        obj_id self = getSelf();
        String[] professions = new String[0];
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        Vector commands = new Vector();
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".professions"))
        {
            professions = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".professions");
        }
        else 
        {
            return professions;
        }
        for (int i = 0; i < professions.length; ++i)
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(professions[i]);
            int professionIndex = utils.stringToInt(st.nextToken());
            int professionLevel = utils.stringToInt(st.nextToken());
            Vector skills = new Vector();
            for (int j = 0; j < PROFESSION_SKILLS[professionIndex].length; ++j)
            {
                if (getSkillLevel(PROFESSION_SKILLS[professionIndex][j]) <= professionLevel)
                {
                    skills.add(PROFESSION_SKILLS[professionIndex][j]);
                }
            }
            for (int j = 0; j < skills.size(); ++j)
            {
                String[] prereqSkills = getSkillPrerequisiteSkills((String)skills.get(j));
                for (int m = 0; m < prereqSkills.length; ++m)
                {
                    if (!skills.contains(prereqSkills[m]))
                    {
                        skills.add(prereqSkills[m]);
                    }
                }
                String[] skillCommands = getSkillCommandsProvided((String)skills.get(j));
                for (int k = 0; k < skillCommands.length; ++k)
                {
                    String displayCommand = skillCommands[k];
                    java.util.StringTokenizer commandTok = new java.util.StringTokenizer(displayCommand, "_");
                    String initialCommand = commandTok.nextToken();
                    if (displayCommand.indexOf('+') != -1)
                    {
                        continue;
                    }
                    String additionalCommand = "";
                    int level = 0;
                    if (commandTok.countTokens() == 1)
                    {
                        level = utils.stringToInt(commandTok.nextToken());
                        if (level == -1)
                        {
                            continue;
                        }
                        additionalCommand = (" (improved x" + level + ")");
                    }
                    else if (commandTok.countTokens() > 1)
                    {
                        continue;
                    }
                    boolean foundCommand = false;
                    for (int l = 0; l < commands.size(); ++l)
                    {
                        String com = (commands.get(l)).toString();
                        if (com.startsWith(initialCommand))
                        {
                            foundCommand = true;
                            int ind = com.indexOf(" (improved x");
                            int comLevel = 0;
                            if (ind != -1)
                            {
                                ind += 12;
                                comLevel = utils.stringToInt(com.substring(ind, ind + 1));
                            }
                            if (level > comLevel)
                            {
                                commands.set(l, (initialCommand + additionalCommand));
                            }
                        }
                    }
                    if (!foundCommand)
                    {
                        commands.add(initialCommand + additionalCommand);
                    }
                }
            }
        }
        java.util.Collections.sort(commands);
        commands.add("defaultAttack");
        commands.add("reset");
        return (String[])commands.toArray(new String[0]);
    }
    public int getSkillLevel(String skill) throws InterruptedException
    {
        if (skill.endsWith("_novice"))
        {
            return 0;
        }
        else if (skill.endsWith("_01"))
        {
            return 1;
        }
        else if (skill.endsWith("_02"))
        {
            return 2;
        }
        else if (skill.endsWith("_03"))
        {
            return 3;
        }
        else if (skill.endsWith("_04"))
        {
            return 4;
        }
        else if (skill.endsWith("_master"))
        {
            return 5;
        }
        else 
        {
            return -1;
        }
    }
    public String getActorInfo() throws InterruptedException
    {
        obj_id self = getSelf();
        String actor = utils.getStringScriptVar(self, "combat_simulator.current_actor");
        String output = "";
        boolean isCreature = false;
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".creature"))
        {
            output += ("\nCreature:\n   " + utils.getStringScriptVar(self, "combat_simulator." + actor + ".creature"));
            isCreature = true;
        }
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".race") && !isCreature)
        {
            output += ("\nRace:\n   " + utils.getStringScriptVar(self, "combat_simulator." + actor + ".race"));
        }
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".gender") && !isCreature)
        {
            output += ("\nGender:\n   " + utils.getStringScriptVar(self, "combat_simulator." + actor + ".gender"));
        }
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".starting_profession") && !isCreature)
        {
            output += ("\nStarting Profession:\n   " + utils.getStringScriptVar(self, "combat_simulator." + actor + ".starting_profession"));
        }
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".professions") && !isCreature)
        {
            output += "\nProfessions: ";
            String[] professions = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".professions");
            for (int i = 0; i < professions.length; ++i)
            {
                java.util.StringTokenizer st = new java.util.StringTokenizer(professions[i]);
                int professionIndex = utils.stringToInt(st.nextToken());
                String professionLevel = st.nextToken();
                if (professionLevel.equals("0"))
                {
                    professionLevel = "novice";
                }
                if (professionLevel.equals("5"))
                {
                    professionLevel = "master";
                }
                output += ("\n   " + COMBAT_SIMULATOR_ACTOR_PROFESSION_OPTIONS[professionIndex] + " " + professionLevel);
            }
        }
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".weapon") && !isCreature)
        {
            output += ("\nWeapon:\n   " + utils.getStringScriptVar(self, "combat_simulator." + actor + ".weapon"));
        }
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".armor") && !isCreature)
        {
            output += "\nArmor: ";
            String[] armor = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".armor");
            for (int i = 0; i < armor.length; ++i)
            {
                output += ("\n   " + armor[i]);
            }
        }
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".buffs"))
        {
            output += "\nBuffs: ";
            String[] buffs = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".buffs");
            for (int i = 0; i < buffs.length; ++i)
            {
                output += ("\n   " + buffs[i]);
            }
        }
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".wounds"))
        {
            output += "\nWound levels: ";
            String[] wounds = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".wounds");
            for (int i = 0; i < wounds.length; ++i)
            {
                output += ("\n   " + wounds[i]);
            }
        }
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".commands") && !isCreature)
        {
            output += "\nCommands: ";
            String[] commands = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".commands");
            for (int i = 0; i < commands.length; ++i)
            {
                output += ("\n   " + commands[i]);
            }
        }
        return output;
    }
    public String[] getLayerOfArray(String depth, String scriptVar) throws InterruptedException
    {
        obj_id self = getSelf();
        Vector newElements = new Vector();
        String[] totalElements = utils.getStringArrayScriptVar(self, scriptVar);
        for (int i = 0; i < totalElements.length; ++i)
        {
            String currentElement = totalElements[i];
            if (currentElement.startsWith(depth))
            {
                currentElement = currentElement.substring(depth.length(), currentElement.length());
                int firstSlash = currentElement.indexOf('/');
                if (firstSlash != -1)
                {
                    currentElement = currentElement.substring(0, firstSlash + 1);
                }
                if (!newElements.contains(currentElement))
                {
                    newElements.add(currentElement);
                }
            }
        }
        java.util.Collections.sort(newElements);
        newElements.add("reset");
        return (String[])newElements.toArray(new String[0]);
    }
    public String getMakePlayerCommand(String actor) throws InterruptedException
    {
        obj_id self = getSelf();
        String command = "";
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".creature"))
        {
            command += "makeCreature";
            command += (" " + actor);
            command += (" " + utils.getStringScriptVar(self, "combat_simulator." + actor + ".creature"));
        }
        else 
        {
            command += "makePlayer";
            command += (" " + actor);
            command += (" " + utils.getStringScriptVar(self, "combat_simulator." + actor + ".race"));
            command += (" " + utils.getStringScriptVar(self, "combat_simulator." + actor + ".gender"));
            command += (" " + utils.getStringScriptVar(self, "combat_simulator." + actor + ".starting_profession"));
        }
        return command;
    }
    public Vector getProfessionCommands(String actor) throws InterruptedException
    {
        obj_id self = getSelf();
        Vector commands = new Vector();
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".creature"))
        {
            return commands;
        }
        String[] professions = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".professions");
        if (professions == null)
        {
            professions = new String[0];
        }
        for (int i = 0; i < professions.length; ++i)
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(professions[i]);
            int professionIndex = utils.stringToInt(st.nextToken());
            String professionLevel = st.nextToken();
            commands.add("giveProfession " + actor + " " + COMBAT_SIMULATOR_ACTOR_PROFESSION_OPTIONS[professionIndex] + " " + professionLevel);
        }
        return commands;
    }
    public Vector getWoundCommands(String actor) throws InterruptedException
    {
        obj_id self = getSelf();
        Vector commands = new Vector();
        String[] wounds = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".wounds");
        if (wounds == null)
        {
            wounds = new String[0];
        }
        for (int i = 0; i < wounds.length; ++i)
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(wounds[i]);
            String attrib = st.nextToken();
            String value = st.nextToken();
            commands.add("setAttribWound " + actor + " " + attrib + " " + value);
        }
        return commands;
    }
    public Vector getBuffCommands(String actor) throws InterruptedException
    {
        obj_id self = getSelf();
        Vector commands = new Vector();
        String[] buffs = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".buffs");
        if (buffs == null)
        {
            buffs = new String[0];
        }
        for (int i = 0; i < buffs.length; ++i)
        {
            commands.add("giveBuff " + actor + " " + buffs[i]);
        }
        return commands;
    }
    public String getWeaponCommand(String actor) throws InterruptedException
    {
        obj_id self = getSelf();
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".creature"))
        {
            return "";
        }
        String command = "giveEquipment";
        command += (" " + actor);
        command += (" object/weapon/" + utils.getStringScriptVar(self, "combat_simulator." + actor + ".weapon"));
        return command;
    }
    public Vector getArmorCommands(String actor) throws InterruptedException
    {
        obj_id self = getSelf();
        Vector commands = new Vector();
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".creature"))
        {
            return commands;
        }
        String[] armor = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".armor");
        if (armor == null)
        {
            armor = new String[0];
        }
        for (int i = 0; i < armor.length; ++i)
        {
            commands.add("giveEquipment " + actor + " object/tangible/wearables/armor/" + armor[i]);
        }
        return commands;
    }
    public Vector getActionCommands(String actor) throws InterruptedException
    {
        obj_id self = getSelf();
        Vector commands = new Vector();
        if (utils.hasScriptVar(self, "combat_simulator." + actor + ".creature"))
        {
            return commands;
        }
        String[] actions = utils.getStringArrayScriptVar(self, "combat_simulator." + actor + ".commands");
        if (actions == null)
        {
            actions = new String[0];
        }
        for (int i = 0; i < actions.length; ++i)
        {
            String action = actions[i];
            int improvedIndex = action.indexOf("(improved x");
            if (improvedIndex != -1)
            {
                action = action.substring(0, improvedIndex);
            }
            commands.add("addCommand " + actor + " " + action);
        }
        return commands;
    }
    public void putCommandList(obj_id combatMaster, String[] commands) throws InterruptedException
    {
        int commandListNumber = 0;
        Vector newCommands = new Vector();
        int sizeOfNewCommands = 0;
        for (int i = 0; i < commands.length; ++i)
        {
            String nextCommand = commands[i];
            if (nextCommand.length() + sizeOfNewCommands > 900)
            {
                setObjVar(combatMaster, "combat_simulator.command_list." + commandListNumber, newCommands);
                ++commandListNumber;
                sizeOfNewCommands = 0;
                newCommands.clear();
            }
            newCommands.add(nextCommand);
            sizeOfNewCommands += nextCommand.length();
        }
        if (sizeOfNewCommands > 0)
        {
            setObjVar(combatMaster, "combat_simulator.command_list." + commandListNumber, newCommands);
        }
    }
    public int getArmorType(String armorPiece) throws InterruptedException
    {
        if (armorPiece.indexOf("backpack") != -1)
        {
            return 0;
        }
        else if (armorPiece.indexOf("bandolier") != -1)
        {
            return 1;
        }
        else if (armorPiece.indexOf("belt") != -1)
        {
            return 2;
        }
        else if (armorPiece.indexOf("bicep_l") != -1)
        {
            return 3;
        }
        else if (armorPiece.indexOf("bicep_r") != -1)
        {
            return 4;
        }
        else if (armorPiece.indexOf("bracer_l") != -1)
        {
            return 5;
        }
        else if (armorPiece.indexOf("bracer_r") != -1)
        {
            return 6;
        }
        else if (armorPiece.indexOf("chest_plate") != -1)
        {
            return 7;
        }
        else if (armorPiece.indexOf("gloves") != -1)
        {
            return 8;
        }
        else if (armorPiece.indexOf("helmet") != -1)
        {
            return 9;
        }
        else if (armorPiece.indexOf("leggings") != -1)
        {
            return 10;
        }
        else if (armorPiece.indexOf("pants") != -1)
        {
            return 11;
        }
        else if (armorPiece.indexOf("shirt") != -1)
        {
            return 12;
        }
        else if (armorPiece.indexOf("shoes") != -1)
        {
            return 13;
        }
        else if (armorPiece.indexOf("utility_belt") != -1)
        {
            return 14;
        }
        else 
        {
            return -1;
        }
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (!isGod(self))
        {
            sendSystemMessageTestingOnly(self, "You cannot run the combat simulator when not in god mode");
            return SCRIPT_OVERRIDE;
        }
        text = toLower(text);
        if (text.equals("startcombatsimulator") || text.equals("scs"))
        {
            combatSimulatorMain();
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public static final String[][] PROFESSION_SKILLS = 
    {
        
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
            "combat_smuggler_novice",
            "combat_smuggler_master",
            "combat_smuggler_underworld_01",
            "combat_smuggler_underworld_02",
            "combat_smuggler_underworld_03",
            "combat_smuggler_underworld_04",
            "combat_smuggler_slicing_01",
            "combat_smuggler_slicing_02",
            "combat_smuggler_slicing_03",
            "combat_smuggler_slicing_04",
            "combat_smuggler_combat_01",
            "combat_smuggler_combat_02",
            "combat_smuggler_combat_03",
            "combat_smuggler_combat_04",
            "combat_smuggler_spice_01",
            "combat_smuggler_spice_02",
            "combat_smuggler_spice_03",
            "combat_smuggler_spice_04"
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
        }
    };
}
