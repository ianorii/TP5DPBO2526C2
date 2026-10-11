import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PeopleMenu extends JFrame {
    private JPanel mainPanel;
    private JTable personTable;
    private JTextField idField;
    private JTextField namaField;
    private JTextField tahunField;
    private JComboBox kategoriComboBox;
    private JSlider kebugaranSlider;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JLabel titleLabel;

    // variabel global
    private ArrayList<Person> listPerson;
    int selectedIndex = -1;

    // variabel database
    private Database database;

    // main program
    public static void main(String[] args) {
        // buat project window
        PeopleMenu menu = new PeopleMenu();

        // atur ukuran window
        menu.setSize(700, 600);

        // meletakkan window di tengah layar
        menu.setLocationRelativeTo(null);

        // isi window
        menu.setContentPane(menu.mainPanel);

        // agar program berhenti ketika window di-close
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setVisible(true);
    }

    // constructor
    public PeopleMenu() {
        // inisialisasi listPerson
        listPerson = new ArrayList<>();

        // instansiasi databse
        database = new Database();

        // isi data list
        populateList();

        // isi personTable
        personTable.setModel(setTable());

        // set font dan ukuran title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] kategoriData = {"???", "Pemula", "Menengah", "Ahli"};
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // set slider
        kebugaranSlider.setMinimum(0);
        kebugaranSlider.setMaximum(10);
        kebugaranSlider.setValue(0);
        kebugaranSlider.setMinorTickSpacing(1);
        kebugaranSlider.setMajorTickSpacing(5);
        kebugaranSlider.setPaintTicks(true);
        kebugaranSlider.setPaintLabels(true);

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(selectedIndex == -1) {
                    // jika tidak ada index yang dipilih
                    insertData();
                } else {
                    // jika ada index yang dipilih
                    updateData();
                }
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearForm();
            }
        });

        // saat tombol delete dihapus
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int pilihan = JOptionPane.showConfirmDialog(null,
                        "Apakah anda yakin akan menghapus data ini?",
                        "Konfirmasi hapus", JOptionPane.YES_NO_OPTION);

                if(pilihan == JOptionPane.YES_OPTION) {
                    deleteData();
                } else {
                    JOptionPane.showMessageDialog(null, "Hapus data dibatalkan");
                }
            }
        });


        personTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex
                selectedIndex = personTable.getSelectedRow();

                // ambil value dari table
                String id = personTable.getModel().getValueAt(selectedIndex, 1).toString();
                String nama = personTable.getModel().getValueAt(selectedIndex, 2).toString();
                String tahunLahir = personTable.getModel().getValueAt(selectedIndex, 3).toString();
                String kategori = personTable.getModel().getValueAt(selectedIndex, 4).toString();
                String kebugaran = personTable.getModel().getValueAt(selectedIndex, 5).toString();

                // isi textfield
                idField.setText(id);
                namaField.setText(nama);
                tahunField.setText(tahunLahir);
                kategoriComboBox.setSelectedItem(kategori);
                kebugaranSlider.setValue(Integer.parseInt(kebugaran));

                // ubah "add" pada button menjadi "update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    };

    // method setTable
    public final DefaultTableModel setTable() {
        // kolom table
        Object[] cols = {"ID", "Nama", "Tahun Lahir", "Kategori", "Kebugaran"};

        // membuat object table dengan cols yang sudah dibuat
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM person;");

            // isi tabel dengan hasil query
            int i = 0;
            while(resultSet.next()) {
                Object[] row = new Object[5];
                row[0] = resultSet.getString("id");
                row[1] = resultSet.getString("nama");
                row[2] = resultSet.getString("thn_lahir");
                row[3] = resultSet.getString("kategori");
                row[4] = resultSet.getString("kebugaran");

                tmp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tmp;
    }

    public void populateList() {
        listPerson.add(new Person("A001", "Rizky Ramadhan", 2001, "Pemula", 6));
        listPerson.add(new Person("A002", "Bimo Prasetyo", 1998, "Menengah", 8));
        listPerson.add(new Person("A003", "Fajar Nugroho", 1995, "Ahli", 9));
        listPerson.add(new Person("A004", "Dimas Saputra", 2003, "Pemula", 5));
        listPerson.add(new Person("A005", "Agus Setiawan", 1990, "Ahli", 10));
        listPerson.add(new Person("A006", "Rudi Hartono", 1999, "Menengah", 7));
        listPerson.add(new Person("A007", "Andi Wijaya", 2002, "Pemula", 6));
        listPerson.add(new Person("A008", "Bagas Pratama", 1996, "Menengah", 8));
        listPerson.add(new Person("A009", "Yoga Firmansyah", 1993, "Ahli", 9));
        listPerson.add(new Person("A010", "Ilham Maulana", 2000, "Pemula", 7));
    }

    // method untuk cek ID pada list
    public boolean cekId(String curId) {
        // return false ketika id sudah ada dalam list
        // return true ketika id belum ada dalam list
        for(int i = 0; i < listPerson.size(); i++) {
            if(listPerson.get(i).getId().equals(curId)) return false;
        }
        return true;
    }

    // method insert
    public void insertData() {
        try {
            // ambil value dari semua inputan
            String id = idField.getText();
            String nama = namaField.getText();
            int tahunLahir = Integer.parseInt(tahunField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            int kebugaran = kebugaranSlider.getValue();

            // error handling untuk kategori kosong
            if(kategori.equals("???")) {
                JOptionPane.showMessageDialog(null, "Kategori tidak boleh kosong",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(cekId(id)) {
                // menambahkan data ke dalam list
                listPerson.add(new Person(id, nama, tahunLahir, kategori, kebugaran));

                // tambahkan data ke database
                String sqlQuery = "INSERT INTO person VALUES ('" + id + "', '" + nama + "', "
                        + tahunLahir + ", '" + kategori + "', " + kebugaran + ");";
                database.insertUpdateDeleteQuery(sqlQuery);

                // update table
                personTable.setModel(setTable());

                // bersihkan form
                clearForm();

                // feedback
                System.out.println("Insert Berhasil");
                JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
            } else {
                // error handling ketika id sudah digunakan
                System.out.println("ERROR : ID sudah dipakai");
                JOptionPane.showMessageDialog(null, "ID sudah dipakai",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            // Error handling untuk tahun
            JOptionPane.showMessageDialog(null, "Tahun harus berupa angka", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // method update
    public void updateData() {
        try {
            // ambil value dari semua inputan
            String id = idField.getText();
            String nama = namaField.getText();
            int tahunLahir = Integer.parseInt(tahunField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            int kebugaran = kebugaranSlider.getValue();

            // error handling untuk kategori kosong
            if(kategori.equals("???")) {
                JOptionPane.showMessageDialog(null, "Kategori tidak boleh kosong",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(cekId(id) || id.equals(listPerson.get(selectedIndex).getId())) {
                // memperbarui data
                listPerson.get(selectedIndex).setId(id);
                listPerson.get(selectedIndex).setNama(nama);
                listPerson.get(selectedIndex).setTahunLahir(tahunLahir);
                listPerson.get(selectedIndex).setKategori(kategori);
                listPerson.get(selectedIndex).setKebugaran(kebugaran);

                // perbarui table
                personTable.setModel(setTable());

                // kosongkan form
                clearForm();

                // feedback
                System.out.println("Update Berhasil");
                JOptionPane.showMessageDialog(null, "Data berhasil diubah");
            } else {
                // error handling ketika id sudah digunakan
                System.out.println("ERROR : ID sudah dipakai");
                JOptionPane.showMessageDialog(null, "ID sudah dipakai",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            // Error handling untuk tahun
            JOptionPane.showMessageDialog(null, "Tahun harus berupa angka", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {
        // hapus data dari list
        listPerson.remove(selectedIndex);

        // update table
        personTable.setModel(setTable());

        // clearform
        clearForm();;

        System.out.println("Delete Berhasil");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
    }

    // method clear form
    public void clearForm() {
        // mengosongkan semua textfield
        idField.setText("");
        namaField.setText("");
        tahunField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        kebugaranSlider.setValue(0);

        // ubah tulisan pada addUpdateButton
        addUpdateButton.setText("Add");

        // sembuntikan delete button
        deleteButton.setVisible(false);

        // ubah selectedItem menjadi default
        selectedIndex = -1;
    }
}