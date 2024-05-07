package Lobster_Combatant.data.scripts.customCrews;

import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.campaign.fleet.CargoData;
import data.scripts.crewReplacer_Crew;

public class LobsterCombatant_CustomCrew_ReqMarines extends crewReplacer_Crew {
    @Override
    public float getCrewInCargo(CargoAPI cargo) {
        return Math.min(super.getCrewInCargo(cargo), cargo.getCommodityQuantity("marines"));
    }
}
