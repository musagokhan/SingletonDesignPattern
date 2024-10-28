package singletonTP.mainOp;

public enum DwoodWorkerEnum {	
	
	/*
	A & B & C isimli class larda reflection ve serialize ile kurucu kırılarak çok miktarda farklı nesne olusturulabilirdi. 
	Bunun onune ENUM yapısı ile geçilir. 
	 */
	

	INSTANCE; // Singleton örneği

	private int checkCounter;

	DwoodWorkerEnum() { // Özel constructor
		checkCounter++;
	    }
	public void cut() {
		System.out.println("Kesme işlemi yapılmıştır");
	}
	public void fire() {
		System.out.println("Yakma işlemi yapılmıştır");
	}

	public void getCheckCounter() {
		System.out.println("DwoodWorkerEnum üretilmiş nesne miktarı: " + checkCounter);
	}
	
}
