package com.mobile.ctm.snapt;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.mobile.ctm.snapt.model.QRResults;


public class ApplicationTest extends ApplicationTestCase<Application> {

    private String goodGas = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2C%2C%2C%2C%2C%2C%2C%2CPE1%204EF%2CBritish%20Gas%2CStandard%2CSO%2CM6%2C09876kWh%2C25122013-24122014%2C%2F%2C";
    private String badGasConsumptionZero = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2C%2C%2C%2C%2C%2C%2C%2CPE1%204EF%2CBritish%20Gas%2CStandard%2CDD%2CM6%2C00000kWh%2C25122013-24122014%2C%2F%2C";
    private String badGasConsumption = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2C%2C%2C%2C%2C%2C%2C%2CPE1%204EF%2CBritish%20Gas%2CStandard%2CDD%2CM6%2C%2C25122013-24122014%2C%2F%2C";
    private String badGasTariff = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2C%2C%2C%2C%2C%2C%2C%2CPE1%204EF%2CBritish%20Gas%2C%2CDD%2CM6%2C09876kWh%2C25122013-24122014%2C%2F%2C";
    private String badGasSupplier = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2C%2C%2C%2C%2C%2C%2C%2CPE1%204EF%2C%2CStandard%2CDD%2CM6%2C09876kWh%2C25122013-24122014%2C%2F%2C";
    private String badGasPostcode = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2C%2C%2C%2C%2C%2C%2C%2C%2CBritish%20Gas%2CStandard%2CDD%2CM6%2C09876kWh%2C25122013-24122014%2C%2F%2C";
    private String badGasEverything = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2C%2C%2C%2C%2C%2C%2C%2C%2C%2C%2C%2C%2C%2C%2C%2F%2C";

    private String goodElectricity = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2CPE1%204EF%2CZog%20Energy%2CMercury%2012%2CDD%2CM6%2C09876kWh%2C25122013-24122014%2C%2C%2C%2C%2C%2C%2C%2C%2F%2C";
    private String goodElectricityEco = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2CPE1%204EF%2CZog%20Energy%2CMercury%2012%2CDD%2CM6%2C09876kWh%2F12345kWh%2C25122013-24122014%2C%2C%2C%2C%2C%2C%2C%2C%2F%2C";
    private String badElectricityConsumptionZero = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2CPE1%204EF%2CZog%20Energy%2CMercury%2012%2CDD%2CM6%2C00000kWh%2C25122013-24122014%2C%2C%2C%2C%2C%2C%2C%2C%2F%2C";
    private String badElectricityConsumption = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2CPE1%204EF%2CZog%20Energy%2CMercury%2012%2CDD%2CM6%2C%2C25122013-24122014%2C%2C%2C%2C%2C%2C%2C%2C%2F%2C";
    private String badElectricityTariff = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2CPE1%204EF%2CZog%20Energy%2C%2CDD%2CM6%2C09876kWh%2C25122013-24122014%2C%2C%2C%2C%2C%2C%2C%2C%2F%2C";
    private String badElectricitySupplier = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2CPE1%204EF%2C%2CMercury%2012%2CDD%2CM6%2C09876kWh%2C25122013-24122014%2C%2C%2C%2C%2C%2C%2C%2C%2F%2C";
    private String badElectricityPostcode = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2C%2CZog%20Energy%2CMercury%2012%2CDD%2CM6%2C09876kWh%2C25122013-24122014%2C%2C%2C%2C%2C%2C%2C%2C%2F%2C";
    private String badElectricityEverything = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2C%2C%2C%2C%2C%2C%2C%2C%2C%2C%2C%2C%2C%2C%2C%2F%2C";

    private String goodDual = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2CPE1%204EF%2CZog%20Energy%2CMercury%2012%2CCC%2CM6%2C09876kWh%2C25122013-24122014%2CPE1%204EF%2CBritish%20Gas%2CStandard%2CPP%2CM6%2C09876kWh%2C25122013-24122014%2C%2F%2C";
    private String goodDualEco = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2CPE1%204EF%2CZog%20Energy%2CMercury%2012%2CDD%2CM6%2C09876kWh%2F12345kWh%2C25122013-24122014%2CPE1%204EF%2CBritish%20Gas%2CStandard%2CDD%2CM6%2C09876kWh%2C25122013-24122014%2C%2F%2C";
    private String goodDualPostcode = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2CPE1%204EF%2CZog%20Energy%2CMercury%2012%2CCC%2CM6%2C09876kWh%2C25122013-24122014%2CDN9%203AN%2CBritish%20Gas%2CStandard%2CPP%2CM6%2C09876kWh%2C25122013-24122014%2C%2F%2C";

