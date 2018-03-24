package script.space.combat;

import script.*;
import script.library.*;

import java.util.Vector;

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
        for (int intI = 0; intI < intSlots.length; intI++)
        {
            if(isShipSlotInstalled(self, intI)) {
                setShipComponentEfficiencyGeneral(self, intSlots[intI], 1.0f);
                setShipComponentEfficiencyEnergy(self, intSlots[intI], 1.0f);
            }
        }
        String strCapitalShipType = getStringObjVar(self, "strCapitalShipType");
        if (strCapitalShipType == null || strCapitalShipType.equals("") || strCapitalShipType.equals("null")) //yes, it seems there's a literal string "null" derived from the space mobile table
        {
            debugSpeakMsg(self, "BAD CAPITAL SHIP DEFINED!");
        }
        else {
            String strFileName = "datatables/space_combat/capital_ships/" + strCapitalShipType + ".iff";
            if (dataTableOpen(strFileName)) {
                setupCapitalShipComponents(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnShipWasHit(obj_id self, obj_id objAttacker, int intWeaponIndex, boolean isMissile, int missileType, int intTargetedComponent, boolean fromPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException
    {
        if (hasObjVar(self, "intInvincible") || space_combat.hasDeathFlags(self) || !pvpCanAttack(objAttacker, self))
        {
            return SCRIPT_CONTINUE;
        }
        int intWeaponSlot = intWeaponIndex + ship_chassis_slot_type.SCST_weapon_0;

        obj_id objPilot = getPilotId(objAttacker);
        transform attackerTransform_w = getTransform_o2w(objAttacker);
        transform defenderTransform_w = getTransform_o2w(self);

        vector hitDirection_o = defenderTransform_w.rotateTranslate_p2l(attackerTransform_w.getPosition_p());
        int intSide = 0;
        if (hitDirection_o.z < 0.f)
        {
            intSide = 1;
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
        if (fltDamage > 0f)
        {
            notifyShipDamage(self, objAttacker, fltDamage);
        }
        space_combat.doComponentDamage(objAttacker, self, intWeaponSlot, intTargetedComponent, fltDamage, intSide);
        checkAndUpdateCapitalShipStatus(self, intTargetedComponent, objAttacker, fltDamage);
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
	    if ((strCapitalShipType == null) || (strCapitalShipType.equals("")) || strCapitalShipType.equals("null"))
        {
            return;
        }
        int intDamagePhase = 1;
        String strFileName = "datatables/space_combat/capital_ships/" + strCapitalShipType + ".iff";
        if (dataTableOpen(strFileName))
        {
            // get components that are part of the first damage phase and make them targetable
            String[] strComponents = dataTableGetStringColumnNoDefaults(strFileName, "strComponents1");
            int[] intDamagePhaseComponents = makeComponentsTargetable(objShip, strComponents);
            utils.setScriptVar(objShip, "intDamagePhaseComponents", intDamagePhaseComponents);

            // get components that are always targetable and make them targetable too.
            strComponents = dataTableGetStringColumnNoDefaults(strFileName, "strAlwaysTargetable");
            makeComponentsTargetable(objShip, strComponents);
        }
        utils.setScriptVar(objShip, "intDamagePhase", 1);
    }
    public void checkAndUpdateCapitalShipStatus(obj_id objShip, int intSlot, obj_id attacker, float damage) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        float fltPercentage = space_crafting.getDamagePercentage(objShip, intSlot);
        dctParams.put("fltPercentage", fltPercentage);
        dctParams.put("intSlot", intSlot);
        dctParams.put("objAttacker", attacker);
        float minHp = getShipComponentHitpointsCurrent(objShip, intSlot);
        float maxHp = getShipComponentHitpointsMaximum(objShip, intSlot);
        if (fltPercentage == 0.0f)
        {
            // don't output slot 115 stuff... that's the main ship and it's generally disabled.
            if(intSlot != 115)
                LOG("space","---- Component DISABLED on ship (" + objShip + ":" + getName(objShip) + ") in slot " + intSlot + " by " + getName(attacker) + " - notifying ship ----");
            space_utils.notifyObject(objShip, "componentDisabled", dctParams);
        }
        else if (fltPercentage < 1f)
        {
            LOG("space","---- Component Damaged (" + damage + ") on ship (" + objShip + ":" + getName(objShip) + ") in slot: " + intSlot + " by " + getName(attacker) + " - integrity at: " + ((minHp/maxHp) * 100) + "% ----");
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

        int dpcSize = intDamagePhaseComponents.size();
        LOG("space","Ship (" + getName(self) + " has " + dpcSize + " component(s) remaining this phase:");

        for(int i = 0; i < dpcSize; i++){
            int slot = ((Integer) intDamagePhaseComponents.get(i)).intValue();
            LOG("space","-- Component in slot " + slot + ": " + getShipComponentName(self, slot) + " has " + getShipComponentHitpointsCurrent(self, slot) + "/" + getShipComponentHitpointsMaximum(self, slot) + " hitpoints.");
            // remove component from being tracked for this phase if it's disabled - also checks for other disabled components.
            if(isShipComponentDisabled(self, slot)){
                intDamagePhaseComponents = utils.removeElementAt(intDamagePhaseComponents, i);
                dpcSize--;
            }
        }
        int[] shipSlots = getShipChassisSlots(self);

        // statements and for loop just for debugging.
        LOG("space","---- This ships (" + self + ":" + getName(self) + ") components and durabilities are as follows:");
        for(int j = 0; j < shipSlots.length; j++){
            int shipSlot = shipSlots[j];
            if(isShipSlotInstalled(self, shipSlot)){
                float minHp = getShipComponentHitpointsCurrent(self, shipSlot);
                float maxHp = getShipComponentHitpointsMaximum(self, shipSlot);
                float integrity =  (minHp/maxHp) * 100;
                LOG("space","---- SLOT " + shipSlot + ": " + getShipComponentName(self, shipSlot) + " HP: "
                        +  minHp + "/" + maxHp + " Integrity: " + integrity + "%"
                );
            }
        }

        if (intDamagePhaseComponents.size() == 0)
        {
            LOG("space","====================== Damage Phase " + utils.getIntScriptVar(self, "intDamagePhase") + " Complete!! ======================");
            LOG("space","====================== Processing Damage Phase!! ======================");
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
	    if ((strCapitalShipType == null) || (strCapitalShipType.equals("")) || strCapitalShipType.equals("null"))
        {
            return;
        }
        String strFileName = "datatables/space_combat/capital_ships/" + strCapitalShipType + ".iff";

        int intDamagePhase = utils.getIntScriptVar(objShip, "intDamagePhase");

        String[] strOldComponents = dataTableGetStringColumnNoDefaults(strFileName, "strComponents" + intDamagePhase);

        intDamagePhase++;

        String[] strNewComponents = dataTableGetStringColumnNoDefaults(strFileName, "strComponents" + intDamagePhase);

        if (strNewComponents != null && strNewComponents.length > 0) {
            LOG("space","============= Found more ship components in table ==============");
            if (strNewComponents.length != 1 || strNewComponents[0].length() != 0) {
                LOG("space","============= Adding " + strNewComponents.length + " Components for Phase " + intDamagePhase + " ==============");
                int[] intDamagePhaseComponents = makeComponentsTargetable(objShip, strNewComponents);
                makeComponentsUntargetable(objShip, strOldComponents);
                utils.setScriptVar(objShip, "intDamagePhaseComponents", intDamagePhaseComponents);
                utils.setScriptVar(objShip, "intDamagePhase", intDamagePhase);
                return;
            }
            LOG("space","============= Could not add components!! ==============");
            for(int i = 0; i < strNewComponents.length; i++){
                LOG("space","== Component (" + strNewComponents[i] + ") with name length " + strNewComponents[i].length() + " could not be added.");
            }
            LOG("space","============= Assuming error and granting pilot rewards!! ==============");
        }
        space_combat.grantRewardsAndCreditForKills(objShip);
        space_combat.targetDestroyed(objShip);
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "KABOOM");
        LOG("space","================ Ship (" + self + ":" + getName(self) + ") has been destroyed ===============");
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
        LOG("space","================ Target (" + self + ":" + getName(self) + ") has been destroyed ===============");
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
        LOG("space","================ Target (" + self + ":" + getName(self) + ") has been disabled ===============");
        if (!space_utils.isPlayerControlledShip(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id objDefender = params.getObjId("objDefender");
        obj_id objPilot = getPilotId(self);
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
