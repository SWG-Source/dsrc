package script.library;

import script.obj_id;
import script.prose_package;
import script.string_id;

public class luck extends script.base_script
{
    public luck()
    {
    }
    public static boolean isLucky(obj_id player) throws InterruptedException
    {
        return isLucky(player, 0.10f, true);
    }
    public static boolean isLucky(obj_id player, float mod) throws InterruptedException
    {
        return isLucky(player, mod, true);
    }
    public static boolean isLucky(obj_id player, float mod, boolean showFlyText) throws InterruptedException
    {
        if (!isPlayer(player))
        {
            return false;
        }
        int level = getLevel(player);
        int cap = level * 5;
        float chance = rand(1, (cap * 1000));
        chance /= 1000.0f;
        float luck = getSkillStatisticModifier(player, "luck");
        float luckBonus = getEnhancedSkillStatisticModifierUncapped(player, "luck_modified");
        luck += luckBonus;
        if (luck > cap)
        {
            luck = cap;
        }
        luck *= mod;
        if (chance < luck)
        {
            if (showFlyText)
            {
                showLuckyFlyText(player);
            }
            return true;
        }
        return false;
    }
    public static void showLuckyFlyText(obj_id player) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, new string_id("system_msg", "lucky_fly_text"));
        showFlyTextPrivateProseWithFlags(player, player, pp, 1.5f, colors.GOLD, FLY_TEXT_FLAG_IS_LUCKY);
    }
}
