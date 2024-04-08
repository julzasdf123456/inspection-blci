package com.lopez.julz.inspectionv2.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Materials {
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "ServiceConnectionId")
    private String ServiceConnectionId;
    @ColumnInfo(name = "MeterBaseSocket")
    private String MeterBaseSocket;
    @ColumnInfo(name = "MetalboxTypeA")
    private String MetalboxTypeA;
    @ColumnInfo(name = "MetalboxTypeB")
    private String MetalboxTypeB;
    @ColumnInfo(name = "Pipe")
    private String Pipe;
    @ColumnInfo(name = "EntranceCap")
    private String EntranceCap;
    @ColumnInfo(name = "Adapter")
    private String Adapter;
    @ColumnInfo(name = "Locknot")
    private String Locknot;
    @ColumnInfo(name = "Mailbox")
    private String Mailbox;
    @ColumnInfo(name = "Buckle")
    private String Buckle;
    @ColumnInfo(name = "PvcElbow")
    private String PvcElbow;
    @ColumnInfo(name = "StainlessStrap")
    private String StainlessStrap;
    @ColumnInfo(name = "Plyboard")
    private String Plyboard;
    @ColumnInfo(name = "StrainInsulator")
    private String StrainInsulator;
    @ColumnInfo(name = "StraindedWireEight")
    private String StraindedWireEight;
    @ColumnInfo(name = "StrandedWireSix")
    private String StrandedWireSix;
    @ColumnInfo(name = "TwistedWireSix")
    private String TwistedWireSix;
    @ColumnInfo(name = "TwistedWireFour")
    private String TwistedWireFour;

    @ColumnInfo(name = "CompressionTapAsu")
    private String CompressionTapAsu;
    @ColumnInfo(name = "CompressionTapYtdTwoFifty")
    private String CompressionTapYtdTwoFifty;
    @ColumnInfo(name = "CompressionTapYtdThreeHundred")
    private String CompressionTapYtdThreeHundred;
    @ColumnInfo(name = "CompressionTapYtdTwoHundred")
    private String CompressionTapYtdTwoHundred;
    @ColumnInfo(name = "CompressionTapYtdOneFifty")
    private String CompressionTapYtdOneFifty;
    @ColumnInfo(name = "MetalScrew")
    private String MetalScrew;
    @ColumnInfo(name = "ElectricalTape")
    private String ElectricalTape;

    public Materials() {}

    public Materials(@NonNull String id, String serviceConnectionId, String meterBaseSocket, String metalboxTypeA, String metalboxTypeB, String pipe, String entranceCap, String adapter, String locknot, String mailbox, String buckle, String pvcElbow, String stainlessStrap, String plyboard, String strainInsulator, String straindedWireEight, String strandedWireSix, String twistedWireSix, String twistedWireFour, String compressionTapAsu, String compressionTapYtdTwoFifty, String compressionTapYtdThreeHundred, String compressionTapYtdTwoHundred, String compressionTapYtdOneFifty, String metalScrew, String electricalTape) {
        this.id = id;
        ServiceConnectionId = serviceConnectionId;
        MeterBaseSocket = meterBaseSocket;
        MetalboxTypeA = metalboxTypeA;
        MetalboxTypeB = metalboxTypeB;
        Pipe = pipe;
        EntranceCap = entranceCap;
        Adapter = adapter;
        Locknot = locknot;
        Mailbox = mailbox;
        Buckle = buckle;
        PvcElbow = pvcElbow;
        StainlessStrap = stainlessStrap;
        Plyboard = plyboard;
        StrainInsulator = strainInsulator;
        StraindedWireEight = straindedWireEight;
        StrandedWireSix = strandedWireSix;
        TwistedWireSix = twistedWireSix;
        TwistedWireFour = twistedWireFour;
        CompressionTapAsu = compressionTapAsu;
        CompressionTapYtdTwoFifty = compressionTapYtdTwoFifty;
        CompressionTapYtdThreeHundred = compressionTapYtdThreeHundred;
        CompressionTapYtdTwoHundred = compressionTapYtdTwoHundred;
        CompressionTapYtdOneFifty = compressionTapYtdOneFifty;
        MetalScrew = metalScrew;
        ElectricalTape = electricalTape;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getMeterBaseSocket() {
        return MeterBaseSocket;
    }

    public void setMeterBaseSocket(String meterBaseSocket) {
        MeterBaseSocket = meterBaseSocket;
    }

    public String getMetalboxTypeA() {
        return MetalboxTypeA;
    }

    public void setMetalboxTypeA(String metalboxTypeA) {
        MetalboxTypeA = metalboxTypeA;
    }

    public String getMetalboxTypeB() {
        return MetalboxTypeB;
    }

    public void setMetalboxTypeB(String metalboxTypeB) {
        MetalboxTypeB = metalboxTypeB;
    }

    public String getPipe() {
        return Pipe;
    }

    public void setPipe(String pipe) {
        Pipe = pipe;
    }

    public String getEntranceCap() {
        return EntranceCap;
    }

    public void setEntranceCap(String entranceCap) {
        EntranceCap = entranceCap;
    }

    public String getAdapter() {
        return Adapter;
    }

    public void setAdapter(String adapter) {
        Adapter = adapter;
    }

    public String getLocknot() {
        return Locknot;
    }

    public void setLocknot(String locknot) {
        Locknot = locknot;
    }

    public String getMailbox() {
        return Mailbox;
    }

    public void setMailbox(String mailbox) {
        Mailbox = mailbox;
    }

    public String getBuckle() {
        return Buckle;
    }

    public void setBuckle(String buckle) {
        Buckle = buckle;
    }

    public String getPvcElbow() {
        return PvcElbow;
    }

    public void setPvcElbow(String pvcElbow) {
        PvcElbow = pvcElbow;
    }

    public String getStainlessStrap() {
        return StainlessStrap;
    }

    public void setStainlessStrap(String stainlessStrap) {
        StainlessStrap = stainlessStrap;
    }

    public String getPlyboard() {
        return Plyboard;
    }

    public void setPlyboard(String plyboard) {
        Plyboard = plyboard;
    }

    public String getStrainInsulator() {
        return StrainInsulator;
    }

    public void setStrainInsulator(String strainInsulator) {
        StrainInsulator = strainInsulator;
    }

    public String getStraindedWireEight() {
        return StraindedWireEight;
    }

    public void setStraindedWireEight(String straindedWireEight) {
        StraindedWireEight = straindedWireEight;
    }

    public String getStrandedWireSix() {
        return StrandedWireSix;
    }

    public void setStrandedWireSix(String strandedWireSix) {
        StrandedWireSix = strandedWireSix;
    }

    public String getTwistedWireSix() {
        return TwistedWireSix;
    }

    public void setTwistedWireSix(String twistedWireSix) {
        TwistedWireSix = twistedWireSix;
    }

    public String getTwistedWireFour() {
        return TwistedWireFour;
    }

    public void setTwistedWireFour(String twistedWireFour) {
        TwistedWireFour = twistedWireFour;
    }

    public String getServiceConnectionId() {
        return ServiceConnectionId;
    }

    public void setServiceConnectionId(String serviceConnectionId) {
        ServiceConnectionId = serviceConnectionId;
    }

    public String getCompressionTapAsu() {
        return CompressionTapAsu;
    }

    public void setCompressionTapAsu(String compressionTapAsu) {
        CompressionTapAsu = compressionTapAsu;
    }

    public String getCompressionTapYtdTwoFifty() {
        return CompressionTapYtdTwoFifty;
    }

    public void setCompressionTapYtdTwoFifty(String compressionTapYtdTwoFifty) {
        CompressionTapYtdTwoFifty = compressionTapYtdTwoFifty;
    }

    public String getCompressionTapYtdThreeHundred() {
        return CompressionTapYtdThreeHundred;
    }

    public void setCompressionTapYtdThreeHundred(String compressionTapYtdThreeHundred) {
        CompressionTapYtdThreeHundred = compressionTapYtdThreeHundred;
    }

    public String getCompressionTapYtdTwoHundred() {
        return CompressionTapYtdTwoHundred;
    }

    public void setCompressionTapYtdTwoHundred(String compressionTapYtdTwoHundred) {
        CompressionTapYtdTwoHundred = compressionTapYtdTwoHundred;
    }

    public String getCompressionTapYtdOneFifty() {
        return CompressionTapYtdOneFifty;
    }

    public void setCompressionTapYtdOneFifty(String compressionTapYtdOneFifty) {
        CompressionTapYtdOneFifty = compressionTapYtdOneFifty;
    }

    public String getMetalScrew() {
        return MetalScrew;
    }

    public void setMetalScrew(String metalScrew) {
        MetalScrew = metalScrew;
    }

    public String getElectricalTape() {
        return ElectricalTape;
    }

    public void setElectricalTape(String electricalTape) {
        ElectricalTape = electricalTape;
    }
}
