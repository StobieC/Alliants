package com.mobile.ctm.snapt.model;
import android.content.Context;

import com.mobile.ctm.snapt.R;
import com.mobile.ctm.snapt.controller.AppController;
import com.mobile.ctm.snapt.utils.Utils;
import com.mobile.ctm.snapt.view.StartFragment;


public class QRResults {

    private static QRResults oneAndOnly;

    private String rawData;
    public String electricityPostCode;
    public String electricityCurrentProvider;
    public String electricityCurrentProviderUrlEncoded;
    public String electricityCurrentTariff;
    public String electricityCurrentTariffUrlEncoded;
    public String electricityPaymentMethod;
    public String electricityMeterReference;
    public String electricityAnnualConsumption;
    public String electricityAnnualConsumptionTierTwo;
    public String electricitySupplyAnnualPeriod;
    public String gasPostCode;
    public String gasCurrentProvider;
    public String gasCurrentProviderUrlEncoded;
    public String gasCurrentTariff;
    public String gasCurrentTariffUrlEncoded;
    public String gasPaymentMethod;
    public String gasMeterReference;
    public String gasAnnualConsumption;
    public String gasSupplyAnnualPeriod;
    public Boolean economySeven;
    public String email;

    public Boolean dataExtractedOK;
    public String errorMessage;
    public String errorMessageForPost;
    public boolean performingSecondScan;

    public static QRResults getInstance() {
        if (oneAndOnly == null) {
            oneAndOnly = new QRResults();
        }
        return oneAndOnly;
    }

    private QRResults() {}

    public void Extract(String rawResult) {
        rawData = rawResult;
        String extract = extractUsefulData(rawResult);
        if (dataExtractedOK) {
            String AsciiCommaUrl = "%2C";
            String[] results = extract.split(AsciiCommaUrl, -1);
            if (results.length == 14) {
                implantElectricityData(results);
                implantGasData(results);
            } else if (results.length < 14) {
                errorMessage = "QR data contains less than 14 data items.";
                errorMessageForPost = "QR_LT_14";
                dataExtractedOK = false;
                AppController.getInstance().postQRScanFailure();
            } else {
                errorMessage = "QR data contains more than 14 data items.";
                errorMessageForPost = "QR_MT_14";
                dataExtractedOK = false;
                AppController.getInstance().postQRScanFailure();
            }
        }
    }

    private String extractUsefulData(String rawResult) {
        String dataDelimiter = "%2C%2F%2C";
        int firstOccurrence = rawResult.indexOf(dataDelimiter);
        if (firstOccurrence > -1) {
            int lastOccurrence = rawResult.lastIndexOf(dataDelimiter);
            if (lastOccurrence > firstOccurrence) {
                dataExtractedOK = true;
                int ddL = dataDelimiter.length();
                int rrL = rawResult.length();
                return rawResult.substring(firstOccurrence + ddL, rrL - ddL);
            } else {
                dataExtractedOK = false;
                errorMessage = "QR data incorrectly delimited.";
                errorMessageForPost = "QR_DL_1";
                AppController.getInstance().postQRScanFailure();
                return "";
            }
        }
        else {
            dataExtractedOK = false;
            errorMessage = "QR data incorrectly delimited.";
            errorMessageForPost = "QR_DL_0";
            AppController.getInstance().postQRScanFailure();
            return "";
        }
    }

    private void implantElectricityData(String[] results)
    {
        if (results[0].isEmpty() || results[1].isEmpty() || results[2].isEmpty() || results[3].isEmpty() || results[4].isEmpty() || results[5].isEmpty() || results[6].isEmpty()) return;
        String AsciiSpaceUrl = "%20";
        electricityPostCode = results[0].toUpperCase().replaceAll(AsciiSpaceUrl, "");
        electricityCurrentProviderUrlEncoded = results[1];
        electricityCurrentProvider = electricityCurrentProviderUrlEncoded.replaceAll(AsciiSpaceUrl, " ");
        electricityCurrentTariffUrlEncoded = results[2];
        electricityCurrentTariff = electricityCurrentTariffUrlEncoded.replaceAll(AsciiSpaceUrl, " ");
        electricityPaymentMethod = results[3].toUpperCase();
        electricityMeterReference = results[4];
        determineElectricityConsumption(results[5]);
        electricitySupplyAnnualPeriod = results[6];
    }

