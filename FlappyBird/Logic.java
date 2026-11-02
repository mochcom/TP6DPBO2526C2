import javax.swing.*; // Pustaka waktu berulang
import java.awt.*; // Pustaka citra
import java.awt.event.ActionEvent; // Event waktu
import java.awt.event.ActionListener; // Pendengar waktu
import java.awt.event.KeyEvent; // Event penekanan tuts
import java.awt.event.KeyListener; // Pendengar tuts
import java.util.ArrayList; // Wadah penyimpan
import javax.sound.sampled.*; // Pustaka audio
import java.net.URL; // Pencari lokasi aset

public class Logic implements ActionListener, KeyListener { // Otak permainan
    int frameWidth = 360; // Lebar bingkai
    int frameHeight = 640; // Tinggi bingkai

    int playerStartPosX = frameWidth / 2; // Sumbu asal X
    int playerStartPosY = frameHeight / 2; // Sumbu asal Y
    int playerWidth = 34; // Luas X burung
    int playerHeight = 24; // Luas Y burung

    int pipeStartPosX = frameWidth; // Posisi tersembunyi
    int pipeStartPosY = 0; // Ujung atap
    int pipeWidth = 64; // Lebar halangan
    int pipeHeight = 512; // Panjang maksimal

    Timer gameLoop; // Jam pendorong bingkai
    Timer pipesCooldown; // Jam mesin pencetak
    int gravity = 1; // Ketertarikan bumi

    View view; // Rujukan layar visual
    Image birdImage; // Piksel burung
    Player player; // Objek aktor

    Image lowerPipeImage; // Piksel corong hadap atas
    Image upperPipeImage; // Piksel corong hadap bawah
    ArrayList<Pipe> pipes; // Penyimpan deret gerak

    boolean gameOver = false; // Tanda nyawa
    double score = 0; // Catatan angka

    Clip bgmClip; // Audio pemutar berulang

    public Logic() { // Saat mesin dihidupkan
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage(); // Memori gambar
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage); // Bentuk utuh

        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage(); // Memori gambar
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage(); // Memori gambar
        pipes = new ArrayList<Pipe>(); // Antrian kosong

