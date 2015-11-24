package script.item.conversion;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;

public class armor_ris extends script.base_script
{
    public armor_ris()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) == player)
        {
            if (hasObjVar(self, "armor.risDeconstruct"))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("craft_armor_ingredients_d", "dismantle_ris"));
            }
            else 
            {
                detachScript(self, "item.conversion.armor_ris");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) == player)
        {
            if (item == menu_info_types.SERVER_MENU1)
            {
                showConfirmationWindow(player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void showConfirmationWindow(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Are you sure you wish to DESTROY this piece of armor and create an RIS armor segment used in the constrution of RIS armor?";
        String title = "Dismantle RIS Armor Piece";
        closeOldWindow(player);
        int pid = sui.msgbox(self, player, prompt, sui.OK_CANCEL, title, 0, "handleConfirmationSelect");
        setWindowPid(player, pid);
    }
    public void dismantleRis(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id pInv = utils.getInventoryContainer(player);
        if (getVolumeFree(pInv) <= 1)
        {
            sendSystemMessage(player, new string_id("quest_armorsmith", "inventory_full"));
            return;
        }
        else 
        {
            destroyObject(self);
            obj_id segment = createObject("object/tangible/component/armor/armor_segment_ris.iff", pInv, "");
            if (isIdValid(segment))
            {
                setCraftedId(segment, segment);
                setCrafter(segment, player);
                sendSystemMessage(player, new string_id("quest_armorsmith", "segment_issued"));
            }
        }
    }
    public void closeOldWindow(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "ris_armor.pid"))
        {
            int oldpid = utils.getIntScriptVar(player, "ris_armor.pid");
            forceCloseSUIPage(oldpid);
            utils.removeScriptVar(player, "ris_armor.pid");
        }
    }
    public void setWindowPid(obj_id player, int pid) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, "ris_armor.pid", pid);
        }
    }
    public int handleConfirmationSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            utils.removeScriptVar(player, "ris_armor.pid");
            return SCRIPT_CONTINUE;
        }
        dismantleRis(player);
        return SCRIPT_CONTINUE;
    }
}
