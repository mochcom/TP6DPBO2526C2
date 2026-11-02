import java.awt.*; // Mengimpor pustaka AWT untuk memproses variabel Image

public class Player {
    private int posX; // Titik koordinat horizontal (kiri-kanan) burung
    private int posY; // Titik koordinat vertikal (atas-bawah) burung
    private int width; // Lebar hitbox/gambar burung
    private int height; // Tinggi hitbox/gambar burung
    private Image image; // Tempat menyimpan aset gambar burung
    private int velocityY = 0; // Kecepatan jatuh vertikal (awalnya 0 atau diam)

    // Konstruktor untuk memberikan nilai awal saat objek burung diciptakan
    public Player(int posX, int posY, int width, int height, Image image) {
        this.posX = posX; // Mengisi nilai awal posX
        this.posY = posY; // Mengisi nilai awal posY
        this.width = width; // Mengisi nilai awal lebar
        this.height = height; // Mengisi nilai awal tinggi
        this.image = image; // Menyisipkan file gambar
        this.velocityY = 0; // Memastikan kecepatan awal vertikal adalah 0
    }

    // Blok metode Getter (mengambil data) dan Setter (mengubah data)
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
    public int getVelocityY() { return velocityY; }
    public void setVelocityY(int velocityY) { this.velocityY = velocityY; }
}