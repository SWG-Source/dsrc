package script.item.tool;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.pet_lib;
import script.library.utils;
import script.library.create;
import script.library.space_combat;

public class navicomputer extends script.base_script
{
    public navicomputer()
    {
    }
    public static final String CREATURE_NAME_FILE = "item_n";
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        obj_id itemControlDevice = getObjIdObjVar(self, "item.controlDevice");
        if (hasObjVar(itemControlDevice, "dataModuleRating"))
        {
            int datastorage = getIntObjVar(self, "dataModuleRating");
        }
        if (hasObjVar(itemControlDevice, "usedMemory"))
        {
            names[idx] = "droid_program_expended_memory";
            int usedMemory = getIntObjVar(itemControlDevice, "usedMemory");
            attribs[idx] = " " + usedMemory;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != getOwner(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id itemControlDevice = getObjIdObjVar(self, "item.controlDevice");
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        int opt_menu = 0;
        if (!space_combat.isAnInitializedNavicomp(self))
        {
            opt_menu = mi.addRootMenu(menu_info_types.INIT_NAVICOMP_DPAD, new string_id(pet_lib.MENU_FILE, "init_navicomp_dpad"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (ai_lib.aiIsDead(player))
        {
            debugServerConsoleMsg(null, "NAVICOMPUTER.OnObjectMenuSelect ***************** FAIL AT POINT A ");
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            debugServerConsoleMsg(null, "NAVICOMPUTER.OnObjectMenuSelect ***************** FAIL AT POINT B ");
            return SCRIPT_CONTINUE;
        }
        if (player != getOwner(self))
        {
            debugServerConsoleMsg(null, "NAVICOMPUTER.OnObjectMenuSelect ***************** FAIL AT POINT C ");
            return SCRIPT_CONTINUE;
        }
        obj_id itemControlDevice = getObjIdObjVar(self, "item.controlDevice");
        if (!hasCertificationsForItem(player, self))
        {
            string_id strSpam = new string_id("space/space_interaction", "not_certified_device");
            sendSystemMessage(player, strSpam);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.INIT_NAVICOMP_DPAD)
        {
            if (!verifyNoOtherInitializedNavicomps(player))
            {
                string_id strSpam = new string_id("space/space_interaction", "already_active_navicomp");
                string_id strSpam2 = new string_id("space/space_interaction", "already_active_navicomp2");
                string_id strSpam3 = new string_id("space/space_interaction", "already_active_navicomp3");
                sendSystemMessage(player, strSpam);
            }
            else 
            {
                initializeNavicomputer(player, self);
            }
        }
        else 
        {
            debugServerConsoleMsg(null, "NAVICOMPUTER.OnObjectMenuSelect ***************** FAIL AT POINT D ");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        obj_id owner = getOwner(self);
        if (!isIdValid(owner))
        {
            return SCRIPT_OVERRIDE;
        }
        if (player != owner)
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id itemControlDevice = getObjIdObjVar(self, "item.controlDevice");
        obj_id navicompDpad = getObjectInSlot(itemControlDevice, "datapad");
        if (!isIdValid(navicompDpad))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id objSource, obj_id objTransferer, obj_id objItem) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void initializeNavicomputer(obj_id player, obj_id navicomp) throws InterruptedException
    {
        createNavicomputerControlDevice(player, navicomp);
        return;
    }
    public void createNavicomputerControlDevice(obj_id player, obj_id navicomp) throws InterruptedException
    {
        if (!isIdValid(navicomp))
        {
            return;
        }
        if (!isIdValid(player))
        {
            return;
        }
        obj_id datapad = utils.getPlayerDatapad(player);
        if (!isIdValid(datapad))
        {
            return;
        }
        if (!canManipulate(player, navicomp, true, true, 15, true))
        {
            return;
        }
        String itemName = getStringObjVar(navicomp, "item.objectName");
        if (itemName == null || itemName.equals(""))
        {
            return;
        }
        String controlTemplate = "object/intangible/ship/" + itemName + ".iff";
        obj_id itemControlDevice = createObject(controlTemplate, datapad, "");
        if (!isIdValid(itemControlDevice))
        {
            string_id strSpam = new string_id("space/space_interaction", "failed_to_create_controller");
            sendSystemMessage(player, strSpam);
            return;
        }
        else 
        {
            setOwner(itemControlDevice, player);
            string_id nameId = new string_id(create.CREATURE_NAME_FILE, itemName);
            setName(itemControlDevice, nameId);
            attachScript(itemControlDevice, "item.tool.navicomputer_control_device");
            setObjVar(itemControlDevice, "item.objectName", itemName);
            if (hasObjVar(navicomp, "dataModuleRating"))
            {
                int itemDataStorageModuleValue = getIntObjVar(navicomp, "dataModuleRating");
                setObjVar(itemControlDevice, "dataModuleRating", itemDataStorageModuleValue);
                initNavicompDatapad(itemControlDevice, navicomp, player);
            }
            destroyObject(navicomp);
        }
        return;
    }
    public void initNavicompDatapad(obj_id itemControlDevice, obj_id navicomp, obj_id player) throws InterruptedException
    {
        obj_id dpad = getObjectInSlot(itemControlDevice, "datapad");
        if (isIdValid(dpad))
        {
            if (!setOwner(dpad, player))
            {
                debugServerConsoleMsg(null, "***************** FAILED to setowner on the navicom controller dpad");
            }
            string_id nameId = new string_id(create.CREATURE_NAME_FILE, "flight_computer_datapad");
            if (!setName(dpad, nameId))
            {
                debugServerConsoleMsg(null, "***************** FAILED to setowner on the navicom controller dpad");
            }
        }
    }
    public boolean verifyNoOtherInitializedNavicomps(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        obj_id datapad = utils.getPlayerDatapad(player);
        if (!isIdValid(datapad))
        {
            return false;
        }
        obj_id[] dpadContents = getContents(datapad);
        for (int i = 0; i < dpadContents.length; i++)
        {
            if (hasObjVar(dpadContents[i], "item.current"))
            {
                return false;
            }
        }
        return true;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!isIdValid(destContainer))
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id newMaster = getContainedBy(destContainer);
        if (isIdValid(self))
        {
            obj_id currentContainer = getContainedBy(self);
            if (hasObjVar(self, "item.controlDevice"))
            {
                string_id strSpam = new string_id("space/space_interaction", "cant_transfer_active_computer");
                sendSystemMessage(transferer, strSpam);
                return SCRIPT_OVERRIDE;
            }
            if (!canBeTransferred(self, newMaster, transferer))
            {
                debugServerConsoleMsg(null, "NAVICOMPUTER.OnAboutToBeTransferred ********** canBeTransferred returned FALSE. ");
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canBeTransferred(obj_id navicomp, obj_id newMaster, obj_id transferer) throws InterruptedException
    {
        if (space_combat.isAnInitializedNavicomp(navicomp))
        {
            if (!hasCertificationsForItem(newMaster, navicomp))
            {
                string_id strSpam = new string_id("space/space_interaction", "no_cert_on_receiver");
                sendSystemMessage(transferer, strSpam);
                return false;
            }
        }
        return true;
    }
}
