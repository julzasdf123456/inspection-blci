package com.lopez.julz.inspectionv2.api;

import androidx.room.ColumnInfo;

public class ServiceConnectionInspections {

    private String id;
    private String ServiceConnectionId;
    private String SEMainCircuitBreakerAsPlan;
    private String SEMainCircuitBreakerAsInstalled;
    private String SENoOfBranchesAsPlan;
    private String SENoOfBranchesAsInstalled;
    private String PoleGIEstimatedDiameter;
    private String PoleGIHeight;
    private String PoleGINoOfLiftPoles;
    private String PoleConcreteEstimatedDiameter;
    private String PoleConcreteHeight;
    private String PoleConcreteNoOfLiftPoles;
    private String PoleHardwoodEstimatedDiameter;
    private String PoleHardwoodHeight;
    private String PoleHardwoodNoOfLiftPoles;
    private String PoleRemarks;
    private String SDWSizeAsPlan;
    private String SDWSizeAsInstalled;
    private String SDWLengthAsPlan;
    private String SDWLengthAsInstalled;
    private String GeoBuilding;
    private String GeoTappingPole;
    private String GeoMeteringPole;
    private String GeoSEPole;
    private String FirstNeighborName;
    private String FirstNeighborMeterSerial;
    private String SecondNeighborName;
    private String SecondNeighborMeterSerial;
    private String EngineerInchargeName;
    private String EngineerInchargeTitle;
    private String EngineerInchargeLicenseNo;
    private String EngineerInchargeLicenseValidity;
    private String EngineerInchargeContactNo;
    private String Status;
    private String Inspector;
    private String DateOfVerification;
    private String EstimatedDateForReinspection;
    private String Notes;
    private String LightingOutlets;
    private String ConvenienceOutlets;
    private String Motor;
    private String TotalLoad;
    private String ContractedDemand;
    private String ContractedEnergy;
    private String DistanceFromSecondaryLine;
    private String SizeOfSecondary;
    private String SizeOfSDW;
    private String TypeOfSDW;
    private String ServiceEntranceStatus;
    private String HeightOfSDW;
    private String DistanceFromTransformer;
    private String SizeOfTransformer;
    private String TransformerNo;
    private String PoleNo;
    private String ConnectedFeeder;
    private String SizeOfSvcPoles;
    private String HeightOfSvcPoles;
    private String LinePassingPrivateProperty;
    private String WrittenConsentByPropertyOwner;
    private String ObstructionOfLines;
    private String LinePassingRoads;
    private String Recommendation;
    private String ForPayment;
    // JUNE 19, 2023
    private String Zone;
    private String Block;
    private String Feeder;
    private String BillDeposit;
    private String LoadType;
    private String Rate;

