package devoxx.microframeworks.exos.models;

public class Wine {

    private String id;
    private String produit;
    private int millesime;
    private String appellation;
    private String couleur;
    private String region;
    private String pays;
    private String bio;
    private String cepageDominant;
    private String alcool;
    private String contenance;
    private PriceCategory priceCategory;

    private String image;
    private String conservation;
    private int aBoireAPartirDe;
    private int garderJusquA;

    private String auNez;
    private String enBouche;
    private String aOeil;

    private String service;
    private String temperatureDeService;
    private String accordsRecommandes;
    private String accordsMetsVin;

    @Override
    public String toString() {
        return String.format("Wine{id='%s', appellation='%s', millesime=%d, produit='%s'}", id, appellation, millesime, produit);
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public int getMillesime() {
        return millesime;
    }

    public void setMillesime(int millesime) {
        this.millesime = millesime;
    }

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCepageDominant() {
        return cepageDominant;
    }

    public void setCepageDominant(String cepageDominant) {
        this.cepageDominant = cepageDominant;
    }

    public String getAlcool() {
        return alcool;
    }

    public void setAlcool(String alcool) {
        this.alcool = alcool;
    }

    public String getContenance() {
        return contenance;
    }

    public void setContenance(String contenance) {
        this.contenance = contenance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getConservation() {
        return conservation;
    }

    public void setConservation(String conservation) {
        this.conservation = conservation;
    }

    public int getaBoireAPartirDe() {
        return aBoireAPartirDe;
    }

    public void setaBoireAPartirDe(int aBoireAPartirDe) {
        this.aBoireAPartirDe = aBoireAPartirDe;
    }

    public int getGarderJusquA() {
        return garderJusquA;
    }

    public void setGarderJusquA(int garderJusquA) {
        this.garderJusquA = garderJusquA;
    }

    public String getAuNez() {
        return auNez;
    }

    public void setAuNez(String auNez) {
        this.auNez = auNez;
    }

    public String getEnBouche() {
        return enBouche;
    }

    public void setEnBouche(String enBouche) {
        this.enBouche = enBouche;
    }

    public String getaOeil() {
        return aOeil;
    }

    public void setaOeil(String aOeil) {
        this.aOeil = aOeil;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTemperatureDeService() {
        return temperatureDeService;
    }

    public void setTemperatureDeService(String temperatureDeService) {
        this.temperatureDeService = temperatureDeService;
    }

    public String getAccordsRecommandes() {
        return accordsRecommandes;
    }

    public void setAccordsRecommandes(String accordsRecommandes) {
        this.accordsRecommandes = accordsRecommandes;
    }

    public String getAccordsMetsVin() {
        return accordsMetsVin;
    }

    public void setAccordsMetsVin(String accordsMetsVin) {
        this.accordsMetsVin = accordsMetsVin;
    }

    public PriceCategory getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(PriceCategory priceCategory) {
        this.priceCategory = priceCategory;
    }
}
