package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.xp;
import script.library.prose;
import script.library.pclib;

public class terminal_xp extends script.terminal.base.terminal_add_use
{
    public terminal_xp()
    {
    }
    public static final int XP_AMOUNT = 100000;
    public static final String TBL = "datatables/skill/skills.iff";
    public static final String VAR_XP_TYPES = "xp_types";
    public static final String VAR_XP_TYPES_LIST = "xp_types.list";
    public static final String VAR_XP_TYPES_NAMES = "xp_types.names";
    public static final String HANDLER_XP_SELECT = "handleXpSelect";
    public static final String HANDLER_XP_AMOUNT_SELECT = "handleXpAmountSelect";
    public static final string_id PROSE_GRANT_XP = new string_id("base_player", "prose_grant_xp");
    public static final String VAR_DESIRED_XP_TYPE = "desired_xpType";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        removeObjVar(self, VAR_XP_TYPES_LIST);
        initializeXpTerminal(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isGod(player) || hasObjVar(player, "beta.terminal_ok"))
        {
            if (item == menu_info_types.ITEM_USE)
            {
                String[] xpTypes = utils.getStringBatchScriptVar(self, VAR_XP_TYPES_NAMES);
                if ((xpTypes != null) && (xpTypes.length > 0))
                {
                    String prompt = "Select the xp type you would like more xp in...";
                    String title = "Beta XP Dispenser";
                    sui.listbox(self, player, prompt, sui.OK_CANCEL, title, xpTypes, HANDLER_XP_SELECT);
                }
            }
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Only authorized users may access this terminal.");
            return SCRIPT_CONTINUE;
        }
    }
    public void initializeXpTerminal(obj_id self) throws InterruptedException
    {
        String[] xpTypes = xp.getXpTypes(self);
        if ((xpTypes != null) && (xpTypes.length > 0))
        {
            setXpTypesScriptVar(self, xpTypes);
        }
    }
    public void setXpTypesScriptVar(obj_id self, String[] varData) throws InterruptedException
    {
        if ((varData == null) || (varData.length == 0))
        {
            debugSpeakMsg(self, "setXpTypesVar: passed bad string array!");
            return;
        }
        utils.setBatchScriptVar(self, VAR_XP_TYPES_LIST, varData);
        String[] xpNames = new String[varData.length];
        for (int i = 0; i < varData.length; i++)
        {
            xpNames[i] = "@exp_n:" + varData[i];
        }
        utils.setBatchScriptVar(self, VAR_XP_TYPES_NAMES, xpNames);
    }
    public int handleXpSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        String[] xpTypes = utils.getStringBatchScriptVar(self, VAR_XP_TYPES_LIST);
        if (xpTypes == null || xpTypes.length == 0 || idx >= xpTypes.length)
        {
            sendSystemMessageTestingOnly(player, "There has been an error in determining the xp type selected.");
            return SCRIPT_CONTINUE;
        }
        String xpType = xpTypes[idx];
        int xp = getExperiencePoints(player, xpType);
        setObjVar(player, VAR_DESIRED_XP_TYPE, xpType);
        String prompt = "Select the amount of XP you desire in the right box";
        String title = "Select your desired XP amount";
        sui.transfer(self, player, prompt, title, "Available", XP_AMOUNT, "Amount", 0, HANDLER_XP_AMOUNT_SELECT);
        return SCRIPT_CONTINUE;
    }
    public int handleXpAmountSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int amt = sui.getTransferInputTo(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String xpType = getStringObjVar(player, VAR_DESIRED_XP_TYPE);
        if (xpType.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        removeObjVar(player, VAR_DESIRED_XP_TYPE);
        xp.grant(player, xpType, amt);
        dictionary d = new dictionary();
        d.put("xpType", xpType);
        d.put("playerId", player);
        messageTo(self, "handleDisplayXp", d, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDisplayXp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("playerId");
        String xpType = params.getString("xpType");
        string_id PROSE_HAVE_XP = new string_id("base_player", "prose_have_xp");
        int xp = getExperiencePoints(player, xpType);
        prose_package pp = prose.getPackage(PROSE_HAVE_XP, "@exp_n:" + xpType, xp);
        sendSystemMessageProse(player, pp);
        return SCRIPT_CONTINUE;
    }
}
