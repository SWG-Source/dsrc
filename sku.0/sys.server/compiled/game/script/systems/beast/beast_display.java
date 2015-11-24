package script.systems.beast;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.beast_lib;
import script.library.create;
import script.library.hue;
import script.library.incubator;
import script.library.sui;
import script.library.utils;

public class beast_display extends script.base_script
{
    public beast_display()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!hasObjVar(self, beast_lib.OBJVAR_OLD_PET_RENAMED) && getOwner(self) == player)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU3, new string_id("beast", "name_beast"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (item == menu_info_types.SERVER_MENU3 && !hasObjVar(self, beast_lib.OBJVAR_OLD_PET_RENAMED) && getOwner(self) == player)
        {
            sui.inputbox(self, player, "@beast:name_d", sui.OK_CANCEL, "@beast:name_t", sui.INPUT_NORMAL, null, "handleSetBeastName", null);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "beast"))
        {
            messageTo(self, "beast_display", null, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void turnOff(obj_id source) throws InterruptedException
    {
        if (isIdValid(source) && hasObjVar(source, "displayed"))
        {
            obj_id displayedBeast = getObjIdObjVar(source, "displayed");
            removeObjVar(source, "displayed");
            if (isIdValid(displayedBeast))
            {
                destroyObject(displayedBeast);
            }
        }
    }
    public void displayBeast(obj_id source) throws InterruptedException
    {
        location loc = getLocation(source);
        obj_id container = getContainedBy(source);
        obj_id displayedBeast = null;
        if (!isIdValid(container) || !hasObjVar(source, "beast") || !isIdValid(loc.cell) || loc.cell != container)
        {
            if (hasObjVar(source, "displayed"))
            {
                displayedBeast = getObjIdObjVar(source, "displayed");
                if (isIdValid(displayedBeast))
                {
                    destroyObject(displayedBeast);
                }
            }
            return;
        }
        if (hasObjVar(source, "displayed"))
        {
            displayedBeast = getObjIdObjVar(source, "displayed");
            destroyObject(displayedBeast);
        }
        displayedBeast = beast_lib.createBasicBeastFromObject(source);
        if (!isIdValid(displayedBeast))
        {
            LOG("beast", "Failed to display beast from decoration item: " + source);
            return;
        }
        setObjVar(source, "displayed", displayedBeast);
        setObjVar(displayedBeast, "beast_display", source);
        setInvulnerable(displayedBeast, true);
        setYaw(displayedBeast, getYaw(source));
        setLevel(displayedBeast, beast_lib.getBCDBeastLevel(source));
        setScale(displayedBeast, beast_lib.getBeastScaleByLevel(displayedBeast));
        setObjVar(displayedBeast, "unmoveable", 1);
        setObjVar(displayedBeast, "noEject", 1);
        utils.setScriptVar(displayedBeast, "do_not_pack", 1);
        attachScript(displayedBeast, "item.special.block_open");
        if (hasObjVar(source, "beast.beastName"))
        {
            setName(displayedBeast, getStringObjVar(source, "beast.beastName"));
        }
        attachScript(displayedBeast, "systems.beast.beast_stuffed");
    }
    public int beast_display(obj_id self, dictionary params) throws InterruptedException
    {
        displayBeast(self);
        return SCRIPT_CONTINUE;
    }
    public int furniture_rotated(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id beastDisplayed = getObjIdObjVar(self, "displayed");
        if (!isIdValid(beastDisplayed))
        {
            return SCRIPT_CONTINUE;
        }
        float yaw = getYaw(self);
        setYaw(beastDisplayed, yaw);
        return SCRIPT_CONTINUE;
    }
    public int furniture_moved(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id beastDisplayed = getObjIdObjVar(self, "displayed");
        if (!isIdValid(beastDisplayed))
        {
            return SCRIPT_CONTINUE;
        }
        displayBeast(self);
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        messageTo(self, "beast_display", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleSetBeastName(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String beastName = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (beastName.equals("") || isNameReserved(beastName))
        {
            sendSystemMessage(player, new string_id("player_structure", "obscene"));
            sui.inputbox(self, player, "@beast:name_d", sui.OK_CANCEL, "@beast:name_t", sui.INPUT_NORMAL, null, "handleSetBeastName", null);
            return SCRIPT_CONTINUE;
        }
        if (beastName.length() < 3)
        {
            sendSystemMessage(player, new string_id("beast", "name_too_short"));
            sui.inputbox(self, player, "@beast:name_d", sui.OK_CANCEL, "@beast:name_t", sui.INPUT_NORMAL, null, "handleSetBeastName", null);
            return SCRIPT_CONTINUE;
        }
        if (beastName.length() > 40)
        {
            sendSystemMessage(player, new string_id("beast", "name_too_long"));
            sui.inputbox(self, player, "@beast:name_d", sui.OK_CANCEL, "@beast:name_t", sui.INPUT_NORMAL, null, "handleSetBeastName", null);
            return SCRIPT_CONTINUE;
        }
        sendDirtyObjectMenuNotification(self);
        setObjVar(self, "beast.beastName", beastName);
        setObjVar(self, beast_lib.OBJVAR_OLD_PET_RENAMED, 1);
        setObjVar(self, incubator.DNA_PARENT_NAME, beastName);
        return SCRIPT_CONTINUE;
    }
    public int OnPack(obj_id self, dictionary params) throws InterruptedException
    {
        turnOff(self);
        return SCRIPT_CONTINUE;
    }
    public int OnUnpack(obj_id self, dictionary params) throws InterruptedException
    {
        displayBeast(self);
        return SCRIPT_CONTINUE;
    }
}
