class Produk(id: String, namaProduk: String) {
    // 1. Data Hiding menggunakan modifier 'private'
    private val id: String = id
    private var namaProduk: String = namaProduk

    // 2. Getter manual di Kotlin
    fun getId(): String {
        return this.id
    }

    fun getNamaProduk(): String {
        return this.namaProduk
    }

    // 3. Setter dengan Validasi Proteksi Objek (Standar Bab 5)
    fun setNamaProduk(namaBaru: String) {
        if (namaBaru.isBlank()) {
            println("[PROTEKSI ATM]: Mengubah nama gagal, data tidak boleh kosong!")
        } else {
            this.namaProduk = namaBaru
            println("[SUKSES]: Data produk berhasil diperbarui.")
        }
    }
}

// MAIN FUNCTION UNTUK RUN DI INTELLIJ
fun main() {
    val tiketVIP = Produk("PROD-01", "Coldplay Jakarta")
    tiketVIP.setNamaProduk("") // Menguji proteksi enkapsulasi
}