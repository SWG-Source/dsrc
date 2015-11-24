package script.item.special;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.craftinglib;
import script.library.pet_lib;
import script.library.player_structure;
import script.library.structure;
import script.library.sui;
import script.library.utils;

public class nomove_furniture_house_only extends script.base_script
{
    public nomove_furniture_house_only()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "notrade"))
        {
            removeObjVar(self, "notrade");
        }
        setObjVar(self, "noTrade", true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "notrade"))
        {
            removeObjVar(self, "notrade");
        }
        setObjVar(self, "noTrade", true);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id dest, obj_id transferer) throws InterruptedException
    {
        if (!isPlayer(transferer) || isGod(transferer))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(self);
        if (!isIdValid(owner))
        {
            return SCRIPT_OVERRIDE;
        }
        if (owner != transferer)
        {
            if (isNoTradeShared(self))
            {
                obj_id container = getContainedBy(self);
                if (isIdValid(container) && (getTemplateName(container)).equals(structure.TEMPLATE_CELL) && isIdValid(dest))
                {
                    obj_id transfererInv = utils.getInventoryContainer(transferer);
                    if (isIdValid(transfererInv) && (dest == transfererInv))
                    {
                        int transfererStationId = getPlayerStationId(transferer);
                        if (transfererStationId != 0 && transfererStationId == getPlayerStationId(owner))
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
            String name = getString(getNameStringId(self));
            String msg = "You are not the owner of " + name + "\n";
            msg += " This item may only be picked up by the owner.";
            String title = "INFO";
            sui.msgbox(transferer, transferer, msg, sui.OK_ONLY, title, "noHandler");
            return SCRIPT_OVERRIDE;
        }
        if (pet_lib.isPet(dest))
        {
            return SCRIPT_OVERRIDE;
        }
        location loc = getLocation(transferer);
        obj_id inv = utils.getInventoryContainer(owner);
        obj_id cell = loc.cell;
        LOG("nomove", "Source = " + transferer + "; Dest = " + dest + "; Owner = " + owner + "; Inv = " + inv);
        if (!isIdValid(inv))
        {
            return SCRIPT_OVERRIDE;
        }
        if (dest == owner || dest == inv)
        {
            return SCRIPT_CONTINUE;
        }
        String validHouseName = null;
        if (isIdValid(cell) && dest == cell)
        {
            obj_id house = getTopMostContainer(transferer);
            if (isIdValid(house) && exists(house) && player_structure.isOwner(house, transferer))
            {
                String houseTemplate = getTemplateName(house);
                String validTemplate = getStringObjVar(self, "validHouse");
                validHouseName = getStringObjVar(self, "validHouseName");
                if (houseTemplate.equals(validTemplate))
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        String name = getString(getNameStringId(self));
        String msg = name + " may only be equipped, placed in your inventory or house.\n";
        msg += " This item may *not* be placed in containers other than your top-level inventory or house.";
        if (validHouseName != null && validHouseName.length() > 0)
        {
            msg = name + " may only be equipped, placed in your inventory or " + validHouseName + " (house).\n";
            msg += " This item may *not* be placed in containers other than your top-level inventory or house.";
        }
        String title = "INFO";
        sui.msgbox(owner, owner, msg, sui.OK_ONLY, title, "noHandler");
        return SCRIPT_OVERRIDE;
    }
}
