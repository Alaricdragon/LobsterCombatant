package Lobster_Combatant.data.scripts;
import Lobster_Combatant.data.scripts.customCrews.listiners.LobsterCombatant_CustomCrew_XP_cargoHandler;
import Lobster_Combatant.data.scripts.customCrews.listiners.LobsterCombatant_DavesDescription;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.thoughtworks.xstream.XStream;

public class LobsterCombatant_startup extends BaseModPlugin {
    @Override
    public void onGameLoad(boolean newGame) {
        super.onGameLoad(newGame);
        //Global.getSector().getListenerManager().addListener(new LobsterCombatant_CommodityTooltipModifier(),true);
        Global.getSector().getEconomy().addUpdateListener(new LobsterCombatant_BaseCampaignEventListener());
        Global.getSector().getListenerManager().addListener(new LobsterCombatant_DavesDescription(),true);


        Global.getSector().getListenerManager().addListener(new LobsterCombatant_CustomCrew_XP_cargoHandler(),true);
        Global.getSector().getGenericPlugins().addPlugin(new LobsterCombatant_CustomCrew_XP_cargoHandler(),true);

    }

    @Override
    public void configureXStream(XStream x) {
        super.configureXStream(x);
        //x.alias("LobsterCombatant_CustomCrew_XP_cargoHandler", LobsterCombatant_CustomCrew_XP_cargoHandler.class);
    }

    @Override
    public void onApplicationLoad() {
        if (Global.getSettings().getModManager().isModEnabled("aaacrew_replacer")) {
            LobsterCombatant_CrewReplacerCompatibility.apply();
        }
    }
/*
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
 */
}