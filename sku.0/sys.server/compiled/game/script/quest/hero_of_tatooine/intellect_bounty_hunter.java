package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.badge;
import script.library.chat;
import script.library.groundquests;
import script.library.sui;
import script.library.utils;

public class intellect_bounty_hunter extends script.base_script
{
    public intellect_bounty_hunter()
    {
    }
    public static final String MARK_OF_INTELLECT = "object/tangible/loot/quest/hero_of_tatooine/mark_intellect.iff";
    public static final String SPAWNER_OBJVAR = "quest.hero_of_tatooine.intellect.spawner";
    public static final String SPAWNER_CONTROLLER = SPAWNER_OBJVAR + ".controller";
    public static final String SPAWNER_COMPLETE = SPAWNER_OBJVAR + ".complete";
    public static final String INTELLECT_OBJVAR = "quest.hero_of_tatooine.intellect";
    public static final String INTELLECT_COMPLETE = INTELLECT_OBJVAR + ".complete";
    public static final String INTELLECT_FAILED = INTELLECT_OBJVAR + ".failed";
    public static final string_id BH_WIN = new string_id("quest/hero_of_tatooine/intellect_liar", "bh_win");
    public static final string_id BH_LOSE = new string_id("quest/hero_of_tatooine/intellect_liar", "bh_lose");
    public static final string_id INTELLECT_INV_FULL = new string_id("quest/hero_of_tatooine/system_messages", "intellect_inv_full");
    public static final string_id NPC_NAME = new string_id("quest/hero_of_tatooine/npc_names", "tearfin");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "");
        setName(self, NPC_NAME);
        return SCRIPT_CONTINUE;
    }
    public int handleImplicationSui(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(player, "quest.hero_of_tatooine.intellect.liar"))
        {
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(self, "[TESTING] You don't have a script var identifying the correct liar. This is a bug. You automatically win!");
            }
            chat.publicChat(self, player, BH_WIN);
            puzzleSuccess(self, player);
            return SCRIPT_CONTINUE;
        }
        int liar = utils.getIntScriptVar(player, "quest.hero_of_tatooine.intellect.liar");
        if (idx == liar)
        {
            chat.publicChat(self, player, BH_WIN);
            puzzleSuccess(self, player);
        }
        else 
        {
            chat.chat(self, player, BH_LOSE, chat.ChatFlag_targetOnly);
            puzzleFailure(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void puzzleSuccess(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id mark_intellect = createObjectInInventoryAllowOverload(MARK_OF_INTELLECT, player);
        removeObjVar(player, INTELLECT_OBJVAR);
        if (!badge.hasBadge(player, "poi_twoliars"))
        {
            badge.grantBadge(player, "poi_twoliars");
            groundquests.sendSignal(player, "hero_of_tatooine_main_intellect");
            CustomerServiceLog("quest", "HERO OF TATOOINE - %TU has acquired the Mark of Intellect", player);
            setObjVar(player, INTELLECT_COMPLETE, 1);
        }
        if (!isIdValid(mark_intellect))
        {
            sendSystemMessage(player, INTELLECT_INV_FULL);
            setObjVar(player, "quest.hero_of_tatooine.owed.intellect", 1);
        }
        obj_id control = getObjIdObjVar(self, SPAWNER_CONTROLLER);
        if (!isIdValid(control))
        {
            return;
        }
        setObjVar(self, SPAWNER_COMPLETE, 1);
        messageTo(control, "handleComplete", null, 0.0f, false);
    }
    public void puzzleFailure(obj_id self, obj_id player) throws InterruptedException
    {
        removeObjVar(player, INTELLECT_OBJVAR);
        setObjVar(player, INTELLECT_FAILED, self);
    }
}
