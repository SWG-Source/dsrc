package script.item.publish_gift;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.player_structure;
import script.library.space_utils;
import script.library.space_transition;

public class magic_painting_cell extends script.base_script
{
    public magic_painting_cell()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "intializePaintingCell", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, "magicPainting");
        int minSwitchTime = getIntObjVar(controller, "timeStamp");
        if (!utils.isNestedWithinAPlayer(controller))
        {
            obj_id house = getTopMostContainer(controller);
            obj_id ship = space_transition.getContainingShip(controller);
            String templateName = "";
            if (isIdValid(ship))
            {
                templateName = getTemplateName(ship);
            }
            if ((isIdValid(house) && (player_structure.isBuilding(house)) || space_utils.isPobType(templateName)))
            {
                if (isGod(item))
                {
                    if (getGameTime() - minSwitchTime > 10)
                    {
                        if (hasObjVar(controller, "paintingId"))
                        {
                            obj_id painting = getObjIdObjVar(controller, "paintingId");
                            if (!exists(painting))
                            {
                                messageTo(controller, "createPainting", null, 3, false);
                            }
                            else 
                            {
                                messageTo(controller, "switchPainting", null, 3, false);
                            }
                        }
                        else 
                        {
                            messageTo(controller, "createPainting", null, 3, false);
                        }
                    }
                }
                else 
                {
                    if (getGameTime() - minSwitchTime > 300)
                    {
                        if (hasObjVar(controller, "paintingId"))
                        {
                            obj_id painting = getObjIdObjVar(controller, "paintingId");
                            if (!exists(painting))
                            {
                                messageTo(controller, "createPainting", null, 3, false);
                            }
                            else 
                            {
                                messageTo(controller, "switchPainting", null, 3, false);
                            }
                        }
                        else 
                        {
                            messageTo(controller, "createPainting", null, 3, false);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id magicPainting = obj_id.NULL_ID;
        if (hasObjVar(self, "magicPainting"))
        {
            magicPainting = getObjIdObjVar(self, "magicPainting");
        }
        if (!exists(magicPainting) || !isIdValid(magicPainting))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == magicPainting)
        {
            detachScript(self, "item.publish_gift.magic_painting_cell");
            removeObjVar(self, "magicPainting");
        }
        return SCRIPT_CONTINUE;
    }
    public int paintingControllerMoved(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "item.publish_gift.magic_painting_cell");
        if (hasObjVar(self, "magicPainting"))
        {
            removeObjVar(self, "magicPainting");
        }
        return SCRIPT_CONTINUE;
    }
    public int intializePaintingCell(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "magicPainting"))
        {
            detachScript(self, "item.publish_gift.magic_painting_cell");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id controller = getObjIdObjVar(self, "magicPainting");
            if (!exists(controller) || !isIdValid(controller))
            {
                detachScript(self, "item.publish_gift.magic_painting_cell");
                removeObjVar(self, "magicPainting");
                return SCRIPT_CONTINUE;
            }
            if (!utils.isNestedWithin(self, controller))
            {
                detachScript(self, "item.publish_gift.magic_painting_cell");
                removeObjVar(self, "magicPainting");
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
