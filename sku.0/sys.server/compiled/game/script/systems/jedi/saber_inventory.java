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
import script.library.ai_lib;

public class saber_inventory extends script.base_script
{
    public saber_inventory()
    {
    }
    public static final string_id SID_SABER_NOT_CRYSTAL = new string_id("jedi_spam", "saber_not_crystal");
    public static final string_id SID_SABER_NOT_WHILE_EQUIP = new string_id("jedi_spam", "saber_not_while_equpped");
    public static final string_id SID_SABER_CRYSTAL_NOT_TUNED = new string_id("jedi_spam", "saber_crystal_not_tuned");
    public static final string_id SID_SABER_CRYSTAL_NOT_OWNER = new string_id("jedi_spam", "saber_crystal_not_owner");
    public static final string_id SID_SABER_ALREADY_HAS_COLOR = new string_id("jedi_spam", "saber_already_has_color");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id saber = getContainedBy(self);
        jedi.resetSaberStats(saber);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id saber = getContainedBy(self);
        obj_id container = getContainedBy(saber);
        if (!isGameObjectTypeOf(item, GOT_component_saber_crystal))
        {
            sendSystemMessage(transferer, SID_SABER_NOT_CRYSTAL);
            return SCRIPT_OVERRIDE;
        }
        if (isPlayer(container))
        {
            sendSystemMessage(transferer, SID_SABER_NOT_WHILE_EQUIP);
            return SCRIPT_OVERRIDE;
        }
        if (!jedi.isCrystalTuned(item))
        {
            sendSystemMessage(transferer, SID_SABER_CRYSTAL_NOT_TUNED);
            return SCRIPT_OVERRIDE;
        }
        if ((isIdValid(transferer) && !jedi.isCrystalOwner(transferer, item)) || !jedi.doesCrystalMatchSaber(saber, item))
        {
            sendSystemMessage(transferer, SID_SABER_CRYSTAL_NOT_OWNER);
            return SCRIPT_OVERRIDE;
        }
        if (jedi.isColorCrystal(item) && jedi.hasColorCrystal(saber))
        {
            sendSystemMessage(transferer, SID_SABER_ALREADY_HAS_COLOR);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id saber = getContainedBy(self);
        if (!isDisabled(item))
        {
            jedi.addCrystalStats(saber, item);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id saber = getContainedBy(self);
        obj_id container = getContainedBy(saber);
        if (isPlayer(container))
        {
            sendSystemMessage(transferer, SID_SABER_NOT_WHILE_EQUIP);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id saber = getContainedBy(self);
        if (!isDisabled(item))
        {
            jedi.removeCrystalStats(saber, item);
        }
        return SCRIPT_CONTINUE;
    }
}
