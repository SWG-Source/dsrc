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
import script.library.armor;

public class armor_base_conversion extends script.base_script
{
    public armor_base_conversion()
    {
    }
    public static final String ARMOR_SET_PREFIX = "object/tangible/wearables/armor/";
    public static final String[] ARMOR_SET_ASSAULT = 
    {
    };
    public static final String[] ARMOR_SET_BATTLE = 
    {
    };
    public static final String[] ARMOR_SET_RECON = 
    {
    };
    public static final String[] ARMOR_TYPE = 
    {
        "Assault",
        "Battle",
        "Reconnaissance"
    };
    public static final String[] ASSAULT_TYPE = 
    {
    };
    public static final String[] BATTLE_TYPE = 
    {
    };
    public static final String[] RECON_TYPE = 
    {
    };
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) == player)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU8, new string_id("craft_armor_ingredients_d", "refit_armor"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) == player)
        {
            if (item == menu_info_types.SERVER_MENU8)
            {
                showConfirmationWindow(player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void showConfirmationWindow(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Are you sure you wish to REFIT this piece of armor to Assault, Battle or Reconnaissance? YOU WILL ONLY BE ABLE TO REFIT A PIECE OF ARMOR ONCE!";
        String title = "Refit Armor Piece";
        closeOldWindow(player);
        int pid = sui.msgbox(self, player, prompt, sui.OK_CANCEL, title, 0, "handleConfirmationSelect");
        setWindowPid(player, pid);
    }
    public void refitArmor(obj_id player, String newItemTemplate, obj_id oldObject, int armorCategory) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        if (getVolumeFree(pInv) <= 1)
        {
            sendSystemMessage(player, new string_id("quest_armorsmith", "inventory_full"));
            return;
        }
        else 
        {
            if (newItemTemplate.length() < 1)
            {
                CustomerServiceLog("armor_converion", "%TU attempted to dismantle Armor " + newItemTemplate + " however was not found in template listing", player);
                return;
            }
            obj_id newObj = createObject(ARMOR_SET_PREFIX + newItemTemplate, pInv, "");
            if (isIdValid(newObj))
            {
                copyObjVar(oldObject, newObj, "armor");
                copyObjVar(oldObject, newObj, "skillmod");
                setObjVar(newObj, "armor.armorCategory", armorCategory);
                setMaxHitpoints(newObj, getMaxHitpoints(oldObject));
                setHitpoints(newObj, getHitpoints(oldObject));
                armor.removeArmorSpecialProtection(newObj, armor.DATATABLE_RECON_LAYER);
                armor.removeArmorSpecialProtection(newObj, armor.DATATABLE_ASSAULT_LAYER);
                int gotType = getGameObjectType(oldObject);
                if (gotType != GOT_armor_hand && gotType != GOT_armor_foot)
                {
                    if (armorCategory == AC_reconnaissance)
                    {
                        armor.setArmorSpecialProtectionPercent(newObj, armor.DATATABLE_RECON_LAYER, 1.0f);
                    }
                    else if (armorCategory == AC_assault)
                    {
                        armor.setArmorSpecialProtectionPercent(newObj, armor.DATATABLE_ASSAULT_LAYER, 1.0f);
                    }
                }
                if (hasObjVar(newObj, "armor.wookieeDeconstruct"))
                {
                    attachScript(newObj, "item.conversion.armor_wookiee");
                }
                int oldSockets = getSkillModSockets(oldObject);
                destroyObject(oldObject);
                setCraftedId(newObj, newObj);
                setCrafter(newObj, player);
                if (oldSockets > 0)
                {
                    setSkillModSockets(newObj, 1);
                }
                else 
                {
                    setSkillModSockets(newObj, 0);
                }
                sendSystemMessage(player, new string_id("quest_armorsmith", "armor_issued"));
            }
            else 
            {
                CustomerServiceLog("armor_converion", "Server attempted to create Armor " + newItemTemplate + " for %TU but failed", player);
                return;
            }
        }
    }
    public void closeOldWindow(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "refit_armor.pid"))
        {
            int oldpid = utils.getIntScriptVar(player, "refit_armor.pid");
            forceCloseSUIPage(oldpid);
            utils.removeScriptVar(player, "refit_armor.pid");
        }
    }
    public void setWindowPid(obj_id player, int pid) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, "refit_armor.pid", pid);
        }
    }
    public int handleConfirmationSelect(obj_id self, dictionary params) throws InterruptedException
    {
        String prompt = "Armor Types you may wear are assigned by Combat Profession. \n\n" + "Bounty Hunters, Commandos, Squad Leaders wear ASSAULT. \n\n" + "Swordsmen, Carbineers, Fencers, Pikemen, Combat Medics, Doctors wear BATTLE. \n\n" + "Pistoleers, Riflemen, Smugglers wear RECONNAISSANCE.";
        String title = "Armor Refit";
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            utils.removeScriptVar(player, "refit_armor.pid");
            return SCRIPT_CONTINUE;
        }
        closeOldWindow(player);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, ARMOR_TYPE, "handleArmorType", true, false);
        setWindowPid(player, pid);
        return SCRIPT_CONTINUE;
    }
    public int handleArmorType(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String prompt = "Select the Armor Appearance you desire";
        String title = "Armor Refit";
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        closeOldWindow(player);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx == -1 || idx > ARMOR_TYPE.length)
        {
            return SCRIPT_CONTINUE;
        }
        String[] choices = new String[0];
        String handler = "";
        switch (idx)
        {
            case 0:
            choices = getAssaultTypes();
            handler = "handleAssaultSelect";
            break;
            case 1:
            choices = getBattleTypes();
            handler = "handleBattleSelect";
            break;
            case 2:
            choices = getReconTypes();
            handler = "handleReconSelect";
            break;
            default:
            return SCRIPT_CONTINUE;
        }
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, choices, handler, true, false);
        setWindowPid(player, pid);
        return SCRIPT_CONTINUE;
    }
    public int handleAssaultSelect(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        closeOldWindow(player);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String[] assaultTemplates = getAssaultTemplates();
        if (idx == -1 || idx > assaultTemplates.length)
        {
            return SCRIPT_CONTINUE;
        }
        refitArmor(player, assaultTemplates[idx], self, AC_assault);
        return SCRIPT_CONTINUE;
    }
    public int handleBattleSelect(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        closeOldWindow(player);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String[] battleTemplates = getBattleTemplates();
        if (idx == -1 || idx > battleTemplates.length)
        {
            return SCRIPT_CONTINUE;
        }
        refitArmor(player, battleTemplates[idx], self, AC_battle);
        return SCRIPT_CONTINUE;
    }
    public int handleReconSelect(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        closeOldWindow(player);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String[] reconTemplates = getReconTemplates();
        if (idx == -1 || idx > reconTemplates.length)
        {
            return SCRIPT_CONTINUE;
        }
        refitArmor(player, reconTemplates[idx], self, AC_reconnaissance);
        return SCRIPT_CONTINUE;
    }
    public String[] getAssaultTemplates() throws InterruptedException
    {
        return ARMOR_SET_ASSAULT;
    }
    public String[] getBattleTemplates() throws InterruptedException
    {
        return ARMOR_SET_BATTLE;
    }
    public String[] getReconTemplates() throws InterruptedException
    {
        return ARMOR_SET_RECON;
    }
    public String[] getAssaultTypes() throws InterruptedException
    {
        return ASSAULT_TYPE;
    }
    public String[] getBattleTypes() throws InterruptedException
    {
        return BATTLE_TYPE;
    }
    public String[] getReconTypes() throws InterruptedException
    {
        return RECON_TYPE;
    }
}
