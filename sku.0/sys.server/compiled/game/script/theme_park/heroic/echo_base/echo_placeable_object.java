package script.theme_park.heroic.echo_base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.create;
import script.library.factions;
import script.library.trial;
import script.library.utils;

public class echo_placeable_object extends script.base_script
{
    public echo_placeable_object()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isValidBarricadeLocation(self))
        {
            messageTo(self, "destroyHothBarricade", null, 1.0f, false);
        }
        else 
        {
            messageTo(self, "handleCheckForBarricadeCleanup", null, 6.0f, false);
        }
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
    public int handleCheckForBarricadeCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidBarricadeLocation(self))
        {
            messageTo(self, "destroyHothBarricade", null, 1.0f, false);
        }
        messageTo(self, "handleCheckForBarricadeCleanup", null, 6.0f, false);
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
    public int destroyHothBarricade(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isValidBarricadeLocation(self))
        {
            messageTo(self, "destroyHothBarricade", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("theme_park/heroic", "hoth_object_deploy"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnGroundTargetLoc(obj_id self, obj_id player, int menuItem, float x, float y, float z) throws InterruptedException
    {
        float yaw = getYaw(player);
        location playerLoc = getLocation(player);
        if (!playerLoc.area.equals("adventure2"))
        {
            trial.cleanupObject(self);
            return SCRIPT_CONTINUE;
        }
        if (menuItem == menu_info_types.ITEM_USE)
        {
            if (!utils.isNestedWithin(self, player))
            {
                return SCRIPT_CONTINUE;
            }
            location createLoc = new location(x, y, z, playerLoc.area, playerLoc.cell);
            String template = getStringObjVar(self, "object_template");
            if (template != null && template.length() > 0)
            {
                obj_id barricade = create.object(template, createLoc);
                if (isIdValid(barricade))
                {
                    if (trial.registerObjectWithSequencer(barricade))
                    {
                        setYaw(barricade, yaw);
                        trial.cleanupObject(self);
                    }
                    else 
                    {
                        trial.cleanupObject(barricade);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
