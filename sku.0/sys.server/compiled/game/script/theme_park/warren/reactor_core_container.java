package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.colors;

public class reactor_core_container extends script.base_script
{
    public reactor_core_container()
    {
    }
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public static final String COREROD_TEMPLATE = "object/tangible/mission/quest_item/warren_core_control_rod_s01.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setOwner(self, obj_id.NULL_ID);
        setName(self, "");
        setName(self, new string_id(SYSTEM_MESSAGES, "core_name_on"));
        return SCRIPT_CONTINUE;
    }
    public void checkForReactorShutDown(obj_id self) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        if (!isIdValid(bldg))
        {
            return;
        }
        obj_id[] contents = getContents(self);
        if (contents == null)
        {
            return;
        }
        int coreCount = 0;
        for (int i = 0; i < contents.length; i++)
        {
            if (hasObjVar(contents[i], "warren.reactorControlRod"))
            {
                coreCount++;
            }
            if (coreCount > 1)
            {
                shutDownCore(self, bldg);
                return;
            }
        }
    }
    public void checkForReactorShutDown(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        obj_id[] contents = getContents(utils.getInventoryContainer(player));
        if (contents == null)
        {
            return;
        }
        int coreCount = 0;
        for (int i = 0; i < contents.length; i++)
        {
            if (hasObjVar(contents[i], "warren.reactorControlRod"))
            {
                moveCoreRodFromPlayerToContainer(contents[i], self);
                sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "insert_rod"));
                doAnimationAction(player, "manipulate_low");
                coreCount++;
            }
            if (coreCount > 1)
            {
                shutDownCore(self, bldg);
                return;
            }
            else if (coreCount > 0)
            {
                checkForReactorShutDown(self);
            }
        }
    }
    public void shutDownCore(obj_id self, obj_id bldg) throws InterruptedException
    {
        permissionsMakePublic(getCellId(getTopMostContainer(self), "plusroom84"));
        utils.setScriptVar(bldg, "warren.reactorOverriden", true);
        messageTo(self, "handleReactivateCore", null, 3600, false);
        showFlyText(self, new string_id(SYSTEM_MESSAGES, "deactivate_core"), 1.75f, colors.GREENYELLOW);
        setName(self, "");
        setName(self, new string_id(SYSTEM_MESSAGES, "core_name_off"));
        obj_id[] contents = getContents(self);
        if (contents == null)
        {
            return;
        }
        for (int i = 0; i < contents.length; i++)
        {
            destroyObject(contents[i]);
        }
    }
    public int handleReactivateCore(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        if (!isIdValid(bldg))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(bldg, "warren.reactorOverriden");
        permissionsMakePrivate(getCellId(getTopMostContainer(self), "plusroom84"));
        setName(self, "");
        setName(self, new string_id(SYSTEM_MESSAGES, "core_name_on"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        location here = getLocation(player);
        location term = getLocation(self);
        float range = getDistance(here, term);
        if (range > 2.0)
        {
            sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "elev_range"));
            return SCRIPT_OVERRIDE;
        }
        checkForReactorShutDown(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnOpenedContainer(obj_id self, obj_id player) throws InterruptedException
    {
        location here = getLocation(player);
        location term = getLocation(self);
        float range = getDistance(here, term);
        if (range > 2.0)
        {
            sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "elev_range"));
            return SCRIPT_OVERRIDE;
        }
        checkForReactorShutDown(self, player);
        return SCRIPT_CONTINUE;
    }
    public void moveCoreRodFromPlayerToContainer(obj_id coreRod, obj_id container) throws InterruptedException
    {
        destroyObject(coreRod);
        coreRod = createObject(COREROD_TEMPLATE, container, "");
        setObjVar(coreRod, "warren.reactorControlRod", true);
        setName(coreRod, "");
        setName(coreRod, new string_id(SYSTEM_MESSAGES, "core_rod_name"));
    }
}
