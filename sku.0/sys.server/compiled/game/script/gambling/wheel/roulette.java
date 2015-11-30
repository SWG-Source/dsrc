package script.gambling.wheel;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.money;
import script.library.prose;
import script.library.gambling;

public class roulette extends script.gambling.base.wheel
{
    public roulette()
    {
    }
    public static final String GAME_TYPE = "roulette";
    public static final int[] RED_NUMBERS = 
    {
        1,
        3,
        5,
        7,
        9,
        12,
        14,
        16,
        18,
        19,
        21,
        23,
        25,
        27,
        30,
        32,
        34,
        36
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        cleanupWheelGame(self);
        String gameType = GAME_TYPE;
        if (hasObjVar(self, gambling.VAR_PREDEFINED_TYPE))
        {
            gameType = getStringObjVar(self, gambling.VAR_PREDEFINED_TYPE);
        }
        gambling.initializeTable(self, gameType);
        return super.OnInitialize(self);
    }
    public int handleBetPlaced(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int ret = params.getInt(money.DICT_CODE);
        int amt = params.getInt(money.DICT_TOTAL);
        if (ret == money.RET_FAIL || amt < 1)
        {
            dictionary d = new dictionary();
            d.put("player", player);
            messageTo(self, "handleBetFailed", d, 0, false);
            return SCRIPT_CONTINUE;
        }
        String arg = params.getString("arg");
        if (arg != null && !arg.equals(""))
        {
            if (isValidBet(self, arg))
            {
                String scriptvar_pid = gambling.VAR_GAME_PLAYERS + "." + player + ".pid";
                if (utils.hasScriptVar(self, scriptvar_pid))
                {
                    int oldpid = utils.getIntScriptVar(self, scriptvar_pid);
                    sui.closeSUI(player, oldpid);
                    utils.removeScriptVar(self, scriptvar_pid);
                }
                CustomerServiceLog("gambling", getGameTime() + ": (" + player + ") " + getName(player) + " bets " + amt + "cr on '" + arg + "'");
                int playerIdx = gambling.getGamePlayerIndex(self, player);
                if (playerIdx < 0)
                {
                    return SCRIPT_CONTINUE;
                }
                String ovpath = gambling.VAR_GAME_PLAYERS + "." + playerIdx + ".bet." + arg;
                if (hasObjVar(self, ovpath))
                {
                    amt += getIntObjVar(self, ovpath);
                    int maxBet = getIntObjVar(self, gambling.VAR_TABLE_BET_MAX);
                    if (maxBet > 0 && amt > maxBet)
                    {
                        int refund = amt - maxBet;
                        sendSystemMessageTestingOnly(player, "The maximum bet for this station is " + maxBet + " credits.");
                        sendSystemMessageTestingOnly(player, "Bet Refund (over-bet): " + refund + " credits");
                        transferBankCreditsTo(self, player, refund, "noHandler", "noHandler", new dictionary());
                        CustomerServiceLog("gambling", getGameTime() + ": (" + player + ") " + getName(player) + " overbet -> processing refund!");
                        CustomerServiceLog("gambling", getGameTime() + ": (" + player + ") " + getName(player) + " refund results: total=" + amt + " refund=" + refund + " updated bet=" + maxBet);
                        amt = maxBet;
                    }
                }
                setObjVar(self, ovpath, amt);
                dictionary d = new dictionary();
                d.put("player", player);
                messageTo(self, "handleRequestUpdatedUI", d, 0f, false);
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessageTestingOnly(player, "Roulette: /bet <amount> <1-36,0,00,red,black,odd,even,high,low>");
        sendSystemMessageTestingOnly(player, "Bet Refund: " + amt + " credits");
        transferBankCreditsTo(self, player, amt, "noHandler", "noHandler", new dictionary());
        return SCRIPT_CONTINUE;
    }
    public int handleWheelSpinning(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = getObjIdArrayObjVar(self, gambling.VAR_TABLE_PLAYERS);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int cnt = params.getInt("cnt");
        cnt--;
        params.put("cnt", cnt);
        if (cnt > 0)
        {
            float delay = 5f;
            switch (cnt)
            {
                case 2:
                for (int i = 0; i < players.length; i++)
                {
                    sendSystemMessage(players[i], new string_id(gambling.STF_INTERFACE, "wheel_begin_slow"));
                }
                break;
                case 1:
                int result = getResult();
                String sResult = getResultString(result);
                prose_package pp = prose.getPackage(new string_id(gambling.STF_INTERFACE, "prose_wheel_slow"), sResult, getResultColor(result));
                for (int i = 0; i < players.length; i++)
                {
                    sendSystemMessageProse(players[i], pp);
                }
                params.put("result", result);
                delay = 10f;
                break;
                default:
                for (int i = 0; i < players.length; i++)
                {
                    sendSystemMessage(players[i], new string_id(gambling.STF_INTERFACE, "wheel_spinning"));
                }
                break;
            }
            messageTo(self, "handleWheelSpinning", params, delay, false);
        }
        else 
        {
            prose_package pp = null;
            int result = getResult();
            String newResult = getResultString(result);
            params.put("result", result);
            pp = prose.getPackage(new string_id(gambling.STF_INTERFACE, "prose_result_change"), newResult, getResultColor(result));
            if (pp != null)
            {
                for (int i = 0; i < players.length; i++)
                {
                    sendSystemMessageProse(players[i], pp);
                }
            }
            messageTo(self, "handleParseResults", params, 1f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleParseResults(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = getObjIdArrayObjVar(self, gambling.VAR_GAME_PLAYERS_IDS);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int result = params.getInt("result");
        String sResult = getResultString(result);
        String resultColor = getResultColor(result);
        CustomerServiceLog("gambling", getGameTime() + ": (" + self + ") " + utils.getStringName(self) + " processing results...");
        CustomerServiceLog("gambling", getGameTime() + ": (" + self + ") " + utils.getStringName(self) + " results: raw=" + result + " sResult=" + sResult + " color=" + resultColor);
        for (int i = 0; i < players.length; i++)
        {
            int total = 0;
            CustomerServiceLog("gambling", getGameTime() + ": player = " + players[i] + " : " + getName(players[i]));
            int playerIdx = gambling.getGamePlayerIndex(self, players[i]);
            if (playerIdx > -1)
            {
                String ovpath = gambling.VAR_GAME_PLAYERS + "." + playerIdx + ".bet." + sResult;
                if (hasObjVar(self, ovpath))
                {
                    int spotBet = getIntObjVar(self, ovpath);
                    int spotPayout = (spotBet * 36) + spotBet;
                    total += spotPayout;
                    CustomerServiceLog("gambling", getGameTime() + ": (" + players[i] + ") has spot bet -> payout = " + spotPayout);
                }
                if (result > 0)
                {
                    ovpath = gambling.VAR_GAME_PLAYERS + "." + playerIdx + ".bet." + resultColor;
                    if (hasObjVar(self, ovpath))
                    {
                        int colorBet = getIntObjVar(self, ovpath);
                        int colorPayout = 2 * colorBet;
                        total += colorPayout;
                        CustomerServiceLog("gambling", getGameTime() + ": (" + players[i] + ") has color bet -> payout = " + colorPayout);
                    }
                }
                if (result > 0)
                {
                    if (result % 2 == 0)
                    {
                        ovpath = gambling.VAR_GAME_PLAYERS + "." + playerIdx + ".bet.even";
                    }
                    else 
                    {
                        ovpath = gambling.VAR_GAME_PLAYERS + "." + playerIdx + ".bet.odd";
                    }
                    if (hasObjVar(self, ovpath))
                    {
                        int evenOddBet = getIntObjVar(self, ovpath);
                        int evenOddPayout = 2 * evenOddBet;
                        total += evenOddPayout;
                        CustomerServiceLog("gambling", getGameTime() + ": (" + players[i] + ") has even/odd bet -> payout = " + evenOddPayout);
                    }
                }
                if (result > 0)
                {
                    if (result > 18)
                    {
                        ovpath = gambling.VAR_GAME_PLAYERS + "." + playerIdx + ".bet.high";
                    }
                    else 
                    {
                        ovpath = gambling.VAR_GAME_PLAYERS + "." + playerIdx + ".bet.low";
                    }
                    if (hasObjVar(self, ovpath))
                    {
                        int hiLoBet = getIntObjVar(self, ovpath);
                        int hiLoPayout = 2 * hiLoBet;
                        total += hiLoPayout;
                        CustomerServiceLog("gambling", getGameTime() + ": (" + players[i] + ") has hi/low bet -> payout = " + hiLoPayout);
                    }
                }
            }
            CustomerServiceLog("gambling", getGameTime() + ": (" + players[i] + ") total payout = " + total);
            if (total > 0)
            {
                dictionary d = new dictionary();
                d.put("player", players[i]);
                d.put("payout", total);
                transferBankCreditsFromNamedAccount(money.ACCT_ROULETTE, players[i], total, "handleGamblingPayout", "noHandler", d);
            }
            else 
            {
                sendSystemMessageTestingOnly(players[i], "Sorry, you did not win this round. Please try again.");
            }
        }
        cleanupWheelGame(self);
        messageTo(self, "handleDelayedRestart", null, 10f, false);
        return SCRIPT_CONTINUE;
    }
    public boolean isValidBet(obj_id self, String arg) throws InterruptedException
    {
        if (!isIdValid(self) || arg == null || arg.equals(""))
        {
            return false;
        }
        if (arg.equals("red") || arg.equals("black") || arg.equals("00"))
        {
            return true;
        }
        if (arg.equals("even") || arg.equals("odd") || arg.equals("high") || arg.equals("low"))
        {
            return true;
        }
        int tmp = utils.stringToInt(arg);
        if (tmp >= 0 && tmp <= 36)
        {
            return true;
        }
        return false;
    }
    public int getResult() throws InterruptedException
    {
        return rand(-1, 36);
    }
    public String getResultString(int roll) throws InterruptedException
    {
        if (roll == -1)
        {
            return "00";
        }
        else 
        {
            return Integer.toString(roll);
        }
    }
    public String getResultString() throws InterruptedException
    {
        return getResultString(getResult());
    }
    public String getResultColor(int result) throws InterruptedException
    {
        if (result == 0 || result == -1)
        {
            return "green";
        }
        if (utils.getElementPositionInArray(RED_NUMBERS, result) > -1)
        {
            return "red";
        }
        return "black";
    }
    public void cleanupWheelGame(obj_id self) throws InterruptedException
    {
        int bank = getBankBalance(self);
        if (bank > 0)
        {
            transferBankCreditsToNamedAccount(self, money.ACCT_ROULETTE, bank, "noHandler", "noHandler", new dictionary());
        }
        obj_id[] players = getObjIdArrayObjVar(self, gambling.VAR_GAME_PLAYERS_IDS);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                int idx = gambling.getGamePlayerIndex(self, players[i]);
                String ovpath = gambling.VAR_GAME_PLAYERS + "." + players[i] + ".pid";
                if (utils.hasScriptVar(self, ovpath))
                {
                    int oldpid = utils.getIntScriptVar(self, ovpath);
                    sui.closeSUI(players[i], oldpid);
                }
                ovpath = gambling.VAR_GAME_PLAYERS + "." + idx + ".bet";
                if (!hasObjVar(self, ovpath))
                {
                    gambling.removeTablePlayer(self, players[i], "");
                }
            }
        }
        removeObjVar(self, gambling.VAR_GAME_BASE);
    }
}
