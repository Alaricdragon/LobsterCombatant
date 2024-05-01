package Lobster_Combatant.data.scripts.customCrews;

import com.fs.starfarer.api.campaign.CargoAPI;
import data.scripts.crewReplacer_Crew;
public class LobsterCombatant_CustomCrew_Volatile_MultiBoost extends crewReplacer_Crew {
    @Override
    public float getCrewPower(CargoAPI cargo) {
        float multi = 1;
        if (cargo.getCommodityQuantity("volatiles") != 0){
            multi = 1.25f;
        }
        return super.getCrewPower(cargo)*multi;
    }

    @Override
    public float getCrewDefence(CargoAPI cargo) {
        float multi = 1;
        if (cargo.getCommodityQuantity("volatiles") != 0){
            multi = 1.25f;
        }
        return super.getCrewDefence(cargo) * multi;
    }
}

