# 📝 NoteApp

Basit ama güçlü bir **Not Defteri Uygulaması**.  
Kullanıcılar not ekleyebilir, düzenleyebilir, silebilir ve notlarına resim ekleyebilir.  
Uygulama **Jetpack Compose, Room, MVVM** ve **Coil** kütüphaneleriyle geliştirilmiştir.  

---

## 🚀 Özellikler
- 📌 Not ekleme, düzenleme ve silme  
- 📷 Galeriden resim ekleme (Coil ile görsel gösterim)  
- 🔍 Not arama özelliği  
- 🌓 Karanlık / Aydınlık tema desteği  
- 🗑️ Tüm notları tek seferde silme  
- 🎨 Modern Material 3 tasarımı  
- ⚡ MVVM + Repository + Room mimarisi  

---

## 🛠️ Kullanılan Teknolojiler
- **Kotlin**  
- **Jetpack Compose** (UI)  
- **Room Database** (Local veritabanı)  
- **MVVM + Repository Pattern**  
- **Coil** (Resim yükleme)  
- **Material 3 Components**  

---

## 📂 Proje Yapısı
```bash
app/
└── src/main/java/com/albayemre/localeroom/
    ├── Data
    │   ├── Local
    │   │   ├── Dao
    │   │   │   └── NoteDatabaseDao.kt
    │   │   ├── Database
    │   │   │   └── NoteDatabase.kt
    │   │   ├── Entity
    │   │   │   └── DataEntity.kt
    │   │   └── Repository
    │   │       └── NoteRepository.kt
    │
    ├── Navigation
    │   ├── AppNavigation.kt
    │   └── NavHost.kt
    │
    ├── ViewModel
    │   └── NoteViewModel.kt
    │
    ├── ui
    │   ├── Screen
    │   │   ├── NoteAdd.kt
    │   │   ├── NoteListScreen.kt
    │   │   └── SplashScreen.kt
    │   └── theme
    │
    ├── MainActivity.kt
    └── NoteApp.kt
```
<p align="center">
  <img src="https://github.com/user-attachments/assets/6aaf19db-3670-4d50-b752-738cb225fdf2" alt="image1" width="250"/>
  <img src="https://github.com/user-attachments/assets/7752d255-ccc3-4eef-b14e-1d303e0f802a" alt="image2" width="250"/>
  <img src="https://github.com/user-attachments/assets/5ea716e7-d4db-4f3a-841d-7a221cfedec3" alt="image3" width="250"/>
</p>


▶️ Kurulum

Bu repoyu klonla:

git clone https://github.com/AlbayEmre/NoteApp.git

Android Studio ile aç
![Uploading image.png…]()

Gerekli bağımlılıkları indir (Gradle sync)

Uygulamayı çalıştır 
