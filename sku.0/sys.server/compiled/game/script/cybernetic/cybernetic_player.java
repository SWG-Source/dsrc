package script.cybernetic;

import script.dictionary;
import script.library.*;
import script.obj_id;
import script.string_id;

public class cybernetic_player extends script.systems.combat.combat_base
{
    public cybernetic_player()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        cybernetic.validateSkillMods(self);
        return SCRIPT_CONTINUE;
    }
    public int cyborgLightning(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!cybernetic.hasUndamagedCybernetic(self, "cyborgLightning"))
        {
            handleFailureSound(self);
            return SCRIPT_OVERRIDE;
        }
        if (!combatStandardAction("cyborgLightning", self, target, params, "", ""))
        {
            handleFailureSound(self);
            return SCRIPT_OVERRIDE;
        }
        executePowerSounds(self, "cyborgLightning");
        return SCRIPT_CONTINUE;
    }
    public int cyborgStrengthBuff(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!cybernetic.hasUndamagedCybernetic(self, "cyborgStrengthBuff"))
        {
            handleFailureSound(self);
            return SCRIPT_OVERRIDE;
        }
        buff.applyBuff(self, "cyborgStrengthBuff");
        doAnimationAction(self, "pound_fist_palm");
        executePowerSounds(self, "cyborgStrengthBuff");
        playClientEffectObj(self, "appearance/pt_heal_resist_states.prt", self, "");
        return SCRIPT_CONTINUE;
    }
    public int cyborgBurstRun(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!cybernetic.hasUndamagedCybernetic(self, "cyborgBurstRun"))
        {
            handleFailureSound(self);
            return SCRIPT_OVERRIDE;
        }
        buff.applyBuff(self, "cyborgBurstRun");
        executePowerSounds(self, "cyborgBurstRun");
        return SCRIPT_CONTINUE;
    }
    public int cyborgRevive(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!cybernetic.hasUndamagedCybernetic(self, "cyborgRevive"))
        {
            handleFailureSound(self);
            return SCRIPT_OVERRIDE;
        }
        if (ai_lib.isInCombat(self))
        {
            handleFailureSound(self);
            return SCRIPT_OVERRIDE;
        }
        if (cybernetic.doCyborgRevive(self, target))
        {
            executePowerSounds(self, "cyborgRevive");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            handleFailureSound(self);
            return SCRIPT_OVERRIDE;
        }
    }
    public int cyborgSureShot(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!cybernetic.hasUndamagedCybernetic(self, "cyborgSureShot"))
        {
            handleFailureSound(self);
            return SCRIPT_OVERRIDE;
        }
        if (!combatStandardAction("cyborgSureShot", self, target, params, "", ""))
        {
            handleFailureSound(self);
            return SCRIPT_OVERRIDE;
        }
        executePowerSounds(self, "cyborgSureShot");
        return SCRIPT_CONTINUE;
    }
    public int cyborgCriticalSnipe(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!cybernetic.hasUndamagedCybernetic(self, "cyborgCriticalSnipe"))
        {
            handleFailureSound(self);
            return SCRIPT_OVERRIDE;
        }
        if (!combatStandardAction("cyborgCriticalSnipe", self, target, params, "", ""))
        {
            handleFailureSound(self);
            return SCRIPT_OVERRIDE;
        }
        executePowerSounds(self, "cyborgCriticalSnipe");
        return SCRIPT_CONTINUE;
    }
    public int cyborgKickDown(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!cybernetic.hasUndamagedCybernetic(self, "cyborgKickDown"))
        {
            handleFailureSound(self);
            return SCRIPT_OVERRIDE;
        }
        if (!combatStandardAction("cyborgKickDown", self, target, params, "", ""))
        {
            handleFailureSound(self);
            return SCRIPT_OVERRIDE;
        }
        executePowerSounds(self, "cyborgKickDown");
        return SCRIPT_CONTINUE;
    }
    public void handleFailureSound(obj_id self) throws InterruptedException
    {
        playClientEffectObj(self, "clienteffect/cyborg_ui_cannot_use.cef", self, "");
    }
    public void executePowerSounds(obj_id self, String commandName) throws InterruptedException
    {
        dictionary parms = new dictionary();
        parms.put("commandName", commandName);
        messageTo(self, "handlePowerDownSound", parms, 2, false);
    }
    public int handlePowerDownSound(obj_id self, dictionary params) throws InterruptedException
    {
        playClientEffectObj(self, "clienteffect/cyborg_itm_powerdown.cef", self, "");
        String commandName = params.getString("commandName");
        if (commandName != null && !commandName.equals(""))
        {
            float cooldownTime = dataTableGetFloat("datatables/command/command_table.iff", commandName, "cooldownTime");
            if (cooldownTime > 3.0f)
            {
                cooldownTime -= 3.0f;
                messageTo(self, "handlePowerUpSound", null, cooldownTime, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePowerUpSound(obj_id self, dictionary params) throws InterruptedException
    {
        playClientEffectObj(self, "clienteffect/cyborg_itm_powerup.cef", self, "");
        return SCRIPT_CONTINUE;
    }
    public int handleInstallPaymentConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("sissynoid", "handleInstallPaymentConfirmed:::: ENTERED");
        if ((params == null) || (params.isEmpty()))
        {
            CustomerServiceLog("cyborg", "Cybernetics - Bad Params - please contact the Design Team if the issue persists");
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int amt = utils.getIntScriptVar(player, "cyborg.install.amount");
        obj_id item = utils.getObjIdScriptVar(player, "cyborg.install.item");
        int playerInstalledCount = cybernetic.getPlayerInstalledCyberneticCount(player);
        int playerCyberSkillMod = getEnhancedSkillStatisticModifierUncapped(player, "cybernetic_psychosis_resistance");
        int cyberneticToInstall = cybernetic.getCyberneticPointValue(item);
        if (playerInstalledCount < 0 || playerCyberSkillMod < 0 || cyberneticToInstall < 0)
        {
            CustomerServiceLog("cyborg", "Player(" + getPlayerFullName(player) + ", " + player + ") can not install Cybernetic (" + item + ") - Code issues - please contact the Design Team.");
            sendSystemMessage(self, new string_id("ep3/cybernetic", "cannot_install"));
            return SCRIPT_CONTINUE;
        }
        LOG("sissynoid", "handleInstallPaymentConfirmed:::: playerInstalledCount: " + playerInstalledCount + ", playerCyberSkillMod: " + playerCyberSkillMod + ", cyberneticToInstall: " + cyberneticToInstall);
        if (!isIdValid(player))
        {
            removeCyberneticInstallScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == -1)
        {
            CustomerServiceLog("cyborg", "Player(" + getPlayerFullName(player) + ", " + player + ") had a cybernetic UI error (btn = -1)");
            removeCyberneticInstallScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        else if (bp != sui.BP_OK)
        {
            removeCyberneticInstallScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(item))
        {
            CustomerServiceLog("cyborg", "Player(" + getPlayerFullName(player) + ", " + player + ") had a cybernetic UI error Cybernetic Part(" + item + ") was invalid");
            removeCyberneticInstallScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (!money.pay(player, "cyborg", amt, "handleInstallPaid", params, false))
        {
            CustomerServiceLog("cyborg", "Player(" + getPlayerFullName(player) + ", " + player + ") had a Failed Payment attempting to install Cybernetic Item(" + item + ")");
            removeCyberneticInstallScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleInstallPaid(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            CustomerServiceLog("cyborg", "Cybernetics - Bad Params - please contact the Design Team if the issue persists");
            return SCRIPT_CONTINUE;
        }
        int amt = utils.getIntScriptVar(self, "cyborg.install.amount");
        obj_id item = utils.getObjIdScriptVar(self, "cyborg.install.item");
        if (params.getInt(money.DICT_CODE) == money.RET_FAIL)
        {
            removeCyberneticInstallScriptVars(self);
            CustomerServiceLog("cyborg", "Player(" + self + ") did not have enough money to install this cybernetic(" + item + ")");
            sendSystemMessage(self, new string_id("ep3/cybernetic", "no_money"));
            return SCRIPT_CONTINUE;
        }
        String itemTemplate = getTemplateName(item);
        if ((itemTemplate.contains("crafted")) && hasScript(item, "item.armor.biolink_item_non_faction"))
        {
            CustomerServiceLog("cyborg", "Crafted Cybernetic Fix -Player(" + self + ") Cybernetic(" + item + ") had the bio-link script removed because it is not needed.");
            detachScript(item, "item.armor.biolink_item_non_faction");
        }
        if (itemTemplate.startsWith("object/tangible/wearables/cybernetic/cybernetic_anakin_forearm.iff"))
        {
            CustomerServiceLog("cyborg", "Anakin Reward Arm Fix: Anakin Forearm(" + item + ") is having it's bio-link removed for player(" + self + ").");
            clearBioLink(item);
            if (hasScript(item, "item.armor.biolink_item_non_faction"))
            {
                CustomerServiceLog("cyborg", "Anakin Reward Arm Fix: Anakin Forearm(" + item + ") is having it's bio-link script for player(" + self + ").");
                detachScript(item, "item.armor.biolink_item_non_faction");
            }
            if (hasObjVar(item, "biolink.id"))
            {
                CustomerServiceLog("cyborg", "Anakin Reward Arm Fix: Anakin Forearm(" + item + ") is having it's bio-link objvar for player(" + self + ").");
                removeObjVar(item, "biolink.id");
            }
            CustomerServiceLog("cyborg", "Anakin Reward Arm Fix: Anakin Forearm(" + item + ") has had all bio-link remnants removed.  Player(" + self + ") was the owner at the time.");
        }
        removeCyberneticInstallScriptVars(self);
        if (!isIdValid(item))
        {
            if (amt > 0)
            {
                CustomerServiceLog("cyborg", "Cybernetic Item(" + item + ") was invalid - player(" + self + ") had his/her credits(" + amt + ") returned to him/her");
                params.put("refundAmount", amt);
                transferBankCreditsFromNamedAccount("cyborg", self, amt, "handleCyborgRefunded", "handleCyborgNotRefunded", params);
            }
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "cybernetic.appearance_install"))
        {
            if (hasObjVar(item, "biolink.id"))
            {
                obj_id biolinkId = getObjIdObjVar(item, "biolink.id");
                if ((biolinkId != self) && (biolinkId != utils.OBJ_ID_BIO_LINK_PENDING) && (biolinkId != null))
                {
                    sendSystemMessage(self, new string_id("ep3/cybernetic", "bio_link_mismatch"));
                    transferBankCreditsFromNamedAccount("cyborg", self, amt, "handleCyborgRefunded", "handleCyborgNotRefunded", params);
                    return SCRIPT_CONTINUE;
                }
            }
            obj_id a_tab_inv = getAppearanceInventory(self);
            if (equip(item, a_tab_inv))
            {
                CustomerServiceLog("cyborg", "Cybernetic Item(" + item + ") was successfully A-Tab installed on Player(" + self + ")");
                playClientEffectObj(self, "clienteffect/cyborg_itm_installed.cef", self, "");
            }
            else 
            {
                sendSystemMessage(self, new string_id("ep3/cybernetic", "cannot_install"));
                CustomerServiceLog("cyborg", "Cybernetic Item(" + item + ") could not be installed - player(" + self + ") had his/her credits(" + amt + ") returned to him/her");
                params.put("refundAmount", amt);
                transferBankCreditsFromNamedAccount("cyborg", self, amt, "handleCyborgRefunded", "handleCyborgNotRefunded", params);
            }
        }
        else 
        {
            if (hasObjVar(item, "biolink.id"))
            {
                obj_id biolinkId = getObjIdObjVar(item, "biolink.id");
                if ((biolinkId != self) && (biolinkId != utils.OBJ_ID_BIO_LINK_PENDING) && (biolinkId != null))
                {
                    sendSystemMessage(self, new string_id("ep3/cybernetic", "bio_link_mismatch"));
                    transferBankCreditsFromNamedAccount("cyborg", self, amt, "handleCyborgRefunded", "handleCyborgNotRefunded", params);
                    return SCRIPT_CONTINUE;
                }
            }
            if (equip(item, self))
            {
                playClientEffectObj(self, "clienteffect/cyborg_itm_installed.cef", self, "");
                CustomerServiceLog("cyborg", "Cybernetic Item(" + item + ") was successfully installed on Player(" + self + ")");
                if ((getTemplateName(item)).contains("crafted"))
                {
                    CustomerServiceLog("cyborg", "Cybernetic Item(" + item + ") was installed on Player(" + self + ") and Bio-Linked - as designed.");
                    setBioLink(item, self);
                }
            }
            else 
            {
                sendSystemMessage(self, new string_id("ep3/cybernetic", "cannot_install"));
                CustomerServiceLog("cyborg", "Cybernetic Item(" + item + ") could not be installed - player(" + self + ") had his/her credits(" + amt + ") returned to him/her");
                params.put("refundAmount", amt);
                transferBankCreditsFromNamedAccount("cyborg", self, amt, "handleCyborgRefunded", "handleCyborgNotRefunded", params);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void removeCyberneticInstallScriptVars(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "cyborg.install.amount") && utils.hasScriptVar(player, "cyborg.install.item"))
        {
            utils.removeScriptVar(player, "cyborg.install.amount");
            utils.removeScriptVar(player, "cyborg.install.item");
        }
    }
    public int handleCyborgRefunded(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleCyborgNotRefunded(obj_id self, dictionary params) throws InterruptedException
    {
        int amt = params.getInt("refundAmount");
        CustomerServiceLog("cyborg", "Player %TU failed to be refunded " + amt + " credits for aborted cybernetic refund", self);
        return SCRIPT_CONTINUE;
    }
    public int handleUnInstallPaymentConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == -1)
        {
            utils.removeScriptVar(player, "cyborg.remove.amount");
            utils.removeScriptVar(player, "cyborg.remove.item");
            return SCRIPT_CONTINUE;
        }
        else if (bp != sui.BP_OK)
        {
            utils.removeScriptVar(player, "cyborg.remove.amount");
            utils.removeScriptVar(player, "cyborg.remove.item");
            return SCRIPT_CONTINUE;
        }
        int amt = utils.getIntScriptVar(player, "cyborg.remove.amount");
        obj_id item = utils.getObjIdScriptVar(player, "cyborg.remove.item");
        if (!isIdValid(item))
        {
            utils.removeScriptVar(player, "cyborg.remove.amount");
            utils.removeScriptVar(player, "cyborg.remove.item");
            return SCRIPT_CONTINUE;
        }
        if (!money.pay(player, "cyborg", amt, "handleUnInstallPaid", params, false))
        {
            utils.removeScriptVar(player, "cyborg.remove.amount");
            utils.removeScriptVar(player, "cyborg.remove.item");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleUnInstallPaid(obj_id self, dictionary params) throws InterruptedException
    {
        if (params.getInt(money.DICT_CODE) == money.RET_FAIL)
        {
            utils.removeScriptVar(self, "cyborg.remove.amount");
            utils.removeScriptVar(self, "cyborg.remove.item");
            sendSystemMessage(self, new string_id("ep3/cybernetic", "no_money"));
            return SCRIPT_CONTINUE;
        }
        int amt = utils.getIntScriptVar(self, "cyborg.remove.amount");
        obj_id item = utils.getObjIdScriptVar(self, "cyborg.remove.item");
        utils.removeScriptVar(self, "cyborg.remove.amount");
        utils.removeScriptVar(self, "cyborg.remove.item");
        if (!isIdValid(item))
        {
            if (amt > 0)
            {
                sendSystemMessage(self, new string_id("ep3/cybernetic", "cannot_remove"));
                params.put("refundAmount", amt);
                transferBankCreditsFromNamedAccount("cyborg", self, amt, "handleCyborgRefunded", "handleCyborgNotRefunded", params);
            }
            return SCRIPT_CONTINUE;
        }
        obj_id inv = utils.getInventoryContainer(self);
        if (isIdValid(inv) && putIn(item, inv))
        {
            playClientEffectObj(self, "clienteffect/cyborg_itm_uninstalled.cef", self, "");
        }
        else 
        {
            sendSystemMessage(self, new string_id("ep3/cybernetic", "cannot_remove"));
            params.put("refundAmount", amt);
            transferBankCreditsFromNamedAccount("cyborg", self, amt, "handleCyborgRefunded", "handleCyborgNotRefunded", params);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRepairPaymentConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == -1)
        {
            utils.removeScriptVar(player, "cyborg.repair.amount");
            utils.removeScriptVar(player, "cyborg.repair.item");
            return SCRIPT_CONTINUE;
        }
        else if (bp != sui.BP_OK)
        {
            utils.removeScriptVar(player, "cyborg.repair.amount");
            utils.removeScriptVar(player, "cyborg.repair.item");
            return SCRIPT_CONTINUE;
        }
        int amt = utils.getIntScriptVar(player, "cyborg.repair.amount");
        obj_id item = utils.getObjIdScriptVar(player, "cyborg.repair.item");
        if (!isIdValid(item))
        {
            utils.removeScriptVar(player, "cyborg.repair.amount");
            utils.removeScriptVar(player, "cyborg.repair.item");
            return SCRIPT_CONTINUE;
        }
        if (!money.pay(player, "cyborg", amt, "handleRepairPaid", params, false))
        {
            utils.removeScriptVar(player, "cyborg.repair.amount");
            utils.removeScriptVar(player, "cyborg.repair.item");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRepairPaid(obj_id self, dictionary params) throws InterruptedException
    {
        if (params.getInt(money.DICT_CODE) == money.RET_FAIL)
        {
            utils.removeScriptVar(self, "cyborg.repair.amount");
            utils.removeScriptVar(self, "cyborg.repair.item");
            sendSystemMessage(self, new string_id("ep3/cybernetic", "no_money"));
            return SCRIPT_CONTINUE;
        }
        int amt = utils.getIntScriptVar(self, "cyborg.repair.amount");
        obj_id item = utils.getObjIdScriptVar(self, "cyborg.repair.item");
        utils.removeScriptVar(self, "cyborg.repair.amount");
        utils.removeScriptVar(self, "cyborg.repair.item");
        if (!isIdValid(item))
        {
            if (amt > 0)
            {
                params.put("refundAmount", amt);
                transferBankCreditsFromNamedAccount("cyborg", self, amt, "handleCyborgRefunded", "handleCyborgNotRefunded", params);
            }
            return SCRIPT_CONTINUE;
        }
        int itemHps = getHitpoints(item);
        int maxHps = getMaxHitpoints(item);
        maxHps--;
        if (itemHps >= maxHps)
        {
            params.put("refundAmount", amt);
            transferBankCreditsFromNamedAccount("cyborg", self, amt, "handleCyborgRefunded", "handleCyborgNotRefunded", params);
            return SCRIPT_CONTINUE;
        }
        setMaxHitpoints(item, maxHps);
        setHitpoints(item, maxHps);
        sendSystemMessage(self, new string_id("ep3/cybernetic", "item_repaired"));
        playClientEffectObj(self, "clienteffect/cyborg_itm_repaired.cef", self, "");
        return SCRIPT_CONTINUE;
    }
}
