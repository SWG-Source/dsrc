package script.item.skill_buff;

import script.*;
import script.library.buff;
import script.library.utils;

public class base extends script.base_script
{
    public base()
    {
    }
    public static final String OBJVAR_SKILL_BUFF_NAME = "skill_buff.name";
    public static final String OBJVAR_SKILL_BUFF_SKILL1 = "skill_buff.skill1";
    public static final String OBJVAR_SKILL_BUFF_AMOUNT1 = "skill_buff.amount1";
    public static final String OBJVAR_SKILL_BUFF_LENGTH1 = "skill_buff.length1";
    public static final String OBJVAR_SKILL_BUFF_SKILL2 = "skill_buff.skill2";
    public static final String OBJVAR_SKILL_BUFF_AMOUNT2 = "skill_buff.amount2";
    public static final String OBJVAR_SKILL_BUFF_LENGTH2 = "skill_buff.length2";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCount(self, 5);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "skill_buff.name"))
        {
            return SCRIPT_CONTINUE;
        }
        String name = getStringObjVar(self, "skill_buff.name");
        String newName = "";
        switch (name) {
            case "skill_buff_carbine_accuracy":
                newName = "fs_carbine_acc";
                break;
            case "skill_buff_carbine_speed":
                newName = "fs_carbine_spd";
                break;
            case "skill_buff_heavy_weapon_accuracy":
                newName = "fs_heavy_acc";
                break;
            case "skill_buff_heavy_weapon_speed":
                newName = "fs_heavy_spd";
                break;
            case "skill_buff_mask_scent":
                newName = "fs_mask_scent";
                break;
            case "skill_buff_melee_accuracy":
                newName = "fs_melee_acc";
                break;
            case "skill_buff_melee_defense":
                newName = "fs_melee_def";
                break;
            case "skill_buff_onehandmelee_accuracy":
                newName = "fs_1h_acc";
                break;
            case "skill_buff_onehandmelee_speed":
                newName = "fs_1h_spd";
                break;
            case "skill_buff_pistol_accuracy":
                newName = "fs_pistol_acc";
                break;
            case "skill_buff_pistol_speed":
                newName = "fs_pistol_spd";
                break;
            case "skill_buff_polearm_accuracy":
                newName = "fs_polearm_acc";
                break;
            case "skill_buff_polearm_speed":
                newName = "fs_polearm_spd";
                break;
            case "skill_buff_ranged_accuracy":
                newName = "fs_ranged_acc";
                break;
            case "skill_buff_ranged_defense":
                newName = "fs_ranged_def";
                break;
            case "skill_buff_rifle_accuracy":
                newName = "fs_rifle_acc";
                break;
            case "skill_buff_rifle_speed":
                newName = "fs_rifle_spd";
                break;
            case "skill_buff_thrown_accuracy":
                newName = "fs_thrown_acc";
                break;
            case "skill_buff_thrown_speed":
                newName = "fs_thrown_spd";
                break;
            case "skill_buff_twohandmelee_accuracy":
                newName = "fs_2h_acc";
                break;
            case "skill_buff_twohandmelee_speed":
                newName = "fs_2h_spd";
                break;
            case "skill_buff_unarmed_accuracy":
                newName = "fs_unarmed_acc";
                break;
            case "skill_buff_unarmed_speed":
                newName = "fs_unarmed_spd";
                break;
        }
        if (!newName.equals(""))
        {
            removeObjVar(self, "skill_buff");
            setObjVar(self, "buff_name", newName);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenuOrServerNotify(menu_info_types.ITEM_USE, null);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id inv = utils.getInventoryContainer(player);
            if (isIdValid(inv) && utils.isNestedWithin(self, inv))
            {
                consumeSkillBuffItem(self, player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "buff_name"))
        {
            return SCRIPT_CONTINUE;
        }
        String buff_name = getStringObjVar(self, "buff_name");
        int buff_crc = getStringCrc(buff_name);
        String skill = buff.getEffectParam(buff_name, 1);
        int value = (int)buff.getEffectValue(buff_name, 1);
        int duration = (int)buff.getDuration(buff_name);
        string_id skill_name = new string_id("stat_n", skill);
        names[idx] = "skill_modifier_name";
        attribs[idx] = utils.packStringId(skill_name);
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "skill_modifier_amount";
        attribs[idx] = "" + value;
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "skill_modifier_length";
        attribs[idx] = formatTime(duration);
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void consumeSkillBuffItem(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        String buff_name = getStringObjVar(self, "buff_name");
        int buff_crc = getStringCrc(buff_name);
        if (buff.hasBuff(player, buff_crc))
        {
            string_id sid = new string_id("skill_buff_n", "already_have");
            sendCombatSpamMessage(player, sid, COMBAT_RESULT_GENERIC);
            return;
        }
        if (buff.applyBuff(player, buff_name))
        {
            incrementCount(self, -1);
            string_id message = new string_id("skill_buff_d", "consume");
            sendCombatSpamMessage(player, message, COMBAT_RESULT_GENERIC);
            int cnt = getCount(self);
            if (cnt < 1)
            {
                destroyObject(self);
            }
        }
        return;
    }
    public int handleUseSkillBuff(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("skill_buff", "handleUseSkillBuff");
        obj_id player = params.getObjId("player");
        LOG("skill_buff", "handleUseSkillBuff player = " + player);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        LOG("skill_buff", "handleUseSkillBuff passed check");
        String name = getStringObjVar(self, OBJVAR_SKILL_BUFF_NAME);
        if (hasSkillModModifier(player, name))
        {
            string_id sid = new string_id("skill_buff_n", "already_have");
            sendSystemMessage(player, sid);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, OBJVAR_SKILL_BUFF_SKILL1))
        {
            String skill1 = getStringObjVar(self, OBJVAR_SKILL_BUFF_SKILL1);
            int amount1 = getIntObjVar(self, OBJVAR_SKILL_BUFF_AMOUNT1);
            int length1 = getIntObjVar(self, OBJVAR_SKILL_BUFF_LENGTH1);
            addSkillModModifier(player, name, skill1, amount1, length1, false, true);
        }
        if (hasObjVar(self, OBJVAR_SKILL_BUFF_SKILL2))
        {
            String skill2 = getStringObjVar(self, OBJVAR_SKILL_BUFF_SKILL2);
            int amount2 = getIntObjVar(self, OBJVAR_SKILL_BUFF_AMOUNT2);
            int length2 = getIntObjVar(self, OBJVAR_SKILL_BUFF_LENGTH2);
            addSkillModModifier(player, name, skill2, amount2, length2, false, true);
        }
        LOG("skill_buf", "handleUseSkillBuff decrementing count on " + self);
        incrementCount(self, -1);
        string_id message = new string_id("skill_buff_d", "consume");
        sendSystemMessage(player, message);
        int cnt = getCount(self);
        if (cnt < 1)
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public String formatTime(int seconds) throws InterruptedException
    {
        String result = "";
        int hours = (seconds / 3600);
        seconds -= (hours * 3600);
        int minutes = (seconds / 60);
        seconds -= (minutes * 60);
        if (hours > 0)
        {
            result += "" + hours + ":";
        }
        if (minutes > 0 || hours > 0)
        {
            if (hours > 0 && minutes < 10)
            {
                result += "0";
            }
            result += "" + minutes + ":";
        }
        if (minutes > 0 && seconds < 10)
        {
            result += "0";
        }
        result += "" + seconds;
        return result;
    }
}
