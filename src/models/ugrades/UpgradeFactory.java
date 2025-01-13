package models.ugrades;

import interfaces.Upgrade;

import java.util.List;

public class UpgradeFactory {
    public static List<Upgrade> getUpgrades(){
        return List.of(
                new FieldHospitals(),
                new ImprovedTesting(),
                new Lockdown(),
                new MassTesting(),
                new MandatoryMasks(),
                new PlaneTransportDisable(),
                new PublicAwarnessCampaign(),
                new ShipTransportDisable(),
                new SocialDistancing(),
                new TrainTransportDisable(),
                new VaccineDevelopment()
        );
    }
}
