package Lobster_Combatant.data.scripts.customCrews;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import data.scripts.crewReplacer_Crew;

import java.util.ArrayList;

public class LobsterCombatant_CustomCrew_Hullmod extends crewReplacer_Crew {
    public String hullModID = "repair_gantry";
    public float[] powerPerSize = {1,2,4,8};
    public ArrayList<FleetMemberAPI> getFleetMembers(CargoAPI cargo){
        ArrayList<FleetMemberAPI> out = new ArrayList<>();
        for (FleetMemberAPI a : cargo.getFleetData().getMembersListCopy()){
            if(a.getVariant().getHullMods().contains(hullModID)) {
                out.add(a);
            }
        }
        return out;
    }
    public float getCrewInCargo(CargoAPI cargo) {
        try {
            float power = 0;
            for (FleetMemberAPI a : getFleetMembers(cargo)) {
                String size = a.getVariant().getHullSpec().getHullSize().name();
                if (size == ShipAPI.HullSize.FRIGATE.name()) {
                    power += powerPerSize[0];
                } else if (size == ShipAPI.HullSize.DESTROYER.name()) {
                    power += powerPerSize[1];
                } else if (size == ShipAPI.HullSize.CRUISER.name()) {
                    power += powerPerSize[2];
                } else if (size == ShipAPI.HullSize.CAPITAL_SHIP.name()) {
                    power += powerPerSize[3];
                } else {
                    power += powerPerSize[1];
                }
            }
            return power;
        }catch (Exception e){
            return 0f;
        }
    }
    /*
    @Override
    public float getCrewToLose(CargoAPI cargo, float crewUsed, float crewLost) {
        return 0;
    }*/

    @Override
    public void removeCrew(CargoAPI cargo, float CrewToLost) {
        super.removeCrew(cargo, CrewToLost);
    }

    @Override
    public String getCrewIcon(CargoAPI cargo) {
        return "";//Global.getSettings().getSpriteName("systemMap", "mission_indicator");
    }

    @Override
    public String getDisplayName(CargoAPI cargo) {
        return "";//"automated tokens";
    }

    @Override
    public void displayCrewAvailable(CargoAPI cargo, float numberOfItems, TextPanelAPI text) {
        super.displayCrewAvailable(cargo, numberOfItems, text);
    }

    @Override
    public void displayCrewLost(CargoAPI cargo, float numberOfItems, TextPanelAPI text) {
        super.displayCrewLost(cargo, numberOfItems, text);
    }
    public float[] getCRLosses(float numberOfItems,ArrayList<FleetMemberAPI> fleet){
        float[] out = new float[fleet.size()];
        for (int a = 0; a < fleet.size(); a++){
            float averageLoss = numberOfItems / (fleet.size()-a);
            FleetMemberAPI b = fleet.get(a);
            //float maxLoss = fleet.get(a);
            out[a] = 0f;
        }
    }
}