    public ServiceConnectionInspections(String id, String serviceConnectionId, String SEMainCircuitBreakerAsPlan, String SEMainCircuitBreakerAsInstalled, String SENoOfBranchesAsPlan, String SENoOfBranchesAsInstalled, String poleGIEstimatedDiameter, String poleGIHeight, String poleGINoOfLiftPoles, String poleConcreteEstimatedDiameter, String poleConcreteHeight, String poleConcreteNoOfLiftPoles, String poleHardwoodEstimatedDiameter, String poleHardwoodHeight, String poleHardwoodNoOfLiftPoles, String poleRemarks, String SDWSizeAsPlan, String SDWSizeAsInstalled, String SDWLengthAsPlan, String SDWLengthAsInstalled, String geoBuilding, String geoTappingPole, String geoMeteringPole, String geoSEPole, String firstNeighborName, String firstNeighborMeterSerial, String secondNeighborName, String secondNeighborMeterSerial, String engineerInchargeName, String engineerInchargeTitle, String engineerInchargeLicenseNo, String engineerInchargeLicenseValidity, String engineerInchargeContactNo, String status, String inspector, String dateOfVerification, String estimatedDateForReinspection, String notes, String lightingOutlets, String convenienceOutlets, String motor, String totalLoad, String contractedDemand, String contractedEnergy, String distanceFromSecondaryLine, String sizeOfSecondary, String sizeOfSDW, String typeOfSDW, String serviceEntranceStatus, String heightOfSDW, String distanceFromTransformer, String sizeOfTransformer, String transformerNo, String poleNo, String connectedFeeder, String sizeOfSvcPoles, String heightOfSvcPoles, String linePassingPrivateProperty, String writtenConsentByPropertyOwner, String obstructionOfLines, String linePassingRoads, String recommendation, String forPayment, String zone, String block, String feeder, String billDeposit, String loadType, String rate) {
        this.id = id;
        ServiceConnectionId = serviceConnectionId;
        this.SEMainCircuitBreakerAsPlan = SEMainCircuitBreakerAsPlan;
        this.SEMainCircuitBreakerAsInstalled = SEMainCircuitBreakerAsInstalled;
        this.SENoOfBranchesAsPlan = SENoOfBranchesAsPlan;
        this.SENoOfBranchesAsInstalled = SENoOfBranchesAsInstalled;
        PoleGIEstimatedDiameter = poleGIEstimatedDiameter;
        PoleGIHeight = poleGIHeight;
        PoleGINoOfLiftPoles = poleGINoOfLiftPoles;
        PoleConcreteEstimatedDiameter = poleConcreteEstimatedDiameter;
        PoleConcreteHeight = poleConcreteHeight;
        PoleConcreteNoOfLiftPoles = poleConcreteNoOfLiftPoles;
        PoleHardwoodEstimatedDiameter = poleHardwoodEstimatedDiameter;
        PoleHardwoodHeight = poleHardwoodHeight;
        PoleHardwoodNoOfLiftPoles = poleHardwoodNoOfLiftPoles;
        PoleRemarks = poleRemarks;
        this.SDWSizeAsPlan = SDWSizeAsPlan;
        this.SDWSizeAsInstalled = SDWSizeAsInstalled;
        this.SDWLengthAsPlan = SDWLengthAsPlan;
        this.SDWLengthAsInstalled = SDWLengthAsInstalled;
        GeoBuilding = geoBuilding;
        GeoTappingPole = geoTappingPole;
        GeoMeteringPole = geoMeteringPole;
        GeoSEPole = geoSEPole;
        FirstNeighborName = firstNeighborName;
        FirstNeighborMeterSerial = firstNeighborMeterSerial;
        SecondNeighborName = secondNeighborName;
        SecondNeighborMeterSerial = secondNeighborMeterSerial;
        EngineerInchargeName = engineerInchargeName;
        EngineerInchargeTitle = engineerInchargeTitle;
        EngineerInchargeLicenseNo = engineerInchargeLicenseNo;
        EngineerInchargeLicenseValidity = engineerInchargeLicenseValidity;
        EngineerInchargeContactNo = engineerInchargeContactNo;
        Status = status;
        Inspector = inspector;
        DateOfVerification = dateOfVerification;
        EstimatedDateForReinspection = estimatedDateForReinspection;
        Notes = notes;
        LightingOutlets = lightingOutlets;
        ConvenienceOutlets = convenienceOutlets;
        Motor = motor;
        TotalLoad = totalLoad;
        ContractedDemand = contractedDemand;
        ContractedEnergy = contractedEnergy;
        DistanceFromSecondaryLine = distanceFromSecondaryLine;
        SizeOfSecondary = sizeOfSecondary;
        SizeOfSDW = sizeOfSDW;
        TypeOfSDW = typeOfSDW;
        ServiceEntranceStatus = serviceEntranceStatus;
        HeightOfSDW = heightOfSDW;
        DistanceFromTransformer = distanceFromTransformer;
        SizeOfTransformer = sizeOfTransformer;
        TransformerNo = transformerNo;
        PoleNo = poleNo;
        ConnectedFeeder = connectedFeeder;
        SizeOfSvcPoles = sizeOfSvcPoles;
        HeightOfSvcPoles = heightOfSvcPoles;
        LinePassingPrivateProperty = linePassingPrivateProperty;
        WrittenConsentByPropertyOwner = writtenConsentByPropertyOwner;
        ObstructionOfLines = obstructionOfLines;
        LinePassingRoads = linePassingRoads;
        Recommendation = recommendation;
        ForPayment = forPayment;
        Zone = zone;
        Block = block;
        Feeder = feeder;
        BillDeposit = billDeposit;
        LoadType = loadType;
        Rate = rate;
    }

    public ServiceConnectionInspections() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceConnectionId() {
        return ServiceConnectionId;
    }

    public void setServiceConnectionId(String serviceConnectionId) {
        ServiceConnectionId = serviceConnectionId;
    }

    public String getSEMainCircuitBreakerAsPlan() {
        return SEMainCircuitBreakerAsPlan;
    }

    public void setSEMainCircuitBreakerAsPlan(String SEMainCircuitBreakerAsPlan) {
        this.SEMainCircuitBreakerAsPlan = SEMainCircuitBreakerAsPlan;
    }

