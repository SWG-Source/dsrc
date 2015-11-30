package script.systems.jedi;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.jedi;
import script.library.pclib;
import script.library.static_item;
import script.library.weapons;

public class saber_base extends script.base_script
{
    public saber_base()
    {
    }
    public static final string_id SID_NO_PARTS_TO_RECOVER = new string_id("jedi_spam", "no_parts_to_recover");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (static_item.isStaticItem(self))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (!utils.isNestedWithinANpcCreature(self))
            {
                weapons.validateWeaponRange(self);
            }
        }
        weapons.setWeaponData(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canManipulate(player, self, false, true, 15, true))
        {
            String saberCert = getStringObjVar(self, "weapon.strCertUsed");
            if (hasObjVar(self, "crafting_components"))
            {
                if (saberCert != null)
                {
                    if (saberCert.equals("cert_onehandlightsaber") || saberCert.equals("cert_twohandlightsaber") || saberCert.equals("cert_polearmlightsaber"))
                    {
                        mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("jedi_spam", "dismantle_saber"));
                    }
                }
            }
            else 
            {
                menu_info_data mid = mi.getMenuItemByType(menu_info_types.SERVER_PET_OPEN);
                if (mid != null)
                {
                    mid.setServerNotify(true);
                }
                else 
                {
                    mi.addRootMenu(menu_info_types.SERVER_PET_OPEN, new string_id("jedi_spam", "open_saber"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (canManipulate(player, self, false, true, 15, true))
        {
            if (item == menu_info_types.SERVER_MENU1)
            {
                dismantleSaber(player);
            }
            if (item == menu_info_types.SERVER_PET_OPEN)
            {
                openSaber(player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void openSaber(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id inv = getObjectInSlot(self, "saber_inv");
        utils.requestContainerOpen(player, inv);
    }
    public int handleResetSaberStats(obj_id self, dictionary params) throws InterruptedException
    {
        jedi.resetSaberStats(self);
        return SCRIPT_CONTINUE;
    }
    public int decaySaberCrystal(obj_id self, dictionary params) throws InterruptedException
    {
        int amount = params.getInt("amount");
        obj_id player = params.getObjId("owner");
        obj_id inv = getObjectInSlot(self, "saber_inv");
        obj_id[] contents = getContents(inv);
        if (contents == null || contents.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id crystal = contents[rand(0, contents.length - 1)];
        int hp = getHitpoints(crystal);
        if (hp > 0)
        {
            pclib.damageAndDecayItem(crystal, amount);
        }
        return SCRIPT_CONTINUE;
    }
    public void dismantleSaber(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        int minDamage = (int)getFloatObjVar(self, "crafting_components.minDamage");
        int maxDamage = (int)getFloatObjVar(self, "crafting_components.maxDamage");
        CustomerServiceLog("jedi_saber", "%TU saber dismantling, had a Min Damage of " + minDamage + " and a Max Damage of " + maxDamage, player);
        int generation = 0;
        String template = getTemplateName(self);
        if (template.endsWith("gen2.iff"))
        {
            generation = 2;
        }
        else if (template.endsWith("gen3.iff"))
        {
            generation = 3;
        }
        else if (template.endsWith("gen4.iff"))
        {
            generation = 4;
        }
        else if (template.endsWith("gen5.iff"))
        {
            generation = 5;
        }
        CustomerServiceLog("jedi_saber", "%TU saber dismantling, was a generation " + generation + " saber.", player);
        int[] generationList = dataTableGetIntColumn("datatables/jedi/saber_conversion.iff", "generation");
        Vector saberList = new Vector();
        saberList.setSize(0);
        for (int i = 0; i < generationList.length; i++)
        {
            if (generationList[i] == generation)
            {
                saberList = utils.addElement(saberList, dataTableGetRow("datatables/jedi/saber_conversion.iff", i));
            }
        }
        int idx = -1;
        for (int i = 0; i < saberList.size(); i++)
        {
            if (minDamage >= ((dictionary)saberList.get(i)).getInt("minDamage") && maxDamage >= ((dictionary)saberList.get(i)).getInt("maxDamage"))
            {
                idx++;
            }
            else 
            {
                break;
            }
        }
        if (idx >= saberList.size())
        {
            sendSystemMessage(player, new string_id("jedi_spam", "saber_cant_convert"));
            return;
        }
        else 
        {
            int pearlCount = generation - 1;
            int pearlLevel = 25;
            if (idx > -1)
            {
                pearlCount = ((dictionary)saberList.get(idx)).getInt("pearlCount");
                pearlLevel = ((dictionary)saberList.get(idx)).getInt("pearlLevel");
            }
            if (pearlCount < 1)
            {
                sendSystemMessage(player, SID_NO_PARTS_TO_RECOVER);
                CustomerServiceLog("jedi_saber", "%TU saber dismantling, No pearls to recover from a training saber.", player);
                return;
            }
            CustomerServiceLog("jedi_saber", "%TU saber dismantling, Initial pearlCount is " + pearlCount + " and Initial PeralLevel is " + pearlLevel, player);
            if (hasObjVar(self, "slicing.hacked"))
            {
                sendSystemMessage(player, new string_id("jedi_spam", "saber_convert_sliced"));
                pearlLevel = pearlLevel - (pearlLevel / 5);
            }
            obj_id inventory = utils.getInventoryContainer(player);
            int invSpace = getVolumeFree(inventory);
            if (invSpace < pearlCount)
            {
                CustomerServiceLog("jedi_saber", "%TU saber dismantling, Didn't add any pearls because Inventory of player was full.", player);
                sendSystemMessage(player, new string_id("jedi_spam", "saber_convert_full"));
                return;
            }
            else 
            {
                for (int i = 0; i < pearlCount; i++)
                {
                    obj_id pearl = createObject("object/tangible/component/weapon/lightsaber/lightsaber_module_krayt_dragon_pearl.iff", inventory, "");
                    if (pearl == null)
                    {
                        CustomerServiceLog("jedi_saber", "ERROR:: %TU saber dismantling, Level " + pearlLevel + "Pearl unable to be created.", player);
                    }
                    else 
                    {
                        CustomerServiceLog("jedi_saber", "%TU saber dismantling, Pearl Level set to " + pearlLevel + "for Pearl " + pearl + ".", player);
                        setObjVar(pearl, jedi.VAR_CRYSTAL_STATS + "." + jedi.VAR_LEVEL, pearlLevel);
                    }
                }
                CustomerServiceLog("jedi_saber", "%TU saber dismantling, completed dismantling saber and destroyed the object which was %TT.", player, self);
                destroyObject(self);
                sendSystemMessage(player, new string_id("jedi_spam", "saber_convert_complete"));
                return;
            }
        }
    }
}
