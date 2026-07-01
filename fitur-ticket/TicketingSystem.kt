import java.util.Scanner

// ==========================================
// 1. MODEL LAYER (Encapsulation)
// ==========================================

class PromoCode(
    val kode: String,
    var diskon: Int,
    var aktif: Boolean = true
)

class Tiket(
    val idTiket: String,
    val jenisTiket: String,
    private val harga: Double,
    private var stok: Int
) {
    private var terjual: Int = 0

    fun getSisaKuota(): Int = stok
    fun getHarga(): Double = harga
    fun getTerjual(): Int = terjual

    fun prosesPenjualan(jumlah: Int): Boolean {
        if (stok >= jumlah) {
            terjual += jumlah
            stok -= jumlah
            return true
        }
        return false
    }

    fun updateStok(jumlahBaru: Int) {
        this.stok = jumlahBaru
        println("Sistem: Stok berhasil diperbarui menjadi $stok.")
    }

    fun resetTerjual() {
        this.terjual = 0
        println("Sistem: Tiket terjual berhasil direset ke 0.")
    }
}

class Panitia(val idPanitia: String, val nama: String) {
    private val passwordAdmin = "bem2026"
    
    private val daftarPromo = mutableMapOf<String, PromoCode>(
        "KONSERBEM" to PromoCode("KONSERBEM", 20, true),
        "BEM2026" to PromoCode("BEM2026", 15, true),
        "MAHASISWA" to PromoCode("MAHASISWA", 10, false)
    )

    fun login(pass: String): Boolean = pass == passwordAdmin

    fun lihatSemuaPromo() {
        println("\n--- DAFTAR KODE PROMO ---")
        println("Kode\t\tDiskon\tStatus")
        println("────────────────────────────────")
        daftarPromo.forEach { (kode, promo) ->
            val status = if (promo.aktif) "AKTIF" else "TIDAK AKTIF"
            println("$kode\t\t${promo.diskon}%\t$status")
        }
        println()
    }

    fun getPromoAktif(): List<PromoCode> {
        return daftarPromo.values.filter { it.aktif }
    }

    fun tambahPromo(kode: String, diskon: Int) {
        if (daftarPromo.containsKey(kode.uppercase())) {
            println("❌ Kode promo '$kode' sudah ada!")
        } else {
            daftarPromo[kode.uppercase()] = PromoCode(kode.uppercase(), diskon, true)
            println("✅ Kode promo '$kode' berhasil ditambahkan dengan diskon $diskon%")
        }
    }

    fun editPromo(kode: String, diskonBaru: Int, aktifBaru: Boolean) {
        val promo = daftarPromo[kode.uppercase()]
        if (promo != null) {
            promo.diskon = diskonBaru
            promo.aktif = aktifBaru
            println("✅ Kode promo '$kode' berhasil diperbarui!")
        } else {
            println("❌ Kode promo '$kode' tidak ditemukan!")
        }
    }

    fun hapusPromo(kode: String) {
        if (daftarPromo.remove(kode.uppercase()) != null) {
            println("✅ Kode promo '$kode' berhasil dihapus!")
        } else {
            println("❌ Kode promo '$kode' tidak ditemukan!")
        }
    }

    fun validasiPromo(kode: String): PromoCode? {
        val promo = daftarPromo[kode.uppercase()]
        return if (promo != null && promo.aktif) promo else null
    }
}

// ==========================================
// 2. MAIN APP (Interaction Layer)
// ==========================================

// Data promo global yang bisa diubah
var dataPromo = mutableMapOf(
    "KONSERBEM" to Pair(20, true),
    "BEM2026" to Pair(15, true),
    "MAHASISWA" to Pair(10, false)
)

