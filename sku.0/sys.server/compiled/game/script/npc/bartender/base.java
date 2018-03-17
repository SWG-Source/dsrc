package script.npc.bartender;

import script.*;
import script.library.ai_lib;
import script.library.chat;
import script.library.factions;
import script.library.money;
import script.library.prose;
import script.library.sui;
import script.library.township;
import script.library.utils;

import java.util.Vector;

public class base extends script.base_script
{
    public base()
    {
    }
    public static final int TICK_TIME = 900;
    public static final String VAR_BARTENDER_BASE = "bartender";
    public static final String VAR_RUMOR_BASE = "bartender.rumor";
    public static final String VAR_RUMOR_SPEAKER = "bartender.rumor.speaker";
    public static final String VAR_RUMOR_TEXT = "bartender.rumor.text";
    public static final String VAR_RUMOR_STAMP = "bartender.rumor.stamp";
    public static final String VAR_LOC_BASE = VAR_BARTENDER_BASE + ".location";
    public static final String TBL_BAR_LOC = "datatables/npc/bartender/bar_location.iff";
    public static final String TBL_MENU_PATH = "datatables/npc/bartender/menu/";
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String SCRIPT_LISTEN = "npc.bartender.listen";
    public static final String[] ANIMS_TOWARD = 
    {
        "applause_excited",
        "applause_polite",
        "check_wrist_device",
        "clap_rousing",
        "entertained",
        "face_bored",
        "face_close_eyes",
        "fidget",
        "hold_object",
        "look_casual",
        "look_right",
        "look_left",
        "manipulate_low",
        "manipulate_medium",
        "pick_up",
        "put_down",
        "stretch",
        "tap_foot"
    };
    public static final String[] ANIMS_AWAY = 
    {
        "check_wrist_device",
        "hold_object",
        "look_casual",
        "look_right",
        "look_left",
        "manipulate_high",
        "manipulate_low",
        "manipulate_medium",
        "pick_up",
        "put_down"
    };
    public static final String CONVONAME = "bartender";
    public static final String STF = "bartender";
    public static final string_id[] OPT_DEFAULT =
    {
        new string_id(STF, "opt_buy"),
        new string_id(STF, "talk_to_me")
    };
    public static final string_id[] OPT_RUMOR = 
    {
        new string_id(STF, "opt_buy"),
        new string_id(STF, "opt_rumor")
    };
    public static final string_id[] OPT_BUY = 
    {
        new string_id(STF, "opt_yes"),
        new string_id(STF, "opt_no")
    };
    public static final string_id PROSE_BUY_PASS = new string_id(STF, "prose_buy_pass");
    public static final string_id PROSE_BUY_FAIL = new string_id(STF, "prose_buy_fail");
    public static final string_id SID_INV_FULL = new string_id(STF, "inv_full");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasScript(self, SCRIPT_CONVERSE))
        {
            attachScript(self, SCRIPT_CONVERSE);
        }
        if (!hasScript(self, SCRIPT_LISTEN))
        {
            attachScript(self, SCRIPT_LISTEN);
        }
        factions.setFaction(self, "Unattackable");
        setInvulnerable(self, true);
        messageTo(self, "handleTick", null, 15, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasScript(self, SCRIPT_CONVERSE))
        {
            detachScript(self, SCRIPT_CONVERSE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        int cnt = 0;
        if (utils.hasScriptVar(self, "bartender.speaking"))
        {
            cnt = utils.getIntScriptVar(self, "bartender.speaking");
        }
        cnt++;
        utils.setScriptVar(self, "bartender.speaking", cnt);
        string_id msg = new string_id(STF, "greet");
        if (hasObjVar(self, VAR_RUMOR_BASE))
        {
            npcStartConversation(speaker, self, CONVONAME, msg, OPT_RUMOR);
        }
        else 
        {
            npcStartConversation(speaker, self, CONVONAME, msg, OPT_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEndNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        int cnt = utils.getIntScriptVar(self, "bartender.speaking");
        cnt--;
        if (cnt < 1)
        {
            utils.removeScriptVar(self, "bartender.speaking");
        }
        if (getBehavior(self) == BEHAVIOR_CALM)
        {
            messageTo(self, "resumeDefaultCalmBehavior", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id speaker, string_id sid_response) throws InterruptedException
    {
        if (!convo.equals(CONVONAME))
        {
            return SCRIPT_CONTINUE;
        }
        String tbl = sid_response.getTable();
        String response = sid_response.getAsciiId();
        if (tbl.equals(STF))
        {
            string_id msg = new string_id(STF, "greet");
            if (response.equals("opt_buy"))
            {
                msg = new string_id(STF, "msg_buy");
                npcSpeak(speaker, msg);
                npcEndConversation(speaker);
                showBuySui(speaker);
            }
            else if (response.equals("opt_rumor"))
            {
                if (hasObjVar(self, VAR_RUMOR_BASE))
                {
                    String rumorSource = getStringObjVar(self, VAR_RUMOR_SPEAKER);
                    String rumorText = getStringObjVar(self, VAR_RUMOR_TEXT);
                    string_id PROSE_RUMOR = new string_id(STF, "prose_rumor" + rand(1, 4));
                    prose_package ppRumor = prose.getPackage(PROSE_RUMOR, rumorSource, rumorText);
                    chat.publicChat(self, speaker, chat.CHAT_WHISPER, chat.getChatMood(self), ppRumor);
                    msg = new string_id(STF, "query_buy");
                    npcSpeak(speaker, msg);
                    npcSetConversationResponses(speaker, OPT_BUY);
                }
                else 
                {
                    msg = new string_id(STF, "no_rumor");
                    npcSpeak(speaker, msg);
                    npcSetConversationResponses(speaker, OPT_DEFAULT);
                }
            }
            else if (response.equals("opt_yes"))
            {
                msg = new string_id(STF, "msg_yes");
                npcSpeak(speaker, msg);
                npcEndConversation(speaker);
                showBuySui(speaker);
            }
            else if (response.equals("opt_no"))
            {
                msg = new string_id(STF, "msg_no");
                npcSpeak(speaker, msg);
                npcEndConversation(speaker);
            }
            else if (response.equals("talk_to_me"))
            {
                location here = getLocation(self);
                obj_id container = getTopMostContainer(self);
                if (isIdValid(container))
                {
                    here = getLocation(container);
                }
                String buildoutAreaName = getBuildoutAreaName(here.x, here.z);
                if (buildoutAreaName != null && buildoutAreaName.equals("nova_orion_station"))
                {
                    msg = township.getNovaOrionRumor(speaker);
                }
                else 
                {
                    int fiction_rumor = rand(1, 9);
                    msg = new string_id(STF, "monthly_fiction_" + fiction_rumor);
                }
                npcSpeak(speaker, msg);
                npcEndConversation(speaker);
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String locationName) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put("locationName", locationName);
        messageTo(self, "handlePlayAnimation", d, 2f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        updateDestination(self);
        return SCRIPT_CONTINUE;
    }
    public int handleTick(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "bartender.speaking") && !hasObjVar(self, "bartender.doNotMove"))
        {
            if (rand(0, 10) < 4)
            {
                bartenderMove(self);
                playBarAnimation(self);
            }
            else {
                playBarAnimation(self);
            }
        }
        messageTo(self, "handleTick", null, rand(15f, 30f), false);
        return SCRIPT_CONTINUE;
    }
    public int handleListen(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasScript(self, SCRIPT_LISTEN))
        {
            attachScript(self, SCRIPT_LISTEN);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleNewRumor(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        removeObjVar(self, VAR_RUMOR_BASE);
        String speaker = params.getString("speaker");
        String text = params.getString("text");
        setObjVar(self, VAR_RUMOR_SPEAKER, speaker);
        setObjVar(self, VAR_RUMOR_TEXT, text);
        setObjVar(self, VAR_RUMOR_STAMP, getGameTime());
        return SCRIPT_CONTINUE;
    }
    public int handlePlayAnimation(obj_id self, dictionary params) throws InterruptedException
    {
        String locationName = params.getString("locationName");
        if (locationName == null || locationName.equals(""))
        {
        }
        else 
        {
            if (hasObjVar(self, VAR_LOC_BASE + "." + locationName + ".heading"))
            {
                float yaw = getFloatObjVar(self, VAR_LOC_BASE + "." + locationName + ".heading");
                boolean faceBar = true;
                switch (rand(1, 2))
                {
                    case 1:
                        float barYaw = yaw + rand(-10f, 10f);
                        setYaw(self, barYaw);
                        break;
                    case 2:
                    default:
                        float yawAway = yaw - 180f + rand(-10f, 10f);
                        setYaw(self, yawAway);
                        faceBar = false;
                    break;
                }
                setObjVar(self,"facingBar",faceBar);
                playBarAnimation(self);
            }
            removeLocationTarget(locationName);
            removeObjVar(self, VAR_LOC_BASE + "." + locationName);
        }
        return SCRIPT_CONTINUE;
    }
    public void playBarAnimation(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return;
        }
        String anim = "check_wrist_device";
        if (getBooleanObjVar(self,"facingBar"))
        {
            int tidx = rand(0, ANIMS_TOWARD.length - 1);
            anim = ANIMS_TOWARD[tidx];
        }
        else 
        {
            int aidx = rand(0, ANIMS_AWAY.length - 1);
            anim = ANIMS_AWAY[aidx];
        }
        ai_lib.doAction(self, anim);
    }
    public boolean bartenderMove(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return false;
        }
        float[] col_x = dataTableGetFloatColumn(TBL_BAR_LOC, "X");
        if ((col_x != null) && (col_x.length > 0))
        {
            int idx = rand(0, col_x.length - 2);
            dictionary row1 = dataTableGetRow(TBL_BAR_LOC, idx);
            dictionary row2 = dataTableGetRow(TBL_BAR_LOC, idx + 1);
            if ((row1 != null) && (!row1.isEmpty()) && (row2 != null) && (!row2.isEmpty()))
            {
                String planet = getCurrentSceneName();
                obj_id structure = getTopMostContainer(self);
                if (!isIdValid(structure))
                {
                    return false;
                }
                float x1 = row1.getFloat("X");
                float y1 = row1.getFloat("Y");
                float z1 = row1.getFloat("Z");
                String cell1 = row1.getString("CELL");
                float heading1 = row1.getFloat("BAR_FACING");
                obj_id cid1 = getCellId(structure, cell1);
                if (!isIdValid(cid1))
                {
                    return false;
                }
                location spot1 = new location(x1, y1, z1, planet, cid1);
                float x2 = row2.getFloat("X");
                float y2 = row2.getFloat("Y");
                float z2 = row2.getFloat("Z");
                String cell2 = row2.getString("CELL");
                float heading2 = row2.getFloat("BAR_FACING");
                obj_id cid2 = getCellId(structure, cell2);
                if (!isIdValid(cid2))
                {
                    return false;
                }
                location spot2 = new location(x2, y2, z2, planet, cid2);
                if (spot1 == null || spot2 == null)
                {
                    LOG("bartender", "bartenderMove(): datatable move locations created as null");
                    return false;
                }
                else 
                {
                }
                location there = null;
                if (cell1.equals(cell2))
                {
                    float nx = x1;
                    if (x1 < x2)
                    {
                        nx = rand(x1, x2);
                    }
                    else if (x2 < x1)
                    {
                        nx = rand(x2, x1);
                    }
                    float nz = z1;
                    if (z1 < z2)
                    {
                        nz = rand(z1, z2);
                    }
                    else if (z2 < z1)
                    {
                        nz = rand(z2, z1);
                    }
                    there = new location(nx, y1, nz, planet, cid1);
                }
                else 
                {
                    there = new location(x1, y1, z1, planet, cid1);
                }
                if (there != null)
                {
                    int now = getGameTime();
                    float heading = (heading1 + heading2) / 2f;
                    setObjVar(self, VAR_LOC_BASE + "." + now + ".location", there);
                    setObjVar(self, VAR_LOC_BASE + "." + now + ".heading", heading);
                    addLocationTarget("" + now, there, 0.25f);
                    ai_lib.aiPathTo(self, there);
                    return true;
                }
                else 
                {
                    LOG("bartender", "bartenderMove(): target location = null");
                }
            }
            else 
            {
                LOG("bartender", "bartenderMove(): one of the selected rows was empty or null");
            }
        }
        else 
        {
            LOG("bartender", "bartenderMove(): col X = null or length = 0");
        }
        return false;
    }
    public void updateDestination(obj_id self) throws InterruptedException
    {
        cleanDestinations(self);
        bartenderMove(self);
    }
    public void cleanDestinations(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, VAR_LOC_BASE))
        {
            obj_var_list ovl = getObjVarList(self, VAR_LOC_BASE);
            if (ovl != null)
            {
                int numItems = ovl.getNumItems();
                for (int i = 0; i < numItems; i++)
                {
                    obj_var ov = ovl.getObjVar(i);
                    if (ov != null)
                    {
                        String name = ov.getName();
                        if (name != null && !name.equals(""))
                        {
                            removeLocationTarget(name);
                        }
                    }
                }
            }
            removeObjVar(self, VAR_LOC_BASE);
        }
    }
    public int showBuySui(obj_id target) throws InterruptedException
    {
        obj_id self = getSelf();
        if (!isIdValid(self) || !isIdValid(target))
        {
            return -1;
        }
        String scriptvar_base = "menu." + target;
        String scriptvar_pid = scriptvar_base + ".pid";
        String scriptvar_opt = scriptvar_base + ".opt";
        if (utils.hasScriptVar(self, scriptvar_pid))
        {
            int oldpid = utils.getIntScriptVar(self, scriptvar_pid);
            sui.closeSUI(target, oldpid);
            utils.removeScriptVarTree(self, scriptvar_base);
        }
        String title = "@" + STF + ":sui_title";
        String prompt = "@" + STF + ":sui_prompt";
        String tbl = TBL_MENU_PATH + getCurrentSceneName() + ".iff";
        int numRows = dataTableGetNumRows(tbl);
        if (numRows > 0)
        {
            Vector opt = new Vector();
            opt.setSize(0);
            Vector entries = new Vector();
            entries.setSize(0);
            for (int i = 0; i < numRows; i++)
            {
                dictionary d = dataTableGetRow(tbl, i);
                if (d != null && !d.isEmpty())
                {
                    String template = d.getString("template");
                    String encodedName = d.getString("encodedName");
                    if (template != null && !template.equals("") && encodedName != null && !encodedName.equals(""))
                    {
                        string_id sid_name = utils.unpackString(encodedName);
                        if (sid_name != null)
                        {
                            String name = getString(sid_name);
                            if (name != null && !name.equals(""))
                            {
                                int cost = d.getInt("price");
                                if (cost > 0)
                                {
                                    entries = utils.addElement(entries, "[" + cost + "] " + name);
                                    opt = utils.addElement(opt, encodedName);
                                }
                            }
                        }
                    }
                }
            }
            if (entries != null && entries.size() > 0 && opt != null && opt.size() == entries.size())
            {
                int pid = sui.listbox(self, target, prompt, sui.OK_CANCEL, title, entries, "handleBuySui");
                if (pid > -1)
                {
                    utils.setScriptVar(self, scriptvar_pid, pid);
                    utils.setScriptVar(self, scriptvar_opt, opt);
                    return pid;
                }
            }
        }
        return -1;
    }
    public int handleBuySui(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String[] opt = utils.getStringArrayScriptVar(self, "menu." + player + ".opt");
        utils.removeScriptVarTree(self, "menu." + player);
        if (opt == null || opt.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        String tbl = TBL_MENU_PATH + getCurrentSceneName() + ".iff";
        int rIdx = dataTableSearchColumnForString(opt[idx], "encodedName", tbl);
        if (rIdx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        dictionary row = dataTableGetRow(tbl, rIdx);
        if (row == null || row.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int price = row.getInt("price");
        money.requestPayment(player, self, price, "handlePayReturn", row, false);
        return SCRIPT_CONTINUE;
    }
    public int handlePayReturn(obj_id self, dictionary params) throws InterruptedException
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
        String template = params.getString("template");
        if (template == null || params.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        String encodedName = params.getString("encodedName");
        string_id sid_name = null;
        if (encodedName != null && !encodedName.equals(""))
        {
            sid_name = utils.unpackString(encodedName);
        }
        int retCode = params.getInt(money.DICT_CODE);
        if (retCode == money.RET_FAIL)
        {
            prose_package ppBuyFail = prose.getPackage(PROSE_BUY_FAIL, getNameFromTemplate(template));
            
            if (sid_name != null)
            {
                ppBuyFail = prose.getPackage(PROSE_BUY_FAIL, sid_name);
            }
            else if (encodedName != null && !encodedName.equals(""))
            {
                ppBuyFail = prose.getPackage(PROSE_BUY_FAIL, encodedName);
            }
            sendSystemMessageProse(player, ppBuyFail);
        }
        else 
        {
            obj_id myInv = utils.getInventoryContainer(self);
            obj_id pInv = utils.getInventoryContainer(player);
            if (!isIdValid(myInv) || !isIdValid(pInv))
            {
                return SCRIPT_CONTINUE;
            }
            int price = params.getInt(money.DICT_TOTAL);
            obj_id drink = createObject(template, myInv, "");
            if (!isIdValid(drink))
            {
                return SCRIPT_CONTINUE;
            }
            if (sid_name != null)
            {
                setName(drink, sid_name);
            }
            if (canPutIn(drink, pInv) == CEC_SUCCESS)
            {
                putIn(drink, pInv);
                prose_package ppBuyPass = prose.getPackage(PROSE_BUY_PASS, drink, price);
                sendSystemMessageProse(player, ppBuyPass);
                money.bankTo(self, money.ACCT_CANTINA_DRINK, price);
            }
            else 
            {
                money.pay(self, player, price, "whoCares", null);
                dictionary d = new dictionary();
                d.put("id", drink);
                messageTo(self, "handleDestroyObject", d, 5, false);
                prose_package ppInvFull = prose.getPackage(SID_INV_FULL, drink);
                if (sid_name != null)
                {
                    ppInvFull = prose.getPackage(SID_INV_FULL, sid_name);
                }
                else if (encodedName != null && !encodedName.equals(""))
                {
                    ppInvFull = prose.getPackage(SID_INV_FULL, encodedName);
                }
                sendSystemMessageProse(player, ppInvFull);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDestroyObject(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id item = params.getObjId("id");
        if (isIdValid(item))
        {
            destroyObject(item);
        }
        return SCRIPT_CONTINUE;
    }
}
