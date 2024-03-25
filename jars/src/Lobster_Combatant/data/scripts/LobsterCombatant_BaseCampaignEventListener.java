package Lobster_Combatant.data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class LobsterCombatant_BaseCampaignEventListener implements EconomyAPI.EconomyUpdateListener {

    @Override
    public void commodityUpdated(String commodityId) {
    }
    @Override
    public void economyUpdated() {
        //making everything a variable for your convenience. just the values here for basic changes.
        String condition = "volturnian_lobster_pens";
        String commodity = "LobsterCombatant_CombatLobster";
        String[] supplyIndustry = {"patrolhq","militarybase","highcommand","lionsguard"};
        int[] supplyValue = {-1,0,1,2};
        String[] demandIndustry = {"grounddefenses","heavybatteries"};
        int[] demandValue = {-3,-2};
        String source = "LobsterCombatant_BaseCampaignEventListener";
        //this for loop runs over every market in the game
        for (MarketAPI market : Global.getSector().getEconomy().getMarketsCopy()){
            //this if statement locks my supply to only be on markets that have lobsters.
            if(market.hasCondition(condition)) {
                //a for loop to run though every single one of my wanted industry
                for (int a = 0; a < supplyIndustry.length; a++) {
                    //this if statment prevents crashes if you try to get a industry that does snot exsist.
                    if (market.hasIndustry(supplyIndustry[a])) {
                        //this line of code adds the commodity to the industry as supply
                        market.getIndustry(supplyIndustry[a]).getSupply(commodity).getQuantity().modifyFlat(source, market.getSize() + supplyValue[a]);
                    }
                }
            }
            for(int a = 0; a < demandIndustry.length; a++) {
                if (market.hasIndustry(demandIndustry[a])) {
                    //this line of code adds the commodity to the industry as demand
                    market.getIndustry(demandIndustry[a]).getDemand(commodity).getQuantity().modifyFlat(source, market.getSize() + demandValue[a]);
                }
            }
        }
    }
    /*@Override
    public void economyUpdated() {
        //making everything a variable for your convenience. just the values here for basic changes.
        String commodity = "LobsterCombatant_CombatLobster";
        String supplyIndustry = "lionsguard";
        String demandIndustry = "heavybatteries";
        String source = "LobsterCombatant_BaseCampaignEventListener";
        //this for loop runs over every market in the game
        for (MarketAPI market : Global.getSector().getEconomy().getMarketsCopy()){
            //this if statment prevents crashes if you try to get a industry that does snot exsist.
            if (market.hasIndustry(supplyIndustry)){
                //this line of code adds the commodity to the industry as supply
                market.getIndustry(supplyIndustry).getSupply(commodity).getQuantity().modifyFlat(source, market.getSize()-1);
            }
            if (market.hasIndustry(demandIndustry)){
                //this line of code adds the commodity to the industry as demand
                market.getIndustry(demandIndustry).getDemand(commodity).getQuantity().modifyFlat(source, market.getSize()-3);
            }
        }
    }*/
    @Override
    public boolean isEconomyListenerExpired() {
        return false;
    }
}
