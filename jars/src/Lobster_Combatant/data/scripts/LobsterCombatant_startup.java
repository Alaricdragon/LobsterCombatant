package Lobster_Combatant.data.scripts;
import Lobster_Combatant.data.scripts.customCrews.LobsterCombatant_CustomCrew_Volatile_MultiBoost;
import Lobster_Combatant.data.scripts.customCrews.LobsterCombatant_CustomCrew_Volatile_StaticBoost;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import data.scripts.crewReplacer_Main;

public class LobsterCombatant_startup extends BaseModPlugin {
    @Override
    public void onGameLoad(boolean newGame) {
        super.onGameLoad(newGame);
        Global.getSector().getListenerManager().addListener(new LobsterCombatant_CommodityTooltipModifier(),true);
        Global.getSector().getEconomy().addUpdateListener(new LobsterCombatant_BaseCampaignEventListener());
    }

    /*@Override
    public void onApplicationLoad() {
        if (Global.getSettings().getModManager().isModEnabled("aaacrew_replacer")) {
            crewReplacer_Main.getCrewSet("marines").addNewCrew("LobsterCombatant_CombatLobster",1.125f,1.5f,10,0);
            crewReplacer_Main.getJob("raiding_marines").addNewCrew("LobsterCombatant_CombatLobster",1.25f,2,10,1);
            crewReplacer_Main.getJob("Mission_hijack_marines").addBlackListCrew("LobsterCombatant_CombatLobster",1);
        }
    }*/

    @Override
    public void onApplicationLoad(){
        LobsterCombatant_CustomCrew_Volatile_MultiBoost Crew = new LobsterCombatant_CustomCrew_Volatile_MultiBoost();
        Crew.name = "LobsterCombatant_CombatLobster";
        Crew.crewPower = 1.2f;
        Crew.crewDefence = 1.2f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 1;
        crewReplacer_Main.getJob("raiding_marines").addCrew(Crew);

        LobsterCombatant_CustomCrew_Volatile_StaticBoost Crew2 = new LobsterCombatant_CustomCrew_Volatile_StaticBoost();
        Crew2.name = "LobsterCombatant_CombatLobster";
        Crew2.crewPower = 1.25f;
        Crew2.crewDefence = 1.25f;
        Crew2.crewPriority = 10;
        Crew2.crewLoadPriority = 0;
        crewReplacer_Main.getCrewSet("marines").addCrew(Crew2);
    }
}