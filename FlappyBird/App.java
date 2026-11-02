import javax.swing.*; // Mengimpor pustaka GUI utama Java
import java.awt.*; // Mengimpor alat pewarnaan dan tata letak
import java.awt.event.ActionEvent; // Mengimpor pendeteksi aksi
import java.awt.event.ActionListener; // Mengimpor pendengar aksi tombol

public class App { // Kelas utama untuk menjalankan program
    public static void main(String[] args) { // Titik mulai eksekusi aplikasi
        JFrame frame = new JFrame("FlappyBird"); // Membuat jendela aplikasi
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Menutup aplikasi saat disilang
        frame.setSize(360, 640); // Mengatur ukuran layar
        frame.setLocationRelativeTo(null); // Memosisikan di tengah layar monitor
        frame.setResizable(false); // Mengunci ukuran agar tidak bisa ditarik

        Logic logika = new Logic(); // Menginisialisasi kelas mesin permainan
        View tampilan = new View(logika); // Menginisialisasi kelas kanvas visual
        logika.setView(tampilan); // Menghubungkan kanvas ke mesin logika

        JPanel menuPanel = new JPanel(); // Membuat panel khusus latar menu
        menuPanel.setLayout(new GridBagLayout()); // Menggunakan layout grid agar elemen di tengah
        menuPanel.setBackground(Color.decode("#71c5cf")); // Mewarnai latar menu dengan warna biru khas langit Flappy Bird

        JPanel buttonPanel = new JPanel(); // Membuat wadah khusus untuk deretan tombol
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 15)); // Mengatur tombol atas-bawah dengan jarak vertikal 15px
        buttonPanel.setBackground(Color.decode("#71c5cf")); // Menyamakan warna latar wadah dengan warna langit
        buttonPanel.setOpaque(false); // Membuat wadah tombol menjadi transparan menyatu dengan latar

        JButton startButton = new JButton("START"); // Membuat tombol Start
        JButton exitButton = new JButton("EXIT"); // Membuat tombol Exit

        // REVISI UI: Memberikan gaya visual yang lebih modern pada tombol
        startButton.setFont(new Font("SansSerif", Font.BOLD, 28)); // Mengubah jenis huruf dan ukuran tombol Start
        startButton.setBackground(Color.decode("#f3b238")); // Mewarnai tombol Start dengan warna oranye cerah
        startButton.setForeground(Color.WHITE); // Mewarnai tulisan tombol Start menjadi putih
        startButton.setFocusPainted(false); // Menghilangkan garis kotak jelek saat tombol diklik
        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3)); // Memberikan bingkai tebal warna putih
        startButton.setPreferredSize(new Dimension(200, 60)); // Membesarkan ukuran rasio tombol

        // REVISI UI: Menerapkan gaya visual yang sama pada tombol Exit
        exitButton.setFont(new Font("SansSerif", Font.BOLD, 28)); // Mengubah jenis huruf dan ukuran tombol Exit
        exitButton.setBackground(Color.decode("#e86101")); // Mewarnai tombol Exit dengan warna oranye gelap/merah
        exitButton.setForeground(Color.WHITE); // Mewarnai tulisan tombol Exit menjadi putih
        exitButton.setFocusPainted(false); // Menghilangkan garis kotak jelek saat tombol diklik
        exitButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3)); // Memberikan bingkai tebal warna putih
        exitButton.setPreferredSize(new Dimension(200, 60)); // Membesarkan ukuran rasio tombol

        buttonPanel.add(startButton); // Memasukkan tombol Start ke wadahnya
        buttonPanel.add(exitButton); // Memasukkan tombol Exit ke wadahnya
        menuPanel.add(buttonPanel); // Memasukkan keseluruhan deretan tombol ke tengah panel layar

        startButton.addActionListener(new ActionListener() { // Mendengarkan klik pada tombol Start
            @Override // Menimpa aksi standar
            public void actionPerformed(ActionEvent e) { // Terpicu saat ditekan
                frame.remove(menuPanel); // Menghapus layar menu
                frame.add(tampilan); // Menampilkan layar game (View)
                frame.revalidate(); // Memuat ulang struktur UI
                frame.repaint(); // Menghapus jejak gambar sisa
                tampilan.requestFocus(); // Memaksa fokus agar spasi bisa bekerja
                logika.startGame(); // Menjalankan logika permainan (timer dan musik)
            } // Tutup fungsi aksi
        }); // Tutup pendengar tombol

        exitButton.addActionListener(new ActionListener() { // Mendengarkan klik pada tombol Exit
            @Override // Menimpa aksi standar
            public void actionPerformed(ActionEvent e) { // Terpicu saat ditekan
                System.exit(0); // Mematikan program secara bersih
            } // Tutup fungsi aksi
        }); // Tutup pendengar tombol

        frame.add(menuPanel); // Menempelkan menu ke jendela saat aplikasi pertama dibuka
        frame.setVisible(true); // Memunculkan aplikasi ke mata pengguna
    } // Tutup metode main
} // Tutup kelas App