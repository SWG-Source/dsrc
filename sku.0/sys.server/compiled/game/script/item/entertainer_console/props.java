package script.item.entertainer_console;

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

public class props extends script.base_script
{
    public props()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id container = getLocation(self).cell;
        if (isIdValid(container))
        {
            boolean check = registerSelf(container);
            if (check)
            {
                String templateString = getTemplateName(self);
                if (templateString != null && templateString.equals("object/tangible/item/entertainer_console/stage_backdrop_generator.iff"))
                {
                    if (hasObjVar(self, "paintingId"))
                    {
                        obj_id oldBackdrop = getObjIdObjVar(self, "paintingId");
                        if (isIdValid(oldBackdrop))
                        {
                            destroyObject(oldBackdrop);
                        }
                        removeObjVar(self, "paintingId");
                        location backdropLocation = getLocation(self);
                        float myYaw = getYaw(self);
                        backdropLocation.y += 0.3f;
                        String staticString = getStringObjVar(self, "paintingType");
                        obj_id backdropId = static_item.createNewItemFunction(staticString, container, backdropLocation);
                        if (!isIdValid(backdropId) || isIdNull(backdropId))
                        {
                            return SCRIPT_CONTINUE;
                        }
                        else 
                        {
                            setYaw(backdropId, myYaw);
                            setObjVar(self, "paintingId", backdropId);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id topContainer = getTopMostContainer(self);
        String templateString = getTemplateName(self);
        if (templateString != null && templateString.equals("object/tangible/item/entertainer_console/stage_backdrop_generator.iff"))
        {
            if (hasObjVar(self, "paintingId"))
            {
                obj_id oldBackdrop = getObjIdObjVar(self, "paintingId");
                destroyObject(oldBackdrop);
                removeObjVar(self, "paintingId");
            }
        }
        if (templateString != null && templateString.equals("object/tangible/item/entertainer_console/stage_fog_machine.iff"))
        {
            if (hasObjVar(self, "fogId"))
            {
                obj_id oldFog = getObjIdObjVar(self, "fogId");
                if (isIdValid(oldFog))
                {
                    destroyObject(oldFog);
                }
                removeObjVar(self, "fogId");
            }
        }
        obj_id container = getLocation(self).cell;
        if (isIdValid(container) && checkArray(container))
        {
            boolean unreg = unregisterSelf();
        }
        if (isIdValid(destContainer))
        {
            boolean reg = registerSelf(destContainer);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkLocation(obj_id cell) throws InterruptedException
    {
        if (getCellName(cell) == null)
        {
            return false;
        }
        if (!utils.isNestedWithinAPlayer(cell))
        {
            obj_id house = getTopMostContainer(cell);
            obj_id ship = space_transition.getContainingShip(getSelf());
            String templateName = "";
            if (isIdValid(ship))
            {
                templateName = getTemplateName(ship);
            }
            if (isIdValid(house) && (player_structure.isBuilding(house) || space_utils.isPobType(templateName)))
            {
                return true;
            }
        }
        return false;
    }
    public boolean registerSelf(obj_id cell) throws InterruptedException
    {
        obj_id self = getSelf();
        if (isIdValid(cell) && !checkArray(cell) && checkLocation(cell))
        {
            String template = getTemplate();
            Vector objectList = new Vector();
            obj_id topContainer = getTopMostContainer(cell);
            if (utils.hasScriptVar(topContainer, template))
            {
                Vector list = utils.getResizeableObjIdArrayScriptVar(topContainer, template);
                for (int i = 0; i < list.size(); i++)
                {
                    objectList.addElement(((obj_id)list.get(i)));
                }
            }
            objectList.addElement(self);
            utils.setScriptVar(topContainer, template, objectList);
            return true;
        }
        return false;
    }
    public boolean unregisterSelf() throws InterruptedException
    {
        obj_id topContainer = getTopMostContainer(getSelf());
        obj_id self = getSelf();
        String template = getTemplate();
        if (isIdValid(topContainer) && utils.hasScriptVar(topContainer, template))
        {
            Vector objectList = utils.getResizeableObjIdArrayScriptVar(topContainer, template);
            for (int i = 0; i < objectList.size(); i++)
            {
                if (((obj_id)objectList.get(i)).equals(self))
                {
                    objectList.removeElement(self);
                    if (objectList.size() > 0)
                    {
                        utils.setScriptVar(topContainer, template, objectList);
                    }
                    else 
                    {
                        utils.removeScriptVar(topContainer, template);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    public boolean checkArray(obj_id cell) throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id topContainer = getTopMostContainer(self);
        String item = getTemplate();
        if (isIdValid(topContainer) && utils.hasScriptVar(topContainer, item))
        {
            Vector objectList = utils.getResizeableObjIdArrayScriptVar(topContainer, item);
            for (int i = 0; i < objectList.size(); i++)
            {
                if (((obj_id)objectList.get(i)) == self)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public String getTemplate() throws InterruptedException
    {
        String templateString = getTemplateName(getSelf());
        String template = "";
        if (templateString != null && templateString.equals("object/tangible/item/entertainer_console/stage_backdrop_generator.iff"))
        {
            template = "entertainer_console.backdrop";
        }
        if (templateString != null && templateString.equals("object/tangible/item/entertainer_console/stage_pyro_machine.iff"))
        {
            template = "entertainer_console.pyrotechnic";
        }
        if (templateString != null && templateString.equals("object/tangible/item/entertainer_console/stage_smoke_machine.iff"))
        {
            template = "entertainer_console.smoke";
        }
        if (templateString != null && templateString.equals("object/tangible/item/entertainer_console/stage_fog_machine.iff"))
        {
            template = "entertainer_console.fog";
        }
        if (templateString != null && templateString.equals("object/tangible/item/entertainer_console/stage_light.iff"))
        {
            template = "entertainer_console.light";
        }
        return template;
    }
}