    private String extraDataItemsPresent = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2C%2C%2C%2C%2C%2C%2C%2C%2CPE1%204EF%2CBritish%20Gas%2CStandard%2CDD%2CM6%2C09876kWh%2C25122013-24122014%2C%2F%2C";
    private String insufficientDataItemsPresent = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2C%2C%2C%2C%2C%2C%2CPE1%204EF%2CBritish%20Gas%2CStandard%2CDD%2CM6%2C09876kWh%2C25122013-24122014%2C%2F%2C";
    private String missingOpeningDelimiter = "http://www.oinc-energy.co.uk/action?parameters=%2C%2C%2C%2C%2C%2C%2CPE1%204EF%2CBritish%20Gas%2CStandard%2CDD%2CM6%2C09876kWh%2C25122013-24122014%2C%2F%2C";
    private String missingClosingDelimiter = "http://www.oinc-energy.co.uk/action?parameters=%2C%2F%2C%2C%2C%2C%2C%2C%2C%2CPE1%204EF%2CBritish%20Gas%2CStandard%2CDD%2CM6%2C09876kWh%2C25122013-24122014";
    private String missingOpeningClosingDelimiter = "http://www.oinc-energy.co.uk/action?parameters=%2C%2C%2C%2C%2C%2C%2CPE1%204EF%2CBritish%20Gas%2CStandard%2CDD%2CM6%2C09876kWh%2C25122013-24122014";


    public ApplicationTest() {
        super(Application.class);
    }


    //  *****  Good data allows for comparison  ***** //

    @SmallTest
    public void test_QRResults_Gas_CanPerformComparison() {
        QRResults qr = QRResults.getInstance();
        qr.WipeInstance();
        qr.Extract(goodGas);
        assertEquals(true, (boolean) qr.canPerformGasComparison());
    }

    @SmallTest
    public void test_QRResults_Electricity_CanPerformComparison() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodElectricity);
        assertEquals(true, (boolean) qr.canPerformElectricityComparison());
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Dual_CanPerformComparisons() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodDual);
        assertEquals(true, (boolean) qr.canPerformElectricityComparison());
        assertEquals(true, (boolean) qr.canPerformGasComparison());
        qr.WipeInstance();
    }

    //  *****  Insufficient Data Within QR Tests ***** //

    @SmallTest
    public void test_QRResults_Gas_CantPerformComparison_ZeroConsumption() {
        QRResults qr = QRResults.getInstance();
        qr.WipeInstance();
        qr.Extract(badGasConsumptionZero);
        assertEquals(false, (boolean) qr.canPerformGasComparison());
    }

    @SmallTest
    public void test_QRResults_Gas_CantPerformComparison_NoConsumption() {
        QRResults qr = QRResults.getInstance();
        qr.WipeInstance();
        qr.Extract(badGasConsumption);
        assertEquals(false, (boolean) qr.canPerformGasComparison());
    }

    @SmallTest
    public void test_QRResults_Gas_CantPerformComparison_NoTariff() {
        QRResults qr = QRResults.getInstance();
        qr.WipeInstance();
        qr.Extract(badGasTariff);
        assertEquals(false, (boolean) qr.canPerformGasComparison());
    }

    @SmallTest
    public void test_QRResults_Gas_CantPerformComparison_NoSupplier() {
        QRResults qr = QRResults.getInstance();
        qr.WipeInstance();
        qr.Extract(badGasSupplier);
        assertEquals(false, (boolean) qr.canPerformGasComparison());
    }

    @SmallTest
    public void test_QRResults_Gas_CantPerformComparison_NoPostcode() {
        QRResults qr = QRResults.getInstance();
        qr.WipeInstance();
        qr.Extract(badGasPostcode);
        assertEquals(false, (boolean) qr.canPerformGasComparison());
    }

    @SmallTest
    public void test_QRResults_Gas_CantPerformComparison_NoNothing() {
        QRResults qr = QRResults.getInstance();
        qr.WipeInstance();
        qr.Extract(badGasEverything);
        assertEquals(false, (boolean) qr.canPerformGasComparison());
    }

    @SmallTest
    public void test_QRResults_Electricity_CantPerformComparison_ZeroConsumption() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(badElectricityConsumptionZero);
        assertEquals(false, (boolean) qr.canPerformElectricityComparison());
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Electricity_CantPerformComparison_NoConsumption() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(badElectricityConsumption);
        assertEquals(false, (boolean) qr.canPerformElectricityComparison());
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Electricity_CantPerformComparison_NoTariff() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(badElectricityTariff);
        assertEquals(false, (boolean) qr.canPerformElectricityComparison());
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Electricity_CantPerformComparison_NoSupplier() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(badElectricitySupplier);
        assertEquals(false, (boolean) qr.canPerformElectricityComparison());
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Electricity_CantPerformComparison_NoPostcode() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(badElectricityPostcode);
        assertEquals(false, (boolean) qr.canPerformElectricityComparison());
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Electricity_CantPerformComparison_NoNothing() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(badElectricityEverything);
        assertEquals(false, (boolean) qr.canPerformElectricityComparison());
        qr.WipeInstance();
    }

