package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.xp;
import script.library.loot;
import script.library.utils;
import script.library.money;
import script.library.features;

public class newbie_skipped extends script.theme_park.newbie_tutorial.tutorial_base
{
    public newbie_skipped()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (features.isSpaceEdition(self))
        {
            setObjVar(self, "jtlNewbie", 4);
        }
        deleteInventory(self);
        fillbank(self);
        grantNewbieStartingMoney(self);
        setObjVar(self, "newbie.oathCompleted", true);
        String skillName = getStringObjVar(self, "newbie.hasSkill");
        if (skillName != null)
        {
            if (!hasSkill(self, skillName))
            {
                grantSkill(self, skillName);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        sendStartingMessage(self);
        newbieTutorialEnableHudElement(self, "all", true, 0.0f);
        obj_id playerInv = utils.getInventoryContainer(self);
        obj_id[] contents = getContents(playerInv);
        for (int i = 0; i < contents.length; i++)
        {
            if (hasObjVar(contents[i], "newbie.item"))
            {
                if (hasScript(contents[i], BOX_ITEM_SCRIPT))
                {
                    detachScript(contents[i], BOX_ITEM_SCRIPT);
                }
                destroyObject(contents[i]);
            }
        }
        transferBankToInventory(self);
        for (int i = 0; i < BOX_CONTENTS.length; i++)
        {
            createObject(BOX_CONTENTS[i], playerInv, "");
        }
        obj_id pInv = utils.getInventoryContainer(self);
        contents = getContents(pInv);
        if (contents != null)
        {
            for (int i = 0; i < contents.length; i++)
            {
            }
        }
        contents = getContents(self);
        if (contents != null)
        {
            for (int i = 0; i < contents.length; i++)
            {
            }
        }
        removeObjVar(self, "newbie");
        removeObjVar(self, "skipTutorial");
        removeObjVar(self, "banking_bankid");
        grantAllNoviceSkills(self);
        return SCRIPT_CONTINUE;
    }
    public int xferFailed(obj_id self, dictionary params) throws InterruptedException
    {
        grantNewbieStartingMoney(self);
        return SCRIPT_CONTINUE;
    }
    public int timeToWithdraw(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            messageTo(self, "xferFailed", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        int amt = params.getInt(money.DICT_AMOUNT);
        withdrawCashFromBank(self, amt, "cashReceived", "xferFailed", params);
        return SCRIPT_CONTINUE;
    }
    public int cashReceived(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int newbieRequestStartingLocations(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        newbieTutorialSendStartingLocationsToPlayer(self, null);
        return SCRIPT_CONTINUE;
    }
    public int newbieSelectStartingLocation(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        sendToStartLocation(self, params);
        return SCRIPT_CONTINUE;
    }
    public int handleEndTutorial(obj_id self, dictionary params) throws InterruptedException
    {
        newbieTutorialEnableHudElement(self, "all", true, 0.0f);
        detachScript(self, "theme_park.newbie_tutorial.newbie_skipped");
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        location loc = getLocation(self);
        String area = loc.area;
        if (!area.equals("tutorial"))
        {
            newbieTutorialEnableHudElement(self, "all", true, 0.0f);
            detachScript(self, "theme_park.newbie_tutorial.newbie_skipped");
        }
        return SCRIPT_CONTINUE;
    }
}
