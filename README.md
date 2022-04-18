# mind-game
Picture Matching Game Android Kotlin ve Java kullanılarak oluşturuldu.

## Geliştiren Ekip
Havva Nergiz <br>
Edanur Hamurcu
Umut Çakmak
Mustafa Uçar

## Screenshots
<table>
  <tr>
    <td>
      <img width="200" src="https://github.com/umutcakmaks/mind-game/blob/master/app/src/main/res/raw/home.png">
    </td>
    <td>
      <img width="200" src="https://github.com/umutcakmaks/mind-game/blob/master/app/src/main/res/raw/options.png">
    </td>
    <td>
    <img width="225" src="https://github.com/umutcakmaks/mind-game/blob/master/app/src/main/res/raw/mindgame.gif">
    </td>
  </tr>
  <tr>
    <td>
      <img width="200" src="https://github.com/umutcakmaks/mind-game/blob/master/app/src/main/res/raw/game.PNG">
    </td>
    <td>
      <img width="200" src="https://github.com/umutcakmaks/mind-game/blob/master/app/src/main/res/raw/gameopen.PNG">
    </td>
    <td>
      <img width="200" src="https://github.com/umutcakmaks/mind-game/blob/master/app/src/main/res/raw/scores.PNG">
    </td>
  </tr>
</table>

## Proje-Yapısı
Tasarımlar ile veriler arasında abstraction için adapter design pattern kullanıldı.

Proje de iki ana bağdaştırıcı kullanıldı:
Biri oyun oynarken kartlar için, diğeri oyun skorları için. Burada amaç view'ları verilerden soyutlamaktır, bu nedenle skorlar için kullanılan adapter:
-Görünümü tutmak için bir RecyclerView kullanılır
-RecordAdapter, RecyclerView için adapter'ı implement eden sınıftır.
-GameRecord, adapter'ın alacağı ve ekranda görüntülenebilmesi için view'e göndereceği bilgileri tutan class tır.
Bu şekilde, hem view GameRecord class'ı hem de GameRecord view'i bilmez. Adapter verileri GameRecord da liste olarak alır ve view'e doldurur.

### Test
MindGameLogicTest: Oyun mantığını test eder. Tüm tıklama kartlarının oyunun doğru şekilde yanıt vermesini sağladığını iddia eder.
JsonReaderTest: JsonReader, kartlarla ilgili tüm bilgileri içeren Json (products.json) dosyasını düzgün okunduğunu garanti eder.

## Özellikler
1- Kartların eşleme sayısı ve zamanı tutulur. Oyun bittiğinde ise puan ve süre tutularak oyun kaydı skorlar ekranında görünür halde

2- Geri sayım modu. Bu mod, oyuna başlamadan önce seçilecek bir kategoridir, böylece iki ana mod olacaktır: normal (gerçek uygulanmış) ve geri sayım. Seçilen zorluğa bağlı olarak, zamanlayıcı daha kısa veya daha uzun olacaktır.

## Mimari
1- DataBinding ve LiveData: Data binding, view objelerine binding nesnesi üzerinden erişimi sağlıyor.Data Binding bize layotlarımız (user interface) ile application logic ve modelimizi birbirine senkron şekilde kullanmamıza olanak sağlar. 
LiveData, gözlemlenebilir bir veri tutma sınıfıdır. LiveData Observable’ın aksine LifeCycle-Aware dir. Yani Activity, Fragment ya da Servis gibi bileşenlerin yaşam döngülerine karşılık hareket edebilir. 
Bu özellik LiveData’nın sadece aktif yaşam döngüsündeki bileşemlerin gözlemleyicilerini güncelleştirmesini sağlar. LiveData Observer sınıfından türetilen bir observer ın aktif yaşam döngüsünde olduğunu varsayar.


2- Room: Room Kütüphanesi sayesinde SQLite ile oluşturduğumuz bir veri tabanın da veri ekleme,silme,güncelleme ve listeleme gibi işlemleri yaparken önümüze çıkan hataları bulmak daha kolay hale gelmektedir.


3- Dagger : "dependency injection” kütüphanesidir.
Bu kütüphane sınıf bağımlıklıklarını kolaylıkla yönetmemizi sağlar. Hangi sınıfları nerede kullanmak istediğimizi belirtiriz ve arka planda Dagger 2 bu sınıfları bizim için oluşturur. Kullanmak istediğimiz yerde ise bize bu sınıfları sağlar.

## Kütüphaneler
Kullanılan Kütüphaneler:

-Resim yükleme için Picasso

-Java nesnelerini JSON'a dönüştürmek için Google Gson

-Çift yönlü hashmapler için Google Guava