//  *****  Format of QR Data Tests ***** //

    @SmallTest
    public void test_QRResults_Format_MissingData() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(insufficientDataItemsPresent);
        assertEquals(false, (boolean) qr.dataExtractedOK);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Format_ExtraData() {
        QRResults qr = QRResults.getInstance();
        qr.WipeInstance();
        qr.Extract(extraDataItemsPresent);
        assertEquals(false, (boolean) qr.dataExtractedOK);
    }

    @SmallTest
    public void test_QRResults_Format_CompleteData() {
        QRResults qr = QRResults.getInstance();
        qr.WipeInstance();
        qr.Extract(goodElectricity);
        assertEquals(true, (boolean) qr.dataExtractedOK);
    }

    @SmallTest
    public void test_QRResults_Format_OpeningDelimiterMissing() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(missingOpeningDelimiter);
        assertEquals(false, (boolean) qr.dataExtractedOK);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Format_ClosingDelimiterMissing() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(missingClosingDelimiter);
        assertEquals(false, (boolean) qr.dataExtractedOK);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Format_OpeningAndClosingDelimiterMissing() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(missingOpeningClosingDelimiter);
        assertEquals(false, (boolean) qr.dataExtractedOK);
        qr.WipeInstance();
    }


//  *****  Data Extract Tests ***** //

    @SmallTest
    public void test_QRResults_Electricity_ExtractPostcode() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodElectricity);
        assertEquals("PE14EF", qr.electricityPostCode);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Electricity_ExtractCurrentProvider() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodElectricity);
        assertEquals("Zog Energy", qr.electricityCurrentProvider);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Electricity_ExtractCurrentProviderUrlEncoded() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodElectricity);
        assertEquals("Zog%20Energy", qr.electricityCurrentProviderUrlEncoded);
        qr.WipeInstance();
 }

    @SmallTest
    public void test_QRResults_Electricity_ExtractCurrentTariff() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodElectricity);
        assertEquals("Mercury 12", qr.electricityCurrentTariff);
        qr.WipeInstance();
 }

    @SmallTest
    public void test_QRResults_Electricity_ExtractCurrentTariffUrlEncoded() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodElectricity);
        assertEquals("Mercury%2012", qr.electricityCurrentTariffUrlEncoded);
        qr.WipeInstance();
 }

    @SmallTest
    public void test_QRResults_Electricity_ExtractPaymentMethod() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodElectricity);
        assertEquals("DD", qr.electricityPaymentMethod);
        qr.WipeInstance();
 }

    @SmallTest
    public void test_QRResults_Electricity_ExtractMeterPoint() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodElectricity);
        assertEquals("M6", qr.electricityMeterReference);
        qr.WipeInstance();
 }

    @SmallTest
    public void test_QRResults_Electricity_ConsumptionStringIsCorrectlyExtracted() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodElectricity);
        assertEquals("9876", qr.electricityAnnualConsumption);
        qr.WipeInstance();
 }

    @SmallTest
    public void test_QRResults_Electricity_ConsumptionStringIsCorrectlyExtracted_Eco() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodElectricityEco);
        assertEquals("9876", qr.electricityAnnualConsumption);
        assertEquals("12345", qr.electricityAnnualConsumptionTierTwo);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Electricity_ExtractSupplyPeriod() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodElectricity);
        assertEquals("25122013-24122014", qr.electricitySupplyAnnualPeriod);
        qr.WipeInstance();
 }

    @SmallTest
    public void test_QRResults_Gas_ExtractPostcode() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodGas);
        assertEquals("PE14EF", qr.gasPostCode);
        qr.WipeInstance();
 }

    @SmallTest
    public void test_QRResults_Gas_ExtractCurrentProvider() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodGas);
        assertEquals("British Gas", qr.gasCurrentProvider);
        qr.WipeInstance();
 }

    @SmallTest
    public void test_QRResults_Gas_ExtractCurrentProviderUrlEncoded() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodGas);
        assertEquals("British%20Gas", qr.gasCurrentProviderUrlEncoded);
        qr.WipeInstance();
 }

    @SmallTest
    public void test_QRResults_Gas_ExtractCurrentTariff() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodGas);
        assertEquals("Standard", qr.gasCurrentTariff);
        qr.WipeInstance();
 }

    @SmallTest
    public void test_QRResults_Gas_ExtractCurrentTariffUrlEncoded() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodGas);
        assertEquals("Standard", qr.gasCurrentTariffUrlEncoded);
        qr.WipeInstance();
 }

    @SmallTest
    public void test_QRResults_Gas_ExtractPaymentMethod() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodGas);
        assertEquals("SO", qr.gasPaymentMethod);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Gas_ExtractMeterPoint() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodGas);
        assertEquals("M6", qr.gasMeterReference);
        qr.WipeInstance();
     }

    @SmallTest
    public void test_QRResults_Gas_ConsumptionStringIsCorrectlyExtracted() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodGas);
        assertEquals("9876", qr.gasAnnualConsumption);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Gas_ExtractSupplyPeriod() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodGas);
        assertEquals("25122013-24122014", qr.gasSupplyAnnualPeriod);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Dual_ExtractPostcode() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodDual);
        assertEquals("PE14EF", qr.electricityPostCode);
        assertEquals("PE14EF", qr.gasPostCode);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Dual_ExtractCurrentProvider() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodDual);
        assertEquals("Zog Energy", qr.electricityCurrentProvider);
        assertEquals("British Gas", qr.gasCurrentProvider);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Dual_ExtractCurrentProviderUrlEncoded() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodDual);
        assertEquals("Zog%20Energy", qr.electricityCurrentProviderUrlEncoded);
        assertEquals("British%20Gas", qr.gasCurrentProviderUrlEncoded);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Dual_ExtractCurrentTariff() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodDual);
        assertEquals("Mercury 12", qr.electricityCurrentTariff);
        assertEquals("Standard", qr.gasCurrentTariff);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Dual_ExtractCurrentTariffUrlEncoded() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodDual);
        assertEquals("Mercury%2012", qr.electricityCurrentTariffUrlEncoded);
        assertEquals("Standard", qr.gasCurrentTariffUrlEncoded);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Dual_ExtractPaymentMethod() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodDual);
        assertEquals("CC", qr.electricityPaymentMethod);
        assertEquals("PP", qr.gasPaymentMethod);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Dual_ExtractMeterPoint() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodDual);
        assertEquals("M6", qr.electricityMeterReference);
        assertEquals("M6", qr.gasMeterReference);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Dual_ConsumptionStringIsCorrectlyExtracted() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodDual);
        assertEquals("9876", qr.electricityAnnualConsumption);
        assertEquals("9876", qr.gasAnnualConsumption);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Dual_ConsumptionStringIsCorrectlyExtracted_Eco() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodDualEco);
        assertEquals("9876", qr.electricityAnnualConsumption);
        assertEquals("9876", qr.gasAnnualConsumption);
        assertEquals("12345", qr.electricityAnnualConsumptionTierTwo);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Dual_ExtractSupplyPeriod() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodDual);
        assertEquals("25122013-24122014", qr.electricitySupplyAnnualPeriod);
        assertEquals("25122013-24122014", qr.gasSupplyAnnualPeriod);
        qr.WipeInstance();
    }

