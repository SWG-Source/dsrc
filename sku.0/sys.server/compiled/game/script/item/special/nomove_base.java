package script.item.special;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.pet_lib;
import script.library.player_structure;
import script.library.structure;
import script.library.space_transition;
import script.library.space_utils;

public class nomove_base extends script.base_script
{
    public nomove_base()
    {
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id dest, obj_id transferer) throws InterruptedException
    {
        if (!isIdValid(transferer))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(transferer))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(self);
        if (!isIdValid(owner) || !isPlayer(owner))
        {
            owner = transferer;
        }
        if (pet_lib.isPet(dest))
        {
            return SCRIPT_OVERRIDE;
        }
        if (owner != transferer)
        {
            if (isNoTradeShared(self))
            {
                obj_id container = getContainedBy(self);
                if (isIdValid(container) && (getTemplateName(container)).equals(structure.TEMPLATE_CELL))
                {
                    if (isIdValid(dest))
                    {
                        obj_id transfererInv = utils.getInventoryContainer(transferer);
                        if (isIdValid(transfererInv) && (dest == transfererInv))
                        {
                            int transfererStationId = getPlayerStationId(transferer);
                            if ((transfererStationId != 0) && (transfererStationId == getPlayerStationId(owner)))
                            {
                                return SCRIPT_CONTINUE;
                            }
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
        location loc = getLocation(transferer);
        obj_id inv = utils.getInventoryContainer(owner);
        obj_id bank = utils.getPlayerBank(owner);
        obj_id cell = loc.cell;
        obj_id appearanceInv = getAppearanceInventory(owner);
        LOG("nomove", "Source = " + transferer + "; Dest = " + dest + "; Owner = " + owner + "; Inv = " + inv + "; Bank = " + bank + "; Appearance Inventory = " + appearanceInv);
        if (!isIdValid(inv) || !isIdValid(bank))
        {
            return SCRIPT_OVERRIDE;
        }
        if (dest == owner || dest == inv || dest == bank || dest == appearanceInv)
        {
            return SCRIPT_CONTINUE;
        }
        boolean inPlayerHouseOrPOB = false;
        if (isIdValid(cell))
        {
            if (dest == cell)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                obj_id currentParent = getContainedBy(dest);
                while (isIdValid(currentParent) && !inPlayerHouseOrPOB)
                {
                    inPlayerHouseOrPOB = currentParent == cell;
                    currentParent = getContainedBy(currentParent);
                }
            }
        }
        obj_id player = utils.getContainingPlayer(dest);
        if (player == transferer || inPlayerHouseOrPOB)
        {
            if (hasObjVar(dest, "noTrade"))
            {
                return SCRIPT_CONTINUE;
            }
            attachScript(dest, "item.special.nomove_container");
            return SCRIPT_CONTINUE;
        }
        if (isGod(transferer))
        {
            sendSystemMessageTestingOnly(transferer, "[GOD MODE] You have been allowed to move a No Trade item.  This item may now be tradable by players if it is in a container!");
            return SCRIPT_CONTINUE;
        }
        String name = getString(getNameStringId(self));
        String msg = name + " may only be equipped by the owner, placed in the owner's inventory, the owner's house, a container in the owners house (must be in the same room when transferring), or placed in the owner's bank.";
        String title = "INFO";
        sui.msgbox(owner, owner, msg, sui.OK_ONLY, title, "noHandler");
        return SCRIPT_OVERRIDE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (isIdValid(self))
        {
            if (hasObjVar(self, "noTrade"))
            {
                return SCRIPT_CONTINUE;
            }
            if (utils.isNestedWithinAPlayer(destContainer))
            {
                setObjVar(self, "noTrade", true);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
