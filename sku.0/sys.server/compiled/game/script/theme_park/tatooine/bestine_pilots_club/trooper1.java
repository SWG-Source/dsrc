package script.theme_park.tatooine.bestine_pilots_club;

import script.obj_id;

public class trooper1 extends script.base_script
{
    public trooper1()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id club = getObjIdObjVar(self, "club");
        messageTo(club, "trooper1Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
