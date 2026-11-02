import java.awt.*; // Mengimpor pustaka grafis

public class Pipe { // Deklarasi kelas Pipe
    private int posX; // Titik koordinat horizontal pipa
    private int posY; // Titik koordinat vertikal pipa
    private int width; // Lebar gambar pipa
    private int height; // Tinggi gambar pipa
    private Image image; // Tempat menyimpan aset gambar pipa (atas/bawah)
    private int velocityX; // Kecepatan pipa bergerak ke arah kiri
    boolean passed = false; // Penanda apakah burung sudah melewati pipa ini (untuk skor nanti)

    // Konstruktor untuk memberikan nilai awal saat objek pipa diciptakan
    public Pipe(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;

        this.velocityX = -3; // Nilai negatif agar pipa bergerak ke kiri (bisa disesuaikan)
        this.passed = false; // Set awal ke false karena burung belum melewatinya
    }

    // Blok Getter dan Setter
    public int getPosX() { return posX; }
    public void setPosX(int posX) { this.posX = posX; }
    public int getPosY() { return posY; }
    public void setPosY(int posY) { this.posY = posY; }
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public Image getImage() { return image; }
    public void setImage(Image image) { this.image = image; }
    public int getVelocityX() { return velocityX; }
    public void setVelocityX(int velocityX) { this.velocityX = velocityX; }
}