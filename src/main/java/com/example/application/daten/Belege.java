package com.example.application.daten;

public class Belege {

    private String dateiname;
    private String ablagedatum;
    private String rechnerisch_richtig;
    private String sachlich_richtig;
    private String indexx;
    private String abrechnungsobjekt;
    private  String mandant;
    private  String sachkonto;
    private String verwendungszweck;
    private Double rechnungsbetrag;

    public Belege(String dateiname, String ablagedatum, String rechnerisch_richtig, String sachlich_richtig,
                  String angeordnet_richtig, String indexx ,String abrechznungsobjekt, String mandant, String sachkonto, String verwendungszweck, Double rechnungsbetrag)  {
        super();
        this.dateiname = dateiname;
        this.ablagedatum = ablagedatum;
        this.rechnerisch_richtig = rechnerisch_richtig;
        this.sachlich_richtig = sachlich_richtig;
        this.angeordnet_richtig = angeordnet_richtig;
        this.indexx = indexx;
        this.abrechnungsobjekt = abrechznungsobjekt;
        this.mandant = mandant;
        this.sachkonto = sachkonto;
        this.verwendungszweck = verwendungszweck;
        this.rechnungsbetrag = rechnungsbetrag;

    }

    public String getDateiname()
    {
        return dateiname;
    }
    public void setDateiname(String dateiname) {
        this.dateiname = dateiname;
    }

    public String getIndexx()
    {
        return indexx;
    }
    public void setIndexx(String indexx) {
        this.indexx = indexx;
    }

    public String getAblagedatum() {
        return ablagedatum;
    }
    public void setAblagedatum(String ablagedatum) {
        this.ablagedatum = ablagedatum;
    }

    public String getRechnerisch_richtig() {
        return rechnerisch_richtig;
    }
    public void setRechnerisch_richtig(String rechnerisch_richtig) {
        this.rechnerisch_richtig = rechnerisch_richtig;
    }

    public String getSachlich_richtig() {
        return sachlich_richtig;
    }
    public void setSachlich_richtig(String sachlich_richtig) {
        this.sachlich_richtig = sachlich_richtig;
    }

    public String getAngeordnet_richtig() {
        return angeordnet_richtig;
    }
    public void setAngeordnet_richtig(String angeordnet_richtig) {
        this.angeordnet_richtig = angeordnet_richtig;
    }

    private String angeordnet_richtig;

    public String getAbrechnungsobjekt() {
        return abrechnungsobjekt;
    }
    public void setAbrechnungsobjekt(String abrechnungsobjekt) {
        this.abrechnungsobjekt = abrechnungsobjekt;
    }

    public String getMandant() {
        return mandant;
    }
    public void setMandant(String mandant) {
        this.mandant = mandant;
    }

    public String getSachkonto() {
        return sachkonto;
    }
    public void setSachkonto(String sachkonto) {
        this.sachkonto = sachkonto;
    }

    public String getVerwendungszweck() {
        return verwendungszweck;
    }
    public void setVerwendungszweck(String verwendungszweck) {
        this.verwendungszweck = verwendungszweck;
    }

    public Double getRechnungsbetrag() {return rechnungsbetrag;    }
    public void setRechnungsbetrag(Double rechnungsbetrag) {
        this.rechnungsbetrag = rechnungsbetrag;
    }

}
