package script.systems.beast;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.beast_lib;
import script.library.hue;
import script.library.incubator;
import script.library.sui;
import script.library.utils;

public class beast_egg extends script.base_script
{
    public beast_egg()
    {
    }
    public static final string_id SID_WHILE_DEAD = new string_id("beast", "no_hatch_while_dead");
    public static final string_id SID_EGG_HATCH = new string_id("beast", "hatch_egg");
    public static final string_id SID_GOD_EGG_HATCH = new string_id("beast", "god_hatch_egg");
    public static final string_id SID_MAKE_MOUNT = new string_id("beast", "egg_make_mount");
    public static final string_id SID_HATCH_MOUNT = new string_id("beast", "egg_hatch_mount");
    public static final string_id SID_MOUNT_CONVERT_PROMPT = new string_id("beast", "egg_make_mount_prompt");
    public static final string_id SID_MOUNT_CONVERT_TITLE = new string_id("beast", "egg_make_mount_title");
    public static final string_id SID_MAKE_HOLOPET = new string_id("beast", "egg_make_holopet");
    public static final string_id SID_HOLOBEAST_CONVERT_PROMPT = new string_id("beast", "egg_make_holobeast_prompt");
    public static final string_id SID_HOLOBEAST_CONVERT_TITLE = new string_id("beast", "egg_make_holobeast_title");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleInitializeValues", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        incubator.setEggHue(self);
        messageTo(self, "rename_egg", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int rename_egg(obj_id self, dictionary params) throws InterruptedException
    {
        String beastType = beast_lib.getBCDBeastType(self);
        beastType = "egg_" + beast_lib.stripBmFromType(beastType);
        setName(self, new string_id("beast", beastType));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id egg = self;
        if (isDead(player) || isIncapacitated(player))
        {
            sendSystemMessage(player, SID_WHILE_DEAD);
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithinAPlayer(egg, false))
        {
            if (beast_lib.isBeastMaster(player) && hasObjVar(egg, beast_lib.OBJVAR_BEAST_TYPE) && !incubator.isEggMountFlagged(egg))
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_EGG_HATCH);
            }
            if (incubator.isEggMountType(egg) && hasSkill(player, "expertise_bm_train_mount_1") && !incubator.isEggMountFlagged(egg))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_MAKE_MOUNT);
            }
            else if (incubator.isEggMountType(egg) && incubator.isEggMountFlagged(egg))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_HATCH_MOUNT);
            }
            if (beast_lib.isBeastMaster(player) && hasObjVar(egg, beast_lib.OBJVAR_BEAST_TYPE))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_MAKE_HOLOPET);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (isDead(player) || isIncapacitated(player))
        {
            sendSystemMessage(player, SID_WHILE_DEAD);
            return SCRIPT_CONTINUE;
        }
        obj_id egg = self;
        if (utils.isNestedWithinAPlayer(egg))
        {
            if (item == menu_info_types.ITEM_USE && !incubator.isEggMountFlagged(egg))
            {
                if (!beast_lib.isBeastMaster(player))
                {
                    return SCRIPT_OVERRIDE;
                }
                obj_id bcd = beast_lib.createBCDFromEgg(player, egg);
                if (beast_lib.isValidBCD(bcd))
                {
                    playClientEffectObj(player, "appearance/pt_egg_crack.prt", player, "");
                    destroyObject(self);
                    return SCRIPT_CONTINUE;
                }
            }
            if (item == menu_info_types.SERVER_MENU1 && hasSkill(player, "expertise_bm_train_mount_1") && !incubator.isEggMountFlagged(egg))
            {
                if (!incubator.isEggMountType(egg))
                {
                    return SCRIPT_CONTINUE;
                }
                int pid = sui.msgbox(self, player, "@" + SID_MOUNT_CONVERT_PROMPT, sui.YES_NO, "@" + SID_MOUNT_CONVERT_TITLE, "handleStampEggAsMount");
                return SCRIPT_CONTINUE;
            }
            else if (item == menu_info_types.SERVER_MENU1 && incubator.isEggMountType(egg) && incubator.isEggMountFlagged(egg))
            {
                if (!incubator.isEggMountType(egg))
                {
                    return SCRIPT_CONTINUE;
                }
                obj_id pcd = incubator.convertEggToMount(egg, player);
                if (isValidId(pcd) && exists(pcd))
                {
                    CustomerServiceLog("BeastEggToMountConversion: ", "Player (" + player + ") has converted Egg (" + egg + ")" + " to PCD (" + pcd + ") we are now going to destroy the egg");
                    destroyObject(egg);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    CustomerServiceLog("BeastEggToMountConversion: ", "Player (" + player + ") tried to convert incubated egg (" + egg + "). Conversion was not a success. New PCD was NOT created and egg was NOT destroyed.");
                    return SCRIPT_CONTINUE;
                }
            }
            if (item == menu_info_types.SERVER_MENU2 && hasObjVar(egg, beast_lib.OBJVAR_BEAST_TYPE))
            {
                if (!beast_lib.isBeastMaster(player))
                {
                    return SCRIPT_CONTINUE;
                }
                int pid = sui.msgbox(self, player, "@" + SID_HOLOBEAST_CONVERT_PROMPT, sui.YES_NO, "@" + SID_HOLOBEAST_CONVERT_TITLE, "handleConvertEggToHolobeast");
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (exists(self))
        {
            for (int i = 0; i < beast_lib.ARRAY_BEAST_INCUBATION_STATS.length; ++i)
            {
                if (hasObjVar(self, beast_lib.ARRAY_BEAST_INCUBATION_STATS[i]) && !incubator.isEggMountFlagged(self))
                {
                    String name = beast_lib.DISPLAY_NAMES[i];
                    int stat = getIntObjVar(self, beast_lib.ARRAY_BEAST_INCUBATION_STATS[i]);
                    if (name.indexOf("_skill") < 0)
                    {
                        if (!name.equals("block_value_bonus"))
                        {
                            names[idx] = beast_lib.DISPLAY_NAMES[i];
                            attribs[idx] = "" + utils.roundFloatByDecimal((float)stat * beast_lib.DISPLAY_OBJVAR_CONVERSION_RATES[i]) + "%";
                            idx++;
                        }
                        else 
                        {
                            names[idx] = beast_lib.DISPLAY_NAMES[i];
                            attribs[idx] = "" + stat;
                            idx++;
                        }
                        continue;
                    }
                    else 
                    {
                        names[idx] = beast_lib.DISPLAY_NAMES[i];
                        attribs[idx] = "" + stat;
                        idx++;
                        continue;
                    }
                }
            }
            if (hasObjVar(self, beast_lib.OBJVAR_BEAST_PARENT))
            {
                names[idx] = "bm_template";
                int hashType = getIntObjVar(self, beast_lib.OBJVAR_BEAST_TYPE);
                String template = getStringObjVar(self, beast_lib.OBJVAR_BEAST_PARENT);
                string_id templateId = new string_id("mob/creature_names", template);
                if (localize(templateId) == null)
                {
                    template = incubator.convertHashTypeToString(hashType);
                    template = beast_lib.stripBmFromType(template);
                    templateId = new string_id("monster_name", template);
                    if (localize(templateId) == null)
                    {
                        templateId = new string_id("mob/creature_names", template);
                    }
                }
                attribs[idx] = "" + localize(templateId);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, beast_lib.OBJVAR_BEAST_TYPE))
            {
                names[idx] = "beast_type";
                int hashType = getIntObjVar(self, beast_lib.OBJVAR_BEAST_TYPE);
                String template = incubator.convertHashTypeToString(hashType);
                template = beast_lib.stripBmFromType(template);
                string_id templateId = new string_id("monster_name", template);
                if (localize(templateId) == null)
                {
                    templateId = new string_id("mob/creature_names", template);
                }
                attribs[idx] = "" + localize(templateId);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (incubator.isEggMountFlagged(self))
            {
                names[idx] = "is_mount";
                attribs[idx] = "true";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, beast_lib.OBJVAR_BEAST_ENGINEER))
            {
                String creatorName = getStringObjVar(self, beast_lib.OBJVAR_BEAST_ENGINEER);
                names[idx] = "bm_creator";
                attribs[idx] = creatorName;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, beast_lib.OBJVAR_BEAST_HUE))
            {
                color eggColor = getPalcolorCustomVarSelectedColor(self, hue.INDEX_1);
                String attrib = "";
                attrib += "" + eggColor.getR() + ", " + eggColor.getG() + ", " + eggColor.getB();
                names[idx] = "bm_rgb_values";
                attribs[idx] = attrib;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleInitializeValues(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("beast", "beast_egg: handleInitializeValues()");
        incubator.initializeEgg(self);
        return SCRIPT_CONTINUE;
    }
    public int handleStampEggAsMount(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        obj_id egg = self;
        incubator.stampEggAsMount(egg, player);
        return SCRIPT_CONTINUE;
    }
    public int handleConvertEggToHolobeast(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (isIdValid(player))
        {
            obj_id holopetCube = beast_lib.createHolopetCubeFromEgg(player, self);
            if (isIdValid(holopetCube))
            {
                destroyObject(self);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
