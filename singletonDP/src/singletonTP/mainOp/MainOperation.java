package singletonTP.mainOp;

public class MainOperation {

	public static void main(String[] args) {		
		
		AwoodWorker.getInstance().getCheckCounter();	
		// (WoodWorker) sınıfının (getInstance) methodu ile (WoodWorker) içinde oluşan nesneyi alırız. (getCheckCounter) ile de kontrol ederiz.
		
		BwoodWorkerLazySingleton.getInstance().getCheckCounter();
		// lazySingleton yapısıdır.
		
		
		DwoodWorkerEnum.INSTANCE.getCheckCounter();
		// enum ile singleton yapımı

	}

}

/*
Singleton Tassarım Kalıbı hakkında bilgiler:
Tanım: 1 class'dan 1 nesne oluşturulmasıdır.

* Oluşturma yolları : singleton oluşturmanın 2 yolu:
	1- Nesne yaratmayı kontrol ederek
	2- ENUM kullanarak 
2.daha sıkıntısız yoldur.

1- Nesne yaratmayı kontrol ederek singleton T.D. :
Aşağıda adım adım deetaylı anlatım mevcuttur. Lütfen sabır ile oku çok öenmli bilgiler var.

AwoodWorker.java : en temel nesne yaratma kontrollü yapıdır.
a. kurucu PRIVATE yapılırki kimse kullanamasın. 
b. Nesneyi class tanımınn yapıldığı yerde oluştururuz. 1.maddede private yaptık.
c. Oluşturulan nesneyi paylaşmak için getInstance() methodu kullanırız. Bu method STATIC'tir. çünkü üretilen nesne artık Sınıfın malıdır.
d. BİLGİ: Program ilk çalışır çalışmaz static yapılar initialize edilir. Buda demek olyorki  bizim bu nesne program çalışır çalışmaz nesne oluşturur. Buda ilk başlangıcı yavaşlatabilrir. Lazım olduğunda oluşturulmasını istersek. Buna LAZYSINGLETON denilir.

BwoodWorkerLazySingleton.java : LAZYSINGLETON için temel bir örnektir.
a. "getInstance()" methoduna NULL çheck koyulur. Böylelikle istenildiğinde nesne oluşturulur.
b. !!! DİKKAT: lazySingleton multiThreat yapısında sıkıntıdır. 
	Birçok kez getInstance ile burada üretilen nesne çağırılsada BİRDEN FAZLA NESNE ÜRETİLEBİLİR. Şöyleki:
	100 adet thread olsun. bunlardan 5 adet thread aynı anda "getInstance()" a girsin NULL görürler ve 5 tanesi de ayrı ayrı nesne üretir. Bu olay raiseCondition'dır :) BUNUNDA ONUNE GEÇMEK İÇİN:
	
	b.1. getInstance methoduna mutexLock yapısı kurularak thread safety sağlanır. !!! synchronized !!! keyword kullanılır.
	b.2. NOT: her thread için "synchronized" koymak aslında maliyetli sürekli Contexswitch yapıyoruz. Örnek ile: threadA geldi synchronized yapıya girip mutexLock yaptı. İşini bitip NULL olan yapıyı kırdı. Ancak her thread buraya gelince kilidi açar ve zaten yapılmış NULL bozma işini görür ve geri çıkar. Yani sürekli thread'lerde contextSwitch yapıalrak performans tüketilir. BUNUNDA ONUNE GEÇMEK İÇİN:
		b.2.1.  Bunun çözümü ise 1. threat NESNE'yi üretince diğer thread lerin artık "synchronized" kullanmasını engellemektir. Bu olaya da (Double-checked Locking) denilir. Yani (synchronized) içindeki NULL kontrolünü (synchronized) başına tekrar getir :)
		ÇOK DETAYLI bir yaklaşım : 
		b.2.2.  (Double-checked Locking) ile koruma yapmış olsak bile: bir thread "threadX olsun" getInstance() içindeki en iç IF'e girsin. "ÖNEMLİ BİLGİ: threadX Buraya kadar geldiğinde GENEL RAM alanınadaki adresi LOKAL'e kopyalar ve tüm işleri esasen LOKAL de yapar."  En iç IF'e girmesi demek NULL yapı gördü nesne üret demek, böylelikle threadX Nesne üretir. Sonrasında değeri NULL'dan farklı kılar. TAM BURASI ÖENMLİ: farklı kıldığı yer esasen lokal olarak threadX'in kullandığı RAM'dir. Bu lokladeki değişikliklik GENEL RAM alanına taşınırki diğer thread'ler tarafından görülsün. Ancak bu taşıma anına kadar Genel alan halen NULL kalacağı için en başta bu alan olduğundan dolayı diğer threrad'ler ilk IF şartını geçip synchronized alanına gelirler. Özetle en dışa koyulan IF bir süre işini göremeyecektir. Performansdan bir süre ödün vermeye devam eceğiz. Bununda önüne geçmek için:
		b.2.3 VOLATILE keyword kullanılır.  Bu ibare Sınıfın en başında tanımlanan değişkene verilir.
			Genel ara bilgi:
			VOLATILE : Bir thread iin lokal de yaptığı değişikliğin Genel memoriye aktarılmasını garanti eden yapıdır.
				EK BILGI: raise Condition olduktan sonra 2 durum olabilir.
					1- dead lock (çoklu nesne yapısında görülür.)
					2- bu yukarıda anlatılan VOLATILE durumu
					Bundan mütevellit Volatile raise Condition bunlardan önce durum !
					
CwoodWorkerInnerClass.java : LAZYSINGLETON yapısını inner class ile sağlayan başka bir yapıdır. Bill Pugh eseridi. Bu yapıda INNER_private_static yapı kurulur. JVM kod derlemesinde staticleri ilk başta yapıyor ama bu static INNER ise ilk başta derlemiyor. Bu özllikten faydalanılmıştır.
NOT: multiThread yapıısna uygundur.
					
!!! Tüm bu korulamalra rağmen birisi gidip reflection veya serialize ile kurucuyu kırarsa istediği kadar nesne üretebilir :))))Bunun da önüne geçmek için singleton yapmaktaki 2. yol olan ENUM kullanılır.

2. ENUM ile singleton T.D. :
DwoodWorkerEnum.java : ENUM yapısı ile singleton nesne üretimi yapılır. 	

 
*/