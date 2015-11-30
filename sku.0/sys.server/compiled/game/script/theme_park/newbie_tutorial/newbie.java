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
import script.library.chat;
import script.library.ai_lib;
import script.library.utils;
import script.library.money;
import script.library.colors;
import script.library.features;
import script.library.groundquests;

public class newbie extends script.theme_park.newbie_tutorial.tutorial_base
{
    public newbie()
    {
    }
    public static final String questNewbieHall01 = "c_newbie_hall_01";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (features.isSpaceEdition(self))
        {
            setObjVar(self, "jtlNewbie", 4);
        }
        obj_id bldg = getTopMostContainer(self);
        setObjVar(bldg, NEWBIE_PLAYER, self);
        newbieTutorialRequest(self, "clientReady");
        newbieTutorialRequest(self, "openStatMigration");
        deleteInventory(self);
        fillbank(self);
        grantNewbieStartingMoney(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (features.isSpaceEdition(self))
        {
            setObjVar(self, "jtlNewbie", 4);
        }
        location loc = getLocation(self);
        String area = loc.area;
        if (area.equals("tutorial"))
        {
            obj_id bldg = getTopMostContainer(self);
            if (bldg != null)
            {
                setObjVar(bldg, NEWBIE_PLAYER, self);
            }
            if (hasObjVar(self, "newbie.trained"))
            {
                String skillName = getStringObjVar(self, "newbie.hasSkill");
                if (skillName != null)
                {
                    if (!hasSkill(self, skillName))
                    {
                        grantSkill(self, skillName);
                    }
                }
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void beginTutorial(obj_id self) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        if (!groundquests.isQuestActiveOrComplete(self, questNewbieHall01))
        {
            groundquests.grantQuest(self, questNewbieHall01, false);
        }
        messageTo(self, "handleWelcome", null, 5, false);
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "newbie.hasReleaseDocuments"))
        {
            obj_id wayp = getObjIdObjVar(self, "newbie.hasReleaseDocuments");
            destroyWaypointInDatapad(wayp, self);
        }
        sendStartingMessage(self);
        String skillName = getStringObjVar(self, "newbie.hasSkill");
        if (skillName != null)
        {
            if (!hasSkill(self, skillName))
            {
                grantSkill(self, skillName);
            }
        }
        removeObjVar(self, "newbie");
        transferBankToInventory(self);
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
        contents = getContents(self);
        for (int i = 0; i < contents.length; i++)
        {
            if (hasObjVar(contents[i], "newbie.item"))
            {
                destroyObject(contents[i]);
            }
        }
        removeObjVar(self, "banking_bankid");
        for (int i = 0; i < BOX_CONTENTS.length; i++)
        {
            createObject(BOX_CONTENTS[i], playerInv, "");
        }
        removeObjVar(self, loot.VAR_DENY_LOOT);
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
        setObjVar(self, "comingFromTutorial", 1);
        grantAllNoviceSkills(self);
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", self);
        obj_id bldg = getTopMostContainer(self);
        obj_id player = getObjIdObjVar(bldg, NEWBIE_PLAYER);
        if (player == null)
        {
            setObjVar(bldg, NEWBIE_PLAYER, self);
            player = self;
        }
        if (isInRoom(self, "r1"))
        {
            return SCRIPT_CONTINUE;
        }
        if (isInRoom(self, "r2"))
        {
            flagRoomComplete(self, "r1");
            obj_id greeter = getObjIdObjVar(bldg, GREETER);
            faceToBehavior(greeter, self);
            messageTo(greeter, "initiateConvo", null, 2, false);
            return SCRIPT_CONTINUE;
        }
        if (isInRoom(self, "r3"))
        {
            flagRoomComplete(self, "r2");
            initiateCommerceRoom(self);
            return SCRIPT_CONTINUE;
        }
        if (isInRoom(self, "r4"))
        {
            flagRoomComplete(self, "r3");
            initiateCloneRoom(self);
            return SCRIPT_CONTINUE;
        }
        if (isInRoom(self, "r5"))
        {
            flagRoomComplete(self, "r4");
            initiatePromptToClone(self);
            return SCRIPT_CONTINUE;
        }
        if (isInRoom(self, "r6"))
        {
            flagRoomComplete(self, "r5");
            doRoomSixPrompt(self);
            return SCRIPT_CONTINUE;
        }
        if (isInRoom(self, "r7"))
        {
            initiatePromptForCombat(self);
            return SCRIPT_CONTINUE;
        }
        if (isInRoom(self, "r8"))
        {
            if (hasObjVar(self, "newbie.hasWeapon"))
            {
                flagRoomComplete(self, "r7");
            }
            if (!hasObjVar(self, "newbie.killedPirate"))
            {
                initiatePirateAttack(self, bldg);
            }
            return SCRIPT_CONTINUE;
        }
        if (isInRoom(self, "r9"))
        {
            messageTo(self, "moveOnToNextRoom", null, 1, false);
            initiateTrainerExplanation(self);
            return SCRIPT_CONTINUE;
        }
        if (isInRoom(self, "r10"))
        {
            flagRoomComplete(self, "r9");
            initiateMissionExplanation(self);
            return SCRIPT_CONTINUE;
        }
        if (isInRoom(self, "r11"))
        {
            flagRoomComplete(self, "r10");
            initiateTravelExplanation(self);
            return SCRIPT_CONTINUE;
        }
        if (isInRoom(self, "r14"))
        {
            handlePromptToNextRoom(self);
            flagRoomComplete(self, "r14");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNewbieTutorialResponse(obj_id self, String action) throws InterruptedException
    {
        if (action.equals("clientReady"))
        {
            location loc = getLocation(self);
            String area = loc.area;
            if (area.equals("tutorial"))
            {
                beginTutorial(self);
            }
            else 
            {
                utils.setScriptVar(self, "newbie.hasExitedTutorial", true);
            }
        }
        else if (action.equals("zoomCamera"))
        {
            if (utils.hasScriptVar(self, "newbie.waitingForCameraZoom"))
            {
                utils.removeScriptVar(self, "newbie.waitingForCameraZoom");
                messageTo(self, "handleChatWindow", null, 10.0f, false);
            }
        }
        else if (action.equals("openCharacterSheet"))
        {
            utils.removeScriptVar(self, "newbie.waitingForcharacterSheet");
            messageTo(self, "handleStatusScreenInfo", null, 1, false);
        }
        else if (action.equals("closeCharacterSheet"))
        {
            utils.removeScriptVar(self, "newbie.waitingForcharacterSheetClose");
        }
        else if (action.equals("openInventory"))
        {
            if (utils.hasScriptVar(self, "newbie.waitingForInventoryOpen"))
            {
                if (isInRoom(self, "r2"))
                {
                    utils.removeScriptVar(self, "newbie.waitingForInventoryOpen");
                    messageTo(self, "handleToolbarPrompt", null, 6, false);
                    newbieTutorialRequest(self, "closeInventory");
                }
                utils.removeScriptVar(self, "newbie.waitingForInventoryOpen");
            }
        }
        else if (action.equals("closeInventory"))
        {
            utils.setScriptVar(self, "newbie.skipCloseInventory", true);
            if (utils.hasScriptVar(self, "newbie.waitingForInventoryClose"))
            {
                messageTo(self, "handlePromptToCommerceRoom", null, 5, false);
            }
            utils.removeScriptVar(self, "newbie.waitingForInventoryClose");
        }
        else if (action.equals("changeMouseMode"))
        {
        }
        else if (action.equals("openStatMigration"))
        {
        }
        else if (action.equals("closeHolocron"))
        {
            messageTo(self, "handleMoveToItemRoomPrompt", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleWelcome(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        setObjVar(bldg, "newbie.player", self);
        messageTo(self, "handleMoveKeys", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int handleMoveKeys(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r1"))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleMoveMouse", null, 7, false);
        return SCRIPT_CONTINUE;
    }
    public int handleMoveMouse(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r1"))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "moveOnToNextRoom", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleMouseWheel(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r1"))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "newbie.waitingForCameraZoom", true);
        messageTo(self, "handleNoMouseWheelOrKeypad", null, MEDIUM_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int handleNoMouseWheelOrKeypad(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r1"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.waitingForCameraZoom"))
        {
            utils.removeScriptVar(self, "newbie.waitingForCameraZoom");
            messageTo(self, "handleChatWindow", null, LONG_DELAY, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePromptForCameraZoom(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r1"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.waitingForCameraZoom"))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChatWindow(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r1"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.alreadyChatted"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            utils.setScriptVar(self, "newbie.alreadyChatted", true);
        }
        utils.setScriptVar(self, "newbie.promptedToChat", true);
        return SCRIPT_CONTINUE;
    }
    public int handleRepeatChatPrompt(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r1"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "newbie.promptedToChat"))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "newbie.promptedToChat", true);
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "newbie.promptedToChat"))
        {
            utils.removeScriptVar(self, "newbie.promptedToChat");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleOpencharacterSheetWindow(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r1"))
        {
            return SCRIPT_CONTINUE;
        }
        newbieTutorialRequest(self, "openCharacterSheet");
        utils.setScriptVar(self, "newbie.waitingForcharacterSheet", true);
        messageTo(self, "handleRepeatOpencharacterSheet", null, VERY_LONG_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int handleRepeatOpencharacterSheet(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r1"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.waitingForcharacterSheet"))
        {
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRepeatClosecharacterSheet(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r1"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.waitingForcharacterSheetClose"))
        {
        }
        return SCRIPT_CONTINUE;
    }
    public int handleStatusScreenInfo(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r1"))
        {
            return SCRIPT_CONTINUE;
        }
        newbieTutorialRequest(self, "closeCharacterSheet");
        utils.setScriptVar(self, "newbie.waitingForcharacterSheetClose", true);
        messageTo(self, "handleRepeatClosecharacterSheet", null, VERY_LONG_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int handleMoveToItemRoomPrompt(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r1"))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, "newbie.promptedToChat");
        obj_id greeter = getObjIdObjVar(getTopMostContainer(self), ROOM1_GREETER);
        if (greeter != null)
        {
            messageTo(greeter, "handleNewArrival", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRepeatItemRoomPrompt(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r1"))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleExplainFreemouse(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r2"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.handleExplainFreemouse"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            utils.setScriptVar(self, "newbie.handleExplainFreemouse", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleExplainInventory(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r2"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.handleExplainInventory"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            utils.setScriptVar(self, "newbie.handleExplainInventory", true);
        }
        messageTo(self, "handleOpenInventory", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleOpenInventory(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "newbie.waitingForInventoryOpen"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isInRoom(self, "r2"))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleToolbarPrompt(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r2"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.handleToolbarPromp"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            utils.setScriptVar(self, "newbie.handleToolbarPromp", true);
        }
        messageTo(self, "handleReadyItemPrompt", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleReadyItemPrompt(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r2"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.handleReadyItemPrompt"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            utils.setScriptVar(self, "newbie.handleReadyItemPrompt", true);
        }
        messageTo(self, "handleUseToolbarPrompt", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int handleUseToolbarPrompt(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r2"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.handleUseToolbarPrompt"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            utils.setScriptVar(self, "newbie.handleUseToolbarPrompt", true);
        }
        if (!utils.hasScriptVar(self, "newbie.skipCloseInventory"))
        {
            utils.setScriptVar(self, "newbie.waitingForInventoryClose", true);
            messageTo(self, "handlePromptToCloseInventory", null, LONG_DELAY, false);
        }
        else 
        {
            messageTo(self, "handlePromptToCommerceRoom", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePromptToCloseInventory(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r2"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "newbie.waitingForInventoryClose"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "newbie.skipCloseInventory"))
        {
            messageTo(self, "handleInventoryAlreadyClosed", null, VERY_LONG_DELAY, false);
        }
        else 
        {
            messageTo(self, "handleInventoryAlreadyClosed", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleInventoryAlreadyClosed(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "newbie.waitingForInventoryClose"))
        {
            utils.removeScriptVar(self, "newbie.waitingForInventoryClose");
        }
        messageTo(self, "handlePromptToCommerceRoom", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int handlePromptToCommerceRoom(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r2"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.promptedToRoom"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            utils.setScriptVar(self, "newbie.promptedToRoom", true);
        }
        messageTo(self, "handleRepeatPromptToCommerceRoom", null, VERY_LONG_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int handleRepeatPromptToCommerceRoom(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r2"))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void initiateCommerceRoom(obj_id player) throws InterruptedException
    {
        obj_id bldg = getBuilding(player);
        obj_id bank = getObjIdObjVar(bldg, BANK);
        flyText(bank, "bank_flytext", 1.5f, colors.YELLOW);
        if (!utils.hasScriptVar(player, "newbie.bankAndBazaarSpamStarted"))
        {
            messageTo(player, "handleBankAndBazaarFlyText", null, 5, false);
            utils.setScriptVar(player, "newbie.bankAndBazaarSpamStarted", true);
        }
        obj_id banker = getObjIdObjVar(bldg, BANKER);
        faceToBehavior(banker, player);
        messageTo(banker, "handleInitiateConvo", null, 2, false);
        makeStaticWaypoint(banker);
    }
    public int handleBankAndBazaarFlyText(obj_id self, dictionary params) throws InterruptedException
    {
        if (isInRoom(self, "r3"))
        {
            obj_id bldg = getBuilding(self);
            if (!utils.hasScriptVar(self, "newbie.usedBank"))
            {
                obj_id bank = getObjIdObjVar(bldg, BANK);
                flyText(bank, "bank_flytext", 1.5f, colors.YELLOW);
            }
            messageTo(self, "handleBankAndBazaarFlyText", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBankInfoSpam(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "newbie.usedBank", true);
        if (!isInRoom(self, "r3"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.bankSpamDone"))
        {
            return SCRIPT_CONTINUE;
        }
        int bankSpamNum = utils.getIntScriptVar(self, "newbie.bankSpam");
        bankSpamNum++;
        if (bankSpamNum == 1)
        {
            utils.setScriptVar(self, "newbie.bankSpam", bankSpamNum);
            messageTo(self, "handleBankInfoSpam", null, 25, false);
        }
        else if (bankSpamNum == 2)
        {
            utils.setScriptVar(self, "newbie.bankSpamDone", true);
            utils.removeScriptVar(self, "newbie.bankSpam");
            messageTo(self, "moveOnToNextRoom", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBazaarInfoSpam(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "newbie.usedBazaar", true);
        if (!isInRoom(self, "r3"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.bazaarSpamDone"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.bankSpam"))
        {
            utils.setScriptVar(self, "newbie.bazaarSpamPending", true);
            return SCRIPT_CONTINUE;
        }
        int bazaarSpamNum = utils.getIntScriptVar(self, "newbie.bazaarSpam");
        bazaarSpamNum++;
        switch (bazaarSpamNum)
        {
            case 1:
            break;
            case 2:
            break;
            case 3:
            utils.setScriptVar(self, "newbie.bazaarSpamDone", true);
            utils.removeScriptVar(self, "newbie.bazaarSpam");
            if (utils.hasScriptVar(self, "newbie.bankSpamPending"))
            {
                messageTo(self, "handleBankInfoSpam", null, MEDIUM_DELAY, false);
            }
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "newbie.bazaarSpam", bazaarSpamNum);
        messageTo(self, "handleBazaarInfoSpam", null, SHORT_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public void initiateCloneRoom(obj_id player) throws InterruptedException
    {
        obj_id cloneNpc = getObjIdObjVar(getBuilding(player), CLONE_NPC);
        messageTo(cloneNpc, "handleInitiateConvo", null, SHORT_DELAY, false);
    }
    public void initiatePromptToClone(obj_id player) throws InterruptedException
    {
        messageTo(player, "handleCloneAndInsureFlyText", null, 1, false);
        obj_id cloneNpc = getObjIdObjVar(getBuilding(player), CLONE_NPC);
        messageTo(cloneNpc, "handleInitiateClonePrompt", null, 2, false);
        obj_id terminal = getObjIdObjVar(getTopMostContainer(player), CLONE_TERMINAL);
        makeStaticWaypoint(terminal);
    }
    public int handleCloneAndInsureFlyText(obj_id self, dictionary params) throws InterruptedException
    {
        if (isInRoom(self, "r5"))
        {
            obj_id bldg = getBuilding(self);
            if (!utils.hasScriptVar(self, "newbie.usedCloneTerm"))
            {
                obj_id bazaar = getObjIdObjVar(bldg, CLONE_TERMINAL);
                flyText(bazaar, "clone_here", 1.5f, colors.YELLOW);
            }
            if (!utils.hasScriptVar(self, "newbie.usedInsureTerm"))
            {
                obj_id bank = getObjIdObjVar(bldg, INSURANCE);
                flyText(bank, "insure_here", 1.5f, colors.YELLOW);
            }
            messageTo(self, "handleCloneAndInsureFlyText", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePromptToInsure(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "newbie.insuredStuff"))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCloneInfoSpam(obj_id self, dictionary params) throws InterruptedException
    {
        removeStaticWaypoint(self);
        obj_id terminal = getObjIdObjVar(getTopMostContainer(self), INSURANCE);
        makeStaticWaypoint(terminal);
        messageTo(self, "handlePromptToInsure", null, SHORT_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int handleInsuranceInfoSpam(obj_id self, dictionary params) throws InterruptedException
    {
        removeStaticWaypoint(self);
        setObjVar(self, "newbie.insuredStuff", true);
        messageTo(self, "moveOnToNextRoom", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public void doRoomSixPrompt(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "newbie.panicInitiated"))
        {
            return;
        }
        else 
        {
            utils.setScriptVar(player, "newbie.panicInitiated", true);
        }
        obj_id bldg = getTopMostContainer(player);
        obj_id panicGuy = getObjIdObjVar(bldg, PANIC_GUY);
        if (!isIdValid(panicGuy))
        {
            return;
        }
        playClientEffectObj(player, "clienteffect/combat_explosion_lair_large.cef", panicGuy, "");
        messageTo(panicGuy, "initiatePanic", null, 2, false);
        messageTo(player, "handleRadar", null, 5, false);
    }
    public int handleSeeWhatIsGoingOn(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void initiatePromptForCombat(obj_id player) throws InterruptedException
    {
        if (!isInRoom(player, "r7"))
        {
            return;
        }
        obj_id bldg = getTopMostContainer(player);
        obj_id combatExplainer = getObjIdObjVar(bldg, COMBAT_EXPLAINER);
        makeStaticWaypoint(combatExplainer);
        messageTo(combatExplainer, "handlePlayerArrival", null, 3, false);
    }
    public int handleExplainCombatToolbar(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void handlePromptToNextRoom(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "newbie.killedPirate"))
        {
            obj_id pirate = getObjIdObjVar(getTopMostContainer(self), PIRATE);
            makeStaticWaypoint(pirate);
            messageTo(self, "handleExplainCombatToolbar", null, SHORT_DELAY, false);
        }
    }
    public int handleRadar(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r8"))
        {
            messageTo(self, "handleRadarMore", null, SHORT_DELAY, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRadarMore(obj_id self, dictionary params) throws InterruptedException
    {
        if (isInRoom(self, "r8"))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleZoomMap", null, MEDIUM_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int handleZoomMap(obj_id self, dictionary params) throws InterruptedException
    {
        if (isInRoom(self, "r8"))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void initiatePirateAttack(obj_id player, obj_id bldg) throws InterruptedException
    {
        obj_id pirate = getObjIdObjVar(bldg, PIRATE);
        if (!exists(pirate))
        {
            return;
        }
        if (ai_lib.isAiDead(pirate))
        {
            return;
        }
        dictionary parms = new dictionary();
        parms.put("player", player);
        messageTo(pirate, "handlePlayerInRoom", parms, 2, false);
        messageTo(player, "handleAttackingDescription", null, LONG_DELAY, false);
    }
    public int handleAttackingDescription(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleCongrats(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void initiateTrainerExplanation(obj_id player) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(player);
        obj_id officer = getObjIdObjVar(bldg, OFFICER);
        if (officer == null)
        {
            return;
        }
        makeStaticWaypoint(officer);
        faceToBehavior(officer, player);
        messageTo(officer, "initiateConvo", null, 2, false);
        for (int i = 0; i < CELEB_GUY_X.length; i++)
        {
            obj_id celeb = getObjIdObjVar(bldg, CELEB_GUY + i);
            if (isIdValid(celeb))
            {
                messageTo(celeb, "handlePlayerEnteredRoom", null, rand(2, 4), false);
            }
        }
    }
    public int handleShowCharSheet(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "handlePromptToMissionRoom", null, VERY_LONG_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int handlePromptToMissionRoom(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleTrainerDoneTalking(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "newbie.trainerIsTalking");
        return SCRIPT_CONTINUE;
    }
    public void initiateMissionExplanation(obj_id player) throws InterruptedException
    {
        if (!isInRoom(player, "r10"))
        {
            return;
        }
        messageTo(player, "handleMissionExplaination", null, 1, false);
    }
    public int handleMissionExplaination(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r10"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "newbie.trainerIsTalking"))
        {
            messageTo(self, "handleExplainNpcMissions", null, 9, false);
        }
        else 
        {
            messageTo(self, "handleMissionExplaination", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleExplainNpcMissions(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r10"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "newbie.doneTalking"))
        {
            obj_id bldg = getTopMostContainer(self);
            obj_id missionNpc = getObjIdObjVar(bldg, MISSION_NPC);
            makeStaticWaypoint(missionNpc);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleGetTravelWaypoint(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "newbie.hasReleaseDocuments"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id travelNpc = getObjIdObjVar(getTopMostContainer(self), TRAVEL_NPC);
        if (travelNpc != null)
        {
            obj_id wayp = createWaypointInDatapad(self, travelNpc);
            if (isIdValid(wayp))
            {
                setWaypointName(wayp, getString(new string_id(NEWBIE_STRING_FILE, "release_docs")));
                setWaypointActive(wayp, true);
                setWaypointVisible(wayp, true);
                setObjVar(self, "newbie.hasReleaseDocuments", wayp);
            }
            makeStaticWaypoint(travelNpc);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleExplainWaypoint(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void initiateTravelExplanation(obj_id player) throws InterruptedException
    {
        obj_id travelNpc = getObjIdObjVar(getTopMostContainer(player), TRAVEL_NPC);
        if (travelNpc != null)
        {
            messageTo(travelNpc, "handleInitiateDialog", null, 0, false);
        }
    }
    public int handleStartOath(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "newbie.takingOath", true);
        messageTo(self, "handlePromptForPromise", null, SHORT_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int handlePromptForPromise(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "newbie.oathCompleted"))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "newbie.takingOath", true);
        if (!utils.hasScriptVar(self, "newbie.repromptSent"))
        {
            messageTo(self, "handleRePromptForPromise", null, LONG_DELAY, false);
            utils.setScriptVar(self, "newbie.repromptSent", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRePromptForPromise(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInRoom(self, "r11"))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "newbie.oathCompleted"))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleRePromptForPromise", null, LONG_DELAY, false);
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
    public int handleEndTutorial(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "newbie.hasExitedTutorial"))
        {
            detachScript(self, "theme_park.newbie_tutorial.newbie");
        }
        else 
        {
            messageTo(self, "handleEndTutorial", null, 10, false);
        }
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
    public int OnLogin(obj_id self) throws InterruptedException
    {
        location loc = getLocation(self);
        String area = loc.area;
        if (!area.equals("tutorial"))
        {
            detachScript(self, "theme_park.newbie_tutorial.newbie");
        }
        return SCRIPT_CONTINUE;
    }
    public int moveOnToNextRoom(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
