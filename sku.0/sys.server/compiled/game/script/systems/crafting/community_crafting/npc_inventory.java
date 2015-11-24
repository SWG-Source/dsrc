package script.systems.crafting.community_crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.community_crafting;

public class npc_inventory extends script.base_script
{
    public npc_inventory()
    {
    }
    public static final String OBJVAR_CRAFTING_TRACKER = community_crafting.OBJVAR_COMMUNITY_CRAFTING_TRACKER;
    public static final String OBJVAR_MY_OWNER = community_crafting.OBJVAR_COMMUNITY_CRAFTING_BASE + ".myOwner";
    public static final String OBJVAR_PLAYERS_CAN_ACCESS_CONTAINER = "players_can_access_container";
    public static final string_id SID_THANK_YOU = new string_id("crafting", "cc_thank_you");
    public static final string_id SID_THANK_YOU_DONE = new string_id("crafting", "cc_thank_you_done");
    public static final string_id SID_THANK_YOU_ONE = new string_id("crafting", "cc_thank_you_one");
    public static final string_id SID_THANK_YOU_WITH_COUNTS = new string_id("crafting", "cc_thank_you_with_counts");
    public static final string_id SID_NOT_CRAFTING = new string_id("crafting", "cc_not_crafting");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id owner = getOwner(self);
        if (isIdValid(owner))
        {
            setOwner(self, obj_id.NULL_ID);
            setObjVar(self, OBJVAR_MY_OWNER, owner);
        }
        setObjVar(self, OBJVAR_PLAYERS_CAN_ACCESS_CONTAINER, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id owner = getOwner(self);
        if (isIdValid(owner))
        {
            setOwner(self, obj_id.NULL_ID);
            setObjVar(self, OBJVAR_MY_OWNER, owner);
        }
        setObjVar(self, OBJVAR_PLAYERS_CAN_ACCESS_CONTAINER, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id craftingTracker = getObjIdObjVar(self, OBJVAR_CRAFTING_TRACKER);
        if (!isIdValid(craftingTracker))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!isIdValid(item))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!community_crafting.isSessionActive(craftingTracker))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!community_crafting.isPlayerCrafting(craftingTracker, transferer))
        {
            sendSystemMessage(transferer, SID_NOT_CRAFTING);
            return SCRIPT_OVERRIDE;
        }
        if (!community_crafting.addIngredient(craftingTracker, transferer, item))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id craftingTracker = getObjIdObjVar(self, OBJVAR_CRAFTING_TRACKER);
        if (!isIdValid(craftingTracker))
        {
            sendSystemMessage(transferer, SID_THANK_YOU);
        }
        else 
        {
            int itemsLeft = community_crafting.getNumIngredientsNeededByPlayer(craftingTracker, transferer);
            if (itemsLeft < 0)
            {
                sendSystemMessage(transferer, SID_THANK_YOU);
            }
            else if (itemsLeft == 0)
            {
                sendSystemMessage(transferer, SID_THANK_YOU_DONE);
            }
            else if (itemsLeft == 1)
            {
                sendSystemMessage(transferer, SID_THANK_YOU_ONE);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = SID_THANK_YOU_WITH_COUNTS;
                pp.digitInteger = itemsLeft;
                sendSystemMessageProse(transferer, pp);
            }
        }
        destroyObject(item);
        return SCRIPT_CONTINUE;
    }
}
