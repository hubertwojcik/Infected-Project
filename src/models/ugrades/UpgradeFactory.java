package models.ugrades;

import interfaces.Upgrade;
import models.country.Country;

import java.util.ArrayList;
import java.util.List;

public class UpgradeFactory {

    public static List<Upgrade> getUpgradesForCountry(Country country) {
        List<Upgrade> upgrades = new ArrayList<>();

        switch (country.getName()) {
            case "Chiny":
                upgrades.add(new FieldHospitals(10, -0.05, 0.15, -0.15));
                upgrades.add(new ImprovedTesting(8, -0.08, 0.10, -0.10));
                upgrades.add(new Lockdown(20, -0.25, 0.00, 0.00));
                upgrades.add(new MassTesting(12, -0.10, 0.05, -0.05));
                upgrades.add(new MandatoryMasks(6, -0.15, 0.00, 0.00));
                upgrades.add(new PlaneTransportDisable(10, -0.07, 0.00, 0.00));
                upgrades.add(new PublicAwarnessCampaign(5, -0.10, 0.00, 0.00));
                upgrades.add(new ShipTransportDisable(8, -0.05, 0.00, 0.00));
                upgrades.add(new SocialDistancing(10, -0.15, 0.00, 0.00));
                upgrades.add(new TrainTransportDisable(9, -0.07, 0.00, 0.00));
                upgrades.add(new VaccineDevelopment(12, 0.00, 0.20, -0.20));
                break;
            case "Indie":
                upgrades.add(new FieldHospitals(15, -0.07, 0.12, -0.12));
                upgrades.add(new ImprovedTesting(10, -0.10, 0.08, -0.08));
                upgrades.add(new Lockdown(25, -0.30, 0.00, 0.00));
                upgrades.add(new MassTesting(15, -0.12, 0.07, -0.07));
                upgrades.add(new MandatoryMasks(8, -0.20, 0.00, 0.00));
                upgrades.add(new PlaneTransportDisable(12, -0.08, 0.00, 0.00));
                upgrades.add(new PublicAwarnessCampaign(6, -0.12, 0.00, 0.00));
                upgrades.add(new ShipTransportDisable(10, -0.06, 0.00, 0.00));
                upgrades.add(new SocialDistancing(12, -0.20, 0.00, 0.00));
                upgrades.add(new TrainTransportDisable(10, -0.08, 0.00, 0.00));
                upgrades.add(new VaccineDevelopment(10, 0.00, 0.25, -0.25));
                break;


            case "Wietnam":
                upgrades.add(new FieldHospitals(20, -0.10, 0.10, -0.10));
                upgrades.add(new ImprovedTesting(15, -0.08, 0.05, -0.05));
                upgrades.add(new Lockdown(30, -0.20, 0.00, 0.00));
                upgrades.add(new MassTesting(18, -0.12, 0.08, -0.08));
                upgrades.add(new MandatoryMasks(10, -0.15, 0.00, 0.00));
                upgrades.add(new PlaneTransportDisable(15, -0.10, 0.00, 0.00));
                upgrades.add(new PublicAwarnessCampaign(8, -0.12, 0.00, 0.00));
                upgrades.add(new ShipTransportDisable(12, -0.08, 0.00, 0.00));
                upgrades.add(new SocialDistancing(15, -0.20, 0.00, 0.00));
                upgrades.add(new TrainTransportDisable(12, -0.08, 0.00, 0.00));
                upgrades.add(new VaccineDevelopment(20, 0.00, 0.18, -0.18));
                break;

            case "Rosja":
                upgrades.add(new FieldHospitals(12, -0.06, 0.15, -0.15));
                upgrades.add(new ImprovedTesting(10, -0.10, 0.12, -0.12));
                upgrades.add(new Lockdown(22, -0.20, 0.00, 0.00));
                upgrades.add(new MassTesting(14, -0.12, 0.08, -0.08));
                upgrades.add(new MandatoryMasks(8, -0.18, 0.00, 0.00));
                upgrades.add(new PlaneTransportDisable(12, -0.08, 0.00, 0.00));
                upgrades.add(new PublicAwarnessCampaign(6, -0.12, 0.00, 0.00));
                upgrades.add(new ShipTransportDisable(10, -0.07, 0.00, 0.00));
                upgrades.add(new SocialDistancing(12, -0.20, 0.00, 0.00));
                upgrades.add(new TrainTransportDisable(11, -0.10, 0.00, 0.00));
                upgrades.add(new VaccineDevelopment(14, 0.00, 0.18, -0.18));
                break;

            case "Kazakhstan":
                upgrades.add(new FieldHospitals(15, -0.07, 0.12, -0.12));
                upgrades.add(new ImprovedTesting(12, -0.08, 0.10, -0.10));
                upgrades.add(new Lockdown(25, -0.25, 0.00, 0.00));
                upgrades.add(new MassTesting(18, -0.10, 0.06, -0.06));
                upgrades.add(new MandatoryMasks(10, -0.15, 0.00, 0.00));
                upgrades.add(new PlaneTransportDisable(14, -0.07, 0.00, 0.00));
                upgrades.add(new PublicAwarnessCampaign(8, -0.12, 0.00, 0.00));
                upgrades.add(new ShipTransportDisable(12, -0.08, 0.00, 0.00));
                upgrades.add(new SocialDistancing(14, -0.18, 0.00, 0.00));
                upgrades.add(new TrainTransportDisable(12, -0.08, 0.00, 0.00));
                upgrades.add(new VaccineDevelopment(18, 0.00, 0.15, -0.15));
                break;

            case "Mongolia":
                upgrades.add(new FieldHospitals(20, -0.08, 0.10, -0.10));
                upgrades.add(new ImprovedTesting(15, -0.07, 0.08, -0.08));
                upgrades.add(new Lockdown(28, -0.20, 0.00, 0.00));
                upgrades.add(new MassTesting(20, -0.10, 0.07, -0.07));
                upgrades.add(new MandatoryMasks(12, -0.12, 0.00, 0.00));
                upgrades.add(new PlaneTransportDisable(16, -0.06, 0.00, 0.00));
                upgrades.add(new PublicAwarnessCampaign(10, -0.10, 0.00, 0.00));
                upgrades.add(new ShipTransportDisable(14, -0.07, 0.00, 0.00));
                upgrades.add(new SocialDistancing(16, -0.15, 0.00, 0.00));
                upgrades.add(new TrainTransportDisable(14, -0.07, 0.00, 0.00));
                upgrades.add(new VaccineDevelopment(25, 0.00, 0.15, -0.15));
                break;

            case "Japonia":
                upgrades.add(new FieldHospitals(10, -0.05, 0.20, -0.2));
                upgrades.add(new ImprovedTesting(8, -0.08, 0.15, -0.15));
                upgrades.add(new Lockdown(18, -0.22, 0.00, 0.00));
                upgrades.add(new MassTesting(12, -0.12, 0.10, -0.10));
                upgrades.add(new MandatoryMasks(6, -0.18, 0.00, 0.00));
                upgrades.add(new PlaneTransportDisable(10, -0.07, 0.00, 0.00));
                upgrades.add(new PublicAwarnessCampaign(5, -0.15, 0.00, 0.00));
                upgrades.add(new ShipTransportDisable(8, -0.05, 0.00, 0.00));
                upgrades.add(new SocialDistancing(12, -0.20, 0.00, 0.00));
                upgrades.add(new TrainTransportDisable(9, -0.10, 0.00, 0.00));
                upgrades.add(new VaccineDevelopment(10, 0.00, 0.22, -0.2));
                break;

            case "Iran":
                upgrades.add(new FieldHospitals(14, -0.06, 0.12, -0.12));
                upgrades.add(new ImprovedTesting(11, -0.08, 0.10, 0.00));
                upgrades.add(new Lockdown(22, -0.20, 0.00, 0.00));
                upgrades.add(new MassTesting(15, -0.12, 0.07, 0.00));
                upgrades.add(new MandatoryMasks(10, -0.15, 0.00, 0.00));
                upgrades.add(new PlaneTransportDisable(12, -0.08, 0.00, 0.00));
                upgrades.add(new PublicAwarnessCampaign(8, -0.10, 0.00, 0.00));
                upgrades.add(new ShipTransportDisable(10, -0.06, 0.00, 0.00));
                upgrades.add(new SocialDistancing(14, -0.18, 0.00, 0.00));
                upgrades.add(new TrainTransportDisable(12, -0.08, 0.00, 0.00));
                upgrades.add(new VaccineDevelopment(16, 0.00, 0.18, -0.18));
                break;

            case "Pakistan":
                upgrades.add(new FieldHospitals(18, -0.08, 0.10, -0.10));
                upgrades.add(new ImprovedTesting(14, -0.10, 0.08, -0.08));
                upgrades.add(new Lockdown(26, -0.28, 0.00, 0.00));
                upgrades.add(new MassTesting(20, -0.14, 0.08, 0.00));
                upgrades.add(new MandatoryMasks(12, -0.20, 0.00, 0.00));
                upgrades.add(new PlaneTransportDisable(14, -0.09, 0.00, 0.00));
                upgrades.add(new PublicAwarnessCampaign(10, -0.12, 0.00, 0.00));
                upgrades.add(new ShipTransportDisable(12, -0.08, 0.00, 0.00));
                upgrades.add(new SocialDistancing(16, -0.20, 0.00, 0.00));
                upgrades.add(new TrainTransportDisable(14, -0.10, 0.00, 0.00));
                upgrades.add(new VaccineDevelopment(18, 0.00, 0.20, -0.20));
                break;

            case "Indonezja":
                upgrades.add(new FieldHospitals(17, -0.07, 0.12, -0.12));
                upgrades.add(new ImprovedTesting(13, -0.09, 0.08, -0.08));
                upgrades.add(new Lockdown(24, -0.25, 0.00, 0.00));
                upgrades.add(new MassTesting(18, -0.12, 0.08, 0.00));
                upgrades.add(new MandatoryMasks(10, -0.18, 0.00, 0.00));
                upgrades.add(new PlaneTransportDisable(14, -0.08, 0.00, 0.00));
                upgrades.add(new PublicAwarnessCampaign(9, -0.10, 0.00, 0.00));
                upgrades.add(new ShipTransportDisable(11, -0.07, 0.00, 0.00));
                upgrades.add(new SocialDistancing(15, -0.18, 0.00, 0.00));
                upgrades.add(new TrainTransportDisable(13, -0.09, 0.00, 0.00));
                upgrades.add(new VaccineDevelopment(16, 0.00, 0.20, -0.2));
                break;

            default:
                throw new IllegalArgumentException("Nieznany kraj: " + country.getName());

        }
        return  upgrades;
    }


}