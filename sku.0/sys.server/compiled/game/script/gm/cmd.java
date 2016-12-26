package script.gm;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.StringTokenizer;
import script.library.arena;
import script.library.badge;
import script.library.city;
import script.library.colors;
import script.library.craftinglib;
import script.library.create;
import script.library.dump;
import script.library.factions;
import script.library.force_rank;
import script.library.fs_counterstrike;
import script.library.fs_dyn_village;
import script.library.fs_quests;
import script.library.gm;
import script.library.hue;
import script.library.jedi_trials;
import script.library.loot;
import script.library.money;
import script.library.npe;
import script.library.quests;
import script.library.pclib;
import script.library.player_structure;
import script.library.ship_ai;
import script.library.skill;
import script.library.sui;
import script.library.target_dummy;
import script.library.trace;
import script.library.utils;
import script.library.veteran_deprecated;
import script.library.xp;
import script.library.weapons;
import script.library.static_item;

public class cmd extends script.base_script
{
    public cmd()
    {
    }
    public int cmdGetPlayerId(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /getPlayerId <player FIRST name>");
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(params);
        String name = st.nextToken();
        obj_id playerId = getPlayerIdFromFirstName(toLower(name));
        if (isIdValid(playerId))
        {
            sendSystemMessageTestingOnly(self, "getPlayerId: player '" + name + "' has object id: " + playerId);
        }
        else
        {
            sendSystemMessageTestingOnly(self, "getPlayerId: The system was unable to find a player named '" + name + "'");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdAiIgnore(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id ship = getPilotedShip(self);
        if (hasObjVar(self, "gm"))
        {
            removeObjVar(self, "gm");
            if (isIdValid(ship))
            {
                ship_ai.unitSetAutoAggroImmune(ship, false);
                ship_ai.unitSetDamageAggroImmune(ship, false);
            }
            sendSystemMessageTestingOnly(self, "aiIgnore[OFF]: AI will now aggro you as normal and fight back.");
        }
        else
        {
            setObjVar(self, "gm", 1);
            if (isIdValid(ship))
            {
                ship_ai.unitRemoveFromAllAttackTargetLists(ship);
                ship_ai.unitSetAutoAggroImmune(ship, true);
                ship_ai.unitSetDamageAggroImmune(ship, true);
            }
            sendSystemMessageTestingOnly(self, "aiIgnore[ON]: AI will no longer aggro you or fight back.");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdForceCommand(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target) || params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /forceCommand -target <command> <params> (with lookat target)");
            return SCRIPT_CONTINUE;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /forceCommand -target <command> <params> (with lookat target)");
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(params);
        String cmd = st.nextToken();
        obj_id cmdTarget = null;
        String cmdParams = "";
        while (st.hasMoreTokens())
        {
            String tmp = st.nextToken();
            if (tmp != null && !tmp.equalsIgnoreCase(""))
            {
                cmdParams += tmp + " ";
            }
        }
        cmdParams.trim();
        sendSystemMessageTestingOnly(self, "/forceCommand: attempting to queue command: '" + cmd + " " + cmdParams + "' for (" + target + ")" + getName(target));
        return SCRIPT_CONTINUE;
    }
    public int cmdMoney(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        dictionary d = gm.parseTarget(params, target, "CREDITS");
        if (d == null)
        {
            return SCRIPT_CONTINUE;
        }
        else if (d.isEmpty())
        {
        }
        else
        {
            params = d.getString("params");
            obj_id oid = d.getObjId("oid");
            if (isIdValid(oid))
            {
                target = oid;
            }
            else
            {
                target = self;
            }
        }
        if (params == null || params.equalsIgnoreCase(""))
        {
            showMoneySyntax(self);
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(toLower(params));
        String arg = st.nextToken();
        if (arg.equalsIgnoreCase("balance"))
        {
            gm.showMoneyStatus(self, target);
            return SCRIPT_CONTINUE;
        }
        else if (arg.equalsIgnoreCase("wipe"))
        {
            queueCommand(self, (2070221263), target, "cash 0", COMMAND_PRIORITY_DEFAULT);
            queueCommand(self, (2070221263), target, "bank 0", COMMAND_PRIORITY_DEFAULT);
            return SCRIPT_CONTINUE;
        }
        if (st.hasMoreTokens())
        {
            String sAmt = st.nextToken();
            if (arg.equalsIgnoreCase("cash"))
            {
                if (gm.setBalance(target, money.MT_CASH, sAmt))
                {
                    sendSystemMessageTestingOnly(self, "[CREDITS] Processing CASH update: (" + target + ")" + utils.getStringName(target) + " amt = " + sAmt);
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "[CREDITS] System rejected the CASH update: (" + target + ")" + utils.getStringName(target) + " amt = " + sAmt);
                }
                return SCRIPT_CONTINUE;
            }
            else if (arg.equalsIgnoreCase("bank"))
            {
                if (gm.setBalance(target, money.MT_BANK, sAmt))
                {
                    sendSystemMessageTestingOnly(self, "[CREDITS] Processing BANK update: (" + target + ")" + utils.getStringName(target) + " amt = " + sAmt);
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "[CREDITS] System rejected the BANK update: (" + target + ")" + utils.getStringName(target) + " amt = " + sAmt);
                }
                return SCRIPT_CONTINUE;
            }
        }
        showMoneySyntax(self);
        return SCRIPT_CONTINUE;
    }
    public void showMoneySyntax(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "[Syntax] /credits [-target]|[-id:<oid>] cash|bank (+|-)<amt>");
        sendSystemMessageTestingOnly(self, "[Syntax] /credits [-target]|[-id:<oid>] balance");
    }
    public int cmdBroadcast(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        queueCommand(self, (-351572629), target, params, COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int cmdBroadcastPlanet(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params != null && !params.equalsIgnoreCase(""))
        {
            sendSystemMessagePlanetTestingOnly(params);
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /broadcastPlanet <message>");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdBroadcastGalaxy(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params != null && !params.equalsIgnoreCase(""))
        {
            sendSystemMessageGalaxyTestingOnly(params);
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /broadcastGalaxy <message>");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdBroadcastArea(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        final float MAX_RANGE = 256.0f;
        final String KEYWORD_RANGE = "-range:";
        float range = 0f;
        String message = "";
        if (params.indexOf(KEYWORD_RANGE) > -1)
        {
            dictionary d = parseRange(params);
            range = d.getFloat("range");
            message = d.getString("params");
            if (range <= 0)
            {
                sendSystemMessageTestingOnly(self, "[Error] /broadcastArea: Invalid broadcast range specified");
                sendSystemMessageTestingOnly(self, "[Syntax] /broadcastArea -range:<radius> <message>");
                return SCRIPT_CONTINUE;
            }
            if (message == null || message.equalsIgnoreCase(""))
            {
                sendSystemMessageTestingOnly(self, "[Error] /broadcastArea: Invalid or no broadcast message specified");
                sendSystemMessageTestingOnly(self, "[Syntax] /broadcastArea -range:<radius> <message>");
                return SCRIPT_CONTINUE;
            }
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /broadcastArea -range:<radius> <message>");
            sendSystemMessageTestingOnly(self, "[Example] /broadcastArea -range:100 Hello world!");
            return SCRIPT_CONTINUE;
        }
        if (range > MAX_RANGE)
        {
            range = MAX_RANGE;
            sendSystemMessageTestingOnly(self, "[Warning] /broadcastArea: Maximum range is " + MAX_RANGE + " meters. Your broadcast has been limited to that area.");
        }
        obj_id[] players = getAllPlayers(getLocation(self), range);
        if (players == null || players.length == 0)
        {
            sendSystemMessageTestingOnly(self, "[Error] /broadcastArea: No players within range");
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < players.length; i++)
        {
            if (isIdValid(players[i]) && isPlayer(players[i]))
            {
                sendSystemMessageTestingOnly(players[i], message);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public dictionary parseRange(String params) throws InterruptedException
    {
        final String KEYWORD_RANGE = "-range:";
        dictionary ret = new dictionary();
        if (params == null || params.equalsIgnoreCase(""))
        {
            return ret;
        }
        int idx = params.indexOf(KEYWORD_RANGE);
        if (idx == -1)
        {
            return ret;
        }
        String retString = "";
        float retRange = Float.NEGATIVE_INFINITY;
        StringTokenizer st = new StringTokenizer(params);
        while (st.hasMoreTokens())
        {
            String arg = st.nextToken();
            if (arg.startsWith(KEYWORD_RANGE))
            {
                String sRange = arg.substring(KEYWORD_RANGE.length());
                retRange = utils.stringToFloat(sRange);
            }
            else
            {
                retString += arg + " ";
            }
        }
        if (retRange != Float.NEGATIVE_INFINITY)
        {
            ret.put("range", retRange);
            ret.put("params", retString.trim());
        }
        return ret;
    }
    public int cmdKill(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target) || !isMob(target))
        {
            sendSystemMessageTestingOnly(self, "/kill: you must have a valid creature target to use this command");
            return SCRIPT_CONTINUE;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /kill -target");
            return SCRIPT_CONTINUE;
        }
        if (isPlayer(target))
        {
            sendSystemMessageTestingOnly(self, "/kill: you must use /killPlayer to kill a player!");
        }
        else
        {
            if (!isIncapacitated(target))
            {
                setPosture(target, POSTURE_INCAPACITATED);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdKillPlayer(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target) || isDead(target))
        {
            sendSystemMessageTestingOnly(self, "/killPlayer: you must have a valid, alive player target to use this command");
            return SCRIPT_CONTINUE;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /killPlayer -target");
            return SCRIPT_CONTINUE;
        }
        setPosture(target, POSTURE_INCAPACITATED);
        pclib.coupDeGrace(target, target, true, true);
        return SCRIPT_CONTINUE;
    }
    public int cmdFreezePlayer(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params.indexOf(gm.KEYWORD_ID) > -1)
        {
            dictionary ret = gm.parseObjId(params);
            target = ret.getObjId("oid");
        }
        if (isIdValid(target) && isGameObjectTypeOf(getGameObjectType(target), GOT_ship))
        {
            target = getShipPilot(target);
        }
        if (isIdValid(target) && isPlayer(target))
        {
            setState(target, STATE_FROZEN, true);
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /freezePlayer [-target]|[-id:<oid>]");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdUnfreezePlayer(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params.indexOf(gm.KEYWORD_ID) > -1)
        {
            dictionary ret = gm.parseObjId(params);
            target = ret.getObjId("oid");
        }
        if (isIdValid(target) && isGameObjectTypeOf(getGameObjectType(target), GOT_ship))
        {
            target = getShipPilot(target);
        }
        if (isIdValid(target) && isPlayer(target))
        {
            setState(target, STATE_FROZEN, false);
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /unfreezePlayer [-target]|[-id:<oid>]");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdShowFactionInformation(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            dictionary ret = gm.parseTarget(params, target, "showFactionInformation");
            obj_id tmpTarget = ret.getObjId("oid");
            if (isIdValid(tmpTarget))
            {
                target = tmpTarget;
            }
            else
            {
                target = self;
            }
            params = ret.getString("params");
            if (params == null)
            {
                params = "";
            }
        }
        if (!isMob(target) && !hasObjVar(target, "pvpCanAttack"))
        {
            String name = getEncodedName(target);
            string_id sid_name = utils.unpackString(name);
            if (sid_name != null)
            {
                name = getString(sid_name);
            }
            sendSystemMessageTestingOnly(self, "/showFactionInformation: (" + target + ")" + name + " is not a valid target");
            return SCRIPT_CONTINUE;
        }
        gm.showFactionInformation(self, target);
        return SCRIPT_CONTINUE;
    }
    public int cmdSetFaction(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            target = self;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            if (params.indexOf(gm.KEYWORD_ID) > -1)
            {
                dictionary d = gm.parseObjId(params);
                obj_id tmpTarget = d.getObjId("oid");
                if (isIdValid(tmpTarget))
                {
                    target = tmpTarget;
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "/setFaction: Unable to parse a valid object id!");
                    return SCRIPT_CONTINUE;
                }
                params = d.getString("params");
            }
            else
            {
                target = self;
            }
        }
        boolean isAdHoc = false;
        if (params.indexOf(gm.KEYWORD_ADHOC) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_ADHOC);
            isAdHoc = true;
        }
        if (params == null || params.equalsIgnoreCase("") || (isPlayer(target) && isAdHoc))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /setFaction [-target] (declared|covert|onleave) <faction name>");
            sendSystemMessageTestingOnly(self, "[Syntax] /setFaction [-target] clear");
            sendSystemMessageTestingOnly(self, "[Syntax] /setFaction [-target] [-adhoc] (declared|covert) <faction name> (AI ONLY)");
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(toLower(params));
        String faction = "";
        boolean declared = false;
        boolean onleave = false;
        boolean makeNeutral = false;
        boolean clearFlags = false;
        while (st.hasMoreTokens())
        {
            String arg = st.nextToken();
            if (arg.equalsIgnoreCase("declared"))
            {
                declared = true;
            }
            else if (arg.equalsIgnoreCase("covert"))
            {
                declared = false;
            }
            else if (arg.equalsIgnoreCase("neutral"))
            {
                makeNeutral = true;
            }
            else if (arg.equalsIgnoreCase("onleave"))
            {
                onleave = true;
            }
            else if (arg.equalsIgnoreCase("clear"))
            {
                clearFlags = clearFlags;
            }
            else
            {
                if (arg.equalsIgnoreCase("imperial"))
                {
                    faction = "Imperial";
                }
                else if (arg.equalsIgnoreCase("rebel"))
                {
                    faction = "Rebel";
                }
                else
                {
                    faction = arg;
                }
            }
        }
        boolean updateFaction = false;
        int facNum = factions.AD_HOC_FACTION;
        if (faction != null && !faction.equalsIgnoreCase(""))
        {
            facNum = factions.getFactionNumber(faction);
            if (facNum == factions.AD_HOC_FACTION)
            {
                if (!isPlayer(target))
                {
                    if (!isAdHoc)
                    {
                        sendSystemMessageTestingOnly(self, "/setFaction: you must use '-adhoc' to set AIs to non-standard factions");
                        return SCRIPT_CONTINUE;
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "/setFaction: attempting to update " + utils.getStringName(target) + "'s faction!");
                        factions.setFaction(target, faction);
                        return SCRIPT_CONTINUE;
                    }
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "/setFaction: you may not set players to adhoc factions!");
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (clearFlags)
        {
            sendSystemMessageTestingOnly(self, "/setFaction: clearing all temp enemy flags...");
            pvpRemoveAllTempEnemyFlags(target);
        }
        if (makeNeutral)
        {
            sendSystemMessageTestingOnly(self, "/setFaction: assigning neutral status...");
            pvpMakeNeutral(target);
        }
        else
        {
            if (faction != null && !faction.equalsIgnoreCase("") && facNum != factions.AD_HOC_FACTION)
            {
                int factionHashCode = dataTableGetInt(factions.FACTION_TABLE, facNum, "pvpFaction");
                if (factionHashCode != factions.AD_HOC_FACTION && factionHashCode != 0)
                {
                    pvpSetAlignedFaction(target, factionHashCode);
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "/setFaction: unable to update " + utils.getStringName(target) + "'s faction to '" + faction + "'!");
                    return SCRIPT_CONTINUE;
                }
            }
            if (declared)
            {
                pvpMakeDeclared(target);
            }
            else if (onleave)
            {
                pvpMakeOnLeave(target);
            }
            else
            {
                pvpMakeCovert(target);
            }
        }
        if (isMob(target) || hasObjVar(target, "pvpCanAttack"))
        {
            sendSystemMessageTestingOnly(self, "/setFaction: displaying " + utils.getStringName(target) + "'s updated faction information!");
            queueCommand(self, (-532090019), target, "", COMMAND_PRIORITY_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdSetFactionStanding(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            target = self;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            target = self;
        }
        if (!isPlayer(target))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /setFactionStanding: use /setFaction to set AI factions");
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /setFactionStanding [-target] <faction name> (+|-)<value>");
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(toLower(params));
        if (st.countTokens() != 2)
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /setFactionStanding [-target] <faction name> (+|-)<value>");
            return SCRIPT_CONTINUE;
        }
        String faction = st.nextToken();
        if (faction.equalsIgnoreCase("imperial"))
        {
            faction = "Imperial";
        }
        else if (faction.equalsIgnoreCase("rebel"))
        {
            faction = "Rebel";
        }
        int mod = 0;
        String sAmt = st.nextToken();
        if (sAmt.startsWith("-"))
        {
            mod = -1;
        }
        if (sAmt.startsWith("+"))
        {
            mod = 1;
            sAmt = sAmt.substring(1);
        }
        float amt = utils.stringToFloat(sAmt);
        if (amt == Float.NEGATIVE_INFINITY)
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /setFactionStanding [-target] <faction name> (+|-)<value>");
            return SCRIPT_CONTINUE;
        }
        int facNum = factions.AD_HOC_FACTION;
        if (faction != null && !faction.equalsIgnoreCase(""))
        {
            facNum = factions.getFactionNumber(faction);
            if (facNum == factions.AD_HOC_FACTION)
            {
                sendSystemMessageTestingOnly(self, "/setFactionStanding: you may not set standing to non-standard factions");
                return SCRIPT_CONTINUE;
            }
            else
            {
                if (mod != 0)
                {
                    sendSystemMessageTestingOnly(self, "/setFactionStanding: modifying '" + faction + "' by " + amt);
                    factions.addFactionStanding(target, faction, amt);
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "/setFactionStanding: setting '" + faction + "' to " + amt);
                    factions.setFactionStanding(target, faction, amt);
                }
            }
        }
        sendSystemMessageTestingOnly(self, "/setFactionStanding: displaying " + utils.getStringName(target) + "'s updated faction information!");
        queueCommand(self, (-532090019), target, "", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int cmdGetRank(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target))
        {
            target = self;
        }
        int rank = pvpGetCurrentGcwRank(target);
        String faction = factions.getFaction(target);
        sendSystemMessageTestingOnly(self, "(" + target + ") " + getName(target) + "'s rank = (" + rank + ") " + getString(new string_id("faction_recruiter", factions.getRankName(rank, faction))));
        return SCRIPT_CONTINUE;
    }
    public int cmdSetRank(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "setRank.pid"))
        {
            int oldpid = utils.getIntScriptVar(self, "setRank.pid");
            sui.closeSUI(self, oldpid);
            utils.removeScriptVarTree(self, "setRank");
        }
        dictionary d = gm.parseTarget(params, target, "SETRANK");
        if (d == null)
        {
            return SCRIPT_CONTINUE;
        }
        else if (d.isEmpty())
        {
        }
        else
        {
            params = d.getString("params");
            obj_id oid = d.getObjId("oid");
            if (isIdValid(oid))
            {
                target = oid;
            }
            else
            {
                target = self;
            }
        }
        if (!isIdValid(target) || !isPlayer(target))
        {
            target = self;
        }
        String faction = factions.getFaction(target);
        if (params == null || params.equalsIgnoreCase(""))
        {
            int rank = pvpGetCurrentGcwRank(target);
            String title = "Set Player Rank";
            String prompt = "Select the desired rank to set the player to.\n\n";
            prompt += "Target = (" + target + ") " + getName(target) + "\n";
            prompt += "Current Rank = (" + rank + ") " + getString(new string_id("faction_recruiter", factions.getRankName(rank, faction)));
            Vector entries = new Vector();
            entries.setSize(0);
            for (int i = 0; i <= factions.MAXIMUM_RANK; i++)
            {
                entries = utils.addElement(entries, "(" + i + ") " + getString(new string_id("faction_recruiter", factions.getRankName(i, faction))));
            }
            int pid = sui.listbox(self, self, prompt, sui.OK_CANCEL, title, entries, "handleSetRankSelection");
            if (pid > -1)
            {
                utils.setScriptVar(self, "setRank.pid", pid);
                utils.setScriptVar(self, "setRank.target", target);
                gm.attachHandlerScript(self);
            }
        }
        int newrank = utils.stringToInt(params);
        if (newrank < 0 || newrank > factions.MAXIMUM_RANK)
        {
            sendSystemMessageTestingOnly(self, "/setRank: unable to parse rank. Please choose a rank between 0 & " + factions.MAXIMUM_RANK);
            return SCRIPT_CONTINUE;
        }
        if (factions.setRank(target, newrank))
        {
            int urank = pvpGetCurrentGcwRank(target);
            sendSystemMessageTestingOnly(self, "(" + target + ") " + getName(target) + "'s rank updated to (" + urank + ") " + getString(new string_id("faction_recruiter", factions.getRankName(urank, faction))));
        }
        else
        {
            sendSystemMessageTestingOnly(self, "The system was unable to update (" + target + ") " + getName(target) + "'s rank updated to (" + newrank + ") " + getString(new string_id("faction_recruiter", factions.getRankName(newrank, faction))));
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGrantSkill(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            target = self;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            target = self;
        }
        if (!isPlayer(target))
        {
            sendSystemMessageTestingOnly(self, "/grantSkill: you must be targetting a player to use this command");
            return SCRIPT_CONTINUE;
        }
        String prompt = "You are attempting to modify the skills for \\#pcontrast3 " + getName(target) + "\\#..\n\n";
        prompt += "Please choose an option:";
        int pid = sui.listbox(self, self, prompt, sui.OK_CANCEL, "GM GRANT SKILL", gm.ROADMAP_SKILL_OPTIONS, "handleGmGrantSkillOptions", true, false);
        utils.setScriptVar(self, "gmGrantSkill.target", target);
        return SCRIPT_CONTINUE;
    }
    public int cmdRevokeSkill(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            target = self;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            target = self;
        }
        if (!isPlayer(target))
        {
            sendSystemMessageTestingOnly(self, "/revokeSkill: you must be targetting a player to use this command");
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /revokeSkill [-target] <skillName>");
            return SCRIPT_CONTINUE;
        }
        params = toLower(params);
        String[] skills = dataTableGetStringColumnNoDefaults(skill.TBL_SKILL, "NAME");
        if (skills == null || skills.length == 0)
        {
            sendSystemMessageTestingOnly(self, "/grantSkill: unable to retrieve canonical skill listing!");
            return SCRIPT_CONTINUE;
        }
        if ((toLower(params)).equalsIgnoreCase("list"))
        {
            listSkills(self, skills);
            return SCRIPT_CONTINUE;
        }
        revokeSkill(target, params);
        sendSystemMessageTestingOnly(self, "You revoke " + params + " from (" + target + ") " + utils.getStringName(target));
        CustomerServiceLog("Skill", "CSR: (" + self + ") " + getName(self) + " has revoked skill '" + params + "' from (" + target + ") " + utils.getStringName(target));
        return SCRIPT_CONTINUE;
    }
    public void listSkills(obj_id self, String[] skills) throws InterruptedException
    {
        Vector entries = new Vector();
        entries.setSize(0);
        for (int i = 0; i < skills.length; i++)
        {
            String stringname = getString(new string_id("skl_n", skills[i]));
            if (stringname != null && !stringname.equalsIgnoreCase(""))
            {
                String linedata = "(" + stringname + ") " + skills[i];
                entries = utils.addElement(entries, linedata);
            }
        }
        String listTitle = "SKILL LISTING";
        String listPrompt = "A listing of all the currently used skills";
        sui.listbox(self, self, listPrompt, sui.OK_ONLY, listTitle, entries, "noHandler", true, false);
    }
    public int cmdSearchCorpse(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (utils.hasScriptVar(self, gm.SCRIPTVAR_SEARCHCORPSE_PID))
        {
            int pid = utils.getIntScriptVar(self, gm.SCRIPTVAR_SEARCHCORPSE_PID);
            forceCloseSUIPage(pid);
            utils.removeScriptVar(self, gm.SCRIPTVAR_SEARCHCORPSE_TARGET);
            utils.removeScriptVar(self, gm.SCRIPTVAR_SEARCHCORPSE_PID);
        }
        if (!isIdValid(target))
        {
            target = self;
        }
        if (!isPlayer(target))
        {
            sendSystemMessageTestingOnly(self, "/searchCorpse: you can only review player corpse history");
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(target, pclib.VAR_CORPSE_BASE))
        {
            sendSystemMessageTestingOnly(self, "/searchCorpse: (" + target + ") " + utils.getStringName(target) + " has no corpse history!");
            return SCRIPT_CONTINUE;
        }
        boolean showStamp = true;
        boolean showKiller = true;
        obj_id[] corpses = utils.getObjIdBatchObjVar(target, pclib.VAR_CORPSE_ID);
        if (corpses == null || corpses.length == 0)
        {
            removeObjVar(target, pclib.VAR_CORPSE_BASE);
            sendSystemMessageTestingOnly(self, "/searchCorpse: (" + target + ") " + utils.getStringName(target) + " has no corpse history!");
            return SCRIPT_CONTINUE;
        }
        int[] stamps = utils.getIntBatchObjVar(target, pclib.VAR_CORPSE_STAMP);
        if (stamps == null || stamps.length == 0 || stamps.length != corpses.length)
        {
            sendSystemMessageTestingOnly(self, "/searchCorpse: (" + target + ") " + utils.getStringName(target) + " has a bad corpse stamp history!");
            showStamp = false;
        }
        obj_id[] killers = utils.getObjIdBatchObjVar(target, pclib.VAR_CORPSE_KILLER);
        if (killers == null || killers.length == 0 || killers.length != corpses.length)
        {
            sendSystemMessageTestingOnly(self, "/searchCorpse: (" + target + ") " + utils.getStringName(target) + " has a bad killer history!");
            showKiller = false;
        }
        Vector entries = new Vector();
        entries.setSize(0);
        for (int i = 0; i < corpses.length; i++)
        {
            String linedata = "id:" + corpses[i];
            if (showKiller)
            {
                linedata += " k:" + killers[i];
            }
            if (showStamp)
            {
                linedata += " t:" + stamps[i];
            }
            entries = utils.addElement(entries, linedata);
        }
        if (killers != null && killers.length > 0)
        {
            String prompt = "Corpse history for target: \n(" + target + ") " + utils.getStringName(target);
            prompt += "\n\nCurrent Time = " + getGameTime();
            String title = "/searchCorpse: " + target;
            int pid = sui.listbox(self, self, prompt, sui.OK_CANCEL, title, entries, "handleSearchCorpseSui");
            if (pid > -1)
            {
                utils.setScriptVar(self, gm.SCRIPTVAR_SEARCHCORPSE_TARGET, target);
                utils.setScriptVar(self, gm.SCRIPTVAR_SEARCHCORPSE_PID, pid);
                gm.attachHandlerScript(self);
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessageTestingOnly(self, "/searchCorpse: was unable to construct a valid interface!");
        return SCRIPT_CONTINUE;
    }
    public int cmdSetSpeed(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            target = self;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            target = self;
        }
        boolean valid = false;
        if (isPlayer(target))
        {
            obj_id ship = getPilotedShip(target);
            if (isIdValid(ship))
            {
                target = ship;
            }
            valid = true;
        }
        else if (isGameObjectTypeOf(getGameObjectType(target), GOT_ship))
        {
            valid = true;
        }
        if (!valid)
        {
            sendSystemMessageTestingOnly(self, "/setSpeed: you must be targeting a player or ship to use this command");
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /setSpeed [-target] <speed multiplier>");
            return SCRIPT_CONTINUE;
        }
        float multiplier = 1f;
        StringTokenizer st = new StringTokenizer(params);
        while (st.hasMoreTokens())
        {
            float tmp = utils.stringToFloat(st.nextToken());
            if (tmp > 0f)
            {
                multiplier = tmp;
            }
        }
        if (multiplier > 0f && multiplier <= 10f)
        {
            setMovementPercent(target, multiplier);
        }
        float newSpeed = getMovementPercent(target);
        sendSystemMessageTestingOnly(self, "(" + target + ") " + utils.getStringName(target) + "'s new speed multiplier = " + newSpeed);
        return SCRIPT_CONTINUE;
    }
    public int cmdSetName(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            target = self;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            target = self;
        }
        if (params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /setName [-target] <name>");
            return SCRIPT_CONTINUE;
        }
        setName(target, params);
        return SCRIPT_CONTINUE;
    }
    public int cmdSetFirstName(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            target = self;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            target = self;
        }
        if (!isPlayer(target))
        {
            sendSystemMessageTestingOnly(self, "/setFirstName: you must be targetting a player to use this command");
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /setFirstName [-target] <new first name>");
            return SCRIPT_CONTINUE;
        }
        if (params.indexOf(" ") > -1)
        {
            sendSystemMessageTestingOnly(self, "/setFirstName: spaces are not allowed in a first name");
            return SCRIPT_CONTINUE;
        }
        String name = utils.getStringName(target);
        String newname = name;
        if (name != null && !name.equalsIgnoreCase(""))
        {
            StringTokenizer st = new StringTokenizer(name);
            String fname = st.nextToken();
            newname = params;
            while (st.hasMoreTokens())newname += " " + st.nextToken();
        }
        if (newname.equalsIgnoreCase(name))
        {
            sendSystemMessageTestingOnly(self, "/setFirstName: name NOT updated...");
        }
        else
        {
            if (setName(target, newname))
            {
                sendSystemMessageTestingOnly(self, "/setFirstName: " + name + " will now be known as " + newname);
            }
            else
            {
                sendSystemMessageTestingOnly(self, "/setFirstName: system disallowed name change...");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdSetLastName(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            target = self;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            target = self;
        }
        if (!isPlayer(target))
        {
            sendSystemMessageTestingOnly(self, "/setLastName: you must be targetting a player to use this command");
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /setLastName [-target] <new last name>");
            return SCRIPT_CONTINUE;
        }
        String name = utils.getStringName(target);
        String newname = name;
        if (name != null && !name.equalsIgnoreCase(""))
        {
            StringTokenizer st = new StringTokenizer(name);
            String fname = st.nextToken();
            newname = fname + " " + params;
        }
        if (newname.equalsIgnoreCase(name))
        {
            sendSystemMessageTestingOnly(self, "/setLastName: name NOT updated...");
        }
        else
        {
            if (setName(target, newname))
            {
                sendSystemMessageTestingOnly(self, "/setLastName: " + name + " will now be known as " + newname);
            }
            else
            {
                sendSystemMessageTestingOnly(self, "/setLastName: system disallowed name change...");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGrantBadge(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            target = self;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            target = self;
        }
        if (!isPlayer(target))
        {
            sendSystemMessageTestingOnly(self, "/grantBadge: you must be targetting a player to use this command");
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /grantBadge [-target] <badgeName> or <index>");
            return SCRIPT_CONTINUE;
        }
        int idx = utils.stringToInt(params);
        String badgeName = "";
        if (idx < 0)
        {
            badgeName = params;
        }
        else
        {
            badgeName = getCollectionSlotName(idx);
        }
        if ((badgeName == null) || (badgeName.length() == 0))
        {
            sendSystemMessageTestingOnly(self, "Error: badge name " + badgeName + " is not a valid badge.");
            return SCRIPT_CONTINUE;
        }
        if (badge.grantBadge(target, badgeName))
        {
            sendSystemMessageTestingOnly(self, "/grantBadge: you grant badge " + badgeName + " to (" + target + ")" + utils.getStringName(target));
        }
        else
        {
            sendSystemMessageTestingOnly(self, "/grantBadge: you are UNABLE to grant badge " + badgeName + " to (" + target + ")" + utils.getStringName(target));
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdRevokeBadge(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            target = self;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            target = self;
        }
        if (!isPlayer(target))
        {
            sendSystemMessageTestingOnly(self, "/revokeBadge: you must be targetting a player to use this command");
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /revokeBadge [-target] <index>");
            return SCRIPT_CONTINUE;
        }
        int idx = utils.stringToInt(params);
        if (idx < 0)
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /revokeBadge [-target] <index>");
            return SCRIPT_CONTINUE;
        }
        String badgeName = getCollectionSlotName(idx);
        if ((badgeName == null) || (badgeName.length() == 0))
        {
            sendSystemMessageTestingOnly(self, "Error: badge number " + idx + " is not a valid badge.");
            return SCRIPT_CONTINUE;
        }
        if (badge.revokeBadge(target, badgeName, true))
        {
            sendSystemMessageTestingOnly(self, "/revokeBadge: you revoke badge #" + idx + " from (" + target + ")" + utils.getStringName(target));
        }
        else
        {
            sendSystemMessageTestingOnly(self, "/revokeBadge: you are UNABLE to revoke badge #" + idx + " from (" + target + ")" + utils.getStringName(target));
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdShowExperience(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            target = self;
        }
        if (!isPlayer(target))
        {
            sendSystemMessageTestingOnly(self, "/showFactionInformation: you must be targetting a player to use this command");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, gm.SCRIPTVAR_SHOWEXPERIENCE_PID))
        {
            int oldPid = utils.getIntScriptVar(self, gm.SCRIPTVAR_SHOWEXPERIENCE_PID);
            forceCloseSUIPage(oldPid);
            utils.removeScriptVar(self, gm.SCRIPTVAR_SHOWEXPERIENCE_PID);
        }
        String title = "EXPERIENCE LISTING";
        String prompt = "Experience Listing for player:\n(" + target + ")" + utils.getStringName(target);
        prompt += "\n\nNOTE: THIS INFORMATION IS NOT REAL-TIME UPDATED! TIME: " + getGameTime();
        Vector entries = new Vector();
        entries.setSize(0);
        int pid = sui.listbox(self, self, prompt, sui.OK_ONLY, title, entries, "cleanupShowFaction");
        if (pid > -1)
        {
            utils.setScriptVar(self, gm.SCRIPTVAR_SHOWEXPERIENCE_PID, pid);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdSetExperience(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            target = self;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            target = self;
        }
        if (!isPlayer(target))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /setExperience: this command may only be used on players");
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.equalsIgnoreCase(""))
        {
            showSetExperienceSyntax(self);
            return SCRIPT_CONTINUE;
        }
        String[] xpTypes = xp.getXpTypes(self);
        if (xpTypes == null || xpTypes.length == 0)
        {
            sendSystemMessageTestingOnly(self, "/setExperience: unable to retrieve canonical xp listing!");
            return SCRIPT_CONTINUE;
        }
        if ((toLower(params)).equalsIgnoreCase("list"))
        {
            Vector entries = new Vector();
            entries.setSize(0);
            for (int i = 0; i < xpTypes.length; i++)
            {
                String stringname = getString(new string_id("exp_n", xpTypes[i]));
                if (stringname != null && !stringname.equalsIgnoreCase(""))
                {
                    String linedata = "(" + stringname + ") " + xpTypes[i];
                    entries = utils.addElement(entries, linedata);
                }
            }
            String listTitle = "XP TYPES";
            String listPrompt = "A listing of all the currently used xp types";
            sui.listbox(self, self, listPrompt, sui.OK_ONLY, listTitle, entries, "noHandler");
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(toLower(params));
        if (st.countTokens() != 2)
        {
            showSetExperienceSyntax(self);
            return SCRIPT_CONTINUE;
        }
        String xp_type = st.nextToken();
        if (utils.getElementPositionInArray(xpTypes, xp_type) == -1)
        {
            sendSystemMessageTestingOnly(self, "/setExperience: '" + xp_type + "' is not a valid xp type.");
            sendSystemMessageTestingOnly(self, "/setExperience: use '/setExperience list' for a valid listing");
            return SCRIPT_CONTINUE;
        }
        int xp_amt = getExperiencePoints(target, xp_type);
        if (st.hasMoreTokens())
        {
            String sAmt = st.nextToken();
            int amt = 0;
            if (sAmt.startsWith("+") || sAmt.startsWith("-"))
            {
                amt = utils.stringToInt(sAmt.substring(1));
                if (amt == -1)
                {
                    sendSystemMessageTestingOnly(self, "/setExperience: invalid xp delta parameter...");
                }
                if (sAmt.startsWith("-"))
                {
                    amt = -amt;
                }
            }
            else
            {
                amt = utils.stringToInt(sAmt);
                if (amt == -1)
                {
                    sendSystemMessageTestingOnly(self, "/setExperience: invalid xp amount: '" + sAmt + "'");
                }
                amt = amt - xp_amt;
            }
            if (amt != 0)
            {
                dictionary resultData = new dictionary();
                resultData.put("target", target);
                resultData.put("xp_type", xp_type);
                xp.grantUnmodifiedExperience(target, xp_type, amt, "cmdSetExperienceResult", resultData);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void showSetExperienceSyntax(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "[Syntax] /setExperience [-target] <xp type> (+|-)<value>");
        sendSystemMessageTestingOnly(self, "[Syntax] /setExperience list");
    }
    public int cmdFindObject(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id objToFind = utils.stringToObjId(params);
        if (!isIdValid(objToFind))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /findObject objid");
        }
        else
        {
            sendSystemMessageTestingOnly(self, "Searching for object: " + objToFind);
            gm.attachHandlerScript(self);
            findObjectAnywhere(objToFind, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdFindObjectByTemplate(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() != 1)
        {
            sendSystemMessageTestingOnly(self, "Format: findObjectByTemplate <template name>, like \"findObjectByTemplate object/mobile/meatlump_brother_lempi.iff\"");
        }
        else
        {
            String templateName = st.nextToken();
            sendConsoleMessage(self, "Searching for object by template name: " + templateName);
            gm.attachHandlerScript(self);
            findObjectAnywhereByTemplate(templateName, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdFindPlayer(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() != 1)
        {
            sendSystemMessageTestingOnly(self, "Format: findPlayer <player name|*>, where * will find all players");
        }
        else
        {
            String partialName = st.nextToken();
            sendConsoleMessage(self, "Searching for players with name containing: " + partialName);
            gm.attachHandlerScript(self);
            findPlayerAnywhereByPartialName(partialName, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdFindWarden(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        sendConsoleMessage(self, "Searching for wardens");
        gm.attachHandlerScript(self);
        findWardenAnywhere(self);
        return SCRIPT_CONTINUE;
    }
    public int cmdFindCreature(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() != 1)
        {
            sendSystemMessageTestingOnly(self, "Format: findCreature <creature name>, like \"findCreature gnort\"");
        }
        else
        {
            String creatureName = st.nextToken();
            sendConsoleMessage(self, "Searching for creature: " + creatureName);
            gm.attachHandlerScript(self);
            findCreatureAnywhere(creatureName, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdWipeItems(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            target = self;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            target = self;
        }
        String title = "CONFIRM DESTROY?";
        String prompt = "You are about to destroy all items owned by (" + target + ")" + utils.getStringName(target) + ".\n\n";
        prompt += "ARE YOU SURE YOU WANT TO DO THIS?";
        int pid = sui.msgbox(self, self, prompt, sui.YES_NO, title, gm.HANDLER_CONFIRM_WIPE);
        if (pid > -1)
        {
            utils.setScriptVar(self, gm.SCRIPTVAR_WIPEITEMS_PID, pid);
            utils.setScriptVar(self, gm.SCRIPTVAR_WIPEITEMS_TARGET, target);
            gm.attachHandlerScript(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdAdjustLotCount(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "You must specify a target.");
                return SCRIPT_CONTINUE;
            }
        }
        if (!isPlayer(target))
        {
            sendSystemMessageTestingOnly(self, "The target must be a player.");
            return SCRIPT_CONTINUE;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() == 0)
        {
            sendSystemMessageTestingOnly(self, "Format: adjustLotCount <amount>");
        }
        else
        {
            String amount_str = st.nextToken();
            int amount = utils.stringToInt(amount_str);
            adjustLotCount(getPlayerObject(target), amount);
            int lots = getAccountNumLots(getPlayerObject(target));
            sendSystemMessageTestingOnly(self, "Lot count adjusted on " + target + " by " + amount + ". New lot count is " + lots);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdEditBankAccount(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "cmdEditBankAccount");
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() != 2)
        {
            sendSystemMessageTestingOnly(self, "Format: editMoney <player name> <amount>");
        }
        else
        {
            String name = st.nextToken();
            String amount_str = st.nextToken();
            int amount = utils.stringToInt(amount_str);
            if (amount == -1 || amount == 0)
            {
                sendSystemMessageTestingOnly(self, "Format: editMoney <player name> <amount>");
            }
            else
            {
                LOG("LOG_CHANNEL", "handleEditBankAccount");
                String firstName = name.toLowerCase();
                obj_id playerId = getPlayerIdFromFirstName(firstName);
                if (!isIdValid(playerId))
                {
                    sendSystemMessageTestingOnly(self, "Could not find an obj_id for the specified player.");
                    return SCRIPT_CONTINUE;
                }
                LOG("LOG_CHANNEL", "target ->" + playerId + " firstName ->" + firstName + " amount ->" + amount);
                if (amount > 0)
                {
                    if (money.bankTo(money.ACCT_CUSTOMER_SERVICE, playerId, amount))
                    {
                        sendSystemMessageTestingOnly(self, "Requesting credit transfer of " + amount + " to " + firstName + " (" + playerId + ")");
                        CustomerServiceLog("money", getFirstName(self) + " (" + self + ") requested a transfer of " + amount + " credits to " + firstName + "(" + playerId + ")");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "Transfer failed.");
                    }
                }
                else
                {
                    amount = amount * -1;
                    if (money.bankTo(playerId, money.ACCT_CUSTOMER_SERVICE, amount))
                    {
                        sendSystemMessageTestingOnly(self, "Requesting credit transfer of " + amount + " from " + firstName + " (" + playerId + ")");
                        CustomerServiceLog("money", getFirstName(self) + " (" + self + ") requested a transfer of " + amount + " credits to " + firstName + "(" + playerId + ")");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdSetHue(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        boolean parseTarget = false;
        if (!isIdValid(target))
        {
            if (params.indexOf(gm.KEYWORD_ID) > -1)
            {
                dictionary ret = gm.parseObjId(params);
                target = ret.getObjId("oid");
                params = ret.getString("params");
            }
            else
            {
                showSetHueSyntax(self);
                return SCRIPT_CONTINUE;
            }
        }
        else
        {
            if (params.indexOf(gm.KEYWORD_TARGET) > -1)
            {
                params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
            }
            else
            {
                showSetHueSyntax(self);
                return SCRIPT_CONTINUE;
            }
        }
        if (!isIdValid(target) || !exists(target) || !target.isLoaded())
        {
            sendSystemMessageTestingOnly(self, "/setHue: the designated target (" + target + ") is not valid for this command");
            return SCRIPT_CONTINUE;
        }
        int varIdx = Integer.MIN_VALUE;
        int palIdx = Integer.MIN_VALUE;
        color c = null;
        String colorname = "";
        StringTokenizer st = new StringTokenizer(params);
        dictionary palColData = hue.getPalcolorData(target);
        if (palColData == null || palColData.isEmpty())
        {
            sendSystemMessageTestingOnly(self, "/setHue: (" + target + ")" + utils.getStringName(target) + " has no palcolor data!");
            return SCRIPT_CONTINUE;
        }
        if (st.hasMoreTokens())
        {
            varIdx = utils.stringToInt(st.nextToken());
            if (st.hasMoreTokens())
            {
                String cArg = st.nextToken();
                palIdx = utils.stringToInt(cArg);
                if (palIdx == -1)
                {
                    c = colors.getColorByName(cArg);
                    if (c != null)
                    {
                        colorname = cArg;
                    }
                }
            }
        }
        boolean showVarIdxUI = true;
        String varIdxPath = hue.INDEX_BASE + varIdx;
        if (varIdx > 0)
        {
            if (palColData.containsKey(varIdxPath))
            {
                showVarIdxUI = false;
            }
            else
            {
                sendSystemMessageTestingOnly(self, "/setHue: (" + target + ")" + utils.getStringName(target) + " does not have var index #" + varIdx);
            }
        }
        if (showVarIdxUI)
        {
            gm.showSetHueVarUI(self, target, palColData);
            return SCRIPT_CONTINUE;
        }
        custom_var cv = getCustomVarByName(target, varIdxPath);
        if (cv == null)
        {
            sendSystemMessageTestingOnly(self, "/setHue: (" + target + ")" + utils.getStringName(target) + " does not have var path " + varIdxPath);
            return SCRIPT_CONTINUE;
        }
        if (c != null && cv.isPalColor())
        {
            sendSystemMessageTestingOnly(self, "/setHue: attempting to hue " + target + "'s " + varIdxPath + " color " + colorname);
            palcolor_custom_var pcv = (palcolor_custom_var)cv;
            pcv.setToClosestColor(c);
            return SCRIPT_CONTINUE;
        }
        ranged_int_custom_var ri = (ranged_int_custom_var)cv;
        boolean showPalIdxUI = true;
        if (palIdx > -1)
        {
            int min = ri.getMinRangeInclusive();
            int max = ri.getMaxRangeInclusive();
            if (palIdx >= min && palIdx <= max)
            {
                showPalIdxUI = false;
            }
            else
            {
                sendSystemMessageTestingOnly(self, "/setHue: (" + target + ")" + utils.getStringName(target) + " pal range is " + min + " to " + max);
            }
        }
        if (showPalIdxUI)
        {
            gm.showSetHueColorUI(self, target, varIdxPath);
            return SCRIPT_CONTINUE;
        }
        if (ri != null)
        {
            sendSystemMessageTestingOnly(self, "/setHue: attempting to hue " + target + "'s " + varIdxPath + " to " + palIdx);
            ri.setValue(palIdx);
        }
        return SCRIPT_CONTINUE;
    }
    public void showSetHueSyntax(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "[Syntax] /setHue [-target]|[-id:<oid>] (var index) (palette index)");
        sendSystemMessageTestingOnly(self, "[Syntax] /setHue [-target]|[-id:<oid>] (var index) (color name)");
    }
    public int cmdGoto(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        dictionary d = new dictionary();
        if (params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /goto <target>");
        }
        else
        {
            StringTokenizer st = new StringTokenizer(params);
            String oid = st.nextToken();
            if (oid != null)
            {
                Long id;
                try
                {
                    id = new Long(oid);
                }
                catch(NumberFormatException err)
                {
                    debugServerConsoleMsg(self, "Long Conversion Failed, Continuing to next onArrivedAtLocation");
                    return SCRIPT_CONTINUE;
                }
                obj_id obj = obj_id.getObjId(id.longValue());
                locateObject(obj, "onObjectLocateResponseForCmdGoto", d);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int onObjectLocateResponseForCmdGoto(obj_id self, dictionary params) throws InterruptedException
    {
        location l = params.getLocation("location");
        if (l != null)
        {
            setLocation(self, l);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdSetPlayerState(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target) || params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /setPlayerState -target (with lookat target)");
            return SCRIPT_CONTINUE;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /setPlayerState -target (with lookat target)");
            return SCRIPT_CONTINUE;
        }
        gm.attachHandlerScript(self);
        gm.showSetPlayerStateUI(target);
        return SCRIPT_CONTINUE;
    }
    public int cmdUnCityBan(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            if (params.indexOf(gm.KEYWORD_ID) > -1)
            {
                dictionary ret = gm.parseObjId(params);
                target = ret.getObjId("oid");
                params = ret.getString("params");
            }
            else
            {
                target = self;
            }
        }
        else
        {
            if (params.indexOf(gm.KEYWORD_TARGET) > -1)
            {
                params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
            }
            else if (params.indexOf(gm.KEYWORD_ID) > -1)
            {
                dictionary ret = gm.parseObjId(params);
                target = ret.getObjId("oid");
                params = ret.getString("params");
            }
            else
            {
                target = self;
            }
        }
        if (!isPlayer(target) || !isIdValid(target))
        {
            sendSystemMessageTestingOnly(self, "[Error] /unCityBan: You must specify a valid player to use this command");
            sendSystemMessageTestingOnly(self, "[Syntax] /unCityBan [-target]|[-id:<oid>]");
            return SCRIPT_CONTINUE;
        }
        int cityID = city.checkCity(self, false);
        if (cityID == 0)
        {
            sendSystemMessageTestingOnly(self, "[Error] /unCityBan: You must be inside the area of the city from which you wish to unban " + getFirstName(target));
            return SCRIPT_CONTINUE;
        }
        if (!city.isCityBanned(target, cityID))
        {
            sendSystemMessageTestingOnly(self, "[Error] /unCityBan: " + getFirstName(target) + " has not been banned from " + cityGetName(cityID));
            return SCRIPT_CONTINUE;
        }
        if (city.removeBan(target, cityID))
        {
            sendSystemMessageTestingOnly(self, "You have unbanned " + getFirstName(target) + " from " + cityGetName(cityID));
            CustomerServiceLog("player_city", "CSR: %TU has unbanned %TT from " + cityGetName(cityID) + "(" + cityID + "/" + cityGetCityHall(cityID) + ")", self, target);
            return SCRIPT_CONTINUE;
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Error] /unCityBan: You were not able to unban " + getFirstName(target) + " from " + cityGetName(cityID));
            return SCRIPT_CONTINUE;
        }
    }
    public int cmdInvulnerable(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id ship = getPilotedShip(self);
        if (hasObjVar(self, "invulnerable"))
        {
            removeObjVar(self, "invulnerable");
            setInvulnerable(self, false);
            if (isIdValid(ship))
            {
                removeObjVar(ship, "intInvincible");
            }
            sendSystemMessageTestingOnly(self, "Invulnerability[OFF]: You are no longer invulnerable.");
        }
        else
        {
            setObjVar(self, "invulnerable", 1);
            setInvulnerable(self, true);
            if (isIdValid(ship))
            {
                setObjVar(ship, "intInvincible", 1);
            }
            sendSystemMessageTestingOnly(self, "Invulnerability[ON]: You are now invulnerable.");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdSetTef(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            sendSystemMessageTestingOnly(self, "[Error] /setTEF: You must target a declared member of a PVP faction to use this command");
            return SCRIPT_CONTINUE;
        }
        if (params != null && !params.equalsIgnoreCase("") && (params.indexOf(gm.KEYWORD_TARGET) > -1))
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /setTEF -target");
            return SCRIPT_CONTINUE;
        }
        String csrFaction = factions.getFaction(self);
        String targetFaction = factions.getFaction(target);
        if (csrFaction == null || csrFaction.equalsIgnoreCase("") || (factions.pvpGetType(self) == factions.PVPTYPE_DECLARED))
        {
            sendSystemMessageTestingOnly(self, "[Error] /setTEF: You must be a covert member of a PVP faction to use this command");
            return SCRIPT_CONTINUE;
        }
        if (targetFaction == null || targetFaction.equalsIgnoreCase("") || (!targetFaction.equalsIgnoreCase(factions.FACTION_IMPERIAL) && !targetFaction.equalsIgnoreCase(factions.FACTION_REBEL)))
        {
            sendSystemMessageTestingOnly(self, "[Error] /setTEF: Your target must be a member of a PVP faction to use this command");
            return SCRIPT_CONTINUE;
        }
        if (isPlayer(target) && (factions.pvpGetType(target) != factions.PVPTYPE_DECLARED))
        {
            sendSystemMessageTestingOnly(self, "[Error] /setTEF: Your target must be a declared member of a PVP faction to use this command");
            return SCRIPT_CONTINUE;
        }
        if (!factions.areFactionsOpposed(csrFaction, targetFaction))
        {
            sendSystemMessageTestingOnly(self, "[Error] /setTEF: You must be factionally opposed to your target to use this command");
            return SCRIPT_CONTINUE;
        }
        pvpSetPersonalEnemyFlag(self, target);
        sendSystemMessageTestingOnly(self, "A temporary enemy flag has been set against your current target (OID: " + target + ")");
        return SCRIPT_CONTINUE;
    }
    public int cmdInitializeComponent(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "cmdInitializeComponent -- " + params);
        if (!isIdValid(target))
        {
            sendSystemMessageTestingOnly(self, "You must have the component item targetted");
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.length() < 1)
        {
            sendSystemMessageTestingOnly(self, "format: /initializeComponent <level> <bonus> <serial number>    /initializeComponent ? for help");
            return SCRIPT_CONTINUE;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() < 3)
        {
            if (st.countTokens() == 1)
            {
                if ((st.nextToken()).equalsIgnoreCase("?"))
                {
                    sendSystemMessageTestingOnly(self, "format: /initializeComponent <level> <bonus> <serial number>");
                    sendSystemMessageTestingOnly(self, "   Create your component and target it.");
                    sendSystemMessageTestingOnly(self, "   level -- The level of the creature at which to generate the loot. Cannot exceed 100.");
                    sendSystemMessageTestingOnly(self, "   bonus -- rare item factor. A bonus of 2 or higher is 'exceptional'. A bonus of 10 is 'lengendary'. Cannot exceed 10");
                    sendSystemMessageTestingOnly(self, "   serial number -- unique number to use for the item. It is recommended that you use the obj_id of the item created.");
                    return SCRIPT_CONTINUE;
                }
            }
            sendSystemMessageTestingOnly(self, "format: /initializeComponent <level> <multiplier> <serial number>    /initializeComponent ? for help");
            return SCRIPT_CONTINUE;
        }
        String first_param = st.nextToken();
        int level = utils.stringToInt(first_param);
        if (level < 1)
        {
            sendSystemMessageTestingOnly(self, "Level must be greater than 0.");
            return SCRIPT_CONTINUE;
        }
        if (level > 100)
        {
            sendSystemMessageTestingOnly(self, "Level cannot exceed 100.");
            return SCRIPT_CONTINUE;
        }
        String second_param = st.nextToken();
        int bonus = utils.stringToInt(second_param);
        if (bonus < 1)
        {
            sendSystemMessageTestingOnly(self, "Bonus must be level 1 or higher.");
            return SCRIPT_CONTINUE;
        }
        if (bonus > 10)
        {
            sendSystemMessageTestingOnly(self, "Bonus cannot exceed 10.");
            return SCRIPT_CONTINUE;
        }
        String third_param = st.nextToken();
        obj_id source = utils.stringToObjId(third_param);
        if (!isIdValid(source))
        {
            sendSystemMessageTestingOnly(self, "You must provide a valid obj_id serial number");
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(target);
        int idx = dataTableSearchColumnForString(template, "template", loot.TBL_COMPONENT_DATA);
        if (idx == -1)
        {
            sendSystemMessageTestingOnly(self, "No loot component data found for " + template + ".");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(target, "csr.loot.creator") || hasObjVar(target, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME))
        {
            sendSystemMessageTestingOnly(self, "That item already has been initialized.");
            return SCRIPT_CONTINUE;
        }
        loot.randomizeComponent(target, level, target);
        setCraftedId(target, source);
        setCrafter(target, self);
        setObjVar(target, "csr.loot.creator", self);
        if (bonus > 1)
        {
            string_id item_sid = getNameStringId(target);
            string_id prefix;
            if (bonus > 9)
            {
                prefix = new string_id("obj_n", "legendary_prefix");
            }
            else
            {
                prefix = new string_id("obj_n", "exceptional_prefix");
            }
            String name = localize(item_sid) + " (" + localize(prefix) + ")";
            setName(target, name);
        }
        CustomerServiceLog("loot", "CSR: %TU has created %TT at level " + level + " and at rarity bonus " + bonus, self, target);
        sendSystemMessageTestingOnly(self, "Component initialization complete.");
        return SCRIPT_CONTINUE;
    }
    public int cmdGmWeapon(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        trace.log("Weapons", "gm.cmdGmWeapon -->> " + params, self, trace.TL_WARNING);
        if (params == null || params.length() < 1)
        {
            sendSystemMessageTestingOnly(self, "format: /GmWeapon <command> (params)   OR   /cmdGmWeapon ? for help");
            return SCRIPT_CONTINUE;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        int tokens = st.countTokens();
        String command = null;
        if (st.hasMoreTokens())
        {
            command = st.nextToken();
        }
        if (command == null)
        {
            sendSystemMessageTestingOnly(self, "format: /GmWeapon <command> (params)   OR   /cmdGmWeapon ? for help");
            return SCRIPT_CONTINUE;
        }
        if ((toLower(command)).equalsIgnoreCase("list"))
        {
            String kind = null;
            if (st.hasMoreTokens())
            {
                kind = st.nextToken();
            }
            if (kind == null)
            {
                sendSystemMessageTestingOnly(self, "format: /GmWeapon <command> (params)   OR   /cmdGmWeapon ? for help");
                sendSystemMessageTestingOnly(self, "	list <unarmed|melee|pistols|rifles|heavy|pistol|polearm|etc|etc> -- Lists all identifiers for the given weapon type");
                return SCRIPT_CONTINUE;
            }
            kind = toLower(kind);
            Vector names = weapons.getWeaponIdsForType(kind);
            if (names.size() < 1)
            {
                sendSystemMessageTestingOnly(self, "0 matches to your ID query for '" + kind + "'.");
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Weapon type IDs matching your query (" + kind + ") are:");
            for (int i = 0; i < names.size(); i++)
            {
                sendSystemMessageTestingOnly(self, (String)names.get(i));
            }
            sendSystemMessageTestingOnly(self, "Done listing " + names.size() + " matches.");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equalsIgnoreCase("create"))
        {
            String schematicId = null;
            if (st.hasMoreTokens())
            {
                schematicId = st.nextToken();
            }
            if (schematicId == null)
            {
                sendSystemMessageTestingOnly(self, "format: /GmWeapon <command> (params)   OR   /cmdGmWeapon ? for help");
                sendSystemMessageTestingOnly(self, "	create <weapon_id> <percent 1-100> -- Creates the indicated weapon with all values at the given percent intensity");
                return SCRIPT_CONTINUE;
            }
            float intensity = 0.50f;
            if (st.hasMoreTokens())
            {
                int tempIntensity = utils.stringToInt(st.nextToken());
                if (tempIntensity < 1)
                {
                    sendSystemMessageTestingOnly(self, "format: /GmWeapon <command> (params)   OR   /cmdGmWeapon ? for help");
                    sendSystemMessageTestingOnly(self, "	create <weapon_id> <percent 1-100> -- Creates the indicated weapon with all values at the given percent intensity");
                    return SCRIPT_CONTINUE;
                }
                intensity = ((float)tempIntensity / 100.0f);
            }
            if (intensity > 1.25f)
            {
                intensity = 1.25f;
            }
            if (weapons.createWeapon(schematicId, self, weapons.VIA_SCHEMATIC, intensity) == null)
            {
                sendSystemMessageTestingOnly(self, "Error creating weapon with supplied ID (" + schematicId + ").");
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Weapon " + schematicId + " created.");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equalsIgnoreCase("limuseschematic"))
        {
            String schematicId = null;
            if (st.hasMoreTokens())
            {
                schematicId = st.nextToken();
            }
            if (schematicId == null)
            {
                sendSystemMessageTestingOnly(self, "format: /GmWeapon <command> (params)   OR   /cmdGmWeapon ? for help");
                sendSystemMessageTestingOnly(self, "	limUseSchematic <weapon_id> <numUses> <optional: skillRequired> -- Creates a limited use draft schematic.");
                return SCRIPT_CONTINUE;
            }
            int numUses = -1;
            if (st.hasMoreTokens())
            {
                numUses = utils.stringToInt(st.nextToken());
            }
            if (numUses < 1)
            {
                sendSystemMessageTestingOnly(self, "format: /GmWeapon <command> (params)   OR   /cmdGmWeapon ? for help");
                sendSystemMessageTestingOnly(self, "	limUseSchematic <weapon_id> <numUses> <optional: skillRequired> -- Creates a limited use draft schematic.");
                return SCRIPT_CONTINUE;
            }
            String requiredSkill = "class_munitions_phase4_master";
            if (st.hasMoreTokens())
            {
                requiredSkill = st.nextToken();
            }
            if (weapons.createLimitedUseSchematic(schematicId, weapons.VIA_SCHEMATIC, numUses, self, schematicId, requiredSkill) != null)
            {
                sendSystemMessageTestingOnly(self, "Successfully created " + numUses + " use draft schematic for item " + schematicId + " for skill " + requiredSkill + ".");
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Error creating limited use draft schematic for " + schematicId + ".");
            }
            return SCRIPT_CONTINUE;
        }
        else if (command.equalsIgnoreCase("?"))
        {
            sendSystemMessageTestingOnly(self, "format: /GmWeapon <command> (params)   OR   /cmdGmWeapon ? for help");
            sendSystemMessageTestingOnly(self, "	list <unarmed|melee|pistols|rifles|heavy|all> -- Lists all identifiers for the given weapon type");
            sendSystemMessageTestingOnly(self, "	create <weapon_id> <percent 1-100> -- Creates the indicated weapon with all values at the given percent intensity");
            sendSystemMessageTestingOnly(self, "	limUseSchematic <weapon_id> <numUses> <optional: skillRequired> -- Creates a limited use draft schematic.");
            return SCRIPT_CONTINUE;
        }
        else
        {
            sendSystemMessageTestingOnly(self, "format: /cmdGmWeapon <command> (params)   OR   /cmdGmWeapon ? for help");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGMFsVillage(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        trace.log(fs_dyn_village.LOG_CHAN, "gm.cmdGMFsVillage -->> " + params, self, trace.TL_WARNING);
        if (params == null || params.length() < 1)
        {
            sendSystemMessageTestingOnly(self, "format: /gmFsVillage <command>    /gmFsVillage ? for help");
            return SCRIPT_CONTINUE;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        int tokens = st.countTokens();
        String command = null;
        if (st.hasMoreTokens())
        {
            command = st.nextToken();
        }
        if (command == null)
        {
            sendSystemMessageTestingOnly(self, "Format: /gmFsVillage <command> (params)    /gmFsVillage ? for help");
            return SCRIPT_CONTINUE;
        }
        if ((toLower(command)).equalsIgnoreCase("registermasterid"))
        {
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "You must target the village master object to do that.");
                return SCRIPT_CONTINUE;
            }
            if (!hasScript(target, fs_dyn_village.SCRIPT_FS_VILLAGE_MASTER))
            {
                sendSystemMessageTestingOnly(self, "You must target the village master object to do that.");
                return SCRIPT_CONTINUE;
            }
            fs_dyn_village.registerObjIdInClusterWideData(target, fs_dyn_village.CLUSTER_OBJID_KEY_MASTER, "msgIdRegistered", self);
            sendSystemMessageTestingOnly(self, "Object ID sent to cluster wide data for storage...");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equalsIgnoreCase("getmasterid"))
        {
            sendSystemMessageTestingOnly(self, "Fetching village master object id...");
            fs_dyn_village.getRegisteredObjIdFromClusterWideData(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER, "msgGotIdResponse", self);
        }
        else if ((toLower(command)).equalsIgnoreCase("getcurrentphase"))
        {
            sendSystemMessageTestingOnly(self, "Fetching current phase number...");
            fs_dyn_village.getRegisteredIntegerFromClusterWideData(fs_dyn_village.CLUSTER_INT_KEY_CUR_PHASE, "msgGotIntResponse", self);
        }
        else if ((toLower(command)).equalsIgnoreCase("getcurrentuid"))
        {
            sendSystemMessageTestingOnly(self, "Fetching current phase uid...");
            fs_dyn_village.getRegisteredIntegerFromClusterWideData(fs_dyn_village.CLUSTER_INT_KEY_PHASE_UID, "msgGotIntResponse", self);
        }
        else if ((toLower(command)).equalsIgnoreCase("destroyphaseobjects"))
        {
            if (getGodLevel(self) < 50)
            {
                sendSystemMessageTestingOnly(self, "You do not have sufficient privileges (god level 50) to destroy the village content.");
                return SCRIPT_CONTINUE;
            }
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "You must target the village master object to do that.");
                return SCRIPT_CONTINUE;
            }
            if (!hasScript(target, fs_dyn_village.SCRIPT_FS_VILLAGE_MASTER))
            {
                sendSystemMessageTestingOnly(self, "You must target the village master object to do that.");
                return SCRIPT_CONTINUE;
            }
            fs_dyn_village.destroyDynamicVillage(target);
            sendSystemMessageTestingOnly(self, "Phase objects destroyed.");
        }
        else if ((toLower(command)).equalsIgnoreCase("activatephase"))
        {
            if (getGodLevel(self) < 50)
            {
                sendSystemMessageTestingOnly(self, "You do not have sufficient privileges (god level 50) to activate village phases.");
                return SCRIPT_CONTINUE;
            }
            int phase = -1;
            if (st.hasMoreTokens())
            {
                phase = utils.stringToInt(st.nextToken());
            }
            if (phase < 1 || phase > fs_dyn_village.MAX_PHASE_NUMBER)
            {
                sendSystemMessageTestingOnly(self, "format: /gmFsVillage activatephase #	(#=1-" + fs_dyn_village.MAX_PHASE_NUMBER);
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(self, "gm.cmd.fs_village.wantPhase", phase);
            fs_dyn_village.getRegisteredObjIdFromClusterWideData(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER, "msgActivatePhase", self);
            sendSystemMessageTestingOnly(self, "Phase change to " + phase + " initiated.");
        }
        else if ((toLower(command)).equalsIgnoreCase("getwaypointdisk"))
        {
            sendSystemMessageTestingOnly(self, "Creating a waypoint datapad...");
            obj_id fdp = create.object("object/tangible/loot/quest/force_sensitive/camp_waypoint_datapad.iff", getObjectInSlot(self, "inventory"), false, false);
            if (!isIdNull(fdp))
            {
                sendSystemMessageTestingOnly(self, "Waypoint datapad created.");
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Datapad creation failed.  Overloaded inventory, maybe?.");
            }
        }
        else if ((toLower(command)).equalsIgnoreCase("getfrequencydisk"))
        {
            sendSystemMessageTestingOnly(self, "Creating a frequency datapad...");
            obj_id fdp = create.object("object/tangible/loot/quest/force_sensitive/camp_frequency_datapad.iff", getObjectInSlot(self, "inventory"), false, false);
            if (!isIdNull(fdp))
            {
                sendSystemMessageTestingOnly(self, "Frequency datapad created.");
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Datapad creation failed.  Overloaded inventory, maybe?.");
            }
        }
        else if ((toLower(command)).equalsIgnoreCase("getcampremote"))
        {
            String key = "alpha";
            if (st.hasMoreTokens())
            {
                key = st.nextToken();
                Vector campsDat = fs_counterstrike.getAllCampHintsFromDT();
                if (campsDat.size() != 2)
                {
                    trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::pickAndWriteCycleNamesAndLocs: -> getAllCampHints returned badly structured data. Camps not determine camp info for this cycle.", null, trace.TL_ERROR_LOG);
                    return SCRIPT_CONTINUE;
                }
                Vector names = (Vector)campsDat.get(1);
                if (names.indexOf(key) < 0)
                {
                    sendSystemMessageTestingOnly(self, "'" + key + "' is not a valid camp name.  Valid camp names are:");
                    for (int i = 0; i < names.size(); i++)
                    {
                        sendSystemMessageTestingOnly(self, (String)names.get(i));
                    }
                    return SCRIPT_CONTINUE;
                }
                sendSystemMessageTestingOnly(self, "Creating a remote for camp" + key + "...");
                obj_id remote = create.object("object/tangible/loot/quest/force_sensitive/camp_remote.iff", getObjectInSlot(self, "inventory"), false, false);
                if (!isIdNull(remote))
                {
                    sendSystemMessageTestingOnly(self, "Remote created.");
                    setName(remote, new string_id("fs_quest_village", "name_rem_" + key));
                    setObjVar(remote, fs_counterstrike.OBJVAR_CAMP_NAME, key);
                    utils.putInPlayerInventory(self, remote);
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "Remote creation failed.  Overloaded inventory, maybe?.");
                }
            }
        }
        else if ((toLower(command)).equalsIgnoreCase("resetplayerquest"))
        {
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "Must target a valid player for that command.");
                return SCRIPT_CONTINUE;
            }
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "Must target a valid player for that command.");
                return SCRIPT_CONTINUE;
            }
            String usage = "Usage:	resetPlayerQuest <3combat|all>";
            if (st.hasMoreTokens())
            {
                String questName = toLower(st.nextToken());
                if (questName.equalsIgnoreCase("3combat"))
                {
                    fs_counterstrike.resetPlayerToStart(target, null);
                }
                else if (questName.equalsIgnoreCase("all"))
                {
                    if (fs_quests.clearFSData(target))
                    {
                        sendSystemMessageTestingOnly(self, "All FS quest data cleared from " + target);
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "Data clear failed.");
                    }
                }
                else
                {
                    sendSystemMessageTestingOnly(self, usage);
                }
            }
            else
            {
                sendSystemMessageTestingOnly(self, usage);
            }
        }
        else if ((toLower(command)).equalsIgnoreCase("resetplayertask"))
        {
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "Must target a valid player for that command.");
                return SCRIPT_CONTINUE;
            }
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "Must target a valid player for that command.");
                return SCRIPT_CONTINUE;
            }
            if (!fs_quests.resetFSTask(target, self))
            {
                sendSystemMessageTestingOnly(self, "Unable to remove task due to error or player has no tasks to remove.");
            }
        }
        else if ((toLower(command)).equalsIgnoreCase("getenemycampinfo"))
        {
            fs_dyn_village.getRegisteredObjIdFromClusterWideData(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER, "msgGetCampInfo", self);
            sendSystemMessageTestingOnly(self, "Fetching enemy camp info...");
        }
        else if ((toLower(command)).equalsIgnoreCase("phasedurationinfo"))
        {
            fs_dyn_village.getRegisteredObjIdFromClusterWideData(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER, "msgGetDurationInfo", self);
            sendSystemMessageTestingOnly(self, "Fetching phase duration info...");
        }
        else if ((toLower(command)).equalsIgnoreCase("startvillage"))
        {
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "Must target a valid player for that command.");
                return SCRIPT_CONTINUE;
            }
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "Must target a valid player for that command.");
                return SCRIPT_CONTINUE;
            }
            fs_quests.makeVillageEligible(target);
            setJediState(target, JEDI_STATE_FORCE_SENSITIVE);
            grantSkill(target, "force_title_jedi_novice");
            if (!hasScript(target, "quest.force_sensitive.fs_kickoff"))
            {
                attachScript(target, "quest.force_sensitive.fs_kickoff");
            }
            setObjVar(target, "fs_kickoff_stage", 8);
            sendSystemMessageTestingOnly(self, target + " set to be village eligible.");
        }
        else if ((toLower(command)).equalsIgnoreCase("clearquestmarker"))
        {
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "Must target a valid player for that command.");
                return SCRIPT_CONTINUE;
            }
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "Must target a valid player for that command.");
                return SCRIPT_CONTINUE;
            }
            if (hasScript(target, "systems.fs_quest.fs_quest_player"))
            {
                detachScript(target, "systems.fs_quest.fs_quest_player");
            }
            sendSystemMessageTestingOnly(self, target + " quest acceptance for this phase has been cleared.");
        }
        else if ((toLower(command)).equalsIgnoreCase("unlockbranch"))
        {
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "Must target a valid player for that command.");
                return SCRIPT_CONTINUE;
            }
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "Must target a valid player for that command.");
                return SCRIPT_CONTINUE;
            }
            fs_quests.unlockBranchSUI(target, self);
        }
        else if ((toLower(command)).equalsIgnoreCase("makeresource"))
        {
            String type = null;
            if (st.hasMoreTokens())
            {
                type = st.nextToken();
            }
            int typeCrc = 0;
            if (type != null)
            {
                typeCrc = getStringCrc(type);
            }
            int template = 0;
            Vector attribs = new Vector();
            switch (typeCrc)
            {
                case (317755815):
                    template = (938424352);
                    attribs.add("crafting_components.res_conductivity");
                    attribs.add("crafting_components.res_decay_resist");
                    attribs.add("crafting_components.res_quality");
                    attribs.add("crafting_components.res_shock_resistance");
                    break;
                case (-1421690251):
                    template = (131111149);
                    attribs.add("crafting_components.res_potential_energy");
                    attribs.add("crafting_components.res_quality");
                    break;
                case (-1146082608):
                    template = (-1867424837);
                    attribs.add("crafting_components.res_malleability");
                    attribs.add("crafting_components.res_quality");
                    break;
                case (460212386):
                    template = (742613618);
                    attribs.add("crafting_components.res_potential_energy");
                    break;
                case (-847505801):
                default:
                    template = (530364865);
                    attribs.add("crafting_components.res_malleability");
                    attribs.add("crafting_components.res_quality");
                    attribs.add("crafting_components.res_toughness");
                    break;
            }
            obj_id inventory = utils.getInventoryContainer(self);
            obj_id resource = createObject(template, inventory, "");
            if (!isIdNull(resource))
            {
                for (int i = 0; i < attribs.size(); ++i)
                {
                    setObjVar(resource, (String)(attribs.get(i)), rand(500, 1000));
                }
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Error creating resource template crc " + template);
            }
        }
        else if (command.equalsIgnoreCase("?"))
        {
            sendSystemMessageTestingOnly(self, "Format: /gmFsVillage <command> (params)");
            sendSystemMessageTestingOnly(self, "	registerMasterId -- re-registers the master object id - disaster recovery only!");
            sendSystemMessageTestingOnly(self, "	getMasterId -- display the FS village master object id");
            sendSystemMessageTestingOnly(self, "	destroyPhaseObjects -- destroys all phase objects. (god level 50 - dangerous!)");
            sendSystemMessageTestingOnly(self, "	activatePhase # -- shifts the village into a different phase (god level 50 - dangerous!)");
            sendSystemMessageTestingOnly(self, "	getCurrentPhase  -- returns the currently active phase of the FS village");
            sendSystemMessageTestingOnly(self, "	getCurrentUid  -- returns the UID of the currently active phase of the FS village");
            sendSystemMessageTestingOnly(self, "	getEnemyCampInfo -- returns information on all spawned enemy camps (Phase 3 only)");
            sendSystemMessageTestingOnly(self, "	getCampRemote  <name> -- creates a remote in your pack to powerdown the named enemy camp");
            sendSystemMessageTestingOnly(self, "	getWaypointDisk <name> -- creates a datapad  with a random waypoint near one of phase 3 enemy camps");
            sendSystemMessageTestingOnly(self, "	getFrequencyDisk <name> -- creates a datapad good for one random remote near one of phase 3 enemy camps");
            sendSystemMessageTestingOnly(self, "	resetPlayerQuest <name> -- resets a player back to the first step of the quest");
            sendSystemMessageTestingOnly(self, "	startvillage -- Sets the target player to be eligible to take village quests.");
            sendSystemMessageTestingOnly(self, "	clearquestmarker -- Removes the marker that denotes the player as having completed a quest this phase.");
            sendSystemMessageTestingOnly(self, "	makeresource <name> -- Creates a pseudo-resource in your inventory.");
            sendSystemMessageTestingOnly(self, "	resource name = rudic, ardanium, ostrine, wind, or endrine");
            sendSystemMessageTestingOnly(self, "	phaseDurationInfo -- get info about current phase and expiriation info");
            sendSystemMessageTestingOnly(self, "    resetplayertask -- removes a completed or active task from the player.");
            sendSystemMessageTestingOnly(self, "    unlockbranch -- unlocks a branch on the specified player.");
            return SCRIPT_CONTINUE;
        }
        else
        {
            sendSystemMessageTestingOnly(self, "Format: /gmFsVillage <command> (params)    /gmFsVillage ? for help");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGMForceRank(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        LOG("force_rank", "gm.cmdGMForceRank -- " + params);
        if (params == null || params.length() < 1)
        {
            sendSystemMessageTestingOnly(self, "format: /gmForceRank <command>    /gmForceRank ? for help");
            return SCRIPT_CONTINUE;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        int tokens = st.countTokens();
        String command = st.nextToken();
        if ((toLower(command)).equalsIgnoreCase("promote"))
        {
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "You must have a target to do that.");
                return SCRIPT_CONTINUE;
            }
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "Your target must be a player.");
                return SCRIPT_CONTINUE;
            }
            if (!force_rank.isForceRanked(target))
            {
                sendSystemMessageTestingOnly(self, "Target is not force ranked.");
                return SCRIPT_CONTINUE;
            }
            int curRank = force_rank.getForceRank(target);
            int curXP = getExperiencePoints(target, force_rank.FRS_XP);
            if (force_rank.promoteForceRank(target))
            {
                int newXP = 0;
                if (curRank + 2 > force_rank.COUNCIL_RANK_NUMBER + 1)
                {
                    LOG("force_rank", "Min XP for rank " + (curRank + 1) + " is " + (force_rank.getForceRankMinXp(curRank + 1)) + ".");
                    newXP = (int)Math.floor(force_rank.getForceRankMinXp(curRank + 1) * 1.5f);
                }
                else
                {
                    LOG("force_rank", "Min XP for rank " + (curRank + 2) + " is " + (force_rank.getForceRankMinXp(curRank + 2)) + ".");
                    newXP = (int)Math.floor(force_rank.getForceRankMinXp(curRank + 2) * 1.5f);
                }
                force_rank.adjustForceRankXP(target, newXP - curXP);
                sendSystemMessageTestingOnly(self, "Promotion initiated.  This may take a minute or so.");
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Promotion failed.");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equalsIgnoreCase("demote"))
        {
            if (!isIdValid(target))
            {
                if (tokens < 2)
                {
                    sendSystemMessageTestingOnly(self, "You must have a target or specify a player name.");
                    return SCRIPT_CONTINUE;
                }
                else
                {
                    String player_name = st.nextToken();
                    if (player_name == null || player_name.length() < 1)
                    {
                        sendSystemMessageTestingOnly(self, "You must have a target or specify a player name.");
                        return SCRIPT_CONTINUE;
                    }
                    obj_id enclave = getTopMostContainer(self);
                    if (!isIdValid(enclave))
                    {
                        sendSystemMessageTestingOnly(self, "You must be in the enclave to demote a player by name.");
                        return SCRIPT_CONTINUE;
                    }
                    if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
                    {
                        sendSystemMessageTestingOnly(self, "You must be in the enclave to demote a player by name.");
                        return SCRIPT_CONTINUE;
                    }
                    int player_rank = force_rank.getForceRank(enclave, player_name);
                    if (player_rank < 1)
                    {
                        sendSystemMessageTestingOnly(self, "You cannot demote a player that is not of at least Rank 1.");
                        return SCRIPT_CONTINUE;
                    }
                    else
                    {
                        if (force_rank.demoteForceRank(enclave, player_name, player_rank - 1))
                        {
                            sendSystemMessageTestingOnly(self, "Demotion initiated.  This may take a minute or so.");
                        }
                        else
                        {
                            sendSystemMessageTestingOnly(self, "Demotion failed.");
                        }
                    }
                }
                return SCRIPT_CONTINUE;
            }
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "Your target must be a player.");
                return SCRIPT_CONTINUE;
            }
            if (!force_rank.isForceRanked(target))
            {
                sendSystemMessageTestingOnly(self, "Target is not force ranked.");
                return SCRIPT_CONTINUE;
            }
            if (force_rank.getForceRank(target) == 0)
            {
                sendSystemMessageTestingOnly(self, "You can't demote a player that is already rank 0.  Use 'remove' to remove the player from the FRS.");
                return SCRIPT_CONTINUE;
            }
            if (force_rank.demoteForceRank(target, force_rank.getForceRank(target) - 1))
            {
                sendSystemMessageTestingOnly(self, "Demotion initiated.  This may take a minute or so.");
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Demotion failed.");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equalsIgnoreCase("pulsemaint"))
        {
            int council;
            if (tokens < 2)
            {
                sendSystemMessageTestingOnly(self, "Format: /gmForceRank pulseMaint <council>    1 for DARK, 2 for LIGHT");
                return SCRIPT_CONTINUE;
            }
            else
            {
                council = utils.stringToInt(st.nextToken());
                if (council != 1 && council != 2)
                {
                    sendSystemMessageTestingOnly(self, "Format: /gmForceRank pulseMaint <council>    1 for DARK, 2 for LIGHT");
                    return SCRIPT_CONTINUE;
                }
                boolean rslt = force_rank.getEnclaveObjId(self, council, "cmdEnclaveIdForPulse");
                sendSystemMessageTestingOnly(self, "Maintenance pulse for council " + council + " initiated. This may take a moment...");
            }
        }
        else if ((toLower(command)).equalsIgnoreCase("add"))
        {
            int council;
            if (tokens < 2)
            {
                sendSystemMessageTestingOnly(self, "Format: /gmForceRank add <council>    1 for DARK, 2 for LIGHT");
                return SCRIPT_CONTINUE;
            }
            else
            {
                council = utils.stringToInt(st.nextToken());
                if (council != 1 && council != 2)
                {
                    sendSystemMessageTestingOnly(self, "Format: /gmForceRank add <council>    1 for DARK, 2 for LIGHT");
                    return SCRIPT_CONTINUE;
                }
            }
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "You must have a target to do that.");
                return SCRIPT_CONTINUE;
            }
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "Your target must be a player.");
                return SCRIPT_CONTINUE;
            }
            if (force_rank.isForceRanked(target))
            {
                sendSystemMessageTestingOnly(self, "Target is already force ranked.");
                return SCRIPT_CONTINUE;
            }
            if (force_rank.addToForceRankSystem(target, council))
            {
                sendSystemMessageTestingOnly(self, "Add to FRS complete.");
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Add to FRS failed.");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equalsIgnoreCase("remove"))
        {
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "You must have a target to do that.");
                return SCRIPT_CONTINUE;
            }
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "Your target must be a player.");
                return SCRIPT_CONTINUE;
            }
            if (!force_rank.isForceRanked(target))
            {
                sendSystemMessageTestingOnly(self, "Target must be rank 0 in order to remove.");
                return SCRIPT_CONTINUE;
            }
            if (force_rank.removeFromForceRankSystem(target, false))
            {
                sendSystemMessageTestingOnly(self, "Removal complete.");
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Removal failed.");
            }
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equalsIgnoreCase("deltaxp"))
        {
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "You must have a target to do that.");
                return SCRIPT_CONTINUE;
            }
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "Your target must be a player.");
                return SCRIPT_CONTINUE;
            }
            if (!force_rank.isForceRanked(target))
            {
                sendSystemMessageTestingOnly(self, "Target must already be in the force ranks for this command.");
                return SCRIPT_CONTINUE;
            }
            if (tokens < 2)
            {
                sendSystemMessageTestingOnly(self, "Format: /gmForceRank deltaXp <amount>  -- any positive or negative integer");
                return SCRIPT_CONTINUE;
            }
            String amount = st.nextToken();
            if (amount == null || amount.equalsIgnoreCase(""))
            {
                sendSystemMessageTestingOnly(self, "Format: /gmForceRank deltaXp <amount>  -- any positive or negative integer");
                return SCRIPT_CONTINUE;
            }
            int amt;
            try
            {
                amt = Integer.parseInt(amount);
            }
            catch(NumberFormatException err)
            {
                sendSystemMessageTestingOnly(self, "Format: /gmForceRank deltaXp <amount>  -- any positive or negative integer");
                return SCRIPT_CONTINUE;
            }
            int newXP = force_rank.adjustForceRankXP(target, amt);
            sendSystemMessageTestingOnly(self, "Done. " + utils.getRealPlayerFirstName(target) + " now has " + newXP + " Council XP.");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equalsIgnoreCase("resetenclave"))
        {
            if (getGodLevel(self) < 10)
            {
                sendSystemMessageTestingOnly(self, "You do not have sufficient privileges to reset an enclave.");
                return SCRIPT_CONTINUE;
            }
            obj_id enclave = getTopMostContainer(self);
            if (!isIdValid(enclave))
            {
                sendSystemMessageTestingOnly(self, "You must be in the enclave to reset it.");
                return SCRIPT_CONTINUE;
            }
            if (!hasScript(enclave, force_rank.SCRIPT_ENCLAVE_CONTROLLER))
            {
                sendSystemMessageTestingOnly(self, "You must be in the enclave to reset it.");
                return SCRIPT_CONTINUE;
            }
            removeObjVar(enclave, "force_rank.roster");
            removeObjVar(enclave, "force_rank.data");
            removeObjVar(enclave, "force_rank.voting");
            force_rank.resetEnclaveData(enclave);
            force_rank.resetClusterData(enclave);
            sendSystemMessageTestingOnly(self, "Enclave reset complete.");
            return SCRIPT_CONTINUE;
        }
        else if ((toLower(command)).equalsIgnoreCase("setchallengescore"))
        {
            if (getGodLevel(self) < 10)
            {
                sendSystemMessageTestingOnly(self, "You do not have sufficient privileges to change the challenge score.");
                return SCRIPT_CONTINUE;
            }
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "You must target the challenge terminal to do that.");
                return SCRIPT_CONTINUE;
            }
            if (!hasScript(target, "systems.gcw.terminal_frs_challenge"))
            {
                sendSystemMessageTestingOnly(self, "You must target the challenge terminal to do that.");
                return SCRIPT_CONTINUE;
            }
            if (tokens < 3)
            {
                sendSystemMessageTestingOnly(self, "Format: /gmForceRank setChallengeScore <rank 2-11> <score>");
                return SCRIPT_CONTINUE;
            }
            String strRank = st.nextToken();
            String strScore = st.nextToken();
            if (strRank == null || strScore == null || strRank.equalsIgnoreCase("") || strScore.equalsIgnoreCase(""))
            {
                sendSystemMessageTestingOnly(self, "Format: /gmForceRank setChallengeScore <rank 2-11> <score>");
                return SCRIPT_CONTINUE;
            }
            int rank;
            int score;
            try
            {
                rank = Integer.parseInt(strRank);
                score = Integer.parseInt(strScore);
            }
            catch(NumberFormatException err)
            {
                sendSystemMessageTestingOnly(self, "Format: /gmForceRank setChallengeScore <rank 2-11> <score>");
                return SCRIPT_CONTINUE;
            }
            int[] ranks = getIntArrayObjVar(target, arena.VAR_ALL_RANKS);
            int[] scores = getIntArrayObjVar(target, arena.VAR_RANK_CHALLENGE_SCORES);
            int rankIdx = utils.getElementPositionInArray(ranks, rank);
            if (rankIdx > -1)
            {
                scores[rankIdx] = score;
                setObjVar(target, arena.VAR_RANK_CHALLENGE_SCORES, scores);
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Format: /gmForceRank setChallengeScore <rank 2-11> <score>");
                return SCRIPT_CONTINUE;
            }
            trace.log("force_rank", "Challenge score for rank " + rank + " manually set to " + score + " by god client " + self, self, trace.TL_CS_LOG);
            sendSystemMessageTestingOnly(self, "Score adjusted. Done.");
        }
        else if (command.equalsIgnoreCase("?"))
        {
            sendSystemMessageTestingOnly(self, "Format: /gmForceRank <command> (params)");
            sendSystemMessageTestingOnly(self, "	add -- adds a player to the FRS");
            sendSystemMessageTestingOnly(self, "	remove -- removes a player to the FRS");
            sendSystemMessageTestingOnly(self, "	promote -- promotes a player one rank");
            sendSystemMessageTestingOnly(self, "	demote -- demotes a player one rank");
            sendSystemMessageTestingOnly(self, "	deltaXp -- grants/revokes council XP");
            sendSystemMessageTestingOnly(self, "    resetenclave -- reinitializes enclave. (God level 10)");
            sendSystemMessageTestingOnly(self, "    pulseMaint -- forces an enclave maintenance pulse. (EXPENSIVE!)");
            sendSystemMessageTestingOnly(self, "	setChallengeScore -- sets the challenge score for a given rank. (God level 10)");
            return SCRIPT_CONTINUE;
        }
        else
        {
            sendSystemMessageTestingOnly(self, "format: /gmForceRank <command>    /gmForceRank ? for help");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgGotIntResponse(obj_id self, dictionary params) throws InterruptedException
    {
        boolean rslt = false;
        String key = "";
        if (params.containsKey("key"))
        {
            key = params.getString("key");
        }
        if (params.containsKey("success"))
        {
            rslt = params.getBoolean("success");
        }
        if (rslt)
        {
            int val = -1;
            if (params.containsKey(key))
            {
                val = params.getInt(key);
                sendSystemMessageTestingOnly(self, "Object key '" + key + "' was registered as " + val);
            }
            return SCRIPT_CONTINUE;
        }
        else
        {
            sendSystemMessageTestingOnly(self, "Failed to find integer '" + key + "' cluster wide data.  Value might not have been registered yet.");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgGetDurationInfo(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER))
        {
            sendSystemMessageTestingOnly(self, "Couldn't find the master objid in the cluster data.");
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER);
        if (!isIdValid(target))
        {
            sendSystemMessageTestingOnly(self, "Village master object is not valid.");
            return SCRIPT_CONTINUE;
        }
        if (!exists(target))
        {
            sendSystemMessageTestingOnly(self, "Must be on the same server as the village master object.  Phase not changed");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(target, fs_dyn_village.SCRIPT_FS_VILLAGE_MASTER))
        {
            sendSystemMessageTestingOnly(self, "Objid returned by cluster data (" + target + ") is not a village master object.");
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(target, fs_dyn_village.OBJVAR_PHASE_START_TIME))
        {
            sendSystemMessageTestingOnly(self, "Master object does not have a start time for this phase.");
            return SCRIPT_CONTINUE;
        }
        int curPhase = fs_dyn_village.getCurrentPhaseAuth(target);
        int duration = fs_dyn_village.getPhaseDuration(curPhase);
        String durationTime = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(duration));
        int startTime = getIntObjVar(target, fs_dyn_village.OBJVAR_PHASE_START_TIME);
        String startedWhen = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(getGameTime() - startTime));
        String timeLeft = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(duration - (getGameTime() - startTime)));
        sendSystemMessageTestingOnly(self, "The current phase (" + curPhase + ") began " + startedWhen + " ago, is " + durationTime + " long, and thus is set to expire naturally in " + timeLeft);
        return SCRIPT_CONTINUE;
    }
    public int msgGetCampInfo(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER))
        {
            sendSystemMessageTestingOnly(self, "Couldn't find the master objid in the cluster data.");
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER);
        if (!isIdValid(target))
        {
            sendSystemMessageTestingOnly(self, "Village master object is not valid.");
            return SCRIPT_CONTINUE;
        }
        if (!exists(target))
        {
            sendSystemMessageTestingOnly(self, "Must be on the same server as the village master object.  Phase not changed");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(target, fs_dyn_village.SCRIPT_FS_VILLAGE_MASTER))
        {
            sendSystemMessageTestingOnly(self, "Objid returned by cluster data (" + target + ") is not a village master object.");
            return SCRIPT_CONTINUE;
        }
        int curPhase = fs_dyn_village.getCurrentPhaseAuth(target);
        if (curPhase != 3)
        {
            sendSystemMessageTestingOnly(self, "Enemy camp information only available during phase 3. Current phase is " + curPhase + ".");
            return SCRIPT_CONTINUE;
        }
        Vector campNames = new Vector();
        location[] campLocs = null;
        obj_id[] campIds = null;
        if (utils.hasScriptVar(target, fs_counterstrike.OBJVAR_CREATED_CAMP_IDS))
        {
            campIds = utils.getObjIdArrayScriptVar(target, fs_counterstrike.OBJVAR_CREATED_CAMP_IDS);
        }
        if (hasObjVar(target, fs_counterstrike.OBJVAR_PHASE_CAMP_NAMES))
        {
            campNames = getResizeableStringArrayObjVar(target, fs_counterstrike.OBJVAR_PHASE_CAMP_NAMES);
        }
        if (utils.hasScriptVar(target, fs_counterstrike.OBJVAR_CREATED_CAMP_LOCS))
        {
            campLocs = utils.getLocationArrayScriptVar(target, fs_counterstrike.OBJVAR_CREATED_CAMP_LOCS);
        }
        if (campIds == null)
        {
            campIds = new obj_id[campNames.size()];
        }
        if (campLocs == null)
        {
            campLocs = new location[campNames.size()];
        }
        String name = "";
        location loc = new location(0.0f, 0.0f, 0.0f);
        obj_id id = null;
        sendSystemMessageTestingOnly(self, "Enemy Camp Data:");
        sendSystemMessageTestingOnly(self, "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        for (int i = 0; i < campNames.size(); i++)
        {
            name = (String)campNames.get(i);
            if (campLocs.length > i)
            {
                loc = campLocs[i];
            }
            else
            {
                loc = null;
            }
            if (loc == null)
            {
                loc = new location(0.0f, 0.0f, 0.0f);
            }
            if (campIds.length > i)
            {
                id = campIds[i];
            }
            else
            {
                id = null;
            }
            sendSystemMessageTestingOnly(self, name + "	" + loc.toString() + "ID=" + id);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgActivatePhase(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "gm.cmd.fs_village.wantPhase"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER))
        {
            sendSystemMessageTestingOnly(self, "Couldn't find the master objid in the cluster data.");
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER);
        if (!isIdValid(target))
        {
            sendSystemMessageTestingOnly(self, "Village master object is not valid.");
            return SCRIPT_CONTINUE;
        }
        if (!exists(target))
        {
            sendSystemMessageTestingOnly(self, "Must be on the same server as the village master object.  Phase not changed");
            return SCRIPT_CONTINUE;
        }
        if (!hasScript(target, fs_dyn_village.SCRIPT_FS_VILLAGE_MASTER))
        {
            sendSystemMessageTestingOnly(self, "You must target the village master object to do that.");
            return SCRIPT_CONTINUE;
        }
        int phase = utils.getIntScriptVar(self, "gm.cmd.fs_village.wantPhase");
        fs_dyn_village.setNextPhaseAuth(target, phase);
        fs_dyn_village.pushAndInitPhase(target);
        sendSystemMessageGalaxyOob("If you are currently on a Force Sensititivy training quest, you should log out now and log back in at this time.  The village phase has been changed and you will not be able to complete your current quest at this time.");
        return SCRIPT_CONTINUE;
    }
    public int msgGotIdResponse(obj_id self, dictionary params) throws InterruptedException
    {
        boolean rslt = false;
        String key = "";
        if (params.containsKey("key"))
        {
            key = params.getString("key");
        }
        if (params.containsKey("success"))
        {
            rslt = params.getBoolean("success");
        }
        if (rslt)
        {
            obj_id id = null;
            if (params.containsKey(key))
            {
                id = params.getObjId(key);
                sendSystemMessageTestingOnly(self, "Object key '" + key + "' was registered as " + id);
            }
            return SCRIPT_CONTINUE;
        }
        else
        {
            sendSystemMessageTestingOnly(self, "Failed to find obj id '" + key + "' cluster wide data.  Id might not have been registered yet.");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgIdRegistered(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey("success"))
        {
            sendSystemMessageTestingOnly(self, "Error registering id.");
            return SCRIPT_CONTINUE;
        }
        boolean rslt = params.getBoolean("success");
        obj_id id = params.getObjId("registeredId");
        String key = params.getString("key");
        if (rslt)
        {
            sendSystemMessageTestingOnly(self, "Successfully registered " + id + " under key " + key);
        }
        else
        {
            sendSystemMessageTestingOnly(self, "FAILED to register " + id + " under key " + key);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgResetFSTask(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, fs_quests.SCRIPT_VAR_REMOVE_TASK_PLAYER);
        int[] tasks = utils.getIntBatchScriptVar(self, fs_quests.SCRIPT_VAR_REMOVE_TASK_LIST);
        utils.removeScriptVar(self, fs_quests.SCRIPT_VAR_REMOVE_TASK_PLAYER);
        utils.removeScriptVar(self, fs_quests.SCRIPT_VAR_REMOVE_TASK_LIST);
        utils.removeScriptVar(self, fs_quests.SCRIPT_VAR_REMOVE_TASK_SUI);
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "cmd.msgRemoveFSTask -- player is invalid.");
            return SCRIPT_CONTINUE;
        }
        if (tasks == null)
        {
            LOG("force_sensitive", "cmd.msgRemoveFSTask -- tasks is null.");
        }
        String button = params.getString("buttonPressed");
        if (button.equalsIgnoreCase("Cancel") || button.equalsIgnoreCase("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (row_selected >= tasks.length)
        {
            return SCRIPT_CONTINUE;
        }
        int task_selected = tasks[row_selected];
        String task_name = quests.getDataEntry(task_selected, "NAME");
        if (isQuestActive(player, task_selected))
        {
            quests.deactivate(task_name, player);
        }
        clearCompletedQuest(player, task_selected);
        unassignTheaterFromPlayer(player);
        sendSystemMessageTestingOnly(self, "Task " + task_name + " reset on " + getName(player));
        CustomerServiceLog("fs_quests", "GM %TU resetting task " + task_name + " on %TT.", self, player);
        return SCRIPT_CONTINUE;
    }
    public int msgCSUnlockBranch(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, fs_quests.SCRIPT_VAR_UNLOCK_BRANCH_PLAYER);
        utils.removeScriptVar(self, fs_quests.SCRIPT_VAR_UNLOCK_BRANCH_SUI);
        utils.removeScriptVar(self, fs_quests.SCRIPT_VAR_UNLOCK_BRANCH_PLAYER);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equalsIgnoreCase("Cancel") || button.equalsIgnoreCase("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        String branch = fs_quests.getBranchFromId(row_selected);
        if (branch == null)
        {
            sendSystemMessageTestingOnly(self, "Can find the selected branch");
            return SCRIPT_CONTINUE;
        }
        fs_quests.unlockBranch(player, branch);
        sendSystemMessageTestingOnly(self, "You unlock branch " + branch + " on " + getName(player));
        return SCRIPT_CONTINUE;
    }
    public int cmdEnclaveIdForPulse(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey("enclave"))
        {
            sendSystemMessageTestingOnly(self, "Error retrieving enclave id.  Pulse not fired.");
            return SCRIPT_CONTINUE;
        }
        obj_id enclave = params.getObjId("enclave");
        if (isIdNull(enclave))
        {
            sendSystemMessageTestingOnly(self, "Error retrieving enclave id.  Pulse not fired.");
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(self, "Pulsing " + enclave + ". . .");
        force_rank.performEnclaveMaintenance(enclave);
        return SCRIPT_CONTINUE;
    }
    public int cmdHasVeteranReward(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!("true").equalsIgnoreCase(getConfigSetting("GameServer", "enableVeteranRewards")))
        {
            return SCRIPT_CONTINUE;
        }
        if (isGod(self) && veteran_deprecated.checkVeteranTarget(target))
        {
            if (params == null || params.length() == 0)
            {
                sendSystemMessageTestingOnly(self, "format: /hasVeteranReward <target> <milestone>");
                return SCRIPT_CONTINUE;
            }
            int milestone = parseMilestone(params);
            if (milestone >= 1 && milestone <= veteran_deprecated.MAX_MILESTONE)
            {
                if (veteran_deprecated.checkVeteranMilestone(target, milestone))
                {
                    sendSystemMessage(self, veteran_deprecated.SID_HAS_MILESTONE);
                }
                else
                {
                    sendSystemMessage(self, veteran_deprecated.SID_HAS_NOT_MILESTONE);
                }
            }
            else
            {
                sendSystemMessage(self, veteran_deprecated.SID_MILESTONE_OUT_OF_RANGE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdSetVeteranReward(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!("true").equalsIgnoreCase(getConfigSetting("GameServer", "enableVeteranRewards")))
        {
            return SCRIPT_CONTINUE;
        }
        if (isGod(self) && veteran_deprecated.checkVeteranTarget(target))
        {
            if (params == null || params.length() == 0)
            {
                sendSystemMessageTestingOnly(self, "format: /setVeteranReward <target> <milestone>");
                return SCRIPT_CONTINUE;
            }
            int milestone = parseMilestone(params);
            if (milestone >= 1 && milestone <= veteran_deprecated.MAX_MILESTONE)
            {
                CustomerServiceLog("veteran", "GM %TU setting player %TT milestone " + milestone, self, target);
                veteran_deprecated.setVeteranMilestone(target, milestone);
                sendSystemMessage(self, veteran_deprecated.SID_OK);
            }
            else
            {
                sendSystemMessage(self, veteran_deprecated.SID_MILESTONE_OUT_OF_RANGE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdClearVeteranReward(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!("true").equalsIgnoreCase(getConfigSetting("GameServer", "enableVeteranRewards")))
        {
            return SCRIPT_CONTINUE;
        }
        if (isGod(self) && veteran_deprecated.checkVeteranTarget(target))
        {
            if (params == null || params.length() == 0)
            {
                sendSystemMessageTestingOnly(self, "format: /clearVeteranReward <target> <milestone>");
                return SCRIPT_CONTINUE;
            }
            int milestone = parseMilestone(params);
            if (milestone >= 1 && milestone <= veteran_deprecated.MAX_MILESTONE)
            {
                CustomerServiceLog("veteran", "GM %TU clearing player %TT milestone " + milestone, self, target);
                veteran_deprecated.clearVeteranMilestone(target, milestone);
                sendSystemMessage(self, veteran_deprecated.SID_OK);
            }
            else
            {
                sendSystemMessage(self, veteran_deprecated.SID_MILESTONE_OUT_OF_RANGE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdOverrideActiveMonths(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!("true").equalsIgnoreCase(getConfigSetting("GameServer", "enableVeteranRewards")))
        {
            return SCRIPT_CONTINUE;
        }
        if (isGod(self))
        {
            if (params == null || params.length() == 0)
            {
                sendSystemMessageTestingOnly(self, "format: /overrideActiveMonths <months>");
                return SCRIPT_CONTINUE;
            }
            int months = parseMilestone(params);
            if (months <= 0)
            {
                removeObjVar(self, veteran_deprecated.OBJVAR_FAKE_VETERAN);
                removeObjVar(self, veteran_deprecated.OBJVAR_TIME_ACTIVE);
                sendSystemMessage(self, veteran_deprecated.SID_ACTIVE_MONTHS_CLEARED);
            }
            else
            {
                int days = months * veteran_deprecated.DAYS_PER_MONTH;
                setObjVar(self, veteran_deprecated.OBJVAR_FAKE_VETERAN_TOTAL_TIME, days);
                setObjVar(self, veteran_deprecated.OBJVAR_FAKE_VETERAN_ENTITLED_TIME, days);
                setObjVar(self, veteran_deprecated.OBJVAR_FAKE_VETERAN_LOGIN_TIME, 0);
                setObjVar(self, veteran_deprecated.OBJVAR_FAKE_VETERAN_ENTITLED_LOGIN_TIME, 0);
                setObjVar(self, veteran_deprecated.OBJVAR_TIME_ACTIVE, days);
                sendSystemMessage(self, veteran_deprecated.SID_OK);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int parseMilestone(String params) throws InterruptedException
    {
        String milestone;
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        switch (st.countTokens())
        {
            case 1:
                milestone = st.nextToken();
                break;
            case 2:
                st.nextToken();
                milestone = st.nextToken();
                break;
            default:
                return -1;
        }
        return (Integer.valueOf(milestone)).intValue();
    }
    public int cmdResetJedi(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target) || params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /resetJedi -target (with lookat target)");
            return SCRIPT_CONTINUE;
        }
        if (params.indexOf(gm.KEYWORD_TARGET) > -1)
        {
            params = gm.removeKeyword(params, gm.KEYWORD_TARGET);
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /resetJedi -target (with lookat target)");
            return SCRIPT_CONTINUE;
        }
        gm.cmdResetJedi(target);
        return SCRIPT_CONTINUE;
    }
    public int cmdDeactivateQuest(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target) || params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[syntax] /deactivateQuest <questName>");
        }
        else
        {
            LOG("newquests", "deactivating quest " + params + " for " + target);
            int id = quests.getQuestId(params);
            if (id > -1)
            {
                quests.deactivate(params, target);
                sendSystemMessageTestingOnly(self, "deactivated quest " + params);
            }
            else
            {
                sendSystemMessageTestingOnly(self, "failed to find quest " + params);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdActivateQuest(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target) || params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[syntax] /activateQuest <questName>");
        }
        else
        {
            LOG("newquests", "activating quest " + params + " for " + target);
            int id = quests.getQuestId(params);
            if (id > -1)
            {
                quests.activate(params, target, null);
                sendSystemMessageTestingOnly(self, "activated quest " + params);
            }
            else
            {
                sendSystemMessageTestingOnly(self, "failed to find quest " + params);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdListActiveQuests(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target))
        {
            sendSystemMessageTestingOnly(self, "[syntax] /listActiveQuests");
        }
        else
        {
            String datatable = "datatables/player/quests.iff";
            int rows = dataTableGetNumRows(datatable);
            if (rows > 0)
            {
                int iter = 0;
                int active = 0;
                for (iter = 0; iter < rows; ++iter)
                {
                    if (isQuestActive(target, iter))
                    {
                        active++;
                        String questName = quests.getDataEntry(iter, "NAME");
                        String questSummary = quests.getDataEntry(iter, "JOURNAL_ENTRY_SUMMARY");
                        String msg = "Name: ";
                        if (questName != null && questName.length() > 0)
                        {
                            msg += questName;
                        }
                        else
                        {
                            msg += "unknown";
                        }
                        msg += " Summary: ";
                        if (questSummary != null && questSummary.length() > 0)
                        {
                            msg += questSummary;
                        }
                        else
                        {
                            msg += "missing summary string";
                        }
                        sendSystemMessageTestingOnly(self, msg);
                    }
                }
                if (active < 1)
                {
                    sendSystemMessageTestingOnly(self, "no active quests");
                }
            }
            else
            {
                sendSystemMessageTestingOnly(self, "no active quests");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdClearCompletedQuest(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        LOG("newquests", "received /clearCompletedQuest");
        if (!isIdValid(target) || !isPlayer(target) || params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[syntax] /clearCompletedQuest <questName>");
        }
        else
        {
            LOG("newquests", "clearing quest " + params + " for " + target);
            int id = quests.getQuestId(params);
            if (id > -1)
            {
                clearCompletedQuest(target, id);
                sendSystemMessageTestingOnly(self, "cleared quest " + params);
                LOG("newquests", "cleared quest " + params + " for player " + target);
            }
            else
            {
                sendSystemMessageTestingOnly(self, "failed to find quest " + params);
                LOG("newquests", "failed to clear quest " + params + " for player " + target);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdCompleteQuest(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target) || params == null || params.equalsIgnoreCase(""))
        {
            sendSystemMessageTestingOnly(self, "[syntax] /deactivateQuest <questName>");
        }
        else
        {
            LOG("newquests", "deactivating quest " + params + " for " + target);
            int id = quests.getQuestId(params);
            if (id > -1)
            {
                quests.complete(params, target, true);
                sendSystemMessageTestingOnly(self, "completed quest " + params);
            }
            else
            {
                sendSystemMessageTestingOnly(self, "failed to find quest " + params);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdListCompletedQuests(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target))
        {
            sendSystemMessageTestingOnly(self, "[syntax] /listActiveQuests");
        }
        else
        {
            String datatable = "datatables/player/quests.iff";
            int rows = dataTableGetNumRows(datatable);
            if (rows > 0)
            {
                int iter = 0;
                int completed = 0;
                for (iter = 0; iter < rows; ++iter)
                {
                    if (isQuestComplete(target, iter))
                    {
                        completed++;
                        String questName = quests.getDataEntry(iter, "NAME");
                        String questSummary = quests.getDataEntry(iter, "JOURNAL_ENTRY_SUMMARY");
                        String msg = "Name: ";
                        if (questName != null && questName.length() > 0)
                        {
                            msg += questName;
                        }
                        else
                        {
                            msg += "unknown";
                        }
                        msg += " Summary: ";
                        if (questSummary != null && questSummary.length() > 0)
                        {
                            msg += questSummary;
                        }
                        else
                        {
                            msg += "missing summary string";
                        }
                        sendSystemMessageTestingOnly(self, msg);
                    }
                }
                if (completed < 1)
                {
                    sendSystemMessageTestingOnly(self, "no complete quests");
                }
            }
            else
            {
                sendSystemMessageTestingOnly(self, "no complete quests");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGrantPadawanTrialsEligibility(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        String[] fs_skills =
                {
                        "force_title_jedi_novice",
                        "force_sensitive_combat_prowess_novice",
                        "force_sensitive_combat_prowess_melee_accuracy_01",
                        "force_sensitive_combat_prowess_melee_accuracy_02",
                        "force_sensitive_combat_prowess_melee_accuracy_03",
                        "force_sensitive_combat_prowess_melee_accuracy_04",
                        "force_sensitive_combat_prowess_melee_speed_01",
                        "force_sensitive_combat_prowess_melee_speed_02",
                        "force_sensitive_combat_prowess_melee_speed_03",
                        "force_sensitive_combat_prowess_melee_speed_04",
                        "force_sensitive_enhanced_reflexes_novice",
                        "force_sensitive_enhanced_reflexes_ranged_defense_01",
                        "force_sensitive_enhanced_reflexes_ranged_defense_02",
                        "force_sensitive_enhanced_reflexes_ranged_defense_03",
                        "force_sensitive_enhanced_reflexes_ranged_defense_04",
                        "force_sensitive_enhanced_reflexes_melee_defense_01",
                        "force_sensitive_enhanced_reflexes_melee_defense_02",
                        "force_sensitive_enhanced_reflexes_melee_defense_03",
                        "force_sensitive_enhanced_reflexes_melee_defense_04",
                        "force_sensitive_heightened_senses_novice",
                        "force_sensitive_heightened_senses_surveying_01",
                        "force_sensitive_heightened_senses_surveying_02",
                        "force_sensitive_heightened_senses_surveying_03",
                        "force_sensitive_heightened_senses_surveying_04",
                        "force_sensitive_heightened_senses_luck_01",
                        "force_sensitive_heightened_senses_luck_02",
                        "force_sensitive_heightened_senses_luck_03",
                        "force_sensitive_heightened_senses_luck_04"
                };
        fs_quests.makeVillageEligible(self);
        setJediState(self, JEDI_STATE_FORCE_SENSITIVE);
        for (int i = 0; i < fs_skills.length; i++)
        {
            String skill = fs_skills[i];
            if (!hasSkill(self, skill))
            {
                grantSkill(self, skill);
            }
        }
        setObjVar(self, jedi_trials.PADAWAN_TRIALS_ELIGIBLE_OBJVAR, true);
        if (hasObjVar(self, "overridePTEligibility"))
        {
            removeObjVar(self, "overridePTEligibility");
        }
        String str_msg = "You have been granted eligibility for the Jedi Padawan trials." + " This included being given the required skills and flags that mark eligibility.";
        sendSystemMessage(self, str_msg, "");
        return SCRIPT_CONTINUE;
    }
    public int cmdGetGameTime(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        sendSystemMessage(self, "The time is " + getGameTime(), null);
        return SCRIPT_CONTINUE;
    }
    public int gmJediState(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params == null || params.length() < 1)
        {
            sendSystemMessageTestingOnly(self, "format: /gmJediState <command>    /gmJediState ? for help");
            return SCRIPT_CONTINUE;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        int tokens = st.countTokens();
        String command = st.nextToken();
        if ((toLower(command)).equalsIgnoreCase("getstate"))
        {
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "You must specify a target.");
                return SCRIPT_CONTINUE;
            }
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "Your target must be a player.");
                return SCRIPT_CONTINUE;
            }
            int jedi_state = getJediState(target);
            switch (jedi_state)
            {
                case JEDI_STATE_FORCE_SENSITIVE:
                    sendSystemMessageTestingOnly(self, getFirstName(target) + " is Force-sensitive.");
                    break;
                case JEDI_STATE_JEDI:
                    sendSystemMessageTestingOnly(self, getFirstName(target) + " is a Jedi.");
                    break;
                case JEDI_STATE_FORCE_RANKED_LIGHT:
                    sendSystemMessageTestingOnly(self, getFirstName(target) + " is a Light Jedi Knight.");
                    break;
                case JEDI_STATE_FORCE_RANKED_DARK:
                    sendSystemMessageTestingOnly(self, getFirstName(target) + " is a Dark Jedi Knight.");
                    break;
                default:
                    sendSystemMessageTestingOnly(self, getFirstName(target) + " is not a Jedi.");
                    break;
            }
            sendSystemMessageTestingOnly(self, "(isJedi returns " + isJedi(target) + ")");
            return SCRIPT_CONTINUE;
        }
        if ((toLower(command)).equalsIgnoreCase("setjedi"))
        {
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "You must specify a target.");
                return SCRIPT_CONTINUE;
            }
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "Your target must be a player.");
                return SCRIPT_CONTINUE;
            }
            if (getJediState(target) != JEDI_STATE_FORCE_SENSITIVE)
            {
                sendSystemMessageTestingOnly(self, "Your target must be Force-sensitive to use this command.");
                return SCRIPT_CONTINUE;
            }
            setJediState(target, JEDI_STATE_JEDI);
            sendSystemMessageTestingOnly(target, getFirstName(target) + "'s state has be set to Jedi.");
            return SCRIPT_CONTINUE;
        }
        if ((toLower(command)).equalsIgnoreCase("?"))
        {
            sendSystemMessageTestingOnly(self, "format: /gmJediState <command>    /gmJediState ? for help");
            sendSystemMessageTestingOnly(self, "   getState  -- returns the Jedi state of the player");
            sendSystemMessageTestingOnly(self, "   setJedi  -- sets the Jedi state of a Force-sensitive player.  Note that this should only be used to fix a Jedi who can't tune a crystal.");
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(self, "format: /gmJediState <command>    /gmJediState ? for help");
        return SCRIPT_CONTINUE;
    }
    public int cmdSetGroupXPBonus(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        int tokens = st.countTokens();
        float bonus = (Float.valueOf(st.nextToken())).floatValue();
        if (bonus <= 0)
        {
            sendSystemMessageTestingOnly(self, "You must specify an argument that is a positive xp bonus. (i.e.: 0.033)");
            return SCRIPT_CONTINUE;
        }
        if (bonus >= 0.3)
        {
            sendSystemMessageTestingOnly(self, "That bonus is too large. It can't be more than 0.3.");
            return SCRIPT_CONTINUE;
        }
        obj_id objGroup = getGroupObject(self);
        if (!isIdValid(objGroup))
        {
            sendSystemMessageTestingOnly(self, "You have to be in a group with other players to set a test group xp bonus.");
            return SCRIPT_CONTINUE;
        }
        obj_id[] members = getGroupMemberIds(objGroup);
        if (members == null || members.length == 0)
        {
            sendSystemMessageTestingOnly(self, "You have to be in a group with other players to set a test group xp bonus.");
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < members.length; i++)
        {
            if (members[i].isLoaded() && isPlayer(members[i]))
            {
                utils.setScriptVar(members[i], "__groupXPBonus", bonus);
            }
        }
        sendSystemMessageTestingOnly(self, "Test group XP bonus set to " + bonus);
        return SCRIPT_CONTINUE;
    }
    public int cmdCreateStaticItem(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (params == null || params.length() < 1)
        {
            sendSystemMessageTestingOnly(self, "format: /CreateStaticItem <NAME> <COUNT>");
            sendSystemMessageTestingOnly(self, "<COUNT> will only be applied on static items that auto stack.");
            return SCRIPT_CONTINUE;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        int tokens = st.countTokens();
        String itemName = null;
        if (st.hasMoreTokens())
        {
            itemName = st.nextToken();
        }
        int count = 0;
        if (st.hasMoreTokens())
        {
            count = utils.stringToInt(st.nextToken());
        }
        if (itemName == null)
        {
            sendSystemMessageTestingOnly(self, "format: /CreateStaticItem <NAME> <COUNT>");
            sendSystemMessageTestingOnly(self, "<COUNT> will only be applied on static items that auto stack.");
            return SCRIPT_CONTINUE;
        }
        obj_id staticItemId = obj_id.NULL_ID;
        dictionary staticItemData = static_item.getMasterItemDictionary(itemName);
        if (staticItemData == null || staticItemData.isEmpty())
        {
            sendSystemMessageTestingOnly(self, "Error creating static item with supplied NAME (" + itemName + ").");
            CustomerServiceLog("loot", "CSR: (" + getFirstName(self) + ")(" + self + ") tried to create  a static item but the Item Name was bad (" + itemName + ")");
        }
        else
        {
            obj_id inventory = utils.getInventoryContainer(self);
            boolean canAutoStack = false;
            if (staticItemData.containsKey("scripts"))
            {
                String staticItemScriptList = staticItemData.getString("scripts");
                if (staticItemScriptList != null && staticItemScriptList.length() > 0 && staticItemScriptList.indexOf("autostack") > -1)
                {
                    canAutoStack = true;
                }
            }
            if (canAutoStack && count > 0)
            {
                for (int i = count; i > 0; i = i - 500)
                {
                    int tempCount = 500;
                    if (i < 500)
                    {
                        tempCount = i;
                    }
                    staticItemId = static_item.createNewItemFunction(itemName, inventory, tempCount);
                }
            }
            else
            {
                staticItemId = static_item.createNewItemFunction(itemName, inventory);
            }
            if (isIdValid(staticItemId))
            {
                if (canAutoStack && count > 0)
                {
                    sendSystemMessageTestingOnly(self, "Static Item " + itemName + " (x" + count + ") created in your inventory.");
                    CustomerServiceLog("loot", "CSR: (" + getFirstName(self) + ")(" + self + ") has created (" + itemName + " [x" + count + "])");
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "Static Item " + itemName + " created in your inventory.");
                    CustomerServiceLog("loot", "CSR: (" + getFirstName(self) + ")(" + self + ") has created (" + itemName + ")");
                }
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Error creating static item with supplied NAME (" + itemName + ").");
                CustomerServiceLog("loot", "CSR: (" + getFirstName(self) + ")(" + self + ") tried to create  a static item but the Item Name was bad (" + itemName + ")");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdDumpTargetInformation(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "targetis " + params);
        if (!isIdValid(target))
        {
            sendSystemMessageTestingOnly(self, "params is " + params);
            if ((params != null) && (!params.equals("")))
            {
                target = utils.stringToObjId(params);
                sendSystemMessageTestingOnly(self, "target is " + target);
            }
        }
        if (!isIdValid(target))
        {
            target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "Target something before using this command");
                return SCRIPT_CONTINUE;
            }
        }
        obj_id objTarget = target;
        String strFileName = "";
        if (!params.equals(""))
        {
            strFileName = params;
        }
        if (!strFileName.endsWith(".txt"))
        {
            strFileName += ".txt";
        }
        String strTest = dump.getTargetInfoString(objTarget);
        if (!strFileName.equals(""))
        {
            saveTextOnClient(self, strFileName, strTest);
        }
        if (isGod(self))
        {
            sendConsoleMessage(self, strTest);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdDumpObjectInformation(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        obj_id oid = obj_id.NULL_ID;
        String filename = null;
        if (st.hasMoreTokens())
        {
            String oidString = st.nextToken();
            oid = utils.stringToObjId(oidString);
        }
        if (st.hasMoreTokens())
        {
            filename = st.nextToken();
        }
        if (isIdValid(oid))
        {
            if (exists(oid))
            {
                String dumpString = dump.getTargetInfoString(oid);
                debugConsoleMsg(self, dumpString);
                if (filename != null)
                {
                    if (!filename.endsWith(".txt"))
                    {
                        filename += ".txt";
                    }
                    saveTextOnClient(self, filename, dumpString);
                }
            }
            else
            {
                sendSystemMessageTestingOnly(self, "/dumpObjectInformation: oid " + oid + " does not exist on this server");
            }
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /dumpObjectInformation <oid> [filename]");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdNpeGotoMedicalBay(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "Sending you to a medical bay instance");
        sendPlayerToTutorial(self);
        return SCRIPT_CONTINUE;
    }
    public int cmdNpeGotoMilleniumFalcon(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "Sending you to a millenium falcon instance");
        npe.movePlayerFromHangarToFalcon(self);
        return SCRIPT_CONTINUE;
    }
    public int cmdNpeGotoTansariiStation(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        int instanceId = 0;
        if (st.hasMoreTokens())
        {
            instanceId = utils.stringToInt(st.nextToken());
            sendSystemMessageTestingOnly(self, "Sending you to Tansarii station instance number " + instanceId);
        }
        else
        {
            sendSystemMessageTestingOnly(self, "Sending you to Tansarii station instance number 0. Pass in the index to a specific instance if you are looking for a specific one.");
        }
        utils.setScriptVar(self, npe.SCRIPT_VAR_INSTANCE_OVERRIDE, instanceId);
        npe.movePlayerFromFalconToSharedStation(self);
        return SCRIPT_CONTINUE;
    }
    public int cmdNpeGotoStationGamma(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        int instanceId = 0;
        if (st.hasMoreTokens())
        {
            instanceId = utils.stringToInt(st.nextToken());
            sendSystemMessageTestingOnly(self, "Sending you to station Gamma instance number " + instanceId);
        }
        else
        {
            sendSystemMessageTestingOnly(self, "Sending you to station Gamma instance number 0. Pass in the index to a specific instance if you are looking for a specific one.");
        }
        utils.setScriptVar(self, npe.SCRIPT_VAR_INSTANCE_OVERRIDE, instanceId);
        npe.movePlayerFromOrdMantellSpaceToOrdMantellDungeon(self);
        return SCRIPT_CONTINUE;
    }
    public int cmdStartRestussStageTwo(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        attachScript(self, "systems.cw_data.cluster_wide_response_manager");
        getClusterWideData("event", "restuss_event", true, self);
        return SCRIPT_CONTINUE;
    }
    public int cmdCompleteRestussStageOne(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id[] objects = getAllObjectsWithObjVar(getLocation(self), 200.0f, "element");
        if (objects == null || objects.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < objects.length; i++)
        {
            if (!hasObjVar(objects[i], "element"))
            {
                continue;
            }
            String element = getStringObjVar(objects[i], "element");
            if (!element.startsWith("ph1"))
            {
                continue;
            }
            if (element.equals("ph1_restuss_master"))
            {
                continue;
            }
            if (element.indexOf("wall") > -1 || element.indexOf("medic") > -1)
            {
                continue;
            }
            if (element.indexOf("baracks") > -1)
            {
                messageTo(objects[i], "incrimentPhase", null, 0, false);
                messageTo(objects[i], "incrimentPhase", null, 20, false);
                messageTo(objects[i], "incrimentPhase", null, 40, false);
                continue;
            }
            if (element.indexOf("headq") > -1)
            {
                messageTo(objects[i], "incrimentPhase", null, 5, false);
                messageTo(objects[i], "incrimentPhase", null, 25, false);
                messageTo(objects[i], "incrimentPhase", null, 45, false);
                continue;
            }
            if (element.indexOf("commu") > -1)
            {
                messageTo(objects[i], "incrimentPhase", null, 10, false);
                messageTo(objects[i], "incrimentPhase", null, 30, false);
                messageTo(objects[i], "incrimentPhase", null, 50, false);
                continue;
            }
            if (element.indexOf("logis") > -1)
            {
                messageTo(objects[i], "incrimentPhase", null, 15, false);
                messageTo(objects[i], "incrimentPhase", null, 35, false);
                messageTo(objects[i], "incrimentPhase", null, 55, false);
                continue;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGmShowQuest(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        StringTokenizer st = new java.util.StringTokenizer(params);
        String playerOid = "";
        String logFile = "";
        if (st.hasMoreTokens())
        {
            playerOid = st.nextToken();
        }
        if (st.hasMoreTokens())
        {
            logFile = st.nextToken();
        }
        if (!playerOid.equals(""))
        {
            obj_id targetOid = utils.stringToObjId(playerOid);
            if (!isIdNull(targetOid))
            {
                String[] listQuests = gm.getAllQuests(targetOid);
                String combinedQuestList = "";
                if (listQuests != null)
                {
                    for (int i = 0; i < listQuests.length; i++)
                    {
                        combinedQuestList += listQuests[i] + "\n";
                    }
                    if (!combinedQuestList.equals(""))
                    {
                        if (logFile.equals("log"))
                        {
                            String dumpString = dump.getTargetInfoString(targetOid);
                            dumpString += "\n---------------------GM QUEST TOOL DATA------------------";
                            dumpString += "\n" + combinedQuestList;
                            String title = "questStringAndPlayerDump" + targetOid + "_" + getGameTime();
                            saveTextOnClient(self, title + ".txt", dumpString);
                        }
                        gm.createCustomUI(self, "Quest List", combinedQuestList);
                        CustomerServiceLog("gmQuest", "CSR: (" + self + ") " + getName(self) + " has accessed quests (" + combinedQuestList + ") from (" + targetOid + ") " + utils.getStringName(targetOid));
                    }
                }
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Please check the OID and try again");
            }
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /gmshowquest <player OID>");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGmClearQuest(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        StringTokenizer st = new java.util.StringTokenizer(params);
        String playerOid = "";
        String questVar = "";
        if (st.hasMoreTokens())
        {
            playerOid = st.nextToken();
        }
        if (st.hasMoreTokens())
        {
            questVar = st.nextToken();
        }
        if (!playerOid.equals("") && !questVar.equals(""))
        {
            obj_id targetOid = utils.stringToObjId(playerOid);
            if (!isIdNull(targetOid))
            {
                gm.forceClearQuest(targetOid, questVar);
                CustomerServiceLog("gmQuest", "CSR: (" + self + ") " + getName(self) + " has cleared (or attempted to clear) quest (" + questVar + ") from (" + targetOid + ") " + utils.getStringName(targetOid));
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Please check the OID and try again");
            }
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /gmclearquest <player OID> <quest/code_string>");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdGmRegrantQuest(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        StringTokenizer st = new java.util.StringTokenizer(params);
        String playerOid = "";
        String questVar = "";
        if (st.hasMoreTokens())
        {
            playerOid = st.nextToken();
        }
        if (st.hasMoreTokens())
        {
            questVar = st.nextToken();
        }
        if (!playerOid.equals("") && !questVar.equals(""))
        {
            obj_id targetOid = utils.stringToObjId(playerOid);
            if (!isIdNull(targetOid))
            {
                gm.forceRegrantQuest(targetOid, questVar);
                CustomerServiceLog("gmQuest", "CSR: (" + self + ") " + getName(self) + " has cleared and granted (or attempted to clear and grant) quest (" + questVar + ") from (" + targetOid + ") " + utils.getStringName(targetOid));
            }
            else
            {
                sendSystemMessageTestingOnly(self, "Please check the OID and try again");
            }
        }
        else
        {
            sendSystemMessageTestingOnly(self, "[Syntax] /gmregrantquest <player OID> <quest/code_string>");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdSetInstanceAuthorized(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, gm.INSTANCE_AUTH))
        {
            utils.removeScriptVar(self, gm.INSTANCE_AUTH);
            sendSystemMessageTestingOnly(self, "You will no longer override instance authorization");
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, gm.INSTANCE_AUTH, 1);
        sendSystemMessageTestingOnly(self, "You are now overriding private instance authorization checks");
        return SCRIPT_CONTINUE;
    }
    public int cmdCombatDataRecord(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        String message = "";
        if (utils.hasScriptVar(self, "testing_recordCombatData"))
        {
            message += "Already recording combat data.";
        }
        else
        {
            utils.setScriptVar(self, "testing_recordCombatData", true);
            message += "Now recording yourcombat data. \n";
            message += "Use /combatDataReport to show your combat data. \n";
            message += "Use /combatDataClear to clear your combat data. \n";
            message += "Use /combatDataStop to stop recording combat data.";
        }
        sendSystemMessage(self, message, "");
        return SCRIPT_CONTINUE;
    }
    public int cmdCombatDataClear(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVarTree(self, target_dummy.BASE_TARGET_DUMMY_VAR))
        {
            utils.removeScriptVarTree(self, target_dummy.BASE_TARGET_DUMMY_VAR);
            sendSystemMessage(self, new string_id("target_dummy", "combat_data_all_clearing"));
            sendSystemMessage(self, new string_id("target_dummy", "combat_data_all_cleared"));
        }
        else
        {
            sendSystemMessage(self, new string_id("target_dummy", "placement_no_combat_data"));
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdCombatDataReport(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        target_dummy.reportCombatData(self, self);
        return SCRIPT_CONTINUE;
    }
    public int cmdCombatDataStop(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "testing_recordCombatData"))
        {
            utils.removeScriptVar(self, "testing_recordCombatData");
            sendSystemMessage(self, "No longer recording combat data.", "");
        }
        else
        {
            sendSystemMessage(self, "Not currently recording combat data.", "");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdCsDumpTarget(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        StringTokenizer st = new java.util.StringTokenizer(params);
        String strObjectOid = "";
        if (st.hasMoreTokens())
        {
            strObjectOid = st.nextToken();
        }
        if (!strObjectOid.equals(""))
        {
            obj_id objectOid = utils.stringToObjId(strObjectOid);
            if (!isIdValid(objectOid))
            {
                sendSystemMessageTestingOnly(self, "The OID you specified is invalid.");
            }
            else
            {
                createDumpFile(self, objectOid);
            }
        }
        else
        {
            obj_id lookAtTarget = dump.findDumpTarget(self);
            if (!isIdValid(lookAtTarget))
            {
                sendSystemMessageTestingOnly(self, "You need to have something targeted or specify an OID to use this command.");
            }
            else if (isIdValid(lookAtTarget))
            {
                createDumpFile(self, lookAtTarget);
            }
            else
            {
                sendSystemMessageTestingOnly(self, "you must have a valid target to use this command.");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void createDumpFile(obj_id self, obj_id targetObject) throws InterruptedException
    {
        if (isValidId(targetObject))
        {
            String combinedString = dump.csTargetDump(self, targetObject, true);
            String objIdToString = "" + targetObject;
            utils.setScriptVar(self, "export.lookAtTarget", objIdToString);
            gm.createDumpTargetUI(self, combinedString);
        }
    }
    public int exportCsDumpFile(obj_id self, dictionary params) throws InterruptedException
    {
        int btn = sui.getIntButtonPressed(params);
        if (btn == 0 && utils.hasScriptVar(self, "export.lookAtTarget"))
        {
            obj_id target = utils.stringToObjId(utils.getStringScriptVar(self, "export.lookAtTarget"));
            String fileData = dump.csTargetDump(self, target, false);
            String title = "targetDump" + target;
            saveTextOnClient(self, title + ".txt", fileData);
        }
        utils.removeScriptVarTree(self, "export");
        return SCRIPT_CONTINUE;
    }
    public int cmdSetGalaxyMessage(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (getGodLevel(self) < 10)
        {
            sendSystemMessage(self, "You do not have the appropriate access level to use this command.", null);
        }
        else
        {
            String commandParams = params;
            if (commandParams != null && !commandParams.equals(""))
            {
                obj_id planetId = getPlanetByName("tatooine");
                if (!isIdNull(planetId))
                {
                    if (commandParams.equals("delete") || commandParams.equals("remove") || commandParams.equals("erase"))
                    {
                        String strMessage = "\\#FF0000" + "Message deleted and reset to default. Check using /motd" + "\\#FFFFFF";
                        sendConsoleMessage(self, strMessage);
                        removeObjVar(planetId, "galaxyMessage");
                        CustomerServiceLog("setGalaxyMessage", "CSR: (" + self + ") " + getName(self) + " has erased the Galaxy-wide message. It is now the default welcome message.");
                    }
                    else if (commandParams.equals("get object") || commandParams.equals("getobject") || commandParams.equals("getObject"))
                    {
                        String strMessage = "\\#FF0000" + planetId + "\\#FFFFFF";
                        sendConsoleMessage(self, strMessage);
                        CustomerServiceLog("setGalaxyMessage", "CSR: (" + self + ") " + getName(self) + " has retrieved the object ID for the Galaxy-wide message.");
                    }
                    else
                    {
                        if (commandParams.indexOf("set") == 0 && commandParams.length() > 4)
                        {
                            commandParams = commandParams.substring(4, commandParams.length());
                            commandParams = commandParams.trim();
                            setObjVar(planetId, "galaxyMessage", commandParams);
                            String strMessage = "\\#FF0000" + "Give it a few moments, then check the new message with /motd" + "\\#FFFFFF";
                            sendConsoleMessage(self, strMessage);
                            CustomerServiceLog("setGalaxyMessage", "CSR: (" + self + ") " + getName(self) + " has changed the Galaxy-wide message to read '" + commandParams + "'");
                        }
                    }
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "The tool failed to find the proper planet object. Notify the tool team.");
                }
            }
            else
            {
                sendConsoleMessage(self, "To set the Galaxy-wide message use the /setLoginMessage command followed by one of the followind commands:");
                sendConsoleMessage(self, "\t1. set <your string here> - sets the login message to a string you specify.");
                sendConsoleMessage(self, "\t2. getObject - retrieves the OID of the object that contains the login message.  This object ID can be used for debugging.");
                sendConsoleMessage(self, "\t3. delete - deletes the current message and sets the login message to the default welcome.");
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdSetWardenGalaxyMessage(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (getGodLevel(self) < 10)
        {
            sendSystemMessage(self, "You do not have the appropriate access level to use this command.", null);
        }
        else
        {
            String commandParams = params;
            if (commandParams != null && !commandParams.equals(""))
            {
                obj_id planetId = getPlanetByName("tatooine");
                if (!isIdNull(planetId))
                {
                    if (commandParams.equals("delete") || commandParams.equals("remove") || commandParams.equals("erase"))
                    {
                        String strMessage = "\\#FF0000" + "Warden message deleted and reset to default. Check using /motd" + "\\#FFFFFF";
                        sendConsoleMessage(self, strMessage);
                        removeObjVar(planetId, "galaxyWardenMessage");
                        CustomerServiceLog("setGalaxyMessage", "CSR: (" + self + ") " + getName(self) + " has erased the warden Galaxy-wide message. It is now the default welcome message.");
                    }
                    else if (commandParams.equals("get object") || commandParams.equals("getobject") || commandParams.equals("getObject"))
                    {
                        String strMessage = "\\#FF0000" + planetId + "\\#FFFFFF";
                        sendConsoleMessage(self, strMessage);
                        CustomerServiceLog("setGalaxyMessage", "CSR: (" + self + ") " + getName(self) + " has retrieved the object ID for the warden Galaxy-wide message.");
                    }
                    else
                    {
                        if (commandParams.indexOf("set") == 0 && commandParams.length() > 4)
                        {
                            commandParams = commandParams.substring(4, commandParams.length());
                            commandParams = commandParams.trim();
                            setObjVar(planetId, "galaxyWardenMessage", commandParams);
                            String strMessage = "\\#FF0000" + "Give it a few moments, then check the new warden message with /motd" + "\\#FFFFFF";
                            sendConsoleMessage(self, strMessage);
                            CustomerServiceLog("setGalaxyMessage", "CSR: (" + self + ") " + getName(self) + " has changed the warden Galaxy-wide message to read '" + commandParams + "'");
                        }
                    }
                }
                else
                {
                    sendSystemMessageTestingOnly(self, "The tool failed to find the proper planet object. Notify the tool team.");
                }
            }
            else
            {
                sendConsoleMessage(self, "To set the warden Galaxy-wide message use the /setWardenLoginMessage command followed by one of the followind commands:");
                sendConsoleMessage(self, "\t1. set <your string here> - sets the warden login message to a string you specify.");
                sendConsoleMessage(self, "\t2. getObject - retrieves the OID of the object that contains the login message.  This object ID can be used for debugging.");
                sendConsoleMessage(self, "\t3. delete - deletes the current warden message and sets the warden login message to the default welcome.");
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
