package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.group;
import script.library.utils;
import script.library.permissions;

public class computer extends script.terminal.base.base_terminal
{
    public computer()
    {
    }
    public static final string_id SID_TERMINAL_DISABLED = new string_id("skill_teacher", "skill_terminal_disabled");
    public static final String MSGS = "dungeon/corvette";
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!hasObjVar(self, "used"))
            {
                setObjVar(self, "used", 1);
                spawnEnemies(self, player);
            }
            String computer = getStringObjVar(self, "computer");
            if (computer == null || computer.equals(""))
            {
                string_id load = new string_id(MSGS, "loading");
                sendSystemMessage(player, load);
                return SCRIPT_CONTINUE;
            }
            if (computer.equals("one"))
            {
                string_id compOne = new string_id(MSGS, "computer_one");
                sendSystemMessage(player, compOne);
                return SCRIPT_CONTINUE;
            }
            if (computer.equals("two"))
            {
                string_id armory = new string_id(MSGS, "armory_code");
                if (!hasObjVar(self, "code"))
                {
                    if (!hasObjVar(player, "corl_corvette.armorybackroom55"))
                    {
                        int secCode4 = rand(10000, 99999);
                        sendSystemMessage(player, armory);
                        sendSystemMessage(player, "" + secCode4, null);
                        setGroupObjVar(player, "corl_corvette.armorybackroom55", secCode4);
                        setObjVar(self, "code", secCode4);
                    }
                    else 
                    {
                        int armoryCode = getIntObjVar(player, "corl_corvette.armorybackroom55");
                        setObjVar(self, "code", armoryCode);
                        sendSystemMessage(player, armory);
                        sendSystemMessage(player, "" + armoryCode, null);
                    }
                }
                else 
                {
                    int existingCode = getIntObjVar(self, "code");
                    sendSystemMessage(player, armory);
                    sendSystemMessage(player, "" + existingCode, null);
                    if (!hasObjVar(player, "corl_corvette.armorybackroom55"))
                    {
                        setGroupObjVar(player, "corl_corvette.armorybackroom55", existingCode);
                    }
                }
            }
            if (computer.equals("three"))
            {
                string_id compThree = new string_id(MSGS, "computer_three");
                sendSystemMessage(player, compThree);
                return SCRIPT_CONTINUE;
            }
            if (computer.equals("four"))
            {
                if (hasObjVar(self, "rebooted"))
                {
                    int bridgeCode = getIntObjVar(self, "bridgeCode");
                    if (bridgeCode == 0)
                    {
                        bridgeCode = rand(10000, 99999);
                    }
                    setObjVar(self, "bridgeCode", bridgeCode);
                    string_id bridge = new string_id(MSGS, "bridge_code");
                    sendSystemMessage(player, bridge);
                    sendSystemMessage(player, "" + bridgeCode, null);
                    setObjVar(self, "rebooted", 1);
                    setGroupObjVar(player, "corl_corvette.bridge66", bridgeCode);
                    return SCRIPT_CONTINUE;
                }
                if (!hasObjVar(player, "corl_corvette.booted"))
                {
                    obj_id bootdisk = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/dungeon/corellian_corvette/bootdisk.iff");
                    if (bootdisk == null)
                    {
                        int slice = checkForSlicing(player);
                        int chance = rand(1, 8);
                        if (chance > slice || hasObjVar(self, "attemptedSlice"))
                        {
                            string_id reboot = new string_id(MSGS, "reboot");
                            sendSystemMessage(player, reboot);
                        }
                        else 
                        {
                            string_id sliced = new string_id(MSGS, "sliced");
                            sendSystemMessage(player, sliced);
                            setObjVar(self, "attemptedSlice", 1);
                            setObjVar(player, "corl_corvette.booted", 1);
                            setObjVar(self, "rebooted", 1);
                        }
                    }
                    else 
                    {
                        string_id disk = new string_id(MSGS, "insert_disk");
                        sendSystemMessage(player, disk);
                        setObjVar(self, "rebooted", 1);
                        destroyObject(bootdisk);
                    }
                }
                else 
                {
                    int bridgeCode = getIntObjVar(self, "bridgeCode");
                    if (bridgeCode == 0)
                    {
                        bridgeCode = rand(10000, 99999);
                    }
                    setObjVar(self, "bridgeCode", bridgeCode);
                    string_id bridge2 = new string_id(MSGS, "bridge_code");
                    sendSystemMessage(player, bridge2);
                    sendSystemMessage(player, "" + bridgeCode, null);
                    setObjVar(self, "rebooted", 1);
                    setGroupObjVar(player, "corl_corvette.bridge66", bridgeCode);
                }
            }
            if (computer.equals("five"))
            {
                string_id journal = new string_id(MSGS, "journal");
                sendSystemMessage(player, journal);
                return SCRIPT_CONTINUE;
            }
            if (computer.equals("six"))
            {
                string_id starboard = new string_id(MSGS, "officer_starboard");
                if (!hasObjVar(self, "code"))
                {
                    if (!hasObjVar(player, "corl_corvette.officerquarters64"))
                    {
                        int secCode = rand(10000, 99999);
                        sendSystemMessage(player, starboard);
                        sendSystemMessage(player, "" + secCode, null);
                        setGroupObjVar(player, "corl_corvette.officerquarters64", secCode);
                        setObjVar(self, "code", secCode);
                    }
                    else 
                    {
                        int old = getIntObjVar(player, "corl_corvette.officerquarters64");
                        setObjVar(self, "code", old);
                        sendSystemMessage(player, starboard);
                        sendSystemMessage(player, "" + old, null);
                    }
                }
                else 
                {
                    int theCode = getIntObjVar(self, "code");
                    sendSystemMessage(player, starboard);
                    sendSystemMessage(player, "" + theCode, null);
                    if (!hasObjVar(player, "corl_corvette.officerquarters64"))
                    {
                        setGroupObjVar(player, "corl_corvette.officerquarters64", theCode);
                    }
                }
            }
            if (computer.equals("seven"))
            {
                string_id port = new string_id(MSGS, "officer_port");
                if (!hasObjVar(self, "code"))
                {
                    if (!hasObjVar(player, "corl_corvette.officerquarters65"))
                    {
                        int secCode2 = rand(10000, 99999);
                        sendSystemMessage(player, port);
                        sendSystemMessage(player, "" + secCode2, null);
                        setGroupObjVar(player, "corl_corvette.officerquarters65", secCode2);
                        setObjVar(self, "code", secCode2);
                    }
                    else 
                    {
                        int other = getIntObjVar(player, "corl_corvette.officerquarters65");
                        setObjVar(self, "code", other);
                        sendSystemMessage(player, port);
                        sendSystemMessage(player, "" + other, null);
                    }
                }
                else 
                {
                    int oldCode = getIntObjVar(self, "code");
                    sendSystemMessage(player, port);
                    sendSystemMessage(player, "" + oldCode, null);
                    if (!hasObjVar(player, "corl_corvette.officerquarters65"))
                    {
                        setGroupObjVar(player, "corl_corvette.officerquarters65", oldCode);
                    }
                }
            }
            if (computer.equals("eight"))
            {
                string_id prison = new string_id(MSGS, "prison_code");
                if (!hasObjVar(self, "code"))
                {
                    if (!hasObjVar(player, "corl_corvette.officerquarters63"))
                    {
                        int secCode3 = rand(10000, 99999);
                        sendSystemMessage(player, prison);
                        sendSystemMessage(player, "" + secCode3, null);
                        setGroupObjVar(player, "corl_corvette.officerquarters63", secCode3);
                        setObjVar(self, "code", secCode3);
                    }
                    else 
                    {
                        int prisonCode = getIntObjVar(player, "corl_corvette.officerquarters63");
                        setObjVar(self, "code", prisonCode);
                        sendSystemMessage(player, prison);
                        sendSystemMessage(player, "" + prisonCode, null);
                    }
                }
                else 
                {
                    int existingCode = getIntObjVar(self, "code");
                    sendSystemMessage(player, prison);
                    sendSystemMessage(player, "" + existingCode, null);
                    if (!hasObjVar(player, "corl_corvette.officerquarters63"))
                    {
                        setGroupObjVar(player, "corl_corvette.officerquarters63", existingCode);
                    }
                }
            }
            if (computer.equals("nine"))
            {
                string_id droid_exp = new string_id(MSGS, "droid_explanation_rebel");
                sendSystemMessage(player, droid_exp);
                return SCRIPT_CONTINUE;
            }
            if (computer.equals("ten"))
            {
                string_id droid_journal = new string_id(MSGS, "droid_explanation_imperial");
                sendSystemMessage(player, droid_journal);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int checkForSlicing(obj_id player) throws InterruptedException
    {
        int slicability = 0;
        if (!hasSkill(player, "class_smuggler_phase1_novice"))
        {
            return slicability;
        }
        else 
        {
            if (hasSkill(player, "class_smuggler_phase1_novice"))
            {
                slicability = slicability + 1;
            }
            if (hasSkill(player, "class_smuggler_phase1_master"))
            {
                slicability = slicability + 1;
            }
            if (hasSkill(player, "class_smuggler_phase2_novice"))
            {
                slicability = slicability + 1;
            }
            if (hasSkill(player, "class_smuggler_phase3_novice"))
            {
                slicability = slicability + 1;
            }
            if (hasSkill(player, "class_smuggler_phase3_novice"))
            {
                slicability = slicability + 1;
            }
            if (hasSkill(player, "class_smuggler_phase4_novice"))
            {
                slicability = slicability + 2;
            }
        }
        return slicability;
    }
    public void setGroupObjVar(obj_id player, String objvarName, int code) throws InterruptedException
    {
        if (group.isGrouped(player))
        {
            obj_id groupObj = getGroupObject(player);
            obj_id[] groupMembers = getGroupMemberIds(groupObj);
            int numGroupMembers = groupMembers.length;
            for (int f = 0; f < numGroupMembers; f++)
            {
                obj_id groupie = groupMembers[f];
                if (isIdValid(groupie))
                {
                    setObjVar(groupie, objvarName, code);
                }
            }
        }
        else 
        {
            setObjVar(player, objvarName, code);
        }
        return;
    }
    public void spawnEnemies(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        location here = getLocation(self);
        obj_id cell = here.cell;
        String room = utils.getCellName(top, cell);
        location loc = getGoodLocation(top, room);
        String type = getTemplateName(top);
        if (type.equals("object/building/general/space_dungeon_corellian_corvette_imperial.iff"))
        {
            obj_id guard = create.object("rebel_super_battle_droid", loc);
            return;
        }
        if (type.equals("object/building/general/space_dungeon_corellian_corvette_rebel.iff"))
        {
            obj_id guard = create.object("imperial_super_battle_droid", loc);
        }
        else 
        {
            obj_id guard = create.object("corsec_super_battle_droid", loc);
        }
        return;
    }
}