        try { // Blok pencarian dan pemuatan suara
            // REVISI AUDIO: Memakai getClassLoader agar lebih stabil melacak direktori bersarang
            URL soundURL = getClass().getClassLoader().getResource("assets/bgm.wav"); // Tentukan letak aset audio
            if (soundURL != null) { // Jika jejak audio eksis di komputer
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL); // Mulai sedot aliran datanya
                bgmClip = AudioSystem.getClip(); // Sediakan penjepit media pemutar
                bgmClip.open(audioIn); // Jepit datanya ke memori
            } else { // Jika ternyata file tidak terdeteksi IDE
                System.out.println("Audio Error: File bgm.wav tidak ditemukan di folder assets!"); // Lempar peringatan ke konsol
            } // Tutup cabang deteksi
        } catch (UnsupportedAudioFileException uae) { // Jika file terbaca tapi ternyata MP3 nyamar jadi WAV
            System.out.println("Audio Error: Format tidak didukung. Pastikan ini format PCM WAV asli, bukan MP3!"); // Lempar spesifikasi galat
        } catch (Exception ex) { // Jika terjadi galat sirkuit kelistrikan lainnya
            ex.printStackTrace(); // Tulis merah di konsol log
        } // Tutup blok coba-coba

        pipesCooldown = new Timer(1500, new ActionListener() { // Pembuat jeda satu setengah detik
            @Override // Timpa jalannya
            public void actionPerformed(ActionEvent actionEvent) { // Saat jeda berakhir
                placePipes(); // Beri perintah wujudkan pipa
            } // Selesai
        }); // Siap

        gameLoop = new Timer(1000 / 60, this); // Pembuat ritme pergerakan grafis 60 kali sedetik
    } // Penutup ruang inisiasi awal

    public void setView(View view) { // Lempar kabel ke layar
        this.view = view; // Tancapkan ke mesin
    } // Tutup

    public Player getPlayer() { // Izinkan layar baca burung
        return player; // Bawa datanya
    } // Tutup

    public ArrayList<Pipe> getPipes() { // Izinkan layar baca deret rintangan
        return pipes; // Bawa data listnya
    } // Tutup

    // FASE PENYEMPURNAAN: Penambahan dua metode ini agar View.java yang merender manual bisa membaca datanya
    public double getScore() { // Izinkan layar mengambil data skor aktual
        return score; // Kembalikan nilai skor (di View akan dibulatkan jadi int)
    } // Tutup

    public boolean isGameOver() { // Izinkan layar membaca apakah kondisi game sedang kiamat
        return gameOver; // Kembalikan nilai kebenarannya
    } // Tutup

    public void startGame() { // Terpicu ketika tombol Start Menu diklik
        gameLoop.start(); // Hidupkan urat nadi permainan
        pipesCooldown.start(); // Buka keran pipa bertubi-tubi
        if (bgmClip != null) { // Bila memori jepitan terisi suara
            bgmClip.setFramePosition(0); // Gulung pita kaset ke nol (awal)
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY); // Perintahkan memutar seumur hidup
        } // Akhir pemutar lagu
    } // Tutup fungsi mulai

    public void placePipes() { // Pembuat balok pipa acak
        int randomPosY = (int) (pipeStartPosY - (pipeHeight / 4) - Math.random() * (pipeHeight / 2)); // Tarik undian posisi tinggi rendah Y
        int openingSpace = frameHeight / 4; // Berikan napas rongga udara di tengahnya

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage); // Tancapkan stalaktit (pipa atas)
        pipes.add(upperPipe); // Antrikan

        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + openingSpace + pipeHeight, pipeWidth, pipeHeight, lowerPipeImage); // Tancapkan stalagmit (pipa bawah)
        pipes.add(lowerPipe); // Antrikan
    } // Beres pencetakan

    public boolean collision(Player a, Pipe b) { // Pemantau hukum benda padat bertabrakan
        return a.getPosX() < b.getPosX() + b.getWidth() && // Dinding barat A vs timur B
                a.getPosX() + a.getWidth() > b.getPosX() && // Dinding timur A vs barat B
                a.getPosY() < b.getPosY() + b.getHeight() && // Plafon A vs Lantai B
                a.getPosY() + a.getHeight() > b.getPosY();   // Lantai A vs Plafon B
    } // Mengembalikan bukti fakta

    public void move() { // Pengerak realita fisik dunia game
        player.setVelocityY(player.getVelocityY() + gravity); // Burung semakin melesat jatuh (Vt = Vo + gt)
        player.setPosY(player.getPosY() + player.getVelocityY()); // Pindah koordinat selaras dengan velositas
        player.setPosY(Math.max(player.getPosY(), 0)); // Benturkan dengan atap layar tak kasat mata

        for (int i = 0; i < pipes.size(); i++) { // Cek semua balok berjejer
            Pipe pipe = pipes.get(i); // Sentuh satu balok
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX()); // Tarik ke kiri layaknya bumi berputar

            if (!pipe.passed && player.getPosX() > pipe.getPosX() + pipe.getWidth()) { // Bila balok terlewati buntut burung
                pipe.passed = true; // Tandai dengan ceklis usang
                score += 0.5; // Ganjarkan koin setengah karena sepasang
                // view.updateScoreLabel DIHAPUS karena layar sudah canggih merender sendiri dari variabel getScore()
            } // Tutup pemberian poin

            if (collision(player, pipe)) { // Cek laka lantas
                gameOver = true; // Sirine merah
            } // Tutup
        } // Maju balok selanjutnya

        if (player.getPosY() > frameHeight) { // Cek jurang gravitasi terdalam
            gameOver = true; // Sirine merah
        } // Tutup
    } // Tutup semesta fisik

    @Override // Modifikasi turunan jam
    public void actionPerformed(ActionEvent e) { // Dipanggil otomatis
        move(); // Kalkulasikan letak baru semuanya
        if (view != null) { // Pastikan layar sudah nempel
            view.repaint(); // Sapu dan gambar bingkai terupdate
        } // Tutup layar

        if (gameOver) { // Bila layar beku mematikan
            pipesCooldown.stop(); // Hentikan kiriman rintangan
            gameLoop.stop(); // Hentikan detak layar sepenuhnya agar mematung
        } // Tutup pembekuan
    } // Selesai frame update

    @Override // Kosong wajib
    public void keyTyped(KeyEvent e) {} // Kosong

    @Override // Reseptor hantaman
    public void keyPressed(KeyEvent e) { // Terima info
        if (e.getKeyCode() == KeyEvent.VK_SPACE) { // Jika Spacebar dihantam
            player.setVelocityY(-10); // Burung ditendang ke angkasa
        } // Tutup

        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) { // Fitur dewa membangkitkan[cite: 4]
            player.setPosY(playerStartPosY); // Tarik burung balik ke perut bumi (tengah)
            player.setVelocityY(0); // Normalkan velositas jadi diam
            pipes.clear(); // Hancurkan sisa rintangan masa lalu
            gameOver = false; // Turunkan bendera setengah tiang

            score = 0; // Hapus rekor, kembali ke fitrah

            gameLoop.start(); // Pompa kembali detak layar
            pipesCooldown.start(); // Nyalakan keran pencetak
        } // Tutup kebangkitan
    } // Beres pencet

    @Override // Kosong wajib
    public void keyReleased(KeyEvent e) {} // Kosong
} // Tamat