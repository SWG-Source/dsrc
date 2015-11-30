package script.theme_park.heroic.echo_base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.factions;
import script.library.groundquests;
import script.library.restuss_event;
import script.library.static_item;
import script.library.trial;
import script.library.utils;

public class echo_barricade extends script.base_script
{
    public echo_barricade()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isValidBarricadeLocation(self))
        {
            factions.setFaction(self, "Rebel", true, 0);
            messageTo(self, "handleHothRebelBarricade", null, 2, false);
        }
        else 
        {
            messageTo(self, "destroyHothBarricade", null, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleHothRebelBarricade(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] objects = getObjectsInRange(self, 5.0f);
        if (objects != null && objects.length > 0)
        {
            for (int i = 0; i < objects.length; i++)
            {
                obj_id object = objects[i];
                if (isIdValid(object))
                {
                    if (factions.isRebel(object) && isMob(object))
                    {
                        buff.applyBuff(object, self, "barricade_defender");
                    }
                }
            }
        }
        messageTo(self, "handleHothRebelBarricade", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (!isValidBarricadeLocation(self))
        {
            messageTo(self, "destroyHothBarricade", null, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int destroyHothBarricade(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public boolean isValidBarricadeLocation(obj_id self) throws InterruptedException
    {
        location here = getLocation(getTopMostContainer(self));
        if (!here.area.equals("adventure2"))
        {
            return false;
        }
        return true;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isValidBarricadeLocation(self))
        {
            messageTo(self, "destroyHothBarricade", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "unmoveableBarricade"))
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("theme_park/heroic", "hoth_barricade_pickup"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int menuItem) throws InterruptedException
    {
        if (!isValidBarricadeLocation(self))
        {
            messageTo(self, "destroyHothBarricade", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (menuItem == menu_info_types.SERVER_MENU1)
        {
            if (utils.isNestedWithin(self, player))
            {
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(self, "unmoveableBarricade"))
            {
                return SCRIPT_CONTINUE;
            }
            if (getHitpoints(self) == getMaxHitpoints(self))
            {
                String staticItem = "hoth_rebel_barricade_inventory";
                obj_id inv = getObjectInSlot(player, "inventory");
                obj_id item = static_item.createNewItemFunction(staticItem, inv);
                if (isIdValid(item))
                {
                    if (trial.registerObjectWithSequencer(item))
                    {
                        groundquests.sendPlacedInInventorySystemMessage(player, item);
                        trial.cleanupObject(self);
                    }
                    else 
                    {
                        trial.cleanupObject(item);
                    }
                }
            }
            else 
            {
                sendSystemMessage(player, new string_id("theme_park/heroic", "hoth_barricade_cannot_pickup"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        handleDestroyBarricade(self, killer);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        handleDestroyBarricade(self, killer);
        return SCRIPT_CONTINUE;
    }
    public void handleDestroyBarricade(obj_id self, obj_id killer) throws InterruptedException
    {
        location death = getLocation(self);
        playClientEffectObj(killer, "clienteffect/combat_explosion_lair_large.cef", self, "");
        playClientEffectLoc(killer, "clienteffect/combat_explosion_lair_large.cef", death, 0);
        setInvulnerable(self, true);
        messageTo(self, "destroyHothBarricade", null, 1f, false);
        return;
    }
}
