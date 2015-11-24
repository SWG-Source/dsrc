package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.static_item;
import script.library.utils;

public class tcg_armor_kit extends script.base_script
{
    public tcg_armor_kit()
    {
    }
    public static final String TEXTURE_CUSTVAR_NAME = "/private/index_texture_1";
    public static final String COLOR_CUSTVAR_NAME_01 = "/private/index_color_1";
    public static final String COLOR_CUSTVAR_NAME_02 = "/private/index_color_2";
    public static final String TEXTURE_OBJVAR_NAME = "armor_kit.previousTexture";
    public static final String COLOR_OBJVAR_NAME_01 = "armor_kit.previousColor_1";
    public static final String COLOR_OBJVAR_NAME_02 = "armor_kit.previousColor_2";
    public static final String ARMOR_KIT_TYPE_OBJVAR = "armor_kit.kitType";
    public static final String ARMOR_KIT_DATA_TABLE = "datatables/tcg/tcg_armor_kit_data.iff";
    public static final String DATA_ARMOR_KIT_TYPE_COLUMN = "amor_kit_type";
    public static final String DATA_ELIGIBLE_ARMOR_COLUMN = "eligible_armor_appearance_substring";
    public static final String DATA_EXCLUDED_ARMOR_COLUMN = "excluded_armor_appearance_string";
    public static final String DATA_TEXTURE_COLUMN = "index_texture_1";
    public static final String DATA_COLOR_1_COLUMN = "index_color_1";
    public static final String DATA_COLOR_2_COLUMN = "index_color_2";
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (utils.hasLocalVar(self, "ctsBeingUnpacked"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, transferer))
        {
            sendSystemMessage(transferer, new string_id("tcg", "armor_kit_not_in_inventory"));
            return SCRIPT_OVERRIDE;
        }
        if (!shouldBeInKit(item, transferer, self))
        {
            sendSystemMessage(transferer, new string_id("tcg", "armor_kit_not_eligible_item"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean shouldBeInKit(obj_id item, obj_id player, obj_id armorKit) throws InterruptedException
    {
        dictionary armorKitData = getArmorKitData(armorKit, player);
        if (armorKitData == null)
        {
            return false;
        }
        String itemAppearanceName = getAppearance(item);
        String excludedArmorAppearances = armorKitData.getString(DATA_EXCLUDED_ARMOR_COLUMN);
        if (checkForMatchingArmorAppearance(excludedArmorAppearances, itemAppearanceName, player))
        {
            return false;
        }
        String eligibleArmorAppearances = armorKitData.getString(DATA_ELIGIBLE_ARMOR_COLUMN);
        if (checkForMatchingArmorAppearance(eligibleArmorAppearances, itemAppearanceName, player))
        {
            return true;
        }
        return false;
    }
    public dictionary getArmorKitData(obj_id armorKit, obj_id player) throws InterruptedException
    {
        if (hasObjVar(armorKit, ARMOR_KIT_TYPE_OBJVAR))
        {
            String armorKitType = getStringObjVar(armorKit, ARMOR_KIT_TYPE_OBJVAR);
            dictionary armorKitData = dataTableGetRow(ARMOR_KIT_DATA_TABLE, armorKitType);
            if (armorKitData != null && !armorKitData.isEmpty())
            {
                return armorKitData;
            }
        }
        if (isGod(player))
        {
            sendSystemMessage(player, "GOD MODE: Invalid armor kit data. Could not find armor kit type or armor kit data.", "");
        }
        return null;
    }
    public boolean checkForMatchingArmorAppearance(String targetArmorAppearances, String itemAppearanceName, obj_id player) throws InterruptedException
    {
        if (targetArmorAppearances != null && targetArmorAppearances.length() > 0)
        {
            String[] targetArmorList = split(targetArmorAppearances, ',');
            if (targetArmorList != null && targetArmorList.length > 0)
            {
                for (int i = 0; i < targetArmorList.length; i++)
                {
                    String targetArmorAppearance = targetArmorList[i];
                    if (targetArmorAppearance != null && targetArmorAppearance.length() > 0)
                    {
                        if (itemAppearanceName.indexOf(targetArmorAppearance) > -1)
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        string_id strSpam = new string_id("tcg", "armor_kit_activate");
        mi.addRootMenu(menu_info_types.SERVER_MENU10, strSpam);
        obj_id[] armorPieces = getContents(self);
        if (armorPieces != null && armorPieces.length > 0)
        {
            string_id strMoreSpam = new string_id("tcg", "armor_kit_flush_kit");
            mi.addRootMenu(menu_info_types.SERVER_MENU11, strMoreSpam);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (!utils.isNestedWithin(self, player))
        {
            sendSystemMessage(player, new string_id("tcg", "armor_kit_not_in_inventory"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU10 || item == menu_info_types.SERVER_MENU11)
        {
            obj_id[] stuff = getContents(self);
            if (stuff == null || stuff.length <= 0)
            {
                sendSystemMessage(player, new string_id("tcg", "armor_kit_empty_kit"));
                return SCRIPT_CONTINUE;
            }
            if (item == menu_info_types.SERVER_MENU10)
            {
                activateArmorKit(self, player);
            }
            if (item == menu_info_types.SERVER_MENU11)
            {
                flushArmorKit(self, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void activateArmorKit(obj_id armorKit, obj_id player) throws InterruptedException
    {
        obj_id[] armorPieces = getContents(armorKit);
        if (armorPieces != null && armorPieces.length > 0)
        {
            for (int i = 0; i < armorPieces.length; i++)
            {
                obj_id armor = armorPieces[i];
                if (shouldBeInKit(armor, player, armorKit))
                {
                    if (hasObjVar(armor, TEXTURE_OBJVAR_NAME))
                    {
                        int previousTexture = getIntObjVar(armor, TEXTURE_OBJVAR_NAME);
                        int previousColor_1 = getIntObjVar(armor, COLOR_OBJVAR_NAME_01);
                        int previousColor_2 = getIntObjVar(armor, COLOR_OBJVAR_NAME_02);
                        setRangedIntCustomVarValue(armor, TEXTURE_CUSTVAR_NAME, previousTexture);
                        setRangedIntCustomVarValue(armor, COLOR_CUSTVAR_NAME_01, previousColor_1);
                        setRangedIntCustomVarValue(armor, COLOR_CUSTVAR_NAME_02, previousColor_2);
                        removeObjVar(armor, TEXTURE_OBJVAR_NAME);
                        removeObjVar(armor, COLOR_OBJVAR_NAME_01);
                        removeObjVar(armor, COLOR_OBJVAR_NAME_02);
                        prose_package pp = new prose_package();
                        prose.setStringId(pp, new string_id("tcg", "armor_kit_texture_reverted"));
                        prose.setTT(pp, getEncodedName(armor));
                        sendSystemMessageProse(player, pp);
                    }
                    else 
                    {
                        dictionary armorKitData = getArmorKitData(armorKit, player);
                        if (armorKitData == null)
                        {
                            return;
                        }
                        int kitTexture = armorKitData.getInt(DATA_TEXTURE_COLUMN);
                        int kitColor_1 = armorKitData.getInt(DATA_COLOR_1_COLUMN);
                        int kitColor_2 = armorKitData.getInt(DATA_COLOR_2_COLUMN);
                        int currentTexture = getRangedIntCustomVarValue(armor, TEXTURE_CUSTVAR_NAME);
                        int currentColor_1 = getRangedIntCustomVarValue(armor, COLOR_CUSTVAR_NAME_01);
                        int currentColor_2 = getRangedIntCustomVarValue(armor, COLOR_CUSTVAR_NAME_02);
                        setObjVar(armor, TEXTURE_OBJVAR_NAME, currentTexture);
                        setObjVar(armor, COLOR_OBJVAR_NAME_02, currentColor_2);
                        setObjVar(armor, COLOR_OBJVAR_NAME_01, currentColor_1);
                        if (kitColor_1 <= -1)
                        {
                            kitColor_1 = currentColor_1;
                        }
                        if (kitColor_2 <= -1)
                        {
                            kitColor_2 = currentColor_2;
                        }
                        setRangedIntCustomVarValue(armor, TEXTURE_CUSTVAR_NAME, kitTexture);
                        setRangedIntCustomVarValue(armor, COLOR_CUSTVAR_NAME_01, kitColor_1);
                        setRangedIntCustomVarValue(armor, COLOR_CUSTVAR_NAME_02, kitColor_2);
                        prose_package pp = new prose_package();
                        prose.setStringId(pp, new string_id("tcg", "armor_kit_texture_applied"));
                        prose.setTT(pp, getEncodedName(armor));
                        sendSystemMessageProse(player, pp);
                    }
                }
            }
        }
        return;
    }
    public void flushArmorKit(obj_id armorKit, obj_id player) throws InterruptedException
    {
        obj_id playerInventory = utils.getInventoryContainer(player);
        obj_id[] armorPieces = getContents(armorKit);
        if (armorPieces != null && armorPieces.length > 0)
        {
            for (int i = 0; i < armorPieces.length; i++)
            {
                obj_id armor = armorPieces[i];
                putIn(armor, playerInventory, player);
            }
        }
    }
}
