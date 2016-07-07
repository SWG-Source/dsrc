package script.creature;

import script.*;
import script.library.buff;
import script.library.utils;

public class sarlacc extends script.base_script
{
    public sarlacc()
    {
    }
    public static final string_id SID_EXTRACT_BILE = new string_id("mob/sarlacc", "extract_bile");
    public static final string_id SID_BILE_FAIL = new string_id("mob/sarlacc", "bile_fail");
    public static final string_id SID_BILE_SUCCESS = new string_id("mob/sarlacc", "bile_success");
    public static final string_id SID_BILE_ALREADY = new string_id("mob/sarlacc", "bile_already");
    public static final string_id SID_SARLACC_ERUPT = new string_id("mob/sarlacc", "sarlacc_erupt");
    public static final string_id SID_SARLACC_POISON = new string_id("mob/sarlacc", "sarlacc_poison");
    public static final string_id SID_SARLACC_DOT = new string_id("mob/sarlacc", "sarlacc_dot");
    public static final String BILE_OBJECT = "object/tangible/loot/quest/quest_item_sarlacc_bile_jar.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasTriggerVolume(self, "sarlaccBreach"))
        {
            createTriggerVolume("sarlaccBreach", 30.f, true);
        }
        if (!hasTriggerVolume(self, "sarlaccPreBreach"))
        {
            createTriggerVolume("sarlaccPreBreach", 60.f, true);
        }
        messageTo(self, "poisonArea", null, 300, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        if (volumeName.equals("sarlaccBreach"))
        {
            if (!isPlayer(whoTriggeredMe))
            {
                return SCRIPT_CONTINUE;
            }
            if (!utils.hasScriptVar(self, "bile.eject"))
            {
                playClientEffectLoc(whoTriggeredMe, "clienteffect/cr_sarlacc_erupt.cef", getLocation(self), 3f);
                obj_id[] contents = getTriggerVolumeContents(self, "sarlaccBreach");
                if (contents != null)
                {
                    for (obj_id content : contents) {
                        if (isPlayer(content)) {
                            sendSystemMessage(content, SID_SARLACC_ERUPT);
                        }
                    }
                }
            }
            utils.setScriptVar(self, "bile.eject", 1);
            messageTo(self, "resetBileEject", null, 10.f, false);
            if (getEnhancedSkillStatisticModifierUncapped(whoTriggeredMe, "resistance_disease") < 24)
            {
                sendSystemMessage(whoTriggeredMe, SID_SARLACC_DOT);
                buff.applyBuff(whoTriggeredMe, "sarlaccSnare");
            }
        }
        else if (volumeName.equals("sarlaccPreBreach"))
        {
            if (!isPlayer(whoTriggeredMe))
            {
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(whoTriggeredMe, SID_SARLACC_POISON);
        }
        return SCRIPT_CONTINUE;
    }
    public int resetBileEject(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "bile.eject");
        return SCRIPT_CONTINUE;
    }
    public int poisonArea(obj_id self, dictionary params) throws InterruptedException
    {
        int playerHint = 0;
        if (!utils.hasScriptVar(self, "bile.eject"))
        {
            int bile = 0;
            obj_id[] contents = getTriggerVolumeContents(self, "sarlaccBreach");
            if (contents != null)
            {
                for (obj_id content : contents) {
                    if (isPlayer(content)) {
                        playerHint = 1;
                        if (bile == 0) {
                            bile = 1;
                            playClientEffectLoc(content, "clienteffect/cr_sarlacc_erupt.cef", getLocation(self), 3f);
                        }
                        if (getEnhancedSkillStatisticModifierUncapped(content, "resistance_disease") < 24) {
                            sendSystemMessage(content, SID_SARLACC_DOT);
                            buff.applyBuff(content, "sarlaccSnare");
                        }
                        sendSystemMessage(content, SID_SARLACC_ERUPT);
                    }
                }
            }
        }
        if (playerHint == 1)
        {
            messageTo(self, "poisonArea", null, 60 + rand(60), false);
        }
        else 
        {
            messageTo(self, "poisonArea", null, 120, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_EXTRACT_BILE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            obj_id[] items = getInventoryAndEquipment(player);
            if (items != null)
            {
                for (obj_id item1 : items) {
                    if ((getTemplateName(item1)).compareTo(BILE_OBJECT) == 0) {
                        sendSystemMessage(player, SID_BILE_ALREADY);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            if (utils.hasScriptVar(self, "bile.taken"))
            {
                sendSystemMessage(player, SID_BILE_FAIL);
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(player, SID_BILE_SUCCESS);
            obj_id pInv = utils.getInventoryContainer(player);
            if (isIdValid(pInv))
            {
                createObject(BILE_OBJECT, pInv, "");
            }
            utils.setScriptVar(self, "bile.taken", 1);
            messageTo(self, "resetBile", null, 300, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int resetBile(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "bile.taken");
        return SCRIPT_CONTINUE;
    }
}
