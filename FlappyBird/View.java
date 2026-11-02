import javax.swing.*; // Mengimpor alat GUI dasar
import java.awt.*; // Mengimpor alat visual dan grafis
import java.util.ArrayList; // Mengimpor alat penyimpan daftar

public class View extends JPanel { // Kelas kanvas yang mewarisi sifat JPanel
    int width = 360; // Ukuran lebar kanvas permainan
    int height = 640; // Ukuran tinggi kanvas permainan
    private Logic logic; // Referensi pengakses mesin aturan (Logic)

    // JLabel scoreLabel telah DIHAPUS agar tampilan bisa dirender manual secara mulus

    public View(Logic logic) { // Konstruktor pembuat layar
        this.logic = logic; // Mengunci sambungan ke logika
        setPreferredSize(new Dimension(width, height)); // Mematok ukuran layar standar
        setBackground(Color.decode("#71c5cf")); // Memberikan warna biru langit estetik

        setFocusable(true); // Memastikan layar bisa menangkap input keyboard pengguna
        addKeyListener(logic); // Menyambungkan penangkap input ke dalam kelas Logic
    } // Penutup konstruktor

    @Override // Menimpa cara JPanel menggambar dirinya
    public void paintComponent(Graphics g) { // Metode otomatis penoreh piksel
        super.paintComponent(g); // Bersihkan kanvas dari coretan frame sebelumnya
        draw(g); // Perintahkan kanvas menoreh piksel baru
    } // Penutup metode paintComponent

    public void draw(Graphics g) { // Fungsi pembuat lukisan grafis
        Player player = logic.getPlayer(); // Tarik koordinat burung saat ini
        if (player != null) { // Jika burung sudah wujud
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), // Gambar di X dan Y nya
                    player.getWidth(), player.getHeight(), null); // Tentukan batas ukurannya
        } // Tutup gambar burung

        ArrayList<Pipe> pipes = logic.getPipes(); // Tarik koordinat semua pipa saat ini
        if (pipes != null) { // Jika daftar pipa tersedia
            for (int i = 0; i < pipes.size(); i++) { // Buka satu persatu dari daftar
                Pipe pipe = pipes.get(i); // Ambil pipa ke-i
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), // Gambarkan pipa di titik X Y nya
                        pipe.getWidth(), pipe.getHeight(), null); // Dengan ukuran spesifik pipa
            } // Tutup perulangan pipa
        } // Tutup jika daftar pipa aman

        // REVISI UI: Merender skor dengan tipografi bayangan (Drop Shadow)
        String scoreText = String.valueOf((int) logic.getScore()); // Menerjemahkan angka desimal skor menjadi teks bulat
        g.setFont(new Font("SansSerif", Font.BOLD, 48)); // Memasang jenis huruf tebal berukuran besar

        g.setColor(Color.BLACK); // Memilih warna hitam untuk bayangan
        g.drawString(scoreText, (width / 2) - 13, 73); // Menggambar bayangan sedikit bergeser ke bawah dan kanan
        g.setColor(Color.WHITE); // Memilih warna putih untuk teks utama
        g.drawString(scoreText, (width / 2) - 15, 70); // Menggambar teks utama tepat di atas bayangan hitam

        // FASE 3 & REVISI UI: Memunculkan Teks GAME OVER jika kondisinya kalah
        if (logic.isGameOver()) { // Mengecek ke kelas Logic apakah permainan sedang kiamat
            g.setFont(new Font("SansSerif", Font.BOLD, 42)); // Menurunkan ukuran huruf untuk teks kiamat

            g.setColor(Color.BLACK); // Warna hitam untuk bayangan Game Over
            g.drawString("GAME OVER", 48, (height / 2) - 47); // Bayangan teks Game Over di tengah
            g.setColor(Color.decode("#e86101")); // Warna merah bata/oranye untuk teks utama
            g.drawString("GAME OVER", 45, (height / 2) - 50); // Teks utama Game Over di tengah layar

            g.setFont(new Font("SansSerif", Font.BOLD, 20)); // Mengecilkan font untuk teks instruksi
            g.setColor(Color.BLACK); // Warna hitam untuk bayangan instruksi
            g.drawString("Press R to Restart", 87, (height / 2) + 22); // Bayangan teks Restart
            g.setColor(Color.WHITE); // Warna putih untuk teks utama instruksi
            g.drawString("Press R to Restart", 85, (height / 2) + 20); // Teks utama Restart menyala
        } // Penutup fungsi rendering Game Over
    } // Penutup metode draw
} // Penutup kelas View