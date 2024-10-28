package singletonTP.mainOp;

public class AwoodWorker {
	private int checkCounter;
	
	/*
	sadece 1 kez olusturup değişmez şekilde sakladık.
	static olmasının nedeni bu sınıfın bir malıdır bundan dolayı
	NOT : singleton da bulunan bu static yapı gereği. Program ilk çalışır çalışmaz static yapılar initialize edilir.
	bunlardan çok olması demek ilk başlangıcı yavaşlatmak demek. örneğin benim bu yapıya çok sonra ihtiyacım var diyelim 
	ancak static buna bakmaz hemen akod çalışınca bunları init eder.
	İŞTE bu geciktirme işlemine LAZYSINGLETON denilir.
	 */
	
	public final static AwoodWorker INSTANCE  = new AwoodWorker(); 
	
	private AwoodWorker() {
		checkCounter++; 
		// ustteki nesne olusturma islemi buraya gelecek ve sayıyı +1 yapacak.
		// buraya bu sınıf dışında erişim yapılamaz çünkü PRIVATE. dolayııs ile nesne üretimi sadece burada yapılır. bunu yapan yerde yukarı satırdaki tek yerdir.
	}
	
	public static AwoodWorker getInstance() {
		return INSTANCE ;
	}
	
	public void cut() {
		System.out.println("kesme islemi yapilmisitir");
	}

	public void fire() {
		System.out.println("yakma islemi yapilmisitir");
	}

	public void getCheckCounter() {
		System.out.println("WoodWorker uretilmis nesne miktari : " + checkCounter); 
	}

	public void setCheckCounter(int checkCounter) {
		this.checkCounter = checkCounter;
	}
	
	
	
}
