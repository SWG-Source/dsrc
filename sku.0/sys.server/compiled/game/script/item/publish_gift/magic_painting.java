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
import script.library.sui;
import script.library.static_item;
import script.library.space_utils;
import script.library.space_transition;

public class magic_painting extends script.base_script
{
    public magic_painting()
    {
    }
    public static final string_id MOVED_CTRL_OBJ = new string_id("spam", "magic_painting_ctrl_moved");
    public static final string_id ROOM_HAS_CTRL_OBJ = new string_id("spam", "magic_painting_ctrl_already");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!utils.isNestedWithinAPlayer(self))
        {
            obj_id house = getTopMostContainer(self);
            if (isIdValid(house) && player_structure.isBuilding(house))
            {
                obj_id containingCell = getLocation(self).cell;
                attachScript(containingCell, "item.publish_gift.magic_painting_cell");
                setObjVar(containingCell, "magicPainting", self);
                if (!hasObjVar(self, "timeStamp"))
                {
                    setObjVar(self, "timeStamp", getGameTime());
                    if (hasObjVar(self, "paintingId"))
                    {
                        obj_id painting = getObjIdObjVar(self, "paintingId");
                        if (!exists(painting))
                        {
                            messageTo(self, "createPainting", null, 3, false);
                        }
                    }
                    else 
                    {
                        messageTo(self, "createPainting", null, 3, false);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "intializePaintingController", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (hasObjVar(self, "paintingId"))
        {
            obj_id painting = getObjIdObjVar(self, "paintingId");
            if (exists(painting))
            {
                destroyObject(painting);
                removeObjVar(self, "paintingId");
                obj_id containingCell = getLocation(self).cell;
                messageTo(containingCell, "paintingControllerMoved", null, 1, false);
                sendSystemMessage(transferer, MOVED_CTRL_OBJ);
            }
            else 
            {
                removeObjVar(self, "paintingId");
                LOG("create", "Magic Painting control object was transferred by a player. It thought it had a painting created but could not find one");
            }
        }
        if (!utils.isNestedWithinAPlayer(destContainer))
        {
            obj_id house = getTopMostContainer(destContainer);
            obj_id ship = space_transition.getContainingShip(destContainer);
            String templateName = "";
            if (isIdValid(ship))
            {
                templateName = getTemplateName(ship);
            }
            if (isIdValid(house) && (player_structure.isBuilding(house) || space_utils.isPobType(templateName)))
            {
                obj_id containingCell = getLocation(transferer).cell;
                if (hasScript(containingCell, "item.publish_gift.magic_painting_cell"))
                {
                    if (destContainer == containingCell)
                    {
                        sendSystemMessage(transferer, ROOM_HAS_CTRL_OBJ);
                        return SCRIPT_OVERRIDE;
                    }
                }
                if (destContainer == containingCell)
                {
                    attachScript(containingCell, "item.publish_gift.magic_painting_cell");
                    setObjVar(containingCell, "magicPainting", self);
                    if (!hasObjVar(self, "timeStamp"))
                    {
                        setObjVar(self, "timeStamp", getGameTime());
                        if (hasObjVar(self, "paintingId"))
                        {
                            obj_id painting = getObjIdObjVar(self, "paintingId");
                            if (!exists(painting))
                            {
                                messageTo(self, "createPainting", null, 3, false);
                            }
                        }
                        else 
                        {
                            messageTo(self, "createPainting", null, 3, false);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int createPainting(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id house = getTopMostContainer(self);
        if (!exists(house) && !isIdValid(house))
        {
            return SCRIPT_CONTINUE;
        }
        location paintingLocation = getLocation(self);
        if (paintingLocation != null)
        {
            if (!isIdValid(paintingLocation.cell))
            {
                return SCRIPT_CONTINUE;
            }
            float myYaw = getYaw(self);
            paintingLocation.y -= 2.5f;
            int chosenPainting = rand(1, 10);
            obj_id painting = static_item.createNewItemFunction("item_publish_gift_magic_painting_" + chosenPainting + "_03_01", house, paintingLocation);
            if (!isIdValid(painting) || isIdNull(painting))
            {
                LOG("create", "Magic Painting control object tried to create a painting that returned null. There's probably a bad painting in the batch");
                return SCRIPT_CONTINUE;
            }
            else 
            {
                setYaw(painting, myYaw);
                setObjVar(self, "timeStamp", getGameTime());
                setObjVar(self, "paintingId", painting);
                utils.setScriptVar(painting, "controller", self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int switchPainting(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "paintingId"))
        {
            obj_id oldPainting = getObjIdObjVar(self, "paintingId");
            if (exists(oldPainting))
            {
                destroyObject(oldPainting);
                messageTo(self, "createPainting", null, 1, false);
            }
            else 
            {
                LOG("create", "Magic Painting control object tried to switch out a painting that didn't exist. Customer can pack up control object and replace it to reset it.");
            }
        }
        LOG("create", "Magic Painting control object tried to switch out a painting that didn't exist. Customer can pack up control object and replace it to reset it.");
        return SCRIPT_CONTINUE;
    }
    public int intializePaintingController(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.isNestedWithinAPlayer(self))
        {
            obj_id house = getTopMostContainer(self);
            obj_id ship = space_transition.getContainingShip(self);
            String templateName = "";
            if (isIdValid(ship))
            {
                templateName = getTemplateName(ship);
            }
            if ((isIdValid(house) && (player_structure.isBuilding(house)) || space_utils.isPobType(templateName)))
            {
                obj_id containingCell = getLocation(self).cell;
                if (!hasScript(containingCell, "item.publish_gift.magic_painting_cell"))
                {
                    attachScript(containingCell, "item.publish_gift.magic_painting_cell");
                }
                if (!hasObjVar(containingCell, "magicPainting"))
                {
                    setObjVar(containingCell, "magicPainting", self);
                }
                if (!hasObjVar(self, "timeStamp"))
                {
                    setObjVar(self, "timeStamp", getGameTime());
                    if (hasObjVar(self, "paintingId"))
                    {
                        obj_id painting = getObjIdObjVar(self, "paintingId");
                        if (!exists(painting))
                        {
                            messageTo(self, "createPainting", null, 3, false);
                        }
                    }
                    else 
                    {
                        messageTo(self, "createPainting", null, 3, false);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
