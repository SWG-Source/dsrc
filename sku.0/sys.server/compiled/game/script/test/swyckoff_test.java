package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.groundquests;
import script.library.pclib;
import script.library.skill;
import script.library.space_quest;
import script.library.space_utils;
import script.library.static_item;
import script.library.utils;
import script.library.weapons;
import script.library.chat;
import script.library.npe;
import script.library.xp;
import script.library.dump;
import script.library.buff;

public class swyckoff_test extends script.base.remote_object_requester
{
    public swyckoff_test()
    {
    }
    public static final String FINISH_PLANET = "tatooine";
    public static final float FINISH_X = 3528.0f;
    public static final float FINISH_Z = -4804.0f;
    public static final String MINIGAME_PARAM_RESULT = "result";
    public static final String MINIGAME_VARNAME_GAME = "game";
    public static final String MINIGAME_VARNAME_SCORE = "score";
    public static final String MINIGAME_VARNAME_TABLE = "table";
    public static final String MINIGAME_VARNAME_PLAYER = "player";
    public static final String MINIGAME_GAMENAME_MAHJONG = "mahjong";
    public static final String MINIGAME_VARNAME_MAHJONG_LAYOUT = "layout";
    public static final String[] COMMAND_LIST = 
    {
        "sw_remove_mods",
        "sw_kill",
        "sw_buff_apply",
        "sw_buff_remove",
        "sw_buff_list",
        "sw_scriptvar_set",
        "sw_scriptvar_list",
        "sw_scriptvar_remove",
        "sw_garbage_collect",
        "sw_objid_isvalid",
        "sw_mahjong"
    };
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (true)
        {
            java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
            if (tok.hasMoreTokens())
            {
                String command = tok.nextToken();
                if (command.equalsIgnoreCase("sw_list_commands"))
                {
                    String outstring = "commands possible:";
                    for (int i = 0; i < COMMAND_LIST.length; i++)
                    {
                        outstring += " " + COMMAND_LIST[i];
                    }
                    debugConsoleMsg(self, outstring);
                }
                if (command.equalsIgnoreCase("sw_remove_mods"))
                {
                    debugConsoleMsg(self, "hit sw_remove_mods");
                    obj_id oid = self;
                    if (tok.hasMoreTokens())
                    {
                        oid = utils.stringToObjId(tok.nextToken());
                    }
                    removeAllAttributeAndSkillmodMods(oid);
                    debugConsoleMsg(self, "removing mods from OID " + oid);
                    debugConsoleMsg(oid, "mods removed by " + self);
                }
                if (command.equalsIgnoreCase("sw_kill"))
                {
                    debugConsoleMsg(self, "hit sw_kill");
                    obj_id oid = self;
                    if (tok.hasMoreTokens())
                    {
                        oid = utils.stringToObjId(tok.nextToken());
                    }
                    kill(oid);
                    debugConsoleMsg(self, "killing OID " + oid);
                    debugConsoleMsg(oid, "killed by " + self);
                }
                if (command.equalsIgnoreCase("sw_buff_apply"))
                {
                    debugConsoleMsg(self, "hit sw_buff_apply");
                    obj_id oid = self;
                    if (tok.hasMoreTokens())
                    {
                        String buffname = tok.nextToken();
                        if (tok.hasMoreTokens())
                        {
                            oid = utils.stringToObjId(tok.nextToken());
                        }
                        if (buff.applyBuff(oid, buffname))
                        {
                            debugConsoleMsg(self, "buff " + buffname + " applied to " + oid);
                            debugConsoleMsg(oid, "buff " + buffname + " applied by " + self);
                        }
                        else 
                        {
                            debugConsoleMsg(self, "failed to apply buff " + buffname + " to " + oid);
                        }
                    }
                    else 
                    {
                        debugConsoleMsg(self, "usage: sw_buff_apply <buffname> [target oid]");
                    }
                }
                if (command.equalsIgnoreCase("sw_buff_remove"))
                {
                    debugConsoleMsg(self, "hit sw_buff_remove");
                    obj_id oid = self;
                    if (tok.hasMoreTokens())
                    {
                        String buffname = tok.nextToken();
                        if (tok.hasMoreTokens())
                        {
                            oid = utils.stringToObjId(tok.nextToken());
                        }
                        if (buff.removeBuff(oid, buffname))
                        {
                            debugConsoleMsg(self, "buff " + buffname + " removed from " + oid);
                            debugConsoleMsg(oid, "buff " + buffname + " removed by " + self);
                        }
                        else 
                        {
                            debugConsoleMsg(self, "failed to remove buff " + buffname + " to " + oid);
                        }
                    }
                    else 
                    {
                        debugConsoleMsg(self, "usage: sw_buff_remove <buffname> [target oid]");
                    }
                }
                if (command.equalsIgnoreCase("sw_buff_list"))
                {
                    debugConsoleMsg(self, "hit sw_buff_list");
                    obj_id oid = self;
                    if (tok.hasMoreTokens())
                    {
                        oid = utils.stringToObjId(tok.nextToken());
                    }
                    int[] buffcrcs = buff.getAllBuffs(oid);
                    String outstring = "Buffs on " + oid;
                    for (int i = 0; i < buffcrcs.length; i++)
                    {
                        outstring += " " + buff.getBuffNameFromCrc(buffcrcs[i]);
                    }
                    debugConsoleMsg(self, outstring);
                }
                if (command.equalsIgnoreCase("sw_scriptvar_set"))
                {
                    debugConsoleMsg(self, "hit sw_scriptvar_set");
                    obj_id oid = self;
                    String scriptvarname = "test.swyckoff";
                    int value = 1;
                    if (tok.hasMoreTokens())
                    {
                        oid = utils.stringToObjId(tok.nextToken());
                    }
                    if (tok.hasMoreTokens())
                    {
                        scriptvarname = tok.nextToken();
                    }
                    if (tok.hasMoreTokens())
                    {
                        value = utils.stringToInt(tok.nextToken());
                    }
                    utils.setScriptVar(oid, scriptvarname, value);
                }
                if (command.equalsIgnoreCase("sw_scriptvar_list"))
                {
                    debugConsoleMsg(self, "hit sw_scriptvar_list");
                    obj_id oid = self;
                    if (tok.hasMoreTokens())
                    {
                        oid = utils.stringToObjId(tok.nextToken());
                    }
                    debugConsoleMsg(self, "showScriptVars: target = " + oid);
                    deltadictionary delta = oid.getScriptVars();
                    if (delta == null)
                    {
                        debugConsoleMsg(self, "NONE!");
                    }
                    else 
                    {
                        debugConsoleMsg(self, "dd = " + delta.toString());
                    }
                }
                if (command.equalsIgnoreCase("sw_scriptvar_remove"))
                {
                    debugConsoleMsg(self, "hit sw_test_scriptvar_remove");
                    obj_id oid = self;
                    String scriptvarname = "test.swyckoff";
                    int value = 1;
                    if (tok.hasMoreTokens())
                    {
                        scriptvarname = tok.nextToken();
                    }
                    if (tok.hasMoreTokens())
                    {
                        value = utils.stringToInt(tok.nextToken());
                    }
                    utils.removeScriptVar(oid, scriptvarname);
                }
                if (command.equalsIgnoreCase("sw_garbage_collect"))
                {
                    debugConsoleMsg(self, "hit sw_garbage_collect");
                    System.gc();
                }
                if (command.equalsIgnoreCase("sw_objid_isvalid"))
                {
                    debugConsoleMsg(self, "hit sw_objid_isvalid");
                    if (tok.hasMoreTokens())
                    {
                        obj_id oid = utils.stringToObjId(tok.nextToken());
                        if (isIdValid(oid))
                        {
                            debugConsoleMsg(self, "the obj_id " + oid + " is valid.");
                        }
                        else 
                        {
                            debugConsoleMsg(self, "the obj_id " + oid + " is NOT valid.");
                        }
                    }
                    else 
                    {
                        debugConsoleMsg(self, "requires an id to verify");
                    }
                }
                if (command.equalsIgnoreCase("sw_mahjong"))
                {
                    debugConsoleMsg(self, "hit sw_mahjong");
                    dictionary data = new dictionary();
                    data.put(MINIGAME_VARNAME_GAME, MINIGAME_GAMENAME_MAHJONG);
                    data.put(MINIGAME_VARNAME_TABLE, obj_id.NULL_ID);
                    if (tok.hasMoreTokens())
                    {
                        String actionType = tok.nextToken();
                        obj_id player = self;
                        if (tok.hasMoreTokens())
                        {
                            player = utils.stringToObjId(tok.nextToken());
                            if (isIdValid(player))
                            {
                                obj_id owneroid = null;
                                if (tok.hasMoreTokens())
                                {
                                    owneroid = utils.stringToObjId(tok.nextToken());
                                    data.put(MINIGAME_VARNAME_TABLE, owneroid);
                                }
                            }
                            else 
                            {
                                debugConsoleMsg(self, "target is not valid");
                            }
                        }
                        else 
                        {
                            if (clientMinigameOpen(self, data))
                            {
                                debugConsoleMsg(self, "no target, opened minigame on self");
                            }
                            else 
                            {
                                debugConsoleMsg(self, "no target, failed to open minigame on self");
                            }
                        }
                        if (isIdValid(player))
                        {
                            if (actionType.equalsIgnoreCase("open"))
                            {
                                if (clientMinigameOpen(player, data))
                                {
                                    debugConsoleMsg(self, "opened minigame on " + player);
                                }
                                else 
                                {
                                    debugConsoleMsg(self, "failed to open minigame on " + player);
                                }
                            }
                            else if (actionType.equalsIgnoreCase("close"))
                            {
                                clientMinigameClose(player, data);
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.swyckoff_test");
        }
        else {
            debugConsoleMsg(self, "attached swyckoff_test");
        }
        return SCRIPT_CONTINUE;
    }
    public int startPerform(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int stopPerform(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int clientMinigameResult(obj_id self, dictionary params) throws InterruptedException
    {
        debugConsoleMsg(self, "minigame params: " + params.toString());
        dictionary data = params.getDictionary(MINIGAME_PARAM_RESULT);
        int score = data.getInt(MINIGAME_VARNAME_SCORE);
        obj_id player = data.getObjId(MINIGAME_VARNAME_PLAYER);
        String layout = data.getString(MINIGAME_VARNAME_MAHJONG_LAYOUT);
        int dummy = data.getInt("missing");
        debugConsoleMsg(self, "minigame result: " + player + " score " + score + " layout " + layout);
        return SCRIPT_CONTINUE;
    }
}
