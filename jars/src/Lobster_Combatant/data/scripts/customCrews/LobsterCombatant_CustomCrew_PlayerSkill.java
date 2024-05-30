package Lobster_Combatant.data.scripts.customCrews;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import data.scripts.crewReplacer_Crew;

public class LobsterCombatant_CustomCrew_PlayerSkill extends crewReplacer_Crew {
    @Override
    public float getCrewInCargo(CargoAPI cargo) {
        try {
            if(cargo.getFleetData().getCommander().getStats().getSkillLevel("automated_ships") == 0) return 0f;
            float skill = 20;
            float level = cargo.getFleetData().getCommander().getStats().getLevel();
            skill *= level;
            return skill;
        }catch (Exception e){
            return 0f;
        }
    }

    @Override
    public float getCrewToLose(CargoAPI cargo, float crewUsed, float crewLost) {
        return 0;
    }

    @Override
    public String getCrewIcon(CargoAPI cargo) {
        return Global.getSettings().getSpriteName("systemMap", "mission_indicator");
    }

    @Override
    public String getDisplayName(CargoAPI cargo) {
        return "automated tokens";
    }
}