    private void implantGasData(String[] results)
    {
        if (results[7].isEmpty() || results[8].isEmpty() || results[9].isEmpty() || results[10].isEmpty() || results[11].isEmpty() || results[12].isEmpty() || results[13].isEmpty()) return;
        String AsciiSpaceUrl = "%20";
        gasPostCode = results[7].toUpperCase().replaceAll(AsciiSpaceUrl, "");
        gasCurrentProviderUrlEncoded = results[8];
        gasCurrentProvider = gasCurrentProviderUrlEncoded.replaceAll(AsciiSpaceUrl, " ");
        gasCurrentTariffUrlEncoded = results[9];
        gasCurrentTariff = gasCurrentTariffUrlEncoded.replaceAll(AsciiSpaceUrl, " ");
        gasPaymentMethod = results[10].toUpperCase();
        gasMeterReference = results[11];
        gasAnnualConsumption = formatConsumptionString(results[12]);
        gasSupplyAnnualPeriod = results[13];

    }

    private void determineElectricityConsumption(String consumption) {
        String AsciiSlashUrl = "%2F";
        if (!consumption.contains(AsciiSlashUrl)) {
            electricityAnnualConsumption = formatConsumptionString(consumption);
            electricityAnnualConsumptionTierTwo = "0";
            economySeven = false;
        } else {
            String[] tieredConsumption = consumption.split(AsciiSlashUrl, 0);
            if (tieredConsumption.length == 2) {
                electricityAnnualConsumption = formatConsumptionString(tieredConsumption[0]);
                electricityAnnualConsumptionTierTwo = formatConsumptionString(tieredConsumption[1]);
                economySeven = true;
            }
            else
            {
                electricityAnnualConsumption = "0";
                electricityAnnualConsumptionTierTwo = "0";
                economySeven = false;
                errorMessage = "Your QR code does not contain the required details of how much electricity you use, we are not able to perform a comparison.";
                errorMessageForPost = "QR_EC_01";
                dataExtractedOK = false;
                AppController.getInstance().postQRScanFailure();
            }
        }
    }

    private String formatConsumptionString(String consumption) {
        String formattedConsumption = consumption.toUpperCase().replaceAll("KWH", "");
        int numericalConsumption;
        try {
            numericalConsumption = Integer.parseInt(formattedConsumption);
        }
        catch(NumberFormatException e) {
            return "0";
        }
        return String.valueOf(numericalConsumption);
    }

    public Boolean canPerformElectricityComparison() {
        if (electricityPostCode == null ||  electricityPostCode.isEmpty()) {
            errorMessage = "Your QR code does not contain a supply postcode, we are not able to perform a comparison.";
            return false;
        }
        if (electricityCurrentProvider == null || electricityCurrentProvider.isEmpty()) {
            errorMessage = "Your QR code does not contain details of your electricity provider, we are not able to perform a comparison.";
            return false;
        }
        if (electricityCurrentTariff == null || electricityCurrentTariff.isEmpty()) {
            errorMessage = "Your QR code does not contain details of your electricity tariff, we are not able to perform a comparison.";
            return false;
        }
        if (electricityPaymentMethod == null || electricityPaymentMethod.isEmpty()) {
            errorMessage = "Your QR code does not contain details of how you pay for your electricity, we are not able to perform a comparison.";
            return false;
        }
        if (electricityAnnualConsumption == null || electricityAnnualConsumption.isEmpty() || electricityAnnualConsumption.equals("0")) {
            errorMessage = "Your QR code does not contain details of how much electricity you use, we are not able to perform a comparison.";
            return false;
        }
        return true;
    }

    public Boolean canPerformGasComparison() {
        if (gasPostCode == null || gasPostCode.isEmpty()) {
            errorMessage = "Your QR code does not contain a supply postcode, we are not able to perform a comparison.";
            return false;
        }
        if (gasCurrentProvider == null || gasCurrentProvider.isEmpty()) {
            errorMessage = "Your QR code does not contain details of your gas provider, we are not able to perform a comparison.";
            return false;
        }
        if (gasCurrentTariff == null || gasCurrentTariff.isEmpty()) {
            errorMessage = "Your QR code does not contain details of your gas tariff, we are not able to perform a comparison.";
            return false;
        }
        if (gasPaymentMethod == null || gasPaymentMethod.isEmpty()) {
            errorMessage = "Your QR code does not contain details of how you pay for your gas, we are not able to perform a comparison.";
            return false;
        }
        if (gasAnnualConsumption == null || gasAnnualConsumption.isEmpty() || gasAnnualConsumption.equals("0")) {
            errorMessage = "Your QR code does not contain details of how much gas you use, we are not able to perform a comparison.";
            return false;
        }
        return true;
    }

    public String PaymentMethodForDisplay(String paymentMethod)
    {
        switch (paymentMethod) {
            case "DD": return "Direct Debit";
            case "SO": return "Standing Order";
            case "CC": return "Cash or Cheque";
            case "PP": return "Pre-payment";
            default: return "Unrecognised";
        }
    }

