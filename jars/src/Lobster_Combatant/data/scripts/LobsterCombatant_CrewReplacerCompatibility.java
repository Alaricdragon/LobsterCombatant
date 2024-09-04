package Lobster_Combatant.data.scripts;

import Lobster_Combatant.data.scripts.customCrews.*;
import Lobster_Combatant.data.scripts.customCrews.listiners.LobsterCombatant_CustomCrew_XP_cargoHandler;
import com.fs.starfarer.api.Global;
import data.scripts.crewReplacer_Crew;
import data.scripts.crewReplacer_Main;
import data.scripts.crews.CrewReplacer_PrivateCrewType_crew;

public class LobsterCombatant_CrewReplacerCompatibility {
    public static void apply(){
        if (Global.getSettings().getBoolean("LobsterCombatant_CustomCrew_PrivateCrewType_crew")) apply_0();
        if (Global.getSettings().getBoolean("LobsterCombatant_CustomCrew_Volatile_MultiBoost")) apply_1();
        if (Global.getSettings().getBoolean("LobsterCombatant_CustomCrew_ReqMarines")) apply_2();
        if (Global.getSettings().getBoolean("LobsterCombatant_CustomCrew_lossOre")) apply_3();
        if (Global.getSettings().getBoolean("LobsterCombatant_CustomCrew_XPSystem")) apply_4();
        if (Global.getSettings().getBoolean("LobsterCombatant_CustomCrew_DaveTheLegendaryDoomPirate")) apply_5();
        if (Global.getSettings().getBoolean("LobsterCombatant_CustomCrew_PlayerSkill")) apply_6();
        if (Global.getSettings().getBoolean("LobsterCombatant_CustomCrew_Hullmod")) apply_7();
    }
    public static void apply_0(){
        crewReplacer_Crew Crew = new CrewReplacer_PrivateCrewType_crew();
        Crew.name = "LobsterCombatant_CombatLobster";
        Crew.crewPower = 1.2f;
        Crew.crewDefence = 1.2f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 1;
        crewReplacer_Main.getJob("raiding_marines").addCrew(Crew);


        Crew = new CrewReplacer_PrivateCrewType_crew();
        Crew.name = "LobsterCombatant_CombatLobster";
        Crew.crewPower = 1.1f;
        Crew.crewDefence = 1.1f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 0;
        crewReplacer_Main.getCrewSet("marines").addCrew(Crew);
    }
    public static void apply_1(){
        crewReplacer_Crew Crew = new LobsterCombatant_CustomCrew_Volatile_MultiBoost();
        Crew.name = "LobsterCombatant_CombatLobster";
        Crew.crewPower = 1.2f;
        Crew.crewDefence = 1.2f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 1;
        crewReplacer_Main.getJob("raiding_marines").addCrew(Crew);

        Crew = new LobsterCombatant_CustomCrew_Volatile_StaticBoost();
        Crew.name = "LobsterCombatant_CombatLobster";
        Crew.crewPower = 1.25f;
        Crew.crewDefence = 1.25f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 0;
        crewReplacer_Main.getCrewSet("marines").addCrew(Crew);
    }
    public static void apply_2(){
        crewReplacer_Crew Crew = new LobsterCombatant_CustomCrew_ReqMarines();
        Crew.name = "hand_weapons";
        Crew.crewPower = 0.2f;
        Crew.crewDefence = 5f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 0;
        crewReplacer_Main.getCrewSet("heavy_machinery").addCrew(Crew);
    }
    public static void apply_3(){
        crewReplacer_Crew Crew = new LobsterCombatant_CustomCrew_lossOre();
        Crew.name = "crew";
        Crew.crewPower = 1f;
        Crew.crewDefence = 1f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 1;
        crewReplacer_Main.getJob("salvage_crew").addCrew(Crew);

    }
    public static void apply_4(){
        crewReplacer_Crew Crew = new LobsterCombatant_CustomCrew_XPSystem();
        Crew.name = "LobsterCombatant_CombatLobster";
        Crew.crewPower = 1f;
        Crew.crewDefence = 1f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 1;
        crewReplacer_Main.getJob("raiding_marines").addCrew(Crew);
    }
    public static void apply_5(){
        crewReplacer_Crew Crew = new LobsterCombatant_CustomCrew_DaveTheLegendaryDoomPirate();
        Crew.name = "LobsterCombatant_Dave";
        Crew.crewPower = 10000f;
        Crew.crewDefence = 10000f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 1;
        crewReplacer_Main.getJob("raiding_marines").addCrew(Crew);
    }
    public static void apply_6(){
        crewReplacer_Crew Crew = new LobsterCombatant_CustomCrew_PlayerSkill();
        Crew.name = "LobsterCombatant_AutomatedShipSkillCrew";
        Crew.crewPower = 1f;
        Crew.crewDefence = 1f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 1;
        crewReplacer_Main.getJob("raiding_marines").addCrew(Crew);
    }
    public static void apply_7(){
        crewReplacer_Crew Crew = new LobsterCombatant_CustomCrew_Hullmod();
        Crew.name = "LobsterCombatant_HullModAttempt";
        Crew.crewPower = 20f;
        Crew.crewDefence = 20f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 1;
        crewReplacer_Main.getJob("salvage_crew").addCrew(Crew);
    }
}
