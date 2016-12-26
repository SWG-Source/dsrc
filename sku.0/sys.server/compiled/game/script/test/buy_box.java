package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class buy_box extends script.base_script
{
    public buy_box()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException {
        if (!isGod(self) || getGodLevel(self) < 10 || !isPlayer(self)) {
            detachScript(self, "test.buy_box");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        LOG("tony_test", "player " + player + " item " + item);
        if (item == menu_info_types.ITEM_PUBLIC_CONTAINER_USE1)
        {
            debugSpeakMsg(player, "You just purchased " + self + "!");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (names == null || attribs == null || names.length != attribs.length)
        {
            return SCRIPT_CONTINUE;
        }
        final int firstFreeIndex = getFirstFreeIndex(names);
        if (firstFreeIndex >= 0 && firstFreeIndex < names.length)
        {
            names[firstFreeIndex] = "cost";
            attribs[firstFreeIndex] = "42";
        }
        return SCRIPT_CONTINUE;
    }
}
