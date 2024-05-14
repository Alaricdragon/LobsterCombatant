package Lobster_Combatant.data.scripts.customCrews;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.campaign.listeners.GroundRaidObjectivesListener;
import com.fs.starfarer.api.impl.PlayerFleetPersonnelTracker;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.CrewReplacer_Log;
import data.scripts.crewReplacer_Crew;

public class LobsterCombatant_CustomCrew_XPSystem extends crewReplacer_Crew {
    /*
    possible issues i need to check for:
    1) crashes because i tried to get a 'unset' memory.
    2) the unset strings.
    steps needed:
    1)(function set, not done) get amount of XP in the class (also the XP bonuses)
    2)(functions set. not done) when calculating losses, apply the new XP gained (saving the old XP bonuses)
    3)(functions set. requires strings.) overwrite both displays to show the damage / defence change form XP.
     */
    //private static final String className = "CrewReplacer_CrewType_marine";
    //private static final boolean logsActive = Global.getSettings().getBoolean("CrewReplacerDisplayMarineLogs");
    //private float[] XPGainData = new float[]{0f,0f};

    private float crewUsedTemp;
    private float ratio = 0;
    private float crewLossMultiTemp = 1;
    private float maxDef = 1.2f;
    private float maxPower = 2f;
    private final static String memoryKey="$LobsterCombatant_LobsterXP";
    public void addXP(float xp,CargoAPI cargo){
        try {
            CrewReplacer_Log.loging("attempting to get dat sweet XP. old XP: " + Global.getSector().getMemory().getFloat(memoryKey) + " gained XP: " + xp, this, true);
            xp += Global.getSector().getMemory().getFloat(memoryKey);
            xp = Math.min(xp, getCrewInCargo(cargo));
            Global.getSector().getMemory().set(memoryKey, xp);
            CrewReplacer_Log.loging("should be new XP: " + xp + " acusal new XP: " + Global.getSector().getMemory().getFloat(memoryKey), this, true);
        }catch (Exception e){
            CrewReplacer_Log.loging("failed to set XP. exseption: "+e,this,true);
        }
    }
    public float getXP(CargoAPI cargo){
        return Math.min(Global.getSector().getMemory().getFloat(memoryKey),getCrewInCargo(cargo));
    }
    public float getEffectiveXP(float xp,CargoAPI cargo){
        float crew = getCrewInCargo(cargo);
        if (crew == 0) return 0f;
        return xp/crew;
    }
    public float getXPDefneceMulti(float xp, CargoAPI cargo){
        return 1 + (getEffectiveXP(xp,cargo) * maxDef);
    }
    public float getXPPowerMulti(float xp, CargoAPI cargo){
        return 1 + (getEffectiveXP(xp,cargo) * maxPower);
    }
    @Override
    public float getCrewDefence(CargoAPI cargo) {
        return super.getCrewDefence(cargo) * getXPDefneceMulti(getXP(cargo),cargo);
    }

    @Override
    public float getCrewPower(CargoAPI cargo) {
        return super.getCrewPower(cargo) * getXPPowerMulti(getXP(cargo),cargo);
    }
    public float getCrewUsedRatio(CargoAPI cargo, float crewUsed){
        Object[] ObjectTemp = (Object[])ExtraData;
        //StatBonus attackerTemp = Global.getSector().getPlayerFleet().getStats().getDynamic().getMod(Stats.PLANETARY_OPERATIONS_MOD);
        float temp = crewUsed*getCrewPower(cargo);
        CrewReplacer_Log.loging("HERE:" + temp,this,true);
        ratio = (float)Math.floor(temp) / (int)ObjectTemp[1];
        return ratio;
    }
    @Override
    public float getCrewToLose(CargoAPI cargo, float crewUsed, float crewLost) {
        float temp = super.getCrewToLose(cargo, crewUsed, crewLost);
        crewUsedTemp = temp;
        return temp;
    }

