package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;
import script.library.ai_lib;
import script.library.anims;
import script.library.beast_lib;
import script.library.buff;
import script.library.cloninglib;
import script.library.combat;
import script.library.corpse;
import script.library.create;
import script.library.datatable;
import script.library.dump;
import script.library.factions;
import script.library.features;
import script.library.gcw;
import script.library.gm;
import script.library.groundquests;
import script.library.hue;
import script.library.jedi;
import script.library.loot;
import script.library.minigame;
import script.library.objvar_mangle;
import script.library.pclib;
import script.library.player_structure;
import script.library.qa;
import script.library.respec;
import script.library.ship_ai;
import script.library.skill;
import script.library.skill_template;
import script.library.space_battlefield;
import script.library.space_combat;
import script.library.space_content;
import script.library.space_crafting;
import script.library.space_create;
import script.library.space_pilot_command;
import script.library.space_quest;
import script.library.space_transition;
import script.library.space_utils;
import script.library.spawning;
import script.library.static_item;
import script.library.sui;
import script.library.utils;
import script.library.xp;
import script.test.qatool;

public class qaspace extends script.base_script
{
    public qaspace()
    {
    }
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final String SPACE_MOBILE_TABLE = "datatables/space_mobile/space_mobile.iff";
    public static final String MASTER_ITEM_TABLE = "datatables/item/master_item/master_item.iff";
    public int runSpaceLootIteration(obj_id self, dictionary params) throws InterruptedException
    {
        int victimCount = params.getInt("victimCount");
        String victimName = params.getString("victimName");
        spaceLootIteration(self, victimName, victimCount);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.qaspace");
        }
        else {
            sendSystemMessageTestingOnly(self, "QaSpace script attached.");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "QaSpace script detached.");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        debugConsoleMsg(self, text);
        java.util.StringTokenizer st = new java.util.StringTokenizer(text);
        String command = st.nextToken();
        String victimShip = "";
        int victimCount = 1;
        obj_id pInv = utils.getInventoryContainer(self);
        if (!command.equals("qaspaceloot"))
        {
            return SCRIPT_CONTINUE;
        }
        if (st.hasMoreTokens())
        {
            victimShip = st.nextToken();
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "Space Loot Error: Missing name of victim ship");
        }
        if (st.hasMoreTokens())
        {
            victimCount = Integer.parseInt(st.nextToken());
        }
        spaceLoot(self, victimShip, victimCount);
        return SCRIPT_CONTINUE;
    }
    public void spaceLoot(obj_id self, String victimName, int victimCount) throws InterruptedException
    {
        obj_id objMyShip = getPilotedShip(self);
        if (objMyShip == null)
        {
            sendSystemMessageTestingOnly(self, "Space Loot Error: You must be in a ship in space.");
            return;
        }
        int shipRowNumber = dataTableSearchColumnForString(victimName, "strIndex", SPACE_MOBILE_TABLE);
        if (shipRowNumber == -1)
        {
            sendSystemMessageTestingOnly(self, "Space Loot Error: You passed in a bad shipType. Type is " + victimName);
            return;
        }
        if (victimCount > 100)
        {
            sendSystemMessageTestingOnly(self, "Space Loot Error: Iterations limited 1 to 100.");
            victimCount = 100;
        }
        if (victimCount < 1)
        {
            sendSystemMessageTestingOnly(self, "Space Loot Error: Iterations limited 1 to 100.");
            victimCount = 1;
        }
        int loopAmountInt = victimCount / 10;
        int remainderInt = victimCount % 10;
        dictionary incrementDict = new dictionary();
        incrementDict.put("victimName", victimName);
        if (loopAmountInt >= 1)
        {
            for (int i = 0; i < loopAmountInt; i++)
            {
                try
                {
                    incrementDict.put("victimCount", 10);
                    messageTo(self, "runSpaceLootIteration", incrementDict, 5, true);
                }
                catch(Exception e)
                {
                    sendSystemMessageTestingOnly(self, "Interrupted! " + e);
                }
            }
        }
        if (remainderInt >= 1)
        {
            incrementDict.put("victimCount", remainderInt);
            messageTo(self, "runSpaceLootIteration", incrementDict, 5, true);
        }
    }
    public void spaceLootIteration(obj_id self, String victimName, int victimCount) throws InterruptedException
    {
        if (victimCount < 1)
        {
            return;
        }
        for (int i = 0; i < victimCount; i++)
        {
            obj_id objMyShip = getPilotedShip(self);
            obj_id objVictimShip = null;
            if (isIdValid(objMyShip))
            {
                objVictimShip = space_create.createShip(victimName, getTransform_o2p(getPilotedShip(self)));
            }
            else 
            {
                objVictimShip = space_create.createShip(victimName, getTransform_o2p(self));
            }
            if (objVictimShip == null)
            {
                sendSystemMessageTestingOnly(self, "Space Loot Error: You passed in a bad shipType. Type is " + victimName);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Space Loot: Made ship of type " + victimName + " object id is: " + objVictimShip);
                notifyShipDamage(objVictimShip, objMyShip, 10.0f);
                space_combat.doChassisDamage(objMyShip, objVictimShip, 0, 1.0f);
                setShipCurrentChassisHitPoints(objVictimShip, 0.0f);
                utils.setLocalVar(objVictimShip, "space.give_rewards", 1);
                space_combat.targetDestroyed(objVictimShip);
                space_combat.setDeathFlags(objVictimShip);
                float fltIntensity = rand(0, 1.0f);
                handleShipDestruction(objVictimShip, fltIntensity);
                space_combat.doDeathCleanup(objVictimShip);
                sendSystemMessageTestingOnly(self, "Space Loot: Killed ship of type " + victimName + " object id is: " + objVictimShip);
            }
        }
    }
}
