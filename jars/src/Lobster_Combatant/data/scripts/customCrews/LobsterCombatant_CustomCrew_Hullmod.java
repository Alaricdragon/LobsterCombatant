package Lobster_Combatant.data.scripts.customCrews;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.CrewReplacer_Log;
import data.scripts.crewReplacer_Crew;

import java.util.ArrayList;

public class LobsterCombatant_CustomCrew_Hullmod extends crewReplacer_Crew {
    public String hullModID = "repair_gantry";
    public float[] powerPerSize = {1,2,4,8};
    public float[] lossesTemp;
    public float HP = 100;
    public ArrayList<FleetMemberAPI> getFleetMembers(CargoAPI cargo){
        ArrayList<FleetMemberAPI> out = new ArrayList<>();
        for (FleetMemberAPI a : cargo.getFleetData().getMembersListCopy()){
            if(a.getVariant().getHullMods().contains(hullModID)) {
                out.add(a);
            }
        }
        return out;
    }
    public float[] getCRLosses(float numberOfItems,ArrayList<FleetMemberAPI> fleet){
        //NOTE: NOT DONE YET
        float[] out = new float[fleet.size()];
        float averageLossTemp = numberOfItems / (fleet.size());
        for (int a = 0; a < fleet.size(); a++){
            float averageLoss = numberOfItems / (fleet.size()-a);
            FleetMemberAPI b = fleet.get(a);
            //fleet.get(a);
            //float maxLoss = fleet.get(a);
            out[a] = averageLossTemp/getSizePower(b);//NOTE: This will need to be modified by the % of current CR readiness.
        }
        return out;
    }
    public float getSizePower(FleetMemberAPI ship){
        String size = ship.getVariant().getHullSpec().getHullSize().name();
        if (size.equals(ShipAPI.HullSize.FRIGATE.name())) return powerPerSize[0];
        if (size.equals(ShipAPI.HullSize.DESTROYER.name())) return powerPerSize[1];
        if (size.equals(ShipAPI.HullSize.CRUISER.name())) return powerPerSize[2];
        if (size.equals(ShipAPI.HullSize.CAPITAL_SHIP.name())) return powerPerSize[3];
        return powerPerSize[1];
    }
    public float getCrewInCargo(CargoAPI cargo) {
        try {
            float power = 0;
            for (FleetMemberAPI a : getFleetMembers(cargo)) {
                power += getSizePower(a)*HP;//NOTE: change this to be *HP*percent of available CR readyness.
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
    public float getCrewDefence(CargoAPI cargo) {
        return super.getCrewDefence(cargo)/HP;
    }

    @Override
    public float getCrewPower(CargoAPI cargo) {
        return super.getCrewPower(cargo)/HP;
    }

    @Override
    public void removeCrew(CargoAPI cargo, float CrewToLost) {
        ArrayList<FleetMemberAPI> fleet = getFleetMembers(cargo);
        for (int a = 0; a < lossesTemp.length; a++){
            //remove combat readiness of ships here.
        }
    }

    @Override
    public String getCrewIcon(CargoAPI cargo) {
        return Global.getSettings().getSpriteName("systemMap", "icon_fleet0");
    }

    @Override
    public String getDisplayName(CargoAPI cargo) {
        return "salvage ships";//"automated tokens";
    }

    @Override
    public void displayCrewAvailable(CargoAPI cargo, float numberOfItems, TextPanelAPI text) {
        numberOfItems = getFleetMembers(cargo).size();
        super.displayCrewAvailable(cargo, numberOfItems, text);
    }

    @Override
    public void displayCrewLost(CargoAPI cargo, float numberOfItems, TextPanelAPI text) {
        ArrayList<FleetMemberAPI> fleet = getFleetMembers(cargo);
        lossesTemp = getCRLosses(numberOfItems,fleet);
        //fleet.get(a).getSpriteOverride()
        for (int a = 0; a < lossesTemp.length; a++){
            if (lossesTemp[a] != 0) {
                String displayName = fleet.get(a).getShipName();//getDisplayName(cargo);
                TooltipMakerAPI tt = text.beginTooltip();
                TooltipMakerAPI iwt = tt.beginImageWithText(fleet.get(a).getHullSpec().getSpriteName(), 24);
                String numberStr = (int) /*numberOfItems*/lossesTemp[a] + "%";
                LabelAPI label = iwt.addPara("%s lost %s of its CR", 0, Misc.getHighlightColor(),displayName, numberStr);
                tt.addImageWithText(0);
                text.addTooltip();
            }
        }
    }
}
