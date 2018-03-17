package script.systems.beast;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.beast_lib;
import script.library.buff;
import script.library.utils;

public class beast_steroid_injector extends script.base_script
{
    public beast_steroid_injector()
    {
    }
    public static final string_id SID_BEAST_HAS_BUFF = new string_id("beast", "beast_has_buff");
    public static final string_id SID_BEAST_WRONG_FAMILY = new string_id("beast", "beast_buff_wrong_type");
    public static final string_id SID_BEAST_NO_BEAST = new string_id("beast", "beast_buff_no_beast");
    public static final string_id SID_USE_ITEM = new string_id("beast", "beast_buff_use");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (exists(self) && hasObjVar(self, "beastSteroidBonus"))
        {
            names[idx] = "beastSteroidBonus";
            int attrib = getIntObjVar(self, "beastSteroidBonus");
            attribs[idx] = Integer.toString(attrib);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (exists(self) && hasObjVar(self, "beast_type"))
        {
            names[idx] = "beastSteroidTypes";
            String objvarVal = getStringObjVar(self, "beast_type");
            String[] spliVal = split(objvarVal, ',');
            String attrib = "";
            if (spliVal != null && spliVal.length > 0)
            {
                attrib = "beast_steroid_";
                for (int i = 0; i < spliVal.length; i = i + 2)
                {
                    attrib = attrib + spliVal[i] + "_" + spliVal[i + 1] + "_d";
                }
            }
            else 
            {
                attrib = "beast_steroid_" + objvarVal + "_d";
            }
            string_id strIdAttrib = new string_id("item_n", attrib);
            attribs[idx] = localize(strIdAttrib);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_USE_ITEM);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (buff.hasBuff(player, "feign_death"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(player);
        if (!isIdValid(beast) || !exists(beast))
        {
            sendSystemMessage(player, SID_BEAST_NO_BEAST);
            return SCRIPT_CONTINUE;
        }
        if (isDead(beast) || isIncapacitated(beast))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            int mark = getIntObjVar(self, beast_lib.OBJVAR_INJECTOR_MARK);
            if (mark <= 0)
            {
                mark = 1;
            }
            obj_id bcd = beast_lib.getBeastBCD(beast);
            String beastName = beast_lib.getBeastType(bcd);
            String beastType = "";
            if (exists(self) && hasObjVar(self, "beast_type"))
            {
                beastType = getStringObjVar(self, "beast_type");
            }
            String[] typeArray = split(beastType, ',');
            dictionary beastDict = utils.dataTableGetRow(beast_lib.BEASTS_TABLE, beastName);
            String specialAttackFamily = beastDict.getString("special_attack_family");
            int injectorCode = beast_lib.useBeastInjector(player, self, beast, specialAttackFamily, typeArray, mark);
            if (injectorCode == beast_lib.INJECTOR_RETURN_WRONG_LEVEL)
            {
                return SCRIPT_CONTINUE;
            }
            if (injectorCode == beast_lib.INJECTOR_RETURN_HAS_BUFF)
            {
                sendSystemMessage(player, SID_BEAST_HAS_BUFF);
                return SCRIPT_CONTINUE;
            }
            if (injectorCode == beast_lib.INJECTOR_RETURN_WRONG_FAMILY)
            {
                sendSystemMessage(player, SID_BEAST_WRONG_FAMILY);
                return SCRIPT_CONTINUE;
            }
            if (injectorCode == beast_lib.INJECTOR_RETURN_SUCESS)
            {
                addXpBuff(self, beast);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean addXpBuff(obj_id injector, obj_id beast) throws InterruptedException
    {
        int buffAmount = 0;
        if (exists(injector) && hasObjVar(injector, "beastSteroidBonus"))
        {
            buffAmount = getIntObjVar(injector, "beastSteroidBonus");
        }
        boolean worked = buff.applyBuff(beast, "bm_beast_steroid");
        utils.setScriptVar(beast, "beastBuff.beastXpBonusPercent", buffAmount);
        decrementCount(injector);
        int chargesLeft = getCount(injector);
        if (chargesLeft <= 0)
        {
            destroyObject(injector);
        }
        else 
        {
            setObjVar(injector, "charges", chargesLeft);
        }
        return true;
    }
}
