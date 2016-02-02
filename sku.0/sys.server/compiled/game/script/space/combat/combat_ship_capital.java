package script.space.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_combat;
import script.library.space_crafting;
import script.library.pclib;
import script.library.utils;
import script.library.space_utils;
import script.library.space_content;
import script.library.ship_ai;

public class combat_ship_capital extends script.space.combat.combat_space_base
{
    public combat_ship_capital()
    {
    }
    public static final string_id SID_TARGET_DISABLED = new string_id("space/quest", "target_disabled2");
    public static final int MAXIMUM_DAMAGE_PHASES = 9;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "space.combat.combat_ship");
        int[] intSlots = getShipChassisSlots(self);
        for (int intI = 0; intI < intSlots[intI]; intI++)
        {
            setShipComponentEfficiencyGeneral(self, intSlots[intI], 1.0f);
            setShipComponentEfficiencyEnergy(self, intSlots[intI], 1.0f);
        }
        String strCapitalShipType = getStringObjVar(self, "strCapitalShipType");
        if ((strCapitalShipType == null) || (strCapitalShipType.equals("")) || strCapitalShipType == "null") //yes, it seems there's a literal string "null" somewhere
        {
            debugSpeakMsg(self, "BAD CAPITAL SHIP DEFINED!");
        }
        String strFileName = "datatables/space_combat/capital_ships/" + strCapitalShipType + ".iff";
        if (dataTableOpen(strFileName))
        {
            setupCapitalShipComponents(self);
        }
        else 
        {
        }
        return SCRIPT_CONTINUE;
    }
    public int OnShipWasHit(obj_id self, obj_id objAttacker, int intWeaponIndex, boolean isMissile, int missileType, int intTargetedComponent, boolean fromPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException
    {
        if (hasObjVar(self, "intInvincible"))
        {
            return SCRIPT_CONTINUE;
        }
        int intWeaponSlot = intWeaponIndex + ship_chassis_slot_type.SCST_weapon_0;
        if (hasObjVar(self, "intInvincible"))
        {
            return SCRIPT_CONTINUE;
        }
        if (space_combat.hasDeathFlags(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id objPilot = getPilotId(objAttacker);
        transform attackerTransform_w = getTransform_o2w(objAttacker);
        transform defenderTransform_w = getTransform_o2w(self);
        vector hitDirection_o = defenderTransform_w.rotateTranslate_p2l(attackerTransform_w.getPosition_p());
        int intSide = 0;
        if (hitDirection_o.z < 0.f)
        {
            intSide = 1;
        }
        if (!pvpCanAttack(objAttacker, self))
        {
            return SCRIPT_CONTINUE;
        }
        pvpAttackPerformed(objAttacker, self);
        float fltDamage = space_combat.getShipWeaponDamage(objAttacker, self, intWeaponSlot, isMissile);
        if (hasObjVar(self, "gunDamage") && !isMissile)
        {
            float damageMod = getFloatObjVar(self, "gunDamage");
            fltDamage = fltDamage * damageMod;
        }
        if (hasObjVar(self, "missileDamage") && isMissile)
        {
            float damageMod = getFloatObjVar(self, "missileDamage");
            fltDamage = fltDamage * damageMod;
        }
        if (hasObjVar(self, "shieldActive"))
        {
            fltDamage = space_combat.doShieldDamage(objAttacker, self, intWeaponSlot, fltDamage, intSide);
        }
        if (fltDamage > 0 && hasObjVar(self, "armorActive"))
        {
            fltDamage = space_combat.doArmorDamage(objAttacker, self, intWeaponSlot, fltDamage, intSide);
        }
        if (!isShipSlotTargetable(self, intTargetedComponent) && space_utils.isPlayerControlledShip(objAttacker))
        {
            ship_ai.unitAddDamageTaken(self, objAttacker, .05f);
            return SCRIPT_CONTINUE;
        }
        ship_ai.unitAddDamageTaken(self, objAttacker, fltDamage);
        if (fltDamage > 0.f)
        {
            notifyShipDamage(self, objAttacker, fltDamage);
        }
        fltDamage = space_combat.doComponentDamage(objAttacker, self, intWeaponSlot, intTargetedComponent, fltDamage, intSide);
        checkAndUpdateCapitalShipStatus(self, intTargetedComponent);
        if (fltDamage > 0)
        {
            boolean boolDisabled = false;
            if (boolDisabled)
            {
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_ON);
        setupCapitalShipComponents(self);
        return SCRIPT_CONTINUE;
    }
    public int[] makeComponentsTargetable(obj_id objShip, String[] strComponents) throws InterruptedException
    {
        int[] intDamagePhaseComponents = new int[strComponents.length];
        for (int intI = 0; intI < strComponents.length; intI++)
        {
            int intChassisSlot = space_crafting.getComponentSlotInt(strComponents[intI]);
            if (isShipSlotInstalled(objShip, intChassisSlot))
            {
                setShipSlotTargetable(objShip, intChassisSlot, true);
                intDamagePhaseComponents[intI] = intChassisSlot;
            }
            else 
            {
            }
        }
        return intDamagePhaseComponents;
    }
    public int[] makeComponentsUntargetable(obj_id objShip, String[] strComponents) throws InterruptedException
    {
        int[] intDamagePhaseComponents = new int[strComponents.length];
        for (int intI = 0; intI < strComponents.length; intI++)
        {
            int intChassisSlot = space_crafting.getComponentSlotInt(strComponents[intI]);
            if (isShipSlotInstalled(objShip, intChassisSlot))
            {
                setShipSlotTargetable(objShip, intChassisSlot, false);
                intDamagePhaseComponents[intI] = intChassisSlot;
            }
            else 
            {
            }
        }
        return intDamagePhaseComponents;
    }
    public void setupCapitalShipComponents(obj_id objShip) throws InterruptedException
    {
        int[] intSlots = space_crafting.getShipInstalledSlots(objShip);
        for (int intI = 0; intI < intSlots.length; intI++)
        {
            setShipSlotTargetable(objShip, intSlots[intI], false);
        }
        String strCapitalShipType = getStringObjVar(objShip, "strCapitalShipType");
	if ((strCapitalShipType == null) || (strCapitalShipType.equals("")) || strCapitalShipType == "null")
        {
            return;
        }
        int intDamagePhase = 1;
        String strFileName = "datatables/space_combat/capital_ships/" + strCapitalShipType + ".iff";
        if (dataTableOpen(strFileName))
        {
            String[] strComponents = dataTableGetStringColumnNoDefaults(strFileName, "strComponents1");
            int[] intDamagePhaseComponents = makeComponentsTargetable(objShip, strComponents);
            utils.setScriptVar(objShip, "intDamagePhaseComponents", intDamagePhaseComponents);
            strComponents = dataTableGetStringColumnNoDefaults(strFileName, "strAlwaysTargetable");
            makeComponentsTargetable(objShip, strComponents);
        }
        else 
        {
        }
        utils.setScriptVar(objShip, "intDamagePhase", 1);
    }
    public void checkAndUpdateCapitalShipStatus(obj_id objShip, int intSlot) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        float fltPercentage = space_crafting.getDamagePercentage(objShip, intSlot);
        dctParams.put("fltPercentage", fltPercentage);
        dctParams.put("intSlot", intSlot);
        if (fltPercentage == 0.0f)
        {
            space_utils.notifyObject(objShip, "componentDisabled", dctParams);
        }
        else if (fltPercentage < 0.25f)
        {
            space_utils.notifyObject(objShip, "componentDamageUpdate", dctParams);
        }
        else if (fltPercentage < 0.50f)
        {
            space_utils.notifyObject(objShip, "componentDamageUpdate", dctParams);
        }
        else if (fltPercentage < 0.75f)
        {
            space_utils.notifyObject(objShip, "componentDamageUpdate", dctParams);
        }
    }
    public int componentDamageUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int componentDisabled(obj_id self, dictionary params) throws InterruptedException
    {
        int intSlot = params.getInt("intSlot");
        setShipSlotTargetable(self, intSlot, false);
        Vector intDamagePhaseComponents = utils.getResizeableIntArrayScriptVar(self, "intDamagePhaseComponents");
        int intIndex = -1;
        for (int intI = 0; intI < intDamagePhaseComponents.size(); intI++)
        {
            if (((Integer)intDamagePhaseComponents.get(intI)).intValue() == intSlot)
            {
                intIndex = intI;
                intI = intDamagePhaseComponents.size() + 1;
            }
        }
        if (intIndex > -1)
        {
            utils.removeElementAt(intDamagePhaseComponents, intIndex);
        }
        if (intDamagePhaseComponents.size() == 0)
        {
            processDamagePhase(self);
        }
        else 
        {
            utils.setScriptVar(self, "intDamagePhaseComponents", intDamagePhaseComponents);
        }
        return SCRIPT_CONTINUE;
    }
    public void processDamagePhase(obj_id objShip) throws InterruptedException
    {
        String strCapitalShipType = getStringObjVar(objShip, "strCapitalShipType");
	if ((strCapitalShipType == null) || (strCapitalShipType.equals("")) || strCapitalShipType == "null")
        {
            return;
        }
        String strFileName = "datatables/space_combat/capital_ships/" + strCapitalShipType + ".iff";
        int intDamagePhase = utils.getIntScriptVar(objShip, "intDamagePhase");
        String[] strOldComponents = dataTableGetStringColumnNoDefaults(strFileName, "strComponents" + intDamagePhase);
        intDamagePhase = intDamagePhase + 1;
        String[] strNewComponents = dataTableGetStringColumnNoDefaults(strFileName, "strComponents" + intDamagePhase);
        if ((strNewComponents != null) && (strNewComponents.length > 0))
        {
            int[] intDamagePhaseComponents = makeComponentsTargetable(objShip, strNewComponents);
            makeComponentsUntargetable(objShip, strOldComponents);
            utils.setScriptVar(objShip, "intDamagePhaseComponents", intDamagePhaseComponents);
            utils.setScriptVar(objShip, "intDamagePhase", intDamagePhase);
            return;
        }
        else 
        {
            space_combat.grantRewardsAndCreditForKills(objShip);
            space_combat.targetDestroyed(objShip);
        }
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "KABOOM");
        obj_id[] notifylist = getObjIdArrayObjVar(self, "destroynotify");
        if (notifylist != null)
        {
            dictionary outparams = new dictionary();
            outparams.put("object", self);
            for (int i = 0; i < notifylist.length; i++)
            {
                space_utils.notifyObject(notifylist[i], "shipDestroyed", outparams);
            }
        }
        obj_id objPilot = getPilotId(self);
        if (!space_utils.isPlayerControlledShip(self))
        {
            if (hasObjVar(self, "objParent"))
            {
                space_content.notifySpawner(self);
            }
        }
        else if (isIdValid(objPilot))
        {
            sendSystemMessageTestingOnly(objPilot, "Earth Shattering Kaboom goes here!");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int objectDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPilot = getPilotId(self);
        if (!space_utils.isPlayerControlledShip(self))
        {
            if (hasObjVar(self, "objParent"))
            {
                space_content.notifySpawner(self);
            }
            float fltIntensity = rand(0, 1.0f);
            handleShipDestruction(self, fltIntensity);
        }
        else 
        {
            sendSystemMessageTestingOnly(objPilot, "Earth Shattering Kaboom goes here!");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int targetDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPilot = getPilotId(self);
        if (!space_utils.isPlayerControlledShip(self))
        {
            return SCRIPT_CONTINUE;
        }
        space_utils.notifyObject(objPilot, "targetDestroyed", params);
        float fltKills = getFloatObjVar(self, "fltKills");
        float fltPercentage = params.getFloat("fltPercentage");
        fltKills = fltKills + fltPercentage;
        sendSystemMessageTestingOnly(objPilot, "TARGET KILLED :Percentage of kill was " + fltPercentage + " total kills this session is " + fltKills);
        setObjVar(self, "fltKills", fltKills);
        return SCRIPT_CONTINUE;
    }
    public int targetDisabled(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objDefender = params.getObjId("objDefender");
        obj_id objPilot = getPilotId(self);
        if (!space_utils.isPlayerControlledShip(self))
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(objPilot, SID_TARGET_DISABLED);
        space_utils.notifyObject(objPilot, "targetDisabled", params);
        return SCRIPT_CONTINUE;
    }
    public int megaDamage(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objAttacker = params.getObjId("objShip");
        float fltRemainingDamage = space_combat.doChassisDamage(objAttacker, self, 0, 45000000);
        if (fltRemainingDamage > 0)
        {
            obj_id objDefenderPilot = getPilotId(self);
            if (!space_utils.isPlayerControlledShip(self))
            {
                space_combat.grantRewardsAndCreditForKills(self);
                space_combat.targetDestroyed(self);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                dictionary dctParams = new dictionary();
                dctParams.put("objAttacker", objAttacker);
                dctParams.put("objShip", self);
                space_utils.notifyObject(objDefenderPilot, "playerShipDestroyed", dctParams);
                setPosture(objDefenderPilot, POSTURE_INCAPACITATED);
                pclib.coupDeGrace(objDefenderPilot, objDefenderPilot);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTryToEquipDroidControlDeviceInShip(obj_id self, obj_id objPlayer, obj_id objControlDevice) throws InterruptedException
    {
        if (!isIdValid(objControlDevice))
        {
            return SCRIPT_CONTINUE;
        }
        if (space_crafting.isUsableAstromechPet(objControlDevice))
        {
            if (space_crafting.isCertifiedForAstromech(objControlDevice, objPlayer))
            {
                if (isShipSlotInstalled(self, ship_chassis_slot_type.SCST_droid_interface))
                {
                    if (!isShipComponentDisabled(self, ship_chassis_slot_type.SCST_droid_interface))
                    {
                        sendSystemMessageTestingOnly(self, "Equipping " + objControlDevice + " to " + self);
                        associateDroidControlDeviceWithShip(self, objControlDevice);
                    }
                    else 
                    {
                        string_id strSpam = new string_id("space/space_interaction", "droid_interface_disabled");
                        sendSystemMessage(objPlayer, strSpam);
                        sendSystemMessageTestingOnly(objPlayer, "Equipping " + objControlDevice + " to " + self);
                        associateDroidControlDeviceWithShip(self, objControlDevice);
                    }
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    sendSystemMessageTestingOnly(objPlayer, "Equipping " + objControlDevice + " to " + self + ", No interface");
                    associateDroidControlDeviceWithShip(self, objControlDevice);
                    string_id strSpam = new string_id("space/space_interaction", "no_droid_command_module");
                    sendSystemMessage(objPlayer, strSpam);
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                string_id strSpam = new string_id("space/space_interaction", "droid_not_certified");
                sendSystemMessage(objPlayer, strSpam);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            string_id strSpam = new string_id("space/space_interaction", "not_an_astromech_for_space");
            sendSystemMessage(objPlayer, strSpam);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnShipComponentUninstalling(obj_id self, obj_id uninstallerId, int intSlot, obj_id targetContainer) throws InterruptedException
    {
        if (intSlot == ship_component_type.SCT_droid_interface)
        {
            obj_id objDroidControlDevice = getDroidControlDeviceForShip(self);
            if (isIdValid(objDroidControlDevice))
            {
                removeDroidControlDeviceFromShip(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int notifyOnDestroy(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id obj = params.getObjId("object");
        if (!isIdValid(obj))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] notifyobjs = getObjIdArrayObjVar(self, "destroynotify");
        obj_id[] newnotifyobjs = null;
        if (notifyobjs == null)
        {
            newnotifyobjs = new obj_id[1];
            newnotifyobjs[0] = obj;
        }
        else 
        {
            newnotifyobjs = new obj_id[notifyobjs.length + 1];
            for (int i = 0; i < notifyobjs.length; i++)
            {
                newnotifyobjs[i] = notifyobjs[i];
            }
            newnotifyobjs[notifyobjs.length] = obj;
        }
        setObjVar(self, "destroynotify", newnotifyobjs);
        return SCRIPT_CONTINUE;
    }
    public int OnShipDisabled(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objAttacker = params.getObjId("objAttacker");
        if (hasScript(self, "space.ai.space_ai"))
        {
            ship_ai.spaceStop(self);
            detachScript(self, "space.ai.space_ai");
            dictionary outparams = new dictionary();
            outparams.put("attacker", objAttacker);
            messageTo(self, "selfDestruct", outparams, 12000.f + rand() * 60.f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int selfDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        space_combat.doChassisDamage(params.getObjId("attacker"), self, 0, 45000000);
        space_combat.targetDestroyed(self);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "intCleaningUp", 1);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
