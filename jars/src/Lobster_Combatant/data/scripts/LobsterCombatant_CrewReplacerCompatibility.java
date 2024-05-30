package Lobster_Combatant.data.scripts;

import Lobster_Combatant.data.scripts.customCrews.*;
import Lobster_Combatant.data.scripts.customCrews.listiners.LobsterCombatant_CustomCrew_XP_cargoHandler;
import data.scripts.crewReplacer_Crew;
import data.scripts.crewReplacer_Main;
import data.scripts.crews.CrewReplacer_CrewType_crew;

public class LobsterCombatant_CrewReplacerCompatibility {
    /*/
    public static void apply(){
        crewReplacer_Crew Crew = new CrewReplacer_CrewType_crew();
        Crew.name = "LobsterCombatant_CombatLobster";
        Crew.crewPower = 1.2f;
        Crew.crewDefence = 1.2f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 1;
        crewReplacer_Main.getJob("raiding_marines").addCrew(Crew);


        Crew = new CrewReplacer_CrewType_crew();
        Crew.name = "LobsterCombatant_CombatLobster";
        Crew.crewPower = 1.1f;
        Crew.crewDefence = 1.1f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 0;
        crewReplacer_Main.getCrewSet("marines").addCrew(Crew);
    }
    */
    /*
    public static void apply(){
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
     */
    /*
    public static void apply(){
        crewReplacer_Crew Crew = new LobsterCombatant_CustomCrew_ReqMarines();
        Crew.name = "hand_weapons";
        Crew.crewPower = 0.2f;
        Crew.crewDefence = 5f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 0;
        crewReplacer_Main.getCrewSet("heavy_machinery").addCrew(Crew);
    }
     */
    /*
    public static void apply(){
        crewReplacer_Crew Crew = new LobsterCombatant_CustomCrew_lossOre();
        Crew.name = "crew";
        Crew.crewPower = 1f;
        Crew.crewDefence = 1f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 1;
        crewReplacer_Main.getJob("salvage_crew").addCrew(Crew);

    }
    */
    /*
    public static void apply(){
        crewReplacer_Crew Crew = new LobsterCombatant_CustomCrew_XPSystem();
        Crew.name = "LobsterCombatant_CombatLobster";
        Crew.crewPower = 1f;
        Crew.crewDefence = 1f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 1;
        crewReplacer_Main.getJob("raiding_marines").addCrew(Crew);
    }
     */
    /*
    public static void apply(){
        crewReplacer_Crew Crew = new LobsterCombatant_CustomCrew_DaveTheLegendaryDoomPirate();
        Crew.name = "LobsterCombatant_Dave";
        Crew.crewPower = 10000f;
        Crew.crewDefence = 10000f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 1;
        crewReplacer_Main.getJob("raiding_marines").addCrew(Crew);
    }
     */
    public static void apply(){
        crewReplacer_Crew Crew = new LobsterCombatant_CustomCrew_PlayerSkill();
        Crew.name = "LobsterCombatant_AutomatedShipSkillCrew";
        Crew.crewPower = 1f;
        Crew.crewDefence = 1f;
        Crew.crewPriority = 10;
        Crew.crewLoadPriority = 1;
        crewReplacer_Main.getJob("raiding_marines").addCrew(Crew);
    }
}