    public String getSEMainCircuitBreakerAsInstalled() {
        return SEMainCircuitBreakerAsInstalled;
    }

    public void setSEMainCircuitBreakerAsInstalled(String SEMainCircuitBreakerAsInstalled) {
        this.SEMainCircuitBreakerAsInstalled = SEMainCircuitBreakerAsInstalled;
    }

    public String getSENoOfBranchesAsPlan() {
        return SENoOfBranchesAsPlan;
    }

    public void setSENoOfBranchesAsPlan(String SENoOfBranchesAsPlan) {
        this.SENoOfBranchesAsPlan = SENoOfBranchesAsPlan;
    }

    public String getSENoOfBranchesAsInstalled() {
        return SENoOfBranchesAsInstalled;
    }

    public void setSENoOfBranchesAsInstalled(String SENoOfBranchesAsInstalled) {
        this.SENoOfBranchesAsInstalled = SENoOfBranchesAsInstalled;
    }

    public String getPoleGIEstimatedDiameter() {
        return PoleGIEstimatedDiameter;
    }

    public void setPoleGIEstimatedDiameter(String poleGIEstimatedDiameter) {
        PoleGIEstimatedDiameter = poleGIEstimatedDiameter;
    }

    public String getPoleGIHeight() {
        return PoleGIHeight;
    }

    public void setPoleGIHeight(String poleGIHeight) {
        PoleGIHeight = poleGIHeight;
    }

    public String getPoleGINoOfLiftPoles() {
        return PoleGINoOfLiftPoles;
    }

    public void setPoleGINoOfLiftPoles(String poleGINoOfLiftPoles) {
        PoleGINoOfLiftPoles = poleGINoOfLiftPoles;
    }

    public String getPoleConcreteEstimatedDiameter() {
        return PoleConcreteEstimatedDiameter;
    }

    public void setPoleConcreteEstimatedDiameter(String poleConcreteEstimatedDiameter) {
        PoleConcreteEstimatedDiameter = poleConcreteEstimatedDiameter;
    }

    public String getPoleConcreteHeight() {
        return PoleConcreteHeight;
    }

    public void setPoleConcreteHeight(String poleConcreteHeight) {
        PoleConcreteHeight = poleConcreteHeight;
    }

    public String getPoleConcreteNoOfLiftPoles() {
        return PoleConcreteNoOfLiftPoles;
    }

    public void setPoleConcreteNoOfLiftPoles(String poleConcreteNoOfLiftPoles) {
        PoleConcreteNoOfLiftPoles = poleConcreteNoOfLiftPoles;
    }

    public String getPoleHardwoodEstimatedDiameter() {
        return PoleHardwoodEstimatedDiameter;
    }

    public void setPoleHardwoodEstimatedDiameter(String poleHardwoodEstimatedDiameter) {
        PoleHardwoodEstimatedDiameter = poleHardwoodEstimatedDiameter;
    }

    public String getPoleHardwoodHeight() {
        return PoleHardwoodHeight;
    }

    public void setPoleHardwoodHeight(String poleHardwoodHeight) {
        PoleHardwoodHeight = poleHardwoodHeight;
    }

    public String getPoleHardwoodNoOfLiftPoles() {
        return PoleHardwoodNoOfLiftPoles;
    }

    public void setPoleHardwoodNoOfLiftPoles(String poleHardwoodNoOfLiftPoles) {
        PoleHardwoodNoOfLiftPoles = poleHardwoodNoOfLiftPoles;
    }

    public String getPoleRemarks() {
        return PoleRemarks;
    }

    public void setPoleRemarks(String poleRemarks) {
        PoleRemarks = poleRemarks;
    }

    public String getSDWSizeAsPlan() {
        return SDWSizeAsPlan;
    }

    public void setSDWSizeAsPlan(String SDWSizeAsPlan) {
        this.SDWSizeAsPlan = SDWSizeAsPlan;
    }

    public String getSDWSizeAsInstalled() {
        return SDWSizeAsInstalled;
    }

    public void setSDWSizeAsInstalled(String SDWSizeAsInstalled) {
        this.SDWSizeAsInstalled = SDWSizeAsInstalled;
    }

    public String getSDWLengthAsPlan() {
        return SDWLengthAsPlan;
    }

    public void setSDWLengthAsPlan(String SDWLengthAsPlan) {
        this.SDWLengthAsPlan = SDWLengthAsPlan;
    }

    public String getSDWLengthAsInstalled() {
        return SDWLengthAsInstalled;
    }

