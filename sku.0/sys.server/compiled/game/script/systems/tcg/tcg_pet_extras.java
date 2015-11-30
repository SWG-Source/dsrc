package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.create;
import script.library.house_pet;
import script.library.locations;
import script.library.player_structure;
import script.library.static_item;
import script.library.utils;

public class tcg_pet_extras extends script.base_script
{
    public tcg_pet_extras()
    {
    }
    public static final string_id SID_PICK_UP_EGG = new string_id("tcg", "pick_up_eggs");
    public static final string_id SID_DISCARD_EGG = new string_id("tcg", "discard_eggs");
    public static final string_id SID_WHILE_DEAD = new string_id("spam", "while_dead");
    public static final string_id SID_NOT_OUTSIDE = new string_id("tcg", "not_outside");
    public static final string_id SID_EGGS_CRUMBLE = new string_id("tcg", "eggs_crumble");
    public static final string_id SID_TOO_FAR_FROM_HOME = new string_id("tcg", "too_far_from_home");
    public static final String NUNA_FLOWER_01 = "object/tangible/item/nuna_egg_flower_stage_01.iff";
    public static final String NUNA_FLOWER_02 = "object/tangible/item/nuna_egg_flower_stage_02.iff";
    public static final String NUNA_FLOWER_03 = "object/tangible/item/nuna_egg_flower_stage_03.iff";
    public static final String NUNA_FLOWER_DEAD = "object/tangible/item/nuna_egg_flower_dead.iff";
    public static final String THIS_SCRIPT = "systems.tcg.tcg_pet_extras";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id rottenEgg = getSelf();
        if (utils.isInHouseCellSpace(rottenEgg))
        {
            obj_id structure = getTopMostContainer(rottenEgg);
            if (!isIdValid(structure))
            {
                return SCRIPT_CONTINUE;
            }
            setObjVar(rottenEgg, "house", structure);
        }
        if (hasObjVar(self, "placed_in_world"))
        {
            int placedTime = getIntObjVar(self, "placed_in_world");
            int gameTime = getCalendarTime();
            if (gameTime - placedTime > 259200)
            {
                CustomerServiceLog("tcg", "Nuna Eggs (" + self + ") persisted in the world for 3 days. Deleting this item.");
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id rottenEgg = getSelf();
        if (utils.isInHouseCellSpace(rottenEgg))
        {
            obj_id structure = getTopMostContainer(rottenEgg);
            if (!isIdValid(structure))
            {
                return SCRIPT_CONTINUE;
            }
            setObjVar(rottenEgg, "house", structure);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id rottenEgg = self;
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            sendSystemMessage(player, SID_WHILE_DEAD);
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(rottenEgg);
        if (!player_structure.isOwner(structure, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithinAPlayer(rottenEgg))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_DISCARD_EGG);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id rottenEgg = self;
        if (!isIdValid(player) || !isIdValid(rottenEgg))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            sendSystemMessage(player, SID_WHILE_DEAD);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (utils.isNestedWithinAPlayer(rottenEgg))
            {
                if (getTopMostContainer(self) == player)
                {
                    LOG("sissynoid", "In Player's Inventory - Player in World");
                    placeRottenEgg(player, rottenEgg);
                }
                else 
                {
                    sendSystemMessage(player, SID_NOT_OUTSIDE);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void placeRottenEgg(obj_id player, obj_id rottenEgg) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(rottenEgg))
        {
            return;
        }
        location loc = getLocation(player);
        if (!hasObjVar(rottenEgg, "house"))
        {
            sendSystemMessage(player, SID_EGGS_CRUMBLE);
            destroyObject(rottenEgg);
            return;
        }
        obj_id playerHouse = getObjIdObjVar(rottenEgg, "house");
        if (!isIdValid(playerHouse))
        {
            sendSystemMessage(player, SID_EGGS_CRUMBLE);
            destroyObject(rottenEgg);
            return;
        }
        location structureLoc = getLocation(playerHouse);
        float dist = utils.getDistance2D(loc, structureLoc);
        if ((dist > 50))
        {
            sendSystemMessage(player, SID_TOO_FAR_FROM_HOME);
            return;
        }
        setLocation(rottenEgg, loc);
        obj_id replacementEggs = createObject("object/tangible/tcg/series4/house_pet_rotten_egg.iff", getLocation(rottenEgg));
        attachScript(replacementEggs, "systems.tcg.tcg_pet_extras");
        attachScript(replacementEggs, "item.special.nomove");
        setObjVar(replacementEggs, "placed_in_world", getCalendarTime());
        setObjVar(replacementEggs, "placed_by", player);
        setObjVar(replacementEggs, "flower_cycle_phase", 0);
        setOwner(replacementEggs, replacementEggs);
        destroyObject(rottenEgg);
        if (!isGod(player))
        {
            messageTo(replacementEggs, "handleRottenEggPulseUpdate", null, 1800, false);
        }
        else 
        {
            messageTo(replacementEggs, "handleRottenEggPulseUpdate", null, 180, false);
        }
    }
    public int handleRottenEggPulseUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("sissynoid", "Pulsing Rotten Egg.");
        int randRoll = rand(1, 100);
        if (randRoll >= 1 && randRoll <= 10)
        {
            if (!hasObjVar(self, "placed_by"))
            {
                destroyObject(self);
                return SCRIPT_CONTINUE;
            }
            location myLoc = getLocation(self);
            obj_id originalOwner = getObjIdObjVar(self, "placed_by");
            destroyObject(self);
            obj_id newItem = createObject(NUNA_FLOWER_DEAD, myLoc);
            if (exists(newItem) && isIdValid(newItem))
            {
                attachScript(newItem, THIS_SCRIPT);
                setOwner(newItem, newItem);
                setObjVar(newItem, "placed_by", originalOwner);
                if (!isGod(originalOwner))
                {
                    messageTo(newItem, "handleDestroyDeadFlowerUpdate", null, 300, false);
                }
                else 
                {
                    sendSystemMessageTestingOnly(originalOwner, "The wildflower has died - ");
                    messageTo(newItem, "handleDestroyDeadFlowerUpdate", null, 60, false);
                }
            }
        }
        if (randRoll >= 11 && randRoll <= 100)
        {
            LOG("sissynoid", "Pulsing - Good Roll (" + randRoll + ")- Updating Flower");
            if (!hasObjVar(self, "flower_cycle_phase"))
            {
                destroyObject(self);
                return SCRIPT_CONTINUE;
            }
            if (getIntObjVar(self, "flower_cycle_phase") < 1)
            {
                LOG("sissynoid", "Phase 1 Flower Update");
                if (!hasObjVar(self, "placed_by"))
                {
                    destroyObject(self);
                    return SCRIPT_CONTINUE;
                }
                obj_id originalOwner = getObjIdObjVar(self, "placed_by");
                location myLoc = getLocation(self);
                destroyObject(self);
                obj_id newItem = createObject(NUNA_FLOWER_01, myLoc);
                if (exists(newItem) && isIdValid(newItem))
                {
                    attachScript(newItem, THIS_SCRIPT);
                    setObjVar(newItem, "flower_cycle_phase", 1);
                    setObjVar(newItem, "placed_by", originalOwner);
                    setOwner(newItem, newItem);
                    if (!isGod(originalOwner))
                    {
                        messageTo(newItem, "handleRottenEggPulseUpdate", null, 1800, false);
                    }
                    else 
                    {
                        messageTo(newItem, "handleRottenEggPulseUpdate", null, 180, false);
                    }
                }
                return SCRIPT_CONTINUE;
            }
            if (getIntObjVar(self, "flower_cycle_phase") < 2)
            {
                LOG("sissynoid", "Phase 2 Flower Update");
                if (!hasObjVar(self, "placed_by"))
                {
                    destroyObject(self);
                    return SCRIPT_CONTINUE;
                }
                obj_id originalOwner = getObjIdObjVar(self, "placed_by");
                location myLoc = getLocation(self);
                destroyObject(self);
                obj_id newItem = createObject(NUNA_FLOWER_02, myLoc);
                if (exists(newItem) && isIdValid(newItem))
                {
                    attachScript(newItem, THIS_SCRIPT);
                    setObjVar(newItem, "flower_cycle_phase", 2);
                    setObjVar(newItem, "placed_by", originalOwner);
                    setOwner(newItem, newItem);
                    if (!isGod(originalOwner))
                    {
                        messageTo(newItem, "handleRottenEggPulseUpdate", null, 1800, false);
                    }
                    else 
                    {
                        messageTo(newItem, "handleRottenEggPulseUpdate", null, 180, false);
                    }
                }
                return SCRIPT_CONTINUE;
            }
            if (getIntObjVar(self, "flower_cycle_phase") < 3)
            {
                LOG("sissynoid", "Phase 3 Flower Update");
                if (!hasObjVar(self, "placed_by"))
                {
                    destroyObject(self);
                    return SCRIPT_CONTINUE;
                }
                obj_id originalOwner = getObjIdObjVar(self, "placed_by");
                location myLoc = getLocation(self);
                destroyObject(self);
                obj_id newItem = createObject(NUNA_FLOWER_03, myLoc);
                if (exists(newItem) && isIdValid(newItem))
                {
                    setObjVar(newItem, "flower_cycle_phase", 3);
                    setObjVar(newItem, "placed_by", originalOwner);
                    setObjVar(newItem, "can_be_picked", 1);
                    setOwner(newItem, originalOwner);
                }
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDestroyDeadFlowerUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