    @Override
    public void removeCrew(CargoAPI cargo, float CrewToLost) {
        super.removeCrew(cargo, CrewToLost);

        float CrewUsed = crewUsedTemp;
        crewLossMultiTemp = getXPDefneceMulti(getXP(cargo),cargo);
        if(CrewUsed == 0){
            //PlayerFleetPersonnelTracker thing = PlayerFleetPersonnelTracker.getInstance();
            //thing.update();
            //CrewReplacer_Log.loging("no marrines used. exiting XP gain code...",this,logsActive);
            return;
        }
        try{
            Object[] ObjectTemp = (Object[])ExtraData;
            PlayerFleetPersonnelTracker thing = PlayerFleetPersonnelTracker.getInstance();
            //float marines = cargo.getMarines();
            GroundRaidObjectivesListener.RaidResultData data = (GroundRaidObjectivesListener.RaidResultData) ObjectTemp[0];

            //float total = marines + CrewToLost;//data.marinesLost;
            float xpGain = data.xpGained;
            xpGain *= thing.XP_PER_RAID_MULT;
            if (xpGain < 0) xpGain = 0;
            xpGain *= getCrewUsedRatio(cargo,crewUsedTemp);
            addXP(xpGain,cargo);
            //add XP
        }catch (Exception E){
            //CrewReplacer_Log.loging("ERROR: failed to add XP to marines",this,true);
        }
    }
    @Override
    public void displayCrewAvailable(CargoAPI cargo, float numberOfItems, TextPanelAPI text){
        String displayName = getDisplayName(cargo);

        TooltipMakerAPI tt = text.beginTooltip();
        TooltipMakerAPI iwt = tt.beginImageWithText(getCrewIcon(cargo), 24);
        String numberStr = (int) numberOfItems + "";
        LabelAPI label = iwt.addPara(numberStr + " " + displayName, 0, Misc.getHighlightColor(), numberStr);
        //cargo.getFleetData().getFleet().getStats().getDynamic().getMod(Stats.PLANETARY_OPERATIONS_MOD).getMultBonus()
        float xpTemp = Math.round(100 * (getXPPowerMulti(getXP(cargo),cargo) - 1));//PlayerFleetPersonnelTracker.getInstance().getMarineData().getXPLevel());

        if(xpTemp != 0 || true) {
            //String temp = "%";
            String XP = "";//CrewReplacer_StringHelper.getString(className,"displayCrewAvailable",0,xpTemp+"");// + "%";
            //XP+=temp;
            iwt.addPara(""/*CrewReplacer_StringHelper.getString(className,"displayCrewAvailable",1)*/, 0, Misc.getHighlightColor(), XP);
        }
        tt.addImageWithText(0);

        text.addTooltip();
    }
    @Override
    public void displayCrewLost(CargoAPI cargo,float numberOfItems, TextPanelAPI text){
        String displayName = getDisplayName(cargo);

        TooltipMakerAPI tt = text.beginTooltip();
        TooltipMakerAPI iwt = tt.beginImageWithText(getCrewIcon(cargo), 24);
        String numberStr = (int) numberOfItems + "";
        LabelAPI label = iwt.addPara(numberStr + " " + displayName, 0, Misc.getHighlightColor(), numberStr);
        //cargo.getFleetData().getFleet().getStats().getDynamic().getMod(Stats.PLANETARY_OPERATIONS_MOD).getMultBonus()
        float xpTemp = Math.round(100 * (1 - crewLossMultiTemp));//PlayerFleetPersonnelTracker.getInstance().getMarineData().getXPLevel());
        if(xpTemp != 0 || true) {
            //String temp = "%";
            String XP = "";//CrewReplacer_StringHelper.getString(className,"displayCrewLost",0,xpTemp+"");// + "%";
            //XP+=temp;
            iwt.addPara(""/*CrewReplacer_StringHelper.getString(className,"displayCrewLost",1)*/, 0, Misc.getHighlightColor(),XP);
            //CrewReplacer_Log.loging("loss multi that was saved is: " + MarinesLossMultiTemp,this,logsActive);
        }
        tt.addImageWithText(0);

        text.addTooltip();
    }
}
