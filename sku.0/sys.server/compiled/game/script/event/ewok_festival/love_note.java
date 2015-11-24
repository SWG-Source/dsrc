package script.event.ewok_festival;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.groundquests;
import script.library.prose;
import script.library.sui;
import script.library.utils;

public class love_note extends script.base_script
{
    public love_note()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int msgLoveNoteSuggestionStart(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String title = getString(new string_id("event/love_day", "love_note_title"));
        String prompt = getString(new string_id("event/love_day", "love_note_01"));
        int pid = sui.inputbox(self, player, prompt, sui.OK_CANCEL, title, sui.INPUT_NORMAL, null, "msgLoveNoteSuggestionResponse");
        if (pid > -1)
        {
            sui.showSUIPage(pid);
            utils.setScriptVar(player, "love_note.pid", pid);
            utils.setScriptVar(player, "love_note.phraseNum", 1);
        }
        return SCRIPT_CONTINUE;
    }
    public void areaDebugMessaging(obj_id self, String message) throws InterruptedException
    {
        obj_id[] players = getAllPlayers(getLocation(getTopMostContainer(self)), 35.0f);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                sendSystemMessage(players[i], message, "");
            }
        }
    }
    public int msgLoveNoteSuggestionResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        String suggestion = sui.getInputBoxText(params);
        int phraseNum = utils.getIntScriptVar(player, "love_note.phraseNum");
        if (btn == sui.BP_CANCEL)
        {
            utils.removeScriptVarTree(player, "love_note");
            return SCRIPT_CONTINUE;
        }
        if ((toLower(suggestion)).equals("love"))
        {
            string_id message = new string_id("event/love_day", "love_note_correct_0" + phraseNum);
            prose_package pp = prose.getPackage(message, player, player);
            prose.setTO(pp, getName(player));
            sendSystemMessageProse(player, pp);
            if (phraseNum >= 5)
            {
                groundquests.sendSignal(player, "loveday_disillusion_love_note_03");
                utils.removeScriptVarTree(player, "love_note");
            }
            else 
            {
                int nextPhrase = phraseNum + 1;
                String title = getString(new string_id("event/love_day", "love_note_title"));
                String prompt = getString(new string_id("event/love_day", "love_note_0" + nextPhrase));
                int pid = sui.inputbox(self, player, prompt, sui.OK_CANCEL, title, sui.INPUT_NORMAL, null, "msgLoveNoteSuggestionResponse");
                if (pid > -1)
                {
                    sui.showSUIPage(pid);
                    utils.setScriptVar(player, "love_note.pid", pid);
                    utils.setScriptVar(player, "love_note.phraseNum", nextPhrase);
                }
            }
        }
        else 
        {
            string_id message = new string_id("event/love_day", "love_note_incorrect_0" + rand(1, 9));
            prose_package pp = prose.getPackage(message, player, player);
            prose.setTO(pp, getName(player));
            sendSystemMessageProse(player, pp);
            utils.removeScriptVarTree(player, "love_note");
        }
        return SCRIPT_CONTINUE;
    }
}
