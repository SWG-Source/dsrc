package script.item.conversion;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.armor;
import script.library.utils;
import script.library.sui;

public class armor_wookiee extends script.base_script
{
    public armor_wookiee()
    {
    }
    public static final String[] WOOKIEE_ASSAULT = 
    {
        "object/tangible/wearables/armor/kashyyykian_hunting/armor_kashyyykian_hunting_chestplate.iff",
        "object/tangible/wearables/armor/kashyyykian_hunting/armor_kashyyykian_hunting_bicep_l.iff",
        "object/tangible/wearables/armor/kashyyykian_hunting/armor_kashyyykian_hunting_bicep_r.iff"
    };
    public static final String[] WOOKIEE_BATTLE = 
    {
        "object/tangible/wearables/armor/kashyyykian_black_mtn/armor_kashyyykian_black_mtn_chestplate.iff",
        "object/tangible/wearables/armor/kashyyykian_black_mtn/armor_kashyyykian_black_mtn_bicep_l.iff",
        "object/tangible/wearables/armor/kashyyykian_black_mtn/armor_kashyyykian_black_mtn_bicep_r.iff"
    };
    public static final String[] WOOKIEE_RECON = 
    {
        "object/tangible/wearables/armor/kashyyykian_ceremonial/armor_kashyyykian_ceremonial_chestplate.iff",
        "object/tangible/wearables/armor/kashyyykian_ceremonial/armor_kashyyykian_ceremonial_bicep_l.iff",
        "object/tangible/wearables/armor/kashyyykian_ceremonial/armor_kashyyykian_ceremonial_bicep_r.iff"
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) == player)
        {
            if (hasObjVar(self, "armor.wookieeDeconstruct"))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU9, new string_id("craft_armor_ingredients_d", "wookiee_cybernetic_enable"));
            }
            else 
            {
                detachScript(self, "item.conversion.armor_wookiee");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) == player)
        {
            if (item == menu_info_types.SERVER_MENU9)
            {
                showConfirmationWindow(player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void showConfirmationWindow(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Are you sure you wish to Resize this Wookiee Chest Piece into 3 seperate pieces?  This will enable this armor to be used with Cybernetics";
        String title = "Cybernetic Enable Wookiee Chest Armor Piece";
        closeOldWindow(player);
        int pid = sui.msgbox(self, player, prompt, sui.OK_CANCEL, title, 0, "handleWookieeConfirmationSelect");
        setWindowPid(player, pid);
    }
    public void dismantleWookiee(obj_id player) throws InterruptedException
    {
        obj_id oldObject = getSelf();
        String templatename = getTemplateName(oldObject);
        String[] armorPieces = new String[]
        {
            "",
            "",
            ""
        };
        obj_id pInv = utils.getInventoryContainer(player);
        if (hasScript(oldObject, "item.conversion.armor_chest_wookie_conversion"))
        {
            sendSystemMessage(player, new string_id("quest_armorsmith", "must_refit"));
            return;
        }
        if (getVolumeFree(pInv) <= 3)
        {
            sendSystemMessage(player, new string_id("quest_armorsmith", "inventory_full"));
            return;
        }
        else 
        {
            if (templatename.equals("object/tangible/wearables/armor/kashyyykian_hunting/armor_kashyyykian_hunting_chest_plate.iff"))
            {
                armorPieces = WOOKIEE_ASSAULT;
            }
            else if (templatename.equals("object/tangible/wearables/armor/kashyyykian_black_mtn/armor_kashyyykian_black_mtn_chest_plate.iff"))
            {
                armorPieces = WOOKIEE_BATTLE;
            }
            else if (templatename.equals("object/tangible/wearables/armor/kashyyykian_ceremonial/armor_kashyyykian_ceremonial_chest_plate.iff"))
            {
                armorPieces = WOOKIEE_RECON;
            }
            if (armorPieces.length < 3)
            {
                CustomerServiceLog("armor_converion", "%TU attempted to dismantle Wookiee " + templatename + " however was not found in schematic listing", player);
                return;
            }
            for (int j = 0; j < armorPieces.length; ++j)
            {
                String armorTemplate = armorPieces[j];
                obj_id armorItem = createObject(armorTemplate, pInv, "");
                if (isIdValid(armorItem))
                {
                    copyObjVar(oldObject, armorItem, "armor");
                    copyObjVar(oldObject, armorItem, "skillmod");
                    int armorLevel = armor.getArmorLevel(armorItem);
                    int hitPoints = (armorLevel + 1) * 15000;
                    if (j == 0)
                    {
                        setMaxHitpoints(armorItem, hitPoints * 2);
                        setHitpoints(armorItem, hitPoints * 2);
                    }
                    else 
                    {
                        setMaxHitpoints(armorItem, hitPoints);
                        setHitpoints(armorItem, hitPoints);
                    }
                    setCraftedId(armorItem, armorItem);
                    setCrafter(armorItem, player);
                    setSkillModSockets(armorItem, 1);
                    removeObjVar(armorItem, "armor.wookieeDeconstruct");
                }
            }
            destroyObject(oldObject);
            sendSystemMessage(player, new string_id("quest_armorsmith", "wookiee_resized"));
        }
    }
    public void closeOldWindow(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "wookiee_armor.pid"))
        {
            int oldpid = utils.getIntScriptVar(player, "wookiee_armor.pid");
            forceCloseSUIPage(oldpid);
            utils.removeScriptVar(player, "wookiee_armor.pid");
        }
    }
    public void setWindowPid(obj_id player, int pid) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, "wookiee_armor.pid", pid);
        }
    }
    public int handleWookieeConfirmationSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            utils.removeScriptVar(player, "wookiee_armor.pid");
            return SCRIPT_CONTINUE;
        }
        dismantleWookiee(player);
        return SCRIPT_CONTINUE;
    }
}