    public void setSDWLengthAsInstalled(String SDWLengthAsInstalled) {
        this.SDWLengthAsInstalled = SDWLengthAsInstalled;
    }

    public String getGeoBuilding() {
        return GeoBuilding;
    }

    public void setGeoBuilding(String geoBuilding) {
        GeoBuilding = geoBuilding;
    }

    public String getGeoTappingPole() {
        return GeoTappingPole;
    }

    public void setGeoTappingPole(String geoTappingPole) {
        GeoTappingPole = geoTappingPole;
    }

    public String getGeoMeteringPole() {
        return GeoMeteringPole;
    }

    public void setGeoMeteringPole(String geoMeteringPole) {
        GeoMeteringPole = geoMeteringPole;
    }

    public String getGeoSEPole() {
        return GeoSEPole;
    }

    public void setGeoSEPole(String geoSEPole) {
        GeoSEPole = geoSEPole;
    }

    public String getFirstNeighborName() {
        return FirstNeighborName;
    }

    public void setFirstNeighborName(String firstNeighborName) {
        FirstNeighborName = firstNeighborName;
    }

    public String getFirstNeighborMeterSerial() {
        return FirstNeighborMeterSerial;
    }

    public void setFirstNeighborMeterSerial(String firstNeighborMeterSerial) {
        FirstNeighborMeterSerial = firstNeighborMeterSerial;
    }

    public String getSecondNeighborName() {
        return SecondNeighborName;
    }

    public void setSecondNeighborName(String secondNeighborName) {
        SecondNeighborName = secondNeighborName;
    }

    public String getSecondNeighborMeterSerial() {
        return SecondNeighborMeterSerial;
    }

    public void setSecondNeighborMeterSerial(String secondNeighborMeterSerial) {
        SecondNeighborMeterSerial = secondNeighborMeterSerial;
    }

    public String getEngineerInchargeName() {
        return EngineerInchargeName;
    }

    public void setEngineerInchargeName(String engineerInchargeName) {
        EngineerInchargeName = engineerInchargeName;
    }

    public String getEngineerInchargeTitle() {
        return EngineerInchargeTitle;
    }

    public void setEngineerInchargeTitle(String engineerInchargeTitle) {
        EngineerInchargeTitle = engineerInchargeTitle;
    }

    public String getEngineerInchargeLicenseNo() {
        return EngineerInchargeLicenseNo;
    }

    public void setEngineerInchargeLicenseNo(String engineerInchargeLicenseNo) {
        EngineerInchargeLicenseNo = engineerInchargeLicenseNo;
    }

    public String getEngineerInchargeLicenseValidity() {
        return EngineerInchargeLicenseValidity;
    }

    public void setEngineerInchargeLicenseValidity(String engineerInchargeLicenseValidity) {
        EngineerInchargeLicenseValidity = engineerInchargeLicenseValidity;
    }

    public String getEngineerInchargeContactNo() {
        return EngineerInchargeContactNo;
    }

