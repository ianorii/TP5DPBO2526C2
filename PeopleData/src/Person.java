public class Person {
    // atribut wajib
    private String id;
    private String nama;
    private int tahunLahir;
    private String kategori;

    // atiibut tambahan
    private int kebugaran;

    // constructor
    public Person(String id, String nama, int tahunLahir, String kategori, int kebugaran) {
        this.id = id;
        this.nama = nama;
        this.tahunLahir = tahunLahir;
        this.kategori = kategori;
        this.kebugaran = kebugaran;
    }

    // setter and getter for id
    public void setId(String id) {this.id = id;}
    public String getId() {return id;}

    // setter and getter for nama
    public void setNama(String nama) {this.nama = nama;}
    public String getNama() {return nama;}

    // setter and getter for tahunLahir
    public void setTahunLahir(int tahunLahir) {this.tahunLahir = tahunLahir;}
    public int getTahunLahir() {return tahunLahir;}

    // setter and getter for kategori
    public void setKategori(String kategori) {this.kategori = kategori;}
    public String getKategori() {return kategori;}

    // setter and getter kebugaran
    public void setKebugaran(int kebugaran) {this.kebugaran = kebugaran;}
    public int getKebugaran() {return kebugaran;}
};