package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.combat;
import script.library.sui;
import script.library.quests;
import script.library.ai_lib;
import script.library.money;

public class msivertson_test extends script.base_script
{
    public msivertson_test()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.msivertson_test");
        }
        return SCRIPT_CONTINUE;
    }
    public void maxStats(obj_id objPlayer) throws InterruptedException
    {
        addAttribModifier(objPlayer, HEALTH, 2000, 0, 0, MOD_POOL);
        addAttribModifier(objPlayer, ACTION, 2000, 0, 0, MOD_POOL);
        addAttribModifier(objPlayer, MIND, 2000, 0, 0, MOD_POOL);
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
        if (tok.hasMoreTokens())
        {
            String command = tok.nextToken();
            debugConsoleMsg(self, "command is: " + command);
            if (command.equals("ms_setSkillMod"))
            {
                String mod = tok.nextToken();
                String amountStr = tok.nextToken();
                int amount = Integer.parseInt(amountStr);
                applySkillStatisticModifier(self, mod, amount);
            }
            else if (command.equals("ms_fillContainer"))
            {
                String amountStr = tok.nextToken();
                int amount = Integer.parseInt(amountStr);
                obj_id target = getLookAtTarget(self);
                for (int i = 0; i < amount; ++i)
                {
                    createObject("object/tangible/food/fruit_melon.iff", target, "");
                }
            }
            else if (command.equals("ms_setVendorSlotsUsed"))
            {
                String amountStr = tok.nextToken();
                int amount = Integer.parseInt(amountStr);
                setObjVar(self, "used_vendor_slots", amount);
            }
            else if (command.equals("ms_logBalance"))
            {
                String comment = tok.nextToken();
                logBalance(comment);
            }
            else if (command.equals("ms_maxStats"))
            {
                maxStats(self);
            }
            else if (command.equals("ms_money"))
            {
                StringBuffer output = new StringBuffer();
                if (tok.hasMoreTokens())
                {
                    String amountStr = tok.nextToken();
                    int amount = Integer.parseInt(amountStr);
                    if (amount > 0)
                    {
                        money.bankTo(money.ACCT_CHARACTER_CREATION, self, amount);
                    }
                    else 
                    {
                        money.bankTo(self, money.ACCT_CHARACTER_CREATION, -amount);
                    }
                }
            }
            else if (command.equals("ms_ownVendor"))
            {
                obj_id target = getLookAtTarget(self);
                if (target != null)
                {
                    createVendorMarket(self, target, 0);
                }
            }
            else if (command.equals("ms_valueVendor"))
            {
                obj_id target = getLookAtTarget(self);
                if (target != null)
                {
                    updateVendorValue(target);
                }
            }
            else if (command.equals("ms_createRoomPrivate"))
            {
                String name = tok.nextToken();
                String title = tok.nextToken();
                chatCreateRoom(false, name, title);
            }
            else if (command.equals("ms_createRoomPublic"))
            {
                String name = tok.nextToken();
                String title = tok.nextToken();
                chatCreateRoom(true, name, title);
            }
            else if (command.equals("ms_joinRoom"))
            {
                String name = tok.nextToken();
                chatEnterRoom(name);
            }
            else if (command.equals("ms_leaveRoom"))
            {
                String name = tok.nextToken();
                chatExitRoom(name);
            }
            else if (command.equals("ms_speak"))
            {
                String avatarName = getChatName(self);
                String roomName = tok.nextToken();
                String msg = tok.nextToken();
                String oob = new String();
                chatSendToRoom(roomName, msg, oob);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