    public void setEngineerInchargeContactNo(String engineerInchargeContactNo) {
        EngineerInchargeContactNo = engineerInchargeContactNo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getInspector() {
        return Inspector;
    }

    public void setInspector(String inspector) {
        Inspector = inspector;
    }

    public String getDateOfVerification() {
        return DateOfVerification;
    }

    public void setDateOfVerification(String dateOfVerification) {
        DateOfVerification = dateOfVerification;
    }

    public String getEstimatedDateForReinspection() {
        return EstimatedDateForReinspection;
    }

    public void setEstimatedDateForReinspection(String estimatedDateForReinspection) {
        EstimatedDateForReinspection = estimatedDateForReinspection;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getLightingOutlets() {
        return LightingOutlets;
    }

    public void setLightingOutlets(String lightingOutlets) {
        LightingOutlets = lightingOutlets;
    }

    public String getConvenienceOutlets() {
        return ConvenienceOutlets;
    }

    public void setConvenienceOutlets(String convenienceOutlets) {
        ConvenienceOutlets = convenienceOutlets;
    }

    public String getMotor() {
        return Motor;
    }

    public void setMotor(String motor) {
        Motor = motor;
    }

    public String getTotalLoad() {
        return TotalLoad;
    }

    public void setTotalLoad(String totalLoad) {
        TotalLoad = totalLoad;
    }

    public String getContractedDemand() {
        return ContractedDemand;
    }

    public void setContractedDemand(String contractedDemand) {
        ContractedDemand = contractedDemand;
    }

    public String getContractedEnergy() {
        return ContractedEnergy;
    }

    public void setContractedEnergy(String contractedEnergy) {
        ContractedEnergy = contractedEnergy;
    }

    public String getDistanceFromSecondaryLine() {
        return DistanceFromSecondaryLine;
    }

    public void setDistanceFromSecondaryLine(String distanceFromSecondaryLine) {
        DistanceFromSecondaryLine = distanceFromSecondaryLine;
    }

    public String getSizeOfSecondary() {
        return SizeOfSecondary;
    }

    public void setSizeOfSecondary(String sizeOfSecondary) {
        SizeOfSecondary = sizeOfSecondary;
    }

    public String getSizeOfSDW() {
        return SizeOfSDW;
    }

    public void setSizeOfSDW(String sizeOfSDW) {
        SizeOfSDW = sizeOfSDW;
    }

    public String getTypeOfSDW() {
        return TypeOfSDW;
    }

    public void setTypeOfSDW(String typeOfSDW) {
        TypeOfSDW = typeOfSDW;
    }

    public String getServiceEntranceStatus() {
        return ServiceEntranceStatus;
    }

    public void setServiceEntranceStatus(String serviceEntranceStatus) {
        ServiceEntranceStatus = serviceEntranceStatus;
    }

    public String getHeightOfSDW() {
        return HeightOfSDW;
    }

    public void setHeightOfSDW(String heightOfSDW) {
        HeightOfSDW = heightOfSDW;
    }

    public String getDistanceFromTransformer() {
        return DistanceFromTransformer;
    }

    public void setDistanceFromTransformer(String distanceFromTransformer) {
        DistanceFromTransformer = distanceFromTransformer;
    }

    public String getSizeOfTransformer() {
        return SizeOfTransformer;
    }

    public void setSizeOfTransformer(String sizeOfTransformer) {
        SizeOfTransformer = sizeOfTransformer;
    }

    public String getTransformerNo() {
        return TransformerNo;
    }

    public void setTransformerNo(String transformerNo) {
        TransformerNo = transformerNo;
    }

    public String getPoleNo() {
        return PoleNo;
    }

    public void setPoleNo(String poleNo) {
        PoleNo = poleNo;
    }

    public String getConnectedFeeder() {
        return ConnectedFeeder;
    }

    public void setConnectedFeeder(String connectedFeeder) {
        ConnectedFeeder = connectedFeeder;
    }

    public String getSizeOfSvcPoles() {
        return SizeOfSvcPoles;
    }

    public void setSizeOfSvcPoles(String sizeOfSvcPoles) {
        SizeOfSvcPoles = sizeOfSvcPoles;
    }

    public String getHeightOfSvcPoles() {
        return HeightOfSvcPoles;
    }

    public void setHeightOfSvcPoles(String heightOfSvcPoles) {
        HeightOfSvcPoles = heightOfSvcPoles;
    }

    public String getLinePassingPrivateProperty() {
        return LinePassingPrivateProperty;
    }

    public void setLinePassingPrivateProperty(String linePassingPrivateProperty) {
        LinePassingPrivateProperty = linePassingPrivateProperty;
    }

    public String getWrittenConsentByPropertyOwner() {
        return WrittenConsentByPropertyOwner;
    }

    public void setWrittenConsentByPropertyOwner(String writtenConsentByPropertyOwner) {
        WrittenConsentByPropertyOwner = writtenConsentByPropertyOwner;
    }

    public String getObstructionOfLines() {
        return ObstructionOfLines;
    }

    public void setObstructionOfLines(String obstructionOfLines) {
        ObstructionOfLines = obstructionOfLines;
    }

    public String getLinePassingRoads() {
        return LinePassingRoads;
    }

    public void setLinePassingRoads(String linePassingRoads) {
        LinePassingRoads = linePassingRoads;
    }

    public String getRecommendation() {
        return Recommendation;
    }

    public void setRecommendation(String recommendation) {
        Recommendation = recommendation;
    }

    public String getForPayment() {
        return ForPayment;
    }

    public void setForPayment(String forPayment) {
        ForPayment = forPayment;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getFeeder() {
        return Feeder;
    }

    public void setFeeder(String feeder) {
        Feeder = feeder;
    }

    public String getBillDeposit() {
        return BillDeposit;
    }

    public void setBillDeposit(String billDeposit) {
        BillDeposit = billDeposit;
    }

    public String getLoadType() {
        return LoadType;
    }

    public void setLoadType(String loadType) {
        LoadType = loadType;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }
}
