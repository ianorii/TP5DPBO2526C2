# JANJI
Saya Muhammad Rian Anugrah dengan NIM 2507241 mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# DESAIN PROGRAM
Program ini dibuat menggunakan Java Swing untuk mengelola data pendaki. Fitur utama meliputi:
- Create: Menambahkan data pendaki baru ke database.
- Read: Menampilkan data pendaki dari database.
- Update: Mengedit data pendaki yang sudah ada di database.
- Delete: Menghapus data pendaki yang ada di database.

## CLASS PADA PROGRAM:
### 1. Class Person
Class ini berisi atribut dan method getter/setter. Berikut atribut-atributnya:
- String id
- String nama
- int tahunLahir
- String kategori
- int kebugaran
Method:
- Constructor Person(String id, String nama, int tahunLahir, String kategori, int kebugaran)
- Getter dan Setter untuk setiap atribut

### 2. Class PeopleMenu extends JFrame
Class ini berisi Graphical User Interface (GUI) dari program, berfungsi untuk mengatur tampilan, menerima input user, serta mengatur keluar-masuk data ke database.

Class ini mengatur tampilan dan logika aplikasi berbasis GUI.
Berfungsi untuk menampilkan form, menampung event handler, serta mengatur database.

Komponen Utama GUI:
- JTextField untuk input ID, Nama, dan Tahun Lahir
- JComboBox untuk memilih Kategori (Pemula, Menengah, Ahli)
- JSlider untuk memberikan nilai Kebugaran (1-10)
- JTable untuk menampilkan daftar pendaki
- Tombol Add/Update, Delete, dan Cancel

Fungsi utama dalam class ini:
- insertData() - Menambahkan pendaki baru ke dalam database
- updateData() - Memperbarui data pendaki yang dipilih di database
- deleteData() - Menghapus data pendaki di database setelah konfirmasi dialog
- clearForm() - Mengosongkan semua input form
- setTable() - Mengatur isi tabel berdasarkan database

### 3. Database
Class ini berfungsi sebagai lapisan koneksi dan eksekusi perintah SQL.
Class ini menangani komunikasi antara program dan database MySQL secara langsung.

Fungsi utama:
- connect() - Membuat koneksi ke database db_people.
- selectQuery(String sql) - Menjalankan perintah SELECT dan mengembalikan hasilnya.
- insertUpdateDeleteQuery(String sql) - Menjalankan perintah INSERT, UPDATE, atau DELETE ke database.

Driver yang dipakai: MySQL Connector/J (lib/mysql-connector-j-8.3.0.jar).

# STRUKTUR FOLDER
```
TP5DPBO2526C2/
├── README.md                  # Dokumen laporan tugas
├── db_people.sql              # Script SQL database (tabel person)
├── Dokumentasi/               # Screenshot dan GIF dokumentasi
│   ├── 01-tampilan-utama.png
│   ├── 02-insert-berhasil.gif
│   └── ...
└── PeopleData/                # Project Java Swing
    ├── .idea/                 # Konfigurasi IntelliJ IDEA
    ├── .gitignore
    ├── lib/                   # Library eksternal
    │   └── mysql-connector-j-26.7.0.jar
    └── src/                   # Source code
        ├── Database.java      # Koneksi & eksekusi query SQL
        ├── PeopleMenu.java    # GUI utama (JFrame)
        ├── PeopleMenu.form    # Desain GUI (IntelliJ GUI Designer)
        └── Person.java        # Class model data pendaki
```

# ALUR PROGRAM
### 1. Tampilan Utama
Tampilan utama berisi formulir data pendaki yang berisi:
- ID
- Nama
- Tahun Lahir
- Kategori
- Kebugaran
- Tombol aksi Add/Update, Delete, dan Cancel.
Di bawah form terdapat tabel yang menampilkan daftar semua pendaki.

### 2. Penambahan Data Baru
User dapat menambahkan data baru dengan mengisi form data pendaki, kemudian klik tombol aksi "Add" untuk menyimpan data ke dalam tabel.
- ID pendaki harus unik, jika ID yang dimasukkan sudah ada, data tidak akan ditambahkan dan muncul pesan "ID sudah dipakai".
- Field ID, Nama, dan Tahun Lahir wajib diisi. Jika ada yang kosong, akan muncul pesan error.
- Kategori wajib dipilih (tidak boleh masih default "???").
- Tahun Lahir harus berupa angka. Jika diisi huruf, akan muncul pesan "Tahun harus berupa angka".
- Field Kebugaran sudah memiliki nilai default dari slider.

### 3. Update Data
Saat salah satu baris tabel diklik, data pendaki akan muncul kembali di form input.
Tombol "Add" akan otomatis berubah menjadi "Update", dan tombol "Delete" akan muncul. Setelah itu user dapat mengubah data sesuai kebutuhan.
- Aturan validasinya sama seperti saat tambah data: ID harus unik, ID/Nama/Tahun Lahir tidak boleh kosong, kategori harus dipilih, dan tahun harus angka.
- ID boleh diganti ke ID baru selama ID baru tersebut belum dipakai.
- Jika update berhasil, tabel akan diperbarui dan form kembali ke mode Add.

### 4. Delete Data
Jika user ingin menghapus salah satu data, klik baris yang diinginkan lalu klik tombol "Delete".

Akan muncul konfirmasi dialog:
"Apakah anda yakin akan menghapus data ini?"

