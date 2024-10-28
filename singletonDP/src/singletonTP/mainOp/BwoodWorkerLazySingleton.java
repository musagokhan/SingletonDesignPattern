package singletonTP.mainOp;

public class BwoodWorkerLazySingleton {
	private int checkCounter;
	
	/*
	sadece 1 kez olusturup değişmez şekilde sakladık.
	static olmasının nedeni bu sınıfın bir malıdır bundan dolayı
	NOT : singleton da bulunan bu static yapı gereği. Program ilk çalışır çalışmaz static yapılar initialize edilir.
	bunlardan çok olması demek ilk başlangıcı yavaşlatmak demek. 
	örneğin benim bu yapıya çok sonra ihtiyacım var diyelim, ancak static buna bakmaz hemen kod çalışınca bunları init eder.
		İŞTE bu üretimi yapmayıp geciktirme işlemi yapma işine LAZYSINGLETON denilir.
	
		!!! LAZYSINGLETON Yapmak:
		"getInstance()" methoduna NULL çheck koyulur.
			LAZYSINGLETON dezavantajı:
			!!! DİKKAT: lazySingleton multiThreat yapısında sıkıntıdır. 
						birçok kez getInstance ile burada üretilen nesne çağırılsada BİRDEN FAZLA NESNE ÜRETİLEBİLİR. Şöyleki:
						100 adet thread olsun. bunlardan 5 adet thread aynı anda "getInstance()" a girsin NULL görürler ve 5 tanesi de ayrı ayrı nesne üretir.
						Buda raiseCondition'dır :)
			-> BUNUNDA ONUNE GEÇMEK İÇİN:
			getInstance methoduna mutexLock yapısı kurularak thread safety sağlanır. !!! synchronized !!! keyword kullanılır.
				NOT: her thread için "synchronized" koymak aslında maliyetli sürekli Contexswitch yapıyoruz. 
				Bunun çözümü ise 1. threat NESNEyi üretince diğer thread lerin artık "synchronized" kullanmasını engellemektir.
				Buna (Double-checked Locking) denilir. Yani (synchronized) içindeki NULL kontrolünü başa al :)
				ÇOK DETAY: 
				(Double-checked Locking) ile koruma yapmış olsak bile: bir thread "threadX olsun" getInstance() içindeki en iç IF'e girsin,
				threadX Buraya kadar geldiğinde GENEL RAM alanınadaki adresi LOKAL'e kopyalar ve tüm işleri esasen LOKAL de yapar.
				Nesne oluştursun. Sonra nesnenin artık null olmadığı aşikar olur. threadX işini lokalde yaptığı için bu durumu GEnel RAM'e bildirene kadar
				diğer thread'ler 1.IF 'de halen NULL görür ve devam ederler. Onları sync durdurur. 
				ANCAK 1. IF'i geçmesi demek (Double-checked Locking) mekanizmasının açığı var demektir. 
				Bunu önlemek için "VOLATILE" keyword kullanılır. Bu ibare Sınıfın en başında tanımlanan değişkene verilir.
				VOLATILE : Bir thread iin lokal de yaptığı değişikliğin GEnel memoriye aktarılmasını garanti eden yapıdır.
				EK BILGI: raise Condition olduktan sonra 2 durum olabilir.
					1- dead lock (çoklu nesne yapısında görülür.)
					2- bu yukarıda anlatılan VOLATILE durumu
					Bundan mütevellit Volatile raise Condition vunlardan önce durum !
					
		NOT: Tüm bu korulamalra rağmen birisi gidip reflection ile kurucuyu kırarsa istediği kadar nesne üretebilir :))))
				
	 */
	
	public static volatile BwoodWorkerLazySingleton INSTANCELazySingleton;
	
	private BwoodWorkerLazySingleton() {
		checkCounter++; 
		// ustteki nesne olusturma islemi buraya gelecek ve sayıyı +1 yapacak.
		// buraya bu sınıf dışında erişim yapılamaz çünkü PRIVATE. dolayııs ile nesne üretimi sadece burada yapılır. bunu yapan yerde yukarı satırdaki tek yerdir.
	}
	
	public static BwoodWorkerLazySingleton getInstance() {
		if (INSTANCELazySingleton == null) { // bu kısım (Double-checked Locking) :sync yapı için Performans kazandırır.  
			synchronized (BwoodWorkerLazySingleton.class) { 	// bu kısım raise Condition onler.	 
				if (INSTANCELazySingleton == null) { // bu kısım lazySingleton icindir
					INSTANCELazySingleton = new BwoodWorkerLazySingleton();
				} 									 // bu kısım lazySingleton icindir
			} 												// bu kısım raise Condition onler.
		}									// bu kısım (Double-checked Locking) :sync yapı için Performans kazandırır.
		
		return INSTANCELazySingleton ;
	}
	
	public void cut() {
		System.out.println("kesme islemi yapilmisitir");
	}

	public void fire() {
		System.out.println("yakma islemi yapilmisitir");
	}

	public void getCheckCounter() {
		System.out.println("WoodWorkerLazySingleton uretilmis nesne miktari : " + checkCounter); 
	}

	public void setCheckCounter(int checkCounter) {
		this.checkCounter = checkCounter;
	}
	
}