fun main() {
    val input = Scanner(System.`in`)
    
    val tiketKonser = Tiket("T001", "Festival", 150000.0, 5)
    val panitiaBEM = Panitia("P01", "Irfan Naufal")

    while (true) {
        println("\n=== SISTEM TICKETING EVENT BEM ===")
        println("1. Login sebagai Pembeli")
        println("2. Login sebagai Panitia")
        println("0. Keluar")
        print("Pilih Peran: ")

        when (input.nextInt()) {
            1 -> {
                println("\n--- PENJUALAN TIKET KONSER ---")
                println("Jenis: ${tiketKonser.jenisTiket} | Harga: Rp${tiketKonser.getHarga()}")
                println("Stok Tersedia: ${tiketKonser.getSisaKuota()}")

                val promoAktif = panitiaBEM.getPromoAktif()
                if (promoAktif.isNotEmpty()) {
                    println("\n🎫 KODE PROMO AKTIF:")
                    promoAktif.forEach { promo ->
                        println("   • ${promo.kode} (Diskon ${promo.diskon}%)")
                    }
                }

                print("\nMasukkan Nama Anda: ")
                val nama = input.next()
                print("Jumlah tiket yang dibeli: ")
                val jumlah = input.nextInt()

                if (tiketKonser.prosesPenjualan(jumlah)) {
                    print("Masukkan Kode Promo (ketik 'none' jika tidak ada): ")
                    val promo = input.next()

                    var totalHarga = tiketKonser.getHarga() * jumlah
                    
                    if (promo.lowercase() != "none") {
                        val promoValid = panitiaBEM.validasiPromo(promo)
                        if (promoValid != null) {
                            val diskon = promoValid.diskon.toDouble() / 100
                            println("✅ Kode Promo '${promoValid.kode}' Berhasil! Diskon ${promoValid.diskon}% diterapkan.")
                            totalHarga = totalHarga * (1 - diskon)
                        } else {
                            println("❌ Kode promo tidak valid atau sudah kadaluarsa!")
                        }
                    }

                    println("\n--- BUKTI PEMBELIAN ---")
                    println("Nama Pembeli: $nama")
                    println("Total Bayar : Rp$totalHarga")
                    println("Status      : BERHASIL")
                } else {
                    println("\n❌ Maaf, tiket sudah sold out atau stok tidak mencukupi!")
                }
            }

            2 -> {
                print("\nMasukkan Password Panitia: ")
                val pass = input.next()

                if (panitiaBEM.login(pass)) {
                    println("✅ Login Berhasil. Selamat bertugas, ${panitiaBEM.nama}!")
                    
                    var loopPanitia = true
                    while (loopPanitia) {
                        println("\n--- MENU PANITIA ---")
                        println("1. Update Stok Tiket")
                        println("2. Cek Laporan Stok")
                        println("3. Reset Tiket Terjual")
                        println("4. Kelola Kode Promo")
                        println("0. Logout")
                        print("Pilih Aksi: ")
                        
                        when (input.nextInt()) {
                            1 -> {
                                print("Masukkan jumlah stok baru: ")
                                val baru = input.nextInt()
                                tiketKonser.updateStok(baru)
                            }
                            2 -> {
                                println("\n--- LAPORAN STOK TIKET ---")
                                println("Stok Tersedia: ${tiketKonser.getSisaKuota()}")
                                println("Tiket Terjual: ${tiketKonser.getTerjual()}")
                            }
                            3 -> {
                                print("Yakin ingin reset jumlah tiket terjual ke 0? (y/n): ")
                                val konfirmasi = input.next().lowercase() == "y"
                                if (konfirmasi) {
                                    tiketKonser.resetTerjual()
                                } else {
                                    println("Operasi dibatalkan.")
                                }
                            }
                            4 -> {
                                var loopPromo = true
                                while (loopPromo) {
                                    println("\n--- KELOLA KODE PROMO ---")
                                    println("1. Lihat Semua Promo")
                                    println("2. Tambah Promo Baru")
                                    println("3. Edit Promo")
                                    println("4. Hapus Promo")
                                    println("0. Kembali")
                                    print("Pilih: ")
                                    
                                    when (input.nextInt()) {
                                        1 -> {
                                            // Tampilkan promo dari data global
                                            println("\n--- DAFTAR KODE PROMO ---")
                                            println("Kode\t\tDiskon\tStatus")
                                            println("────────────────────────────────")
                                            dataPromo.forEach { (kode, data) ->
                                                val status = if (data.second) "AKTIF" else "TIDAK AKTIF"
                                                println("$kode\t\t${data.first}%\t$status")
                                            }
                                            println()
                                        }
                                        2 -> {
                                            print("Masukkan kode promo baru: ")
                                            val kodeBaru = input.next().uppercase()
                                            print("Masukkan besar diskon (%): ")
                                            val diskonBaru = input.nextInt()
                                            if (diskonBaru in 1..100) {
                                                dataPromo[kodeBaru] = Pair(diskonBaru, true)
                                                println("✅ Kode promo '$kodeBaru' berhasil ditambahkan dengan diskon $diskonBaru%")
                                            } else {
                                                println("❌ Diskon harus antara 1-100%")
                                            }
                                        }
                                        3 -> {
                                            print("Masukkan kode promo yang akan diedit: ")
                                            val kodeEdit = input.next().uppercase()
                                            print("Masukkan diskon baru (%): ")
                                            val diskonBaru = input.nextInt()
                                            print("Aktifkan? (y/n): ")
                                            val aktifBaru = input.next().lowercase() == "y"
                                            if (dataPromo.containsKey(kodeEdit)) {
                                                if (diskonBaru in 1..100) {
                                                    dataPromo[kodeEdit] = Pair(diskonBaru, aktifBaru)
                                                    println("✅ Kode promo '$kodeEdit' berhasil diperbarui! (Diskon: $diskonBaru%, Aktif: $aktifBaru)")
                                                } else {
                                                    println("❌ Diskon harus antara 1-100%")
                                                }
                                            } else {
                                                println("❌ Kode promo '$kodeEdit' tidak ditemukan!")
                                            }
                                        }
                                        4 -> {
                                            print("Masukkan kode promo yang akan dihapus: ")
                                            val kodeHapus = input.next().uppercase()
                                            if (dataPromo.remove(kodeHapus) != null) {
                                                println("✅ Kode promo '$kodeHapus' berhasil dihapus!")
                                            } else {
                                                println("❌ Kode promo '$kodeHapus' tidak ditemukan!")
                                            }
                                        }
                                        0 -> loopPromo = false
                                        else -> println("Pilihan tidak tersedia.")
                                    }
                                }
                            }
                            0 -> loopPanitia = false
                            else -> println("Pilihan tidak tersedia.")
                        }
                    }
                } else {
                    println("❌ Password Salah! Akses Ditolak.")
                }
            }
            0 -> break
            else -> println("Pilihan tidak tersedia.")
        }
    }
    println("Sistem Berhenti. Terima kasih!")
}
