package ro.unibuc.hello.data;

public class ApartamentEntity {
    private String id;
    private String idProprietar;
    private String idLocatie;
    private int numarApartament;
    private int etaj;
    private int numarCamere;
    private int numarPaturi;
    private int numarBai;
    private String tara;
    private String oras;

    public ApartamentEntity() {}

    public ApartamentEntity(String id, int etaj, String idLocatie, String idProprietar, int numarApartament, int numarBai, int numarCamere, int numarPaturi, String tara, String oras) {
        this.id = id;
        this.etaj = etaj;
        this.idLocatie = idLocatie;
        this.idProprietar = idProprietar;
        this.numarApartament = numarApartament;
        this.numarBai = numarBai;
        this.numarCamere = numarCamere;
        this.numarPaturi = numarPaturi;
        this.tara = tara;
        this.oras = oras;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdProprietar() { return idProprietar; }
    public void setIdProprietar(String idProprietar) { this.idProprietar = idProprietar; }
    public String getIdLocatie() { return idLocatie; }
    public void setIdLocatie(String idLocatie) { this.idLocatie = idLocatie; }
    public int getNumarApartament() { return numarApartament; }
    public void setNumarApartament(int numarApartament) { this.numarApartament = numarApartament; }
    public int getEtaj() { return etaj; }
    public void setEtaj(int etaj) { this.etaj = etaj; }
    public int getNumarCamere() { return numarCamere; }
    public void setNumarCamere(int numarCamere) { this.numarCamere = numarCamere; }
    public int getNumarPaturi() { return numarPaturi; }
    public void setNumarPaturi(int numarPaturi) { this.numarPaturi = numarPaturi; }
    public int getNumarBai() { return numarBai; }
    public void setNumarBai(int numarBai) { this.numarBai = numarBai; }
    public String getTara() { return tara; }
    public void setTara(String tara) { this.tara = tara; }
    public String getOras() { return oras; }
    public void setOras(String oras) { this.oras = oras; }
}
