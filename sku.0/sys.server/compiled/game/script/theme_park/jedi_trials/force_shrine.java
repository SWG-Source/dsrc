package script.theme_park.jedi_trials;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.badge;
import script.library.buff;
import script.library.jedi_trials;
import script.library.jedi;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class force_shrine extends script.base_script
{
    public force_shrine()
    {
    }
    public static final string_id MEDITATE_MENU = new string_id("jedi_trials", "meditate");
    public static final string_id ISSUE_ROBE_MENU = new string_id("jedi_trials", "issue_robe");
    public static final string_id SHOW_RESPECT = new string_id("jedi_trials", "show_respect");
    public static final string_id FULL_INVENTORY = new string_id("jedi_trials", "inventory_full");
    public static final string_id NOT_JEDI = new string_id("jedi_trials", "not_padawan");
    public static final string_id ISSUE_ROBE_ULTRA_LIGHT = new string_id("jedi_trials", "ultra_robe_light_issued");
    public static final string_id ISSUE_ROBE_ULTRA_DARK = new string_id("jedi_trials", "ultra_robe_dark_issued");
    public static final string_id ALREADY_HAVE = new string_id("jedi_trials", "already_have");
    public static final string_id ISSUE_ULTRA_ROBE_LIGHT_MENU = new string_id("jedi_trials", "issue_ultra_robe_light");
    public static final string_id ISSUE_ULTRA_ROBE_DARK_MENU = new string_id("jedi_trials", "issue_ultra_robe_dark");
    public static final string_id CLOAK_TOO_SOON = new string_id("jedi_trials", "cloak_too_soon");
    public static final String PADAWAN_ROBE = "object/tangible/wearables/robe/robe_jedi_padawan_generic.iff";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canMeditateHere(self, player))
        {
            int menuOption = mi.addRootMenu(menu_info_types.SERVER_ITEM_OPTIONS, MEDITATE_MENU);
            if (utils.isProfession(player, utils.FORCE_SENSITIVE) && !utils.playerHasItemByTemplateInInventoryOrEquipped(player, PADAWAN_ROBE))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU5, ISSUE_ROBE_MENU);
            }
            if (utils.isProfession(player, utils.FORCE_SENSITIVE) && canGetUltraCloak(player))
            {
                if (!jedi.hasAnyUltraCloak(player) && getState(player, STATE_MEDITATE) == 1)
                {
                    mi.addRootMenu(menu_info_types.SERVER_MENU6, ISSUE_ULTRA_ROBE_LIGHT_MENU);
                    mi.addRootMenu(menu_info_types.SERVER_MENU7, ISSUE_ULTRA_ROBE_DARK_MENU);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        int posture = getPosture(player);
        if (canMeditateHere(self, player))
        {
            if (item == menu_info_types.SERVER_ITEM_OPTIONS)
            {
                if (posture != POSTURE_CROUCHED)
                {
                    sendSystemMessage(player, SHOW_RESPECT);
                    return SCRIPT_CONTINUE;
                }
                jedi_trials.giveGenericForceShrineMessage(player);
            }
            if (item == menu_info_types.SERVER_MENU5)
            {
                if (posture != POSTURE_CROUCHED)
                {
                    sendSystemMessage(player, SHOW_RESPECT);
                    return SCRIPT_CONTINUE;
                }
                issuePadawanRobe(player);
            }
            if (item == menu_info_types.SERVER_MENU6)
            {
                if (getState(player, STATE_MEDITATE) != 1)
                {
                    sendSystemMessage(player, SHOW_RESPECT);
                    return SCRIPT_CONTINUE;
                }
                issueUltraCloak(player, 0);
            }
            if (item == menu_info_types.SERVER_MENU7)
            {
                if (getState(player, STATE_MEDITATE) != 1)
                {
                    sendSystemMessage(player, SHOW_RESPECT);
                    return SCRIPT_CONTINUE;
                }
                issueUltraCloak(player, 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleEnterTrialsChoice(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!utils.hasScriptVar(player, "jedi_trials.trials_type"))
        {
            return SCRIPT_CONTINUE;
        }
        String trialsType = utils.getStringScriptVar(player, "jedi_trials.trials_type");
        utils.removeScriptVar(player, "jedi_trials.trials_script");
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            if (trialsType.equals("knight"))
            {
                if (jedi_trials.isEligibleForJediKnightTrials(player))
                {
                    if (!hasScript(player, jedi_trials.KNIGHT_TRIALS_SCRIPT))
                    {
                        attachScript(player, jedi_trials.KNIGHT_TRIALS_SCRIPT);
                    }
                }
            }
            else if (trialsType.equals("padawan"))
            {
                if (jedi_trials.isEligibleForJediPadawanTrials(player))
                {
                    if (!hasScript(player, jedi_trials.PADAWAN_TRIALS_SCRIPT))
                    {
                        attachScript(player, jedi_trials.PADAWAN_TRIALS_SCRIPT);
                    }
                    else 
                    {
                        jedi_trials.doPadawanTrialsSetup(player);
                    }
                }
            }
            break;
            case sui.BP_CANCEL:
            jedi_trials.giveGenericForceShrineMessage(player);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canMeditateHere(obj_id forceShrine, obj_id player) throws InterruptedException
    {
        if (isIdValid(forceShrine) && isIdValid(player))
        {
            if (utils.isProfession(player, utils.FORCE_SENSITIVE))
            {
                return true;
            }
            else if (hasObjVar(player, "overridePTEligibility"))
            {
                if (isGod(player))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isOnJediTrials(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, jedi_trials.PADAWAN_QUESTLIST_OBJVAR) || hasObjVar(player, jedi_trials.KNIGHT_QUESTLIST_OBJVAR))
        {
            return true;
        }
        return false;
    }
    public boolean hasNotFoundShrine(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, jedi_trials.JEDI_TRIALS_SHRINELOC_OBJVAR))
        {
            return true;
        }
        return false;
    }
    public boolean isTargetShrine(obj_id self, obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, "jedi_trials.allowAnyShrine"))
        {
            return true;
        }
        location targetShrineLoc = getLocationObjVar(player, jedi_trials.JEDI_TRIALS_SHRINELOC_OBJVAR);
        location currentShrineLoc = getLocation(self);
        if (currentShrineLoc != null && targetShrineLoc != null)
        {
            String targetPlanet = targetShrineLoc.area;
            String currentPlanet = currentShrineLoc.area;
            if (targetPlanet.equals(currentPlanet))
            {
                if (utils.getDistance2D(currentShrineLoc, targetShrineLoc) <= 10)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isTargetShrinePlanet(obj_id self, obj_id player) throws InterruptedException
    {
        location currentShrineLoc = getLocation(self);
        location targetShrineLoc = getLocationObjVar(player, jedi_trials.JEDI_TRIALS_SHRINELOC_OBJVAR);
        if (currentShrineLoc != null && targetShrineLoc != null)
        {
            String currentPlanet = currentShrineLoc.area;
            String targetPlanet = targetShrineLoc.area;
            if (targetPlanet.equals(currentPlanet))
            {
                return true;
            }
        }
        return false;
    }
    public void issuePadawanRobe(obj_id player) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        if (!utils.isProfession(player, utils.FORCE_SENSITIVE))
        {
            sendSystemMessage(player, NOT_JEDI);
            return;
        }
        if (utils.playerHasItemByTemplateInBank(player, PADAWAN_ROBE))
        {
            sendSystemMessage(player, new string_id("jedi_trials", "robe_banked"));
            return;
        }
        if (utils.playerHasItemByTemplateInInventoryOrEquipped(player, PADAWAN_ROBE))
        {
            sendSystemMessage(player, new string_id("jedi_trials", "robe_equipped"));
            return;
        }
        if (getVolumeFree(pInv) <= 0)
        {
            sendSystemMessage(player, FULL_INVENTORY);
            return;
        }
        static_item.createNewItemFunction("item_jedi_robe_padawan_04_01", pInv);
        sendSystemMessage(player, new string_id("jedi_trials", "robe_issued"));
    }
    public boolean canGetUltraCloak(obj_id player) throws InterruptedException
    {
        return (badge.hasBadge(player, "bdg_col_jedi_robe"));
    }
    public void issueUltraCloak(obj_id player, int robeType) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        String robeName = "";
        String clientEffect = "";
        string_id robeSpam = new string_id();
        switch (robeType)
        {
            case 0:
            robeName = jedi.JEDI_CLOAK_LIGHT_HOOD_DOWN;
            robeSpam = ISSUE_ROBE_ULTRA_LIGHT;
            clientEffect = "clienteffect/jedi_master_cloak_good.cef";
            break;
            case 1:
            robeName = jedi.JEDI_CLOAK_DARK_HOOD_DOWN;
            robeSpam = ISSUE_ROBE_ULTRA_DARK;
            clientEffect = "clienteffect/jedi_master_cloak_evil.cef";
            break;
            default:
            return;
        }
        if (robeName != null && !robeName.equals(""))
        {
            if (!utils.isProfession(player, utils.FORCE_SENSITIVE))
            {
                sendSystemMessage(player, NOT_JEDI);
                return;
            }
            if (jedi.hasAnyUltraCloak(player))
            {
                sendSystemMessage(player, ALREADY_HAVE);
                return;
            }
            if (getVolumeFree(pInv) <= 0)
            {
                sendSystemMessage(player, FULL_INVENTORY);
                return;
            }
            if (buff.hasBuff(player, "utlra_jedi_cloak_block"))
            {
                sendSystemMessage(player, CLOAK_TOO_SOON);
                return;
            }
            static_item.createNewItemFunction(robeName, pInv);
            playClientEffectObj(player, clientEffect, player, "");
            sendSystemMessage(player, robeSpam);
            buff.applyBuff(player, "utlra_jedi_cloak_block");
        }
    }
}
