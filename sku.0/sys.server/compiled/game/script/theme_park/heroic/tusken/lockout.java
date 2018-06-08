package script.theme_park.heroic.tusken;

import script.dictionary;
import script.obj_id;

public class lockout extends script.base_script
{
    public lockout()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        permissionsMakePrivate(self);
        return SCRIPT_CONTINUE;
    }
    public int unlock(obj_id self, dictionary params) throws InterruptedException
    {
        permissionsMakePublic(self);
        return SCRIPT_CONTINUE;
    }
    public int lock(obj_id self, dictionary params) throws InterruptedException
    {
        permissionsMakePrivate(self);
        return SCRIPT_CONTINUE;
    }
}
