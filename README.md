# Ticketing Event BEM - Penjualan Tiket Konser

**Tugas Pemrograman Berorientasi Objek (PBO)**  
Disusun oleh: Irfan Naufal (04231040)

---

## 📋 Deskripsi Proyek

Sistem aplikasi ticketing untuk penjualan tiket konser yang diselenggarakan oleh BEM (Badan Eksekutif Mahasiswa). Aplikasi ini dirancang menggunakan prinsip-prinsip Pemrograman Berorientasi Objek untuk mengelola proses pembelian tiket dengan validasi kuota dan kode promo.

---

## 👥 Entitas Utama

### 1. **Pembeli**
- Atribut: ID Pembeli, Nama, Email, Nomor Telepon
- Fungsi: Melakukan pembelian tiket dan menggunakan kode promo

### 2. **Panitia**
- Atribut: ID Panitia, Nama, Role, Kontak
- Fungsi: Mengelola acara, kuota tiket, dan validasi kode promo

### 3. **Tiket**
- Atribut: ID Tiket, Jenis Tiket, Harga, Kuota, Terjual, Status
- Fungsi: Merepresentasikan ketersediaan tiket untuk konser

---

## 📌 Aturan Bisnis

### Pembelian Tiket Ditolak Jika:

1. **❌ Kuota Tiket Habis (Sold Out)**
   - Jika jumlah tiket terjual sudah mencapai kuota maksimal
   - Sistem akan menampilkan pesan: "Maaf, tiket sudah sold out"

2. **❌ Kode Promo Tidak Valid**
   - Kode promo salah/tidak sesuai
   - Kode promo sudah kadaluarsa (expired)
   - Sistem akan menampilkan pesan: "Kode promo tidak valid atau sudah kadaluarsa"

### Pembelian Tiket Berhasil Jika:

3. **✅ Kuota Tiket Tersedia**
4. **✅ Kode Promo Valid (jika digunakan)**
5. **✅ Data Pembeli Lengkap dan Valid**

---

## 🏗️ Struktur Proyek

```
PemrogramanBerorientasiObjek/
├── src/
│   ├── model/
│   │   ├── Pembeli.java
│   │   ├── Panitia.java
│   │   ├── Tiket.java
│   │   └── PromoCode.java
│   ├── service/
│   │   ├── TicketingService.java
│   │   ├── PromoService.java
│   │   └── PembelianService.java
│   └── Main.java
├── test/
│   └── TicketingTest.java
├── README.md
└── .gitignore
```

---

## 🚀 Cara Penggunaan

### 1. Compile Proyek
```bash
javac -d bin src/model/*.java src/service/*.java src/Main.java
```

### 2. Jalankan Aplikasi
```bash
java -cp bin Main
```

### 3. Fitur Utama
- ✏️ Input data pembeli
- 🎫 Pilih jenis tiket
- 💰 Masukkan kode promo (opsional)
- ✅ Validasi dan proses pembelian
- 📄 Tampilkan bukti pembelian

---

## 💻 Persyaratan Sistem

- **Java Development Kit (JDK)** 8 atau lebih tinggi
- **IDE** : Eclipse, IntelliJ IDEA, atau NetBeans (opsional)
- **Text Editor** : VS Code, Sublime Text, atau sejenisnya

---

## 📚 Konsep OOP yang Diterapkan

- **Encapsulation** : Atribut private dengan getter/setter
- **Inheritance** : Hirarki kelas untuk entitas yang berbeda
- **Polymorphism** : Method overriding untuk validasi berbeda
- **Abstraction** : Class/interface untuk blueprint bisnis logic

---

## 📝 Catatan Pengembangan

- [ ] Implementasi class model (Pembeli, Panitia, Tiket, PromoCode)
- [ ] Implementasi service layer untuk bisnis logic
- [ ] Implementasi validasi kuota tiket
- [ ] Implementasi validasi kode promo
- [ ] Implementasi proses pembelian
- [ ] Tambahkan unit testing
- [ ] Dokumentasi kode

---

## 📄 Lisensi

Proyek ini adalah tugas akademik dan dapat digunakan untuk keperluan pendidikan.

---

**Terakhir diupdate:** 23 april 2026# PBO_Praktik