    public String ConsumptionForDisplay(String usage)
    {
        return usage + "kWh";
    }

    private String getMonthName(String month, Context context) {
        Integer monthIndex = Integer.parseInt(month);
        return context.getResources().getStringArray(R.array.month_names)[monthIndex - 1];
    }

    public String GasStatementPeriodForDisplay(Context context) {
        String dayStart = gasSupplyAnnualPeriod.substring(0, 2);
        String monthStart = gasSupplyAnnualPeriod.substring(2, 4);
        String monthNameStart = getMonthName(monthStart, context);
        String yearStart = gasSupplyAnnualPeriod.substring(4, 8);
        String dayEnd = gasSupplyAnnualPeriod.substring(9, 11);
        String monthEnd = gasSupplyAnnualPeriod.substring(11, 13);
        String monthNameEnd = getMonthName(monthEnd, context);
        String yearEnd = gasSupplyAnnualPeriod.substring(13, 17);
        return dayStart + " " + monthNameStart + " " + yearStart + " - " + dayEnd + " " + monthNameEnd + " " + yearEnd;
    }

    public String ElectricityStatementPeriodForDisplay(Context context) {
        String dayStart = electricitySupplyAnnualPeriod.substring(0, 2);
        String monthStart = electricitySupplyAnnualPeriod.substring(2, 4);
        String monthNameStart = getMonthName(monthStart, context);
        String yearStart = electricitySupplyAnnualPeriod.substring(4, 8);
        String dayEnd = electricitySupplyAnnualPeriod.substring(9, 11);
        String monthEnd = electricitySupplyAnnualPeriod.substring(11, 13);
        String monthNameEnd = getMonthName(monthEnd, context);
        String yearEnd = electricitySupplyAnnualPeriod.substring(13, 17);
        return dayStart + " " + monthNameStart + " " + yearStart + " - " + dayEnd + " " + monthNameEnd + " " + yearEnd;
    }

    public Boolean areTwoPostcodesPresent() {
        if (electricityPostCode == null || gasPostCode == null)
            return false;
        return !(electricityPostCode.equals(gasPostCode));
    }

    public String GenerateUrlQuickly() {
        StringBuilder url = new StringBuilder(256);
        return url.append(Utils.Quickly)
                  .append(Utils.Postcode_Parameter).append(postCodeForReference())
                  .append(Utils.Electricity_Supplier_Name_Parameter).append(electricityCurrentProviderUrlEncoded)
                  .append(Utils.Electricity_Tariff_Name_Parameter).append(electricityCurrentTariffUrlEncoded)
                  .append(Utils.Electricity_Payment_Method_Parameter).append(electricityPaymentMethod)
                  .append(Utils.Electricity_Usage_Parameter).append(electricityAnnualConsumption)
                  .append(Utils.Gas_Supplier_Name_Parameter).append(gasCurrentProviderUrlEncoded)
                  .append(Utils.Gas_Tariff_Name_Parameter).append(gasCurrentTariffUrlEncoded)
                  .append(Utils.Gas_Payment_Method_Parameter).append(gasPaymentMethod)
                  .append(Utils.Gas_Usage_Parameter).append(gasAnnualConsumption)
                  .append(Utils.Night_Percentage_Parameter).append(electricityAnnualConsumptionTierTwo)
                  .append(Utils.Email_Parameter).append(email)
                  .append(Utils.Marketing_Allowed_Parameter).append(StartFragment.getMarketingAllowed())
                  .append(Utils.Meter_Reference_Parameter).append(meterPointForReference())
                  .append(Utils.Platform_Parameter).append(Utils.Android_Platform)
                  .toString();
    }

    public String meterPointForReference() {
        if (electricityMeterReference == null || electricityMeterReference.isEmpty())
            return gasMeterReference;
        return electricityMeterReference;
    }

    public String postCodeForReference() {
        if (electricityPostCode == null || electricityPostCode.isEmpty())
            return gasPostCode;
        return electricityPostCode;

    }

    public void WipeInstance() {
        oneAndOnly = new QRResults();
    }

    public String GenerateUrlScanFailure() {
        StringBuilder url = new StringBuilder(256);
        return url.append(Utils.ScanFail)
                .append(Utils.Platform_Parameter_ScanFailure).append(Utils.Android_Platform)
                .append(Utils.Meter_Parameter).append(meterPointForReference())
                .append(Utils.Message_Parameter).append(errorMessageForPost)
                .append(Utils.Data_Parameter).append(rawData)
                .toString();
    }
}