Jika memilih Yes, data akan dihapus dari database dan tabel.
Jika memilih No, penghapusan dibatalkan dan data tetap ada.

### 5. Cancel/Membersihkan form
Klik tombol "Cancel" untuk mengosongkan semua input form dan membatalkan mode edit (tombol kembali menjadi "Add", tombol Delete hilang).

# DOKUMENTASI
Semua dokumentasi disimpan di folder Dokumentasi/. Ganti setiap penanda di bawah dengan screenshot atau GIF milikmu.

## READ
Menampilkan tampilan awal program dengan tabel data pendaki.

> TARUH SCREENSHOT TAMPILAN UTAMA DI SINI
> Saran file: Dokumentasi/01-tampilan-utama.png
> ![Tampilan utama](Dokumentasi/01-tampilan-utama.png)

## ADD
### Insert Berhasil
Mengisi semua form dengan benar lalu klik Add, data masuk ke tabel.

> TARUH GIF/SCREENSHOT INSERT BERHASIL DI SINI
> Saran file: Dokumentasi/02-insert-berhasil.gif
> ![Insert berhasil](Dokumentasi/02-insert-berhasil.gif)

### Insert ID yang sudah ada
Memasukkan ID yang sama dengan data yang sudah ada, muncul pesan ID sudah dipakai.

> TARUH SCREENSHOT INSERT ID SUDAH ADA DI SINI
> Saran file: Dokumentasi/03-insert-id-duplikat.png
> ![Insert ID duplikat](Dokumentasi/03-insert-id-duplikat.png)

### Insert tapi salah satu form ada yang kosong
Mengosongkan ID / Nama / Tahun Lahir lalu klik Add, muncul pesan field tidak boleh kosong.

> TARUH SCREENSHOT INSERT FIELD KOSONG DI SINI (boleh 3 file: ID, Nama, Tahun)
> Saran file: Dokumentasi/04-insert-id-kosong.png, Dokumentasi/05-insert-nama-kosong.png, Dokumentasi/06-insert-tahun-kosong.png
> ![Insert ID kosong](Dokumentasi/04-insert-id-kosong.png)
> ![Insert nama kosong](Dokumentasi/05-insert-nama-kosong.png)
> ![Insert tahun kosong](Dokumentasi/06-insert-tahun-kosong.png)

### Insert tapi kategori belum dipilih
Membiarkan kategori di pilihan awal, muncul pesan kategori tidak boleh kosong.

> TARUH SCREENSHOT INSERT KATEGORI KOSONG DI SINI
> Saran file: Dokumentasi/07-insert-kategori-kosong.png
> ![Insert kategori kosong](Dokumentasi/07-insert-kategori-kosong.png)

### Insert tapi tahun lahir bukan angka
Mengisi tahun lahir dengan huruf, muncul pesan tahun harus berupa angka.

> TARUH SCREENSHOT INSERT TAHUN BUKAN ANGKA DI SINI
> Saran file: Dokumentasi/08-insert-tahun-bukan-angka.png
> ![Insert tahun bukan angka](Dokumentasi/08-insert-tahun-bukan-angka.png)

## UPDATE
### Update Berhasil
Klik baris tabel, ubah datanya, klik Update, data di tabel berubah.

> TARUH GIF/SCREENSHOT UPDATE BERHASIL DI SINI
> Saran file: Dokumentasi/09-update-berhasil.gif
> ![Update berhasil](Dokumentasi/09-update-berhasil.gif)

### Update ID yang sudah ada
Dalam mode edit, ganti ID ke milik data lain, muncul pesan ID sudah dipakai.

> TARUH SCREENSHOT UPDATE ID SUDAH ADA DI SINI
> Saran file: Dokumentasi/10-update-id-duplikat.png
> ![Update ID duplikat](Dokumentasi/10-update-id-duplikat.png)

### Update tapi salah satu form ada yang kosong
Dalam mode edit, kosongkan ID / Nama / Tahun Lahir atau kembalikan kategori ke awal atau isi tahun dengan huruf, muncul pesan error yang sesuai.

> TARUH SCREENSHOT UPDATE VALIDASI DI SINI
> Saran file: Dokumentasi/11-update-field-kosong.png, Dokumentasi/12-update-tahun-bukan-angka.png
> ![Update field kosong](Dokumentasi/11-update-field-kosong.png)
> ![Update tahun bukan angka](Dokumentasi/12-update-tahun-bukan-angka.png)

## DELETE
### Delete Berhasil (pilih Yes)
Klik baris tabel, klik Delete, pilih Yes pada konfirmasi, data terhapus dari tabel.

> TARUH GIF/SCREENSHOT DELETE BERHASIL DI SINI
> Saran file: Dokumentasi/13-delete-yes.gif
> ![Delete Yes](Dokumentasi/13-delete-yes.gif)

### Delete Dibatalkan (pilih No)
Klik Delete lalu pilih No, muncul pesan batal dan data tetap ada.

> TARUH SCREENSHOT DELETE BATAL DI SINI
> Saran file: Dokumentasi/14-delete-no.png
> ![Delete No](Dokumentasi/14-delete-no.png)

## CANCEL
Klik Cancel saat mode edit, form kosong kembali seperti awal.

> TARUH GIF/SCREENSHOT CANCEL DI SINI
> Saran file: Dokumentasi/15-cancel.gif
> ![Cancel](Dokumentasi/15-cancel.gif)