//  *****  Data Display Format Tests ***** //

    @SmallTest
    public void test_QRResults_PaymentMethods_Display() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodElectricity);
        assertEquals("Direct Debit", qr.PaymentMethodForDisplay(qr.electricityPaymentMethod));
        qr.WipeInstance();
        qr.Extract(goodGas);
        assertEquals("Standing Order", qr.PaymentMethodForDisplay(qr.gasPaymentMethod));
        qr.WipeInstance();
        qr.Extract(goodDual);
        assertEquals("Pre-payment", qr.PaymentMethodForDisplay(qr.gasPaymentMethod));
        assertEquals("Cash or Cheque", qr.PaymentMethodForDisplay(qr.electricityPaymentMethod));
    }

    @SmallTest
    public void test_QRResults_Consumption_Display() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodGas);
        assertEquals("9876kWh", qr.ConsumptionForDisplay(qr.gasAnnualConsumption));
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_MeterReference_DefaultToElectricity() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodDual);
        assertEquals("M6", qr.meterPointForReference());
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_PostCode_DefaultToElectricity() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(goodDualPostcode);
        assertEquals("PE14EF", qr.postCodeForReference());
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_SecondScan_OverlayElectricity() {
        QRResults qr = QRResults.getInstance();
        qr.Extract(badElectricitySupplier);
        qr.Extract(goodElectricity);
        assertEquals("Zog Energy", qr.electricityCurrentProvider);
        qr.WipeInstance();
    }

    @SmallTest
    public void test_QRResults_Quickly_CorrectUrlForElectricity() {
        QRResults qr = QRResults.getInstance();
        qr.WipeInstance();
        qr.Extract(goodElectricity);
        String url = "/Energy/Qr/Quickly?postcode=PE14EF&lecSupplier=Zog%20Energy&lecTariff=Mercury%2012&lecPaymentMethod=DD&lecUsage=9876&gasSupplier=null&gasTariff=null&gasPaymentMethod=null&gasUsage=null&lecUsageTwo=0&email=null&marketingAllowed=false&meterReference=M6&platform=Android";
        String generatedUrl = qr.GenerateUrlQuickly();
        int index = generatedUrl.indexOf("/Energy");
        generatedUrl = generatedUrl.substring(index);
        assertEquals(url, generatedUrl);
    }

    @SmallTest
    public void test_QRResults_Quickly_CorrectUrlForGas() {
        QRResults qr = QRResults.getInstance();
        qr.WipeInstance();
        qr.Extract(goodGas);
        String url = "/Energy/Qr/Quickly?postcode=PE14EF&lecSupplier=null&lecTariff=null&lecPaymentMethod=null&lecUsage=null&gasSupplier=British%20Gas&gasTariff=Standard&gasPaymentMethod=SO&gasUsage=9876&lecUsageTwo=null&email=null&marketingAllowed=false&meterReference=M6&platform=Android";
        String generatedUrl = qr.GenerateUrlQuickly();
        int index = generatedUrl.indexOf("/Energy");
        generatedUrl = generatedUrl.substring(index);
        assertEquals(url, generatedUrl);
    }

    @SmallTest
    public void test_QRResults_Quickly_CorrectUrlForDual() {
        QRResults qr = QRResults.getInstance();
        qr.WipeInstance();
        qr.Extract(goodDualEco);
        String url = "/Energy/Qr/Quickly?postcode=PE14EF&lecSupplier=Zog%20Energy&lecTariff=Mercury%2012&lecPaymentMethod=DD&lecUsage=9876&gasSupplier=British%20Gas&gasTariff=Standard&gasPaymentMethod=DD&gasUsage=9876&lecUsageTwo=12345&email=null&marketingAllowed=false&meterReference=M6&platform=Android";
        String generatedUrl = qr.GenerateUrlQuickly();
        int index = generatedUrl.indexOf("/Energy");
        generatedUrl = generatedUrl.substring(index);
        assertEquals(url, generatedUrl);
    }

//    List mockery = mock(List.class);
//    mockery.add("string");
//    verify(mockery).add("strong");

}
