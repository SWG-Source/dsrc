package script.test;

import script.dictionary;
import script.library.sui;
import script.library.utils;
import script.obj_id;
import script.string_id;

import java.util.Vector;

public class test_sui extends script.base_script
{
    public test_sui()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.test_sui");
        }
        else{
            debugSpeakMsg(self, "test_sui: attached!");
            debugServerConsoleMsg(self, "test_sui: attached!");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "test_sui: detached!");
        debugServerConsoleMsg(self, "test_sui: detached!");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        switch (text) {
            case "msgbox":
                debugSpeakMsg(self, "about to load msgbox");
                sui.msgbox(self, new string_id("ui", "ok"));
                break;
            case "complex msgbox":
                debugSpeakMsg(self, "about to load msgbox");
                sui.msgbox(self, self, "test test test", sui.YES_NO_CANCEL, "handleMsgBox", sui.MSG_NORMAL, "handleMsgBox");
                break;
            case "inputbox":
                debugSpeakMsg(self, "about to load normal styled inputbox");
                sui.inputbox(self, "test test test", "handleInputBox");
                break;
            case "combobox": {
                debugSpeakMsg(self, "about to load combo styled inputbox");
                String[] d = new String[5];
                for (int i = 0; i < d.length; i++) {
                    d[i] = "test_" + i;
                }
                sui.combobox(self, "test test test", d, "handleInputBox");
                break;
            }
            case "listbox": {
                debugSpeakMsg(self, "about to load listbox");
                Vector d = new Vector();
                d.setSize(0);
                for (int i = 0; i < sui.MAX_ARRAY_SIZE * 2; i++) {
                    d = utils.addElement(d, "test_" + i);
                }
                sui.listbox(self, "test test test", d, "handleListBox");
                break;
            }
            case "bank":
                debugSpeakMsg(self, "loading transfer sui");
                int cash = getCashBalance(self);
                int bank = getBankBalance(self);
                sui.transfer(self, self, "BANK PROMPT", "BANK TITLE", "Cash", cash, "Bank", bank, "handleBankTest");
                break;
            case "test forceClose": {
                int time = 5;
                sendSystemMessageTestingOnly(self, "Loading SUI to test force close...");
                int box = sui.msgbox(self, "force close test... closing box in " + time + " seconds");
                dictionary d = new dictionary();
                d.put("sui", box);
                messageTo(self, "testForceClose", d, time, false);
                break;
            }
            case "nuke sui":
                removeObjVar(self, "sui");
                break;
        }
        return SCRIPT_CONTINUE;
    }
    public void outputPageCallbackDictionary(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int pId = params.getInt("pageId");
        debugSpeakMsg(self, "outputPageCallbackDictionary: player = " + player);
        debugSpeakMsg(self, "outputPageCallbackDictionary: pageId = " + pId);
        debugSpeakMsg(self, "outputPageCallbackDictionary: buttonPressed = " + sui.getButtonPressed(params));
        debugSpeakMsg(self, "outputPageCallbackDictionary: intButtonPressed = " + sui.getIntButtonPressed(params));
        String[] props = params.getStringArray("propertyStrings");
        debugSpeakMsg(self, "outputPageCallbackDictionary: " + params.toString());
        if (props == null)
        {
            debugSpeakMsg(self, "outputPageCallbackDictionary: props = null");
        }
        else 
        {
            if (props.length == 0)
            {
                debugServerConsoleMsg(player, "outputPageCallbackDictionary: no property strings!");
            }
            else 
            {
                for (int i = 0; i < props.length; i++)
                {
                    debugSpeakMsg(self, "outputPageCallbackDictionary: prop " + i + " = " + props[i]);
                }
            }
        }
    }
    public int handleMsgBox(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "callback handleMsgBox: triggered!");
        outputPageCallbackDictionary(self, params);
        return SCRIPT_CONTINUE;
    }
    public int handleInputBox(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "callback handleInputBox: triggered!");
        outputPageCallbackDictionary(self, params);
        return SCRIPT_CONTINUE;
    }
    public int handleListBox(obj_id self, dictionary params) throws InterruptedException
    {
        debugServerConsoleMsg(self, "callback handleListBox: triggered!");
        outputPageCallbackDictionary(self, params);
        debugSpeakMsg(self, sui.LISTBOX_SELECTEDROW + " = " + utils.stringToInt(params.getString(sui.LISTBOX_SELECTEDROW)));
        return SCRIPT_CONTINUE;
    }
    public int handleBankTest(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "callback handleBankTest: triggered!");
        outputPageCallbackDictionary(self, params);
        debugSpeakMsg(self, "InputTo = " + sui.getTransferInputTo(params));
        debugSpeakMsg(self, "InputFrom = " + sui.getTransferInputFrom(params));
        return SCRIPT_CONTINUE;
    }
    public int testForceClose(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int box = params.getInt("sui");
        sendSystemMessageTestingOnly(self, "Attempting to force close sui #" + box);
        forceCloseSUIPage(box);
        return SCRIPT_CONTINUE;
    }
}
