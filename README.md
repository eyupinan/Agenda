# Kartaca Challenge
## Docker
 * Programın çalıştırılabilmesi için yapılması gereken ilk olarak github repository'sinin clone'lanıp ```docker-compose up```
 komutu ile çalışır hale getirilmesidir.
## Angular Server
 Angular server docker çalışır hale getirildikten sonra browser'da ```localhost:4200``` adresinde çalışır hale gelmektedir. Bu angular server'i api serveri olarak geliştirilmiş spring server'ına bağlı olarak çalışmaktadır. Port değişimi için Dockerfile içerisindeki cmd komutuna ```--port port_numarası``` eklenmeli, Expose değeri değiştirilmelidir.
## Spring Server
 Spring server'a başladıktan sonra ```localhost:8080``` adresinden erişim sağlanabilir. Eğer port değişimi yapılmak isteniyor ise yapılması gerekenler :
 * ```/resources/application.properties ``` içerisindeki port değeri değiştirilmeli
 * Dockerfile içerisindeki ```EXPOSE``` değeri değiştirilmeli
 * Angular Server'in spring server'ı tanıyabilmesi için ```challenge-front/schedule-app/src/proxy.conf.json``` dosyasındaki target url'nin port numarasının düzenlenmesi gerekir.
## Arayüz
 * Login işlemi ```localhost:port/login``` sayfasından , Kayıt işlemi ```localhost:port/register``` sayfasından yapılmaktadır.
 * Takvime erişim ```localhost:port/``` adresinden sağlanmaktadır. Eğer giriş yapılmamış ise giriş sayfasına yönlendirilir. 
 * Takvim sayfasında istenilen bir tarih yukarıdaki tarih giriş alanından belirlenebilir.
 * Bu alan içerisinde takvim üzerinde herhangi bir noktaya tıklanması durumunda yeni bir etkinlik oluşturmak için gerekli form açılır.
 * var olan bir etkinliğe tıklanması durumunda yukarıdaki form içeriği dolu bir şekilde ekrana yansır ve burada save butonu ile etkinlik güncellenebilir.Form içerisindeki ```delete``` butonu ile etkinlik silinebilir.
 * Yukarıdaki ```logout``` butonu ile oturum kapatılabilir.
## Api
 * Apiye giriş istekleri ```api/login``` kayıt istekleri ```api/register``` ve logout işlemi için ```/api/logout``` adresleri kullanılır. Register ve Login işlemleri urlencoded data ile post işlemi kabul etmektedir. Login için gönderilmesi gereken veriler:username,password'dür. Register işlemi içinse gereken veriler:username,password,phone,email,address'tir.
 * Event arama ve ekleme işlemleri ```/api/event``` adresinden gerçekleştirilir. Bu işlemler için GET ve POST komutları kullanılır. GET komutunda sorgu startDate ve endDate query parametreleri ile gerçekleştirilir. Girişi olmayan istekler reddedilir. Tarih formatı ```dd-MM-yyyy``` şeklinde olması gerekmektedir.Arayüzde bu aralık 1 hafta olarak api tarafına sorgu yapılmaktadır.
 * Güncelleme ve silme işlemleri için ```/api/event/{id}``` adresleri kullanılır. PUT ve DELETE metodları kullanılır.
 * Post ve put işlemlerinde body içerisinde asıl data bulunması gerekmektedir. Bu asıl data içeriği: header,description,startDate,endDate'tir.
