package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.utils;
import script.library.sui;

public class workbench extends script.base_script
{
    public workbench()
    {
    }
    public static final string_id AGUMENTED_REBREATHER = new string_id("dungeon/death_watch", "agumented_rebreather");
    public static final string_id MNU_WORKBENCH = new string_id("dungeon/death_watch", "mnu_workbench");
    public static final string_id MISSING_COMPONENT = new string_id("dungeon/death_watch", "missing_component");
    public static final string_id MISSING_COMPONENT2 = new string_id("dungeon/death_watch", "missing_component2");
    public static final string_id NOT_SKILLED = new string_id("dungeon/death_watch", "not_skilled");
    public static final string_id MNU_FILTER = new string_id("dungeon/death_watch", "mnu_filter");
    public static final string_id AUGMENTED_FILTER = new string_id("dungeon/death_watch", "agumented_filter");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = player_structure.getStructure(player);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        int mnuRebreather = mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_WORKBENCH);
        int mnuFilter = mi.addRootMenu(menu_info_types.SERVER_MENU2, MNU_FILTER);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = player_structure.getStructure(player);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (checkForGasMask(player) == false)
            {
                sendSystemMessage(player, MISSING_COMPONENT);
                return SCRIPT_CONTINUE;
            }
            if (checkForEnhancedFilter(player) == false)
            {
                sendSystemMessage(player, MISSING_COMPONENT);
                return SCRIPT_CONTINUE;
            }
            combineGasMask(player);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            if (checkForFilter(player) == false)
            {
                sendSystemMessage(player, MISSING_COMPONENT2);
                return SCRIPT_CONTINUE;
            }
            if (checkForGelPacket(player) == false)
            {
                sendSystemMessage(player, MISSING_COMPONENT2);
                return SCRIPT_CONTINUE;
            }
            if (!utils.isProfession(player, utils.TRADER))
            {
                sendSystemMessage(player, NOT_SKILLED);
                return SCRIPT_CONTINUE;
            }
            combineFilter(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkForGasMask(obj_id player) throws InterruptedException
    {
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/wearables/goggles/rebreather.iff"))
                {
                    obj_id mask = objContents[intI];
                    if (!hasObjVar(mask, "death_watch_ready"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean checkForFilter(obj_id player) throws InterruptedException
    {
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/filter.iff"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean checkForEnhancedFilter(obj_id player) throws InterruptedException
    {
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/enhanced_filter.iff"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean checkForGelPacket(obj_id player) throws InterruptedException
    {
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/gel_packet.iff"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void combineGasMask(obj_id player) throws InterruptedException
    {
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/wearables/goggles/rebreather.iff"))
                {
                    obj_id mask = objContents[intI];
                    if (!hasObjVar(mask, "death_watch_ready"))
                    {
                        setObjVar(mask, "death_watch_ready", 1);
                        sendSystemMessage(player, AGUMENTED_REBREATHER);
                        break;
                    }
                }
            }
        }
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/enhanced_filter.iff"))
                {
                    obj_id filter = objContents[intI];
                    destroyObject(filter);
                    break;
                }
            }
        }
        return;
    }
    public void combineFilter(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/filter.iff"))
                {
                    obj_id filter = objContents[intI];
                    if (isIdValid(filter))
                    {
                        destroyObject(filter);
                        obj_id playerInv = getObjectInSlot(player, "inventory");
                        if (isIdValid(playerInv))
                        {
                            sendSystemMessage(player, AUGMENTED_FILTER);
                            obj_id item = createObject("object/tangible/dungeon/death_watch_bunker/enhanced_filter.iff", playerInv, "");
                            break;
                        }
                    }
                }
            }
        }
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/gel_packet.iff"))
                {
                    obj_id packet = objContents[intI];
                    destroyObject(packet);
                    break;
                }
            }
        }
        return;
    }
}
