import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    int selectedIndex = -1;
    String curId = "";

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
        // instansiasi databse
        database = new Database();

        // isi personTable
        personTable.setModel(setTable());

        // set font dan ukuran title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] kategoriData = {"???", "Pemula", "Menengah", "Ahli"};
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // set slider
        kebugaranSlider.setMinimum(1);
        kebugaranSlider.setMaximum(10);
        kebugaranSlider.setValue(1);
        kebugaranSlider.setMinorTickSpacing(1);
        kebugaranSlider.setMajorTickSpacing(3);
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
                String id = personTable.getModel().getValueAt(selectedIndex, 0).toString();
                String nama = personTable.getModel().getValueAt(selectedIndex, 1).toString();
                String tahunLahir = personTable.getModel().getValueAt(selectedIndex, 2).toString();
                String kategori = personTable.getModel().getValueAt(selectedIndex, 3).toString();
                String kebugaran = personTable.getModel().getValueAt(selectedIndex, 4).toString();

                // mengambil id dari data yang diklik
                curId = id;

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

    // method insert
    public void insertData() {
        try {
            // ambil value dari semua inputan
            String id = idField.getText();
            String nama = namaField.getText();
            int tahunLahir = Integer.parseInt(tahunField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            int kebugaran = kebugaranSlider.getValue();
            // error handling untuk id kosong
            if(id.equals("")) {
                JOptionPane.showMessageDialog(null, "ID tidak boleh kosong",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // error handling untuk nama kosong
            if(nama.equals("")) {
                JOptionPane.showMessageDialog(null, "Nama tidak boleh kosong",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // error handling untuk nama kosong
            if(tahunField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Tahun Lahir tidak boleh kosong",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // error handling untuk kategori kosong
            if(kategori.equals("???")) {
                JOptionPane.showMessageDialog(null, "Kategori tidak boleh kosong",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
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
            } catch(RuntimeException e) {
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

            // error handling untuk id kosong
            if(id.equals("")) {
                JOptionPane.showMessageDialog(null, "ID tidak boleh kosong",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // error handling untuk nama kosong
            if(nama.equals("")) {
                JOptionPane.showMessageDialog(null, "Nama tidak boleh kosong",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // error handling untuk nama kosong
            if(tahunField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Tahun Lahir tidak boleh kosong",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // error handling untuk kategori kosong
            if(kategori.equals("???")) {
                JOptionPane.showMessageDialog(null, "Kategori tidak boleh kosong",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // query update
                String sqlQuery = "UPDATE person SET id = '" + id + "', nama = '" + nama +
                        "', thn_lahir = " + tahunLahir + ", kategori = '" + kategori + "', kebugaran = "
                        + kebugaran + " WHERE id = '" + curId + "';";
                database.insertUpdateDeleteQuery(sqlQuery);

                // update table
                personTable.setModel(setTable());

                // clear form
                clearForm();

                // feedback
                System.out.println("Update Berhasil");
                JOptionPane.showMessageDialog(null, "Data berhasil diperbarui");
            } catch (RuntimeException e){
                // error handling ketika id sudah digunakan
                idField.setText(curId);
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
        // query delete
        String sqrlQuery = "DELETE FROM person WHERE id = '" + curId + "';";
        database.insertUpdateDeleteQuery(sqrlQuery);

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

        // ubah curId
        curId = "";
    }
}