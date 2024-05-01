package Lobster_Combatant.data.scripts.customCrews;
import com.fs.starfarer.api.campaign.CargoAPI;
import data.scripts.crewReplacer_Crew;

public class LobsterCombatant_CustomCrew_Volatile_StaticBoost extends crewReplacer_Crew {
    @Override
    public float getCrewPowerInCargo(CargoAPI cargo) {
        int bonus = (int)Math.min(getCrewInCargo(cargo),cargo.getCommodityQuantity("volatiles"));
        bonus = (int)Math.min(bonus,250);
        return super.getCrewPowerInCargo(cargo)+bonus;
    }
}
