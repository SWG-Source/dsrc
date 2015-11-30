package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.community_crafting;
import script.library.utils;

public class player_community_crafting extends script.base_script
{
    public player_community_crafting()
    {
    }
    public static final float MIN_NPC_DISTANCE = 6.0f;
    public static final String OBJVAR_CLEANUP = community_crafting.OBJVAR_COMMUNITY_CRAFTING_BASE + ".cleanup";
    public static final String OBJVAR_REWARD_PRIZE = community_crafting.OBJVAR_COMMUNITY_CRAFTING_BASE + "." + community_crafting.REWARD_PRIZE;
    public static final String OBJVAR_REWARD_PRIZE_SLOT = community_crafting.OBJVAR_COMMUNITY_CRAFTING_BASE + "." + community_crafting.REWARD_PRIZE_SLOT;
    public static final String OBJVAR_REWARD_PRIZE_TYPE = community_crafting.OBJVAR_COMMUNITY_CRAFTING_BASE + "." + community_crafting.REWARD_PRIZE_TYPE;
    public static final String OBJVAR_REWARD_PRIZE_SCRIPT = community_crafting.OBJVAR_COMMUNITY_CRAFTING_BASE + "." + community_crafting.REWARD_PRIZE_SCRIPT;
    public static final string_id SID_CC_PRIZE_INVENTORY_FULL = new string_id("crafting", "cc_prize_inventory_full");
    public static final string_id SID_CC_PRIZE_INVENTORY_FULL_SUBJECT = new string_id("system_msg", "inventory_full");
    public static final string_id SID_CC_PRIZE_INVENTORY_FROM = new string_id("crafting", "cc_prize_inventory_full_from");
    public int OnLogin(obj_id self) throws InterruptedException
    {
        boolean canCleanup = true;
        if (hasObjVar(self, OBJVAR_REWARD_PRIZE))
        {
            int prizeCrc = getIntObjVar(self, OBJVAR_REWARD_PRIZE);
            int slot = getIntObjVar(self, OBJVAR_REWARD_PRIZE_SLOT);
            int type = getIntObjVar(self, OBJVAR_REWARD_PRIZE_TYPE);
            String script = getStringObjVar(self, OBJVAR_REWARD_PRIZE_SCRIPT);
            if (givePlayerPrize(self, prizeCrc, slot, type, script))
            {
                removeObjVar(self, OBJVAR_REWARD_PRIZE);
            }
            else 
            {
                canCleanup = false;
            }
        }
        if (canCleanup && hasObjVar(self, OBJVAR_CLEANUP))
        {
            cleanup(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCleanupCommunityCrafting(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, OBJVAR_REWARD_PRIZE))
        {
            cleanup(self);
        }
        else 
        {
            setObjVar(self, OBJVAR_CLEANUP, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCommunityCraftingReward(obj_id self, dictionary params) throws InterruptedException
    {
        int prizeCrc = params.getInt(community_crafting.REWARD_PRIZE);
        int slot = params.getInt(community_crafting.REWARD_PRIZE_SLOT);
        int type = params.getInt(community_crafting.REWARD_PRIZE_TYPE);
        String script = params.getString(community_crafting.REWARD_PRIZE_SCRIPT);
        if (!givePlayerPrize(self, prizeCrc, slot, type, script))
        {
            setObjVar(self, OBJVAR_REWARD_PRIZE, prizeCrc);
            setObjVar(self, OBJVAR_REWARD_PRIZE_SLOT, slot);
            setObjVar(self, OBJVAR_REWARD_PRIZE_TYPE, type);
            if (script != null)
            {
                setObjVar(self, OBJVAR_REWARD_PRIZE_SCRIPT, script);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleViewingNpcInventory(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id npc = params.getObjId("npc");
        if (isIdValid(npc))
        {
            if (getDistance(self, npc) > MIN_NPC_DISTANCE)
            {
                obj_id npcInventory = utils.getInventoryContainer(npc);
                if (isIdValid(npcInventory))
                {
                    queueCommand(self, (822776054), npcInventory, "", COMMAND_PRIORITY_IMMEDIATE);
                }
            }
            else 
            {
                messageTo(self, "handleViewingNpcInventory", params, 15, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void cleanup(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            CustomerServiceLog("community_crafting", "WARNING player_community_crafting.script: cleanup passed " + "invalid player id");
            return;
        }
        int[] schematics = getIntArrayObjVar(self, community_crafting.OBJVAR_COMMUNITY_CRAFTING_PLAYER_SCHEMATICS);
        if (schematics != null)
        {
            for (int i = 0; i < schematics.length; ++i)
            {
                if (schematics[i] != 0)
                {
                    revokeSchematic(self, schematics[i]);
                }
            }
        }
        removeObjVar(self, community_crafting.OBJVAR_COMMUNITY_CRAFTING_BASE);
        detachScript(self, community_crafting.SCRIPT_COMMUNITY_CRAFTING_PLAYER);
    }
    public boolean givePlayerPrize(obj_id self, int prizeCrc, int slot, int type, String script) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            CustomerServiceLog("community_crafting", "WARNING player_community_crafting.script: givePlayerPrize passed " + "invalid player id");
            return false;
        }
        obj_id prize = createObjectInInventoryAllowOverload(prizeCrc, self);
        if (!isIdValid(prize))
        {
            CustomerServiceLog("community_crafting", "WARNING player_community_crafting.script: givePlayerPrize could " + "not create prize " + prizeCrc + " for player %TU", self);
            return false;
        }
        if (script != null && script.length() > 0)
        {
            attachScript(prize, script);
        }
        CustomerServiceLog("community_crafting", "Gave player %TU community crafting prize " + prize, self);
        return true;
    }
}
